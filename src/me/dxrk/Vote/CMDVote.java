package me.dxrk.Vote;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import me.dxrk.Events.LocksmithHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import mkremins.fanciful.FancyMessage;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class CMDVote implements Listener, CommandExecutor {

  Methods m = Methods.getInstance();

  public void openVotingInv(Player p) {
    Inventory i = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.AQUA + "Vote!");
    i.setItem(0, voteItem(p, "Mc-lists"));
    i.setItem(1, voteItem(p, "Mineservers"));
    i.setItem(2, voteItem(p, "topG"));
    i.setItem(3, voteItem(p, "Minecraft-mp"));
    i.setItem(4, voteItem(p, "MinecraftServers"));
    p.openInventory(i);
  }
  
  String prefix = ChatColor.translateAlternateColorCodes('&', "&7&l[&b&lVote&d&lParty&7&l] &f");
  
  public void updateNPC() {
    orderTop();
    CitizensAPI.getNPCRegistry().getById(99).setName(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(0))).getName());
    CitizensAPI.getNPCRegistry().getById(100).setName(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(1))).getName());
    CitizensAPI.getNPCRegistry().getById(101).setName(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(2))).getName());
  }
  
  public void giveItem(Player p, ItemStack i) {
    if (i.getItemMeta().hasDisplayName()) {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + "You have been given &b" + i.getItemMeta().getDisplayName()));
    } else {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + "You have been given &b" + i.getAmount() + " " + i.getType().toString().toLowerCase() + "(s)"));
    } 
    if (i.getType().equals(Material.EMERALD)) {
      double base = Double.parseDouble(getInt(ChatColor.stripColor(i.getItemMeta().getDisplayName())));
      if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("100 Trillion")))
    	  base = 1.0E14D;
      if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("10 Trillion")))
          base = 10.0e12D; 
      if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("250 Trillion")))
        base = 250.0e12D; 
      if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("500 Trillion")))
          base = 500.0E12D; 
      if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("1 Quadrillion")))
          base = 1.0E15D; 
      if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("1 Trillion")))
        base = 1.0E12D; 
      if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("750 Trillion")))
          base = 750.0E12D; 
      Main.econ.depositPlayer(p, base);
    } else if (i.getType().equals(Material.PRISMARINE_CRYSTALS)) {
      String base = getInt(i.getItemMeta().getDisplayName());
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tokens add " + p.getName() + " " + base);
    } else {
      p.getInventory().addItem(i);
    } 
  }
  
  public String getInt(String s) {
    StringBuilder sb = new StringBuilder();
    byte b;
    int i;
    char[] arrayOfChar;
    for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
      char c = arrayOfChar[b];
      if (isInt(c))
        sb.append(c); 
      b++;
    } 
    return sb.toString();
  }
  
  public boolean isInt(char c) {
    try {
      String str = String.valueOf(c);
      int i = Integer.parseInt(str);
      return i >= 1;
    } catch (NumberFormatException e) {
      return false;
    } 
  }
  
  @EventHandler
  public void onInvClick(InventoryClickEvent e) {
    if (e.getInventory().getName().equals(ChatColor.AQUA + "Vote!")) {
      e.setCancelled(true);
      if (e.getRawSlot() < 9 && 
        e.getCurrentItem() != null) {
        if (e.getCurrentItem().getDurability() == 14) {
          e.getWhoClicked().sendMessage(ChatColor.RED + "You can not " + 
              " yet!");
          openVotingInv((Player)e.getWhoClicked());
          return;
        } 
        if (e.getCurrentItem().getDurability() == 5) {
          Player p = (Player)e.getWhoClicked();
          p.sendMessage("");
          FancyMessage vote = (new FancyMessage("✔      ")).color(ChatColor.AQUA).style(ChatColor.BOLD);
          vote.then("Click me to vote").color(ChatColor.AQUA).style(ChatColor.UNDERLINE).tooltip(ChatColor.GRAY + "Click here.");
          if (e.getRawSlot() == 0)
            vote.link("https://mc-lists.org/server-genesis-network.2622/vote"); 
          if (e.getRawSlot() == 1)
            vote.link("https://mineservers.com/server/WaazZQi5/vote"); 
          if (e.getRawSlot() == 2)
            vote.link("https://topg.org/minecraft-servers/server-615534"); 
          if (e.getRawSlot() == 3)
            vote.link("https://minecraft-mp.com/server/268257/vote/");
          if (e.getRawSlot() == 4)
            vote.link("https://minecraftservers.org/vote/594587"); 
          vote.then("      ✔").color(ChatColor.AQUA).style(ChatColor.BOLD);
          vote.send(p);
          p.sendMessage("");
          p.closeInventory();
        } 
      } 
    } 
  }
  
  public ItemStack voteItem(Player p, String serviceName) {
    ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
    ItemMeta im = i.getItemMeta();
    if (canVote(p, serviceName)) {
      im.setDisplayName(ChatColor.GREEN + "Click to vote!");
    } else {
      im.setDisplayName(ChatColor.RED + "You can not vote yet!");
      i.setDurability((short)14);
      ArrayList<String> lore = new ArrayList<>();
      lore.add(ChatColor.GRAY + "Vote again in:");
      lore.add(ChatColor.RED + formatTime(getCooldownTime(p, serviceName)));
      im.setLore(lore);
    } 
    i.setItemMeta(im);
    return i;
  }
  
  public boolean canVote(Player p, String serviceName) {
    if (!this.settings.getVote().contains(p.getUniqueId().toString()))
      return true; 
    long time = this.settings.getVote().getLong(p.getUniqueId().toString() + "." + serviceName);
    long currentTime = (new Date()).getTime();
    return currentTime >= time;
  }
  
  public long getCooldownTime(Player p, String serviceName) {
    long time = this.settings.getVote().getLong(p.getUniqueId().toString() + "." + serviceName);
    long currentTime = (new Date()).getTime();
    return time - currentTime;
  }
  
  public String formatTime(long base) {
    int hours = 0;
    int minutes = 0;
    long seconds = 0L;
    long ms = base;
    while (ms > 1000L) {
      seconds++;
      ms -= 1000L;
    } 
    while (seconds > 60L) {
      minutes++;
      seconds -= 60L;
    } 
    while (minutes > 60) {
      minutes -= 60;
      hours++;
    } 
    return hours + " Hours " + minutes + " Minutes" + " and " + Math.round((float)seconds) + " Seconds";
  }
  
  SettingsManager settings = SettingsManager.getInstance();
  
  public static HashMap<String, Integer> allMiners = new HashMap<>();
  
  public static List<String> top5 = new ArrayList<>();
  
  public static HashMap<String, Integer> points = new HashMap<>();
  
  public void updatepoints() {
    points.clear();
    for (String s : this.settings.getVote().getKeys(false)) {
      if (!s.contains("Server") && !s.contains("VoteShop"))
        points.put(s, this.settings.getVote().getInt(s + ".Votes"));
    } 
  }
  
  
  public static CMDVote instance = new CMDVote();
  
  public static CMDVote getInstance() {
	  return instance;
  }
  
  
  
