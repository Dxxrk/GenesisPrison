package me.dxrk.Commands;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CMDAc implements CommandExecutor, Listener {
  public static CMDAc instance = new CMDAc();
  
  public static CMDAc getInstnace() {
    return instance;
  }
  
  public static ArrayList<Player> ac = new ArrayList<>();
  
  String c(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("ac")) {
      if (!(sender instanceof Player))
        return false; 
      if (!sender.hasPermission("staffchat.use"))
        return false; 
      Player p = (Player)sender;
      if (args.length == 0) {
        if (ac.contains(p)) {
          ac.remove(p);
          p.sendMessage(c("&bStaff chat disabled!"));
        } else {
          ac.add(p);
          p.sendMessage(c("&bStaff chat enabled!"));
        } 
        return false;
      } 
      StringBuilder sb = new StringBuilder();
      for (int i = 0; i < args.length; i++)
        sb.append(args[i]).append(" "); 
      String allArgs = sb.toString().trim();
      for (Player pp : Bukkit.getOnlinePlayers()) {
        if (pp.hasPermission("staffchat.use")) {
          pp.sendMessage(c("&c&l&4&lStaffchat&c&l&r &e" + p.getName() + " &7  &c" + allArgs)); 
        }
      } 
    } 
    return false;
  }
  
  
}
