package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
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

public class CMDTags implements Listener, CommandExecutor {
  SettingsManager settings = SettingsManager.getInstance();
  
  public int getPage(String s) {
    StringBuilder lvl = new StringBuilder();
    byte b;
    int i;
    char[] arrayOfChar;
    for (i = (arrayOfChar = ChatColor.stripColor(s).toCharArray()).length, b = 0; b < i; ) {
      char c = arrayOfChar[b];
      if (isInt(c))
        lvl.append(c); 
      b++;
    } 
    if (isInt(lvl.toString()))
      return Integer.parseInt(lvl.toString()); 
    return -1;
  }
  
  public boolean isInt(String s) {
    try {
      int i = Integer.parseInt(s);
      return true;
    } catch (Exception e1) {
      return false;
    } 
  }
  
  public boolean isInt(char ss) {
    String s = String.valueOf(ss);
    try {
      int i = Integer.parseInt(s);
      return true;
    } catch (Exception e1) {
      return false;
    } 
  }
  
  public ItemStack createItemStack(Material mat, String name) {
    ItemStack spacer = new ItemStack(mat, 1);
    ItemMeta im = spacer.getItemMeta();
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    spacer.setItemMeta(im);
    return spacer;
  }
  
  public void fillInv(Inventory i, int page) {
    for (int x = 36; x < i.getSize(); x++) {
      ItemStack spacer = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
      ItemMeta im = spacer.getItemMeta();
      im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7[&5&lTags&7]"));
      spacer.setItemMeta(im);
      i.setItem(x, spacer);
    } 
    i.setItem(45, createItemStack(Material.BOOK, "&6<< Previous Page"));
    i.setItem(53, createItemStack(Material.BOOK, "&6Next Page >>"));
    i.setItem(47, createItemStack(Material.EMERALD, "&aBuy Tags"));
    i.setItem(51, createItemStack(Material.EMERALD, "&aBuy Tags"));
    i.setItem(49, createItemStack(Material.TORCH, "&6Page: &e1"));
  }
  
  public void loadTags(Inventory i, int page, Player p) {
    int x = -1;
    for (String s : this.settings.getTags().getKeys(false)) {
      x++;
      if (page == 1) {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        List<String> lore = new ArrayList<String>();
        if (!p.hasPermission("Tag." + s) && !p.hasPermission("tags.*") && !p.hasPermission("tag.*")) {
          item.setType(Material.BARRIER);
          lore.add(ChatColor.GRAY + "This tag is locked!");
        } else {
          lore.add(ChatColor.GRAY + "Click to use tag!");
        } 
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.settings.getTags().getString(s)));
        im.setLore(lore);
        item.setItemMeta(im);
        i.addItem(new ItemStack[] { item });
        continue;
      } 
      if (page == 2) {
        if (x > 35) {
          ItemStack item = new ItemStack(Material.PAPER, 1);
          List<String> lore = new ArrayList<String>();
          if (!p.hasPermission("Tag." + s) && !p.hasPermission("tags.*") && !p.hasPermission("tag.*")) {
            item.setType(Material.BARRIER);
            lore.add(ChatColor.GRAY + "This tag is locked!");
          } else {
            lore.add(ChatColor.GRAY + "Click to use tag!");
          } 
          ItemMeta im = item.getItemMeta();
          im.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.settings.getTags().getString(s)));
          im.setLore(lore);
          item.setItemMeta(im);
          i.addItem(new ItemStack[] { item });
        } 
        continue;
      } 
      if (page == 3 && 
        x > 71) {
        ItemStack item = new ItemStack(Material.PAPER, 1);
        List<String> lore = new ArrayList<String>();
        if (!p.hasPermission("Tag." + s) && !p.hasPermission("tags.*") && !p.hasPermission("tag.*")) {
          item.setType(Material.BARRIER);
          lore.add(ChatColor.GRAY + "This tag is locked!");
        } else {
          lore.add(ChatColor.GRAY + "Click to use tag!");
        } 
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.settings.getTags().getString(s)));
        im.setLore(lore);
        item.setItemMeta(im);
        i.addItem(new ItemStack[] { item });
      } 
    } 
  }
  
  public void openTagInv(Player p, int page) {
    Inventory i = Bukkit.createInventory(null, 54, ChatColor.DARK_PURPLE + "Tags " + page);
    fillInv(i, page);
    loadTags(i, page, p);
    p.openInventory(i);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getInventory().getName().contains(ChatColor.DARK_PURPLE + "Tags")) {
      Player p = (Player)e.getWhoClicked();
      e.setCancelled(true);
      if (e.getRawSlot() == 45) {
        if (getPage(e.getInventory().getName()) == 1)
          return; 
        openTagInv(p, getPage(e.getInventory().getName()) - 1);
        return;
      } 
      if (e.getRawSlot() == 53) {
        if (getPage(e.getInventory().getName()) == 3)
          return; 
        openTagInv(p, getPage(e.getInventory().getName()) + 1);
        return;
      } 
      if (e.getRawSlot() == 47 || e.getRawSlot() == 47) {
        return;
      } 
      if (e.getRawSlot() == 41)
        return; 
      if (e.getCurrentItem() == null)
        return; 
      if (e.getCurrentItem().getType().equals(Material.PAPER)) {
        this.settings.getData().set(String.valueOf(p.getUniqueId().toString()) + ".Tag", e.getCurrentItem().getItemMeta().getDisplayName());
        this.settings.saveData();
        p.sendMessage(ChatColor.GRAY + "Tag set to " + ChatColor.translateAlternateColorCodes('&', e.getCurrentItem().getItemMeta().getDisplayName()));
        return;
      } 
    } 
  }
  
  Random r = new Random();
  
  public String randomTagName() {
    List<String> names = new ArrayList<String>();
    for (String s : this.settings.getTags().getKeys(false))
      names.add(s); 
    return names.get(this.r.nextInt(names.size()));
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("Tags"))
      if (args.length == 0) {
        if (sender instanceof Player) {
          Player p = (Player)sender;
          openTagInv(p, 1);
        } 
      } else if (args.length == 1) {
        Player p = (Player)sender;
        if (args[0].equalsIgnoreCase("off")) {
          this.settings.getData().set(String.valueOf(p.getUniqueId().toString()) + ".Tag", "");
          this.settings.saveData();
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Your tag has been removed!"));
          return true;
        } 
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/tags"));
      } else if (args.length == 2) {
        Player give = Bukkit.getPlayer(args[0]);
        if (give == null) {
          sender.sendMessage(ChatColor.GOLD + "Player not found!");
          return false;
        } 
        if (args[1].toLowerCase().equals("random")) {
          String tag = randomTagName();
          Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddp " + give.getName() + " " + "Tag." + tag);
          give.sendMessage(ChatColor.GOLD + "You have been given the " + ChatColor.translateAlternateColorCodes('&', String.valueOf(this.settings.getTags().getString(tag)) + " &6tag!"));
        } else {
          String tag = args[1];
          boolean real = false;
          for (String s : this.settings.getTags().getKeys(false)) {
            if (s.toLowerCase().equals(tag.toLowerCase()))
              real = true; 
          } 
          if (!real) {
            give.sendMessage(ChatColor.GOLD + "Tag not found!");
            return false;
          } 
          Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "manuaddp " + give.getName() + " Tag." + tag);
          give.sendMessage(ChatColor.GOLD + "You have been given the " + ChatColor.translateAlternateColorCodes('&', String.valueOf(this.settings.getTags().getString(tag)) + " &6tag!"));
        } 
      }  
    return false;
  }
}
