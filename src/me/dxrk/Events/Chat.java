package me.dxrk.Events;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import me.dxrk.gangs.GangCommand;
import me.dxrk.gangs.Methods;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Commands.CMDAc;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;

public class Chat implements Listener, CommandExecutor {
  static Chat instance = new Chat();
  
  public static Chat getInstance() {
    return instance;
  }
  
  SettingsManager settings = SettingsManager.getInstance();
  
  public static boolean isMuted = false;
  
  Methods gang = Methods.getInstance();
  
  SellHandler sell = SellHandler.getInstance();
  
  Tokens tokens = Tokens.getInstance();
  
  public static String c(String s) {
    return ChatColor.translateAlternateColorCodes('&', s);
  }
  
  public boolean getOption(Player p, String s) {
    if (!this.settings.getData().contains(String.valueOf(String.valueOf(p.getUniqueId().toString())) + ".Chat." + s)) {
      this.settings.getData().set(String.valueOf(String.valueOf(p.getUniqueId().toString())) + ".Chat." + s, Integer.valueOf(0));
      this.settings.saveData();
    } 
    if (this.settings.getData().getInt(String.valueOf(String.valueOf(p.getUniqueId().toString())) + ".Chat." + s) == 0)
      return true; 
    return false;
  }
  
  public String getChatColor(Player p) {
    String s = this.settings.getcolor().getString(String.valueOf(String.valueOf(p.getName())) + ".Color");
    return s;
  }
  
  public void openColorInv(Player p) {
    Inventory i = Bukkit.createInventory(null, 18, ChatColor.AQUA + "Chat Color");
    i.setItem(2, glassSlide(9, "&bAqua"));
    i.setItem(3, glassSlide(0, "&7White"));
    i.setItem(4, glassSlide(4, "&eYellow"));
    i.setItem(5, glassSlide(11, "&9Blue"));
    i.setItem(6, glassSlide(13, "&2Green"));
    i.setItem(7, glassSlide(10, "&5Purple"));
    i.setItem(10, Default());
    i.setItem(11, glassSlide(1, "&6Colonel"));
    i.setItem(12, glassSlide(14, "&cAres"));
    i.setItem(13, glassSlide(5, "&aHermes"));
    i.setItem(14, glassSlide(1, "&6A&ep&6o&el&6l&eo"));
    i.setItem(15, glassSlide(15, "&8K&7r&8o&7n&8o&7s"));
    i.setItem(16, glassSlide(8, "&fZ&7e&fu&7s"));
    p.openInventory(i);
  }
  
  public void setChatColor(Player p, String color) {
    this.settings.getcolor().set(String.valueOf(String.valueOf(p.getName())) + ".Color", color);
    this.settings.savecolorFile();
  }
  
