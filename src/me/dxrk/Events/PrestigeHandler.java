package me.dxrk.Events;

import com.connorlinfoot.titleapi.TitleAPI;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Vote.CMDVoteShop;
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
            if(RankupHandler.getInstance().getRank(p) >=1000)
                openInv(p);
            else
                p.sendMessage(m.c("&cMust be level 1000 or higher."));
        }



        return false;
    }


    public void openInv(Player p) {
        Inventory prestige = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&cPrestige: "));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&cWarning: This will reset your rank to 1!!"));
        lore.add(m.c(" "));
        lore.add(m.c("&7Prestiging gives a 50% increase to sell prices."));
        lore.add(m.c("&7Will also make levelling up considerably harder."));
        lore.add(m.c("&a+$0.25 Coupon"));
        lore.add(m.c("&b+1 &4&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
        String uuid = p.getUniqueId().toString();
        int prestiges = pl.getInt(uuid+".Prestiges");
        if(prestiges >=100) {
            lore.clear();
            lore.add(m.c("&c&lMAX LEVEL"));
            prestige.setItem(2, prestigeItem(lore, m.c("&6&lYou did it!")));
            p.openInventory(prestige);
            return;
        }
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
                String uuid = p.getUniqueId().toString();
                int prestiges = pl.getInt(uuid+".Prestiges");
                if(prestiges >=100) {
                    return;
                }
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

        //Adding Prestige and resetting rank
        int prestiges = pl.getInt(uuid+".Prestiges");
        pl.set(uuid+".Prestiges", (prestiges+1));
        RankupHandler.getInstance().setRank(p, 1);
        Main.econ.withdrawPlayer(p, Main.econ.getBalance(p));
        settings.savePlayerData();
        TitleAPI.sendTitle(p, 2, 40, 2, m.c("&c&lPrestiged!"), m.c("&b&lPrestige +1"));
        MineHandler.getInstance().updateMine(p, 1);
        CMDVoteShop.addCoupon(p, 0.25);
        LocksmithHandler.getInstance().addKey(p, "Seasonal", 1);
    }

    public static void addPrestiges(Player p, int amt) {
        String uuid = p.getUniqueId().toString();
        int prestiges = pl.getInt(uuid+".Prestiges");
        pl.set(uuid+".Prestiges", prestiges+amt);
        settings.savePlayerData();
    }







}
