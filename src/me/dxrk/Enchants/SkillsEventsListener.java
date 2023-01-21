package me.dxrk.Enchants;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.scheduler.BukkitRunnable;

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
        Random r = new Random();

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
        //Zeus
        if(settings.getPlayerData().get(p.getUniqueId().toString()+".PickaxeSkill").equals("Zeus")) {
            int skill = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillLevel");
            int i = r.nextInt(10000/(skill));
            if(i == 1) {
                if(activeEvents.size() >=2) {
                    return;
                }
                int ii = r.nextInt(2);
                if(ii == 0){
                    if(activeEvents.contains("Lightning")) return;
                    activateEvent("Lightning");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &e&lLightning &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Lightning");
                            Bukkit.broadcastMessage(m.c("&e&lLightning &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
                if(ii == 1) {
                    if(activeEvents.contains("Thunderstorm")) return;
                    activateEvent("Thunderstorm");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &e&lThunderstorm &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Thunderstorm");
                            Bukkit.broadcastMessage(m.c("&e&lThunderStorm &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
            }
        }
        //Poseidon
        if(settings.getPlayerData().get(p.getUniqueId().toString()+".PickaxeSkill").equals("Poseidon")) {
            int skill = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillLevel");
            int i = r.nextInt(10000/(skill));
            if(i == 1) {
                if(activeEvents.size() >=2) {
                    return;
                }
                int ii = r.nextInt(2);
                if(ii == 0){
                    if(activeEvents.contains("Typhoon")) return;
                    activateEvent("Typhoon");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &b&lTyphoon &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Tsunami");
                            Bukkit.broadcastMessage(m.c("&b&lTyphoon &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
                if(ii == 1) {
                    if(activeEvents.contains("Tsunami")) return;
                    activateEvent("Tsunami");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &b&lTsunami &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Tsunami");
                            Bukkit.broadcastMessage(m.c("&b&lTsunami &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
            }
        }
        //Hades
        if(settings.getPlayerData().get(p.getUniqueId().toString()+".PickaxeSkill").equals("Hades")) {
            int skill = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillLevel");
            int i = r.nextInt(10000/(skill));
            if(i == 1) {
                if(activeEvents.size() >=2) {
                    return;
                }
                int ii = r.nextInt(2);
                if(ii == 0){
                    if(activeEvents.contains("Meteor Shower")) return;
                    activateEvent("Meteor Shower");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &4&lMeteor Shower &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Meteor Shower");
                            Bukkit.broadcastMessage(m.c("&4&lMeteor Shower &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
                if(ii == 1) {
                    if(activeEvents.contains("Scorched Earth")) return;
                    activateEvent("Scorched Earth");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &4&lScorched Earth &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Scorched Earth");
                            Bukkit.broadcastMessage(m.c("&4&lScorched Earth &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
            }
        }
        //Ares
        if(settings.getPlayerData().get(p.getUniqueId().toString()+".PickaxeSkill").equals("Ares")) {
            int skill = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillLevel");
            int i = r.nextInt(10000/(skill));
            if(i == 1) {
                if(activeEvents.size() >=2) {
                    return;
                }
                int ii = r.nextInt(2);
                if(ii == 0){
                    if(activeEvents.contains("War Torn")) return;
                    activateEvent("War Torn");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &c&lWar Torn &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("War Torn");
                            Bukkit.broadcastMessage(m.c("&c&lWar Torn &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
                if(ii == 1) {
                    if(activeEvents.contains("Bloodshed")) return;
                    activateEvent("Bloodshed");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &c&lBloodshed &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Bloodshed");
                            Bukkit.broadcastMessage(m.c("&c&lBloodshed &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
            }
        }
        //Aphrodite
        if(settings.getPlayerData().get(p.getUniqueId().toString()+".PickaxeSkill").equals("Aphrodite")) {
            int skill = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillLevel");
            int i = r.nextInt(10000/(skill));
            if(i == 1) {
                if(activeEvents.size() >=2) {
                    return;
                }
                int ii = r.nextInt(2);
                if(ii == 0){
                    if(activeEvents.contains("Allure")) return;
                    activateEvent("Allure");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3has activated the &d&lAllure &3Event!"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Allure");
                            Bukkit.broadcastMessage(m.c("&d&lAllure &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
                if(ii == 1) {
                    if(activeEvents.contains("Strong Desire")) return;
                    activateEvent("Strong Desire");
                    Bukkit.broadcastMessage(m.c("&f&l"+p.getName()+" &3Has activated the &d&lStrong Desire &3Event"));
                    new BukkitRunnable() {
                        public void run() {
                            deactiveEvent("Strong Desire");
                            Bukkit.broadcastMessage(m.c("&d&lStrong Desire &3Event has ended."));
                        }
                    }.runTaskLater(Main.plugin, 20*1800L);
                }
            }
        }

    }





}
