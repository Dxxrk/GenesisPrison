package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CMDTrash implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("trash")) {
            if (!(sender instanceof Player))
                return false;
            Player p = (Player) sender;
            p.openInventory(Bukkit.createInventory(null, 54, ChatColor.translateAlternateColorCodes('&', "&cTrash")));
        }
        return true;
    }
}
