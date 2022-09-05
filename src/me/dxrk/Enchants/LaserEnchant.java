package me.dxrk.Enchants;

import java.util.List;

import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class LaserEnchant implements Listener{
	
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
	
	

}