public void orderTop() {
    updatepoints();
    top5.clear();
    Object[] a = points.entrySet().toArray();
    Arrays.sort(a,
            new Comparator() {
              public int compare(Object o1, Object o2) {
                return ((Integer)((Map.Entry<?, ?>)o2).getValue()).compareTo(
                        (Integer)((Map.Entry<?, ?>)o1).getValue());
              }
            });

    byte b;
    int i;
    Object[] arrayOfObject1;
    for (i = (arrayOfObject1 = a).length, b = 0; b < i; ) {
      Object e = arrayOfObject1[b];
      top5.add((String)((Map.Entry<?, ?>)e).getKey());
      b++;
    } 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("Vote")) {
      Player p = (Player)sender;
      if (args.length == 0) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bstore.mcgenesis.net/vote"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dYou currently have &7&l[&b" + getVotes(p) + "&7&l]&d votes!"));
      }
    } else if(cmd.getName().equalsIgnoreCase("voteparty")) {
    	if(sender.hasPermission("voteparty.start")) {
    	voteParty();
    	}
    }
    return false;
  }
  
  public int getVotes(Player p) {
    return this.settings.getVote().getInt(p.getUniqueId().toString() + ".Votes");
  }
  
  public int getVotes(OfflinePlayer p) {
    return this.settings.getVote().getInt(p.getUniqueId().toString() + ".Votes");
  }
  
  public static ArrayList<ItemStack> rewards = new ArrayList<>();
  
  Random r = new Random();
  
  int rand = 0;
  
  private ItemStack randomItem(ArrayList<ItemStack> list) {
    int x = this.r.nextInt(list.size());
    if (x == this.rand)
      if (x + 2 > list.size()) {
        x--;
      } else {
        x++;
      }  
    this.rand = x;
    return list.get(x);
  }
  
  public void randomRewards(int delay) {
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
      for (Player p : Bukkit.getOnlinePlayers())
        CMDVote.this.giveItem(p, CMDVote.this.randomItem(CMDVote.rewards));
    },(delay * 10L));
  }
  
  public void loadChests() {
    Chest c1 = (Chest)Bukkit.getWorld("Prison").getBlockAt(new Location(Bukkit.getWorld("Prison"), 1312.0D, 1.0D, 701.0D)).getState();
    byte b;
    int i;
    ItemStack[] arrayOfItemStack;
    for (i = (arrayOfItemStack = c1.getInventory().getContents()).length, b = 0; b < i; ) {
      ItemStack itemStack = arrayOfItemStack[b];
      if (itemStack != null)
        rewards.add(itemStack); 
      b++;
    } 
  }
  
  public boolean giveReward(int i) {
    return this.r.nextInt(i) == 1;
  }
  

  
  public void voteParty() {
    if (rewards.size() == 0)
      loadChests();
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost Sell VoteParty 2.0 900");
    for(Player p : Bukkit.getOnlinePlayers()){
    LocksmithHandler.getInstance().addKey(p, "alpha", 1);
    LocksmithHandler.getInstance().addKey(p, "beta", 1);
    LocksmithHandler.getInstance().addKey(p, "omega", 1);
    LocksmithHandler.getInstance().addKey(p, "seasonal", 1);
    LocksmithHandler.getInstance().addKey(p, "community", 1);
    }
  }

  private String getTodayDate() {
    LocalDate time = java.time.LocalDate.now();

    return time.toString();
  }
  
  @EventHandler
  public void onPlayerVote(VotifierEvent e) {
    Vote v = e.getVote();
    Player p = Bukkit.getServer().getPlayer(v.getUsername());
    int servervotes = this.settings.getVote().getInt("ServerVotes");
    this.settings.getVote().set("ServerVotes", servervotes - 1);
    assert p != null;
    int totalvotes = this.settings.getVote().getInt(p.getUniqueId().toString() + ".Votes");
    int newtotalvotes = totalvotes + 1;
    long time = (new Date()).getTime() + 86400000L;
    this.settings.getVote().set(p.getUniqueId().toString() + ".Votes", newtotalvotes);
    CMDVoteShop.addCoupon(p, 0.05);
    this.settings.getVote().set(p.getUniqueId().toString() + "." + v.getServiceName(), time);
    this.settings.saveVote();
    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tokens add " + p.getName() + " 50000");
    Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cratekey " + p.getName()+" vote");
    this.settings.getVote().set(p.getUniqueId().toString()+".HasVoted", getTodayDate());
    this.settings.saveVote();
    p.sendMessage(m.c("&f&lVote &8| &b+1 Vote Key &e+⛀50,000"));
    if (servervotes < 2) {
      this.settings.getVote().set("ServerVotes", 30);
      this.settings.saveVote();
      voteParty();
    }
  }
}
