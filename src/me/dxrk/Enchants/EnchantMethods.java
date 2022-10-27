package me.dxrk.Enchants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.dxrk.Commands.CMDVoteShop;
import me.dxrk.Main.Functions;
import me.dxrk.Events.PickXPHandler;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Events.SellHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Events.ResetHandler;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Events.ResetHandler.ResetReason;

import me.jet315.prisonmines.mine.Mine; 
import net.md_5.bungee.api.ChatColor;

public class EnchantMethods {
	
	static EnchantMethods instance = new EnchantMethods();
	
	public static EnchantMethods getInstance() {
	    return instance;
	  }
	
	private Methods m = Methods.getInstance();
	private final SettingsManager settings = SettingsManager.getInstance();
	
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
	  
	  public void addLevel(Player p, ItemStack ii, String s) {
		  ItemStack i = ii.clone();
	        ItemMeta im = i.getItemMeta();
	        List<String> lore = im.getLore();
	        int x;
	        for (x = 0; x < lore.size(); x++) {
	        	if(lore.get(x).contains(ChatColor.stripColor(s))) {
	        		int blockss = this.getFortune((String)ii.getItemMeta().getLore().get(x));
	        		if(blockss <10) {
	        		lore.set(x, (Object)ChatColor.RED + s+" " + (Object)ChatColor.RED + (blockss + 1));
	        		}
	        	}
	        }
	        im.setLore(lore);
	        i.setItemMeta(im);
	        p.setItemInHand(i);
	        p.updateInventory();
	    }
	  
