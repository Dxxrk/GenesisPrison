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
	Methods m = Methods.getInstance();
	
	
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
		ops.setItem(2, option(p, "Autosell"));
		ops.setItem(3, option(p, "Backpack-Warnings"));
		ops.setItem(4, option(p, "Tokens-Messages"));
		ops.setItem(5, option(p, "Discovery-Messages"));
		ops.setItem(6, option(p, "Encounter-Messages"));
		ops.setItem(7, option(p, "Explosion-Messages"));
		ops.setItem(10, option(p, "Wave-Messages"));
		ops.setItem(11, option(p, "Vaporize-Messages"));
		ops.setItem(12, option(p, "TokenFinder-Messages"));
		ops.setItem(13, option(p, "Stake-Messages"));
		ops.setItem(14, option(p, "Research-Messages"));
		ops.setItem(15, option(p, "RuneParty-Messages"));
		ops.setItem(16, option(p, "Booster-Messages"));
		ops.setItem(22, option(p, "Junkpile-Messages"));
		p.openInventory(ops);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("options")) {
			Player p = (Player)sender;
			openOptions(p);
		}
		
		
		return false;
	}
	public ItemStack Spacer() {
	    ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)8);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Genesis"));
	    im.addEnchant(Enchantment.DURABILITY, 0, false);
	    i.setItemMeta(im);
	    i.removeEnchantment(Enchantment.DURABILITY);
	    return i;
	  }
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
		if(!p.hasPlayedBefore()) {
			this.settings.getOptions().set(p.getUniqueId().toString()+".Autorankup", false);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Autosell", false);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Backpack-Warnings", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Tokens-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Dust Finder-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Key Finder-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Explosion-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Wave-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Nuke-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Token Finder-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Stake-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Research-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Rune Party-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Booster-Messages", true);
			this.settings.getOptions().set(p.getUniqueId().toString()+".Junkpile-Messages", true);
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
		    
		    
		    
		    
		    
	}

}
