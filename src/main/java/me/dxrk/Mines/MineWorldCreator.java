package me.dxrk.Mines;


import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import jdk.nashorn.internal.objects.Global;
import org.bukkit.*;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.Objects;


public class MineWorldCreator {

    static MineWorldCreator instance = new MineWorldCreator();

    public static MineWorldCreator getInstance() {
        return instance;
    }

    private World world;

    public void unloadWorld(World world) {
        this.world = Bukkit.getWorld("");
        if (!world.equals(null)) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    public boolean deleteWorld(File path) {
        if (path.exists()) {
            File files[] = path.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return (path.delete());
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (!(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public void createMineWorld(String name) {
        if (Bukkit.getWorld(name) != null) {
            return;
        }
        World mineWorld = Bukkit.createWorld((new WorldCreator(name))
                .type(WorldType.FLAT)
                .generator(new EmptyWorldGenerator()));
        mineWorld.setKeepSpawnInMemory(false);
        mineWorld.save();
        RegionManager regions = Objects.requireNonNull(getWorldGuard()).getRegionManager(mineWorld);
        GlobalProtectedRegion global = new GlobalProtectedRegion("__global__");
        global.setFlag(DefaultFlag.BLOCK_PLACE, StateFlag.State.ALLOW);
        global.setFlag(DefaultFlag.FALL_DAMAGE, StateFlag.State.DENY);
        global.setFlag(DefaultFlag.FEED_AMOUNT, 100);
        global.setFlag(DefaultFlag.FEED_DELAY, 1);
        global.setFlag(DefaultFlag.BLOCK_BREAK, StateFlag.State.ALLOW);
        global.setFlag(DefaultFlag.PVP, StateFlag.State.DENY);
        global.setFlag(DefaultFlag.LIGHTER, StateFlag.State.DENY);
        global.setFlag(DefaultFlag.USE, StateFlag.State.ALLOW);
        global.setFlag(DefaultFlag.INTERACT, StateFlag.State.DENY);
        global.setFlag(DefaultFlag.OTHER_EXPLOSION, StateFlag.State.DENY);
        global.setFlag(DefaultFlag.MOB_SPAWNING, StateFlag.State.DENY);
        global.setFlag(DefaultFlag.WEATHER_LOCK, WeatherType.CLEAR);
        global.setFlag(DefaultFlag.TIME_LOCK, "6000");
        global.setPriority(0);
        regions.addRegion(global);
    }

    public void pasteSchematic(Schematic schematic, Location location) {
        Clipboard clipboard = schematic.getClipboard();
        if (clipboard == null)
            throw new IllegalStateException("Schematic does not have a Clipboard! This should never happen!");
        EditSession session = (new EditSessionBuilder(FaweAPI.getWorld(location.getWorld().getName()))).fastmode(Boolean.TRUE).build();
        location.setY(clipboard.getOrigin().getBlockY());
        Vector centerVector = BukkitUtil.toVector(location);
        schematic.paste(session, centerVector, false, false, null);
        session.flushQueue();
        Region region = clipboard.getRegion();
        region.setWorld(FaweAPI.getWorld(location.getWorld().getName()));
        /*try {
            region.shift(centerVector.subtract(clipboard.getOrigin()));
        } catch (RegionOperationException e) {
            e.printStackTrace();
        }*/
    }


}
