package me.dxrk.Events;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class KitAndWarp implements Listener {

    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    public static List<String> getDonorKits() {
        List<String> kits = new ArrayList<>();
        kits.add("Demi-GodKeys");
        kits.add("TitanKeys");
        kits.add("God");
        kits.add("GodKeys");
        kits.add("Olympian");
        kits.add("OlympianKeys");
        kits.add("Genesis");
        kits.add("GenesisKeys");
        return kits;
    }

    public static List<String> getRankedMines() {
        List<String> kits = new ArrayList<>();
        kits.add("Use /mine");
        return kits;
    }

    public static List<String> getOtherMines() {
        List<String> kits = new ArrayList<>();
        kits.add("Crates");
        return kits;
    }

    @EventHandler
    public void onCmd(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String first = null;
        String second = null;
        String third = null;
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = e.getMessage().split(" ")).length, b = 0; b < i; ) {
            String s = arrayOfString[b];
            if (first == null) {
                try {
                    first = s;
                } catch (Exception ignored) {
                }
            } else if (second == null) {
                try {
                    second = s;
                } catch (Exception ignored) {
                }
            } else if (third == null) {
                try {
                    third = s;
                } catch (Exception ignored) {
                }
            }
            b++;
        }
        assert first != null;
        if ((first.equalsIgnoreCase("/kit") || first.equalsIgnoreCase("/kits")) &&
                second == null) {
            boolean z = PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("BuildMode");
            if(z){
                p.sendMessage(m.c("&cYou can't access this while in buildmode."));
                return;
            }
            e.setCancelled(true);
            p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Kits:");
            FancyMessage donorkits = new FancyMessage("");
            for (String s : getDonorKits()) {
                if (p.hasPermission("Essentials.kits." + s)) {
                    donorkits.then(s).tooltip(ChatColor.GRAY + "Click for kit!").command("/kit " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
                    continue;
                }
                donorkits.then(s).tooltip(ChatColor.RED + "You do not have this kit!").command("/kit " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
            }
            donorkits.send(p);
        } else if ((first.equalsIgnoreCase("/kit") || first.equalsIgnoreCase("/kits")) && second != null) {
            e.setCancelled(true);
            boolean z = PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("BuildMode");
            if(z){
                p.sendMessage(m.c("&cYou can't access this while in buildmode."));
                return;
            }
            if (second.equalsIgnoreCase("demi-godkeys")) {
                if (p.hasPermission("rank.demi-god")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitDemiGodKeys") == 0 || p.hasPermission("rank.admin")) {
                        LocksmithHandler.getInstance().addKey(p, "token", 5);
                        LocksmithHandler.getInstance().addKey(p, "omega", 5);
                        LocksmithHandler.getInstance().addKey(p, "beta", 5);
                        LocksmithHandler.getInstance().addKey(p, "alpha", 5);
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitDemiGodKeys", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bDemi-GodKeys Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitDemiGodKeys"))));
                    }
                }
            }
            if (second.equalsIgnoreCase("titankeys")) {
                if (p.hasPermission("rank.titan")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitTitanKeys") == 0 || p.hasPermission("rank.admin")) {
                        LocksmithHandler.getInstance().addKey(p, "token", 7);
                        LocksmithHandler.getInstance().addKey(p, "omega", 7);
                        LocksmithHandler.getInstance().addKey(p, "beta", 7);
                        LocksmithHandler.getInstance().addKey(p, "alpha", 7);
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitTitanKeys", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bTitanKeys Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitTitanKeys"))));
                    }
                }
            }
            if (second.equalsIgnoreCase("godkeys")) {
                if (p.hasPermission("rank.god")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGodKeys") == 0 || p.hasPermission("rank.admin")) {
                        LocksmithHandler.getInstance().addKey(p, "token", 9);
                        LocksmithHandler.getInstance().addKey(p, "omega", 9);
                        LocksmithHandler.getInstance().addKey(p, "beta", 9);
                        LocksmithHandler.getInstance().addKey(p, "alpha", 9);
                        LocksmithHandler.getInstance().addKey(p, "community", 4);
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitGodKeys", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bGodKeys Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGodKeys"))));
                    }
                }
            }
            if (second.equalsIgnoreCase("olympiankeys")) {
                if (p.hasPermission("rank.olympian")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitOlympianKeys") == 0 || p.hasPermission("rank.admin")) {
                        LocksmithHandler.getInstance().addKey(p, "token", 11);
                        LocksmithHandler.getInstance().addKey(p, "omega", 11);
                        LocksmithHandler.getInstance().addKey(p, "beta", 11);
                        LocksmithHandler.getInstance().addKey(p, "alpha", 11);
                        LocksmithHandler.getInstance().addKey(p, "community", 5);
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitOlympianKeys", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bOlympianKeys Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitOlympianKeys"))));
                    }
                }
            }
            if (second.equalsIgnoreCase("genesiskeys")) {
                if (p.hasPermission("rank.genesis")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGenesisKeys") == 0 || p.hasPermission("rank.admin")) {
                        LocksmithHandler.getInstance().addKey(p, "token", 13);
                        LocksmithHandler.getInstance().addKey(p, "omega", 13);
                        LocksmithHandler.getInstance().addKey(p, "beta", 13);
                        LocksmithHandler.getInstance().addKey(p, "alpha", 13);
                        LocksmithHandler.getInstance().addKey(p, "community", 6);
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitGenesisKeys", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bGenesisKeys Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGenesisKeys"))));
                    }
                }
            }
            if (second.equalsIgnoreCase("god")) {
                if (p.hasPermission("rank.god")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGod") == 0 || p.hasPermission("rank.admin")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate " + p.getName() + " contraband");
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitGod", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bGod Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGod"))));
                    }
                }
            }
            if (second.equalsIgnoreCase("olympian")) {
                if (p.hasPermission("rank.olympian")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitOlympian") == 0 || p.hasPermission("rank.admin")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate " + p.getName() + " contraband");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate " + p.getName() + " contraband");
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitOlympian", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bOlympian Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitOlympian"))));
                    }
                }
            }
            if (second.equalsIgnoreCase("genesis")) {
                if (p.hasPermission("rank.genesis")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGenesis") == 0 || p.hasPermission("rank.admin")) {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate " + p.getName() + " contraband");
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate " + p.getName() + " genesis");
                        PlayerDataHandler.getInstance().getPlayerData(p).set("KitGenesis", 604800);
                        p.sendMessage(m.c("&f&lKits &8| &bGenesis Claimed!"));
                    } else {
                        p.sendMessage(m.c("&f&lKits &8| &bYou can use that kit again in " + Leaderboards.formatTime(PlayerDataHandler.getInstance().getPlayerData(p).getInt("KitGenesis"))));
                    }
                }
            }
        }
        if ((first.equalsIgnoreCase("/warp") || first.equalsIgnoreCase("/warps")) &&
                second == null) {
            e.setCancelled(true);

            FancyMessage minewarps = new FancyMessage("");
            p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Mine Warps:");

            for (String s : getRankedMines()) {
                minewarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/mine tp").color(ChatColor.DARK_AQUA).then("").color(ChatColor.GRAY);
            }
            minewarps.send(p);
            p.sendMessage(ChatColor.AQUA + "" + ChatColor.BOLD + "Other Warps:");
            FancyMessage otherwarps = new FancyMessage("");
            for (String s : getOtherMines()) {
                if (p.hasPermission("Essentials.warps." + s)) {
                    otherwarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/warp " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
                    continue;
                }
                otherwarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/warp " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
            }
            otherwarps.send(p);
        }
    }
}
