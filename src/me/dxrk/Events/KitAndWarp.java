package me.dxrk.Events;

import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class KitAndWarp implements Listener {

  public static List<String> getDonorKits() {
    List<String> kits = new ArrayList<>();
    kits.add("Demi-God");
    kits.add("Titan");
    kits.add("God");
    kits.add("Olympian");
    kits.add("Genesis");
    return kits;
  }
  
  public static List<String> getRankedMines() {
    List<String> kits = new ArrayList<>();
    kits.add("Use /mine");
    return kits;
  }
  
  public static List<String> getOtherMines() {
    List<String> kits = new ArrayList<>();
    kits.add("Crates");
    return kits;
  }
  
  @EventHandler
  public void onCmd(PlayerCommandPreprocessEvent e) {
    Player p = e.getPlayer();
    String first = null;
    String second = null;
    String third = null;
    byte b;
    int i;
    String[] arrayOfString;
    for (i = (arrayOfString = e.getMessage().split(" ")).length, b = 0; b < i; ) {
      String s = arrayOfString[b];
      if (first == null) {
        try {
          first = s;
        } catch (Exception ignored) {}
      } else if (second == null) {
        try {
          second = s;
        } catch (Exception ignored) {}
      } else if (third == null) {
        try {
          third = s;
        } catch (Exception ignored) {}
      } 
      b++;
    }
    assert first != null;
    if ((first.equalsIgnoreCase("/kit") || first.equalsIgnoreCase("/kits")) &&
      second == null) {
      e.setCancelled(true);
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Kits:");
      FancyMessage donorkits = new FancyMessage("");
      for (String s : getDonorKits()) {
        if (p.hasPermission("Essentials.kits." + s)) {
          donorkits.then(s).tooltip(ChatColor.GRAY + "Click for kit!").command("/kit " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
          continue;
        } 
        donorkits.then(s).tooltip(ChatColor.RED + "You do not have this kit!").command("/kit " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
      } 
      donorkits.send(p);
    }
    else if ((first.equalsIgnoreCase("/kit") || first.equalsIgnoreCase("/kits")) && second != null) {
      if(second.equalsIgnoreCase("godkeys")){
        if(p.hasPermission("rank.god")) {
          LocksmithHandler.getInstance().addKey(p, "token", 11);
          LocksmithHandler.getInstance().addKey(p, "omega", 11);
          LocksmithHandler.getInstance().addKey(p, "beta", 11);
          LocksmithHandler.getInstance().addKey(p, "alpha", 11);
          LocksmithHandler.getInstance().addKey(p, "community", 6);
        }
      }
      if(second.equalsIgnoreCase("olympiankeys")){
        if(p.hasPermission("rank.olympian")) {
          LocksmithHandler.getInstance().addKey(p, "token", 13);
          LocksmithHandler.getInstance().addKey(p, "omega", 13);
          LocksmithHandler.getInstance().addKey(p, "beta", 13);
          LocksmithHandler.getInstance().addKey(p, "alpha", 13);
          LocksmithHandler.getInstance().addKey(p, "community", 8);
        }
      }
      if(second.equalsIgnoreCase("genesiskeys")){
        if(p.hasPermission("rank.genesis")) {
          LocksmithHandler.getInstance().addKey(p, "token", 15);
          LocksmithHandler.getInstance().addKey(p, "omega", 15);
          LocksmithHandler.getInstance().addKey(p, "beta", 15);
          LocksmithHandler.getInstance().addKey(p, "alpha", 15);
          LocksmithHandler.getInstance().addKey(p, "community", 10);
        }
      }
      if(second.equalsIgnoreCase("god")) {
        if(p.hasPermission("rank.god")) {
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate "+p.getName()+" contraband");
        }
      }
      if(second.equalsIgnoreCase("olympian")) {
        if(p.hasPermission("rank.olympian")) {
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate "+p.getName()+" contraband");
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate "+p.getName()+" contraband");
        }
      }
      if(second.equalsIgnoreCase("genesis")) {
        if(p.hasPermission("rank.genesis")) {
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate "+p.getName()+" contraband");
          Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate "+p.getName()+" genesis");
        }
      }
    }
    if ((first.equalsIgnoreCase("/warp") || first.equalsIgnoreCase("/warps")) &&
      second == null) {
      e.setCancelled(true);

      FancyMessage minewarps = new FancyMessage("");
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Mine Warps:");

      for (String s : getRankedMines()) {
          minewarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/mine tp").color(ChatColor.DARK_AQUA).then("").color(ChatColor.GRAY);
      }
      minewarps.send(p);
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Other Warps:");
      FancyMessage otherwarps = new FancyMessage("");
      for (String s : getOtherMines()) {
        if (p.hasPermission("Essentials.warps." + s)) {
          otherwarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/warp " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
          continue;
        } 
        otherwarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/warp " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
      } 
      otherwarps.send(p);
    } 
  }
}
