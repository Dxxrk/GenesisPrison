package me.dxrk.Events;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.dxrk.Events.ResetHandler.ResetReason;
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
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

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
	
	
	/*
		Finding a players Mine Block when first creating.
	 */

	public Material mineBlock(Player p){
		Material mat = Material.COBBLESTONE;
		//Find players prestige/rank



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
				//Add options to remove mine
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

		Location pworld = new Location(Bukkit.getWorld(p.getName()+"sWorld"), -2.5, 65, 0.5);

		WorldBorder wb = Bukkit.getWorld(p.getName()+"sWorld").getWorldBorder();
		wb.setCenter(-3, 22);
		wb.setSize(250);

		p.teleport(pworld);

		Location ploc = p.getLocation();

		Location point1 = new Location(Bukkit.getWorld(p.getName()+"sWorld"), 13, 64, 6);
		Location point2 = new Location(Bukkit.getWorld(p.getName()+"sWorld"), -19, 1, 38);


		//Creating the actual mine
		ResetHandler.api.createMine(p.getUniqueId().toString(), point1, point2);
		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());

		RegionManager regions = Objects.requireNonNull(getWorldGuard()).getRegionManager(p.getWorld());
		if(regions.hasRegion(p.getUniqueId().toString())) {
			return;
		}

		m.setSpawnLocation(pworld);
		m.getResetManager().setMineResetTime(999999);
		m.getResetManager().setPercentageReset(20);
		m.getMineRegion().setBlocksMinedInRegion(0);
		m.save();
		ResetHandler.resetMineFull(m, ResetReason.NORMAL, mineBlock(p).getId());


		ProtectedRegion region = new ProtectedCuboidRegion(p.getUniqueId().toString(),
				new BlockVector(point1.getX(), point1.getY(), point1.getZ()),
				new BlockVector(point2.getX(), point2.getY(), point2.getZ()));
		if(regions.hasRegion(p.getUniqueId().toString())) {
			regions.removeRegion(p.getUniqueId().toString());
		}

		//Create region that allows building within limits.
		ProtectedRegion global = new GlobalProtectedRegion("global");
		global.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.ALLOW);
		global.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.ALLOW);
		global.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.INTERACT, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);

		regions.addRegion(global);

		//Create Mine region that allows the enchants to work only inside the mine
		region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
		region.setFlag(DefaultFlag.BLOCK_BREAK.getRegionGroupFlag(), RegionGroup.MEMBERS);
		region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
		region.setFlag(DefaultFlag.INTERACT, StateFlag.State.ALLOW);
		DefaultDomain members = region.getMembers();
		members.addPlayer(p.getUniqueId());
		regions.addRegion(region);

		p.sendMessage(c("&7&oMine Generated."));

	}


	/*
		UPDATING THE MINE
	 */
	
	public void updateMine(Player p, String rank, int prestige){
		if(rank == null && prestige ==0) return;

		if(rank == null && prestige >0) {
			//go through prestige mine blocks
		}
		if(rank != null && prestige == 0){
			//go through rank mine blocks
		}

	}

	
	
	  
	

	

}
