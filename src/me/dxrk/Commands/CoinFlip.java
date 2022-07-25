package me.dxrk.Commands;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

public class CoinFlip implements Listener, CommandExecutor {
  public static Inventory coinFlipInv;
  
  SettingsManager settings = SettingsManager.getInstance();
  
  public static HashMap<String, Double> games = new HashMap<String, Double>();
  
  public static HashMap<String, String> side = new HashMap<String, String>();
  
  public ItemStack coinHead(OfflinePlayer p, Double bet, String side) {
    ItemStack i = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    SkullMeta im = (SkullMeta)i.getItemMeta();
    im.setOwner(p.getName());
    im.setDisplayName(ChatColor.LIGHT_PURPLE + "Bet: " + ChatColor.WHITE + format(bet.doubleValue()));
    List<String> lore = new ArrayList<String>();
    lore.add("");
    lore.add(ChatColor.AQUA + "Better: " + ChatColor.WHITE + p.getName());
    lore.add("");
    lore.add(ChatColor.AQUA + "Side: " + ChatColor.WHITE + side);
    im.setLore(lore);
    i.setItemMeta((ItemMeta)im);
    return i;
  }
  
  public void updateInv() {
    if (coinFlipInv == null)
      loadCoinFlipInv(); 
    for (int xx = 9; xx < 45; xx++)
      coinFlipInv.setItem(xx, null); 
    int x = 9;
    for (String s : games.keySet()) {
      if (x < 45) {
        coinFlipInv.setItem(x, coinHead(Bukkit.getOfflinePlayer(s), games.get(s), side.get(s)));
        x++;
      } 
    } 
    coinFlipInv.setItem(0, fakeHead(100000.0D));
    coinFlipInv.setItem(1, fakeHead(1.0E7D));
    coinFlipInv.setItem(2, fakeHead(1.0E9D));
    coinFlipInv.setItem(3, fakeHead(1.0E10D));
    coinFlipInv.setItem(4, fakeHead(1.0E11D));
    coinFlipInv.setItem(5, fakeHead(1.0E12D));
    coinFlipInv.setItem(6, fakeHead(1.0E13D));
    coinFlipInv.setItem(7, fakeHead(1.0E14D));
    coinFlipInv.setItem(8, fakeHead(1.0E15D));
    saveGamesToFile();
  }
  
  public static HashMap<Player, Boolean> winning = new HashMap<Player, Boolean>();
  
  @EventHandler
  public void onTouch(PlayerInteractEntityEvent e) {
    if (e.getRightClicked() instanceof Player) {
      Player clicked = (Player)e.getRightClicked();
      if (clicked.getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&e&lCoin&a&lFlip"))) {
        Player p = e.getPlayer();
        if (coinFlipInv == null)
          updateInv(); 
        p.openInventory(coinFlipInv);
      } 
    } 
  }
  
