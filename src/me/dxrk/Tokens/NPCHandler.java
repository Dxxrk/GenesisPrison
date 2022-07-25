package me.dxrk.Tokens;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import me.dxrk.Main.Main;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;

public class NPCHandler implements Listener{
	
	Tokens tokens = Tokens.getInstance();
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	public boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
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
	 	
	 	
	 	@EventHandler
	    public void tokenshopopen(PlayerInteractAtEntityEvent e ) {
	    	Player p = e.getPlayer();
	    	Entity mob = e.getRightClicked();
	    	
	    	if (!mob.isCustomNameVisible())
	            return; 
	    	 
	    	if(ChatColor.stripColor(e.getRightClicked().getName()).contains("Token Shop")) {
	    		this.tokens.openEtShop(p);
	    	}
	    	
	    }
	 	
	 	
	
	
	 public Inventory Collector() {
	    	Inventory CollectorInv = Bukkit.createInventory(null, InventoryType.HOPPER, c("&5&lCollector"));
	    	ItemStack collect = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)10);
	    	ItemMeta cm = collect.getItemMeta();
	    	List<String> lore = new ArrayList<>();
	    	cm.setDisplayName(c("&5Collect"));
	    	cm.addEnchant(Enchantment.DIG_SPEED, 32000, true);
		    cm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		    lore.add(c("&7Click to collect your tokens!"));
		    lore.add(c("&7Each enchant = 100 Tokens!"));
		    cm.setLore(lore);
		    collect.setItemMeta(cm);
	    	
		    CollectorInv.setItem(0, collect);
		    CollectorInv.setItem(1, collect);
		    CollectorInv.setItem(3, collect);
		    CollectorInv.setItem(4, collect);
	    	
	    	return CollectorInv;
	    }
	 
	 
	 
	 
	
	 
	 
	    
	    @EventHandler
	    public void collectorclick(PlayerInteractAtEntityEvent e ) {
	    	Player p = e.getPlayer();
	    	Entity mob = e.getRightClicked();
	    	 
	    	
	    	if (!mob.isCustomNameVisible())
	            return; 
	    	
	    	
	    	
	    	if(ChatColor.stripColor(e.getRightClicked().getName()).contains("Banker")) {
	    		p.sendMessage(c("&aComing Soon.."));
	    	} 
	    	
	    }
	    
	    
	    
	    
	    
	    
	    @EventHandler
	    public void collectorclick(InventoryClickEvent e ) {
	    	Player p = (Player)e.getWhoClicked();
	    	if(e.getInventory() == null) return;
    		if(e.getClickedInventory() == null) return;
	    	if(e.getInventory().getName().equals(c("&5&lCollector"))) {
	    		
	    		if(e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY) {
	    			e.setCancelled(true);
	    		}
	    		
	    		
	    		
	    			
	    		
	    	if(e.getClickedInventory().getName().equals(c("&5&lCollector"))) {
	    		if(e.getSlot() == 0 || e.getSlot() == 1 || e.getSlot() == 3 || e.getSlot() == 4) {
	    			e.setCancelled(true);
	    			if(e.getClickedInventory().getItem(2) == null) return;
	    			if(!e.getClickedInventory().getItem(2).getType().equals(Material.DIAMOND_PICKAXE)) return;
	    			
	    			int enchants = 0;
	    			
	    			List<String> lore = e.getClickedInventory().getItem(2).getItemMeta().getLore();
	    		    int x;
	    		    for (x = 0; x < lore.size(); x++) {
	    		      String s = lore.get(x);
	    		      
	    		      if(ChatColor.stripColor(s).contains("Discovery"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Encounter"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Wave"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Explosion"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Corrupt"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Greed"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Research"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("TokenFinder"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Vaporize"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Collector"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Junkpile"))
	    		    	  enchants = enchants+1;
	    		      if(ChatColor.stripColor(s).contains("Stake"))
	    		    	  enchants = enchants+1;
	    		      
	    		    }
	    		    e.getInventory().setItem(2, null);
	    			p.openInventory(Collector());
	    			Tokens.getInstance().addTokens(p, enchants*100);
	    			p.sendMessage(c("&7&k&li&b&lTokens&7&l&ki&r &7➤ &b+"+(enchants*100)));
	    			
	    		
	    		}
	    	}
	    	}
	    }
	    
	    @EventHandler
	    public void colectorclose(InventoryCloseEvent  e) {
	    	if(e.getInventory().getName().equals(c("&5&lCollector"))) {
	    		
	    		if(e.getInventory().getItem(2) == null) return;
	    		
	    		
	    		e.getPlayer().getInventory().addItem(e.getInventory().getItem(2));
	    		Collector().setItem(2, null);
	    	}
	    }

}
