package me.dxrk.Enchants;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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

public class PickaxeSkillTree implements Listener {

    static Methods m = Methods.getInstance();
    static SettingsManager settings = SettingsManager.getInstance();


    private static ItemStack Spacer(){
        ItemStack white = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)0);
        ItemMeta wm = white.getItemMeta();
        wm.setDisplayName(m.c(" "));
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
        zm.setDisplayName(m.c("&e&lZeus Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &e&lskills."));
        zm.setLore(lore);
        zeus.setItemMeta(zm);
        skills.setItem(0, zeus);
        lore.clear();

        ItemStack poseidon = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
        ItemMeta pm = poseidon.getItemMeta();
        pm.setDisplayName(m.c("&9&lPoseidon Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &9&lPoseidon."));
        pm.setLore(lore);
        poseidon.setItemMeta(pm);
        skills.setItem(1, poseidon);
        lore.clear();

        ItemStack hades = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta hm = hades.getItemMeta();
        hm.setDisplayName(m.c("&4&lHades Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &4&lhades."));
        hm.setLore(lore);
        hades.setItemMeta(hm);
        skills.setItem(2, hades);
        lore.clear();

        ItemStack aphrodite = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)6);
        ItemMeta am = aphrodite.getItemMeta();
        am.setDisplayName(m.c("&d&lAphrodite Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &d&lAphrodite."));
        am.setLore(lore);
        aphrodite.setItemMeta(am);
        skills.setItem(3, aphrodite);
        lore.clear();

        ItemStack athena = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
        ItemMeta atm = athena.getItemMeta();
        atm.setDisplayName(m.c("&2&lAthena Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &2&lAthena."));
        atm.setLore(lore);
        athena.setItemMeta(atm);
        skills.setItem(4, athena);

        p.openInventory(skills);
    }

    private List<String> zeusSkills(){
        List<String> skills = new ArrayList<>();
        skills.add("Unlocked Zeus");
        skills.add("Token Bonus (Level 1)");
        skills.add("Luck Boost (Level 1)");
        skills.add("Fortune Boost (Level 1)");
        skills.add("Zeus (Level 2)");
        skills.add("Token Bonus (Level 2)");
        skills.add("Luck Boost (Level 2)");
        skills.add("Token Bonus (Level 3)");
        skills.add("Coupon Finder (Level 1)");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Zeus (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("Coupon Finder (Level 2)");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Zeus (Level 4)");
        skills.add("Coupon Finder (Level 3)");
        skills.add("Coupon Finder (Level 4)");
        skills.add("Coupon Finder (Level 5)");
        skills.add("Zeus (Level 5)");

        return skills;
    }
    private List<String> poseidonSkills(){
        List<String> skills = new ArrayList<>();
        skills.add("Poseidon Unlocked");
        skills.add("Token Bonus (Level 1)");
        skills.add("Luck Boost (Level 1)");
        skills.add("Fortune Boost (Level 1)");
        skills.add("Poseidon (Level 2)");
        skills.add("Token Bonus (Level 2)");
        skills.add("Luck Boost (Level 2)");
        skills.add("Token Bonus (Level 3)");
        skills.add("Coupon Finder (Level 1)");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Poseidon (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("Coupon Finder (Level 2)");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Poseidon (Level 4)");
        skills.add("Coupon Finder (Level 3)");
        skills.add("Coupon Finder (Level 4)");
        skills.add("Coupon Finder (Level 5)");
        skills.add("Poseidon (Level 5)");

        return skills;
    }
    private List<String> hadesSkills(){
        List<String> skills = new ArrayList<>();
        skills.add("Hades Unlocked");
        skills.add("Token Bonus (Level 1)");
        skills.add("Luck Boost (Level 1)");
        skills.add("Fortune Boost (Level 1)");
        skills.add("Hades (Level 2)");
        skills.add("Token Bonus (Level 2)");
        skills.add("Luck Boost (Level 2)");
        skills.add("Token Bonus (Level 3)");
        skills.add("Coupon Finder (Level 1)");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Hades (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("Coupon Finder (Level 2)");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Hades (Level 4)");
        skills.add("Coupon Finder (Level 3)");
        skills.add("Coupon Finder (Level 4)");
        skills.add("Coupon Finder (Level 5)");
        skills.add("Hades (Level 5)");

        return skills;
    }
    private List<String> aphroditeSkills(){
        List<String> skills = new ArrayList<>();
        skills.add("Aphrodite Unlocked");
        skills.add("Token Bonus (Level 1)");
        skills.add("Luck Boost (Level 1)");
        skills.add("Fortune Boost (Level 1)");
        skills.add("Aphrodite (Level 2)");
        skills.add("Token Bonus (Level 2)");
        skills.add("Luck Boost (Level 2)");
        skills.add("Token Bonus (Level 3)");
        skills.add("Coupon Finder (Level 1)");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Aphrodite (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("Coupon Finder (Level 2)");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Aphrodite (Level 4)");
        skills.add("Coupon Finder (Level 3)");
        skills.add("Coupon Finder (Level 4)");
        skills.add("Coupon Finder (Level 5)");
        skills.add("Aphrodite (Level 5)");

        return skills;
    }
    private List<String> athenaSkills(){
        List<String> skills = new ArrayList<>();
        skills.add("Athena Unlocked");
        skills.add("Token Bonus (Level 1)");
        skills.add("Luck Boost (Level 1)");
        skills.add("Fortune Boost (Level 1)");
        skills.add("Athena (Level 2)");
        skills.add("Token Bonus (Level 2)");
        skills.add("Luck Boost (Level 2)");
        skills.add("Token Bonus (Level 3)");
        skills.add("Coupon Finder (Level 1)");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Athena (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("Coupon Finder (Level 2)");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Athena (Level 4)");
        skills.add("Coupon Finder (Level 3)");
        skills.add("Coupon Finder (Level 4)");
        skills.add("Coupon Finder (Level 5)");
        skills.add("Athena (Level 5)");

        return skills;
    }
    public int findPickaxeType(Player p) {
        if(p.getItemInHand().getType().equals(Material.WOOD_PICKAXE)) return 1;
        if(p.getItemInHand().getType().equals(Material.STONE_PICKAXE)) return 2;
        if(p.getItemInHand().getType().equals(Material.GOLD_PICKAXE)) return 3;
        if(p.getItemInHand().getType().equals(Material.IRON_PICKAXE)) return 4;
        if(p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) return 5;

        return 1;
    }
    public void setType(Player p, int i) {
        switch(i){
            case 1:
                p.getItemInHand().setType(Material.STONE_PICKAXE);
                break;
            case 2:
                p.getItemInHand().setType(Material.GOLD_PICKAXE);
                break;
            case 3:
                p.getItemInHand().setType(Material.IRON_PICKAXE);
                break;
            case 4:
                p.getItemInHand().setType(Material.DIAMOND_PICKAXE);
                break;
        }

    }

    //Each Skill path is able to summon server-wide events from their respective tree.
    public static void openZeus(Player p){
        Inventory zeus = Bukkit.createInventory(null, 54, m.c("&e&lZeus Skill Tree"));
        for(int i = 0; i < 54; i++){
            zeus.setItem(i, Spacer());
        }
        //Token Bonus
        zeus.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        zeus.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 6));
        zeus.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 11));
        zeus.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 16));
        zeus.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 21));
        zeus.setItem(47, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 25));
        //Luck Boost
        zeus.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        zeus.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 7));
        zeus.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        zeus.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 19));
        zeus.setItem(48, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 25)); // pickaxe level 396 to max
        //Fortune
        zeus.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        zeus.setItem(5, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 7));
        zeus.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        zeus.setItem(49, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 19));
        zeus.setItem(46, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 25));
        //Coupon finder
        zeus.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        zeus.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        zeus.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        zeus.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        zeus.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 40));
        //Level ups / abilities
        zeus.setItem(18, skillItem(p, "Unlocked Zeus", m.c("&7Chose the Zeus path. Unlocks the Thunder and Lightning Events."), 0));
        zeus.setItem(22, skillItem(p, "Zeus (Level 2)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(8, skillItem(p, "Zeus (Level 3)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(45, skillItem(p, "Zeus (Level 4)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(26, skillItem(p, "Zeus (Level 5)", m.c("&7Upgrades Zeus Events."), 0));
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
        poseidon.setItem(18, skillItem(p, "Unlocked Poseidon", m.c("&7Chose the Poseidon path. Unlocks the Twister and Tsunami Events."), 0));
        poseidon.setItem(22, skillItem(p, "Poseidon (Level 2)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(8, skillItem(p, "Poseidon (Level 3)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(45, skillItem(p, "Poseidon (Level 4)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(26, skillItem(p, "Poseidon (Level 5)", m.c("&7Upgrades Poseidon Events."), 0));
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
        hades.setItem(18, skillItem(p, "Unlocked Hades", m.c("&7Chose the Hades path. Unlocks the Meteor and Scorched Earth Events."), 0));
        hades.setItem(22, skillItem(p, "Hades (Level 2)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(8, skillItem(p, "Hades (Level 3)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(45, skillItem(p, "Hades (Level 4)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(26, skillItem(p, "Hades (Level 5)", m.c("&7Upgrades Hades Events."), 0));
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
        athena.setItem(18, skillItem(p, "Unlocked Athena", m.c("&7Chose the athena path. Unlocks the chance for the Wise Words Event."), 0));
        athena.setItem(22, skillItem(p, "Athena (Level 2)", m.c("&7Unlocks the War Torn Event"), 0));
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
        aphrodite.setItem(18, skillItem(p, "Unlocked Aphrodite", m.c("&7Chose the aphrodite path. Unlocks the chance for the Cease Fire Event."), 0));
        aphrodite.setItem(22, skillItem(p, "Aphrodite (Level 2)", m.c("&7Unlocks the God's Grace Event"), 0));
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

    public void selectPath(Player p, String path, String color) {
        ItemStack pitem = p.getItemInHand().clone();
        ItemMeta pm = pitem.getItemMeta();
        List<String> lore = pm.getLore();

        settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkill", path);
        settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillLevel", 1);
        List<String> skills = new ArrayList<>();
        skills.add("Unlocked "+path);
        int line = 0;
        for(int i = 0; i < lore.size(); i++){
            String s = lore.get(i);
            if(s.contains("Skill:")){
                line = i;
            }
        }
        lore.set(line, m.c("&cSkill: "+color+path +" (Level 1)"));
        settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skills);
        settings.savePlayerData();

        switch(path){
            case "Zeus":
                openZeus(p);
                break;
            case "Poseidon":
                openPoseidon(p);
                break;
            case "Hades":
                openHades(p);
                break;
            case "Aphrodite":
                openAphrodite(p);
                break;
            case "Athena":
                openAthena(p);
                break;
        }
    }


    @SuppressWarnings("deprecation")
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getName() == null) return;
        if(e.getCurrentItem() == null) return;
        if(!e.getCurrentItem().hasItemMeta()) return;

        if(e.getInventory().getName().equals(m.c("&cSelect a Path"))){
            e.setCancelled(true);
            if(e.getClickedInventory().equals(p.getInventory())) return;


            if(e.getSlot() == 0){
                selectPath(p, "Zeus", "&f");
            }
            if(e.getSlot() == 1){
                selectPath(p, "Poseidon", "&9");
            }
            if(e.getSlot() == 2){
                selectPath(p, "Hades", "&4");
            }
            if(e.getSlot() == 3){
                selectPath(p, "Aphrodite", "&d");
            }
            if(e.getSlot() == 4){
                selectPath(p, "Athena", "&2");
            }
        }
        if(e.getInventory().getName().equals(m.c("&e&lZeus Skill Tree"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getBlocks(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
            String previous = "";
            for(int i = 0; i < zeusSkills().size(); i++){
                if(zeusSkills().get(i).equals(skill)){
                    previous = zeusSkills().get(zeusSkills().indexOf(skill) - 1);
                }
            }
            if(!skillsUnlocked.contains(previous)) {
                p.sendMessage(m.c("&cError: Please unlock &a"+previous+" &cfirst."));
                p.closeInventory();
                return;
            }
            if(skillPoints < price) return;
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);
            if(skill.contains("Zeus")) {
                setType(p, findPickaxeType(p));

                ItemStack pitem = p.getItemInHand().clone();
                ItemMeta pm = pitem.getItemMeta();
                List<String> lore = pm.getLore();

                int line = 0;
                for(int i = 0; i < lore.size(); i++){
                    String s = lore.get(i);
                    if(s.contains("Skill:")){
                        line = i;
                    }
                }
                lore.set(line, m.c("&cSkill: &fZeus (Level "+PickaxeLevel.getInstance().getBlocks(skill)+")"));

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openZeus(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&9&lPoseidon Skill Tree"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getBlocks(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
            String previous = "";
            for(int i = 0; i < poseidonSkills().size(); i++){
                if(poseidonSkills().get(i).equals(skill)){
                    previous = poseidonSkills().get(poseidonSkills().indexOf(skill) - 1);
                }
            }
            if(!skillsUnlocked.contains(previous)) {
                p.sendMessage(m.c("&cError: Please unlock &a"+previous+" &cfirst."));
                return;
            }
            if(skillPoints < price) return;
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);
            if(skill.contains("Poseidon")) {
                setType(p, findPickaxeType(p));

                ItemStack pitem = p.getItemInHand().clone();
                ItemMeta pm = pitem.getItemMeta();
                List<String> lore = pm.getLore();

                int line = 0;
                for(int i = 0; i < lore.size(); i++){
                    String s = lore.get(i);
                    if(s.contains("Skill:")){
                        line = i;
                    }
                }
                lore.set(line, m.c("&cSkill: &9Poseidon (Level "+PickaxeLevel.getInstance().getBlocks(skill)+")"));

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openPoseidon(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&4&lHades Skill Tree"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getBlocks(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
            String previous = "";
            for(int i = 0; i < hadesSkills().size(); i++){
                if(hadesSkills().get(i).equals(skill)){
                    previous = hadesSkills().get(hadesSkills().indexOf(skill) - 1);
                }
            }
            if(!skillsUnlocked.contains(previous)) {
                p.sendMessage(m.c("&cError: Please unlock &a"+previous+" &cfirst."));
                return;
            }
            if(skillPoints < price) return;
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);
            if(skill.contains("Hades")) {
                setType(p, findPickaxeType(p));

                ItemStack pitem = p.getItemInHand().clone();
                ItemMeta pm = pitem.getItemMeta();
                List<String> lore = pm.getLore();

                int line = 0;
                for(int i = 0; i < lore.size(); i++){
                    String s = lore.get(i);
                    if(s.contains("Skill:")){
                        line = i;
                    }
                }
                lore.set(line, m.c("&cSkill: &4Hades (Level "+PickaxeLevel.getInstance().getBlocks(skill)+")"));

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openHades(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&d&lAphrodite Skill Tree"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getBlocks(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
            String previous = "";
            for(int i = 0; i < aphroditeSkills().size(); i++){
                if(aphroditeSkills().get(i).equals(skill)){
                    previous = aphroditeSkills().get(aphroditeSkills().indexOf(skill) - 1);
                }
            }
            if(!skillsUnlocked.contains(previous)) {
                p.sendMessage(m.c("&cError: Please unlock &a"+previous+" &cfirst."));
                return;
            }
            if(skillPoints < price) return;
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);
            if(skill.contains("Aphrodite")) {
                setType(p, findPickaxeType(p));

                ItemStack pitem = p.getItemInHand().clone();
                ItemMeta pm = pitem.getItemMeta();
                List<String> lore = pm.getLore();

                int line = 0;
                for(int i = 0; i < lore.size(); i++){
                    String s = lore.get(i);
                    if(s.contains("Skill:")){
                        line = i;
                    }
                }
                lore.set(line, m.c("&cSkill: &dAphrodite (Level "+PickaxeLevel.getInstance().getBlocks(skill)+")"));

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openAphrodite(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&2&lAthena Skill Tree"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getBlocks(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
            String previous = "";
            for(int i = 0; i < athenaSkills().size(); i++){
                if(athenaSkills().get(i).equals(skill)){
                    previous = athenaSkills().get(athenaSkills().indexOf(skill) - 1);
                }
            }
            if(!skillsUnlocked.contains(previous)) {
                p.sendMessage(m.c("&cError: Please unlock &a"+previous+" &cfirst."));
                return;
            }
            if(skillPoints < price) return;
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);
            if(skill.contains("Athena")) {
                setType(p, findPickaxeType(p));

                ItemStack pitem = p.getItemInHand().clone();
                ItemMeta pm = pitem.getItemMeta();
                List<String> lore = pm.getLore();

                int line = 0;
                for(int i = 0; i < lore.size(); i++){
                    String s = lore.get(i);
                    if(s.contains("Skill:")){
                        line = i;
                    }
                }
                lore.set(line, m.c("&cSkill: &2Athena (Level "+PickaxeLevel.getInstance().getBlocks(skill)+")"));

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openAthena(p);
        }

    }






}
