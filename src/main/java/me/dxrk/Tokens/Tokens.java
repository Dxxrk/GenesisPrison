package me.dxrk.Tokens;

import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Tokens {
    static Tokens instance = new Tokens();

    public static Inventory etShop = null;

    public static Tokens getInstance() {
        return instance;
    }

    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    String prefix = ChatColor.translateAlternateColorCodes('&', "&f&lTokens &8|&r ");

    public double getBalance(Player p) {
        return Main.econ.getBalance(p);
    }

    public boolean hasTokens(Player p, double tokens) {
        return getTokens(p) >= tokens;
    }

    public double getTokens(Player p) {
        if (!PlayerDataHandler.getInstance().getPlayerData(p).contains("Tokens"))
            return 0;
        return PlayerDataHandler.getInstance().getPlayerData(p).getDouble("Tokens");
    }

    public void setTokens(Player p, double tokens) {
        PlayerDataHandler.getInstance().getPlayerData(p).set("Tokens", tokens);
        p.sendMessage(this.prefix + "Tokens set to " + tokens);
        p.getScoreboard().getTeam("tokens").setSuffix(m.c("&e" + Main.formatAmt(getTokens(p))));
    }

    public void addTokens(Player p, double tokens) {
        PlayerDataHandler.getInstance().getPlayerData(p).set("Tokens", getTokens(p) + tokens);
        p.getScoreboard().getTeam("tokens").setSuffix(m.c("&e" + Main.formatAmt(getTokens(p))));
    }

    public void takeTokens(Player p, double tokens) {
        PlayerDataHandler.getInstance().getPlayerData(p).set("Tokens", getTokens(p) - tokens);
        p.getScoreboard().getTeam("tokens").setSuffix(m.c("&e" + Main.formatAmt(getTokens(p))));
    }

    public void sendTokens(Player sender, Player reciever, double tokens) {
        if (getTokens(sender) < tokens) {
            sender.sendMessage(this.prefix + "You do not have enough tokens!");
            return;
        }
        sender.sendMessage(this.prefix + m.c("&7You have sent &b" + reciever.getName() + " &e" + tokens + "⛀"));
        reciever.sendMessage(this.prefix + m.c("&b" + sender.getName() + "&7 has sent you &e" + tokens + "⛀"));
        addTokens(reciever, tokens);
        takeTokens(sender, tokens);
    }



}
