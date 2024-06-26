package me.dxrk.Enchants;

import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineSystem;
import me.dxrk.Vote.CMDVoteShop;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkillsEventsListener implements Listener {

    static SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    public static String events = "";
    public static List<String> activeEvents = new ArrayList<>();

    public static double getEventXP() {
        double amt = 1;
        for (String s : activeEvents) {
            if ("Allure".equals(s) || "Strong Desire".equals(s)) {
                amt += 0.25;
            }
        }


        return amt;
    }

    public static double getEventFortune() {
        double amt = 1;
        for (String s : activeEvents) {
            if ("Typhoon".equals(s) || "Tsunami".equals(s)) {
                amt += 0.1;
            }
        }


        return amt;
    }

    public static double getEventKeyFortune() {
        double amt = 0;
        for (String s : activeEvents) {
            if ("Thunderstorm".equals(s) || "ThunderBolt".equals(s)) {
                amt += 25;
            }
        }
        return amt;
    }

    public static double getEventToken() {
        double amt = 1;
        for (String s : activeEvents) {
            if ("War Torn".equals(s)) {
                amt += 0.25;
            }
            if ("Bloodshed".equals(s)) {
                amt += 0.25;
            }
        }
        return amt;
    }

    public static double getEventMulti() {
        double amt = 0;
        for (String s : activeEvents) {
            if ("Scorched Earth".equals(s) || "Meteor Shower".equals(s)) {
                amt += 50;
            }
        }


        return amt;
    }


    public static double getSkillsBoostToken(Player p) {
        double amt = 1.0;
        double boost = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("SkillTokenBoost");
        amt += boost;
        return amt;
    }

    public static double getSkillsBoostFortune(Player p) {
        double amt = 1.0;
        double boost = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("SkillFortuneBoost");
        amt += boost;
        return amt;
    }

    public static double getSkillsBoostLuck(Player p) {
        double amt = 1.0;
        double boost = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("SkillLuckBoost");
        amt += boost;
        return amt;
    }

    public void couponBreak(Player p) {
        List<String> skills = PlayerDataHandler.getInstance().getPlayerData(p).getStringList("PickaxeSkillsUnlocked");
        Random r = new Random();
        int level = 0;
        for (String s : skills) {
            if (s.contains("Coupon")) {
                level = level + 20;
            }
        }
        if (level > 0) {
            double min = 0.01;
            double max = 0.10;
            double coupon = Math.round((min + (max - min) * r.nextDouble()) * 100) / 100.0;
            if (r.nextInt(15000 - (level * 30)) == 1) {
                CMDVoteShop.addCoupon(p, coupon);
                p.sendMessage(m.c("&f&lCoupon Finder &8| &b+$" + coupon));
            }
        }
    }


    public void updateEvent() {
        StringBuilder s = new StringBuilder();
        for (String activeEvent : activeEvents) {
            s.append(activeEvent).append(", ");
        }
        events = s.toString();
    }


    public void activateEvent(String event) {
        if (activeEvents.contains(event)) {
            return;
        }
        activeEvents.add(event);
        updateEvent();
    }

    public void deactiveEvent(String event) {
        activeEvents.remove(event);
        updateEvent();
    }

    public void eventBreak(Player p, String event) {
        Random r = new Random();

        List<String> skillsUnlocked = PlayerDataHandler.getInstance().getPlayerData(p).getStringList("PickaxeSkillsUnlocked");
        for (String s : skillsUnlocked) {
            if (s.contains(event)) {
                activateEvent(event);
            }
        }
    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Random r = new Random();
        if (!MineSystem.getInstance().getMineByPlayer(p).isLocationInMine(e.getBlock().getLocation())) {
            return;
        }
        couponBreak(p);
        //Zeus
        if (PlayerDataHandler.getInstance().getPlayerData(p).get("PickaxeSkill").equals("Zeus")) {
            int skill = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PickaxeSkillLevel");
            int i = r.nextInt(10000 / (skill));
            if (i == 1) {
                if (activeEvents.size() >= 2) {
                    return;
                }
                int ii = r.nextInt(2);
                if (ii == 0) {
                    if (activeEvents.contains("ThunderBolt")) return;
                    activateEvent("ThunderBolt");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &e&lThunderBolt &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("ThunderBolt");
                            Bukkit.broadcastMessage(m.c("&e&lThunderBolt &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
                if (ii == 1) {
                    if (activeEvents.contains("Thunderstorm")) return;
                    activateEvent("Thunderstorm");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &e&lThunderstorm &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Thunderstorm");
                            Bukkit.broadcastMessage(m.c("&e&lThunderStorm &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
            }
        }
        //Poseidon
        if (PlayerDataHandler.getInstance().getPlayerData(p).get("PickaxeSkill").equals("Poseidon")) {
            int skill = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PickaxeSkillLevel");
            int i = r.nextInt(10000 / (skill));
            if (i == 1) {
                if (activeEvents.size() >= 2) {
                    return;
                }
                int ii = r.nextInt(2);
                if (ii == 0) {
                    if (activeEvents.contains("Typhoon")) return;
                    activateEvent("Typhoon");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &b&lTyphoon &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Typhoon");
                            Bukkit.broadcastMessage(m.c("&b&lTyphoon &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
                if (ii == 1) {
                    if (activeEvents.contains("Tsunami")) return;
                    activateEvent("Tsunami");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &b&lTsunami &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Tsunami");
                            Bukkit.broadcastMessage(m.c("&b&lTsunami &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
            }
        }
        //Hades
        if (PlayerDataHandler.getInstance().getPlayerData(p).get("PickaxeSkill").equals("Hades")) {
            int skill = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PickaxeSkillLevel");
            int i = r.nextInt(10000 / (skill));
            if (i == 1) {
                if (activeEvents.size() >= 2) {
                    return;
                }
                int ii = r.nextInt(2);
                if (ii == 0) {
                    if (activeEvents.contains("Meteor Shower")) return;
                    activateEvent("Meteor Shower");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &4&lMeteor Shower &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Meteor Shower");
                            Bukkit.broadcastMessage(m.c("&4&lMeteor Shower &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
                if (ii == 1) {
                    if (activeEvents.contains("Scorched Earth")) return;
                    activateEvent("Scorched Earth");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &4&lScorched Earth &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Scorched Earth");
                            Bukkit.broadcastMessage(m.c("&4&lScorched Earth &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
            }
        }
        //Ares
        if (PlayerDataHandler.getInstance().getPlayerData(p).get("PickaxeSkill").equals("Ares")) {
            int skill = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PickaxeSkillLevel");
            int i = r.nextInt(10000 / (skill));
            if (i == 1) {
                if (activeEvents.size() >= 2) {
                    return;
                }
                int ii = r.nextInt(2);
                if (ii == 0) {
                    if (activeEvents.contains("War Torn")) return;
                    activateEvent("War Torn");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &c&lWar Torn &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("War Torn");
                            Bukkit.broadcastMessage(m.c("&c&lWar Torn &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
                if (ii == 1) {
                    if (activeEvents.contains("Bloodshed")) return;
                    activateEvent("Bloodshed");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &c&lBloodshed &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Bloodshed");
                            Bukkit.broadcastMessage(m.c("&c&lBloodshed &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
            }
        }
        //Aphrodite
        if (PlayerDataHandler.getInstance().getPlayerData(p).get("PickaxeSkill").equals("Aphrodite")) {
            int skill = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PickaxeSkillLevel");
            int i = r.nextInt(10000 / (skill));
            if (i == 1) {
                if (activeEvents.size() >= 2) {
                    return;
                }
                int ii = r.nextInt(2);
                if (ii == 0) {
                    if (activeEvents.contains("Allure")) return;
                    activateEvent("Allure");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3has activated the &d&lAllure &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Allure");
                            Bukkit.broadcastMessage(m.c("&d&lAllure &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
                if (ii == 1) {
                    if (activeEvents.contains("Strong Desire")) return;
                    activateEvent("Strong Desire");
                    Bukkit.broadcastMessage(m.c("&f&l" + p.getName() + " &3Has activated the &d&lStrong Desire &3Event"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Strong Desire");
                            Bukkit.broadcastMessage(m.c("&d&lStrong Desire &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20 * 900L);
                }
            }
        }

    }


}
