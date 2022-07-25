package me.dxrk.Tokens;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.sk89q.worldguard.protection.flags.DefaultFlag;

import me.dxrk.Main.ResetHandler;
import me.dxrk.Main.ResetHandler.ResetReason;
import me.jet315.prisonmines.mine.Mine;
import net.md_5.bungee.api.ChatColor;

public class Enchants implements Listener{
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	
	 static Enchants instance = new Enchants();
	  
	  public static Enchants getInstance() {
	    return instance;
	  }
	
	
	
	  
	  public int getLevel(String ss) {
		    String s = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', ss));
		    StringBuilder lvl = new StringBuilder();
		    byte b;
		    int i;
		    char[] arrayOfChar;
		    for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
		      char c = arrayOfChar[b];
		      if (isInt(c))
		        lvl.append(c); 
		      b++;
		    } 
		    if (isInt(lvl.toString()))
		      return Integer.parseInt(lvl.toString()); 
		    return -1;
		  }
		  
		  public boolean isInt(String s) {
		    try {
		      int i = Integer.parseInt(s);
		      return true;
		    } catch (Exception e1) {
		      return false;
		    } 
		  }
		  
		  public boolean isInt(char ss) {
		    String s = String.valueOf(ss);
		    try {
		      int i = Integer.parseInt(s);
		      return true;
		    } catch (Exception e1) {
		      return false;
		    } 
		  }
		  public List<String> getEnchants(ItemStack i) {
			    return i.getItemMeta().getLore();
			  }
			  
		  
		  public int getFortune(String s) {
		        StringBuilder lvl = new StringBuilder();
		        s = ChatColor.stripColor((String)s);
		        char[] arrayOfChar = s.toCharArray();
		        int i = arrayOfChar.length;
		        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
		            char c = arrayOfChar[b];
		            if (!this.isInt(c)) continue;
		            lvl.append(c);
		        }
		        if (this.isInt(lvl.toString())) {
		            return Integer.parseInt(lvl.toString());
		        }
		        return -1;
		    }
		  
		  public void addFortune(Player p, ItemStack ii, int loree) {
		        int blockss = this.getFortune((String)ii.getItemMeta().getLore().get(loree));
		        ItemStack i = ii.clone();
		        ItemMeta im = i.getItemMeta();
		        List<String> lore = im.getLore();
		        lore.set(loree, (Object)ChatColor.GRAY + "Fortune " + (Object)ChatColor.GRAY + (blockss + 1));
		        im.setLore(lore);
		        i.setItemMeta(im);
		        p.setItemInHand(i);
		        p.updateInventory();
		        int blocks = blockss + 1;
		    }
		  
		  
		  
		  
		  
	
	
	@SuppressWarnings("deprecation")
	@EventHandler (priority = EventPriority.HIGHEST)
	public void onBreak(BlockBreakEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		
		if (p.getItemInHand() == null)
		      return; 
		    if (!p.getItemInHand().hasItemMeta())
		      return; 
		    if (!p.getItemInHand().getItemMeta().hasLore())
		      return; 
		    if(EnchantMethods.set(b).allows(DefaultFlag.LIGHTER)) {
		    	EnchantMethods.getInstance().Wave(p, b);
		    	EnchantMethods.getInstance().Explosion(p, b);
		    	b.getDrops().clear();
		    	EnchantMethods.getInstance().Research(p);
		    	if(p.hasPermission("enchant.vaporize") && p.hasPermission("enchant.vaporizeunlock")) {
		    	EnchantMethods.getInstance().Vaporize(p, b);
		    	}
		    	if(p.hasPermission("enchant.junkpile") && p.hasPermission("enchant.junkpileunlock")) {
		    	EnchantMethods.getInstance().Junkpile(p);
		    	}
		    	if(p.hasPermission("enchant.stake") && p.hasPermission("enchant.stakeunlock")) {
		    	EnchantMethods.getInstance().Stake(p);
		    	}
		    	if(p.hasPermission("enchant.booster") && p.hasPermission("enchant.boosterunlock")) {
		    	XPEnchantHandler.getInstance().BoosterBreak(p);
		    	}
		    	if(p.hasPermission("enchant.runeparty") && p.hasPermission("enchant.runepartyunlock")) {
		    	XPEnchantHandler.getInstance().RunePartyBreak(p);
		    	}
		    	Mine m = null;
		    	for(Mine mine: ResetHandler.api.getMinesByBlock(b)) {
		    		m = mine;
		    	}
		    	if(m != null)
		    		m.removeBlockFromRegion(b);
		    	
		    	if(m.getMineRegion().getBlocksLeftPercentage() < 75F) {
					ResetHandler.resetMine(m, ResetReason.PERCENTAGE);
				}
		    	
		    		
		    	
		    	
		    	
		    	
		}
	}

}
