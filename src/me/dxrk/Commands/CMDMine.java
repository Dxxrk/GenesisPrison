package me.dxrk.Commands;

import me.dxrk.Events.MineHandler;
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

	private void openMineBlockInv(Player p){
		Inventory inv = Bukkit.createInventory(null, 54, c("&c&lMine Blocks"));
		ItemStack ironbar = new ItemStack(Material.IRON_FENCE);
		ItemMeta im = ironbar.getItemMeta();
		im.setDisplayName(c("&c&lGenesis &b&lPrison"));
		ironbar.setItemMeta(im);
		for(int i = 0; i <54; i++){
			inv.setItem(i, ironbar);
		}
		inv.setItem(2, mineBlockItem(Material.COBBLESTONE, c("&bPrestige 0"), c("&7Rank: Default"), c("&a$2000000")));
		inv.setItem(3, mineBlockItem(Material.MOSSY_COBBLESTONE, c("&bPrestige 25"), c("&7Rank: N/A"), c("&a$3000000")));
		inv.setItem(4, mineBlockItem(Material.STONE, c("&bPrestige 50"), c("&7Rank: Donator"), c("&a$4000000")));
		inv.setItem(5, mineBlockItem(Material.SMOOTH_BRICK, c("&bPrestige 75"), c("&7Rank: N/A"), c("&a$5000000")));
		inv.setItem(6, mineBlockItem(Material.SANDSTONE, c("&bPrestige 100"), c("&7Rank: VIP"), c("&a$6000000")));
		inv.setItem(11, mineBlockItem(Material.HARD_CLAY, c("&bPrestige 125"), c("&7Rank: N/A"), c("&a$7000000")));
		inv.setItem(12, mineBlockItem(Material.BRICK, c("&bPrestige 150"), c("&7Rank: MVP"), c("&a$8000000")));
		inv.setItem(13, mineBlockItem(Material.COAL_ORE, c("&bPrestige 200"), c("&7Rank: N/A"), c("&a$9000000")));
		inv.setItem(14, mineBlockItem(Material.COAL_BLOCK, c("&bPrestige 250"), c("&7Rank: Hero"), c("&a$10000000")));
		inv.setItem(15, mineBlockItem(Material.IRON_ORE, c("&bPrestige 300"), c("&7Rank: N/A"), c("&a$11000000")));
		inv.setItem(20, mineBlockItem(Material.IRON_BLOCK, c("&bPrestige 350"), c("&7Rank: N/A"), c("&a$12000000")));
		inv.setItem(21, mineBlockItem(Material.GOLD_ORE, c("&bPrestige 400"), c("&7Rank: Demi-God"), c("&a$13000000")));
		inv.setItem(22, mineBlockItem(Material.GOLD_BLOCK, c("&bPrestige 450"), c("&7Rank: N/A"), c("&a$14000000")));
		inv.setItem(23, mineBlockItem(Material.REDSTONE_ORE, c("&bPrestige 500"), c("&7Rank: Titan"), c("&a$15000000")));
		inv.setItem(24, mineBlockItem(Material.REDSTONE_BLOCK, c("&bPrestige 575"), c("&7Rank: N/A"), c("&a$16000000")));
		inv.setItem(29, mineBlockItem(Material.LAPIS_ORE, c("&bPrestige 650"), c("&7Rank: N/A"), c("&a$17000000")));
		inv.setItem(30, mineBlockItem(Material.LAPIS_BLOCK, c("&bPrestige 725"), c("&7Rank: God"), c("&a$18000000")));
		inv.setItem(31, mineBlockItem(Material.DIAMOND_ORE, c("&bPrestige 800"), c("&7Rank: N/A"), c("&a$19000000")));
		inv.setItem(32, mineBlockItem(Material.DIAMOND_BLOCK, c("&bPrestige 875"), c("&7Rank: N/A"), c("&a$20000000")));
		inv.setItem(33, mineBlockItem(Material.EMERALD_ORE, c("&bPrestige 950"), c("&7Rank: Olympian"), c("&a$21000000")));
		inv.setItem(38, mineBlockItem(Material.EMERALD_BLOCK, c("&bPrestige 1025"), c("&7Rank: N/A"), c("&a$22000000")));
		inv.setItem(39, mineBlockItem(Material.NETHERRACK, c("&bPrestige 1100"), c("&7Rank: N/A"), c("&a$23000000")));
		inv.setItem(40, mineBlockItem(Material.NETHER_BRICK, c("&bPrestige 1200"), c("&7Rank: Genesis"), c("&a$24000000")));
		inv.setItem(41, mineBlockItem(Material.QUARTZ_ORE, c("&bPrestige 1300"), c("&7Rank: N/A"), c("&a$25000000")));
		inv.setItem(42, mineBlockItem(Material.QUARTZ_BLOCK, c("&bPrestige 1400"), c("&7Rank: N/A"), c("&a$26000000")));
		inv.setItem(48, mineBlockItem(Material.PRISMARINE, c("&bPrestige 1500"), c("&7Rank: N/A"), c("&a$27000000")));
		inv.setItem(50, mineBlockItem(Material.ENDER_STONE, c("&bPrestige 1750"), c("&7Rank: N/A"), c("&a$28000000")));


		p.openInventory(inv);

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
	  		tm.setDisplayName(c("&cTeleport to your mine!"));
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
			if(args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp")) {
				if(Bukkit.getWorlds().contains(Bukkit.getWorld(p.getName() + "sWorld"))) {
					Location pworld = new Location(Bukkit.getWorld(p.getName()+"sWorld"), 0.5, 113, 0.5, -90, 0);
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
					Location pworld = new Location(Bukkit.getWorld(args[1] + "sWorld"), 0.5, 113, 0.5, -90, 0);
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
				if(settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".hasMine") == false) {
					MineHandler.getInstance().CreateMine(p);
				} else {
					Location pworld = new Location(Bukkit.getWorld(p.getName()+"sWorld"), 0.5, 113, 0.5, -90, 0);
					p.teleport(pworld);
				}
			}
		}
	  if(e.getInventory().getName().equals(c("&c&lMine Blocks"))) {
		  if(e.getClickedInventory().equals(p.getInventory())) return;
		  e.setCancelled(true);
	  }
	    
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}