  public ItemStack fakeHead(double amnt) {
    ItemStack i = new ItemStack(Material.SKULL_ITEM, 1);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.LIGHT_PURPLE + "Bet: " + format(amnt));
    List<String> lore = new ArrayList<String>();
    lore.add(ChatColor.BLACK +""+ amnt);
    lore.add(ChatColor.AQUA + "Better: " + ChatColor.WHITE + "Epsilon");
    lore.add("");
    Random r = new Random();
    int r1 = r.nextInt(2);
    if (r1 == 0) {
      lore.add(ChatColor.AQUA + "Side: " + ChatColor.WHITE + "HEADS");
    } else {
      lore.add(ChatColor.AQUA + "Side: " + ChatColor.WHITE + "Tails");
    } 
    im.setLore(lore);
    i.setItemMeta(im);
    return i;
  }
  
  public void tryBetFake(Player p, ItemStack i) {
    if (i == null)
      return; 
    if (i.getType() != Material.SKULL_ITEM)
      return; 
    double amnt = Double.parseDouble(ChatColor.stripColor(i.getItemMeta().getLore().get(0)));
    if (Main.econ.getBalance((OfflinePlayer)p) < amnt) {
      p.sendMessage(ChatColor.RED + "You do not have enough money!");
      return;
    } 
    Main.econ.withdrawPlayer((OfflinePlayer)p, amnt);
    Random r = new Random();
    if (r.nextInt(100) < 40) {
      p.sendMessage(ChatColor.GREEN + "You won!");
      Main.econ.depositPlayer((OfflinePlayer)p, amnt * 2.0D);
      return;
    } 
    p.sendMessage(ChatColor.RED + "You lost!");
  }
  
  public void tryBet(Player p, ItemStack i) {
    String otherSide;
    if (i == null)
      return; 
    if (i.getType() != Material.SKULL_ITEM)
      return; 
    SkullMeta im = (SkullMeta)i.getItemMeta();
    String otherPlayer = im.getOwner();
    if (otherPlayer.equals(p.getName())) {
      p.sendMessage(ChatColor.RED + "Error: " + ChatColor.GRAY + "You can not pick your own bet!");
      return;
    } 
    double amnt = ((Double)games.get(otherPlayer)).doubleValue();
    String sidee = side.get(otherPlayer);
    if (sidee.equalsIgnoreCase("Heads")) {
      otherSide = "Tails";
    } else {
      otherSide = "Heads";
    } 
    if (Main.econ.getBalance((OfflinePlayer)p) < amnt) {
      p.sendMessage(ChatColor.RED + "You do not have enough money!");
      return;
    } 
    Main.econ.withdrawPlayer((OfflinePlayer)p, amnt);
    winning.put(p, Boolean.valueOf(true));
    Random r = new Random();
    int x = r.nextInt(3) + 10;
    p.openInventory(flippingInv(otherPlayer, sidee));
    flipInvs(x, p, otherSide, otherPlayer, sidee, amnt);
    games.remove(otherPlayer);
    side.remove(otherPlayer);
    updateInv();
  }
  
  public void saveGamesToFile() {
    try {
      for (String s : this.settings.getCoinFlip().getKeys(false))
        this.settings.getCoinFlip().set(s, null); 
    } catch (Exception exception) {}
    try {
      for (String s : games.keySet()) {
        this.settings.getCoinFlip().set(String.valueOf(s) + ".bet", games.get(s));
        this.settings.getCoinFlip().set(String.valueOf(s) + ".side", side.get(s));
      } 
    } catch (Exception exception) {}
    this.settings.saveCoinFlip();
  }
  
  public void flipInvs(final int x, final Player p, final String p1side, final String otherPlayer, final String p2side, final double amnt) {
    if (x == 0) {
      if (((Boolean)winning.get(p)).booleanValue()) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "&bCongrats! You won!"));
        Main.econ.depositPlayer((OfflinePlayer)p, amnt * 2.0D);
        if (Bukkit.getPlayer(otherPlayer) != null) {
          Player op = Bukkit.getPlayer(otherPlayer);
          op.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "&dSorry! Someone played your coinflip and you lost"));
        } 
      } else {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "&dSorry! You lost!"));
        Main.econ.depositPlayer(Bukkit.getOfflinePlayer(otherPlayer), amnt * 2.0D);
        if (Bukkit.getPlayer(otherPlayer) != null) {
          Player op = Bukkit.getPlayer(otherPlayer);
          op.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "&bCongrats! Someone played your coinflip and you won!"));
        } 
      } 
      winning.remove(p);
      updateInv();
    } else {
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
            public void run() {
              if (((Boolean)CoinFlip.winning.get(p)).booleanValue()) {
                CoinFlip.winning.put(p, Boolean.valueOf(false));
                p.openInventory(CoinFlip.this.flippingInv(otherPlayer, p2side));
              } else {
                CoinFlip.winning.put(p, Boolean.valueOf(true));
                p.openInventory(CoinFlip.this.flippingInv(p.getName(), p1side));
              } 
              CoinFlip.this.flipInvs(x - 1, p, p1side, otherPlayer, p2side, amnt);
            }
          },(x * 2));
    } 
  }
  
  public Inventory flippingInv(String p, String side) {
    Inventory i = Bukkit.createInventory(null, 45, ChatColor.AQUA + "Flipping!");
    for (int x = 0; x < i.getSize(); x++) {
      ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1);
      ItemMeta itemMeta = item.getItemMeta();
      if (side.toUpperCase().equals("HEADS")) {
        itemMeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Heads");
        item.setDurability((short)2);
      } else {
        itemMeta.setDisplayName(ChatColor.AQUA + "Tails");
        item.setDurability((short)3);
      } 
      item.setItemMeta(itemMeta);
      i.setItem(x, item);
    } 
    ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
    SkullMeta im = (SkullMeta)head.getItemMeta();
    im.setOwner(p);
    head.setItemMeta((ItemMeta)im);
    i.setItem(22, head);
    return i;
  }
  
  String prefix = ChatColor.translateAlternateColorCodes('&', "&7&l[&d&lCoin&b&lFlip&7&l] &f");
  
  public void createBet(Player p, double bet, String sidee) {
    games.put(p.getName(), Double.valueOf(bet));
    side.put(p.getName(), sidee);
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "You have created a bet for &b" + sidee + " &ffor &b$" + format(bet)));
    updateInv();
  }
  
  private ItemStack sign() {
    ItemStack i = new ItemStack(Material.SIGN, 1);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.AQUA + "Want to bet?");
    List<String> lore = new ArrayList<String>();
    lore.add("");
    lore.add(ChatColor.GRAY + "/Coinflip Bet <Amount> <Heads|Tails>");
    im.setLore(lore);
    i.setItemMeta(im);
    return i;
  }
  
  public void loadCoinFlipInv() {
    try {
      for (String s : this.settings.getCoinFlip().getKeys(false)) {
        games.put(s, Double.valueOf(this.settings.getCoinFlip().getDouble(String.valueOf(s) + ".bet")));
        side.put(s, this.settings.getCoinFlip().getString(String.valueOf(s) + ".side"));
        this.settings.getCoinFlip().set(s, null);
      } 
    } catch (Exception exception) {}
    coinFlipInv = Bukkit.createInventory(null, 54, ChatColor.AQUA +""+ ChatColor.BOLD + "Coin" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Flip");
    coinFlipInv.setItem(49, sign());
  }
  
  public void sendHelpMsg(Player p) {
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[][][][][][][][][][][&dCoin&bFlip&7][][][][][][][][][]"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/&dCoinFlip &bBet <Amount> <Heads|Tails>"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/&dCoinFlip &bView"));
  }
  
  public boolean isDouble(String s) {
    try {
      double d = Double.parseDouble(s);
      if (d < 0.0D)
        return false; 
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getInventory().getName().equals(ChatColor.AQUA + "Flipping!"))
      e.setCancelled(true); 
    if (e.getInventory().getName().equals(ChatColor.AQUA +""+ ChatColor.BOLD + "Coin" + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + "Flip")) {
      e.setCancelled(true);
      if (e.getCurrentItem() != null) {
        if (e.getRawSlot() < 9) {
          tryBetFake((Player)e.getWhoClicked(), e.getCurrentItem());
          return;
        } 
        if (e.getCurrentItem().getType().equals(Material.SKULL_ITEM))
          tryBet((Player)e.getWhoClicked(), e.getCurrentItem()); 
      } 
    } 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("CoinFlip")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      if (args.length == 0) {
        sendHelpMsg(p);
        return false;
      } 
      if (args[0].equalsIgnoreCase("bet")) {
        if (args.length != 3) {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + "Error: &7/CoinFlip Bet <Amount> <Heads|Tails>"));
          return false;
        } 
        if (!isDouble(args[1])) {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + "Error: &7Not a valid amount"));
          return false;
        } 
        if (games.containsKey(p.getName())) {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + "Error: &7You already have a game up"));
          return false;
        } 
        double amnt = Double.parseDouble(args[1]);
        if (Main.econ.getBalance((OfflinePlayer)p) < amnt) {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + "Error: &7You do not have enough money"));
          return false;
        } 
        if (args[2].equalsIgnoreCase("Heads") || args[2].equalsIgnoreCase("Tails")) {
          createBet(p, amnt, args[2].toUpperCase());
          Main.econ.withdrawPlayer((OfflinePlayer)p, amnt);
        } else {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ChatColor.RED + "Error: &7Not a valid side"));
        } 
        return false;
      } 
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("View")) {
          if (coinFlipInv == null)
            updateInv(); 
          p.openInventory(coinFlipInv);
          return true;
        } 
        sendHelpMsg(p);
      } else {
        sendHelpMsg(p);
      } 
    } 
    return false;
  }
  
  public String format(double amt) {
    if (amt >= 1.0E15D)
      return String.format("%.1f Quad", new Object[] { Double.valueOf(amt / 1.0E15D) }); 
    if (amt >= 1.0E12D)
      return String.format("%.1f Tril", new Object[] { Double.valueOf(amt / 1.0E12D) }); 
    if (amt >= 1.0E9D)
      return String.format("%.1f Bil", new Object[] { Double.valueOf(amt / 1.0E9D) }); 
    if (amt >= 1000000.0D)
      return String.format("%.1f Mil", new Object[] { Double.valueOf(amt / 1000000.0D) }); 
    return NumberFormat.getNumberInstance(Locale.US).format(amt);
  }
}
