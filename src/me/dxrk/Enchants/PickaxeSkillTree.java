package me.dxrk.Enchants;

import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
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

import static java.lang.Integer.parseInt;

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
        List<String> skillsUnlocked = PlayerDataHandler.getPlayerData(p).getStringList("PickaxeSkillsUnlocked");
        if(skillsUnlocked.contains(name)){
            if(name.equals("Zeus (Level 5)") || name.equals("Poseidon (Level 5)") || name.equals("Hades (Level 5)") || name.equals("Ares (Level 5)") || name.equals("Aphrodite (Level 5)")){
                skill = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)1);
                sm.setDisplayName(m.c("&6Rebirth Pickaxe"));
                List<String> confirm = new ArrayList<>();
                confirm.add(m.c("&aClick if you want to rebirth your pickaxe"));
                sm.setLore(confirm);
                skill.setItemMeta(sm);
                return skill;
            }
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

        List<String> completed = PlayerDataHandler.getPlayerData(p).getStringList("CompletedPaths");

        ItemStack zeus = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)4);
        ItemMeta zm = zeus.getItemMeta();
        zm.setDisplayName(m.c("&e&lZeus Path"));
        if(!completed.contains("Zeus")) {
            lore.add(m.c("&7&oSelect this to confirm your skill path as &e&lskills."));
            lore.add(m.c("&7Provides a boost in gaining keys."));
        }
        else
            lore.add(m.c("&aCompleted."));
        zm.setLore(lore);
        zeus.setItemMeta(zm);
        skills.setItem(0, zeus);
        lore.clear();

        ItemStack poseidon = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
        ItemMeta pm = poseidon.getItemMeta();
        pm.setDisplayName(m.c("&9&lPoseidon Path"));
        if(!completed.contains("Poseidon")){
            lore.add(m.c("&7&oSelect this to confirm your skill path as &9&lPoseidon."));
            lore.add(m.c("&7Provides a boost in fortune."));
        }
        else
            lore.add(m.c("&aCompleted."));
        pm.setLore(lore);
        poseidon.setItemMeta(pm);
        skills.setItem(1, poseidon);
        lore.clear();

        ItemStack hades = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
        ItemMeta hm = hades.getItemMeta();
        hm.setDisplayName(m.c("&4&lHades Path"));
        if(!completed.contains("Hades")){
            lore.add(m.c("&7&oSelect this to confirm your skill path as &4&lhades."));
            lore.add(m.c("&7Provides a boost to your personal multi."));
        }
        else
            lore.add(m.c("&aCompleted."));
        hm.setLore(lore);
        hades.setItemMeta(hm);
        skills.setItem(2, hades);
        lore.clear();

        ItemStack aphrodite = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)6);
        ItemMeta am = aphrodite.getItemMeta();
        am.setDisplayName(m.c("&d&lAphrodite Path"));
        if(!completed.contains("Aphrodite")){
            lore.add(m.c("&7&oSelect this to confirm your skill path as &d&lAphrodite."));
            lore.add(m.c("&7Provides a boost to your pickaxe xp."));
        }
        else
            lore.add(m.c("&aCompleted."));
        am.setLore(lore);
        aphrodite.setItemMeta(am);
        skills.setItem(3, aphrodite);
        lore.clear();

        ItemStack ares = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)13);
        ItemMeta atm = ares.getItemMeta();
        atm.setDisplayName(m.c("&2&lAres Path"));
        if(!completed.contains("Ares")){
            lore.add(m.c("&7&oSelect this to confirm your skill path as &2&lAres."));
            lore.add(m.c("&7Provides a boost in gaining tokens."));
        }
        else
            lore.add(m.c("&aCompleted."));
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
        skills.add("250 raise in max level of keyfinder");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Zeus (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("+20% chance for x2 keys");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Zeus (Level 4)");
        skills.add("500 raise in max level of keyfinder");
        skills.add("+30% chance for x2 keys");
        skills.add("750 raise in max level of keyfinder");
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
        skills.add("1250 raise in max level of fortune");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Poseidon (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("8% Fortune Boost");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Poseidon (Level 4)");
        skills.add("1500 raise in max level of fortune");
        skills.add("12% Fortune Boost");
        skills.add("1750 raise in max level of fortune");
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
        skills.add("150 raise in max level of junkpile");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Hades (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("+100% Junkpile Multi Gain");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Hades (Level 4)");
        skills.add("250 raise in max level of junkpile");
        skills.add("+200% Junkpile Multi Gain");
        skills.add("350 raise in max level of junkpile");
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
        skills.add("500 raise in max level of xp finder");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Aphrodite (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("+300% Junkpile XP Gain");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Aphrodite (Level 4)");
        skills.add("1000 raise in max level of xp finder");
        skills.add("+500% Junkpile XP Gain");
        skills.add("1500 raise in max level of xp finder");
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
        skills.add("200 raise in max level of token finder");
        skills.add("Fortune Boost (Level 2)");
        skills.add("Luck Boost (Level 3)");
        skills.add("Token Bonus (Level 4)");
        skills.add("Ares (Level 3)");
        skills.add("Token Bonus (Level 5)");
        skills.add("Luck Boost (Level 4)");
        skills.add("Fortune Boost (Level 3)");
        skills.add("8% Token Boost");
        skills.add("Fortune Boost (Level 4)");
        skills.add("Luck Boost (Level 5)");
        skills.add("Token Bonus (Level 6)");
        skills.add("Fortune Boost (Level 5)");
        skills.add("Ares (Level 4)");
        skills.add("300 raise in max level of token finder");
        skills.add("12% Token Boost");
        skills.add("500 raise in max level of token finder");
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
        Inventory zeus = Bukkit.createInventory(null, 54, m.c("&e&lZeus Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            zeus.setItem(i, Spacer());
        }
        //Token Bonus
        zeus.setItem(9, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 2% token bonus."), 1));
        zeus.setItem(37, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 2% token bonus."), 4));
        zeus.setItem(29, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 2% token bonus."), 7));
        zeus.setItem(13, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 2% token bonus."), 12));
        zeus.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 2% token bonus."), 16));
        zeus.setItem(15, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 2% token bonus."), 20));
        //Luck Boost
        zeus.setItem(18, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 1));
        zeus.setItem(38, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 5));
        zeus.setItem(12, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 9));
        zeus.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 13));
        zeus.setItem(24, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        zeus.setItem(27, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 1));
        zeus.setItem(11, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 5));
        zeus.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 9));
        zeus.setItem(33, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 13));
        zeus.setItem(16, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 17));
        //Special buffs
        zeus.setItem(20, skillItem(p, "250 raise in max level of keyfinder", m.c("&7Each of these skills raises the max level of keyfinder."), 15));
        zeus.setItem(42, skillItem(p, "+20% chance for x2 keys", m.c("&7Each of these skills raises the chance of finding 2x keys."), 20));
        zeus.setItem(26, skillItem(p, "500 raise in max level of keyfinder", m.c("&7Each of these skills raises the max level of keyfinder."), 25));
        zeus.setItem(35, skillItem(p, "+30% chance for x2 keys", m.c("&7Each of these skills raises the chance of finding 2x keys."), 30));
        zeus.setItem(44, skillItem(p, "750 raise in max level of keyfinder", m.c("&7Each of these skills raises the max level of keyfinder."), 35));
        //Level ups / abilities
        zeus.setItem(0, skillItem(p, "Unlocked Zeus", m.c("&7Chose the Zeus path. Unlocks Thunderstorm and ThunderBolt Events."), 0));
        zeus.setItem(36, skillItem(p, "Zeus (Level 2)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(22, skillItem(p, "Zeus (Level 3)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(17, skillItem(p, "Zeus (Level 4)", m.c("&7Upgrades Zeus Events."), 0));
        zeus.setItem(53, skillItem(p, "Zeus (Level 5)", m.c("&7Upgrades Zeus Events and Unlocks an enchant."), 0));
        p.openInventory(zeus);
    }

    public static void openPoseidon(Player p){
        Inventory poseidon = Bukkit.createInventory(null, 54, m.c("&9&lPoseidon Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            poseidon.setItem(i, Spacer());
        }
        //Token Bonus
        poseidon.setItem(9, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 2% token bonus."), 1));
        poseidon.setItem(37, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 2% token bonus."), 4));
        poseidon.setItem(29, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 2% token bonus."), 7));
        poseidon.setItem(13, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 2% token bonus."), 12));
        poseidon.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 2% token bonus."), 16));
        poseidon.setItem(15, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 2% token bonus."), 20));
        //Luck Boost
        poseidon.setItem(18, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 1));
        poseidon.setItem(38, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 5));
        poseidon.setItem(12, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 9));
        poseidon.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 13));
        poseidon.setItem(24, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        poseidon.setItem(27, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 1));
        poseidon.setItem(11, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 5));
        poseidon.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 9));
        poseidon.setItem(33, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 13));
        poseidon.setItem(16, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 17));
        //Special buffs
        poseidon.setItem(20, skillItem(p, "1250 raise in max level of fortune", m.c("&7Each of these skills raises the max level of fortune."), 15));
        poseidon.setItem(42, skillItem(p, "8% Fortune Boost", m.c("&7Each of these skills gives an additional fortune bonus."), 20));
        poseidon.setItem(26, skillItem(p, "1500 raise in max level of fortune", m.c("&7Each of these skills raises the max level of fortune."), 25));
        poseidon.setItem(35, skillItem(p, "12% Fortune Boost", m.c("&7Each of these skills gives an additional fortune bonus."), 30));
        poseidon.setItem(44, skillItem(p, "1750 raise in max level of fortune", m.c("&7Each of these skills raises the max level of fortune."), 35));
        //Level ups / abilities
        poseidon.setItem(0, skillItem(p, "Unlocked Poseidon", m.c("&7Chose the Poseidon path. Unlocks Tsunami and Typhoon Events."), 0));
        poseidon.setItem(36, skillItem(p, "Poseidon (Level 2)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(22, skillItem(p, "Poseidon (Level 3)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(17, skillItem(p, "Poseidon (Level 4)", m.c("&7Upgrades Poseidon Events."), 0));
        poseidon.setItem(53, skillItem(p, "Poseidon (Level 5)", m.c("&7Upgrades Poseidon Events and Unlocks an enchant."), 0));
        p.openInventory(poseidon);
    }

    public static void openHades(Player p){
        Inventory hades = Bukkit.createInventory(null, 54, m.c("&4&lHades Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            hades.setItem(i, Spacer());
        }
        //Token Bonus
        hades.setItem(9, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 2% token bonus."), 1));
        hades.setItem(37, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 2% token bonus."), 4));
        hades.setItem(29, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 2% token bonus."), 7));
        hades.setItem(13, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 2% token bonus."), 12));
        hades.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 2% token bonus."), 16));
        hades.setItem(15, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 2% token bonus."), 20));
        //Luck Boost
        hades.setItem(18, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 1));
        hades.setItem(38, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 5));
        hades.setItem(12, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 9));
        hades.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 13));
        hades.setItem(24, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        hades.setItem(27, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 1));
        hades.setItem(11, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 5));
        hades.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 9));
        hades.setItem(33, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 13));
        hades.setItem(16, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 17));
        //Special buffs
        hades.setItem(20, skillItem(p, "150 raise in max level of junkpile", m.c("&7Each of these skills raises the max level of junkpile."), 15));
        hades.setItem(42, skillItem(p, "+100% Junkpile Multi Gain", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 20));
        hades.setItem(26, skillItem(p, "250 raise in max level of junkpile", m.c("&7Each of these skills raises the max level of junkpile."), 25));
        hades.setItem(35, skillItem(p, "+200% Junkpile Multi Gain", m.c("&7Each of these skills raises the chance to find Coupons to use on the store."), 30));
        hades.setItem(44, skillItem(p, "350 raise in max level of junkpile", m.c("&7Each of these skills raises the max level of junkpile."), 35));
        //Level ups / abilities
        hades.setItem(0, skillItem(p, "Unlocked Hades", m.c("&7Chose the Hades path. Unlocks Meteor Shower and Scorched Earth Events."), 0));
        hades.setItem(36, skillItem(p, "Hades (Level 2)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(22, skillItem(p, "Hades (Level 3)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(17, skillItem(p, "Hades (Level 4)", m.c("&7Upgrades Hades Events."), 0));
        hades.setItem(53, skillItem(p, "Hades (Level 5)", m.c("&7Upgrades Hades Events and Unlocks an enchant."), 0));
        p.openInventory(hades);
    }

    public static void openAres(Player p){
        Inventory ares = Bukkit.createInventory(null,54, m.c("&2&lAres Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            ares.setItem(i, Spacer());
        }
        //Token Bonus
        ares.setItem(9, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 2% token bonus."), 1));
        ares.setItem(37, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 2% token bonus."), 4));
        ares.setItem(29, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 2% token bonus."), 7));
        ares.setItem(13, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 2% token bonus."), 12));
        ares.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 2% token bonus."), 16));
        ares.setItem(15, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 2% token bonus."), 20));
        //Luck Boost
        ares.setItem(18, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 1));
        ares.setItem(38, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 5));
        ares.setItem(12, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 9));
        ares.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 13));
        ares.setItem(24, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        ares.setItem(27, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 1));
        ares.setItem(11, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 5));
        ares.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 9));
        ares.setItem(33, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 13));
        ares.setItem(16, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 17));
        //Special buffs
        ares.setItem(20, skillItem(p, "200 raise in max level of token finder", m.c("&7Each of these skills raises the max level of token finder."), 15));
        ares.setItem(42, skillItem(p, "8% Token Boost", m.c("&7Each of these skills gives an additional token bonus."), 20));
        ares.setItem(26, skillItem(p, "300 raise in max level of token finder", m.c("&7Each of these skills raises the max level of token finder."), 25));
        ares.setItem(35, skillItem(p, "12% Token Boost", m.c("&7Each of these skills gives an additional token bonus."), 30));
        ares.setItem(44, skillItem(p, "500 raise in max level of token finder", m.c("&7Each of these skills raises the max level of token finder."), 35));
        //Level ups / abilities
        ares.setItem(0, skillItem(p, "Unlocked Ares", m.c("&7Chose the ares path. Unlocks War Torn and Bloodshed Events."), 0));
        ares.setItem(36, skillItem(p, "Ares (Level 2)", m.c("&7Upgrades Ares Events."), 0));
        ares.setItem(22, skillItem(p, "Ares (Level 3)", m.c("&7Upgrades Ares Events."), 0));
        ares.setItem(17, skillItem(p, "Ares (Level 4)", m.c("&7Upgrades Ares Events."), 0));
        ares.setItem(53, skillItem(p, "Ares (Level 5)", m.c("&7Upgrades Ares Events and Unlocks an enchant."), 0));
        p.openInventory(ares);
    }

    public static void openAphrodite(Player p){
        Inventory aphrodite = Bukkit.createInventory(null, 54, m.c("&d&lAphrodite Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"));
        for(int i = 0; i < 54; i++){
            aphrodite.setItem(i, Spacer());
        }
        //Token Bonus
        aphrodite.setItem(9, skillItem(p, "Token Bonus (Level 1)", m.c("&7Each of these skills gives an additional 2% token bonus."), 1));
        aphrodite.setItem(37, skillItem(p, "Token Bonus (Level 2)", m.c("&7Each of these skills gives an additional 2% token bonus."), 4));
        aphrodite.setItem(29, skillItem(p, "Token Bonus (Level 3)", m.c("&7Each of these skills gives an additional 2% token bonus."), 7));
        aphrodite.setItem(13, skillItem(p, "Token Bonus (Level 4)", m.c("&7Each of these skills gives an additional 2% token bonus."), 12));
        aphrodite.setItem(31, skillItem(p, "Token Bonus (Level 5)", m.c("&7Each of these skills gives an additional 2% token bonus."), 16));
        aphrodite.setItem(15, skillItem(p, "Token Bonus (Level 6)", m.c("&7Each of these skills gives an additional 2% token bonus."), 20));
        //Luck Boost
        aphrodite.setItem(18, skillItem(p, "Luck Boost (Level 1)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 1));
        aphrodite.setItem(38, skillItem(p, "Luck Boost (Level 2)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 5));
        aphrodite.setItem(12, skillItem(p, "Luck Boost (Level 3)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 9));
        aphrodite.setItem(40, skillItem(p, "Luck Boost (Level 4)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 13));
        aphrodite.setItem(24, skillItem(p, "Luck Boost (Level 5)", m.c("&7Each of these skills gives an additional 2% chance for enchants to proc."), 17)); // pickaxe level 275 to max
        //Fortune
        aphrodite.setItem(27, skillItem(p, "Fortune Boost (Level 1)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 1));
        aphrodite.setItem(11, skillItem(p, "Fortune Boost (Level 2)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 5));
        aphrodite.setItem(41, skillItem(p, "Fortune Boost (Level 3)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 9));
        aphrodite.setItem(33, skillItem(p, "Fortune Boost (Level 4)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 13));
        aphrodite.setItem(16, skillItem(p, "Fortune Boost (Level 5)", m.c("&7Each of these skills gives a 2% effective boost to fortune."), 17));
        //Special buffs
        aphrodite.setItem(20, skillItem(p, "500 raise in max level of xp finder", m.c("&7Each of these skills raises the max level of xp finder."), 15));
        aphrodite.setItem(42, skillItem(p, "+300% Junkpile XP Gain", m.c("&7Each of these skills raises the amount of XP given by junkpile."), 20));
        aphrodite.setItem(26, skillItem(p, "1000 raise in max level of xp finder", m.c("&7Each of these skills raises the max level of xp finder."), 25));
        aphrodite.setItem(35, skillItem(p, "+500% Junkpile XP Gain", m.c("&7Each of these skills raises the amount of XP given by junkpile."), 30));
        aphrodite.setItem(44, skillItem(p, "1500 raise in max level of xp finder", m.c("&7Each of these skills raises the max level of xp finder."), 35));
        //Level ups / abilities
        aphrodite.setItem(0, skillItem(p, "Unlocked Aphrodite", m.c("&7Chose the aphrodite path. Unlocks Allure and Strong Desire Events."), 0));
        aphrodite.setItem(36, skillItem(p, "Aphrodite (Level 2)", m.c("&7Upgrades Aphrodite Events."), 0));
        aphrodite.setItem(22, skillItem(p, "Aphrodite (Level 3)", m.c("&7Upgrades Aphrodite Events."), 0));
        aphrodite.setItem(17, skillItem(p, "Aphrodite (Level 4)", m.c("&7Upgrades Aphrodite Events."), 0));
        aphrodite.setItem(53, skillItem(p, "Aphrodite (Level 5)", m.c("&7Upgrades Aphrodite Events and Unlocks an enchant."), 0));
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

        PlayerDataHandler.getPlayerData(p).set("PickaxeSkill", path);
        PlayerDataHandler.getPlayerData(p).set("PickaxeSkillLevel", 1);
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
        PlayerDataHandler.getPlayerData(p).set("PickaxeSkillsUnlocked", skills);
        PlayerDataHandler.savePlayerData(p);
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

    public void openConfirmation(Player p){
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&4&lAre you sure?"));
        inv.setItem(1,Spacer());
        inv.setItem(3,Spacer());

        ItemStack no = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)14);
        ItemMeta nometa = no.getItemMeta();
        nometa.setDisplayName(m.c("&c&lNo!"));
        no.setItemMeta(nometa);
        inv.setItem(0,no);

        ItemStack yes = new ItemStack(Material.STAINED_GLASS_PANE,1,(short)13);
        ItemMeta yesmeta = yes.getItemMeta();
        yesmeta.setDisplayName(m.c("&a&lYes!"));
        yes.setItemMeta(yesmeta);
        inv.setItem(4,yes);

        ItemStack info = new ItemStack(Material.PAPER,1);
        ItemMeta infometa = info.getItemMeta();
        infometa.setDisplayName(m.c("&6Rebirth:"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&cRebirthing your pickaxe removes all your enchants."));
        lore.add(m.c("&cResets your tokens to 0."));
        lore.add(m.c("&cResets your pickaxe level back to 0."));
        lore.add(m.c("&cUnlocks a brand new enchant depending on your path."));
        lore.add(m.c("&cAnd you may choose another path again."));
        infometa.setLore(lore);
        info.setItemMeta(infometa);
        inv.setItem(2,info);

        p.openInventory(inv);
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
            List<String> completed = PlayerDataHandler.getPlayerData(p).getStringList("CompletedPaths");

            if(e.getSlot() == 0 && !completed.contains("Zeus")){
                selectPath(p, "Zeus", "&f");
            }
            if(e.getSlot() == 1 && !completed.contains("Poseidon")){
                selectPath(p, "Poseidon", "&9");
            }
            if(e.getSlot() == 2 && !completed.contains("Hades")){
                selectPath(p, "Hades", "&4");
            }
            if(e.getSlot() == 3 && !completed.contains("Aphrodite")){
                selectPath(p, "Aphrodite", "&d");
            }
            if(e.getSlot() == 4 && !completed.contains("Ares")){
                selectPath(p, "Ares", "&2");
            }
        }
        if(e.getInventory().getName().equals(m.c("&e&lZeus Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 1){
                openConfirmation(p);
                return;
            }
            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = PlayerDataHandler.getPlayerData(p).getStringList("PickaxeSkillsUnlocked");
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
            PlayerDataHandler.getPlayerData(p).set("PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);

            String[] firstword = skill.split(" ");
            if(firstword[0].equals("Token")){
                double tokenboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillTokenBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillTokenBoost",tokenboost+0.02);
            }
            if(firstword[0].equals("Luck")){
                double luckboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillLuckBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillLuckBoost",luckboost+0.02);
            }
            if(firstword[0].equals("Fortune")){
                double fortuneboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillFortuneBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillFortuneBoost",fortuneboost+0.02);
            }
            if(firstword[0].equals("250") || firstword[0].equals("500") || firstword[0].equals("750")){
                int maxlevel = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".KFMaxLevelRaise");
                int maxleveladd = parseInt(firstword[0]);
                PlayerDataHandler.getPlayerData(p).set("KFMaxLevelRaise",maxlevel+maxleveladd);
            }
            if(firstword[0].equals("+20%")){
                double keyboost = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".SkillKeyBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillKeyBoost",keyboost+20);
            }
            if(firstword[0].equals("+30%")){
                double keyboost = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".SkillKeyBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillKeyBoost",keyboost+30);
            }
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
            PlayerDataHandler.getPlayerData(p).set("PickaxeSkillsUnlocked", skillsUnlocked);
            PlayerDataHandler.savePlayerData(p);
            openZeus(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&9&lPoseidon Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 1){
                openConfirmation(p);
                return;
            }

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = PlayerDataHandler.getPlayerData(p).getStringList("PickaxeSkillsUnlocked");
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
            PlayerDataHandler.getPlayerData(p).set("PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);

            String[] firstword = skill.split(" ");
            if(firstword[0].equals("Token")){
                double tokenboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillTokenBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillTokenBoost",tokenboost+0.02);
            }
            if(firstword[0].equals("Luck")){
                double luckboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillLuckBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillLuckBoost",luckboost+0.02);
            }
            if(firstword[0].equals("Fortune")){
                double fortuneboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillFortuneBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillFortuneBoost",fortuneboost+0.02);
            }
            if(firstword[0].equals("1250") || firstword[0].equals("1500") || firstword[0].equals("1750")){
                int maxlevel = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".FortuneMaxLevelRaise");
                int maxleveladd = parseInt(firstword[0]);
                PlayerDataHandler.getPlayerData(p).set("FortuneMaxLevelRaise",maxlevel+maxleveladd);
            }
            if(firstword[0].equals("8%")){
                double fortuneboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillFortuneBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillFortuneBoost", fortuneboost+0.08);
            }
            if(firstword[0].equals("12%")){
                double fortuneboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillFortuneBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillFortuneBoost", fortuneboost+0.12);
            }
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
            PlayerDataHandler.getPlayerData(p).set("PickaxeSkillsUnlocked", skillsUnlocked);
            PlayerDataHandler.savePlayerData(p);
            openPoseidon(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&4&lHades Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 1){
                openConfirmation(p);
                return;
            }

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = PlayerDataHandler.getPlayerData(p).getInt("PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = PlayerDataHandler.getPlayerData(p).getStringList("PickaxeSkillsUnlocked");
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
            PlayerDataHandler.getPlayerData(p).set("PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);

            String[] firstword = skill.split(" ");
            if(firstword[0].equals("Token")){
                double tokenboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillTokenBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillTokenBoost",tokenboost+0.02);
            }
            if(firstword[0].equals("Luck")){
                double luckboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillLuckBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillLuckBoost",luckboost+0.02);
            }
            if(firstword[0].equals("Fortune")){
                double fortuneboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillFortuneBoost");
                PlayerDataHandler.getPlayerData(p).set("SkillFortuneBoost",fortuneboost+0.02);
            }
            if(firstword[0].equals("150") || firstword[0].equals("250") || firstword[0].equals("350")){
                int maxlevel = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".JunkpileMaxLevelRaise");
                int maxleveladd = parseInt(firstword[0]);
                PlayerDataHandler.getPlayerData(p).set("JunkpileMaxLevelRaise",maxlevel+maxleveladd);
            }
            if(firstword[0].equals("+100%")){
                double skilljunkpilemultiboost = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".SkillJunkpileMultiBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillJunkpileMultiBoost",skilljunkpilemultiboost+1);
            }
            if(firstword[0].equals("+200%")){
                double skilljunkpilemultiboost = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".SkillJunkpileMultiBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillJunkpileMultiBoost",skilljunkpilemultiboost+2);
            }
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
            PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            PlayerDataHandler.savePlayerData(p);
            openHades(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&d&lAphrodite Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 1){
                openConfirmation(p);
                return;
            }

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = PlayerDataHandler.getPlayerData(p).getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
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
            PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);

            String[] firstword = skill.split(" ");
            if(firstword[0].equals("Token")){
                double tokenboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillTokenBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillTokenBoost",tokenboost+0.02);
            }
            if(firstword[0].equals("Luck")){
                double luckboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillLuckBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillLuckBoost",luckboost+0.02);
            }
            if(firstword[0].equals("Fortune")){
                double fortuneboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillFortuneBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillFortuneBoost",fortuneboost+0.02);
            }
            if(firstword[0].equals("500") || firstword[0].equals("1000") || firstword[0].equals("1500")){
                int maxlevel = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".XPFMaxLevelRaise");
                int maxleveladd = parseInt(firstword[0]);
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".XPFMaxLevelRaise",maxlevel+maxleveladd);
            }
            if(firstword[0].equals("+300%")){
                int junkpilexpboost = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".SkillJunkpileXPBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillKeyBoost",junkpilexpboost+3);
            }
            if(firstword[0].equals("+500%")){
                int junkpilexpboost = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".SkillJunkpileXPBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillKeyBoost",junkpilexpboost+5);
            }
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
            PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            PlayerDataHandler.savePlayerData(p);
            openAphrodite(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&2&lAres Skill Tree: &a"+PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId().toString()+".PickaxeSkillPoints")+" Skill Points"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 5) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 1){
                openConfirmation(p);
                return;
            }

            int price = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(2)));
            int skillPoints = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
            String skill = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            List<String> skillsUnlocked = PlayerDataHandler.getPlayerData(p).getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
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
            PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints-price);

            skillsUnlocked.add(skill);
            String[] firstword = skill.split(" ");
            if(firstword[0].equals("Token")){
                double tokenboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillTokenBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillTokenBoost",tokenboost+0.02);
            }
            if(firstword[0].equals("Luck")){
                double luckboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillLuckBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillLuckBoost",luckboost+0.02);
            }
            if(firstword[0].equals("Fortune")){
                double fortuneboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillFortuneBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillFortuneBoost",fortuneboost+0.02);
            }
            if(firstword[0].equals("200") || firstword[0].equals("300") || firstword[0].equals("500")){
                int maxlevel = PlayerDataHandler.getPlayerData(p).getInt(p.getUniqueId()+".TFMaxLevelRaise");
                int maxleveladd = parseInt(firstword[0]);
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".TFMaxLevelRaise",maxlevel+maxleveladd);
            }
            if(firstword[0].equals("8%")){
                double tokenboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillTokenBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillTokenBoost", tokenboost+0.08);
            }
            if(firstword[0].equals("12%")){
                double tokenboost = PlayerDataHandler.getPlayerData(p).getDouble(p.getUniqueId()+".SkillTokenBoost");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".SkillTokenBoost", tokenboost+0.12);
            }
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
            PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skillsUnlocked);
            PlayerDataHandler.savePlayerData(p);
            openAres(p);
        }
        if(e.getClickedInventory().getName().equals(m.c("&4&lAre you sure?"))){
            e.setCancelled(true);
            if(e.getCurrentItem().getType().equals(Material.STAINED_GLASS_PANE) && e.getCurrentItem().getData().getData() == 0) return;
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(m.c("&c&lNo!"))){
                p.closeInventory();
                return;
            }
            if(e.getCurrentItem().getItemMeta().getDisplayName().equals(m.c("&a&lYes!"))){
                Tokens.getInstance().setTokens(p,0);

                String path = PlayerDataHandler.getPlayerData(p).get("PickaxeSkill").toString();
                List<String> completed = PlayerDataHandler.getPlayerData(p).getStringList(p.getUniqueId().toString()+".CompletedPaths");
                completed.add(path);
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".CompletedPaths", completed);
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkill", "None");
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkillPoints", 0);
                PickaxeLevel.getInstance().resetPickaxe(p);
                List<String> skills = new ArrayList<>();
                PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".PickaxeSkillsUnlocked", skills);
                PlayerDataHandler.savePlayerData(p);

                p.closeInventory();
                /*
                Clear pickaxe enchants
                Reset players pick level to 0
                hopefully token/luck/fortune buffs stay
                */
            }
        }
    }


}
