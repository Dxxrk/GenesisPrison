package me.dxrk.Mines;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.weather.WeatherType;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.GlobalProtectedRegion;
import com.sk89q.worldguard.protection.regions.RegionContainer;
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


    public void createMineWorld(String name) {
        if (Bukkit.getWorld(name) != null) {
            return;
        }
        World mineWorld = Bukkit.createWorld((new WorldCreator(name))
                .type(WorldType.FLAT)
                .generator(new EmptyWorldGenerator()));
        mineWorld.setKeepSpawnInMemory(false);
        mineWorld.save();
        RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
        RegionManager regions = container.get(BukkitAdapter.adapt(mineWorld));
        GlobalProtectedRegion global = new GlobalProtectedRegion("__global__");
        global.setFlag(Flags.BLOCK_PLACE, StateFlag.State.ALLOW);
        global.setFlag(Flags.FALL_DAMAGE, StateFlag.State.DENY);
        global.setFlag(Flags.FEED_AMOUNT, 100);
        global.setFlag(Flags.FEED_DELAY, 1);
        global.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
        global.setFlag(Flags.PVP, StateFlag.State.DENY);
        global.setFlag(Flags.LIGHTER, StateFlag.State.DENY);
        global.setFlag(Flags.USE, StateFlag.State.ALLOW);
        global.setFlag(Flags.INTERACT, StateFlag.State.DENY);
        global.setFlag(Flags.OTHER_EXPLOSION, StateFlag.State.DENY);
        global.setFlag(Flags.MOB_SPAWNING, StateFlag.State.DENY);
        global.setFlag(Flags.WEATHER_LOCK, WeatherType.REGISTRY.get("clear"));
        global.setFlag(Flags.TIME_LOCK, "6000");
        global.setPriority(0);
        regions.addRegion(global);
    }




}
