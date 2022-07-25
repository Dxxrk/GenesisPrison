package me.dxrk.Commands;

import java.util.ArrayList;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import me.dxrk.Events.CreateMine;
import me.dxrk.Main.Main;

public class GiveBook implements CommandExecutor, Listener {
  Main pl;
  
  public GiveBook(Main p) {
    p.getServer().getPluginManager().registerEvents(this, (Plugin)p);
    this.pl = p;
  }
  
  public ItemStack DRBook(String name, int rank) {
    ItemStack a = new ItemStack(Material.BOOK, 1);
    ItemMeta am = a.getItemMeta();
    am.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    ArrayList<String> lore = new ArrayList<String>();
    lore.add(ChatColor.translateAlternateColorCodes('&', "&3{&bx&3}&bRight Click To Activate&3{&bx&3}&r"));
    lore.add(ChatColor.translateAlternateColorCodes('&', "&a &a &a &a &a &a &a &a&7”&fMore Info In /buy&7”"));
    lore.add(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB " + rank));
    am.setLore(lore);
    a.setItemMeta(am);
    return a;
  }
  
  public void errorMessage(CommandSender p) {
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4/givebook player (number from 1-8)"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c1 - Cavalry"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c2 - Hoplite"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c3 - Captain"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c4 - Colonel"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c5 - Ares"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c6 - Hermes"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c7 - Apollo"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c8 - Kronos"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c9 - Zeus"));
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("givebook"))
      if (sender.hasPermission("epsilon.givebook")) {
        if (args.length == 2) {
          if (Bukkit.getPlayer(args[0]) != null) {
            if (args[1].equals("1")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0&4&l{&7&l+&4&l}&7&l&kW&c&lCavalry&7&l&kW&4&l{&7&l+&4&l}&r", 1) });
            } else if (args[1].equals("2")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &a&e&l{&7&l+&e&l}&6&l&kW&e&lHoplite&6&l&kW&e&l{&7&l+&e&l}&r", 2) });
            } else if (args[1].equals("3")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &a &a &a &a &a&a&l{&7&l+&a&l}&3&l&kW&a&lCaptain&3&l&kW&a&l{&7&l+&a&l}&r", 3) });
            } else if (args[1].equals("4")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &a &a&6&l{&7&l+&6&l}&e&l&kW&6&lColonel&e&l&kW&6&l{&7&l+&6&l}&r", 4) });
            } else if (args[1].equals("5")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &0 &f&l{&7&l+&f&l}&5&l&kW&c&lAres&5&l&kW&f&l{&7&l+&f&l}&r", 5) });
            } else if (args[1].equals("6")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &0 &0 &c&l{&7&l+&c&l}&2&l&kW&a&lHermes&2&l&kW&c&l{&7&l+&c&l}&r", 6) });
            } else if (args[1].equals("7")) {
            	Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &0 &0 &5&l{&7&l+&5&l}&e&l&kW&6&lApollo&e&l&kW&5&l{&7&l+&5&l}&r", 7) });
            } else if (args[1].equals("8")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &0 &b&l{&7&l+&b&l}&d&l&kW&8&lKronos&d&l&kW&b&l{&7&l+&b&l}&r", 8) });
            } else if (args[1].equals("9")) {
              Bukkit.getPlayer(args[0]).getInventory().addItem(new ItemStack[] { DRBook("&0  &0 &0 &0 &0 &0 &0 &0 &f&l{&7&l+&f&l}&7&l&kW&f&lZeus&7&l&kW&f&l{&7&l+&f&l}&r", 9) });
            } else {
              errorMessage(sender);
            } 
          } else {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cTarget must be online"));
          } 
        } else {
          errorMessage(sender);
        } 
      } else {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cMissing permission"));
      }  
    return false;
  }
  
  @EventHandler
  public void onClick(PlayerInteractEvent e) {
    if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
      Player p = e.getPlayer();
      if (p.getItemInHand() != null && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore())
        for (String s : p.getItemInHand().getItemMeta().getLore()) {
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 1"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Cavalry");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used a &4&l{&7&l+&4&l}&7&l&kW&c&lCavalry&7&l&kW&4&l{&7&l+&4&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 2"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Hoplite");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used a &e&l{&6&l+&e&l}&6&l&kW&e&lHoplite&6&l&kW&e&l{&6&l+&e&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 3"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Captain");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used a &a&l{&3&l+&a&l}&3&l&kW&a&lCaptain&3&l&kW&a&l{&3&l+&a&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 4"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Colonel");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used a &6&l{&e&l+&6&l}&e&l&kW&6&lColonel&e&l&kW&6&l{&e&l+&6&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 5"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Ares");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used an &f&l{&b&l+&f&l}&9&l&kW&c&lAres&9&l&kW&f&l{&b&l+&f&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 6"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Hermes");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used a &c&l{&3&l+&c&l}&3&l&kW&c&lHermes&3&l&kW&c&l{&3&l+&c&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 7"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Apollo");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used an &5&l{&d&l+&5&l}&d&l&kW&5&lApollo&d&l&kW&5&l{&d&l+&5&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 8"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Kronos");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used an &b&l{&5&l+&b&l}&d&l&kW&8&lKronos&d&l&kW&b&l{&5&l+&b&l}&r &bbook!"));
          } 
          if (s.equals(ChatColor.translateAlternateColorCodes('&', "&0&l@DRB 9"))) {
            Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " Zeus");
            CreateMine.getInstance().checkRank(p);
            p.setItemInHand(null);
            Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&d" + p.getName() + " &bhas used an &f&l{&7&l+&f&l}&7&l&kW&f&lZeus&7&l&kW&f&l{&7&l+&f&l}&r &bbook!"));
          } 
        }  
    } 
  }
}
