package me.dxrk.Events;

import com.connorlinfoot.titleapi.TitleAPI;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.dxrk.Enchants.EnchantMethods;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Events.ResetHandler.ResetReason;
import me.dxrk.Main.Functions;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import me.jet315.prisonmines.mine.Mine;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.*;

@SuppressWarnings("deprecation")
public class SellHandler implements Listener, CommandExecutor {
  SettingsManager settings = SettingsManager.getInstance();
  
  Methods methods = Methods.getInstance();
  
  
  public static HashMap<Player, Inventory> backpacks = new HashMap<>();
  
  public static ArrayList<Player> picksell = new ArrayList<>();
  
  public static ArrayList<Player> picksellstfu = new ArrayList<>();
  
  
  
  public static double servermulti = 1.0D;
  
  public static HashMap<Player, Double> pickmulti = new HashMap<>();
  
  public static ArrayList<Player> autosell = new ArrayList<>();
  
  public static SellHandler instance = new SellHandler();
  
  public static SellHandler getInstance() {
    return instance;
  }
  static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  
  
  public static String format(double amt) {
	  if (amt >= 1.0E18D)
	      return String.format("%.1f Quint", amt / 1.0E18D); 
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
  
  public static HashMap<Player, ItemStack[]> newBP = new HashMap<>();
  
  
  
  public static int getBPEmptySlots(Player p) {
	  SettingsManager.getInstance().getbpSize();
        return SettingsManager.getInstance().getbpSize().getInt(p.getUniqueId().toString())*9;
  }
  
  
  
  
  
  public double getMulti(Player p) {
      double d = SettingsManager.getInstance().getMultiplier().getDouble(p.getUniqueId().toString());
      
      if (d < 1.0) {
          d = 1.0;
      }
      double dround = d*10.0;
	  double drounded = Math.round(dround) /10.0;
	  if(p.hasPermission("rank.zeus")) {
		  drounded += 10;
	  } else if(p.hasPermission("rank.kronos")) {
		  drounded += 9;
	  } else if(p.hasPermission("rank.apollo")) {
		  drounded += 8;
	  } else if(p.hasPermission("rank.hermes")) {
		  drounded += 7;
	  } else if(p.hasPermission("rank.ares")) {
		  drounded += 6;
	  } else if(p.hasPermission("rank.colonel")) {
		  drounded += 5;
	  } else if(p.hasPermission("rank.captain")) {
		  drounded += 4;
	  } else if(p.hasPermission("rank.hoplite")) {
		  drounded += 3;
	  } else if(p.hasPermission("rank.cavalry")) {
		  drounded += 2;
	  }
	  
	  return drounded;
  }

  public boolean isDbl(String s) {
	    try {
	      Double.parseDouble(s);
	      return true;
	    } catch (NumberFormatException e) {
	      return false;
	    } 
	  }
  
  
  
  
  public void saveBackPack(Player p) {
    try {
      Inventory i = backpacks.get(p);
      for (int x = 0; x < this.settings.getbpSize().getInt(p.getUniqueId().toString())*900; x++) {
        ItemStack item = i.getItem(x);
        this.settings.getBackPacks().set(p.getUniqueId().toString() + "." + x, item);
      } 
      this.settings.saveBackPacks();
    } catch (Exception ignored) {}
  }
  
  public void openBackPack(Player p) {
    Inventory i = backpacks.get(p);
    p.openInventory(i);
  }
  
  public void loadBackPack(Player p) {
    Inventory backpack = Bukkit.createInventory(null, this.settings.getbpSize().getInt(p.getUniqueId().toString())*900, ChatColor.RED + p.getName() + "'s BackPack");
    if (this.settings.getBackPacks().contains(p.getUniqueId().toString()))
      for (int x = 0; x < this.settings.getbpSize().getInt(p.getUniqueId().toString())*900; x++) {
        try {
          ItemStack item = this.settings.getBackPacks()
            .getItemStack(p.getUniqueId().toString() + "." + x);
          backpack.setItem(x, item);
        } catch (Exception ignored) {}
      }

    backpacks.put(p, backpack);
  }

  public void setBPSize(Player p, int i) {
      this.settings.getbpSize().set(p.getUniqueId().toString(), i);
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void setbp(PlayerJoinEvent e ) {
	  loadBackPack(e.getPlayer());
  	  Player p = e.getPlayer();


  	  if(p.hasPermission("rank.helper") && !p.getName().equals("BakonStrip")) {
  		  e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b+&7> &e&l" + p.getName()));
  	  } else {
  		  e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b+&7> &b" + p.getName()));
  	  }
	  if(!this.settings.getbpSize().contains(p.getUniqueId().toString())) {
		  setBPSize(p, 1);
	  }
	  if(!this.settings.getMultiplier().contains(p.getUniqueId().toString())) {
		  this.settings.getMultiplier().set(e.getPlayer().getUniqueId().toString(), 1.0);
		  this.settings.saveMultiplier();
	  }
	  if(p.hasPermission("bplevel.10")) {
		  setBPSize(p, 11);
	  } else if(p.hasPermission("bplevel.9")) {
		  setBPSize(p, 10);
	  }else if(p.hasPermission("bplevel.8")) {
		  setBPSize(p, 9);
	  }else if(p.hasPermission("bplevel.7")) {
		  setBPSize(p, 8);
	  }else if(p.hasPermission("bplevel.6")) {
		  setBPSize(p, 7);
	  }else if(p.hasPermission("bplevel.5")) {
		  setBPSize(p, 6);
	  }else if(p.hasPermission("bplevel.4")) {
		  setBPSize(p, 5);
	  }else if(p.hasPermission("bplevel.3")) {
		  setBPSize(p, 4);
	  }else if(p.hasPermission("bplevel.2")) {
		  setBPSize(p, 3);
	  }else if(p.hasPermission("bplevel.1")) {
		  setBPSize(p, 2);
	  } else {
		  setBPSize(p, 1);
	  }
	  this.settings.savebpSize();
  }

  @EventHandler
  public void invcl(InventoryClickEvent e) {
    if (e.getInventory().getName().equals(ChatColor.RED + "Investments"))
      e.setCancelled(true);
  }




  public void setMulti(Player p, double d) {
	  this.settings.getMultiplier().set(p.getUniqueId().toString(), d);
  }


	private static Methods m = Methods.getInstance();

  public static void sellAllItems(Player p, List<ItemStack> items, String enchantName) {
	    double total = 0.0D;
	    int amountotal = 0;
	    double greed = Functions.greed(p);
	    double sell = Functions.sellBoost(p);
	    double miningboost = BoostsHandler.sell;
    	SettingsManager.getInstance().getMultiplier();
	    double multi = SellHandler.getInstance().getMulti(p);
	    for (ItemStack i : items) {
	  	      if (i != null) {



	  	    	double price = Methods.getBlockSellPrice("A", i.getTypeId());


	  	    	total += price * (multi+greed) * sell * miningboost;

	  	        amountotal += i.getAmount();

	  	      }
	  	    }
	    p.updateInventory();
	    if(SettingsManager.getInstance().getOptions().getBoolean(p.getUniqueId().toString()+"."+enchantName+"-Messages") == true) {
	    	p.sendMessage(c("&f&l"+enchantName+" &8| &b+$"+format(total*amountotal)));
	    }
	    if(EnchantMethods.stakemap.containsKey(p)) {
  	    	double b = EnchantMethods.stakemap.get(p);
  	    	EnchantMethods.stakemap.put(p, b+(total*amountotal));
  	    }
	    Main.econ.depositPlayer(p, total*amountotal);
	  }



  public static void sellAllItemsbp(Player p, List<ItemStack> items) {
  	    Methods.getSellRank(p);
  	    double total = 0.0D;
  	  double greed = Functions.greed(p);
  	double sell = Functions.sellBoost(p);
  	double miningboost = BoostsHandler.sell;



  	SettingsManager.getInstance().getMultiplier();
  	    double multi = SellHandler.getInstance().getMulti(p);
  	  for (ItemStack i : items) {
  	      if (i != null) {



  	    	double price = Methods.getBlockSellPrice("A", i.getTypeId());


  	     total += price * (multi+greed)* i.getAmount() * sell * miningboost;

  	        i.getAmount();

  	      }
  	    }
  	    p.updateInventory();
  	  if(EnchantMethods.stakemap.containsKey(p)) {
	    	double b = EnchantMethods.stakemap.get(p);
	    	EnchantMethods.stakemap.put(p, b+(total));
	    }
  	    Main.econ.depositPlayer(p, total);
  	  }






  public void sellAllItems(Player p) {
	  String rank = Methods.getSellRank(p);
    double total = 0.0D;
    SettingsManager.getInstance().getMultiplier();
    double multi = SellHandler.getInstance().getMulti(p);
    double greed = Functions.greed(p);
    double sell = Functions.sellBoost(p);
    double miningboost = BoostsHandler.sell;




     //total += price * multi* itemStack.getAmount() * greed * perk * miningboost;
    byte b;
    int i;
    Inventory inv = backpacks.get(p);
    ItemStack[] arrayOfItemStack;
    for (i = (arrayOfItemStack = p.getInventory().getContents()).length, b = 0; b < i; ) {
      ItemStack itemStack = arrayOfItemStack[b];

      if (itemStack != null &&
    	        Methods.getBlockSellPrice(rank, itemStack.getTypeId()) > 0.0D) {
    	        double price = Methods.getBlockSellPrice("A", itemStack.getTypeId());
    	        total += price * (multi+greed)* itemStack.getAmount() * sell * miningboost;
    	        itemStack.getAmount();
    	        p.getInventory().removeItem(itemStack);
    	      }
    	      b = (byte)(b + 1);
    }
    ItemStack[] bpitems= inv.getContents();
    List<ItemStack> items = Arrays.asList(bpitems);


    sellAllItemsbp(p, items);
    inv.clear();

    p.updateInventory();
    if(EnchantMethods.stakemap.containsKey(p)) {
	    	double bb = EnchantMethods.stakemap.get(p);
	    	EnchantMethods.stakemap.put(p, bb+(total));
	    }

    Main.econ.depositPlayer(p, total);
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
	  p.getScoreboard().getTeam("xp").setPrefix(c("&7XP: &b✴"));



	  for (i = (arrayOfItemStack = p.getInventory().getContents()).length, b = 0; b < i; ) {
		  ItemStack itemStack = arrayOfItemStack[b];
		  if(itemStack != null && itemStack.getType().equals(Material.DIAMOND_PICKAXE)) {
			  if(itemStack.hasItemMeta()) {
				  if(itemStack.getItemMeta().hasLore()) {
					  String xp = Main.formatAmt(PickXPHandler.getBlocks(itemStack.getItemMeta().getLore().get(1)));
					  p.getScoreboard().getTeam("xp").setSuffix(c("&b"+xp));
				  }
			  }
		  }
		  b = (byte)(b + 1);
	  }

  }

  @EventHandler
  public void onPickSell(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    if (p.getItemInHand() == null)
      return;
    ItemStack item = p.getItemInHand();
    if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
      if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
        return;

      if(p.isSneaking()) {
    	  if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {


    	  PickaxeLevel.getInstance().openenchantmenu(p);
    	  }
      }
      if (!backpacks.containsKey(p))
        loadBackPack(p);
      if (picksell.contains(p))
        return;
      sellAllItems(p);
    }
  }



  public boolean isInt(String s) {
	    try {
	      Integer.parseInt(s);
	      return true;
	    } catch (Exception e1) {
	      return false;
	    }
	  }

	  public boolean isInt(char ss) {
	    String s = String.valueOf(ss);
	    try {
	      Integer.parseInt(s);
	      return true;
	    } catch (Exception e1) {
	      return false;
	    } 
	  }
		  
	  
	  public int getFortune(String s) {
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
	  
	  
	  @EventHandler(priority = EventPriority.HIGHEST)
	  public void onBlockPlace(BlockPlaceEvent e ) {
		  Player p = e.getPlayer();
		  WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
		    ApplicableRegionSet set = wg.getRegionManager(p.getWorld())
		      .getApplicableRegions(e.getBlock().getLocation());
		    if (set.allows(DefaultFlag.LIGHTER)) {
		    	p.sendMessage(c("&9&lGenesis&8� &5Can't place blocks in the mine!"));
		    	e.setCancelled(true);
		    }
	  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onBlockBreak(BlockBreakEvent event) {
	  
    Player p = event.getPlayer();
    WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    ApplicableRegionSet set = wg.getRegionManager(p.getWorld())
      .getApplicableRegions(event.getBlock().getLocation());
    if (!set.allows(DefaultFlag.LIGHTER)) {
      if (p.isOp() && p.getItemInHand() != null && 
        p.getItemInHand().getType() == Material.DIAMOND_PICKAXE)
        event.setCancelled(true); 
      return;
    }
    
    
    new ItemStack(Material.INK_SACK, 1, (short) 4);
    
    double fortuity = Functions.Foruity(p);
    
    
    
    
    if (!event.isCancelled())
      if (p.getItemInHand() != null) {
        if (p.getItemInHand().getItemMeta().getLore().get(2).contains("Fortune")) {
          int loots = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(2))*fortuity / 
            (3.5));
          Collection<ItemStack> drops = event.getBlock().getDrops();
          if(event.getBlock().getType().equals(Material.STONE)) {
        	  drops.clear();
        	  drops.add(new ItemStack(Material.STONE));
          }
         
          for (ItemStack drop : drops) {
            drop.setAmount(loots);
            p.getInventory().addItem(drop);
            p.updateInventory();
          }
          
          event.getBlock().setType(Material.AIR);
          event.setCancelled(true);

         
          
          
          
        }
      }
  }
  

  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    saveBackPack(p);
    backpacks.remove(p);
    this.settings.savebpSize();
    
  }
  
  
  
  
  
