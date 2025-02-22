/*
package me.dxrk.utils;

import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import me.dxrk.utils.BlockChanger.WorkloadRunnable;
import me.dxrk.utils.BlockChanger.SectionSetWorkload;

import static me.dxrk.utils.BlockChanger.*;

public class DynamicCuboidSetter {

    */
/**
     * BlockChance remains static as it's just a data structure with no
     * instance-specific behavior. It defines the properties for each block type's
     * distribution pattern.
     *//*

    public static record BlockChance(
            ItemStack itemStack,
            double startWeight,    // Weight at level 1
            double peakWeight,     // Maximum weight at peak level
            double endWeight,      // Weight at level 100
            double peakLevel,      // Level at which weight reaches its peak
            double allowedDepthLevel1,
            double allowedDepthLevel100,
            boolean isParabolic    // Whether to use parabolic or linear distribution
    ) {
        // Add validation in the record's canonical constructor
        public BlockChance {
            if (itemStack == null) {
                throw new IllegalArgumentException("ItemStack cannot be null");
            }
            if (peakWeight < 0 || startWeight < 0 || endWeight < 0) {
                throw new IllegalArgumentException("Weights cannot be negative");
            }
        }
    }

    */
/**
     * Now an instance method that could potentially be overridden or customized
     * in subclasses. This allows for different ore distributions in different
     * contexts if needed.
     *//*

    protected static List<BlockChance> zoneChances() {
        List<BlockChance> chances = new ArrayList<>();

        // Cobblestone: Gradual linear decrease
        chances.add(new BlockChance(
                new ItemStack(Material.COBBLESTONE),
                95, 0, 10,     // Start very high, end at 10% to maintain some presence
                1,             // Peak level (unused)
                1.0, 1.0,      // Allowed anywhere
                false          // Linear distribution
        ));

        // Coal Ore: Early game resource
        chances.add(new BlockChance(
                new ItemStack(Material.COAL_ORE),
                5, 40, 5,      // Start at 5%, peak at 40%, end at 5%
                15,            // Peaks at level 15
                0.7, 1.0,      // Depth restrictions
                true           // Parabolic distribution
        ));

        // Copper Ore: Early-mid game resource
        chances.add(new BlockChance(
                new ItemStack(Material.COPPER_ORE),
                0, 35, 5,      // Peak slightly lower than coal
                30,            // Peaks at level 30
                0.6, 1.0,      // Depth restrictions
                true
        ));

        // Iron Ore: Mid game resource
        chances.add(new BlockChance(
                new ItemStack(Material.IRON_ORE),
                0, 35, 5,      // Similar peak to copper
                45,            // Peaks at level 45
                0.5, 1.0,      // Deeper restrictions
                true
        ));

        // Lapis Ore: Mid-late game resource
        chances.add(new BlockChance(
                new ItemStack(Material.LAPIS_ORE),
                0, 30, 5,      // Slightly lower peak
                60,            // Peaks at level 60
                0.4, 1.0,      // Even deeper restrictions
                true
        ));

        // Gold Ore: Late game resource
        chances.add(new BlockChance(
                new ItemStack(Material.GOLD_ORE),
                0, 30, 5,      // Similar peak to lapis
                70,            // Peaks at level 70
                0.3, 1.0,      // Deep restrictions
                true
        ));

        // Redstone Ore: Late game resource
        chances.add(new BlockChance(
                new ItemStack(Material.REDSTONE_ORE),
                0, 25, 5,      // Slightly lower peak
                80,            // Peaks at level 80
                0.3, 1.0,      // Deep restrictions
                true
        ));

        // Diamond Ore: End game resource
        chances.add(new BlockChance(
                new ItemStack(Material.DIAMOND_ORE),
                0, 25, 5,      // Similar peak to redstone
                90,            // Peaks at level 90
                0.2, 1.0,      // Very deep restrictions
                true
        ));

        // Emerald Ore: Progressive increase to end game
        chances.add(new BlockChance(
                new ItemStack(Material.EMERALD_ORE),
                0, 0, 50,      // Linear progression to 50%
                100,           // Peak level (unused for linear)
                0.1, 1.0,      // Deepest restrictions
                false          // Linear distribution
        ));

        // ... rest of the ore definitions remain the same ...

        return chances;
    }

    */
