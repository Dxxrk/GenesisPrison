package me.dxrk.Events;

import me.dxrk.Main.BlockChanger;
import me.jet315.prisonmines.JetsPrisonMines;
import me.jet315.prisonmines.JetsPrisonMinesAPI;
import me.jet315.prisonmines.mine.Mine;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.IBlockData;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Random;

public class ResetHandler {

	private static JetsPrisonMines jpm = (JetsPrisonMines) Bukkit.getPluginManager().getPlugin("JetsPrisonMines");
	public static JetsPrisonMinesAPI api = jpm.getAPI();


	public static ItemStack mineBlock(Player p) {
		int rank = RankupHandler.getInstance().getRank(p);
		Random r = new Random();
		int block = r.nextInt(3);
		return MineHandler.Blocks(rank / 16).get(block);
	}

	public static void setBlockFast(Location loc, ItemStack i) {
		World world = loc.getWorld();
		int x = loc.getBlockX();
		int y = loc.getBlockY();
		int z = loc.getBlockZ();
		int blockId = i.getTypeId();
		byte data = i.getData().getData();
		net.minecraft.server.v1_8_R3.World w = ((CraftWorld) world).getHandle();
		net.minecraft.server.v1_8_R3.Chunk chunk = w.getChunkAt(x >> 4, z >> 4);
		BlockPosition bp = new BlockPosition(x, y, z);
		int combined = blockId + (data << 12);
		IBlockData ibd = net.minecraft.server.v1_8_R3.Block.getByCombinedId(combined);
		chunk.a(bp, ibd);
		Chunk bukkitchunk = loc.getWorld().getChunkAt(loc.getBlock());
		world.refreshChunk(bukkitchunk.getX(), bukkitchunk.getZ());
	}


	public static void resetMine(Player p, Mine mine, ResetReason reason, List<ItemStack> blocks) {
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
				setBlockFast(bloc, blocks.get(block));
			}
		}


		
		mine.getResetManager().setMineResetTime(999999999);
		mine.getResetManager().setTimeTillReset(999999999);
		mine.getMineRegion().setBlocksMinedInRegion(0);
	}
	
	@SuppressWarnings("deprecation")
	public static void resetMineFull(Player p, Mine mine, ResetReason reason, List<ItemStack> blocks) {
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
			setBlockFast(bloc, blocks.get(block));
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