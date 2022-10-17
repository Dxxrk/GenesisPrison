package me.dxrk.Enchants;

import me.dxrk.Events.ResetHandler;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class PickaxeLevel implements Listener, CommandExecutor{
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	public static PickaxeLevel instance = new PickaxeLevel();
	  
	  public static PickaxeLevel getInstance() {
	    return instance;
	  }

	  public SettingsManager settings = SettingsManager.getInstance();
	
	public ItemStack spacer() {
	    ItemStack a = new ItemStack(Material.DIAMOND_PICKAXE, 1, (short)0);
	    ItemMeta am = a.getItemMeta();
		List<String> lore = new ArrayList<>();
	    am.setDisplayName(c("&cTest Pickaxe"));
	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		lore.add(c("&b&m-<>-&aEnchants&b&m-<>-"));
		lore.add(" ");
		lore.add(c("&b&m-<>-&aTrinkets 0/4&b&m-<>-"));
		lore.add(" ");
		lore.add(c("&b&m-<>-&aLevel&b&m-<>-"));
		lore.add(c("&cLevel: &e1"));
		lore.add(c("&cProgress: &e0%"));
		am.setLore(lore);
	    a.setItemMeta(am);
	    return a;
	  }
	
	
	public void EFFfix(Player p, ItemStack i ) {
		ItemStack hand = i.clone();
		ItemMeta am = hand.getItemMeta();
	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    hand.setItemMeta(am);
	    p.setItemInHand(hand);
        p.updateInventory();
	}
	
	
	public int getBlocks(String s) {
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
            Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }
    
    public void addUnlock(Player p, ItemStack ii) {
        int blockss = this.getBlocks(ii.getItemMeta().getLore().get(0));
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(0, c("&6&lUnlock Tokens: " +(blockss + 1)));
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
    }
    
    
    
    
   
    

    

    
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg3) {
		
		if(sender instanceof Player) {
			Player p = (Player)sender;
			
			
			
			
		if(label.equalsIgnoreCase("pick")) {
			if(!p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
				 p.sendMessage(c("&cPlease Hold a Pickaxe!"));
				return true;
			}
			openenchantmenu(p);
			
			
		}
		}
		if(sender instanceof Player) {
			Player p = (Player)sender;
		if(label.equalsIgnoreCase("pickaxe")) {
			if(!p.hasPermission("rank.owner")) {
				return false;
			}
			 p.getInventory().addItem(spacer());
			 p.updateInventory();
		}
		}
		if(label.equalsIgnoreCase("resetallmines")) {
			
			if(sender instanceof Player) {
				Player p = (Player)sender;
				if(p.hasPermission("rank.owner")) {
			ResetHandler.resetAllMines();
				}
			}
		}
		return false;
	}
	
	
	static boolean isMultipleof5 (int n) 
	{ 
		return n%5 == 0;
	}
	
	
	
	
	
	
	public void ResetXP(Player p) {
		ItemStack pick = p.getItemInHand().clone();
		ItemMeta pm = pick.getItemMeta();
		List<String> lore = pm.getLore();
		lore.set(1, c("&7XP: &b0"));
		pm.setLore(lore);
		pick.setItemMeta(pm);
		p.setItemInHand(pick);
		
	}
	
	
	
	
	public ItemStack Spacer() {
	    ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)8);
	    ItemMeta im = i.getItemMeta();
	    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Genesis"));
	    im.addEnchant(Enchantment.DURABILITY, 0, false);
	    i.setItemMeta(im);
	    i.removeEnchantment(Enchantment.DURABILITY);
	    return i;
	  }
	

	
	
	
	public void setEnchantItem(String enchantName, Material mat, String name, String desc, int priceStart, Inventory inv, int slot, Player p) {
		
		int enchantLevel = 0;
		
		for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
  	    	String s = p.getItemInHand().getItemMeta().getLore().get(x);
  	    	if (ChatColor.stripColor(s).contains(enchantName)) {
  	    		enchantLevel = getBlocks(p.getItemInHand().getItemMeta().getLore().get(x));
  	    	}
  	    }
		double price;
		if(enchantLevel == 0) {
			price = priceStart;
		}else {
			price = enchantPrice(enchantName, enchantLevel);
		}
		
		String cost;
		
		if(enchantLevel != maxLevel(enchantName)){
			cost = c("&bCost: &e"+((int)price)+"⛀");
			}else {
				cost = c("&bCost: &eMAX LEVEL!");
			}
		
		
		ItemStack i = new ItemStack(mat);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> lore = new ArrayList<>();
		lore.add(desc);
		lore.add(" ");
		lore.add(c("&bCurrent Level: &e"+enchantLevel));
		lore.add(c("&bMax Level: &e"+maxLevel(enchantName)));
		lore.add(cost);
		lore.add(" ");
		lore.add(c("&7&oLeft Click: Buy 1"));
		lore.add(c("&7&oRight Click: Buy 10"));
		lore.add(c("&7&oShift+Right Click: Buy 100"));
		lore.add(c("&7&oShift+Left Click: Buy Max"));

		im.setLore(lore);
		i.setItemMeta(im);
		lore.clear();
		
		
		inv.setItem(slot, i);
	}

	
	
	public void openenchantmenu(Player p) {
		Inventory enchantmenu = Bukkit.createInventory(null, 54, c("&d&lPurchase Enchants!"));

		List<String> lore = new ArrayList<>();

		for(int i = 0; i < 54; i++){
			enchantmenu.setItem(i, Spacer());
		}

		setEnchantItem("Lucky", Material.GOLD_NUGGET, c("&bUpgrade Lucky"), c("&7Boosts the chance of other enchants to proc."), 4000, enchantmenu, 13, p);
		setEnchantItem("Booster", Material.POTION, c("&bUpgrade Booster"), c("&7Chance to find low timed boosts."), 5000, enchantmenu, 20, p);
		setEnchantItem("Key Party", Material.EYE_OF_ENDER, c("&bUpgrade Key Party"), c("&7Chance to give everyone online a key."), 3500, enchantmenu, 39, p);
		setEnchantItem("Multiply", Material.EMERALD, c("&bUpgrade Multiply"), c("&7Chance to double the effectiveness of all currencies"), 10000, enchantmenu, 23, p);
		setEnchantItem("Fortuity", Material.GOLD_INGOT, c("&bUpgrade Fortuity"), c("&7Boosts the effectiveness of Fortune."), 7500, enchantmenu, 41, p);
		setEnchantItem("Dust Finder", Material.SUGAR, c("&bUpgrade Dust Finder"), c("&7Finds a Rune after Blocks Till Dust Quota is met."), 1000, enchantmenu, 14, p);
		setEnchantItem("Key Finder", Material.TRIPWIRE_HOOK, c("&bUpgrade Key Finder"), c("&7Chance to find a Rune after breaking a block."), 1000, enchantmenu, 4, p);
		setEnchantItem("Wave", Material.GOLD_PLATE, c("&bUpgrade Wave"), c("&7Chance to break an entire layer of the mine."), 5000, enchantmenu, 32, p);
		setEnchantItem("Explosion", Material.FIREBALL, c("&bUpgrade Explosion"), c("&7Chance to explode a large hole in the mine(5x5x5)."), 2500, enchantmenu, 30, p);
		setEnchantItem("Greed", Material.DIAMOND, c("&bUpgrade Greed"), c("&7Increases selling price for blocks."), 5000, enchantmenu, 29, p);
		setEnchantItem("Research", Material.REDSTONE, c("&bUpgrade Research"), c("&7Chance to grant you one level instantly."), 3000, enchantmenu, 40, p);
		setEnchantItem("Token Finder", Material.PRISMARINE_CRYSTALS, c("&bUpgrade Token Finder"), c("&7Increase the amount of tokens randomly found."), 500, enchantmenu, 12, p);
		setEnchantItem("Nuke", Material.TNT, c("&bUpgrade Nuke"), c("&7Low Chance to break the entire mine."), 10000,  enchantmenu, 31, p);
		setEnchantItem("Junkpile", Material.BUCKET, c("&bUpgrade Junkpile"), c("&7Chance to find random items while mining."), 2500, enchantmenu, 33, p);
		setEnchantItem("Fortune", Material.NETHER_STAR, c("&bUpgrade Fortune"), c("&7Increases amount of blocks you sell."), 100, enchantmenu, 21, p);
		setEnchantItem("Prestige Finder", Material.BEACON, c("&bUpgrade Prestige Finder"), c("&7Chance to randomly gain some prestiges"), 2500, enchantmenu, 22, p);
		setEnchantItem("XP", Material.EXP_BOTTLE, c("&bUpgrade XP <>"), c("&7..."), 1000, enchantmenu, 24, p);
		setEnchantItem("Laser", Material.BLAZE_ROD, c("&bUpgrade Laser"), c("&7..."), 8500, enchantmenu, 49, p);
		
		

		
		
		

		
	  p.openInventory(enchantmenu);
	}

	public int lineOfEnchant(Player p, String Enchant){
		int x = 0;
		ItemStack pitem = p.getItemInHand().clone();
		List<String> lore = pitem.getItemMeta().getLore();
		for(int i = 0; i < lore.size(); i++){
			String s = lore.get(i);
			if(ChatColor.stripColor(s).contains(Enchant)){
				x = i;
			}
		}

		return x;
	}

	private List<String> Enchants(){
		List<String> list = new ArrayList<>();
		list.add("Key Finder");
		list.add("Key Party");
		list.add("Dust Finder");
		list.add("Token Finder");
		list.add("Wave");
		list.add("Junkpile");
		list.add("Nuke");
		list.add("Explosion");
		list.add("Fortuity");
		list.add("Lucky");
		list.add("Booster");
		list.add("Research");
		list.add("Multiply");
		list.add("Fortune");
		list.add("Greed");
		list.add("Prestige Finder");
		list.add("XP");
		list.add("Laser");
		list.add("Charity");

		return list;
	}
	private List<String> Trinkets(){
		List<String> list = new ArrayList<>();
		list.add("Extra Tokens");
		list.add("Double Keys");
		list.add("Bonus XP");
		list.add("Additional Luck");
		list.add("Sell Boost");

		return list;
	}

	public List<String> orgainzeEnchants(List<String> list){
		List<String> lore = new ArrayList<>();
		lore.add(c("&b&m-<>-&aEnchants&b&m-<>-"));
		List<String> ilore = list;
		List<String> Enchants = new ArrayList<>();
		for(String s : ilore){
			if(ChatColor.stripColor(s).contains("Enchants") || ChatColor.stripColor(s).contains("Trinkets") || ChatColor.stripColor(s).contains("Level:") || ChatColor.stripColor(s).contains("Progress:"))  continue;
			if(s.equals(" ")) continue;
			for(String ss : Enchants()){
				if(ChatColor.stripColor(s).contains(ss)){
					Enchants.add(s);
				}
			}

		}
		Collections.sort(Enchants);
		lore.addAll(Enchants);

		return lore;
	}

	public List<String> organizeTrinkets(List<String> list){
		List<String> lore = new ArrayList<>();
		int trinkets = 0;
		List<String> ilore = list;
		List<String> Trinkets = new ArrayList<>();

		for(String s : ilore){
			if(ChatColor.stripColor(s).contains("Enchants") || ChatColor.stripColor(s).contains("Trinkets") || ChatColor.stripColor(s).contains("Level:") || ChatColor.stripColor(s).contains("Progress:"))  continue;
			if(s.equals(" ")) continue;
			for(String ss : Trinkets()){
				if(ChatColor.stripColor(s).contains(ss)){
					Trinkets.add(s);
					trinkets++;
				}
			}


		}
		lore.add(c("&b&m-<>-&aTrinkets "+trinkets+"/4&b&m-<>-"));
		Collections.sort(Trinkets);
		lore.addAll(Trinkets);

		return lore;
	}

	public List<String> pickLevel(List<String> list){
		List<String> lore = new ArrayList<>();
		List<String> ilore = list;
		for(String s : ilore){
			if(ChatColor.stripColor(s).contains("Level:")){
				lore.add(s);
			}
		}
		for(String s : ilore){
			if(ChatColor.stripColor(s).contains("Progress:")){
				lore.add(s);
			}
		}


		return lore;
	}
	public List<String> Lore(List<String> list){
		List<String> lore = new ArrayList<>(orgainzeEnchants(list));
		lore.add(c("  "));
		lore.addAll(organizeTrinkets(list));
		lore.add(c("  "));
		lore.add(c("&b&m-<>-&aLevel&b&m-<>-"));
		lore.addAll(pickLevel(list));
		return lore;
	}

	public double enchantPrice(String Enchant, int level){
		double i = 0;
		switch (Enchant) {
			case "Key Finder":
				if(level == 0) {
					i = 1000;
					break;
				}
				i = 1000+(1000 * (level * 0.0015)); // 18 Million Level 5,000 MAX

				break;
			case "Dust Finder":
				if(level == 0) {
					i = 1000;
					break;
				}
				i = 1000+(1000 * (level * 0.002)); // 25 Million Level 5,000 MAX

				break;
			case "Fortune":
				if(level == 0) {
					i = 100;
					break;
				}
				i = 100+(100 * (level * 0.032)); // 1 Billion Level 25,000 MAX

				break;
			case "Wave":
				if(level == 0) {
					i = 5000;
					break;
				}
				i = 5000+(5000 * (level * 0.02)); // 50 Million Level 1,000 MAX

				break;
			case "Token Finder":
				if(level == 0) {
					i = 500;
					break;
				}
				i = 500+(500 * (level * 0.02)); // 31 Million Level 2,500 MAX

				break;
			case "Charity":
				if(level == 0) {
					i = 2500;
					break;
				}
				i = 2500+(2500 * (level * 0.15)); // 187 Million Level 1,000 MAX

				break;
			case "Nuke":
				if(level == 0) {
					i = 10000;
					break;
				}
				i = 10000+(10000*(level*1.19760479049)); // 1.5 Billion Level 500 MAX

				break;
			case "Explosion":
				if(level == 0) {
					i = 2500;
					break;
				}
				i = 2500+(2500*(level*0.025)); // 31 Million Level 1,000 MAX

				break;
			case "Research":
				if(level == 0) {
					i = 3000;
					break;
				}
				i = 3000+(3000*(level*0.008)); // 108 Million Level 3,000 MAX

				break;
			case "Greed":
				if(level == 0) {
					i = 5000;
					break;
				}
				i = 5000+(5000*(level*10)); // 252 Million Level 100 MAX

				break;
			case "Junkpile":
				if(level == 0) {
					i = 2500;
					break;
				}
				i = 2500+(2500*(level*0.02)); // 100 Million Level 2,000 MAX

				break;
			case "Key Party":
				if(level == 0) {
					i = 3500;
					break;
				}
				i = 3500+(3500*(level*0.035)); // 61 Million Level 1,000 MAX

				break;
			case "Prestige Finder":
				if(level == 0) {
					i = 2500;
					break;
				}
				i = 2500+(2500*(level*0.005)); // 156 Million Level 5,000 MAX

				break;
			case "XP":
				if(level == 0) {
					i = 1000;
					break;
				}
				i = 1000+(1000*(level*0.005)); // 250 Million Level 10,000 MAX

				break;
			case "Fortuity":
				if(level == 0) {
					i = 7500;
					break;
				}
				i = 7500+(7500*(level*0.1)); // 375 Million Level 1,000 MAX

				break;
			case "Booster":
				if(level == 0) {
					i = 5000;
					break;
				}
				i = 5000+(5000*(level*0.017)); // 265 Million Level 2,500 MAX

				break;
			case "Multiply":
				if(level == 0) {
					i = 10000;
					break;
				}
				i = 10000+(10000*(level*0.1)); // 1.12 Billion Level 1,500 MAX

				break;
			case "Lucky":
				if(level == 0) {
					i = 4000;
					break;
				}
				i = 4000+(4000*(level*0.0023)); // 115 Million Level 5,000 MAX

				break;
			case "Laser":
				if(level == 0) {
					i = 8500;
					break;
				}
				i = 8500+(8500*(level*0.4)); // 957 Million Level 750 MAX

				break;

		}


		return i;
	}

	public int maxLevel(String Enchant){
		int i = 0;
		switch(Enchant){
			case "Key Finder":
			case "Dust Finder":
			case "Prestige Finder":
			case "Lucky":
				i = 5000;

				break;

			case "Wave":
			case "Fortuity":
			case "Key Party":
			case "Explosion":
			case "Charity":
				i = 1000;

				break;
			case "Token Finder":
			case "Booster":
				i = 2500;

				break;

			case "Fortune":
				i = 25000;

				break;

			case "Nuke":
				i = 500;

				break;

			case "Research":
				i = 3000;

				break;
			case "Greed":
				i = 100;

				break;
			case "Junkpile":
				i = 2000;

				break;

			case "XP":
				i = 10000;

				break;

			case "Multiply":
				i = 1500;

				break;

			case "Laser":
				i = 750;

				break;
		}
		return i;
	}

	private Tokens tokens = Tokens.getInstance();

	
	public void upgradeEnchant(Player p, ItemStack i, String Enchant, int num, Boolean isMax) {
		
		 //Fix the way trinkets apply according to the new lore setup + refine trinkets in their handler -- done 1/2
		//Change this to accomodate for the new lore setup / try to find a way to organize enchants on apply -- done
		
		//Enchants
		ItemStack pitem = i.clone();
		ItemMeta pm = pitem.getItemMeta();
		List<String> lore = pm.getLore();
		boolean hasEnchant = false;
		if(isMax == false) {

			for(String s : lore){
				if(ChatColor.stripColor(s).contains(Enchant)){
					hasEnchant = true;
				}
			}


			if (hasEnchant == false) {
				for(int x = 0; x < num; x++) {
					int level = 0;
					int plus = level + 1;
					int price = (int) enchantPrice(Enchant, level);
					if (Tokens.getInstance().getTokens(p) >= price) {
						if(plus > maxLevel(Enchant)) return;
						lore.add(c("&c" + Enchant + " &e" + plus));
						this.tokens.takeTokens(p, price);

					} else {
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
						return;
					}

				}

			} else {
				int line = 0;
				for(int z = 0; z < lore.size(); z++){
					String s = lore.get(z);
					if(ChatColor.stripColor(s).contains(Enchant))
						line = z;
				}
				for(int x = 0; x < num; x++){
					int level = getBlocks(lore.get(line));
					int plus = level + 1;
					int price = (int) enchantPrice(Enchant, level);
					if(Tokens.getInstance().getTokens(p) >= price){
						if(plus > maxLevel(Enchant)) return;
						lore.set(line, c("&c"+Enchant+" &e"+plus));
						this.tokens.takeTokens(p, price);
					} else {
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
						return;
					}

				}

			}
		}
		else {
			for(String s : lore){
				if(ChatColor.stripColor(s).contains(Enchant)){
					hasEnchant = true;
				}
			}

			if (hasEnchant == false) {
				for(int x = 0; x < 100001; x++) {
					int level = 0;
					int plus = level + 1;
					int price = (int) enchantPrice(Enchant, level);
					if (Tokens.getInstance().getTokens(p) >= price) {
						if(plus > maxLevel(Enchant)) return;
						lore.add(c("&c" + Enchant + " &e" + plus));
						this.tokens.takeTokens(p, price);

					} else {
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
						return;
					}

				}

			} else {
				int line = 0;
				for(int z = 0; z < lore.size(); z++){
					String s = lore.get(z);
					if(ChatColor.stripColor(s).contains(Enchant))
						line = z;
				}
				for(int x = 0; x < 100001; x++){
					int level = getBlocks(lore.get(line));
					int plus = level + 1;
					int price = (int) enchantPrice(Enchant, level);
					if(Tokens.getInstance().getTokens(p) >= price) {
						if (plus > maxLevel(Enchant)) return;
						lore.set(line, c("&c" + Enchant + " &e" + plus));
						this.tokens.takeTokens(p, price);
					} else {
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens "));
						return;
					}
				}

			}

		}
		pm.setLore(Lore(lore));
		pitem.setItemMeta(pm);
		p.setItemInHand(pitem);
		p.updateInventory();



	}
	
	@EventHandler
	  public void openMenu(PlayerInteractEvent e) {
	    Player p = e.getPlayer();
	    if (p.getItemInHand() == null)
	      return; 
	    ItemStack item = p.getItemInHand();
	    if (item.getType().equals(Material.DIAMOND_PICKAXE)) {
	      if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
	        return;
		  if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
				  openenchantmenu(p);
	    	  
	    	  }

	    }
	  }
	
	@EventHandler
	public void oninv(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		
		if (e.getClickedInventory() == null)
		      return; 
		    if (e.getClickedInventory().getName() == null)
		      return; 
		

		
		
		
		if(e.getClickedInventory().getName().equals(c("&d&lPurchase Enchants!"))) {
			e.setCancelled(true);


					if(e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem() == null) return;
					if(e.getCurrentItem().equals(Spacer())) return;
					String[] display = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("Upgrade ");
						String name = display[1];


						if(e.getClick().equals(ClickType.LEFT)) {
							upgradeEnchant(p, p.getItemInHand(), name, 1, false);
						} else if(e.getClick().equals(ClickType.RIGHT)){
							upgradeEnchant(p, p.getItemInHand(), name, 10, false);
						} else if(e.getClick().equals(ClickType.SHIFT_RIGHT)){
							upgradeEnchant(p, p.getItemInHand(), name, 100, false);
						} else if(e.getClick().equals(ClickType.SHIFT_LEFT)){
							upgradeEnchant(p, p.getItemInHand(), name, 1, true);
						}


				

			p.updateInventory();
			openenchantmenu(p);
			
			
			
			
			
		}
	}





	
	
	
	public static void spawnFireworks(Location location, int amount){
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();
       
        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());
       
        fw.setFireworkMeta(fwm);
        fw.detonate();
       
        for(int i = 0;i<amount; i++){
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }
	

}
