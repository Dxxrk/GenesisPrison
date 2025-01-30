package me.dxrk.Events;


import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineSystem;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.UUID;


public class MomentumHandler implements Listener {

    public static HashMap<UUID, ArrayList<Long>> momentum = new HashMap<>();
    public static HashMap<UUID, Integer> seconds = new HashMap<>();

    static Methods m = Methods.getInstance();
    static SettingsManager settings = SettingsManager.getInstance();

    public static void addSeconds(UUID id) {
        int s = seconds.get(id);
        seconds.put(id, s + 1);
    }

    public static int getSeconds(UUID id) {
        if (seconds.containsKey(id)) return seconds.get(id);
        return 0;
    }

    public static void runMomentum() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            if (momentum.isEmpty()) return;
            for (UUID player : momentum.keySet()) {
                ArrayList<Long> timeStamps = momentum.get(player);
                if (timeStamps.size() == 0)
                    continue;
                long currentTimeStamp = System.currentTimeMillis();
                long lastTimeStamp = Collections.<Long>max(timeStamps);
                Player p = Bukkit.getPlayer(player);
                if (currentTimeStamp - lastTimeStamp >= 60000L) {
                    removeMomentum(player);
                    if (p != null)
                        //ActionBarAPI.sendActionBar(p, m.c("&d&lMomentum: ") + Leaderboards.formatTime(getSeconds(player)));
                        return;
                } else {
                    addSeconds(player);                                                                                             ////replace with packets?
                    if (p != null) {
                        //ActionBarAPI.sendActionBar(p, m.c("&d&lMomentum: ") + Leaderboards.formatTime(getSeconds(player)));
                        sendTitleMomentum(p);
                    }
                }
            }
        }, 20L, 20L);
    }

    private static void removeMomentum(UUID id) {
        int max = seconds.get(id);
        int prev = PlayerDataHandler.getInstance().getPlayerData(id).getInt("MaxMomentum");
        if (max > prev) {
            PlayerDataHandler.getInstance().getPlayerData(id).set("MaxMomentum", max);
        }
        seconds.remove(id);
        momentum.remove(id);
    }

    //Create increments of buffs and || save highest momentum to file, next season.
    public static double getBonus(UUID id) {
        int seconds = getSeconds(id);
        if (seconds >= 300 && seconds < 600)
            return 1.05;
        if (seconds >= 600 && seconds < 900)
            return 1.1;
        if (seconds >= 900 && seconds < 1200)
            return 1.15;
        if (seconds >= 1200 && seconds < 1500)
            return 1.2;
        if (seconds >= 1500 && seconds < 1800)
            return 1.25;
        if (seconds >= 1800 && seconds < 2100)
            return 1.3;
        if (seconds >= 2100 && seconds < 2400)
            return 1.35;
        if (seconds >= 2400 && seconds < 2700)
            return 1.4;
        if (seconds >= 2700 && seconds < 3000)
            return 1.45;
        if (seconds >= 3000)
            return 1.5;
        return 1.0;
    }

    public static void sendTitleMomentum(Player p) {
        int seconds = getSeconds(p.getUniqueId());
        if (seconds == 300) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("5% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 600) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("10% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 900) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("15% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 1200) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("20% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 1500) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("25% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 1800) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("30% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 2100) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("35% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 2400) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("40% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 2700) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("45% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }
        if (seconds == 3000) {
            Title t = Title.title(Component.text("Momentum").decorate(TextDecoration.BOLD).color(NamedTextColor.LIGHT_PURPLE), Component.text("50% Income").decorate(TextDecoration.BOLD).color(NamedTextColor.AQUA),
                    Title.Times.times(Duration.ofMillis(100), Duration.ofSeconds(2), Duration.ofMillis(100)));
        }

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        UUID id = p.getUniqueId();
        if (!MineSystem.getInstance().getMineByPlayer(p).isLocationInMine(e.getBlock().getLocation())) {
            return;
        }
        ArrayList<Long> timeStamps = (momentum.get(id) == null) ? new ArrayList<>() : momentum.get(id);
        timeStamps.add(System.currentTimeMillis());
        momentum.put(id, timeStamps);
        if (!seconds.containsKey(id))
            seconds.put(id, 0);
    }


}
