package me.dxrk.Mines;


import com.boydti.fawe.FaweAPI;
import com.boydti.fawe.object.schematic.Schematic;
import com.boydti.fawe.util.EditSessionBuilder;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitUtil;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.regions.RegionOperationException;
import me.dxrk.Main.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;

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
        if(!world.equals(null)) {
            Bukkit.getServer().unloadWorld(world, true);
        }
    }

    public boolean deleteWorld(File path) {
        if(path.exists()) {
            File files[] = path.listFiles();
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteWorld(files[i]);
                } else {
                    files[i].delete();
                }
            }
        }
        return(path.delete());
    }

    public void createMineWorld1(Player p) {
        if(Bukkit.getWorld(p.getUniqueId().toString()) != null) {
            World world = Bukkit.getWorld(p.getUniqueId().toString());
            unloadWorld(world);
            File file = world.getWorldFolder();
            deleteWorld(file);
        }
        World mineWorld = Bukkit.createWorld(new WorldCreator(p.getUniqueId().toString())
                .type(WorldType.FLAT)
                .generator(new EmptyWorldGenerator()));

        File schem = new File(Main.plugin.getDataFolder() + File.separator + "schematics", "firstmine.schematic");
        Location paste = new Location(mineWorld, 0, 144, 0);
        paste.setYaw(-90);
        pasteSchematic(Objects.requireNonNull(WESchematic.getSchematic(schem)), paste);
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
