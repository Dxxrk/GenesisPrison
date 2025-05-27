package me.dxrk.Mines;

import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.UUID;

public class Mine {
    private String mineName;
    private Location corner1;
    private Location corner2;
    private ItemStack block1;
    private ItemStack block2;
    private ItemStack block3;
    private Location spawnLocation;
    private World mineWorld;

    private double resetpercent;

    private int TotalBlocks;
    private int Blocks;


    public Mine(String name, Location c1, Location c2, Location spawn, World world, double reset) {
        this.mineName = name;
        this.corner1 = c1;
        this.corner2 = c2;
        this.spawnLocation = spawn;
        this.resetpercent = reset;
        this.mineWorld = world;
        this.Blocks = 0;
    }

    public boolean isLocationInMine(Location paramLocation) {
        // Check for null values first
        if (paramLocation == null)
            return false;
        if (paramLocation.getWorld() == null)
            return false;
        if (getMineWorld() == null)
            return false;

        // Check if worlds match
        if (!getMineWorld().equals(paramLocation.getWorld()))
            return false;

        // Check if all coordinates are within bounds
        return paramLocation.getBlockY() >= this.getMinPoint().getBlockY() &&
                paramLocation.getBlockY() <= this.getMaxPoint().getBlockY() &&
                paramLocation.getBlockX() >= this.getMinPoint().getBlockX() &&
                paramLocation.getBlockX() <= this.getMaxPoint().getBlockX() &&
                paramLocation.getBlockZ() >= this.getMinPoint().getBlockZ() &&
                paramLocation.getBlockZ() <= this.getMaxPoint().getBlockZ();
    }


    public String getMineName() {
        return this.mineName;
    }

    public Location getSpawnLocation() {
        return this.spawnLocation;
    }

    public World getMineWorld() {
        return this.mineWorld;
    }

    public Location getMinPoint() {
        return this.corner1;
    }

    public Location getMaxPoint() {
        return this.corner2;
    }

    public void setMinPoint(int x, int y, int z) {
        Location loc = this.getMinPoint().clone().add(x, y, z);
        this.corner1 = loc;
    }

    public void setMaxPoint(int x, int y, int z) {
        Location loc = this.getMaxPoint().clone().add(x, y, z);
        this.corner2 = loc;
    }

    public double getResetPercent() {
        return this.resetpercent;
    }

    public void setResetPercent(double reset) {
        this.resetpercent = reset;
    }

    //TODO Rework this to lower the count by 1 every time player breaks a block in the mine.

    public int getTotalBlocks() {
            return 112627;
    }

    public void mineBlock() {
        this.Blocks += 1;
    }

    public int getBlocksMined() { // If 27 blocks mined this == 112600
        if(getTotalBlocks() > 0) {
            return getTotalBlocks() - this.Blocks;
        }
        return 0;
    }

    public float getBlocksLeftPercentage() {
        int blocksMined = this.getBlocksMined();
        int totalBlocks = this.getTotalBlocks();

        if (totalBlocks == 0) {
            return 0.0F;
        }

        return 100.0F * (totalBlocks - blocksMined) / totalBlocks;
    }

    public void save() {
        try {
            File mineFile = new File(Main.plugin.getDataFolder() + File.separator + "mines", this.getMineName() + ".yml");
            if (!mineFile.exists()) {
                mineFile.createNewFile();
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(mineFile);

            config.set("mine_name", this.getMineName());
            config.set("min_point.X", this.getMinPoint().getX());
            config.set("min_point.Y", this.getMinPoint().getY());
            config.set("min_point.Z", this.getMinPoint().getZ());
            config.set("max_point.X", this.getMaxPoint().getX());
            config.set("max_point.Y", this.getMaxPoint().getY());
            config.set("max_point.Z", this.getMaxPoint().getZ());
            config.set("spawn_loc.X", this.getSpawnLocation().getX());
            config.set("spawn_loc.Y", this.getSpawnLocation().getY());
            config.set("spawn_loc.Z", this.getSpawnLocation().getZ());
            config.set("mine_world", this.getMineWorld().getName());
            config.set("reset", this.getResetPercent());
            config.save(mineFile);
        } catch (Exception e) {
            System.out.println("ERROR SAVING MINE: " + getMineName());
            System.out.println("ERROR SAVING MINE: " + getMineName());
            System.out.println("ERROR SAVING MINE: " + getMineName());
            System.out.println(e.getMessage());
        }
    }


    public void reset() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (this.isLocationInMine(p.getLocation())) {
                Location l = new Location(this.getMineWorld(), p.getLocation().getX(), this.getMaxPoint().getY()+2, p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
                p.teleport(l);
            }
        }
        UUID id = UUID.fromString(this.getMineName());
        Player p = Bukkit.getPlayer(id);

        Main.packetSender.sendDynamicAreaPacketsChunk(p, this.getMinPoint(), this.getMaxPoint(), 1, DynamicMultiBlockPacketSender.createDefaultBlockChances());
        this.Blocks = 0;
        //BlockChanger.setDynamicCuboidAsynchronously(this.getMinPoint(), this.getMaxPoint(), 1, false);
    }


    public void delete() {
        File file = new File(Main.plugin.getDataFolder() + File.separator + "mines", getMineName() + ".yml");
        if (file.exists()) {
            file.delete();
        }
    }

}
