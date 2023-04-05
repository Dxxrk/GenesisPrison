package me.dxrk.Enchants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dxrk.Events.*;
import me.dxrk.Main.*;
import me.dxrk.Tokens.Tokens;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.dxrk.Vote.CMDVoteShop;
import me.dxrk.Events.ResetHandler.ResetReason;

import me.jet315.prisonmines.mine.Mine;
public class EnchantMethods {
	
	static EnchantMethods instance = new EnchantMethods();
	
	public static EnchantMethods getInstance() {
	    return instance;
	  }
	
	private Methods m = Methods.getInstance();
	private final SettingsManager settings = SettingsManager.getInstance();

	public static List<Player> laser = new ArrayList<>();

	
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

	private WorldGuardPlugin getWorldGuard() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

		if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
			return null;
		}

		return (WorldGuardPlugin) plugin;
	}



	public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2, World w)
	{
		List<Block> blocks = new ArrayList<>();

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

		  public static List<Block> laserBlocks(Block b){
			  List<Block> blocks = new ArrayList<>();

				for(int i = 0; i < 200; i++){
					World w = b.getLocation().getWorld();
					int x = b.getX();
					int y = b.getY();
					int z = b.getZ();
					Block bb = w.getBlockAt(new Location(w, x + i, y, z));
					Block bup = w.getBlockAt(bb.getRelative(BlockFace.UP).getLocation());
					Block bdown = w.getBlockAt(bb.getRelative(BlockFace.DOWN).getLocation());
					Block bleft = w.getBlockAt(bb.getRelative(BlockFace.WEST).getLocation());
					Block bright = w.getBlockAt(bb.getRelative(BlockFace.EAST).getLocation());
					Block bfront = w.getBlockAt(bb.getRelative(BlockFace.NORTH).getLocation());
					Block bback = w.getBlockAt(bb.getRelative(BlockFace.SOUTH).getLocation());
					blocks.add(bup);
					blocks.add(bdown);
					blocks.add(bleft);
					blocks.add(bright);
					blocks.add(bfront);
					blocks.add(bback);
				}
				//repeat this for every direction of coordinates.
			  for(int i = 0; i < 200; i++){
				  World w = b.getLocation().getWorld();
				  int x = b.getX();
				  int y = b.getY();
				  int z = b.getZ();
				  Block bb = w.getBlockAt(new Location(w, x - i, y, z));
				  Block bup = w.getBlockAt(bb.getRelative(BlockFace.UP).getLocation());
				  Block bdown = w.getBlockAt(bb.getRelative(BlockFace.DOWN).getLocation());
				  Block bleft = w.getBlockAt(bb.getRelative(BlockFace.WEST).getLocation());
				  Block bright = w.getBlockAt(bb.getRelative(BlockFace.EAST).getLocation());
				  Block bfront = w.getBlockAt(bb.getRelative(BlockFace.NORTH).getLocation());
				  Block bback = w.getBlockAt(bb.getRelative(BlockFace.SOUTH).getLocation());
				  blocks.add(bup);
				  blocks.add(bdown);
				  blocks.add(bleft);
				  blocks.add(bright);
				  blocks.add(bfront);
				  blocks.add(bback);
			  }
			  for(int i = 0; i < 200; i++){
				  World w = b.getLocation().getWorld();
				  int x = b.getX();
				  int y = b.getY();
				  int z = b.getZ();
				  Block bb = w.getBlockAt(new Location(w, x, y + i, z));
				  Block bup = w.getBlockAt(bb.getRelative(BlockFace.UP).getLocation());
				  Block bdown = w.getBlockAt(bb.getRelative(BlockFace.DOWN).getLocation());
				  Block bleft = w.getBlockAt(bb.getRelative(BlockFace.WEST).getLocation());
				  Block bright = w.getBlockAt(bb.getRelative(BlockFace.EAST).getLocation());
				  Block bfront = w.getBlockAt(bb.getRelative(BlockFace.NORTH).getLocation());
				  Block bback = w.getBlockAt(bb.getRelative(BlockFace.SOUTH).getLocation());
				  blocks.add(bup);
				  blocks.add(bdown);
				  blocks.add(bleft);
				  blocks.add(bright);
				  blocks.add(bfront);
				  blocks.add(bback);
			  }
			  for(int i = 0; i < 200; i++){
				  World w = b.getLocation().getWorld();
				  int x = b.getX();
				  int y = b.getY();
				  int z = b.getZ();
				  Block bb = w.getBlockAt(new Location(w, x, y - i, z));
				  Block bup = w.getBlockAt(bb.getRelative(BlockFace.UP).getLocation());
				  Block bdown = w.getBlockAt(bb.getRelative(BlockFace.DOWN).getLocation());
				  Block bleft = w.getBlockAt(bb.getRelative(BlockFace.WEST).getLocation());
				  Block bright = w.getBlockAt(bb.getRelative(BlockFace.EAST).getLocation());
				  Block bfront = w.getBlockAt(bb.getRelative(BlockFace.NORTH).getLocation());
				  Block bback = w.getBlockAt(bb.getRelative(BlockFace.SOUTH).getLocation());
				  blocks.add(bup);
				  blocks.add(bdown);
				  blocks.add(bleft);
				  blocks.add(bright);
				  blocks.add(bfront);
				  blocks.add(bback);
			  }
			  for(int i = 0; i < 200; i++){
				  World w = b.getLocation().getWorld();
				  int x = b.getX();
				  int y = b.getY();
				  int z = b.getZ();
				  Block bb = w.getBlockAt(new Location(w, x, y, z + i));
				  Block bup = w.getBlockAt(bb.getRelative(BlockFace.UP).getLocation());
				  Block bdown = w.getBlockAt(bb.getRelative(BlockFace.DOWN).getLocation());
				  Block bleft = w.getBlockAt(bb.getRelative(BlockFace.WEST).getLocation());
				  Block bright = w.getBlockAt(bb.getRelative(BlockFace.EAST).getLocation());
				  Block bfront = w.getBlockAt(bb.getRelative(BlockFace.NORTH).getLocation());
				  Block bback = w.getBlockAt(bb.getRelative(BlockFace.SOUTH).getLocation());
				  blocks.add(bup);
				  blocks.add(bdown);
				  blocks.add(bleft);
				  blocks.add(bright);
				  blocks.add(bfront);
				  blocks.add(bback);
			  }
			  for(int i = 0; i < 200; i++){
				  World w = b.getLocation().getWorld();
				  int x = b.getX();
				  int y = b.getY();
				  int z = b.getZ();
				  Block bb = w.getBlockAt(new Location(w, x, y, z - i));
				  Block bup = w.getBlockAt(bb.getRelative(BlockFace.UP).getLocation());
				  Block bdown = w.getBlockAt(bb.getRelative(BlockFace.DOWN).getLocation());
				  Block bleft = w.getBlockAt(bb.getRelative(BlockFace.WEST).getLocation());
				  Block bright = w.getBlockAt(bb.getRelative(BlockFace.EAST).getLocation());
				  Block bfront = w.getBlockAt(bb.getRelative(BlockFace.NORTH).getLocation());
				  Block bback = w.getBlockAt(bb.getRelative(BlockFace.SOUTH).getLocation());
				  blocks.add(bup);
				  blocks.add(bdown);
				  blocks.add(bleft);
				  blocks.add(bright);
				  blocks.add(bfront);
				  blocks.add(bback);
			  }


			  return blocks;
		  }

	public void Laser(Player p, Block b, int level) {

		ArrayList<ItemStack> sellblocks = new ArrayList<>();
		for(Mine m : ResetHandler.api.getMineManager().getMinesByBlock(b)) {
			Location min = new Location(p.getWorld(), m.getMineRegion().getMinPoint().getX(), b.getY(), m.getMineRegion().getMinPoint().getZ());
			Location max = new Location(p.getWorld(), m.getMineRegion().getMaxPoint().getX(), b.getY(), m.getMineRegion().getMaxPoint().getZ());
			int blocks = 1;


			for (Block b1 : laserBlocks(b)) {
				if (set(b1).allows(DefaultFlag.LIGHTER)) {
					m.removeBlockFromRegion(b1);
					if (b1.getType() != Material.BEDROCK && b1.getType() != Material.AIR) {
						ResetHandler.setBlockFast(b1.getLocation(), new ItemStack(Material.AIR));
						blocks = blocks + 1;
					}
				}
			}
			double fortuity = Functions.Foruity(p);
			double skill = SkillsEventsListener.getSkillsBoostFortune(p);
			int line = 0;
			for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
				if(org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
					line = x;
				}
			}
			int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity*skill /
					(14));

			double levelcap = 1+(level/750);

			sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) ((blocks/3* (fortune)*levelcap))));




			int tokens = (int) (KeysHandler.tokensPerBlock(p)*blocks*levelcap);
			Tokens.getInstance().addTokens(p, tokens);
			SellHandler.getInstance().sellEnchant(p, sellblocks, "Laser", tokens);


		}
	}
	public void Laser(Player p, Block b) {
		Random r = new Random();

		double lucky = Functions.Karma(p);
		double luck = Functions.luckBoost(p);
		double skill = SkillsEventsListener.getSkillsBoostLuck(p);

		int level = 0;
		int chance;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Laser")) {
				level = m.getBlocks(s);
			}
		}

		if(level == 0) return;
		if(level == 1) {
			chance = (int) (7500 * lucky * luck*skill);
		} else {
			chance = (int) ((7500 - (1.2*level*lucky * luck * skill)));
			if(chance < 600){
				chance = 600;
			}
		}
		int i = r.nextInt(chance);
		if(laser.contains(p)){
			Laser(p, b, level);
			i = 2;
		}

		if(i == 1) {
			p.sendMessage(c("&f&lLaser &8| &bActivated!"));
			laser.add(p);
			Laser(p, b, level);
			new BukkitRunnable(){
				@Override
				public void run(){
					laser.remove(p);
					p.sendMessage(c("&f&lLaser &8| &bDeactivated!"));
				}
			}.runTaskLater(Main.plugin, (long) (20*3.5));

		}

	}
		



	  
	  public void Wave(Player p, Block b, int level) {
			
			ArrayList<ItemStack> sellblocks = new ArrayList<>();
			for(Mine m : ResetHandler.api.getMineManager().getMinesByBlock(b)) {
			Location min = new Location(p.getWorld(), m.getMineRegion().getMinPoint().getX(), b.getY(), m.getMineRegion().getMinPoint().getZ());
			Location max = new Location(p.getWorld(), m.getMineRegion().getMaxPoint().getX(), b.getY(), m.getMineRegion().getMaxPoint().getZ());
			int blocks = 1;
			
			
			for (Block b1 : blocksFromTwoPoints(min, max, p.getWorld())) {
				if (set(b1).allows(DefaultFlag.LIGHTER)) {
					m.removeBlockFromRegion(b1);
					if (b1.getType() != Material.BEDROCK && b1.getType() != Material.AIR) {
						ResetHandler.setBlockFast(b1.getLocation(), new ItemStack(Material.AIR));
						blocks = blocks + 1;
					}
				}
			}
			double fortuity = Functions.Foruity(p);
				double skill = SkillsEventsListener.getSkillsBoostFortune(p);
				double event = SkillsEventsListener.getEventFortune();
				int line = 0;
				for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
					if(org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
						line = x;
					}
				}
				int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity*skill*event /
						(14));

				double levelcap = 1+(level/1000);


		    
		    sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) ((blocks/1.75* (fortune)*levelcap))));



				int tokens = (int) (KeysHandler.tokensPerBlock(p)*blocks*levelcap);
				Tokens.getInstance().addTokens(p, tokens);
				SellHandler.getInstance().sellEnchant(p, sellblocks, "Wave", tokens);
					
				
			}
		}
	  
	  public void Wave(Player p, Block b) {
		  Random r = new Random();
		  
		  double lucky = Functions.Karma(p);
		  double luck = Functions.luckBoost(p);
		  double skill = SkillsEventsListener.getSkillsBoostLuck(p);
	    	
	    	int level = 0;
	    	int chance;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Wave")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
			      if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (2000 * lucky * luck*skill);
				  } else {
					  chance = (int) ((2000 - (1.15*level*lucky * luck * skill)));
					  if(chance < 300){
						  chance = 300;
					  }
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
						  ResetHandler.setBlockFast(b1.getLocation(), new ItemStack(Material.AIR));
			              blocks = blocks+1;
			    	  }
			            for (Mine mine : ResetHandler.api.getMineManager().getMinesByBlock(b1))
			              mine.removeBlockFromRegion(b1); 
			          
			        }
				}

				
				double fortuity = Functions.Foruity(p);
				double skill = SkillsEventsListener.getSkillsBoostFortune(p);
				double event = SkillsEventsListener.getEventFortune();
				int line = 0;
				for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
					if(org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
						line = x;
					}
				}
				int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity*skill*event /
						(14));

				double levelcap = 1+(level/500);






			    
			    sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) ((blocks* (fortune))*levelcap)));



				int tokens = (int) (KeysHandler.tokensPerBlock(p)*blocks*levelcap*2);
				Tokens.getInstance().addTokens(p, tokens);
				SellHandler.getInstance().sellEnchant(p, sellblocks, "Explosion", tokens);

			}
			
			
		}
	  
	  
	  public void Explosion(Player p, Block b) {
		  Random r = new Random();
		  
		  double lucky = Functions.Karma(p);
		  double luck = Functions.luckBoost(p);
		  double skill = SkillsEventsListener.getSkillsBoostLuck(p);
	    	
	    	int level = 0;
	    	int chance = 1750;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Explosion")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
			      if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (1350 * lucky * luck*skill);
				  } else {
					  chance = (int) ((1350 - (0.75*level*lucky * luck * skill)));
					  if(chance < 150){
						  chance = 150;
					  }
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  exploBreak(p, b, level);
		    	  
		    	  }
	  }
	  
	 
	
	 
	  
	  public void roundRank(Player p) {
		Random r = new Random();
		  int tmin = 7;
		  int tmax = 12;
		  int ranks = r.nextInt(tmax - tmin)+ tmin;
		  for(int i = 0; i < ranks; i++) {
			  RankupHandler.getInstance().upRank(p);
			  if((RankupHandler.getInstance().getRank(p) %16 == 0) && RankupHandler.getInstance().getRank(p) <1000)
				  MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
		  }
		 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Research-Messages") == true) {
			 p.sendMessage(c("&f&lResearch &8| &b+"+ranks+ " Levels"));
		 }
			    
		  
	  }
	  
	  public void Research(Player p) {
		  Random r = new Random();
		  
		  double lucky = Functions.Karma(p);
		  double luck = Functions.luckBoost(p);
		  double skill = SkillsEventsListener.getSkillsBoostLuck(p);
	    	
		  int level = 0;
	    	int chance = 4500;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				      if (ChatColor.stripColor(s).contains("Research")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
				  if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (4300 * lucky * luck*skill);
				  } else {
					  chance = (int) ((4300 - (0.52*level*lucky * luck * skill)));
					  if(chance < 400){
						  chance = 400;
					  }
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  roundRank(p);
		    	  
		    	  }
	  }
	  
	  
	  
	  public void nukebreak(Player p, Block b, int level) {
			ArrayList<ItemStack> sellblocks = new ArrayList<>();
			
			
			
			int amountblocks = 0;
			
			for(Mine m : ResetHandler.api.getMineManager().getMinesByBlock(b)) {
				int mined = m.getMineRegion().getBlocksMinedInRegion();
				int total = m.getMineRegion().getTotalBlocksInRegion();
				amountblocks = total - mined;



				int rank = RankupHandler.getInstance().getRank(p);
	        	  ResetHandler.resetMine(p, m, ResetReason.NUKE, MineHandler.Blocks(rank/16));



			
			double fortuity = Functions.Foruity(p);
				double skill = SkillsEventsListener.getSkillsBoostFortune(p);
				double event = SkillsEventsListener.getEventFortune();
				int line = 0;
				for(int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++){
					if(org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")){
						line = x;
					}
				}
				int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line))*fortuity*skill*event /
						(14));

				double levelcap = 1+(level/500);

		

		    sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) (((amountblocks/10)*(fortune*fortuity)*levelcap))));



				int tokens = (int) (KeysHandler.tokensPerBlock(p)*(amountblocks/6)*levelcap);
				Tokens.getInstance().addTokens(p, tokens);
				SellHandler.getInstance().sellEnchant(p, sellblocks, "Nuke", tokens);
				}
	  }
			
			
		
	  
	  public void Vaporize(Player p, Block b) {
		  Random r = new Random();
		  
		  double lucky = Functions.Karma(p);
		  double luck = Functions.luckBoost(p);
		  double skill = SkillsEventsListener.getSkillsBoostLuck(p);
	    	
		  int level = 0;
	    	int chance = 25000;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Nuke")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
			      if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (23000 * lucky * luck*skill);
				  } else {
					  chance = (int) ((23000 - (20*level*lucky * luck * skill)));
					  if(chance < 2000){
						  chance = 2000;
					  }
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  nukebreak(p, b, level);
		    	  
		    	  }
	  }
	
	  
	  
	  public void RandomItem(Player p) {
		 Random r = new Random();

		 int fmin = 1000;
		 int fmax = 2500;
		 int xp = r.nextInt(fmax - fmin)+ fmin;
		 
		 
		 double min = 0.1;
		 double max = 0.5;
		 double multi = Math.round((min + (max - min) * r.nextDouble())*10)/10.0;
		 
		 
		 
		 int rr = r.nextInt(100);
		 if(rr >=0 && rr <40) {

			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+"+xp+" XP"));
			 }

			 PickXPHandler.getInstance().addXP(p, xp);
		 } else if(rr >=40 && rr <60) {
			 Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "multi add "+ p.getName() +" "+multi);
			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+"+multi+" Multi"));
			 }
		 } else if(rr >=60 && rr <70) {
			 RankupHandler.getInstance().upRank(p);
			 RankupHandler.getInstance().upRank(p);
			 RankupHandler.getInstance().upRank(p);
			 RankupHandler.getInstance().upRank(p);
			 RankupHandler.getInstance().upRank(p);
			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+5 Levels"));
			 }
		 } else if(rr >=70 && rr <75) {
			 CMDVoteShop.addCoupon(p, 0.05);
			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+$0.05 Coupon"));
			 }
		 }	else if(rr >=75) {
				int rint = r.nextInt(4);
				if(rint == 0){
					p.getInventory().addItem(TrinketHandler.getInstance().commonDust());
				}
				else if(rint == 1){
					p.getInventory().addItem(TrinketHandler.getInstance().rareDust());
				}
				else if(rint == 2){
					p.getInventory().addItem(TrinketHandler.getInstance().epicDust());
				}
				else if(rint == 3){
					p.getInventory().addItem(TrinketHandler.getInstance().legDust());
				}
				else if(rint == 4){
					p.getInventory().addItem(TrinketHandler.getInstance().herDust());
				}
		 }
		  
		  
		  
	  }
	  
	  
	  
	  public void Junkpile(Player p) {
		  Random r = new Random();
		  
		  double lucky = Functions.Karma(p);
		  double luck = Functions.luckBoost(p);
		  double skill = SkillsEventsListener.getSkillsBoostLuck(p);
	    	
		  int level = 0;
	    	int chance = 3500;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Junkpile")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
				  if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (4400 * lucky * luck*skill);
				  } else {
					  chance = (int) ((4400 - (0.85*level*lucky * luck * skill)));
					  if(chance < 500){
						  chance = 500;
					  }
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  RandomItem(p);
		    	  
		    	  }
	  }

	public void KeyParty(Player p) {
		Random r = new Random();
		int i = r.nextInt(8);

		if(i == 0 || i ==1) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(ScoreboardHandler.isAFK(pp)) continue;
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".Key-Party-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d"+p.getName()+" &b+1 &7&lAlpha &7Key!"));
				}
				KeysHandler.getInstance().addKey(pp, "Alpha", 1);
			}
		} else if(i == 2 || i == 3) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(ScoreboardHandler.isAFK(pp)) continue;
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".Key-Party-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d"+p.getName()+" &b+1 &c&lBeta &7Key!"));
				}
				KeysHandler.getInstance().addKey(pp, "Beta", 1);
			}
		}else if(i == 4 || i==5) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(ScoreboardHandler.isAFK(pp)) continue;
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".Key-Party-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d"+p.getName()+" &b+1 &e&lToken &7Key!"));
				}
				KeysHandler.getInstance().addKey(pp, "Token", 1);
			}
		}else if(i == 6 || i==7) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(ScoreboardHandler.isAFK(pp)) continue;
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".Key-Party-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d"+p.getName()+" &b+1 &4&lOmega &7Key!"));
				}
				KeysHandler.getInstance().addKey(pp, "Omega", 1);
			}
		}else if(i ==8) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(ScoreboardHandler.isAFK(pp)) continue;
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".Key-Party-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d"+p.getName()+" &b+1 &c&l&ki&d&lSeasonal&c&l&ki&r &7Key!"));
				}
				KeysHandler.getInstance().addKey(pp, "Seasonal", 1);
			}
		}


	}


	public void KeyPartyBreak(Player p) {
		Random r = new Random();

			double lucky = Functions.Karma(p);
			double luck = Functions.luckBoost(p);
			double skill = SkillsEventsListener.getSkillsBoostLuck(p);

			int level = 0;
			int chance = 3000;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {

				if (ChatColor.stripColor(s).contains("Key Party")) {
					level = m.getBlocks(s);
				}
			}

			if(level == 0) return;
			if(level == 1) {
				chance = (int) (3800 * lucky * luck*skill);
			} else {
				chance = (int) ((3800 - (level*lucky * luck * skill)));
				if(chance < 400){
					chance = 400;
				}
			}
			int i = r.nextInt(chance);

			if(i == 1) {
				KeyParty(p);

			}

	}


	public void Booster(Player p) {
		Random r = new Random();
		int i = r.nextInt(5);

		if(i == 0) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 2.5 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2.5x Currency Boost!"));
			}
		} else if(i == 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 2.5 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 2.5x Currency Boost!"));
			}
		}else if(i == 2) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp "+p.getName()+" 2 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2x XP Boost!"));
			}
		}else if(i == 3) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 3.0 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 3.0x Currency Boost!"));
			}
		}else if(i == 4) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 3.0 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 3.0x Currency Boost!"));
			}
		}else if(i == 5) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp "+p.getName()+" 2 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 2x XP Boost!"));
			}
		}

	}

	public void BoosterBreak(Player p) {
		Random r = new Random();

		double lucky = Functions.Karma(p);
		double luck = Functions.luckBoost(p);
		double skill = SkillsEventsListener.getSkillsBoostLuck(p);

		int level = 0;
		int chance = 4500;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Booster")) {
				level = m.getBlocks(s);
			}
		}

		if(level == 0) return;
		if(level == 1) {
			chance = (int) (6300 * lucky * luck*skill);
		} else {
			chance = (int) ((6300 - (0.65*level*lucky * luck * skill)));
			if(chance < 550){
				chance = 550;
			}
		}
		int i = r.nextInt(chance);

		if(i == 1) {
			Booster(p);

		}


	}

	private void prestigeFinder(Player p, int level){
		int prestige = 0;
		Random r = new Random();
		int rint = r.nextInt(4);
		if(rint == 0){
			prestige = (int) (1*(level/1000));
		}
		else if(rint == 1){
			prestige = (int) (2*(level/1000));
		}
		else if(rint == 2){
			prestige = (int) (3*(level/1000));
		}
		else if(rint == 3){
			prestige = (int) (4*(level/1000));
		}
		if(prestige < 1){
			prestige = 1;
		}


		PrestigeHandler.addPrestiges(p, prestige);
		p.sendMessage(m.c("&f&lPrestige Finder &8| &b+&e"+prestige+" &bPrestiges."));
	}


	public void prestigeBreak(Player p){
		Random r = new Random();

		double lucky = Functions.Karma(p);
		double luck = Functions.luckBoost(p);
		double skill = SkillsEventsListener.getSkillsBoostLuck(p);

		int level = 0;
		int chance = 4500;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Prestige Finder")) {
				level = m.getBlocks(s);
			}
		}

		if(level == 0) return;
		if(level == 1) {
			chance = (int) (4500 * lucky * luck*skill);
		} else {
			chance = (int) ((4500 - (0.3*level*lucky * luck * skill)));
			if(chance < 600){
				chance = 600;
			}
		}
		int i = r.nextInt(chance);

		if(i == 1) {
			prestigeFinder(p, level);

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

	  
	    
	  
	  
	  


	

}
