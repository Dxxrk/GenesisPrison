package me.dxrk.Enchants;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PickaxeSkillTree implements Listener {

    static Methods m = Methods.getInstance();
    static SettingsManager settings = SettingsManager.getInstance();


    private static ItemStack Spacer(){
        ItemStack white = new ItemStack(Material.STAINED_GLASS_PANE, (short)0);
        ItemMeta wm = white.getItemMeta();
        wm.setDisplayName(m.c("&7Skills"));
        white.setItemMeta(wm);
        return white;
    }

    private static ItemStack skillItem(Player p, String name, String desc, int price){
        ItemStack skill = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta sm = skill.getItemMeta();
        List<String> lore = new ArrayList<>();
        lore.add(desc);
        lore.add(" ");
        List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
        if(skillsUnlocked.contains(name)){
            skill = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
            sm.setDisplayName(m.c("&a"+name));
            sm.setLore(lore);
            skill.setItemMeta(sm);
            return skill;
        }


        sm.setDisplayName(m.c("&c"+name));
        lore.add(m.c("&9Price: &b"+price));
        sm.setLore(lore);
        skill.setItemMeta(sm);


        return skill;
    }


    public static void openSkills(Player p){
        Inventory skills = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&cSelect a Path"));
        List<String> lore = new ArrayList<>();

        ItemStack zeus = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
        ItemMeta zm = zeus.getItemMeta();
        lore.add(m.c("&7&oSelect this to confirm your skill path as &e&lZeus."));
        zm.setLore(lore);
        zeus.setItemMeta(zm);
        skills.setItem(0, zeus);
        lore.clear();

        ItemStack poseidon = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
        ItemMeta pm = poseidon.getItemMeta();
        lore.add(m.c("&7&oSelect this to confirm your skill path as &9&lPoseidon."));
        pm.setLore(lore);
        poseidon.setItemMeta(pm);
        skills.setItem(1, poseidon);
        lore.clear();

        ItemStack hades = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta hm = hades.getItemMeta();
        lore.add(m.c("&7&oSelect this to confirm your skill path as &4&lhades."));
        hm.setLore(lore);
        hades.setItemMeta(hm);
        skills.setItem(2, hades);
        lore.clear();

        ItemStack aphrodite = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)6);
        ItemMeta am = aphrodite.getItemMeta();
        lore.add(m.c("&7&oSelect this to confirm your skill path as &d&lAphrodite."));
        am.setLore(lore);
        aphrodite.setItemMeta(am);
        skills.setItem(3, aphrodite);
        lore.clear();

        ItemStack athena = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
        ItemMeta atm = athena.getItemMeta();
        lore.add(m.c("&7&oSelect this to confirm your skill path as &2&lAthena."));
        atm.setLore(lore);
        athena.setItemMeta(atm);
        skills.setItem(4, athena);

        p.openInventory(skills);
    }

    private List<String> zeusSkills(){
        List<String> zeus = new ArrayList<>();
        zeus.add("Token Bonus (Level 1");

        return zeus;
    }

    //Each Skill path is able to summon server-wide events from their respective tree.
    public static void openZeus(Player p){
        Inventory zeus = Bukkit.createInventory(null, 54, m.c("&e&lZeus Skill Tree"));
        for(int i = 0; i < 54; i++){
            zeus.setItem(i, Spacer());
        }
        //Token Bonus
        zeus.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        zeus.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 5));
        zeus.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 9));
        zeus.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 13));
        zeus.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 17));
        zeus.setItem(41, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 21));
        zeus.setItem(47, skillItem(p, "Token Bonus (Level 7)", m.c("&7Each of these skills gives an additional 5% token bonus."), 25));
        //Luck Boost
        zeus.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        zeus.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 7));
        zeus.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        zeus.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 19));
        zeus.setItem(46, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 25)); // pickaxe level 396 to max
        //Fortune
        zeus.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        zeus.setItem(41, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 7));
        zeus.setItem(49, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        zeus.setItem(48, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 19));
        zeus.setItem(5, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 25));
        //Coupon finder
        zeus.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        zeus.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        zeus.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        zeus.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        zeus.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 40));
        //Level ups / abilities
        zeus.setItem(18, skillItem(p, "Unlocked Zeus", m.c("&7Chose the Zeus path. Unlocks the chance for the Thunder God Event."), 0));
        zeus.setItem(22, skillItem(p, "Zeus (Level 2)", m.c("&7Unlocks the Lightning God Event"), 0));
        zeus.setItem(8, skillItem(p, "Zeus (Level 3)", m.c("&7Raises Thunder Event to level 2"), 0));
        zeus.setItem(45, skillItem(p, "Zeus (Level 4)", m.c("&7Raises Thunder Event to level 3"), 0));
        zeus.setItem(26, skillItem(p, "Zeus (Level 5)", m.c("&7Raises Ligntning Event to level 2"), 0));
        p.openInventory(zeus);
    }

    public static void openPoseidon(Player p){
        Inventory poseidon = Bukkit.createInventory(null, 54, m.c("&9&lPoseidon Skill Tree"));
        for(int i = 0; i < 54; i++){
            poseidon.setItem(i, Spacer());
        }
        //Token Bonus
        poseidon.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        poseidon.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 5));
        poseidon.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 9));
        poseidon.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 13));
        poseidon.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 17));
        poseidon.setItem(41, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 21));
        poseidon.setItem(47, skillItem(p, "Token Bonus (Level 7)", m.c("&7Each of these skills gives an additional 5% token bonus."), 25));
        //Luck Boost
        poseidon.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        poseidon.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 7));
        poseidon.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        poseidon.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 19));
        poseidon.setItem(46, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 25)); // pickaxe level 396 to max
        //Fortune
        poseidon.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        poseidon.setItem(41, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 7));
        poseidon.setItem(49, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        poseidon.setItem(48, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 19));
        poseidon.setItem(5, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 25));
        //Coupon finder
        poseidon.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        poseidon.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        poseidon.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        poseidon.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        poseidon.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 40));
        //Level ups / abilities
        poseidon.setItem(18, skillItem(p, "Unlocked Poseidon", m.c("&7Chose the Poseidon path. Unlocks the chance for the Twister God Event."), 0));
        poseidon.setItem(22, skillItem(p, "Poseidon (Level 2)", m.c("&7Unlocks the Tsunami God Event"), 0));
        poseidon.setItem(8, skillItem(p, "Poseidon (Level 3)", m.c("&7Raises Twister Event to level 2"), 0));
        poseidon.setItem(45, skillItem(p, "Poseidon (Level 4)", m.c("&7Raises Twister Event to level 3"), 0));
        poseidon.setItem(26, skillItem(p, "Poseidon (Level 5)", m.c("&7Raises Tsunami Event to level 2"), 0));
        p.openInventory(poseidon);
    }

    public static void openHades(Player p){
        Inventory hades = Bukkit.createInventory(null, 54, m.c("&4&lHades Skill Tree"));
        for(int i = 0; i < 54; i++){
            hades.setItem(i, Spacer());
        }
        //Token Bonus
        hades.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        hades.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 5));
        hades.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 9));
        hades.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 13));
        hades.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 17));
        hades.setItem(41, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 21));
        hades.setItem(47, skillItem(p, "Token Bonus (Level 7)", m.c("&7Each of these skills gives an additional 5% token bonus."), 25));
        //Luck Boost
        hades.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        hades.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 7));
        hades.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        hades.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 19));
        hades.setItem(46, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 25)); // pickaxe level 396 to max
        //Fortune
        hades.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        hades.setItem(41, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 7));
        hades.setItem(49, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        hades.setItem(48, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 19));
        hades.setItem(5, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 25));
        //Coupon finder
        hades.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        hades.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        hades.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        hades.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        hades.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 40));
        //Level ups / abilities
        hades.setItem(18, skillItem(p, "Unlocked Hades", m.c("&7Chose the Hades path. Unlocks the chance for the Molten Rain God Event."), 0));
        hades.setItem(22, skillItem(p, "Hades (Level 2)", m.c("&7Unlocks the Broken Earth God Event"), 0));
        hades.setItem(8, skillItem(p, "Hades (Level 3)", m.c("&7Raises Molten Rain Event to level 2"), 0));
        hades.setItem(45, skillItem(p, "Hades (Level 4)", m.c("&7Raises Molten Rain Event to level 3"), 0));
        hades.setItem(26, skillItem(p, "Hades (Level 5)", m.c("&7Raises Broken Earth Event to level 2"), 0));
        p.openInventory(hades);
    }

    public static void openAthena(Player p){
        Inventory athena = Bukkit.createInventory(null,54, m.c("&2&lAthena Skill Tree"));
        for(int i = 0; i < 54; i++){
            athena.setItem(i, Spacer());
        }
        //Token Bonus
        athena.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        athena.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 5));
        athena.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 9));
        athena.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 13));
        athena.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 17));
        athena.setItem(41, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 21));
        athena.setItem(47, skillItem(p, "Token Bonus (Level 7)", m.c("&7Each of these skills gives an additional 5% token bonus."), 25));
        //Luck Boost
        athena.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        athena.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 7));
        athena.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        athena.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 19));
        athena.setItem(46, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 25)); // pickaxe level 396 to max
        //Fortune
        athena.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        athena.setItem(41, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 7));
        athena.setItem(49, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        athena.setItem(48, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 19));
        athena.setItem(5, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 25));
        //Coupon finder
        athena.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        athena.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        athena.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        athena.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        athena.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 40));
        //Level ups / abilities
        athena.setItem(18, skillItem(p, "Unlocked Athena", m.c("&7Chose the athena path. Unlocks the chance for the Wise Words God Event."), 0));
        athena.setItem(22, skillItem(p, "Athena (Level 2)", m.c("&7Unlocks the War Torn God Event"), 0));
        athena.setItem(8, skillItem(p, "Athena (Level 3)", m.c("&7Raises Wise Words Event to level 2"), 0));
        athena.setItem(45, skillItem(p, "Athena (Level 4)", m.c("&7Raises Wise Words Event to level 3"), 0));
        athena.setItem(26, skillItem(p, "Athena (Level 5)", m.c("&7Raises War Torn Event to level 2"), 0));
        p.openInventory(athena);
    }

    public static void openAphrodite(Player p){
        Inventory aphrodite = Bukkit.createInventory(null, 54, m.c("&d&lAphrodite Skill Tree"));
        for(int i = 0; i < 54; i++){
            aphrodite.setItem(i, Spacer());
        }
        //Token Bonus
        aphrodite.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        aphrodite.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 5));
        aphrodite.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 9));
        aphrodite.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 13));
        aphrodite.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 17));
        aphrodite.setItem(41, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 21));
        aphrodite.setItem(47, skillItem(p, "Token Bonus (Level 7)", m.c("&7Each of these skills gives an additional 5% token bonus."), 25));
        //Luck Boost
        aphrodite.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        aphrodite.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 7));
        aphrodite.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        aphrodite.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 19));
        aphrodite.setItem(46, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 25)); // pickaxe level 396 to max
        //Fortune
        aphrodite.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        aphrodite.setItem(41, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 7));
        aphrodite.setItem(49, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        aphrodite.setItem(48, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 19));
        aphrodite.setItem(5, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 25));
        //Coupon finder
        aphrodite.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        aphrodite.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        aphrodite.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        aphrodite.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        aphrodite.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 40));
        //Level ups / abilities
        aphrodite.setItem(18, skillItem(p, "Unlocked Aphrodite", m.c("&7Chose the aphrodite path. Unlocks the chance for the Cease Fire God Event."), 0));
        aphrodite.setItem(22, skillItem(p, "Aphrodite (Level 2)", m.c("&7Unlocks the God's Grace God Event"), 0));
        aphrodite.setItem(8, skillItem(p, "Aphrodite (Level 3)", m.c("&7Raises Cease Fire Event to level 2"), 0));
        aphrodite.setItem(45, skillItem(p, "Aphrodite (Level 4)", m.c("&7Raises Cease Fire Event to level 3"), 0));
        aphrodite.setItem(26, skillItem(p, "Aphrodite (Level 5)", m.c("&7Raises God's Grace Event to level 2"), 0));
        p.openInventory(aphrodite);
    }

    //Add Skill trees to each available path.
    //Skill points for every pickaxe level past 25
    //Skills are red panes until unlocked then green panes(enchanted)
    //Skills cost skill points increasing as you get farther into the tree
    //The level ups (different pickaxe item dia, gold, etc) are free once available in the tree.



    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getName() == null) return;

        if(e.getClickedInventory().getName().equals(m.c("&cSelect a Path"))){
            e.setCancelled(true);
            if(e.getSlot() == 0){
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkill", "Zeus");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillLevel", 1);
                List<String> skills = new ArrayList<>();
                skills.add("Unlocked Zeus");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skills);
                settings.savePlayerData();
                openZeus(p);
            }
            if(e.getSlot() == 1){
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkill", "Poseidon");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillLevel", 1);
                List<String> skills = new ArrayList<>();
                skills.add("Unlocked Poseidon");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skills);
                settings.savePlayerData();
                openPoseidon(p);
            }
            if(e.getSlot() == 2){
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkill", "Hades");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillLevel", 1);
                List<String> skills = new ArrayList<>();
                skills.add("Unlocked Hades");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skills);
                settings.savePlayerData();
                openHades(p);
            }
            if(e.getSlot() == 3){
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkill", "Aphrodite");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillLevel", 1);
                List<String> skills = new ArrayList<>();
                skills.add("Unlocked Aphrodite");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skills);
                settings.savePlayerData();
                openAphrodite(p);
            }
            if(e.getSlot() == 4){
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkill", "Athena");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillLevel", 1);
                List<String> skills = new ArrayList<>();
                skills.add("Unlocked Athena");
                settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skills);
                settings.savePlayerData();
                openAthena(p);
            }
        }
        if(e.getClickedInventory().getName().equals(m.c("&e&lZeus Skill Tree"))){
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getDurability() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getDurability() == 0) return;

            int price = PickaxeLevel.getInstance().getBlocks(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            if(skillPoints < price) return;
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);
            List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
            skillsUnlocked.add(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()));
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            openZeus(p);
        }

    }






}
