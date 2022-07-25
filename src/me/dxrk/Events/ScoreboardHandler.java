package me.dxrk.Events;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import com.connorlinfoot.titleapi.TitleAPI;
import com.earth2me.essentials.Essentials;

import me.dxrk.Commands.Vanish;
import me.dxrk.Main.Boosts;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.PickaxeLevel;
import me.dxrk.Tokens.Tokens;
import net.ess3.api.events.UserBalanceUpdateEvent;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;

public class ScoreboardHandler implements Listener{
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	
	public static Permission perms = null;
	public static Economy econ = null;
	
	public static int getPlayersOnline() {
	    int x = 0;
	    for (Player p : Bukkit.getOnlinePlayers()) {
	    	if (!Vanish.vanished.contains(p.getUniqueId()))
	        x++; 
	    } 
	    return x;
	  }
	
	public static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	private static String format(double tps)
	  {
	      return ( ( tps > 18.0 ) ? ChatColor.GREEN : ( tps > 16.0 ) ? ChatColor.YELLOW : ChatColor.RED ).toString()
	              + Math.min( Math.round( tps * 100.0 ) / 100.0, 20.0 );
	  }
	  
	  public static ChatColor rainbowSB() {
		  Random r = new Random();
		    int x = r.nextInt(5);
		    if (x == 0)
		      return ChatColor.AQUA; 
		    if (x == 1)
		      return ChatColor.GREEN; 
		    if (x == 2)
		      return ChatColor.RED; 
		    if (x == 3)
		      return ChatColor.YELLOW; 
		    if(x == 4)
		      return ChatColor.LIGHT_PURPLE;
		    if(x == 5)
		      return ChatColor.DARK_AQUA;
		    return ChatColor.WHITE;
		  }
	
	
	  
	  public static ChatColor randomColor() {
		    Random r = new Random();
		    int color = r.nextInt(6);
		    switch (color) {
		      case 0:
		        return ChatColor.AQUA;
		      case 1:
		        return ChatColor.GREEN;
		      case 2:
		        return ChatColor.LIGHT_PURPLE;
		      case 3:
		        return ChatColor.YELLOW;
		      case 4:
		        return ChatColor.RED;
		    } 
		    return ChatColor.WHITE;
		  }
	  
	  
	  
	  
	  
		
		
	    
	    static PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
	    
	    
	    private static Method getHandleMethod;
	    private static Field pingField;
	  
	 
	    
	    public static int getPing(Player player)
	    {
	        try {
	            if (getHandleMethod == null)
	                getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
	            Object craftPlayer = getHandleMethod.invoke(player);
	            if (pingField == null)
	                pingField = craftPlayer.getClass().getDeclaredField("ping");
	            return pingField.getInt(craftPlayer);
	        } catch (Exception e) {
	            return -1;
	        }
	    }
	    