/**
     * Helper method for calculating weights. Made private since it's an internal
     * implementation detail. Could be protected if subclasses need to use it.
     *//*

    private static double calculateWeight(BlockChance block, int level) {
        if (!block.isParabolic) {
            // Linear interpolation for non-parabolic blocks (like emerald)
            double t = (level - 1) / 99.0;
            return block.startWeight() + (block.endWeight() - block.startWeight()) * t;
        }

        // Parabolic distribution calculation
        double x = (level - block.peakLevel()) / (block.peakLevel() - 1);
        double a = block.peakWeight();
        double b = block.startWeight();
        double c = block.endWeight();

        // Quadratic function that passes through (0,b), (1,a), and (2,c)
        double p = (a - 2 * b + c) / 2;
        double q = (-3 * a + 4 * b - c) / 2;
        double r = a;

        return Math.max(0, p * x * x + q * x + r);
    }

    */
/**
     * Instance method for block selection, uses the instance's zone1Chances
     * and calculateWeight methods.
     *//*

    private static ItemStack getRandomBlockForLevelAndDepth(int level, double currentRelY, List<BlockChance> blockChances) {
        test();
        level = Math.max(1, Math.min(100, level));
        double totalWeight = 0;
        double[] effectiveWeights = new double[blockChances.size()];

        for (int i = 0; i < blockChances.size(); i++) {
            BlockChance bc = blockChances.get(i);
            double weight = calculateWeight(bc, level);

            double allowedDepth = bc.allowedDepthLevel1() +
                    (bc.allowedDepthLevel100() - bc.allowedDepthLevel1()) * ((level - 1) / 99.0);

            if (currentRelY <= allowedDepth) {
                effectiveWeights[i] = weight;
            } else {
                effectiveWeights[i] = 0;
            }
            totalWeight += effectiveWeights[i];
        }

        if (totalWeight <= 0) {
            return blockChances.get(0).itemStack();
        }

        double random = Math.random() * totalWeight;
        for (int i = 0; i < effectiveWeights.length; i++) {
            if (random < effectiveWeights[i]) {
                return blockChances.get(i).itemStack();
            }
            random -= effectiveWeights[i];
        }
        return blockChances.get(blockChances.size() - 1).itemStack();
    }

    */
/**
     * An asynchronous cuboid setter that uses dynamic block selection based on both
     * the player's level and the current block's vertical position.
     *
     * The cuboid is defined by two locations, where loc1 is the bottom corner and loc2 is the top corner.
     *
     * @param loc1    one corner of the cuboid (the bottom corner)
     * @param loc2    the opposite corner (the top corner)
     * @param level   the player's level (1–100)
     * @param physics whether physics should be applied when setting blocks
     * @return a CompletableFuture that completes when the cuboid has been processed
     *//*

    public static CompletableFuture<Void> setDynamicCuboidAsynchronously(Location loc1, Location loc2, int level, boolean physics) {
        List<BlockChance> blockChances = zoneChances();
        World world = loc1.getWorld();
        Object nmsWorld = getWorld(world);

        int x1 = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int y1 = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int z1 = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int x2 = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int y2 = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int z2 = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        int sizeX = Math.abs(x2 - x1) + 1;
        int sizeY = Math.abs(y2 - y1) + 1;
        int sizeZ = Math.abs(z2 - z1) + 1;
        int cuboidSize = sizeX * sizeY * sizeZ;

        CompletableFuture<Void> workloadFinishFuture = new CompletableFuture<>();
        WorkloadRunnable workloadRunnable = new WorkloadRunnable();
        BukkitTask workloadTask = Bukkit.getScheduler().runTaskTimer(Main.plugin, workloadRunnable, 1, 1);

        int x = 0, y = 0, z = 0;
        Location location = new Location(world, x1, y1, z1);
        Object blockPosition = newMutableBlockPosition(location);

        double height = y2 - y1;
        if (height <= 0) height = 1;

        for (int i = 0; i < cuboidSize; i++) {
            double currentRelY = (location.getY() - y1) / height;
            ItemStack chosenStack = getRandomBlockForLevelAndDepth(level, currentRelY, blockChances);
            Object blockData = getBlockData(chosenStack);

            SectionSetWorkload workload = new SectionSetWorkload(nmsWorld, blockPosition, blockData, location.clone(), physics);
            workloadRunnable.addWorkload(workload);

            if (++x >= sizeX) {
                x = 0;
                if (++y >= sizeY) {
                    y = 0;
                    ++z;
                }
            }
            location.setX(x1 + x);
            location.setY(y1 + y);
            location.setZ(z1 + z);
        }

        workloadRunnable.whenComplete(() -> {
            workloadFinishFuture.complete(null);
            workloadTask.cancel();
        });
        return workloadFinishFuture;
    }
}

*/
