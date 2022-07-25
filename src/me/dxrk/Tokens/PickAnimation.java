package me.dxrk.Tokens;

import com.google.common.collect.Lists;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;
import org.bukkit.util.Vector;

import java.util.List;

public class PickAnimation {
    // Config variables
    private static final float SEPARATOR = 2;
    private static final float RAD_PER_SEC = 4.0F;
    private static final float RAD_PER_TICK = RAD_PER_SEC / 20F;

    private Location center;
    private double radius;

    private List<ArmorStand> picks;

    public PickAnimation(Location center, double radius) {
        this.center = center;
        this.radius = radius;
        picks = Lists.newArrayList();
    }
    
    public void removeStands() {
    	for (int i = 0; i < picks.size(); i++) {
            ArmorStand stand = picks.get(i);
            stand.remove();
    	}
    }
    
    public BukkitTask task;

    public void start(JavaPlugin plugin) {
        for (double angle = 0; angle < Math.PI * 2; angle += SEPARATOR) {
            spawnStand(angle);
        }

       task = new BukkitRunnable(){
            int tick = 0;

            public void run() {
                ++tick;

                for (int i = 0; i < picks.size(); i++) {
                    ArmorStand stand = picks.get(i);
                    Location loc = getLocationInCircle(RAD_PER_TICK * tick + SEPARATOR * i);

                    // Entity falling bug
                    stand.setVelocity(new Vector(1, 0, 0));

                    stand.teleport(loc);
                }
            }
        }.runTaskTimer(plugin, 0L, 1L);
        
    }

    private void spawnStand(double angle) {
        Location loc = getLocationInCircle(angle);

        ArmorStand stand = Bukkit.getWorld("world_the_end").spawn(loc, ArmorStand.class);

        // TODO Customize by hiding, adding items, rotation...
        
        ItemStack combined = new ItemStack(Material.DIAMOND_PICKAXE);
    	ItemMeta cm = combined.getItemMeta();
    	cm.addEnchant(Enchantment.DIG_SPEED, 32000, true);
	    cm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
	    combined.setItemMeta(cm);
        
        stand.setSmall(true);
        stand.setVisible(false);
        stand.setGravity(false);
        stand.setArms(true);
        stand.setItemInHand(new ItemStack(combined));
        EulerAngle a = new EulerAngle(-45f, 0f, 0f);
        stand.setRightArmPose(a);

        picks.add(stand);
        picks.add(stand);
        picks.add(stand);
        picks.add(stand);
    }

    private Location getLocationInCircle(double angle) {
        double x = center.getX() + radius * Math.cos(angle);
        double z = center.getZ() + radius * Math.sin(angle);

        return new Location(Bukkit.getWorld("world_the_end"), x, center.getY(), z);
    }
}
