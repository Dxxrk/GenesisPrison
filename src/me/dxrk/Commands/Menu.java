package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.dxrk.Events.KitAndWarp;
import me.dxrk.Main.Main;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
import org.bukkit.plugin.Plugin;

public class Menu implements CommandExecutor, Listener {
  Main pl;
  
  public Menu(Main p) {
    p.getServer().getPluginManager().registerEvents(this, (Plugin)p);
    this.pl = p;
  }
  
  public void openMenu(Player p) {
    Inventory inv = Bukkit.createInventory(null, 27, ChatColor.translateAlternateColorCodes('&', "&bEpsilon Menu"));
    for (int i = 0; i < 26; i++)
      inv.setItem(i, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)2)); 
    inv.setItem(9, item(Material.DOUBLE_PLANT, 1, 0, "&bVote!", Arrays.asList(new String[] { "&6Click here for the voting links!", "&bOr use /vote" })));
    inv.setItem(10, item(Material.DIAMOND_CHESTPLATE, 1, 0, "&3Kits!", Arrays.asList(new String[] { "&6Click here to view your kits!", "&bOr use /kits" })));
    inv.setItem(11, item(Material.COMPASS, 1, 0, "&fWarps!", Arrays.asList(new String[] { "&6Click here to view all warps!", "&bOr use /warps" })));
    inv.setItem(12, item(Material.GLASS_BOTTLE, 1, 0, "&5Perks!", Arrays.asList(new String[] { "&6Click here to open the Perk Menu!", "&bOr use /perks" })));
    inv.setItem(13, item(Material.IRON_FENCE, 1, 0, "&7Prison Break!", Arrays.asList(new String[] { "&6Click here to warp to Prison Break!", "&bOr use /prisonbreak" })));
    inv.setItem(14, item(Material.SKULL_ITEM, 1, 2, "&cZombies!", Arrays.asList(new String[] { "&6Click here to join a game of Zombies!", "&bOr use /zombies join" })));
    inv.setItem(15, item(Material.WHEAT, 1, 0, "&4Black Market!", Arrays.asList(new String[] { "&6Click here to view the black market", "&bOr use /bm" })));
    inv.setItem(16, item(Material.SKULL_ITEM, 1, 1, "&aMob&cArena!", Arrays.asList(new String[] { "&6Click here to play mob arena", "&bOr use /MobArena" })));
    inv.setItem(17, item(Material.DIAMOND, 1, 0, "&bLoyalty!", Arrays.asList(new String[] { "&6Click here to view loyalty menu", "&bOr use /Loyalty" })));
    p.openInventory(inv);
  }
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (!e.getInventory().getName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&bEpsilon Menu")))
      return; 
    e.setCancelled(true);
    if (e.getCurrentItem() == null)
      return; 
    ItemStack clicked = e.getCurrentItem();
    Player p = (Player)e.getWhoClicked();
    int slot = e.getSlot();
    if (clicked.getType() != Material.STAINED_GLASS_PANE)
      p.closeInventory(); 
    if (slot == 9)
      p.performCommand("vote"); 
    if (slot == 10) {
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Kits:");
      FancyMessage kits = new FancyMessage("");
      for (String s : KitAndWarp.getStarterKits())
        kits.then(s).tooltip(ChatColor.GRAY + "Click for kit!").command("/kit " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY); 
      kits.send(p);
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Donor Kits:");
      FancyMessage donorkits = new FancyMessage("");
      for (String s : KitAndWarp.getDonorKits()) {
        if (p.hasPermission("Essentials.kits." + s)) {
          donorkits.then(s).tooltip(ChatColor.GRAY + "Click for kit!").command("/kit " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
          continue;
        } 
        donorkits.then(s).tooltip(ChatColor.RED + "You do not have this kit!").command("/kit " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
      } 
      donorkits.send(p);
    } 
    if (slot == 11) {
      List<String> mines = new ArrayList<String>();
      List<String> nonmines = new ArrayList<String>();
      for (String s : Main.ess.getWarps().getList()) {
        if (s.length() == 1) {
          mines.add(s);
          continue;
        } 
        nonmines.add(s);
      } 
      FancyMessage minewarps = new FancyMessage("");
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Mine Warps:");
      for (String s : mines) {
        if (p.hasPermission("Essentials.warps." + s)) {
          minewarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/warp " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
          continue;
        } 
        minewarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/warp " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
      } 
      for (String s : KitAndWarp.getRankedMines()) {
        if (p.hasPermission("Essentials.warps." + s)) {
          minewarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/warp " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
          continue;
        } 
        minewarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/warp " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
      } 
      minewarps.send(p);
      p.sendMessage(ChatColor.AQUA +""+ ChatColor.BOLD + "Other Warps:");
      FancyMessage otherwarps = new FancyMessage("");
      for (String s : KitAndWarp.getOtherMines()) {
        if (p.hasPermission("Essentials.warps." + s)) {
          otherwarps.then(s).tooltip(ChatColor.GRAY + "Click to warp!").command("/warp " + s).color(ChatColor.DARK_AQUA).then(", ").color(ChatColor.GRAY);
          continue;
        } 
        otherwarps.then(s).tooltip(ChatColor.RED + "You can not warp here!").command("/warp " + s).color(ChatColor.RED).then(", ").color(ChatColor.GRAY);
      } 
      otherwarps.send(p);
      FancyMessage donorWarps = (new FancyMessage("[Click To See Donor Warps]")).color(ChatColor.AQUA).tooltip(ChatColor.GRAY + "Type /DonorWarps or Click Here").command("/donorwarps");
      donorWarps.send(p);
    } 
    if (slot == 12)
      p.performCommand("perks"); 
    if (slot == 13)
      p.performCommand("prisonbreak"); 
    if (slot == 14)
      p.performCommand("zombies"); 
    if (slot == 15)
      p.performCommand("bm"); 
    if (slot == 16)
      p.performCommand("mobarena"); 
    if (slot == 17)
      p.performCommand("loyalty"); 
  }
  
  public ItemStack item(Material mat, int amt, int data, String name, List<String> lore) {
    ItemStack a = new ItemStack(mat, amt, (short)data);
    ItemMeta am = a.getItemMeta();
    am.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
    ArrayList<String> loree = new ArrayList<String>();
    for (String s : lore)
      loree.add(ChatColor.translateAlternateColorCodes('&', s)); 
    am.setLore(loree);
    a.setItemMeta(am);
    return a;
  }

@Override
public boolean onCommand(CommandSender arg0, Command arg1, String arg2, String[] arg3) {
	// TODO Auto-generated method stub
	return false;
}
  
  
}
