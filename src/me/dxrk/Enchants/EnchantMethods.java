package me.dxrk.Enchants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dxrk.Events.*;
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
import me.dxrk.Main.Functions;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
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
						b1.setType(Material.AIR);
						blocks = blocks + 1;
					}
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
					(3.5));

			double levelcap = level/10;

			if(levelcap <1){
				levelcap = 1;
			}


			sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(),(int) ((blocks* (fortune)*levelcap))));

			SellHandler.getInstance().sellEnchant(p, sellblocks, "Laser");

			List<String> lore = p.getItemInHand().getItemMeta().getLore();
			double tf = 1;
			int x;
			for (x = 0; x < lore.size(); x++) {
				String s = lore.get(x);
				if (org.bukkit.ChatColor.stripColor(s).contains("Token Finder")) {
					tf = getBlocks(ChatColor.stripColor(s))*0.0048;
				}
			}
			double multiply = 1;
			if(Functions.multiply.contains(p)) multiply = 2;


			Tokens.getInstance().addTokens(p, 15*blocks*tf*multiply);


		}
	}
	public void Laser(Player p, Block b) {
		Random r = new Random();

		double lucky = Functions.Lucky(p);
		double luck = Functions.luckBoost(p);

		int level = 0;
		int chance;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Laser")) {
				level = m.getBlocks(s);
			}
		}

		if(level == 0) return;
		if(level == 1) {
			chance = (int) (2450 / lucky / luck);
		} else {
			chance = (int) ((2450 - (1.6*level))/lucky / luck);
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
			}.runTaskLater(Main.plugin, 20*3L);

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
						b1.setType(Material.AIR);
						blocks = blocks + 1;
					}
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

				List<String> lore = p.getItemInHand().getItemMeta().getLore();
				double tf = 1;
				int x;
				for (x = 0; x < lore.size(); x++) {
					String s = lore.get(x);
					if (org.bukkit.ChatColor.stripColor(s).contains("Token Finder")) {
						tf = getBlocks(ChatColor.stripColor(s))*0.0048;
					}
				}

				double multiply = 1;
				if(Functions.multiply.contains(p)) multiply = 2;


				Tokens.getInstance().addTokens(p, 15*blocks*tf*multiply);
					
				
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
					  chance = (int) ((2450 - (1.75*level))/lucky / luck);
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

				List<String> lore = p.getItemInHand().getItemMeta().getLore();
				double tf = 1;
				int x;
				for (x = 0; x < lore.size(); x++) {
					String s = lore.get(x);
					if (org.bukkit.ChatColor.stripColor(s).contains("Token Finder")) {
						tf = getBlocks(ChatColor.stripColor(s))*0.0048;
					}
				}

				double multiply = 1;
				if(Functions.multiply.contains(p)) multiply = 2;


				Tokens.getInstance().addTokens(p, 15*blocks*tf*multiply);


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
					  chance = (int) ((1750 - (1.25*level))/lucky / luck);
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
					  chance = (int) ((4500 - (1.167*level))/lucky / luck);
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

				SellHandler.getInstance().sellEnchant(p, sellblocks, "Nuke");

				List<String> lore = p.getItemInHand().getItemMeta().getLore();
				double tf = 1;
				int x;
				for (x = 0; x < lore.size(); x++) {
					String s = lore.get(x);
					if (org.bukkit.ChatColor.stripColor(s).contains("Token Finder")) {
						tf = getBlocks(ChatColor.stripColor(s))*0.0048;
					}
				}

				double multiply = 1;
				if(Functions.multiply.contains(p)) multiply = 2;


				Tokens.getInstance().addTokens(p, 15*amountblocks*tf*multiply);
					
				}
	  }
			
			
		
	  
	  public void Vaporize(Player p, Block b) {
		  Random r = new Random();
		  
		  double lucky = Functions.Lucky(p);
		  double luck = Functions.luckBoost(p);
	    	
		  int level = 0;
	    	int chance = 25000;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {
				
				      if (ChatColor.stripColor(s).contains("Nuke")) {
				    	  level = m.getBlocks(s);
				      }
			}
				  
			      if(level == 0) return;
				  if(level == 1) {
					  chance = (int) (25000 / lucky / luck);
				  } else {
					  chance = (int) ((25000 - (39*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  nukebreak(p, b, level);
		    	  
		    	  }
	  }
	
	  
	  
	  public void RandomItem(Player p) {
		 Random r = new Random();

		 int fmin = 100;
		 int fmax = 2000;
		 int xp = r.nextInt(fmax - fmin)+ fmin;
		 
		 
		 double min = 0.1;
		 double max = 0.5;
		 double multi = Math.round((min + (max - min) * r.nextDouble())*10)/10.0;
		 
		 
		 
		 int rr = r.nextInt(100);
		 if(rr >=0 && rr <40) {

			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+"+xp+" XP"));
			 }
			 double x = PickXPHandler.getInstance().getXP(p);

			 settings.getPlayerData().set(p.getUniqueId().toString()+".PickXP", (xp+x));
			 settings.savePlayerData();
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
		 } else if(rr >=70 && rr <90) {
			 CMDVoteShop.addVotePoint(p, 1);
			 if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Junkpile-Messages") == true) {
			 p.sendMessage(c("&f&lJunkpile &8| &b+1 Vote Point"));
			 }
		 }	else if(rr >=90) {
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
					  chance = (int) ((3500 - (1*level))/lucky / luck);
				  }
		    	  int i = r.nextInt(chance);
		    	   
		    	  if(i == 1) {
		    		  RandomItem(p);
		    	  
		    	  }
	  }

	public void RuneParty(Player p) {
		Random r = new Random();
		int i = r.nextInt(8);

		if(i == 0 || i ==1) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &e&lMidas &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Midas", 1);
			}
		} else if(i == 2 || i == 3) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &9&lPoseidon &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Poseidon", 1);
			}
		}else if(i == 4 || i==5) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &4&lHades &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Hades", 1);
			}
		}else if(i == 6 || i==7) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &f&lPolis &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Polis", 1);
			}
		}else if(i ==8) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(this.settings.getOptions().getBoolean(pp.getUniqueId().toString()+".RuneParty-Messages") == true) {
					pp.sendMessage(Methods.getInstance().c("&f&lRuneParty &8| &bFrom &d"+p.getName()+" &b+1 &c&lOblivion &bRune!"));
				}
				KeysHandler.getInstance().addKey(pp, "Oblivion", 1);
			}
		}


	}


	public void KeyPartyBreak(Player p) {
		Random r = new Random();

			double lucky = Functions.Lucky(p);
			double luck = Functions.luckBoost(p);

			int level = 0;
			int chance = 3000;
			for (String s : p.getItemInHand().getItemMeta().getLore()) {

				if (ChatColor.stripColor(s).contains("Key Party")) {
					level = m.getBlocks(s);
				}
			}

			if(level == 0) return;
			if(level == 1) {
				chance = (int) (3000 / lucky / luck);
			} else {
				chance = (int) ((3000 - (2*level))/lucky / luck);
			}
			int i = r.nextInt(chance);

			if(i == 1) {
				RuneParty(p);

			}

	}


	public void Booster(Player p) {
		Random r = new Random();
		int i = r.nextInt(5);

		if(i == 0) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 2.5 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2.5x Sell Boost!"));
			}
		} else if(i == 1) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 2.5 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 2.5x Sell Boost!"));
			}
		}else if(i == 2) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp "+p.getName()+" 2 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2x XP Boost!"));
			}
		}else if(i == 3) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 3.0 300");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 3.0x Sell Boost!"));
			}
		}else if(i == 4) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell "+p.getName()+" 3.0 600");
			if(this.settings.getOptions().getBoolean(p.getUniqueId().toString()+".Booster-Messages") == true) {
				p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 3.0x Sell Boost!"));
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

		double lucky = Functions.Lucky(p);
		double luck = Functions.luckBoost(p);

		int level = 0;
		int chance = 4500;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Booster")) {
				level = m.getBlocks(s);
			}
		}

		if(level == 0) return;
		if(level == 1) {
			chance = (int) (4500 / lucky / luck);
		} else {
			chance = (int) ((4500 - (1.1*level))/lucky / luck);
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
			prestige = (int) (1*(level/16.67));
		}
		else if(rint == 1){
			prestige = (int) (2*(level/16.67));
		}
		else if(rint == 2){
			prestige = (int) (3*(level/16.67));
		}
		else if(rint == 3){
			prestige = (int) (4*(level/16.67));
		}


		PickXPHandler.getInstance().addXP(p, prestige);
		p.sendMessage(m.c("&f&lPrestige Finder &8| &b+&e"+prestige+" &bPrestiges."));
	}


	public void prestigeBreak(Player p){
		Random r = new Random();

		double lucky = Functions.Lucky(p);
		double luck = Functions.luckBoost(p);

		int level = 0;
		int chance = 4500;
		for (String s : p.getItemInHand().getItemMeta().getLore()) {

			if (ChatColor.stripColor(s).contains("Prestige Finder")) {
				level = m.getBlocks(s);
			}
		}

		if(level == 0) return;
		if(level == 1) {
			chance = (int) (4500 / lucky / luck);
		} else {
			chance = (int) ((4500 - (0.75*level))/lucky / luck);
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
