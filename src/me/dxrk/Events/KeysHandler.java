package me.dxrk.Events;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dxrk.Main.Functions;
import me.dxrk.Main.Methods;
import me.dxrk.Main.RomanNumber;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KeysHandler implements Listener {
  public SettingsManager settings = SettingsManager.getInstance();
  
  Random r = new Random();
  
  static KeysHandler instance = new KeysHandler();
  
  public static KeysHandler getInstance() {
    return instance;
  }
  
  private Methods m = Methods.getInstance();
  
  
  public void addKey(Player p, String key, int amt) {
    int keys = this.settings.getLocksmith().getInt(p.getUniqueId().toString() + "." + key.toLowerCase());
    key = key.toLowerCase();
    if (this.settings.getLocksmith().get(p.getUniqueId().toString() + "." + p.getName()) == null) {
      this.settings.getLocksmith().set(p.getUniqueId().toString() + ".name", p.getName());
      this.settings.saveLocksmith();
    } 
    this.settings.getLocksmith().set(p.getUniqueId().toString() + "." + key, keys + amt);
    this.settings.saveLocksmith();
  }
  
  public void takeKey(Player p, String key, int amt) {
    int keys = this.settings.getLocksmith().getInt(p.getUniqueId().toString() + "." + key.toLowerCase());
    key = key.toLowerCase();
    if (this.settings.getLocksmith().get(p.getUniqueId().toString() + "." + p.getName()) == null) {
      this.settings.getLocksmith().set(p.getUniqueId().toString() + ".name", p.getName());
      this.settings.saveLocksmith();
    } 
    this.settings.getLocksmith().set(p.getUniqueId().toString() + "." + key, keys - amt);
    this.settings.saveLocksmith();
  }
  
  public void KRMSG2(Player p, String key, String color, boolean bc, String lvl) {
	  String s;
	  
    s = ChatColor.translateAlternateColorCodes('&', 
    		"&f&lEncounter &8| &b+2 " + color + key + " &bRune");
	  
	    if (bc) {
	      Bukkit.broadcastMessage(s);
	      addKey(p, key, 2);
	    } else {
	    	if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Encounter-Messages") == true) {
	    		p.sendMessage(s);
	    	}
	      addKey(p, key, 2);
	    }
	    int keysfound = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".KeysFound");
		  this.settings.getPlayerData().set(p.getUniqueId().toString()+".KeysFound", (keysfound+2));
	  }
	  
	  
  
  public void KRMSG(Player p, String key, String color, boolean bc, String lvl) {
	  String s;
	  
    s = ChatColor.translateAlternateColorCodes('&', 
        "&f&lEncounter &8| &b+1 " + color + key + " &bRune");
	  
    if (bc) {
      Bukkit.broadcastMessage(s);
      addKey(p, key, 1);
    } else {
    	if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Encounter-Messages") == true) {
    		p.sendMessage(s);
    	}
      addKey(p, key, 1);
    } 
    int keysfound = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".KeysFound");
	  this.settings.getPlayerData().set(p.getUniqueId().toString()+".KeysFound", (keysfound+1));
  }
  
  public void DiscMSG(Player p, String dust) {
	  switch (dust) {
		  case "Common":
			  p.getInventory().addItem(TrinketHandler.getInstance().commonDust());
			  if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Discovery-Messages") == true) {
				  p.sendMessage(m.c("&f&lDiscovery &8| &b+1 Common Trinket Dust"));
			  }
			  break;
		  case "Rare":
			  p.getInventory().addItem(TrinketHandler.getInstance().rareDust());
			  if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Discovery-Messages") == true) {
				  p.sendMessage(m.c("&f&lDiscovery &8| &9+1 Rare Trinket Dust"));
			  }
			  break;
		  case "Epic":
			  p.getInventory().addItem(TrinketHandler.getInstance().epicDust());
			  if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Discovery-Messages") == true) {
				  p.sendMessage(m.c("&f&lDiscovery &8| &5+1 Epic Trinket Dust"));
			  }
			  break;
		  case "Legendary":
			  p.getInventory().addItem(TrinketHandler.getInstance().legDust());
			  if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Discovery-Messages") == true) {
				  p.sendMessage(m.c("&f&lDiscovery &8| &6+1 Legendary Trinket Dust"));
			  }
			  break;
		  case "Heroic":
			  p.getInventory().addItem(TrinketHandler.getInstance().herDust());
			  if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Discovery-Messages") == true) {
				  p.sendMessage(m.c("&f&lDiscovery &8| &4+1 Heroic Trinket Dust"));
			  }
			  break;
	  }
  }
  
  
  
  public boolean findKey(int level) {
	  return this.r.nextInt(level) == 1;
  }
  
  public void dustFinder(Player p, String s) {
	  int level = m.getBlocks(s);
	  int chance;
	  if(level == 0) return;
	  if(level == 1) {
		  chance = 1350;
	  } else {
		  chance = 1350 - (7*level);
	  }

	  if (ChatColor.stripColor(s).contains("Encounter ")) {
		  if (findKey(chance))
			  keyToGiveR(level, p);
	  }

  }
  

  
  
  public void keyToGive(int kfLvL, Player p) {
    double rand = this.r.nextInt(1000);
    
    if(rand <=499) {
    	DiscMSG(p, "Common");
    } else if(rand >= 500 && rand <=799) {
    	DiscMSG(p, "Rare");
    } else if(rand >= 800 && rand <=949) {
    	DiscMSG(p, "Epic");
    } else if(rand >= 950 && rand <=991) {
    	DiscMSG(p, "Legendary");
    } else if(rand >=992) {
    	DiscMSG(p, "Heroic");
    }
    
  }
  
  public void keyToGiveR(int kfLvL, Player p) {
    double rand = this.r.nextInt(100);
    int chance = 0;
    List<String> lore = p.getItemInHand().getItemMeta().getLore();
	  for (String s : lore) {
		  if (ChatColor.stripColor(s).contains("Double Keys")) {
			  chance += m.getBlocks(s);
		  }
	  }
    int kf = this.r.nextInt(100);
    if(chance > 0 && kf <= chance) { // change to check for lore on pickaxe having "Key Fortune"
    	if(kfLvL < 50) {
    		if (rand < 10.0D) {
    	          KRMSG2(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand < 35.0D) {
    	          KRMSG2(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand < 67.5D) {
    	          KRMSG2(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	        } else {
    	          KRMSG2(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
    	        }
    	} else {
    		if (rand < 0.25D) {
  	          KRMSG2(p, "Oblivion", "&c&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand < 15.0D) {
  	          KRMSG2(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand < 45.0D) {
  	          KRMSG2(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
  	        }else if (rand < 72.5D) {
    	      KRMSG2(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	    } else {
  	          KRMSG2(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
  	        }
    	}
    } else {
    	if(kfLvL < 50) {
    		if (rand < 10.0D) {
    	          KRMSG(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand < 35.0D) {
    	          KRMSG(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand < 67.5D) {
    	          KRMSG(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	        } else {
    	          KRMSG(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
    	        }
    	} else {
    		if (rand < 0.25D) {
  	          KRMSG(p, "Oblivion", "&c&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand < 15.0D) {
  	          KRMSG(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand < 45.0D) {
  	          KRMSG(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
  	        }else if (rand < 72.5D) {
    	      KRMSG(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	    } else {
  	          KRMSG(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
  	        }
    	}
      
    }
  }
  
  public void keyRoulette(Player p, String s) {

	  int level = m.getBlocks(s);
	  int chance;
	  if(level == 0) return;
	  if(level == 1) {
		  chance = 1350;
	  } else {
		  chance = 1350 - (7*level);
	  }
	  
    if (ChatColor.stripColor(s).contains("Encounter ")) {
      if (findKey(chance))
        keyToGiveR(level, p);
    }
  }



	public void findTokens(Player p) {
		List<String> lore = p.getItemInHand().getItemMeta().getLore();
		double tf = 1;
		int x;
		for (x = 0; x < lore.size(); x++) {
			String s = lore.get(x);
			if (ChatColor.stripColor(s).contains("Token Finder")) {
				tf = m.getBlocks(ChatColor.stripColor(s))*0.0048;
			}
		}
		double multiply = 1;
		if(Functions.multiply.contains(p)) multiply = 2;

		Random r = new Random();
		int rint = r.nextInt(150);
		int fmin = 7500;
		int fmax = 40000;
		int tokens = r.nextInt(fmax - fmin)+ fmin;
		if(rint == 1) {
			Tokens.getInstance().addTokens(p, (tokens+1)*tf*multiply);
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Tokens-Messages") == true) {
				p.sendMessage(m.c("&f&lTokens &8| &b+"+((tokens+1)*tf*multiply)));
			}
		}
	}
  
  
  @EventHandler
  @SuppressWarnings("deprecation")
  public void onBreak(BlockBreakEvent e) {
    Player p = e.getPlayer();
    if (p.getItemInHand() == null)
      return; 
    if (e.isCancelled())
      return; 
    ItemStack i = p.getItemInHand();
    if (!i.hasItemMeta())
      return; 
    if (!i.getItemMeta().hasLore())
      return; 
    WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    ApplicableRegionSet set = wg.getRegionManager(p.getWorld()).getApplicableRegions(e.getBlock().getLocation());
    if (!set.allows(DefaultFlag.LIGHTER))
      return; 
    
    findTokens(p);
    
    Random o = new Random();
    int ol = o.nextInt(500000);
    if(ol == 1) {
    	Bukkit.broadcastMessage(Methods.getInstance().c("&e&lThe gods have granted &5&l"+p.getName()+" &e&lan &f&l&kOO&e&lOlympus&f&l&kOO&r &e&lRune!"));
    	addKey(p, "Olympus", 1);
    }
    
    List<String> lore = i.getItemMeta().getLore();
    int x;
    for (x = 0; x < lore.size(); x++) {
      String s = lore.get(x);
      if (ChatColor.stripColor(s).contains("Key Finder")) {
        keyRoulette(p, s);
      }
      
    }
    for (x = 0; x < lore.size(); x++) {
      String s = lore.get(x);
      if (ChatColor.stripColor(s).contains("Dust Finder")) {
			dustFinder(p, s);
      }
      
    }
    
  }
}
