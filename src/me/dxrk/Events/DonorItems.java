package me.dxrk.Events;

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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Main.Main;

public class DonorItems implements Listener, CommandExecutor{
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	
	
	public ItemStack EnchantVoucher(Player p, String enchant, int level) {
		ItemStack i = new ItemStack(Material.PAPER);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(c("&9&l"+enchant+" &b"+level));
		List<String> lore = new ArrayList<String>();
		lore.add(c("&cEnchant Voucher"));
		lore.add(c("&7&oDrag onto a pickaxe to apply"));
		im.setLore(lore);
		i.setItemMeta(im);
		Main.perms.playerAdd(p, "enchant."+enchant.toLowerCase());
		Main.perms.playerAdd(p, "enchant."+enchant.toLowerCase()+"unlock");
		
		
		return i;
	}
	
	
	
	

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("giveenchant")) {
			if(cs.hasPermission("rank.owner")) {
				if(args.length == 3) {
					Player receiver = Bukkit.getServer().getPlayer(args[0]);
					receiver.getInventory().addItem(EnchantVoucher(receiver, args[1], Integer.parseInt(args[2])));
				}
			}
		}
		return false;
	}
	
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		
		if(e.getCurrentItem() == null) return;
		if (e.getCurrentItem().getType().equals(Material.AIR))
		      return; 
		if(!e.getClickedInventory().equals(p.getInventory())) return;
		if(e.getCursor() == null) return;
		if (e.getRawSlot() == e.getSlot())
		      return; 
		    if (!e.getCursor().hasItemMeta())
		      return; 
		    if (!e.getCursor().getItemMeta().hasLore())
		      return; 
		
		if(e.getClick().equals(ClickType.LEFT)) {
			if(e.getCursor().getType().equals(Material.PAPER) && e.getCursor().getItemMeta().getLore().get(0).equals(c("&cEnchant Voucher"))) {
				
				String enchant = e.getCursor().getItemMeta().getDisplayName();
				String[] en = ChatColor.stripColor(enchant).toLowerCase().split(" ");
				
				if(e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE)) {
					ItemStack i = e.getCurrentItem().clone();
					ItemMeta im = i.getItemMeta();
					List<String> lore = im.getLore();
					int level = 0;
					for(int x = 0; x <lore.size(); x++) {
						if(ChatColor.stripColor(lore.get(x)).toLowerCase().contains(en[0])) {
							lore.remove(x);
							level = PickXPHandler.getBlocks(ChatColor.stripColor(lore.get(x)));
						}
					}
					
					int added = level+Integer.parseInt(en[1]);
					if(added > 100) {
						added = 100;
					}
					
					lore.add(c("&9&l"+en[0]+" &b"+added));
					im.setLore(lore);
					i.setItemMeta(im);
					p.getInventory().setItem(e.getSlot(), i);
					p.updateInventory();
					e.setCursor(null);
					e.setCancelled(true);
					
				}
				else {
					return;
				}
			}
		}
		
	}
	
	

}
