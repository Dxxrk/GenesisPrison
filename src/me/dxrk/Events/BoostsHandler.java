package me.dxrk.Events;

import me.dxrk.Discord.jdaHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;

public class BoostsHandler implements Listener, CommandExecutor {
    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static BoostsHandler instance = new BoostsHandler();

    public static BoostsHandler getInstance() {
        return instance;
    }

    SettingsManager settings = SettingsManager.getInstance();


    public static boolean sactive = false;
    public static boolean xactive = false;
    public static double sell = 1;
    public static double xp = 1;

    public static ArrayList<String> nextUpsell = new ArrayList<>();
    public static ArrayList<String> nextUpxp = new ArrayList<>();

    public static String timeFormat(int i) {
        Date d = new Date(i * 1000L);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    public static int toSeconds(String s) {
        String[] format = ChatColor.stripColor(s).split(":");
        int hour = Integer.parseInt(format[0]);
        int mins = Integer.parseInt(format[1]);
        int seconds = Integer.parseInt(format[2]);
        return (hour * 3600) + (mins * 60) + seconds;
    }

    public ItemStack Spacer() {
        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 8);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9Genesis"));
        im.addEnchant(Enchantment.DURABILITY, 0, false);
        i.setItemMeta(im);
        i.removeEnchantment(Enchantment.DURABILITY);
        return i;
    }

    public ArrayList<ItemStack> QueueSell(ArrayList<String> array) {
        ArrayList<ItemStack> queue = new ArrayList<>();
        for (String s : array) {
            String[] split = s.split(" ");
            ItemStack item = new ItemStack(Material.POTION, 1, (short) 8260);
            ItemMeta im = item.getItemMeta();
            im.addItemFlags(ItemFlag.values());
            im.setDisplayName(c("&f&l" + split[2] + "x Currency Boost"));
            List<String> lore = new ArrayList<>();
            lore.add(c("&dActivated By: &f" + split[1]));
            lore.add(c("&d" + timeFormat(Integer.parseInt(split[3]))));
            im.setLore(lore);
            item.setItemMeta(im);
            queue.add(item);

        }

        return queue;
    }

    public ArrayList<ItemStack> QueueXP(ArrayList<String> array) {
        ArrayList<ItemStack> queue = new ArrayList<>();
        for (String s : array) {
            String[] split = s.split(" ");
            ItemStack item = new ItemStack(Material.EXP_BOTTLE);
            ItemMeta im = item.getItemMeta();
            im.addItemFlags(ItemFlag.values());
            im.setDisplayName(c("&f&l" + split[2] + "x XP Boost"));
            List<String> lore = new ArrayList<>();
            lore.add(c("&dActivated By: &f" + split[1]));
            lore.add(c("&d" + timeFormat(Integer.parseInt(split[3]))));
            im.setLore(lore);
            item.setItemMeta(im);
            queue.add(item);
        }

        return queue;
    }


