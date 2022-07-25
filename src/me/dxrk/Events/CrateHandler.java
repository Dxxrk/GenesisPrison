package me.dxrk.Events;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

import mkremins.fanciful.FancyMessage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
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
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;

public class CrateHandler implements Listener, CommandExecutor {
  public SettingsManager settings = SettingsManager.getInstance();
  
  public FileConfiguration cr = this.settings.getCrates();
  
  Plugin pl = Bukkit.getPluginManager().getPlugin("GenesisEcon");
  
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
      }).runTaskTimer(this.pl, 0L, 5L);
  }
  
  public List<Location> locations() {
    ArrayList<Location> locations = new ArrayList<>();
    for (String s : this.cr.getKeys(false)) {
      if (!s.contains(".")) {
        Location loc = new Location(Bukkit.getWorld(this.cr.getString(String.valueOf(s) + ".Location.World")), this.cr.getInt(String.valueOf(s) + ".Location.X"), this.cr.getInt(String.valueOf(s) + ".Location.Y"), this.cr.getInt(String.valueOf(s) + ".Location.Z"));
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
    return is;
  }
  
  public String getCrate(Block b) {
    Location loc = b.getLocation();
    for (String s : this.cr.getKeys(true)) {
      if (!s.contains(".") && this.cr.getInt(String.valueOf(s) + ".Location.X") == loc.getBlockX() && 
        this.cr.getInt(String.valueOf(s) + ".Location.Y") == loc.getBlockY() && this.cr.getInt(String.valueOf(s) + ".Location.Z") == loc.getBlockZ() && 
        this.cr.getString(String.valueOf(s) + ".Location.World").equalsIgnoreCase(b.getLocation().getWorld().getName()))
        return s; 
    } 
    return null;
  }
  
  public ItemStack getKey(String key, int amt) {
    ItemStack keyy = new ItemStack(Material.getMaterial(this.cr.getInt(String.valueOf(key) + ".Key.ID")), amt);
    ItemMeta am = keyy.getItemMeta();
    am.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.cr.getString(String.valueOf(key) + ".Key.Name")));
    am.setLore(Arrays.asList(new String[] { ChatColor.translateAlternateColorCodes('&', this.cr.getString(String.valueOf(key) + ".Key.Lore")) }));
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
    if (!c(this.cr.getString(String.valueOf(s) + ".Key.Name")).equals(a.getItemMeta().getDisplayName()))
      return false; 
    if (!c(this.cr.getString(String.valueOf(s) + ".Key.Lore")).equals(a.getItemMeta().getLore().get(0)))
      return false; 
    if (this.cr.getInt(String.valueOf(s) + ".Key.ID") != a.getTypeId())
      return false; 
    return true;
  }
  
  public int getRows(String s) {
    return this.cr.getInt(String.valueOf(s) + ".Rows");
  }
  
  public ItemStack spacer() {
    ItemStack a = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)10);
    ItemMeta am = a.getItemMeta();
    am.setDisplayName(c("&9Genesis&5Crates"));
    am.addEnchant(Enchantment.DURABILITY, 0, false);
    a.setItemMeta(am);
    a.removeEnchantment(Enchantment.DURABILITY);
    return a;
  }
  
  public String checkForSlot(ItemStack item, String crate) {
    String slot = "";
    for (String s : getRewards(crate)) {
      ItemStack x = loadItem(crate, "." + s);
      boolean same = true;
      if (x.getAmount() != item.getAmount())
        same = false; 
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
    if (this.cr.get(String.valueOf(crate) + "." + s + ".Command") != null)
      return true; 
    return false;
  }
  
  public List<String> getCommands(String crate, String s) {
    return this.cr.getStringList(String.valueOf(crate) + "." + s + ".Command");
  }
  
  public void loadCrate(Player p, String s) {
    if (!s.contains(".")) {
      Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, c("&c" + s + " Crate"));
      for (int i = 0; i < inv.getSize(); i++) {
        if (this.cr.get(String.valueOf(s) + ".Slot" + i + ".ItemID") != null) {
          inv.setItem(i, loadItem(s, String.valueOf(".Slot" + i)));
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
                Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
              } 
            } else {
            	if(inv.getItem(13).getType() == Material.DIAMOND_PICKAXE) {
            		ItemStack hand = inv.getItem(13).clone();
            		ItemMeta am = hand.getItemMeta();
            	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
            	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            	    hand.setItemMeta(am);
            	    p.getInventory().addItem(new ItemStack[] { hand });
            	} else {
              p.getInventory().addItem(new ItemStack[] { inv.getItem(13).clone() });
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
      }).runTaskLater(this.pl, time);
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
      s = ChatColor.stripColor((String)s);
      char[] arrayOfChar = s.toCharArray();
      int i = arrayOfChar.length;
      for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
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
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
      } 
    } else {
    	if(won.getType() == Material.DIAMOND_PICKAXE) {
    		ItemStack hand = won.clone();
    		ItemMeta am = hand.getItemMeta();
    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	    hand.setItemMeta(am);
    	    p.getInventory().addItem(new ItemStack[] { hand });
    	} else {
      p.getInventory().addItem(new ItemStack[] { won });
    	}
    	p.updateInventory();
    } 
  }
  
  public String formatKey(String s) {
    if (s.equalsIgnoreCase("vote") || s.equalsIgnoreCase("vote"))
      return "Vote"; 
    if (s.equalsIgnoreCase("midas"))
      return "Midas"; 
    if (s.equalsIgnoreCase("poseidon"))
      return "Poseidon"; 
    if (s.equalsIgnoreCase("hades"))
      return "Hades"; 
    if (s.equalsIgnoreCase("oblivion"))
      return "Oblivion"; 
    if (s.equalsIgnoreCase("polis"))
      return "Polis"; 
    if (s.equalsIgnoreCase("olympus"))
      return "Olympus"; 
    if (s.equalsIgnoreCase("prestige"))
        return "Prestige";
    return "Error";
  }
  
  
  public String getRandom(String s) {
    List<String> items = new ArrayList<>();
    Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, "test");
    for (int i = 0; i < inv.getSize(); i++) {
      if (this.cr.get(String.valueOf(s) + ".Slot" + i + ".Name") != null) {
      for (int xi = 0; xi < this.cr.getInt(String.valueOf(s) + ".Slot" + i + ".Chance"); xi++) {
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
	      if (this.cr.get(String.valueOf(s) + ".Slot" + i + ".Name") != null) {
	      for (int xi = 0; xi < this.cr.getInt(String.valueOf(s) + ".Slot" + i + ".Chance"); xi++) {
	               items.add("Slot" + i);
	      }
	    } 
	    }
    return items;
  }
  
  @SuppressWarnings("deprecation")
public ItemStack loadItem(String s, String slot) {
    String name = this.cr.getString(String.valueOf(s) + slot + ".Name");
    int amt = 1;
    if (this.cr.getInt(String.valueOf(s) + slot + ".Amount") > 0)
      amt = this.cr.getInt(String.valueOf(s) + slot + ".Amount"); 
    int id = 1;
    if (this.cr.getInt(String.valueOf(s) + slot + ".ItemID") > 0)
      id = this.cr.getInt(String.valueOf(s) + slot + ".ItemID"); 
    int sh = 0;
    if (this.cr.get(String.valueOf(s) + slot + ".Short") != null)
      sh = this.cr.getInt(String.valueOf(s) + slot + ".Short"); 
    List<String> lore = null;
    if (this.cr.getStringList(String.valueOf(s) + slot + ".Lore") != null)
      lore = this.cr.getStringList(String.valueOf(s) + slot + ".Lore"); 
    List<String> ench = null;
    if (this.cr.getStringList(String.valueOf(s) + slot + ".Enchantments") != null)
      ench = this.cr.getStringList(String.valueOf(s) + slot + ".Enchantments"); 
    ItemStack item = new ItemStack(id, amt, (short)sh);
    ItemMeta am = item.getItemMeta();
    am.setDisplayName(c(name));
    if (lore != null) {
      List<String> newlore = new ArrayList<>();
      for (String l : lore)
        newlore.add(c(l.replaceAll("§", "&"))); 
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
	      return String.format("%.1f Quad", new Object[] { Double.valueOf(amt / 1.0E15D) }); 
	    if (amt >= 1.0E12D)
	      return String.format("%.1f Tril", new Object[] { Double.valueOf(amt / 1.0E12D) }); 
	    if (amt >= 1.0E9D)
	      return String.format("%.1f Bil", new Object[] { Double.valueOf(amt / 1.0E9D) }); 
	    if (amt >= 1000000.0D)
	      return String.format("%.1f Mil", new Object[] { Double.valueOf(amt / 1000000.0D) }); 
	    return NumberFormat.getNumberInstance(Locale.US).format(amt);
	  }
  @EventHandler
  public void onInt(PlayerInteractEvent e) {
    if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
      if (!isCrate(e.getClickedBlock()))
        return; 
      e.setCancelled(true);
      Player p = e.getPlayer();
      loadCrate(p, getCrate(e.getClickedBlock()));
    } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
      if (!isCrate(e.getClickedBlock()))
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
          	  ArrayList<String> rw = new ArrayList<String>();
          	  ArrayList<String> rww = new ArrayList<String>();
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
    	    }else if(name.contains("Billion")) {
    	    	double m = getValuedbl(name)*1e9;
    	    	money += m;
    	    	
    	    } else if(name.contains("Trillion")) {
    	    	double m = getValuedbl(name)*1e12;
    	    	money += m;
    	    	
    	    } else if(name.contains("Quadrillion")) {
    	    	double m = getValuedbl(name)*1e15;
    	    	money += m;
    	    	
    	    } else {
    	    	rww.add(c(won.getItemMeta().getDisplayName()));
    	    }
    	    
    	    
    	    
    	    if (slotHasCommands(getCrate(e.getClickedBlock()), slot)) {
    	      for (String ss : getCommands(getCrate(e.getClickedBlock()), slot)) {
    	        ss = ss.replaceAll("%PLAYER%", p.getName());
    	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
    	      } 
    	    } else {
    	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
    	    		ItemStack hand = won.clone();
    	    		ItemMeta am = hand.getItemMeta();
    	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
    	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	    	    hand.setItemMeta(am);
    	    	    p.getInventory().addItem(new ItemStack[] { hand });
    	    	} else {
    	      p.getInventory().addItem(new ItemStack[] { won });
    	    	}
    	    	p.updateInventory();
    	    }
    	    p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
	    	double percents = 0.0;
	        p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
	        percents = (Double.valueOf(Main.econ.getBalance((OfflinePlayer)p) / RankupHandler.getInstance().rankPrice(p)).doubleValue()*100);
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
      	ArrayList<String> rw2 = new ArrayList<String>();
      	 // String[] rewards = {c("&7&lMulti: "+multi+"x"), c("&7&lTokens: "+tokens), c("&7&lMoney: "+Main.formatAmt(money)), c("&7Other:")};
      	
      	rw.add(c("&f&lMulti &8| &b"+multi+"x"));
     	rw.add(c("&f&lTokens &8| &b"+tokens));
     	rw.add(c("&f&lMoney &8| &b"+Main.formatAmt(money)));
      	for(int x = 0; x < rw.size(); x++) {
      		p.sendMessage(c(rw.get(x)));
      	}
      	
      	 rw2.addAll(rww);
      	  
      	  FancyMessage reward = new FancyMessage("");
      	  reward.then(c("&f&lOther &8| &b(Hover)")).tooltip(rw2);
      	  reward.send(p);
      	  multi = 0;
      	  tokens = 0;
      	  money = 0;
                	
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
  
  
  
  public void openall(Player p, int midas, int poseidon, int hades, int polis, int oblivion, int olympus, int prestige, int vote) {
	  double multi = 0;
  	  int tokens = 0;
  	  double money = 0;
  	  ArrayList<String> rw = new ArrayList<String>();
  	  ArrayList<String> rww = new ArrayList<String>();
  	int i;
	  for (i = 0; i < midas; i++) {
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
    }else if(name.contains("Billion")) {
    	double m = getValuedbl(name)*1e9;
    	money += m;
    	
    } else if(name.contains("Trillion")) {
    	double m = getValuedbl(name)*1e12;
    	money += m;
    	
    } else if(name.contains("Quadrillion")) {
    	double m = getValuedbl(name)*1e15;
    	money += m;
    	
    } else {
    	rww.add(c(won.getItemMeta().getDisplayName()));
    }
    
    
    
    if (slotHasCommands("Midas", slot)) {
      for (String ss : getCommands("Midas", slot)) {
        ss = ss.replaceAll("%PLAYER%", p.getName());
        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
      } 
    } else {
    	if(won.getType() == Material.DIAMOND_PICKAXE) {
    		ItemStack hand = won.clone();
    		ItemMeta am = hand.getItemMeta();
    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
    	    hand.setItemMeta(am);
    	    p.getInventory().addItem(new ItemStack[] { hand });
    	} else {
      p.getInventory().addItem(new ItemStack[] { won });
    	}
    	p.updateInventory();
    }
   
	  }
	  for (i = 0; i < poseidon; i++) {
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
	    }else if(name.contains("Billion")) {
	    	double m = getValuedbl(name)*1e9;
	    	money += m;
	    	
	    } else if(name.contains("Trillion")) {
	    	double m = getValuedbl(name)*1e12;
	    	money += m;
	    	
	    } else if(name.contains("Quadrillion")) {
	    	double m = getValuedbl(name)*1e15;
	    	money += m;
	    	
	    } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Poseidon", slot)) {
	      for (String ss : getCommands("Poseidon", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(new ItemStack[] { hand });
	    	} else {
	      p.getInventory().addItem(new ItemStack[] { won });
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < hades; i++) {
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
	    }else if(name.contains("Billion")) {
	    	double m = getValuedbl(name)*1e9;
	    	money += m;
	    	
	    } else if(name.contains("Trillion")) {
	    	double m = getValuedbl(name)*1e12;
	    	money += m;
	    	
	    } else if(name.contains("Quadrillion")) {
	    	double m = getValuedbl(name)*1e15;
	    	money += m;
	    	
	    } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Hades", slot)) {
	      for (String ss : getCommands("Hades", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(new ItemStack[] { hand });
	    	} else {
	      p.getInventory().addItem(new ItemStack[] { won });
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < polis; i++) {
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
	    }else if(name.contains("Billion")) {
	    	double m = getValuedbl(name)*1e9;
	    	money += m;
	    	
	    } else if(name.contains("Trillion")) {
	    	double m = getValuedbl(name)*1e12;
	    	money += m;
	    	
	    } else if(name.contains("Quadrillion")) {
	    	double m = getValuedbl(name)*1e15;
	    	money += m;
	    	
	    } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Polis", slot)) {
	      for (String ss : getCommands("Polis", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(new ItemStack[] { hand });
	    	} else {
	      p.getInventory().addItem(new ItemStack[] { won });
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < oblivion; i++) {
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
	    }else if(name.contains("Billion")) {
	    	double m = getValuedbl(name)*1e9;
	    	money += m;
	    	
	    } else if(name.contains("Trillion")) {
	    	double m = getValuedbl(name)*1e12;
	    	money += m;
	    	
	    } else if(name.contains("Quadrillion")) {
	    	double m = getValuedbl(name)*1e15;
	    	money += m;
	    	
	    } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Oblivion", slot)) {
	      for (String ss : getCommands("Oblivion", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(new ItemStack[] { hand });
	    	} else {
	      p.getInventory().addItem(new ItemStack[] { won });
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < olympus; i++) {
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
	    }else if(name.contains("Billion")) {
	    	double m = getValuedbl(name)*1e9;
	    	money += m;
	    	
	    } else if(name.contains("Trillion")) {
	    	double m = getValuedbl(name)*1e12;
	    	money += m;
	    	
	    } else if(name.contains("Quadrillion")) {
	    	double m = getValuedbl(name)*1e15;
	    	money += m;
	    	
	    } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Olympus", slot)) {
	      for (String ss : getCommands("Olympus", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(new ItemStack[] { hand });
	    	} else {
	      p.getInventory().addItem(new ItemStack[] { won });
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  for (i = 0; i < prestige; i++) {
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
	    }else if(name.contains("Billion")) {
	    	double m = getValuedbl(name)*1e9;
	    	money += m;
	    	
	    } else if(name.contains("Trillion")) {
	    	double m = getValuedbl(name)*1e12;
	    	money += m;
	    	
	    } else if(name.contains("Quadrillion")) {
	    	double m = getValuedbl(name)*1e15;
	    	money += m;
	    	
	    } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Prestige", slot)) {
	      for (String ss : getCommands("Prestige", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(new ItemStack[] { hand });
	    	} else {
	      p.getInventory().addItem(new ItemStack[] { won });
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
	    }else if(name.contains("Billion")) {
	    	double m = getValuedbl(name)*1e9;
	    	money += m;
	    	
	    } else if(name.contains("Trillion")) {
	    	double m = getValuedbl(name)*1e12;
	    	money += m;
	    	
	    } else if(name.contains("Quadrillion")) {
	    	double m = getValuedbl(name)*1e15;
	    	money += m;
	    	
	    } else {
	    	rww.add(c(won.getItemMeta().getDisplayName()));
	    }
	    
	    
	    
	    if (slotHasCommands("Vote", slot)) {
	      for (String ss : getCommands("Vote", slot)) {
	        ss = ss.replaceAll("%PLAYER%", p.getName());
	        Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), ss);
	      } 
	    } else {
	    	if(won.getType() == Material.DIAMOND_PICKAXE) {
	    		ItemStack hand = won.clone();
	    		ItemMeta am = hand.getItemMeta();
	    	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    	    hand.setItemMeta(am);
	    	    p.getInventory().addItem(new ItemStack[] { hand });
	    	} else {
	      p.getInventory().addItem(new ItemStack[] { won });
	    	}
	    	p.updateInventory();
	    }
	   
		  }
	  this.settings.savePlayerData();
	ArrayList<String> rw2 = new ArrayList<String>();
	 // String[] rewards = {c("&7&lMulti: "+multi+"x"), c("&7&lTokens: "+tokens), c("&7&lMoney: "+Main.formatAmt(money)), c("&7Other:")};
	if(multi >0)
	rw.add(c("&f&lMulti &8| &b"+multi+"x"));
	if(tokens > 0)
	rw.add(c("&f&lTokens &8| &b"+tokens));
	if(money >0)
	rw.add(c("&f&lMoney &8| &b$"+Main.formatAmt(money)));
	if(rw.size() >0) {
		for(int x = 0; x < rw.size(); x++) {
			p.sendMessage(c(rw.get(x)));
		}
	}
	
	 rw2.addAll(rww);
	  
	  FancyMessage reward = new FancyMessage("");
	  reward.then(c("&f&lOther &8| &b(Hover)")).tooltip(rw2);
	  if(rw2.size() >0) {
	  reward.send(p);
	  }
	  multi = 0;
	  tokens = 0;
	  money = 0;
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
			  if(crate.equalsIgnoreCase("midas"))  {
				  loadCrate(p, "Midas");
			  } else if(crate.equalsIgnoreCase("poseidon"))  {
				  loadCrate(p, "Poseidon");
			  } else if(crate.equalsIgnoreCase("hades"))  {
				  loadCrate(p, "Hades");
			  } else if(crate.equalsIgnoreCase("polis"))  {
				  loadCrate(p, "Polis");
			  } else if(crate.equalsIgnoreCase("oblivion"))  {
				  loadCrate(p, "Oblivion");
			  } else if(crate.equalsIgnoreCase("olympus"))  {
				  loadCrate(p, "Olympus");
			  } else if(crate.equalsIgnoreCase("prestige"))  {
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
		  int midas = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".midas");
		    int poseidon = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".poseidon");
		    int hades = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".hades");
		    int polis = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".polis");
		    int vote = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".vote");
		    int prestige = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".prestige");
		    int oblivion = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".oblivion");
		    int olympus = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".olympus");
		    openall(p, midas, poseidon, hades, polis, oblivion, olympus, prestige, vote);
		    LocksmithHandler.getInstance().takeKey(p, "Midas", midas);
		    LocksmithHandler.getInstance().takeKey(p, "Poseidon", poseidon);
		    LocksmithHandler.getInstance().takeKey(p, "Hades", hades);
		    LocksmithHandler.getInstance().takeKey(p, "Polis", polis);
		    LocksmithHandler.getInstance().takeKey(p, "Oblivion", oblivion);
		    LocksmithHandler.getInstance().takeKey(p, "Olympus", olympus);
		    LocksmithHandler.getInstance().takeKey(p, "Vote", vote);
		    LocksmithHandler.getInstance().takeKey(p, "Prestige", prestige);
		  } else {
			  p.sendMessage(c("&f&lCrates &8| &bYou must be rank Ares+ to use /openall"));
		  }
		  p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
			double percents = 0.0;
		    p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
		    percents = (Double.valueOf(Main.econ.getBalance((OfflinePlayer)p) / RankupHandler.getInstance().rankPrice(p)).doubleValue()*100);
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
        String keys = "";
        for (String s : this.cr.getKeys(false)) {
          if (!s.contains("."))
            keys = String.valueOf(keys) + s + " "; 
        } 
        if (!sender.hasPermission("cratekey.give")) {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command!"));
          return false;
        } 
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease choose a key from the list below."));
        sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', "&7")) + keys.trim());
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
      } else {
        if (args.length == 1) {
          Player p = (Player)sender;
          if (p.hasPermission("cratekey.give")) {
            if (formatKey(args[0]).equalsIgnoreCase("error")) {
              p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat key does not exist."));
              return false;
            } 
            p.getInventory().addItem(new ItemStack[] { getKey(formatKey(args[0]), 1) });
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| &b+" + formatKey(args[0]) + " &bKey!"));
            return true;
          } 
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command."));
          return false;
        } 
        if (args.length == 2) {
          if (Bukkit.getPlayer(args[1]) == null) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is not online!"));
            return false;
          } 
          if (!sender.hasPermission("cratekey.give.other")) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to give other players keys!"));
            return false;
          } 
          Player p = Bukkit.getPlayer(args[1]);
          //p.getInventory().addItem(new ItemStack[] { getKey(formatKey(args[0]), 1) });
          LocksmithHandler.getInstance().addKey(p, formatKey(args[0]), 1);
         
        } else {
          String keys = "";
          for (String s : this.cr.getKeys(false)) {
            if (!s.contains("."))
              keys = String.valueOf(keys) + s + " "; 
          } 
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease choose a key from the list below."));
          sender.sendMessage(String.valueOf(ChatColor.translateAlternateColorCodes('&', "&7")) + keys.trim());
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
        } 
      }  
    return true;
  }
}
