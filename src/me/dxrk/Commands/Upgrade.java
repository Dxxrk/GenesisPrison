package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class Upgrade implements CommandExecutor, Listener {
  
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
  public void addTimeStamp(Player p, ItemStack i, String enc, int lvl) {
    Date time = new Date();
    long ms = time.getTime();
    String s = String.valueOf(enc) + ":" + ms + ":" + lvl;
    ItemMeta im = i.getItemMeta();
    List<String> lore = new ArrayList<String>();
    if (i.getItemMeta().hasLore())
      lore = i.getItemMeta().getLore(); 
    lore.add(ChatColor.BLACK + s);
    im.setLore(lore);
    i.setItemMeta(im);
    p.setItemInHand(i);
  }
  
  public int getEffEnchantLvL(String s) {
    if (s.contains("#EFF")) {
      int lvl = 0;
      ArrayList<String> info = new ArrayList<String>();
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = ChatColor.stripColor(s).split(":")).length, b = 0; b < i; ) {
        String ss = arrayOfString[b];
        if (!ss.contains("#"))
          info.add(ss); 
        b++;
      } 
      lvl = Integer.parseInt(info.get(1));
      return lvl;
    } 
    return 0;
  }
  
  public int getForEnchantLvL(String s) {
    if (s.contains("#FOR")) {
      int lvl = 0;
      ArrayList<String> info = new ArrayList<String>();
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = ChatColor.stripColor(s).split(":")).length, b = 0; b < i; ) {
        String ss = arrayOfString[b];
        if (!ss.contains("#"))
          info.add(ss); 
        b++;
      } 
      lvl = Integer.parseInt(info.get(1));
      return lvl;
    } 
    return 0;
  }
  
  public boolean checkExpEnchant(String s) {
    if (s.contains("#EFF") || s.contains("#FOR")) {
      Date time = new Date();
      long ms = -1L;
      long Time = time.getTime();
      ArrayList<String> info = new ArrayList<String>();
      byte b;
      int i;
      String[] arrayOfString;
      for (i = (arrayOfString = ChatColor.stripColor(s).split(":")).length, b = 0; b < i; ) {
        String ss = arrayOfString[b];
        if (!ss.contains("#"))
          info.add(ss); 
        b++;
      } 
      ms = Long.parseLong(info.get(0));
      ms += 3600000L;
      if (ms <= Time)
        return true; 
      return false;
    } 
    return false;
  }
  
  public void onBreak(BlockBreakEvent e) {
    Player p = e.getPlayer();
    if (p.getItemInHand() == null)
      return; 
    ItemStack i = p.getItemInHand();
    if (!i.hasItemMeta())
      return; 
    if (!i.getItemMeta().hasLore())
      return; 
    List<String> lore = new ArrayList<String>();
    int eff = 0;
    int fort = 0;
    for (String s : i.getItemMeta().getLore()) {
      if (checkExpEnchant(s)) {
        eff += getEffEnchantLvL(s);
        fort += getForEnchantLvL(s);
        continue;
      } 
      lore.add(s);
    } 
    if (eff != 0) {
      ItemMeta im = p.getItemInHand().getItemMeta();
      im.setLore(lore);
      p.getItemInHand().setItemMeta(im);
      p.getItemInHand().addUnsafeEnchantment(Enchantment.DIG_SPEED, 
          p.getItemInHand().getEnchantmentLevel(Enchantment.DIG_SPEED) - eff);
    } 
    if (fort != 0) {
      ItemMeta im = p.getItemInHand().getItemMeta();
      im.setLore(lore);
      p.getItemInHand().setItemMeta(im);
      p.getItemInHand().addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 
          p.getItemInHand().getEnchantmentLevel(Enchantment.LOOT_BONUS_BLOCKS) - fort);
    } 
    p.updateInventory();
  }
  
  public boolean canUpgrade(Player p, int lvl) {
    return true;
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

public void addFortune(Player p, ItemStack ii, int loree, int value) {
      int blockss = this.getFortune((String)ii.getItemMeta().getLore().get(loree));
      ItemStack i = ii.clone();
      ItemMeta im = i.getItemMeta();
      List<String> lore = im.getLore();
      lore.set(loree, (Object)ChatColor.GRAY + "Fortune " + (Object)ChatColor.GRAY + (blockss + value));
      im.setLore(lore);
      i.setItemMeta(im);
      p.setItemInHand(i);
      p.updateInventory();
      int blocks = blockss + 1;
  }




	@EventHandler
	public void onCombine(InventoryClickEvent e) {
		Player p = (Player)e.getWhoClicked();
		
		
		
		ItemStack item = e.getCursor();
		ItemStack item2 = e.getCurrentItem();
		
		if (item != null && 
	              item.getType().equals(Material.ENCHANTED_BOOK) && 
	              item.hasItemMeta() && 
	              item.getItemMeta().hasLore()) {
			if (item2 != null && 
		              item2.getType().equals(Material.ENCHANTED_BOOK) && 
		              item2.hasItemMeta() && 
		              item2.getItemMeta().hasLore()) {
				if (((String)item.getItemMeta().getLore().get(1)).contains("Fortune") || (
		                  (String)item.getItemMeta().getLore().get(1)).contains("fortune")) {
					if (((String)item2.getItemMeta().getLore().get(1)).contains("Fortune") || (
			                  (String)item2.getItemMeta().getLore().get(1)).contains("fortune")) {
				
						String amount1 = ChatColor.stripColor((
				                  (String)item.getItemMeta().getLore().get(0)).replace("+", "").replace(" ", ""));
						String amount2 = ChatColor.stripColor((
				                  (String)item2.getItemMeta().getLore().get(0)).replace("+", "").replace(" ", ""));
						
						
						int combine = Integer.parseInt(amount1) + Integer.parseInt(amount2);
						e.setCursor(null);
						
						
						
						ItemStack upgradebook = new ItemStack(Material.ENCHANTED_BOOK);
				          ItemMeta upgmade = upgradebook.getItemMeta();
				          upgmade.setDisplayName(ChatColor.AQUA + "+ Upgrade Book +");
				          ArrayList<String> lore = new ArrayList<String>();
				            lore.add(ChatColor.GRAY + "+ " + ChatColor.LIGHT_PURPLE + combine);
				            lore.add(ChatColor.GRAY + "+ " + ChatColor.LIGHT_PURPLE + "fortune");
				            upgmade.setLore(lore);
				            upgradebook.setItemMeta(upgmade);
						
				            e.setCurrentItem(upgradebook);
						
						
				
					}
				}
			}
			
			
			
		}
		
	}





  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    String prefix = ChatColor.LIGHT_PURPLE + "Upgrade" + ChatColor.GRAY + 
      ChatColor.BOLD + " > " + ChatColor.AQUA;
    if (cmd.getName().equalsIgnoreCase("Upgrade")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      if (p.getItemInHand() != null)
        if (p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
          boolean any = false;
          for (ItemStack item : p.getInventory()) {
            if (item != null && 
              item.getType().equals(Material.ENCHANTED_BOOK) && 
              item.hasItemMeta() && 
              item.getItemMeta().hasLore()) {
              String amount = ChatColor.stripColor((
                  (String)item.getItemMeta().getLore().get(0)).replace("+", "").replace(" ", ""));
              if (isInt(amount)) {
                int mult = item.getAmount();
                int levels = Integer.parseInt(amount) * mult;
                if (((String)item.getItemMeta().getLore().get(1)).contains("Efficiency") || (
                  (String)item.getItemMeta().getLore().get(1)).contains("efficiency")) {
                  if (!canUpgrade(p, levels + p.getItemInHand()
                      .getEnchantmentLevel(Enchantment.DIG_SPEED))) {
                    p.sendMessage(ChatColor.RED + 
                        "You are not a high enough rank to upgrade your pickaxe this high.");
                    return false;
                  } 
                  p.getItemInHand().addUnsafeEnchantment(Enchantment.DIG_SPEED, levels + 
                      p.getItemInHand().getEnchantmentLevel(Enchantment.DIG_SPEED));
                  p.sendMessage(ChatColor.AQUA + "Efficiency " + ChatColor.GRAY + 
                      " has been upgraded by " + ChatColor.AQUA + 
                      String.valueOf(levels));
                  p.getInventory().removeItem(new ItemStack[] { item });
                  p.updateInventory();
                  any = true;
                  continue;
                } 
                if (((String)item.getItemMeta().getLore().get(1)).contains("Fortune") || (
                  (String)item.getItemMeta().getLore().get(1)).contains("fortune")) {
                  any = true;
                  if (!canUpgrade(p, levels + p.getItemInHand()
                      .getEnchantmentLevel(Enchantment.DIG_SPEED))) {
                    p.sendMessage(ChatColor.RED + 
                        "You are not a high enough rank to upgrade your pickaxe this high.");
                    return false;
                  }
                  if((getFortune(p.getItemInHand().getItemMeta().getLore().get(2))+levels) > 10000) {
                	  int max = 10000-getFortune(p.getItemInHand().getItemMeta().getLore().get(2));
                	  addFortune(p, p.getItemInHand(), 2, max);
                	  p.sendMessage(ChatColor.RED + "Max Fortune.");
                	  p.sendMessage(ChatColor.AQUA + "Fortune " + ChatColor.GRAY + 
                              " has been upgraded by " + ChatColor.AQUA + 
                              String.valueOf(levels));
                          p.getInventory().removeItem(new ItemStack[] { item });
                          p.updateInventory();
                	  return false;
                  } else {
                  addFortune(p, p.getItemInHand(), 2, levels);
                  }
                  p.sendMessage(ChatColor.AQUA + "Fortune " + ChatColor.GRAY + 
                      " has been upgraded by " + ChatColor.AQUA + 
                      String.valueOf(levels));
                  p.getInventory().removeItem(new ItemStack[] { item });
                  p.updateInventory();
                }
              } 
            } 
          } 
          if (!any)
            p.sendMessage(String.valueOf(prefix) + ChatColor.RED + "You need an upgrade book!"); 
        } else {
          p.sendMessage(String.valueOf(prefix) + ChatColor.RED + "You need to hold a pickaxe!");
        }  
    } else if (cmd.getName().equalsIgnoreCase("UpgradeBook")) {
      if (args.length == 3) {
        if (sender.hasPermission("Epsilon.Admin") || !(sender instanceof Player)) {
          Player target = Bukkit.getServer().getPlayer(args[0]);
          if (target == null) {
            sender.sendMessage(ChatColor.RED + "Error no player found!");
            return false;
          } 
          ItemStack upgradebook = new ItemStack(Material.ENCHANTED_BOOK);
          ItemMeta upgmade = upgradebook.getItemMeta();
          upgmade.setDisplayName(ChatColor.AQUA + "+ Upgrade Book +");
          ArrayList<String> lore = new ArrayList<String>();
          if (isInt(args[2])) {
            lore.add(ChatColor.GRAY + "+ " + ChatColor.LIGHT_PURPLE + args[2]);
          } else {
            sender.sendMessage(ChatColor.RED + "Error not a number value!");
            return false;
          } 
          if (args[1].equalsIgnoreCase("Efficiency") || args[1].equalsIgnoreCase("Fortune")) {
            lore.add(ChatColor.GRAY + "+ " + ChatColor.LIGHT_PURPLE + args[1]);
          } else {
            sender.sendMessage(ChatColor.RED + "Enchantment not found!");
            return false;
          } 
          upgmade.setLore(lore);
          upgradebook.setItemMeta(upgmade);
          target.getInventory().addItem(new ItemStack[] { upgradebook });
        } 
      } else {
        sender.sendMessage(String.valueOf(prefix) + ChatColor.RED + "/UpgradeBook <Player> <Enchantment> <Levels>");
      } 
    } else if (cmd.getName().equalsIgnoreCase("UpgradeBookRandom")) {
      if (args.length == 3) {
        if (sender.hasPermission("Epsilon.Admin") || !(sender instanceof Player)) {
          Player target = Bukkit.getServer().getPlayer(args[0]);
          if (target == null) {
            sender.sendMessage(ChatColor.RED + "Error no player found!");
            return false;
          } 
          Random r = new Random();
          ItemStack upgradebook = new ItemStack(Material.ENCHANTED_BOOK);
          ItemMeta upgmade = upgradebook.getItemMeta();
          upgmade.setDisplayName(ChatColor.AQUA + "+ Upgrade Book +");
          ArrayList<String> lore = new ArrayList<String>();
          if (isInt(args[2])) {
            lore.add(ChatColor.GRAY + "+ " + ChatColor.LIGHT_PURPLE + r.nextInt(Integer.parseInt(args[2])));
          } else {
            sender.sendMessage(ChatColor.RED + "Error not a number value!");
            return false;
          } 
          if (args[1].equalsIgnoreCase("Efficiency") || args[1].equalsIgnoreCase("Fortune")) {
            lore.add(ChatColor.GRAY + "+ " + ChatColor.LIGHT_PURPLE + args[1]);
          } else {
            sender.sendMessage(ChatColor.RED + "Enchantment not found!");
            return false;
          } 
          upgmade.setLore(lore);
          upgradebook.setItemMeta(upgmade);
          target.getInventory().addItem(new ItemStack[] { upgradebook });
        } 
      } else {
        sender.sendMessage(String.valueOf(prefix) + ChatColor.RED + "/UpgradeBook <Player> <Enchantment> <Levels>");
      } 
    } 
    return true;
  }
}
