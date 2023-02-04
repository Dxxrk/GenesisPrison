package me.dxrk.Events;

import com.connorlinfoot.titleapi.TitleAPI;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PrestigeHandler implements Listener, CommandExecutor {

    Methods m = Methods.getInstance();
    static SettingsManager settings = SettingsManager.getInstance();
    public static FileConfiguration pl = settings.getPlayerData();

    private ItemStack prestigeItem(List<String> lore, String name) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }



    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if(p == null){
            return false;
        }


        if(label.equalsIgnoreCase("prestige")){
            int prestiges;
            int times = pl.getInt(p.getUniqueId().toString()+".TimesPrestiged");
            int rank = RankupHandler.getInstance().getRank(p);
            double divisor = 7.5 * (1.5*times);
            if(times ==0) {
                prestiges = (int) (rank/7.5);
            } else {
                prestiges = (int) Math.round(rank / divisor);
            }

            openInv(p, prestiges, times);


        }



        return false;
    }


    public void openInv(Player p, int prestiges, int times) {
        Inventory prestige = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&cPrestige: You have prestiged &a"+times+" &ctimes."));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&cWarning: This will reset your rank to 1!!"));
        lore.add(m.c(" "));
        lore.add(m.c("&aPrestiging now will give you &b"+prestiges+" &aprestiges."));


        prestige.setItem(2, prestigeItem(lore, m.c("&6&lCLICK TO PRESTIGE!")));
        prestige.setItem(0, PickaxeLevel.getInstance().Spacer());
        prestige.setItem(1, PickaxeLevel.getInstance().Spacer());
        prestige.setItem(3, PickaxeLevel.getInstance().Spacer());
        prestige.setItem(4, PickaxeLevel.getInstance().Spacer());

        p.openInventory(prestige);


    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getName() == null)
            return;

        if(e.getClickedInventory().getName().contains(m.c("&cPrestige:"))){
            e.setCancelled(true);

            if(e.getSlot() == 2){
                Inventory sure = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&c&lARE YOU SURE?"));

                ItemStack yes = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
                ItemMeta ym = yes.getItemMeta();
                List<String> lore = new ArrayList<>();
                lore.add(m.c("&7&oThis Action is permanent."));
                ym.setDisplayName(m.c("&a&lYES"));
                ym.setLore(lore);
                yes.setItemMeta(ym);
                lore.clear();

                ItemStack no = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
                ItemMeta nm = no.getItemMeta();
                nm.setDisplayName(m.c("&c&lNO"));
                no.setItemMeta(nm);

                sure.setItem(1, yes);
                sure.setItem(3, no);
                sure.setItem(0, PickaxeLevel.getInstance().Spacer());
                sure.setItem(2, PickaxeLevel.getInstance().Spacer());
                sure.setItem(4, PickaxeLevel.getInstance().Spacer());
                p.openInventory(sure);

            }
        }
        if(e.getClickedInventory().getName().equals(m.c("&c&lARE YOU SURE?"))){
            e.setCancelled(true);
            if(e.getSlot() == 1){
                prestige(p);
                p.closeInventory();

            }
            if(e.getSlot() == 3){
                p.closeInventory();
            }
        }
    }





    public void prestige(Player p) {
        String uuid = p.getUniqueId().toString();
        int timesprestied = pl.getInt(uuid+".TimesPrestiged");


        //Adding Prestiges(boost) and resetting rank
        int rank = RankupHandler.getInstance().getRank(p);
        double divisor = 7.5 * (1.5*timesprestied);
        int prestiges = pl.getInt(uuid+".Prestiges");
        int pr;
        if(timesprestied ==0) {
            pr = (int) (rank /7.5);
        } else {
            pr = (int) Math.round(rank / divisor);
        }
        pl.set(uuid+".Prestiges", prestiges+pr);
        RankupHandler.getInstance().setRank(p, 1);
        pl.set(uuid+".TimesPrestiged", timesprestied+1);
        settings.savePlayerData();
        TitleAPI.sendTitle(p, 2, 40, 2, m.c("&c&lPrestiged!"), m.c("&b&lPrestiges Gained: +"+pr));
        MineHandler.getInstance().updateMine(p);


    }

    public static void addPrestiges(Player p, int amt) {
        String uuid = p.getUniqueId().toString();
        int prestiges = pl.getInt(uuid+".Prestiges");
        pl.set(uuid+".Prestiges", prestiges+amt);
        settings.savePlayerData();
    }







}
