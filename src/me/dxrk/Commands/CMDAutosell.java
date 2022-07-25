package me.dxrk.Commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dxrk.Events.SellHandler;
import me.dxrk.Main.SettingsManager;

public class CMDAutosell implements CommandExecutor {
	
	SettingsManager settings = SettingsManager.getInstance();
	
	
	
	
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("autosell") && 
      sender instanceof Player) {
      Player p = (Player)sender;
      if (p.hasPermission("command.autosell")) {
        if (SellHandler.autosell.contains(p)) {
          SellHandler.autosell.remove(p);
          p.sendMessage(ChatColor.RED +""+ ChatColor.BOLD + "Auto-Sell Turned Off");
          settings.getOptions().set(p.getUniqueId().toString()+".Autosell", false);
          return true;
        } 
        SellHandler.autosell.add(p);
        p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Auto-Sell Turned On");
        settings.getOptions().set(p.getUniqueId().toString()+".Autosell", true);
        return true;
      } 
      p.sendMessage(ChatColor.RED + "This perk is only for Ares and higher.");
      return false;
    } 
    return false;
  }
}
