package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.*;

public class BoostsHandler implements Listener, CommandExecutor {
    Methods m = Methods.getInstance();

    public static BoostsHandler instance = new BoostsHandler();

    public static BoostsHandler getInstance() {
        return instance;
    }

    public static String timeFormat(int i) {
        Date d = new Date(i * 1000L);
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss"); // HH for 0-23
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(d);
    }

    private int toSeconds(String s) {
        String[] format = ChatColor.stripColor(s).split(":");
        int hour = Integer.parseInt(format[0]);
        int mins = Integer.parseInt(format[1]);
        int seconds = Integer.parseInt(format[2]);
        return (hour * 3600) + (mins * 60) + seconds;
    }

    public static boolean hasActiveBoost(Player p) {
        return token.containsKey(p.getUniqueId()) || sell.containsKey(p.getUniqueId()) || enchant.containsKey(p.getUniqueId()) || gems.containsKey(p.getUniqueId())
                || xp.containsKey(p.getUniqueId());
    }

    private void removeBoost(Player p) {
        token.remove(p.getUniqueId());
        sell.remove(p.getUniqueId());
        enchant.remove(p.getUniqueId());
        gems.remove(p.getUniqueId());
        xp.remove(p.getUniqueId());
    }


    public void startBoostCount(Player p, String type, int duration) {
        new BukkitRunnable() {
            int count = duration;

            @Override
            public void run() {
                if (count <= 0) {
                    removeBoost(p);
                    //time.put(p.getUniqueId(), 0);
                    time.remove(p.getUniqueId());
                    ScoreboardHandler.updateSB(p);
                    if (p.isOnline()) {
                        p.sendMessage(m.c("&f&lBoost &8| &bYour " + type.substring(0, 1).toUpperCase() + type.substring(1) + " Boost has ended."));
                    }
                    cancel();
                    return;
                }
                count--;
                time.put(p.getUniqueId(), count);
            }
        }.runTaskTimer(Main.plugin, 0, 20L);
    }

    public static HashMap<UUID, Double> token = new HashMap<>();
    public static HashMap<UUID, Double> sell = new HashMap<>();
    public static HashMap<UUID, Double> enchant = new HashMap<>();
    public static HashMap<UUID, Double> gems = new HashMap<>();
    public static HashMap<UUID, Double> xp = new HashMap<>();
    public static HashMap<UUID, Integer> time = new HashMap<>();


    public void activeBoost(Player p, String type, double power, int duration) {
        switch (type) {
            case "token":
                token.put(p.getUniqueId(), power);
                time.put(p.getUniqueId(), duration);
                startBoostCount(p, type, duration);
                break;
            case "sell":
                sell.put(p.getUniqueId(), power);
                time.put(p.getUniqueId(), duration);
                startBoostCount(p, type, duration);
                break;
            case "enchant":
                enchant.put(p.getUniqueId(), power);
                time.put(p.getUniqueId(), duration);
                startBoostCount(p, type, duration);
                break;
            case "gems":
                gems.put(p.getUniqueId(), power);
                time.put(p.getUniqueId(), duration);
                startBoostCount(p, type, duration);
                break;
            case "xp":
                xp.put(p.getUniqueId(), power);
                time.put(p.getUniqueId(), duration);
                startBoostCount(p, type, duration);
                break;
        }
        p.sendMessage(m.c("&f&lBoost &8| &bActivated a &a" + power + " " + type.substring(0, 1).toUpperCase() + type.substring(1) + " Boost &bfor &d" + timeFormat(duration)));
    }

    public void giveBoost(Player p, String type, double power, int duration) {
        switch (type) {
            case "token":
                p.getInventory().addItem(Boost((short) 8227, m.c("&e&lToken Boost"), power, duration));
                break;
            case "sell":
                p.getInventory().addItem(Boost((short) 8226, m.c("&2&lSell Boost"), power, duration));
                break;
            case "enchant":
                p.getInventory().addItem(Boost((short) 8229, m.c("&d&lEnchant Boost"), power, duration));
                break;
            case "gems":
                p.getInventory().addItem(Boost((short) 8225, m.c("&a&lGem Boost"), power, duration));
                break;
            case "xp":
                p.getInventory().addItem(Boost((short) 8228, m.c("&c&lXP Boost"), power, duration));
                break;
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("giveboost")) {
            if (!sender.isOp()) return false;
            if (args.length == 4) {
                Player p = Bukkit.getPlayer(args[0]);
                String type = args[1];
                double power = Double.parseDouble(args[2]);
                int duration = Integer.parseInt(args[3]);
                giveBoost(p, type, power, duration);
            }
        }
        if (cmd.getName().equalsIgnoreCase("boost")) {
            Player p = (Player) sender;
            if (hasActiveBoost(p)) {
                String boost = m.c("&7No Boost Active");
                if (BoostsHandler.token.containsKey(p.getUniqueId())) {
                    boost = m.c("&eToken Boost: &7" + BoostsHandler.token.get(p.getUniqueId()));
                }
                if (BoostsHandler.sell.containsKey(p.getUniqueId())) {
                    boost = m.c("&2Sell Boost: &7" + BoostsHandler.sell.get(p.getUniqueId()));
                }
                if (BoostsHandler.enchant.containsKey(p.getUniqueId())) {
                    boost = m.c("&dEnchant Boost: &7" + BoostsHandler.enchant.get(p.getUniqueId()));
                }
                if (BoostsHandler.gems.containsKey(p.getUniqueId())) {
                    boost = m.c("&aGem Boost: &7" + BoostsHandler.gems.get(p.getUniqueId()));
                }
                if (BoostsHandler.xp.containsKey(p.getUniqueId())) {
                    boost = m.c("&cXP Boost: &7" + BoostsHandler.xp.get(p.getUniqueId()));
                }
                p.sendMessage(m.c("&f&lBoost &8| " + boost));
                p.sendMessage(m.c("&f&lBoost &8| &d" + timeFormat(time.get(p.getUniqueId()))));
            } else {
                p.sendMessage(m.c("&f&lBoost &8| &bNo Active Boost"));
            }
        }

        return false;
    }

    @EventHandler
    public void onInt(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) return;

        if (p.getItemInHand().getType().equals(Material.BONE)) {
            String type = ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName()).split(" ")[0];
            double power = Double.parseDouble(ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(0)).split(" ")[1]);
            int seconds = toSeconds(ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(1)).split(" ")[1]);
            if (!hasActiveBoost(p)) {
                activeBoost(p, type.toLowerCase(), power, seconds);
                int amount = p.getItemInHand().getAmount();
                if (amount > 1) {
                    p.getItemInHand().setAmount(amount - 1);
                } else {
                    p.setItemInHand(null);
                }
                p.updateInventory();
            } else {
                p.sendMessage(m.c("&f&lBoost &8| &bYou already have a boost active."));
            }
        }
    }

    @EventHandler
    public void onDrink(PlayerItemConsumeEvent e){
        if(e.getItem().getType().equals(Material.POTION)) e.setCancelled(true);
    }


    public ItemStack Boost(short data, String type, double power, int duration) {
        ItemStack boost = new ItemStack(Material.POTION, 1, data);
        ItemMeta bm = boost.getItemMeta();
        bm.setDisplayName(type);
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&bPower: &a" + power));
        lore.add(m.c("&bLength: &a" + timeFormat(duration)));
        bm.setLore(lore);
        bm.addEnchant(Enchantment.DURABILITY, 0, false);
        bm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        bm.addItemFlags(ItemFlag.values());
        boost.setItemMeta(bm);
        boost.removeEnchantment(Enchantment.DURABILITY);
        return boost;
    }


}
