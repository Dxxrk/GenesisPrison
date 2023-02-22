package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.MultiBlockChanger;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.jet315.prisonmines.JetsPrisonMines;
import me.jet315.prisonmines.JetsPrisonMinesAPI;
import me.jet315.prisonmines.mine.Mine;
import me.jet315.prisonmines.mine.location.MineRegion;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static me.dxrk.Events.PrestigeHandler.settings;

public class ResetHandler {

	private static JetsPrisonMines jpm = (JetsPrisonMines) Bukkit.getPluginManager().getPlugin("JetsPrisonMines");
	public static JetsPrisonMinesAPI api = jpm.getAPI();

	public static void resetMine(Mine mine, ResetReason reason, List<ItemStack> blocks) {
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


		Random r = new Random();
		for(Location bloc : mine.getMineRegion().getLocationOfBlocksInMine()) {
			if(bloc.getWorld().getBlockAt(bloc).getType() == Material.AIR) {
				int block = r.nextInt(3);
				mm.addBlockChanges(bloc, blocks.get(block).getType(), blocks.get(block).getData().getData());
			}

		}

		
		
		
		mm.start(Main.getInstance());
		
		mine.getResetManager().setMineResetTime(999999999);
		mine.getResetManager().setTimeTillReset(999999999);
		mine.getMineRegion().setBlocksMinedInRegion(0);
	}
	
	@SuppressWarnings("deprecation")
	public static void resetMineFull(Mine mine, ResetReason reason, List<ItemStack> blocks) {
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

		Random r = new Random();
		for(Location bloc : mine.getMineRegion().getLocationOfBlocksInMine()) {
			int block = r.nextInt(3);
			mm.addBlockChanges(bloc, blocks.get(block).getType(), blocks.get(block).getData().getData());
		}

		
		
		mm.start(Main.getInstance());
		
		mine.getResetManager().setMineResetTime(999999999);
		mine.getResetManager().setTimeTillReset(999999999);
		mine.getMineRegion().setBlocksMinedInRegion(0);
	}


	public enum ResetReason {
		NORMAL,
		INTERVAL,
		NUKE,
		PERCENTAGE,
		ALL
	}

}