package me.dxrk.Enchants;

import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Methods;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PickaxeUpgrades implements Listener {
    Methods m = Methods.getInstance();


    //TODO Add descriptions for what each augment does to the pickaxe.

    public ItemStack Upgrade1() {
        ItemStack i = new ItemStack(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Hardened Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);
        return i;
    }

    public ItemStack Upgrade2() {
        ItemStack i = new ItemStack(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Golden Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);

        return i;
    }

    public ItemStack Upgrade3() {
        ItemStack i = new ItemStack(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Steel Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);
        return i;
    }


    public ItemStack Upgrade4() {
        ItemStack i = new ItemStack(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Crystal Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);
        return i;
    }

    public ItemStack Upgrade5() {
        ItemStack i = new ItemStack(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Hellforged Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);
        return i;
    }


    @EventHandler
    public void applyPickUpgrade(InventoryClickEvent e) {
        if(e.getCurrentItem().getType().equals(Material.AIR)) return;
        if(e.getCursor().getType().equals(Material.AIR)) return;
        Player p = (Player) e.getWhoClicked();
        if(e.getCursor().hasItemMeta()) {
            if(e.getCursor().getItemMeta().hasDisplayName()) {
                if(e.getCurrentItem().getType().equals(Material.WOODEN_PICKAXE) || e.getCurrentItem().getType().equals(Material.STONE_PICKAXE) || e.getCurrentItem().getType().equals(Material.GOLDEN_PICKAXE)
                || e.getCurrentItem().getType().equals(Material.IRON_PICKAXE) || e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE) || e.getCurrentItem().getType().equals(Material.NETHERITE_PICKAXE)) {
                    if(e.getCursor().getType().equals(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE)) {
                        PlayerDataHandler.getInstance().getPlayerData(p);

                    }
                    else if(e.getCursor().getType().equals(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE)) {

                    }
                    else if(e.getCursor().getType().equals(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE)) {

                    }
                    else if(e.getCursor().getType().equals(Material.TIDE_ARMOR_TRIM_SMITHING_TEMPLATE)) {

                    }
                    else if(e.getCursor().getType().equals(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE)) {

                    }
                }
            }
        }
    }



}
