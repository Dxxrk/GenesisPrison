package me.dxrk.Mines;


import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashSet;

public class Mines {

    static Mines instance = new Mines();

    public static Mines getInstance() {
        return instance;
    }

    private boolean areMinesLoaded = false;

    public Mines() {
    }

    public void enable() {
        this.areMinesLoaded = false;
        createFolder();
        loadMines();
    }

    public void createFolder() {
        File var1 = new File(Main.plugin.getDataFolder() + File.separator + "mines");
        if (!var1.exists()) {
            var1.mkdir();
        }
    }

    /**
     * Force loads all chunks within a cuboid defined by two corner points
     * @param minPoint The minimum corner of the cuboid
     * @param maxPoint The maximum corner of the cuboid
     */
    public void forceLoadChunksInCuboid(Location minPoint, Location maxPoint) {
        // Ensure both locations are in the same world
        if (!minPoint.getWorld().equals(maxPoint.getWorld())) {
            throw new IllegalArgumentException("Both locations must be in the same world!");
        }

        World world = minPoint.getWorld();

        // Get the actual min/max coordinates (in case they were passed in wrong order)
        int minX = Math.min(minPoint.getBlockX(), maxPoint.getBlockX());
        int maxX = Math.max(minPoint.getBlockX(), maxPoint.getBlockX());
        int minZ = Math.min(minPoint.getBlockZ(), maxPoint.getBlockZ());
        int maxZ = Math.max(minPoint.getBlockZ(), maxPoint.getBlockZ());

        // Convert block coordinates to chunk coordinates
        int minChunkX = minX >> 4; // Equivalent to minX / 16 but faster
        int maxChunkX = maxX >> 4;
        int minChunkZ = minZ >> 4;
        int maxChunkZ = maxZ >> 4;

        // Load all chunks in the range
        for (int chunkX = minChunkX; chunkX <= maxChunkX; chunkX++) {
            for (int chunkZ = minChunkZ; chunkZ <= maxChunkZ; chunkZ++) {
                world.setChunkForceLoaded(chunkX, chunkZ, true);
            }
        }
    }

    public void loadMines() {
        File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "mines")).listFiles();
        File[] var3 = mineFiles;
        assert mineFiles != null;
        int amountOfMines = mineFiles.length;
        for (int i = 0; i < amountOfMines; ++i) {
            File mineFile = var3[i];
            FileConfiguration config = YamlConfiguration.loadConfiguration(mineFile);

            try {
                String mineName = config.getString("mine_name");
                World mineWorld = Bukkit.getWorld(config.getString("mine_world"));
                Location minPoint = new Location(mineWorld, config.getDouble("min_point.X"), config.getDouble("min_point.Y"), config.getDouble("min_point.Z"));
                Location maxPoint = new Location(mineWorld, config.getDouble("max_point.X"), config.getDouble("max_point.Y"), config.getDouble("max_point.Z"));
                ItemStack block1 = config.getItemStack("first_block");
                ItemStack block2 = config.getItemStack("second_block");
                ItemStack block3 = config.getItemStack("third_block");
                Location spawnLoc = new Location(mineWorld, config.getDouble("spawn_loc.X"), config.getDouble("spawn_loc.Y"), config.getDouble("spawn_loc.Z"));
                double reset = config.getDouble("reset");
                Mine mine = new Mine(mineName, minPoint, maxPoint, spawnLoc, mineWorld, reset);
                MineSystem.getInstance().addActiveMine(mine);
            } catch (Exception e) {
                System.out.println(" ");
                System.out.println("Unable to load the mine file " + mineFile.getName() + ", Please delete this file at location: " + mineFile.getAbsolutePath());
                System.out.println("Stack trace:");
                System.out.println(e.getMessage());
                System.out.println(" ");
            }
        }
        World world = Bukkit.getWorld("MineWorld");

        Location point1 = new Location(world, -20, -63, -20);
        Location point2 = new Location(world, 20, 3, 20);
        forceLoadChunksInCuboid(point1, point2);
        this.areMinesLoaded = true;
    }


    public boolean isMinesLoaded() {
        return this.areMinesLoaded;
    }
}
