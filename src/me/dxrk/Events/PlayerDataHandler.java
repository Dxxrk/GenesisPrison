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
    if (this.pl.get(uuid + ".RankupMessages") == null) {
      this.pl.set(uuid + ".RankupMessages", Boolean.TRUE);
      this.settings.savePlayerData();
    } 
    if (this.pl.get(uuid + ".KeyFinderMessages") == null) {
      this.pl.set(uuid + ".KeyFinderMessages", Boolean.TRUE);
      this.settings.savePlayerData();
    } 
    if (this.pl.get(uuid + ".Particles") == null) {
      this.pl.set(uuid + ".Particles", Boolean.TRUE);
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
      this.pl.set(uuid+".PickLevel", 0);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".PickXP") == null){
      this.pl.set(uuid+".PickXP", 0);
      this.settings.savePlayerData();
    }
    if(this.pl.get(uuid+".Trinkets") == null){
      List<String> Trinkets = new ArrayList<>();
      this.pl.set(uuid+".Trinkets", Trinkets);
      this.settings.savePlayerData();
    }
  }
}
