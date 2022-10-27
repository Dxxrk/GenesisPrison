package me.dxrk.Main;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import me.dxrk.Main.Methods;
import net.md_5.bungee.api.ChatColor;

public class Functions implements Listener{
	
	public static int multiCap(Player p) {
		
		return 300;
	}
	static Methods m = Methods.getInstance();
	
	
	public static double sellBoost(Player p) {
		double sell = 1;
		
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("sell")) {
				
				String[] n = ChatColor.stripColor(s).split("%");
				String[] num = n[0].split(" ");
				double sel = Double.parseDouble(num[4])/100;
				
				sell += sel;
			}
		}
		
		return sell;
	}

	public static double xpBoost(Player p) {
		double xp = 1;

		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("xp")) {

				String[] n = ChatColor.stripColor(s).split("%");
				String[] num = n[0].split(" ");
				double x = Double.parseDouble(num[4])/100;

				xp += x;
			}
		}

		return xp;
	}

	
	public static double luckBoost(Player p) {
		double luck = 1;
		
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("lucky")) {
				
				String[] n = ChatColor.stripColor(s).split("%");
				String[] num = n[0].split(" ");
				double luc = Double.parseDouble(num[4])/100;
				
				luck += luc;
			}
		}
		
		return luck;
	}
	
	
	public static double greed(Player p) {
		double level = 0;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			
		      if (ChatColor.stripColor(s).contains("Greed")) {
		    	  level = m.getBlocks(s);
		      }
		}
		double greed = level / 5;
		if(greed == 0){
			greed = 1;
		}
		
		
		return greed;
	}
	
	public static double Foruity(Player p) {

		

	    for (String s : p.getItemInHand().getItemMeta().getLore()) {
	      	if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 10")) {
	      		return 1.55;
	      	} else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 9")) {
	      		return 1.5;
	      	} else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 8")) {
	      		return 1.45;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 7")) {
	      		return 1.4;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 6")) {
	      		return 1.35;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 5")) {
	      		return 1.3;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 4")) {
	      		return 1.25;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 3")) {
	      		return 1.2;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 2")) {
	      		return 1.15;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("fortuity 1")) {
	      		return 1.1;
	      	}
	      	}
	    return 1;
	}
	
	
	public static double Lucky(Player p) {

    	
    	for (String s : p.getItemInHand().getItemMeta().getLore()) {
	      	if(ChatColor.stripColor(s).toLowerCase().contains("lucky 10")) {
	      		return 1.6;
	      	} else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 9")) {
	      		return 1.55;
	      	} else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 8")) {
	      		return 1.5;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 7")) {
	      		return 1.45;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 6")) {
	      		return 1.4;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 5")) {
	      		return 1.35;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 4")) {
	      		return 1.3;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 3")) {
	      		return 1.25;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 2")) {
	      		return 1.2;
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("lucky 1")) {
	      		return 1.15;
	      	}
	      	}
    	return 1;
	}
	
	private static Random r = new Random();
	
	public static double Multiply(Player p) {
		
		if(!p.hasPermission("enchant.multiply") || !p.hasPermission("enchant.multiplyunlock")) {
			return 1;
		}
		
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
	      	if(ChatColor.stripColor(s).toLowerCase().contains("multiply 10")) {
				int i = r.nextInt(2);
				if(i == 1) {
					return 2;
				}
	      	} else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 9")) {
	      		int i = r.nextInt(3);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	} else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 8")) {
	      		int i = r.nextInt(4);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 7")) {
	      		int i = r.nextInt(5);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 6")) {
	      		int i = r.nextInt(6);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 5")) {
	      		int i = r.nextInt(7);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 4")) {
	      		int i = r.nextInt(8);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 3")) {
	      		int i = r.nextInt(9);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 2")) {
	      		int i = r.nextInt(10);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}else if(ChatColor.stripColor(s).toLowerCase().contains("multiply 1")) {
	      		int i = r.nextInt(11);
	      		if(i == 1) {
	      			return 2;
	      		}
	      	}
	      	}
		return 1;
	}
	
	public static String prestige(Player p) {
		String prestige = "";
		
		for(int i = 1; i <101; i++) {
			if(p.hasPermission("prestige."+i)) {
				if(i<11) {
					prestige = m.c("&b&lP"+i+"&8-&3");
				} else if(i<21) {
					prestige = m.c("&9&lT"+(i-10)+"&8-&b");
				} else if(i<31) {
					prestige = m.c("&2&lS"+(i-20)+"&8-&a");
				} else if(i<41) {
					prestige = m.c("&5&lA"+(i-30)+"&8-&d");
				} else if(i<51) {
					prestige = m.c("&4&lC"+(i-40)+"&8-&e");
				} else if(i<61) {
					prestige = m.c("&d&lL"+(i-50)+"&8-&5");
				} else if(i<71) {
					prestige = m.c("&3&lF"+(i-60)+"&8-&b");
				} else if(i<81) {
					prestige = m.c("&e&lM"+(i-70)+"&8-&c");
				} else if(i<91) {
					prestige = m.c("&c&lE"+(i-80)+"&8-&6");
				} else {
					prestige = m.c("&6&lO"+(i-90)+"&8-&f");
				}
				
			}
		}
	    return prestige;
	}
	
	public static String pres(Player p) {
		String prestige = "";
		
		for(int i = 1; i <101; i++) {
			if(p.hasPermission("prestige."+i)) {
				prestige = m.c("&fPrestige &7"+i);
			}
		}
		
		
		return prestige;
	}
	
	

}
