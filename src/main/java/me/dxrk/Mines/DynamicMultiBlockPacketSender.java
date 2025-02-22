package me.dxrk.Mines;

import it.unimi.dsi.fastutil.shorts.Short2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.shorts.Short2ObjectMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundSectionBlocksUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicMultiBlockPacketSender {
    private final Plugin plugin;
    private static final boolean DEBUG = true;

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

    /**
     * Holds block data for a specific chunk section
     */
    private static class ChunkSectionData {
        final SectionPos sectionPos;
        final Short2ObjectMap<BlockState> blockStates;
        final List<Packet<ClientGamePacketListener>> blockEntityPackets;

        ChunkSectionData(SectionPos sectionPos) {
            this.sectionPos = sectionPos;
            this.blockStates = new Short2ObjectLinkedOpenHashMap<>();
            this.blockEntityPackets = new ArrayList<>();
        }

        void addBlock(BlockPos pos, BlockState state, ServerLevel level) {
            short relativePos = (short) SectionPos.sectionRelativePos(pos);
            blockStates.put(relativePos, state);

            // Handle block entities
            if (state.hasBlockEntity()) {
                BlockEntity blockEntity = level.getBlockEntity(pos);
                if (blockEntity != null) {
                    Packet<ClientGamePacketListener> packet = blockEntity.getUpdatePacket();
                    if (packet != null) {
                        blockEntityPackets.add(packet);
                    }
                }
            }
        }
    }

    /**
     * Calculates the weight for a block type at a given level
     */
    private static double calculateWeight(BlockChance block, int level) {
        // If below minimum level, return 0 weight
        if (level < block.minimumLevel()) {
            return 0;
        }

        if (!block.isParabolic) {
            // Linear interpolation for non-parabolic blocks
            double t = (level - block.minimumLevel()) /
                    (double) (100 - block.minimumLevel());
            return block.startWeight() + (block.endWeight() - block.startWeight()) * t;
        }

        // Normalize the level to a 0-1 range between minimum level and peak
        double normalizedLevel;
        if (level <= block.peakLevel()) {
            normalizedLevel = (level - block.minimumLevel()) /
                    (block.peakLevel() - block.minimumLevel());
        } else {
            // After peak, normalize between peak and max level
            normalizedLevel = 1 + (level - block.peakLevel()) /
                    (100 - block.peakLevel());
        }

        // Quadratic function that starts at minimumLevel
        if (normalizedLevel < 0) {
            return 0;
        }

        if (level <= block.peakLevel()) {
            // Rising phase: quadratic increase to peak
            double t = normalizedLevel;
            return block.startWeight() + (block.peakWeight() - block.startWeight()) * (t * t);
        } else {
            // Falling phase: linear decrease from peak to end
            double t = (level - block.peakLevel()) / (100 - block.peakLevel());
            return block.peakWeight() + (block.endWeight() - block.peakWeight()) * t;
        }
    }

    /**
     * Converts a Material to BlockState
     */
    private BlockState materialToBlockState(Material material) {
        net.minecraft.world.level.block.Block nmsBlock =
                org.bukkit.craftbukkit.util.CraftMagicNumbers.getBlock(material);
        return nmsBlock.defaultBlockState();
    }

    /**
     * Selects a random block based on level and depth
     */
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

    /**
     * Debug logs a message if debug mode is enabled
     */
    private void debugLog(String message) {
        if (DEBUG) {
            plugin.getLogger().info("[Debug] " + message);
        }
    }

    private BlockPos.MutableBlockPos getBlockPos(Location loc) {
        return new BlockPos.MutableBlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
    }

    /**
     * Sends multi-block change packets for an area using dynamic block distribution
     */
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

        debugLog("Starting packet send for player: " + player.getName());

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
        int sizeX = Math.abs(x2 - x1) + 1;
        int sizeY = Math.abs(y2 - y1) + 1;
        int sizeZ = Math.abs(z2 - z1) + 1;
        int cuboidSize = sizeX * sizeY * sizeZ;

        Map<SectionPos, ChunkSectionData> sectionDataMap = new HashMap<>();

        int x = 0, y = 0, z = 0;
        Location location = new Location(world, x1, y1, z1);

        double totalHeight = y2 - y1;
        if (totalHeight <= 0) totalHeight = 1;

        // Generate block data for each position
        Map<Location, Material> locs = new HashMap<>();
        for (int i = 0; i < cuboidSize; i++) {
            // Skip if chunk isn't loaded
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
            locs.put(location.clone(), material);
            location.setX(x1 + x);
            location.setY(y1 + y);
            location.setZ(z1 + z);
        }

        debugLog("Generated " + sectionDataMap.size() + " chunk sections");

        // Send packets for each chunk section
        for (ChunkSectionData sectionData : sectionDataMap.values()) {
            if (sectionData.blockStates.isEmpty()) {
                continue;
            }

            try {// Send multi-block update packet
                serverPlayer.connection.send(
                        new ClientboundSectionBlocksUpdatePacket(
                                sectionData.sectionPos,
                                sectionData.blockStates.keySet(),
                                sectionData.blockStates.values().toArray(BlockState[]::new)
                        )
                );


                // Send any block entity packets
                for (Packet<ClientGamePacketListener> packet : sectionData.blockEntityPackets) {
                    serverPlayer.connection.send(packet);
                }
            } catch (Exception e) {
                plugin.getLogger().warning("Failed to send packet for section " +
                        sectionData.sectionPos + ": " + e.getMessage());
                if (DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        PacketInterceptor.blockLocs.put(player, locs);
        debugLog("Finished sending all packets");
    }

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

    /**
     * Creates the default block chances configuration
     */
    public static List<BlockChance> createDefaultBlockChances() {
        List<BlockChance> chances = new ArrayList<>();

        // Cobblestone: Always present, gradually decreasing
        chances.add(new BlockChance(
                new ItemStack(Material.COBBLESTONE),
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
