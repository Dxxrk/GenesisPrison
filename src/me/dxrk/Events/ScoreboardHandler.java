package me.dxrk.Events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.earth2me.essentials.Essentials;
import me.dxrk.Commands.CMDVanish;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.minecraft.server.v1_8_R3.MinecraftServer;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Random;

public class ScoreboardHandler implements Listener{
	
	static SettingsManager settings = SettingsManager.getInstance();
	
	
	public static Permission perms = null;
	public static Economy econ = null;
	
	public static int getPlayersOnline() {
	    int x = 0;
	    for (Player p : Bukkit.getOnlinePlayers()) {
	    	if (!CMDVanish.vanished.contains(p.getUniqueId()))
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
		  return ChatColor.LIGHT_PURPLE;
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
			      return String.format("%.1f K", amt / 1000.0D).replace(".0 ", "")
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
	    	//Add prefix here if there's enough space.

	    	return "";
	    }
	    
	    static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	    
	    public static boolean isAFK(Player p) {
			return ess.getUser(p) != null && ess.getUser(p).isAfk();
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
							  //set suffix here
							int prestiges = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Prestiges");
							NewBoard.getTeam("prestige").setSuffix(c("&b"+prestiges));

		                    //prisonrank
		        	        NewBoard.getTeam("prank").setPrefix(c("&7Level: "));

		        	        //rankup%

		        	        NewBoard.getTeam("percent").setPrefix(c("&7Rankup: "));

		        	        p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
		        	    	double percents;
		        	        p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
		        	        percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
		        	        double dmultiply = percents * 10.0;
		        	        double dRound = Math.round(dmultiply) / 10.0;

		        	        	if(dRound>=100.0) {
		        	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
		        	        	} else {
		        	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
		        	        }

		        	        p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))));

		        	        //balance
		        	        NewBoard.getTeam("balance").setPrefix(c("&7Balance: &a&l$"));

		        	        //tokens
		        	        NewBoard.getTeam("tokens").setPrefix(c("&7Tokens: &e⛀"));

		        	        //xp
		        	        NewBoard.getTeam("xp").setPrefix(c("&7XP Needed: &b✴"));

							double xp = (PickXPHandler.getInstance().calculateXPNeeded(p,PickXPHandler.getInstance().getLevel(p))-PickXPHandler.getInstance().getXP(p));
							double xmultiply = xp * 10.0;
							int xround = (int) (Math.round(xmultiply) / 10.0);
							NewBoard.getTeam("xp").setSuffix(c("&b"+xround));

							NewBoard.getTeam("PickLevel").setPrefix(c("&7Level: "));
							NewBoard.getTeam("PickLevel").setSuffix(c("&b"+PickXPHandler.getInstance().getLevel(p)));


		        	        //multi
		        	        NewBoard.getTeam("multi").setPrefix(c("&7Multi: "));
							NewBoard.getTeam("multi").setSuffix(c("&b"+SellHandler.getInstance().getMulti(p)));


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
		        obj.getScore(ChatColor.BLUE+"").setScore(15);

				//Player
				Team player = NewBoard.registerNewTeam("Player");
				player.addEntry(ChatColor.RED+""+ChatColor.BLACK);
				player.setPrefix(c("&9&lPlayer:"));
				obj.getScore(ChatColor.RED+""+ChatColor.BLACK).setScore(14);
		        
		        //DonorRank
		        Team donor = NewBoard.registerNewTeam("donor");
		        donor.addEntry(ChatColor.DARK_AQUA+"");
		        obj.getScore(ChatColor.DARK_AQUA+"").setScore(13);
		        //Prestige
		        Team prestige = NewBoard.registerNewTeam("prestige");
		        prestige.addEntry(ChatColor.LIGHT_PURPLE+"");
		        obj.getScore(ChatColor.LIGHT_PURPLE+"").setScore(12);
		        //Prisonrank
		        Team prank = NewBoard.registerNewTeam("prank");
		        prank.addEntry(ChatColor.BLACK+"");
		        obj.getScore(ChatColor.BLACK+"").setScore(11);
				//rankup%
				Team percent = NewBoard.registerNewTeam("percent");
				percent.addEntry(ChatColor.WHITE+"");
				obj.getScore(ChatColor.WHITE+"").setScore(10);


		        //Balance
		        Team balance = NewBoard.registerNewTeam("balance");
		        balance.addEntry(ChatColor.DARK_BLUE+"");
		        obj.getScore(ChatColor.DARK_BLUE+"").setScore(9);

		        //Tokens
		        Team tokens = NewBoard.registerNewTeam("tokens");
		        tokens.addEntry(ChatColor.GOLD+"");
		        obj.getScore(ChatColor.GOLD+"").setScore(8);

		        //Multi
		        Team multi = NewBoard.registerNewTeam("multi");
		        multi.addEntry(ChatColor.DARK_RED+"");
		        obj.getScore(ChatColor.DARK_RED+"").setScore(7);
		        //3rdLine
		        Team rdLine = NewBoard.registerNewTeam("3rdLine");
		        rdLine.addEntry(ChatColor.GREEN+"");
		        rdLine.setPrefix(c(""));
			    rdLine.setSuffix(c(""));
		        obj.getScore(ChatColor.GREEN+"").setScore(6);
				//Pickaxe
				Team Pickaxe = NewBoard.registerNewTeam("Pickaxe");
				Pickaxe.addEntry(ChatColor.GOLD+""+ChatColor.GRAY);
				Pickaxe.setPrefix(c("&9&lPickaxe:"));
				obj.getScore(ChatColor.GOLD+""+ChatColor.GRAY).setScore(5);
				//PickLevel
				Team pickLevel = NewBoard.registerNewTeam("PickLevel");
				pickLevel.addEntry(ChatColor.DARK_AQUA+""+ChatColor.DARK_PURPLE);
				obj.getScore(ChatColor.DARK_AQUA+""+ChatColor.DARK_PURPLE).setScore(4);
				//XP
				Team xp = NewBoard.registerNewTeam("xp");
				xp.addEntry(ChatColor.AQUA+"");
				obj.getScore(ChatColor.AQUA+"").setScore(3);
				//Server
				Team Server = NewBoard.registerNewTeam("Server");
				Server.addEntry(ChatColor.LIGHT_PURPLE+""+ChatColor.DARK_RED);
				Server.setPrefix(c(""));
				Server.setSuffix(c(""));
				obj.getScore(ChatColor.LIGHT_PURPLE+""+ChatColor.DARK_RED).setScore(2);
		        //vote
		        Team vote = NewBoard.registerNewTeam("vote");
		        vote.addEntry(ChatColor.DARK_PURPLE+"");
		        obj.getScore(ChatColor.DARK_PURPLE+"").setScore(1);

		        
		        
		        
		        
		        
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
				//set suffix after setting up prestiges.
				int prestiges = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Prestiges");
				NewBoard.getTeam("prestige").setSuffix(c("&b"+prestiges));

            //prisonrank
	        NewBoard.getTeam("prank").setPrefix(c("&7Level: "));
	        
	        //rankup%
	        
	        NewBoard.getTeam("percent").setPrefix(c("&7Rankup: "));
	        
	        p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
	    	double percents;
	        p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
	        percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
	        double dmultiply = percents*10.0;
	        double dRound = Math.round(dmultiply) /10.0;
	        //adjust for new rank without "prestige" and change rankuphandler. + change scoreboard titles
	        	if(dRound>=100.0) {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
	        	} else {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
	        }

	        p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))));
	      
	        //balance
	        NewBoard.getTeam("balance").setPrefix(c("&7Balance: &a&l$"));
	        
	        //tokens
	        NewBoard.getTeam("tokens").setPrefix(c("&7Tokens: "));
			p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))+"⛀"));
	       
	        //multi
	        NewBoard.getTeam("multi").setPrefix(c("&7Multi: "));
			NewBoard.getTeam("multi").setSuffix(c("&b"+SellHandler.getInstance().getMulti(p)));
	        

	        //vote
	        int servervotes = settings.getVote().getInt("ServerVotes");
	        NewBoard.getTeam("vote").setPrefix(c("&dVoteParty: "));
	        NewBoard.getTeam("vote").setSuffix(c("&b"+(30-servervotes)+"/30"));
			//PickLevel
			NewBoard.getTeam("PickLevel").setPrefix(c("&7Level: "));
			NewBoard.getTeam("PickLevel").setSuffix(c("&b"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickLevel")));
	    	


			/*
			for (double tpss : MinecraftServer.getServer().recentTps ) {
		      		    	  String tp = ( format(tpss));

		      		    	NewBoard.getTeam("tps").setPrefix(ChatColor.GRAY +"TPS: "+  tp);
		      		      }
			 */
	    	
	    	new BukkitRunnable(){
	    		boolean titlechanged = false;
	    		@Override
	    		public void run() {

					String tps = "";

					String store = c("&6store.mcgenesis.net");

					for (double tpss : MinecraftServer.getServer().recentTps ) {
						String tp = ( format(tpss));

						tps = c("&7Server TPS: "+tp);
					}
	    			
	    			int ping = getPing(p);
					
					if(titlechanged == false) {
						//Add keys, stock market, event, and websites into tablist
					
						TitleAPI.sendTabTitle(p, c("&9&lGenesis&b&lMC\n&aConnected to: &9Prison\n\n&dPlayers Online: &c"+getPlayersOnline()+"\n&7Ping: &a"+ping+"\n"+tps+"\n\n&8&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"),
								c("&7&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n\n&6Boosts\n"+ BoostsHandler.sname+"&7/"+ BoostsHandler.xname+"\n"+ BoostsHandler.stimeLeft+"&7/"+ BoostsHandler.xtimeLeft+"\n\n"+store));
					titlechanged = true;
					} else {
						TitleAPI.sendTabTitle(p, c("&9&lGenesis&b&lMC\n&aConnected to: &9Prison\n\n&dPlayers Online: &c"+getPlayersOnline()+"\n&7Ping: &a"+ping+"\n"+tps+"\n\n&7&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"),
								c("&8&m=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-\n\n&6Boosts\n"+ BoostsHandler.sname+"&7/"+ BoostsHandler.xname+"\n"+ BoostsHandler.stimeLeft+"&7/"+ BoostsHandler.xtimeLeft+"\n\n"+store));
						titlechanged = false;
					}
					if(!p.isOnline()) {
						cancel();
					}
	    		}
	    		
	    		
	    	}.runTaskTimer(Main.plugin, 0, 10L);
	    	
	    	
	    }
	    
	    
	    
	          
	        
	      
	    
	    
	    
	
}
