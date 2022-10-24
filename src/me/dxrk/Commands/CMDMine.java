package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;

import me.dxrk.Events.MineHandler;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Main.Main;
import me.dxrk.Events.ResetHandler;
import me.dxrk.Events.ResetHandler.ResetReason;
import me.dxrk.Tokens.Tokens;
import me.jet315.prisonmines.mine.Mine;

public class CMDMine implements CommandExecutor, Listener {

	SettingsManager settings = SettingsManager.getInstance();
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  
	public Inventory mineMenu = Bukkit.createInventory(null, 27, c("&a&lYour Mine!"));
	
	
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("mine")) {
    	if(args.length == 0) {
      		if (!(sender instanceof Player))
        		return false;
      		Player p = (Player)sender;

	  		ItemStack teleport = new ItemStack(Material.GRASS);
	  		ItemMeta tm = teleport.getItemMeta();
	  		tm.setDisplayName(c("&cTeleport to your mine!"));
	  		teleport.setItemMeta(tm);

	  		mineMenu.setItem(13, teleport);

	  		p.openInventory(mineMenu);

    	}
		if(args.length == 1) {
			Player p = (Player)sender;
			if(args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
				if(Bukkit.getWorlds().contains(Bukkit.getWorld(p.getName() + "sWorld"))) {
					Location pworld = new Location(Bukkit.getWorld(p.getName() + "sWorld"), -2.5, 66, 0.5);
					p.teleport(pworld);
				} else {
					p.sendMessage(c("&f&lMine &8| &7Unable to find your mine(/mine)."));
				}
			}
		}
      
    }
    return true;
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
	  Player p = (Player) e.getWhoClicked();
	  
	  if (e.getClickedInventory() == null)
	      return; 
	    if (e.getClickedInventory().getName() == null)
	      return; 
	    
	    if(e.getClickedInventory().getName().equals(c("&a&lYour Mine!"))) {
			if(e.getSlot() == 13) {
				if(settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".hasMine") == false) {
					MineHandler.getInstance().CreateMine(p);
				} else {
					Location pworld = new Location(Bukkit.getWorld(p.getName()+"sWorld"), -3, 65, 0);
					p.teleport(pworld);
				}
			}
		}
	    
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}


