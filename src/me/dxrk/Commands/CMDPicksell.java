package me.dxrk.Commands;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dxrk.Events.SellHandler;
import me.dxrk.Main.SettingsManager;

public class CMDPicksell implements CommandExecutor {
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("picksell") && 
      sender instanceof Player) {
      Player p = (Player)sender;
      if (args.length == 0) {
        if (SellHandler.picksell.contains(p)) {
          p.sendMessage(ChatColor.GRAY + "PickSell Enabled!");
          SellHandler.picksell.remove(p);
          return true;
        } 
        p.sendMessage(ChatColor.GRAY + "PickSell Disabled!");
        SellHandler.picksell.add(p);
      } else if (args.length == 1 && args[0].equalsIgnoreCase("stfu")) {
        if (SettingsManager.getInstance().getPlayerData().get(String.valueOf(p.getUniqueId().toString()) + ".PicksellSTFU") == null) {
          p.sendMessage(ChatColor.GRAY + "PickSell Chat Enabled!");
          SettingsManager.getInstance().getPlayerData().set(String.valueOf(p.getUniqueId().toString()) + ".PicksellSTFU", Boolean.valueOf(false));
          SettingsManager.getInstance().savePlayerData();
          return false;
        } 
        if (!SettingsManager.getInstance().getPlayerData().getBoolean(String.valueOf(p.getUniqueId().toString()) + ".PicksellSTFU")) {
          p.sendMessage(ChatColor.GRAY + "PickSell Chat Disabled!");
          SettingsManager.getInstance().getPlayerData().set(String.valueOf(p.getUniqueId().toString()) + ".PicksellSTFU", Boolean.valueOf(true));
          SettingsManager.getInstance().savePlayerData();
          return false;
        } 
        if (SettingsManager.getInstance().getPlayerData().getBoolean(String.valueOf(p.getUniqueId().toString()) + ".PicksellSTFU")) {
          p.sendMessage(ChatColor.GRAY + "PickSell Chat Enabled!");
          SettingsManager.getInstance().getPlayerData().set(String.valueOf(p.getUniqueId().toString()) + ".PicksellSTFU", Boolean.valueOf(false));
          SettingsManager.getInstance().savePlayerData();
          return false;
        } 
      } 
    } 
    return false;
  }
}
