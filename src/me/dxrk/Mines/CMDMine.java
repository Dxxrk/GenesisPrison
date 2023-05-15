package me.dxrk.Mines;

import me.dxrk.Main.SettingsManager;
import org.bukkit.*;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class CMDMine implements CommandExecutor, Listener {


	SettingsManager settings = SettingsManager.getInstance();
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  


	private ItemStack mineBlockItem(Material mat, String prestige, String rank, String sellprice) {
		ItemStack i = new ItemStack(mat);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(prestige);
		List<String> lore = new ArrayList<>();
		lore.add(rank);
		lore.add(" ");
		lore.add(sellprice);
		im.setLore(lore);
		i.setItemMeta(im);

		return i;
	}

	public ItemStack Head(Player p) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(p.getName());
		skull.setItemMeta(meta);

		return skull;
	}
	
	@Override
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("mine")) {
    	if(args.length == 0) {
      		if (!(sender instanceof Player)) return false;
      		Player p = (Player)sender;
			Inventory mineMenu = Bukkit.createInventory(null, 9, c("&a&lYour Mine!"));

	  		ItemStack teleport = new ItemStack(Material.GRASS);
	  		ItemMeta tm = teleport.getItemMeta();
	  		tm.setDisplayName(c("&cTeleport to your mine! (/mine home|tp)"));
	  		teleport.setItemMeta(tm);
	  		mineMenu.setItem(4, teleport);

			ItemStack mineBlocks = new ItemStack(Material.IRON_FENCE);
			ItemMeta mm = mineBlocks.getItemMeta();
			mm.setDisplayName(c("&aNew Blocks every 16 Levels!"));
			mineBlocks.setItemMeta(mm);
			mineMenu.setItem(6, mineBlocks);

			ItemStack portal = Head(p);
			ItemMeta pm = portal.getItemMeta();
			pm.setDisplayName(c("&bTo Visit another mine use /mine visit <name>."));
			portal.setItemMeta(pm);
			mineMenu.setItem(2, portal);


	  		p.openInventory(mineMenu);

    	}
		if(args.length == 1) {
			Player p = (Player)sender;
			if(args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("go")) {
				if(Bukkit.getWorlds().contains(Bukkit.getWorld(p.getUniqueId().toString()))) {
					Location pworld = new Location(Bukkit.getWorld(p.getUniqueId().toString()), 0.5, 100.5, 0.5, -90, 0);
					p.teleport(pworld);
				} else {
					p.sendMessage(c("&f&lMine &8| &7Unable to find your mine(/mine)."));
				}
			}
		}
		if(args.length == 2) {
			Player p = (Player)sender;
			if(args[0].equalsIgnoreCase("visit")) {
				boolean online = false;
				for(Player on : Bukkit.getOnlinePlayers()) {
					String name = on.getName();
					if(name.equalsIgnoreCase(args[1])){
						online = true;
					}
				}
				if(online == false) {
					p.sendMessage(c("&f&lMine &8| &7Player not Online."));
					return false;
				}

				if(Bukkit.getWorlds().contains(Bukkit.getWorld(args[1] + "sWorld"))) {
					Location pworld = new Location(Bukkit.getWorld(args[1] + "sWorld"), 0.5, 100.5, 0.5, -90, 0);
					p.teleport(pworld);
				} else {
					p.sendMessage(c("&f&lMine &8| &7That player has not created their mine."));
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
	    
	    if(e.getInventory().getName().equals(c("&a&lYour Mine!"))) {
			if(e.getClickedInventory().equals(p.getInventory())) return;
			e.setCancelled(true);
			if(e.getSlot() == 4) {
				if(!settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".HasMine")) {
					MineHandler.getInstance().CreateMine(p);
				} else {
					Mine m = MineSystem.getInstance().getMineByPlayer(p);
					p.teleport(m.getSpawnLocation());
				}
			}
		}
	  if(e.getInventory().getName().equals(c("&c&lMine Blocks"))) {
		  if(e.getClickedInventory().equals(p.getInventory())) return;
		  e.setCancelled(true);
	  }
	    
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}


