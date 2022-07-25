package me.dxrk.Events;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.dxrk.Main.Main;

public class Staff implements Listener, CommandExecutor {
  Inventory staffsinv = null;
  
  public ItemStack registeritem(String type, String price) {
    ItemStack staff = new ItemStack(Material.STICK, 1);
    ItemMeta meta = staff.getItemMeta();
    meta.setDisplayName(ChatColor.GRAY + "Epsilon Staff: " + ChatColor.AQUA + type);
    ArrayList<String> lore = new ArrayList<String>();
    lore.add(ChatColor.GRAY + "Remaining Uses:");
    lore.add(ChatColor.GOLD + "5");
    lore.add(ChatColor.GRAY + "Price: " + ChatColor.GREEN + price);
    meta.setLore(lore);
    staff.setItemMeta(meta);
    return staff;
  }
  
  public boolean isInt(String s) {
    try {
      Integer.parseInt(s);
    } catch (NumberFormatException nfe) {
      return false;
    } 
    return true;
  }
  
  public void useStaff(Player p, PotionEffectType e, int time, int power) {
    int uses = Integer.parseInt(ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(1)).replace(" ", ""));
    if (uses > 1) {
      ItemMeta meta = p.getItemInHand().getItemMeta();
      meta.setDisplayName(p.getItemInHand().getItemMeta().getDisplayName());
      ArrayList<String> newlore = new ArrayList<String>();
      newlore.add(p.getItemInHand().getItemMeta().getLore().get(0));
      newlore.add(ChatColor.GOLD + String.valueOf(uses - 1));
      meta.setLore(newlore);
      p.getItemInHand().setItemMeta(meta);
    } else {
      p.setItemInHand(null);
    } 
    p.updateInventory();
    p.removePotionEffect(e);
    p.addPotionEffect(new PotionEffect(e, time * 20, power));
    p.getWorld().playEffect(p.getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
  }
  
  @EventHandler
  public void useStaff(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    if (p.getItemInHand() == null)
      return; 
    if (p.getItemInHand().getType() != Material.STICK)
      return; 
    if (!p.getItemInHand().hasItemMeta())
      return; 
    if (!p.getItemInHand().getItemMeta().hasLore())
      return; 
    String type = ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName().replace("Staff: ", ""));
    if (type.equalsIgnoreCase("Speed"))
      useStaff(p, PotionEffectType.SPEED, 30, 1); 
    if (type.equalsIgnoreCase("Mining"))
      useStaff(p, PotionEffectType.FAST_DIGGING, 30, 3); 
    if (type.equalsIgnoreCase("Power"))
      useStaff(p, PotionEffectType.INCREASE_DAMAGE, 30, 0); 
    if (type.equalsIgnoreCase("Invisibility"))
      useStaff(p, PotionEffectType.INVISIBILITY, 30, 1); 
    if (type.equalsIgnoreCase("Jump"))
      useStaff(p, PotionEffectType.JUMP, 30, 1); 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("StaffsGive")) {
      if (args.length == 3) {
        if (sender.hasPermission("Epsilon.Admin") || !(sender instanceof Player)) {
          Player target = Bukkit.getServer().getPlayer(args[0]);
          if (target == null) {
            sender.sendMessage(ChatColor.RED + "Error no player found!");
            return false;
          } 
          ItemStack upgradebook = new ItemStack(Material.STICK);
          ItemMeta upgmade = upgradebook.getItemMeta();
          String type = args[1];
          if (type.equalsIgnoreCase("Speed") || type.equalsIgnoreCase("Mining") || type.equalsIgnoreCase("Power") || type.equalsIgnoreCase("Invisibility") || type.equalsIgnoreCase("Jump")) {
            upgmade.setDisplayName(ChatColor.GRAY + "Staff: " + ChatColor.AQUA + type);
          } else {
            sender.sendMessage(ChatColor.RED + "Staff type not found: Avalible Types: Speed | Mining | Power | Invisibility | Jump");
            return false;
          } 
          ArrayList<String> lore = new ArrayList<String>();
          lore.add(ChatColor.GRAY + "Remaining Uses:");
          if (isInt(args[2])) {
            lore.add(ChatColor.GOLD + args[2]);
          } else {
            sender.sendMessage(ChatColor.RED + "Error not a number value!");
            return false;
          } 
          upgmade.setLore(lore);
          upgradebook.setItemMeta(upgmade);
          target.getInventory().addItem(new ItemStack[] { upgradebook });
        } 
      } else {
        sender.sendMessage(ChatColor.RED + "/StaffsGive <Player> <Type> <Uses>");
      } 
    } else if (cmd.getName().equalsIgnoreCase("Staffs") && 
      sender instanceof Player) {
        this.staffsinv = Bukkit.createInventory(null, 9, ChatColor.RED + "Buy Staffs");
        this.staffsinv.setItem(0, registeritem("Speed", "750000000"));
        this.staffsinv.setItem(2, registeritem("Mining", "750000000"));
        this.staffsinv.setItem(4, registeritem("Power", "2000000000"));
        this.staffsinv.setItem(6, registeritem("Invisibility", "5000000000000"));
        this.staffsinv.setItem(8, registeritem("Jump", "500000000"));
      
      Player p = (Player)sender;
      p.openInventory(this.staffsinv);
    } 
    return true;
  }
  
  @EventHandler
  public void onclick(InventoryClickEvent e) {
    if (e.getInventory().getName().equals(ChatColor.RED + "Buy Staffs")) {
      if (e.getCurrentItem() == null)
        return; 
      ItemStack i = e.getCurrentItem();
      try {
        int price = Integer.parseInt(ChatColor.stripColor(i.getItemMeta().getLore().get(2)).replace("Price: ", ""));
        Player p = (Player)e.getWhoClicked();
        if (Main.econ.getBalance((OfflinePlayer)p) >= price) {
          i.setAmount(1);
          p.getInventory().addItem(new ItemStack[] { i });
          Main.econ.withdrawPlayer((OfflinePlayer)p, price);
          p.sendMessage(ChatColor.GREEN + "Transaction complete!");
          e.setCancelled(true);
        } else {
          p.sendMessage(ChatColor.RED + "Error not enough money!");
          e.setCancelled(true);
        } 
      } catch (Exception exception) {}
    } 
  }
}
