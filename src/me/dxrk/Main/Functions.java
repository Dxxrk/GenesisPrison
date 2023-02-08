package me.dxrk.Main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import org.bukkit.scheduler.BukkitRunnable;

public class Functions implements Listener{
	
	public static int multiCap(Player p) {
		
		return 300;
	}
	static Methods m = Methods.getInstance();

	public static List<Player> multiply = new ArrayList<>();
	

	public static double XPEnchant(Player p){
		double xp = 1;
		if(p.getItemInHand() == null) return 1;
		if(p.getItemInHand().getType().equals(Material.AIR)) return 1;
		if(!p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) && !p.getItemInHand().getType().equals(Material.IRON_PICKAXE) && !p.getItemInHand().getType().equals(Material.GOLD_PICKAXE)
		&& !p.getItemInHand().getType().equals(Material.STONE_PICKAXE) && !p.getItemInHand().getType().equals(Material.WOOD_PICKAXE)) return 1;

		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("xp finder")) {

				String[] n = ChatColor.stripColor(s).split(" ");
				double x = Double.parseDouble(n[2])/400;

				xp += x;
			}
		}

		return xp;
	}


	public static double sellBoost(Player p) {
		double sell = 1;
		if(!p.getItemInHand().hasItemMeta()) return 1;
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
		if(!p.getItemInHand().hasItemMeta()) return 1;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("xp trinket")) {

				String[] n = ChatColor.stripColor(s).split("%");
				String[] num = n[0].split(" ");
				double x = Double.parseDouble(num[4])/100;

				xp += x;
			}
		}

		return xp;
	}
	public static double tokenBoost(Player p) {
		double xp = 1;
		if(!p.getItemInHand().hasItemMeta()) return 1;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("token trinket")) {

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
		if(!p.getItemInHand().hasItemMeta()) return 1;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("lucky") && ChatColor.stripColor(s).toLowerCase().contains("%")) {
				
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

		int level;

		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Fortuity")) {
				level = m.getBlocks(s);
				return 1+(level*0.0015);
			}
		}
	    return 1;
	}
	
	
	public static double Lucky(Player p) {
		int level;

		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if (ChatColor.stripColor(s).contains("Karma")) {
				level = m.getBlocks(s);
				return 1+(level*0.0001);
			}
		}
		return 1;
	}
	
	private static Random r = new Random();
	
	public static void Multiply(Player p) {


		double lucky = Functions.Lucky(p);
		double luck = Functions.luckBoost(p);

		int level = 0;
		int chance;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Multiply")) {
				level = m.getBlocks(s);
			}
		}

		if(level == 0) return;
		if(level == 1) {
			chance = (int) (3000 / lucky / luck);
		} else {
			chance = (int) ((3000 - (1.5*level))/lucky / luck);
		}
		int i = r.nextInt(chance);

		if(i == 1) {
			p.sendMessage(m.c("&f&lMultiply &8| &bActivated!"));
			multiply.add(p);

			new BukkitRunnable(){
				@Override
				public void run(){
					p.sendMessage(m.c("&f&lMultiply &8| &bDeactivated!"));
				}
			}.runTaskLater(Main.plugin, 20*10L);

		}
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
