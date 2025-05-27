package me.dxrk.Mines;

import it.unimi.dsi.fastutil.shorts.Short2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.chunk.LevelChunkSection;
import net.minecraft.world.ticks.LevelChunkTicks;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;


public class DynamicMultiBlockPacketSender {
    private final Plugin plugin;
    private static final boolean DEBUG = false;

    public DynamicMultiBlockPacketSender(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Represents the distribution pattern for a specific block type
     */
    public record BlockChance(
            ItemStack itemStack,
            double startWeight,
            double peakWeight,
            double endWeight,
            double peakLevel,
            double allowedDepthLevel1,
            double allowedDepthLevel100,
            boolean isParabolic,
            int minimumLevel
    ) {
        public BlockChance {
            if (itemStack == null) {
                throw new IllegalArgumentException("ItemStack cannot be null");
            }
            if (peakWeight < 0 || startWeight < 0 || endWeight < 0) {
                throw new IllegalArgumentException("Weights cannot be negative");
            }
            if (minimumLevel < 1 || minimumLevel > 100) {
                throw new IllegalArgumentException("Minimum level must be between 1 and 100");
            }
        }
    }

    private static class ChunkSectionData {
        final SectionPos sectionPos;
        final Short2ObjectMap<BlockState> blockStates;

        ChunkSectionData(SectionPos sectionPos) {
            this.sectionPos = sectionPos;
            this.blockStates = new Short2ObjectLinkedOpenHashMap<>();
        }

        void addBlock(BlockPos pos, BlockState state, ServerLevel level) {
            short relativePos = SectionPos.sectionRelativePos(pos);
            blockStates.put(relativePos, state);
        }
    }


    private static double calculateWeight(BlockChance block, int level) {
        if (level < block.minimumLevel()) {
            return 0;
        }

        if (!block.isParabolic) {
            double t = (level - block.minimumLevel()) /
                    (double) (100 - block.minimumLevel());
            return block.startWeight() + (block.endWeight() - block.startWeight()) * t;
        }

        double normalizedLevel;
        if (level <= block.peakLevel()) {
            normalizedLevel = (level - block.minimumLevel()) /
                    (block.peakLevel() - block.minimumLevel());
        } else {
            normalizedLevel = 1 + (level - block.peakLevel()) /
                    (100 - block.peakLevel());
        }

        if (normalizedLevel < 0) {
            return 0;
        }

        if (level <= block.peakLevel()) {
            double t = normalizedLevel;
            return block.startWeight() + (block.peakWeight() - block.startWeight()) * (t * t);
        } else {
            double t = (level - block.peakLevel()) / (100 - block.peakLevel());
            return block.peakWeight() + (block.endWeight() - block.peakWeight()) * t;
        }
    }

    private BlockState materialToBlockState(Material material) {
        Block nmsBlock =
                CraftMagicNumbers.getBlock(material);
        return nmsBlock.defaultBlockState();
    }

    private Material getRandomBlockForLevelAndDepth(
            int level,
            double currentRelY,
            List<BlockChance> blockChances
    ) {
        level = Math.max(1, Math.min(100, level));
        double totalWeight = 0;
        double[] effectiveWeights = new double[blockChances.size()];

        for (int i = 0; i < blockChances.size(); i++) {
            BlockChance bc = blockChances.get(i);
            double weight = calculateWeight(bc, level);

            double allowedDepth = bc.allowedDepthLevel1() +
                    (bc.allowedDepthLevel100() - bc.allowedDepthLevel1()) * ((level - 1) / 99.0);

            if (currentRelY <= allowedDepth) {
                effectiveWeights[i] = weight;
            } else {
                effectiveWeights[i] = 0;
            }
            totalWeight += effectiveWeights[i];
        }

        if (totalWeight <= 0) {
            return blockChances.get(0).itemStack().getType();
        }

        double random = Math.random() * totalWeight;
        for (int i = 0; i < effectiveWeights.length; i++) {
            if (random < effectiveWeights[i]) {
                return blockChances.get(i).itemStack().getType();
            }
            random -= effectiveWeights[i];
        }
        return blockChances.get(blockChances.size() - 1).itemStack().getType();
    }

    private void debugLog() {
        if (DEBUG) {
            plugin.getLogger().info("[Debug] " + "Finished sending all packets");
        }
    }

    private BlockPos.MutableBlockPos getBlockPos(Location loc) {
        return new BlockPos.MutableBlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    public List<ChunkPos> getPos(Location loc1, Location loc2) {
        World world = loc1.getWorld();
        ServerLevel serverLevel = ((CraftWorld) world).getHandle();


        int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        int minChunkX = x1 >> 4;
        int maxChunkX = x2 >> 4;
        int minChunkZ = z1 >> 4;
        int maxChunkZ = z2 >> 4;
        List<ChunkPos> chunkPositions = new ArrayList<>();
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);
                chunkPositions.add(chunkPos);
            }
        }
        return chunkPositions;
    }

    public Map<ChunkPos, LevelChunk> getModifiedChunks(Location loc1, Location loc2, Player player, int level, List<BlockChance> blockChances) {
        if (!loc1.getWorld().equals(loc2.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world");
        }
        if (blockChances.isEmpty()) {
            throw new IllegalArgumentException("Block chances list cannot be empty");
        }

        // Get NMS handles
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        World world = loc1.getWorld();
        ServerLevel serverLevel = ((CraftWorld) world).getHandle();


        int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());


        double totalHeight = 0;
        if (totalHeight <= 0) totalHeight = 1;
        else {
            totalHeight = y2 - y1;
        }
        double finalTotalHeight = totalHeight;
        int minChunkX = x1 >> 4;
        int maxChunkX = x2 >> 4;
        int minChunkZ = z1 >> 4;
        int maxChunkZ = z2 >> 4;
        Map<ChunkPos, LevelChunk> modifiedChunks = new HashMap<>();
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);

                if (!serverLevel.getChunkSource().hasChunk(chunkX, chunkZ)) {
                    continue;
                }

                LevelChunk realChunk = serverLevel.getChunk(chunkX, chunkZ);

                LevelChunk fakeChunk = createFakeChunk(realChunk, serverLevel);
                modifiedChunks.put(chunkPos, fakeChunk);

                int startX = Math.max(x1, chunkX << 4);
                int endX = Math.min(x2, (chunkX << 4) + 15);
                int startZ = Math.max(z1, chunkZ << 4);
                int endZ = Math.min(z2, (chunkZ << 4) + 15);

                for (int x = startX; x <= endX; x++) {
                    for (int z = startZ; z <= endZ; z++) {
                        for (int y = y1; y <= y2; y++) {
                            int sectionIndex = fakeChunk.getSectionIndex(y >> 4);
                            if (sectionIndex < 0 || sectionIndex >= fakeChunk.getSections().length) {
                                continue;
                            }

                            int relX = x & 0xF;
                            int relZ = z & 0xF;
                            int relY = y & 0xF;

                            double currentRelY = (y - y1) / finalTotalHeight;

                            Material material = getRandomBlockForLevelAndDepth(
                                    level,
                                    currentRelY,
                                    blockChances
                            );
                            BlockState blockState = materialToBlockState(material);
                            LevelChunkSection section = fakeChunk.getSection(sectionIndex);
                            if (section != null) {
                                section.setBlockState(relX, relY, relZ, blockState, false);
                            }
                        }
                    }
                }
            }
        }
        return modifiedChunks;
    }


    public void sendDynamicAreaPacketsChunk(
            Player player,
            Location loc1,
            Location loc2,
            int level,
            List<BlockChance> blockChances
    ) {
        if (!loc1.getWorld().equals(loc2.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world");
        }
        if (blockChances.isEmpty()) {
            throw new IllegalArgumentException("Block chances list cannot be empty");
        }


        // Get NMS handles
        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        World world = loc1.getWorld();
        ServerLevel serverLevel = ((CraftWorld) world).getHandle();

        if (!player.getWorld().equals(world)) {
            plugin.getLogger().info("Player " + player.getName() + " is not in the target world. Skipping packet send.");
            return;
        }

        int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());


        double totalHeight = 0;
        if (totalHeight <= 0) totalHeight = 1;
        else {
            totalHeight = y2 - y1;
        }
        double finalTotalHeight = totalHeight;
        CompletableFuture.runAsync(() -> {
            try {
                int minChunkX = x1 >> 4;
                int maxChunkX = x2 >> 4;
                int minChunkZ = z1 >> 4;
                int maxChunkZ = z2 >> 4;

                Map<ChunkPos, LevelChunk> modifiedChunks = new HashMap<>();

                for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
                    for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                        ChunkPos chunkPos = new ChunkPos(chunkX, chunkZ);

                        if (!serverLevel.getChunkSource().hasChunk(chunkX, chunkZ)) {
                            continue;
                        }

                        LevelChunk realChunk = serverLevel.getChunk(chunkX, chunkZ);

                        LevelChunk fakeChunk = createFakeChunk(realChunk, serverLevel);
                        modifiedChunks.put(chunkPos, fakeChunk);

                        int startX = Math.max(x1, chunkX << 4);
                        int endX = Math.min(x2, (chunkX << 4) + 15);
                        int startZ = Math.max(z1, chunkZ << 4);
                        int endZ = Math.min(z2, (chunkZ << 4) + 15);

                        for (int x = startX; x <= endX; x++) {
                            for (int z = startZ; z <= endZ; z++) {
                                for (int y = y1; y <= y2; y++) {
                                    int sectionIndex = fakeChunk.getSectionIndex(y >> 4);
                                    if (sectionIndex < 0 || sectionIndex >= fakeChunk.getSections().length) {
                                        continue;
                                    }

                                    int relX = x & 0xF;
                                    int relZ = z & 0xF;
                                    int relY = y & 0xF;

                                    double currentRelY = (y - y1) / finalTotalHeight;

                                    Material material = getRandomBlockForLevelAndDepth(
                                            level,
                                            currentRelY,
                                            blockChances
                                    );
                                    BlockState blockState = materialToBlockState(material);
                                    LevelChunkSection section = fakeChunk.getSection(sectionIndex);
                                    if (section != null) {
                                        section.setBlockState(relX, relY, relZ, blockState, false);
                                    }
                                }
                            }
                        }
                    }
                }

                final Map<ChunkPos, LevelChunk> finalModifiedChunks = modifiedChunks;
                try {
                    if (!player.getWorld().equals(world)) {
                        plugin.getLogger().info("Player " + player.getName() + " changed worlds. Skipping packet send.");
                        return;
                    }
                    for (LevelChunk fakeChunk : finalModifiedChunks.values()) {
                        ClientboundLevelChunkWithLightPacket packet = new ClientboundLevelChunkWithLightPacket(
                                fakeChunk,
                                serverLevel.getLightEngine(),
                                null,
                                null,
                                true
                        );
                        serverPlayer.connection.send(packet);
                    }
                } catch (Exception e) {
                    plugin.getLogger().warning("Failed to send chunk packets: " + e.getMessage());
                    if (DEBUG) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to process dynamic area packets: " + e.getMessage());
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        });
    }


    private LevelChunk createFakeChunk(LevelChunk original, ServerLevel level) {
        ChunkPos pos = original.getPos();

        LevelChunkSection[] originalSections = original.getSections();
        LevelChunkSection[] clonedSections = new LevelChunkSection[originalSections.length];

        for (int i = 0; i < originalSections.length; i++) {
            LevelChunkSection originalSection = originalSections[i];
            if (originalSection != null && !originalSection.hasOnlyAir()) {
                clonedSections[i] = new LevelChunkSection(
                        level.registryAccess().registryOrThrow(Registries.BIOME),
                        level,
                        pos,
                        i + level.getMinSection()
                );

                for (int x = 0; x < 16; x++) {
                    for (int y = 0; y < 16; y++) {
                        for (int z = 0; z < 16; z++) {
                            BlockState state = originalSection.getBlockState(x, y, z);
                            if (!state.isAir()) {
                                clonedSections[i].setBlockState(x, y, z, state, false);
                            }
                        }
                    }
                }
            } else {
                clonedSections[i] = new LevelChunkSection(
                        level.registryAccess().registryOrThrow(Registries.BIOME),
                        level,
                        pos,
                        i + level.getMinSection()
                );
            }
        }

        return new LevelChunk(
                level,
                pos,
                original.getUpgradeData(),
                new LevelChunkTicks<>(),
                new LevelChunkTicks<>(),
                original.getInhabitedTime(),
                clonedSections,
                null,
                original.getBlendingData()
        );
    }

    //new method for when staff visit player to see what they're mining

    public void sendDynamicAreaPackets(
            Player player,
            Location loc1,
            Location loc2,
            int level,
            List<BlockChance> blockChances
    ) {
        if (!loc1.getWorld().equals(loc2.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world");
        }
        if (blockChances.isEmpty()) {
            throw new IllegalArgumentException("Block chances list cannot be empty");
        }


        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        World world = loc1.getWorld();
        ServerLevel serverLevel = ((CraftWorld) world).getHandle();

        int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        int sizeX = Math.abs(x2 - x1) + 1;
        int sizeY = Math.abs(y2 - y1) + 1;
        int sizeZ = Math.abs(z2 - z1) + 1;
        int cuboidSize = sizeX * sizeY * sizeZ;

        Map<SectionPos, ChunkSectionData> sectionDataMap = new HashMap<>();

        int x = 0, y = 0, z = 0;
        Location location = new Location(world, x1, y1, z1);

        double totalHeight = y2 - y1;
        if (totalHeight <= 0) totalHeight = 1;

        for (int i = 0; i < cuboidSize; i++) {
            if (!serverLevel.getChunkSource().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
                Chunk c = location.getChunk();
                c.load();
            }

            BlockPos.MutableBlockPos pos = getBlockPos(location);
            SectionPos sectionPos = SectionPos.of(pos);

            ChunkSectionData sectionData = sectionDataMap.computeIfAbsent(
                    sectionPos,
                    ChunkSectionData::new
            );

            double currentRelY = (location.getY() - y1) / totalHeight;
            Material material = getRandomBlockForLevelAndDepth(
                    level,
                    currentRelY,
                    blockChances
            );

            BlockState blockState = materialToBlockState(material);
            sectionData.addBlock(pos, blockState, serverLevel);

            if (++x >= sizeX) {
                x = 0;
                if (++y >= sizeY) {
                    y = 0;
                    ++z;
                }
            }
            location.setX(x1 + x);
            location.setY(y1 + y);
            location.setZ(z1 + z);
        }

        for (ChunkSectionData sectionData : sectionDataMap.values()) {
            if (sectionData.blockStates.isEmpty()) {
                continue;
            }

            try {
                serverPlayer.connection.send(
                        new ClientboundSectionBlocksUpdatePacket(
                                sectionData.sectionPos,
                                sectionData.blockStates.keySet(),
                                sectionData.blockStates.values().toArray(BlockState[]::new)
                        )
                );
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to send packet for section " +
                        sectionData.sectionPos + ": " + e.getMessage());
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void jackhammer(
            Player player,
            Location loc1,
            Location loc2
    ) {
        if (!loc1.getWorld().equals(loc2.getWorld())) {
            throw new IllegalArgumentException("Locations must be in the same world");
        }


        ServerPlayer serverPlayer = ((CraftPlayer) player).getHandle();
        World world = loc1.getWorld();
        ServerLevel serverLevel = ((CraftWorld) world).getHandle();

        int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        int sizeX = Math.abs(x2 - x1) + 1;
        int sizeY = Math.abs(y2 - y1) + 1;
        int sizeZ = Math.abs(z2 - z1) + 1;
        int cuboidSize = sizeX * sizeY * sizeZ;

        Map<SectionPos, ChunkSectionData> sectionDataMap = new HashMap<>();

        int x = 0, y = 0, z = 0;
        Location location = new Location(world, x1, y1, z1);

        double totalHeight = y2 - y1;
        if (totalHeight <= 0) totalHeight = 1;

        for (int i = 0; i < cuboidSize; i++) {
            if (!serverLevel.getChunkSource().isChunkLoaded(location.getBlockX() >> 4, location.getBlockZ() >> 4)) {
                Chunk c = location.getChunk();
                c.load();
            }

            BlockPos.MutableBlockPos pos = getBlockPos(location);
            SectionPos sectionPos = SectionPos.of(pos);

            ChunkSectionData sectionData = sectionDataMap.computeIfAbsent(
                    sectionPos,
                    ChunkSectionData::new
            );

            double currentRelY = (location.getY() - y1) / totalHeight;
            Material material = Material.AIR;

            BlockState blockState = materialToBlockState(material);
            sectionData.addBlock(pos, blockState, serverLevel);

            if (++x >= sizeX) {
                x = 0;
                if (++y >= sizeY) {
                    y = 0;
                    ++z;
                }
            }

            location.setX(x1 + x);
            location.setY(y1 + y);
            location.setZ(z1 + z);
        }

        for (ChunkSectionData sectionData : sectionDataMap.values()) {
            if (sectionData.blockStates.isEmpty()) {
                continue;
            }

            try {
                serverPlayer.connection.send(
                        new ClientboundSectionBlocksUpdatePacket(
                                sectionData.sectionPos,
                                sectionData.blockStates.keySet(),
                                sectionData.blockStates.values().toArray(BlockState[]::new)
                        )
                );
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to send packet for section " +
                        sectionData.sectionPos + ": " + e.getMessage());
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        }
    }

    //TODO Change this to set the blocks destroy stage, also FIX SOUND
    public void sendDestroyPacket(Player p, Location loc) {
        ServerPlayer serverPlayer = ((CraftPlayer) p).getHandle();
        World world = loc.getWorld();
        ServerLevel serverLevel = ((CraftWorld) world).getHandle();
        BlockPos pos = new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        try {
            serverPlayer.connection.send(
                    new ClientboundBlockUpdatePacket(
                            pos,
                            materialToBlockState(Material.AIR)
                    )
            );
        } catch (Exception e) {
            plugin.getLogger().warning("Failed to send packet for position " +
                    pos + ": " + e.getMessage());
            if (DEBUG) {
                e.printStackTrace();
            }
        }
    }

    public static List<BlockChance> createDefaultBlockChances() {
        List<BlockChance> chances = new ArrayList<>();

        // Cobblestone: Always present, gradually decreasing
        chances.add(new BlockChance(
                new ItemStack(Material.COPPER_BLOCK),
                95, 0, 0,     // Start very high, end at 0%
                1,             // Peak level (unused)
                1.0, 1.0,      // Allowed anywhere
                false,         // Linear distribution
                1             // Available from level 1
        ));

        // Coal Ore: Early game resource
        chances.add(new BlockChance(
                new ItemStack(Material.COAL_ORE),
                5, 40, 5,      // Start at 5%, peak at 40%, end at 5%
                15,            // Peaks at level 15
                0.7, 1.0,      // Depth restrictions
                true,          // Parabolic distribution
                1             // Available from level 1
        ));

        // Iron Ore: Mid-early game resource
        chances.add(new BlockChance(
                new ItemStack(Material.IRON_ORE),
                0, 35, 10,     // Start at 0%, peak at 35%, end at 10%
                25,            // Peaks at level 25
                0.6, 0.9,      // Depth restrictions
                true,          // Parabolic distribution
                10            // Only appears from level 10
        ));

        // Gold Ore: Mid game resource
        chances.add(new BlockChance(
                new ItemStack(Material.GOLD_ORE),
                0, 30, 15,     // Start at 0%, peak at 30%, end at 15%
                40,            // Peaks at level 40
                0.4, 0.8,      // Depth restrictions
                true,          // Parabolic distribution
                20            // Only appears from level 20
        ));

        // Diamond Ore: Late mid-game resource
        chances.add(new BlockChance(
                new ItemStack(Material.DIAMOND_ORE),
                0, 25, 20,     // Start at 0%, peak at 25%, end at 20%
                60,            // Peaks at level 60
                0.0, 0.6,      // Depth restrictions
                true,          // Parabolic distribution
                30            // Only appears from level 30
        ));

        // Emerald Ore: Linear increase with level
        chances.add(new BlockChance(
                new ItemStack(Material.EMERALD_ORE),
                0, 0, 15,      // Linear increase to 15%
                100,           // Peak level (unused for linear)
                0.2, 0.4,      // Very restricted depth
                false,         // Linear distribution
                40            // Only appears from level 40
        ));

        return chances;
    }
}
