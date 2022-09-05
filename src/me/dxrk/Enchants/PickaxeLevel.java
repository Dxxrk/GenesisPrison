package me.dxrk.Enchants;

import com.connorlinfoot.titleapi.TitleAPI;

import me.dxrk.Events.LocksmithHandler;
import me.dxrk.Events.PickXPHandler;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Main.Main;
import me.dxrk.Events.ResetHandler;
import me.dxrk.Main.SettingsManager;
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
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
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
        int blockss = this.getBlocks((String)ii.getItemMeta().getLore().get(0));
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(0, c("&6&lUnlock Tokens: " +(blockss + 1)));
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
    }
    
    
    
    
   
    
    HashMap<Player, ItemStack> held = new HashMap<Player, ItemStack>();
    
    
    
    
    
   public HashMap<Player, Boolean> happened = new HashMap<Player, Boolean>();
    
   public HashMap<Player, Integer> enchants = new HashMap<Player, Integer>();
   
   
    
    
    
    
    

    public Inventory pinv = Bukkit.createInventory(null, InventoryType.HOPPER, c("&6&lPRESTIGE OPTIONS"));
    
    
    
    
   
    
    public void upgrade(Player p) {
    	
    	
		
			if(this.getBlocks(p.getItemInHand().getItemMeta().getLore().get(1)) >= 55555) {
				
					
						
				this.addUnlock(p, p.getItemInHand());
				
					
					
					p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lLevel Up!"), c("&6&l+1 Unlock Token"));
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
				        @Override
				        public void run() {
				        	p.closeInventory();
							spawnFireworks(p.getLocation(), 1);
				        }
				    }, 40L);
					PickXPHandler.getInstance().removeXP(p, p.getItemInHand(), 55555);
				
					
				
		} else {
			p.playSound(p.getLocation(), Sound.VILLAGER_HIT, 1.3f, 1.3f);
			p.sendMessage(c("&5You do not have &b" + Main.formatAmt(55555) + " &5XP!"));
		}
	
    }
    public void takeUnlock(Player p, ItemStack ii) {
    	int blockss = this.getBlocks((String)ii.getItemMeta().getLore().get(0));
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(0, c("&6&lUnlock Tokens: " +(blockss - 1)));
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
	
	public void addFortune(Player p, int num) {
		for(int n = 0; n < num; n++) {
			int level = getBlocks(p.getItemInHand().getItemMeta().getLore().get(2));
			int price = (int) (10*(level*0.0025));
		ItemStack i = p.getItemInHand().clone();
		ItemMeta im = i.getItemMeta();
		List<String> lore = im.getLore();
		
		
		if(p.hasPermission("prestige.41") || p.hasPermission("prestige.42") || p.hasPermission("prestige.43") || p.hasPermission("prestige.44") || p.hasPermission("prestige.45")
				||p.hasPermission("prestige.46")||p.hasPermission("prestige.47")||p.hasPermission("prestige.48")||p.hasPermission("prestige.49")||p.hasPermission("prestige.50")) {
				if(getBlocks(lore.get(2)) >= 30000) {
					p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
					return;
				}
			}
		
		
		else if(p.hasPermission("prestige.31") || p.hasPermission("prestige.32") || p.hasPermission("prestige.33") || p.hasPermission("prestige.34") || p.hasPermission("prestige.35")
				||p.hasPermission("prestige.36")||p.hasPermission("prestige.37")||p.hasPermission("prestige.38")||p.hasPermission("prestige.39")||p.hasPermission("prestige.40")) {
				if(getBlocks(lore.get(2)) >= 30000) {
					p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
					return;
				}
			}
		
		
		else if(p.hasPermission("prestige.21") || p.hasPermission("prestige.22") || p.hasPermission("prestige.23") || p.hasPermission("prestige.24") || p.hasPermission("prestige.25")
				||p.hasPermission("prestige.26")||p.hasPermission("prestige.27")||p.hasPermission("prestige.28")||p.hasPermission("prestige.29")||p.hasPermission("prestige.30")) {
				if(getBlocks(lore.get(2)) >= 30000) {
					p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
					return;
				}
			}
		
		else if(p.hasPermission("prestige.11") || p.hasPermission("prestige.12") || p.hasPermission("prestige.13") || p.hasPermission("prestige.14") || p.hasPermission("prestige.15")
			|| p.hasPermission("prestige.16") || p.hasPermission("prestige.17") || p.hasPermission("prestige.18") || p.hasPermission("prestige.19") || p.hasPermission("prestige.20")) {
			if(getBlocks(lore.get(2)) >= 30000) {
				p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
				return;
			}
		} else {
		
		if(getBlocks(lore.get(2)) >= 30000) {
			p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
			return;
		}
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
			p.sendMessage(c("&cNot Enough XP"));
			return;
		}
		}
	}
	
	
	
	public void setEnchantItem(String enchantName, String name, String desc, int priceStart, double priceMultiple, int maxLevel, Inventory inv, int slot, Player p) {
		
		int enchantLevel = 0;
		
		for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
  	    	String s = p.getItemInHand().getItemMeta().getLore().get(x);
  	    	if (ChatColor.stripColor(s).contains(enchantName)) {
  	    		enchantLevel = getBlocks(p.getItemInHand().getItemMeta().getLore().get(x));
  	    	}
  	    }
		int price = 0;
		if(enchantLevel == 0) {
			price = priceStart;
		}else {
			price = priceStart+((int) (priceStart*(enchantLevel*priceMultiple)));
		}
		
		String cost = "";
		
		if(enchantLevel != maxLevel){
			cost = c("&dCost: &b"+price+" XP");
			}else {
				cost = c("&dCost: &bMAX LEVEL!");
			}
		
		
		ItemStack i = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(name);
		List<String> lore = new ArrayList<String>();
		lore.add(c("&dCurrent Level: &b"+enchantLevel));
		lore.add(c("&dMax Level: &b"+maxLevel));
		lore.add(cost);
		lore.add(desc);
		im.setLore(lore);
		i.setItemMeta(im);
		lore.clear();
		if(enchantName.equals("Discovery")) {
			if(!p.hasPermission("enchant.discoveryunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Discovery"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Encounter")) {
			if(!p.hasPermission("enchant.encounterunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Encounter"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Wave")) {
			if(!p.hasPermission("enchant.waveunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Wave"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Greed")) {
			if(!p.hasPermission("enchant.greedunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Greed"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Research")) {
			if(!p.hasPermission("enchant.researchunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Research"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("TokenFinder")) {
			if(!p.hasPermission("enchant.tokenfinderunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock TokenFinder"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		
		if(enchantName.equals("Vaporize")) {
			if(!p.hasPermission("enchant.vaporizeunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Vaporize"));
				im.setLore(null);
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.vaporize")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 5+ to unlock Vaporize!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
			
		}
		if(enchantName.equals("Junkpile")) {
			if(!p.hasPermission("enchant.junkpileunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Junkpile"));
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.junkpile")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 10+ to unlock Junkpile!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Stake")) {
			if(!p.hasPermission("enchant.stakeunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Stake"));
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.stake")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 20+ to unlock Stake!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Lucky")) {
			if(!p.hasPermission("enchant.luckyunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Lucky"));
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.lucky")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 40+ to unlock Lucky!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Booster")) {
			if(!p.hasPermission("enchant.boosterunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Booster"));
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.booster")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 35+ to unlock Booster!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("RuneParty")) {
			if(!p.hasPermission("enchant.runepartyunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock RuneParty"));
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.runeparty")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 25+ to unlock RuneParty!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Multiply")) {
			if(!p.hasPermission("enchant.multiplyunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Multiply"));
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.multiply")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 30+ to unlock Multiply!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		if(enchantName.equals("Fortuity")) {
			if(!p.hasPermission("enchant.fortuityunlock")) {
				i.setType(Material.GOLD_BLOCK);
				im = i.getItemMeta();
				im.setDisplayName(c("&6&lSpend 1 Unlock Token to unlock Fortuity"));
				i.setItemMeta(im);
			}
			if(!p.hasPermission("enchant.fortuity")) {
				i.setType(Material.BARRIER);
				im = i.getItemMeta();
				im.setDisplayName(c("&cYou must be Prestige 50+ to unlock Fortuity!"));
				im.setLore(null);
				i.setItemMeta(im);
			}
		}
		
		
		inv.setItem(slot, i);
	}

	
	
	public void openenchantmenu(Player p) {
		Inventory enchantmenu = Bukkit.createInventory(null, InventoryType.CHEST, c("&d&lPurchase me.dxrk.Enchants!"));
		ItemStack upgrade = new ItemStack(Material.DIAMOND_PICKAXE);
		ItemMeta um = upgrade.getItemMeta();
		um.setDisplayName(c("&d&lUpgrade your pickaxe!"));
		List<String> lore = new ArrayList<String>();
		lore.add(c("&6&l+1 Unlock Token"));
		lore.add(c("&5Need 55,555XP"));
		um.setLore(lore);
		upgrade.setItemMeta(um);
		lore.clear();
		
		enchantmenu.setItem(22, upgrade);
		
		setEnchantItem("Lucky", c("&bUpgrade Lucky"), c("&7Boosts the chance of other enchants to proc."), 100000, 0.1, 10, enchantmenu, 2, p);
		setEnchantItem("Booster", c("&bUpgrade Booster"), c("&7Chance to find low timed boosts."), 100000, 0.05, 10, enchantmenu, 3, p);
		setEnchantItem("RuneParty", c("&bUpgrade RuneParty"), c("&7Chance to give everyone online a rune."), 100000, 0.1, 10, enchantmenu, 4, p);
		setEnchantItem("Multiply", c("&bUpgrade Multiply"), c("&7Chance to double the effectiveness of TokenFinder."), 100000, 0.1, 10, enchantmenu, 5, p);
		setEnchantItem("Fortuity", c("&bUpgrade Fortuity"), c("&7Boosts the effectiveness of Fortune."), 100000, 0.1, 10, enchantmenu, 6, p);
		setEnchantItem("Discovery", c("&bUpgrade Discovery"), c("&7Finds a Rune after �Blocks Till Dust� Quota is met."), 1000, 0.181626, 100, enchantmenu, 0, p);
		setEnchantItem("Encounter", c("&bUpgrade Encounter"), c("&7Chance to find a Rune after breaking a block."), 1000, 0.181626, 100, enchantmenu, 1, p);
		setEnchantItem("Wave", c("&bUpgrade Wave"), c("&7Chance to break an entire layer of the mine."), 1000, 0.181626, 100, enchantmenu, 7, p);
		setEnchantItem("Explosion", c("&bUpgrade Explosion"), c("&7Chance to explode a large hole in the mine(5x5x5)."), 1000, 0.181626, 100, enchantmenu, 8, p);
		setEnchantItem("Greed", c("&bUpgrade Greed"), c("&7Increases selling price for blocks."), 1000, 0.181626, 100, enchantmenu, 9, p);
		setEnchantItem("Research", c("&bUpgrade Research"), c("&7Chance to grant you one level instantly."), 1000, 0.181626, 100, enchantmenu, 10, p);
		setEnchantItem("TokenFinder", c("&bUpgrade TokenFinder"), c("&7Gives you a random amount of tokens and gives everyone online 10% of the amount."), 1000, 0.181626, 100, enchantmenu, 11, p);
		setEnchantItem("Vaporize", c("&bUpgrade Vaporize"), c("&7Low Chance to break the entire mine."), 1000, 0.181626, 100, enchantmenu, 15, p);
		setEnchantItem("Stake", c("&bUpgrade Stake"), c("&7 When activated the money you make for 30 seconds will be given as a bonus after the timer ends."), 1000, 0.181626, 100, enchantmenu, 16, p);
		setEnchantItem("Junkpile", c("&bUpgrade Junkpile"), c("&7Chance to find random items while mining."), 1000, 0.181626, 100, enchantmenu, 17, p);
		
		
		
	  int level = getBlocks(p.getItemInHand().getItemMeta().getLore().get(2));
		int price = (int) (10*(level*0.0025));
		ItemStack fortune = new ItemStack(Material.ENCHANTED_BOOK);
		ItemMeta fm = fortune.getItemMeta();
		fm.setDisplayName(c("&bUpgrade Fortune"));
		lore.add(c("&dCurrent Level: &b"+getBlocks(p.getItemInHand().getItemMeta().getLore().get(2))));
		lore.add(c("&dMax Level: &b30,000"));
		lore.add(c("&5Cost: &b"+price+" " + "XP"));
		lore.add(c("&7Left Click: 1"));
		lore.add(c("&7Right Click: 10"));
		lore.add(c("&7Shift +Left Click: 100"));
		fm.setLore(lore);
		lore.clear();
		fortune.setItemMeta(fm);
		enchantmenu.setItem(13, fortune);
		
		
		
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
	
	public void upgradeEnchant(Player p, ItemStack i) {
		
		 
		
		String[] display = ChatColor.stripColor(i.getItemMeta().getDisplayName()).split(" ");
		String name = display[1];
		int price = getBlocks(i.getItemMeta().getLore().get(2));
		
		ItemStack pi = p.getItemInHand().clone();
		ItemMeta pm = pi.getItemMeta();
		List<String> lore = pm.getLore();
		List<String> trinket = new ArrayList<String>();
		for(int t = 0; t < lore.size(); t++) {
			String s = lore.get(t);
			if(ChatColor.stripColor(s).toLowerCase().contains("trinket:")) {
				lore.remove(t);
				trinket.add(s);
			}
		}
		
		
			int line = 0;
		
		int x;
		for (x = 0; x < lore.size(); x++) {
  	    	String s = lore.get(x);
  	    	if (ChatColor.stripColor(s).contains(name)) {
  	    		line = x;
  	    	}
  	    }
		if(line !=0) {
		int level = this.getBlocks(lore.get(line));
		int plus = level+1;
		if(level == getBlocks(i.getItemMeta().getLore().get(1))) {
			p.sendMessage(c("&cYou already have the maximum level of this enchant!"));
			return;
		}
  			lore.set(line, c("&9&l"+name+" &b"+ (plus)));
		
		
		} else {
			lore.add(c("&9&l"+name+" &b1"));
		}
		
		
		
		int blocks = getBlocks(lore.get(1));
		
		if(blocks >= price) {
		
			lore.addAll(trinket);
			pm.setLore(lore);
	        pi.setItemMeta(pm);
	        p.setItemInHand(pi);
	        p.updateInventory();
	        
	        PickXPHandler.getInstance().removeXP(p, p.getItemInHand(), price);
		}else {
			p.sendMessage(c("&cNot Enough XP"));
			return;
		}
		openenchantmenu(p);
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
	    	  
	    	  openenchantmenu(p);
	    	  
	    	  }
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
		
		
		
		if(e.getClickedInventory().getName().equals(c("&d&lPurchase me.dxrk.Enchants!"))) {
			e.setCancelled(true);
			if(e.getClick().equals(ClickType.LEFT)) {
				if((e.getSlot() >= 0 && e.getSlot() <= 12) || (e.getSlot() >= 14 && e.getSlot() <= 21) || (e.getSlot() >=23 && e.getSlot() <=26)) {
					if(e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem().equals(null)) return;
					if(e.getCurrentItem().equals(Spacer())) return;
					
						if(e.getCurrentItem().getType().equals(Material.BARRIER)) {
							return;
						}
						if(e.getCurrentItem().getType().equals(Material.GOLD_BLOCK)) {
							if(getBlocks(p.getItemInHand().getItemMeta().getLore().get(0)) >0) {
								
							String[] s = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split(" ");
							String ss = s[6];
							Main.perms.playerAdd(p, "enchant."+ss+"unlock");
							takeUnlock(p, p.getItemInHand());
							openenchantmenu(p);
							return;
							} else {
								p.closeInventory();
								p.sendMessage(c("&cNot Enough Unlock Tokens"));
								return;
							}
						}
						
						upgradeEnchant(p, e.getCurrentItem());
					}
				if(e.getSlot() == 22) {
					upgrade(p);
					p.closeInventory();
				}
				
				}
			if(e.getSlot() == 13) {
				if(e.getClick().equals(ClickType.LEFT)) {
					addFortune(p, 1);
				} else if(e.getClick().equals(ClickType.SHIFT_LEFT)) {
					addFortune(p, 100);
				} else if(e.getClick().equals(ClickType.RIGHT)) {
					addFortune(p, 10);
				}
				
				
				openenchantmenu(p);
			}
			
			
			
			
			
			
		}
		
		
		
		
		
		
		
		
		
		if(e.getInventory().getName().equals(c("&6&lPRESTIGE OPTIONS"))) {
			e.setCancelled(true);
			
			if(e.getSlot() == 2) {
				if(RankupHandler.getInstance().getRank(p) >=100) {
					prestige(p);
				} else {
					p.closeInventory();
					p.sendMessage(c("&cNot High Enough Level!"));
					return;
				}
			}
		}
		
		
		
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();

		for(int i = 0; i < 101; i++) {
			if (p.hasPermission("prestige." + i)) {
				settings.getPlayerData().set(p.getUniqueId().toString() + ".Prestige", i);
				settings.savePlayerData();
				return;
			}
		}
		if(!p.hasPermission("prestige.1")){
			settings.getPlayerData().set(p.getUniqueId().toString() + ".Prestige", 0);
			settings.savePlayerData();
			return;
		}

	}


	public void prestige(Player p){

		for(int i = 1; i < 101; i++){
			if(p.hasPermission("prestige."+i)){
				if(i==4){
					Main.perms.playerAdd(p, "enchant.vaporize");
				}
				if(i==9){
					Main.perms.playerAdd(p, "enchant.junkpile");
				}
				if(i==19){
					Main.perms.playerAdd(p, "enchant.stake");
				}
				if(i==24){
					Main.perms.playerAdd(p, "enchant.runeparty");
				}
				if(i==29){
					Main.perms.playerAdd(p, "enchant.multiply");
				}
				if(i==34){
					Main.perms.playerAdd(p, "enchant.booster");
				}
				if(i==39){
					Main.perms.playerAdd(p, "enchant.lucky");
				}
				if(i==49){
					Main.perms.playerAdd(p, "enchant.fortuity");
				}
				if(i==100){
					return;
				}
				p.closeInventory();
				Main.perms.playerAdd(p, "prestige."+(i+1));
				Main.perms.playerRemove(p, "prestige."+i);
				settings.getPlayerData().set(p.getUniqueId().toString()+".Prestige", (i+1));
				RankupHandler.getInstance().setRank(p, 1);

				LocksmithHandler.getInstance().addKey(p, "Prestige", 1);

				TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige "+(i+1)));
				settings.savePlayerData();
				return;
			}


		}
		if(!p.hasPermission("prestige.1")){
			p.closeInventory();
			Main.perms.playerAdd(p, "prestige.1");
			settings.getPlayerData().set(p.getUniqueId().toString()+".Prestige", 1);
			RankupHandler.getInstance().setRank(p, 1);

			LocksmithHandler.getInstance().addKey(p, "Prestige", 1);

			TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 1"));
			settings.savePlayerData();
			return;
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
