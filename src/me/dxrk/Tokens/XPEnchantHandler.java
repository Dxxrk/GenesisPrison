package me.dxrk.Tokens;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import me.dxrk.Events.KeysHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.md_5.bungee.api.ChatColor;

public class XPEnchantHandler implements Listener{
	
	
	//Lucky Enchant -- done
	// Double TokenFinder effectiveness -- done
	//Fortune Boost Enchant -- done
	
	static XPEnchantHandler instance = new XPEnchantHandler();
	
	SettingsManager settings = SettingsManager.getInstance();
	  
	  public static XPEnchantHandler getInstance() {
	    return instance;
	  }
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	
	
	public void RuneParty(Player p) {
		Random r = new Random();
		int i = r.nextInt(8);
		
		if(i == 0 || i ==1) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &e&lMidas &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Midas", 1);
			}
		} else if(i == 2 || i == 3) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &9&lPoseidon &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Poseidon", 1);
			}
		}else if(i == 4 || i==5) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &4&lHades &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Hades", 1);
			}
		}else if(i == 6 || i==7) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &f&lPolis &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Polis", 1);
			}
		}else if(i ==8) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &c&lOblivion &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Oblivion", 1);
			}
		}
		
		
	}
	
	
	public void RunePartyBreak(Player p) {
		Random r = new Random();
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 10")) {
				int i = r.nextInt(1500);
				if(i == 1) {
					RuneParty(p);
				}
			} else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 9")) {
				int i = r.nextInt(1600);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 8")) {
				int i = r.nextInt(1700);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 7")) {
				int i = r.nextInt(1800);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 6")) {
				int i = r.nextInt(1900);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 5")) {
				int i = r.nextInt(2000);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 4")) {
				int i = r.nextInt(2100);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 3")) {
				int i = r.nextInt(2200);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 2")) {
				int i = r.nextInt(2300);
				if(i == 1) {
					RuneParty(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("runeparty 1")) {
				int i = r.nextInt(2400);
				if(i == 1) {
					RuneParty(p);
				}
			}
			
			
		}
	}
	
	
	public void Booster(Player p) {
		Random r = new Random();
		int i = r.nextInt(5);
		
		if(i == 0) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 2.5 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2.5x Sell Boost!"));
			}
		} else if(i == 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 2.5 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
			p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 2.5x Sell Boost!"));
			}
		}else if(i == 2) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp "+p.getName()+" 2 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
			p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2x XP Boost!"));
			}
		}else if(i == 3) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 3.0 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
			p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 3.0x Sell Boost!"));
			}
		}else if(i == 4) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 3.0 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
			p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 3.0x Sell Boost!"));
			}
		}else if(i == 5) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp "+p.getName()+" 2 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
			p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 2x XP Boost!"));
			}
		}
		
	}
	
	public void BoosterBreak(Player p) {
		Random r = new Random();
		
		for (String s : p.getItemInHand().getItemMeta().getLore()) {
			if(ChatColor.stripColor(s).toLowerCase().contains("booster 10")) {
				int i = r.nextInt(2700);
				if(i == 1) {
					Booster(p);
				}
			} else if(ChatColor.stripColor(s).toLowerCase().contains("booster 9")) {
				int i = r.nextInt(2800);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 8")) {
				int i = r.nextInt(2900);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 7")) {
				int i = r.nextInt(3000);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 6")) {
				int i = r.nextInt(3100);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 5")) {
				int i = r.nextInt(3200);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 4")) {
				int i = r.nextInt(3300);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 3")) {
				int i = r.nextInt(3400);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 2")) {
				int i = r.nextInt(3500);
				if(i == 1) {
					Booster(p);
				}
			}else if(ChatColor.stripColor(s).toLowerCase().contains("booster 1")) {
				int i = r.nextInt(3600);
				if(i == 1) {
					Booster(p);
				}
			}
		}
		
	}
	
	
	
	
	
	
	

}
