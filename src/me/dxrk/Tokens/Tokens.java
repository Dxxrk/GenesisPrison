package me.dxrk.Tokens;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;

public class Tokens {
  static Tokens instance = new Tokens();
  
  public static Inventory etShop = null;
  
  public static Tokens getInstance() {
    return instance;
  }
  
  SettingsManager settings = SettingsManager.getInstance();
  
  String prefix = ChatColor.translateAlternateColorCodes('&', "&7&k&li&b&lTokens&7&l&ki&r &7➤ ");
  
  public double getBalance(Player p) {
    return Main.econ.getBalance((OfflinePlayer)p);
  }
  
  public boolean hasTokens(Player p, int tokens) {
    if (getTokens(p) < tokens)
      return false; 
    return true;
  }
  
  public int getTokens(Player p) {
    if (!this.settings.getET().contains(p.getUniqueId().toString()))
      return 0; 
    return this.settings.getET().getInt(p.getUniqueId().toString());
  }
  
  public void setTokens(Player p, int tokens) {
    this.settings.getET().set(p.getUniqueId().toString(), Integer.valueOf(tokens));
    p.sendMessage(String.valueOf(this.prefix) + "Tokens set to " + tokens);
    this.settings.saveEtFile();
    p.getScoreboard().getTeam("tokens").setSuffix(PickaxeLevel.c("&e"+Main.formatAmt(getTokens(p))));
  }
  
  public void addTokens(Player p, int tokens) {
    this.settings.getET().set(p.getUniqueId().toString(), Integer.valueOf(getTokens(p) + tokens));
    this.settings.saveEtFile();
    p.getScoreboard().getTeam("tokens").setSuffix(PickaxeLevel.c("&e"+Main.formatAmt(getTokens(p))));
  }
  
  public void takeTokens(Player p, int tokens) {
    this.settings.getET().set(p.getUniqueId().toString(), Integer.valueOf(getTokens(p) - tokens));
    this.settings.saveEtFile();
    p.sendMessage(String.valueOf(this.prefix) + "You now have " + getTokens(p) + " tokens.");
    p.getScoreboard().getTeam("tokens").setSuffix(PickaxeLevel.c("&e"+Main.formatAmt(getTokens(p))));
  }
  
  public void sendTokens(Player sender, Player reciever, int tokens) {
    if (getTokens(sender) < tokens) {
      sender.sendMessage(String.valueOf(this.prefix) + "You do not have enough tokens!");
      return;
    } 
    sender.sendMessage(String.valueOf(this.prefix) + "You have sent " + reciever.getName() + " " + tokens + " tokens.");
    reciever.sendMessage(String.valueOf(this.prefix) + sender.getName() + " has sent you " + tokens + " tokens.");
    addTokens(reciever, tokens);
    takeTokens(sender, tokens);
  }
  
  public void openEtShop(Player p) {
    if (this.pages.isEmpty())
      loadEtShop(); 
    p.openInventory(this.pages.get(0));
  }
  
  public ArrayList<Inventory> pages = new ArrayList<Inventory>();
  
  private ItemStack pageSwitch(String direction, int page) {
    ItemStack i = new ItemStack(Material.PAPER, 1);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.AQUA + direction);
    List<String> lore = new ArrayList<String>();
    lore.add(ChatColor.BLACK + "" + page);
    im.setLore(lore);
    i.setItemMeta(im);
    return i;
  }
  
  public void loadEtShop() {
    this.pages.clear();
    int page = 0;
    for (String s : this.settings.getTokenShop().getKeys(false)) {
      Inventory i = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Token Shop Page " + page);
      for (int x = 0; x < 45; x++) {
        if (this.settings.getTokenShop().contains(String.valueOf(s) + "." + x + ".item"))
          i.setItem(x, this.settings.getTokenShop().getItemStack(String.valueOf(s) + "." + x + ".item")); 
      }
      i.setItem(45, pageSwitch("Previous Page", page - 1));
      i.setItem(46, new ItemStack(Material.BARRIER));
      i.setItem(47, new ItemStack(Material.BARRIER));
      i.setItem(48, new ItemStack(Material.BARRIER));
      i.setItem(49, new ItemStack(Material.BARRIER));
      i.setItem(50, new ItemStack(Material.BARRIER));
      i.setItem(51, new ItemStack(Material.BARRIER));
      i.setItem(52, new ItemStack(Material.BARRIER));
      i.setItem(53, pageSwitch("Next Page", page + 1));
      this.pages.add(i);
      page++;
    } 
  }
  
  public void loadEditableShop(Player p, String name) {
    Inventory i = Bukkit.createInventory(null, 54, ChatColor.AQUA + "Edit Shop: " + name);
    if (this.settings.getTokenShop().contains(name))
      for (int x = 0; x < i.getSize(); x++) {
        if (this.settings.getTokenShop().contains(String.valueOf(name) + "." + x))
          i.setItem(x, this.settings.getTokenShop().getItemStack(String.valueOf(name) + "." + x + ".item")); 
      }  
    p.openInventory(i);
  }
  
  public void refreshConfig() {
    for (String s : this.settings.getTokenShop().getKeys(false)) {
      for (int x = 0; x < 45; x++) {
        if (this.settings.getTokenShop().contains(String.valueOf(s) + "." + x)) {
          ItemStack i = this.settings.getTokenShop().getItemStack(String.valueOf(s) + "." + x);
          this.settings.getTokenShop().set(String.valueOf(s) + "." + x + ".item", i);
          this.settings.saveTokenShop();
        } 
      } 
    } 
    this.settings.saveTokenShop();
  }
  
  public void saveTokenShop(Inventory i) {
    String page = ChatColor.stripColor(i.getName()).replace("Edit Shop:", "").replace(" ", "");
    for (int x = 0; x < 45; x++) {
      if (i.getItem(x) == null) {
        this.settings.getTokenShop().set(String.valueOf(page) + "." + x, null);
      } else {
        this.settings.getTokenShop().set(String.valueOf(page) + "." + x + ".item", i.getItem(x));
      } 
      this.settings.saveTokenShop();
      loadEtShop();
    } 
  }
}
