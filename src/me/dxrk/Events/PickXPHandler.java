package me.dxrk.Events;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dxrk.Main.Methods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PickXPHandler
implements Listener {
	
	public static PickXPHandler instance = new PickXPHandler();
	public static PickXPHandler getInstance() {
		return instance;
	}
	
	
	public Methods m = Methods.getInstance();
	
	
    public static int getBlocks(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor((String)s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
            char c = arrayOfChar[b];
            if (!isInt(c)) continue;
            lvl.append(c);
        }
        if (isInt(lvl.toString())) {
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }
//test
    public static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }

    public static boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }
    
    
    public void giveXPItem(Player p, int XP) {
    	ItemStack i = new ItemStack(Material.MAGMA_CREAM);
    	ItemMeta im = i.getItemMeta();
    	im.setDisplayName(m.c("&6&lPick XP Voucher"));
    	List<String> lore = new ArrayList<>();
    	lore.add(m.c("&a+"+XP));
    	im.setLore(lore);
    	i.setItemMeta(im);
    	p.getInventory().addItem(i);
    }
    
    
    
    
    
    
    @EventHandler
	public void onCombine(InventoryClickEvent e) {
		
		
		
		ItemStack item = e.getCursor();
		ItemStack item2 = e.getCurrentItem();
		
		if (item != null && 
	              item.getType().equals(Material.MAGMA_CREAM) && 
	              item.hasItemMeta() && 
	              item.getItemMeta().hasLore()) {
			if (item2 != null && 
		              item2.getType().equals(Material.DIAMOND_PICKAXE) && 
		              item2.hasItemMeta() && 
		              item2.getItemMeta().hasLore()) {
				if (item.getItemMeta().getDisplayName().equals(m.c("&6&lPick XP Voucher"))) {
						
						
						int xptoadd = getBlocks(item.getItemMeta().getLore().get(0));
						int xpbase = getBlocks(item2.getItemMeta().getLore().get(1));
						int amt = e.getCursor().getAmount();
						
						if(amt == 1) {
			            	e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
			            } else {
			            	e.getWhoClicked().getItemOnCursor().setAmount(amt - 1);
			            }
						
						
						ItemStack upgradebook = item2.clone();
				          ItemMeta upgmade = upgradebook.getItemMeta();
				          List<String> lore = item2.getItemMeta().getLore();
				          lore.set(1, m.c("&7XP: &b"+(xptoadd+ xpbase)));
				            upgmade.setLore(lore);
				            upgradebook.setItemMeta(upgmade);
				            lore.clear();
						
				            e.setCurrentItem(upgradebook);
				            
				            
				            e.setCancelled(true);
				            
				           /* ItemStack cursor = new ItemStack(Material.MAGMA_CREAM);
				            ItemMeta cm = cursor.getItemMeta();
				            List<String> clore = new ArrayList<>();
				            clore.add(m.c("&a+"+xptoadd));
				            cm.setDisplayName(m.c("&6&lPick XP Voucher"));
				            cm.setLore(clore);
				            cursor.setItemMeta(cm);
				            int amt = (item.getAmount()-1);
							cursor.setAmount(amt);
							e.setCursor(cursor);*/
						
				
					}
				} else if (item2 != null && 
			              item2.getType().equals(Material.MAGMA_CREAM) && 
			              item2.hasItemMeta() && 
			              item2.getItemMeta().hasLore()) {
					if (item.getItemMeta().getDisplayName().equals(m.c("&6&lPick XP Voucher")) && item2.getItemMeta().getDisplayName().equals(m.c("&6&lPick XP Voucher"))) {
						e.setCancelled(true);
						
						int xp1 = getBlocks(item.getItemMeta().getLore().get(0));
						int xp2 = getBlocks(item2.getItemMeta().getLore().get(0));
						int amt = e.getCursor().getAmount();
						
						if(amt == 1) {
			            	e.getWhoClicked().setItemOnCursor(new ItemStack(Material.AIR));
			            } else {
			            	e.getWhoClicked().getItemOnCursor().setAmount(amt - 1);
			            }
						
						ItemStack ma = new ItemStack(Material.MAGMA_CREAM);
						ItemMeta mm = ma.getItemMeta();
						List<String> lore = new ArrayList<>();
						lore.add(m.c("&a+"+(xp1+xp2)));
						mm.setDisplayName(m.c("&6&lPick XP Voucher"));
						mm.setLore(lore);
						ma.setItemMeta(mm);
						e.setCurrentItem(ma);
						
						
						
					}
					
				}
			
		}
			}
			
		
		
	
    
    
    

    public void addXP(Player p, ItemStack ii, int XP) {
        int blockss = getBlocks((String)ii.getItemMeta().getLore().get(1));
        int blocks = XP* BoostsHandler.xp;
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(1, (Object)ChatColor.GRAY + "XP: " + (Object)ChatColor.AQUA + (blockss + blocks));
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
    }
    
    public void removeXP(Player p, ItemStack ii, int XP) {
        int blockss = getBlocks(ii.getItemMeta().getLore().get(1));
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(1, ChatColor.GRAY + "XP: " + ChatColor.AQUA + (blockss - XP));
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
    }
    

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) {
            return;
        }
        if (e.isCancelled()) {
            return;
        }
        ItemStack i = p.getItemInHand();
        if (!i.hasItemMeta()) {
            return;
        }
        if (!i.getItemMeta().hasLore()) {
            ItemMeta im = i.getItemMeta();
            ArrayList<String> list = new ArrayList<String>();
            list.add((Object)ChatColor.GRAY + "XP " + (Object)ChatColor.AQUA + "1");
            im.setLore(list);
            p.getItemInHand().setItemMeta(im);
            p.updateInventory();
            return;
        }
        
        WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        ApplicableRegionSet set = wg.getRegionManager(p.getWorld()).getApplicableRegions(e.getBlock().getLocation());
        if (!set.allows(DefaultFlag.LIGHTER)) {
            return;
        }
        List<String> lore = i.getItemMeta().getLore();
        boolean hasBlocks = false;
        int lorenum = -1;
        for (int x = 0; x < lore.size(); ++x) {
            String s = (String)lore.get(x);
            if (!s.contains("XP:")) continue;
            lorenum = x;
            hasBlocks = true;
        }
        if (!hasBlocks) {
            ItemMeta im = i.getItemMeta();
            lore.add((Object)ChatColor.GRAY + "XP: " + (Object)ChatColor.AQUA + "1");
            im.setLore(lore);
            p.getItemInHand().setItemMeta(im);
            p.updateInventory();
            return;
        }
        int chance = 0;
        List<String> llore = p.getItemInHand().getItemMeta().getLore();
        for(int x = 0; x < llore.size(); x++) {
        	String s = llore.get(x);
        	if(ChatColor.stripColor(s).contains("Double XP")) {
        		chance += m.getBlocks(s);
        	}
        }
        Random r = new Random();
        int ri = r.nextInt(100);
        if(ri <= chance) {
        	this.addXP(p, p.getItemInHand(), 2);
        } else {
        this.addXP(p, p.getItemInHand(), 1);
        }
    }
}

