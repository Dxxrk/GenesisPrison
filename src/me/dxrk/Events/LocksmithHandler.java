package me.dxrk.Events;

import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Main.SettingsManager;

public class LocksmithHandler implements Listener, CommandExecutor {
  static LocksmithHandler instance = new LocksmithHandler();
  
  public static LocksmithHandler getInstance() {
    return instance;
  }
  
  public SettingsManager settings = SettingsManager.getInstance();
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("locksmith") || label.equalsIgnoreCase("ls") || label.equalsIgnoreCase("keys")) {
      if (!(sender instanceof Player))
        return false; 
      if (!sender.hasPermission("locksmith.command")) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be &9Captain &cor higher to use this command!"));
        return false;
      } 
      openInv((Player)sender);
    } 
    
    return true;
  }
  
  public void addKey(Player p, String key, int amt) {
	  int keysfound = this.settings.getPlayerData().getInt(p.getUniqueId().toString()+".KeysFound");
	  this.settings.getPlayerData().set(p.getUniqueId().toString()+".KeysFound", keysfound+amt);
    int keys = this.settings.getLocksmith().getInt(String.valueOf(p.getUniqueId().toString()) + "." + key.toLowerCase());
    key = key.toLowerCase();
    if (this.settings.getLocksmith().get(String.valueOf(p.getUniqueId().toString()) + "." + p.getName()) == null) {
      this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + ".name", p.getName());
      this.settings.saveLocksmith();
    } 
    this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + "." + key, Integer.valueOf(keys + amt));
    this.settings.saveLocksmith();
    this.settings.savePlayerData();
  }
  
  public void takeKey(Player p, String key, int amt) {
    int keys = this.settings.getLocksmith().getInt(String.valueOf(p.getUniqueId().toString()) + "." + key.toLowerCase());
    key = key.toLowerCase();
    if (this.settings.getLocksmith().get(String.valueOf(p.getUniqueId().toString()) + "." + p.getName()) == null) {
      this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + ".name", p.getName());
      this.settings.saveLocksmith();
    } 
    this.settings.getLocksmith().set(String.valueOf(p.getUniqueId().toString()) + "." + key, Integer.valueOf(keys - amt));
    this.settings.saveLocksmith();
  }
  
  public ItemStack key(String name, String lore, int amt) {
    ItemStack a = null;
    if(lore.contains("Midas")) {
    	a = new ItemStack(Material.GOLD_NUGGET, amt);
    } else if(lore.contains("Poseidon")) {
    	a = new ItemStack(Material.GHAST_TEAR, amt);
    } else if(lore.contains("Hades")) {
    	a = new ItemStack(Material.NETHER_STALK, amt);
    } else if(lore.contains("Polis")) {
    	a = new ItemStack(Material.NETHER_STAR, amt);
    } else if(lore.contains("Vote")) {
    	a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
    } else if(lore.contains("Prestige")) {
    	a = new ItemStack(Material.PRISMARINE_SHARD, amt);
    } else if(lore.contains("Oblivion")) {
    	a = new ItemStack(Material.DRAGON_EGG, amt);
    } else if(lore.contains("Olympus")) {
    	a = new ItemStack(Material.BLAZE_ROD, amt);
    }
    
    
    ItemMeta am = a.getItemMeta();
    am.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    am.setLore(Arrays.asList(new String[] { ChatColor.translateAlternateColorCodes('&', "&cRight Click &7the " + lore + " &7Crate") }));
    a.setItemMeta(am);
    return a;
  }
  
  public ItemStack item(String name, int amt) {
    ItemStack a;
    if (amt > 63) {
      a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
    } else {
      a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
    } 
    ItemMeta am = a.getItemMeta();
    if (name.equals("Midas")) {
    	a= new ItemStack(Material.GOLD_NUGGET, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lMidas &7Rune &b(" + amt + ")")); 
    }
    if (name.equals("Poseidon")) {
    	a = new ItemStack(Material.GHAST_TEAR, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&9&lPoseidon &7Rune &b(" + amt + ")")); 
    }
    if (name.equals("Hades")) {
    	a = new ItemStack(Material.NETHER_STALK, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lHades &7Rune &b(" + amt + ")")); 
    }
    if (name.equals("Polis")) {
    	a = new ItemStack(Material.NETHER_STAR, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lPolis &7Rune &b(" + amt + ")")); 
    }
    if (name.equals("Vote")) {
    	a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Vote &7Rune &b(" + amt + ")")); 
    }
    if (name.equals("Prestige")) {
    	a = new ItemStack(Material.PRISMARINE_SHARD, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lPrestige &7Rune &b(" + amt + ")")); 
    }
    if (name.equals("Oblivion")) {
    	a = new ItemStack(Material.DRAGON_EGG, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&l&kOO&4&lOblivion&c&l&kOO&r &7Rune &b(" + amt + ")")); 
    }
    if (name.equals("Olympus")) {
    	a = new ItemStack(Material.BLAZE_ROD, 1);
      am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&l&kOO&e&lOlympus&f&l&kOO&r &7Rune &b(" + amt + ")")); 
    }
    am.setLore(Arrays.asList(new String[] { ChatColor.translateAlternateColorCodes('&', "&bLeft-Click &7to withdraw &b1 &7key"), 
            ChatColor.translateAlternateColorCodes('&', "&bRight-Click &7to withdraw &bALL &7key(s)") }));
    a.setItemMeta(am);
    return a;
  }
  
  public void openInv(Player p) {
    String uuid = p.getUniqueId().toString();
    int midas = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".midas");
    int poseidon = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".poseidon");
    int hades = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".hades");
    int polis = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".polis");
    int vote = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".vote");
    int prestige = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".prestige");
    int oblivion = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".oblivion");
    int olympus = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".olympus");
    Inventory inv = Bukkit.createInventory(null, 9, 
        ChatColor.translateAlternateColorCodes('&', "&bLocksmith"));
    ItemStack mid = new ItemStack(Material.STONE_BUTTON, 1);
    ItemMeta am = mid.getItemMeta();
    am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bLocksmith"));
    mid.setItemMeta(am);
    inv.setItem(0, item("Midas", midas));
    inv.setItem(1, item("Poseidon", poseidon));
    inv.setItem(2, item("Hades", hades));
    inv.setItem(3, item("Polis", polis));
    inv.setItem(4, mid);
    inv.setItem(5, item("Vote", vote));
    inv.setItem(6, item("Prestige", prestige));
    inv.setItem(7, item("Oblivion", oblivion));
    inv.setItem(8, item("Olympus", olympus));
    p.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(PlayerInteractAtEntityEvent e) {
    if (e.getRightClicked().getCustomName() == null)
      return; 
    if (ChatColor.stripColor(e.getRightClicked().getName()).contains("Locksmith"))
      openInv(e.getPlayer());
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (!e.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&bLocksmith")))
      return; 
    if (e.getCurrentItem() == null)
      return; 
    e.setCancelled(true);
    Player p = (Player)e.getWhoClicked();
    String uuid = p.getUniqueId().toString();
    int slot = e.getSlot();
    int midas = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".midas");
    int poseidon = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".poseidon");
    int hades = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".hades");
    int polis = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".polis");
    int vote = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".vote");
    int prestige = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".prestige");
    int oblivion = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".oblivion");
    int olympus = this.settings.getLocksmith().getInt(String.valueOf(uuid) + ".olympus");
    if (e.getClick() == ClickType.LEFT) {
      if (slot == 0) {
        if (midas < 1)
          return; 
        p.getInventory().addItem(new ItemStack[] { key("&e&lMidas &7Rune", "&e&lMidas", 1) });
        takeKey(p, "midas", 1);
      } 
      if (slot == 1) {
        if (poseidon < 1)
          return; 
        p.getInventory().addItem(new ItemStack[] { key("&9&lPoseidon &7Rune", "&9&lPoseidon", 1) });
        takeKey(p, "poseidon", 1);
      } 
      if (slot == 2) {
        if (hades < 1)
          return; 
        p.getInventory().addItem(new ItemStack[] { key("&4&lHades &7Rune", "&4&lHades", 1) });
        takeKey(p, "hades", 1);
      } 
      if (slot == 3) {
        if (polis < 1)
          return; 
        p.getInventory().addItem(new ItemStack[] { key("&f&lPolis &7Rune", "&f&lPolis", 1) });
        takeKey(p, "polis", 1);
      }
      if (slot == 5) {
          if (vote < 1)
            return; 
          p.getInventory().addItem(new ItemStack[] { key("&7Vote &7Rune", "&7Vote", 1) });
          takeKey(p, "vote", 1);
        }
      if (slot == 6) {
          if (prestige < 1)
            return; 
          p.getInventory().addItem(new ItemStack[] { key("&5&lPrestige &7Rune", "&5&lPrestige", 1) });
          takeKey(p, "prestige", 1);
        }
      if (slot == 7) {
          if (oblivion < 1)
            return; 
          p.getInventory().addItem(new ItemStack[] { key("&c&l&kOO&4&lOblivion&c&l&kOO&r &7Rune", "&4Oblivion", 1) });
          takeKey(p, "oblivion", 1);
        }
      if (slot == 8) {
          if (olympus < 1)
            return; 
          p.getInventory().addItem(new ItemStack[] { key("&f&l&kOO&e&lOlympus&f&l&kOO&r &7Rune", "&e&lOlympus", 1) });
          takeKey(p, "olympus", 1);
        }
    }
    if (e.getClick() == ClickType.RIGHT) {
    	if (slot == 0) {
            if (midas < 1)
              return; 
            p.getInventory().addItem(new ItemStack[] { key("&e&lMidas &7Rune", "&e&lMidas", midas) });
            takeKey(p, "midas", midas);
          } 
          if (slot == 1) {
            if (poseidon < 1)
              return; 
            p.getInventory().addItem(new ItemStack[] { key("&9&lPoseidon &7Rune", "&9&lPoseidon", poseidon) });
            takeKey(p, "poseidon", poseidon);
          } 
          if (slot == 2) {
            if (hades < 1)
              return; 
            p.getInventory().addItem(new ItemStack[] { key("&4&lHades &7Rune", "&4&lHades", hades) });
            takeKey(p, "hades", hades);
          } 
          if (slot == 3) {
            if (polis < 1)
              return; 
            p.getInventory().addItem(new ItemStack[] { key("&f&lPolis &7Rune", "&f&lPolis", polis) });
            takeKey(p, "polis", polis);
          }
          if (slot == 5) {
              if (vote < 1)
                return; 
              p.getInventory().addItem(new ItemStack[] { key("&7Vote &7Rune", "&7Vote", vote) });
              takeKey(p, "vote", vote);
            }
          if (slot == 6) {
              if (prestige < 1)
                return; 
              p.getInventory().addItem(new ItemStack[] { key("&5&lPrestige &7Rune", "&5&lPrestige", prestige) });
              takeKey(p, "prestige", prestige);
            }
          if (slot == 7) {
              if (oblivion < 1)
                return; 
              p.getInventory().addItem(new ItemStack[] { key("&c&l&kOO&4&lOblivion&c&l&kOO&r &7Rune", "&4Oblivion", oblivion) });
              takeKey(p, "oblivion", oblivion);
            }
          if (slot == 8) {
              if (olympus < 1)
                return; 
              p.getInventory().addItem(new ItemStack[] { key("&f&l&kOO&e&lOlympus&f&l&kOO&r &7Rune", "&e&lOlympus", olympus) });
              takeKey(p, "olympus", olympus);
            }
    }
    openInv(p);
  }
}
