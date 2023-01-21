package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.MultiBlockChanger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import me.jet315.prisonmines.JetsPrisonMines;
import me.jet315.prisonmines.JetsPrisonMinesAPI;
import me.jet315.prisonmines.mine.Mine;
import me.jet315.prisonmines.mine.location.MineRegion;

public class ResetHandler {

	private static JetsPrisonMines jpm = (JetsPrisonMines) Bukkit.getPluginManager().getPlugin("JetsPrisonMines");
	public static JetsPrisonMinesAPI api = jpm.getAPI();

	public static void resetMine(Mine mine, ResetReason reason) {
		World world = mine.getMineRegion().getWorld();
		MultiBlockChanger mm = new MultiBlockChanger(world);
		mm.setMaxChanges(25000);
		if(mine.getSpawnLocation() != null) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(mine.isLocationInRegion(pp.getLocation())) {
					pp.teleport(mine.getSpawnLocation());
					if(reason == ResetReason.NUKE) {
						pp.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMine is being Nuked you have been escorted to a safe place."));
					}
				}
			}
		}

		
		
		for(Location bloc : mine.getMineRegion().getLocationOfBlocksInMine()) {
			if(bloc.getWorld().getBlockAt(bloc).getType() == Material.AIR) {
				mm.addBlockChanges(bloc, mine.getBlockManager().getRandomBlockFromMine().getType(), (byte)0);
			}
		}

		
		
		
		mm.start(Main.getInstance());
		
		mine.getResetManager().setMineResetTime(999999999);
		mine.getResetManager().setTimeTillReset(999999999);
		mine.getMineRegion().setBlocksMinedInRegion(0);
	}
	
	@SuppressWarnings("deprecation")
	public static void resetMineFull(Mine mine, ResetReason reason, int id) {
		World world = mine.getSpawnLocation().getWorld();
		MultiBlockChanger mm = new MultiBlockChanger(world);
		mm.setMaxChanges(25000);
		if(mine.getSpawnLocation() != null) {
			for(Player pp : Bukkit.getOnlinePlayers()) {
				if(mine.isLocationInRegion(pp.getLocation())) {
					pp.teleport(mine.getSpawnLocation());
					if(reason == ResetReason.NUKE) {
						pp.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMine is being Vaporized you have been escorted to a safe place.")); 
					}
				}
			}
		}
		
		
		for(Location bloc : mine.getMineRegion().getLocationOfBlocksInMine()) {
			
				mm.addBlockChanges(bloc, Material.getMaterial(id), (byte)0);
				
			
		}

		
		
		mm.start(Main.getInstance());
		
		mine.getResetManager().setMineResetTime(999999999);
		mine.getResetManager().setTimeTillReset(999999999);
		mine.getMineRegion().setBlocksMinedInRegion(0);
	}

	public static void resetAllMines() {
		for(Mine m : api.getMines()) {
			resetMine(m, ResetReason.ALL);
		}
	}

	public static void autoResetMines() {
		new BukkitRunnable() {
			int i = 1;
			public void run() {
				Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.ITALIC + "Resetting mines...");
				for(Mine mine : api.getMines()) {
					mine.getResetManager().setMineResetTime(999999999);
					MineRegion mr = mine.getMineRegion();
					if(mr.getBlocksMinedInRegion() > (mr.getTotalBlocksInRegion()/10)) {
						resetMineLater(mine, i, ResetReason.INTERVAL);
						i++;
					}
				}
			}
		}.runTaskTimerAsynchronously(Main.plugin, 600*20, 600*20);
	}

	public static void resetMineLater(Mine mine, int seconds, ResetReason reason) {
		new BukkitRunnable() {
			public void run() {
				resetMine(mine, ResetReason.INTERVAL);
			}
		}.runTaskLaterAsynchronously(Main.plugin, seconds*2);
	}

	public static void checkMineForReset(Mine mine, ResetReason reason) {
		if(mine.getMineRegion().getBlocksMinedPercentage() > 60) {
			resetMine(mine, reason);
		}
	}
	
	public enum ResetReason {
		NORMAL,
		INTERVAL,
		NUKE,
		PERCENTAGE,
		ALL
	}

}