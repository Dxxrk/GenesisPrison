package me.dxrk.Mines;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.dxrk.Main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.*;

public class PacketInterceptor {
    private final Plugin plugin;
    private final ProtocolManager protocolManager;
    public static Map<Player, Map<Location, Material>> blockLocs = new HashMap<>();

    public PacketInterceptor(Plugin plugin) {
        this.plugin = plugin;
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        setupPacketListeners();
    }

    private void setupPacketListeners() {
        // Listen for block breaking attempts
        protocolManager.addPacketListener(new PacketAdapter(plugin, PacketType.Play.Client.BLOCK_DIG) {
            @Override
            public void onPacketReceiving(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                Player p = event.getPlayer();
                BlockPosition pos = packet.getBlockPositionModifier().read(0);
                Location loc = pos.toLocation(p.getWorld());

                // Check if this location contains a fake block
                if (blockLocs.get(p).containsKey(loc)) {
                    if(p.getEquipment().getItemInMainHand().getType() == Material.WOODEN_PICKAXE) {
                        Material mat = blockLocs.get(p).get(loc);
                        Main.packetSender.sendDestroyPacket(p, loc);
                        Map<Location, Material> blocks = blockLocs.get(p);
                        blocks.remove(loc);
                        blockLocs.put(p, blocks);
                    }
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
                Player player = event.getPlayer();

                try {
                    Location targetLoc = null;
                    if (event.getPacketType() == PacketType.Play.Client.USE_ITEM_ON) {
                            BlockPosition pos = event.getPacket().getMovingBlockPositions().read(0).getBlockPosition();
                            targetLoc = new Location(player.getWorld(), pos.getX(), pos.getY(), pos.getZ());

                    }

                    if (blockLocs.get(player).containsKey(targetLoc)) {
                        event.setCancelled(true);
                    }
                } catch (Exception e) {
                    plugin.getLogger().warning("Error in right-click handler: " + e.getMessage());
                }
            }
        });
    }


    public void cleanup() {
        blockLocs.clear();
    }

}
