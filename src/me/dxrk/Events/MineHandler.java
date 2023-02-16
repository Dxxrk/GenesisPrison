package me.dxrk.Events;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.dxrk.Events.ResetHandler.ResetReason;
import me.dxrk.Main.SettingsManager;
import me.jet315.prisonmines.mine.Mine;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MineHandler implements Listener, CommandExecutor{
	
	public static MineHandler instance = new MineHandler();
	
	public static MineHandler getInstance() {
		return instance;
	}
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

	    if (!(plugin instanceof WorldGuardPlugin)) {
	        return null; 
	    }

	    return (WorldGuardPlugin) plugin;
	}

	WorldBorderApi borderapi = BorderAPI.getApi();


	
	private ArrayList<Player> list = new ArrayList<>();

	SettingsManager settings = SettingsManager.getInstance();
	
	
	/*
		Finding a players Mine Block when first creating.
	 */


	//Create a list that adds different materials
	// You then use the list to grab materials +-1 from the initial mineblock | i.e if the block is moss stone, you would also get cobblestone and stone in your mine
	// So on and so forth with every single block up until you get to prestige 2000, which is 40 different initial mine blocks
	// This also means changing the blocks that the donator ranks get to be more precise and balanced and not as grindy to reach the next block.

	private List<ItemStack> mineBlocks() {
		List<ItemStack> mineblocks = new ArrayList<>();
		mineblocks.add(new ItemStack(Material.COBBLESTONE));
		mineblocks.add(new ItemStack(Material.MOSSY_COBBLESTONE));
		mineblocks.add(new ItemStack(Material.STONE, 1, (short)0));
		mineblocks.add(new ItemStack(Material.STONE, 1, (short)1));
		mineblocks.add(new ItemStack(Material.STONE, 1, (short)2));
		mineblocks.add(new ItemStack(Material.STONE, 1, (short)3));
		mineblocks.add(new ItemStack(Material.STONE, 1, (short)4));
		mineblocks.add(new ItemStack(Material.STONE, 1, (short)5));
		mineblocks.add(new ItemStack(Material.STONE, 1, (short)6));
		mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short)0));
		mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short)1));
		mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short)2));
		mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short)3));
		mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short)0));
		mineblocks.add(new ItemStack(Material.SANDSTONE, 1, (short)0));
		mineblocks.add(new ItemStack(Material.SANDSTONE, 1, (short)1));
		mineblocks.add(new ItemStack(Material.SANDSTONE, 1, (short)2));
		mineblocks.add(new ItemStack(Material.RED_SANDSTONE, 1, (short)0));
		mineblocks.add(new ItemStack(Material.RED_SANDSTONE, 1, (short)1));
		mineblocks.add(new ItemStack(Material.RED_SANDSTONE, 1, (short)2));
		mineblocks.add(new ItemStack(Material.HARD_CLAY));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)0));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)1));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)2));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)3));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)4));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)5));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)6));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)7));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)8));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)9));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)10));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)11));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)12));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)13));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)14));
		mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short)15));
		mineblocks.add(new ItemStack(Material.BRICK));
		mineblocks.add(new ItemStack(Material.COAL_ORE));
		mineblocks.add(new ItemStack(Material.COAL_BLOCK));
		mineblocks.add(new ItemStack(Material.IRON_ORE));
		mineblocks.add(new ItemStack(Material.IRON_BLOCK));
		mineblocks.add(new ItemStack(Material.GOLD_ORE));
		mineblocks.add(new ItemStack(Material.GOLD_BLOCK));
		mineblocks.add(new ItemStack(Material.REDSTONE_ORE));
		mineblocks.add(new ItemStack(Material.REDSTONE_BLOCK));
		mineblocks.add(new ItemStack(Material.LAPIS_ORE));
		mineblocks.add(new ItemStack(Material.LAPIS_BLOCK));
		mineblocks.add(new ItemStack(Material.DIAMOND_ORE));
		mineblocks.add(new ItemStack(Material.DIAMOND_BLOCK));
		mineblocks.add(new ItemStack(Material.EMERALD_ORE));
		mineblocks.add(new ItemStack(Material.EMERALD_BLOCK));
		mineblocks.add(new ItemStack(Material.NETHERRACK));
		mineblocks.add(new ItemStack(Material.NETHER_BRICK));
		mineblocks.add(new ItemStack(Material.QUARTZ_ORE));
		mineblocks.add(new ItemStack(Material.QUARTZ_BLOCK, 1, (short)0));
		mineblocks.add(new ItemStack(Material.QUARTZ_BLOCK, 1, (short)1));
		mineblocks.add(new ItemStack(Material.QUARTZ_BLOCK, 1, (short)2));
		mineblocks.add(new ItemStack(Material.PRISMARINE, 1, (short)0));
		mineblocks.add(new ItemStack(Material.PRISMARINE, 1, (short)1));
		mineblocks.add(new ItemStack(Material.PRISMARINE, 1, (short)2));
		mineblocks.add(new ItemStack(Material.ENDER_STONE));
		mineblocks.add(new ItemStack(Material.OBSIDIAN));
		return mineblocks;
	}

	public void changeMineBlock(Player p) {

	}

	public Material mineBlock(Player p){
		Material mat = Material.COBBLESTONE;
		//Find players prestige/rank

		int prestiges = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Prestiges");

		if(prestiges >= 1750){
			mat = Material.ENDER_STONE;
		}
		else if(prestiges >=1500){
			mat = Material.PRISMARINE;
		}
		else if(prestiges >=1400){
			mat = Material.QUARTZ_BLOCK;
		}
		else if(prestiges >=1300) {
			mat = Material.QUARTZ_ORE;
		}
		else if(prestiges >=1200) {
			mat = Material.NETHER_BRICK;
		}
		else if(p.hasPermission("rank.genesis")){
			mat = Material.NETHER_BRICK;
		}
		else if(prestiges >=1100) {
			mat = Material.NETHERRACK;
		}
		else if(prestiges >=1025) {
			mat = Material.EMERALD_BLOCK;
		}
		else if(prestiges >=950) {
			mat = Material.EMERALD_ORE;
		}
		else if(p.hasPermission("rank.olympian")) {
			mat = Material.EMERALD_ORE;
		}
		else if(prestiges >=875) {
			mat = Material.DIAMOND_BLOCK;
		}
		else if(prestiges >=800) {
			mat = Material.DIAMOND_ORE;
		}
		else if(prestiges >=725) {
			mat = Material.LAPIS_BLOCK;
		}
		else if(p.hasPermission("rank.god")) {
			mat = Material.LAPIS_BLOCK;
		}
		else if(prestiges >=650) {
			mat = Material.LAPIS_ORE;
		}
		else if(prestiges >=575) {
			mat = Material.REDSTONE_BLOCK;
		}
		else if(prestiges >=500) {
			mat = Material.REDSTONE_ORE;
		}
		else if(p.hasPermission("rank.titan")) {
			mat = Material.REDSTONE_ORE;
		}
		else if(prestiges >=450) {
			mat = Material.GOLD_BLOCK;
		}
		else if(prestiges >=400) {
			mat = Material.GOLD_ORE;
		}
		else if(p.hasPermission("rank.demi-god")) {
			mat = Material.GOLD_ORE;
		}
		else if(prestiges >=350) {
			mat = Material.IRON_BLOCK;
		}
		else if(prestiges >=300) {
			mat = Material.IRON_ORE;
		}
		else if(prestiges >=250) {
			mat = Material.COAL_BLOCK;
		}
		else if(p.hasPermission("rank.hero")) {
			mat = Material.COAL_BLOCK;
		}
		else if(prestiges >=200) {
			mat = Material.COAL_ORE;
		}
		else if(prestiges >=150) {
			mat = Material.BRICK;
		}
		else if(p.hasPermission("rank.mvp")) {
			mat = Material.BRICK;
		}
		else if(prestiges >=125) {
			mat = Material.HARD_CLAY;
		}
		else if(prestiges >=100) {
			mat = Material.SANDSTONE;
		}
		else if(p.hasPermission("rank.vip")) {
			mat = Material.SANDSTONE;
		}
		else if(prestiges >=75) {
			mat = Material.SMOOTH_BRICK;
		}
		else if(prestiges >=50) {
			mat = Material.STONE;
		}
		else if(p.hasPermission("rank.donator")) {
			mat = Material.STONE;
		}
		else if(prestiges >=25) {
			mat = Material.MOSSY_COBBLESTONE;
		}



		return mat;
	}
	
	
	

	

	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmdd, String label, String[] args) {
		Player player = (Player)sender;

		if(label.equalsIgnoreCase("removemine")) {
			if(!player.hasPermission("staff.removemine")) return false;
			if(args.length == 1) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
				Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
				m.delete();
				MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
				MVWorldManager wm = core.getMVWorldManager();
				wm.unloadWorld(p.getName()+"sWorld");
				wm.deleteWorld(p.getName()+"sWorld");
				settings.getPlayerData().set(p.getUniqueId().toString()+".HasMine", false);
			}
		}
		if(label.equalsIgnoreCase("updatemine")) {
			if(args.length == 1){
				Player p = Bukkit.getPlayer(args[0]);
				updateMine(p);
			}
		}
		return false;
	}
	
	@EventHandler
	public void drop(PlayerDropItemEvent e) {
		if(e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_PICKAXE)) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(c("&cYou cannot drop your pickaxe!"));
		}
	}
	

	/*
		CREATING THE MINE
	 */
	@SuppressWarnings("deprecation")
	public void CreateMine(Player p){
		MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
		MVWorldManager wm = core.getMVWorldManager();


		//Setting up the world
		wm.cloneWorld("Dxrk", p.getName()+"sWorld");
		wm.getMVWorld(p.getName()+"sWorld").setAlias(p.getName()+"sWorld");

		Location pworld = new Location(Bukkit.getWorld(p.getName()+"sWorld"), 0.5, 113, 0.5, -90, 0);

		WorldBorder wb = Bukkit.getWorld(p.getName()+"sWorld").getWorldBorder();
		wb.setCenter(0, 0);
		wb.setSize(250);

		p.teleport(pworld);

		Location point1 = new Location(Bukkit.getWorld(p.getName()+"sWorld"), 9, 111, 34);
		Location point2 = new Location(Bukkit.getWorld(p.getName()+"sWorld"), 49, 79, -34);


		//Creating the actual mine
		ResetHandler.api.createMine(p.getUniqueId().toString(), point1, point2);
		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());

		RegionManager regions = getWorldGuard().getRegionManager(Bukkit.getWorld(p.getName() + "sWorld"));
		if(regions.hasRegion(p.getUniqueId().toString())) {
			return;
		}

		m.setSpawnLocation(pworld);
		m.getResetManager().setMineResetTime(999999);
		m.getResetManager().setPercentageReset(20);
		m.getMineRegion().setBlocksMinedInRegion(0);
		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0.0F);
		m.getBlockManager().addBlockToMineRegion(new ItemStack(mineBlock(p)), 100.0F);
		m.save();
		ResetHandler.resetMineFull(m, ResetReason.NORMAL, mineBlock(p).getId());

		if(regions.hasRegion(p.getUniqueId().toString())) {
			regions.removeRegion(p.getUniqueId().toString());
		}
		//Create Mine region that allows the enchants to work only inside the mine
		ProtectedRegion region = new ProtectedCuboidRegion(p.getUniqueId().toString(),
				new BlockVector(point1.getX(), point1.getY(), point1.getZ()),
				new BlockVector(point2.getX(), point2.getY(), point2.getZ()));
		region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.INTERACT, StateFlag.State.ALLOW);
		DefaultDomain members = region.getMembers();
		members.addPlayer(p.getUniqueId());
		region.setPriority(2);
		regions.addRegion(region);

		Location g1 = new Location(Bukkit.getWorld(p.getName()+"sWorld"), 66, 181, -74);
		Location g2 = new Location(Bukkit.getWorld(p.getName()+"sWorld"), -14, 69, 74);

		//Create region that allows building within limits.
		ProtectedRegion outside = new ProtectedCuboidRegion("outside",
				new BlockVector(g1.getX(), g1.getY(), g1.getZ()),
				new BlockVector(g2.getX(), g2.getY(), g2.getZ()));
		outside.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
		outside.setFlag(DefaultFlag.FALL_DAMAGE, StateFlag.State.DENY);
		outside.setFlag(DefaultFlag.FEED_AMOUNT, 100);
		outside.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.DENY);
		outside.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
		outside.setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		outside.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
		outside.setFlag(DefaultFlag.INTERACT, StateFlag.State.ALLOW);
		outside.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
		outside.setPriority(1);

		regions.addRegion(outside);
		settings.getPlayerData().set(p.getUniqueId().toString()+".HasMine", true);

		p.sendMessage(c("&7&oMine Generation Complete."));

	}


	/*
		UPDATING THE MINE
	 */
	@SuppressWarnings("deprecation")
	public void updateMine(Player p){
		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
		m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0.0F);
		m.getBlockManager().addBlockToMineRegion(new ItemStack(mineBlock(p)), 100.0F);
		m.getResetManager().setMineResetTime(999999);
		m.getResetManager().setPercentageReset(20);
		m.getMineRegion().setBlocksMinedInRegion(0);
		m.save();
		ResetHandler.resetMineFull(m, ResetReason.NORMAL, mineBlock(p).getId());

	}

	
	
	  
	

	

}
