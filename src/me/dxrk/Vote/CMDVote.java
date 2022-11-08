package me.dxrk.Vote;

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
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "You have been given &b" + i.getItemMeta().getDisplayName()));
    } else {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "You have been given &b" + i.getAmount() + " " + i.getType().toString().toLowerCase() + "(s)"));
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
      Main.econ.depositPlayer((OfflinePlayer)p, base);
    } else if (i.getType().equals(Material.PRISMARINE_CRYSTALS)) {
      String base = getInt(i.getItemMeta().getDisplayName());
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "tokens add " + p.getName() + " " + base);
    } else {
      p.getInventory().addItem(new ItemStack[] { i });
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
      if (i < 1)
        return false; 
      return true;
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
          FancyMessage vote = (new FancyMessage("✔      ")).color(ChatColor.AQUA).style(new ChatColor[] { ChatColor.BOLD });
          vote.then("Click me to vote").color(ChatColor.AQUA).style(new ChatColor[] { ChatColor.UNDERLINE }).tooltip(ChatColor.GRAY + "Click here.");
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
          vote.then("      ✔").color(ChatColor.AQUA).style(new ChatColor[] { ChatColor.BOLD });
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
      ArrayList<String> lore = new ArrayList<String>();
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
    long time = this.settings.getVote().getLong(String.valueOf(p.getUniqueId().toString()) + "." + serviceName);
    long currentTime = (new Date()).getTime();
    if (currentTime >= time)
      return true; 
    return false;
  }
  
  public long getCooldownTime(Player p, String serviceName) {
    long time = this.settings.getVote().getLong(String.valueOf(p.getUniqueId().toString()) + "." + serviceName);
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
    return String.valueOf(hours) + " Hours " + minutes + " Minutes" + " and " + Math.round((float)seconds) + " Seconds";
  }
  
  SettingsManager settings = SettingsManager.getInstance();
  
  public static HashMap<String, Integer> allMiners = new HashMap<String, Integer>();
  
  public static List<String> top5 = new ArrayList<String>();
  
  public static HashMap<String, Integer> points = new HashMap<String, Integer>();
  
  public void updatepoints() {
    points.clear();
    for (String s : this.settings.getVote().getKeys(false)) {
      if (!s.contains("Server") && !s.contains("VoteShop"))
        points.put(s, Integer.valueOf(this.settings.getVote().getInt(String.valueOf(s) + ".Votes"))); 
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
    Arrays.sort(a, new Comparator() {
          public int compare(Object o1, Object o2) {
            return ((Integer)((Map.Entry)o2).getValue()).compareTo(
                (Integer)((Map.Entry)o1).getValue());
          }
        });
    int xx = 0;
    byte b;
    int i;
    Object[] arrayOfObject1;
    for (i = (arrayOfObject1 = a).length, b = 0; b < i; ) {
      Object e = arrayOfObject1[b];
      if (xx > 4)
        return; 
      top5.add((String)((Map.Entry)e).getKey());
      xx++;
      b++;
    } 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("Vote")) {
      Player p = (Player)sender;
      if (args.length == 0) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[&b&l✔&7&l&m]&m----------&7&l[&d&lVoting&7&l&m]&7&m----------&7&l&m[&b&l✔&7&l]"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/&bVote Links &7- &bVote for the server!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/&bVote Rewards &7- &bView voting rewards!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7/&bVote Top &7- &bList top 5 voters!"));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dYou currently have &7&l[&b" + getVotes(p) + "&7&l]&d votes!"));
      } 
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("Links"))
          openVotingInv(p); 
        if (args[0].equalsIgnoreCase("partyyy") && 
          p.isOp())
          voteParty(); 
        if (args[0].equalsIgnoreCase("Top")) {
          orderTop();
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[&b&l✔&7&l&m]&m----------&7&l[&d&lTop Voters&7&l&m]&7&m----------&7&l&m[&b&l✔&7&l]"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "1. &b" + Bukkit.getOfflinePlayer(UUID.fromString(top5.get(0))).getName() + "&7&l [&b" + getVotes(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(0)))) + "&7&l]"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "2. &b" + Bukkit.getOfflinePlayer(UUID.fromString(top5.get(1))).getName() + "&7&l [&b" + getVotes(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(1)))) + "&7&l]"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "3. &b" + Bukkit.getOfflinePlayer(UUID.fromString(top5.get(2))).getName() + "&7&l [&b" + getVotes(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(2)))) + "&7&l]"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "4. &b" + Bukkit.getOfflinePlayer(UUID.fromString(top5.get(3))).getName() + "&7&l [&b" + getVotes(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(3)))) + "&7&l]"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "5. &b" + Bukkit.getOfflinePlayer(UUID.fromString(top5.get(4))).getName() + "&7&l [&b" + getVotes(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(4)))) + "&7&l]"));
        } 
        if (args[0].equalsIgnoreCase("Rewards")) {
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[&b&l✔&7&l&m]&m----------&7&l[&d&lRewards&7&l&m]&7&m----------&7&l&m[&b&l✔&7&l]"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&bRandoms&7]"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b10&7] &a&l$50 Billion"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b25&7] &a&l500 Tokens"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b100&7] &a&l$1 Trillion"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b750&7] &a&l$1 Quadrillion"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b1000&7] &f&lPolis Rune"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b2500&7] &a&lCaptain Rank"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b5000&7] &a&l/autosell"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b10000&7] &a&l$10 Buycraft Voucher"));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7[&b1&7/&b25000&7] &d&lOblivion Rune"));
        } 
      } 
    } else if(cmd.getName().equalsIgnoreCase("voteparty")) {
    	if(sender.hasPermission("voteparty.start")) {
    	voteParty();
    	}
    }
    return false;
  }
  
  public int getVotes(Player p) {
    return this.settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votes");
  }
  
  public int getVotes(OfflinePlayer p) {
    return this.settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votes");
  }
  
  public static ArrayList<ItemStack> rewards = new ArrayList<ItemStack>();
  
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
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, new Runnable() {
          public void run() {
            for (Player p : Bukkit.getOnlinePlayers())
              CMDVote.this.giveItem(p, CMDVote.this.randomItem(CMDVote.rewards));
          }
        },(delay * 10));
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
    if (this.r.nextInt(i) == 1)
      return true; 
    return false;
  }
  
  public void bcSpecial(Player p, String msg) {
    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&7&l[&b&lLucky&d&lVote&7&l] &b" + p.getName() + "&f was lucky and found &a&l" + msg));
  }
  
  public void tryRewards(Player p) {
    if (giveReward(10)) {
      Main.econ.depositPlayer((OfflinePlayer)p, 100e12);
      bcSpecial(p, "$100 Trillion");
    } else if (giveReward(25)) {
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "et add " + p.getName() + " 500");
      bcSpecial(p, "500 Tokens");
    }  else if (giveReward(100)) {
    	Main.econ.depositPlayer((OfflinePlayer)p, 500e12);
      bcSpecial(p, "$500 Trillion");
    }   else if (giveReward(750)) {
    	Main.econ.depositPlayer((OfflinePlayer)p, 1e15);
      bcSpecial(p, "$1 Quadrillion");
    } else if (giveReward(1000)) {
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "cratekey Oblivion " + p.getName());
      bcSpecial(p, "a Oblivion Rune");
    } else if (giveReward(2500)) {
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "givebook " + p.getName() + " 3");
      bcSpecial(p, "Captain Rank!");
    } else if (giveReward(5000)) {
    	Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "givebook " + p.getName() + " 5");
      bcSpecial(p, "Ares Rank");
    } else if (giveReward(10000)) {
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "m " + p.getName() + " Screenshot this and wait for Staff");
      bcSpecial(p, "A $10 Buycraft voucher");
    } else if (giveReward(25000)) {
      Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "cratekey Olympus " + p.getName());
      bcSpecial(p, "an Olympus Rune");
    } 
  }
  
  public void voteParty() {
    if (rewards.size() == 0)
      loadChests(); 
    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + "&f&lVote Party Starting!!"));
    randomRewards(1);
    randomRewards(5);
    randomRewards(10);
    randomRewards(15);
    randomRewards(20);
    randomRewards(25);
    randomRewards(30);
    randomRewards(35);
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost Sell VoteParty 2.0 900");
    for(Player p : Bukkit.getOnlinePlayers()){
    LocksmithHandler.getInstance().addKey(p, "vote", 1);
    LocksmithHandler.getInstance().addKey(p, "midas", 1);
    LocksmithHandler.getInstance().addKey(p, "poseidon", 1);
    LocksmithHandler.getInstance().addKey(p, "hades", 1);
    LocksmithHandler.getInstance().addKey(p, "polis", 1);
    }
  }
  
  @EventHandler
  public void onPlayerVote(VotifierEvent e) {
    Vote v = e.getVote();
    Player p = Bukkit.getServer().getPlayer(v.getUsername());
    if (p == null)
      Bukkit.broadcastMessage(String.valueOf(e.getVote().getServiceName()) + " " + e.getVote().getTimeStamp()); 
    int servervotes = this.settings.getVote().getInt("ServerVotes");
    this.settings.getVote().set("ServerVotes", Integer.valueOf(servervotes - 1));
    int votepoints = this.settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votepoints");
    int totalvotes = this.settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votes");
    int newvps = votepoints +1;
    int newtotalvotes = totalvotes + 1;
    long time = (new Date()).getTime() + 86400000L;
    this.settings.getVote().set(String.valueOf(p.getUniqueId().toString()) + ".Votes", Integer.valueOf(newtotalvotes));
    this.settings.getVote().set(String.valueOf(p.getUniqueId().toString()) + ".Votepoints", Integer.valueOf(newvps));
    this.settings.getVote().set(String.valueOf(p.getUniqueId().toString()) + "." + v.getServiceName(), Long.valueOf(time));
    this.settings.saveVote();
    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "et add " + p.getName() + " 100");
    Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), "cratekey vote " + p.getName());
    String msg1 = ChatColor.translateAlternateColorCodes('&', "&d&l✔ &7Thank&b " + p.getName() + "&7 for voting! &b&l/Vote");
    String msg2 = ChatColor.translateAlternateColorCodes('&', "&d&l✔ &b" + (servervotes - 1) + " &7Votes till a vote party!");
    FancyMessage msg3 = (new FancyMessage("✔ Click Here To Vote ✔")).color(ChatColor.LIGHT_PURPLE).style(new ChatColor[] { ChatColor.BOLD }).command("/vote links");
    Bukkit.broadcastMessage(msg1);
    Bukkit.broadcastMessage(msg2);
    for (Player pp : Bukkit.getOnlinePlayers())
      msg3.send(pp); 
    tryRewards(p);
    this.settings.saveVote();
    if (servervotes < 2) {
      this.settings.getVote().set("ServerVotes", Integer.valueOf(30));
      this.settings.saveVote();
      voteParty();
      return;
    }
  }
}
