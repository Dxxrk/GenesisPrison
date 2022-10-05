package me.dxrk.Events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.dxrk.Enchants.EnchantMethods;
import me.dxrk.Events.ResetHandler.ResetReason;
import me.dxrk.Main.Functions;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import me.jet315.prisonmines.mine.Mine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.*;

@SuppressWarnings("deprecation")
public class SellHandler implements Listener, CommandExecutor {
  SettingsManager settings = SettingsManager.getInstance();
  
  Methods methods = Methods.getInstance();

  public static double servermulti = 1.0D;
  
  public static HashMap<Player, Double> pickmulti = new HashMap<>();

  
  public static SellHandler instance = new SellHandler();
  
  public static SellHandler getInstance() {
    return instance;
  }
  static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  
  
  public static String format(double amt) {
	  if (amt >= 1.0E18D)
	      return String.format("%.1f Quint", amt / 1.0E18D); 
    if (amt >= 1.0E15D)
      return String.format("%.1f Quad", amt / 1.0E15D); 
    if (amt >= 1.0E12D)
      return String.format("%.1f Tril", amt / 1.0E12D); 
    if (amt >= 1.0E9D)
      return String.format("%.1f Bil", amt / 1.0E9D); 
    if (amt >= 1000000.0D)
      return String.format("%.1f Mil", amt / 1000000.0D); 
    return NumberFormat.getNumberInstance(Locale.US).format(amt);
  }
  

  
  
  
  public static int getBPEmptySlots(Player p) {
	  SettingsManager.getInstance().getbpSize();
        return SettingsManager.getInstance().getbpSize().getInt(p.getUniqueId().toString())*9;
  }
  
  
  
  
  
  public double getMulti(Player p) {
      double d = SettingsManager.getInstance().getMultiplier().getDouble(p.getUniqueId().toString());
      
      if (d < 1.0) {
          d = 1.0;
      }
      double dround = d*10.0;
	  double drounded = Math.round(dround) /10.0;
	  if(p.hasPermission("rank.zeus")) {
		  drounded += 10;
	  } else if(p.hasPermission("rank.kronos")) {
		  drounded += 9;
	  } else if(p.hasPermission("rank.apollo")) {
		  drounded += 8;
	  } else if(p.hasPermission("rank.hermes")) {
		  drounded += 7;
	  } else if(p.hasPermission("rank.ares")) {
		  drounded += 6;
	  } else if(p.hasPermission("rank.colonel")) {
		  drounded += 5;
	  } else if(p.hasPermission("rank.captain")) {
		  drounded += 4;
	  } else if(p.hasPermission("rank.hoplite")) {
		  drounded += 3;
	  } else if(p.hasPermission("rank.cavalry")) {
		  drounded += 2;
	  }
	  
	  return drounded;
  }

  public boolean isDbl(String s) {
	    try {
	      Double.parseDouble(s);
	      return true;
	    } catch (NumberFormatException e) {
	      return false;
	    } 
	  }
  
  
  
  

  






  @EventHandler
  public void invcl(InventoryClickEvent e) {
    if (e.getInventory().getName().equals(ChatColor.RED + "Investments"))
      e.setCancelled(true);
  }




  public void setMulti(Player p, double d) {
	  this.settings.getMultiplier().set(p.getUniqueId().toString(), d);
  }


	private static Methods m = Methods.getInstance();

