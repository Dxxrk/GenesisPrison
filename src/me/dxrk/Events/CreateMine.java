package me.dxrk.Events;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.google.common.eventbus.Subscribe;
import com.intellectualcrafters.plot.object.Plot;
import com.intellectualcrafters.plot.object.PlotPlayer;
import com.plotsquared.bukkit.events.PlayerClaimPlotEvent;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.RegionGroup;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.dxrk.Main.Main;
import me.dxrk.Main.ResetHandler;
import me.dxrk.Main.ResetHandler.ResetReason;
import me.jet315.prisonmines.mine.Mine;

public class CreateMine implements Listener, CommandExecutor{
	
	public static CreateMine instance = new CreateMine();
	
	public static CreateMine getInstance() {
		return instance;
	}
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	private WorldGuardPlugin getWorldGuard() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

	    if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
	        return null; 
	    }

	    return (WorldGuardPlugin) plugin;
	}
	
	private ArrayList<Player> list = new ArrayList<Player>();
	
	
	

	private List<UUID> cavalry = new ArrayList<UUID>();
	private List<UUID> hoplite = new ArrayList<UUID>();
	private List<UUID> captain = new ArrayList<UUID>();
	private List<UUID> colonel = new ArrayList<UUID>();
	private List<UUID> ares = new ArrayList<UUID>();
	private List<UUID> hermes = new ArrayList<UUID>();
	private List<UUID> apollo = new ArrayList<UUID>();
	private List<UUID> kronos = new ArrayList<UUID>();
	private List<UUID> zeus = new ArrayList<UUID>();
	
	
	
	public void checkRank(Player p) {
				
					
					Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
					if(m == null) {
						
					}
					if(p.hasPermission("rank.zeus") || p.hasPermission("plotup.9")) {
						if(!zeus.contains(p.getUniqueId())) {
							zeus.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHER_BRICK), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 112);
						}
					} 
					else if(p.hasPermission("rank.kronos") || p.hasPermission("plotup.8")) {
							
						if(!kronos.contains(p.getUniqueId())) {
							kronos.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.QUARTZ_ORE), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 153);
						}
					}
					else if(p.hasPermission("rank.apollo") || p.hasPermission("plotup.7")) {
						if(!apollo.contains(p.getUniqueId())) {
							apollo.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHERRACK), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 87);
						}
					}
					else if(p.hasPermission("rank.hermes") || p.hasPermission("plotup.6")) {
						if(!hermes.contains(p.getUniqueId())) {
							hermes.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.ENDER_STONE), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 121);
						}
					}
					else if(p.hasPermission("rank.ares") || p.hasPermission("plotup.5")) {
						if(!ares.contains(p.getUniqueId())) {
							ares.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.PRISMARINE), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 168);
						}
					}
					else if(p.hasPermission("rank.colonel") || p.hasPermission("plotup.4")) {
						if(!colonel.contains(p.getUniqueId())) {
							colonel.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.BRICK), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 45);
						}
					}
					else if(p.hasPermission("rank.captain") || p.hasPermission("plotup.3")) {
						if(!captain.contains(p.getUniqueId())) {
							captain.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.HARD_CLAY), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 172);
						}
					}
					else if(p.hasPermission("rank.hoplite") || p.hasPermission("plotup.2")) {
						if(!hoplite.contains(p.getUniqueId())) {
							hoplite.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.SMOOTH_BRICK), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 98);
						}
					}
					else if(p.hasPermission("rank.cavalry") || p.hasPermission("plotup.1")) {
						if(!cavalry.contains(p.getUniqueId())) {
							cavalry.add(p.getUniqueId());
							
							m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
							m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.STONE), 100);
							ResetHandler.resetMineFull(m, ResetReason.INTERVAL, 1);
						}
					}
				
				
			}
	
	public void givePlotItem(Player p, String s) {
		ItemStack i = new ItemStack(Material.GRASS);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(c("&a&lPlace on a Plot to Claim!"));
		List<String> lore = new ArrayList<String>();
		lore.add(c("&8Owner: &7"+s));
		im.setLore(lore);
		i.setItemMeta(im);
		p.getInventory().addItem(i);
	}
	
	public ItemStack plotItem(String s) {
		ItemStack i = new ItemStack(Material.GRASS);
		ItemMeta im = i.getItemMeta();
		im.setDisplayName(c("&a&lPlace on a Plot to Claim!"));
		List<String> lore = new ArrayList<String>();
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
		if(label.equalsIgnoreCase("checkminereset")) {
			if(sender.hasPermission("rank.owner")) {
				if(args.length == 1) {
					Player p = Bukkit.getPlayer(args[0]);
					checkRank(p);
				}
				
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
	
	@EventHandler
	public void place(BlockPlaceEvent e) {
		Player p = e.getPlayer();
		Block b = e.getBlock();
		if(b.getType().equals(Material.GRASS) && p.getItemInHand().getItemMeta().getDisplayName().equals(c("&a&lPlace on a Plot to Claim!"))) {
			e.setCancelled(true);
		}
	}
	
	
	@EventHandler
	@Subscribe
	public void onClaim(PlayerClaimPlotEvent e) {
		Player p = e.getPlayer();
		e.setCancelled(true);
		
		RegionManager regions = getWorldGuard().getRegionManager(p.getWorld());
		if(regions.hasRegion(p.getUniqueId().toString())) {
			return;
		}
		
		list.add(p);
		
		p.sendMessage(c("&7&oCreating Mine..."));
		
			new BukkitRunnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				//String[] name = p.getItemInHand().getItemMeta().getLore().get(0).split(" ");
				
				//OfflinePlayer player = Bukkit.getOfflinePlayer(ChatColor.stripColor(name[1]));
				
				PlotPlayer pp = PlotPlayer.wrap(p);
				
				com.intellectualcrafters.plot.object.Location ploc = pp.getLocation();
				
				if(ploc.isPlotRoad()) {
					e.setCancelled(true);
					list.remove(p);
					p.sendMessage(c("&cMust stand in a plot!"));
					p.updateInventory();
					return;
				}
				
				
				Plot plot = Plot.getPlot(ploc);
				
				
				
				
				plot.claim(pp, false, null);
				plot.setOwner(p.getUniqueId());
				
				
				com.intellectualcrafters.plot.object.Location l = plot.getAllCorners().get(3);
				com.intellectualcrafters.plot.object.Location l2 = plot.getAllCorners().get(1);
				
				Location min = new Location(p.getWorld(), l.getX(), l.getY()+1, l.getZ());
				Location max = new Location(p.getWorld(), l2.getX(), l2.getY()+64, l2.getZ());
				
				
				
				
				
				ResetHandler.api.createMine(p.getUniqueId().toString(), min, max);
				Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
					
				
				if(Main.perms.playerHas("world", p, "rank.zeus") || Main.perms.playerHas("world", p, "plotup.9")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHER_BRICK), 100);
					
				} 
				else if(Main.perms.playerHas("world", p, "rank.kronos") || Main.perms.playerHas("world", p, "plotup.8")) {
						
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.QUARTZ_ORE), 100);
					
				}
				else if(Main.perms.playerHas("world", p, "rank.apollo") || Main.perms.playerHas("world", p, "plotup.7")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHERRACK), 100);
					
				}
				else if(Main.perms.playerHas("world", p, "rank.hermes") || Main.perms.playerHas("world", p, "plotup.6")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.ENDER_STONE), 100);
					
				}
				else if(Main.perms.playerHas("world", p, "rank.ares") || Main.perms.playerHas("world", p, "plotup.5")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.PRISMARINE), 100);
					
				}
				else if(Main.perms.playerHas("world", p, "rank.colonel") || Main.perms.playerHas("world", p, "plotup.4")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.BRICK), 100);
					
				}
				else if(Main.perms.playerHas("world", p, "rank.captain") || Main.perms.playerHas("world", p, "plotup.3")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.HARD_CLAY), 100);
					
				}
				else if(Main.perms.playerHas("world", p, "rank.hoplite") || Main.perms.playerHas("world", p, "plotup.2")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.SMOOTH_BRICK), 100);
					
				}
				else if(Main.perms.playerHas("world", p, "rank.cavalry") || Main.perms.playerHas("world", p, "plotup.1")) { 
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.STONE), 100);
						
					
				} else {
					m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
					m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.COBBLESTONE), 100);
					
				}
					
					
					m.setSpawnLocation(new Location(p.getWorld(), plot.getCenter().getX(), 65, plot.getCenter().getZ()));
					m.getResetManager().setMineResetTime(999999);
					m.getResetManager().setPercentageReset(20);
					m.getMineRegion().setBlocksMinedInRegion(0);
					m.save();
					ResetHandler.resetMineFull(m, ResetReason.NORMAL, m.getBlockManager().getRandomBlockFromMine().getTypeId());
					
					RegionManager regions = getWorldGuard().getRegionManager(p.getWorld());
					ProtectedRegion region = new ProtectedCuboidRegion(p.getUniqueId().toString(), 
							new BlockVector(min.getX(), min.getY(), min.getZ()), 
							new BlockVector(max.getX(), max.getY(), max.getZ()));
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
					
				
				
				p.sendMessage(c("&7&oDone!"));
				list.remove(p);
			}
		}.runTaskLater(Main.plugin, 20*2);
		} 
		
	@EventHandler
	public void onPlace(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if(p.getItemInHand().equals(new ItemStack(Material.AIR)) || p.getItemInHand() == null) { e.setCancelled(true); return;}
		if(!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
		if(!p.getItemInHand().hasItemMeta()) return;
		
		if(p.getItemInHand().getType().equals(Material.GRASS) && p.getItemInHand().getItemMeta().getDisplayName().equals(c("&a&lPlace on a Plot to Claim!"))) {
		e.setCancelled(true);
		
		list.add(p);
		p.sendMessage(c("&7&oCreating Mine..."));
		
			new BukkitRunnable() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void run() {
				
				String[] name = p.getItemInHand().getItemMeta().getLore().get(0).split(" ");
				
				OfflinePlayer player = Bukkit.getOfflinePlayer(ChatColor.stripColor(name[1]));
				
				PlotPlayer pp = PlotPlayer.wrap(player);
				
				com.intellectualcrafters.plot.object.Location ploc = pp.getLocation();
				
				if(ploc.isPlotRoad()) {
					e.setCancelled(true);
					list.remove(p);
					p.sendMessage(c("&cMust stand in a plot!"));
					p.updateInventory();
					return;
				}
				
				
				Plot plot = Plot.getPlot(ploc);
				
				
				ItemStack block = new ItemStack(Material.GRASS);
				ItemMeta bm = block.getItemMeta();
				bm.setDisplayName(c("&a&lPlace on a Plot to Claim!"));
				List<String> lore = new ArrayList<String>();
				lore.add(c("&8Owner: "+player));
				bm.setLore(lore);
				block.setItemMeta(bm);
				
				p.getInventory().remove(block);
				
				plot.claim(pp, false, null);
				plot.setOwner(player.getUniqueId());
				
				
				com.intellectualcrafters.plot.object.Location l = plot.getAllCorners().get(3);
				com.intellectualcrafters.plot.object.Location l2 = plot.getAllCorners().get(1);
				
				Location min = new Location(p.getWorld(), l.getX(), l.getY()+1, l.getZ());
				Location max = new Location(p.getWorld(), l2.getX(), l2.getY()+64, l2.getZ());
				
				
				
				
				
				ResetHandler.api.createMine(player.getUniqueId().toString(), min, max);
				Mine m = ResetHandler.api.getMineByName(player.getUniqueId().toString());
					
				
				if(Main.perms.playerHas("world", player, "rank.zeus") || Main.perms.playerHas("world", player, "plotup.9")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHER_BRICK), 100);
					
				} 
				else if(Main.perms.playerHas("world", player, "rank.kronos") || Main.perms.playerHas("world", player, "plotup.8")) {
						
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.QUARTZ_ORE), 100);
					
				}
				else if(Main.perms.playerHas("world", player, "rank.apollo") || Main.perms.playerHas("world", player, "plotup.7")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.NETHERRACK), 100);
					
				}
				else if(Main.perms.playerHas("world", player, "rank.hermes") || Main.perms.playerHas("world", player, "plotup.6")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.ENDER_STONE), 100);
					
				}
				else if(Main.perms.playerHas("world", player, "rank.ares") || Main.perms.playerHas("world", player, "plotup.5")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.PRISMARINE), 100);
					
				}
				else if(Main.perms.playerHas("world", player, "rank.colonel") || Main.perms.playerHas("world", player, "plotup.4")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.BRICK), 100);
					
				}
				else if(Main.perms.playerHas("world", player, "rank.captain") || Main.perms.playerHas("world", player, "plotup.3")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.HARD_CLAY), 100);
					
				}
				else if(Main.perms.playerHas("world", player, "rank.hoplite") || Main.perms.playerHas("world", player, "plotup.2")) {
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.SMOOTH_BRICK), 100);
					
				}
				else if(Main.perms.playerHas("world", player, "rank.cavalry") || Main.perms.playerHas("world", player, "plotup.1")) { 
					
						
						m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
						m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.STONE), 100);
						
					
				} else {
					m.getBlockManager().modifyBlockChanceInRegion(m.getBlockManager().getRandomBlockFromMine(), 0);
					m.getBlockManager().addBlockToMineRegion(new ItemStack(Material.COBBLESTONE), 100);
					
				}
					
					
					m.setSpawnLocation(new Location(p.getWorld(), plot.getCenter().getX(), 65, plot.getCenter().getZ()));
					m.getResetManager().setMineResetTime(999999);
					m.getResetManager().setPercentageReset(20);
					m.getMineRegion().setBlocksMinedInRegion(0);
					m.save();
					ResetHandler.resetMineFull(m, ResetReason.NORMAL, m.getBlockManager().getRandomBlockFromMine().getTypeId());
					
					RegionManager regions = getWorldGuard().getRegionManager(p.getWorld());
					ProtectedRegion region = new ProtectedCuboidRegion(player.getUniqueId().toString(), 
							new BlockVector(min.getX(), min.getY(), min.getZ()), 
							new BlockVector(max.getX(), max.getY(), max.getZ()));
					if(regions.hasRegion(player.getUniqueId().toString())) {
						regions.removeRegion(player.getUniqueId().toString());
					}
					
					
					
					
					
					region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
					region.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
					region.setFlag(DefaultFlag.BLOCK_BREAK.getRegionGroupFlag(), RegionGroup.MEMBERS);
					region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
					region.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
					region.setFlag(DefaultFlag.INTERACT, StateFlag.State.ALLOW);
					DefaultDomain members = region.getMembers();
					members.addPlayer(player.getUniqueId());
					regions.addRegion(region);
					
				
				
				p.sendMessage(c("&7&oDone!"));
				list.remove(p);
			}
		}.runTaskLater(Main.plugin, 20*2);
		} 
		
	}
	
	
	  
	

	

}
