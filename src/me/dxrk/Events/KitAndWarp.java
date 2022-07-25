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
  public static List<String> getStarterKits() {
    List<String> kits = new ArrayList<String>();
    kits.add("Pickaxe");
    kits.add("Food");
    kits.add("PvP");
    kits.add("Tools");
    return kits;
  }
  
  public static List<String> getDonorKits() {
    List<String> kits = new ArrayList<String>();
    kits.add("Cavalry");
    kits.add("Hoplite");
    kits.add("Captain");
    kits.add("Colonel");
    kits.add("Ares");
    kits.add("Hermes");
    kits.add("Apollo");
    kits.add("Kronos");
    kits.add("Zeus");
    return kits;
  }
  
  public static List<String> getRankedMines() {
    List<String> kits = new ArrayList<String>();
    kits.add("Use /mine or /p h");
    return kits;
  }
  
  public static List<String> getOtherMines() {
    List<String> kits = new ArrayList<String>();
    kits.add("Crates");
    kits.add("Plots");
    kits.add("Boss");
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
        } catch (Exception exception) {}
      } else if (second == null) {
        try {
          second = s;
        } catch (Exception exception) {}
      } else if (third == null) {
        try {
          third = s;
        } catch (Exception exception) {}
      } 
      b++;
    } 
    if ((first.toLowerCase().equalsIgnoreCase("/kit") || first.toLowerCase().equalsIgnoreCase("/kits")) && 
      second == null) {
      e.setCancelled(true);
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Kits:");
      FancyMessage kits = new FancyMessage("");
      for (String s : getStarterKits())
        kits.then(s).tooltip(ChatColor.GRAY + "Click for kit!").command("/kit " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY); 
      kits.send(p);
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Donor Kits:");
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
    if (first.toLowerCase().equalsIgnoreCase("/donorwarps") && 
      second == null) {
      e.setCancelled(true);
      FancyMessage minewarps = new FancyMessage("");
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Donor Warps:");
      for (String s : getDonorKits()) {
        if (p.hasPermission("Essentials.warps." + s)) {
          minewarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/warp " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
          continue;
        } 
        minewarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/warp " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
      } 
      minewarps.send(p);
    } 
    if ((first.toLowerCase().equalsIgnoreCase("/warp") || first.toLowerCase().equalsIgnoreCase("/warps")) && 
      second == null) {
      e.setCancelled(true);
      List<String> mines = new ArrayList<String>();
      List<String> nonmines = new ArrayList<String>();
      for (String s : Main.ess.getWarps().getList()) {
        if (s.length() == 1) {
          mines.add(s);
          continue;
        } 
        nonmines.add(s);
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
      FancyMessage donorWarps = (new FancyMessage("[Click To See Donor Warps]")).color(ChatColor.AQUA).tooltip(ChatColor.GRAY + "Type /DonorWarps or Click Here").command("/donorwarps");
      donorWarps.send(p);
    } 
  }
}