  public static void sellEnchant(Player p, List<ItemStack> items, String enchantName) {
	    double total = 0.0D;
	    int amountotal = 0;
	    double greed = Functions.greed(p);
	    double sell = Functions.sellBoost(p);
	    double miningboost = BoostsHandler.sell;
	    double multi = SellHandler.getInstance().getMulti(p);
	    for (ItemStack i : items) {
	  	      if (i != null) {


				//Change this config file to look a lot nicer + add different block prices
	  	    	double price = Methods.getBlockSellPrice("A", i.getTypeId());


	  	    	total += price * (multi+greed) * sell * miningboost;

	  	        amountotal += i.getAmount();

	  	      }
	  	    }
	    p.updateInventory();
	    if(SettingsManager.getInstance().getOptions().getBoolean(p.getUniqueId().toString()+"."+enchantName+"-Messages") == true) {
	    	p.sendMessage(c("&f&l"+enchantName+" &8| &b+$"+format(total*amountotal)));
	    }
	    if(EnchantMethods.stakemap.containsKey(p)) {
  	    	double b = EnchantMethods.stakemap.get(p);
  	    	EnchantMethods.stakemap.put(p, b+(total*amountotal));
  	    }
	    Main.econ.depositPlayer(p, total*amountotal);
	  }



  public static void sell(Player p, List<ItemStack> items) {
  	    double total = 0.0D;
  	  double greed = Functions.greed(p);
  	double sell = Functions.sellBoost(p);
  	double miningboost = BoostsHandler.sell;



  	    double multi = SellHandler.getInstance().getMulti(p);
  	  for (ItemStack i : items) {
  	      if (i != null) {



  	    	double price = Methods.getBlockSellPrice("A", i.getTypeId());


  	     total += price * (multi+greed)* i.getAmount() * sell * miningboost;

  	        i.getAmount();

  	      }
  	    }
  	    p.updateInventory();
  	  if(EnchantMethods.stakemap.containsKey(p)) {
	    	double b = EnchantMethods.stakemap.get(p);
	    	EnchantMethods.stakemap.put(p, b+(total));
	    }
  	    Main.econ.depositPlayer(p, total);
  	  }











  public boolean isInt(String s) {
	    try {
	      Integer.parseInt(s);
	      return true;
	    } catch (Exception e1) {
	      return false;
	    }
	  }

	  public boolean isInt(char ss) {
	    String s = String.valueOf(ss);
	    try {
	      Integer.parseInt(s);
	      return true;
	    } catch (Exception e1) {
	      return false;
	    } 
	  }
		  
	  
	  public int getFortune(String s) {
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
	  
	  
	  @EventHandler(priority = EventPriority.HIGHEST)
	  public void onBlockPlace(BlockPlaceEvent e ) {
		  Player p = e.getPlayer();
		  WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		    ApplicableRegionSet set = wg.getRegionManager(p.getWorld())
		      .getApplicableRegions(e.getBlock().getLocation());
		    if (set.allows(DefaultFlag.LIGHTER)) {
		    	e.setCancelled(true);
		    }
	  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onBlockBreak(BlockBreakEvent event) {
	  
    Player p = event.getPlayer();
    WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    ApplicableRegionSet set = wg.getRegionManager(p.getWorld())
      .getApplicableRegions(event.getBlock().getLocation());
    if (!set.allows(DefaultFlag.LIGHTER)) {
      if (p.isOp() && p.getItemInHand() != null && 
        p.getItemInHand().getType() == Material.DIAMOND_PICKAXE)
        event.setCancelled(true); 
      return;
    }

    double fortuity = Functions.Foruity(p);
    
    
    
    
    if (!event.isCancelled())
      if (p.getItemInHand() != null) {
        if (p.getItemInHand().getItemMeta().getLore().get(2).contains("Fortune")) {
          int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(2))*fortuity /
            (3.5));

          
          event.getBlock().setType(Material.AIR);
          event.setCancelled(true);

		  List<ItemStack> drops = new ArrayList<>();
		  ItemStack drop = new ItemStack(event.getBlock().getType(), fortune);
		  drops.add(drop);
		  sell(p, drops);

         
          
          
          
        }
      }
  }
  


  
  
  
  
  

  
  
  private ArrayList<String> reset = new ArrayList<>();
  
  
  public Inventory inv = Bukkit.createInventory(null, 45, c("&cBackpack"));
  
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
	  

