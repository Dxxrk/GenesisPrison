package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class CMDPromote implements Listener, CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("promote")) {
            Player p = (Player) sender;
            if(!p.isOp()) return false;
            Player player = Bukkit.getPlayer(args[0]);
            if(args[1].equalsIgnoreCase("mod")) {

            }
        }
        return false;
    }



}
