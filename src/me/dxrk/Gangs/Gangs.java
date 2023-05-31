package me.dxrk.Gangs;


import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;

public class Gangs implements Listener {
    static Gangs instance = new Gangs();

    public static Gangs getInstance() {
        return instance;
    }
    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    public String getGang(OfflinePlayer p){
        return PlayerDataHandler.getPlayerData(p).getString("Gang");
    }
    public int getGangLevel(String gang) {
        return settings.getGangs().getInt(gang+".Level");
    }
    public double getGangBlocks(String gang) {
        return settings.getGangs().getDouble(gang+".BlocksBroken");
    }

    public boolean hasGang(OfflinePlayer p) {
        return !PlayerDataHandler.getPlayerData(p).getString("Gang").equals("");
    }

    public void createGang(Player p, String name) {
        settings.getGangs().set(name+".Owner", p.getUniqueId().toString());
        settings.getGangs().set(name+".Level", 0);
        settings.getGangs().set(name+".BlocksBroken", 0);
        settings.getGangs().set(name+".MaxMembers", 4);
        List<String> members = new ArrayList<>();
        settings.getGangs().set(name+".Members", members);
        List<String> perks = new ArrayList<>();
        settings.getGangs().set(name+".PerksUnlocked", perks);
        settings.saveGangs();
        PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".Gang", name);
        PlayerDataHandler.savePlayerData(p);
    }

    public void addMember(Player p, String name) {
        List<String> members = settings.getGangs().getStringList(name+".Members");
        members.add(p.getUniqueId().toString());
        settings.getGangs().set(name+".Members", members);
        settings.saveGangs();
        PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".Gang", name);
        PlayerDataHandler.savePlayerData(p);
    }

    public void removeMember(OfflinePlayer p, String name) {
        List<String> members = settings.getGangs().getStringList(name+".Members");
        members.remove(p.getUniqueId().toString());
        settings.getGangs().set(name+".Members", members);
        settings.saveGangs();
        PlayerDataHandler.getPlayerData(p).set(p.getUniqueId().toString()+".Gang", "");
        PlayerDataHandler.savePlayerData(p);
    }

    public void addToBank(String name, double tokens) {
        int gangTokens = settings.getGangs().getInt(name+".GangTokens");
        settings.getGangs().set(name+"GangTokens", gangTokens+((int) (tokens/10000)));
        settings.saveGangs();
    }

    public void removeFromBank(String name, int amt) {
        int gangTokens = settings.getGangs().getInt(name+".GangTokens");
        settings.getGangs().set(name+"GangTokens", gangTokens-amt);
        settings.saveGangs();
    }

    public void addPerk(String name, String perk) {
        List<String> perks = settings.getGangs().getStringList(name+".PerksUnlocked");
        perks.add(perk);
        settings.getGangs().set(name+".PerksUnlocked", perks);
        settings.saveGangs();
    }
    public void changeOwner(Player p, String newOwner, String name) {
        String owner = settings.getGangs().getString(name+".Owner");
        List<String> members = settings.getGangs().getStringList(name+".Members");
        members.remove(p.getUniqueId().toString());
        members.add(owner);
        settings.getGangs().set(name+".Owner", newOwner);
        settings.getGangs().set(name+".Members", members);
    }
}
