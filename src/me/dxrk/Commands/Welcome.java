package me.dxrk.Commands;

import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.dxrk.Main.Main;

public class Welcome implements Listener, CommandExecutor {
  public static HashMap<Player, Integer> welcomes = new HashMap<Player, Integer>();
  
  public void onJoin(PlayerJoinEvent e) {
    final Player p = e.getPlayer();
    if (!p.hasPlayedBefore()) {
      welcomes.put(p, Integer.valueOf(1));
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
              Welcome.welcomes.remove(p);
            }
          },  3600L);
    } 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("Welcome")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      if (args.length != 1) {
        p.sendMessage("/Welcome <Player>");
        return false;
      } 
      Player welcomed = Bukkit.getPlayer(args[0]);
      if (welcomed == null) {
        p.sendMessage("Player not found");
        return false;
      } 
      if (welcomes.containsKey(p)) {
        if (((Integer)welcomes.get(p)).intValue() == 3) {
          welcomes.remove(p);
        } else {
          welcomes.put(p, Integer.valueOf(((Integer)welcomes.get(p)).intValue() + 1));
        } 
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "loyalty add " + p.getName() + " 1");
        p.sendMessage(ChatColor.GRAY + "You have been given a loyalty point!");
      } 
      p.chat("Welcome " + welcomed.getName() + "!");
    } 
    return true;
  }
}
