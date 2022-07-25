package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Main.SettingsManager;

public class Nicks implements CommandExecutor, Listener {
  SettingsManager settings = SettingsManager.getInstance();
  
  public HashMap<Player, String> edit = new HashMap<Player, String>();
  
  public HashMap<Player, String> currentletter = new HashMap<Player, String>();
  
  public HashMap<Player, List<String>> editlist = new HashMap<Player, List<String>>();
  
  public HashMap<Player, Integer> currentnumber = new HashMap<Player, Integer>();
  
  public HashMap<Player, Integer> max = new HashMap<Player, Integer>();
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (!(sender instanceof Player))
      return false; 
    Player p = (Player)sender;
    if (cmd.getName().equalsIgnoreCase("Nicknames") || cmd.getName().equalsIgnoreCase("Nick") || cmd.getName().equalsIgnoreCase("Nickname"))
      if (args.length == 0) {
        this.edit.remove(p);
        this.currentletter.remove(p);
        this.max.remove(p);
        this.currentnumber.remove(p);
        this.editlist.remove(p);
        char c = p.getName().charAt(0);
        openEditInv(p, String.valueOf(c));
      } else if (args.length == 1) {
        String newNick = args[0];
        if (!p.hasPermission("rank.hermes")) {
          p.sendMessage(ChatColor.RED + "You do not have permission for this! Use /Nickname");
          return false;
        } 
        if ((newNick.contains("&k") || newNick.contains("&K")) && 
          !p.hasPermission("rank.zeus")) {
          p.sendMessage(ChatColor.RED + "Only Zeus Rank can have magic in nicknames");
          return false;
        } 
        if ((newNick.contains("&l") || newNick.contains("&L")) && 
          !p.hasPermission("rank.kronos")) {
          p.sendMessage(ChatColor.RED + "Only Kronos+ rank can have bold in nicknames");
          return false;
        } 
        if ((newNick.contains("&o") || newNick.contains("&O")) && 
          !p.hasPermission("rank.apollo")) {
          p.sendMessage(ChatColor.RED + "Only Apollo+ rank can have italic in nicknames");
          return false;
        } 
        if (args[0].length() > 36) {
          p.sendMessage(ChatColor.RED + "Nickname too long!");
          return false;
        } 
        this.settings.getcolor().set(String.valueOf(p.getName()) + ".Nickname", args[0]);
        this.settings.savecolorFile();
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Nickname set to: " + args[0]));
      } else {
        sender.sendMessage(ChatColor.RED + "/Nickname <Nickname>");
        return false;
      }  
    return false;
  }
  
  @EventHandler
  public void invClick(InventoryClickEvent e) {
    if (!e.getInventory().getName().contains("Current Letter"))
      return; 
    Player p = (Player)e.getWhoClicked();
    if (this.max.get(p) == null) {
      List<String> titleindiv = new ArrayList<String>();
      for (int x = 0; x < p.getName().toString().length(); x++) {
        char letter = p.getName().toString().charAt(x);
        String letter1 = String.valueOf(letter);
        titleindiv.add(letter1);
      } 
      this.currentletter.put(p, titleindiv.get(0));
      this.max.put(p, Integer.valueOf(titleindiv.size()));
      this.currentnumber.put(p, Integer.valueOf(0));
      this.editlist.put(p, titleindiv);
      this.edit.put(p, "&9");
    } 
    int slot = e.getSlot();
    e.setCancelled(true);
    if (slot < 43) {
      if (!p.hasPermission("rank.Hoplite")) {
        try {
          e.getCurrentItem().setType(Material.BARRIER);
        } catch (Exception exception) {}
        p.sendMessage(ChatColor.RED + "Hoplite and higher, sorry.");
        return;
      } 
      String s = this.edit.get(p);
      if (slot == 0)
        s = String.valueOf(s) + "&1"; 
      if (slot == 1)
        s = String.valueOf(s) + "&2"; 
      if (slot == 2)
        s = String.valueOf(s) + "&3"; 
      if (slot == 3)
        s = String.valueOf(s) + "&4"; 
      if (slot == 4)
        s = String.valueOf(s) + "&5"; 
      if (slot == 5)
        s = String.valueOf(s) + "&6"; 
      if (slot == 6)
        s = String.valueOf(s) + "&7"; 
      if (slot == 7)
        s = String.valueOf(s) + "&8"; 
      if (slot == 8)
        s = String.valueOf(s) + "&9"; 
      if (slot == 18)
        s = String.valueOf(s) + "&a"; 
      if (slot == 19)
        s = String.valueOf(s) + "&b"; 
      if (slot == 20)
        s = String.valueOf(s) + "&c"; 
      if (slot == 21)
        s = String.valueOf(s) + "&d"; 
      if (slot == 22)
        s = String.valueOf(s) + "&e"; 
      if (slot == 23)
        s = String.valueOf(s) + "&f"; 
      if (slot == 36)
        if (p.hasPermission("rank.Ares")) {
          s = String.valueOf(s) + "&l";
        } else {
          e.getCurrentItem().setType(Material.BARRIER);
          p.sendMessage(ChatColor.RED + "Ares and higher!");
        }  
      if (slot == 37)
        if (p.hasPermission("rank.Captain")) {
          s = String.valueOf(s) + "&n";
        } else {
          e.getCurrentItem().setType(Material.BARRIER);
          p.sendMessage(ChatColor.RED + "Captain and higher!");
        }  
      if (slot == 38)
        if (p.hasPermission("rank.Captain")) {
          s = String.valueOf(s) + "&m";
        } else {
          e.getCurrentItem().setType(Material.BARRIER);
          p.sendMessage(ChatColor.RED + "Captain and higher!");
        }  
      if (slot == 39)
        if (p.hasPermission("rank.Colonel")) {
          s = String.valueOf(s) + "&o";
        } else {
          e.getCurrentItem().setType(Material.BARRIER);
          p.sendMessage(ChatColor.RED + "Colonel and higher!");
        }  
      this.edit.put(p, s);
      return;
    } 
    if (slot == 44) {
      String s = this.edit.get(p);
      List<String> list = this.editlist.get(p);
      String letter = String.valueOf(p.getName().charAt(((Integer)this.currentnumber.get(p)).intValue()));
      s = String.valueOf(s) + letter;
      this.edit.put(p, s);
      int current = ((Integer)this.currentnumber.get(p)).intValue();
      this.currentnumber.put(p, Integer.valueOf(current + 1));
      if (((Integer)this.currentnumber.get(p)).intValue() >= ((Integer)this.max.get(p)).intValue()) {
        this.settings.getcolor().set(String.valueOf(p.getName()) + ".Nickname", String.valueOf(this.edit.get(p)));
        p.sendMessage(ChatColor.GREEN + "Nickname Updated");
        this.settings.savecolorFile();
        p.closeInventory();
        this.edit.remove(p);
        this.currentletter.remove(p.getName());
        this.max.remove(p);
        this.currentnumber.remove(p);
        this.editlist.remove(p);
        return;
      } 
      String letter1 = list.get(current + 1);
      openEditInv(p, letter1);
    } 
  }
  
  public ItemStack invItem(Material mat, int shortnum, String name) {
    ItemStack i = new ItemStack(mat, 1, (short)shortnum);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    i.setItemMeta(im);
    return i;
  }
  
  public void openEditInv(Player p, String letter) {
    Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED + letter + " | Current Letter");
    inv.setItem(0, invItem(Material.WOOL, 11, "&1Dark Blue"));
    inv.setItem(1, invItem(Material.WOOL, 13, "&2Dark Green"));
    inv.setItem(2, invItem(Material.WOOL, 9, "&3Cyan"));
    inv.setItem(3, invItem(Material.WOOL, 14, "&4Dark Red"));
    inv.setItem(4, invItem(Material.WOOL, 10, "&5Purple"));
    inv.setItem(5, invItem(Material.WOOL, 1, "&6Gold"));
    inv.setItem(6, invItem(Material.WOOL, 8, "&7Light Gray"));
    inv.setItem(7, invItem(Material.WOOL, 7, "&8Dark Gray"));
    inv.setItem(8, invItem(Material.WOOL, 3, "&9Dark Aqua"));
    inv.setItem(18, invItem(Material.STAINED_GLASS, 5, "&aLight Green"));
    inv.setItem(19, invItem(Material.STAINED_GLASS, 3, "&bAqua"));
    inv.setItem(20, invItem(Material.STAINED_GLASS, 14, "&cRed"));
    inv.setItem(21, invItem(Material.STAINED_GLASS, 6, "&dPink"));
    inv.setItem(22, invItem(Material.STAINED_GLASS, 4, "&eYellow"));
    inv.setItem(23, invItem(Material.STAINED_GLASS, 0, "&fWhite"));
    inv.setItem(36, invItem(Material.IRON_INGOT, 0, "&f&lBold"));
    inv.setItem(37, invItem(Material.GOLD_INGOT, 0, "&f&nUnderline"));
    inv.setItem(38, invItem(Material.DIAMOND, 0, "&f&mStrikethrough"));
    inv.setItem(39, invItem(Material.EMERALD, 0, "&f&oItalic"));
    inv.setItem(44, invItem(Material.NETHER_STAR, 0, "&c&lNext Letter"));
    p.openInventory(inv);
  }
}
