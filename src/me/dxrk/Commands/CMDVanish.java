package me.dxrk.Commands;

import me.dxrk.Main.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.List;

public class CMDVanish implements Listener, CommandExecutor {
    Methods m = Methods.getInstance();

    public static List<Player> vanished = new ArrayList<>();

    public static boolean isVanished(Player p) {
        if (vanished.contains(p))
            return true;
        return false;
    }
    //Remake this class to use packets(probably)

    String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("vanish") || label.equalsIgnoreCase("v") || label.equalsIgnoreCase("ev") || label.equalsIgnoreCase("evanish")) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (vanished.contains(player)) {
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        people.showPlayer(player);
                    }
                    if (player.hasPermission("rank.helper")) {
                        vanished.remove(player);
                        player.sendMessage(m.c("&f&lVanish &8| &bYou are no longer vanished."));
                    } else {
                        player.sendMessage(m.c("&cNo Permission"));
                    }
                } else {
                    for (Player people : Bukkit.getOnlinePlayers()) {
                        if (people.hasPermission("rank.helper")) continue;
                        people.hidePlayer(player);
                    }
                    if (player.hasPermission("rank.helper")) {
                        vanished.add(player);
                        player.sendMessage(m.c("&f&lVanish &8| &bYou are vanished!"));
                    } else {
                        player.sendMessage(m.c("&cNo Permission"));
                    }
                }
            }
        }
        return false;
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (p.hasPermission("rank.helper")) {
            if (vanished.contains(p)) {
                e.setQuitMessage("");
                vanished.remove(p);
            } else {
                e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b-&7> &e&l" + p.getName()));
            }
        } else {
            e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b-&7> &b" + p.getName()));
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        for (Player value : vanished) {
            if (!player.hasPermission("rank.helper"))
                player.hidePlayer(value);
        }
        if (player.hasPermission("rank.helper")) {
            for (Player people : Bukkit.getOnlinePlayers()) {
                if (!people.hasPermission("rank.helper"))
                    people.hidePlayer(player);
            }
            vanished.add(player);
            player.sendMessage(m.c("&f&lVanish &8| &bYou are vanished!"));
            e.setJoinMessage("");
        } else {
            e.setJoinMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b+&7> &b" + player.getName()));
        }

    }


}