	  public static ApplicableRegionSet set(Block b) {
		    WorldGuardPlugin worldGuard = WorldGuardPlugin.inst();
		    RegionManager regionManager = worldGuard.getRegionManager(b.getWorld());
		    return regionManager.getApplicableRegions(b.getLocation());
		  }
	  
	  
		public static ArrayList<Block> getBlocksAroundCenter(Location loc, int radius) {
		    ArrayList<Block> blocks = new ArrayList<>();
		    for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; x++) {
		      for (int y = loc.getBlockY() - radius; y <= loc.getBlockY() + radius; y++) {
		        for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; z++) {
		          Location l = new Location(loc.getWorld(), x, y, z);
		          if (l.distance(loc) <= radius)
		            blocks.add(l.getBlock()); 
		        } 
		      } 
		    } 
		    return blocks;
		  }
		
		
		
		  public int getEmptySlots(Player p) {
		        PlayerInventory inventory = p.getInventory();
		        ItemStack[] cont = inventory.getContents();
		        int i = 0;
		        for (ItemStack item : cont)
		          if (item != null && item.getType() != Material.AIR) {
		            i++;
		          }
		        return 36 - i;
		    }
		  public int getBPEmptySlots(Player p) {
			  SettingsManager.getInstance().getbpSize();
		        return SettingsManager.getInstance().getbpSize().getInt(p.getUniqueId().toString())*9;
		  }
		  private WorldGuardPlugin getWorldGuard() {
			    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

			    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			        return null; 
			    }

			    return (WorldGuardPlugin) plugin;
			}
		  
		  
		  
		  public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2, World w)
			{
			List<Block> blocks = new ArrayList<Block>();
			 
			int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
			miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
			minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
			maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
			maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
			maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
			for (int x = minx; x<=maxx;x++) {
			for (int y = miny; y<=maxy;y++) {
			for (int z = minz; z<=maxz;z++) {
			Block b = w.getBlockAt(x, y, z);
			 
			blocks.add(b);
			}
			}
			}
			 
			return blocks;
			}
	  
	  public void Wave(Player p, Block b, int level) {
			
			ArrayList<ItemStack> sellblocks = new ArrayList<>();
			for(Mine m : ResetHandler.api.getMineManager().getMinesByBlock(b)) {
			Location min = new Location(p.getWorld(), m.getMineRegion().getMinPoint().getX(), b.getY(), m.getMineRegion().getMinPoint().getZ());
			Location max = new Location(p.getWorld(), m.getMineRegion().getMaxPoint().getX(), b.getY(), m.getMineRegion().getMaxPoint().getZ());
			int blocks = 1;
			
			
			for (Block b1 : blocksFromTwoPoints(min, max, p.getWorld())) {
		          m.removeBlockFromRegion(b1);
		          if (b1.getType() != Material.BEDROCK && b1.getType() != Material.AIR) {
		              b1.setType(Material.AIR); 
		              blocks = blocks+1;
		    	  } 
 			}
			double fortuity = Functions.Foruity(p);
				int line = 0;
				for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
					if(org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
						line = x;
					}
				}
				int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity /
						(8.6));

				double levelcap = level/10;

				if(levelcap <1){
					levelcap = 1;
				}

		    
		    sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) ((blocks* (fortune)*levelcap))));
		    
		    SellHandler.getInstance().sellEnchant(p, sellblocks, "Wave");
					
				
			}
		}
	  
	  public void Wave(Player p, Block b) {
		  Random r = new Random();
		  
		  double lucky = Functions.Lucky(p);
		  double luck = Functions.luckBoost(p);
	    	
	    	int level = 0;
	    	int chance = 2450;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Wave")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
			      if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (2450 / lucky / luck);
				  } else {
					  chance = (int) ((2450 - (11*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  Wave(p, b, level);
		    	  
		    	  }
		      
	  }
	  
	  
	  @SuppressWarnings("deprecation")
		public void exploBreak(Player p, Block b, int level) {
			ArrayList<ItemStack> sellblocks = new ArrayList<>();
			
			int blocks =1;
			
			Location min = new Location(p.getWorld(), b.getX()-2, b.getY()-2, b.getZ()-2);
			Location max = new Location(p.getWorld(), b.getX()+2, b.getY()+2, b.getZ()+2);
			
			for(Mine m : ResetHandler.api.getMinesByBlock(b)) {
				for (Block b1 : blocksFromTwoPoints(min, max, p.getWorld())) {
			      if (set(b1).allows(DefaultFlag.LIGHTER)) {
			    	  
			           
			    	  if (b1.getType() != Material.BEDROCK && b1.getType() != Material.AIR) {
			              b1.setType(Material.AIR); 
			              blocks = blocks+1;
			    	  }
			            for (Mine mine : ResetHandler.api.getMineManager().getMinesByBlock(b1))
			              mine.removeBlockFromRegion(b1); 
			          
			        }
				}

				
				double fortuity = Functions.Foruity(p);
				int line = 0;
				for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
					if(org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
						line = x;
					}
				}
				int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity /
						(8.6));

				double levelcap = level/10;

				if(levelcap <1){
					levelcap = 1;
				}





			    
			    sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) ((blocks* (fortune))*levelcap)));

				SellHandler.getInstance().sellEnchant(p, sellblocks, "Explosion");
			}
			
			
		}
	  
	  
	  public void Explosion(Player p, Block b) {
		  Random r = new Random();
		  
		  double lucky = Functions.Lucky(p);
		  double luck = Functions.luckBoost(p);
	    	
	    	int level = 0;
	    	int chance = 1750;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Explosion")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
			      if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (1750 / lucky / luck);
				  } else {
					  chance = (int) ((1750 - (11*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  exploBreak(p, b, level);
		    	  
		    	  }
	  }
	  
	 
	
	 
	  
	  public void roundRank(Player p) {
		 RankupHandler.getInstance().upRank(p);
		 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Research-Messages") == true) {
			 p.sendMessage(c("&f&lResearch &8| &b+1 Rankup"));
		 }
			    
		  
	  }
	  
	  public void Research(Player p) {
		  Random r = new Random();
		  
		  double lucky = Functions.Lucky(p);
		  double luck = Functions.luckBoost(p);
	    	
		  int level = 0;
	    	int chance = 4500;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Research")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
				  if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (4500 / lucky / luck);
				  } else {
					  chance = (int) ((4500 - (11*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  roundRank(p);
		    	  
		    	  }
	  }
	  
	  
	  
	  public void vaporizebreak(Player p, Block b, int level) {
			ArrayList<ItemStack> sellblocks = new ArrayList<>();
			
			
			
			int amountblocks = 0;
			
			for(Mine m : ResetHandler.api.getMineManager().getMinesByBlock(b)) {
				int mined = m.getMineRegion().getBlocksMinedInRegion();
				int total = m.getMineRegion().getTotalBlocksInRegion();
				amountblocks = total - mined;
				
				
					
				
	        	  
	        	  ResetHandler.resetMine(m, ResetReason.NUKE);



			
			double fortuity = Functions.Foruity(p);
				int line = 0;
				for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
					if(org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
						line = x;
					}
				}
				int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity /
						(8.6));

				double levelcap = level/10;

				if(levelcap <1){
					levelcap = 1;
				}
		

		    sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) ((amountblocks/95* (fortune*fortuity)*levelcap))));

				SellHandler.getInstance().sellEnchant(p, sellblocks, "Vaporize");
					
				}
	  }
			
			
		
	  
	  public void Vaporize(Player p, Block b) {
		  Random r = new Random();
		  
		  double lucky = Functions.Lucky(p);
		  double luck = Functions.luckBoost(p);
	    	
		  int level = 0;
	    	int chance = 25000;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Vaporize")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
			      if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (25000 / lucky / luck);
				  } else {
					  chance = (int) ((25000 - (55*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  vaporizebreak(p, b, level);
		    	  
		    	  }
	  }
	
	  
	  
	  public void RandomItem(Player p) {
		  
		 Random r = new Random();
		 int fmin = 100;
		 int fmax = 2000;
		 int fortune = r.nextInt(fmax - fmin)+ fmin;
		 
		 
		 double min = 0.1;
		 double max = 0.5;
		 double multi = Math.round((min + (max - min) * r.nextDouble())*10)/10.0;
		 
		 
		 
		 int rr = r.nextInt(100);
		 if(rr >=0 && rr <40) {

			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+"+fortune+" XP"));
			 }
		 } else if(rr >=40 && rr <60) {
			 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "multi add "+ p.getName() +" "+multi);
			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+"+multi+" Multi"));
			 }
		 } else if(rr >=60 && rr <70) {
			 RankupHandler.getInstance().upRank(p);
			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+1 Rankup"));
			 }
		 } else if(rr >=70) {
			 CMDVoteShop.addVotePoint(p);
			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+1 Vote Point"));
			 }
		 }
		  
		  
		  
	  }
	  
	  
	  
	  public void Junkpile(Player p) {
		  Random r = new Random();
		  
		  double lucky = Functions.Lucky(p);
		  double luck = Functions.luckBoost(p);
	    	
		  int level = 0;
	    	int chance = 3500;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Junkpile")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
				  if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (3500 / lucky / luck);
				  } else {
					  chance = (int) ((3500 - (11*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  RandomItem(p);
		    	  
		    	  }
	  }
	  
	  
	  public static HashMap<Player, Double> stakemap = new HashMap<>();
	  
	  
	  public void GainStake(Player p) {
		  if(stakemap.containsKey(p)) return;
		  stakemap.put(p, 1.0);
		  if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Stake-Messages") == true) {
			  p.sendMessage(c("&f&lStake | &bStarted"));
		  }
		  
		  new BukkitRunnable() {
			  @Override
			  public void run() {
				  double d = stakemap.get(p);
				  Main.econ.depositPlayer(p, d);
				  if(settings.getOptions().getBoolean(p.getUniqueId().toString()+".Stake-Messages") == true) {
					  p.sendMessage(c("&f&lStake | &b+$"+Main.formatAmt(d)));
				  }
				  stakemap.remove(p);
			  }
		  }.runTaskLater(Main.plugin, 20*30);
		  
		  
		 
	  }
	  
	  
	  public void Stake(Player p) {
		  Random r = new Random();
		  
		  double lucky = Functions.Lucky(p);
		  double luck = Functions.luckBoost(p);
	    	
		  int level = 0;
	    	int chance = 4500;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Stake")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
				  if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (3000 / lucky / luck);
				  } else {
					  chance = (int) ((3000 - (11*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  GainStake(p);
		    	  
		    	  }
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
	  static String c(String s) {
		    return ChatColor.translateAlternateColorCodes('&', s);
		  }
	  
	  
	  static String Enchant;
	    
	    static boolean happened = false;
	  
	    
	  
	  
	  


	

}
