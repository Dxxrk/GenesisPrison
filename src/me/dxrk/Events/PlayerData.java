package me.dxrk.Events;


import me.dxrk.Main.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.UUID;

public class PlayerData {

    private File pdatafile;
    private FileConfiguration pdata;

    public PlayerData(UUID id) {
        pdatafile = new File(Main.plugin.getDataFolder() + File.separator + "playerdata", id.toString() + ".yml");
        pdata = YamlConfiguration.loadConfiguration(pdatafile);
    }


    public FileConfiguration get() {
        return this.pdata;
    }

    public void save() {
        try {
            pdata.save(pdatafile);
        } catch (Exception e) {
            System.out.println("COULD NOT SAVE " + pdatafile.getName());
            System.out.println(e.getMessage());
        }
    }


}