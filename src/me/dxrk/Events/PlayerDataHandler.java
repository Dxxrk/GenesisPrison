package me.dxrk.Events;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.dxrk.Main.SettingsManager;

import java.util.ArrayList;
import java.util.List;

public class PlayerDataHandler implements Listener {
  public SettingsManager settings = SettingsManager.getInstance();
  
  public FileConfiguration pl = this.settings.getPlayerData();
  
  public static PlayerDataHandler instance = new PlayerDataHandler();
  
  public static PlayerDataHandler getInstance() {
    return instance;
  }
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
    String name = e.getPlayer().getName();
    String uuid = e.getPlayer().getUniqueId().toString();
    if (this.pl.get(uuid + ".Name") == null) {
      this.pl.set(uuid + ".Name", name);
      this.settings.savePlayerData();
    } 
    if (this.pl.get(uuid + ".KeysFound") == null) {
      this.pl.set(uuid + ".KeysFound", 0);
      this.settings.savePlayerData();
    } 
    if (this.pl.get(uuid + ".BlocksBroken") == null) {
      this.pl.set(uuid + ".BlocksBroken", 0);
      this.settings.savePlayerData();
    } 
    if (this.pl.get(uuid + ".CratesOpened") == null) {
      this.pl.set(uuid + ".CratesOpened", 0);
      this.settings.savePlayerData();
    } 
    if (this.pl.get(uuid + ".TimePlayed") == null) {
      this.pl.set(uuid + ".TimePlayed", 0);
      this.settings.savePlayerData();
    }
    if (this.pl.get(uuid + ".TimesPrestiged") == null) {
      this.pl.set(uuid + ".TimesPrestiged", 0);
      this.settings.savePlayerData();
    }
    if (this.pl.get(uuid + ".Prestiges") == null) {
      this.pl.set(uuid + ".Prestiges", 0);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickLevel") == null){
      this.pl.set(uuid+".PickLevel", 1);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickXP") == null){
      this.pl.set(uuid+".PickXP", 0.0D);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".Trinkets") == null){
      List<String> Trinkets = new ArrayList<>();
      this.pl.set(uuid+".Trinkets", Trinkets);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickaxeSkill") == null){
      this.pl.set(uuid+".PickaxeSkill", "None");
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickaxeSkillLevel") == null){
      this.pl.set(uuid+".PickaxeSkillLevel", 0);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickaxeSkillPoints") == null){
      this.pl.set(uuid+".PickaxeSkillPoints", 0);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickaxeSkillPointsSpent") == null){
      this.pl.set(uuid+".PickaxeSkillPointsSpent", 0);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickaxeSkillsUnlocked") == null){
      List<String> skills = new ArrayList<>();
      this.pl.set(uuid+".PickaxeSkillsUnlocked", skills);
      this.settings.savePlayerData();
    }
  }
}
