package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.connorlinfoot.actionbarapi.ActionBarAPI;

public class Vanish implements Listener, CommandExecutor{
	
	public static List<UUID> vanished = new ArrayList<>();
	  
	  public static boolean isVanished(Player p) {
	    if (vanished.contains(p.getUniqueId()))
	      return true; 
	    return false;
	  }
	  
	  String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	  
	  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	    if (label.equalsIgnoreCase("vanish") || label.equalsIgnoreCase("v") || label.equalsIgnoreCase("ev") || label.equalsIgnoreCase("evanish")) {
	      if (!(sender instanceof Player))
	        return false; 
	      Player p = (Player)sender;
	      if (p.hasPermission("vanish.use"))
	        if (isVanished(p)) {
	          for (Player pp : Bukkit.getOnlinePlayers()) {
	            if (args.length > 0)
	              if (pp.hasPermission("vanish.see")) {
	                pp.sendMessage(c("&7<&b+&7> &e&l" + p.getName() + " &a(Vanish)"));
	              } else {
	                pp.sendMessage(c("&7<&b+&7> &e&l" + p.getName()));
	              }  
	            vanished.remove(p.getUniqueId());
	            pp.showPlayer(p);
	            ActionBarAPI.sendActionBar(p, c("&cYou are no longer Vanished"), 200);
	          } 
	        } else {
	          for (Player pp : Bukkit.getOnlinePlayers()) {
	            if (args.length > 0)
	              if (pp.hasPermission("vanish.see")) {
	                pp.sendMessage(c("&7<&b-&7> &e&l" + p.getName() + " &a(Vanish)"));
	              } else {
	                pp.sendMessage(c("&7<&b-&7> &e&l" + p.getName()));
	              }  
	            pp.hidePlayer(p);
	            vanished.add(p.getUniqueId());
	            ActionBarAPI.sendActionBar(p, c("&cYou Are Now Vanished"), 200);
	          } 
	        }  
	    } 
	    return false;
	  }
	  
	  @EventHandler
	  public void onJoin(PlayerJoinEvent e) {
		  if(vanished.contains(e.getPlayer().getUniqueId())) {
			  for(Player p : Bukkit.getOnlinePlayers()) {
				  p.hidePlayer(e.getPlayer());
			  }
				  
		  }
		  for(Player p : Bukkit.getOnlinePlayers()) {
			  for(int i = 0; i < vanished.size(); i++) {
				  Player pp = Bukkit.getPlayer(vanished.get(i));
				  p.hidePlayer(pp);
				  
			  }
		  }
		  
		  
		  
	  }
	
	

}
