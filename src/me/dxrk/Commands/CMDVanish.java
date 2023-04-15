package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.mojang.authlib.GameProfile;
import me.dxrk.Main.Main;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_8_R3.PlayerInteractManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import org.bukkit.event.player.PlayerQuitEvent;

public class CMDVanish implements Listener, CommandExecutor{
	
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
	            vanished.remove(p.getUniqueId());
	            pp.showPlayer(p);
	            ActionBarAPI.sendActionBar(p, c("&cYou are no longer Vanished"), 40);
	          } 
	        } else {
	          for (Player pp : Bukkit.getOnlinePlayers()) {
				if(!p.hasPermission("staffchat.use"))
	            	pp.hidePlayer(p);
	            vanished.add(p.getUniqueId());
	            ActionBarAPI.sendActionBar(p, c("&cYou Are Now Vanished"), 40);
	          } 
	        }  
	    } 
	    return false;
	  }

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (p.hasPermission("rank.helper")) {
			if(vanished.contains(p.getUniqueId())) {
				e.setQuitMessage("");
			} else {
				e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b-&7> &e&l" + p.getName()));
			}
		} else {
			e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b-&7> &b" + p.getName()));
		}
	}
	  
	  @EventHandler(priority = EventPriority.HIGHEST)
	  public void onJoin(PlayerJoinEvent e) {
		  Player p = e.getPlayer();
		  if (p.hasPermission("rank.helper")) {
			  e.setJoinMessage("");
			  vanished.add(p.getUniqueId());
		  } else {
			  e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b+&7> &b" + p.getName()));
		  }
		  if(vanished.contains(p.getUniqueId())) {
			  for(Player pp : Bukkit.getOnlinePlayers()) {
				  pp.hidePlayer(p);
			  }
				  
		  }
		  for (UUID uuid : vanished) {
			  Player van = Bukkit.getPlayer(uuid);
			  if(!p.hasPermission("staffchat.use") && van !=null)
			  	p.hidePlayer(van);
		  }
	  }
	
	

}
