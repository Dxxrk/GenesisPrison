package me.dxrk.Mines;

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
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.File;

public class MineHandler implements Listener, CommandExecutor {

    public static MineHandler instance = new MineHandler();

    public static MineHandler getInstance() {
        return instance;
    }

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
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

    /*public static Map<Material, Float> MAT_HARDNESS = new HashMap<>();
    public static SortedMap<Material, Float> orderblocks = new TreeMap<>();

    private static void addHardness() {
        Arrays.stream(Material.values()).filter(Material::isBlock).filter(Material::isItem).forEach(MineHandler::h);
    }

    private static void h(Material mat) {
        MAT_HARDNESS.put(mat, mat.getHardness());
    }

    private static List<ItemStack> blocks() {
        addHardness();
        MAT_HARDNESS.get
    }*/


    private static List<Material> zone1() { // basic overworld
        List<Material> blocks = new ArrayList<>();
        blocks.add(Material.STONE);
        blocks.add(Material.)
    }


    private static List<ItemStack> mineBlocks() {
        List<ItemStack> mineblocks = new ArrayList<>();
        mineblocks.add(new ItemStack(Material.COBBLESTONE)); //0
        mineblocks.add(new ItemStack(Material.MOSSY_COBBLESTONE));
        mineblocks.add(new ItemStack(Material.STONE));
        mineblocks.add(new ItemStack(Material.GRANITE));
        mineblocks.add(new ItemStack(Material.POLISHED_GRANITE));
        mineblocks.add(new ItemStack(Material.DIORITE));
        mineblocks.add(new ItemStack(Material.POLISHED_DIORITE));
        mineblocks.add(new ItemStack(Material.ANDESITE));
        mineblocks.add(new ItemStack(Material.POLISHED_ANDESITE));
        mineblocks.add(new ItemStack(Material.STONE_BRICKS));
        mineblocks.add(new ItemStack(Material.MOSSY_STONE_BRICKS));
        mineblocks.add(new ItemStack(Material.CRACKED_STONE_BRICKS));
        mineblocks.add(new ItemStack(Material.CHISELED_STONE_BRICKS));
        mineblocks.add(new ItemStack(Material.STONE_BRICKS)); // replace with another block
        mineblocks.add(new ItemStack(Material.SANDSTONE));
        mineblocks.add(new ItemStack(Material.CHISELED_SANDSTONE));
        mineblocks.add(new ItemStack(Material.SMOOTH_SANDSTONE));
        mineblocks.add(new ItemStack(Material.RED_SANDSTONE));
        mineblocks.add(new ItemStack(Material.CHISELED_RED_SANDSTONE));
        mineblocks.add(new ItemStack(Material.SMOOTH_RED_SANDSTONE));
        mineblocks.add(new ItemStack(Material.TERRACOTTA));
        mineblocks.add(new ItemStack(Material.WHITE_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.ORANGE_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.BRICK));
        mineblocks.add(new ItemStack(Material.BLACK_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.COAL_ORE));
        mineblocks.add(new ItemStack(Material.RED_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.COAL_BLOCK));
        mineblocks.add(new ItemStack(Material.GREEN_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.IRON_ORE));
        mineblocks.add(new ItemStack(Material.BROWN_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.IRON_BLOCK));
        mineblocks.add(new ItemStack(Material.BLUE_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.GOLD_ORE));
        mineblocks.add(new ItemStack(Material.PURPLE_TERRACOTTA)); //replace with another block
        mineblocks.add(new ItemStack(Material.GOLD_BLOCK));
        mineblocks.add(new ItemStack(Material.PURPLE_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.REDSTONE_ORE));
        mineblocks.add(new ItemStack(Material.CYAN_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.REDSTONE_BLOCK));
        mineblocks.add(new ItemStack(Material.LIGHT_GRAY_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.LAPIS_ORE));
        mineblocks.add(new ItemStack(Material.GRAY_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.LAPIS_BLOCK));
        mineblocks.add(new ItemStack(Material.PINK_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.DIAMOND_ORE));
        mineblocks.add(new ItemStack(Material.LIME_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.DIAMOND_BLOCK));
        mineblocks.add(new ItemStack(Material.YELLOW_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.EMERALD_ORE));
        mineblocks.add(new ItemStack(Material.LIGHT_BLUE_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.EMERALD_BLOCK));
        mineblocks.add(new ItemStack(Material.MAGENTA_TERRACOTTA));
        mineblocks.add(new ItemStack(Material.NETHERRACK));
        mineblocks.add(new ItemStack(Material.NETHER_BRICK));
        mineblocks.add(new ItemStack(Material.NETHER_QUARTZ_ORE));
        mineblocks.add(new ItemStack(Material.QUARTZ_BLOCK));
        mineblocks.add(new ItemStack(Material.CHISELED_QUARTZ_BLOCK));
        mineblocks.add(new ItemStack(Material.QUARTZ_PILLAR));
        mineblocks.add(new ItemStack(Material.PRISMARINE));
        mineblocks.add(new ItemStack(Material.PRISMARINE_BRICKS));
        mineblocks.add(new ItemStack(Material.DARK_PRISMARINE));
        mineblocks.add(new ItemStack(Material.OBSIDIAN));
        mineblocks.add(new ItemStack(Material.END_STONE));
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
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        if ("updatemine".equalsIgnoreCase(label)) {
            if (args.length == 1) {
                Player p = Bukkit.getPlayer(args[0]);
                updateMine(p, RankupHandler.instance.getRank(p));
            }
        }
        return false;
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_PICKAXE) || e.getItemDrop().getItemStack().getType().equals(Material.WOODEN_PICKAXE) ||
                e.getItemDrop().getItemStack().getType().equals(Material.STONE_PICKAXE) || e.getItemDrop().getItemStack().getType().equals(Material.IRON_PICKAXE) ||
                e.getItemDrop().getItemStack().getType().equals(Material.GOLDEN_PICKAXE)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(c("&cYou cannot drop your pickaxe!"));
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity ent = event.getEntity();
        if (ent instanceof Player player) {
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
                    Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 119, 0.5, m.getSpawnLocation().getZ());
                }
            }.runTaskLater(Main.plugin, 20L);

        }
    }

    public void createMine(String name, Location corner1, Location corner2, Location spawn, World world, double percent) {
        Mine m = new Mine(name, corner1, corner2, new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), spawn, world, percent);
        m.save();
        MineSystem.getInstance().addActiveMine(m);
    }


    /*
        CREATING THE MINE
     */
    @SuppressWarnings("deprecation")
    public void CreateMine(Player p, String schem, String mineworld) {
        //Setting up the world
        World world = Bukkit.getWorld("mines");
        int mines = settings.getOptions().getInt("numberofmines");

        File schematic = new File(Main.plugin.getDataFolder() + File.separator + "schematics", schem + ".schematic");
        Location paste = new Location(world, 0, 144, (mines * 500));
        paste.setYaw(-90);
        Methods.getInstance().pasteSchematic(schematic, paste);
        Location pworld = new Location(world, 0.5, 144.5, (mines * 500) + 0.5, -90, 0);
        PlayerDataHandler.getInstance().getPlayerData(p).set("MineSize", 30);

        Location finalPworld = pworld;

        Location point1 = new Location(world, -15, 114, (mines * 500) - 14);
        Location point2 = new Location(world, 14, 143, (mines * 500) + 15);


        createMine(p.getUniqueId().toString(), point1, point2, pworld, world, 25.0);
        Mine m = MineSystem.getInstance().getMineByPlayer(p);
        p.sendMessage(Methods.getInstance().c("&f&lMine &8| &bLoading Mine.."));
        Bukkit.getScheduler().runTaskLater(Main.plugin,
                () -> {
                    p.teleport(finalPworld);
                    Methods.getInstance().createWorldBorder(p, world, 119, 0.5, (mines * 500) + 0.5);
                    m.reset();
                }, 50L);
        /*RegionManager regions = getWorldGuard().getRegionManager(world);
        if (regions.hasRegion(p.getName())) {
            regions.removeRegion(p.getName());
        }
        if (regions.hasRegion(p.getName() + "outside")) {
            regions.removeRegion(p.getName() + "outside");
        }
        //Create Mine region that allows the enchants to work only inside the mine
        ProtectedCuboidRegion region = new ProtectedCuboidRegion(p.getUniqueId().toString(),
                new BlockVector(point1.getX(), point1.getY(), point1.getZ()),
                new BlockVector(point2.getX(), point2.getY(), point2.getZ()));
        region.setFlag(DefaultFlag.LIGHTER, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
        region.setFlag(DefaultFlag.INTERACT, StateFlag.State.DENY);
        DefaultDomain members = region.getMembers();
        members.addPlayer(p.getUniqueId());
        region.setPriority(2);
        regions.addRegion(region);

        Location g1 = new Location(world, -50, 255, (mines * 500) + 75);
        Location g2 = new Location(world, 100, 0, (mines * 500) - 75);

        //Create region that allows building within limits.
        ProtectedCuboidRegion outside = new ProtectedCuboidRegion(p.getUniqueId() + "outside",
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
        outside.setFlag(DefaultFlag.INTERACT, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
        outside.setFlag(DefaultFlag.WEATHER_LOCK, WeatherType.CLEAR);
        outside.setFlag(DefaultFlag.TIME_LOCK, "6000");
        DefaultDomain omembers = outside.getMembers();
        omembers.addPlayer(p.getUniqueId());
        outside.setPriority(1);

        regions.addRegion(outside);*/


        PlayerDataHandler.getInstance().getPlayerData(p).set("HasMine", true);
        settings.getOptions().set("numberofmines", (mines + 1));
        settings.saveOptions();
        PlayerDataHandler.getInstance().savePlayerData(p);
    }
}
