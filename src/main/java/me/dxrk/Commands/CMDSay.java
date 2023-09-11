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
        String prefix = c("&c&lGenesis&b» &f&l");
        if (cmd.getName().equalsIgnoreCase("Say")) {
            if (sender.hasPermission("Epsilon.Say")) {
                if (args.length == 0) {
                    sender.sendMessage(prefix + ChatColor.RED + "Please enter a message!");
                } else {
                    StringBuilder message = new StringBuilder();
                    for (String arg : args) message.append(arg).append(" ");
                    String Message1 = ChatColor.translateAlternateColorCodes('&', message.toString());
                    Bukkit.broadcastMessage(prefix + Message1);
                }
                return true;
            }
            sender.sendMessage(prefix + "You do not have permission!");
        }
        return false;
    }
}
