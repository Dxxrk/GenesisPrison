package me.dxrk.Mines;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import com.comphenix.protocol.wrappers.WrappedAttribute;
import com.comphenix.protocol.wrappers.WrappedLevelChunkData;
import com.destroystokyo.paper.antixray.ChunkPacketInfo;
import me.dxrk.Enchants.EnchantsNEW;
import me.dxrk.Enchants.Tool;
import me.dxrk.Enchants.ToolHandler;
import me.dxrk.Events.PickaxeEvents;
import me.dxrk.Main.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.network.protocol.game.ClientboundLevelChunkPacketData;
import net.minecraft.network.protocol.game.ClientboundLevelChunkWithLightPacket;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.craftbukkit.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.Map;

public class PacketInterceptor {
    private final Plugin plugin;
    private final ProtocolManager protocolManager;

    public PacketInterceptor(Plugin plugin) {
        this.plugin = plugin;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        setupPacketListeners();
    }

    private BlockState materialToBlockState(Material material) {
        Block nmsBlock =
                CraftMagicNumbers.getBlock(material);
        return nmsBlock.defaultBlockState();
    }

    private int materialToBlockStateId(Material mat) {
        Block nmsBlock =
                CraftMagicNumbers.getBlock(mat);

        return Block.getId(nmsBlock.defaultBlockState());
    }


    public void breakPacket(Player p, Location loc) {
        BlockPos pos = new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        ((CraftPlayer) p).getHandle().connection.send(
                new ClientboundBlockUpdatePacket(
                        pos,
                        materialToBlockState(Material.AIR)
                )
        );
    }

