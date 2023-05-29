package me.dxrk.Enchants;

import me.dxrk.Events.PickXPHandler;
import me.dxrk.Events.TrinketHandler;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Integer.parseInt;


public class PickaxeLevel implements Listener, CommandExecutor {

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static PickaxeLevel instance = new PickaxeLevel();

    public static PickaxeLevel getInstance() {
        return instance;
    }

    public SettingsManager settings = SettingsManager.getInstance();

    public ItemStack pickaxe() {
        ItemStack a = new ItemStack(Material.WOOD_PICKAXE, 1, (short) 0);
        ItemMeta am = a.getItemMeta();
        List<String> lore = new ArrayList<>();
        am.setDisplayName(c("&cStarter Pickaxe"));
        am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
        am.addEnchant(Enchantment.DURABILITY, 32000, true);
        am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lore.add(c("&b&m-<>-&aEnchants&b&m-<>- "));
        lore.add(c("&cFortune &e10"));
        lore.add("  ");
        lore.add(c("&b&m-<>-&aTrinkets 0/4&b&m-<>- "));
        lore.add("  ");
        lore.add(c("&b&m-<>-&aLevel&b&m-<>- "));
        lore.add(c("&cLevel: &e1"));
        lore.add(c("&cProgress: &e0%"));
        am.setLore(lore);
        a.setItemMeta(am);
        return a;
    }


    public int getInt(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor(s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (byte) (b + 1)) {
            char c = arrayOfChar[b];
            if (!this.isInt(c)) continue;
            lvl.append(c);
        }
        if (this.isInt(lvl.toString())) {
            return parseInt(lvl.toString());
        }
        return -1;
    }

    public boolean isInt(String s) {
        try {
            parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (sender instanceof Player) {
            Player p = (Player) sender;


            if (label.equalsIgnoreCase("pick")) {
                if (!p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) {
                    p.sendMessage(c("&cPlease Hold a Pickaxe!"));
                    return true;
                }
                openenchantmenu(p);


            }

        }

        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (label.equalsIgnoreCase("pickaxe")) {
                if (!p.hasPermission("rank.owner")) {
                    return false;
                }
                p.getInventory().addItem(pickaxe());
                p.updateInventory();
            }
        }
        if (label.equalsIgnoreCase("resetpickaxe")) {
            if (sender.hasPermission("genesis.resetpickaxe")) {
                Player reciever = Bukkit.getPlayerExact(args[0]);
                resetPickaxe(reciever);
            }
        }
        if (label.equalsIgnoreCase("setskillpoints")) {
            if (sender.hasPermission("genesis.setskillpoints")) {
                Player reciever = Bukkit.getPlayerExact(args[0]);
                int amount = parseInt(args[1]);
                settings.getPlayerData().set(reciever.getUniqueId().toString() + ".PickaxeSkillPoints", amount);
            }
        }

        return false;
    }


    public ItemStack Spacer() {
        ItemStack i = new ItemStack(Material.IRON_FENCE);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lGenesis &b&lPrison"));
        i.setItemMeta(im);
        return i;
    }


    public void setEnchantItem(String enchantName, Material mat, String name, String desc, int priceStart, Inventory inv, int slot, Player p, int unlocked) {

        int enchantLevel = 0;

        for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
            String s = p.getItemInHand().getItemMeta().getLore().get(x);
            if (ChatColor.stripColor(s).contains(enchantName)) {
                enchantLevel = getInt(p.getItemInHand().getItemMeta().getLore().get(x));
            }
        }
        double price;
        if (enchantLevel == 0) {
            price = priceStart;
        } else {
            price = enchantPrice(enchantName, enchantLevel);
        }

        String cost;

        if (enchantLevel != maxLevel(enchantName, p)) {
            cost = c("&bCost: &e" + ((int) price) + "⛀");
        } else {
            cost = c("&bCost: &eMAX LEVEL!");
        }


