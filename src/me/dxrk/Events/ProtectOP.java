package me.dxrk.Events;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class ProtectOP implements Listener {
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    Player p = event.getPlayer();
    if (p.isOp() || p.hasPermission("*")) {
    	if (p.getName().equalsIgnoreCase("Dxrk") || p.getName().equalsIgnoreCase("Kevin20230") || p.getName().equalsIgnoreCase("32j") || p.getName().equalsIgnoreCase("BakonStrip") || p.getName().equalsIgnoreCase("CyaClouds") ||p.getName().equalsIgnoreCase("CensoredAnime")||p.getName().equalsIgnoreCase("PeachBao"))
            return; 
      event.setCancelled(true);
      p.sendMessage(ChatColor.RED + "YOU ARE NOT A REGISTERED OP!");
    } 
  }
  
  @EventHandler
  public void onbreak(BlockBreakEvent event) {
    Player p = event.getPlayer();
    if (p.isOp() || p.hasPermission("*")) {
    	if (p.getName().equalsIgnoreCase("Dxrk") || p.getName().equalsIgnoreCase("Kevin20230") || p.getName().equalsIgnoreCase("32j") || p.getName().equalsIgnoreCase("BakonStrip") || p.getName().equalsIgnoreCase("CyaClouds") ||p.getName().equalsIgnoreCase("CensoredAnime")||p.getName().equalsIgnoreCase("PeachBao"))
            return; 
      event.setCancelled(true);
      p.sendMessage(ChatColor.RED + "YOU ARE NOT A REGISTERED OP!");
    } 
  }
  
  @EventHandler
  public void onbreak(PlayerMoveEvent event) {
    Player p = event.getPlayer();
    if (p.isOp() || p.hasPermission("*")) {
    	if (p.getName().equalsIgnoreCase("Dxrk") || p.getName().equalsIgnoreCase("Kevin20230") || p.getName().equalsIgnoreCase("32j") || p.getName().equalsIgnoreCase("BakonStrip") || p.getName().equalsIgnoreCase("CyaClouds") ||p.getName().equalsIgnoreCase("CensoredAnime")||p.getName().equalsIgnoreCase("PeachBao"))
        return; 
      event.setTo(event.getFrom());
      p.sendMessage(ChatColor.RED + "YOU ARE NOT A REGISTERED OP!");
    } 
  }
}
