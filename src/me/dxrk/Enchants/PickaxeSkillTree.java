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
        lore.add(m.c("&7Provides a boost in gaining keys."));
        zm.setLore(lore);
        zeus.setItemMeta(zm);
        skills.setItem(0, zeus);
        lore.clear();

        ItemStack poseidon = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
        ItemMeta pm = poseidon.getItemMeta();
        pm.setDisplayName(m.c("&9&lPoseidon Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &9&lPoseidon."));
        lore.add(m.c("&7Provides a boost in fortune."));
        pm.setLore(lore);
        poseidon.setItemMeta(pm);
        skills.setItem(1, poseidon);
        lore.clear();

        ItemStack hades = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta hm = hades.getItemMeta();
        hm.setDisplayName(m.c("&4&lHades Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &4&lhades."));
        lore.add(m.c("&7Provides a boost to your personal multi."));
        hm.setLore(lore);
        hades.setItemMeta(hm);
        skills.setItem(2, hades);
        lore.clear();

        ItemStack aphrodite = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)6);
        ItemMeta am = aphrodite.getItemMeta();
        am.setDisplayName(m.c("&d&lAphrodite Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &d&lAphrodite."));
        lore.add(m.c("&7Provides a boost to your pickaxe xp."));
        am.setLore(lore);
        aphrodite.setItemMeta(am);
        skills.setItem(3, aphrodite);
        lore.clear();

        ItemStack ares = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
        ItemMeta atm = ares.getItemMeta();
        atm.setDisplayName(m.c("&2&lAres Path"));
        lore.add(m.c("&7&oSelect this to confirm your skill path as &2&lAres."));
        lore.add(m.c("&7Provides a boost in gaining tokens."));
        atm.setLore(lore);
        ares.setItemMeta(atm);
        skills.setItem(4, ares);

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
        skills.add("Unlocked Poseidon");
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
        skills.add("Unlocked Hades");
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
        skills.add("Unlocked Aphrodite");
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
    private List<String> aresSkills(){
        List<String> skills = new ArrayList<>();
        skills.add("Unlocked Ares");
        skills.add("Token Bonus (Level 1)");
        skills.add("Luck Boost (Level 1)");
        skills.add("Fortune Boost (Level 1)");
        skills.add("Ares (Level 2)");
        skills.add("Token Bonus (Level 2)");
        skills.add("Luck Boost (Level 2)");
        skills.add("Token Bonus (Level 3)");
        skills.add("Coupon Finder (Level 1)");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Ares (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("Coupon Finder (Level 2)");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Ares (Level 4)");
        skills.add("Coupon Finder (Level 3)");
        skills.add("Coupon Finder (Level 4)");
        skills.add("Coupon Finder (Level 5)");
        skills.add("Ares (Level 5)");

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
        Inventory zeus = Bukkit.createInventory(null, 54, m.c("&e&lZeus Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            zeus.setItem(i, Spacer());
        }
        //Token Bonus
        zeus.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        zeus.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 4));
        zeus.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 7));
        zeus.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 12));
        zeus.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 16));
        zeus.setItem(47, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 20));
        //Luck Boost
        zeus.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        zeus.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 5));
        zeus.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 9));
        zeus.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        zeus.setItem(48, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        zeus.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        zeus.setItem(5, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 5));
        zeus.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 9));
        zeus.setItem(49, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        zeus.setItem(46, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 17));
        //Coupon finder
        zeus.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 15));
        zeus.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        zeus.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        zeus.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        zeus.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        //Level ups / abilities
        zeus.setItem(18, skillItem(p, "Unlocked Zeus", m.c("&7Chose the Zeus path. Unlocks Thunderstorm and ThunderBolt Events."), 0));
        zeus.setItem(22, skillItem(p, "Zeus (Level 2)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(8, skillItem(p, "Zeus (Level 3)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(45, skillItem(p, "Zeus (Level 4)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(26, skillItem(p, "Zeus (Level 5)", m.c("&7Upgrades Zeus Events."), 0));
        p.openInventory(zeus);
    }

    public static void openPoseidon(Player p){
        Inventory poseidon = Bukkit.createInventory(null, 54, m.c("&9&lPoseidon Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            poseidon.setItem(i, Spacer());
        }
        //Token Bonus
        poseidon.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        poseidon.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 4));
        poseidon.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 7));
        poseidon.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 12));
        poseidon.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 16));
        poseidon.setItem(47, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 20));
        //Luck Boost
        poseidon.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        poseidon.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 5));
        poseidon.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 9));
        poseidon.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        poseidon.setItem(48, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        poseidon.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        poseidon.setItem(5, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 5));
        poseidon.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 9));
        poseidon.setItem(49, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        poseidon.setItem(46, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 17));
        //Coupon finder
        poseidon.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 15));
        poseidon.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        poseidon.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        poseidon.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        poseidon.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        //Level ups / abilities
        poseidon.setItem(18, skillItem(p, "Unlocked Poseidon", m.c("&7Chose the Poseidon path. Unlocks Tsunami and Typhoon Events."), 0));
        poseidon.setItem(22, skillItem(p, "Poseidon (Level 2)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(8, skillItem(p, "Poseidon (Level 3)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(45, skillItem(p, "Poseidon (Level 4)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(26, skillItem(p, "Poseidon (Level 5)", m.c("&7Upgrades Poseidon Events."), 0));
        p.openInventory(poseidon);
    }

    public static void openHades(Player p){
        Inventory hades = Bukkit.createInventory(null, 54, m.c("&4&lHades Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            hades.setItem(i, Spacer());
        }
        //Token Bonus
        hades.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        hades.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 4));
        hades.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 7));
        hades.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 12));
        hades.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 16));
        hades.setItem(47, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 20));
        //Luck Boost
        hades.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        hades.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 5));
        hades.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 9));
        hades.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        hades.setItem(48, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        hades.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        hades.setItem(5, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 5));
        hades.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 9));
        hades.setItem(49, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        hades.setItem(46, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 17));
        //Coupon finder
        hades.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 15));
        hades.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        hades.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        hades.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        hades.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        //Level ups / abilities
        hades.setItem(18, skillItem(p, "Unlocked Hades", m.c("&7Chose the Hades path. Unlocks Meteor Shower and Scorched Earth Events."), 0));
        hades.setItem(22, skillItem(p, "Hades (Level 2)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(8, skillItem(p, "Hades (Level 3)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(45, skillItem(p, "Hades (Level 4)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(26, skillItem(p, "Hades (Level 5)", m.c("&7Upgrades Hades Events."), 0));
        p.openInventory(hades);
    }

    public static void openAres(Player p){
        Inventory ares = Bukkit.createInventory(null,54, m.c("&2&lAres Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            ares.setItem(i, Spacer());
        }
        //Token Bonus
        ares.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        ares.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 4));
        ares.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 7));
        ares.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 12));
        ares.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 16));
        ares.setItem(47, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 20));
        //Luck Boost
        ares.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        ares.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 5));
        ares.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 9));
        ares.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        ares.setItem(48, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        ares.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        ares.setItem(5, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 5));
        ares.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 9));
        ares.setItem(49, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        ares.setItem(46, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 17));
        //Coupon finder
        ares.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 15));
        ares.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        ares.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        ares.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        ares.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        //Level ups / abilities
        ares.setItem(18, skillItem(p, "Unlocked Ares", m.c("&7Chose the ares path. Unlocks War Torn and Bloodshed Events."), 0));
        ares.setItem(22, skillItem(p, "Ares (Level 2)", m.c("&7Upgrades Ares Events."), 0));
        ares.setItem(8, skillItem(p, "Ares (Level 3)", m.c("&7Upgrades Ares Events."), 0));
        ares.setItem(45, skillItem(p, "Ares (Level 4)", m.c("&7Upgrades Ares Events."), 0));
        ares.setItem(26, skillItem(p, "Ares (Level 5)", m.c("&7Upgrades Ares Events."), 0));
        p.openInventory(ares);
    }

    public static void openAphrodite(Player p){
        Inventory aphrodite = Bukkit.createInventory(null, 54, m.c("&d&lAphrodite Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            aphrodite.setItem(i, Spacer());
        }
        //Token Bonus
        aphrodite.setItem(19, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 5% token bonus."), 1));
        aphrodite.setItem(13, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 5% token bonus."), 4));
        aphrodite.setItem(3, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 5% token bonus."), 7));
        aphrodite.setItem(7, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 5% token bonus."), 12));
        aphrodite.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 5% token bonus."), 16));
        aphrodite.setItem(47, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 5% token bonus."), 20));
        //Luck Boost
        aphrodite.setItem(20, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 1));
        aphrodite.setItem(4, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 5));
        aphrodite.setItem(6, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 9));
        aphrodite.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 13));
        aphrodite.setItem(48, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 5% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        aphrodite.setItem(21, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 1));
        aphrodite.setItem(5, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 5));
        aphrodite.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 9));
        aphrodite.setItem(49, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 13));
        aphrodite.setItem(46, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 5% effective boost to fortune."), 17));
        //Coupon finder
        aphrodite.setItem(2, skillItem(p, "Coupon Finder (Level 1)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 15));
        aphrodite.setItem(42, skillItem(p, "Coupon Finder (Level 2)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        aphrodite.setItem(23, skillItem(p, "Coupon Finder (Level 3)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 25));
        aphrodite.setItem(24, skillItem(p, "Coupon Finder (Level 4)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        aphrodite.setItem(25, skillItem(p, "Coupon Finder (Level 5)", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 35));
        //Level ups / abilities
        aphrodite.setItem(18, skillItem(p, "Unlocked Aphrodite", m.c("&7Chose the aphrodite path. Unlocks Allure and Strong Desire Events."), 0));
        aphrodite.setItem(22, skillItem(p, "Aphrodite (Level 2)", m.c("&7Upgrades Aphrodite Events."), 0));
        aphrodite.setItem(8, skillItem(p, "Aphrodite (Level 3)", m.c("&7Upgrades Aphrodite Events."), 0));
        aphrodite.setItem(45, skillItem(p, "Aphrodite (Level 4)", m.c("&7Upgrades Aphrodite Events."), 0));
        aphrodite.setItem(26, skillItem(p, "Aphrodite (Level 5)", m.c("&7Upgrades Aphrodite Events."), 0));
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
        for(int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            if(s.contains("Skill:")){
                line = i;
            }
        }
        lore.set(line, m.c("&cSkill: "+color+path +" (Level 1)"));
        pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
        pitem.setItemMeta(pm);
        p.setItemInHand(pitem);
        p.updateInventory();
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
            case "Ares":
                openAres(p);
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
                selectPath(p, "Ares", "&2");
            }
        }
        if(e.getInventory().getName().equals(m.c("&e&lZeus Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
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
                lore.set(line, m.c("&cSkill: &eZeus (Level "+PickaxeLevel.getInstance().getInt(skill)+")"));
                pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
                pitem.setItemMeta(pm);
                p.updateInventory();
                p.setItemInHand(pitem);


            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openZeus(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&9&lPoseidon Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
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
                lore.set(line, m.c("&cSkill: &9Poseidon (Level "+PickaxeLevel.getInstance().getInt(skill)+")"));
                pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
                pitem.setItemMeta(pm);
                p.updateInventory();
                p.setItemInHand(pitem);

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openPoseidon(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&4&lHades Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
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
                lore.set(line, m.c("&cSkill: &4Hades (Level "+PickaxeLevel.getInstance().getInt(skill)+")"));
                pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
                pitem.setItemMeta(pm);
                p.updateInventory();
                p.setItemInHand(pitem);

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openHades(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&d&lAphrodite Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
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
                lore.set(line, m.c("&cSkill: &dAphrodite (Level "+PickaxeLevel.getInstance().getInt(skill)+")"));
                pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
                pitem.setItemMeta(pm);
                p.updateInventory();
                p.setItemInHand(pitem);

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openAphrodite(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&2&lAres Skill Tree: &a"+settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
            String previous = "";
            for(int i = 0; i < aresSkills().size(); i++){
                if(aresSkills().get(i).equals(skill)){
                    previous = aresSkills().get(aresSkills().indexOf(skill) - 1);
                }
            }
            if(!skillsUnlocked.contains(previous)) {
                p.sendMessage(m.c("&cError: Please unlock &a"+previous+" &cfirst."));
                return;
            }
            if(skillPoints < price) return;
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);
            if(skill.contains("Ares")) {
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
                lore.set(line, m.c("&cSkill: &2Ares (Level "+PickaxeLevel.getInstance().getInt(skill)+")"));
                pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
                pitem.setItemMeta(pm);
                p.updateInventory();
                p.setItemInHand(pitem);

            }
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            settings.savePlayerData();
            openAres(p);
        }

    }






}
