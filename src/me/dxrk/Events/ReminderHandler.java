package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDate;

public class ReminderHandler implements Listener {

    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    private String getTodayDate() {
        LocalDate time = java.time.LocalDate.now();

        return time.toString();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!settings.getDaily().get(p.getUniqueId().toString() + ".FreeReward").equals(getTodayDate())) {
                    if (settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Daily-Reminder"))
                        p.sendMessage(m.c("&f&lDaily &8| &bYou Haven't claimed your daily rewards! &7/Daily."));
                }
                if (!p.isOnline()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 20 * 300L);

        new BukkitRunnable() {
            @Override
            public void run() {
                if (!settings.getVote().get(p.getUniqueId().toString() + ".HasVoted").equals(getTodayDate())) {
                    if (settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Vote-Reminder"))
                        p.sendMessage(m.c("&f&lVote &8| &bYou Haven't voted today! &7/Vote."));
                }
                if (!p.isOnline()) {
                    this.cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 20 * 300L);

    }


}
