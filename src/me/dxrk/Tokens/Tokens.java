package me.dxrk.Tokens;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Tokens {
  static Tokens instance = new Tokens();
  
  public static Inventory etShop = null;
  
  public static Tokens getInstance() {
    return instance;
  }
  
  SettingsManager settings = SettingsManager.getInstance();
  Methods m = Methods.getInstance();

  String prefix = ChatColor.translateAlternateColorCodes('&', "&f&lTokens &8|&r ");
  
  public double getBalance(Player p) {
    return Main.econ.getBalance(p);
  }
  
  public boolean hasTokens(Player p, double tokens) {
    return getTokens(p) >= tokens;
  }
  
  public double getTokens(Player p) {
    if (!this.settings.getET().contains(p.getUniqueId().toString()))
      return 0; 
    return this.settings.getET().getInt(p.getUniqueId().toString());
  }
  
  public void setTokens(Player p, double tokens) {
    this.settings.getET().set(p.getUniqueId().toString(), tokens);
    p.sendMessage(this.prefix + "Tokens set to " + tokens);
    this.settings.saveEtFile();
    p.getScoreboard().getTeam("tokens").setSuffix(m.c("&e"+Main.formatAmt(getTokens(p))));
  }
  
  public void addTokens(Player p, double tokens) {
    this.settings.getET().set(p.getUniqueId().toString(), getTokens(p) + tokens);
    this.settings.saveEtFile();
    p.getScoreboard().getTeam("tokens").setSuffix(m.c("&e"+Main.formatAmt(getTokens(p))));
  }
  
  public void takeTokens(Player p, double tokens) {
    this.settings.getET().set(p.getUniqueId().toString(), getTokens(p) - tokens);
    this.settings.saveEtFile();
    p.getScoreboard().getTeam("tokens").setSuffix(m.c("&e"+Main.formatAmt(getTokens(p))));
  }
  
  public void sendTokens(Player sender, Player reciever, double tokens) {
    if (getTokens(sender) < tokens) {
      sender.sendMessage(this.prefix + "You do not have enough tokens!");
      return;
    } 
    sender.sendMessage(this.prefix + "&7You have sent &b" + reciever.getName() + " &e" + tokens + "⛀");
    reciever.sendMessage(this.prefix +"&b"+ sender.getName() + "&7 has sent you &e" + tokens + "⛀");
    addTokens(reciever, tokens);
    takeTokens(sender, tokens);
  }
  
  public void openEtShop(Player p) {
    if (this.pages.isEmpty())
      loadEtShop(); 
    p.openInventory(this.pages.get(0));
  }
  
  public ArrayList<Inventory> pages = new ArrayList<>();
  
  private ItemStack pageSwitch(String direction, int page) {
    ItemStack i = new ItemStack(Material.PAPER, 1);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.AQUA + direction);
    List<String> lore = new ArrayList<>();
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
        if (this.settings.getTokenShop().contains(s + "." + x + ".item"))
          i.setItem(x, this.settings.getTokenShop().getItemStack(s + "." + x + ".item"));
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
        if (this.settings.getTokenShop().contains(name + "." + x))
          i.setItem(x, this.settings.getTokenShop().getItemStack(name + "." + x + ".item"));
      }  
    p.openInventory(i);
  }
  
  public void refreshConfig() {
    for (String s : this.settings.getTokenShop().getKeys(false)) {
      for (int x = 0; x < 45; x++) {
        if (this.settings.getTokenShop().contains(s + "." + x)) {
          ItemStack i = this.settings.getTokenShop().getItemStack(s + "." + x);
          this.settings.getTokenShop().set(s + "." + x + ".item", i);
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
        this.settings.getTokenShop().set(page + "." + x, null);
      } else {
        this.settings.getTokenShop().set(page + "." + x + ".item", i.getItem(x));
      } 
      this.settings.saveTokenShop();
      loadEtShop();
    } 
  }
}
