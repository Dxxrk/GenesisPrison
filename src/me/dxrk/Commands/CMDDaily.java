package me.dxrk.Commands;

import me.dxrk.Events.LocksmithHandler;
import me.dxrk.Events.PickXPHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
		List<String> loree = new ArrayList<>();
		if(lore.equals(m.c("&7Click to Claim!"))){
			loree.add("");
			loree.add(m.c("&7Rewards:"));
		}
		if(name.equals(m.c("&b&lSponsor Reward!"))){
			loree.add(m.c("&e3x &e&lToken &7Key"));
		}
		else if(name.equals(m.c("&a&lVIP Reward!"))){
			loree.add(m.c("&e3x &e&lToken &7Key"));
			loree.add(m.c("&e5x &7&lAlpha &7Key"));
		}
		else if(name.equals(m.c("&6&lMVP Reward!"))){
			loree.add(m.c("&e5x &c&lBeta &7Key"));
			loree.add(m.c("&e&lTokens +100,000"));
		}
		else if(name.equals(m.c("&c&lHero Reward!"))){
			loree.add(m.c("&bCommon Trnket Dust +1"));
			loree.add(m.c("&9Rare Trinket Dust +1"));
			loree.add(m.c("&5Epic Trinket Dust +1"));
			loree.add(m.c("&6Legendary Trinket Dust +1"));
		}
		else if(name.equals(m.c("&5&lDemi-God Reward!"))){
			loree.add(m.c("&e1x &5&lCommunity &7Key"));
			loree.add(m.c("&e2x &4&lOmega &7Key"));
		}
		else if(name.equals(m.c("&3&lTitan Reward!"))){
			loree.add(m.c("&e&lTokens +150,000"));
			loree.add(m.c("&e2x &4&lOmega &7Key"));
			loree.add(m.c("&e1x &4&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
		}
		else if(name.equals(m.c("&d&lGod Reward!"))){
			loree.add(m.c("&e5x &e&lToken &7Key"));
			loree.add(m.c("&b10000 XP"));
		}
		else if(name.equals(m.c("&e&lOlympian Reward!"))){
			loree.add(m.c("&e5x &4&lOmega &7Key"));
			loree.add(m.c("&e2x &5&lCommunity &7Key"));
		}
		else if(name.equals(m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lReward!"))){
			loree.add(m.c("&e&lTokens +250,000"));
		}
		else if(name.equals(m.c("&7Free Daily Reward!"))){
			loree.add(m.c("&e&lTokens +50,000"));
			loree.add(m.c("&e1x &e&lToken &7Key"));
			loree.add(m.c("&e1x &7&lAlpha &7Key"));
			loree.add(m.c("&e1x &c&lBeta &7Key"));
			loree.add(m.c("&e1x &4&lOmega &7Key"));
			loree.add(m.c("&f&l1% Chance for &3&lRank &7Key."));
		}
		loree.add("");
		loree.add(lore);
		im.setLore(loree);
		i.setItemMeta(im);
		
		return i;
		
	}
	
	
	public void openDaily(Player p) {
		Inventory inv = Bukkit.createInventory(null, 9, m.c("&e&lDaily Rewards:"));
		inv.setItem(3, rewardChest(m.c("&7Free Daily Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(5, rewardChest(m.c("&6&lRank Rewards"), m.c("&7Click to Open!")));
		p.openInventory(inv);
		
	}
	
	public void openRank(Player p) {
		Inventory inv = Bukkit.createInventory(null, 27, m.c("&e&lDaily Rank Rewards:"));
		inv.setItem(0, rewardChest(m.c("&b&lSponsor Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(10, rewardChest(m.c("&a&lVIP Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(20, rewardChest(m.c("&6&lMVP Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(12, rewardChest(m.c("&c&lHero Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(4, rewardChest(m.c("&5&lDemi-God Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(14, rewardChest(m.c("&3&lTitan Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(24, rewardChest(m.c("&d&lGod Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(16, rewardChest(m.c("&e&lOlympian Reward!"), m.c("&7Click to Claim!")));
		inv.setItem(8, rewardChest(m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lReward!"), m.c("&7Click to Claim!")));
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
			Random r = new Random();
			if(r.nextInt(100) == 1){
				LocksmithHandler.getInstance().addKey(p, "Rank", 1);
				p.sendMessage(m.c("&f&l You won the &3&lRank &7Key! &f&lCongratulations!"));
			}
			LocksmithHandler.getInstance().addKey(p, "Token", 1);
			LocksmithHandler.getInstance().addKey(p, "Alpha", 1);
			LocksmithHandler.getInstance().addKey(p, "Beta", 1);
			LocksmithHandler.getInstance().addKey(p, "Omega", 1);
			Tokens.getInstance().addTokens(p, 50000);
			this.settings.getDaily().set(p.getUniqueId().toString()+".FreeReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily Claimed!"));
		}
		if(rank.equals("sponsor")) {
			LocksmithHandler.getInstance().addKey(p, "Token", 3);
			this.settings.getDaily().set(p.getUniqueId().toString()+".SponsorReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &b&lSponsor &bClaimed!"));
		}
		if(rank.equals("vip")) {
			LocksmithHandler.getInstance().addKey(p, "Token", 3);
			LocksmithHandler.getInstance().addKey(p, "Alpha", 5);
			this.settings.getDaily().set(p.getUniqueId().toString()+".VIPReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &a&lVIP &bClaimed!"));		}
		if(rank.equals("mvp")) {
			Tokens.getInstance().addTokens(p, 100000);
			LocksmithHandler.getInstance().addKey(p, "Beta", 5);
			this.settings.getDaily().set(p.getUniqueId().toString()+".MVPReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &6&lMVP &bClaimed!"));
		}
		if(rank.equals("hero")) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" common");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" rare");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" epic");
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givedust "+p.getName()+" legendary");
			this.settings.getDaily().set(p.getUniqueId().toString()+".HeroReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &c&lHero &bClaimed!"));
		}
		if(rank.equals("demi-god")) {
			LocksmithHandler.getInstance().addKey(p, "Community", 1);
			LocksmithHandler.getInstance().addKey(p, "Omega", 2);
			this.settings.getDaily().set(p.getUniqueId().toString()+".Demi-GodReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &5&lDemi-God &bClaimed!"));
		}
		if(rank.equals("titan")) {
			Tokens.getInstance().addTokens(p, 150000);
			LocksmithHandler.getInstance().addKey(p, "Omega", 2);
			LocksmithHandler.getInstance().addKey(p, "Seasonal", 1);
			this.settings.getDaily().set(p.getUniqueId().toString()+".TitanReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &3&lTitan &bClaimed!"));
		}
		if(rank.equals("god")) {
			PickXPHandler.getInstance().addXP(p, 10000);
			LocksmithHandler.getInstance().addKey(p, "Token", 5);
			this.settings.getDaily().set(p.getUniqueId().toString()+".GodReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &d&lGod &bClaimed!"));
		}
		if(rank.equals("olympian")) {
			LocksmithHandler.getInstance().addKey(p, "Omega", 5);
			LocksmithHandler.getInstance().addKey(p, "Community", 2);
			this.settings.getDaily().set(p.getUniqueId().toString()+".OlympianReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &e&lOlympian &bClaimed!"));
		}
		if(rank.equals("genesis")) {
			Tokens.getInstance().addTokens(p, 250000);
			this.settings.getDaily().set(p.getUniqueId().toString()+".GenesisReward", getTodayDate());
			p.sendMessage(m.c("&f&lRewards &8| &bDaily &4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &bClaimed!"));
		}
	}
	
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!settings.getDaily().contains(p.getUniqueId().toString())) {
			settings.getDaily().set(p.getUniqueId().toString()+".FreeReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".SponsorReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".VIPReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".MVPReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".HeroReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".Demi-GodReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".TitanReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".GodReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".OlympianReward", "");
			settings.getDaily().set(p.getUniqueId().toString()+".GenesisReward", "");
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
					if(!p.hasPermission("rank.sponsor")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".SponsorReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "sponsor");
		    		}
		    	}
		    	if(e.getSlot() == 10) {
					if(!p.hasPermission("rank.vip")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".VIPReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "vip");
		    		}
		    	}
		    	if(e.getSlot() == 20) {
					if(!p.hasPermission("rank.mvp")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".MVPReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "mvp");
		    		}
		    	}
		    	if(e.getSlot() == 12) {
					if(!p.hasPermission("rank.hero")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".HeroReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "hero");
		    		}
		    	}
		    	if(e.getSlot() == 4) {
					if(!p.hasPermission("rank.demi-god")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".Demi-GodReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "demi-god");
		    		}
		    	}
		    	if(e.getSlot() == 14) {
					if(!p.hasPermission("rank.titan")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".TitanReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "titan");
		    		}
		    	}
		    	if(e.getSlot() == 24) {
					if(!p.hasPermission("rank.god")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".GodReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "god");
		    		}
		    	}
		    	if(e.getSlot() == 16) {
					if(!p.hasPermission("rank.olympian")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".OlympianReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "olympian");
		    		}
		    	}
		    	if(e.getSlot() == 8) {
					if(!p.hasPermission("rank.genesis")) {
						p.sendMessage(m.c("&cNo Permission!"));
						return;
					}
		    		if(this.settings.getDaily().get(p.getUniqueId().toString()+".GenesisReward").equals(getTodayDate())) {
		    			p.sendMessage(m.c("&cAlready Claimed Today!"));
		    			return;
		    		} else {
		    			giveRewards(p, "genesis");
		    		}
		    	}
		    	settings.saveDaily();
		    }
		
		
	}
	

}
