package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import mkremins.fanciful.FancyMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.*;

public class CrateHandler implements Listener, CommandExecutor {
  public SettingsManager settings = SettingsManager.getInstance();
  
  public FileConfiguration cr = this.settings.getCrates();
  

  
  public static CrateHandler instance = new CrateHandler();
  
  public static CrateHandler getInstance() {
    return instance;
  }
  
  String c(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
  private double getRelativeCoord(int i) {
    double d = i;
    d = (d < 0.0D) ? (d - 0.5D) : (d + 0.5D);
    return d;
  }
  
  
  public Location getCenter(Location loc) {
    return new Location(loc.getWorld(), getRelativeCoord(loc.getBlockX()), getRelativeCoord(loc.getBlockY()), 
        getRelativeCoord(loc.getBlockZ()));
  }
  
  public void effectsAF() {
    (new BukkitRunnable() {
        public void run() {
          try {
            for (Location loc : CrateHandler.this.locations())
              loc.getWorld().playEffect(loc, Effect.POTION_SWIRL, 2); 
          } catch (Exception localException) {
            Bukkit.getLogger().info(CrateHandler.this.c("&4FAILED TO LOAD PARTICLES AROUND CRATES!"));
          } 
        }
      }).runTaskTimer(Main.plugin, 0L, 5L);
  }
  
  public List<Location> locations() {
    ArrayList<Location> locations = new ArrayList<>();
    for (String s : this.cr.getKeys(false)) {
      if (!s.contains(".")) {
        Location loc = new Location(Bukkit.getWorld(this.cr.getString(s + ".Location.World")), this.cr.getInt(s + ".Location.X"), this.cr.getInt(s + ".Location.Y"), this.cr.getInt(s + ".Location.Z"));
        locations.add(loc);
      } 
    } 
    return locations;
  }
  
  public boolean isCrate(Block b) {
    boolean is = false;
    Location l = b.getLocation();
    String w = l.getWorld().getName();
    int x = l.getBlockX();
    int y = l.getBlockY();
    int z = l.getBlockZ();
    for (Location loc : locations()) {
      if (loc.getWorld().getName().equals(w) && loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z)
        is = true; 
    } 
    return !is;
  }
  
  public String getCrate(Block b) {
    Location loc = b.getLocation();
    for (String s : this.cr.getKeys(true)) {
      if (!s.contains(".") && this.cr.getInt(s + ".Location.X") == loc.getBlockX() && 
        this.cr.getInt(s + ".Location.Y") == loc.getBlockY() && this.cr.getInt(s + ".Location.Z") == loc.getBlockZ() && 
        this.cr.getString(s + ".Location.World").equalsIgnoreCase(b.getLocation().getWorld().getName()))
        return s; 
    } 
    return null;
  }
  
  public ItemStack getKey(String key, int amt) {
    ItemStack keyy = new ItemStack(Material.getMaterial(this.cr.getInt(key + ".Key.ID")), amt);
    ItemMeta am = keyy.getItemMeta();
    am.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.cr.getString(key + ".Key.Name")));
    am.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', this.cr.getString(key + ".Key.Lore"))));
    keyy.setItemMeta(am);
    return keyy;
  }
  
  public boolean isKey(ItemStack a, String s) {
    if (!a.hasItemMeta())
      return false; 
    if (!a.getItemMeta().hasLore())
      return false; 
    if (!a.getItemMeta().hasDisplayName())
      return false; 
    if (!c(this.cr.getString(s + ".Key.Name")).equals(a.getItemMeta().getDisplayName()))
      return false; 
    if (!c(this.cr.getString(s + ".Key.Lore")).equals(a.getItemMeta().getLore().get(0)))
      return false;
    return this.cr.getInt(s + ".Key.ID") == a.getTypeId();
  }
  
  public int getRows(String s) {
    return this.cr.getInt(s + ".Rows");
  }
  
  public ItemStack spacer() {
    ItemStack a = new ItemStack(Material.AIR);
    return a;
  }
  
  public String checkForSlot(ItemStack item, String crate) {
    String slot = "";
    for (String s : getRewards(crate)) {
      ItemStack x = loadItem(crate, "." + s);
      boolean same = x.getAmount() == item.getAmount();
        if (x.getType() != item.getType())
            same = false;
      if (!x.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()))
        same = false; 
      if (same)
        slot = s; 
    } 
    return slot;
  }
  
  public boolean slotHasCommands(String crate, String s) {
      return this.cr.get(crate + "." + s + ".Command") != null;
  }
  
  public List<String> getCommands(String crate, String s) {
    return this.cr.getStringList(crate + "." + s + ".Command");
  }
  
  public void loadCrate(Player p, String s) {
    if (!s.contains(".")) {
      Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, c("&c" + s + " Crate"));
      for (int i = 0; i < inv.getSize(); i++) {
        if (this.cr.get(s + ".Slot" + i + ".ItemID") != null) {
          inv.setItem(i, loadItem(s, ".Slot" + i));
        } else {
          inv.setItem(i, spacer());
        } 
      } 
      p.openInventory(inv);
    } 
  }
  
  public void spinCrate(final Player p, final String crate, final Inventory inv, int time, final Sound s, final boolean end) {
    (new BukkitRunnable() {
        public void run() {
          if (end) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| " + inv.getItem(13).getItemMeta().getDisplayName()));
            p.closeInventory();
            String slot = CrateHandler.this.checkForSlot(inv.getItem(13), crate);
            if (CrateHandler.this.slotHasCommands(crate, slot)) {
              for (String ss : CrateHandler.this.getCommands(crate, slot)) {
                ss = ss.replaceAll("%PLAYER%", p.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
              } 
            } else {
            	if(inv.getItem(13).getType() == Material.DIAMOND_PICKAXE) {
            		ItemStack hand = inv.getItem(13).clone();
            		ItemMeta am = hand.getItemMeta();
            	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
            	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            	    hand.setItemMeta(am);
            	    p.getInventory().addItem(hand);
            	} else {
              p.getInventory().addItem(inv.getItem(13).clone());
            	}
            } 
          } else {
            inv.setItem(17, inv.getItem(16).clone());
            inv.setItem(16, inv.getItem(15).clone());
            inv.setItem(15, inv.getItem(14).clone());
            inv.setItem(14, inv.getItem(13).clone());
            inv.setItem(13, inv.getItem(12).clone());
            inv.setItem(12, inv.getItem(11).clone());
            inv.setItem(11, inv.getItem(10).clone());
            inv.setItem(10, inv.getItem(9).clone());
            inv.setItem(9, CrateHandler.this.loadItem(crate, "." + CrateHandler.this.getRandom(crate)));
            p.playSound(p.getLocation(), s, 1.0F, 1.0F);
          } 
        }
      }).runTaskLater(Main.plugin, time);
  }
  
  public void openCrate(String crate, Player p) {
    Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&cOpening Crate..."));
    int i;
    for (i = 0; i < inv.getSize(); i++)
      inv.setItem(i, spacer()); 
    inv.setItem(4, new ItemStack(Material.REDSTONE_TORCH_ON, 1));
    inv.setItem(22, new ItemStack(Material.REDSTONE_TORCH_ON, 1));
    inv.setItem(9, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(10, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(11, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(12, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(13, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(14, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(15, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(16, loadItem(crate, "." + getRandom(crate)));
    inv.setItem(17, loadItem(crate, "." + getRandom(crate)));
    p.openInventory(inv);
    for (i = 0; i < 60; i++)
      spinCrate(p, crate, inv, i, Sound.WOOD_CLICK, false); 
    spinCrate(p, crate, inv, 80, Sound.CHICKEN_EGG_POP, true);
  }
  
  
  public int getValueInt(String s) {
      StringBuilder lvl = new StringBuilder();
      s = ChatColor.stripColor(s);
      char[] arrayOfChar = s.toCharArray();
      int i = arrayOfChar.length;
      for (int b = 0; b < i; b = (byte)(b + 1)) {
          char c = arrayOfChar[b];
          if (!this.isInt(c)) continue;
          lvl.append(c);
      }
      if (this.isInt(lvl.toString())) {
          return Integer.parseInt(lvl.toString());
      }
      return -1;
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
  public double getValuedbl(String s) {
      StringBuilder lvl = new StringBuilder();
      s = ChatColor.stripColor(s);
      char[] arrayOfChar = s.toCharArray();
      int i = arrayOfChar.length;
      for (int b = 0; b < i; b = (byte)(b + 1)) {
          char c = arrayOfChar[b];
          if (!this.isDbl(c)) continue;
          lvl.append(c);
      }
      if (this.isDbl(lvl.toString())) {
          return Double.parseDouble(lvl.toString());
      }
      return -1;
  }
	
	public boolean isDbl(String s) {
      try {
          double i = Double.parseDouble(s);
          return true;
      }
      catch (Exception e1) {
          return false;
      }
  }

  public boolean isDbl(char ss) {
      String s = String.valueOf(ss);
      try {
          double i = Double.parseDouble(s);
          return true;
      }
      catch (Exception e1) {
          return false;
      }
  }
  
  
  
  
 
  
  public void openSneakingCrate(String crate, Player p) {
    ItemStack won = loadItem(crate, "." + getRandom(crate));
    String slot = checkForSlot(won, crate);
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| " + won.getItemMeta().getDisplayName()));
    if (slotHasCommands(crate, slot)) {
      for (String ss : getCommands(crate, slot)) {
        ss = ss.replaceAll("%PLAYER%", p.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
      } 
    } else {
    	if(won.getType() == Material.DIAMOND_PICKAXE) {
    		ItemStack hand = won.clone();
    		ItemMeta am = hand.getItemMeta();
    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	    hand.setItemMeta(am);
    	    p.getInventory().addItem(hand);
    	} else {
      p.getInventory().addItem(won);
    	}
    	p.updateInventory();
    } 
  }
  
  public String formatKey(String s) {
    if (s.equalsIgnoreCase("vote"))
      return "Vote"; 
    if (s.equalsIgnoreCase("alpha"))
      return "Midas"; 
    if (s.equalsIgnoreCase("beta"))
      return "Poseidon"; 
    if (s.equalsIgnoreCase("omega"))
      return "Hades"; 
    if (s.equalsIgnoreCase("seasonal"))
      return "Oblivion"; 
    if (s.equalsIgnoreCase("token"))
      return "Polis"; 
    if (s.equalsIgnoreCase("rank"))
      return "Olympus"; 
    if (s.equalsIgnoreCase("community"))
        return "Prestige";
    return "Error";
  }
  
  
  public String getRandom(String s) {
    List<String> items = new ArrayList<>();
    Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, "test");
    for (int i = 0; i < inv.getSize(); i++) {
      if (this.cr.get(s + ".Slot" + i + ".Name") != null) {
      for (int xi = 0; xi < this.cr.getInt(s + ".Slot" + i + ".Chance"); xi++) {
               items.add("Slot" + i);
      }
    } 
    }
    
    
    Random r = new Random();
    int n = r.nextInt(items.size());
    String slot = items.get(n);
    return slot;
  }
  
  public List<String> getRewards(String s) {
	  List<String> items = new ArrayList<>();
	    Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, "test");
	    for (int i = 0; i < inv.getSize(); i++) {
	      if (this.cr.get(s + ".Slot" + i + ".Name") != null) {
	      for (int xi = 0; xi < this.cr.getInt(s + ".Slot" + i + ".Chance"); xi++) {
	               items.add("Slot" + i);
	      }
	    } 
	    }
    return items;
  }
  
  @SuppressWarnings("deprecation")
public ItemStack loadItem(String s, String slot) {
    String name = this.cr.getString(s + slot + ".Name");
    int amt = 1;
    if (this.cr.getInt(s + slot + ".Amount") > 0)
      amt = this.cr.getInt(s + slot + ".Amount"); 
    int id = 1;
    if (this.cr.getInt(s + slot + ".ItemID") > 0)
      id = this.cr.getInt(s + slot + ".ItemID"); 
    int sh = 0;
    if (this.cr.get(s + slot + ".Short") != null)
      sh = this.cr.getInt(s + slot + ".Short"); 
    List<String> lore = null;
    if (this.cr.getStringList(s + slot + ".Lore") != null)
      lore = this.cr.getStringList(s + slot + ".Lore"); 
    List<String> ench = null;
    if (this.cr.getStringList(s + slot + ".Enchantments") != null)
      ench = this.cr.getStringList(s + slot + ".Enchantments"); 
    ItemStack item = new ItemStack(id, amt, (short)sh);
    ItemMeta am = item.getItemMeta();
    am.setDisplayName(c(name));
    if (lore != null) {
      List<String> newlore = new ArrayList<>();
      for (String l : lore)
        newlore.add(c(l.replaceAll("�", "&"))); 
      am.setLore(newlore);
    } 
    if(id == 373) {
    	am.addItemFlags(ItemFlag.values());
    }
    item.setItemMeta(am);
    if (ench != null)
      for (String e : ench)
        item.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(e.split(";")[0])), Integer.parseInt(e.split(";")[1]));
    return item;
  }
  public static String format(double amt) {
	    if (amt >= 1.0E15D)
	      return String.format("%.1f Quad", amt / 1.0E15D);
	    if (amt >= 1.0E12D)
	      return String.format("%.1f Tril", amt / 1.0E12D);
	    if (amt >= 1.0E9D)
	      return String.format("%.1f Bil", amt / 1.0E9D);
	    if (amt >= 1000000.0D)
	      return String.format("%.1f Mil", amt / 1000000.0D);
	    return NumberFormat.getNumberInstance(Locale.US).format(amt);
	  }
  @EventHandler
  public void onInt(PlayerInteractEvent e) {
    if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
      if (isCrate(e.getClickedBlock()))
        return; 
      e.setCancelled(true);
      Player p = e.getPlayer();
      loadCrate(p, getCrate(e.getClickedBlock()));
    } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
      if (isCrate(e.getClickedBlock()))
        return; 
      e.setCancelled(true);
      if (isKey(e.getPlayer().getItemInHand(), getCrate(e.getClickedBlock()))) {
        Player p = e.getPlayer();
        
        if (p.isSneaking()) {
        	if (p.getItemInHand().getAmount() == 1) {
                p.setItemInHand(null);
                openSneakingCrate(getCrate(e.getClickedBlock()), p);
              } else {
            	  ItemStack key = p.getItemInHand().clone();
                p.setItemInHand(null);
          	  double multi = 0;
          	  int tokens = 0;
          	  double money = 0;
          	  ArrayList<String> rw = new ArrayList<>();
          	  ArrayList<String> rww = new ArrayList<>();
          	int i;
      	  for (i = 0; i < key.getAmount(); i++) {
      		ItemStack won = loadItem(getCrate(e.getClickedBlock()), "." + getRandom(getCrate(e.getClickedBlock())));
    	    String slot = checkForSlot(won, getCrate(e.getClickedBlock()));
    	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
    	    
    	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
    	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
    	     
    	    if(name.contains("Tokens")) {
    	    	tokens += getValueInt(name);
    	    	
    	    } else if(name.contains("Multi")) {
    	    	
    	    	String[] nn = name.split(" ");
    	    	
    	    	double mm = Double.parseDouble(nn[0]);
    	    	
    	    	multi += mm;
    	    }else if(name.contains("%")) {

                int percent = getValueInt(name);
                double per = ((double)percent)/100;
    	    	double m = RankupHandler.getInstance().rankPrice(p) * per;
    	    	money += m;
    	    	
    	    } else {
    	    	rww.add(c(won.getItemMeta().getDisplayName()));
    	    }
    	    
    	    
    	    
    	    if (slotHasCommands(getCrate(e.getClickedBlock()), slot)) {
    	      for (String ss : getCommands(getCrate(e.getClickedBlock()), slot)) {
    	        ss = ss.replaceAll("%PLAYER%", p.getName());
    	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
    	      } 
    	    } else {
    	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
    	    		ItemStack hand = won.clone();
    	    		ItemMeta am = hand.getItemMeta();
    	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
    	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	    	    hand.setItemMeta(am);
    	    	    p.getInventory().addItem(hand);
    	    	} else {
    	      p.getInventory().addItem(won);
    	    	}
    	    	p.updateInventory();
    	    }
    	    p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
	    	double percents;
	        p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
	        percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
	        double dmultiply = percents*10.0;
	        double dRound = Math.round(dmultiply) /10.0;
	        if(RankupHandler.getInstance().getRank(p) == 100) {
	        	p.getScoreboard().getTeam("percent").setSuffix(c("&c/prestige"));
	        }
	        
	        else {
	        	if(dRound>=100.0) {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
	        	} else {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
	        }
	        }
	        p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))));
      	  }
      	  this.settings.savePlayerData();
                // String[] rewards = {c("&7&lMulti: "+multi+"x"), c("&7&lTokens: "+tokens), c("&7&lMoney: "+Main.formatAmt(money)), c("&7Other:")};
      	
      	rw.add(c("&f&lMulti &8| &b"+multi+"x"));
     	rw.add(c("&f&lTokens &8| &b"+tokens));
     	rw.add(c("&f&lMoney &8| &b"+Main.formatAmt(money)));
                for (String s : rw) {
                    p.sendMessage(c(s));
                }

                ArrayList<String> rw2 = new ArrayList<>(rww);
      	  
      	  FancyMessage reward = new FancyMessage("");
      	  reward.then(c("&f&lOther &8| &b(Hover)")).tooltip(rw2);
      	  reward.send(p);
            }
          
        } else {
        	if (p.getItemInHand().getAmount() == 1) {
                p.setItemInHand(null);
              } else {
                ItemStack key = p.getItemInHand().clone();
                key.setAmount(key.getAmount() - 1);
                p.setItemInHand(key);
              }
          openSneakingCrate(getCrate(e.getClickedBlock()), p);
        } 
      } else {
        e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| &bYou must be holding a &4" + getCrate(e.getClickedBlock()) + " &bKey."));
      } 
    } 
  }
  
  
  
  public void openall(Player p, int alpha, int beta, int omega, int token, int seasonal, int rank, int community, int vote) {
	  double multi = 0;
  	  int tokens = 0;
  	  double money = 0;
  	  ArrayList<String> rw = new ArrayList<>();
  	  ArrayList<String> rww = new ArrayList<>();
  	int i;
	  for (i = 0; i < alpha; i++) {
		ItemStack won = loadItem("Midas", "." + getRandom("Midas"));
    String slot = checkForSlot(won, "Midas");
    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
    
    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
     
    if(name.contains("Tokens")) {
    	tokens += getValueInt(name);
    	
    } else if(name.contains("Multi")) {
    	
    	String[] nn = name.split(" ");
    	
    	double mm = Double.parseDouble(nn[0]);
    	
    	multi += mm;
    }else if(name.contains("%")) {

        int percent = getValueInt(name);
        double per = ((double)percent)/100;
        double m = RankupHandler.getInstance().rankPrice(p) * per;
        money += m;

    } else {
    	rww.add(c(won.getItemMeta().getDisplayName()));
    }
    
    
    
    if (slotHasCommands("Midas", slot)) {
      for (String ss : getCommands("Midas", slot)) {
        ss = ss.replaceAll("%PLAYER%", p.getName());
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
      } 
    } else {
    	if(won.getType() == Material.DIAMOND_PICKAXE) {
    		ItemStack hand = won.clone();
    		ItemMeta am = hand.getItemMeta();
    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	    hand.setItemMeta(am);
    	    p.getInventory().addItem(hand);
    	} else {
      p.getInventory().addItem(won);
    	}
    	p.updateInventory();
    }
   
	  }
	  for (i = 0; i < beta; i++) {
			ItemStack won = loadItem("Poseidon", "." + getRandom("Poseidon"));
	    String slot = checkForSlot(won, "Poseidon");
	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
	    
	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
	     
	    if(name.contains("Tokens")) {
	    	tokens += getValueInt(name);
	    	
	    } else if(name.contains("Multi")) {
	    	
	    	String[] nn = name.split(" ");
	    	
	    	double mm = Double.parseDouble(nn[0]);
	    	
	    	multi += mm;
	    }else if(name.contains("%")) {

            int percent = getValueInt(name);
            double per = ((double)percent)/100;
            double m = RankupHandler.getInstance().rankPrice(p) * per;
            money += m;

        } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Poseidon", slot)) {
	      for (String ss : getCommands("Poseidon", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(hand);
	    	} else {
	      p.getInventory().addItem(won);
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < omega; i++) {
			ItemStack won = loadItem("Hades", "." + getRandom("Hades"));
	    String slot = checkForSlot(won, "Hades");
	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
	    
	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
	     
	    if(name.contains("Tokens")) {
	    	tokens += getValueInt(name);
	    	
	    } else if(name.contains("Multi")) {
	    	
	    	String[] nn = name.split(" ");
	    	
	    	double mm = Double.parseDouble(nn[0]);
	    	
	    	multi += mm;
	    }else if(name.contains("%")) {

            int percent = getValueInt(name);
            double per = ((double)percent)/100;
            double m = RankupHandler.getInstance().rankPrice(p) * per;
            money += m;

        } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Hades", slot)) {
	      for (String ss : getCommands("Hades", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(hand);
	    	} else {
	      p.getInventory().addItem(won);
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < token; i++) {
			ItemStack won = loadItem("Polis", "." + getRandom("Polis"));
	    String slot = checkForSlot(won, "Polis");
	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
	    
	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
	     
	    if(name.contains("Tokens")) {
	    	tokens += getValueInt(name);
	    	
	    } else if(name.contains("Multi")) {
	    	
	    	String[] nn = name.split(" ");
	    	
	    	double mm = Double.parseDouble(nn[0]);
	    	
	    	multi += mm;
	    }else if(name.contains("%")) {

            int percent = getValueInt(name);
            double per = ((double)percent)/100;
            double m = RankupHandler.getInstance().rankPrice(p) * per;
            money += m;

        } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Polis", slot)) {
	      for (String ss : getCommands("Polis", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(hand);
	    	} else {
	      p.getInventory().addItem(won);
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < seasonal; i++) {
			ItemStack won = loadItem("Oblivion", "." + getRandom("Oblivion"));
	    String slot = checkForSlot(won, "Oblivion");
	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
	    
	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
	     
	    if(name.contains("Tokens")) {
	    	tokens += getValueInt(name);
	    	
	    } else if(name.contains("Multi")) {
	    	
	    	String[] nn = name.split(" ");
	    	
	    	double mm = Double.parseDouble(nn[0]);
	    	
	    	multi += mm;
	    }else if(name.contains("%")) {

            int percent = getValueInt(name);
            double per = ((double)percent)/100;
            double m = RankupHandler.getInstance().rankPrice(p) * per;
            money += m;

        } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Oblivion", slot)) {
	      for (String ss : getCommands("Oblivion", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(hand);
	    	} else {
	      p.getInventory().addItem(won);
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < rank; i++) {
			ItemStack won = loadItem("Olympus", "." + getRandom("Olympus"));
	    String slot = checkForSlot(won, "Olympus");
	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
	    
	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
	     
	    if(name.contains("Tokens")) {
	    	tokens += getValueInt(name);
	    	
	    } else if(name.contains("Multi")) {
	    	
	    	String[] nn = name.split(" ");
	    	
	    	double mm = Double.parseDouble(nn[0]);
	    	
	    	multi += mm;
	    }else if(name.contains("%")) {

            int percent = getValueInt(name);
            double per = ((double)percent)/100;
            double m = RankupHandler.getInstance().rankPrice(p) * per;
            money += m;

        } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Olympus", slot)) {
	      for (String ss : getCommands("Olympus", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(hand);
	    	} else {
	      p.getInventory().addItem(won);
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < community; i++) {
			ItemStack won = loadItem("Prestige", "." + getRandom("Prestige"));
	    String slot = checkForSlot(won, "Prestige");
	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
	    
	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
	     
	    if(name.contains("Tokens")) {
	    	tokens += getValueInt(name);
	    	
	    } else if(name.contains("Multi")) {
	    	
	    	String[] nn = name.split(" ");
	    	
	    	double mm = Double.parseDouble(nn[0]);
	    	
	    	multi += mm;
	    }else if(name.contains("%")) {

            int percent = getValueInt(name);
            double per = ((double)percent)/100;
            double m = RankupHandler.getInstance().rankPrice(p) * per;
            money += m;

        } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Prestige", slot)) {
	      for (String ss : getCommands("Prestige", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(hand);
	    	} else {
	      p.getInventory().addItem(won);
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < vote; i++) {
			ItemStack won = loadItem("Vote", "." + getRandom("Vote"));
	    String slot = checkForSlot(won, "Vote");
	    String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());
	    
	    int crates = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".CratesOpened");
	    this.settings.getPlayerData().set(p.getUniqueId().toString()+".CratesOpened", crates+1);
	     
	    if(name.contains("Tokens")) {
	    	tokens += getValueInt(name);
	    	
	    } else if(name.contains("Multi")) {
	    	
	    	String[] nn = name.split(" ");
	    	
	    	double mm = Double.parseDouble(nn[0]);
	    	
	    	multi += mm;
	    }else if(name.contains("%")) {

            int percent = getValueInt(name);
            double per = ((double)percent)/100;
            double m = RankupHandler.getInstance().rankPrice(p) * per;
            money += m;

        } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Vote", slot)) {
	      for (String ss : getCommands("Vote", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(hand);
	    	} else {
	      p.getInventory().addItem(won);
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  this.settings.savePlayerData();
      // String[] rewards = {c("&7&lMulti: "+multi+"x"), c("&7&lTokens: "+tokens), c("&7&lMoney: "+Main.formatAmt(money)), c("&7Other:")};
	if(multi >0)
	    rw.add(c("&f&lMulti &8| &b"+multi+"x"));
	if(tokens > 0)
	    rw.add(c("&f&lTokens &8| &b"+tokens));
	if(money >0)
	    rw.add(c("&f&lMoney &8| &b$"+Main.formatAmt(money)));
	if(rw.size() >0) {
        for (String s : rw) {
            p.sendMessage(c(s));
        }
	}

      ArrayList<String> rw2 = new ArrayList<>(rww);
	  
	  FancyMessage reward = new FancyMessage("");
	  reward.then(c("&f&lOther &8| &b(Hover)")).tooltip(rw2);
	  if(rw2.size() >0) {
	  reward.send(p);
	  }
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getInventory().getName().contains("Crate"))
      e.setCancelled(true); 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	  if(label.equalsIgnoreCase("crateinfo")) {
		  if(args.length == 1) {
			  Player p = (Player)sender;
			  String crate = args[0];
			  if(crate.equalsIgnoreCase("alpha"))  {
				  loadCrate(p, "Midas");
			  } else if(crate.equalsIgnoreCase("beta"))  {
				  loadCrate(p, "Poseidon");
			  } else if(crate.equalsIgnoreCase("omega"))  {
				  loadCrate(p, "Hades");
			  } else if(crate.equalsIgnoreCase("token"))  {
				  loadCrate(p, "Polis");
			  } else if(crate.equalsIgnoreCase("seasonal"))  {
				  loadCrate(p, "Oblivion");
			  } else if(crate.equalsIgnoreCase("rank"))  {
				  loadCrate(p, "Olympus");
			  } else if(crate.equalsIgnoreCase("community"))  {
				  loadCrate(p, "Prestige");
			  } else if(crate.equalsIgnoreCase("vote"))  {
				  loadCrate(p, "Vote");
			  } else {
				  p.sendMessage(c("&f&lCrates &8| &cNot a Crate."));
			  }
		  } else {
			  sender.sendMessage(c("&f&lCrates &8| &cPlease Specify a Crate."));
		  }
	  }
	  if(label.equalsIgnoreCase("openall")) {
		  Player p = (Player)sender;
		  if(p.hasPermission("command.openall")) {
		  String uuid = p.getUniqueId().toString();
		  int alpha = this.settings.getLocksmith().getInt(uuid + ".alpha");
		    int beta = this.settings.getLocksmith().getInt(uuid + ".beta");
		    int omega = this.settings.getLocksmith().getInt(uuid + ".omega");
		    int token = this.settings.getLocksmith().getInt(uuid + ".token");
		    int vote = this.settings.getLocksmith().getInt(uuid + ".vote");
		    int seasonal = this.settings.getLocksmith().getInt(uuid + ".seasonal");
		    int community = this.settings.getLocksmith().getInt(uuid + ".community");
		    int rank = this.settings.getLocksmith().getInt(uuid + ".rank");
		    openall(p, alpha, beta, omega, token, seasonal, rank, community, vote);
		    LocksmithHandler.getInstance().takeKey(p, "Alpha", alpha);
		    LocksmithHandler.getInstance().takeKey(p, "Beta", beta);
		    LocksmithHandler.getInstance().takeKey(p, "Omega", omega);
		    LocksmithHandler.getInstance().takeKey(p, "Token", token);
		    LocksmithHandler.getInstance().takeKey(p, "Seasonal", seasonal);
		    LocksmithHandler.getInstance().takeKey(p, "Rank", rank);
		    LocksmithHandler.getInstance().takeKey(p, "Vote", vote);
		    LocksmithHandler.getInstance().takeKey(p, "Community", community);
		  } else {
			  p.sendMessage(c("&f&lCrates &8| &bYou must be rank Ares+ to use /openall"));
		  }
		  p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
			double percents;
		    p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
		    percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
		    double dmultiply = percents*10.0;
		    double dRound = Math.round(dmultiply) /10.0;
		    if(RankupHandler.getInstance().getRank(p) == 100) {
		    	p.getScoreboard().getTeam("percent").setSuffix(c("&c/prestige"));
		    }
		    
		    else {
		    	if(dRound>=100.0) {
		    		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
		    	} else {
		    		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
		    }
		    }
		    p.getScoreboard().getTeam("tokens").setSuffix(c("&e"+Main.formatAmt(Tokens.getInstance().getTokens(p))));
	  }
	  
	  
	  
    if (label.equalsIgnoreCase("cratekey"))
      if (args.length == 0) {
        StringBuilder keys = new StringBuilder();
        for (String s : this.cr.getKeys(false)) {
          if (!s.contains("."))
            keys.append(s).append(" ");
        } 
        if (!sender.hasPermission("cratekey.give")) {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command!"));
          return false;
        } 
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease choose a key from the list below."));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7") + keys.toString().trim());
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
      } else {
        if (args.length == 1) {
          Player p = (Player)sender;
          if (p.hasPermission("cratekey.give")) {
            if (formatKey(args[0]).equalsIgnoreCase("error")) {
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat key does not exist."));
              return false;
            } 
            p.getInventory().addItem(getKey(formatKey(args[0]), 1));
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| &b+" + formatKey(args[0]) + " &bKey!"));
            return true;
          } 
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command."));
          return false;
        } 
        if (args.length == 2) {
          if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is not online!"));
            return false;
          } 
          if (!sender.hasPermission("cratekey.give.other")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to give other players keys!"));
            return false;
          } 
          Player p = Bukkit.getPlayer(args[0]);
          //p.getInventory().addItem(new ItemStack[] { getKey(formatKey(args[0]), 1) });
          LocksmithHandler.getInstance().addKey(p, formatKey(args[1]), 1);
         
        }
        if(args.length == 3){
            int i = Integer.parseInt(args[2]);
            if (Bukkit.getPlayer(args[0]) == null) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is not online!"));
                return false;
            }
            if (!sender.hasPermission("cratekey.give.other")) {
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to give other players keys!"));
                return false;
            }
            Player p = Bukkit.getPlayer(args[0]);
            LocksmithHandler.getInstance().addKey(p, formatKey(args[1]), i);
        }

        else {
          StringBuilder keys = new StringBuilder();
          for (String s : this.cr.getKeys(false)) {
            if (!s.contains("."))
              keys.append(s).append(" ");
          } 
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease choose a key from the list below."));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7") + keys.toString().trim());
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
        } 
      }  
    return true;
  }
}
