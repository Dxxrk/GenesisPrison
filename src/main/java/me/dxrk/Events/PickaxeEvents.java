package me.dxrk.Events;

import me.dxrk.Enchants.Tool;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

public class PickaxeEvents implements Listener {

    static PickaxeEvents instance = new PickaxeEvents();
    public static PickaxeEvents getInstance() {
        return instance;
    }


    public int getMaxXP(int level) {
        if (level == 1) {
            return 1000;
        }
        return (int) (1000 * (Math.pow(level, 1.38)));
    }

    public void addXP(Tool tool, Player p) {
        tool.addXP(1);
        if (tool.getXP() >= getMaxXP(tool.getLevel())) {
            tool.levelUp();
            tool.resetXP();
            startLevelUp(p);
        }
    }


    //LevelUp
    public void startLevelUp(Player p){

    }




    //Resource Bank
}