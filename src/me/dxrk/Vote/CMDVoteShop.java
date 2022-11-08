package me.dxrk.Vote;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.buycraft.plugin.client.ApiException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CMDVoteShop implements Listener, CommandExecutor{
	
	public static SettingsManager settings = SettingsManager.getInstance();
	
	public Methods m = Methods.getInstance();
	
	
	
	
	public ItemStack Spacer() {
		ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)10);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(m.c("&9Genesis&bVote"));
		im.addEnchant(Enchantment.DURABILITY, 3, true);
		im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		i.setItemMeta(im);
		return i;
	}

	
	
	public void openVS(Player p) {
		Inventory voteshop = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&d&lVote Shop: &b" + getVotePoints(p)));

		double amount = getVotePoints(p)*0.05;
		NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
		String coupon = formatter.format(amount);
		ItemStack item = new ItemStack(Material.PAPER);
		ItemMeta im = item.getItemMeta();
		im.setDisplayName(m.c("&7Withdraw up to &b$"+coupon+"&7."));
		List<String> lore = new ArrayList<>();
		lore.add(m.c("&7&oClick to withdraw"));
		lore.add(m.c("&7&oEnter the amount you want to withdraw and hit Esc."));
		im.setLore(lore);
		item.setItemMeta(im);
		voteshop.setItem(2, item);
		p.openInventory(voteshop);
	}
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("voteshop")) {
			openVS((Player)sender);
			
		}
		if(cmd.getName().equalsIgnoreCase("addvotepoint")){
			if(args.length == 2) {
				Player p = Bukkit.getPlayer(args[0]);
				int points = Integer.parseInt(args[1]);
				addVotePoint(p, points);
			}
		}

		return false;
	}
	
	public int getPrice(String s) {
	    StringBuilder lvl = new StringBuilder();
	    s = ChatColor.stripColor(s);
	    byte b;
	    int i;
	    char[] arrayOfChar;
	    for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
	      char c = arrayOfChar[b];
	      if (isInt(c))
	        lvl.append(c); 
	      b++;
	    } 
	    if (isInt(lvl.toString()))
	      return Integer.parseInt(lvl.toString()); 
	    return -1;
	  }
	
	
	public boolean isInt(String s) {
	    try {
	      int i = Integer.parseInt(s);
	      return true;
	    } catch (Exception e1) {
	      return false;
	    } 
	  }
	  
	  public boolean isInt(char ss) {
	    String s = String.valueOf(ss);
	    try {
	      int i = Integer.parseInt(s);
	      return true;
	    } catch (Exception e1) {
	      return false;
	    } 
	  }
	  
	  public static List<String> votelog = settings.getVote().getStringList("VoteShopLog");
	  
	  
	  @EventHandler
	  public void onClick(InventoryClickEvent e) {
		  Player p = (Player)e.getWhoClicked();
		  if (e.getClickedInventory() == null)
		      return; 
		    if (e.getClickedInventory().getName() == null)
		      return; 
		    
		    if(e.getClickedInventory().getName().equals(m.c("&d&lVote Shop: &b" + getVotePoints(p)))) {
		    	e.setCancelled(true);
				if(e.getSlot() == 2){
					if(getVotePoints(p) <=0) return;
					Main.getSignGUI().open(p, new String[] { "", "-------", "-----", "---" }, (player, lines) -> {
						double amount = Double.parseDouble(lines[0]);
						BuycraftUtil.createCoupon(player, amount);
					});
				}

		    	
		    }
	  }
	  
	  
	  public static void addVotePoint(Player p, int points) {
		  int vps = settings.getVote().getInt(p.getUniqueId().toString() + ".Votepoints");
		  int newvps = vps + points;
		  
		  settings.getVote().set(p.getUniqueId().toString() + ".Votepoints", newvps);
		  settings.saveVote();
	  }
	  
	  public int getVotePoints(Player p) {
		  if(!settings.getVote().contains(p.getUniqueId().toString())) 
			  return 0;
		  
		  return settings.getVote().getInt(p.getUniqueId().toString() + ".Votepoints");
	  }
	  public void removeVotePoints(Player p, int i) {
		  int vps = settings.getVote().getInt(p.getUniqueId().toString() + ".Votepoints");
		  int newvps = vps - i;
		  
		  settings.getVote().set(p.getUniqueId().toString() + ".Votepoints", newvps);
		  settings.saveVote();
	  }
	  
	
	

}
