package me.dxrk.Mines;

import me.dxrk.Main.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Mine {
    private String mineName;
    private Location corner1;
    private Location corner2;
    private ItemStack block1;
    private ItemStack block2;
    private ItemStack block3;
    private Location spawnLocation;
    private World mineWorld;

    private float resetpercent;

    private int TotalBlocks;
    private int Blocks;


    //move this method to CreateMine
   /* public void createMine(String name, Location corner1, Location corner2, Location spawn, World world) throws IOException {
        File mine = new File(Main.plugin.getDataFolder()+File.separator+"mines", name+".yml");
        FileConfiguration minefile = YamlConfiguration.loadConfiguration(mine);
        minefile.set("mine_name", name);
        minefile.set("mine_world", world.getName());
        minefile.set("min_point", corner1);
        minefile.set("max_point", corner2);
        minefile.set("first_block", new ItemStack(Material.COBBLESTONE));
        minefile.set("second_block", new ItemStack(Material.COBBLESTONE));
        minefile.set("third_block", new ItemStack(Material.COBBLESTONE));
        minefile.set("spawn_loc", spawn);
        minefile.save(mine);
        Mine m = new Mine(name, corner1, corner2, new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), new ItemStack(Material.COBBLESTONE), spawn, world);
        this.minesystem.addActiveMine(m);
    }*/

    public Mine(String name, Location c1, Location c2, ItemStack b1, ItemStack b2, ItemStack b3, Location spawn, World world, float reset) {
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

    public String getMineName() {return this.mineName;}

    public Location getSpawnLocation() {return this.spawnLocation;}

    public World getMineWorld() {return this.mineWorld;}

    public Location getMinPoint() {return this.corner1;}
    public Location getMaxPoint() {return this.corner2;}

    public float getResetPercent() {return this.resetpercent;}
    public void setResetPercent(float reset) {this.resetpercent = reset;}

    public boolean isInMine(Location l) {
        return getLocationsInMine(this.corner1, this.corner2, this.mineWorld).contains(l);
    }

    public int getTotalBlocks() {
        return getLocationsInMine(this.corner1, this.corner2, this.mineWorld).size();
    }

    public int getBlocksLeft() {
        return getLocationsInMineNotAir(this.corner1, this.corner2, this.mineWorld).size();
    }
    public int getBlocksMined() {
        return getLocationsInMineAir(this.corner1, this.corner2, this.mineWorld).size();
    }

    public float getBlocksLeftPercentage() {
        int var1 = this.getBlocksMined();
        int var2 = this.getTotalBlocks();
        return var1 == 0 ? 100.0F : 100.0F - (float)var1 / (float)var2 * 100.0F;
    }

    public ItemStack getBlock1() {return this.block1;}
    public ItemStack getBlock2() {return this.block2;}
    public ItemStack getBlock3() {return this.block3;}
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
            config.set("reset_percentage", this.getResetPercent());
            config.save(mineFile);
        } catch(Exception e) {
            System.out.println("ERROR SAVING MINE: "+getMineName());
            System.out.println("ERROR SAVING MINE: "+getMineName());
            System.out.println("ERROR SAVING MINE: "+getMineName());
            System.out.println(e.getMessage());
        }
    }


    private List<Location> getLocationsInMine(Location loc1, Location loc2, World w) {
        List<Location> blocks = new ArrayList<>();

        int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
                miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
                minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
                maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
                maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
                maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                for (int z = minz; z <= maxz; z++) {
                    Location b = new Location(w, x, y, z);
                    blocks.add(b);
                }
            }
        }

        return blocks;
    }
    private List<Location> getLocationsInMineNotAir(Location loc1, Location loc2, World w) {
        List<Location> blocks = new ArrayList<>();

        int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
                miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
                minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
                maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
                maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
                maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                for (int z = minz; z <= maxz; z++) {
                    Location b = new Location(w, x, y, z);
                    if(!w.getBlockAt(b).getType().equals(Material.AIR))
                        blocks.add(b);
                }
            }
        }

        return blocks;
    }
    private List<Location> getLocationsInMineAir(Location loc1, Location loc2, World w) {
        List<Location> blocks = new ArrayList<>();

        int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
                miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
                minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
                maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
                maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
                maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                for (int z = minz; z <= maxz; z++) {
                    Location b = new Location(w, x, y, z);
                    if(w.getBlockAt(b).getType().equals(Material.AIR))
                        blocks.add(b);
                }
            }
        }

        return blocks;
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
