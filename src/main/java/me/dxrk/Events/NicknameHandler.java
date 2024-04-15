package me.dxrk.Events;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NicknameHandler implements Listener, CommandExecutor {

    private HashMap<Player, String> currentLetter = new HashMap<>();

    public static List<Player> waitingForNick = new ArrayList<>();

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, String[] args) {
        Player p = (Player) sender;

        if (cmd.getName().equalsIgnoreCase("nick") || cmd.getName().equalsIgnoreCase("nickname")) {
            TextComponent msg = Component.text()
                    .append(Component.text("Enter your Nickname:").decoration(TextDecoration.BOLD, true).color(NamedTextColor.GREEN))
                    .build();
            p.sendMessage(msg);
            waitingForNick.add(p);
        }

        return false;
    }


}
