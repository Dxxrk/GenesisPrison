package me.dxrk.Events;

import me.dxrk.Commands.CMDRanks;
import me.dxrk.Commands.CMDTags;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class CrateHandler implements Listener, CommandExecutor {
    public SettingsManager settings = SettingsManager.getInstance();

    public FileConfiguration cr = this.settings.getCrates();


    public static CrateHandler instance = new CrateHandler();

    public static CrateHandler getInstance() {
        return instance;
    }

    String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String getCrate(Block b) {
        Location loc = b.getLocation();
        World w = Bukkit.getWorld("world_the_end");
        Location alpha = new Location(w, 65, 61, 96);
        Location beta = new Location(w, 59, 61, 96);
        Location omega = new Location(w, 53, 61, 96);
        Location vote = new Location(w, 65, 61, 80);
        Location token = new Location(w, 59, 61, 80);
        Location community = new Location(w, 53, 61, 80);
        Location seasonal = new Location(w, 48, 62, 87);
        Location rank = new Location(w, 48, 62, 89);
        if (loc.equals(alpha)) {
            return "Alpha";
        }
        if (loc.equals(beta)) {
            return "Beta";
        }
        if (loc.equals(omega)) {
            return "Omega";
        }
        if (loc.equals(vote)) {
            return "Vote";
        }
        if (loc.equals(token)) {
            return "Token";
        }
        if (loc.equals(community)) {
            return "Community";
        }
        if (loc.equals(seasonal)) {
            return "Seasonal";
        }
        if (loc.equals(rank)) {
            return "Rank";
        }
        return null;
    }

    private List<Location> crateLocations() {
        List<Location> locs = new ArrayList<>();
        World w = Bukkit.getWorld("world_the_end");
        Location alpha = new Location(w, 65, 61, 96);
        Location beta = new Location(w, 59, 61, 96);
        Location omega = new Location(w, 53, 61, 96);
        Location vote = new Location(w, 65, 61, 80);
        Location token = new Location(w, 59, 61, 80);
        Location community = new Location(w, 53, 61, 80);
        Location seasonal = new Location(w, 48, 62, 87);
        Location rank = new Location(w, 48, 62, 89);
        locs.add(alpha);
        locs.add(beta);
        locs.add(omega);
        locs.add(vote);
        locs.add(token);
        locs.add(community);
        locs.add(seasonal);
        locs.add(rank);
        return locs;
    }


    public String formatKey(String s) {
        if (s.equalsIgnoreCase("vote"))
            return "Vote";
        if (s.equalsIgnoreCase("alpha"))
            return "Alpha";
        if (s.equalsIgnoreCase("beta"))
            return "Beta";
        if (s.equalsIgnoreCase("omega"))
            return "Omega";
        if (s.equalsIgnoreCase("seasonal"))
            return "Seasonal";
        if (s.equalsIgnoreCase("token"))
            return "Token";
        if (s.equalsIgnoreCase("rank"))
            return "Rank";
        if (s.equalsIgnoreCase("community"))
            return "Community";
        return "Error";
    }

    public static String format(double amt) {
        if (amt >= 1.0E15D)
            return String.format("%.1f Quad", amt / 1.0E15D);
        if (amt >= 1.0E12D)
            return String.format("%.1f Tril", amt / 1.0E12D);
        if (amt >= 1.0E9D)
            return String.format("%.1f Bil", amt / 1.0E9D);
        if (amt >= 1000000.0D)
            return String.format("%.1f Mil", amt / 1000000.0D);
        return NumberFormat.getNumberInstance(Locale.US).format(amt);
    }


    @EventHandler
    public void onInt(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        if (e.getAction().equals(Action.LEFT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) return;
        if (!crateLocations().contains(e.getClickedBlock().getLocation())) return;
        e.setCancelled(true);
        String crate = getCrate(e.getClickedBlock());
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            openCrateInv(p, crate);
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (hasKey(p, crate)) {
                if (p.isSneaking()) {
                    int keys = this.settings.getLocksmith().getInt(p.getUniqueId() + "." + crate.toLowerCase());
                    openCrate(p, crate, keys);
                } else {
                    openCrate(p, crate, 1);
                }
            } else {
                p.sendMessage(c("&f&lCrates &8| &bYou do not have any of this key"));
            }
        }
    }

    public boolean hasKey(Player p, String crate) {
        return this.settings.getLocksmith().getInt(p.getUniqueId() + "." + crate.toLowerCase()) > 0;
    }

    public void addKey(Player p, String key, int amt) {
        int keysfound = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId() + ".KeysFound");
        PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId() + ".KeysFound", keysfound + amt);
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId() + ".name", p.getName());
            this.settings.saveLocksmith();
        }
        this.settings.getLocksmith().set(p.getUniqueId() + "." + key, keys + amt);
    }

    public void takeKey(Player p, String key, int amt) {
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId() + ".name", p.getName());
        }
        this.settings.getLocksmith().set(p.getUniqueId() + "." + key, keys - amt);
    }

    private ItemStack crateItem(String name, Material mat, double chance) {
        ItemStack item = new ItemStack(mat);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(c("&c" + chance + "%"));
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    private ItemStack crateItem(String name, ItemStack i, double chance) {
        ItemStack item = i.clone();
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(c("&c" + chance + "%"));
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    private List<ItemStack> crateItems(String crate) {
        List<ItemStack> items = new ArrayList<>();
        if (crate.equals("Alpha")) {
            items.add(crateItem(c("&e30,000 Tokens"), Material.MAGMA_CREAM, 50));
            items.add(crateItem(c("&e&lToken &7Key"), Material.TRIPWIRE_HOOK, 25));
            items.add(crateItem(c("&c&lBeta &7Key"), Material.TRIPWIRE_HOOK, 15));
            items.add(crateItem(c("&a+1.0 Multi"), Material.EMERALD, 10));
            return items;
        }
        if (crate.equals("Beta")) {
            items.add(crateItem(c("&e50,000 Tokens"), Material.MAGMA_CREAM, 50));
            items.add(crateItem(c("&e2x &e&lToken &7Key"), Material.TRIPWIRE_HOOK, 25));
            items.add(crateItem(c("&7&lAlpha &7Key"), Material.TRIPWIRE_HOOK, 10));
            items.add(crateItem(c("&4&lOmega &7Key"), Material.TRIPWIRE_HOOK, 10));
            items.add(crateItem(c("&a+1.5 Multi"), Material.EMERALD, 5));
        }
        if (crate.equals("Omega")) {
            items.add(crateItem(c("&e70,000 Tokens"), Material.MAGMA_CREAM, 50));
            items.add(crateItem(c("&e3x &e&lToken &7Key"), Material.TRIPWIRE_HOOK, 25));
            items.add(crateItem(c("&e2x &c&lBeta &7Key"), Material.TRIPWIRE_HOOK, 15));
            items.add(crateItem(c("&a+3.0 Multi"), Material.EMERALD, 9));
            items.add(crateItem(c("&4&l&ki&f&lSeasonal&4&l&ki&r &7Key"), Material.TRIPWIRE_HOOK, 1));
        }
        if (crate.equals("Token")) {
            items.add(crateItem(c("&e75,000-250,000 Tokens"), Material.MAGMA_CREAM, 100));
        }
        if (crate.equals("Vote")) {
            items.add(crateItem(c("&e100,000-500,000 Tokens"), Material.MAGMA_CREAM, 40));
            items.add(crateItem(c("&e3x &e&lToken &7Key"), Material.TRIPWIRE_HOOK, 25));
            items.add(crateItem(c("&a&l5,000 Pickaxe XP"), Material.EXPERIENCE_BOTTLE, 15));
            items.add(crateItem(c("&a+5.0 Multi"), Material.EMERALD, 10));
            items.add(crateItem(c("&4&l&ki&f&lSeasonal&4&l&ki&r &7Key"), Material.TRIPWIRE_HOOK, 9));
            items.add(crateItem(c("&3&lRank &7Key"), Material.TRIPWIRE_HOOK, 1));
        }
        if (crate.equals("Seaosnal")) {
            items.add(crateItem(c("&e300,000 Tokens"), Material.MAGMA_CREAM, 40));
            items.add(crateItem(c("&a+15.0 Multi"), Material.EMERALD, 25));
            items.add(crateItem(c("&a&l10,000 Pickaxe XP"), Material.EXPERIENCE_BOTTLE, 20));
            items.add(crateItem(c("&f&l• &c&lC&6&lo&e&ln&a&lt&3&lr&9&la&5&lb&c&la&6&ln&e&ld &3&lC&9&lr&5&la&c&lt&6&le &f&l•"), Material.ENDER_CHEST, 12.5));
            items.add(crateItem(c("&3&lRank &7Key"), Material.TRIPWIRE_HOOK, 2));
            items.add(crateItem(c("&f&l&k[&7&l*&f&l&k]&r &c&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r"), Material.ENDER_CHEST, 0.5));
        }
        if (crate.equals("Rank")) {
            items.add(crateItem(c("&b&lSponsor Rank"), Material.NETHER_STAR, 15));
            items.add(crateItem(c("&a&lVIP Rank"), Material.NETHER_STAR, 15));
            items.add(crateItem(c("&6&lMVP Rank"), Material.NETHER_STAR, 20));
            items.add(crateItem(c("&c&lHero Rank"), Material.NETHER_STAR, 20));
            items.add(crateItem(c("&5&lDemi-God Rank"), Material.NETHER_STAR, 15));
            items.add(crateItem(c("&3&lTitan Rank"), Material.NETHER_STAR, 10));
            items.add(crateItem(c("&d&lGod Rank"), Material.NETHER_STAR, 5));
            items.add(crateItem(c("&e&lOlympian Rank"), Material.NETHER_STAR, 1));
            items.add(crateItem(c("&a&l&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank"), Material.NETHER_STAR, 0.1));
        }
        if (crate.equals("Community")) {
            items.add(crateItem(c("&bRandom &7Tag"), Material.NAME_TAG, 21));
            items.add(crateItem(c("&b2x Sell Boost 10:00"), BoostsHandler.getInstance().Boost((short) 12, "sell", 2, 600), 13));
            items.add(crateItem(c("&e2x Token Boost 10:00"), BoostsHandler.getInstance().Boost((short) 11, "token", 2, 600), 13));
            items.add(crateItem(c("&d2x Enchant Boost 10:00"), BoostsHandler.getInstance().Boost((short) 5, "enchant", 2, 600), 13));
            items.add(crateItem(c("&c2x XP Boost 10:00"), BoostsHandler.getInstance().Boost((short) 1, "xp", 2, 600), 13));
            items.add(crateItem(c("&a2x Gem Boost 10:00"), BoostsHandler.getInstance().Boost((short) 10, "gem", 2, 600), 12));
            items.add(crateItem(c("&cR&6a&en&ad&3o&9m &cC&6h&ea&at &9C&5o&cl&6o&er"), Material.PAPER, 15));
        }
        return items;
    }

    public void openCrateInv(Player p, String crate) {
        Inventory inv = Bukkit.createInventory(null, 9, c("&b" + crate + " Crate"));
        for (ItemStack item : crateItems(crate)) {
            inv.addItem(item);
        }
        p.openInventory(inv);
    }

    public void openCrate(Player p, String crate, int amt) {
        if (crate.equals("Alpha")) {
            openAlpha(p, amt);
        }
        if (crate.equals("Beta")) {
            openBeta(p, amt);
        }
        if (crate.equals("Omega")) {
            openOmega(p, amt);
        }
        if (crate.equals("Vote")) {
            openVote(p, amt);
        }
        if (crate.equals("Token")) {
            openToken(p, amt);
        }
        if (crate.equals("Community")) {
            openCommunity(p, amt);
        }
        if (crate.equals("Seasonal")) {
            openSeasonal(p, amt);
        }
        if (crate.equals("Rank")) {
            openRank(p, amt);
        }
    }

    public void openAlpha(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Alpha", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 500) {
                Tokens.getInstance().addTokens(p, 30000);
            }
            if (ri > 500 && ri <= 750) {
                addKey(p, "Token", 1);
            }
            if (ri > 750 && ri <= 900) {
                addKey(p, "Beta", 1);
            }
            if (ri > 900) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 1.0);
            }
        }
    }

    public void openToken(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Token", amount);
        for (int i = 0; i < amount; i++) {
            int min = 75000;
            int max = 250000;
            int tokens = r.nextInt(max - min) + min;
            Tokens.getInstance().addTokens(p, tokens);
        }
    }

    public void openBeta(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Beta", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 500) {
                Tokens.getInstance().addTokens(p, 50000);
            }
            if (ri > 500 && ri <= 750) {
                addKey(p, "Token", 2);
            }
            if (ri > 750 && ri <= 850) {
                addKey(p, "Alpha", 2);
            }
            if (ri > 850 && ri <= 950) {
                addKey(p, "Omega", 1);
            }
            if (ri > 950) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 1.5);
            }
        }
    }

    public void openOmega(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Omega", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 500) {
                Tokens.getInstance().addTokens(p, 70000);
            }
            if (ri > 500 && ri <= 750) {
                addKey(p, "Token", 3);
            }
            if (ri > 750 && ri <= 900) {
                addKey(p, "Beta", 2);
            }
            if (ri > 900 && ri <= 990) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 3.0);
            }
            if (ri > 990) {
                addKey(p, "Seasonal", 1);
            }
        }
    }

    public void openVote(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Vote", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 400) {
                int min = 100000;
                int max = 500000;
                int tokens = r.nextInt(max - min) + min;
                Tokens.getInstance().addTokens(p, tokens);
            }
            if (ri > 400 && ri <= 650) {
                addKey(p, "Token", 3);
            }
            if (ri > 650 && ri <= 800) {
                PickXPHandler.getInstance().addXP(p, 5000);
            }
            if (ri > 800 && ri <= 900) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 5.0);
            }
            if (ri > 900 && ri <= 990) {
                addKey(p, "Seasonal", 1);
            }
            if (ri > 990) {
                addKey(p, "Rank", 1);
            }
        }
    }

    public void openSeasonal(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Seasonal", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 400) {
                Tokens.getInstance().addTokens(p, 300000);
            }
            if (ri > 400 && ri <= 650) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 15.0);
            }
            if (ri > 650 && ri <= 850) {
                PickXPHandler.getInstance().addXP(p, 10000);
            }
            if (ri > 850 && ri <= 975) {
                p.getInventory().addItem(CrateFunctions.ContrabandCrate());
            }
            if (ri > 975 && ri <= 995) {
                addKey(p, "Rank", 1);
            }
            if (ri > 995) {
                p.getInventory().addItem(CrateFunctions.GenesisCrate());
            }
        }
    }

    public void openRank(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Rank", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 131) { //13.2% 0-131
                p.getInventory().addItem(CMDRanks.rankItem("&b&lSponsor"));
            }
            if (ri > 131 && ri <= 282) {//15% 132-282
                p.getInventory().addItem(CMDRanks.rankItem("&a&lVIP"));
            }
            if (ri > 282 && ri <= 483) {//20% 283-483
                p.getInventory().addItem(CMDRanks.rankItem("&6&lMVP"));
            }
            if (ri > 483 && ri <= 684) {//20% 484-684
                p.getInventory().addItem(CMDRanks.rankItem("&c&lHero"));
            }
            if (ri > 684 && ri <= 835) {//15% 685-835
                p.getInventory().addItem(CMDRanks.rankItem("&5&lDemi-God"));
            }
            if (ri > 835 && ri <= 936) {//10% 836-936
                p.getInventory().addItem(CMDRanks.rankItem("&3&lTitan"));
            }
            if (ri > 936 && ri <= 987) {//5% 937-987
                p.getInventory().addItem(CMDRanks.rankItem("&d&lGod"));
            }
            if (ri > 987 && ri <= 998) {//1% 988-998
                p.getInventory().addItem(CMDRanks.rankItem("&e&lOlympian"));
            }
            if (ri == 999) {//0.1%
                p.getInventory().addItem(CMDRanks.rankItem("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls"));
            }
        }
    }

    public void openCommunity(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Community", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(100);
            if (ri <= 20) {
                CMDTags.addRandomTag(p);
            }
            if (ri > 20 && ri <= 33) {
                BoostsHandler.getInstance().giveBoost(p, "sell", 2, 600);
            }
            if (ri > 33 && ri <= 46) {
                BoostsHandler.getInstance().giveBoost(p, "token", 2, 600);
            }
            if (ri > 46 && ri <= 59) {
                BoostsHandler.getInstance().giveBoost(p, "enchant", 2, 600);
            }
            if (ri > 59 && ri <= 72) {
                BoostsHandler.getInstance().giveBoost(p, "xp", 2, 600);
            }
            if (ri > 72 && ri <= 84) {
                BoostsHandler.getInstance().giveBoost(p, "gem", 2, 600);
            }
            if (ri > 84 && ri <= 86) {
                Main.perms.playerAdd(p, "ChatColor.White");
            }
            if (ri > 86 && ri <= 88) {
                Main.perms.playerAdd(p, "ChatColor.Yellow");
            }
            if (ri > 88 && ri <= 90) {
                Main.perms.playerAdd(p, "ChatColor.Blue");
            }
            if (ri > 90 && ri <= 92) {
                Main.perms.playerAdd(p, "ChatColor.Green");
            }
            if (ri > 92 && ri <= 94) {
                Main.perms.playerAdd(p, "ChatColor.Purple");
            }
            if (ri > 94 && ri <= 96) {
                Main.perms.playerAdd(p, "ChatColor.Gold");
            }
            if (ri > 96 && ri <= 98) {
                Main.perms.playerAdd(p, "ChatColor.Red");
            }
            if (ri > 98) {
                Main.perms.playerAdd(p, "ChatColor.Lime");
            }
        }
    }

    public void openAll(Player p) {
        String uuid = p.getUniqueId().toString();
        int a = this.settings.getLocksmith().getInt(uuid + ".alpha");
        int b = this.settings.getLocksmith().getInt(uuid + ".beta");
        int o = this.settings.getLocksmith().getInt(uuid + ".omega");
        int to = this.settings.getLocksmith().getInt(uuid + ".token");
        int v = this.settings.getLocksmith().getInt(uuid + ".vote");
        int s = this.settings.getLocksmith().getInt(uuid + ".seasonal");
        int c = this.settings.getLocksmith().getInt(uuid + ".community");
        int r = this.settings.getLocksmith().getInt(uuid + ".rank");
        if (a == 0 && b == 0 && o == 0 && to == 0 && v == 0 && s == 0 && c == 0 && r == 0) return;
        openAlpha(p, a);
        openBeta(p, b);
        openOmega(p, o);
        openToken(p, to);
        openVote(p, v);
        openSeasonal(p, s);
        openCommunity(p, c);
        openRank(p, r);
        settings.saveLocksmith();
        openAll(p);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Crate"))
            e.setCancelled(true);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("crateinfo")) {
            if (args.length == 1) {
                Player p = (Player) sender;
                String crate = args[0];
                if (crate.equalsIgnoreCase("alpha")) {
                    openCrateInv(p, "Alpha");
                } else if (crate.equalsIgnoreCase("beta")) {
                    openCrateInv(p, "Beta");
                } else if (crate.equalsIgnoreCase("omega")) {
                    openCrateInv(p, "Omega");
                } else if (crate.equalsIgnoreCase("token")) {
                    openCrateInv(p, "Token");
                } else if (crate.equalsIgnoreCase("seasonal")) {
                    openCrateInv(p, "Seasonal");
                } else if (crate.equalsIgnoreCase("rank")) {
                    openCrateInv(p, "Rank");
                } else if (crate.equalsIgnoreCase("community")) {
                    openCrateInv(p, "Community");
                } else if (crate.equalsIgnoreCase("vote")) {
                    openCrateInv(p, "Vote");
                } else {
                    p.sendMessage(c("&f&lCrates &8| &cNot a Crate."));
                }
            } else {
                sender.sendMessage(c("&f&lCrates &8| &cPlease Specify a Crate."));
            }
        }
        if (label.equalsIgnoreCase("openall")) {
            Player p = (Player) sender;
            if (p.hasPermission("command.openall")) {
                openAll(p);
            } else {
                p.sendMessage(c("&f&lCrates &8| &bYou must be rank Hero+ to use /openall"));
            }
            p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
            double percents;
            p.getScoreboard().getTeam("balance").setSuffix(c("&a" + Main.formatAmt(Tokens.getInstance().getBalance(p))));
            percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) * 100);
            double dmultiply = percents * 10.0;
            double dRound = Math.round(dmultiply) / 10.0;
            if (RankupHandler.getInstance().getRank(p) == 100) {
                p.getScoreboard().getTeam("percent").setSuffix(c("&c/prestige"));
            } else {
                if (dRound >= 100.0) {
                    p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
                } else {
                    p.getScoreboard().getTeam("percent").setSuffix(c("&c") + (dRound) + "%");
                }
            }
            p.getScoreboard().getTeam("tokens").setSuffix(c("&e" + Main.formatAmt(Tokens.getInstance().getTokens(p))));
            PlayerDataHandler.getInstance().savePlayerData(p);
            settings.saveLocksmith();
        }


        if (label.equalsIgnoreCase("cratekey"))
            if (args.length == 0) {
                StringBuilder keys = new StringBuilder();
                for (String s : this.cr.getKeys(false)) {
                    if (!s.contains("."))
                        keys.append(s).append(" ");
                }
                if (!sender.hasPermission("cratekey.give")) {
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command!"));
                    return false;
                }
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease choose a key from the list below."));
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7") + keys.toString().trim());
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
            } else {
                if (args.length == 1) {
                    Player p = (Player) sender;
                    if (p.hasPermission("cratekey.give")) {
                        if (formatKey(args[0]).equalsIgnoreCase("error")) {
                            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat key does not exist."));
                            return false;
                        }
                        addKey(p, formatKey(args[0]), 1);
                        //p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| &b+1 " + formatKey(args[0]) + " &bKey!"));
                        return true;
                    }
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to use this command."));
                    return false;
                }
                if (args.length == 2) {
                    if (Bukkit.getPlayer(args[0]) == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is not online!"));
                        return false;
                    }
                    if (!sender.hasPermission("cratekey.give.other")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to give other players keys!"));
                        return false;
                    }
                    Player p = Bukkit.getPlayer(args[0]);
                    //p.getInventory().addItem(new ItemStack[] { getKey(formatKey(args[0]), 1) });
                    addKey(p, formatKey(args[1]), 1);
                    //p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| &b+1 " + formatKey(args[1]) + " &bKey!"));
                    return false;

                }
                if (args.length == 3) {
                    int i = Integer.parseInt(args[2]);
                    if (Bukkit.getPlayer(args[0]) == null) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThat player is not online!"));
                        return false;
                    }
                    if (!sender.hasPermission("cratekey.give.other")) {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have permission to give other players keys!"));
                        return false;
                    }
                    Player p = Bukkit.getPlayer(args[0]);
                    addKey(p, formatKey(args[1]), i);
                    //p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| &b+"+i +" "+ formatKey(args[1]) + " &bKey!"));
                    return false;
                } else {
                    StringBuilder keys = new StringBuilder();
                    for (String s : this.cr.getKeys(false)) {
                        if (!s.contains("."))
                            keys.append(s).append(" ");
                    }
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlease choose a key from the list below."));
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7") + keys.toString().trim());
                    sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8&m-----------------------------------------------------"));
                }
            }
        return true;
    }
}
