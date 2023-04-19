package me.dxrk.Mines;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.blocks.BlockID;
import com.sk89q.worldedit.function.mask.BlockMask;
import com.sk89q.worldedit.function.mask.Mask;
import com.sk89q.worldedit.function.pattern.RandomPattern;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.regions.Region;
import me.dxrk.Events.RankupHandler;
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
	/*@SuppressWarnings("deprecation")
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
	}*/
	public static WorldEditVector toWEVector(Location bukkitVector) {
		return new WorldEditVector(bukkitVector.getX(), bukkitVector.getY(), bukkitVector.getZ());
	}
	public static Region transform(WorldEditRegion region) {
		return new CuboidRegion(
				FaweAPI.getWorld(region.getWorld().getName()),
				transform(region.getMinimumPoint()),
				transform(region.getMaximumPoint()));
	}

	public static Vector transform(WorldEditVector vector) {
		return new Vector(vector.getX(), vector.getY(), vector.getZ());
	}

	public static WorldEditVector transform(Vector vector) {
		return new WorldEditVector(vector.getX(), vector.getY(), vector.getZ());
	}
	@SuppressWarnings("deprecation")
	public static void setAIR(Location loc1, Location loc2, List<ItemStack> blocks) {
		WorldEditRegion region = new WorldEditRegion(toWEVector(loc1), toWEVector(loc2), loc1.getWorld());
		EditSession session = (new EditSessionBuilder(FaweAPI.getWorld(region.getWorld().getName()))).fastmode(Boolean.TRUE).build();
		BaseBlock block = new BaseBlock(blocks.get(0).getTypeId(), blocks.get(0).getData().getData());
		BaseBlock block2 = new BaseBlock(blocks.get(1).getTypeId(), blocks.get(1).getData().getData());
		BaseBlock block3 = new BaseBlock(blocks.get(2).getTypeId(), blocks.get(2).getData().getData());
		Mask mask = new BlockMask(session.getExtent(), block, block2, block3);
		session.replaceBlocks(transform(region),mask, new BaseBlock(BlockID.AIR));
		session.flushQueue();
	}
	@SuppressWarnings("deprecation")
	public static void fillAIR(WorldEditRegion region, ItemStack i, ItemStack i2, ItemStack i3) {
		EditSession session = (new EditSessionBuilder(FaweAPI.getWorld(region.getWorld().getName()))).fastmode(Boolean.TRUE).build();
		RandomPattern pat = new RandomPattern();
		BaseBlock block = new BaseBlock(i.getTypeId(), i.getData().getData());
		BaseBlock block2 = new BaseBlock(i2.getTypeId(), i2.getData().getData());
		BaseBlock block3 = new BaseBlock(i3.getTypeId(), i3.getData().getData());
		pat.add(block, 0.33);
		pat.add(block2, 0.34);
		pat.add(block3, 0.33);
		Mask mask = new BlockMask(session.getExtent(), new BaseBlock(BlockID.AIR));
		session.replaceBlocks(transform(region), mask, pat);
		session.flushQueue();
	}
	@SuppressWarnings("deprecation")
	public static void fill(WorldEditRegion region, ItemStack i, ItemStack i2, ItemStack i3) {
		EditSession session = (new EditSessionBuilder(FaweAPI.getWorld(region.getWorld().getName()))).fastmode(Boolean.TRUE).build();
		RandomPattern pat = new RandomPattern();
		BaseBlock block = new BaseBlock(i.getTypeId(), i.getData().getData());
		BaseBlock block2 = new BaseBlock(i2.getTypeId(), i2.getData().getData());
		BaseBlock block3 = new BaseBlock(i3.getTypeId(), i3.getData().getData());
		pat.add(block, 0.33);
		pat.add(block2, 0.34);
		pat.add(block3, 0.33);
		session.setBlocks(transform(region), pat);
		session.flushQueue();
	}

	public static void resetMineWorldEdit(Mine mine, Location loc1, Location loc2, List<ItemStack> blocks) {
		WorldEditRegion miningRegion = new WorldEditRegion(toWEVector(loc1), toWEVector(loc2), loc1.getWorld());
		fillAIR(miningRegion, blocks.get(0), blocks.get(1), blocks.get(2));
		for(Player pp : Bukkit.getOnlinePlayers()) {
			if(mine.isLocationInRegion(pp.getLocation())) {
				pp.teleport(mine.getSpawnLocation());
			}
		}
	}
	public static void resetMineFullWorldEdit(Mine mine, Location loc1, Location loc2, List<ItemStack> blocks) {
		WorldEditRegion miningRegion = new WorldEditRegion(toWEVector(loc1), toWEVector(loc2), loc1.getWorld());
		fill(miningRegion, blocks.get(0), blocks.get(1), blocks.get(2));
		for(Player pp : Bukkit.getOnlinePlayers()) {
			if(mine.isLocationInRegion(pp.getLocation())) {
				pp.teleport(mine.getSpawnLocation());
			}
		}
	}


	/*public static void resetMine(Player p, Mine mine, ResetReason reason, List<ItemStack> blocks) {
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
	}*/

}