        ItemStack i = new ItemStack(mat);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(desc);
        lore.add(" ");
        lore.add(c("&bCurrent Level: &e" + enchantLevel));
        lore.add(c("&bMax Level: &e" + maxLevel(enchantName, p)));
        lore.add(cost);
        lore.add(" ");
        lore.add(c("&7&oLeft Click: Buy 1"));
        lore.add(c("&7&oRight Click: Buy 10"));
        lore.add(c("&7&oShift+Right Click: Buy 100"));
        lore.add(c("&7&oShift+Left Click: Buy Max"));
        if (unlocked > PickXPHandler.getInstance().getLevel(p)) {
            lore.clear();
            lore.add(c("&cUnlocked at level: " + unlocked));
        }

        im.setLore(lore);
        i.setItemMeta(im);
        lore.clear();


        inv.setItem(slot, i);
    }


    public void openenchantmenu(Player p) {
        Inventory enchantmenu = Bukkit.createInventory(null, 54, c("&d&lPurchase Enchants!"));

        setEnchantItem("Key Finder", Material.TRIPWIRE_HOOK, c("&bUpgrade Key Finder"), c("&7Chance to find a Key."), 1000, enchantmenu, 4, p, 1);
        setEnchantItem("Token Finder", Material.PRISMARINE_CRYSTALS, c("&bUpgrade Token Finder"), c("&7Increase the amount of tokens randomly found."), 1000, enchantmenu, 12, p, 1);
        setEnchantItem("Fortune", Material.NETHER_STAR, c("&bUpgrade Fortune"), c("&7Increases amount of blocks you sell."), 100, enchantmenu, 13, p, 1);
        setEnchantItem("Explosion", Material.FIREBALL, c("&bUpgrade Explosion"), c("&7Chance to explode a large hole in the mine(5x5x5)."), 2500, enchantmenu, 14, p, 10);
        setEnchantItem("XP Finder", Material.EXP_BOTTLE, c("&bUpgrade XP Finder"), c("&7Increases the amount of XP found while mining."), 3000, enchantmenu, 20, p, 15);
        setEnchantItem("Dust Finder", Material.SUGAR, c("&bUpgrade Dust Finder"), c("&7Chance to find Trinket dust."), 2500, enchantmenu, 21, p, 25);
        setEnchantItem("Wave", Material.GOLD_PLATE, c("&bUpgrade Wave"), c("&7Chance to break an entire layer of the mine."), 4500, enchantmenu, 22, p, 40);
        setEnchantItem("Research", Material.REDSTONE, c("&bUpgrade Research"), c("&7Chance to grant you one level instantly."), 4000, enchantmenu, 23, p, 50);
        setEnchantItem("Greed", Material.DIAMOND, c("&bUpgrade Greed"), c("&7Increases selling price for blocks."), 5000, enchantmenu, 24, p, 60);
        setEnchantItem("Key Party", Material.EYE_OF_ENDER, c("&bUpgrade Key Party"), c("&7Chance to give everyone online a key."), 6000, enchantmenu, 29, p, 75);
        setEnchantItem("Junkpile", Material.BUCKET, c("&bUpgrade Junkpile"), c("&7Chance to find random items while mining."), 7500, enchantmenu, 30, p, 85);
        setEnchantItem("Booster", Material.POTION, c("&bUpgrade Booster"), c("&7Chance to find low timed boosts."), 8000, enchantmenu, 31, p, 100);
        setEnchantItem("Karma", Material.RABBIT_FOOT, c("&bUpgrade Karma"), c("&7Boosts the chance of other enchants to proc."), 9000, enchantmenu, 32, p, 150);
        setEnchantItem("Fortuity", Material.GOLD_INGOT, c("&bUpgrade Fortuity"), c("&7Boosts the effectiveness of Fortune."), 9750, enchantmenu, 33, p, 175);
        setEnchantItem("Multiply", Material.EMERALD, c("&bUpgrade Multiply"), c("&7Chance to double the effectiveness of all currencies for 10s."), 10000, enchantmenu, 39, p, 200);
        setEnchantItem("Laser", Material.BLAZE_ROD, c("&bUpgrade Laser"), c("&7Chance to summon a laser to obliterate blocks in its path."), 15000, enchantmenu, 40, p, 210);
        setEnchantItem("Nuke", Material.TNT, c("&bUpgrade Nuke"), c("&7Low Chance to break the entire mine."), 20000, enchantmenu, 41, p, 220);


        ItemStack trinkets = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta tm = trinkets.getItemMeta();
        tm.setDisplayName(c("&e&lTrinkets"));
        List<String> lore = new ArrayList<>();
        lore.add(c("&7&oOpen Trinket Menu."));
        tm.setLore(lore);
        trinkets.setItemMeta(tm);
        enchantmenu.setItem(52, trinkets);
        lore.clear();

        ItemStack skills = new ItemStack(Material.WOOD_PICKAXE);
        ItemMeta sm = skills.getItemMeta();
        sm.setDisplayName(c("&6Pickaxe Skills"));
        if (PickXPHandler.getInstance().getLevel(p) < 25) {
            lore.add(c("&cUnlocks at Pickaxe Level 25!"));
        } else {
            lore.add(c("&aOpen Pickaxe Skills"));
        }
        sm.setLore(lore);
        skills.setItemMeta(sm);
        enchantmenu.setItem(46, skills);
        lore.clear();

        ItemStack skillenchants = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta semeta = skillenchants.getItemMeta();
        semeta.setDisplayName(c("&a&lPickaxe Skill Enchants"));
        lore.add(c("&6Open skill enchant menu"));
        semeta.setLore(lore);
        skillenchants.setItemMeta(semeta);
        enchantmenu.setItem(49, skillenchants);

        enchantmenu.setItem(45, Spacer());
        enchantmenu.setItem(47, Spacer());
        enchantmenu.setItem(48, Spacer());
        enchantmenu.setItem(50, Spacer());
        enchantmenu.setItem(51, Spacer());
        enchantmenu.setItem(53, Spacer());


        p.openInventory(enchantmenu);
    }

    public int lineOfEnchant(Player p, String Enchant) {
        int x = 0;
        ItemStack pitem = p.getItemInHand().clone();
        List<String> lore = pitem.getItemMeta().getLore();
        for (int i = 0; i < lore.size(); i++) {
            String s = lore.get(i);
            if (ChatColor.stripColor(s).contains(Enchant)) {
                x = i;
            }
        }

        return x;
    }


    public void resetPickaxe(Player p) {
        ItemStack pitem = p.getItemInHand().clone();
        ItemMeta pm = pitem.getItemMeta();
        List<String> plore = pm.getLore();
        //test numero zwei
        ItemStack newpick = pickaxe().clone();
        ItemMeta nm = newpick.getItemMeta();
        List<String> lore = nm.getLore();
        for(String s : plore) {
            if (SkillEnchants().contains(ChatColor.stripColor(s))) {
                lore.add(s);
            }
        }
        nm.setDisplayName(pm.getDisplayName());
        nm.setLore(Lore(lore, p));
        newpick.setItemMeta(nm);
        p.setItemInHand(newpick);
        p.updateInventory();
    }

    private List<String> Enchants() {
        List<String> list = new ArrayList<>();
        list.add("Key Finder");
        list.add("Key Party");
        list.add("Dust Finder");
        list.add("Token Finder");
        list.add("Jackhammer");
        list.add("Junkpile");
        list.add("Nuke");
        list.add("Fortuity");
        list.add("Karma");
        list.add("Booster");
        list.add("Multiply");
        list.add("Fortune");
        list.add("Greed");
        list.add("XP Finder");
        list.add("Charity");
        list.add("Treasury");
        list.add("Seismic Shock");
        return list;
    }

    private List<String> SkillEnchants() {
        List<String> list = new ArrayList<>();
        list.add("Infernum");
        list.add("Calamity");
        list.add("Euphoria");
        list.add("Battlecry");
        list.add("Tidal Wave");
        return list;
    }

    private List<String> Trinkets() {
        List<String> list = new ArrayList<>();
        list.add("Token");
        list.add("Double Keys");
        list.add("XP");
        list.add("Lucky");
        list.add("Sell");

        return list;
    }

    public List<String> orgainzeEnchants(List<String> list) {
        List<String> lore = new ArrayList<>();
        lore.add(c("&b&m-<>-&aEnchants&b&m-<>-"));
        List<String> ilore = list;
        List<String> Enchants = new ArrayList<>();
        for (String s : ilore) {
            if (ChatColor.stripColor(s).contains("Enchants") || ChatColor.stripColor(s).contains("Trinkets") || ChatColor.stripColor(s).contains("Level:") || ChatColor.stripColor(s).contains("Progress:")
                    || ChatColor.stripColor(s).contains("Trinket")) continue;
            if (s.equals(" ")) continue;
            for (String ss : Enchants()) {
                if (ChatColor.stripColor(s).contains(ss)) {
                    Enchants.add(s);
                }
            }
            for (String ss : SkillEnchants()) {
                if (ChatColor.stripColor(s).contains(ss)) {
                    Enchants.add(s);
                }
            }

        }
        Collections.sort(Enchants);
        lore.addAll(Enchants);

        return lore;
    }

    public List<String> organizeTrinkets(Player p) {
        List<String> lore = new ArrayList<>();
        int trinkets = 0;
        List<String> ilore = settings.getPlayerData().getStringList(p.getUniqueId().toString() + ".Trinkets");
        List<String> Trinkets = new ArrayList<>();

        for (String s : ilore) {
            if (ChatColor.stripColor(s).contains("Enchants") || ChatColor.stripColor(s).contains("Trinkets") || ChatColor.stripColor(s).contains("Level:") || ChatColor.stripColor(s).contains("Progress:"))
                continue;
            if (s.equals(" ")) continue;
            for (String ss : Trinkets()) {
                if (ChatColor.stripColor(s).contains(ss)) {
                    Trinkets.add(s);
                    trinkets++;
                }
            }


        }
        lore.add(c("&b&m-<>-&aTrinkets " + trinkets + "/4&b&m-<>-"));
        Collections.sort(Trinkets);
        lore.addAll(Trinkets);

        return lore;
    }

    public List<String> pickLevel(List<String> list) {
        List<String> lore = new ArrayList<>();
        List<String> ilore = list;
        for (String s : ilore) {
            if (ChatColor.stripColor(s).contains("Level:")) {
                lore.add(s);
            }
        }
        for (String s : ilore) {
            if (ChatColor.stripColor(s).contains("Progress:")) {
                lore.add(s);
            }
        }
        for (String s : ilore) {
            if (ChatColor.stripColor(s).contains("Skill:")) {
                lore.add(s);
            }
        }


        return lore;
    }

    public List<String> Lore(List<String> list, Player p) {
        List<String> lore = new ArrayList<>(orgainzeEnchants(list));
        lore.add(c(" "));
        lore.addAll(organizeTrinkets(p));
        lore.add(c(" "));
        lore.add(c("&b&m-<>-&aLevel&b&m-<>-"));
        lore.addAll(pickLevel(list));
        return lore;
    }

    public double enchantPrice(String Enchant, int level) {
        double i = 0;
        switch (Enchant) {
            case "Key Finder":
                if (level == 0) {
                    i = 1000;
                    break;
                }
                i = 1000 + (1000 * (level * 0.0036)); // 50 Million Level 5,000 MAX

                break;
            case "Dust Finder":
                if (level == 0) {
                    i = 2500;
                    break;
                }
                i = 2500 + (2500 * (level * 0.002)); // 75 Million Level 5,000 MAX

                break;
            case "Fortune":
                if (level == 0) {
                    i = 100;
                    break;
                }
                i = 100 + (100 * (level * 0.032)); // 1 Billion Level 25,000 MAX

                break;
            case "Jackhammer":
                if (level == 0) {
                    i = 4500;
                    break;
                }
                i = 4500 + (4500 * (level * 0.09141)); // 225 Million Level 1,000 MAX

                break;
            case "Token Finder":
                if (level == 0) {
                    i = 1000;
                    break;
                }
                i = 1000 + (1000 * (level * 0.01822)); // 85 Million Level 3,000 MAX

                break;
            case "Charity":
                if (level == 0) {
                    i = 10000;
                    break;
                }
                i = 10000 + (10000 * (level * 0.13)); // 659 Million Level 1,000 MAX

                break;
            case "Nuke":
                if (level == 0) {
                    i = 20000;
                    break;
                }
                i = 20000 + (20000 * (level * 0.6)); // 1.5 Billion Level 500 MAX

                break;
            case "Greed":
                if (level == 0) {
                    i = 5000;
                    break;
                }
                i = 5000 + (5000 * (level * 7.05)); // 252 Million Level 100 MAX

                break;
            case "Junkpile":
                if (level == 0) {
                    i = 7500;
                    break;
                }
                i = 7500 + (7500 * (level * 0.02)); // 315 Million Level 2,000 MAX

                break;
            case "Key Party":
                if (level == 0) {
                    i = 6000;
                    break;
                }
                i = 6000 + (6000 * (level * 0.076)); // 234 Million Level 1,000 MAX

                break;
            case "XP Finder":
                if (level == 0) {
                    i = 3000;
                    break;
                }
                i = 3000 + (3000 * (level * 0.0023)); // 180 Million Level 10,000 MAX

                break;
            case "Fortuity":
                if (level == 0) {
                    i = 9750;
                    break;
                }
                i = 9750 + (9750 * (level * 0.1315)); // 650 Million Level 1,000 MAX

                break;
            case "Booster":
                if (level == 0) {
                    i = 8000;
                    break;
                }
                i = 8000 + (8000 * (level * 0.013)); // 345 Million Level 2,500 MAX

                break;
            case "Multiply":
                if (level == 0) {
                    i = 10000;
                    break;
                }
                i = 10000 + (10000 * (level * 0.0743)); // 850 Million Level 1,500 MAX

                break;
            case "Karma":
                if (level == 0) {
                    i = 9000;
                    break;
                }
                i = 9000 + (9000 * (level * 0.00405)); // 500 Million Level 5,000 MAX

                break;
            case "Treasury":
                if (level == 0) {
                    i = 6000;
                    break;
                }
                i = 6000 + (6000 * (level * 0.015)); // 296 Million Level 2,500 MAX

                break;
            case "Seismic Shock":
                if (level == 0) {
                    i = 15000;
                    break;
                }
                i = 15000 + (15000 * (level * 0.15)); // 1.1 Billion Level 1,000 MAX

                break;
            case "Calamity":
                if (level == 0) {
                    i = 12500;
                    break;
                }
                i = 12500 + (12500 * (level * 0.125)); // 793 Million Level 1,000 MAX

                break;
        }
        return i;
    }

    public int maxLevel(String Enchant, Player p) {
        int i = 0;
        switch (Enchant) {
            case "Key Finder":
                int skillmaxlevelkf = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".KFMaxLevelRaise");
                i = 5000 + skillmaxlevelkf;
                break;
            case "Dust Finder":
            case "Prestige Finder":
            case "Karma":
                i = 5000;

                break;

            case "Jackhammer":
            case "Fortuity":
            case "Key Party":
            case "Charity":
            case "Seismic Shock":
            case "Calamity":
                i = 1000;

                break;
            case "Token Finder":
                int skillmaxleveltf = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".TFMaxLevelRaise");
                i = 3000 + skillmaxleveltf;
                break;
            case "Research":
                i = 3000;

                break;
            case "Booster":
            case "Treasury":
                i = 2500;

                break;

            case "Fortune":
                int skillmaxlevelfortune = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".FortuneMaxLevelRaise");
                i = 25000 + skillmaxlevelfortune;
                break;
            case "Nuke":
                i = 500;

                break;

            case "Greed":
                i = 100;

                break;
            case "Junkpile":
                int skillmaxleveljunkpile = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".JunkpileMaxLevelRaise");
                i = 2000 + skillmaxleveljunkpile;
                break;
            case "XP Finder":
                int skillmaxlevelxpf = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".XPFMaxLevelRaise");
                i = 10000 + skillmaxlevelxpf;
                break;
            case "Multiply":
                i = 1500;

                break;
        }
        return i;
    }

    private Tokens tokens = Tokens.getInstance();


    public void upgradeEnchant(Player p, ItemStack i, String Enchant, int num, Boolean isMax) {

        //Fix the way trinkets apply according to the new lore setup + refine trinkets in their handler -- done 1/2
        //Change this to accomodate for the new lore setup / try to find a way to organize enchants on apply -- done

        //Enchants
        ItemStack pitem = i.clone();
        ItemMeta pm = pitem.getItemMeta();
        List<String> lore = pm.getLore();
        boolean hasEnchant = false;
        if (isMax == false) {

            for (String s : lore) {
                if (ChatColor.stripColor(s).contains("Trinket")) {
                    continue;
                }
                if (ChatColor.stripColor(s).contains(Enchant)) {
                    hasEnchant = true;
                }
            }


            if (hasEnchant == false) {
                int level = 0;
                int plus = level + 1;
                int price = (int) enchantPrice(Enchant, level);
                if (Tokens.getInstance().getTokens(p) >= price) {
                    if (plus > maxLevel(Enchant, p)) return;
                    lore.add(c("&c" + Enchant + " &e" + plus));
                    this.tokens.takeTokens(p, price);

                } else {
                    p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
                    return;
                }


            } else {
                int line = 0;
                for (int z = 0; z < lore.size(); z++) {
                    String s = lore.get(z);
                    if (ChatColor.stripColor(s).contains(Enchant))
                        line = z;
                }
                for (int x = 0; x < num; x++) {
                    int level = getInt(lore.get(line));
                    int plus = level + 1;
                    int price = (int) enchantPrice(Enchant, level);
                    if (Tokens.getInstance().getTokens(p) >= price) {
                        if (plus > maxLevel(Enchant, p)) return;
                        lore.set(line, c("&c" + Enchant + " &e" + plus));
                        this.tokens.takeTokens(p, price);
                    } else {
                        p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
                        break;
                    }

                }

            }
        } else {
            for (String s : lore) {
                if (ChatColor.stripColor(s).contains(Enchant)) {
                    hasEnchant = true;
                }
            }

            if (hasEnchant == false) {
                int level = 0;
                int plus = level + 1;
                int price = (int) enchantPrice(Enchant, level);
                if (Tokens.getInstance().getTokens(p) >= price) {
                    if (plus > maxLevel(Enchant, p)) return;
                    lore.add(c("&c" + Enchant + " &e" + plus));
                    this.tokens.takeTokens(p, price);
                } else {
                    p.sendMessage(c("&f&lTokens &8| &7Not enough Tokens."));
                    return;
                }


            } else {
                int line = 0;
                for (int z = 0; z < lore.size(); z++) {
                    String s = lore.get(z);
                    if (ChatColor.stripColor(s).contains(Enchant))
                        line = z;
                }
                int l = getInt(lore.get(line));
                for (int x = 0; x < (maxLevel(Enchant, p) - l); x++) {
                    int level = getInt(lore.get(line));
                    int plus = level + 1;
                    int price = (int) enchantPrice(Enchant, level);
                    if (Tokens.getInstance().getTokens(p) >= price) {
                        if (plus > maxLevel(Enchant, p)) return;
                        lore.set(line, c("&c" + Enchant + " &e" + plus));
                        this.tokens.takeTokens(p, price);
                    } else {
                        break;
                    }

                }

            }

        }
        pm.setLore(Lore(lore, p));
        pitem.setItemMeta(pm);
        p.setItemInHand(pitem);
        p.updateInventory();


    }

    @EventHandler
    public void openMenu(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null)
            return;
        ItemStack item = p.getItemInHand();
        if (item.getType().equals(Material.DIAMOND_PICKAXE) || item.getType().equals(Material.IRON_PICKAXE) || item.getType().equals(Material.GOLD_PICKAXE) || item.getType().equals(Material.STONE_PICKAXE)
                || item.getType().equals(Material.WOOD_PICKAXE)) {
            if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.LEFT_CLICK_BLOCK))
                return;
            if (e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                openenchantmenu(p);

            }

        }
    }

    @EventHandler
    public void openSkillEnchantsInv(Player p) {
        Inventory inv = Bukkit.createInventory(null, InventoryType.HOPPER, c("&6Pickaxe Skill Enchants"));

        List<String> completed = settings.getPlayerData().getStringList(p.getUniqueId().toString() + ".CompletedPaths");

        ItemStack calamity;
        if (!completed.contains("Zeus")) {
            calamity = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta calamitymeta = calamity.getItemMeta();
            calamitymeta.setDisplayName(c("&bUpgrade Calamity"));
            List<String> calamitylore = new ArrayList<>();
            calamitylore.add(c("&cUnlocks after finishing Zeus Path."));
            calamitymeta.setLore(calamitylore);
            calamity.setItemMeta(calamitymeta);
        } else {
            calamity = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
            ItemMeta calamitymeta = calamity.getItemMeta();
            calamitymeta.setDisplayName(c("&bUpgrade Calamity"));
            List<String> calamitylore = new ArrayList<>();
            calamitylore.add(c("&cTylar do your upgrading balances here"));
            calamitymeta.setLore(calamitylore);
            calamity.setItemMeta(calamitymeta);
        }
        inv.setItem(0, calamity);
        ItemStack infernum;
        if (!completed.contains("Hades")) {
            infernum = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta infernummeta = infernum.getItemMeta();
            infernummeta.setDisplayName(c("&bUpgrade Infernum"));
            List<String> infernumlore = new ArrayList<>();
            infernumlore.add(c("&cUnlocks after finishing Hades Path."));
            infernummeta.setLore(infernumlore);
            infernum.setItemMeta(infernummeta);
        } else {
            infernum = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
            ItemMeta infernummeta = infernum.getItemMeta();
            infernummeta.setDisplayName(c("&bUpgrade Infernum"));
            List<String> infernumlore = new ArrayList<>();
            infernumlore.add(c("&cTylar do your upgrading balances here"));
            infernummeta.setLore(infernumlore);
            infernum.setItemMeta(infernummeta);
        }
        inv.setItem(1, infernum);
        ItemStack tidalwave;
        if (!completed.contains("Poseidon")) {
            tidalwave = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta tidalwavemeta = tidalwave.getItemMeta();
            tidalwavemeta.setDisplayName(c("&bUpgrade Tidal Wave"));
            List<String> tidalwavelore = new ArrayList<>();
            tidalwavelore.add(c("&cUnlocks after finishing Poseidon Path."));
            tidalwavemeta.setLore(tidalwavelore);
            tidalwave.setItemMeta(tidalwavemeta);
        } else {
            tidalwave = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
            ItemMeta tidalwavemeta = tidalwave.getItemMeta();
            tidalwavemeta.setDisplayName(c("&bUpgrade Tidal Wave"));
            List<String> tidalwavelore = new ArrayList<>();
            tidalwavelore.add(c("&cTylar do your upgrading balances here"));
            tidalwavemeta.setLore(tidalwavelore);
            tidalwave.setItemMeta(tidalwavemeta);
        }
        inv.setItem(2, tidalwave);
        ItemStack euphoria;
        if (!completed.contains("Aphrodite")) {
            euphoria = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta euphoriameta = euphoria.getItemMeta();
            euphoriameta.setDisplayName(c("&bUpgrade Euphoria"));
            List<String> euphorialore = new ArrayList<>();
            euphorialore.add(c("&cUnlocks after finishing Aphrodite Path."));
            euphoriameta.setLore(euphorialore);
            euphoria.setItemMeta(euphoriameta);
        } else {
            euphoria = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
            ItemMeta euphoriameta = euphoria.getItemMeta();
            euphoriameta.setDisplayName(c("&bUpgrade Euphoria"));
            List<String> euphorialore = new ArrayList<>();
            euphorialore.add(c("&cTylar do your upgrading balances here"));
            euphoriameta.setLore(euphorialore);
            euphoria.setItemMeta(euphoriameta);
        }
        inv.setItem(3, euphoria);
        ItemStack battlecry;
        if (!completed.contains("Ares")) {
            battlecry = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
            ItemMeta battlecrymeta = battlecry.getItemMeta();
            battlecrymeta.setDisplayName(c("&bUpgrade Battlecry"));
            List<String> battlecrylore = new ArrayList<>();
            battlecrylore.add(c("&cUnlocks after finishing Ares Path."));
            battlecrymeta.setLore(battlecrylore);
            battlecry.setItemMeta(battlecrymeta);
        } else {
            battlecry = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 13);
            ItemMeta battlecrymeta = battlecry.getItemMeta();
            battlecrymeta.setDisplayName(c("&bUpgrade Battlecry"));
            List<String> battlecrylore = new ArrayList<>();
            battlecrylore.add(c("&cTylar do your upgrading balances here"));
            battlecrymeta.setLore(battlecrylore);
            battlecry.setItemMeta(battlecrymeta);
        }
        inv.setItem(4, battlecry);

        p.openInventory(inv);
    }

    @EventHandler
    public void oninv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getName() == null) return;


        if (e.getInventory().getName().equals(c("&d&lPurchase Enchants!"))) {
            e.setCancelled(true);
            if (e.getClickedInventory().equals(p.getInventory())) return;
            if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem() == null) return;
            if (e.getCurrentItem().equals(Spacer())) return;

            if (e.getSlot() == 46) {
                if (PickXPHandler.getInstance().getLevel(p) < 25) {
                    return;
                }
                if (settings.getPlayerData().get(p.getUniqueId().toString() + ".PickaxeSkill").equals("None")) {
                    PickaxeSkillTree.openSkills(p);
                } else {
                    if (settings.getPlayerData().get(p.getUniqueId().toString() + ".PickaxeSkill").equals("Zeus")) {
                        PickaxeSkillTree.openZeus(p);
                    } else if (settings.getPlayerData().get(p.getUniqueId().toString() + ".PickaxeSkill").equals("Poseidon")) {
                        PickaxeSkillTree.openPoseidon(p);
                    } else if (settings.getPlayerData().get(p.getUniqueId().toString() + ".PickaxeSkill").equals("Hades")) {
                        PickaxeSkillTree.openHades(p);
                    } else if (settings.getPlayerData().get(p.getUniqueId().toString() + ".PickaxeSkill").equals("Ares")) {
                        PickaxeSkillTree.openAres(p);
                    } else if (settings.getPlayerData().get(p.getUniqueId().toString() + ".PickaxeSkill").equals("Aphrodite")) {
                        PickaxeSkillTree.openAphrodite(p);
                    }
                }
                return;
            }
            if (e.getSlot() == 52) {
                TrinketHandler.getInstance().openTrinkets(p);
                return;
            }
            if (e.getSlot() == 49) {
                openSkillEnchantsInv(p);
                return;
            }

            String[] display = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("Upgrade ");
            String name = display[1];

            if (isInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0).split(" ")[3]))) {
                return;
            }


            if (e.getClick().equals(ClickType.LEFT)) {
                upgradeEnchant(p, p.getItemInHand(), name, 1, false);
            } else if (e.getClick().equals(ClickType.RIGHT)) {
                upgradeEnchant(p, p.getItemInHand(), name, 10, false);
            } else if (e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                upgradeEnchant(p, p.getItemInHand(), name, 100, false);
            } else if (e.getClick().equals(ClickType.SHIFT_LEFT)) {
                upgradeEnchant(p, p.getItemInHand(), name, 1, true);
            }

            p.updateInventory();
            openenchantmenu(p);


        } else if (e.getInventory().getName().equals(c("&6Pickaxe Skill Enchants"))) {
            e.setCancelled(true);
            if (e.getClickedInventory().equals(p.getInventory())) return;
            if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem() == null) return;
            //add upgrading for pickaxe skill enchants
        }
    }


    public static void spawnFireworks(Location location, int amount) {
        Location loc = location;
        Firework fw = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
        FireworkMeta fwm = fw.getFireworkMeta();

        fwm.setPower(2);
        fwm.addEffect(FireworkEffect.builder().withColor(Color.LIME).flicker(true).build());

        fw.setFireworkMeta(fwm);
        fw.detonate();

        for (int i = 0; i < amount; i++) {
            Firework fw2 = (Firework) loc.getWorld().spawnEntity(loc, EntityType.FIREWORK);
            fw2.setFireworkMeta(fwm);
        }
    }


}
