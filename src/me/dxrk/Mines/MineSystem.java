package me.dxrk.Mines;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class MineSystem {
    public static HashMap<String, Mine> mines = new HashMap<>();

    static MineSystem instance = new MineSystem();

    public static MineSystem getInstance() {
        return instance;
    }

    public void addActiveMine(Mine var1) {
        mines.put(var1.getMineName(), var1);
    }

    public void removeActiveMine(Mine var1) {
        mines.remove(var1.getMineName(), var1);
    }

    public HashMap<String, Mine> getActiveMines() {
        return mines;
    }

    public boolean doesMineNameExist(String var1) {
        return mines.containsKey(var1);
    }

    public Mine getMineByPlayer(Player p) {
        String uuid = p.getUniqueId().toString();
        return mines.get(uuid);
    }

    public Mine getMineByName(String name) {
        return mines.get(name);
    }


}
