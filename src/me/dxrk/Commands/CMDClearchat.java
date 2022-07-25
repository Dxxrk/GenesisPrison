package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDClearchat implements CommandExecutor {
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("clearchat")) {
      for (int i = 0; i < 100; i++)
        Bukkit.broadcastMessage(" "); 
      Bukkit.broadcastMessage(c("&c&k;&bExistor&c&k;&r &7Chat has been cleared."));
    } 
    return false;
  }
}
