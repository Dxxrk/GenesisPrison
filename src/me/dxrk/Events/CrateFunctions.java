package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrateFunctions {

   private static Methods m = Methods.getInstance();


    public static String randomKey() {
        Random r = new Random();
        int ri = r.nextInt(6);
        switch(ri){
            case 0:
                return m.c("&7&lAlpha &7Key");
            case 1:
                return m.c("&c&lBeta &7Key");
            case 2:
                return m.c("&4&lOmega &7Key");
            case 3:
                return m.c("&e&lToken &7Key");
            case 4:
                return m.c("&4&l&ki&f&lSeasonal&4&l&ki&r &7Key");
            case 5:
                return m.c("&5&lCommunity &7Key");
        }

        return "";

    }


    public static ItemStack GenesisCrate() {
        ItemStack gcrate = new ItemStack(Material.ENDER_CHEST);
        ItemMeta gm = gcrate.getItemMeta();
        gm.setDisplayName(m.c("&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Upon placing this item, you will recieve 8 random items"));
        lore.add(m.c("&7From the list below, all rewards are randomly selected."));
        lore.add(m.c("&a&lRewards:"));
        lore.add(m.c(" "));
        lore.add(m.c("&e&l&m--&e&lTokens&m--"));
        lore.add(m.c("&e⛀3,000,000-10,000,000"));
        lore.add(m.c(" "));
        lore.add(m.c("&c&l&m--&c&lKeys&m--"));
        lore.add(m.c("&c1-10x Random Keys"));
        lore.add(m.c("&c3x Rank Keys"));
        lore.add(m.c(" "));
        lore.add(m.c("&5&l&m--&5&lRanks&m--"));
        lore.add(m.c("&e&lOlympian Rank"));
        lore.add(m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank"));
        lore.add(m.c(" "));
        lore.add(m.c("&6&l&m--&6&lItems&m--"));
        lore.add(m.c("&51-5x Epic Trinkets"));
        lore.add(m.c("&61-3x Legendary Trinkets"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&m--&f&lTroll&m--"));
        lore.add(m.c("&fMjölnir"));
        lore.add(m.c("&fPack-a-Punch"));
        lore.add(m.c("&fFree Lunch :)"));
        lore.add(m.c(" "));
        lore.add(m.c("&d&l&m--&5&lMisc.&m--"));
        lore.add(m.c("&dItem Rename"));
        lore.add(m.c("&d3x Currency Boost"));
        lore.add(m.c("&d2x XP Boost"));
        lore.add(m.c("&d2x Currency Boost"));
        gm.setLore(lore);
        gcrate.setItemMeta(gm);

        return gcrate;
    }
    public static ItemStack ContrabandCrate() {
        ItemStack gcrate = new ItemStack(Material.ENDER_CHEST);
        ItemMeta gm = gcrate.getItemMeta();
        gm.setDisplayName(m.c("&c&lContraband Crate"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Upon placing this item, you will recieve 8 random items"));
        lore.add(m.c("&7From the list below, all rewards are randomly selected."));
        lore.add(m.c("&a&lRewards:"));
        lore.add(m.c(" "));
        lore.add(m.c("&e&l&m--&e&lTokens&m--"));
        lore.add(m.c("&e⛀500,000-2,000,000"));
        lore.add(m.c(" "));
        lore.add(m.c("&c&l&m--&c&lKeys&m--"));
        lore.add(m.c("&c1-5x Random Keys"));
        lore.add(m.c("&c1x Rank Keys"));
        lore.add(m.c(" "));
        lore.add(m.c("&5&l&m--&5&lRanks&m--"));
        lore.add(m.c("&c&lHero Rank"));
        lore.add(m.c("&5&lDemi-God Rank"));
        lore.add(m.c("&3&lTitan Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&6&l&m--&6&lItems&m--"));
        lore.add(m.c("&b1-3x Rare Trinkets"));
        lore.add(m.c("&51x Epic Trinkets"));
        lore.add(m.c(" "));
        lore.add(m.c("&d&l&m--&5&lMisc.&m--"));
        lore.add(m.c("&dItem Rename"));
        lore.add(m.c("&d1.5x Currency Boost"));
        lore.add(m.c("&d2x XP Boost"));
        lore.add(m.c("&d2x Currency Boost"));
        lore.add(m.c("&d2.5-3.5 Multi"));
        gm.setLore(lore);
        gcrate.setItemMeta(gm);

        return gcrate;
    }

    public static ItemStack Reward(String crate){
        ItemStack reward = new ItemStack(Material.PAPER);
        ItemMeta rm = reward.getItemMeta();
        if(crate.equals("genesis")){
            Random r = new Random();
            int ri = r.nextInt(200);

            if(ri <= 40){
                int tmin = 5000000;
                int tmax = 15000000;
                int tokens = r.nextInt(tmax - tmin)+ tmin;
                rm.setDisplayName(m.c("&b"+ Main.formatAmt(tokens)+" Tokens"));
                reward.setType(Material.PRISMARINE_CRYSTALS);
                List<String> lore = new ArrayList<>();
                lore.add("tokens add %PLAYER% "+tokens);
                rm.setLore(lore);
            }
            if(ri > 40 && ri <= 95){
                int tmin = 1;
                int tmax = 10;
                int keys = r.nextInt(tmax - tmin)+ tmin;
                rm.setDisplayName(m.c("&e"+keys+"x ")+randomKey());
                reward.setType(Material.TRIPWIRE_HOOK);
                List<String> lore = new ArrayList<>();
                String key = ChatColor.stripColor(rm.getDisplayName()).split(" ")[1];
                if(key.contains("Seasonal")){
                    lore.add("cratekey %PLAYER% seasonal" + keys);
                } else {
                    lore.add("cratekey %PLAYER% " + key + " " + keys);
                }
                rm.setLore(lore);
            }
            if(ri > 95 && ri <=100) {
                rm.setDisplayName(m.c("&e3x &3&lRank &7Key"));
                reward.setType(Material.TRIPWIRE_HOOK);
                List<String> lore = new ArrayList<>();
                lore.add("cratekey %PLAYER% rank 3");
                rm.setLore(lore);
            }
            if(ri > 100 && ri <=125){
                int tmin = 1;
                int tmax = 3;
                int trinkets = r.nextInt(tmax - tmin)+ tmin;
                rm.setDisplayName(m.c("&e"+trinkets+"x &6Legendary Trinket"));
                reward.setType(Material.GOLD_NUGGET);
                List<String> lore = new ArrayList<>();
                lore.add("givetrinket %PLAYER% legendary "+trinkets);
                rm.setLore(lore);
            }
            if(ri > 125 && ri <=150) {
                int tmin = 1;
                int tmax = 5;
                int trinkets = r.nextInt(tmax - tmin)+ tmin;
                rm.setDisplayName(m.c("&e"+trinkets+"x &5Epic Trinket"));
                reward.setType(Material.GOLD_NUGGET);
                List<String> lore = new ArrayList<>();
                lore.add("givetrinket %PLAYER% epic "+trinkets);
                rm.setLore(lore);
            }
            if(ri > 150 && ri <=160){
                rm.setDisplayName(m.c("&e&lOlympian Rank"));
                reward.setType(Material.NETHER_STAR);
                List<String> lore = new ArrayList<>();
                lore.add("giverank %PLAYER% &e&lOlympian Rank");
                rm.setLore(lore);
            }
            if(ri == 161){
                rm.setDisplayName(m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank"));
                reward.setType(Material.NETHER_STAR);
                List<String> lore = new ArrayList<>();
                lore.add("giverank %PLAYER% &4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank");
                rm.setLore(lore);
            }
            if(ri > 161 && ri <=190) {
                Random misc = new Random();
                int misci = misc.nextInt(4);
                List<String> lore = new ArrayList<>();
                switch(misci){
                    case 0:
                        ItemStack sellBoost = new ItemStack(Material.POTION, 1, (short)8260);
                        ItemMeta sm = sellBoost.getItemMeta();
                        sm.setDisplayName(m.c("&f&l3x Currency Boost"));
                        lore.add("giveboost sell %PLAYER% 3 7200");
                        sm.setLore(lore);
                        sellBoost.setItemMeta(sm);
                        lore.clear();
                        return sellBoost;
                    case 1:
                        ItemStack sellBoost1 = new ItemStack(Material.POTION, 1, (short)8260);
                        ItemMeta sm1 = sellBoost1.getItemMeta();
                        sm1.setDisplayName(m.c("&f&l2x Currency Boost"));
                        lore.add("giveboost sell %PLAYER% 2 7200");
                        sm1.setLore(lore);
                        sellBoost1.setItemMeta(sm1);
                        lore.clear();
                        return sellBoost1;
                    case 2:
                        reward.setType(Material.EXP_BOTTLE);
                        rm.setDisplayName(m.c("&f&l2x XP Boost"));
                        lore.add("giveboost xp %PLAYER% 2 7200");
                        rm.setLore(lore);
                        lore.clear();
                    case 3:
                        reward.setType(Material.PAPER);
                        rm.setDisplayName(m.c("&bRename Paper"));
                        lore.add("renamepaper %PLAYER%");
                        rm.setLore(lore);
                        lore.clear();


                }
            }
            if(ri > 190) {
                Random misc = new Random();
                int misci = misc.nextInt(3);
                List<String> lore = new ArrayList<>();
                switch(misci){
                    case 0:
                        reward.setType(Material.MUSHROOM_SOUP);
                        rm.setDisplayName(m.c("&2Free Lunch :)"));
                    case 1:
                        reward.setType(Material.IRON_AXE);
                        rm.setDisplayName(m.c("&e&l&ki&7&lMjölnir&e&l&ki&r"));
                        reward.addEnchantment(Enchantment.DURABILITY, 3);
                    case 2:
                        reward.setType(Material.DISPENSER);
                        rm.setDisplayName(m.c("&5&lPack-a-Punch"));
                        reward.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5);
                }
            }

        }
        if(crate.equals("contraband")) {
            Random r = new Random();
            int ri = r.nextInt(100);

            if(ri <= 30){
                int tmin = 500000;
                int tmax = 2000000;
                int tokens = r.nextInt(tmax - tmin)+ tmin;
                rm.setDisplayName(m.c("&b"+ Main.formatAmt(tokens)+" Tokens"));
                reward.setType(Material.PRISMARINE_CRYSTALS);
                List<String> lore = new ArrayList<>();
                lore.add("tokens add %PLAYER% "+tokens);
                rm.setLore(lore);
            }
            if(ri > 30 && ri <= 55){
                int tmin = 1;
                int tmax = 5;
                int keys = r.nextInt(tmax - tmin)+ tmin;
                rm.setDisplayName(m.c("&e"+keys+"x ")+randomKey());
                reward.setType(Material.TRIPWIRE_HOOK);
                List<String> lore = new ArrayList<>();
                String key = ChatColor.stripColor(rm.getDisplayName()).split(" ")[1];
                if(key.contains("Seasonal")){
                    lore.add("cratekey %PLAYER% seasonal" + keys);
                } else {
                    lore.add("cratekey %PLAYER% " + key + " " + keys);
                }
                rm.setLore(lore);
            }
            if(ri == 56) {
                rm.setDisplayName(m.c("&e1x &3&lRank &7Key"));
                reward.setType(Material.TRIPWIRE_HOOK);
                List<String> lore = new ArrayList<>();
                lore.add("cratekey %PLAYER% rank 1");
                rm.setLore(lore);
            }
            if(ri > 56 && ri <=71){
                int tmin = 1;
                int tmax = 3;
                int trinkets = r.nextInt(tmax - tmin)+ tmin;
                rm.setDisplayName(m.c("&e"+trinkets+"x &bRare Trinket"));
                reward.setType(Material.GOLD_NUGGET);
                List<String> lore = new ArrayList<>();
                lore.add("givetrinket %PLAYER% rare "+trinkets);
                rm.setLore(lore);
            }
            if(ri > 71 && ri <=81) {

                rm.setDisplayName(m.c("&e1x &5Epic Trinket"));
                reward.setType(Material.GOLD_NUGGET);
                List<String> lore = new ArrayList<>();
                lore.add("givetrinket %PLAYER% epic 1");
                rm.setLore(lore);
            }
            if(ri > 81 && ri <=85){
                rm.setDisplayName(m.c("&c&lHero Rank"));
                reward.setType(Material.NETHER_STAR);
                List<String> lore = new ArrayList<>();
                lore.add("giverank %PLAYER% &c&lHero Rank");
                rm.setLore(lore);
            }
            if(ri > 85 && ri <= 88){
                rm.setDisplayName(m.c("&5&lDemi-God Rank"));
                reward.setType(Material.NETHER_STAR);
                List<String> lore = new ArrayList<>();
                lore.add("giverank %PLAYER% &5&lDemi-God Rank");
                rm.setLore(lore);
            }
            if(ri > 88 && ri <=90){
                rm.setDisplayName(m.c("&3&lTitan Rank"));
                reward.setType(Material.NETHER_STAR);
                List<String> lore = new ArrayList<>();
                lore.add("giverank %PLAYER% &3&lTitan Rank");
                rm.setLore(lore);
            }
            if(ri > 90) {
                Random misc = new Random();
                int misci = misc.nextInt(5);
                List<String> lore = new ArrayList<>();
                switch(misci){
                    case 0:
                        ItemStack sellBoost = new ItemStack(Material.POTION, 1, (short)8260);
                        ItemMeta sm = sellBoost.getItemMeta();
                        sm.setDisplayName(m.c("&f&l3x Currency Boost"));
                        lore.add("giveboost sell %PLAYER% 1.5 1800");
                        sm.setLore(lore);
                        sellBoost.setItemMeta(sm);
                        lore.clear();
                        return sellBoost;
                    case 1:
                        ItemStack sellBoost1 = new ItemStack(Material.POTION, 1, (short)8260);
                        ItemMeta sm1 = sellBoost1.getItemMeta();
                        sm1.setDisplayName(m.c("&f&l2x Currency Boost"));
                        lore.add("giveboost sell %PLAYER% 2 900");
                        sm1.setLore(lore);
                        sellBoost1.setItemMeta(sm1);
                        lore.clear();
                        return sellBoost1;
                    case 2:
                        reward.setType(Material.EXP_BOTTLE);
                        rm.setDisplayName(m.c("&f&l2x XP Boost"));
                        lore.add("giveboost xp %PLAYER% 2 1200");
                        rm.setLore(lore);
                        lore.clear();
                    case 3:
                        reward.setType(Material.PAPER);
                        rm.setDisplayName(m.c("&bRename Paper"));
                        lore.add("renamepaper %PLAYER%");
                        rm.setLore(lore);
                        lore.clear();
                    case 4:
                        double min = 2.5;
                        double max = 3.5;
                        double multi = Math.round((min + (max - min) * r.nextDouble())*10)/10.0;
                        reward.setType(Material.EMERALD);
                        rm.setDisplayName(m.c("&a"+multi+" Multi"));
                        lore.add("multi add %PLAYER% "+multi);
                        rm.setLore(lore);
                        lore.clear();

                }
            }
        }


        reward.setItemMeta(rm);
        return reward;
    }





}
