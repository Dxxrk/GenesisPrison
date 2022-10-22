package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;

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
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Events.PickXPHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;

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
	
	
	public ItemStack voteitem(Material item, String name, String lore1, String lore2, int price, int amt, short sh) {
		ItemStack i = new ItemStack(item, amt, (short)sh);
		ItemMeta im = i.getItemMeta();
		List<String> lore = new ArrayList<>();
		if(lore1 != null)
		lore.add(lore1);
		if(lore2 != null)
		lore.add(lore2);
		lore.add(m.c("&dPrice: &b"+price));
		im.setDisplayName(name);
		im.setLore(lore);
		lore.clear();
		im.addItemFlags(ItemFlag.values());
		i.setItemMeta(im);
		return i;
		
	}
	
	
	public void openVS(Player p) {
		Inventory voteshop = Bukkit.createInventory(null, 36, m.c("&d&lVote Shop: &b" + getVotePoints(p)));
		voteshop.setItem(0, voteitem(Material.BOOK, m.c("&c&lAres &7Rank"), null, null, 175, 1, (short)0));
		voteshop.setItem(1, voteitem(Material.BOOK, m.c("&a&lHermes &7Rank"), null, null, 250, 1, (short)0));
		voteshop.setItem(2, voteitem(Material.BOOK, m.c("&6&lApollo &7Rank"), null, null, 325, 1, (short)0));
		voteshop.setItem(3, Spacer());
		voteshop.setItem(4, Spacer());
		voteshop.setItem(5, Spacer());
		voteshop.setItem(6, voteitem(Material.NETHER_STAR, m.c("&f&lPolis &7Rune"), null, null, 15, 5, (short)0));
		voteshop.setItem(7, voteitem(Material.DRAGON_EGG, m.c("&c&l&kOO&4&lOblivion&c&l&kOO&r &7Rune"), null, null, 30, 1, (short)0));
		voteshop.setItem(8, voteitem(Material.BLAZE_ROD, m.c("&f&l&kOO&e&lOlympus&f&l&kOO&r &7Rune"), null, null, 50, 1, (short)0));
		voteshop.setItem(9, Spacer());
		voteshop.setItem(10, Spacer());
		voteshop.setItem(11, voteitem(Material.EXP_BOTTLE, m.c("&f&l2x XP Boost"), m.c("&d01:00:00"), null, 35, 1, (short)0));
		voteshop.setItem(12, Spacer());
		voteshop.setItem(13, voteitem(Material.GRASS, m.c("&a&l+1 PlotMine"), m.c("&7Notify Staff Upon Purchase"), null, 75, 1, (short)0));
		voteshop.setItem(14, Spacer());
		voteshop.setItem(15, voteitem(Material.POTION, m.c("&f&l2.0x Sell Boost"), m.c("&d1:00:00"), null, 35, 1, (short)8260));
		voteshop.setItem(16, Spacer());
		voteshop.setItem(17, Spacer());
		voteshop.setItem(18, Spacer());
		voteshop.setItem(19, voteitem(Material.EXP_BOTTLE, m.c("&f&l2x XP Boost"), m.c("&d02:00:00"), null, 50, 1, (short)0));
		voteshop.setItem(20, Spacer());
		voteshop.setItem(21, Spacer());
		voteshop.setItem(22, voteitem(Material.PAPER, m.c("&bItem Rename"), null, null, 2, 1, (short)0));
		voteshop.setItem(23, Spacer());
		voteshop.setItem(24, Spacer());
		voteshop.setItem(25, voteitem(Material.POTION, m.c("&f&l3.0x Sell Boost"), m.c("&d01:00:00"), null, 75, 1, (short)8260));
		voteshop.setItem(26, Spacer());
		voteshop.setItem(27, voteitem(Material.NAME_TAG, m.c("&f&l&ki&e&lOlympian&f&l&ki&r &7Tag"), null, null, 5, 1, (short)0));
		voteshop.setItem(28, voteitem(Material.NAME_TAG, m.c("&7&l&ki&8&lTitan&7&l&ki&r &7Tag"), null, null, 5, 1, (short)0));
		voteshop.setItem(29, voteitem(Material.NAME_TAG, m.c("&a&l&ki&2&lGaia&a&l&ki&r &7Tag"), null, null, 5, 1, (short)0));
		voteshop.setItem(30, Spacer());
		voteshop.setItem(31, Spacer());
		voteshop.setItem(32, Spacer());
		voteshop.setItem(33, voteitem(Material.MAGMA_CREAM, m.c("&6&lPick XP Voucher"), m.c("&a+50000"), null, 15, 1, (short)0));
		voteshop.setItem(34, voteitem(Material.MAGMA_CREAM, m.c("&6&lPick XP Voucher"), m.c("&a+200000"), null, 35, 1, (short)0));
		voteshop.setItem(35, voteitem(Material.MAGMA_CREAM, m.c("&6&lPick XP Voucher"), m.c("&a+500000"), null, 70, 1, (short)0));
		p.openInventory(voteshop);
	}
	
	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("voteshop")) {
			openVS((Player)sender);
			
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
		    	if(e.getSlot() == 0) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givebook "+p.getName()+" 5");
		    			removeVotePoints(p, price);
		    			votelog.add(p.getName()+"bought Ares Rank");
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 1) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givebook "+p.getName()+" 6");
		    			removeVotePoints(p, price);
		    			votelog.add(p.getName()+"bought Hermes Rank");
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 2) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givebook "+p.getName()+" 7");
		    			removeVotePoints(p, price);
		    			votelog.add(p.getName()+"bought Apollo Rank");
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 6) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cratekey polis "+p.getName());
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cratekey polis "+p.getName());
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cratekey polis "+p.getName());
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cratekey polis "+p.getName());
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cratekey polis "+p.getName());
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 7) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cratekey oblivion "+p.getName());
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 8) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cratekey olympus "+p.getName());
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 11) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost XP "+p.getName()+ " 2 3600");
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 13) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			removeVotePoints(p, price);
		    			votelog.add(p.getName()+"+1 PlotMine");
		    			p.sendMessage(m.c("&cBought &a+1PlotMine."));
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 15) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost Sell "+p.getName()+ " 2.0 3600");
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 19) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost XP "+p.getName()+ " 2 7200");
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 22) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "renamepaper "+p.getName());
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 25) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost Sell "+p.getName()+ " 3.0 3600");
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 27) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+p.getName()+" tag.Olympus");
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 28) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+p.getName()+" tag.Titan");
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 29) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(0));
		    		if(getVotePoints(p)>=price) {
		    			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddp "+p.getName()+" tag.Gaia");
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 33) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 34) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	if(e.getSlot() == 35) {
		    		int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(1));
		    		if(getVotePoints(p)>=price) {
		    			removeVotePoints(p, price);
		    		} else {
		    			p.sendMessage(m.c("&cError: Not Enough Points"));
		    			p.closeInventory();
		    			return;
		    		}
		    	}
		    	settings.saveVote();
		    	openVS(p);
		    	
		    }
	  }
	  
	  
	  public static void addVotePoint(Player p) {
		  int vps = settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votepoints");
		  int newvps = vps + 1;
		  
		  settings.getVote().set(String.valueOf(p.getUniqueId().toString()) + ".Votepoints", Integer.valueOf(newvps));
		  settings.saveVote();
	  }
	  
	  public int getVotePoints(Player p) {
		  if(!settings.getVote().contains(p.getUniqueId().toString())) 
			  return 0;
		  
		  return settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votepoints");
	  }
	  public void removeVotePoints(Player p, int i) {
		  int vps = settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votepoints");
		  int newvps = vps - i;
		  
		  settings.getVote().set(String.valueOf(p.getUniqueId().toString()) + ".Votepoints", Integer.valueOf(newvps));
		  settings.saveVote();
	  }
	  
	
	

}
