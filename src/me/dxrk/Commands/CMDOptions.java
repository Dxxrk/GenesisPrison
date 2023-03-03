package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Events.SellHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.md_5.bungee.api.ChatColor;

public class CMDOptions implements Listener, CommandExecutor{
	
	SettingsManager settings = SettingsManager.getInstance();
	static Methods m = Methods.getInstance();
	
	
	private ItemStack option(Player p, String option) {
		ItemStack ops = new ItemStack(Material.INK_SACK);
		ItemMeta om = ops.getItemMeta();
		List<String> lore = new ArrayList<String>();
		if(settings.getOptions().getBoolean(p.getUniqueId().toString()+"."+option) == true) {
			ops.setDurability((short)10);
			om.setDisplayName(m.c("&a"+option)+" Enabled");
			lore.add(m.c("&7Click to Disable"));
		} else {
			ops.setDurability((short)1);
			om.setDisplayName(m.c("&c"+option)+" Disabled");
			lore.add(m.c("&7Click to Enable"));
		}
		om.setLore(lore);
		ops.setItemMeta(om);
		
		
		
		
		return ops;
	}
	
	
	public void openOptions(Player p) {
		Inventory ops = Bukkit.createInventory(null, 45, m.c("&3&lOptions:"));
		for(int i = 0; i < 45; i++) {
			ops.setItem(i, Spacer());
		}
		ops.setItem(1, option(p, "Autorankup"));
		ops.setItem(2, option(p, "Dust-Finder-Messages"));
		ops.setItem(3, option(p, "Key-Finder-Messages"));
		ops.setItem(4, option(p, "Explosion-Messages"));
		ops.setItem(5, option(p, "Wave-Messages"));
		ops.setItem(6, option(p, "Nuke-Messages"));
		ops.setItem(7, option(p, "Research-Messages"));
		ops.setItem(10, option(p, "Key-Party-Messages"));
		ops.setItem(11, option(p, "Booster-Messages"));
		ops.setItem(12, option(p, "Junkpile-Messages"));
		ops.setItem(13, option(p, "Laser-Messages"));
		ops.setItem(14, option(p, "Prestige-Finder-Messages"));
		ops.setItem(15, option(p, "Vote-Reminder"));
		ops.setItem(16, option(p, "Daily-Reminder"));
		p.openInventory(ops);
	}

	private ItemStack tagColor(String name, short data) {
		ItemStack item = new ItemStack(Material.INK_SACK, 1, data);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(name);
		List<String> lore = new ArrayList<>();
		lore.add(m.c("&7Change your tag color to "+ChatColor.stripColor(name)));
		im.setLore(lore);
		item.setItemMeta(im);
		return item;
	}
	public static String TagColor(String s) {
		if(s.equalsIgnoreCase("default"))
			return m.c("&f&ki&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls&f&ki&r");
		if(s.equalsIgnoreCase("pink"))
			return m.c("&f&ki&d&lGenesis&f&ki&r");
		if(s.equalsIgnoreCase("blue"))
			return m.c("&f&ki&b&lGenesis&f&ki&r");
		if(s.equalsIgnoreCase("lime"))
			return m.c("&f&ki&a&lGenesis&f&ki&r");
		if(s.equalsIgnoreCase("yellow"))
			return m.c("&f&ki&e&lGenesis&f&ki&r");
		if(s.equalsIgnoreCase("red"))
			return m.c("&f&ki&c&lGenesis&f&ki&r");
		if(s.equalsIgnoreCase("green"))
			return m.c("&f&ki&2&lGenesis&f&ki&r");
		if(s.equalsIgnoreCase("gold"))
			return m.c("&f&ki&6&lGenesis&f&ki&r");
		if(s.equalsIgnoreCase("cyan"))
			return m.c("&f&ki&3&lGenesis&f&ki&r");
		return m.c("&f&ki&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls&f&ki&r");
	}

	public void openGenesis(Player p) {
		Inventory gen = Bukkit.createInventory(null, 9, m.c("&cChange Tag Color"));
		gen.setItem(0, tagColor(m.c("&4&lD&c&le&6&lf&e&la&a&lu&b&ll&d&lt"), (short)8));
		gen.setItem(1, tagColor(m.c("&d&lPink"), (short)9));
		gen.setItem(2, tagColor(m.c("&b&lBlue"), (short)12));
		gen.setItem(3, tagColor(m.c("&a&lLime"), (short)10));
		gen.setItem(4, tagColor(m.c("&e&lYellow"), (short)11));
		gen.setItem(5, tagColor(m.c("&c&lRed"), (short)1));
		gen.setItem(6, tagColor(m.c("&2&lGreen"), (short)2));
		gen.setItem(7, tagColor(m.c("&6&lGold"), (short)14));
		gen.setItem(8, tagColor(m.c("&3&lCyan"), (short)6));
		p.openInventory(gen);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("options")) {
			Player p = (Player)sender;
			openOptions(p);
		}
		if(cmd.getName().equalsIgnoreCase("genesis")) {
			Player p = (Player)sender;
			if(p.hasPermission("rank.genesis"))
				openGenesis(p);
			else
				p.sendMessage(m.c("&cNo Permission"));
		}
		
		
		return false;
	}
	public ItemStack Spacer() {
	    ItemStack i = new ItemStack(Material.IRON_FENCE);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lGenesis &b&lPrison"));
	    i.setItemMeta(im);
	    return i;
	  }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore()) {
			this.settings.getOptions().set(p.getUniqueId().toString()+".Autorankup", false);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Tokens-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Dust-Finder-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Key-Finder-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Explosion-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Wave-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Nuke-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Research-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Key-Party-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Booster-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Junkpile-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Laser-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Prestige-Finder-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Daily-Reminder", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Vote-Reminder", true);
			this.settings.saveOptions();
		}
		if(this.settings.getOptions().get(p.getUniqueId().toString()+".GenesisColor") == null){
			this.settings.getOptions().set(p.getUniqueId().toString()+".GenesisColor", "Default");
			this.settings.saveOptions();
		}
		if(this.settings.getVote().get(p.getUniqueId().toString()+".HasVoted") == null){
			this.settings.getVote().set(p.getUniqueId().toString()+".HasVoted", "nope");
			this.settings.saveVote();
		}
	}
	
	
	
	@EventHandler
	public void optionsClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (e.getClickedInventory() == null)
		      return; 
		    if (e.getClickedInventory().getName() == null)
		      return; 
		    
		    if(e.getClickedInventory().getName().equals(m.c("&3&lOptions:"))) {
		    	e.setCancelled(true);
		    	if(e.getCurrentItem().equals(Spacer())) return;
		    	if(e.getCurrentItem().getType().equals(Material.INK_SACK)) {
		    		String[] name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split(" ");
		    		if(name[1].equals("Enabled")) {
						this.settings.getOptions().set(p.getUniqueId().toString()+"."+name[0], false);

		    		} else if(name[1].equals("Disabled")) {
						this.settings.getOptions().set(p.getUniqueId().toString()+"."+name[0], true);

		    		}
		    	}
		    	openOptions(p);
		    	this.settings.saveOptions();
		    	
		    }
			if(e.getClickedInventory().getName().equals(m.c("&cChange Tag Color"))) {
				e.setCancelled(true);
				String color = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
				p.sendMessage(m.c("&7Prefix Changed to: "+e.getCurrentItem().getItemMeta().getDisplayName()));
				this.settings.getOptions().set(p.getUniqueId().toString()+".GenesisColor", color);
				this.settings.saveOptions();

			}
		    
		    
		    
		    
		    
	}

}
