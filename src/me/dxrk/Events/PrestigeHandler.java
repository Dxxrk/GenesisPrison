package me.dxrk.Events;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PrestigeHandler implements Listener {

    Methods m = Methods.getInstance();
    SettingsManager settings = SettingsManager.getInstance();
    public FileConfiguration pl = this.settings.getPlayerData();

    private ItemStack prestigeItem(Material mat, List<String> lore, String name) {
        ItemStack item = new ItemStack(mat);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }


    public void openInv(Player p) {
        Inventory prestige = Bukkit.createInventory(null, 27, m.c("&cPrestige:"));




    }


    //Every 10 ranks or levels you can prestige +1
    //Every time you prestige the next time you do it, it will go to every 15 ranks or levels
    //Increases 1.5x each time you prestige
    //Ranking up will also get 1.5x harder each time you prestige


    public void prestige(Player p) {
        //Resetting rank
        String uuid = p.getUniqueId().toString();
        int timesprestied = this.pl.getInt(uuid+".TimesPrestiged");
        RankupHandler.getInstance().setRank(p, 1);
        this.pl.set(uuid+".TimesPrestiged", timesprestied+1);
        settings.savePlayerData();

        //Adding Prestiges(boost)
        int rank = RankupHandler.getInstance().getRank(p);
        double divisor = 10 * 1.5;
        int prestiges;
        if(timesprestied ==0) {
            prestiges = rank/10;
        } else {
            prestiges = (int) (rank / divisor);
        }
        this.pl.set(uuid+".Prestiges", prestiges);


    }







}
