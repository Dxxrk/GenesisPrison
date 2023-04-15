package me.dxrk.Events;

import com.connorlinfoot.actionbarapi.ActionBarAPI;
import com.connorlinfoot.titleapi.TitleAPI;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.dxrk.Enchants.EnchantMethods;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.inventivetalent.bossbar.BossBar;
import org.inventivetalent.bossbar.BossBarAPI;
import org.inventivetalent.bossbar.BossBarTimer;

import java.text.SimpleDateFormat;
import java.util.*;


public class MomentumHandler implements Listener {

    public static Map<UUID, Integer> momentum = new HashMap<>();
    public static List<UUID> mining = new ArrayList<>();
    public static Map<UUID, Date> startedMining = new HashMap<>();

    static Methods m = Methods.getInstance();
    public static Date getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        return date;
    }




    /*public static void setBossBar(Player p, int seconds) {
        float max = 60*15f;
        float progress = seconds/max;
        BossBar bossBar = BossBarAPI.addBar(p, // The receiver of the BossBar
                new TextComponent(m.c("&d&lMomentum: ") + Leaderboards.formatTime(seconds)), // Displayed message
                BossBarAPI.Color.PINK, // Color of the bar
                BossBarAPI.Style.PROGRESS, // Bar style
                progress); // Progress (0.0 - 1.0)
    }*/
    public static void setActionBar(Player p, int seconds) {
        ActionBarAPI.sendActionBar(p, m.c("&d&lMomentum: ") + Leaderboards.formatTime(seconds));
    }

    public static void addMomentum(Player p) {
        int seconds = momentum.get(p.getUniqueId());
        momentum.put(p.getUniqueId(), seconds+1);
    }
    public static void removeMomentum(Player p) {
        int seconds = momentum.get(p.getUniqueId());
        if(seconds <= 0) {
            momentum.put(p.getUniqueId(), 0);
            return;
        }
        if(seconds >=5) {
            momentum.put(p.getUniqueId(), seconds - 5);
        } else {
            momentum.put(p.getUniqueId(), seconds - 1);
        }
    }

    //Create increments of buffs and || save highest momentum to file, next season.
    public static double getBonus(int seconds) {
        //5 Minutes
        if(seconds >=300 && seconds <600) {
            return 1.10;
        }
        //10 Minutes
        if(seconds >=600 && seconds <900) {
            return 1.20;
        }
        //15 Minutes
        if(seconds >=900) {
            return 1.35;
        }
        return 1;
    }
    public static void sendTitleMomentum(Player p) {
        int seconds = momentum.get(p.getUniqueId());
        if(seconds == 300) {
            TitleAPI.sendTitle(p, 2, 40, 2, m.c("&d&lMomentum"), m.c("&b&l10% Income"));
        }
        if(seconds == 600) {
            TitleAPI.sendTitle(p, 2, 40, 2, m.c("&d&lMomentum"), m.c("&b&l20% Income"));
        }
        if(seconds == 900) {
            TitleAPI.sendTitle(p, 2, 40, 2, m.c("&d&lMomentum"), m.c("&b&l35% Income"));
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(e.getBlock().getWorld().getName().equals(p.getName()+"sWorld") && EnchantMethods.set(e.getBlock()).allows(DefaultFlag.LIGHTER)) {
            mining.add(p.getUniqueId());
            startedMining.put(p.getUniqueId(), getDate());
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if(momentum.containsKey(p.getUniqueId())) {
            setActionBar(p, momentum.get(p.getUniqueId()));
        } else {
            momentum.put(p.getUniqueId(), 0);
            setActionBar(p, 0);
        }
    }
    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        UUID Id = p.getUniqueId();
        momentum.remove(Id);
        startedMining.remove(Id);
        mining.remove(Id);
    }


}
