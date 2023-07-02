package me.dxrk.Events;

import me.dxrk.Main.Methods;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CrateFunctions {

    private static Methods m = Methods.getInstance();


    public static String randomKey() {
        Random r = new Random();
        int ri = r.nextInt(125);
        if (ri <= 25) {
            return m.c("&7&lAlpha &7Key");
        } else if (ri <= 50) {
            return m.c("&c&lBeta &7Key");
        } else if (ri <= 75) {
            return m.c("&4&lOmega &7Key");
        } else if (ri <= 100) {
            return m.c("&e&lToken &7Key");
        } else if (ri <= 105) {
            return m.c("&4&l&ki&f&lSeasonal&4&l&ki&r &7Key");
        } else {
            return m.c("&5&lCommunity &7Key");
        }
    }

    public static String randomKey2() {
        Random r = new Random();
        int ri = r.nextInt(5);
        switch (ri) {
            case 0:
                return m.c("&7&lAlpha &7Key");
            case 1:
                return m.c("&c&lBeta &7Key");
            case 2:
                return m.c("&4&lOmega &7Key");
            case 3:
                return m.c("&e&lToken &7Key");
            case 4:
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
        lore.add(m.c("&7These will be Common, Rare, or Epic and 1 guaranteed Legendary"));
        lore.add(m.c("&a&lCommon Rewards:"));
        lore.add(m.c("&a• &e⛀250,000 Tokens"));
        lore.add(m.c("&a• &a⬥12,500 Gems"));
        lore.add(m.c("&a• &e15x &7&lAlpha &7Keys"));
        lore.add(m.c("&a• &e10x &c&lBeta &7Keys"));
        lore.add(m.c("&a• &e5x &4&lOmega &7Key"));
        lore.add(m.c("&a• &a&lVIP Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&b&lRare Rewards:"));
        lore.add(m.c("&b• &e⛀500,000 Tokens"));
        lore.add(m.c("&b• &a⬥25,000 Gems"));
        lore.add(m.c("&b• &e15x &c&lBeta &7Keys"));
        lore.add(m.c("&b• &e10x &4&lOmega &7Keys"));
        lore.add(m.c("&b• &e3x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
        lore.add(m.c("&b• &6&lMVP Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&5&lEpic Rewards:"));
        lore.add(m.c("&5• &e⛀1,000,000 Tokens"));
        lore.add(m.c("&5• &e5x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Keys"));
        lore.add(m.c("&5• &e2.0x Token Boost"));
        lore.add(m.c("&5• &c&lHero Rank"));
        lore.add(m.c("&5• &5&lDemi-God Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&kii&6&lLegendary Rewards&f&l&kii&r"));
        lore.add(m.c("&6• &e⛀1,500,000 Tokens"));
        lore.add(m.c("&6• &e3.0x Token Boost"));
        lore.add(m.c("&6• &e3x &3&lRank &7Key"));
        lore.add(m.c("&6• &e3.0x Sell Boost"));
        lore.add(m.c("&6• &c&l2x Monster &7&lEgg"));
        lore.add(m.c("&6• &e&lOlympian Rank"));


        gm.setLore(lore);
        gcrate.setItemMeta(gm);

        return gcrate;
    }

    public static ItemStack AprilCrate() {
        ItemStack gcrate = new ItemStack(Material.ENDER_CHEST);
        ItemMeta gm = gcrate.getItemMeta();
        gm.setDisplayName(m.c("&f&l&k[&7&l*&f&l&k]&r &b&lApril Crate &f&l&k[&7&l*&f&l&k]&r"));
        List<String> lore = new ArrayList<>();


        lore.add(m.c("&7Upon placing this item, you will recieve 8 random items"));
        lore.add(m.c("&7These will be Common, Rare, or Epic and 1 guaranteed Legendary"));
        lore.add(m.c("&a&lCommon Rewards:"));
        lore.add(m.c("&a• &e⛀500,000 Tokens"));
        lore.add(m.c("&a• &a⬥30,000 Gems"));
        lore.add(m.c("&a• &e25x &7&lAlpha &7Keys"));
        lore.add(m.c("&a• &e20x &c&lBeta &7Keys"));
        lore.add(m.c("&a• &e15x &4&lOmega &7Key"));
        lore.add(m.c("&a• &c&lHero Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&b&lRare Rewards:"));
        lore.add(m.c("&b• &e⛀1,000,000 Tokens"));
        lore.add(m.c("&b• &a⬥50,000 Gems"));
        lore.add(m.c("&b• &e25x &c&lBeta &7Keys"));
        lore.add(m.c("&b• &e20x &4&lOmega &7Keys"));
        lore.add(m.c("&b• &e5x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
        lore.add(m.c("&b• &5&lDemi-God Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&5&lEpic Rewards:"));
        lore.add(m.c("&5• &e⛀1,500,000 Tokens"));
        lore.add(m.c("&5• &e10x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Keys"));
        lore.add(m.c("&5• &e3.0x Token Boost"));
        lore.add(m.c("&5• &3&lTitan Rank"));
        lore.add(m.c("&5• &d&lGod Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&kii&6&lLegendary Rewards&f&l&kii&r"));
        lore.add(m.c("&6• &e⛀3,000,000 Tokens"));
        lore.add(m.c("&6• &e5.0x Token Boost"));
        lore.add(m.c("&6• &e7x &3&lRank &7Key"));
        lore.add(m.c("&6• &e5.0x Sell Boost"));
        lore.add(m.c("&6• &c&l5x Monster &7&lEgg"));
        lore.add(m.c("&6• &4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank"));


        gm.setLore(lore);
        gcrate.setItemMeta(gm);
        return gcrate;
    }

    public static ItemStack ContrabandCrate() {
        ItemStack gcrate = new ItemStack(Material.ENDER_CHEST);
        ItemMeta gm = gcrate.getItemMeta();
        gm.setDisplayName(m.c("&f&l• &c&lC&6&lo&e&ln&a&lt&3&lr&9&la&5&lb&c&la&6&ln&e&ld &3&lC&9&lr&5&la&c&lt&6&le &f&l•"));
        List<String> lore = new ArrayList<>();


        lore.add(m.c("&7Upon placing this item, you will recieve 8 random items"));
        lore.add(m.c("&7These will be Common, Rare, or Epic and 1 guaranteed Legendary"));
        lore.add(m.c("&a&lCommon Rewards:"));
        lore.add(m.c("&a• &e⛀50,000 Tokens"));
        lore.add(m.c("&a• &a⬥2,500 Gems"));
        lore.add(m.c("&a• &e5x &7&lAlpha &7Keys"));
        lore.add(m.c("&a• &e3x &c&lBeta &7Keys"));
        lore.add(m.c("&a• &e1x &4&lOmega &7Key"));
        lore.add(m.c("&a• &b&lSponsor Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&b&lRare Rewards:"));
        lore.add(m.c("&b• &e⛀100,000 Tokens"));
        lore.add(m.c("&b• &a⬥5,000 Gems"));
        lore.add(m.c("&b• &e5x &c&lBeta &7Keys"));
        lore.add(m.c("&b• &e3x &4&lOmega &7Keys"));
        lore.add(m.c("&b• &e1x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
        lore.add(m.c("&b• &a&lVIP Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&5&lEpic Rewards:"));
        lore.add(m.c("&5• &e⛀250,000 Tokens"));
        lore.add(m.c("&5• &e3x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Keys"));
        lore.add(m.c("&5• &e1.5x Token Boost"));
        lore.add(m.c("&5• &6&lMVP Rank"));
        lore.add(m.c("&5• &c&lHero Rank"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&kii&6&lLegendary Rewards&f&l&kii&r"));
        lore.add(m.c("&6• &e⛀500,000 Tokens"));
        lore.add(m.c("&6• &e2.0x Token Boost"));
        lore.add(m.c("&6• &e1x &3&lRank &7Key"));
        lore.add(m.c("&6• &e2.0x Sell Boost"));
        lore.add(m.c("&6• &c&lMonster &7&lEgg"));
        lore.add(m.c("&6• &d&lGod Rank"));


        gm.setLore(lore);
        gcrate.setItemMeta(gm);

        return gcrate;
    }

    public static ItemStack Reward(String crate, String rarity) {
        ItemStack reward = new ItemStack(Material.PAPER);
        ItemMeta rm = reward.getItemMeta();
        Random r = new Random();
        List<String> lore = new ArrayList<>();
        if (crate.equals("contraband")) {
            if (rarity.equals("Common")) {
                int i = r.nextInt(121);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e50,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 50000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    rm.setDisplayName(m.c("&e5x &7&lAlpha &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% alpha 5");
                    rm.setLore(lore);
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&e3x &c&lBeta &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% beta 3");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e1x &4&lOmega &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% omega 1");
                    rm.setLore(lore);
                }
                else if (i <=100) {
                    rm.setDisplayName(m.c("&b&lSponsor Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &b&lSponsor Rank");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&a⬥2,500 Gems"));
                    reward.setType(Material.EMERALD);
                    lore.add("gems add %PLAYER% 2500");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Rare")) {
                int i = r.nextInt(121);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e100,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 100000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    rm.setDisplayName(m.c("&e5x &c&lBeta &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% beta 5");
                    rm.setLore(lore);
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&e3x &c&lOmega &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% omega 3");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e1x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% seasonal 1");
                    rm.setLore(lore);
                }
                else if (i <=100) {
                    rm.setDisplayName(m.c("&a&lVIP Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &a&lVIP Rank");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&a⬥5,000 Gems"));
                    reward.setType(Material.EMERALD);
                    lore.add("gems add %PLAYER% 5000");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Epic")) {
                int i = r.nextInt(101);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e250,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 250000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8227);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e1.5x Token Boost"));
                    lore.add("giveboost %PLAYER% token 1.5");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&6&lMVP Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &6&lMVP Rank");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e3x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% seasonal 3");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&c&lHero Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &c&lHero Rank");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Legendary")) {
                int i = r.nextInt(121);
                if (i <= 65) {
                    rm.setDisplayName(m.c("&e500,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 500000");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8227);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e2.0x Token Boost"));
                    lore.add("giveboost %PLAYER% token 2.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=95) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8228);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e2.0x Sell Boost"));
                    lore.add("giveboost %PLAYER% sell 2.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=110) {
                    rm.setDisplayName(m.c("&c&lMonster &7&lEgg"));
                    reward.setType(Material.MONSTER_EGG);
                    lore.add("giveegg %PLAYER%");
                    rm.setLore(lore);
                }
                else if (i <=117) {
                    rm.setDisplayName(m.c("&e1x &3&lRank &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% Rank 1");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&d&lGod Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &d&lGod Rank");
                    rm.setLore(lore);
                }
            }

        }else if (crate.equals("genesis")) {
            if (rarity.equals("Common")) {
                int i = r.nextInt(121);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e250,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 250000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    rm.setDisplayName(m.c("&e15x &7&lAlpha &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% alpha 15");
                    rm.setLore(lore);
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&e10x &c&lBeta &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% beta 10");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e5x &4&lOmega &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% omega 5");
                    rm.setLore(lore);
                }
                else if (i <=100) {
                    rm.setDisplayName(m.c("&a&lVIP Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &a&lVIP Rank");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&a⬥12,500 Gems"));
                    reward.setType(Material.EMERALD);
                    lore.add("gems add %PLAYER% 12500");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Rare")) {
                int i = r.nextInt(121);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e500,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 500000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    rm.setDisplayName(m.c("&e15x &c&lBeta &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% beta 15");
                    rm.setLore(lore);
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&e10x &c&lOmega &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% omega 10");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e3x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% seasonal 3");
                    rm.setLore(lore);
                }
                else if (i <=100) {
                    rm.setDisplayName(m.c("&6&lMVP Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &6&lMVP Rank");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&a⬥25,000 Gems"));
                    reward.setType(Material.EMERALD);
                    lore.add("gems add %PLAYER% 25000");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Epic")) {
                int i = r.nextInt(101);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e1,000,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 1000000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8227);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e2.0x Token Boost"));
                    lore.add("giveboost %PLAYER% token 2.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&c&lHero Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &c&lHero Rank");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e5x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% seasonal 5");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&5&lDemi-God Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &5&lDemi-God Rank");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Legendary")) {
                int i = r.nextInt(121);
                if (i <= 65) {
                    rm.setDisplayName(m.c("&e1,500,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 1500000");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8227);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e3.0x Token Boost"));
                    lore.add("giveboost %PLAYER% token 3.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=95) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8228);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e3.0x Sell Boost"));
                    lore.add("giveboost %PLAYER% sell 3.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=110) {
                    rm.setDisplayName(m.c("&c&l2x Monster &7&lEgg"));
                    reward.setType(Material.MONSTER_EGG);
                    lore.add("giveegg %PLAYER% 2");
                    rm.setLore(lore);
                }
                else if (i <=117) {
                    rm.setDisplayName(m.c("&e3x &3&lRank &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% Rank 3");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&e&lOlympian Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &e&lOlympian Rank");
                    rm.setLore(lore);
                }
            }

        }else if (crate.equals("april")) {
            if (rarity.equals("Common")) {
                int i = r.nextInt(121);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e500,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 500000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    rm.setDisplayName(m.c("&e25x &7&lAlpha &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% alpha 25");
                    rm.setLore(lore);
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&e20x &c&lBeta &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% beta 20");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e15x &4&lOmega &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% omega 15");
                    rm.setLore(lore);
                }
                else if (i <=100) {
                    rm.setDisplayName(m.c("&c&lHero Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &c&lHero Rank");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&a⬥30,000 Gems"));
                    reward.setType(Material.EMERALD);
                    lore.add("gems add %PLAYER% 30000");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Rare")) {
                int i = r.nextInt(121);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e1,000,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 1000000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    rm.setDisplayName(m.c("&e25x &c&lBeta &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% beta 25");
                    rm.setLore(lore);
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&e20x &c&lOmega &7Keys"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% omega 20");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e5x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% seasonal 5");
                    rm.setLore(lore);
                }
                else if (i <=100) {
                    rm.setDisplayName(m.c("&5&lDemi-God Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &5&lDemi-God Rank");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&a⬥50,000 Gems"));
                    reward.setType(Material.EMERALD);
                    lore.add("gems add %PLAYER% 50000");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Epic")) {
                int i = r.nextInt(101);
                if (i <= 20) {
                    rm.setDisplayName(m.c("&e1,500,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 1500000");
                    rm.setLore(lore);
                }
                else if (i <=40) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8227);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e3.0x Token Boost"));
                    lore.add("giveboost %PLAYER% token 3.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=60) {
                    rm.setDisplayName(m.c("&3&lTitan Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &3&lTitan Rank");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    rm.setDisplayName(m.c("&e10x &4&l&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% seasonal 10");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&d&lGod Rank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &d&lGod Rank");
                    rm.setLore(lore);
                }
            }
            if (rarity.equals("Legendary")) {
                int i = r.nextInt(121);
                if (i <= 65) {
                    rm.setDisplayName(m.c("&e3,000,000 Tokens"));
                    reward.setType(Material.MAGMA_CREAM);
                    lore.add("tokens add %PLAYER% 3000000");
                    rm.setLore(lore);
                }
                else if (i <=80) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8227);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e5.0x Token Boost"));
                    lore.add("giveboost %PLAYER% token 5.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=95) {
                    ItemStack reward1 = new ItemStack(Material.POTION, 1, (short) 8228);
                    ItemMeta rm1 = reward1.getItemMeta();
                    rm1.setDisplayName(m.c("&e5.0x Sell Boost"));
                    lore.add("giveboost %PLAYER% sell 5.0");
                    rm1.setLore(lore);
                    return reward1;
                }
                else if (i <=110) {
                    rm.setDisplayName(m.c("&c&l5x Monster &7&lEgg"));
                    reward.setType(Material.MONSTER_EGG);
                    lore.add("giveegg %PLAYER% 5");
                    rm.setLore(lore);
                }
                else if (i <=117) {
                    rm.setDisplayName(m.c("&e7x &3&lRank &7Key"));
                    reward.setType(Material.TRIPWIRE_HOOK);
                    lore.add("cratekey %PLAYER% Rank 7");
                    rm.setLore(lore);
                }
                else {
                    rm.setDisplayName(m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank"));
                    reward.setType(Material.NETHER_STAR);
                    lore.add("giverank %PLAYER% &4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank");
                    rm.setLore(lore);
                }
            }

        }

        reward.setItemMeta(rm);
        return reward;
    }


}
