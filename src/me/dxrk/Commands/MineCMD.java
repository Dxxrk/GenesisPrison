package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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

import com.intellectualcrafters.plot.object.PlotPlayer;

import me.dxrk.Main.Main;
import me.dxrk.Main.ResetHandler;
import me.dxrk.Main.ResetHandler.ResetReason;
import me.dxrk.Tokens.Tokens;
import me.jet315.prisonmines.mine.Mine;

public class MineCMD implements CommandExecutor, Listener {
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  
	public Inventory plotUpgrades = Bukkit.createInventory(null, 9, c("&a&lUpgrade your Plot!"));
	
	
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("mine")) {
    	if(args.length == 0) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
     
      p.performCommand("plot home");
      
    	}
    	if(args.length == 1) {
    		if(args[0].equalsIgnoreCase("upgrade")) {
    			List<String> lore = new ArrayList<>();
    			ItemStack stone = new ItemStack(Material.STONE);
    			ItemMeta sm = stone.getItemMeta();
    			sm.setDisplayName(c("&6Upgrade 1: $12 Million per block"));
    			lore.add(c("&bCost: 10,000 Tokens"));
    			sm.setLore(lore);
    			lore.clear();
    			stone.setItemMeta(sm);
    			plotUpgrades.setItem(0, stone);
    			
    			ItemStack stonebrick = new ItemStack(Material.SMOOTH_BRICK);
    			ItemMeta sbm = stonebrick.getItemMeta();
    			sbm.setDisplayName(c("&6Upgrade 2: $14 Million per block"));
    			lore.add(c("&bCost: 20,000 Tokens"));
    			sbm.setLore(lore);
    			lore.clear();
    			stonebrick.setItemMeta(sbm);
    			plotUpgrades.setItem(1, stonebrick);
    			
    			ItemStack clay = new ItemStack(Material.HARD_CLAY);
    			ItemMeta cm = clay.getItemMeta();
    			cm.setDisplayName(c("&6Upgrade 3: $16 Million per block"));
    			lore.add(c("&bCost: 35,000 Tokens"));
    			cm.setLore(lore);
    			lore.clear();
    			clay.setItemMeta(cm);
    			plotUpgrades.setItem(2, clay);
    			
    			ItemStack brick = new ItemStack(Material.BRICK);
    			ItemMeta bm = brick.getItemMeta();
    			bm.setDisplayName(c("&6Upgrade 4: $18 Million per block"));
    			lore.add(c("&bCost: 50,000 Tokens"));
    			bm.setLore(lore);
    			lore.clear();
    			brick.setItemMeta(bm);
    			plotUpgrades.setItem(3, brick);
    			
    			ItemStack pris = new ItemStack(Material.PRISMARINE);
    			ItemMeta pm = pris.getItemMeta();
    			pm.setDisplayName(c("&6Upgrade 5: $20 Million per block"));
    			lore.add(c("&bCost: 75,000 Tokens"));
    			pm.setLore(lore);
    			lore.clear();
    			pris.setItemMeta(pm);
    			plotUpgrades.setItem(4, pris);
    			
    			ItemStack end = new ItemStack(Material.ENDER_STONE);
    			ItemMeta em = end.getItemMeta();
    			em.setDisplayName(c("&6Upgrade 6: $22 Million per block"));
    			lore.add(c("&bCost: 100,000 Tokens"));
    			em.setLore(lore);
    			lore.clear();
    			end.setItemMeta(em);
    			plotUpgrades.setItem(5, end);
    			
    			ItemStack nrack = new ItemStack(Material.NETHERRACK);
    			ItemMeta nrm = nrack.getItemMeta();
    			nrm.setDisplayName(c("&6Upgrade 7: $24 Million per block"));
    			lore.add(c("&bCost: 150,000 Tokens"));
    			nrm.setLore(lore);
    			lore.clear();
    			nrack.setItemMeta(nrm);
    			plotUpgrades.setItem(6, nrack);
    			
    			ItemStack quartz = new ItemStack(Material.QUARTZ_ORE);
    			ItemMeta qm = quartz.getItemMeta();
    			qm.setDisplayName(c("&6Upgrade 8: $26 Million per block"));
    			lore.add(c("&bCost: 200,000 Tokens"));
    			qm.setLore(lore);
    			lore.clear();
    			quartz.setItemMeta(qm);
    			plotUpgrades.setItem(7, quartz);
    			
    			ItemStack nbrick = new ItemStack(Material.NETHER_BRICK);
    			ItemMeta nbm = nbrick.getItemMeta();
    			nbm.setDisplayName(c("&6Upgrade 9: $28 Million per block"));
    			lore.add(c("&bCost: 250,000 Tokens"));
    			nbm.setLore(lore);
    			lore.clear();
    			nbrick.setItemMeta(nbm);
    			plotUpgrades.setItem(8, nbrick);
    			
    			((Player)sender).openInventory(plotUpgrades);
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
	    
	    if(e.getClickedInventory().getName().equals(c("&a&lUpgrade your Plot!"))) {
	    	e.setCancelled(true);
	    	if(e.getSlot() == 0) {
	    		if(p.hasPermission("plotup.1") || p.hasPermission("plotup.2") || p.hasPermission("plotup.3") || p.hasPermission("plotup.4")|| p.hasPermission("plotup.5")
	    				|| p.hasPermission("plotup.6") || p.hasPermission("plotup.7") || p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.cavalry")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 10000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.STONE), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 1);
				Tokens.getInstance().takeTokens(p, 10000);
				Main.perms.playerAdd(p, "plotup.1");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 1) {
	    		if(p.hasPermission("plotup.2") || p.hasPermission("plotup.3") || p.hasPermission("plotup.4")|| p.hasPermission("plotup.5")
	    				|| p.hasPermission("plotup.6") || p.hasPermission("plotup.7") || p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.hoplite")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 20000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.SMOOTH_BRICK), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 98);
				Tokens.getInstance().takeTokens(p, 20000);
				Main.perms.playerAdd(p, "plotup.2");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 2) {
	    		if(p.hasPermission("plotup.3") || p.hasPermission("plotup.4")|| p.hasPermission("plotup.5")
	    				|| p.hasPermission("plotup.6") || p.hasPermission("plotup.7") || p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.captain")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 35000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.HARD_CLAY), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 172);
				Tokens.getInstance().takeTokens(p, 35000);
				Main.perms.playerAdd(p, "plotup.3");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 3) {
	    		if(p.hasPermission("plotup.4")|| p.hasPermission("plotup.5")
	    				|| p.hasPermission("plotup.6") || p.hasPermission("plotup.7") || p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.colonel")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 50000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.BRICK), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 45);
				Tokens.getInstance().takeTokens(p, 50000);
				Main.perms.playerAdd(p, "plotup.4");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 4) {
	    		if(p.hasPermission("plotup.5")
	    				|| p.hasPermission("plotup.6") || p.hasPermission("plotup.7") || p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.ares")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 75000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.PRISMARINE), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 168);
				Tokens.getInstance().takeTokens(p, 75000);
				Main.perms.playerAdd(p, "plotup.5");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 5) {
	    		if(p.hasPermission("plotup.6") || p.hasPermission("plotup.7") || p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.hermes")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 100000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.ENDER_STONE), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 121);
				Tokens.getInstance().takeTokens(p, 100000);
				Main.perms.playerAdd(p, "plotup.6");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 6) {
	    		if(p.hasPermission("plotup.7") || p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.apollo")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 150000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				}
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHERRACK), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 87);
				Tokens.getInstance().takeTokens(p, 150000);
				Main.perms.playerAdd(p, "plotup.7");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 7) {
	    		if(p.hasPermission("plotup.8") || p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.kronos")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 200000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.QUARTZ_ORE), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 153);
				Tokens.getInstance().takeTokens(p, 200000);
				Main.perms.playerAdd(p, "plotup.8");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    	if(e.getSlot() == 8) {
	    		if(p.hasPermission("plotup.9")
	    				|| p.hasPermission("rank.zeus")) {
	    			p.sendMessage(c("&cYou have an upgrade equal or higher than this"));
	    			p.closeInventory();
	    			return;
	    		}
	    		if(Tokens.getInstance().getTokens(p) < 250000) {
					p.sendMessage(c("&cError: Not Enough Tokens"));
					p.closeInventory();
					return;
				} 
	    		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
	    		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
				m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHER_BRICK), 100);
				ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 112);
				Tokens.getInstance().takeTokens(p, 250000);
				Main.perms.playerAdd(p, "plotup.9");
				p.sendMessage(c("&aUpgade Successful!"));
				p.closeInventory();
	    	}
	    }
	    
	  
  }
  
  
  
  
  
  
  
  
  
  
  
  
  
}


