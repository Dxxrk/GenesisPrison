package me.dxrk.Events;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    int keys = this.settings.getLocksmith().getInt(String.valueOf(p.getUniqueId().toString()) + "." + key.toLowerCase());
    key = key.toLowerCase();
    if (this.settings.getLocksmith().get(String.valueOf(p.getUniqueId().toString()) + "." + p.getName()) == null) {
      this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + ".name", p.getName());
      this.settings.saveLocksmith();
    } 
    this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + "." + key, Integer.valueOf(keys + amt));
    this.settings.saveLocksmith();
  }
  
  public void takeKey(Player p, String key, int amt) {
    int keys = this.settings.getLocksmith().getInt(String.valueOf(p.getUniqueId().toString()) + "." + key.toLowerCase());
    key = key.toLowerCase();
    if (this.settings.getLocksmith().get(String.valueOf(p.getUniqueId().toString()) + "." + p.getName()) == null) {
      this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + ".name", p.getName());
      this.settings.saveLocksmith();
    } 
    this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + "." + key, Integer.valueOf(keys - amt));
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
	  if(dust.equals("Common")) {
		  p.getInventory().addItem(TrinketHandler.getInstance().commonDust());
		  if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Discovery-Messages") == true) {
		  p.sendMessage(m.c("&f&lDiscovery &8| &b+1 Common Trinket Dust"));
		  }
	  } else if(dust.equals("Rare")) {
		  p.getInventory().addItem(TrinketHandler.getInstance().rareDust());
		  if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Discovery-Messages") == true) {
		  p.sendMessage(m.c("&f&lDiscovery &8| &9+1 Rare Trinket Dust"));
		  }
	  } else if(dust.equals("Epic")) {
		  p.getInventory().addItem(TrinketHandler.getInstance().epicDust());
		  if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Discovery-Messages") == true) {
		  p.sendMessage(m.c("&f&lDiscovery &8| &5+1 Epic Trinket Dust"));
		  }
	  } else if(dust.equals("Legendary")) {
		  p.getInventory().addItem(TrinketHandler.getInstance().legDust());
		  if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Discovery-Messages") == true) {
		  p.sendMessage(m.c("&f&lDiscovery &8| &6+1 Legendary Trinket Dust"));
		  }
	  } else if(dust.equals("Heroic")) {
		  p.getInventory().addItem(TrinketHandler.getInstance().herDust());
		  if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Discovery-Messages") == true) {
			  p.sendMessage(m.c("&f&lDiscovery &8| &4+1 Heroic Trinket Dust"));
		  }
	  }
  }
  
  
  
  public boolean findKey(int level) {
    if (this.r.nextInt(level) == 1)
      return true; 
    return false;
  }
  
  public void discovery(Player p, int x, String s) {
	  int level = 0;
	  ItemStack i = p.getItemInHand();
	  List<String> lore = i.getItemMeta().getLore();
	  
	  for (int z = 0; z < lore.size(); z++) {
	      String ss = lore.get(z);
	      if (ChatColor.stripColor(ss).contains("Discovery")) {
	    	  level = Integer.parseInt(ChatColor.stripColor(ss).split(" ")[1]);
	      }
	  }
	  if(level == 0) return;
	  int blockstill = 3000;
	  if(level == 1) {
		  blockstill = 3000;
	  } else {
		  blockstill = 3000 - (10*level);
	  }
	      
	  
	  
    String amountstring = ChatColor.stripColor(s).replace("Blocks Till Dust: ", "").replace(" ", "");
    try {
      int amount = Integer.parseInt(amountstring);
      if (amount < 0) {
        String s1 = lore.get(x - 1);
        if (s1.contains("Discovery ")) {
          ItemMeta itemMeta = p.getItemInHand().getItemMeta();
          lore.set(x, ChatColor.GRAY + "Blocks Till Dust: " + ChatColor.AQUA + String.valueOf(blockstill));
          itemMeta.setLore(lore);
          p.getItemInHand().setItemMeta(itemMeta);
          p.updateInventory();
          keyToGive(6, p);
        } 
      } else {
        lore.set(x, ChatColor.GRAY + "Blocks Till Dust: " + ChatColor.AQUA + String.valueOf(amount - 1));
      } 
      ItemMeta meta = p.getItemInHand().getItemMeta();
      meta.setLore(lore);
      p.getItemInHand().setItemMeta(meta);
      p.updateInventory();
    } catch (Exception exception) {}
  }
  
  public void giveDust(Player p, String lvl) {
	  Random r = new Random();
	  int rint = r.nextInt(100);
	  if(rint >0 && rint >20) {
		  
	  }
	  
	  
  }
  
  
  public void keyToGive(int kfLvL, Player p) {
    double rand = this.r.nextInt(1000);
    
    if(rand >=0 && rand <=499) {
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
    for(int x = 0; x < lore.size(); x++) {
    	String s = lore.get(x);
    	if(ChatColor.stripColor(s).contains("Double Keys")) {
    		chance += m.getBlocks(s);
    	}
    }
    int kf = this.r.nextInt(100);
    if(chance > 0 && kf >=0 && kf <=chance) { // change to check for lore on pickaxe having "Key Fortune"
    	if(kfLvL < 50) {
    		if (rand >= 0 && rand < 10.0D) {
    	          KRMSG2(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand >= 10.0D && rand < 35.0D) {
    	          KRMSG2(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand >= 35.0D && rand < 67.5D) {
    	          KRMSG2(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if(rand >= 67.5){
    	          KRMSG2(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
    	        }
    	} else if(kfLvL >=50) {
    		if (rand >= 0 && rand < 0.25D) {
  	          KRMSG2(p, "Oblivion", "&c&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand >= 0.25D && rand < 15.0D) {
  	          KRMSG2(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand >=15.0D && rand < 45.0D) {
  	          KRMSG2(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
  	        }else if (rand >= 45.0D && rand < 72.5D) {
    	      KRMSG2(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	    } else if(rand >=72.5D){
  	          KRMSG2(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
  	        }
    	}
    } else {
    	if(kfLvL < 50) {
    		if (rand >= 0 && rand < 10.0D) {
    	          KRMSG(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand >= 10.0D && rand < 35.0D) {
    	          KRMSG(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if (rand >= 35.0D && rand < 67.5D) {
    	          KRMSG(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	        } else if(rand >= 67.5){
    	          KRMSG(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
    	        }
    	} else if(kfLvL >=50) {
    		if (rand >= 0 && rand < 0.25D) {
  	          KRMSG(p, "Oblivion", "&c&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand >= 0.25D && rand < 15.0D) {
  	          KRMSG(p, "Polis", "&f&l", false, RomanNumber.toRoman(kfLvL));
  	        } else if (rand >=15.0D && rand < 45.0D) {
  	          KRMSG(p, "Hades", "&4&l", false, RomanNumber.toRoman(kfLvL));
  	        }else if (rand >= 45.0D && rand < 72.5D) {
    	      KRMSG(p, "Midas", "&e&l", false, RomanNumber.toRoman(kfLvL));
    	    } else if(rand >=72.5D){
  	          KRMSG(p, "Poseidon", "&9&l", false, RomanNumber.toRoman(kfLvL));
  	        }
    	}
      
    }
  }
  
  public void keyRoulette(Player p, String s) {
	  
	  
	  
	  int level = m.getBlocks(s);
	  int blockstill = 1350;
	  if(level == 0) return;
	  if(level == 1) {
		  blockstill = 1350;
	  } else {
		  blockstill = 1350 - (7*level);
	  }
	  
    if (ChatColor.stripColor(s).contains("Encounter ")) {
      if (findKey(blockstill))
        keyToGiveR(level, p);
    }
  }
  
  public boolean DoesNotHaveLoreKF(String s2) {
		  if(!ChatColor.stripColor(s2).contains("Discovery ") && !ChatColor.stripColor(s2).contains("Encounter ")) 
			  return true;
	  
	return false;
	  
  }
  
  

public void tokenstoGive(int tokens, Player p) {
	double multiply = Functions.Multiply(p);
	int rand = this.r.nextInt(tokens);
	
	int divisor = 10;
	int rand10 = (int) (Math.ceil((double)rand*multiply / divisor));
	
	
	if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".TokenFinder-Messages") == true) {
		p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lTokenFinder &8| &b+" + (((int)rand*multiply)) + " Tokens"));
	}
	Tokens.getInstance().addTokens(p, (int) (((int)rand*multiply)));
	for(Player pp : Bukkit.getOnlinePlayers()) {
		if(p.getUniqueId() == pp.getUniqueId()) {
			continue;
		}else {
			if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".TokenFinder-Messages") == true) {
				pp.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lTokenFinder &8| &bFrom &d" + p.getName() + " &b+" + ((int)rand10) + " Tokens"));
			}
		Tokens.getInstance().addTokens(pp, (int) (((int)rand10)));
		}
	}
	    
}

public void tokenFinder(Player p) {
	
	double lucky = Functions.Lucky(p);
	double luck = Functions.luckBoost(p);
	
	int level = 0;
	int chance = 2650;
	for (String s : p.getItemInHand().getItemMeta().getLore()) {
		
		      if (ChatColor.stripColor(s).contains("TokenFinder")) {
		    	  level = m.getBlocks(s);
		      }
	}
	
	
	
	
		  if(level == 0) return;
		  
		  if(level == 1) {
			  chance = (int) (2650 / lucky / luck);
		  } else {
			  chance = (int) ((2650 - (11*level))/lucky / luck);
		  }
    	  int i = r.nextInt(chance);
    	   
    	  int tokens = 120+ 5*level;
    	  if(i == 1) {
    		  tokenstoGive(tokens, p);
    	  
    	  }
	
}






	public void findTokens(Player p) {
		Random r = new Random();
		int rint = r.nextInt(777);
		int tokens = r.nextInt(77);
		if(rint == 1) {
			Tokens.getInstance().addTokens(p, tokens+1);
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Tokens-Messages") == true) {
				p.sendMessage(m.c("&f&lTokens &8| &b+"+(tokens+1)));
			}
		}
	}
  
  
  @EventHandler
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
      if (ChatColor.stripColor(s).contains("Encounter")) {
        keyRoulette(p, s);
      }
      
    }
    for (x = 0; x < lore.size(); x++) {
        String s = lore.get(x);
        if (ChatColor.stripColor(s).contains("TokenFinder")) {
          tokenFinder(p);
        }
        
      }
    for (x = 0; x < lore.size(); x++) {
      String s = lore.get(x);
      if (s.contains("Blocks Till Dust:"))
        discovery(p, x, s);
      if (ChatColor.stripColor(s).contains("Discovery")) {
        boolean loree = false;
        try {
          String sss = lore.get(x + 1);
          loree = true;
        } catch (Exception exception) {}
        if (!loree || !((String)lore.get(x + 1)).contains("Blocks Till Dust:")) {
          ItemMeta meta = p.getItemInHand().getItemMeta();
          List<String> newLore = new ArrayList<>();
          int y;
          for (y = 0; y < x + 1; y++)
            newLore.add(lore.get(y)); 
          newLore.add(ChatColor.GRAY + "Blocks Till Dust: " + ChatColor.AQUA + String.valueOf(100));
          try {
            for (y = x + 1; y < lore.size(); y++)
              newLore.add(lore.get(y)); 
          } catch (Exception exception) {}
          meta.setLore(newLore);
          p.getItemInHand().setItemMeta(meta);
          p.updateInventory();
        } 
      }
      
    }
    
  }
}
