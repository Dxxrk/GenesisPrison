package me.dxrk.Main;

import java.security.SecureRandom;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Functions implements Listener {

    static Methods m = Methods.getInstance();

    public static List<Player> multiply = new ArrayList<>();


    public static double XPEnchant(Player p) {
        double xp = 1;
        if (p.getEquipment().getItemInMainHand() == null) return 1;
        if (p.getEquipment().getItemInMainHand().getType().equals(Material.AIR)) return 1;
        if (!p.getEquipment().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE) && !p.getEquipment().getItemInMainHand().getType().equals(Material.IRON_PICKAXE) && !p.getEquipment().getItemInMainHand().getType().equals(Material.GOLDEN_PICKAXE)
                && !p.getEquipment().getItemInMainHand().getType().equals(Material.STONE_PICKAXE) && !p.getEquipment().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE))
            return 1;

        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (ChatColor.stripColor(s).toLowerCase().contains("xp finder")) {

                String[] n = ChatColor.stripColor(s).split(" ");
                double x = Double.parseDouble(n[2]) / 1111.1111;

                xp += x;
            }
        }

        return xp;
    }


    public static double sellBoost(Player p) {
        double sell = 1;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return 1;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (ChatColor.stripColor(s).toLowerCase().contains("sell")) {

                String[] n = ChatColor.stripColor(s).split("%");
                String[] num = n[0].split(" ");
                double sel = Double.parseDouble(num[4]) / 100;

                sell += sel;
            }
        }

        return sell;
    }

    public static double xpBoost(Player p) {
        double xp = 1;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return 1;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (ChatColor.stripColor(s).toLowerCase().contains("xp trinket")) {

                String[] n = ChatColor.stripColor(s).split("%");
                String[] num = n[0].split(" ");
                double x = Double.parseDouble(num[4]) / 100;

                xp += x;
            }
        }

        return xp;
    }

    public static double tokenBoost(Player p) {
        double xp = 1;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return 1;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (ChatColor.stripColor(s).toLowerCase().contains("token trinket")) {

                String[] n = ChatColor.stripColor(s).split("%");
                String[] num = n[0].split(" ");
                double x = Double.parseDouble(num[4]) / 100;

                xp += x;
            }
        }

        return xp;
    }


    public static double luckBoost(Player p) {
        double luck = 1;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return 1;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (ChatColor.stripColor(s).toLowerCase().contains("lucky") && ChatColor.stripColor(s).toLowerCase().contains("%")) {

                String[] n = ChatColor.stripColor(s).split("%");
                String[] num = n[0].split(" ");
                double luc = Double.parseDouble(num[4]) / 100;

                luck += luc;
            }
        }

        return luck;
    }


    public static double greed(Player p) {
        double level = 0;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return 1;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {

            if (ChatColor.stripColor(s).contains("Greed")) {
                level = m.getBlocks(s);
            }
        }
        double greed = level * 2;
        if (greed == 0) {
            greed = 1;
        }


        return greed;
    }

    public static double Foruity(Player p) {

        int level;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return 1;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {

            if (ChatColor.stripColor(s).contains("Fortuity")) {
                level = m.getBlocks(s);
                return 1 + (level * 0.0015);
            }
        }
        return 1;
    }


    public static double Karma(Player p) {
        int level;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return 1;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {
            if (ChatColor.stripColor(s).contains("Karma")) {
                level = m.getBlocks(s);
                return 1 + (level * 0.00007);
            }
        }
        return 1;
    }

    private static Random r = new SecureRandom();

    public static void Multiply(Player p) {


        double lucky = Functions.Karma(p);
        double luck = Functions.luckBoost(p);

        int level = 0;
        int chance;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return;
        for (String s : p.getEquipment().getItemInMainHand().getItemMeta().getLore()) {

            if (ChatColor.stripColor(s).contains("Multiply")) {
                level = m.getBlocks(s);
            }
        }

        if (level == 0) return;
        if (level == 1) {
            chance = (int) (5500 / lucky / luck);
        } else {
            chance = (int) ((5500 - (level)) / lucky / luck);
        }
        int i = r.nextInt(chance);

        if (i == 1 && !multiply.contains(p)) {
            p.sendMessage(m.c("&f&lMultiply &8| &bActivated!"));
            multiply.add(p);

            new BukkitRunnable() {
                @Override
                public void run() {
                    p.sendMessage(m.c("&f&lMultiply &8| &bDeactivated!"));
                    multiply.remove(p);
                }
            }.runTaskLater(Main.plugin, 20 * 12L);

        }
    }


    public static String pres(Player p) {
        String prestige = "";

        for (int i = 1; i < 101; i++) {
            if (p.hasPermission("prestige." + i)) {
                prestige = m.c("&fPrestige &7" + i);
            }
        }


        return prestige;
    }


}
