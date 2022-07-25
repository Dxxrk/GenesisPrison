package me.dxrk.Commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Updates implements CommandExecutor {
  public String color(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("updates")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      p.sendMessage(color("&b08&7/&b12&7/&b20 &7| &bNew Spawn Added"));
    } 
    return true;
  }
}
