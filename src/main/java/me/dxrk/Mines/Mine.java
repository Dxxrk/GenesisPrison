package me.dxrk.Mines;

import me.dxrk.Main.Main;
import me.dxrk.utils.BlockChanger;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Mine {
    private String mineName;
    private Location corner1;
    private Location corner2;
    private ItemStack block1;
    private ItemStack block2;
    private ItemStack block3;
    private Location spawnLocation;
    private World mineWorld;
    private Map<ItemStack, Integer> chances;
    private Map<ItemStack, Double> depth;

    private double resetpercent;

    private int TotalBlocks;
    private int Blocks;


    public Mine(String name, Location c1, Location c2, Location spawn, World world, double reset, ItemStack... items) {
        Map<ItemStack>
        for(ItemStack i : items) {

        }

        this.mineName = name;
        this.corner1 = c1;
        this.corner2 = c2;
        this.block1 = b1;
        this.block2 = b2;
        this.block3 = b3;
        this.spawnLocation = spawn;
        this.mineWorld = world;
        this.resetpercent = reset;


    }

    public boolean isLocationInMine(Location paramLocation) {
        if (paramLocation == null)
            return false;
        if (paramLocation.getWorld() == null)
            return false;
        if (getMineWorld() == null)
            return false;
        if (getMineWorld()
                .equals(paramLocation.getWorld()) &&
                paramLocation.getBlockY() >= this.getMinPoint().getBlockY() && paramLocation.getBlockY() <= this.getMaxPoint().getBlockY())
            return paramLocation.getBlockX() >= this.getMinPoint().getBlockX() && paramLocation.getBlockX() <= this.getMaxPoint().getBlockX() &&
                    paramLocation.getBlockZ() >= this.getMinPoint().getBlockZ() && paramLocation.getBlockZ() <= this.getMaxPoint().getBlockZ();
        return false;
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


    public int getTotalBlocks() {
        int b = 0;
        Location location1 = this.corner1;
        Location location2 = this.corner2;
        for (int i = location1.getBlockX(); i <= location2.getBlockX(); i++) {
            for (int j = location1.getBlockZ(); j <= location2.getBlockZ(); j++) {
                for (int k = location1.getBlockY(); k <= location2.getY(); k++)
                    b++;
            }
        }
        return b;
    }

    public int getBlocksMined() {
        int b = 0;
        Location location1 = this.corner1;
        Location location2 = this.corner2;
        for (int i = location1.getBlockX(); i <= location2.getBlockX(); i++) {
            for (int j = location1.getBlockZ(); j <= location2.getBlockZ(); j++) {
                for (int k = location1.getBlockY(); k <= location2.getY(); k++) {
                    if (location1.getWorld().getBlockAt(i, k, j).getType() == Material.AIR)
                        b++;
                }
            }
        }
        return b;
    }

    public float getBlocksLeftPercentage() {
        int i = this.getBlocksMined();
        int j = this.getTotalBlocks();
        if (i == 0)
            return 100.0F;
        return 100.0F - (float) i / j * 100.0F;
    }

    public ItemStack getBlock1() {
        return this.block1;
    }

    public ItemStack getBlock2() {
        return this.block2;
    }

    public ItemStack getBlock3() {
        return this.block3;
    }

    public Map<ItemStack, Integer> getChances(){return this.chances;}

    public Map<ItemStack, Double> getDepth(){return this.depth;}

    public void setBlock1(ItemStack i) {
        this.block1 = i;
    }

    public void setBlock2(ItemStack i) {
        this.block2 = i;
    }

    public void setBlock3(ItemStack i) {
        this.block3 = i;
    }

    public void save() {
        try {
            File mineFile = new File(Main.plugin.getDataFolder() + File.separator + "mines", this.getMineName() + ".yml");
            if (!mineFile.exists()) {
                mineFile.createNewFile();
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(mineFile);

            config.set("mine_name", this.getMineName());
            config.set("mine_world", this.getMineWorld().getName());
            config.set("min_point.X", this.getMinPoint().getX());
            config.set("min_point.Y", this.getMinPoint().getY());
            config.set("min_point.Z", this.getMinPoint().getZ());
            config.set("max_point.X", this.getMaxPoint().getX());
            config.set("max_point.Y", this.getMaxPoint().getY());
            config.set("max_point.Z", this.getMaxPoint().getZ());
            config.set("first_block", this.getBlock1());
            config.set("second_block", this.getBlock2());
            config.set("third_block", this.getBlock3());
            config.set("spawn_loc.X", this.getSpawnLocation().getX());
            config.set("spawn_loc.Y", this.getSpawnLocation().getY());
            config.set("spawn_loc.Z", this.getSpawnLocation().getZ());
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

        BlockChanger.setSectionCuboidAsynchronously(this.getMinPoint(), this.getMaxPoint(), false, this.getBlock1(), this.getBlock2(), this.getBlock3());
        /*Bukkit.getScheduler().runTaskAsynchronously(Main.plugin,
                () -> {
                    BlockChanger.setCuboidAsynchronously(this.getMinPoint(), this.getMaxPoint(), this.getBlock1(), this.getBlock2(), this.getBlock3(), false);
                });*/
    }

    public void expandMine(int i) {
        this.setMinPoint(-i, -i, -i);
        this.setMaxPoint(i, 0, i);
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin,
                () -> {
                    Location floor1 = new Location(this.getMineWorld(), this.getMinPoint().getX() - i, this.getMinPoint().getY() - i, this.getMinPoint().getZ() - i);
                    Location floor2 = new Location(this.getMineWorld(), this.getMaxPoint().getX() + i, this.getMinPoint().getY() - i, this.getMaxPoint().getZ() + i);
                    BlockChanger.setSectionCuboidAsynchronously(floor1, floor2, new ItemStack(Material.BEDROCK), false);

                    Location wallone1 = new Location(this.getMineWorld(), this.getMinPoint().getX() - i, this.getMinPoint().getY() - i, this.getMinPoint().getZ() - i);
                    Location wallone2 = new Location(this.getMineWorld(), this.getMinPoint().getX() - i, this.getMaxPoint().getY(), this.getMaxPoint().getZ() + i); // SOUTH WALL
                    BlockChanger.setSectionCuboidAsynchronously(wallone1, wallone2, new ItemStack(Material.BEDROCK), false);

                    Location walltwo1 = new Location(this.getMineWorld(), this.getMinPoint().getX() - i, this.getMinPoint().getY() - i, this.getMinPoint().getZ() - i);
                    Location walltwo2 = new Location(this.getMineWorld(), this.getMaxPoint().getX() + i, this.getMaxPoint().getY(), this.getMinPoint().getZ() - i); // EAST WALL
                    BlockChanger.setSectionCuboidAsynchronously(walltwo1, walltwo2, new ItemStack(Material.BEDROCK), false);

                    Location wallthree1 = new Location(this.getMineWorld(), this.getMaxPoint().getX() + i, this.getMinPoint().getY() - i, this.getMinPoint().getZ() - i);
                    Location wallthree2 = new Location(this.getMineWorld(), this.getMaxPoint().getX() + i, this.getMaxPoint().getY(), this.getMaxPoint().getZ() + i); // NORTH WALL
                    BlockChanger.setSectionCuboidAsynchronously(wallthree1, wallthree2, new ItemStack(Material.BEDROCK), false);

                    Location wallfour1 = new Location(this.getMineWorld(), this.getMinPoint().getX() - i, this.getMinPoint().getY() - i, this.getMaxPoint().getZ() + i);
                    Location wallfour2 = new Location(this.getMineWorld(), this.getMaxPoint().getX() + i, this.getMaxPoint().getY(), this.getMaxPoint().getZ() + i); // WEST WALL
                    BlockChanger.setSectionCuboidAsynchronously(wallfour1, wallfour2, new ItemStack(Material.BEDROCK), false);
                });
        this.save();
        this.reset();
    }


    public boolean delete() {
        File file = new File(Main.plugin.getDataFolder() + File.separator + "mines", getMineName() + ".yml");
        if (file.exists()) {
            file.delete();
            return true;
        }
        return false;
    }

}
