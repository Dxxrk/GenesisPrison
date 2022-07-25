package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class KeyFinders implements CommandExecutor {
  public boolean isInt(String s) {
    try {
      Integer.parseInt(s);
      return true;
    } catch (Exception e) {
      return false;
    } 
  }
  
  public String intToRoman(int i) {
    if (i == 1)
      return "I"; 
    if (i == 2)
      return "II"; 
    if (i == 3)
      return "III"; 
    if (i == 4)
      return "IV"; 
    if (i == 5)
      return "V";
	return null; 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("KeyFinder") || cmd.getName().equalsIgnoreCase("KF")) {
      if (args.length == 0) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b/KeyFinder <#> for keyfinder information"));
        return false;
      } 
      if (args.length == 1) {
        if (args[0].equalsIgnoreCase("V") || args[0].equalsIgnoreCase("5")) {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKeyFinder V - 750 Blocks till key"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lDemonic Key &7&o(2%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lMythic Key &7&o(15%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bBaron Key &7&o(33%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Rare Key &7&o(50%)"));
          return true;
        } 
        if (args[0].equalsIgnoreCase("IV") || args[0].equalsIgnoreCase("4")) {
        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKeyFinder V - 1000 Blocks till key"));
        	sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lDemonic Key &7&o(1%)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lMythic Key &7&o(10%)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bBaron Key &7&o(34%)"));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Rare Key &7&o(55%)"));
          return true;
        } 
        if (args[0].equalsIgnoreCase("III") || args[0].equalsIgnoreCase("3")) {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKeyFinder III - 1500 Blocks till key"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lDemonic Key &7&o(0.5%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lMythic Key &7&o(5%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bBaron Key &7&o(46%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Rare Key &7&o(48%)"));
        } 
        if (args[0].equalsIgnoreCase("II") || args[0].equalsIgnoreCase("2")) {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKeyFinder II - 2000 Blocks till key"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lDemonic Key &7&o(0.2%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lMythic Key &7&o(3%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bBaron Key &7&o(48%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Rare Key &7&o(48%)"));
          return true;
        } 
        if (args[0].equalsIgnoreCase("I") || args[0].equalsIgnoreCase("1")) {
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bKeyFinder I - 2500 Blocks till key"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4&lDemonic Key &7&o(0.1%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&5&lMythic Key &7&o(1%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bBaron Key &7&o(49%)"));
          sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&3Rare Key &7&o(49%)"));
          return true;
        } 
      } else if (args.length == 3 || args.length == 4) {
        if (sender instanceof Player) {
          Player pp = (Player)sender;
          if (!pp.hasPermission("KeyFinder.Spawn"))
            return false; 
        } 
        Player p = Bukkit.getPlayer(args[0]);
        if (p == null) {
          sender.sendMessage(ChatColor.GOLD + "Player not found!");
          return false;
        } 
        int kf = 0;
        int enc = 0;
        if (isInt(args[1])) {
          kf = Integer.parseInt(args[1]);
        } else {
          sender.sendMessage(ChatColor.GOLD + "Invald KF #!");
        } 
        if (isInt(args[2])) {
          enc = Integer.parseInt(args[2]);
        } else {
          sender.sendMessage(ChatColor.GOLD + "Invald Fortune & Efficiency!");
        } 
        ItemStack i = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta im = i.getItemMeta();
        if (args.length == 4) {
          String s = ChatColor.translateAlternateColorCodes('&', args[3]).replace("_", "");
          im.setDisplayName(s);
        } 
        List<String> lore = new ArrayList<String>();
        lore.add(ChatColor.translateAlternateColorCodes('&', ChatColor.GRAY + "KeyFinder " + intToRoman(kf)));
        im.setLore(lore);
        i.setItemMeta(im);
        i.addUnsafeEnchantment(Enchantment.LOOT_BONUS_BLOCKS, enc);
        i.addUnsafeEnchantment(Enchantment.DIG_SPEED, enc);
        p.getInventory().addItem(new ItemStack[] { i });
      } 
    } 
    return false;
  }
}