	    public static String name(Player p) {
	    	StringBuilder str = new StringBuilder();
   		 str.append(p.getName());
   		 if(str.length() >14) {
   			 str.setLength(14);
   		 }
   		 return str.toString();
	    	
	    }
	    public static String formatAmt(double amt) {
	    	amt = amt/1000 * 1000;
	    	
	    	
		    if (amt <= 0.0D)
		      return String.valueOf(0); 
		    if (amt >= 1000.0D)
			      return String.format("%.1f K", new Object[] { Double.valueOf(amt / 1000.0D) }).replace(".0 ", "")
			    		  .replace(".1 ", "")
			    		  .replace(".2 ", "")
			    		  .replace(".3 ", "")
			    		  .replace(".4 ", "")
			    		  .replace(".5 ", "")
			    		  .replace(".6 ", "")
			    		  .replace(".7 ", "")
			    		  .replace(".8 ", "")
			    		  .replace(".9 ", "");
		    
		    return NumberFormat.getNumberInstance(Locale.US).format(amt);
		  }
	    
	    
	    public static String prefix(Player p) {
	    	int rank = RankupHandler.getInstance().getRank(p);
	    	if(p.hasPermission("prestige.100")) {
	    		return c("&6O10-"+formatAmt(rank)+" ");
	    	}
	    		
	    	else if(p.hasPermission("prestige.99")) {
	    		return c("&cO9-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.98")) {
	    		return c("&eO8-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.97")) {
	    		return c("&3O7-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.96")) {
	    		return c("&dO6-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.95")) {
	    		return c("&4O5-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.94")) {
	    		return c("&5O4-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.93")) {
	    		return c("&2O3-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.92")) {
	    		return c("&9O2-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.91")) {
	    		return c("&bO1-"+formatAmt(rank)+" ");
	    	}
	    	else if(p.hasPermission("prestige.90")) {
	    		return c("&6E10-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.89")) {
	    		return c("&cE9-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.88")) {
	    		return c("&eE8-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.87")) {
	    		return c("&3E7-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.86")) {
	    		return c("&dE6-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.85")) {
	    		return c("&4E5-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.84")) {
	    		return c("&5E4-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.83")) {
	    		return c("&2E3-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.82")) {
	    		return c("&9E2-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.81")) {
	    		return c("&bE1-"+formatAmt(rank)+" ");
	    	}
	    	else if(p.hasPermission("prestige.80")) {
	    		return c("&6M10-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.79")) {
	    		return c("&cM9-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.78")) {
	    		return c("&eM8-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.77")) {
	    		return c("&3M7-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.76")) {
	    		return c("&dM6-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.75")) {
	    		return c("&4M5-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.74")) {
	    		return c("&5M4-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.73")) {
	    		return c("&2M3-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.72")) {
	    		return c("&9M2-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.71")) {
	    		return c("&bM1-"+formatAmt(rank)+" ");
	    	}
	    	else if(p.hasPermission("prestige.70")) {
	    		return c("&6F10-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.69")) {
	    		return c("&cF9-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.68")) {
	    		return c("&eF8-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.67")) {
	    		return c("&3F7-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.66")) {
	    		return c("&dF6-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.65")) {
	    		return c("&4F5-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.64")) {
	    		return c("&5F4-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.63")) {
	    		return c("&2F3-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.62")) {
	    		return c("&9F2-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.61")) {
	    		return c("&bF1-"+formatAmt(rank)+" ");
	    	}
	    	else if(p.hasPermission("prestige.60")) {
	    		return c("&6L10-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.59")) {
	    		return c("&cL9-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.58")) {
	    		return c("&eL8-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.57")) {
	    		return c("&3L7-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.56")) {
	    		return c("&dL6-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.55")) {
	    		return c("&4L5-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.54")) {
	    		return c("&5L4-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.53")) {
	    		return c("&2L3-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.52")) {
	    		return c("&9L2-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.51")) {
	    		return c("&bL1-"+formatAmt(rank)+" ");
	    	}
	    		else if(p.hasPermission("prestige.50")) {
		    		return c("&6C10-"+formatAmt(rank)+" ");
		    	} else if(p.hasPermission("prestige.49")) {
		    		return c("&cC9-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.48")) {
		    		return c("&eC8-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.47")) {
		    		return c("&3C7-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.46")) {
		    		return c("&dC6-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.45")) {
		    		return c("&4C5-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.44")) {
		    		return c("&5C4-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.43")) {
		    		return c("&2C3-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.42")) {
		    		return c("&9C2-"+formatAmt(rank)+" ");
		    	}else if(p.hasPermission("prestige.41")) {
		    		return c("&bC1-"+formatAmt(rank)+" ");
		    	}
		    	else
			    	if(p.hasPermission("prestige.40")) {
			    		return c("&6A10-"+formatAmt(rank)+" ");
			    	} else if(p.hasPermission("prestige.39")) {
			    		return c("&cA9-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.38")) {
			    		return c("&eA8-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.37")) {
			    		return c("&3A7-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.36")) {
			    		return c("&dA6-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.35")) {
			    		return c("&4A5-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.34")) {
			    		return c("&5A4-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.33")) {
			    		return c("&2A3-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.32")) {
			    		return c("&9A2-"+formatAmt(rank)+" ");
			    	}else if(p.hasPermission("prestige.31")) {
			    		return c("&bA1-"+formatAmt(rank)+" ");
			    	}
			    	else
				    	if(p.hasPermission("prestige.30")) {
				    		return c("&6S10-"+formatAmt(rank)+" ");
				    	} else if(p.hasPermission("prestige.29")) {
				    		return c("&cS9-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.28")) {
				    		return c("&eS8-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.27")) {
				    		return c("&3S7-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.26")) {
				    		return c("&dS6-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.25")) {
				    		return c("&4S5-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.24")) {
				    		return c("&5S4-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.23")) {
				    		return c("&2S3-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.22")) {
				    		return c("&9S2-"+formatAmt(rank)+" ");
				    	}else if(p.hasPermission("prestige.21")) {
				    		return c("&bS1-"+formatAmt(rank)+" ");
				    	}
				    	else if(p.hasPermission("prestige.20")) {
	    		return c("&6T10-"+formatAmt(rank)+" ");
	    	} else if(p.hasPermission("prestige.19")) {
	    		return c("&cT9-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.18")) {
	    		return c("&eT8-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.17")) {
	    		return c("&3T7-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.16")) {
	    		return c("&dT6-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.15")) {
	    		return c("&4T5-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.14")) {
	    		return c("&5T4-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.13")) {
	    		return c("&2T3-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.12")) {
	    		return c("&9T2-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.11")) {
	    		return c("&bT1-"+formatAmt(rank)+" ");
	    	}else
	    	if(p.hasPermission("prestige.10")) {
	    		return c("&6P10-"+formatAmt(rank)+" ");
	    	} else if(p.hasPermission("prestige.9")) {
	    		return c("&cP9-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.8")) {
	    		return c("&eP8-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.7")) {
	    		return c("&3P7-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.6")) {
	    		return c("&dP6-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.5")) {
	    		return c("&4P5-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.4")) {
	    		return c("&5P4-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.3")) {
	    		return c("&2P3-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.2")) {
	    		return c("&9P2-"+formatAmt(rank)+" ");
	    	}else if(p.hasPermission("prestige.1")) {
	    		return c("&bP1-"+formatAmt(rank)+" ");
	    	}else if(!p.hasPermission("prestige.1")) {
	    		return c("&a"+formatAmt(rank)+" ");
	    	}
	    	
	    	
	    	
	    	return null;
	    }
	    
