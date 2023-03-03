package me.dxrk.Events;

import me.dxrk.Main.BlockChanger;
import me.jet315.prisonmines.JetsPrisonMines;
import me.jet315.prisonmines.JetsPrisonMinesAPI;
import me.jet315.prisonmines.mine.Mine;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ResetHandler {

	private static JetsPrisonMines jpm = (JetsPrisonMines) Bukkit.getPluginManager().getPlugin("JetsPrisonMines");
	public static JetsPrisonMinesAPI api = jpm.getAPI();

	public static void resetMine(Mine mine, ResetReason reason, List<ItemStack> blocks) {
		World world = mine.getMineRegion().getWorld();
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
				BlockChanger.setSectionBlockAsynchronously(bloc, blocks.get(block), false);
			}
		}


		
		mine.getResetManager().setMineResetTime(999999999);
		mine.getResetManager().setTimeTillReset(999999999);
		mine.getMineRegion().setBlocksMinedInRegion(0);
	}
	
	@SuppressWarnings("deprecation")
	public static void resetMineFull(Mine mine, ResetReason reason, List<ItemStack> blocks) {
		World world = mine.getSpawnLocation().getWorld();
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
			BlockChanger.setSectionBlockAsynchronously(bloc, blocks.get(block), false);
		}

		

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