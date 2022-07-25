package me.dxrk.Commands;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;

import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import net.md_5.bungee.api.ChatColor;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagList;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class BasicCommands implements CommandExecutor, Listener {
  static BasicCommands cmds;
  
  public static BasicCommands getInstance() {
    return cmds;
  }
  
  
  
  public static List<String> dropson = new ArrayList<String>();
  
  SettingsManager settings = SettingsManager.getInstance();
  
  public String getAPI(String api) {
    URL url = null;
    try {
      url = new URL(api);
    } catch (MalformedURLException e) {
      e.printStackTrace();
    } 
    String response = null;
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
      String s = null;
      while ((s = reader.readLine()) != null)
        response = s; 
    } catch (IOException e) {
      e.printStackTrace();
    } 
    return response;
  }
  
  public boolean isInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    } 
  }
  
  public boolean canBreak(Block b) {
    WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    ApplicableRegionSet set = wg.getRegionManager(b.getWorld()).getApplicableRegions(b.getLocation());
    if (set.allows(DefaultFlag.LIGHTER))
      return true; 
    return false;
  }
  
  public String helpMessage(String command, String desc) {
    String s = ChatColor.AQUA + "/" + command + ChatColor.GRAY + ChatColor.BOLD + " Â» " + ChatColor.BLUE + desc;
    return s;
  }
  
  public void sendHelp(Player p, int i) {
	  
	  if(i == 1) {
		  p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b--&7Page &b1/5&7--"));
	      p.sendMessage(ChatColor.GRAY + "Getting Started: Do /plot auto to claim a plot, which will also serve as your mine!");
	      p.sendMessage(ChatColor.AQUA + "Selling: Right click your pickaxe, if you're Ares rank or higher you get /autosell!");
	      p.sendMessage(ChatColor.GRAY + "Gameplay: Mine to get money to help you prestige as well as XP to level up your pickaxe!");
	      p.sendMessage(ChatColor.AQUA + "NPCs: There are NPCs around spawn that can help you on your journey");
	      p.sendMessage(ChatColor.GRAY + "LockSmith NPC: Collect your keys and /warp crates to open!");
	  }
	  
	  
    if (i == 2) {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b--&7Page &b2/5&7--"));
      p.sendMessage(ChatColor.GRAY + "/Pick - " + ChatColor.GRAY + "Open the Pickaxe Upgrade menu.");
      p.sendMessage(ChatColor.AQUA + "/Rankup - " + ChatColor.GRAY + "Spend money to get to the next rank!");
      p.sendMessage(ChatColor.GRAY + "/Chat - " + ChatColor.GRAY + "Allows you to change chat settings so that you only see the things you want to see!");
      p.sendMessage(ChatColor.AQUA + "/Nick & /Nickname - " + ChatColor.GRAY + "This lets you change your nickname in the chat!");
      p.sendMessage(ChatColor.GRAY + "Daily - " + ChatColor.GRAY + "Open The Daily Rewards Menu!");
      
    } 
    if (i == 3) {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b--&7Page &b3/5&7--"));
      p.sendMessage(ChatColor.GRAY + "/Autorankup(/aru) - " + ChatColor.GRAY + "Toggles Automatically ranking up");
      p.sendMessage(ChatColor.AQUA + "/ChatColor - " + ChatColor.GRAY + "Allows you to change your chat colour!");
      p.sendMessage(ChatColor.GRAY + "/Who & /List - " + ChatColor.GRAY + "This will show you all of the players online and set them into categories for you.");
      p.sendMessage(ChatColor.AQUA + "/Dropitems - " + ChatColor.GRAY + "Toggle this to enable / disabled the ability to drop pickaxes on the floor.");
      p.sendMessage(ChatColor.GRAY + "/Staffs - " + ChatColor.GRAY + "This will open a shop where you can buy staffs to enhance your PvP experience!");
      
      
    } 
    if (i == 4) {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b--&7Page &b4/5&7--"));
      p.sendMessage(ChatColor.GRAY + "/Options - " + ChatColor.GRAY + "Open the Options menu to toggle settings on/off.");
      p.sendMessage(ChatColor.AQUA + "/Vote - " + ChatColor.GRAY + "Allows you to vote for the server for cool rewards!");
      p.sendMessage(ChatColor.GRAY + "/Tokens - " + ChatColor.GRAY + "The basic command for our Token currency!");
      p.sendMessage(ChatColor.AQUA + "/Upgrade - " + ChatColor.GRAY + "Will use all upgrade books in your inventory to upgrade the item in your hand!");
      p.sendMessage(ChatColor.GRAY + "/Trash & /Disposal - " + ChatColor.GRAY + "Opens an empty inventory for you to move all items you don't want into it. These items will be deleted PERMANENTLY.");
    } 
    if (i == 5) {
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b--&7Page &b5/5&7--"));
      p.sendMessage(ChatColor.GRAY + "/Trails - " + ChatColor.GRAY + "Look at Trails you have access to");
      p.sendMessage(ChatColor.AQUA + "/MaxRankup - " + ChatColor.GRAY + "Will allow you to rank up as much as you can afford.");
      p.sendMessage(ChatColor.GRAY + "/Autosell - " + ChatColor.GRAY + "Toggles Autosell.");
      p.sendMessage(ChatColor.AQUA + "/Multi - " + ChatColor.GRAY + "Shows your current Multi.");
      p.sendMessage(ChatColor.GRAY + "/Backpack & /BP - " + ChatColor.GRAY + "Opens the backpack menu.");
    } 
  }
  
  String prefix = ChatColor.LIGHT_PURPLE + "Epsilon" + ChatColor.AQUA + "Pay" + ChatColor.GRAY + ChatColor.BOLD + " > ";
  
  public ItemStack glowingItem(Player p, ItemStack item) {
    ItemStack i = new ItemStack(p.getItemInHand().getType(), p.getItemInHand().getAmount(), p.getItemInHand().getDurability());
    ItemMeta im = item.getItemMeta().clone();
    im.addEnchant(Enchantment.DURABILITY, 0, false);
    i.setItemMeta(im);
    i.removeEnchantment(Enchantment.DURABILITY);
    return i;
  }
  
  public static ItemStack addGlow(ItemStack item){ 
	  net.minecraft.server.v1_8_R3.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
	  NBTTagCompound tag = null;
	  if (!nmsStack.hasTag()) {
	      tag = new NBTTagCompound();
	      nmsStack.setTag(tag);
	  }
	  if (tag == null) tag = nmsStack.getTag();
	  NBTTagList ench = new NBTTagList();
	  tag.set("ench", ench);
	  nmsStack.setTag(tag);
	  return CraftItemStack.asCraftMirror(nmsStack);
	}
  
  public int getPlayersOnline() {
	    int x = 0;
	    for (Player p : Bukkit.getOnlinePlayers()) {
	    	if (!Vanish.vanished.contains(p))
	        x++; 
	    } 
	    return x;
	  }
  
  public int getDonorsOnline() {
    int x = 0;
    for (Player p : Bukkit.getOnlinePlayers()) {
    	if (!Vanish.vanished.contains(p) && 
        p.hasPermission("rank.donor"))
        x++; 
    } 
    return x;
  }
  
  public int getStaff() {
    int x = 0;
    for (Player p : Bukkit.getOnlinePlayers()) {
    	if (!Vanish.vanished.contains(p) && 
        p.hasPermission("rank.helper"))
        x++; 
    } 
    return x;
  }
  
  private String onlinePlayers() {
    StringBuilder sb = new StringBuilder();
    for (Player p : Bukkit.getOnlinePlayers()) {
    	if (!Vanish.vanished.contains(p))
        sb.append(ChatColor.WHITE + p.getName() + ChatColor.GRAY + ", "); 
    } 
    return sb.toString();
  }
  
  private String onlineStaff() {
    StringBuilder sb = new StringBuilder();
    for (Player p : Bukkit.getOnlinePlayers()) {
    	if(p.hasPermission("rank.helper")) {
      if (!Vanish.vanished.contains(p))
        sb.append(ChatColor.GOLD + p.getName() + ChatColor.GRAY + ", "); 
    } 
    }
    return sb.toString();
  }
  private String onlineDonors() {
	    StringBuilder sb = new StringBuilder();
	    for (Player p : Bukkit.getOnlinePlayers()) {
	    	if(p.hasPermission("rank.donor")) {
	      if (!Vanish.vanished.contains(p))
	        sb.append(ChatColor.AQUA + p.getName() + ChatColor.GRAY + ", "); 
	    } 
	    }
	    return sb.toString();
	  }
  
  public ArrayList<String> waiting = new ArrayList<String>();
  
  public void getFirst10(Player p) {
    if (this.waiting.size() == 0) {
      p.sendMessage(ChatColor.WHITE + "There is no current list!");
      return;
    } 
    for (int x = 0; x < 10; x++) {
      try {
        int i = x + 1;
        p.sendMessage(ChatColor.GRAY +""+ i + ") " + (String)this.waiting.get(x));
      } catch (Exception exception) {}
    } 
  }
  
  public void sendOnlineList(CommandSender p) {
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&f&lAll Players &7&l(&f&l" + getPlayersOnline() + "&7&l)"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', onlinePlayers()));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lOnline Donators &7&l(&b&l" + getDonorsOnline() + "&7&l)"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', onlineDonors()));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', ""));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&lOnline Staff &7&l(&6&l" + getStaff() + "&7&l)"));
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', onlineStaff()));
  }
  
  public List<String> top5 = new ArrayList<String>();
  
  public HashMap<String, Integer> points = new HashMap<String, Integer>();
  
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
  
  public void updatepoints() {
    this.points.clear();
    for (String s : this.settings.getData().getKeys(false)) {
      if (this.settings.getData().contains(String.valueOf(s) + ".Kills"))
        try {
          String ss = Bukkit.getOfflinePlayer(UUID.fromString(s)).getName();
          this.points.put(s, Integer.valueOf(this.settings.getData().getInt(String.valueOf(s) + ".Kills")));
        } catch (Exception exception) {} 
    } 
  }
  
  public void orderTop() {
    updatepoints();
    this.top5.clear();
    Object[] a = this.points.entrySet().toArray();
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
      this.top5.add((String)((Map.Entry)e).getKey());
      xx++;
      b++;
    } 
  }
  
  public boolean isDouble(String s) {
    try {
      Double.parseDouble(s);
      return true;
    } catch (NumberFormatException e) {
      return false;
    } 
  }
  
  int ii = 0;
  
  @EventHandler
  public void onClick(PlayerInteractEvent e) {
    Player p = e.getPlayer();
    if (p.getItemInHand() != null && 
      p.getItemInHand().hasItemMeta() && 
      p.getItemInHand().getItemMeta().hasDisplayName() && 
      p.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.translateAlternateColorCodes('&', "&bEpsilon Bank Note")))
      for (String lore : p.getItemInHand().getItemMeta().getLore()) {
        String amt = ChatColor.stripColor(lore);
        double total = Double.parseDouble(amt);
        int itemAmt = p.getItemInHand().getAmount();
        if (itemAmt == 1) {
          p.setItemInHand(null);
          Main.econ.depositPlayer((OfflinePlayer)p, total);
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2+" + format(total)));
          p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aNew Balance: " + format(Main.econ.getBalance((OfflinePlayer)p))));
          continue;
        } 
        ItemStack a = new ItemStack(p.getItemInHand().getType(), p.getItemInHand().getAmount() - 1);
        ItemMeta am = p.getItemInHand().getItemMeta();
        a.setItemMeta(am);
        p.setItemInHand(a);
        Main.econ.depositPlayer((OfflinePlayer)p, total);
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&2+" + format(total)));
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aNew Balance: " + format(Main.econ.getBalance((OfflinePlayer)p))));
      }  
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    if (this.waiting.contains(e.getPlayer().getName()))
      this.waiting.remove(e.getPlayer().getName()); 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    Plugin gm_plugin = Bukkit.getServer().getPluginManager()
      .getPlugin("GroupManager");
    GroupManager gm = (GroupManager)gm_plugin;
    if (cmd.getName().equalsIgnoreCase("Who") || cmd.getName().equalsIgnoreCase("List"))
      sendOnlineList(sender); 
    if (cmd.getName().equalsIgnoreCase("RedeemDonor")) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.GOLD + "/RedeemDonor <List/Add>");
        return true;
      } 
      if (sender instanceof Player) {
        Player p = (Player)sender;
        if (args[0].equalsIgnoreCase("List")) {
          getFirst10(p);
          return true;
        } 
        if (args[0].equalsIgnoreCase("RemoveTop")) {
          if (!p.isOp())
            return false; 
          this.waiting.remove(this.waiting.get(0));
          return true;
        } 
        if (args[0].equalsIgnoreCase("Add")) {
          if (!this.waiting.contains(p.getName())) {
            this.waiting.add(p.getName());
            p.sendMessage(ChatColor.WHITE + "You have been added to the list. ");
            p.sendMessage(ChatColor.WHITE + "You are number " + ChatColor.GOLD + this.waiting.size());
          } else {
            p.sendMessage(ChatColor.WHITE + "You are already on the list");
          } 
          return true;
        } 
      } 
    } 
    if (cmd.getName().equalsIgnoreCase("discord"))
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Join our discord at &bhttps://discord.gg/KGDSxbB")); 
    
    if (cmd.getName().equalsIgnoreCase("Glow")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      if (!p.hasPermission("Epsilon.Glow")) {
        p.sendMessage(ChatColor.RED + "You do not have permission");
        return false;
      } else {
     p.setItemInHand(addGlow(p.getItemInHand()));
      }
    } 
    if (label.equalsIgnoreCase("joke"))
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b" + getAPI("http://api.icndb.com/jokes/random").split("joke\": \"")[1].split("\", \"categories")[0].replaceAll("&quot;", "\""))); 
    if (label.equalsIgnoreCase("withdraw")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      if (args.length != 1) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/withdraw <amount>"));
        return false;
      } 
      if (!isDouble(args[0])) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c/withdraw <amount>"));
        return false;
      } 
      if (Main.econ.getBalance((OfflinePlayer)p) < Double.parseDouble(args[0])) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou do not have &4" + args[0]));
        return false;
      } 
      if (Main.econ.getBalance((OfflinePlayer)p) == Double.parseDouble(args[0])) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou can't withdraw your exact balance due to bugs!"));
        return false;
      } 
      if (Double.parseDouble(args[0]) < 1.0D) {
        p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cWhy?"));
        return false;
      } 
      ItemStack a = new ItemStack(Material.PAPER, 1);
      ItemMeta am = a.getItemMeta();
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bEpsilon Bank Note"));
      am.setLore(Arrays.asList(new String[] { ChatColor.translateAlternateColorCodes('&', "&a" + args[0]) }));
      a.setItemMeta(am);
      Main.econ.withdrawPlayer((OfflinePlayer)p, Double.parseDouble(args[0]));
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4-" + format(Double.parseDouble(args[0]))));
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aNew Balance: " + format(Main.econ.getBalance((OfflinePlayer)p))));
      p.getInventory().addItem(new ItemStack[] { a });
    } 
    if (cmd.getName().equalsIgnoreCase("Youtuber")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      if (args.length != 2) {
        p.sendMessage(ChatColor.RED + "/Youtuber <SubCount> <Link To Server Video>");
        p.sendMessage(ChatColor.GRAY + "To keep youtuber rank you must post epsilon once a week!");
        p.sendMessage(ChatColor.GRAY + "If accepted the higher the sub count the better the perks!");
        return true;
      } 
      this.settings.getYT().set(String.valueOf(p.getName()) + ".subs", args[0]);
      this.settings.getYT().set(String.valueOf(p.getName()) + ".link", args[1]);
      this.settings.saveYT();
      p.sendMessage(ChatColor.GREEN + "Application submitted!");
    } 
    if (cmd.getName().equalsIgnoreCase("SetMotd")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      if (!p.isOp())
        return false; 
      String message = "";
      for (int i = 0; i < args.length; i++)
        message = String.valueOf(message) + args[i] + " "; 
      this.settings.getData().set("MOTD", message);
      this.settings.saveData();
      this.settings.reloadData();
      return false;
    } 
    if (cmd.getName().equalsIgnoreCase("topkillers")) {
      if (!(sender instanceof Player))
        return false; 
      orderTop();
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bï¿½ &9&lEpsilon &b&m--++---------------------------------++--"));
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Out of " + this.points.size() + " killers..."));
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&71. &9" + Bukkit.getOfflinePlayer(UUID.fromString(this.top5.get(0))).getName() + " &7 Kills: &9" + String.valueOf(this.settings.getData().getInt(String.valueOf(this.top5.get(0)) + ".Kills"))));
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&72. &9" + Bukkit.getOfflinePlayer(UUID.fromString(this.top5.get(1))).getName() + " &7 Kills: &9" + String.valueOf(this.settings.getData().getInt(String.valueOf(this.top5.get(1)) + ".Kills"))));
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&73. &9" + Bukkit.getOfflinePlayer(UUID.fromString(this.top5.get(2))).getName() + " &7 Kills: &9" + String.valueOf(this.settings.getData().getInt(String.valueOf(this.top5.get(2)) + ".Kills"))));
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&74. &9" + Bukkit.getOfflinePlayer(UUID.fromString(this.top5.get(3))).getName() + " &7 Kills: &9" + String.valueOf(this.settings.getData().getInt(String.valueOf(this.top5.get(3)) + ".Kills"))));
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&75. &9" + Bukkit.getOfflinePlayer(UUID.fromString(this.top5.get(4))).getName() + " &7 Kills: &9" + String.valueOf(this.settings.getData().getInt(String.valueOf(this.top5.get(4)) + ".Kills"))));
      sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bï¿½ &9&lTop 5 Killers &b&m--++-----------------------------++--"));
    } 
    
    if (cmd.getName().equalsIgnoreCase("Help")) {
      Player p = (Player)sender;
      if (args.length == 0) {
        sendHelp(p, 1);
      } else if (args.length == 1) {
        int i;
        try {
          i = Integer.parseInt(args[0]);
        } catch (Exception e) {
          p.sendMessage(ChatColor.RED + "Page not found");
          return false;
        } 
        if (i > 4 || i < 1) {
          p.sendMessage(ChatColor.RED + "Page not found");
          return false;
        } 
        sendHelp(p, i);
      } 
    }  else if (cmd.getName().equalsIgnoreCase("UpdateBc")) {
      Player p = (Player)sender;
      if (p.hasPermission("Epsilon.Updatebc")) {
        StringBuilder sb = new StringBuilder();
        for (int x = 0; x < args.length; x++)
          sb.append(String.valueOf(args[x]) + " "); 
        this.settings.getData().set("Broadcast", sb.toString());
        this.settings.saveData();
        this.settings.reloadData();
      } 
    } else {
      if (cmd.getName().equalsIgnoreCase("DropItems")) {
        if (dropson.contains(sender.getName())) {
          dropson.remove(sender.getName());
          sender.sendMessage(ChatColor.RED + "You can no longer drop items");
          return true;
        } 
        dropson.add(sender.getName());
        sender.sendMessage(ChatColor.GREEN + "You can now drop items");
        return true;
      } 
      if (cmd.getName().equalsIgnoreCase("Trash") || cmd.getName().equalsIgnoreCase("Dispose") || cmd.getName().equalsIgnoreCase("Disposal")) {
        Player p = (Player)sender;
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Dispose your items");
        p.openInventory(inv);
      } else if (cmd.getName().equalsIgnoreCase("Color")) {
        Player p = (Player)sender;
        if (args.length != 1) {
          p.sendMessage(ChatColor.RED + "/Color <Number>");
          return false;
        } 
        if (!isInt(args[0])) {
          p.sendMessage(ChatColor.RED + "/Color <Number>");
          return false;
        } 
        if (p.getItemInHand().getType().equals(Material.STAINED_GLASS) || p.getItemInHand().getType().equals(Material.STAINED_GLASS_PANE) || p.getItemInHand().getType().equals(Material.WOOL)) {
          try {
            ItemStack is = p.getItemInHand();
            ItemStack newitem = new ItemStack(is.getType(), is.getAmount(), (short)Integer.parseInt(args[0]));
            p.setItemInHand(newitem);
            p.updateInventory();
          } catch (Exception e1) {
            Exception exception1;
            p.sendMessage(ChatColor.RED + "Invalid number or item.");
            return false;
          } 
        } else {
          p.sendMessage(ChatColor.RED + "Invalid Item");
          return false;
        } 
      } else if (cmd.getName().equalsIgnoreCase("Website")) {
        sender.sendMessage(ChatColor.GRAY + "Website: " + ChatColor.AQUA + "https://store.mcgenesis.net");
      } 
    } 
    return false;
  }
}
