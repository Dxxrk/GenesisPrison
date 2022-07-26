package me.dxrk.Events;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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

	public int getPrestiges(Player p){
		int prestiges = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Prestiges");
		return prestiges;
	}


  public void setMulti(Player p, double d) {
	  this.settings.getMultiplier().set(p.getUniqueId().toString(), d);
  }


	private static Methods m = Methods.getInstance();

  public void sellEnchant(Player p, List<ItemStack> items, String enchantName) {
	    double total = 0.0D;
	    int amountotal = 0;
	    double greed = Functions.greed(p);
	    double sell = Functions.sellBoost(p);
	    double miningboost = BoostsHandler.sell;
	    double multi = SellHandler.getInstance().getMulti(p);
		double prestige = getPrestiges(p)*0.02;
		double multiply = 1;
		if(prestige < 1){
			prestige = 1;
		}
		if(Functions.multiply.contains(p)){
			multiply = 2;
		}
	    for (ItemStack i : items) {
	  	      if (i != null) {


				//Change this config file to look a lot nicer + add different block prices
	  	    	double price = Methods.getBlockSellPrice("A", i.getTypeId());


	  	    	total += price * (multi+greed) * sell * miningboost*prestige*multiply;

	  	        amountotal += i.getAmount();

	  	      }
	  	    }
	    p.updateInventory();
	    if(SettingsManager.getInstance().getOptions().getBoolean(p.getUniqueId().toString()+"."+enchantName+"-Messages") == true) {
	    	p.sendMessage(c("&f&l"+enchantName+" &8| &b+$"+format(total*amountotal)));
	    }

	    Main.econ.depositPlayer(p, total*amountotal);
	  }



  public void sell(Player p, List<ItemStack> items) {
	  double total = 0.0D;
	  int amountotal = 0;
	  double greed = Functions.greed(p);
	  double sell = Functions.sellBoost(p);
	  double miningboost = BoostsHandler.sell;
	  double multi = SellHandler.getInstance().getMulti(p);
	  double prestige = getPrestiges(p)*0.02;
	  double multiply = 1;
	  if(prestige < 1){
		  prestige = 1;
	  }
	  if(Functions.multiply.contains(p)){
		  multiply = 2;
	  }
	  for (ItemStack i : items) {
		  if (i != null) {


			  //Change this config file to look a lot nicer + add different block prices
			  double price = Methods.getBlockSellPrice("A", i.getTypeId());


			  total += price * (multi+greed) * sell * miningboost*prestige*multiply;

			  amountotal += i.getAmount();

		  }
	  }

	  p.updateInventory();


	  Main.econ.depositPlayer(p, total*amountotal);
	  double percents;
	  p.getScoreboard().getTeam("balance").setSuffix(c("&a" + Main.formatAmt(Tokens.getInstance().getBalance(p))));
	  percents = Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) * 100.0D;
	  double dmultiply = percents * 10.0D;
	  double dRound = Math.round(dmultiply) / 10.0D;
	  if (dRound >= 100.0D) {
		  p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
	  } else {
		  p.getScoreboard().getTeam("percent").setSuffix(c("&c") + dRound + "%");
	  }
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
        p.getItemInHand().getType() == Material.DIAMOND_PICKAXE || p.getItemInHand().getType() == Material.WOOD_PICKAXE
	  || p.getItemInHand().getType() == Material.STONE_PICKAXE || p.getItemInHand().getType() == Material.GOLD_PICKAXE
	  || p.getItemInHand().getType() == Material.IRON_PICKAXE)
        event.setCancelled(true);
      return;
    }

    double fortuity = Functions.Foruity(p);
    
    
    
    
    if (!event.isCancelled())
      if (p.getItemInHand() != null) {

		  int line = 0;
		  for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
			  if(ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
				  line = x;
			  }
		  }
          int fortune = (int) (getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity /
            (3.5));

          


		  ArrayList<ItemStack> sellblocks = new ArrayList<>();

		  sellblocks.add(new ItemStack(event.getBlock().getType(), fortune));
		  event.getBlock().setType(Material.AIR);
		  event.setCancelled(true);

		  sell(p, sellblocks);

      }
  }
  


  
  
  
  
  

  
  
  private ArrayList<String> reset = new ArrayList<>();
  
  

  
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
					 p.teleport(mine.getSpawnLocation());
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
