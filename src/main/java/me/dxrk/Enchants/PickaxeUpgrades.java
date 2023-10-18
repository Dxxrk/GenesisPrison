package me.dxrk.Enchants;

import me.dxrk.Main.Methods;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PickaxeUpgrades implements Listener {
    Methods m = Methods.getInstance();


    public ItemStack Upgrade1() {
        ItemStack i = new ItemStack(Material.WILD_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Golden Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);

        return i;
    }

    public ItemStack Upgrade2() {
        ItemStack i = new ItemStack(Material.COAST_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Steel Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);
        return i;
    }

    public ItemStack Upgrade3() {
        ItemStack i = new ItemStack(Material.DUNE_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Crystal Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
        i.setItemMeta(im);
        return i;
    }

    public ItemStack Upgrade4() {
        ItemStack i = new ItemStack(Material.RIB_ARMOR_TRIM_SMITHING_TEMPLATE);
        ItemMeta im = i.getItemMeta();
        im.displayName(m.colorText("Hardened Pickaxe Augment", TextColor.color(0xFFD700), TextDecoration.BOLD, true));
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

    }



}
