package me.dxrk.Events;

import me.dxrk.Commands.CMDRanks;
import me.dxrk.Commands.CMDTags;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import mkremins.fanciful.FancyMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.*;

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

    private double getRelativeCoord(int i) {
        double d = i;
        d = (d < 0.0D) ? (d - 0.5D) : (d + 0.5D);
        return d;
    }


    public Location getCenter(Location loc) {
        return new Location(loc.getWorld(), getRelativeCoord(loc.getBlockX()), getRelativeCoord(loc.getBlockY()),
                getRelativeCoord(loc.getBlockZ()));
    }

    public void effectsAF() {
        (new BukkitRunnable() {
            public void run() {
                try {
                    for (Location loc : CrateHandler.this.locations())
                        loc.getWorld().playEffect(loc, Effect.POTION_SWIRL, 2);
                } catch (Exception localException) {
                    Bukkit.getLogger().info(CrateHandler.this.c("&4FAILED TO LOAD PARTICLES AROUND CRATES!"));
                }
            }
        }).runTaskTimer(Main.plugin, 0L, 5L);
    }

    public List<Location> locations() {
        ArrayList<Location> locations = new ArrayList<>();
        for (String s : this.cr.getKeys(false)) {
            if (!s.contains(".")) {
                Location loc = new Location(Bukkit.getWorld(this.cr.getString(s + ".Location.World")), this.cr.getInt(s + ".Location.X"), this.cr.getInt(s + ".Location.Y"), this.cr.getInt(s + ".Location.Z"));
                locations.add(loc);
            }
        }
        return locations;
    }

    public boolean isCrate(Block b) {
        boolean is = false;
        Location l = b.getLocation();
        String w = l.getWorld().getName();
        int x = l.getBlockX();
        int y = l.getBlockY();
        int z = l.getBlockZ();
        for (Location loc : locations()) {
            if (loc.getWorld().getName().equals(w) && loc.getBlockX() == x && loc.getBlockY() == y && loc.getBlockZ() == z)
                is = true;
        }
        return !is;
    }

    public String getCrate(Block b) {
        Location loc = b.getLocation();
        for (String s : this.cr.getKeys(true)) {
            if (!s.contains(".") && this.cr.getInt(s + ".Location.X") == loc.getBlockX() &&
                    this.cr.getInt(s + ".Location.Y") == loc.getBlockY() && this.cr.getInt(s + ".Location.Z") == loc.getBlockZ() &&
                    this.cr.getString(s + ".Location.World").equalsIgnoreCase(b.getLocation().getWorld().getName()))
                return s;
        }
        return null;
    }

    public ItemStack getKey(String key, int amt) {
        ItemStack keyy = new ItemStack(Material.getMaterial(this.cr.getInt(key + ".Key.ID")), amt);
        ItemMeta am = keyy.getItemMeta();
        am.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.cr.getString(key + ".Key.Name")));
        am.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', this.cr.getString(key + ".Key.Lore"))));
        keyy.setItemMeta(am);
        return keyy;
    }

    public boolean isKey(ItemStack a, String s) {
        if (!a.hasItemMeta())
            return false;
        if (!a.getItemMeta().hasLore())
            return false;
        if (!a.getItemMeta().hasDisplayName())
            return false;
        if (!c(this.cr.getString(s + ".Key.Name")).equals(a.getItemMeta().getDisplayName()))
            return false;
        if (!c(this.cr.getString(s + ".Key.Lore")).equals(a.getItemMeta().getLore().get(0)))
            return false;
        return this.cr.getInt(s + ".Key.ID") == a.getTypeId();
    }

    public int getRows(String s) {
        return this.cr.getInt(s + ".Rows");
    }

    public ItemStack spacer() {
        ItemStack a = new ItemStack(Material.AIR);
        return a;
    }

    public String checkForSlot(ItemStack item, String crate) {
        String slot = "";
        for (String s : getRewards(crate)) {
            ItemStack x = loadItem(crate, "." + s);
            boolean same = x.getAmount() == item.getAmount();
            if (x.getType() != item.getType())
                same = false;
            if (!x.getItemMeta().getDisplayName().equals(item.getItemMeta().getDisplayName()))
                same = false;
            if (same)
                slot = s;
        }
        return slot;
    }

    public boolean slotHasCommands(String crate, String s) {
        return this.cr.get(crate + "." + s + ".Command") != null;
    }

    public List<String> getCommands(String crate, String s) {
        return this.cr.getStringList(crate + "." + s + ".Command");
    }

    public void loadCrate(Player p, String s) {
        if (!s.contains(".")) {
            Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, c("&c" + s + " Crate"));
            for (int i = 0; i < inv.getSize(); i++) {
                if (this.cr.get(s + ".Slot" + i + ".ItemID") != null) {
                    inv.setItem(i, loadItem(s, ".Slot" + i));
                } else {
                    inv.setItem(i, spacer());
                }
            }
            p.openInventory(inv);
        }
    }

    public void spinCrate(final Player p, final String crate, final Inventory inv, int time, final Sound s, final boolean end) {
        (new BukkitRunnable() {
            public void run() {
                if (end) {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| " + inv.getItem(13).getItemMeta().getDisplayName()));
                    p.closeInventory();
                    String slot = CrateHandler.this.checkForSlot(inv.getItem(13), crate);
                    if (CrateHandler.this.slotHasCommands(crate, slot)) {
                        for (String ss : CrateHandler.this.getCommands(crate, slot)) {
                            ss = ss.replaceAll("%PLAYER%", p.getName());
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                        }
                    } else {
                        if (inv.getItem(13).getType() == Material.DIAMOND_PICKAXE) {
                            ItemStack hand = inv.getItem(13).clone();
                            ItemMeta am = hand.getItemMeta();
                            am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
                            am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                            hand.setItemMeta(am);
                            p.getInventory().addItem(hand);
                        } else {
                            p.getInventory().addItem(inv.getItem(13).clone());
                        }
                    }
                } else {
                    inv.setItem(17, inv.getItem(16).clone());
                    inv.setItem(16, inv.getItem(15).clone());
                    inv.setItem(15, inv.getItem(14).clone());
                    inv.setItem(14, inv.getItem(13).clone());
                    inv.setItem(13, inv.getItem(12).clone());
                    inv.setItem(12, inv.getItem(11).clone());
                    inv.setItem(11, inv.getItem(10).clone());
                    inv.setItem(10, inv.getItem(9).clone());
                    inv.setItem(9, CrateHandler.this.loadItem(crate, "." + CrateHandler.this.getRandom(crate)));
                    p.playSound(p.getLocation(), s, 1.0F, 1.0F);
                }
            }
        }).runTaskLater(Main.plugin, time);
    }

    public void openCrate(String crate, Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&cOpening Crate..."));
        int i;
        for (i = 0; i < inv.getSize(); i++)
            inv.setItem(i, spacer());
        inv.setItem(4, new ItemStack(Material.REDSTONE_TORCH_ON, 1));
        inv.setItem(22, new ItemStack(Material.REDSTONE_TORCH_ON, 1));
        inv.setItem(9, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(10, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(11, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(12, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(13, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(14, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(15, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(16, loadItem(crate, "." + getRandom(crate)));
        inv.setItem(17, loadItem(crate, "." + getRandom(crate)));
        p.openInventory(inv);
        for (i = 0; i < 60; i++)
            spinCrate(p, crate, inv, i, Sound.WOOD_CLICK, false);
        spinCrate(p, crate, inv, 80, Sound.CHICKEN_EGG_POP, true);
    }


    public int getValueInt(String s) {
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
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }

    public boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public double getValuedbl(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor(s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (byte) (b + 1)) {
            char c = arrayOfChar[b];
            if (!this.isDbl(c)) continue;
            lvl.append(c);
        }
        if (this.isDbl(lvl.toString())) {
            return Double.parseDouble(lvl.toString());
        }
        return -1;
    }

    public boolean isDbl(String s) {
        try {
            double i = Double.parseDouble(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isDbl(char ss) {
        String s = String.valueOf(ss);
        try {
            double i = Double.parseDouble(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }


    public void openSneakingCrate(String crate, Player p) {
        ItemStack won = loadItem(crate, "." + getRandom(crate));
        String slot = checkForSlot(won, crate);
        if (!ChatColor.stripColor(won.getItemMeta().getDisplayName()).contains("Tokens"))
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| " + won.getItemMeta().getDisplayName()));
        if (slotHasCommands(crate, slot)) {
            for (String ss : getCommands(crate, slot)) {
                ss = ss.replaceAll("%PLAYER%", p.getName());
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
            }
        } else {
            if (won.getType() == Material.DIAMOND_PICKAXE) {
                ItemStack hand = won.clone();
                ItemMeta am = hand.getItemMeta();
                am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
                am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                hand.setItemMeta(am);
                p.getInventory().addItem(hand);
            } else {
                p.getInventory().addItem(won);
            }
            p.updateInventory();
        }
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


    public String getRandom(String s) {
        List<String> items = new ArrayList<>();
        Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, "test");
        for (int i = 0; i < inv.getSize(); i++) {
            if (this.cr.get(s + ".Slot" + i + ".Name") != null) {
                for (int xi = 0; xi < this.cr.getInt(s + ".Slot" + i + ".Chance"); xi++) {
                    items.add("Slot" + i);
                }
            }
        }


        Random r = new Random();
        int n = r.nextInt(items.size());
        String slot = items.get(n);
        return slot;
    }

    public List<String> getRewards(String s) {
        List<String> items = new ArrayList<>();
        Inventory inv = Bukkit.createInventory(null, getRows(s) * 9, "test");
        for (int i = 0; i < inv.getSize(); i++) {
            if (this.cr.get(s + ".Slot" + i + ".Name") != null) {
                for (int xi = 0; xi < this.cr.getInt(s + ".Slot" + i + ".Chance"); xi++) {
                    items.add("Slot" + i);
                }
            }
        }
        return items;
    }

    @SuppressWarnings("deprecation")
    public ItemStack loadItem(String s, String slot) {
        String name = this.cr.getString(s + slot + ".Name");
        int amt = 1;
        if (this.cr.getInt(s + slot + ".Amount") > 0)
            amt = this.cr.getInt(s + slot + ".Amount");
        int id = 1;
        if (this.cr.getInt(s + slot + ".ItemID") > 0)
            id = this.cr.getInt(s + slot + ".ItemID");
        int sh = 0;
        if (this.cr.get(s + slot + ".Short") != null)
            sh = this.cr.getInt(s + slot + ".Short");
        List<String> lore = null;
        if (this.cr.getStringList(s + slot + ".Lore") != null)
            lore = this.cr.getStringList(s + slot + ".Lore");
        List<String> ench = null;
        if (this.cr.getStringList(s + slot + ".Enchantments") != null)
            ench = this.cr.getStringList(s + slot + ".Enchantments");
        ItemStack item = new ItemStack(id, amt, (short) sh);
        ItemMeta am = item.getItemMeta();
        am.setDisplayName(c(name));
        if (lore != null) {
            List<String> newlore = new ArrayList<>();
            for (String l : lore)
                newlore.add(c(l.replaceAll("�", "&")));
            am.setLore(newlore);
        }
        if (id == 373) {
            am.addItemFlags(ItemFlag.values());
        }
        item.setItemMeta(am);
        if (ench != null)
            for (String e : ench)
                item.addUnsafeEnchantment(Enchantment.getById(Integer.parseInt(e.split(";")[0])), Integer.parseInt(e.split(";")[1]));
        return item;
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

    private int randomTokensClick(String crate) {
        if (crate.equals("Alpha")) return randomTokens(25000, 100000);
        if (crate.equals("Beta")) return randomTokens(30000, 125000);
        if (crate.equals("Omega")) return randomTokens(45000, 150000);
        if (crate.equals("Token")) return randomTokens(75000, 300000);
        if (crate.equals("Vote")) return randomTokens(100000, 500000);
        return 0;
    }

    @EventHandler
    public void onInt(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        String uuid = p.getUniqueId().toString();
        if (e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if (isCrate(e.getClickedBlock()))
                return;
            e.setCancelled(true);

            loadCrate(p, getCrate(e.getClickedBlock()));
        } else if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (isCrate(e.getClickedBlock()))
                return;
            e.setCancelled(true);
            if (this.settings.getLocksmith().getInt(uuid + "." + getCrate(e.getClickedBlock()).toLowerCase()) > 0) {
                int keys = this.settings.getLocksmith().getInt(uuid + "." + getCrate(e.getClickedBlock()).toLowerCase());

                if (p.isSneaking()) {
                    if (keys == 1) {
                        takeKey(p, getCrate(e.getClickedBlock()), 1);
                        openSneakingCrate(getCrate(e.getClickedBlock()), p);
                    } else {
                        takeKey(p, getCrate(e.getClickedBlock()), keys);
                        double multi = 0;
                        int tokens = 0;
                        int token = 0;
                        int alpha = 0;
                        int beta = 0;
                        int omega = 0;

                        ArrayList<String> rw = new ArrayList<>();
                        ArrayList<String> rww = new ArrayList<>();
                        int i;
                        for (i = 0; i < keys; i++) {
                            ItemStack won = loadItem(getCrate(e.getClickedBlock()), "." + getRandom(getCrate(e.getClickedBlock())));
                            String slot = checkForSlot(won, getCrate(e.getClickedBlock()));
                            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

                            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
                            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

                            if (name.contains("Tokens")) {
                                int tgive = randomTokensClick(getCrate(e.getClickedBlock()));
                                tokens += tgive;
                                continue;

                            } else if (name.contains("Multi")) {

                                String[] nn = name.split(" ");

                                double mm = Double.parseDouble(nn[1]);

                                multi += mm;
                            } else if (name.contains("Key")) {
                                if (name.contains("Token Key")) {
                                    token++;
                                }
                                if (name.contains("Alpha Key")) {
                                    alpha++;
                                }
                                if (name.contains("Beta Key")) {
                                    beta++;
                                }
                                if (name.contains("Omega Key")) {
                                    omega++;
                                }

                            } else {
                                rww.add(c(won.getItemMeta().getDisplayName()));
                            }


                            if (slotHasCommands(getCrate(e.getClickedBlock()), slot)) {
                                for (String ss : getCommands(getCrate(e.getClickedBlock()), slot)) {
                                    ss = ss.replaceAll("%PLAYER%", p.getName());
                                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                                }
                            } else {
                                if (won.getType() == Material.DIAMOND_PICKAXE) {
                                    ItemStack hand = won.clone();
                                    ItemMeta am = hand.getItemMeta();
                                    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
                                    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                                    hand.setItemMeta(am);
                                    p.getInventory().addItem(hand);
                                } else {
                                    p.getInventory().addItem(won);
                                }
                                p.updateInventory();
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
                        }
                        // String[] rewards = {c("&7&lMulti: "+multi+"x"), c("&7&lTokens: "+tokens), c("&7&lMoney: "+Main.formatAmt(money)), c("&7Other:")};
                        if (token > 0)
                            rww.add(c("&e" + token + "x &e&lToken &7Key"));
                        if (alpha > 0)
                            rww.add(c("&e" + alpha + "x &7&lAlpha &7Key"));
                        if (beta > 0)
                            rww.add(c("&e" + beta + "x &c&lBeta &7Key"));
                        if (omega > 0)
                            rww.add(c("&e" + omega + "x &4&lOmega &7Key"));
                        ArrayList<String> rw2 = new ArrayList<>(rww);

                        FancyMessage reward = new FancyMessage("");
                        reward.then(c("&f&lRewards &8| &b(Hover)")).tooltip(rw2);
                        reward.send(p);
                        if (tokens > 0) {
                            p.sendMessage(c("&e+⛀" + Main.formatAmt(tokens)));
                            Tokens.getInstance().addTokens(p, tokens);
                        }
                        if (multi > 0)
                            p.sendMessage(c("&a+" + multi + " Multi"));
                    }

                } else {
                    if (keys == 1) {
                        takeKey(p, getCrate(e.getClickedBlock()), 1);
                    } else {
                        takeKey(p, getCrate(e.getClickedBlock()), 1);
                    }
                    openSneakingCrate(getCrate(e.getClickedBlock()), p);
                }
                settings.saveLocksmith();
            } else {
                e.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lCrates &8| &bYou do not have a &4" + getCrate(e.getClickedBlock()) + " &bKey."));
            }
        }
    }

    private int randomTokens(int min, int max) {
        Random r = new Random();
        int tokens = r.nextInt(max - min) + min;

        return tokens;
    }

    public void addKey(Player p, String key, int amt) {
        int keysfound = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".KeysFound");
        PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".KeysFound", keysfound + amt);
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId().toString() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId().toString() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId().toString() + ".name", p.getName());
            this.settings.saveLocksmith();
        }
        this.settings.getLocksmith().set(p.getUniqueId().toString() + "." + key, keys + amt);
    }

    public void takeKey(Player p, String key, int amt) {
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId().toString() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId().toString() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId().toString() + ".name", p.getName());
        }
        this.settings.getLocksmith().set(p.getUniqueId().toString() + "." + key, keys - amt);
    }


    public void openAlpha(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Alpha", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 500) {
                Tokens.getInstance().addTokens(p, 50000);
            }
            if (ri > 500 && ri <= 750) {
                addKey(p, "Token", 1);
            }
            if (ri > 750 && ri <= 850) {
                addKey(p, "Beta", 1);
            }
            if (ri > 850) {
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
            if (ri > 850 && ri <= 900) {
                addKey(p, "Omega", 1);
            }
            if (ri > 900) {
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
                Tokens.getInstance().addTokens(p, 100000);
            }
            if (ri > 500 && ri <= 750) {
                addKey(p, "Token", 2);
            }
            if (ri > 750 && ri <= 850) {
                addKey(p, "Alpha", 2);
            }
            if (ri > 850 && ri <= 900) {
                addKey(p, "Beta", 2);
            }
            if (ri > 900) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 3.0);
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
                addKey(p, "Seasonal", 1);
            }
            if (ri > 800 && ri <= 900) {
                PickXPHandler.getInstance().addXP(p, 5000);
            }
            if (ri > 900 && ri <= 990) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 5.0);
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
                Tokens.getInstance().addTokens(p, 500000);
            }
            if (ri > 400 && ri <= 750) {
                SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 15.0);
            }
            if (ri > 750 && ri <= 990) {
                p.getInventory().addItem(CrateFunctions.ContrabandCrate());
            }
            if (ri > 990) {
                p.getInventory().addItem(CrateFunctions.GenesisCrate());
            }

        }
    }

    public void openRank(Player p, int amount) {
        Random r = new Random();
        takeKey(p, "Rank", amount);
        for (int i = 0; i < amount; i++) {
            int ri = r.nextInt(1000);
            if (ri <= 150) {
                p.getInventory().addItem(CMDRanks.rankItem("&b&lSponsor"));
            }
            if (ri > 150 && ri <= 300) {
                p.getInventory().addItem(CMDRanks.rankItem("&a&lVIP"));
            }
            if (ri > 300 && ri <= 450) {
                p.getInventory().addItem(CMDRanks.rankItem("&6&lMVP"));
            }
            if (ri > 450 && ri < 600) {
                p.getInventory().addItem(CMDRanks.rankItem("&c&lHero"));
            }
            if (ri > 600 && ri < 800) {
                p.getInventory().addItem(CMDRanks.rankItem("&5&lDemi-God"));
            }
            if (ri > 800 && ri < 925) {
                p.getInventory().addItem(CMDRanks.rankItem("&3&lTitan"));
            }
            if (ri > 925 && ri < 975) {
                p.getInventory().addItem(CMDRanks.rankItem("&d&lGod"));
            }
            if (ri > 975 && ri <= 997) {
                p.getInventory().addItem(CMDRanks.rankItem("&e&lOlympian"));
            }
            if (ri > 997) {
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
            if (ri > 20 && ri < 36) {
                BoostsHandler.getInstance().giveBoost(p, "sell", 2, 600);
            }
            if (ri > 37 && ri < 53) {
                BoostsHandler.getInstance().giveBoost(p, "sell", 1.5, 900);
            }
            if (ri > 54 && ri < 69) {
                BoostsHandler.getInstance().giveBoost(p, "xp", 1.5, 900);
            }
            if (ri > 69 && ri < 84) {
                BoostsHandler.getInstance().giveBoost(p, "xp", 2, 600);
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


    public void openall(Player p, int alpha, int beta, int omega, int token, int seasonal, int rank, int community, int vote, List<String> rw, int t, double m) {
        takeKey(p, "Alpha", alpha);
        takeKey(p, "Beta", beta);
        takeKey(p, "Omega", omega);
        takeKey(p, "Token", token);
        takeKey(p, "Seasonal", seasonal);
        takeKey(p, "Rank", rank);
        takeKey(p, "Vote", vote);
        takeKey(p, "Community", community);
        double multi = m;
        int tokens = t;
        List<String> rww = rw;
        int i;
        for (i = 0; i < alpha; i++) {
            ItemStack won = loadItem("Alpha", "." + getRandom("Alpha"));
            String slot = checkForSlot(won, "Alpha");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                int tgive = randomTokens(25000, 100000);
                tokens += tgive;

                continue;

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Alpha", slot)) {
                for (String ss : getCommands("Alpha", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            } else {
                if (won.getType() == Material.DIAMOND_PICKAXE) {
                    ItemStack hand = won.clone();
                    ItemMeta am = hand.getItemMeta();
                    am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
                    am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    hand.setItemMeta(am);
                    p.getInventory().addItem(hand);
                } else {
                    p.getInventory().addItem(won);
                }
                p.updateInventory();
            }

        }
        for (i = 0; i < beta; i++) {
            ItemStack won = loadItem("Beta", "." + getRandom("Beta"));
            String slot = checkForSlot(won, "Beta");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                int tgive = randomTokens(30000, 125000);
                tokens += tgive;

                continue;

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Beta", slot)) {
                for (String ss : getCommands("Beta", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            }

        }
        for (i = 0; i < omega; i++) {
            ItemStack won = loadItem("Omega", "." + getRandom("Omega"));
            String slot = checkForSlot(won, "Omega");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                int tgive = randomTokens(45000, 150000);
                tokens += tgive;

                continue;

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Omega", slot)) {
                for (String ss : getCommands("Omega", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            }

        }
        for (i = 0; i < token; i++) {
            ItemStack won = loadItem("Token", "." + getRandom("Token"));
            String slot = checkForSlot(won, "Token");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                int tgive = randomTokens(75000, 300000);
                tokens += tgive;

                continue;

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Token", slot)) {
                for (String ss : getCommands("Token", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            }

        }
        for (i = 0; i < seasonal; i++) {
            ItemStack won = loadItem("Seasonal", "." + getRandom("Seasonal"));
            String slot = checkForSlot(won, "Seasonal");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                tokens += getValueInt(name);

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Seasonal", slot)) {
                for (String ss : getCommands("Seasonal", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            }

        }
        for (i = 0; i < rank; i++) {
            ItemStack won = loadItem("Rank", "." + getRandom("Rank"));
            String slot = checkForSlot(won, "Rank");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                tokens += getValueInt(name);

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Rank", slot)) {
                for (String ss : getCommands("Rank", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            }

        }
        for (i = 0; i < community; i++) {
            ItemStack won = loadItem("Community", "." + getRandom("Community"));
            String slot = checkForSlot(won, "Community");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                tokens += getValueInt(name);

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Community", slot)) {
                for (String ss : getCommands("Community", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            }

        }
        for (i = 0; i < vote; i++) {
            ItemStack won = loadItem("Vote", "." + getRandom("Vote"));
            String slot = checkForSlot(won, "Vote");
            String name = ChatColor.stripColor(won.getItemMeta().getDisplayName());

            int crates = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".CratesOpened");
            PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId().toString() + ".CratesOpened", crates + 1);

            if (name.contains("Tokens")) {
                int tgive = randomTokens(100000, 500000);
                tokens += tgive;

                continue;

            } else if (name.contains("Multi")) {

                String[] nn = name.split(" ");

                double mm = Double.parseDouble(nn[1]);

                multi += mm;
            } else if (!name.contains("Key")) {
                rww.add(c(won.getItemMeta().getDisplayName()));
            }


            if (slotHasCommands("Vote", slot)) {
                for (String ss : getCommands("Vote", slot)) {
                    ss = ss.replaceAll("%PLAYER%", p.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ss);
                }
            }

        }

        if (LocksmithHandler.getInstance().hasKeys(p)) {
            String uuid = p.getUniqueId().toString();
            int a = this.settings.getLocksmith().getInt(uuid + ".alpha");
            int b = this.settings.getLocksmith().getInt(uuid + ".beta");
            int o = this.settings.getLocksmith().getInt(uuid + ".omega");
            int to = this.settings.getLocksmith().getInt(uuid + ".token");
            int v = this.settings.getLocksmith().getInt(uuid + ".vote");
            int s = this.settings.getLocksmith().getInt(uuid + ".seasonal");
            int c = this.settings.getLocksmith().getInt(uuid + ".community");
            int r = this.settings.getLocksmith().getInt(uuid + ".rank");
            openall(p, a, b, o, to, s, r, c, v, rww, tokens, multi);
            return;
        }


        FancyMessage reward = new FancyMessage("");
        reward.then(c("&f&lRewards &8| &b(Hover)")).tooltip(rww);
        if (rww.size() > 0) {
            reward.send(p);
        }
        p.sendMessage(c("&e+⛀" + Main.formatAmt(tokens)));
        Tokens.getInstance().addTokens(p, tokens);
        p.sendMessage(c("&a+" + multi + " Multi"));


    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().contains("Crate"))
            e.setCancelled(true);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("crateinfo")) {
            if (args.length == 1) {
                Player p = (Player) sender;
                String crate = args[0];
                if (crate.equalsIgnoreCase("alpha")) {
                    loadCrate(p, "Alpha");
                } else if (crate.equalsIgnoreCase("beta")) {
                    loadCrate(p, "Beta");
                } else if (crate.equalsIgnoreCase("omega")) {
                    loadCrate(p, "Omega");
                } else if (crate.equalsIgnoreCase("token")) {
                    loadCrate(p, "Token");
                } else if (crate.equalsIgnoreCase("seasonal")) {
                    loadCrate(p, "Seasonal");
                } else if (crate.equalsIgnoreCase("rank")) {
                    loadCrate(p, "Rank");
                } else if (crate.equalsIgnoreCase("community")) {
                    loadCrate(p, "Community");
                } else if (crate.equalsIgnoreCase("vote")) {
                    loadCrate(p, "Vote");
                } else {
                    p.sendMessage(c("&f&lCrates &8| &cNot a Crate."));
                }
            } else {
                sender.sendMessage(c("&f&lCrates &8| &cPlease Specify a Crate."));
            }
        }
        if (label.equalsIgnoreCase("openall")) {
            Player p = (Player) sender;
            boolean b = PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("BuildMode");
            if(b){
                p.sendMessage(c("&cYou can't access this while in buildmode."));
                return false;
            }
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