	  if(cmd.getName().equalsIgnoreCase("resetmine") || cmd.getName().equalsIgnoreCase("rm")) {
		  if (cs instanceof Player) {
			  Player p = (Player)cs;
			  if(p.hasPermission("mines.resetmine")) {
				 Mine mine = ResetHandler.api.getMineByName(p.getUniqueId().toString());
				 if(reset.contains(p.getUniqueId().toString())) {
					 p.sendMessage(c("&c/resetmine(/rm) is on cooldown."));
				 } else {
					 ResetHandler.resetMine(mine, ResetReason.NORMAL);
					 reset.add(p.getUniqueId().toString());
				 }
				 new BukkitRunnable() {

					@Override
					public void run() {
						reset.remove(p.getUniqueId().toString());
						
					}
					 
				 }.runTaskLater(Main.plugin, 20*60);
			  }
		  }
	  }
	  
	  

    if(cmd.getName().equalsIgnoreCase("multi")) {

    	if(args.length ==0) {
    		if(!(cs instanceof Player)) return false;
    	cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dYour Multi: &b" + getMulti((Player)cs)));
    	} else if(args.length == 1) {
    		if(!cs.hasPermission("rank.admin")) return false;
			if(args[0].equalsIgnoreCase("add")) {
				cs.sendMessage(ChatColor.RED + "Specify a Players Name!");
    		}
    	} else if(args.length ==  2) {
    		if(!cs.hasPermission("rank.admin")) {
    			cs.sendMessage(ChatColor.RED + "No Permission.");
    		return false;
    		}
    		if(args[0].equalsIgnoreCase("add")) {
    			Player reciever = Bukkit.getServer().getPlayer(args[1]);
    			if(!reciever.isOnline()) {
    				cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    			} else {
    				cs.sendMessage(ChatColor.RED + "You must specify an amount");
    			}
    		}
    		if(args[0].equalsIgnoreCase("set")) {
    			Player reciever = Bukkit.getServer().getPlayer(args[1]);
    			if(!reciever.isOnline()) {
    				cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    			} else {
    				cs.sendMessage(ChatColor.RED + "You must specify an amount");
    			}
    		}
    		} else if(args.length == 3) {
    			Player reciever = Bukkit.getServer().getPlayer(args[1]);
    			if(!cs.hasPermission("rank.admin")) return false;
    			if(args[0].equalsIgnoreCase("add") ) {
    				
    				if(isDbl(args[2])) {
    					if (reciever == null) {
    		               cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    		                return false;
    		              } 
    					
    				String Amount = args[2].replace("-", "");
    	               double amnt = Double.parseDouble(Amount);
    	               double d = this.settings.getMultiplier().getDouble(reciever.getUniqueId().toString());
    	               double newMulti = d+amnt;
    	               if(newMulti > Functions.multiCap(reciever)) {
    	            	   newMulti = Functions.multiCap(reciever);
    	               }
    	               
    	               this.settings.getMultiplier().set(reciever.getUniqueId().toString(), newMulti);
    	               this.settings.saveMultiplier();
    					
    	            
    				}
    			}
    			if(args[0].equalsIgnoreCase("set") ) {
    				
    				if(isDbl(args[2])) {
    					if (reciever == null) {
    		               cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    		                return false;
    		              } 
    					
    				String Amount = args[2].replace("-", "");
    	               double amount = Double.parseDouble(Amount);
    	               this.settings.getMultiplier().set(reciever.getUniqueId().toString(), amount);
    	               this.settings.saveMultiplier();
    	            
    				}
    			}
    		}
    	}
     if(cmd.getName().equalsIgnoreCase("withdraw")) {
    		if(!cs.hasPermission("rank.admin")) {
    			cs.sendMessage(ChatColor.RED+ "Withdraw is disabled");
    			return false;
    		}
    		
    	}
     if(cmd.getName().equalsIgnoreCase("forcereset")) {
    	 if(cs.hasPermission("rank.owner")) {
    	 ResetHandler.resetAllMines();
    	 }
     }
    
    return false;
  }
}
