package me.dxrk.Mines;


import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;

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

    public void disable() {
    }

    public void createFolder() {
        File var1 = new File(Main.plugin.getDataFolder() + File.separator + "mines");
        if (!var1.exists()) {
            var1.mkdir();
        }
    }

    public void preLoadWorlds() {
        File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "mines")).listFiles();
        File[] var = mineFiles;
        assert mineFiles != null;
        int amountOfMines = mineFiles.length;
        for (int i = 0; i < amountOfMines; ++i) {
            File mineFile = var[i];
            String name = mineFile.getName().split("\\.")[0];
            if(Bukkit.getWorld(name) == null) {
                new WorldCreator(name).createWorld();
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
                if(Bukkit.getWorld(config.getString("mine_world")) == null) {
                    new WorldCreator(config.getString("mine_world")).createWorld();
                }
                World mineWorld = Bukkit.getWorld(config.getString("mine_world"));
                Location minPoint = new Location(mineWorld, config.getInt("min_point.X"), config.getInt("min_point.Y"), config.getInt("min_point.Z"));
                Location maxPoint = new Location(mineWorld, config.getInt("max_point.X"), config.getInt("max_point.Y"), config.getInt("max_point.Z"));
                ItemStack block1 = config.getItemStack("first_block");
                ItemStack block2 = config.getItemStack("second_block");
                ItemStack block3 = config.getItemStack("third_block");
                Location spawnLoc = new Location(mineWorld, config.getInt("spawn_loc.X"), config.getInt("spawn_loc.Y"), config.getInt("spawn_loc.Z"));
                float reset = config.getFloat("reset_percentage");
                Mine mine = new Mine(mineName, minPoint, maxPoint, block1, block2, block3, spawnLoc, mineWorld, reset);
                MineSystem.getInstance().addActiveMine(mine);
            } catch (Exception e) {
                System.out.println(" ");
                System.out.println("Unable to load the mine file " + mineFile.getName() + ", Please delete this file at location: " + mineFile.getAbsolutePath());
                System.out.println("Stack trace:");
                System.out.println(e.getMessage());
                System.out.println(" ");
            }
        }

        this.areMinesLoaded = true;
    }



    public boolean isMinesLoaded() {
        return this.areMinesLoaded;
    }
}
