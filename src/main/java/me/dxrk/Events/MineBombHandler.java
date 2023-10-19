package me.dxrk.Events;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class MineBombHandler implements Listener, CommandExecutor {


    @EventHandler
    public void onInt(PlayerInteractEvent e) {

    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("giveminebomb")) {
            if (sender.isOp()) {
                Player p = Bukkit.getPlayer(args[0]);
                String rarity = args[1];
                if ("common".equalsIgnoreCase(rarity)) {

                } else if ("rare".equalsIgnoreCase(rarity)) {

                } else if ("epic".equalsIgnoreCase(rarity)) {

                }
            }
        }
        return false;
    }
}
