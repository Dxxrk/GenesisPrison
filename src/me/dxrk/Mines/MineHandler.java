package me.dxrk.Mines;

import com.github.yannicklamprecht.worldborder.api.BorderAPI;
import com.github.yannicklamprecht.worldborder.api.WorldBorderApi;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MineHandler implements Listener, CommandExecutor {

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

    static SettingsManager settings = SettingsManager.getInstance();
	
	
	/*
		Finding a players Mine Block when first creating.
	 */


    //Create a list that adds different materials
    // You then use the list to grab materials +-1 from the initial mineblock | i.e if the block is moss stone, you would also get cobblestone and stone in your mine
    // So on and so forth with every single block up until you get to prestige 2000, which is 40 different initial mine blocks
    // This also means changing the blocks that the donator ranks get to be more precise and balanced and not as grindy to reach the next block.

    private static List<ItemStack> mineBlocks() {
        List<ItemStack> mineblocks = new ArrayList<>();
        mineblocks.add(new ItemStack(Material.COBBLESTONE)); //0
        mineblocks.add(new ItemStack(Material.MOSSY_COBBLESTONE));
        mineblocks.add(new ItemStack(Material.STONE, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.STONE, 1, (short) 1));
        mineblocks.add(new ItemStack(Material.STONE, 1, (short) 2));
        mineblocks.add(new ItemStack(Material.STONE, 1, (short) 3));
        mineblocks.add(new ItemStack(Material.STONE, 1, (short) 4));
        mineblocks.add(new ItemStack(Material.STONE, 1, (short) 5));
        mineblocks.add(new ItemStack(Material.STONE, 1, (short) 6));
        mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 1));
        mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 2));
        mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 3));
        mineblocks.add(new ItemStack(Material.SMOOTH_BRICK, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.SANDSTONE, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.SANDSTONE, 1, (short) 1));
        mineblocks.add(new ItemStack(Material.SANDSTONE, 1, (short) 2));
        mineblocks.add(new ItemStack(Material.RED_SANDSTONE, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.RED_SANDSTONE, 1, (short) 1));
        mineblocks.add(new ItemStack(Material.RED_SANDSTONE, 1, (short) 2));
        mineblocks.add(new ItemStack(Material.HARD_CLAY));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 1));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 2));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 3));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 4));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 5));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 6));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 7));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 8));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 9));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 10));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 11));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 12));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 13));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 14));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 15));
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
        mineblocks.add(new ItemStack(Material.QUARTZ_BLOCK, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.QUARTZ_BLOCK, 1, (short) 1));
        mineblocks.add(new ItemStack(Material.QUARTZ_BLOCK, 1, (short) 2));
        mineblocks.add(new ItemStack(Material.PRISMARINE, 1, (short) 0));
        mineblocks.add(new ItemStack(Material.PRISMARINE, 1, (short) 1));
        mineblocks.add(new ItemStack(Material.PRISMARINE, 1, (short) 2));
        mineblocks.add(new ItemStack(Material.OBSIDIAN));
        mineblocks.add(new ItemStack(Material.ENDER_STONE));
        return mineblocks;
    }

    public static List<ItemStack> Blocks(int start) {
        List<ItemStack> blocks = new ArrayList<>();
        if (start == 0) {
            blocks.add(mineBlocks().get(0));
            blocks.add(mineBlocks().get(0));
            blocks.add(mineBlocks().get(0));
        } else if (start >= 62) {
            blocks.add(mineBlocks().get(62));
            blocks.add(mineBlocks().get(62));
            blocks.add(mineBlocks().get(62));
        } else {
            blocks.add(mineBlocks().get(start - 1));
            blocks.add(mineBlocks().get(start));
            blocks.add(mineBlocks().get(start + 1));
        }
        return blocks;
    }


    @SuppressWarnings("deprecation")
    public void updateMine(Player p, int rank) {
        int start = rank / 16;
        List<ItemStack> blocks = Blocks(start);
        Mine m = MineSystem.getInstance().getMineByPlayer(p);
        if (start == 0) {
            m.setBlock1(new ItemStack(Material.COBBLESTONE));
            m.setBlock2(new ItemStack(Material.COBBLESTONE));
            m.setBlock3(new ItemStack(Material.COBBLESTONE));
        } else {
            m.setBlock1(blocks.get(0));
            m.setBlock2(blocks.get(1));
            m.setBlock3(blocks.get(2));
        }
        m.save();
        ResetHandler.resetMineFullWorldEdit(m, m.getMinPoint(), m.getMaxPoint(), blocks);

    }

    /*@EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        World world = Bukkit.getWorld(p.getUniqueId().toString());
        if(world != null)
            MineWorldCreator.getInstance().unloadWorld(Bukkit.getWorld(p.getUniqueId().toString()));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".HasMine")) {
            World world = new WorldCreator(p.getUniqueId().toString()).createWorld();
        }
    }*/


    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmdd, String label, String[] args) {
        Player player = (Player) sender;

        if (label.equalsIgnoreCase("removemine")) {
            if (!player.hasPermission("staff.removemine")) return false;
            if (args.length == 1) {
                OfflinePlayer p = Bukkit.getOfflinePlayer(args[0]);
                Mine m = MineSystem.getInstance().getMineByPlayer(player);
                //add methods for mine delete
                settings.getPlayerData().set(p.getUniqueId().toString() + ".HasMine", false);
                m.delete();
                MineSystem.getInstance().removeActiveMine(m);
                if (p.isOnline()) {
                    Player po = p.getPlayer();
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "spawn " + po.getName());
                }
                if (Bukkit.getWorld(p.getUniqueId().toString()) != null) {
                    World world = Bukkit.getWorld(p.getUniqueId().toString());
                    MineWorldCreator.getInstance().unloadWorld(world);
                    File file = world.getWorldFolder();
                    MineWorldCreator.getInstance().deleteWorld(file);
                }
            }
        }
        if (label.equalsIgnoreCase("updatemine")) {
            if (args.length == 1) {
                Player p = Bukkit.getPlayer(args[0]);
                updateMine(p, RankupHandler.instance.getRank(p));
            }
        }
        return false;
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_PICKAXE) || e.getItemDrop().getItemStack().getType().equals(Material.WOOD_PICKAXE) ||
                e.getItemDrop().getItemStack().getType().equals(Material.STONE_PICKAXE) || e.getItemDrop().getItemStack().getType().equals(Material.IRON_PICKAXE) ||
                e.getItemDrop().getItemStack().getType().equals(Material.GOLD_PICKAXE)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(c("&cYou cannot drop your pickaxe!"));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity ent = event.getEntity();
        if (ent instanceof Player) {
            Player player = (Player) ent;
            if (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
                event.setCancelled(true);
            }
        }
    }

    public void createMine(String name, Location corner1, Location corner2, Location spawn, World world) {
        Mine m = new Mine(name, corner1, corner2, new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), spawn, world);
        m.save();
        MineSystem.getInstance().addActiveMine(m);
    }


    /*
        CREATING THE MINE
     */
    @SuppressWarnings("deprecation")
    public void CreateMine(Player p) {
        //Setting up the world
        MineWorldCreator.getInstance().createMineWorld1(p);
        World world = Bukkit.getWorld(p.getUniqueId().toString());

        Location pworld = new Location(world, 0.5, 100.5, 0.5, -90, 0);

        WorldBorder wb = world.getWorldBorder();
        wb.setCenter(31, 0);
        wb.setSize(86);
        p.teleport(pworld);

        Location point1 = new Location(world, 15, 98, -16);
        Location point2 = new Location(world, 47, 65, 16);


        //Creating the actual mine
        //ResetHandler.api.createMine(p.getUniqueId().toString(), point1, point2);

        createMine(p.getUniqueId().toString(), point1, point2, pworld, world);
        Mine m = MineSystem.getInstance().getMineByPlayer(p);

        RegionManager regions = getWorldGuard().getRegionManager(world);
        if (regions.hasRegion("mine")) {
            regions.removeRegion("mine");
        }
        if (regions.hasRegion("outside")) {
            regions.removeRegion("outside");
        }
        //Create Mine region that allows the enchants to work only inside the mine
        ProtectedRegion region = new ProtectedCuboidRegion("mine",
                new BlockVector(point1.getX(), point1.getY(), point1.getZ()),
                new BlockVector(point2.getX(), point2.getY(), point2.getZ()));
        region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.INTERACT, StateFlag.State.ALLOW);
        DefaultDomain members = region.getMembers();
        members.addPlayer(p.getUniqueId());
        region.setPriority(2);
        regions.addRegion(region);

        Location g1 = new Location(world, -15, 255, 50);
        Location g2 = new Location(world, 82, 0, -46);

        //Create region that allows building within limits.
        ProtectedRegion outside = new ProtectedCuboidRegion("outside",
                new BlockVector(g1.getX(), g1.getY(), g1.getZ()),
                new BlockVector(g2.getX(), g2.getY(), g2.getZ()));
        outside.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.FALL_DAMAGE, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.FEED_AMOUNT, 100);
        outside.setFlag(DefaultFlag.FEED_DELAY, 1);
        outside.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
        outside.setFlag(DefaultFlag.INTERACT, StateFlag.State.ALLOW);
        outside.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.WEATHER_LOCK, WeatherType.CLEAR);
        outside.setFlag(DefaultFlag.TIME_LOCK, "6000");
        outside.setPriority(1);

        regions.addRegion(outside);

        int rank = RankupHandler.getInstance().getRank(p);
        if (settings.getPlayerData().getBoolean(p.getUniqueId().toString() + ".Ethereal")) {
            rank = 1000;
        }
        ResetHandler.resetMineFullWorldEdit(m, point2, point1, Blocks(rank / 16));
        settings.getPlayerData().set(p.getUniqueId().toString() + ".HasMine", true);
    }


	/*
		UPDATING THE MINE
	 */


}
