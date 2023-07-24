package me.dxrk.utils;

import me.dxrk.Main.Main;
import me.dxrk.Mines.MineSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class WaveEffect {
    Runnable timer = new Runnable() {
        @SuppressWarnings("deprecation")
        public void run() {
            for (Location loc : getCircle(l, rad, (rad * ((int) (Math.PI * 2))))) {
                Block b = loc.getBlock();
                if (b.getType().equals(Material.BEDROCK) || !MineSystem.getInstance().getMineByPlayer(player).isLocationInMine(b.getLocation()))
                    continue;
                FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getType(), loc.getBlock().getData());
                fb.setVelocity(new Vector(0, .3, 0));
                loc.getBlock().setType(Material.AIR);
                fb.setDropItem(false);
                fb.remove();
            }
            rad++;
            rad = (((rad % time) == 0) ? 1 : rad);
        }
    };

    private Location l;
    private int rad = 1;
    private int id;
    private int time;
    private Player player;

    public WaveEffect(Location l, int t, Player p) {
        this.l = l;
        this.time = t;
        this.player = p;
        start(2);
    }

    /**
     * Return A List Of Locations That
     * Make Up A Circle Using A Provided
     * Center, Radius, And Desired Points.
     *
     * @param center
     * @param radius
     * @param amount
     * @return
     */
    private ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = ((2 * Math.PI) / amount);
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    public static ArrayList<Location> getCircle1(Location loc, int radius) {
        ArrayList<Location> blocks = new ArrayList<>();
        for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; x++) {
            for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; z++) {
                Location l = new Location(loc.getWorld(), x, loc.getY(), z);
                if (l.distance(loc) <= radius && !l.getBlock().getType().equals(Material.AIR))
                    blocks.add(l);
            }

        }
        return blocks;
    }

    /**
     * Starts The Timer
     *
     * @param delay
     */
    private void start(int delay) {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), timer, delay, delay);
    }

    /**
     * Stops The Timer
     */
    public void stop() {
        Bukkit.getScheduler().cancelTask(id);
    }
}

