package me.dxrk.Events;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.dxrk.Main.SettingsManager;

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
    if (this.pl.get(String.valueOf(uuid) + ".Name") == null) {
      this.pl.set(String.valueOf(uuid) + ".Name", name);
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".KeysFound") == null) {
      this.pl.set(String.valueOf(uuid) + ".KeysFound", Integer.valueOf(0));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".BlocksBroken") == null) {
      this.pl.set(String.valueOf(uuid) + ".BlocksBroken", Integer.valueOf(0));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".CratesOpened") == null) {
      this.pl.set(String.valueOf(uuid) + ".CratesOpened", Integer.valueOf(0));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".TimePlayed") == null) {
      this.pl.set(String.valueOf(uuid) + ".TimePlayed", Integer.valueOf(0));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".PicksellSTFU") == null) {
      this.pl.set(String.valueOf(uuid) + ".PicksellSTFU", Boolean.valueOf(false));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".Beta") == null) {
      this.pl.set(String.valueOf(uuid) + ".Beta", Boolean.valueOf(false));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".RankupMessages") == null) {
      this.pl.set(String.valueOf(uuid) + ".RankupMessages", Boolean.valueOf(true));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".KeyFinderMessages") == null) {
      this.pl.set(String.valueOf(uuid) + ".KeyFinderMessages", Boolean.valueOf(true));
      this.settings.savePlayerData();
    } 
    if (this.pl.get(String.valueOf(uuid) + ".Particles") == null) {
      this.pl.set(String.valueOf(uuid) + ".Particles", Boolean.valueOf(true));
      this.settings.savePlayerData();
    } 
  }
}