  @EventHandler
  public void bpClick(InventoryClickEvent e ) {
	  Player p = (Player) e.getWhoClicked();
	  
	  if (e.getClickedInventory() == null)
	      return; 
	    if (e.getClickedInventory().getName() == null)
	      return; 
	    if(e.getCurrentItem() == null) 
	    	return;
	  
	    
	    
	    
	 
	  if(e.getInventory().getName().equals(c("&cBackpack"))) {
		  
		  
		  e.setCancelled(true);
	  
	  
		  
		  
		  if(e.getSlot() == 3) {
			  if(p.hasPermission("bplevel.1")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=2500) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 2500);
				  Main.perms.playerAdd(p, "bplevel.1");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 2);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 1!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 11) {
			  if(p.hasPermission("bplevel.2")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.1")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 1 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=7500) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 7500);
				  Main.perms.playerAdd(p, "bplevel.2");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 3);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 2!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
			  
		  }
		  if(e.getSlot() == 19) {
			  if(p.hasPermission("bplevel.3")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.2")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 2 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=10000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 10000);
				  Main.perms.playerAdd(p, "bplevel.3");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 4);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 3!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 29) {
			  if(p.hasPermission("bplevel.4")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.3")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 3 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=15000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 15000);
				  Main.perms.playerAdd(p, "bplevel.4");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 5);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 4!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 39) {
			  if(p.hasPermission("bplevel.5")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.4")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 4 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=20000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 20000);
				  Main.perms.playerAdd(p, "bplevel.5");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 6);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 5!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 41) {
			  if(p.hasPermission("bplevel.6")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.5")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 5 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=30000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 30000);
				  Main.perms.playerAdd(p, "bplevel.6");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 7);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 6!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 33) {
			  if(p.hasPermission("bplevel.7")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.6")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 6 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=40000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 40000);
				  Main.perms.playerAdd(p, "bplevel.7");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 8);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 7!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 25) {
			  if(p.hasPermission("bplevel.8")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.7")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 7 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=50000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 50000);
				  Main.perms.playerAdd(p, "bplevel.8");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 9);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 8!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 15) {
			  if(p.hasPermission("bplevel.9")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.8")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 8 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=75000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 75000);
				  Main.perms.playerAdd(p, "bplevel.9");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 10);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 9!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  if(e.getSlot() == 5) {
			  if(p.hasPermission("bplevel.10")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: You already have this stage!"));
				  return;
			  }
			  if(!p.hasPermission("bplevel.9")) {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Buy Level 9 First"));
				  return;
			  }
			  if(Tokens.getInstance().getTokens(p) >=100000) {
				  p.closeInventory();
				  Tokens.getInstance().takeTokens(p, 100000);
				  Main.perms.playerAdd(p, "bplevel.10");
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 11);
				  saveBackPack(p);
				  this.settings.savebpSize();
				  TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lBackpack"), c("&6&lStage 10!"));
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
			  } else {
				  p.closeInventory();
				  p.sendMessage(c("&cError: Not Enough Tokens"));
			  }
		  }
		  this.settings.savebpSize();
	  
	  }
  }
  
  
  private ArrayList<String> reset = new ArrayList<>();
  
  
  public Inventory inv = Bukkit.createInventory(null, 45, c("&cBackpack"));
  
  public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
	  
	  if(cmd.getName().equalsIgnoreCase("bpreset")) {
		  if (cs instanceof Player) {
			  Player p = (Player)cs;
			  if(p.hasPermission("bp.admin")) {
				  this.settings.getbpSize().set(p.getUniqueId().toString(), 1);
				  this.settings.savebpSize();
			  }
		  }
	  }
	  if(cmd.getName().equalsIgnoreCase("resetmine") || cmd.getName().equalsIgnoreCase("rm")) {
		  if (cs instanceof Player) {
			  Player p = (Player)cs;
			  if(p.hasPermission("mines.resetmine")) {
				 Mine mine = ResetHandler.api.getMineByName(p.getUniqueId().toString());
				 if(reset.contains(p.getUniqueId().toString())) {
					 p.sendMessage(c("&c/resetmine(/rm) is on cooldown."));
				 } else {
					 ResetHandler.resetMine(mine, ResetReason.NORMAL);
					 reset.add(p.getUniqueId().toString());
				 }
				 new BukkitRunnable() {

					@Override
					public void run() {
						reset.remove(p.getUniqueId().toString());
						
					}
					 
				 }.runTaskLater(Main.plugin, 20*60);
			  }
		  }
	  }
	  
	  
    if (cmd.getName().equalsIgnoreCase("BackPack") || cmd.getName().equalsIgnoreCase("BP")) {
      if (cs instanceof Player) {
        Player p = (Player)cs;
        if (args.length == 0) {
        	List<String> lore = new ArrayList<>();
        	
        	
        	
        	
          
        	ItemStack info = new ItemStack(Material.PAPER);
        	ItemMeta im = info.getItemMeta();
        	im.setDisplayName(c("&6&lBackpack Info"));
        	lore.add(c("&cBackpack Rows: &b " + this.settings.getbpSize().getInt(p.getUniqueId().toString())));
        	if(p.hasPermission("bplevel.10")) {
        	lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 10"));
        	} else if(p.hasPermission("bplevel.9")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 9"));
        	} else if(p.hasPermission("bplevel.8")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 8"));
        	} else if(p.hasPermission("bplevel.7")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 7"));
        	} else if(p.hasPermission("bplevel.6")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 6"));
        	} else if(p.hasPermission("bplevel.5")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 5"));
        	} else if(p.hasPermission("bplevel.4")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 4"));
        	} else if(p.hasPermission("bplevel.3")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 3"));
        	} else if(p.hasPermission("bplevel.2")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 2"));
        	} else if(p.hasPermission("bplevel.1")) {
        		lore.add(c("&cFor me.dxrk.Enchants: : &bLevel 1"));
        	}
        	im.setLore(lore);
        	lore.clear();
        	info.setItemMeta(im);
        	inv.setItem(22, info);
          
          ItemStack stage1 = new ItemStack(Material.CHEST);
          ItemMeta m1 = stage1.getItemMeta();
          m1.setDisplayName(c("&c&lBackpack Stage 1!"));   
          lore.add(c("&7&oEffective for Level 1 me.dxrk.Enchants."));
          lore.add(c("&7&o2 Backpack Rows."));
          lore.add(c("&b2,500 &dTokens"));
          m1.setLore(lore);
          lore.clear();
          stage1.setItemMeta(m1);
          inv.setItem(3, stage1);
          
          ItemStack stage2 = new ItemStack(Material.CHEST);
          ItemMeta m2 = stage2.getItemMeta();
          m2.setDisplayName(c("&c&lBackpack Stage 2!"));   
          lore.add(c("&7&oEffective for Level 2 me.dxrk.Enchants."));
          lore.add(c("&7&o3 Backpack Rows."));
          lore.add(c("&b7,500 &dTokens"));
          m2.setLore(lore);
          lore.clear();
          stage2.setItemMeta(m2);
          inv.setItem(11, stage2);
          
          ItemStack stage3 = new ItemStack(Material.CHEST);
          ItemMeta m3 = stage3.getItemMeta();
          m3.setDisplayName(c("&c&lBackpack Stage 3!"));   
          lore.add(c("&7&oEffective for Level 3 me.dxrk.Enchants."));
          lore.add(c("&7&o4 Backpack Rows."));
          lore.add(c("&b10,000 &dTokens"));
          m3.setLore(lore);
          lore.clear();
          stage3.setItemMeta(m3);
          inv.setItem(19, stage3);
          
          ItemStack stage4 = new ItemStack(Material.CHEST);
          ItemMeta m4 = stage4.getItemMeta();
          m4.setDisplayName(c("&c&lBackpack Stage 4!"));   
          lore.add(c("&7&oEffective for Level 4 me.dxrk.Enchants."));
          lore.add(c("&7&o5 Backpack Rows."));
          lore.add(c("&b15,000 &dTokens"));
          m4.setLore(lore);
          lore.clear();
          stage4.setItemMeta(m4);
          inv.setItem(29, stage4);
          
          ItemStack stage5 = new ItemStack(Material.CHEST);
          ItemMeta m5 = stage5.getItemMeta();
          m5.setDisplayName(c("&c&lBackpack Stage 5!"));   
          lore.add(c("&7&oEffective for Level 5 me.dxrk.Enchants."));
          lore.add(c("&7&o6 Backpack Rows."));
          lore.add(c("&b20,000 &dTokens"));
          m5.setLore(lore);
          lore.clear();
          stage5.setItemMeta(m5);
          inv.setItem(39, stage5);
          
          ItemStack stage6 = new ItemStack(Material.CHEST);
          ItemMeta m6 = stage6.getItemMeta();
          m6.setDisplayName(c("&c&lBackpack Stage 6!"));   
          lore.add(c("&7&oEffective for Level 6 me.dxrk.Enchants."));
          lore.add(c("&7&o7 Backpack Rows."));
          lore.add(c("&b30,000 &dTokens"));
          m6.setLore(lore);
          lore.clear();
          stage6.setItemMeta(m6);
          inv.setItem(41, stage6);
          
          ItemStack stage7 = new ItemStack(Material.CHEST);
          ItemMeta m7 = stage7.getItemMeta();
          m7.setDisplayName(c("&c&lBackpack Stage 7!"));   
          lore.add(c("&7&oEffective for Level 7 me.dxrk.Enchants."));
          lore.add(c("&7&o8 Backpack Rows."));
          lore.add(c("&b40,000 &dTokens"));
          m7.setLore(lore);
          lore.clear();
          stage7.setItemMeta(m7);
          inv.setItem(33, stage7);
          
          ItemStack stage8 = new ItemStack(Material.CHEST);
          ItemMeta m8 = stage8.getItemMeta();
          m8.setDisplayName(c("&c&lBackpack Stage 8!"));   
          lore.add(c("&7&oEffective for Level 8 me.dxrk.Enchants."));
          lore.add(c("&7&o9 Backpack Rows."));
          lore.add(c("&b50,000 &dTokens"));
          m8.setLore(lore);
          lore.clear();
          stage8.setItemMeta(m8);
          inv.setItem(25, stage8);
          
          ItemStack stage9 = new ItemStack(Material.CHEST);
          ItemMeta m9 = stage9.getItemMeta();
          m9.setDisplayName(c("&c&lBackpack Stage 9!"));   
          lore.add(c("&7&oEffective for Level 9 me.dxrk.Enchants."));
          lore.add(c("&7&o10 Backpack Rows."));
          lore.add(c("&b75,000 &dTokens"));
          m9.setLore(lore);
          lore.clear();
          stage9.setItemMeta(m9);
          inv.setItem(15, stage9);
          
          ItemStack stage10 = new ItemStack(Material.CHEST);
          ItemMeta m10 = stage10.getItemMeta();
          m10.setDisplayName(c("&c&lBackpack Stage 10!"));   
          lore.add(c("&7&oEffective for Level 10 me.dxrk.Enchants."));
          lore.add(c("&7&o11 Backpack Rows."));
          lore.add(c("&b100,000 &dTokens"));
          m10.setLore(lore);
          lore.clear();
          stage10.setItemMeta(m10);
          inv.setItem(5, stage10);
          
          p.openInventory(inv);
        } else if (args.length == 1 && 
          args[0].equalsIgnoreCase("clear")) {
          Inventory bp = backpacks.get(p);
          for (int i = 0; i < bp.getSize(); i++)
            bp.setItem(i, null); 
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have cleared your backpack!"));
        } 
      } 
    } else if (cmd.getName().equalsIgnoreCase("SellAll") && 
      cs instanceof Player) {
      Player p = (Player)cs;
      sellAllItems(p);
      p.sendMessage(c("&cRight Click your pickaxe to Sell easier!"));
    }
    if(cmd.getName().equalsIgnoreCase("multi")) {

    	if(args.length ==0) {
    		if(!(cs instanceof Player)) return false;
    	cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dYour Multi: &b" + getMulti((Player)cs)));
    	} else if(args.length == 1) {
    		if(!cs.hasPermission("rank.admin")) return false;
			if(args[0].equalsIgnoreCase("add")) {
				cs.sendMessage(ChatColor.RED + "Specify a Players Name!");
    		}
    	} else if(args.length ==  2) {
    		if(!cs.hasPermission("rank.admin")) {
    			cs.sendMessage(ChatColor.RED + "No Permission.");
    		return false;
    		}
    		if(args[0].equalsIgnoreCase("add")) {
    			Player reciever = Bukkit.getServer().getPlayer(args[1]);
    			if(!reciever.isOnline()) {
    				cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    			} else {
    				cs.sendMessage(ChatColor.RED + "You must specify an amount");
    			}
    		}
    		if(args[0].equalsIgnoreCase("set")) {
    			Player reciever = Bukkit.getServer().getPlayer(args[1]);
    			if(!reciever.isOnline()) {
    				cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    			} else {
    				cs.sendMessage(ChatColor.RED + "You must specify an amount");
    			}
    		}
    		} else if(args.length == 3) {
    			Player reciever = Bukkit.getServer().getPlayer(args[1]);
    			if(!cs.hasPermission("rank.admin")) return false;
    			if(args[0].equalsIgnoreCase("add") ) {
    				
    				if(isDbl(args[2])) {
    					if (reciever == null) {
    		               cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    		                return false;
    		              } 
    					
    				String Amount = args[2].replace("-", "");
    	               double amnt = Double.parseDouble(Amount);
    	               double d = this.settings.getMultiplier().getDouble(reciever.getUniqueId().toString());
    	               double newMulti = d+amnt;
    	               if(newMulti > Functions.multiCap(reciever)) {
    	            	   newMulti = Functions.multiCap(reciever);
    	               }
    	               
    	               this.settings.getMultiplier().set(reciever.getUniqueId().toString(), newMulti);
    	               this.settings.saveMultiplier();
    					
    	            
    				}
    			}
    			if(args[0].equalsIgnoreCase("set") ) {
    				
    				if(isDbl(args[2])) {
    					if (reciever == null) {
    		               cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
    		                return false;
    		              } 
    					
    				String Amount = args[2].replace("-", "");
    	               double amount = Double.parseDouble(Amount);
    	               this.settings.getMultiplier().set(reciever.getUniqueId().toString(), amount);
    	               this.settings.saveMultiplier();
    	            
    				}
    			}
    		}
    	}
     if(cmd.getName().equalsIgnoreCase("withdraw")) {
    		if(!cs.hasPermission("rank.admin")) {
    			cs.sendMessage(ChatColor.RED+ "Withdraw is disabled");
    			return false;
    		}
    		
    	}
     if(cmd.getName().equalsIgnoreCase("forcereset")) {
    	 if(cs.hasPermission("rank.owner")) {
    	 ResetHandler.resetAllMines();
    	 }
     }
    
    return false;
  }
}