    private Inventory boostPublic() {
        Inventory i = Bukkit.createInventory(null, 45, c("&3&lBoost Queue"));

        for (int x = 0; x < 45; x++) {
            i.setItem(x, Spacer());
        }


        ItemStack emptySell = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta esm = emptySell.getItemMeta();
        esm.setDisplayName(c("&7&lEmpty"));
        esm.addItemFlags(ItemFlag.values());
        emptySell.setItemMeta(esm);

        ItemStack emptyXP = new ItemStack(Material.GLASS_BOTTLE);
        ItemMeta exm = emptyXP.getItemMeta();
        exm.setDisplayName(c("&7&lEmpty"));
        exm.addItemFlags(ItemFlag.values());
        emptyXP.setItemMeta(exm);


        if (QueueSell(nextUpsell).size() > 0 && QueueSell(nextUpsell).get(0) != null) {
            i.setItem(11, QueueSell(nextUpsell).get(0));
        } else {
            i.setItem(11, emptySell);
        }

        if (QueueSell(nextUpsell).size() > 1 && QueueSell(nextUpsell).get(1) != null) {
            i.setItem(12, QueueSell(nextUpsell).get(1));
        } else {
            i.setItem(12, emptySell);
        }

        if (QueueSell(nextUpsell).size() > 2 && QueueSell(nextUpsell).get(2) != null) {
            i.setItem(13, QueueSell(nextUpsell).get(2));
        } else {
            i.setItem(13, emptySell);
        }
        if (QueueSell(nextUpsell).size() > 3 && QueueSell(nextUpsell).get(3) != null) {
            i.setItem(14, QueueSell(nextUpsell).get(3));
        } else {
            i.setItem(14, emptySell);
        }
        if (QueueSell(nextUpsell).size() > 4 && QueueSell(nextUpsell).get(4) != null) {
            i.setItem(15, QueueSell(nextUpsell).get(4));
        } else {
            i.setItem(15, emptySell);
        }
        if (QueueXP(nextUpxp).size() > 0 && QueueXP(nextUpxp).get(0) != null) {
            i.setItem(29, QueueXP(nextUpxp).get(0));
        } else {
            i.setItem(29, emptyXP);
        }
        if (QueueXP(nextUpxp).size() > 1 && QueueXP(nextUpxp).get(1) != null) {
            i.setItem(30, QueueXP(nextUpxp).get(1));
        } else {
            i.setItem(30, emptyXP);
        }
        if (QueueXP(nextUpxp).size() > 2 && QueueXP(nextUpxp).get(2) != null) {
            i.setItem(31, QueueXP(nextUpxp).get(2));
        } else {
            i.setItem(31, emptyXP);
        }
        if (QueueXP(nextUpxp).size() > 3 && QueueXP(nextUpxp).get(3) != null) {
            i.setItem(32, QueueXP(nextUpxp).get(3));
        } else {
            i.setItem(32, emptyXP);
        }
        if (QueueXP(nextUpxp).size() > 4 && QueueXP(nextUpxp).get(4) != null) {
            i.setItem(33, QueueXP(nextUpxp).get(4));
        } else {
            i.setItem(33, emptyXP);
        }

        ItemStack boost = new ItemStack(Material.CHEST);
        ItemMeta bm = boost.getItemMeta();
        bm.setDisplayName(c("&d&lOpen your Boosts"));
        boost.setItemMeta(bm);

        i.setItem(8, boost);


        return i;
    }


    public static int selltime = 0;
    public static String stimeLeft = c("&d" + timeFormat(selltime));
    public static int xptime = 0;
    public static String xtimeLeft = c("&d" + timeFormat(xptime));
    public static String names = "";
    public static String namex = "";

    Runnable selltimer = () -> {
        if (sactive == false) {
            selltime = 0;
            stimeLeft = c("&d" + timeFormat(0));
            sname = c("&a$&f1.0x");
            selltime = 0;
            stimeLeft = c("&d" + timeFormat(0));
            sell = 1;
            settings.getBoost().set("ActiveSell.Amp", 0);
            settings.getBoost().set("ActiveSell.Duration", 0);
            Bukkit.broadcastMessage(c("&f&lBoost &8| &aCurrency Boost has Ended!"));
            cancelSell();
            activeNextSellBoost();
        }
        if (selltime <= 0) {
            sactive = false;
            return;
        }
        selltime = selltime - 1;
        stimeLeft = c("&d" + timeFormat(selltime));

    };
    private int sellId;

