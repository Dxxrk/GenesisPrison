package me.dxrk.Events;

import java.util.ArrayList;
import java.util.List;

import mkremins.fanciful.FancyMessage;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.dxrk.Main.Main;

public class KitAndWarp implements Listener {

  public static List<String> getDonorKits() {
    List<String> kits = new ArrayList<>();
    kits.add("Donator");
    kits.add("VIP");
    kits.add("MVP");
    kits.add("Hero");
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
    if ((first.equalsIgnoreCase("/warp") || first.equalsIgnoreCase("/warps")) &&
      second == null) {
      e.setCancelled(true);
      List<String> mines = new ArrayList<>();
      List<String> nonmines = new ArrayList<>();
      for (String s : Main.ess.getWarps().getList()) {
        if (s.length() == 1) {
          mines.add(s);
        }
      }
      FancyMessage minewarps = new FancyMessage("");
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Mine Warps:");
      for (String s : mines) {
        if (p.hasPermission("Essentials.warps." + s)) {
          minewarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/p auto").color(ChatColor.DARK_AQUA).then("").color(ChatColor.GRAY);
          continue;
        } 
        minewarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/p auto").color(ChatColor.RED).then("").color(ChatColor.GRAY);
      } 
      for (String s : getRankedMines()) {
        if (p.hasPermission("Essentials.warps." + s)) {
          minewarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/warp " + s).color(ChatColor.DARK_AQUA).then("").color(ChatColor.GRAY);
          continue;
        } 
        minewarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/warp " + s).color(ChatColor.RED).then("").color(ChatColor.GRAY);
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
