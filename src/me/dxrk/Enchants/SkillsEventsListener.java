package me.dxrk.Enchants;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SkillsEventsListener implements Listener {

    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    public static String events = "";
    public static List<String> activeEvents = new ArrayList<>();



    public void updateGodEvent(){
        StringBuilder s = new StringBuilder();
        for (String activeEvent : activeEvents) {
            s.append(activeEvent).append(", ");
        }
        events = s.toString();
    }


    public void activateGodEvent(String event) {
        if(activeEvents.contains(event)){
            return;
        }
        activeEvents.add(event);



    }

    public void deactiveGodEvent(String event){
        activeEvents.remove(event);

    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Random r = new Random();

    }





}
