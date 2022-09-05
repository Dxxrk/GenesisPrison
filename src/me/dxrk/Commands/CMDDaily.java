package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Events.LocksmithHandler;
import me.dxrk.Events.PickXPHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CMDDaily implements Listener, CommandExecutor{
	
	Methods m = Methods.getInstance();
	
	SettingsManager settings = SettingsManager.getInstance();
	
	
	private String getTodayDate() {
		LocalDate time = java.time.LocalDate.now();
		
		return time.toString();
	}
	
	
	public ItemStack rewardChest(String name, String lore) {
		ItemStack i = new ItemStack(Material.CHEST);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> loree = new ArrayList<String>();
		if(lore.equals(m.c("&7Click to Claim!"))){
			loree.add("");
			loree.add(m.c("&7Rewards:"));
		}
		if(name.equals(m.c("&cCavalry Reward!"))){
			loree.add(m.c("&6&lPickaxe XP Voucher 1500"));
		}
		else if(name.equals(m.c("&eHoplite Reward!"))){
			loree.add(m.c("&6&lPickaxe XP Voucher 2000"));
			loree.add(m.c("&4&lHades Rune +1"));
		}
		else if(name.equals(m.c("&9Captain Reward!"))){
			loree.add(m.c("&f&lPolis Rune +1"));
			loree.add(m.c("&e&lTokens +1000"));
		}
		else if(name.equals(m.c("&6Colonel Reward!"))){
			loree.add(m.c("&bCommon Trnket Dust +1"));
			loree.add(m.c("&9Rare Trinket Dust +1"));
			loree.add(m.c("&5Epic Trinket Dust +1"));
			loree.add(m.c("&6Legendary Trinket Dust +1"));
		}
		else if(name.equals(m.c("&c&lAres Reward!"))){
			loree.add(m.c("&6&lPickaxe XP Voucher 2500"));
			loree.add(m.c("&f&lPolis Rune +2"));
		}
		else if(name.equals(m.c("&a&lHermes Reward!"))){
			loree.add(m.c("&e&lTokens +1500"));
			loree.add(m.c("&4&lHades Rune +2"));
			loree.add(m.c("&f&lPolis Rune +2"));
		}
		else if(name.equals(m.c("&6&lApollo Reward!"))){
			loree.add(m.c("&e&lTokens +1500"));
			loree.add(m.c("&e&lMidas Rune +5"));
			loree.add(m.c("&9&lPoseidon Rune +5"));
		}
		else if(name.equals(m.c("&8&lKronos Reward!"))){
			loree.add(m.c("&6&lPickaxe XP Voucher 3000"));
			loree.add(m.c("&4&lOblivion Rune +1"));
		}
		else if(name.equals(m.c("&f&lZeus Reward!"))){
			loree.add(m.c("&e&lTokens +2500"));
			loree.add(m.c("&6&lPickaxe XP Voucher 5000"));
			loree.add(m.c("&e&lMidas Rune +2"));
			loree.add(m.c("&9&lPoseidon Rune +2"));
			loree.add(m.c("&4&lHades Rune +2"));
			loree.add(m.c("&f&lPolis Rune +2"));
			loree.add(m.c("&4&lOblivion Rune +2"));
		}
		else if(name.equals(m.c("&6Free Daily Reward!"))){
			loree.add(m.c("&e&lMidas Rune +1"));
			loree.add(m.c("&9&lPoseidon Rune +1"));
			loree.add(m.c("&4&lHades Rune +1"));
			loree.add(m.c("&f&lPolis Rune +1"));
			loree.add(m.c("&6&lPickaxe XP Voucher 1000"));
			loree.add(m.c("&e&lTokens +500"));
		}
		loree.add("");
		loree.add(lore);
		im.setLore(loree);
		i.setItemMeta(im);
		
		return i;
		
	}
	
	
	public void openDaily(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, m.c("&e&lDaily Rewards:"));
		inv.setItem(3, rewardChest(m.c("&6Free Daily Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(5, rewardChest(m.c("&6&lRank Rewards"), m.c("&7Click to Open!")));
		p.openInventory(inv);
		
	}
	
	public void openRank(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, m.c("&e&lDaily Rank Rewards:"));
		inv.setItem(0, rewardChest(m.c("&cCavalry Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(10, rewardChest(m.c("&eHoplite Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(20, rewardChest(m.c("&9Captain Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(12, rewardChest(m.c("&6Colonel Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(4, rewardChest(m.c("&c&lAres Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(14, rewardChest(m.c("&a&lHermes Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(24, rewardChest(m.c("&6&lApollo Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(16, rewardChest(m.c("&8&lKronos Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(8, rewardChest(m.c("&f&lZeus Reward!"), m.c("&7Click to Claim!")));
		p.openInventory(inv);
	}
	
	
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("daily")) {
			Player p = (Player)sender;
			openDaily(p);
			
		}
		
		
		
		
		return false;
	}
	
	
	
	public void giveRewards(Player p, String rank) {
		if(rank.equals("free")) {
			LocksmithHandler.getInstance().addKey(p, "Midas", 1);
			LocksmithHandler.getInstance().addKey(p, "Poseidon", 1);
			LocksmithHandler.getInstance().addKey(p, "Hades", 1);
			LocksmithHandler.getInstance().addKey(p, "Polis", 1);
			PickXPHandler.getInstance().giveXPItem(p, 1000);
			Tokens.getInstance().addTokens(p, 500);
			this.settings.getDaily().set(p.getUniqueId().toString()+".FreeReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily Claimed!"));
		}
		if(rank.equals("cavalry")) {
			PickXPHandler.getInstance().giveXPItem(p, 1500);
			this.settings.getDaily().set(p.getUniqueId().toString()+".CavalryReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &cCavalry &bClaimed!"));
		}
		if(rank.equals("hoplite")) {
			PickXPHandler.getInstance().giveXPItem(p, 2000);
			LocksmithHandler.getInstance().addKey(p, "Hades", 1);
			this.settings.getDaily().set(p.getUniqueId().toString()+".HopliteReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &eHoplite &bClaimed!"));		}
		if(rank.equals("captain")) {
			Tokens.getInstance().addTokens(p, 1000);
			LocksmithHandler.getInstance().addKey(p, "Polis", 1);
			this.settings.getDaily().set(p.getUniqueId().toString()+".CaptainReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &9Captain &bClaimed!"));
		}
		if(rank.equals("colonel")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" common");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" rare");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" epic");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" legendary");
			this.settings.getDaily().set(p.getUniqueId().toString()+".ColonelReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &6Colonel &bClaimed!"));
		}
		if(rank.equals("ares")) {
			PickXPHandler.getInstance().giveXPItem(p, 2500);
			LocksmithHandler.getInstance().addKey(p, "Polis", 2);
			this.settings.getDaily().set(p.getUniqueId().toString()+".AresReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &c&lAres &bClaimed!"));
		}
		if(rank.equals("hermes")) {
			Tokens.getInstance().addTokens(p, 1500);
			LocksmithHandler.getInstance().addKey(p, "Hades", 2);
			LocksmithHandler.getInstance().addKey(p, "Polis", 2);
			this.settings.getDaily().set(p.getUniqueId().toString()+".HermesReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &a&lHermes &bClaimed!"));
		}
		if(rank.equals("apollo")) {
			Tokens.getInstance().addTokens(p, 1500);
			LocksmithHandler.getInstance().addKey(p, "Midas", 5);
			LocksmithHandler.getInstance().addKey(p, "Poseidon", 5);
			this.settings.getDaily().set(p.getUniqueId().toString()+".ApolloReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &6&lApollo &bClaimed!"));
		}
		if(rank.equals("kronos")) {
			PickXPHandler.getInstance().giveXPItem(p, 3000);
			LocksmithHandler.getInstance().addKey(p, "Oblivion", 1);
			this.settings.getDaily().set(p.getUniqueId().toString()+".KronosReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &8&lKronos &bClaimed!"));
		}
		if(rank.equals("zeus")) {
			Tokens.getInstance().addTokens(p, 2500);
			PickXPHandler.getInstance().giveXPItem(p, 5000);
			LocksmithHandler.getInstance().addKey(p, "Midas", 2);
			LocksmithHandler.getInstance().addKey(p, "Poseidon", 2);
			LocksmithHandler.getInstance().addKey(p, "Hades", 2);
			LocksmithHandler.getInstance().addKey(p, "Polis", 2);
			LocksmithHandler.getInstance().addKey(p, "Oblivion", 2);
			this.settings.getDaily().set(p.getUniqueId().toString()+".ZeusReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &f&lZeus &bClaimed!"));
		}
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!settings.getDaily().contains(p.getUniqueId().toString())) {
			settings.getDaily().set(p.getUniqueId().toString()+".FreeReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".CavalryReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".HopliteReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".CaptainReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".ColonelReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".AresReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".HermesReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".ApolloReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".KronosReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".ZeusReward", "");
		}
		settings.saveDaily();
	}
	
	
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();

		if (e.getClickedInventory() == null)
		      return; 
		    if (e.getClickedInventory().getName() == null)
		      return; 
		    
		    if(e.getClickedInventory().getName().equals(m.c("&e&lDaily Rewards:"))) {
		    	e.setCancelled(true);
		    	
		    	if(e.getSlot() == 3) {
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".FreeReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "free");
		    		}
		    	}
		    	settings.saveDaily();
		    	if(e.getSlot() == 5) {
		    		openRank(p);
		    	}
		    }
		    if(e.getClickedInventory().getName().equals(m.c("&e&lDaily Rank Rewards:"))) {
		    	e.setCancelled(true);
		    	
		    	if(e.getSlot() == 0) {
					if(!p.hasPermission("rank.cavalry")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".CavalryReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "cavalry");
		    		}
		    	}
		    	if(e.getSlot() == 10) {
					if(!p.hasPermission("rank.hoplite")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".HopliteReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "hoplite");
		    		}
		    	}
		    	if(e.getSlot() == 20) {
					if(!p.hasPermission("rank.captain")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".CaptainReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "captain");
		    		}
		    	}
		    	if(e.getSlot() == 12) {
					if(!p.hasPermission("rank.colonel")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".ColonelReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "colonel");
		    		}
		    	}
		    	if(e.getSlot() == 4) {
					if(!p.hasPermission("rank.ares")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".AresReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "ares");
		    		}
		    	}
		    	if(e.getSlot() == 14) {
					if(!p.hasPermission("rank.hermes")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".HermesReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "hermes");
		    		}
		    	}
		    	if(e.getSlot() == 24) {
					if(!p.hasPermission("rank.apollo")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".ApolloReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "apollo");
		    		}
		    	}
		    	if(e.getSlot() == 16) {
					if(!p.hasPermission("rank.kronos")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".KronosReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "kronos");
		    		}
		    	}
		    	if(e.getSlot() == 8) {
					if(!p.hasPermission("rank.zeus")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".ZeusReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "zeus");
		    		}
		    	}
		    	settings.saveDaily();
		    }
		
		
	}
	

}
