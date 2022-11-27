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



    public void updateEvent(){
        StringBuilder s = new StringBuilder();
        for (String activeEvent : activeEvents) {
            s.append(activeEvent).append(", ");
        }
        events = s.toString();
    }


    public void activateEvent(String event) {
        if(activeEvents.contains(event)){
            return;
        }
        activeEvents.add(event);



    }

    public void deactiveEvent(String event){
        activeEvents.remove(event);

    }

    public void eventBreak(Player p, String event){
        List<String> skillsUnlocked = settings.getPlayerData().getStringList(p.getUniqueId().toString()+".PickaxeSkillsUnlocked");
        for(String s : skillsUnlocked) {
            if(s.contains(event)) {
                activateEvent(event);
            }
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Random r = new Random();

    }





}
