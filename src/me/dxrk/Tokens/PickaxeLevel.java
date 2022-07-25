package me.dxrk.Tokens;

import com.connorlinfoot.titleapi.TitleAPI;

import me.dxrk.Events.LocksmithHandler;
import me.dxrk.Events.PickXPHandler;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.ResetHandler;
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
				im.setDisplayName(c("&cYou must be Prestige 50+ to unlock Lucky!"));
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
				im.setDisplayName(c("&cYou must be Prestige 50+ to unlock Booster!"));
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
				im.setDisplayName(c("&cYou must be Prestige 50+ to unlock RuneParty!"));
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
				im.setDisplayName(c("&cYou must be Prestige 50+ to unlock Multiply!"));
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
		Inventory enchantmenu = Bukkit.createInventory(null, InventoryType.CHEST, c("&d&lPurchase Enchants!"));
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
		setEnchantItem("Discovery", c("&bUpgrade Discovery"), c("&7Finds a Rune after “Blocks Till Dust” Quota is met."), 1000, 0.181626, 100, enchantmenu, 0, p);
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
		
		
		
		if(e.getClickedInventory().getName().equals(c("&d&lPurchase Enchants!"))) {
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
					if(p.hasPermission("prestige.100")) {
						p.closeInventory();
						p.sendMessage(c("&f&l&kO&e&lMax Prestige&f&l&kO&r"));
					}
					
					else if(p.hasPermission("prestige.99")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.100");
						Main.perms.playerRemove(p, "prestige.99");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l10"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.98")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.99");
						Main.perms.playerRemove(p, "prestige.98");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l9"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.97")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.98");
						Main.perms.playerRemove(p, "prestige.97");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l8"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.96")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.97");
						Main.perms.playerRemove(p, "prestige.96");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l7"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.95")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.96");
						Main.perms.playerRemove(p, "prestige.95");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l6"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.94")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.95");
						Main.perms.playerRemove(p, "prestige.94");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l5"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.93")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.94");
						Main.perms.playerRemove(p, "prestige.93");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l4"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.92")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.93");
						Main.perms.playerRemove(p, "prestige.92");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l3"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.91")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.92");
						Main.perms.playerRemove(p, "prestige.91");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l2"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.90")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.91");
						Main.perms.playerRemove(p, "prestige.90");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&f&l&kO&e&lOlympus&f&l&kO&r &6&l1"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.89")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.90");
						Main.perms.playerRemove(p, "prestige.89");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l10"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.88")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.89");
						Main.perms.playerRemove(p, "prestige.88");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l9"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.87")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.88");
						Main.perms.playerRemove(p, "prestige.87");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l8"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.86")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.87");
						Main.perms.playerRemove(p, "prestige.86");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l7"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.85")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.86");
						Main.perms.playerRemove(p, "prestige.85");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l6"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.84")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.85");
						Main.perms.playerRemove(p, "prestige.84");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l5"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.83")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.84");
						Main.perms.playerRemove(p, "prestige.83");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l4"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.82")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.83");
						Main.perms.playerRemove(p, "prestige.82");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l3"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.81")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.82");
						Main.perms.playerRemove(p, "prestige.81");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l2"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.80")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.81");
						Main.perms.playerRemove(p, "prestige.80");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&a&lElysian Fields &6&l1"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.79")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.80");
						Main.perms.playerRemove(p, "prestige.79");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l10"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.78")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.79");
						Main.perms.playerRemove(p, "prestige.78");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l9"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.77")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.78");
						Main.perms.playerRemove(p, "prestige.77");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l8"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.76")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.77");
						Main.perms.playerRemove(p, "prestige.76");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l7"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.75")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.76");
						Main.perms.playerRemove(p, "prestige.75");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l6"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.74")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.75");
						Main.perms.playerRemove(p, "prestige.74");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l5"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.73")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.74");
						Main.perms.playerRemove(p, "prestige.73");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l4"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.72")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.73");
						Main.perms.playerRemove(p, "prestige.72");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l3"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.71")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.72");
						Main.perms.playerRemove(p, "prestige.71");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l2"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.70")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.71");
						Main.perms.playerRemove(p, "prestige.70");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&b&lMeadows of Asphodel &6&l1"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.69")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.70");
						Main.perms.playerRemove(p, "prestige.69");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l10"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.68")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.69");
						Main.perms.playerRemove(p, "prestige.68");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l9"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.67")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.68");
						Main.perms.playerRemove(p, "prestige.67");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l8"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.66")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.67");
						Main.perms.playerRemove(p, "prestige.66");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l7"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.65")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.66");
						Main.perms.playerRemove(p, "prestige.65");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l6"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.64")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.65");
						Main.perms.playerRemove(p, "prestige.64");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l5"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.63")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.64");
						Main.perms.playerRemove(p, "prestige.63");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l4"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.62")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.63");
						Main.perms.playerRemove(p, "prestige.62");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l3"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.61")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.62");
						Main.perms.playerRemove(p, "prestige.61");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l2"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.60")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.61");
						Main.perms.playerRemove(p, "prestige.60");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&c&lFields of Mourning &6&l1"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.59")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.60");
						Main.perms.playerRemove(p, "prestige.59");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l10"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.58")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.59");
						Main.perms.playerRemove(p, "prestige.58");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l9"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.57")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.58");
						Main.perms.playerRemove(p, "prestige.57");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l8"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.56")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.57");
						Main.perms.playerRemove(p, "prestige.56");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l7"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.55")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.56");
						Main.perms.playerRemove(p, "prestige.55");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l6"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.54")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.55");
						Main.perms.playerRemove(p, "prestige.54");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l5"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.53")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.54");
						Main.perms.playerRemove(p, "prestige.53");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l4"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.52")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.53");
						Main.perms.playerRemove(p, "prestige.52");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l3"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.51")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.52");
						Main.perms.playerRemove(p, "prestige.51");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l2"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}
					else if(p.hasPermission("prestige.50")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.51");
						Main.perms.playerRemove(p, "prestige.50");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&2&lLethe &6&l1"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.49")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.50");
						Main.perms.playerAdd(p, "enchant.lucky");
						Main.perms.playerAdd(p, "enchant.runeparty");
						Main.perms.playerAdd(p, "enchant.booster");
						Main.perms.playerAdd(p, "enchant.multiply");
						Main.perms.playerAdd(p, "enchant.fortuity");
						Main.perms.playerRemove(p, "prestige.49");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l10"));
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.48")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.49");
						Main.perms.playerRemove(p, "prestige.48");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l9"));
						
						
					}else if(p.hasPermission("prestige.47")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.48");
						Main.perms.playerRemove(p, "prestige.47");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l8"));
						
						
					}else if(p.hasPermission("prestige.46")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.47");
						Main.perms.playerRemove(p, "prestige.46");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l7"));
						
						
					}else if(p.hasPermission("prestige.45")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.46");
						Main.perms.playerRemove(p, "prestige.45");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l6"));
						
						
					}else if(p.hasPermission("prestige.44")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.45");
						Main.perms.playerRemove(p, "prestige.44");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l5"));
						
						
					}else if(p.hasPermission("prestige.43")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.44");
						Main.perms.playerRemove(p, "prestige.43");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l4"));
						
						
					}else if(p.hasPermission("prestige.42")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.43");
						Main.perms.playerRemove(p, "prestige.42");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l3"));
						
						
					}else if(p.hasPermission("prestige.41")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.42");
						Main.perms.playerRemove(p, "prestige.41");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l2"));
						
						
					}
					
					
					else if(p.hasPermission("prestige.40")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.41");
						Main.perms.playerRemove(p, "prestige.40");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lCocytus &6&l1"));
					}else if(p.hasPermission("prestige.39")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.40");
						Main.perms.playerRemove(p, "prestige.39");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l10"));
						
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.38")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.39");
						Main.perms.playerRemove(p, "prestige.38");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l9"));
						
						
					}else if(p.hasPermission("prestige.37")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.38");
						Main.perms.playerRemove(p, "prestige.37");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l8"));
						
						
					}else if(p.hasPermission("prestige.36")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.37");
						Main.perms.playerRemove(p, "prestige.36");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l7"));
						
						
					}else if(p.hasPermission("prestige.35")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.36");
						Main.perms.playerRemove(p, "prestige.35");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l6"));
						
						
					}else if(p.hasPermission("prestige.34")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.35");
						Main.perms.playerRemove(p, "prestige.34");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l5"));
						
						
					}else if(p.hasPermission("prestige.33")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.34");
						Main.perms.playerRemove(p, "prestige.33");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l4"));
						
						
					}else if(p.hasPermission("prestige.32")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.33");
						Main.perms.playerRemove(p, "prestige.32");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l3"));
						
						
					}else if(p.hasPermission("prestige.31")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.32");
						Main.perms.playerRemove(p, "prestige.31");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l2"));
						
						
					}
					
					else if(p.hasPermission("prestige.30")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.31");
						Main.perms.playerRemove(p, "prestige.30");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lAcheron &6&l1"));
					}else if(p.hasPermission("prestige.29")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.30");
						Main.perms.playerRemove(p, "prestige.29");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l10"));
						
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.28")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.29");
						Main.perms.playerRemove(p, "prestige.28");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l9"));
						
						
					}else if(p.hasPermission("prestige.27")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.28");
						Main.perms.playerRemove(p, "prestige.27");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l8"));
						
						
					}else if(p.hasPermission("prestige.26")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.27");
						Main.perms.playerRemove(p, "prestige.26");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l7"));
						
						
					}else if(p.hasPermission("prestige.25")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.26");
						Main.perms.playerRemove(p, "prestige.25");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l6"));
						
						
					}else if(p.hasPermission("prestige.24")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.25");
						Main.perms.playerRemove(p, "prestige.24");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l5"));
						
						
					}else if(p.hasPermission("prestige.23")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.24");
						Main.perms.playerRemove(p, "prestige.23");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l4"));
						
						
					}else if(p.hasPermission("prestige.22")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.23");
						Main.perms.playerRemove(p, "prestige.22");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l3"));
						
						
					}else if(p.hasPermission("prestige.21")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.22");
						Main.perms.playerRemove(p, "prestige.21");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l2"));
						
						
					}
					
					
					else if(p.hasPermission("prestige.20")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.21");
						Main.perms.playerRemove(p, "prestige.20");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lStyx &6&l1"));
						
					}else if(p.hasPermission("prestige.19")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.20");
						Main.perms.playerAdd(p, "enchant.stake");
						Main.perms.playerRemove(p, "prestige.19");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l10"));
						
						
						p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
					}else if(p.hasPermission("prestige.18")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.19");
						Main.perms.playerRemove(p, "prestige.18");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l9"));
						
						
					}else if(p.hasPermission("prestige.17")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.18");
						Main.perms.playerRemove(p, "prestige.17");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l8"));
						
						
					}else if(p.hasPermission("prestige.16")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.17");
						Main.perms.playerRemove(p, "prestige.16");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l7"));
						
						
					}else if(p.hasPermission("prestige.15")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.16");
						Main.perms.playerRemove(p, "prestige.15");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l6"));
						
						
					}else if(p.hasPermission("prestige.14")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.15");
						Main.perms.playerRemove(p, "prestige.14");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l5"));
						
						
					}else if(p.hasPermission("prestige.13")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.14");
						Main.perms.playerRemove(p, "prestige.13");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l4"));
						
						
					}else if(p.hasPermission("prestige.12")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.13");
						Main.perms.playerRemove(p, "prestige.12");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l3"));
						
						
					}else if(p.hasPermission("prestige.11")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.12");
						Main.perms.playerRemove(p, "prestige.11");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l2"));
						
						
					}else if(p.hasPermission("prestige.10")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.11");
						Main.perms.playerRemove(p, "prestige.10");
						RankupHandler.getInstance().setRank(p, 1);
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&4&lTartarus &6&l1"));
						
						
						
					} else if(p.hasPermission("prestige.9")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.10");
						Main.perms.playerAdd(p, "enchant.junkpile");
						Main.perms.playerRemove(p, "prestige.9");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 10"));
						
						
					} else if(p.hasPermission("prestige.8")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.9");
						Main.perms.playerRemove(p, "prestige.8");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 9"));
						
						
					} else if(p.hasPermission("prestige.7")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.8");
						Main.perms.playerRemove(p, "prestige.7");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 8"));
						
						
					} else if(p.hasPermission("prestige.6")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.7");
						Main.perms.playerRemove(p, "prestige.6");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 7"));
						
						
					} else if(p.hasPermission("prestige.5")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.6");
						Main.perms.playerRemove(p, "prestige.5");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 6"));
						
						
					} else if(p.hasPermission("prestige.4")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.5");
						Main.perms.playerAdd(p, "enchant.vaporize");
						Main.perms.playerRemove(p, "prestige.4");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 5"));
						
						
					} else if(p.hasPermission("prestige.3")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.4");
						Main.perms.playerRemove(p, "prestige.3");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 4"));
						
						
					} else if(p.hasPermission("prestige.2")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.3");
						Main.perms.playerRemove(p, "prestige.2");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 3"));
						
						
					} else if(p.hasPermission("prestige.1")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.2");
						Main.perms.playerRemove(p, "prestige.1");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 2"));
						
						
					} else if(!p.hasPermission("prestige.1")) {
						p.closeInventory();
						Main.perms.playerAdd(p, "prestige.1");
						RankupHandler.getInstance().setRank(p, 1);
						
						LocksmithHandler.getInstance().addKey(p, "Prestige", 1);
						
						TitleAPI.sendTitle(p, 2, 40,  2, c("&c&lRank Prestiged"), c("&6&lPrestige 1"));
						
						
					}
				} else {
					p.closeInventory();
					p.sendMessage(c("&cNot High Enough Level!"));
					return;
				}
			}
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
