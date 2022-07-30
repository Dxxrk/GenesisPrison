package me.dxrk.Discord;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class CMDDiscord implements Listener, CommandExecutor {

    SettingsManager settings = SettingsManager.getInstance();

    Methods m = Methods.getInstance();


    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return false;
    }
}
