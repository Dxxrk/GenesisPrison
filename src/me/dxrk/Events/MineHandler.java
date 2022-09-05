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
	
	
	

	private List<UUID> cavalry = new ArrayList<>();
	private List<UUID> hoplite = new ArrayList<>();
	private List<UUID> captain = new ArrayList<>();
	private List<UUID> colonel = new ArrayList<>();
	private List<UUID> ares = new ArrayList<>();
	private List<UUID> hermes = new ArrayList<>();
	private List<UUID> apollo = new ArrayList<>();
	private List<UUID> kronos = new ArrayList<>();
	private List<UUID> zeus = new ArrayList<>();
	
	
	

	
	public void givePlotItem(Player p, String s) {
		ItemStack i = new ItemStack(Material.GRASS);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(c("&a&lPlace on a Plot to Claim!"));
		List<String> lore = new ArrayList<>();
		lore.add(c("&8Owner: &7"+s));
		im.setLore(lore);
		i.setItemMeta(im);
		p.getInventory().addItem(i);
	}
	
	public ItemStack plotItem(String s) {
		ItemStack i = new ItemStack(Material.GRASS);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(c("&a&lPlace on a Plot to Claim!"));
		List<String> lore = new ArrayList<>();
		lore.add(c("&8Owner: &7"+s));
		im.setLore(lore);
		i.setItemMeta(im);
		return i;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = e.getPlayer();
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmdd, String label, String[] args) {
		Player player = (Player)sender;
		if(label.equalsIgnoreCase("giveplotitem")) {
			if(args.length == 2) {
				Player p = Bukkit.getPlayer(args[0]);
				givePlotItem(p, args[1]);
			}
		}
		if(label.equalsIgnoreCase("removemine")) {
			if(!player.hasPermission("staff.removemine")) return false;
			if(args.length == 1) {
				OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
				PlotPlayer pp = PlotPlayer.wrap(p);
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

		wm.cloneWorld("Dxrk", p.getName()+"sWorld");

		Location pworld = new Location(Bukkit.getWorld(p.getName()+"sWorld"), -3, 65, 0);

		WorldBorder wb = Bukkit.getWorld(p.getName()+"sWorld").getWorldBorder();
		wb.setCenter(0, 0);
		wb.setSize(250);

		p.teleport(pworld);

		Location ploc = p.getLocation();

		Location point1 = new Location(p.getWorld(), 13, 64, 6);
		Location point2 = new Location(p.getWorld(), -19, 1, 38);

		ResetHandler.api.createMine(p.getUniqueId().toString(), point1, point2);
		Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());

		RegionManager regions = Objects.requireNonNull(getWorldGuard()).getRegionManager(p.getWorld());
		if(regions.hasRegion(p.getUniqueId().toString())) {
			return;
		}

		m.setSpawnLocation(new Location(p.getWorld(), pworld.getX(), 65, pworld.getZ()));
		m.getResetManager().setMineResetTime(999999);
		m.getResetManager().setPercentageReset(20);
		m.getMineRegion().setBlocksMinedInRegion(0);
		m.save();
		ResetHandler.resetMineFull(m, ResetReason.NORMAL, m.getBlockManager().getRandomBlockFromMine().getTypeId());


		ProtectedRegion region = new ProtectedCuboidRegion(p.getUniqueId().toString(),
				new BlockVector(point1.getX(), point1.getY(), point1.getZ()),
				new BlockVector(point2.getX(), point2.getY(), point2.getZ()));
		if(regions.hasRegion(p.getUniqueId().toString())) {
			regions.removeRegion(p.getUniqueId().toString());
		}





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
	
	public void upgradeMine(Player p, String rank, int prestige){
		if(rank == null && prestige ==0) return;

		if(rank == null && prestige >0) {
			//go through prestige mine blocks
		}
		if(rank != null && prestige == 0){
			//go through rank mine blocks
		}

	}

	
	
	  
	

	

}
