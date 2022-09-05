package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CMDSay implements CommandExecutor {
	
	public static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	
	
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    String prefix = c("&9&lGenesis&8» ");
    if (cmd.getName().equalsIgnoreCase("Say")) {
      if (sender.hasPermission("Epsilon.Say")) {
        if (args.length == 0) {
          sender.sendMessage(String.valueOf(prefix) + ChatColor.RED + "Please enter a message!");
        } else {
          String message = "";
          for (int i = 0; i < args.length; i++)
            message = String.valueOf(message) + args[i] + " "; 
          String Message1 = ChatColor.translateAlternateColorCodes('&', message);
          Bukkit.broadcastMessage(String.valueOf(prefix) + Message1);
        } 
        return true;
      } 
      sender.sendMessage(prefix+ "You do not have permission!");
    } 
    return false;
  }
}