    private void setupPacketListeners() {
        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.MAP_CHUNK) {
            @Override
            public void onPacketSending(PacketEvent e) {
                int chunkX = e.getPacket().getIntegers().read(0);
                int chunkZ = e.getPacket().getIntegers().read(1);

                PacketContainer packet = new PacketContainer(PacketType.Play.Server.MAP_CHUNK);
                if(!e.getPlayer().getWorld().equals(Bukkit.getWorld("MineWorld"))) return;

                //Chunk X Z
                packet.getIntegers().write(0, chunkX);
                packet.getIntegers().write(1, chunkZ);
                Mine m = MineSystem.getInstance().getMineByPlayer(e.getPlayer());
                Map<ChunkPos, LevelChunk> chunks = Main.packetSender.getModifiedChunks(m.getMinPoint(), m.getMaxPoint(), e.getPlayer(), 1, DynamicMultiBlockPacketSender.createDefaultBlockChances());
                LevelChunk levelChunk = null;
                ChunkPos c = new ChunkPos(chunkX, chunkZ);
                for(ChunkPos cpos : chunks.keySet()) {
                    if(cpos.x == c.x && cpos.z == c.z) {
                        levelChunk = chunks.get(cpos);
                    }
                }
                if(levelChunk == null) {
                    return;
                }

                ClientboundLevelChunkWithLightPacket chunkWithLightPacket = new ClientboundLevelChunkWithLightPacket(levelChunk, levelChunk.getLevel().getLightEngine(), null, null, false);

                ChunkPacketInfo<BlockState> chunkPacketInfo = new ChunkPacketInfo<>(chunkWithLightPacket, levelChunk);
                ClientboundLevelChunkPacketData chunkPacket = new ClientboundLevelChunkPacketData(levelChunk, chunkPacketInfo);

                WrappedLevelChunkData.ChunkData chunkData = new WrappedLevelChunkData.ChunkData(chunkPacket);

                packet.getLevelChunkData().write(0, chunkData);
                packet.getLightUpdateData().write(0, new WrappedLevelChunkData.LightData(chunkWithLightPacket.getLightData()));

                e.setPacket(packet);
            }

        });

        // Listen for block breaking attempts
        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Server.UPDATE_ATTRIBUTES) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                List<WrappedAttribute> meow = packet.getAttributeCollectionModifier().read(0);
                for (WrappedAttribute wa : meow) {
                    if (wa.getAttributeKey().contains("break")) {
                        event.setCancelled(true);
                    }
                }
            }
        });
        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player p = event.getPlayer();
                    /*BlockPosition pos = packet.getBlockPositionModifier().read(0);
                    Location loc = pos.toLocation(p.getWorld());



                    // Check if this location contains a fake block
                    if (loc != null && blockLocs.get(p).containsKey(loc)) {
                        if (p.getEquipment().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE) {
                            event.setCancelled(true);

                            PacketContainer pac = protocolManager.createPacket(PacketType.Play.Server.BLOCK_CHANGED_ACK);
                            if(!sequenceId.containsKey(p)) {
                                sequenceId.put(p, 0);
                            }
                            pac.getIntegers().write(0, sequenceId.get(p));
                            sequenceId.compute(p, (k, id) -> id + 1);
                            Material mat = blockLocs.get(p).get(loc);
                            //Main.packetSender.sendDestroyPacket(p, loc);
                            PacketContainer mmm = protocolManager.createPacket(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
                            int locationId = loc.getBlockX() + loc.getBlockY() + loc.getBlockZ();
                            mmm.getIntegers().write(0, locationId);
                            mmm.getBlockPositionModifier().write(0, pos);
                            mmm.getIntegers().write(1, 20);
                            protocolManager.sendServerPacket(p, mmm);
                            breakPacket(p, loc);
                            //protocolManager.sendServerPacket(p, pac);

                            Map<Location, Material> blocks = blockLocs.get(p);
                            blocks.remove(loc);
                            blockLocs.put(p, blocks);
                            Mine m = MineSystem.getInstance().getMineByPlayer(p);
                            if(m.getBlocksLeftPercentage() < m.getResetPercent()) {
                                m.reset();
                            }
                            //do enchant

                        }
                    }*/
                if(p.getEquipment().getItemInMainHand().getType() == Material.AIR) return;
                BlockPosition pos = packet.getBlockPositionModifier().read(0);
                Location loc = pos.toLocation(p.getWorld());
                Tool tool = ToolHandler.getInstance().toolFromItemStack(p.getEquipment().getItemInMainHand());
                if(!MineHandler.getInstance().hasMine(p)) return;
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                if (loc != null && m.isLocationInMine(loc)) {
                    event.setCancelled(true);
                    m.mineBlock();
                    PickaxeEvents.getInstance().addXP(tool, p);
                    breakPacket(p, loc);
                    if (m.getBlocksLeftPercentage() > m.getResetPercent()) {
                        m.reset();
                    }
                    EnchantsNEW.getInstance().keyFinder(p, p.getEquipment().getItemInMainHand());
                    EnchantsNEW.getInstance().Jackhammer(p, m, loc, p.getEquipment().getItemInMainHand());
                }

            }
        });

        // Listen for block interactions (right clicks)
        protocolManager.addPacketListener(new PacketAdapter(plugin,
                PacketType.Play.Client.USE_ITEM,
                PacketType.Play.Client.USE_ITEM_ON) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                if (event.isCancelled()) return;
                PacketContainer packet = event.getPacket();
                Player p = event.getPlayer();

                try {
                    if (event.getPacketType() == PacketType.Play.Client.USE_ITEM_ON) {
                        if(MineHandler.getInstance().hasMine(p)) {
                            Mine m = MineSystem.getInstance().getMineByPlayer(p);
                            BlockPosition pos = event.getPacket().getMovingBlockPositions().read(0).getBlockPosition();
                            Location loc = pos.toLocation(p.getWorld());
                            if (loc != null && m.isLocationInMine(loc)) {
                                event.setCancelled(true);
                            }
                        }

                    }
                } catch (Exception e) {
                    plugin.getLogger().warning("Error in right-click handler: " + e.getMessage());
                }
            }
        });
    }


}