	    static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	    
	    public static boolean isAFK(Player p) {
	    	if(ess.getUser(p) != null && ess.getUser(p).isAfk()) {
	    		return true;
	    	}
	    	return false;
	    }
	    
	    
	        @SuppressWarnings("deprecation")
			public static void updateSB(Player p) {
	        	
	        	
		            	
		            	Scoreboard NewBoard = p.getScoreboard();
		            	NewBoard.getTeam("donor").setPrefix(c("&7Rank: "));
		      	        	if (p.getName().equalsIgnoreCase("Dxrk")) {
		      	              
		      	            NewBoard.getTeam("donor").setSuffix(c("&d&lOwner"));
		      	            } else if (p.getName().equalsIgnoreCase("BakonStrip") || p.getName().equalsIgnoreCase("32j")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&5&lManager"));
		      	            } else if (p.hasPermission("rank.builder")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&a&lBuilder"));
		      	            } else if (p.hasPermission("rank.mod")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&9&lMod"));
		      	            } else if (p.hasPermission("rank.zeus")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&f&lZeus"));
		      	            } else if (p.hasPermission("rank.kronos")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&8&lKronos"));
		      	            } else if (p.hasPermission("rank.apollo")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&6&lApollo"));
		      	            } else if (p.hasPermission("rank.hermes")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&a&lHermes"));
		      	            } else if (p.hasPermission("rank.ares")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&c&lAres"));
		      	            } else if (p.hasPermission("rank.colonel")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&6Colonel"));
		      	            } else if (p.hasPermission("rank.captain")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&9Captain"));
		      	            } else if (p.hasPermission("rank.hoplite")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&eHoplite"));
		      	            } else if (p.hasPermission("rank.cavalry")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&cCavalry"));
		      	            } else if (p.hasPermission("rank.citizen")) {
		      	            	NewBoard.getTeam("donor").setSuffix(c("&7Member"));
		      	            }
		            	
		            	
		        	        //Prestige
		      	        	NewBoard.getTeam("prestige").setPrefix(c("&7Prestige: "));
		      	        	if(p.hasPermission("Prestige.100")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lO10         "));
		      	        	}else if(p.hasPermission("Prestige.99")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lO9          "));
		      	        	}else if(p.hasPermission("Prestige.98")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lO8          "));
		      	        	}else if(p.hasPermission("Prestige.97")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lO7          "));
		      	        	}else if(p.hasPermission("Prestige.96")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lO6          "));
		      	        	}else if(p.hasPermission("Prestige.95")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lO5          "));
		      	        	}else if(p.hasPermission("Prestige.94")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lO4          "));
		      	        	}else if(p.hasPermission("Prestige.93")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lO3          "));
		      	        	}else if(p.hasPermission("Prestige.92")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lO2          "));
		      	        	}else if(p.hasPermission("Prestige.91")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lO1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.90")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lE10         "));
		      	        	}else if(p.hasPermission("Prestige.89")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lE9          "));
		      	        	}else if(p.hasPermission("Prestige.88")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lE8          "));
		      	        	}else if(p.hasPermission("Prestige.87")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lE7          "));
		      	        	}else if(p.hasPermission("Prestige.86")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lE6          "));
		      	        	}else if(p.hasPermission("Prestige.85")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lE5          "));
		      	        	}else if(p.hasPermission("Prestige.84")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lE4          "));
		      	        	}else if(p.hasPermission("Prestige.83")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lE3          "));
		      	        	}else if(p.hasPermission("Prestige.82")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lE2          "));
		      	        	}else if(p.hasPermission("Prestige.81")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lE1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.80")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lM10         "));
		      	        	}else if(p.hasPermission("Prestige.79")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lM9          "));
		      	        	}else if(p.hasPermission("Prestige.78")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lM8          "));
		      	        	}else if(p.hasPermission("Prestige.77")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lM7          "));
		      	        	}else if(p.hasPermission("Prestige.76")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lM6          "));
		      	        	}else if(p.hasPermission("Prestige.75")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lM5          "));
		      	        	}else if(p.hasPermission("Prestige.74")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lM4          "));
		      	        	}else if(p.hasPermission("Prestige.73")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lM3          "));
		      	        	}else if(p.hasPermission("Prestige.72")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lM2          "));
		      	        	}else if(p.hasPermission("Prestige.71")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lM1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.70")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lF10         "));
		      	        	}else if(p.hasPermission("Prestige.69")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lF9          "));
		      	        	}else if(p.hasPermission("Prestige.68")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lF8          "));
		      	        	}else if(p.hasPermission("Prestige.67")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lF7          "));
		      	        	}else if(p.hasPermission("Prestige.66")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lF6          "));
		      	        	}else if(p.hasPermission("Prestige.65")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lF5          "));
		      	        	}else if(p.hasPermission("Prestige.64")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lF4          "));
		      	        	}else if(p.hasPermission("Prestige.63")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lF3          "));
		      	        	}else if(p.hasPermission("Prestige.62")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lF2          "));
		      	        	}else if(p.hasPermission("Prestige.61")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lF1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.60")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lL10         "));
		      	        	}else if(p.hasPermission("Prestige.59")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lL9          "));
		      	        	}else if(p.hasPermission("Prestige.58")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lL8          "));
		      	        	}else if(p.hasPermission("Prestige.57")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lL7          "));
		      	        	}else if(p.hasPermission("Prestige.56")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lL6          "));
		      	        	}else if(p.hasPermission("Prestige.55")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lL5          "));
		      	        	}else if(p.hasPermission("Prestige.54")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lL4          "));
		      	        	}else if(p.hasPermission("Prestige.53")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lL3          "));
		      	        	}else if(p.hasPermission("Prestige.52")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lL2          "));
		      	        	}else if(p.hasPermission("Prestige.51")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lL1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.50")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lC10         "));
		      	        	} else if(p.hasPermission("Prestige.49")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lC9          "));
		      	        	}else if(p.hasPermission("Prestige.48")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lC8          "));
		      	        	}else if(p.hasPermission("Prestige.47")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lC7          "));
		      	        	}else if(p.hasPermission("Prestige.46")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lC6          "));
		      	        	}else if(p.hasPermission("Prestige.45")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lC5          "));
		      	        	}else if(p.hasPermission("Prestige.44")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lC4          "));
		      	        	}else if(p.hasPermission("Prestige.43")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lC3          "));
		      	        	}else if(p.hasPermission("Prestige.42")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lC2          "));
		      	        	}else if(p.hasPermission("Prestige.41")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lC1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.40")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lA10         "));
		      	        	} else if(p.hasPermission("Prestige.39")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lA9          "));
		      	        	}else if(p.hasPermission("Prestige.38")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lA8          "));
		      	        	}else if(p.hasPermission("Prestige.37")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lA7          "));
		      	        	}else if(p.hasPermission("Prestige.36")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lA6          "));
		      	        	}else if(p.hasPermission("Prestige.35")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lA5          "));
		      	        	}else if(p.hasPermission("Prestige.34")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lA4          "));
		      	        	}else if(p.hasPermission("Prestige.33")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lA3          "));
		      	        	}else if(p.hasPermission("Prestige.32")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lA2          "));
		      	        	}else if(p.hasPermission("Prestige.31")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lA1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.30")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lS10         "));
		      	        	} else if(p.hasPermission("Prestige.29")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lS9          "));
		      	        	}else if(p.hasPermission("Prestige.28")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lS8          "));
		      	        	}else if(p.hasPermission("Prestige.27")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lS7          "));
		      	        	}else if(p.hasPermission("Prestige.26")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lS6          "));
		      	        	}else if(p.hasPermission("Prestige.25")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lS5          "));
		      	        	}else if(p.hasPermission("Prestige.24")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lS4          "));
		      	        	}else if(p.hasPermission("Prestige.23")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lS3          "));
		      	        	}else if(p.hasPermission("Prestige.22")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lS2          "));
		      	        	}else if(p.hasPermission("Prestige.21")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lS1          "));
		      	        	}
		      	        	else if(p.hasPermission("Prestige.20")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&lT10         "));
		      	        	} else if(p.hasPermission("Prestige.19")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lT9          "));
		      	        	}else if(p.hasPermission("Prestige.18")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lT8          "));
		      	        	}else if(p.hasPermission("Prestige.17")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lT7          "));
		      	        	}else if(p.hasPermission("Prestige.16")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lT6          "));
		      	        	}else if(p.hasPermission("Prestige.15")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lT5          "));
		      	        	}else if(p.hasPermission("Prestige.14")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lT4          "));
		      	        	}else if(p.hasPermission("Prestige.13")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lT3          "));
		      	        	}else if(p.hasPermission("Prestige.12")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lT2          "));
		      	        	}else if(p.hasPermission("Prestige.11")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lT1          "));
		      	        	}else if(p.hasPermission("Prestige.10")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&6&l10          "));
		      	        	} else if(p.hasPermission("Prestige.9")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&c&l9           "));
		      	        	}else if(p.hasPermission("Prestige.8")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&e&l8           "));
		      	        	}else if(p.hasPermission("Prestige.7")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&3&l7           "));
		      	        	}else if(p.hasPermission("Prestige.6")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&d&l6           "));
		      	        	}else if(p.hasPermission("Prestige.5")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&4&l5           "));
		      	        	}else if(p.hasPermission("Prestige.4")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&5&l4           "));
		      	        	}else if(p.hasPermission("Prestige.3")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&2&l3           "));
		      	        	}else if(p.hasPermission("Prestige.2")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&9&l2           "));
		      	        	}else if(p.hasPermission("Prestige.1")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&b&l1           "));
		      	        	}else if(!p.hasPermission("Prestige.1")) {
		      	        		NewBoard.getTeam("prestige").setSuffix(c("&a&l0           "));
		      	        	}
		                    //prisonrank
		        	        NewBoard.getTeam("prank").setPrefix(c("&7Level: "));
		        	        
		        	        //rankup%
		        	        
		        	        NewBoard.getTeam("percent").setPrefix(c("&7Rankup: "));
		        	        
		        	        p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
		        	    	double percents = 0.0;
		        	        p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
		        	        percents = (Double.valueOf(Main.econ.getBalance((OfflinePlayer)p) / RankupHandler.getInstance().rankPrice(p)).doubleValue()*100);
		        	        double dmultiply = percents*10.0;
		        	        double dRound = Math.round(dmultiply) /10.0;
		        	        if(RankupHandler.getInstance().getRank(p) == 100) {
		        	        	p.getScoreboard().getTeam("percent").setSuffix(c("&c/prestige"));
		        	        }
		        	        
		        	        else {
		        	        	if(dRound>=100.0) {
		        	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
		        	        	} else {
		        	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
		        	        }
		        	        }
		        	        p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))));
		        	      
		        	        //balance
		        	        NewBoard.getTeam("balance").setPrefix(c("&7Balance: &a&l$"));
		        	        
		        	        //tokens
		        	        NewBoard.getTeam("tokens").setPrefix(c("&7Tokens: &e⛀"));
		        	        
		        	        //xp
		        	        NewBoard.getTeam("xp").setPrefix(c("&7XP: &b✴"));
		        	        
		        	        
		        	        byte b;
		        	        int i;
		        	        ItemStack[] arrayOfItemStack;
		        	        for (i = (arrayOfItemStack = p.getInventory().getContents()).length, b = 0; b < i; ) {
		        	          ItemStack itemStack = arrayOfItemStack[b];
		        	        	if(itemStack != null && itemStack.getType().equals(Material.DIAMOND_PICKAXE)) {
		        	        		if(itemStack.hasItemMeta()) {
		        	        			if(itemStack.getItemMeta().hasLore()) {
		        	        				String xp = Main.formatAmt(PickXPHandler.getBlocks(itemStack.getItemMeta().getLore().get(1)));
		        	        				NewBoard.getTeam("xp").setSuffix(c("&b"+xp));
		        	        			}
		        	        		}
		        	        	}
		        	        	b = (byte)(b + 1);
		        	        }
		        	        
		        	       
		        	        //multi
		        	        NewBoard.getTeam("multi").setPrefix(c("&7Multi: "));
		        	        SellHandler.getInstance();
							NewBoard.getTeam("multi").setSuffix(c("&b"+SellHandler.getInstance().getMulti(p)));
		        	        
		        	        //tps
		        	        for (double tpss : MinecraftServer.getServer().recentTps ) {
		      		    	  String tp = ( format(tpss));
		      		    	  
		      		    	NewBoard.getTeam("tps").setPrefix(ChatColor.GRAY +"TPS: "+  tp);
		      		      }
		        	        //vote
		        	        int servervotes = settings.getVote().getInt("ServerVotes");
		        	        NewBoard.getTeam("vote").setPrefix(c("&dVoteParty: "));
		        	        NewBoard.getTeam("vote").setSuffix(c("&b"+(30-servervotes)+"/30"));
		        	        
		
		        		            
		        	    	 for(Player pp : Bukkit.getOnlinePlayers()) {
		        	    		 
		        	    		 
		        	    		 
		        	    		 if (pp.getName().equalsIgnoreCase("Dxrk")) {
		        		            	if(NewBoard.getTeam("a"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("a"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("a"+name(pp));
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&f&l"));
		        		            	}
		        		            	team.setSuffix(c(" &d&lOwner"));
		        		            	team.addPlayer(pp);
		        		              
		        		              
		        		              
		        		              
		        		            }  else if (pp.getName().equalsIgnoreCase("BakonStrip")) {
		        		            	if(NewBoard.getTeam("f"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("f"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("f"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&7"));
		        		            	}
		        		            	team.setSuffix(c(" &e⚡&f&lZeus&e⚡"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	  
		        		              
		        		              
		        		              
		        		            } else if (pp.getName().equalsIgnoreCase("32j")) {
		        		            	if(NewBoard.getTeam("b"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("b"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("b"+name(pp));
		        		            	
		        		            	team.setPrefix(prefix(pp)+c("&d&l"));
		        		            	team.setSuffix(c(" &5&lManager"));
		        		            	team.addPlayer(pp);
		        		            	
		        		             
		        		              
		        		            } else if (pp.hasPermission("rank.admin")) {
		        		            	if(NewBoard.getTeam("c"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("c"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("c"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&f&l"));
		        		            	}
		        		            	team.setSuffix(c(" &4&lAdmin"));
		        		            	team.addPlayer(pp);
			        		            	  
			        		              
			        		              
			        		        } else if (pp.hasPermission("rank.builder")) {
			        		        	if(NewBoard.getTeam("e"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("e"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("e"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&2&l"));
		        		            	}
		        		            	team.setSuffix(c(" &a&lBuilder"));
		        		            	team.addPlayer(pp);
			        		        	
		        		            	  
		        		              
		        		              
		        		            } else if (pp.hasPermission("rank.mod")) {
		        		            	if(NewBoard.getTeam("d"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("d"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("d"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&b&l"));
		        		            	}
		        		            	team.setSuffix(c(" &9&lMod"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	 
		        		            }  else if (pp.hasPermission("rank.zeus")) {
		        		            	if(NewBoard.getTeam("f"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("f"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("f"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&7"));
		        		            	}
		        		            	team.setSuffix(c(" &e⚡&f&lZeus&e⚡"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	//&7&l&ki&e&l⚡&7&l&ki&f&lZeus&7&l&ki&e&l⚡&7&l&ki&r
		        		            	 
		        		            } else if (pp.hasPermission("rank.kronos")) {
		        		            	if(NewBoard.getTeam("g"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("g"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("g"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&f"));
		        		            	}
		        		            	team.setSuffix(c(" &8&lKronos"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	//&f&l&ki&8&lK&0&lr&8&lo&0&ln&8&lo&0&ls&f&l&ki&r
		        		            	 
		        		            } else if (pp.hasPermission("rank.apollo")) {
		        		            	if(NewBoard.getTeam("h"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("h"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("h"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&e"));
		        		            	}
		        		            	team.setSuffix(c(" &6&lApollo"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	 
		        		            } else if (pp.hasPermission("rank.hermes")) {
		        		            	if(NewBoard.getTeam("i"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("i"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("i"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&a"));
		        		            	}
		        		            	team.setSuffix(c(" &a&lHermes"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	
		        		            	 
		        		            } else if (pp.hasPermission("rank.ares")) {
		        		            	if(NewBoard.getTeam("j"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("j"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("j"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&c"));
		        		            	}
		        		            	team.setSuffix(c(" &c&lAres"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	
		        		            	 
		        		            } else if (pp.hasPermission("rank.Colonel")) {
		        		            	if(NewBoard.getTeam("k"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("k"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("k"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&b"));
		        		            	}
		        		            	team.setSuffix(c(" &6Colonel"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	
		        		            	 
		        		            } else if (pp.hasPermission("rank.captain")) {
		        		            	if(NewBoard.getTeam("l"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("l"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("l"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&b"));
		        		            	}
		        		            	team.setSuffix(c(" &9Captain"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	
		        		            	 
		        		            } else if (pp.hasPermission("rank.hoplite")) {
		        		            	if(NewBoard.getTeam("m"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("m"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("m"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&b"));
		        		            	}
		        		            	team.setSuffix(c(" &eHoplite"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	
		        		            	 
		        		            } else if (pp.hasPermission("rank.cavalry")) {
		        		            	if(NewBoard.getTeam("n"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("n"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("n"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&b"));
		        		            	}
		        		            	team.setSuffix(c(" &cCavalry"));
		        		            	team.addPlayer(pp);
		        		            	
		        		            	
		        		            	 
		        		            } else {
		        		            	if(NewBoard.getTeam("o"+name(pp)) == null) 
		        		            		NewBoard.registerNewTeam("o"+name(pp));
		        		            	
		        		            	Team team = NewBoard.getTeam("o"+name(pp));
		        		            	
		        		            	if(isAFK(pp)) {
		        		            		team.setPrefix(prefix(pp)+c("&8"));
		        		            	}else {
		        		            	team.setPrefix(prefix(pp)+c("&b"));
		        		            	}
		        		            	team.setSuffix(c(""));
		        		            	team.addPlayer(pp);
		        		              
		        		              
		        		              
		        		            }
		        	    	 }
		        	        
		        	        
	        }
	        
	 
	 
	        

	    public void setSB(Player pp) {
	    	 ScoreboardManager scoreboardManager = Bukkit.getScoreboardManager();
	            Scoreboard NewBoard = scoreboardManager.getNewScoreboard();
	            Objective obj = NewBoard.registerNewObjective("Test", "Dummy");
		        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		        obj.setDisplayName(ChatColor.BLUE + "" + ChatColor.BOLD + "Genesis" + ChatColor.GRAY + "⤲"+ ChatColor.AQUA + ""+ChatColor.BOLD+ "Prison");
		        
		        
		        Team stLine = NewBoard.registerNewTeam("1stLine");
		        stLine.addEntry(ChatColor.BLUE+"");
		        stLine.setPrefix(c(""));
		        stLine.setSuffix(c(""));
		        obj.getScore(ChatColor.BLUE+"").setScore(13);
		        
		        //DonorRank
		        Team donor = NewBoard.registerNewTeam("donor");
		        donor.addEntry(ChatColor.DARK_AQUA+"");
		        obj.getScore(ChatColor.DARK_AQUA+"").setScore(12);
		        //Prestige
		        Team prestige = NewBoard.registerNewTeam("prestige");
		        prestige.addEntry(ChatColor.LIGHT_PURPLE+"");
		        obj.getScore(ChatColor.LIGHT_PURPLE+"").setScore(11);
		        //Prisonrank
		        Team prank = NewBoard.registerNewTeam("prank");
		        prank.addEntry(ChatColor.BLACK+"");
		        obj.getScore(ChatColor.BLACK+"").setScore(10);
		        
		        
		        //2ndLine
		        Team ndLine = NewBoard.registerNewTeam("2ndline");
			      ndLine.addEntry((ChatColor.BLUE +""+ChatColor.BLACK  ));
			      ndLine.setPrefix(c(""));
			      ndLine.setSuffix(c(""));
			     obj.getScore(ChatColor.BLUE +""+ ChatColor.BLACK).setScore(9);
		        //Balance
		        Team balance = NewBoard.registerNewTeam("balance");
		        balance.addEntry(ChatColor.DARK_BLUE+"");
		        obj.getScore(ChatColor.DARK_BLUE+"").setScore(8);
		        //rankup%
		        Team percent = NewBoard.registerNewTeam("percent");
		        percent.addEntry(ChatColor.WHITE+"");
		        obj.getScore(ChatColor.WHITE+"").setScore(7);
		        //Tokens
		        Team tokens = NewBoard.registerNewTeam("tokens");
		        tokens.addEntry(ChatColor.GOLD+"");
		        obj.getScore(ChatColor.GOLD+"").setScore(6);
		        //XP
		        Team xp = NewBoard.registerNewTeam("xp");
		        xp.addEntry(ChatColor.AQUA+"");
		        obj.getScore(ChatColor.AQUA+"").setScore(5);
		        //Multi
		        Team multi = NewBoard.registerNewTeam("multi");
		        multi.addEntry(ChatColor.DARK_RED+"");
		        obj.getScore(ChatColor.DARK_RED+"").setScore(4);
		        //3rdLine
		        Team rdLine = NewBoard.registerNewTeam("3rdLine");
		        rdLine.addEntry(ChatColor.GREEN+"");
		        rdLine.setPrefix(c(""));
			    rdLine.setSuffix(c(""));
		        obj.getScore(ChatColor.GREEN+"").setScore(3);
		        //vote
		        Team vote = NewBoard.registerNewTeam("vote");
		        vote.addEntry(ChatColor.DARK_PURPLE+"");
		        obj.getScore(ChatColor.DARK_PURPLE+"").setScore(2);
		        
		        //tps
		        Team tps = NewBoard.registerNewTeam("tps");
		        tps.addEntry(ChatColor.YELLOW+"");
		        obj.getScore(ChatColor.YELLOW+"").setScore(1);
		        
		        
		        
		        
		        
		        pp.setScoreboard(NewBoard);
	    }
	    
	    
	    
	    @EventHandler
	    public void onJoin(PlayerJoinEvent e ) {
	    	Player p = e.getPlayer();
	    	setSB(p);
	    	Scoreboard NewBoard = p.getScoreboard();
	    	NewBoard.getTeam("donor").setPrefix(c("&7Rank: "));
	        	if (p.getName().equalsIgnoreCase("Dxrk")) {
	              
	            NewBoard.getTeam("donor").setSuffix(c("&d&lOwner"));
	            } else if (p.getName().equalsIgnoreCase("BakonStrip") || p.getName().equalsIgnoreCase("32j")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&5&lManager"));
	            } else if (p.hasPermission("rank.builder")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&a&lBuilder"));
	            } else if (p.hasPermission("rank.mod")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&9&lMod"));
	            } else if (p.hasPermission("rank.zeus")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&f&lZeus"));
	            } else if (p.hasPermission("rank.kronos")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&8&lKronos"));
	            } else if (p.hasPermission("rank.apollo")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&6&lApollo"));
	            } else if (p.hasPermission("rank.hermes")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&a&lHermes"));
	            } else if (p.hasPermission("rank.ares")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&c&lAres"));
	            } else if (p.hasPermission("rank.colonel")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&6Colonel"));
	            } else if (p.hasPermission("rank.captain")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&9Captain"));
	            } else if (p.hasPermission("rank.hoplite")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&eHoplite"));
	            } else if (p.hasPermission("rank.cavalry")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&cCavalry"));
	            } else if (p.hasPermission("rank.citizen")) {
	            	NewBoard.getTeam("donor").setSuffix(c("&7Member"));
	            } 
    	
    	
	        //Prestige
	        	NewBoard.getTeam("prestige").setPrefix(c("&7Prestige: "));
  	        	if(p.hasPermission("Prestige.20")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&6&l1T0         "));
  	        	} else if(p.hasPermission("Prestige.19")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&c&lT9          "));
  	        	}else if(p.hasPermission("Prestige.18")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&e&lT8          "));
  	        	}else if(p.hasPermission("Prestige.17")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&3&lT7          "));
  	        	}else if(p.hasPermission("Prestige.16")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&d&lT6          "));
  	        	}else if(p.hasPermission("Prestige.15")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&4&lT5          "));
  	        	}else if(p.hasPermission("Prestige.14")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&5&lT4          "));
  	        	}else if(p.hasPermission("Prestige.13")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&2&lT3          "));
  	        	}else if(p.hasPermission("Prestige.12")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&9&lT2          "));
  	        	}else if(p.hasPermission("Prestige.11")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&b&lT1          "));
  	        	}else if(!p.hasPermission("Prestige.1")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&a&l0           "));
  	        	}else if(p.hasPermission("Prestige.10")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&6&l10          "));
  	        	} else if(p.hasPermission("Prestige.9")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&c&l9           "));
  	        	}else if(p.hasPermission("Prestige.8")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&e&l8           "));
  	        	}else if(p.hasPermission("Prestige.7")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&3&l7           "));
  	        	}else if(p.hasPermission("Prestige.6")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&d&l6           "));
  	        	}else if(p.hasPermission("Prestige.5")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&4&l5           "));
  	        	}else if(p.hasPermission("Prestige.4")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&5&l4           "));
  	        	}else if(p.hasPermission("Prestige.3")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&2&l3           "));
  	        	}else if(p.hasPermission("Prestige.2")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&9&l2           "));
  	        	}else if(p.hasPermission("Prestige.1")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&b&l1           "));
  	        	}else if(!p.hasPermission("Prestige.1")) {
  	        		NewBoard.getTeam("prestige").setSuffix(c("&a&l0           "));
  	        	}
            //prisonrank
	        NewBoard.getTeam("prank").setPrefix(c("&7Level: "));
	        
	        //rankup%
	        
	        NewBoard.getTeam("percent").setPrefix(c("&7Rankup: "));
	        
	        p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
	    	double percents = 0.0;
	        p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
	        percents = (Double.valueOf(Main.econ.getBalance((OfflinePlayer)p) / RankupHandler.getInstance().rankPrice(p)).doubleValue()*100);
	        double dmultiply = percents*10.0;
	        double dRound = Math.round(dmultiply) /10.0;
	        if(RankupHandler.getInstance().getRank(p) == 100) {
	        	p.getScoreboard().getTeam("percent").setSuffix(c("&c/prestige"));
	        }
	        
	        else {
	        	if(dRound>=100.0) {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
	        	} else {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
	        }
	        }
	        p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))));
	      
	        //balance
	        NewBoard.getTeam("balance").setPrefix(c("&7Balance: &a&l$"));
	        
	        //tokens
	        NewBoard.getTeam("tokens").setPrefix(c("&7Tokens: &e⛀"));
	       
	        //multi
	        NewBoard.getTeam("multi").setPrefix(c("&7Multi: "));
	        SellHandler.getInstance();
			NewBoard.getTeam("multi").setSuffix(c("&b"+SellHandler.getInstance().getMulti(p)));
	        
	        //tps
	        for (double tpss : MinecraftServer.getServer().recentTps ) {
		    	  String tp = ( format(tpss));
		    	  
		    	NewBoard.getTeam("tps").setPrefix(ChatColor.GRAY +"TPS: "+  tp);
		      }
	        //vote
	        int servervotes = settings.getVote().getInt("ServerVotes");
	        NewBoard.getTeam("vote").setPrefix(c("&dVoteParty: "));
	        NewBoard.getTeam("vote").setSuffix(c("&b"+(30-servervotes)+"/30"));
	    	
	        p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))));
	    	
	    	new BukkitRunnable(){
	    		boolean titlechanged = false;
	    		@Override
	    		public void run() {
	    			
	    			int ping = getPing(p);
					
					if(titlechanged == false) {
					
						TitleAPI.sendTabTitle(p, c("&9&lGenesis&b&lMC\n&aConnected to: &9Prison\n\n&dPlayers Online: &c"+getPlayersOnline()+"\n&7Ping: &a"+ping+"\n\n&8&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"), 
								c("&7&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n\n&6Boosts\n"+Boosts.sname+"&7/"+Boosts.xname+"\n"+Boosts.stimeLeft+"&7/"+Boosts.xtimeLeft+"\n"));
					titlechanged = true;
					} else {
						TitleAPI.sendTabTitle(p, c("&9&lGenesis&b&lMC\n&aConnected to: &9Prison\n\n&dPlayers Online: &c"+getPlayersOnline()+"\n&7Ping: &a"+ping+"\n\n&7&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"), 
								c("&8&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n\n&6Boosts\n"+Boosts.sname+"&7/"+Boosts.xname+"\n"+Boosts.stimeLeft+"&7/"+Boosts.xtimeLeft+"\n"));
						titlechanged = false;
					}
					if(!p.isOnline()) {
						this.cancel();
					}
	    		}
	    		
	    		
	    	}.runTaskTimer(Main.plugin, 0, 10L);
	    	
	    	
	    }
	    
	    
	    
	          
	        
	      
	    
	    
	    
	
}