    public void sCountdown() {
        sellId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), selltimer, 2, 20L);
    }
    public void cancelSell() {
        Bukkit.getScheduler().cancelTask(sellId);
    }

    Runnable xptimer = () -> {
        if (xactive == false) {
            xptime = 0;
            xtimeLeft = c("&d" + timeFormat(0));
            xname = c("&a✴&f1x");
            xptime = 0;
            xtimeLeft = c("&d" + timeFormat(0));
            xp = 1;
            settings.getBoost().set("ActiveXP.Amp", 0);
            settings.getBoost().set("ActiveXP.Duration", 0);
            Bukkit.broadcastMessage(c("&f&lBoost &8| &aXP Boost has Ended!"));
            cancelXP();
            activeNextXPBoost();
        }
        if (xptime <= 0) {
            xactive = false;
            return;
        }
        xptime = xptime - 1;
        xtimeLeft = c("&d" + timeFormat(xptime));

    };
    private int xpId;

    public void xCountdown() {
        xpId = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), xptimer, 2, 20L);
    }
    public void cancelXP() {
        Bukkit.getScheduler().cancelTask(xpId);
    }


    public static String sname = c("&a$&f1.0x");
    public static String xname = c("&a✴&f1x");

    public void orderQueueSell() {
        SortedMap<Double, String> queuesell = new TreeMap<>(Collections.reverseOrder());
        for (String s : nextUpsell) {
            queuesell.put(Double.valueOf(s.split(" ")[2]), s);
        }
        nextUpsell.clear();
        Set<Map.Entry<Double, String>> qsell = queuesell.entrySet();
        List<Map.Entry<Double, String>> newqsell = new ArrayList<>(qsell);
        for (Map.Entry<Double, String> doubleStringEntry : newqsell) {
            nextUpsell.add(doubleStringEntry.getValue());
        }
    }

    public void orderQueueXP() {
        SortedMap<Double, String> queuexp = new TreeMap<>(Collections.reverseOrder());
        for (String s : nextUpxp) {
            queuexp.put(Double.valueOf(s.split(" ")[2]), s);
        }
        nextUpxp.clear();
        Set<Map.Entry<Double, String>> qxp = queuexp.entrySet();
        List<Map.Entry<Double, String>> newqxp = new ArrayList<>(qxp);
        for (Map.Entry<Double, String> doubleStringEntry : newqxp) {
            nextUpxp.add(doubleStringEntry.getValue());
        }
    }

    public void activeNextSellBoost() {
        if(nextUpsell.size() >0) {
            String[] next = nextUpsell.get(0).split(" ");
            activeBoost("sell", Double.parseDouble(next[2]), Integer.parseInt(next[3]), next[1]);
            nextUpsell.remove(0);
        }
    }
    public void activeNextXPBoost() {
        if(nextUpxp.size() >0) {
            String[] next = nextUpxp.get(0).split(" ");
            activeBoost("xp", Double.parseDouble(next[2]), Integer.parseInt(next[3]), next[1]);
            nextUpxp.remove(0);
        }
    }

    public void activeBoost(String type, double amp, int dur, String name) {
        if (type.equalsIgnoreCase("sell")) {
            if (sactive == true) {
                if (amp > sell) {
                    Bukkit.getScheduler().cancelTask(sellId);
                    nextUpsell.add("sell " + names + " " + sell + " " + selltime);
                    orderQueueSell();
                    sactive = true;

                    sell = amp;

                    selltime = dur;

                    names = name;

                    sCountdown();
                    settings.getBoost().set("ActiveSell.Amp", amp);
                    settings.getBoost().set("ActiveSell.Duration", dur);

                    Bukkit.broadcastMessage(c("&f&lBoost &8| &b&l" + name + " &dhas activated a " + amp + "x Currency Boost!"));
                    Bukkit.broadcastMessage(c("&f&lBoost &8| &7Length: &b" + timeFormat(dur)));

                    TextChannel channel = jdaHandler.jda.getTextChannelById("1003031504278016051");

                    EmbedBuilder b = new EmbedBuilder();
                    b.setTitle("__" + name + " Activated a Currency Boost__");
                    b.addField("Multiplier: " + amp + "x", "Length: " + timeFormat(dur), false);
                    b.setColor(Color.BLUE);

                    assert channel != null;
                    channel.sendMessageEmbeds(b.build()).queue();

                    sname = c("&a$&a" + amp + "x");
                    return;
                }
                if (nextUpsell.size() > 0) {
                    String[] first = nextUpsell.get(0).split(" ");
                    if (amp >= Double.parseDouble(first[3])) {
                        ArrayList<String> hold = new ArrayList<>(nextUpsell);
                        nextUpsell.clear();
                        nextUpsell.add("activeboost sell " + name + " " + amp + " " + dur);
                        nextUpsell.addAll(hold);
                    } else {
                        nextUpsell.add("activeboost sell " + name + " " + amp + " " + dur);
                    }
                } else {
                    nextUpsell.add("sell " + name + " " + amp + " " + dur);
                }
                return;
            }


            sactive = true;

            sell = amp;

            selltime = dur;

            names = name;

            sCountdown();

            settings.getBoost().set("ActiveSell.Amp", amp);
            settings.getBoost().set("ActiveSell.Duration", dur);

            Bukkit.broadcastMessage(c("&f&lBoost &8| &b&l" + name + " &dhas activated a " + amp + "x Currency Boost!"));
            Bukkit.broadcastMessage(c("&f&lBoost &8| &7Length: &b" + timeFormat(dur)));

            TextChannel channel = jdaHandler.jda.getTextChannelById("1003031504278016051");

            EmbedBuilder b = new EmbedBuilder();
            b.setTitle("__" + name + " Activated a Currency Boost__");
            b.addField("Multiplier: " + amp + "x", "Length: " + timeFormat(dur), false);
            b.setColor(Color.BLUE);

            assert channel != null;
            channel.sendMessageEmbeds(b.build()).queue();

            sname = c("&a$&a" + amp + "x");
        }
        if (type.equalsIgnoreCase("xp")) {
            if (xactive == true) {
                if (amp > xp) {
                    Bukkit.getScheduler().cancelTask(xpId);
                    nextUpxp.add("XP" + " " + namex + " " + xp + " " + xptime);
                    orderQueueXP();
                    xactive = true;

                    xp = amp;

                    xptime = dur;

                    namex = name;

                    xCountdown();
                    settings.getBoost().set("ActiveXP.Amp", amp);
                    settings.getBoost().set("ActiveXP.Duration", dur);

                    Bukkit.broadcastMessage(c("&f&lBoost &8| &b&l" + name + " &dhas activated a " + amp + "x XP Boost!"));
                    Bukkit.broadcastMessage(c("&f&lBoost &8| &7Length: &b" + timeFormat(dur)));

                    TextChannel channel = jdaHandler.jda.getTextChannelById("1003031504278016051");

                    EmbedBuilder b = new EmbedBuilder();
                    b.setTitle("__" + name + " Activated a XP Boost__");
                    b.addField("Multiplier: " + amp + "x", "Length: " + timeFormat(dur), false);
                    b.setColor(Color.BLUE);

                    assert channel != null;
                    channel.sendMessageEmbeds(b.build()).queue();

                    xname = c("&a$&a" + amp + "x");
                    return;
                }
                if (nextUpxp.size() > 0) {
                    String[] first = nextUpxp.get(0).split(" ");
                    if (amp >= Integer.parseInt(first[3])) {
                        ArrayList<String> hold = new ArrayList<>(nextUpxp);
                        nextUpxp.clear();
                        nextUpxp.add("XP " + name + " " + amp + " " + dur);
                        nextUpxp.addAll(hold);
                    } else {
                        nextUpxp.add("XP " + name + " " + amp + " " + dur);
                    }
                } else {
                    nextUpxp.add("XP " + name + " " + amp + " " + dur);
                }
                return;
            }


            xactive = true;

            xp = amp;

            xptime = dur;
            xCountdown();

            settings.getBoost().set("ActiveXP.Amp", amp);
            settings.getBoost().set("ActiveXP.Duration", dur);


            Bukkit.broadcastMessage(c("&f&lBoost &8| &b&l" + name + " &dhas activated a " + amp + "x XP Boost!"));
            Bukkit.broadcastMessage(c("&f&lBoost &8| &7Length: &b" + timeFormat(dur)));

            TextChannel channel = jdaHandler.jda.getTextChannelById("1003031504278016051");
            EmbedBuilder b = new EmbedBuilder();
            b.setTitle("__" + name + " Activated an XP Boost__");
            b.addField("Multiplier: " + amp + "x", "Length: " + timeFormat(dur), false);
            b.setColor(Color.GREEN);

            assert channel != null;
            channel.sendMessageEmbeds(b.build()).queue();

            xname = c("&a✴&e" + amp + "x");
        }
    }

    public void giveBoost(Player p, String type, double amp, int dur) {
        if(type.equalsIgnoreCase("sell"))
            boostsinv.get(p).add(BoostSell("&b" + amp + "x Currency Boost", "&d" + timeFormat(dur)));
        if(type.equalsIgnoreCase("xp"))
            boostsinv.get(p).add(BoostXP("&a" + amp + "x XP Boost", "&d" + timeFormat(dur)));
    }
    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("giveboost")) {


            if (args.length == 4) {
                Player p = Bukkit.getPlayer(args[1]);

                if (args[0].equalsIgnoreCase("sell")) {
                    double amp = Double.parseDouble(args[2]);
                    int dur = Integer.parseInt(args[3]);

                    boostsinv.get(p).add(BoostSell("&b" + amp + "x Currency Boost", "&d" + timeFormat(dur)));

                }
                if (args[0].equalsIgnoreCase("XP")) {
                    int amp = Integer.parseInt(args[2]);
                    int dur = Integer.parseInt(args[3]);
                    boostsinv.get(p).add(BoostXP("&a" + amp + "x XP Boost", "&d" + timeFormat(dur)));

                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("boost")) {
            if (cs instanceof Player) {
                Player p = (Player) cs;
                p.openInventory(boostPublic());
            }
        }


        return false;
    }


    public static HashMap<Player, ArrayList<ItemStack>> boostsinv = new HashMap<>();


    public void saveBinv(Player p) {
        this.settings.getBoost().set(p.getUniqueId().toString() + ".Boosts", boostsinv.get(p));
    }

    public void openBoost(Player p) {
        Inventory i = Bukkit.createInventory(null, 45, c("&7" + p.getName() + "'s Boosts"));
        for (int x = 0; x < 45; x++) {
            try {
                ItemStack item = boostsinv.get(p).get(x);
                i.setItem(x, item);
            } catch (Exception ignored) {
            }
        }

        p.openInventory(i);
    }

    @SuppressWarnings("unchecked")
    public void loadBoost(Player p) {
        ArrayList<ItemStack> boost = new ArrayList<>();
        if (this.settings.getBoost().contains(p.getUniqueId().toString())) {
            boostsinv.put(p, (ArrayList<ItemStack>) this.settings.getBoost().get(p.getUniqueId().toString() + ".Boosts"));
        } else {
            boostsinv.put(p, boost);
        }

    }


    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        loadBoost(p);
    }


    @EventHandler
    public void leave(PlayerQuitEvent e) {

        saveBinv(e.getPlayer());
        settings.saveboosts();
        boostsinv.remove(e.getPlayer());
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
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }


    @EventHandler
    public void BoostPlace(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getName() == null)
            return;
        if (e.getCurrentItem() == null) {
            return;
        }

        if (e.getInventory().getName().equals(c("&3&lBoost Queue"))) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta() == null) return;
            if (e.getSlot() == 8) {
                if (e.getCurrentItem().getType().equals(Material.CHEST)) {
                    openBoost(p);
                }
            }
        }


        if (e.getInventory().getName().equals(c("&7" + p.getName() + "'s Boosts"))) {
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getItemMeta() == null) return;
            e.setCancelled(true);
            if (e.getCurrentItem().getType().equals(Material.POTION)) {

                String[] amps = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("x");

                double amp = Double.parseDouble(amps[0]);


                int dur = toSeconds(e.getCurrentItem().getItemMeta().getLore().get(0));

                activeBoost("sell", amp, dur, p.getName());


            } else if (e.getCurrentItem().getType().equals(Material.EXP_BOTTLE)) {
                String[] amps = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("x");


                int amp = Integer.parseInt(amps[0]);


                int dur = toSeconds(e.getCurrentItem().getItemMeta().getLore().get(0));
                activeBoost("xp", amp, dur, p.getName());

            }
            boostsinv.get(p).remove(e.getSlot());
            saveBinv(p);
            settings.saveboosts();
            openBoost(p);
        }

    }

    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        if (e.getInventory().getName().equals(c("&7" + p.getName() + "'s Boosts"))) {
            saveBinv(p);
            settings.saveboosts();
        }
    }


    @EventHandler
    public void setMaxStackSize(net.minecraft.server.v1_8_R3.Item getitem, int i) {
        try {
            Field field = net.minecraft.server.v1_8_R3.Item.class.getDeclaredField("maxStackSize");
            field.setAccessible(true);
            field.setInt(getitem, i);
        } catch (Exception ignored) {
        }
    }


    public ItemStack BoostSell(String name, String length) {
        ItemStack i = new ItemStack(Material.POTION, 1, (short) 8260);
        ItemMeta im = i.getItemMeta();
        im.addItemFlags(ItemFlag.values());
        im.setDisplayName(c(name));
        List<String> lores = new ArrayList<>();
        lores.add(c(length));
        im.setLore(lores);
        im.addEnchant(Enchantment.DURABILITY, 0, false);
        i.setItemMeta(im);
        i.removeEnchantment(Enchantment.DURABILITY);
        lores.clear();
		/*net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(i);
		net.minecraft.server.v1_8_R3.Item getitem = i2.getItem();
		setMaxStackSize(getitem, 1);*/
        return i;
    }

    public ItemStack BoostXP(String name, String length) {
        ItemStack i = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta im = i.getItemMeta();
        im.addItemFlags(ItemFlag.values());
        im.setDisplayName(c(name));
        List<String> lores = new ArrayList<>();
        lores.add(c(length));
        im.setLore(lores);
        im.addEnchant(Enchantment.DURABILITY, 0, false);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        lores.clear();
        net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(i);
        net.minecraft.server.v1_8_R3.Item getitem = i2.getItem();
        setMaxStackSize(getitem, 1);
        return i;
    }


}