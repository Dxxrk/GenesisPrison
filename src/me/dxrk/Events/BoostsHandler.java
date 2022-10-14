package me.dxrk.Events;

import java.awt.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import me.dxrk.Discord.jdaHandler;
import net.dv8tion.jda.api.entities.TextChannel;

public class BoostsHandler implements Listener, CommandExecutor{
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	 public static BoostsHandler instance = new BoostsHandler();
	  
	  public static BoostsHandler getInstance() {
	    return instance;
	  }
	
	  SettingsManager settings = SettingsManager.getInstance();
	  
	  
	  
	
	public static boolean sactive = false;
	public static boolean xactive = false;
	public static double sell = 1;
	public static int xp = 1;
	public static String sacname = "";
	public static String xacname = "";
	
	public static ArrayList<String> nextUpsell = new ArrayList<>();
	public static ArrayList<String> nextUpxp = new ArrayList<>();
	
	public static String timeFormat(int i) {
		Date d = new Date(i * 1000L);
		SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
		df.setTimeZone(TimeZone.getTimeZone("GMT"));
		return df.format(d);
	  }
	
	public static int toSeconds(String s) {
		String[] format = ChatColor.stripColor(s).split(":");
		int hour = Integer.parseInt(format[0]);
		int mins = Integer.parseInt(format[1]);
		int seconds = Integer.parseInt(format[2]);
		return (hour*3600) + (mins*60) + seconds;
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
	
	public ArrayList<ItemStack> QueueSell(ArrayList<String> array) {
		ArrayList<ItemStack> queue = new ArrayList<>();
		for (String s : array) {
			String[] split = s.split(" ");
			ItemStack item = new ItemStack(Material.POTION, 1, (short) 8260);
			ItemMeta im = item.getItemMeta();
			im.addItemFlags(ItemFlag.values());
			im.setDisplayName(c("&f&l" + split[3] + "x Sell Boost"));
			List<String> lore = new ArrayList<>();
			lore.add(c("&dActivated By: &f" + split[2]));
			lore.add(c("&d" + timeFormat(Integer.parseInt(split[4]))));
			im.setLore(lore);
			item.setItemMeta(im);
			queue.add(item);

		}
		
		return queue;
	}
	
	public ArrayList<ItemStack> QueueXP(ArrayList<String> array) {
		ArrayList<ItemStack> queue = new ArrayList<>();
		for (String s : array) {
			String[] split = s.split(" ");
			ItemStack item = new ItemStack(Material.EXP_BOTTLE);
			ItemMeta im = item.getItemMeta();
			im.addItemFlags(ItemFlag.values());
			im.setDisplayName(c("&f&l" + split[3] + "x XP Boost"));
			List<String> lore = new ArrayList<>();
			lore.add(c("&dActivated By: &f" + split[2]));
			lore.add(c("&d" + timeFormat(Integer.parseInt(split[4]))));
			im.setLore(lore);
			item.setItemMeta(im);
			queue.add(item);
		}
		
		return queue;
	}
	
	
	private Inventory boostPublic() {
		Inventory i = Bukkit.createInventory(null, 45, c("&3&lBoost Queue"));
		
		for(int x = 0; x <45; x++) {
			i.setItem(x, Spacer());
		}
		
		
		
		
		ItemStack emptySell = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta esm = emptySell.getItemMeta();
		esm.setDisplayName(c("&7&lEmpty"));
		esm.addItemFlags(ItemFlag.values());
		emptySell.setItemMeta(esm);
		
		ItemStack emptyXP = new ItemStack(Material.GLASS_BOTTLE);
		ItemMeta exm = emptyXP.getItemMeta();
		exm.setDisplayName(c("&7&lEmpty"));
		exm.addItemFlags(ItemFlag.values());
		emptyXP.setItemMeta(exm);
		
		
		
		if(QueueSell(nextUpsell).size() >0 && QueueSell(nextUpsell).get(0) != null) {
			i.setItem(11, QueueSell(nextUpsell).get(0));
		} else {
			i.setItem(11, emptySell);
		}
		
		if(QueueSell(nextUpsell).size() >1 && QueueSell(nextUpsell).get(1) != null) {
			i.setItem(12, QueueSell(nextUpsell).get(1));
		} else {
			i.setItem(12, emptySell);
		}
		
		if(QueueSell(nextUpsell).size() >2 && QueueSell(nextUpsell).get(2) != null) {
			i.setItem(13, QueueSell(nextUpsell).get(2));
		} else {
			i.setItem(13, emptySell);
		}
		if(QueueSell(nextUpsell).size() >3 && QueueSell(nextUpsell).get(3) != null) {
			i.setItem(14, QueueSell(nextUpsell).get(3));
			} else {
				i.setItem(14, emptySell);
			}
		if(QueueSell(nextUpsell).size() >4 && QueueSell(nextUpsell).get(4) != null) {
			i.setItem(15, QueueSell(nextUpsell).get(4));
			} else {
				i.setItem(15, emptySell);
			}
		if(QueueXP(nextUpxp).size() >0 && QueueXP(nextUpxp).get(0) != null) {
			i.setItem(29, QueueXP(nextUpxp).get(0));
		} else {
			i.setItem(29, emptyXP);
		}
		if(QueueXP(nextUpxp).size() >1 && QueueXP(nextUpxp).get(1) != null) {
			i.setItem(30, QueueXP(nextUpxp).get(1));
			} else {
				i.setItem(30, emptyXP);
			}
		if(QueueXP(nextUpxp).size() >2 && QueueXP(nextUpxp).get(2) != null) {
			i.setItem(31, QueueXP(nextUpxp).get(2));
			} else {
				i.setItem(31, emptyXP);
			}
		if(QueueXP(nextUpxp).size() >3 && QueueXP(nextUpxp).get(3) != null) {
			i.setItem(32, QueueXP(nextUpxp).get(3));
			} else {
				i.setItem(32, emptyXP);
			}
		if(QueueXP(nextUpxp).size() >4 && QueueXP(nextUpxp).get(4) != null) {
			i.setItem(33, QueueXP(nextUpxp).get(4));
			} else {
				i.setItem(33, emptyXP);
			}
		
		ItemStack boost = new ItemStack(Material.CHEST);
		ItemMeta bm = boost.getItemMeta();
		bm.setDisplayName(c("&d&lOpen your Boosts"));
		boost.setItemMeta(bm);
		
		i.setItem(8, boost);
		
		
		return i;
	}
	
	
	public static int selltime = 0;
	public static String stimeLeft = c("&d"+timeFormat(selltime));
	public static int xptime = 0;
	public static String xtimeLeft = c("&d"+timeFormat(xptime));
	
	
	public void sCountdown() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(sactive == false) {
					selltime = 0;
					stimeLeft = c("&d"+timeFormat(0));
					this.cancel();
				}
				if(selltime <= 0) {
					return;
				}
				selltime = selltime - 1;
				stimeLeft = c("&d"+timeFormat(selltime));
				
			}
		}.runTaskTimer(Main.plugin, 0L, 20L);
	}
	public void xCountdown() {
		new BukkitRunnable() {
			@Override
			public void run() {
				if(xactive == false) {
					xptime = 0;
					xtimeLeft = c("&d"+timeFormat(0));
					this.cancel();
				}
				if(xptime <= 0) {
					return;
				}
				xptime = xptime - 1;
				xtimeLeft = c("&d"+timeFormat(xptime));
				
			}
		}.runTaskTimer(Main.plugin, 0L, 20L);
	}
	
	
	public static String sname = c("&a$&f1.0x");
	public static String xname = c("&a✴&f1x");
	
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("activeboost")) {
			if(args.length == 4) {
				if(!cs.hasPermission("rank.admin")) return false;
				if(args[0].equalsIgnoreCase("sell")) {
					double amp = Double.parseDouble(args[2]);
					int dur = Integer.parseInt(args[3]);
					if(sactive == true) {
						if(nextUpsell.size() > 0) {
							String[] first = nextUpsell.get(0).split(" ");
							if(amp > Double.parseDouble(first[3])) {
								ArrayList<String> hold = new ArrayList<>(nextUpsell);
								nextUpsell.clear();
								nextUpsell.add("activeboost sell " + args[1]+" "+ amp+" "+dur);
								nextUpsell.addAll(hold);
							} else {
								nextUpsell.add("activeboost sell " + args[1]+" "+ amp+" "+dur);
							}
						} else {
						nextUpsell.add("activeboost sell " + args[1]+" "+ amp+" "+dur);
						}
						return false;
					}
					
					
					
					sactive  = true;
					
					sell = amp;
					
					selltime = dur;
					
					sacname = args[1];
					sCountdown();
					
					settings.getBoost().set("ActiveSell.Amp", amp);
					settings.getBoost().set("ActiveSell.Duration", dur);
					
					settings.getBoost().set("ActivatorSell", args[1]);
					
					settings.saveboosts();
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &e&l"+ args[1]+" &dhas activated a "+amp+"x Sell Boost!");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &7Length: &b"+timeFormat(dur));
					
					TextChannel channel = jdaHandler.jda.getTextChannelById("1003031504278016051");

					EmbedBuilder b = new EmbedBuilder();
					b.setTitle("__"+args[1]+" Activated a Sell Boost__");
					b.addField("Multiplier: "+amp+"x", "Length: "+timeFormat(dur), false);
					b.setColor(Color.BLUE);

					assert channel != null;
					channel.sendMessageEmbeds(b.build()).queue();
					
					sname = c("&a$&d"+amp+"x");
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
						if(sactive == true) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &f&l"+args[1]+"'s &d&l"+amp+"x Sell Boost has ended!");
							}
						sactive = false;
						sname = c("&a$&f1.0x");
						selltime = 0;
						stimeLeft = c("&d"+timeFormat(0));
						sell = 1;

						settings.getBoost().set("ActiveSell.Amp", 0);
						settings.getBoost().set("ActiveSell.Duration", 0);

						settings.getBoost().set("ActivatorSell", "");
						settings.saveboosts();
						if(!nextUpsell.isEmpty()) {
							String next = nextUpsell.get(0);
							nextUpsell.remove(0);

							new BukkitRunnable() {@Override public void run() { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), next); } }.runTaskLater(Main.plugin, 40L);
						}

					}, dur*20L);
					
				}
				if(args[0].equalsIgnoreCase("XP")) {
					int amp = Integer.parseInt(args[2]);
					int dur = Integer.parseInt(args[3]);
					if(xactive == true) {
						if(nextUpxp.size() > 0) {
							String[] first = nextUpxp.get(0).split(" ");
							if(amp > Integer.parseInt(first[3])) {
								ArrayList<String> hold = new ArrayList<>(nextUpxp);
								nextUpxp.clear();
								nextUpxp.add("activeboost XP " + args[1]+" "+ amp+" "+dur);
								nextUpxp.addAll(hold);
							} else {
								nextUpxp.add("activeboost XP " + args[1]+" "+ amp+" "+dur);
							}
						} else {
						nextUpxp.add("activeboost XP " + args[1]+" "+ amp+" "+dur);
						}
						return false;
					}
					
					
					
					xactive  = true;
					
					xp = amp;
					
					xptime = dur;
					xacname = args[1];
					xCountdown();
					
					settings.getBoost().set("ActiveXP.Amp", amp);
					settings.getBoost().set("ActiveXP.Duration", dur);
					
					settings.getBoost().set("ActivatorXP", args[1]);
					
					settings.saveboosts();
					
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &e&l"+ args[1]+" &dhas activated a "+amp+"x XP Boost!");
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &7Length: &b"+timeFormat(dur));
					
					TextChannel channel = jdaHandler.jda.getTextChannelById("1003031504278016051");
					EmbedBuilder b = new EmbedBuilder();
					b.setTitle("__"+args[1]+" Activated an XP Boost__");
					b.addField("Multiplier: "+amp+"x", "Length: "+timeFormat(dur), false);
					b.setColor(Color.GREEN);

					assert channel != null;
					channel.sendMessageEmbeds(b.build()).queue();
					
					xname = c("&a✴&e"+amp+"x");
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
						if(xactive == true) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &f&l"+args[1]+"'s &d&l"+amp+"x XP Boost has ended!");
							}
						xactive = false;
						xname = c("&a✴&f1x");
						xptime = 0;
						xtimeLeft = c("&d"+timeFormat(0));
						xp = 1;

						settings.getBoost().set("ActiveXP.Amp", 0);
						settings.getBoost().set("ActiveXP.Duration", 0);

						settings.getBoost().set("ActivatorXP", "");
						settings.saveboosts();
						if(!nextUpxp.isEmpty()) {
							String next = nextUpxp.get(0);
							nextUpxp.remove(0);


							new BukkitRunnable() {@Override public void run() { Bukkit.dispatchCommand(Bukkit.getConsoleSender(), next); } }.runTaskLater(Main.plugin, 40L);
						}

					}, dur*20L);
					
				}
			
		}
		}
		
		if(cmd.getName().equalsIgnoreCase("giveboost")) {
			
			
			if(args.length == 4) {
				Player p = Bukkit.getPlayer(args[1]);
				
				if(args[0].equalsIgnoreCase("sell")) {
					double amp = Double.parseDouble(args[2]);
					int dur = Integer.parseInt(args[3]);
					
					boostsinv.get(p).add(BoostSell("&f&l"+amp+"x Sell Boost", "&d"+timeFormat(dur)));
				
				}
				if(args[0].equalsIgnoreCase("XP")) {
					int amp = Integer.parseInt(args[2]);
					int dur = Integer.parseInt(args[3]);
					boostsinv.get(p).add(BoostXP("&f&l"+amp+"x XP Boost", "&d"+timeFormat(dur)));
				
				}
			}
		}
		if(cmd.getName().equalsIgnoreCase("boost")) {
			if(cs instanceof Player) {
				Player p = (Player)cs;
				p.openInventory(boostPublic());
			}
		}
		
		
		
		return false;
	}
	
	
	
	
	
	public static HashMap<Player, ArrayList<ItemStack>> boostsinv = new HashMap<>();
	
	
	
	public void saveBinv(Player p) {
	    try {
	      ArrayList<ItemStack> i = boostsinv.get(p);
	      
	      for (int x = 0; x < i.size(); x++) {
	        ItemStack item = i.get(x);
	        this.settings.getBoost().set(p.getUniqueId().toString() + "." + x, item);
	      }
	      
	      this.settings.saveboosts();
	    } catch (Exception ignored) {}
	  }
	  
	  public void openBoost(Player p) {
	    Inventory i = Bukkit.createInventory(null, 45, c("&7"+p.getName()+"'s Boosts"));
	    ArrayList<ItemStack> bp = boostsinv.get(p);
	    
	    for(int x = 0; x < 45; x++) {
	    	try {
	    		ItemStack item = bp.get(x);
	    		i.setItem(x, item);
	    	} catch (Exception ignored) {}
	    }
	    
	    p.openInventory(i);
	  }
	  
	  public void loadBoost(Player p) {
	    ArrayList<ItemStack> boost = new ArrayList<>();
	    if (this.settings.getBoost().contains(p.getUniqueId().toString()))
	      for (int x = 0; x < 2500; x++) {
	        try {
	          ItemStack item = this.settings.getBoost()
	            .getItemStack(p.getUniqueId().toString() + "." + x);
	          if(item != null)
	        	  boost.add(item);
	        } catch (Exception ignored) {}
	      }  
	    	
	    
	    boostsinv.put(p, boost);
	    
	  }
	  
	  
	  
	  @EventHandler
	  public void join(PlayerJoinEvent e) {
	    Player p = e.getPlayer();
	    loadBoost(p); 
	  }
	  
	 
	  
	  @EventHandler
	  public void leave(PlayerQuitEvent e) {
		  
		  saveBinv(e.getPlayer());
		  boostsinv.remove(e.getPlayer());
	  }
	  public boolean isInt(String s) {
	        try {
	            int i = Integer.parseInt(s);
	            return true;
	        }
	        catch (Exception e1) {
	            return false;
	        }
	    }

	    public boolean isInt(char ss) {
	        String s = String.valueOf(ss);
	        try {
	            int i = Integer.parseInt(s);
	            return true;
	        }
	        catch (Exception e1) {
	            return false;
	        }
	    }
	  
	  public int getInt(String s) {
	        StringBuilder lvl = new StringBuilder();
	        s = ChatColor.stripColor(s);
	        char[] arrayOfChar = s.toCharArray();
	        int i = arrayOfChar.length;
	        for (int b = 0; b < i; b = (byte)(b + 1)) {
	            char c = arrayOfChar[b];
	            if (!this.isInt(c)) continue;
	            lvl.append(c);
	        }
	        if (this.isInt(lvl.toString())) {
	            return Integer.parseInt(lvl.toString());
	        }
	        return -1;
	    }
	  
	  
	@EventHandler
	public void BoostPlace(InventoryClickEvent e ) {
		Player p = (Player) e.getWhoClicked();
		
		if (e.getClickedInventory() == null)
		      return; 
		    if (e.getClickedInventory().getName() == null)
		      return; 
		    if(e.getCurrentItem() == null) {
		    	return;
		    }
		    
		    if(e.getInventory().getName().equals(c("&3&lBoost Queue"))) {
		    	e.setCancelled(true);
		    	if(e.getCurrentItem() == null) return;
			    if(e.getCurrentItem().getItemMeta() == null) return;
		    	if(e.getSlot() == 8) {
		    		if(e.getCurrentItem().getType().equals(Material.CHEST)) {
		    			openBoost(p);
		    		}
		    	}
		    }
		    
		
		if(e.getInventory().getName().equals(c("&7"+p.getName()+"'s Boosts"))) {
			if(e.getCurrentItem() == null) return;
		    if(e.getCurrentItem().getItemMeta() == null) return;
			e.setCancelled(true);
		if(e.getCurrentItem().getType().equals(Material.POTION)) {
			
			String [] amps = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("x");
			
			double amp = Double.parseDouble(amps[0]);
			
			
			int dur	= toSeconds(e.getCurrentItem().getItemMeta().getLore().get(0));
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost Sell "+p.getName()+ " "+amp+" "+dur);
			
			ArrayList<ItemStack> b = boostsinv.get(p);
			if(e.getSlot() == 0) {
				b.remove(0);
			} else {
				b.remove(e.getSlot());
			}
			
			
			boostsinv.put(p, b);
			 
			openBoost(p);
		}else if(e.getCurrentItem().getType().equals(Material.EXP_BOTTLE)) {
			String [] amps = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("x");
			
			
			int amp = Integer.parseInt(amps[0]);
			
			
			
			
			int dur	= toSeconds(e.getCurrentItem().getItemMeta().getLore().get(0));
			
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost XP "+p.getName()+ " "+amp+" "+dur);
			
			ArrayList<ItemStack> b = boostsinv.get(p);
			b.remove(e.getSlot());
			
			boostsinv.put(p, b);
			
			openBoost(p);
			
		}
		}
		
	}
	
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		Player p = (Player) e.getPlayer();
		if(e.getInventory().getName().equals(c("&7"+p.getName()+"'s Boosts"))) {
			settings.saveboosts();
		}
	}
	
	
	 @EventHandler
	    public void setMaxStackSize(net.minecraft.server.v1_8_R3.Item getitem, int i){
	        try {
	        Field field = net.minecraft.server.v1_8_R3.Item.class.getDeclaredField("maxStackSize");
	        field.setAccessible(true);
	        field.setInt(getitem, i);
	        } catch (Exception ignored) {}}
	
	 
	public ItemStack BoostSell(String name, String length) {
		ItemStack i = new ItemStack(Material.POTION, 1, (short)8260);
		ItemMeta im = i.getItemMeta();
		im.addItemFlags(ItemFlag.values());
		im.setDisplayName(c(name));
		List<String> lores = new ArrayList<>();
		lores.add(c(length));
		im.setLore(lores);
	    im.addEnchant(Enchantment.DURABILITY, 0, false);
		i.setItemMeta(im);
		i.removeEnchantment(Enchantment.DURABILITY);
		lores.clear();
		net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(i);
		net.minecraft.server.v1_8_R3.Item getitem = i2.getItem();
		setMaxStackSize(getitem, 1);
		return i;
	}
	public ItemStack BoostXP(String name, String length) {
		ItemStack i = new ItemStack(Material.EXP_BOTTLE);
		ItemMeta im = i.getItemMeta();
		im.addItemFlags(ItemFlag.values());
		im.setDisplayName(c(name));
		List<String> lores = new ArrayList<>();
		lores.add(c(length));
		im.setLore(lores);
	    im.addEnchant(Enchantment.DURABILITY, 0, false);
	    im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		i.setItemMeta(im);
		lores.clear();
		net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(i);
		net.minecraft.server.v1_8_R3.Item getitem = i2.getItem();
		setMaxStackSize(getitem, 1);
		return i;
	}
	
	
	
	
	
	

}
