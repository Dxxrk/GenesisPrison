package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class TagsHandler implements Listener, CommandExecutor {

    private SettingsManager settings = SettingsManager.getInstance();
    private Methods m = Methods.getInstance();

    private HashMap<Player, Integer> repeat = new HashMap<>();

    public void giveRandomTag(Player p) {

        ArrayList<String> tags = new ArrayList<>();
        tags.addAll(settings.getTags().getKeys(false));
        Random r = new Random();
        int ri = r.nextInt(tags.size());
        if (p.hasPermission("tags." + tags.get(ri))) {
            repeat.put(p, repeat.get(p) + 1);
            if (repeat.get(p) > 10) {
                repeat.remove(p);
                return;
            }
            giveRandomTag(p);
            return;
        }
        Main.perms.playerAdd(p, "tags." + tags.get(ri));
        p.sendMessage(m.c("&f&lTags &8| &bUnlocked &7" + tags.get(ri) + " &bTag!"));
    }


    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("randomtag")) {
            if (cs.isOp()) {
                if (args.length == 1) {
                    Player p = Bukkit.getServer().getPlayer(args[0]);
                    repeat.put(p, 0);
                    giveRandomTag(p);
                }
            }
        }


        return false;
    }


}
