package me.dxrk.Main;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProtectOP implements Listener {

  private List<String> staff = Arrays.asList("Dxrk", "32j", "BakonStrip", "Pikashoo", "_Lone_Ninja_");

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
    Player p = event.getPlayer();
    if (p.isOp() || p.hasPermission("*")) {
      if(staff.contains(p.getName()))
        return;
      event.setCancelled(true);
      p.sendMessage(ChatColor.RED + "YOU ARE NOT A REGISTERED STAFF!");
    } 
  }
  
  @EventHandler
  public void onbreak(BlockBreakEvent event) {
    Player p = event.getPlayer();
    if (p.isOp() || p.hasPermission("*")) {
      if(staff.contains(p.getName()))
        return;
      event.setCancelled(true);
      p.sendMessage(ChatColor.RED + "YOU ARE NOT A REGISTERED STAFF!");
    } 
  }
  
  @EventHandler
  public void onbreak(PlayerMoveEvent event) {
    Player p = event.getPlayer();
    if (p.isOp() || p.hasPermission("*")) {
      if(staff.contains(p.getName()))
        return;
      event.setTo(event.getFrom());
      p.sendMessage(ChatColor.RED + "YOU ARE NOT A REGISTERED STAFF!");
    } 
  }
}
