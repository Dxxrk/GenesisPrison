package me.dxrk.utils;

import java.util.ArrayList;

import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

public class WaveEffect {
    Runnable timer = new Runnable() {
        @SuppressWarnings("deprecation")
        public void run() {
            for (Location loc : getCircle(l, rad, (rad * ((int) (Math.PI * 2))))) {
                if(loc.getBlock().getType().equals(Material.BEDROCK)) continue;
                FallingBlock fb = loc.getWorld().spawnFallingBlock(loc, loc.getBlock().getType(), loc.getBlock().getData());
                fb.setVelocity(new Vector(0, .3, 0));
                loc.getBlock().setType(Material.AIR);
                fb.setDropItem(false);

            }
            rad++;
            rad = (((rad % 20) == 0) ? 1 : rad);
        }
    };

    private Location l;
    private int rad = 1;
    private int id;

    public WaveEffect(Location l) {
        this.l = l;
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

