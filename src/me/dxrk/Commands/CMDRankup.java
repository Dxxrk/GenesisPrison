package me.dxrk.Commands;


import net.md_5.bungee.api.ChatColor;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.dxrk.Events.RankupHandler;
import me.dxrk.Events.SellHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;



public class CMDRankup implements CommandExecutor {
  Methods methods = Methods.getInstance();
  
  SettingsManager settings = SettingsManager.getInstance();
  
  RankupHandler rankup = RankupHandler.getInstance();
  
  SellHandler sell = SellHandler.getInstance();
  
  public static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  
  public boolean isInt(String s) {
	    try {
	      int i = Integer.parseInt(s);
	      return true;
	    } catch (Exception e1) {
	      return false;
	    } 
	  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Player p = (Player)sender;
    if (label.equalsIgnoreCase("rankup")) {
          this.rankup.rankup(p);
          this.settings.saveRankupPrices();
      }
    
    if (label.equalsIgnoreCase("maxrankup") || label.equalsIgnoreCase("rankupmax")) {
      if (p == null)
        return false; 
      this.rankup.MaxRankup(p);
      this.settings.saveRankupPrices();
    } 
    if(label.equalsIgnoreCase("setrank")) {
    	if(p.hasPermission("admin.setrank")) {
    	if(args.length == 2) {
    	Player reciever = Bukkit.getServer().getPlayer(args[0]);
    	if(isInt(args[1])) {
    		this.rankup.setRank(reciever, Integer.parseInt(args[1]));
    	} else {
    		p.sendMessage(c("&cThat is not a number!"));
    	}
    	}
    }
    	else {
        	p.sendMessage(c("&cNo Permission"));
        }
    }
    
    return false;
  }
}
