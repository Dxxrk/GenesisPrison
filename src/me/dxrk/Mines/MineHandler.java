package me.dxrk.Mines;

import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.domains.DefaultDomain;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        mineblocks.add(new ItemStack(Material.BRICK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 15));
        mineblocks.add(new ItemStack(Material.COAL_ORE));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 14));
        mineblocks.add(new ItemStack(Material.COAL_BLOCK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 13));
        mineblocks.add(new ItemStack(Material.IRON_ORE));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 12));
        mineblocks.add(new ItemStack(Material.IRON_BLOCK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 11));
        mineblocks.add(new ItemStack(Material.GOLD_ORE));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 11));
        mineblocks.add(new ItemStack(Material.GOLD_BLOCK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 10));
        mineblocks.add(new ItemStack(Material.REDSTONE_ORE));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 9));
        mineblocks.add(new ItemStack(Material.REDSTONE_BLOCK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 8));
        mineblocks.add(new ItemStack(Material.LAPIS_ORE));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 7));
        mineblocks.add(new ItemStack(Material.LAPIS_BLOCK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 6));
        mineblocks.add(new ItemStack(Material.DIAMOND_ORE));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 5));
        mineblocks.add(new ItemStack(Material.DIAMOND_BLOCK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 4));
        mineblocks.add(new ItemStack(Material.EMERALD_ORE));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 3));
        mineblocks.add(new ItemStack(Material.EMERALD_BLOCK));
        mineblocks.add(new ItemStack(Material.STAINED_CLAY, 1, (short) 2));
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
        if (start >= 62) {
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
    public void updateMine(Player p, int prestige) {
        List<ItemStack> blocks = Blocks(prestige);
        Mine m = MineSystem.getInstance().getMineByPlayer(p);
        if (PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("CustomBlock") == null) {
            if (prestige == 0) {
                m.setBlock1(new ItemStack(Material.COBBLESTONE));
                m.setBlock2(new ItemStack(Material.COBBLESTONE));
                m.setBlock3(new ItemStack(Material.COBBLESTONE));
            } else {
                m.setBlock1(blocks.get(0));
                m.setBlock2(blocks.get(1));
                m.setBlock3(blocks.get(2));
            }
        } else {
            ItemStack block = PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("CustomBlock");
            m.setBlock1(block);
            m.setBlock2(block);
            m.setBlock3(block);
        }
        m.save();
        m.reset();

    }


    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmdd, String label, String[] args) {
        Player player = (Player) sender;

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

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) return;
        if (PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("HasMine")) {
            Mine m = MineSystem.getInstance().getMineByPlayer(p);
            Location loc = new Location(m.getMineWorld(), m.getSpawnLocation().getX(), m.getSpawnLocation().getY(), m.getSpawnLocation().getZ(), -90, 0);
            p.teleport(loc);
            new BukkitRunnable() {
                @Override
                public void run() {
                    int minesize = PlayerDataHandler.getInstance().getPlayerData(p).getInt("MineSize");
                    if (minesize == 1 || minesize == 2) {
                        Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 83, 34.5, m.getSpawnLocation().getZ());
                    }
                    if (minesize == 3) {
                        Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 103, 39.5, m.getSpawnLocation().getZ());
                    }
                }
            }.runTaskLater(Main.plugin, 20L);

        }
    }

    public void createMine(String name, Location corner1, Location corner2, Location spawn, World world, double percent) {
        Mine m = new Mine(name, corner1, corner2, new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), spawn, world, percent);
        m.save();
        MineSystem.getInstance().addActiveMine(m);
    }

    public void pasteSchematic(Schematic schematic, Location location) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin,
                () -> {
                    Clipboard clipboard = schematic.getClipboard();
                    if (clipboard == null)
                        throw new IllegalStateException("Schematic does not have a Clipboard! This should never happen!");
                    EditSession session = (new EditSessionBuilder(FaweAPI.getWorld(location.getWorld().getName()))).fastmode(Boolean.TRUE).build();
                    location.setY(clipboard.getOrigin().getBlockY());
                    Vector centerVector = BukkitUtil.toVector(location);
                    schematic.paste(session, centerVector, false, false, null);
                    session.flushQueue();
                    Region region = clipboard.getRegion();
                    region.setWorld(FaweAPI.getWorld(location.getWorld().getName()));
                });
    }

    /*
        CREATING THE MINE
     */
    @SuppressWarnings("deprecation")
    public void CreateMine(Player p, String schem, String mineworld) {
        //Setting up the world
        World world = Bukkit.getWorld(mineworld);
        int mines = settings.getOptions().getInt("numberofmines");

        File schematic = new File(Main.plugin.getDataFolder() + File.separator + "schematics", schem + ".schematic");
        Location paste = new Location(world, 0, 144, (mines * 250));
        paste.setYaw(-90);
        pasteSchematic(Objects.requireNonNull(WESchematic.getSchematic(schematic)), paste);
        Location pworld = null;
        if (schem.equals("firstmine")) {
            pworld = new Location(world, 0.5, 100.5, (mines * 250) + 0.5, -90, 0);
            PlayerDataHandler.getInstance().getPlayerData(p).set("MineSize", 1);
        }
        if (schem.equals("secondmine")) {
            pworld = new Location(world, 0.5, 125.5, (mines * 250) + 0.5, -90, 0);
            PlayerDataHandler.getInstance().getPlayerData(p).set("MineSize", 2);
        }
        if (schem.equals("thirdmine")) {
            pworld = new Location(world, 0.5, 125.5, (mines * 250) + 0.5, -90, 0);
            PlayerDataHandler.getInstance().getPlayerData(p).set("MineSize", 3);
        }
        Location finalPworld = pworld;
        Bukkit.getScheduler().runTaskLater(Main.plugin,
                () -> {
                    p.teleport(finalPworld);
                }, 40L);



        Location point1 = new Location(world, 15, 65, (mines * 250) - 16);
        Location point2 = new Location(world, 47, 98, (mines * 250) + 16);
        if (schem.equals("firstmine")) {
            Methods.getInstance().createWorldBorder(p, world, 83, 34.5, (mines * 250) + 0.5);
        }
        if (schem.equals("secondmine")) {
            //change for mine2's dimensions
            point1 = new Location(world, 9, 75, (mines * 250) - 23);
            point2 = new Location(world, 56, 123, (mines * 250) + 24);
            Methods.getInstance().createWorldBorder(p, world, 83, 34.5, (mines * 250) + 0.5);
        }
        if (schem.equals("thirdmine")) {
            //change for mine3's dimensions
            point1 = new Location(world, 9, 59, (mines * 250) - 31);
            point2 = new Location(world, 71, 123, (mines * 250) + 31);
            Methods.getInstance().createWorldBorder(p, world, 103, 39.5, (mines * 250) + 0.5);
        }

        createMine(p.getUniqueId().toString(), point1, point2, pworld, world, 25.0);
        Mine m = MineSystem.getInstance().getMineByPlayer(p);


        RegionManager regions = getWorldGuard().getRegionManager(world);
        if (regions.hasRegion(p.getName())) {
            regions.removeRegion(p.getName());
        }
        if (regions.hasRegion(p.getName() + "outside")) {
            regions.removeRegion(p.getName() + "outside");
        }
        //Create Mine region that allows the enchants to work only inside the mine
        ProtectedRegion region = new ProtectedCuboidRegion(p.getName(),
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

        Location g1 = new Location(world, -50, 255, (mines * 250) + 75);
        Location g2 = new Location(world, 100, 0, (mines * 250) - 75);

        //Create region that allows building within limits.
        ProtectedRegion outside = new ProtectedCuboidRegion(p.getName() + "outside",
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
        DefaultDomain omembers = outside.getMembers();
        omembers.addPlayer(p.getUniqueId());
        outside.setPriority(1);

        regions.addRegion(outside);

        m.reset();
        PlayerDataHandler.getInstance().getPlayerData(p).set("HasMine", true);
        settings.getOptions().set("numberofmines", (mines + 1));
        settings.saveOptions();
        PlayerDataHandler.getInstance().savePlayerData(p);
    }
}