  private ItemStack glassSlide(int color, String name) {
    ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)color);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    i.setItemMeta(im);
    return i;
  }
  
  private ItemStack Default() {
    ItemStack a = new ItemStack(Material.BARRIER);
    ItemMeta am = a.getItemMeta();
    am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Default Chat"));
    a.setItemMeta(am);
    return a;
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getInventory().getName().equals(ChatColor.AQUA + "Chat Color")) {
      e.setCancelled(true);
      Player p = (Player)e.getWhoClicked();
      if (e.getRawSlot() == 10)
        setChatColor(p, null); 
      if (e.getRawSlot() == 2)
        setChatColor(p, "Aqua"); 
      if (e.getRawSlot() == 3) {
        if (!p.hasPermission("ChatColor.White")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "White");
      } 
      if (e.getRawSlot() == 4) {
        if (!p.hasPermission("ChatColor.Yellow")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Yellow");
      } 
      if (e.getRawSlot() == 5) {
        if (!p.hasPermission("ChatColor.Blue")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Blue");
      } 
      if (e.getRawSlot() == 6) {
        if (!p.hasPermission("ChatColor.green")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Green");
      }
      if (e.getRawSlot() == 7) {
          if (!p.hasPermission("ChatColor.Purple")) {
            p.sendMessage(ChatColor.RED + "You do not have this chat color.");
            return;
          } 
          setChatColor(p, "Purple");
        }
      if (e.getRawSlot() == 11) {
        if (!p.hasPermission("Rank.Colonel")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Colonel");
      } 
      if (e.getRawSlot() == 12) {
        if (!p.hasPermission("Rank.Ares")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Ares");
      } 
      if (e.getRawSlot() == 13) {
        if (!p.hasPermission("Rank.Hermes")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Hermes");
      } 
      if (e.getRawSlot() == 14) {
        if (!p.hasPermission("Rank.Apollo")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Apollo");
      } 
      if (e.getRawSlot() == 15) {
        if (!p.hasPermission("Rank.Kronos")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Kronos");
      } 
      if (e.getRawSlot() == 16) {
        if (!p.hasPermission("Rank.Zeus")) {
          p.sendMessage(ChatColor.RED + "You do not have this chat color.");
          return;
        } 
        setChatColor(p, "Zeus");
      } 
      p.closeInventory();
      p.sendMessage(ChatColor.GREEN + "Chat color changed.");
    } 
  }
  
  public String rainbowText(String s) {
    String result = "";
    int i = 0;
    byte b;
    int j;
    char[] arrayOfChar;
    for (j = (arrayOfChar = s.toCharArray()).length, b = 0; b < j; ) {
      Character c = Character.valueOf(arrayOfChar[b]);
      if (Character.isSpaceChar(c.charValue())) {
        result = String.valueOf(String.valueOf(result)) + c;
      } else if (i == 0) {
        result = String.valueOf(String.valueOf(result)) + "&a&l" + c;
        i = 1;
      } else if (i == 1) {
        result = String.valueOf(String.valueOf(result)) + "&b&l" + c;
        i = 2;
      } else if (i == 2) {
        result = String.valueOf(String.valueOf(result)) + "&c&l" + c;
        i = 3;
      } else if (i == 3) {
        result = String.valueOf(String.valueOf(result)) + "&d&l" + c;
        i = 4;
      } else if (i == 4) {
        result = String.valueOf(String.valueOf(result)) + "&e&l" + c;
        i = 5;
      } else {
        result = String.valueOf(String.valueOf(result)) + "&f&l" + c;
        i = 0;
      } 
      b = (byte)(b + 1);
    } 
    return ChatColor.translateAlternateColorCodes('&', result);
  }
  
  public String headmodText(String s) {
    String result = "";
    int i = 0;
    byte b;
    int j;
    char[] arrayOfChar;
    for (j = (arrayOfChar = s.toCharArray()).length, b = 0; b < j; ) {
      Character c = Character.valueOf(arrayOfChar[b]);
      if (Character.isSpaceChar(c.charValue())) {
        result = String.valueOf(String.valueOf(result)) + c;
      } else if (i == 0) {
        result = String.valueOf(String.valueOf(result)) + "&a" + c;
        i = 1;
      } else if (i == 1) {
        result = String.valueOf(String.valueOf(result)) + "&c" + c;
        i = 2;
      } else if (i == 2) {
        result = String.valueOf(String.valueOf(result)) + "&e" + c;
        i = 3;
      } else if (i == 3) {
        result = String.valueOf(String.valueOf(result)) + "&b" + c;
        i = 4;
      } else {
        result = String.valueOf(String.valueOf(result)) + "&d" + c;
        i = 0;
      } 
      b = (byte)(b + 1);
    } 
    return ChatColor.translateAlternateColorCodes('&', result);
  }
  
  public void switchOption(Player p, String s) {
    if (getOption(p, s)) {
      this.settings.getData().set(String.valueOf(String.valueOf(p.getUniqueId().toString())) + ".Chat." + s, Integer.valueOf(1));
      this.settings.saveData();
    } else {
      this.settings.getData().set(String.valueOf(String.valueOf(p.getUniqueId().toString())) + ".Chat." + s, Integer.valueOf(0));
      this.settings.saveData();
    } 
  }
  
  public ChatColor randomColor() {
    Random r = new Random();
    int color = r.nextInt(6);
    switch (color) {
      case 0:
        return ChatColor.AQUA;
      case 1:
        return ChatColor.GREEN;
      case 2:
        return ChatColor.LIGHT_PURPLE;
      case 3:
        return ChatColor.YELLOW;
      case 4:
        return ChatColor.RED;
    } 
    return ChatColor.WHITE;
  }
  
  public ItemStack optionItem(String name, boolean on) {
    ItemStack i = new ItemStack(Material.INK_SACK, 1);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.AQUA + name);
    List<String> lore = new ArrayList<>();
    lore.add("");
    if (on) {
      lore.add(ChatColor.GRAY + "Click to toggle off!");
      i.setDurability((short)10);
    } else {
      lore.add(ChatColor.GRAY + "Click to toggle on!");
      i.setDurability((short)8);
    } 
    im.setLore(lore);
    i.setItemMeta(im);
    return i;
  }
  
  public void openToggleOptions(Player p) {
    Inventory i = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.AQUA + "Chat Options");
    i.setItem(0, optionItem("Gang", getOption(p, "Gang")));
    i.setItem(1, optionItem("Level", getOption(p, "Level")));
    i.setItem(2, optionItem("Nickname", getOption(p, "Nickname")));
    i.setItem(3, optionItem("Donor", getOption(p, "Donor")));
    i.setItem(4, optionItem("Tag", getOption(p, "Tag")));
    p.openInventory(i);
  }
  
  @EventHandler
  public void onClick1(InventoryClickEvent e) {
    if (e.getInventory().getName().equals(ChatColor.AQUA + "Chat Options")) {
      e.setCancelled(true);
      Player p = (Player)e.getWhoClicked();
      if (e.getRawSlot() == 0)
        switchOption(p, "Gang"); 
      if (e.getRawSlot() == 1)
        switchOption(p, "Level"); 
      if (e.getRawSlot() == 2)
        switchOption(p, "Nickname"); 
      if (e.getRawSlot() == 3)
        switchOption(p, "Donor"); 
      if (e.getRawSlot() == 4)
        switchOption(p, "Tag"); 
      openToggleOptions(p);
    } 
  }
  
  public HashMap<Player, Long> waiting = new HashMap<>();
  
  public String prestige(int i) {
    if (i >= 10) {
      String s = String.valueOf(i);
      StringBuilder sb = new StringBuilder();
      byte b;
      int j;
      char[] arrayOfChar;
      for (j = (arrayOfChar = s.toCharArray()).length, b = 0; b < j; ) {
        char ss = arrayOfChar[b];
        int col = Integer.parseInt((new StringBuilder(String.valueOf(ss))).toString());
        String str = (new StringBuilder(String.valueOf(ss))).toString();
        if (col == 0)
          str = "a"; 
        if (col == 8)
          str = "b"; 
        if (col == 1)
          str = "c"; 
        if (col == 2)
          str = "d"; 
        if (col == 7)
          str = "e"; 
        sb.append("&" + str + "&l" + ss);
        b = (byte)(b + 1);
      } 
      return sb.toString();
    } 
    String color = (new StringBuilder(String.valueOf(i))).toString();
    if (i == 0)
      color = "a"; 
    if (i == 8)
      color = "b"; 
    if (i == 1)
      color = "c"; 
    if (i == 2)
      color = "d"; 
    if (i == 7)
      color = "e"; 
    return "&" + color + "&l" + i;
  }
  
  public String prefix(Player p) {
    String rank = "";
    if (p.getName().equalsIgnoreCase("Dxrk")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&d&l&ki&f&lOwner&d&l&ki&r ");
    }  else if (p.hasPermission("Rank.Admin")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&0&k&l;&8&lAdmin&0&k&l;&r ");
    } else if (p.hasPermission("Rank.Manager")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&f&l&k;&5&lManager&f&l&k;&r ");
    } else if (p.hasPermission("Rank.Mod")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&b&l&k;&9&lMod&b&l&k;&r ");
    } else if (p.hasPermission("Rank.Builder")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&2&l&k;&a&lBuilder&2&l&k;&r ");
    } else if (p.hasPermission("Rank.Helper")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&6&l&k;&e&lHelper&6&l&k;&r ");
    } else if (p.hasPermission("Rank.Zeus")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&7&l&ki&e&l⚡&7&l&ki&f&lZeus&7&l&ki&e&l⚡&7&l&ki&r ");
    } else if (p.hasPermission("Rank.Kronos")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&f&l&kii&8&lKronos&f&l&kii&r ");
    } else if (p.hasPermission("Rank.Apollo")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&c&l&kii&6&lApollo&c&l&kii&r ");
    } else if (p.hasPermission("Rank.Hermes")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&b&l&ki&a&lHermes&b&l&ki&r ");
    } else if (p.hasPermission("Rank.Ares")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&a&l&ki&c&lAres&a&l&ki&r ");
    } else if (p.hasPermission("Rank.Colonel")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&8&k&l:&6Colonel&8&k&l:&r ");
    } else if (p.hasPermission("Rank.Captian")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&8&k&l:&9Captain&8&k&l:&r ");
    } else if (p.hasPermission("Rank.Hoplite")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&8&k&l:&eHoplite&8&k&l:&r ");
    } else if (p.hasPermission("Rank.Cavalry")) {
      rank = ChatColor.translateAlternateColorCodes('&', "&8&k&l:&cCavalry&8&k&l:&r ");
    }
    return rank;
  }
  
  public static String format(double amt) {
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
  
  public ItemStack getHover(Player p) {
    ItemStack i = new ItemStack(Material.PAPER, 1);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&fReal Name &7" + p.getName()));
    List<String> lore = new ArrayList<>();
     lore.add(Functions.pres(p));
    lore.add(ChatColor.translateAlternateColorCodes('&', "&fPrison Level &7" + RankupHandler.getInstance().getRank(p)));
    if (prefix(p) != null) {
      lore.add(ChatColor.translateAlternateColorCodes('&', "&fDonor Rank &7" + prefix(p)));
    } else {
      lore.add(ChatColor.translateAlternateColorCodes('&', "&fDonor Rank &7"));
    } 
    if (this.gang.getGang(p) != null) {
      lore.add(ChatColor.translateAlternateColorCodes('&', "&fGang &7&8[" + this.gang.getGang(p) + "&8] &7(&b" + this.gang.getTag(this.gang.getGang(p)) + "&7)"));
    } else {
      lore.add(ChatColor.translateAlternateColorCodes('&', "&fGang &7"));
    } 
    lore.add(ChatColor.translateAlternateColorCodes('&', "&fBalance &7&8[&a" + format(Main.econ.getBalance((OfflinePlayer)p)) + "&8] "));
    lore.add(ChatColor.translateAlternateColorCodes('&', "&fTokens &7&8[&b" + Tokens.getInstance().getTokens(p) + "&8] "));
    lore.add(ChatColor.translateAlternateColorCodes('&', "&fBlocks Mined: &7» &8[&b" + this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".BlocksBroken") + "&8] "));
    lore.add("");
    lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to visit plot"));
    im.setLore(lore);
    i.setItemMeta(im);
    return i;
  }
  
  public static ArrayList<Player> ac = new ArrayList<>();
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("MuteChat")) {
      if (!sender.hasPermission("Chat.Mute"))
        return false; 
      if (!isMuted) {
        isMuted = true;
        Bukkit.broadcastMessage(ChatColor.GRAY +""+ ChatColor.BOLD + sender.getName() + " has muted chat!");
      } else {
        isMuted = false;
        Bukkit.broadcastMessage(ChatColor.GRAY +""+ ChatColor.BOLD + sender.getName() + " has unmuted chat!");
      } 
    } 
    if (cmd.getName().equalsIgnoreCase("Chat") && 
      sender instanceof Player) {
      Player p = (Player)sender;
      openToggleOptions(p);
    } 
    if (cmd.getName().equalsIgnoreCase("ChatColor")) {
      Player p = (Player)sender;
      openColorInv(p);
    } 
    return false;
  }
  
  static Random r = new Random();
  
  public static ChatColor rainbowColor() {
    int x = r.nextInt(5);
    if (x == 0)
      return ChatColor.AQUA; 
    if (x == 1)
      return ChatColor.GREEN; 
    if (x == 2)
      return ChatColor.RED; 
    if (x == 3)
      return ChatColor.YELLOW; 
    return ChatColor.WHITE;
  }
  
  public ChatColor adminColor() {
    int x = r.nextInt(3);
    if (x == 0)
      return ChatColor.DARK_RED; 
    if (x == 1)
      return ChatColor.GRAY; 
    return ChatColor.WHITE;
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    Player p = e.getPlayer();
    if (p.hasPermission("rank.helper")) {
      e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b-&7> &e&l" + p.getName()));
    } else {
      e.setQuitMessage(ChatColor.translateAlternateColorCodes('&', "&7<&b-&7> &b" + p.getName()));
    } 
  }
  
  @EventHandler
  public void onDeath(PlayerDeathEvent e ) {
	  
	  Player killer = e.getEntity().getKiller();
	  Player killed = e.getEntity();
	  
	  if(!(e.getEntity().getKiller() instanceof Player)) return;
	  
	  EntityDamageEvent ede = killed.getLastDamageCause();
	  FancyMessage chatFormat = new FancyMessage("");
	  String gangtagkiller = null;
	  String gangtagkilled = null;
	  
	  if(this.gang.getGang(killer) == null) {
	    	gangtagkiller = "";
	    } else {
	    	gangtagkiller = this.gang.getTag(this.gang.getGang(killer));
	    }
	  if(this.gang.getGang(killed) == null) {
	    	gangtagkilled = "";
	    } else {
	    	gangtagkilled = this.gang.getTag(this.gang.getGang(killed));
	    }
	  
	  DamageCause dc = ede.getCause();
	  
	  if(dc == DamageCause.FALL) {
		  
		  chatFormat.then((ChatColor.translateAlternateColorCodes('&', this.settings.getcolor().getString(String.valueOf(killed.getName()) + ".Nickname")) + "&7has fallen to death!"));
	  }
	  
	  if(killer.getItemInHand() != null && killer.getItemInHand().getType() != Material.AIR) {
		  if(this.gang.getGang(killer) != null && this.gang.getGang(killed) == null) {
			   
			  
			  chatFormat.then((ChatColor.translateAlternateColorCodes('&', "&c"+ killed.getName() +
					  " &7was killed by &f&l[" + gangtagkiller+"] &c" + killer.getName() + " &7Using &7[" + killer.getItemInHand().getItemMeta().getDisplayName() + "&7]")));
			  } else if(this.gang.getGang(killed) != null && this.gang.getGang(killer) == null) {
				  chatFormat.itemTooltip(killer.getItemInHand());
				  chatFormat.then((ChatColor.translateAlternateColorCodes('&', "&f&l[" + gangtagkilled+"] &c" + killed.getName() +
						  " &7was killed by &c" + killer.getName() + " &7Using &7[" + killer.getItemInHand().getItemMeta().getDisplayName() + "&7]")));
			  } else if(this.gang.getGang(killed) == null && this.gang.getGang(killer) == null) {
				  
				  chatFormat.then((ChatColor.translateAlternateColorCodes('&', "&c"+ killed.getName() +
						  " &7was killed by &c" +  killer.getName() + " &7Using &7[" + killer.getItemInHand().getItemMeta().getDisplayName() + "&7]")));
			  } else {
				  
				  chatFormat.then((ChatColor.translateAlternateColorCodes('&', "&f&l[" + gangtagkilled+"] &c" + killed.getName() +
						  " &7was killed by &f&l[" + gangtagkiller+"] &c" + killer.getName() + " &7Using &7[" + killer.getItemInHand().getItemMeta().getDisplayName() + "&7]")));
			  }
		  chatFormat.itemTooltip(killer.getItemInHand());
	  } else {
	  
	  chatFormat.then((ChatColor.translateAlternateColorCodes('&', "&c" + killed.getName() + " &7was given the hands by &c" + killer.getName())));
	  }
	  
	  
	  chatFormat.send(Bukkit.getOnlinePlayers());
  }
  
  @EventHandler
  public void NameHover(AsyncPlayerChatEvent event) {
	  cchat = false;
    String name, s;
    Player p = event.getPlayer();
    if (event.isCancelled())
      return; 
    if (isMuted && 
      !p.hasPermission("A")) {
      event.setCancelled(true);
      return;
    } 
    if (event.getMessage().contains("@"))
      for (Player pp : Bukkit.getOnlinePlayers()) {
        if (event.getMessage().contains("@" + pp.getName()))
          pp.playSound(pp.getLocation(), Sound.CLICK, 1.0F, 1.0F); 
      }  
    if (!p.hasPermission("rank.hoplite"))
      if (this.waiting.containsKey(event.getPlayer())) {
        long time = ((Long)this.waiting.get(event.getPlayer())).longValue();
        long currentime = (new Date()).getTime();
        long ms = 1000L;
        if (currentime > time + ms) {
          this.waiting.remove(event.getPlayer());
          this.waiting.put(event.getPlayer(), Long.valueOf((new Date()).getTime()));
        } else {
          long msrem = time + ms - currentime;
          String cooldownmsg = ChatColor.translateAlternateColorCodes('&', "&7Please wait &c" + String.valueOf(msrem) + " ms &7till you send another message.");
          String donormsg = ChatColor.translateAlternateColorCodes('&', "&Hoplite Rank &7and higher can bypass this wait.");
          event.getPlayer().sendMessage(cooldownmsg);
          event.getPlayer().sendMessage(donormsg);
          event.setCancelled(true);
          return;
        } 
      } else {
        this.waiting.put(event.getPlayer(), Long.valueOf((new Date()).getTime()));
      }  
    event.setCancelled(true);
    String pgang = Functions.prestige(p);
    
    String rank = ChatColor.translateAlternateColorCodes('&', "" + RankupHandler.getInstance().getRank(p) + " ");
    if (p.hasPermission("Chat.RankOff")) {
      rank = ""; 
      pgang = "";
    }
    String prefix = prefix(p);
    if (this.settings.getcolor().getString(String.valueOf(String.valueOf(p.getName())) + ".Nickname") == null) {
      name = ChatColor.BLUE + p.getName();
    } else {
      name = ChatColor.translateAlternateColorCodes('&', this.settings.getcolor().getString(String.valueOf(String.valueOf(p.getName())) + ".Nickname"));
    } 
    String suffix = null;
    if (this.settings.getData().contains(String.valueOf(String.valueOf(p.getUniqueId().toString())) + ".Tag"))
      suffix = ChatColor.translateAlternateColorCodes('&', this.settings.getData().getString(String.valueOf(String.valueOf(p.getUniqueId().toString())) + ".Tag")); 
    String msg = event.getMessage();
    int caps = 0;
    byte b;
    int i;
    char[] arrayOfChar;
    for (i = (arrayOfChar = msg.toCharArray()).length, b = 0; b < i; ) {
      Character c = Character.valueOf(arrayOfChar[b]);
      if (Character.isUpperCase(c.charValue()))
        caps++; 
      b = (byte)(b + 1);
    } 
    if (caps > 5 && !p.isOp()) {
      s = event.getMessage().toLowerCase();
    } else {
      s = event.getMessage();
    } 
    for (Player ps : event.getRecipients()) {
      FancyMessage chatFormat = new FancyMessage("");
      StringBuilder firstpart = new StringBuilder();
      if (pgang != null && 
        getOption(ps, "Prestige"))
        firstpart.append(pgang); 
      if (getOption(ps, "Level"))
        firstpart.append(rank); 
      if (prefix(p) != null && 
        getOption(ps, "Donor"))
        firstpart.append(prefix); 
      if (!getOption(ps, "Nickname")) {
        firstpart.append(ChatColor.GRAY + p.getName());
      } else {
        firstpart.append(name);
      } 
      if (suffix != null && 
        getOption(ps, "Tag"))
        firstpart.append(suffix); 
      String First = firstpart.toString();
      chatFormat.then(First).itemTooltip(getHover(p)).suggest("/plot h " + p.getName());
      chatFormat.then(ChatColor.translateAlternateColorCodes('&', " &8» "));
      
      if (ChatColor.stripColor(s).toLowerCase().contains("[item]") && p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
    	  String ss;
    	  if(!p.getItemInHand().getItemMeta().hasDisplayName()) {
    		  ss = c("&7»&b"+p.getItemInHand().getType().toString().toLowerCase().replace("_", " ")+"&7«&ex"+p.getItemInHand().getAmount());
    		  chatFormat.then(ss).itemTooltip(p.getItemInHand());
    	  } else {
    	  ss = c("&7»"+p.getItemInHand().getItemMeta().getDisplayName()+"&7«&ex"+p.getItemInHand().getAmount());
          chatFormat.then(ss).itemTooltip(p.getItemInHand());
    	  }
        }
      
      
      
      if (p.hasPermission("Epsilon.ChatColor")) {
        if (getChatColor(p) != null) {
          if (getChatColor(p).equals("Apollo")) {
            boolean w = false;
            StringBuilder sb = new StringBuilder();
            byte b1;
            int j;
            char[] arrayOfChar1;
            for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
              char ss = arrayOfChar1[b1];
              if (!w) {
                sb.append(ChatColor.GOLD +""+ ss);
                w = true;
              } else {
                sb.append(ChatColor.YELLOW +""+ ss);
                w = false;
              } 
              b1 = (byte)(b1 + 1);
            } 
            chatFormat.then(sb.toString());
          } else if (getChatColor(p).equals("Kronos")) {
            boolean w = false;
            StringBuilder sb = new StringBuilder();
            byte b1;
            int j;
            char[] arrayOfChar1;
            for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
              char ss = arrayOfChar1[b1];
              if (!w) {
                sb.append(ChatColor.DARK_GRAY +""+ ss);
                w = true;
              } else {
                sb.append(ChatColor.GRAY +""+ ss);
                w = false;
              } 
              b1 = (byte)(b1 + 1);
            } 
            chatFormat.then(sb.toString());
          } else if (getChatColor(p).equals("Zeus")) {
            boolean w = false;
            StringBuilder sb = new StringBuilder();
            byte b1;
            int j;
            char[] arrayOfChar1;
            for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
              char ss = arrayOfChar1[b1];
              if (!w) {
                sb.append(ChatColor.WHITE +""+ ss);
                w = true;
              } else {
                sb.append(ChatColor.GRAY +""+ ss);
                w = false;
              } 
              b1 = (byte)(b1 + 1);
            } 
            chatFormat.then(sb.toString());
          } else {
        	  boolean w = false;
              StringBuilder sb = new StringBuilder();
              byte b1;
              int k;
              char[] arrayOfChar1;
              for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
                char ss = arrayOfChar1[b1];
                if (!w) {
                  sb.append(ChatColor.translateAlternateColorCodes('&', ss+""));
                  w = true;
                } else {
                  sb.append(ChatColor.translateAlternateColorCodes('&', ss+""));
                  w = false;
                } 
                b1 = (byte)(b1 + 1);
              }
              chatFormat.then(sb.toString());
          }
        } else if (p.getName().equalsIgnoreCase("Kevin20230") || p.getName().equalsIgnoreCase("Dxrk")) {
          StringBuilder sb = new StringBuilder();
          byte b1;
          int j;
          char[] arrayOfChar1;
          for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
            char ss = arrayOfChar1[b1];
            sb.append(ChatColor.WHITE +""+ ChatColor.BOLD + ss);
            b1 = (byte)(b1 + 1);
          } 
          chatFormat.then(sb.toString());
        } else if (p.hasPermission("chat.admin")) {
          boolean w = false;
          StringBuilder sb = new StringBuilder();
          byte b1;
          int k;
          char[] arrayOfChar1;
          for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
            char ss = arrayOfChar1[b1];
            if (!w) {
              sb.append(ChatColor.RED +""+ ChatColor.BOLD + ss);
              w = true;
            } else {
              sb.append(ChatColor.RED +""+ ChatColor.BOLD + ss);
              w = false;
            } 
            b1 = (byte)(b1 + 1);
          } 
          chatFormat.then(sb.toString());
        } else if (p.hasPermission("chat.Manager")) {
          boolean w = false;
          StringBuilder sb = new StringBuilder();
          byte b1;
          int k;
          char[] arrayOfChar1;
          for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
            char ss = arrayOfChar1[b1];
            if (!w) {
              sb.append(ChatColor.LIGHT_PURPLE +""+ ChatColor.BOLD + ss);
              w = true;
            } else {
              sb.append(ChatColor.LIGHT_PURPLE +""+ ChatColor.BOLD + ss);
              w = false;
            } 
            b1 = (byte)(b1 + 1);
          }
          chatFormat.then(sb.toString());
        } else {
        	boolean w = false;
            StringBuilder sb = new StringBuilder();
            byte b1;
            int k;
            char[] arrayOfChar1;
            for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
              char ss = arrayOfChar1[b1];
              if (!w) {
                sb.append(ChatColor.translateAlternateColorCodes('&', ss+""));
                w = true;
              } else {
                sb.append(ChatColor.translateAlternateColorCodes('&', ss+""));
                w = false;
              } 
              b1 = (byte)(b1 + 1);
            }
            chatFormat.then(sb.toString());
        } 
        if (p.hasPermission("Rank.Owner")) {
          chatFormat.color(ChatColor.DARK_RED);
        } else if (p.getName().equalsIgnoreCase("BakonStirp")) {
          chatFormat.color(ChatColor.DARK_PURPLE);
        } else if (p.hasPermission("Rank.Admin")) {
          chatFormat.color(ChatColor.RED).style(new ChatColor[] { ChatColor.BOLD });
        } else if (p.hasPermission("Rank.MOD")) {
          chatFormat.color(ChatColor.AQUA).style(new ChatColor[] { ChatColor.BOLD });
        } else if (p.hasPermission("Rank.Helper")) {
          chatFormat.color(ChatColor.YELLOW).style(new ChatColor[] { ChatColor.BOLD });
        } else if (getChatColor(p) == null || getChatColor(p).equals("Aqua")) {
          chatFormat.color(ChatColor.AQUA);
        } else if (getChatColor(p).equalsIgnoreCase("White")) {
          chatFormat.color(ChatColor.WHITE);
        } else if (getChatColor(p).equalsIgnoreCase("Yellow")) {
          chatFormat.color(ChatColor.YELLOW);
        } else if (getChatColor(p).equalsIgnoreCase("Blue")) {
          chatFormat.color(ChatColor.BLUE);
        } else if (getChatColor(p).equalsIgnoreCase("Green")) {
          chatFormat.color(ChatColor.DARK_GREEN);
        } else if (getChatColor(p).equalsIgnoreCase("Purple")) {
          chatFormat.color(ChatColor.DARK_PURPLE);
        }else if (getChatColor(p).equalsIgnoreCase("Colonel")) {
          chatFormat.color(ChatColor.GOLD);
        } else if (getChatColor(p).equalsIgnoreCase("Ares")) {
          chatFormat.color(ChatColor.RED);
        } else if (getChatColor(p).equalsIgnoreCase("Hermes")) {
          chatFormat.color(ChatColor.GREEN);
        } 
        
      }
    	  if(!CMDAc.ac.contains(p)) {
    		  if(!GangCommand.gc.contains(p)) {
    			  chatFormat.send(ps);
    			  if(cchat == false) {
    			  chatFormat.send(Bukkit.getConsoleSender());
    			  cchat = true;
    			  }
    	  } else {
    		  String gangg = gang.getGang(p);
    		  
    			  if(gang.getGang(ps).equals(gang.getGang(p))) {
    			  ps.sendMessage(c("&b"+gang.getGangName(gangg)+"&7 x &b"+p.getName()+" &7"+msg));
    			  }
				
    	  }
    	  }else {
    		  
  		        if (ps.hasPermission("staffchat.use")) {
  		          ps.sendMessage(c("&c&l&4&lStaffchat&c&l&r &e" + p.getName() + " &7  &c" + msg)); 
  		        }
  	  }
      
    } 
  }
  
  private boolean cchat = false;
  
}
