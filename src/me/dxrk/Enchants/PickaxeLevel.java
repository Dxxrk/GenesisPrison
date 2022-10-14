package me.dxrk.Enchants;

import com.connorlinfoot.titleapi.TitleAPI;
import me.dxrk.Events.PickXPHandler;
import me.dxrk.Events.ResetHandler;
import me.dxrk.Main.Main;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;


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
	    am.setDisplayName(c("&cTest Pickaxe"));
	    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
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
    
    
    
    
   
    
    HashMap<Player, ItemStack> held = new HashMap<>();
    
    
    
    
    
   public HashMap<Player, Boolean> happened = new HashMap<>();
    
   public HashMap<Player, Integer> enchants = new HashMap<>();
   
   
    
    
    
    
    

    public Inventory pinv = Bukkit.createInventory(null, InventoryType.HOPPER, c("&6&lPRESTIGE OPTIONS"));
    
    
    
    
   
    
    public void upgrade(Player p) {
    	
    	
		
			if(this.getBlocks(p.getItemInHand().getItemMeta().getLore().get(1)) >= 55555) {
				
					
						
				this.addUnlock(p, p.getItemInHand());
				
					
					
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lLevel Up!"), c("&6&l+1 Unlock Token"));
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
						p.closeInventory();
						spawnFireworks(p.getLocation(), 1);
					}, 40L);
					PickXPHandler.getInstance().removeXP(p, p.getItemInHand(), 55555);
				
					
				
		} else {
			p.playSound(p.getLocation(), Sound.VILLAGER_HIT, 1.3f, 1.3f);
			p.sendMessage(c("&5You do not have &b" + Main.formatAmt(55555) + " &5XP!"));
		}
	
    }
    public void takeUnlock(Player p, ItemStack ii) {
    	int blockss = this.getBlocks(ii.getItemMeta().getLore().get(0));
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(0, c("&6&lUnlock Tokens: " +(blockss - 1)));
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
    }



	public void practice(int x){
		if(x == 100){
			return; // cancels
		}
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
		if(sender instanceof Player) {
			Player p = (Player)sender;
		if(label.equalsIgnoreCase("prestige")) {
			
			List<String> lore = new ArrayList<>();
			
			
			
			ItemStack rank = new ItemStack(Material.EXP_BOTTLE);
			ItemMeta rm = rank.getItemMeta();
			rm.setDisplayName(c("&c&lRank Prestige"));
			lore.add(c("&7&oReset rank to 1!"));
			lore.add(c("&7&oUnlock perks to have throughout the next prestiges!"));
			rm.setLore(lore);
			rank.setItemMeta(rm);
			
			pinv.setItem(0, Spacer());
			pinv.setItem(1, Spacer());
			pinv.setItem(2, rank);
			pinv.setItem(3, Spacer());
			pinv.setItem(4, Spacer());
			p.openInventory(pinv);
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
	
	public void addFortune(Player p, int num, Boolean isMax) {
		if(isMax == false) {
			for (int n = 0; n < num; n++) {
				int level = getBlocks(p.getItemInHand().getItemMeta().getLore().get(2));
				int price = (int) (100 * (level * 0.2));
				ItemStack i = p.getItemInHand().clone();
				ItemMeta im = i.getItemMeta();
				List<String> lore = im.getLore();


				if (getBlocks(lore.get(2)) >= 10000) {
					p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
					return;
				}

				int blockss = getBlocks(i.getItemMeta().getLore().get(1));
				if (blockss >= price) {

					lore.set(1, ChatColor.GRAY + "XP: " + ChatColor.AQUA + (blockss - price));
					int one = 1;
					lore.set(2, c("&7Fortune: " + (getBlocks(lore.get(2)) + one)));
					im.setLore(lore);
					i.setItemMeta(im);
					p.setItemInHand(i);
					p.updateInventory();


				} else {
					return;
				}
			}
		}
			if(isMax == true){
				for(int n = 0; n < 100000; n++) {
					int level = getBlocks(p.getItemInHand().getItemMeta().getLore().get(2));
					int price = (int) (10*(level*0.0025));
					ItemStack i = p.getItemInHand().clone();
					ItemMeta im = i.getItemMeta();
					List<String> lore = im.getLore();




					if(getBlocks(lore.get(2)) >= 30000) {
						p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
						return;
					}

					int blockss = getBlocks(i.getItemMeta().getLore().get(1));
					if(blockss >= price) {

					lore.set(1, ChatColor.GRAY + "XP: " + ChatColor.AQUA + (blockss - price));
					int one = 1;
					lore.set(2, c("&7Fortune: "+ (getBlocks(lore.get(2))+one)));
					im.setLore(lore);
					i.setItemMeta(im);
					p.setItemInHand(i);
					p.updateInventory();


					} else {
						return;
					}
				}
		}
	}
	
	
	
	public void setEnchantItem(String enchantName, Material mat, String name, String desc, int priceStart, double priceMultiple, int maxLevel, Inventory inv, int slot, Player p) {
		
		int enchantLevel = 0;
		
		for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
  	    	String s = p.getItemInHand().getItemMeta().getLore().get(x);
  	    	if (ChatColor.stripColor(s).contains(enchantName)) {
  	    		enchantLevel = getBlocks(p.getItemInHand().getItemMeta().getLore().get(x));
  	    	}
  	    }
		int price;
		if(enchantLevel == 0) {
			price = priceStart;
		}else {
			price = priceStart+((int) (priceStart*(enchantLevel*priceMultiple)));
		}
		
		String cost;
		
		if(enchantLevel != maxLevel){
			cost = c("&dCost: &b"+price+" XP");
			}else {
				cost = c("&dCost: &bMAX LEVEL!");
			}
		
		
		ItemStack i = new ItemStack(mat);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> lore = new ArrayList<>();
		lore.add(c("&dCurrent Level: &b"+enchantLevel));
		lore.add(c("&dMax Level: &b"+maxLevel));
		lore.add(cost);
		lore.add(desc);
		im.setLore(lore);
		i.setItemMeta(im);
		lore.clear();
		
		
		inv.setItem(slot, i);
	}

	
	
	public void openenchantmenu(Player p) {
		Inventory enchantmenu = Bukkit.createInventory(null, InventoryType.CHEST, c("&d&lPurchase Enchants!"));

		List<String> lore = new ArrayList<>();

		setEnchantItem("Lucky", Material.GOLD_NUGGET, c("&bUpgrade Lucky"), c("&7Boosts the chance of other enchants to proc."), 100000, 0.1, 10, enchantmenu, 2, p);
		setEnchantItem("Booster", Material.POTION, c("&bUpgrade Booster"), c("&7Chance to find low timed boosts."), 100000, 0.05, 10, enchantmenu, 3, p);
		setEnchantItem("Key Party", Material.EYE_OF_ENDER, c("&bUpgrade Key Party"), c("&7Chance to give everyone online a rune."), 100000, 0.1, 10, enchantmenu, 4, p);
		setEnchantItem("Multiply", Material.EMERALD, c("&bUpgrade Multiply"), c("&7Chance to double the effectiveness of all currencies"), 100000, 0.1, 10, enchantmenu, 5, p);
		setEnchantItem("Fortuity", Material.GOLD_INGOT, c("&bUpgrade Fortuity"), c("&7Boosts the effectiveness of Fortune."), 100000, 0.1, 10, enchantmenu, 6, p);
		setEnchantItem("Dust Finder", Material.SUGAR, c("&bUpgrade Dust Finder"), c("&7Finds a Rune after �Blocks Till Dust� Quota is met."), 1000, 0.181626, 100, enchantmenu, 0, p);
		setEnchantItem("Key Finder", Material.TRIPWIRE_HOOK, c("&bUpgrade Key Finder"), c("&7Chance to find a Rune after breaking a block."), 1000, 0.181626, 100, enchantmenu, 1, p);
		setEnchantItem("Wave", Material.GOLD_PLATE, c("&bUpgrade Wave"), c("&7Chance to break an entire layer of the mine."), 1000, 0.181626, 100, enchantmenu, 7, p);
		setEnchantItem("Explosion", Material.FIREWORK_CHARGE, c("&bUpgrade Explosion"), c("&7Chance to explode a large hole in the mine(5x5x5)."), 1000, 0.181626, 100, enchantmenu, 8, p);
		setEnchantItem("Greed", Material.DIAMOND, c("&bUpgrade Greed"), c("&7Increases selling price for blocks."), 1000, 0.181626, 100, enchantmenu, 9, p);
		setEnchantItem("Research", Material.REDSTONE, c("&bUpgrade Research"), c("&7Chance to grant you one level instantly."), 1000, 0.181626, 100, enchantmenu, 10, p);
		setEnchantItem("Token Finder", Material.PRISMARINE_CRYSTALS, c("&bUpgrade Token Finder"), c("&7Gives you a random amount of tokens and gives everyone online 10% of the amount."), 1000, 0.181626, 100, enchantmenu, 11, p);
		setEnchantItem("Nuke", Material.TNT, c("&bUpgrade Nuke"), c("&7Low Chance to break the entire mine."), 1000, 0.181626, 100, enchantmenu, 15, p);
		setEnchantItem("Junkpile", Material.BUCKET, c("&bUpgrade Junkpile"), c("&7Chance to find random items while mining."), 1000, 0.181626, 100, enchantmenu, 17, p);
		setEnchantItem("Fortune", Material.NETHER_STAR, c("&bUpgrade Fortune"), c("&7Increases amount of blocks you sell."), 1000, 0.181626, 100, enchantmenu, 17, p);
		setEnchantItem("Prestige Finder", Material.BEACON, c("&bUpgrade Prestige Finder"), c("&7Chance to randomly gain some prestiges"), 1000, 0.181626, 100, enchantmenu, 17, p);
		setEnchantItem("XP <>", Material.EXP_BOTTLE, c("&bUpgrade XP <>"), c("&7..."), 1000, 0.181626, 100, enchantmenu, 17, p);
		setEnchantItem("Laser", Material.BLAZE_ROD, c("&bUpgrade Laser"), c("&7..."), 1000, 0.181626, 100, enchantmenu, 17, p);
		
		

		
		
		
		enchantmenu.setItem(12, Spacer());
		enchantmenu.setItem(14, Spacer());
		enchantmenu.setItem(18, Spacer());
		enchantmenu.setItem(19, Spacer());
		enchantmenu.setItem(20, Spacer());
		enchantmenu.setItem(21, Spacer());
		enchantmenu.setItem(23, Spacer());
		enchantmenu.setItem(24, Spacer());
		enchantmenu.setItem(25, Spacer());
		enchantmenu.setItem(26, Spacer());
		
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
		list.add("Token");
		list.add("Key");
		list.add("XP");
		list.add("Luck");

		return list;
	}

	public List<String> orgainzeEnchants(List<String> list){
		List<String> lore = new ArrayList<>();
		lore.add(c("&b&m-<>-&aEnchants&b&m-<>-"));
		List<String> ilore = list;
		List<String> Enchants = new ArrayList<>();
		for(String s : ilore){
			if(Enchants().contains(ChatColor.stripColor(s))){
				Enchants.add(s);
			}
		}
		Collections.sort(Enchants);
		lore.addAll(Enchants);



		return lore;
	}

	public List<String> organizeTrinkets(List<String> list){
		List<String> lore = new ArrayList<>();
		lore.add(c("&b&m-<>-&aTrinkets&b&m-<>-"));
		List<String> ilore = list;
		List<String> Trinkets = new ArrayList<>();
		for(String s : ilore){
			if(Trinkets().contains(ChatColor.stripColor(s))){
				Trinkets.add(s);
			}
		}
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
				i = 1000 * (level * 0.0015); // 18 Million Level 5,000 MAX

				break;
			case "Dust Finder":
				i = 1000 * (level * 0.002); // 25 Million Level 5,000 MAX

				break;
			case "Fortune":
				i = 100 * (level * 0.002); // 1 Billion Level 100,000 MAX

				break;
			case "Wave":
				i = 5000 * (level * 0.02); // 50 Million Level 1,000 MAX

				break;
			case "Token Finder":
				i = 500 * (level * 0.02); // 31 Million Level 2,500 MAX

				break;
			case "Charity":
				i = 2500 * (level * 0.15); // 187 Million Level 1,000 MAX

				break;
			case "Nuke":
				i = 10000*(level*1.19760479049); // 1.5 Billion Level 500 MAX

				break;
			case "Explosion":
				i = 2500*(level*0.025); // 31 Million Level 1,000 MAX

				break;
			case "Research":
				i = 3000*(level*0.008); // 108 Million Level 3,000 MAX

				break;
			case "Greed":
				i = 5000*(level*10); // 252 Million Level 100 MAX

				break;
			case "Junkpile":
				i = 2500*(level*0.02); // 100 Million Level 2,000 MAX

				break;
			case "Key Party":
				i = 3500*(level*0.035); // 61 Million Level 1,000 MAX

				break;
			case "Prestige Finder":
				i = 2500*(level*0.005); // 156 Million Level 5,000 MAX

				break;
			case "XP":
				i = 1000*(level*0.005); // 250 Million Level 10,000 MAX

				break;
			case "Fortuity":
				i = 7500*(level*0.1); // 375 Million Level 1,000 MAX

				break;
			case "Booster":
				i = 5000*(level*0.017); // 265 Million Level 2,500 MAX

				break;
			case "Multiply":
				i = 10000*(level*0.1); // 1.12 Billion Level 1,500 MAX

				break;
			case "Lucky":
				i = 4000*(level*0.0023); // 115 Million Level 5,000 MAX

				break;
			case "Laser":
				i = 8500*(level*0.4); // 957 Million Level 750 MAX

				break;

		}


		return i;
	}

	
	public void upgradeEnchant(Player p, ItemStack i, String Enchant, int num, Boolean isMax) {
		
		 //Fix the way trinkets apply according to the new lore setup + refine trinkets in their handler -- done 1/2
		//Change this to accomodate for the new lore setup / try to find a way to organize enchants on apply -- done
		
		//Enchants
		ItemStack pitem = i.clone();
		ItemMeta pm = pitem.getItemMeta();
		List<String> lore = pm.getLore();
		if(isMax == false) {

			if (!lore.contains(Enchant)) {
				for(int x = 0; x < num; x++) {
					int level = 0;
					int plus = level + 1;
					double price = enchantPrice(Enchant, level);
					if (Tokens.getInstance().getTokens(p) > price) {
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
						return;
					}
					lore.add(c("&c" + Enchant + " &e" + plus));
					Tokens.getInstance().takeTokens(p, price);
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
					double price = enchantPrice(Enchant, level);
					if(Tokens.getInstance().getTokens(p) > price){
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
						return;
					}
					lore.set(line, c("&c"+Enchant+" &e"+plus));
					Tokens.getInstance().takeTokens(p, price);
				}
			}
		}
		else {
			if (!lore.contains(Enchant)) {
				for(int x = 0; x < 100001; x++) {
					int level = 0;
					int plus = level + 1;
					double price = enchantPrice(Enchant, level);
					if (Tokens.getInstance().getTokens(p) > price) {
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
						return;
					}
					lore.add(c("&c" + Enchant + " &e" + plus));
					Tokens.getInstance().takeTokens(p, price);
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
					double price = enchantPrice(Enchant, level);
					if(Tokens.getInstance().getTokens(p) > price){
						p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
						return;
					}
					lore.set(line, c("&c"+Enchant+" &e"+plus));
					Tokens.getInstance().takeTokens(p, price);
				}
			}

		}
		pm.setLore(Lore(lore));
		i.setItemMeta(pm);
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
		
		    happened.put(p, false);		
		
		
		
		if(e.getClickedInventory().getName().equals(c("&d&lPurchase Enchants!"))) {
			e.setCancelled(true);

				if((e.getSlot() >= 0 && e.getSlot() <= 12) || (e.getSlot() >= 14 && e.getSlot() <= 21) || (e.getSlot() >=23 && e.getSlot() <=26)) {
					if(e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem() == null) return; if(e.getCurrentItem().equals(Spacer())) return;
						String[] display = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split(" ");
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
