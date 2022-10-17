package me.dxrk.Tokens;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Main.SettingsManager;

public class TokensCMD implements CommandExecutor, Listener {
  SettingsManager settings = SettingsManager.getInstance();
  
  Tokens tokens = Tokens.getInstance();
  
  String prefix = ChatColor.translateAlternateColorCodes('&', "&f&lTokens &8|&r ");
  
  public void prefixMsg(Player p, String s) {
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + s));
  }
  
  public boolean isInt(String str) {
    try {
      int i = Integer.parseInt(str);
      return i >= 1;
    } catch (NumberFormatException e) {
      return false;
    } 
  }
  
  public static String getPrice(ItemStack i) {
    ItemStack item = i;
    if (item.getItemMeta().hasLore())
      for (String line : item.getItemMeta().getLore()) {
        if (line.contains("Price")) {
          String level = ChatColor.stripColor(line).replace("Price:", "").replace("Tokens", "").replace(" ", "");
          level = ChatColor.stripColor(level);
          String S = level;
          return S;
        } 
      }  
    return getPrice(item);
  }
  
  public int getEmptySlots(Player p) {
      PlayerInventory inventory = p.getInventory();
      ItemStack[] cont = inventory.getContents();
      int i = 0;
      for (ItemStack item : cont)
        if (item != null && item.getType() != Material.AIR) {
          i++;
        }
      return 36 - i;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("Tokens") || cmd.getName().equalsIgnoreCase("Token"))
      if (!(sender instanceof Player)) {
        if (args[0].equalsIgnoreCase("Add")) {
          Player reciever = Bukkit.getServer().getPlayer(args[1]);
          if (!reciever.isOnline()) {
            sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
            return false;
          }
          if (isInt(args[2])) {
            String Amount = args[2];
            int amount = Integer.parseInt(Amount);
            this.tokens.addTokens(reciever, amount);
          } 
        } 
      } else {
        Player p = (Player)sender;
        String s = ChatColor.GRAY + "-- (" + ChatColor.AQUA + "+" + ChatColor.GRAY + ") -- (" + ChatColor.LIGHT_PURPLE + "+" + ChatColor.GRAY + ") -- (" + ChatColor.AQUA + "Tokens" + ChatColor.GRAY + ") -- (" + ChatColor.LIGHT_PURPLE + "+" + ChatColor.GRAY + ") -- (" + ChatColor.AQUA + "+" + ChatColor.GRAY + ") --";
        if (args.length == 0) {
          p.sendMessage(s);
          p.sendMessage(ChatColor.AQUA + "/Tokens Balance" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Displays your Epsilon token balance");
          p.sendMessage(ChatColor.AQUA + "/Tokens Send (Player) (Amount)" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Send someone tokens");
          p.sendMessage(ChatColor.AQUA + "/Tokens Withdraw" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Turn tokens to virtual items for shops!");
          p.sendMessage(ChatColor.AQUA + "/Tokens Buy" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Buy some more tokens!");
          p.sendMessage(ChatColor.AQUA + "/Tokens Shop" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Spend your tokens");
          p.sendMessage(ChatColor.AQUA + "/Enchanter" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Buy Custom me.dxrk.Enchants!");
          p.sendMessage(s);
        } else {
          if (args[0].equalsIgnoreCase("Help")) {
        	  p.sendMessage(s);
              p.sendMessage(ChatColor.AQUA + "/Tokens Balance" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Displays your Epsilon token balance");
              p.sendMessage(ChatColor.AQUA + "/Tokens Send (Player) (Amount)" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Send someone tokens");
              p.sendMessage(ChatColor.AQUA + "/Tokens Withdraw" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Turn tokens to virtual items for shops!");
              p.sendMessage(ChatColor.AQUA + "/Tokens Buy" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Buy some more tokens!");
              p.sendMessage(ChatColor.AQUA + "/Tokens Shop" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Spend your tokens");
              p.sendMessage(ChatColor.AQUA + "/Enchanter" + ChatColor.GRAY + " - " + ChatColor.DARK_AQUA + "Buy Custom me.dxrk.Enchants!");
              p.sendMessage(s);
          } else if (args[0].equalsIgnoreCase("Balance") || args[0].equalsIgnoreCase("Bal")) {
            prefixMsg(p, "&e" + ((int)this.tokens.getTokens(p)) + "⛀");
          } else if (args[0].equalsIgnoreCase("AddCommand")) {
            String page = args[1];
            int slot = Integer.parseInt(args[2]);
            StringBuilder sb = new StringBuilder();
            for (int x = 3; x < args.length; x++)
              sb.append(args[x]).append(" ");
            List<String> commands = new ArrayList<>();
            if (this.tokens.settings.getTokenShop().contains(page + "." + slot + ".command"))
              commands = this.tokens.settings.getTokenShop().getStringList(page + "." + slot + ".command");
            commands.add(sb.toString());
            this.tokens.settings.getTokenShop().set(page + "." + slot + ".command", commands);
            this.tokens.settings.saveTokenShop();
          } else if (args[0].equalsIgnoreCase("EditShop")) {
            if (!p.hasPermission("Tokens.EditShop")) {
            	return false;
            }
              if (args.length != 2) {
                p.sendMessage(ChatColor.RED + "Please pick which shop you want to edit!");
                StringBuilder sb = new StringBuilder();
                for (String ss : this.settings.getTokenShop().getKeys(false))
                  sb.append(ChatColor.GOLD).append(ss).append(", ");
                p.sendMessage(sb.toString());
              } else {
                this.tokens.loadEditableShop(p, args[1]);
              }
          } else if (args[0].equalsIgnoreCase("WithDraw")) {
            if (args.length != 2)
              return false; 
            if (isInt(args[1])) {
              double bal = this.tokens.getTokens(p);
              int i = Integer.parseInt(args[1]);
              if (bal >= i) {
                ItemStack token = new ItemStack(Material.PRISMARINE_CRYSTALS, i);
                ItemMeta MS8 = token.getItemMeta();
                MS8.setDisplayName(ChatColor.AQUA + "Token");
                token.setItemMeta(MS8);
                if(token.getAmount() > getEmptySlots(p)) {
                	p.sendMessage(ChatColor.RED+"You cannot hold that many tokens!");
                	return false;
                }
                p.getInventory().addItem(token);
                p.updateInventory();
                this.tokens.takeTokens(p, i);
              } else {
                prefixMsg(p, "&7You do not have enough tokens");
              } 
            } else {
              prefixMsg(p, "&7That is not a valid number!");
            } 
          } else if (args[0].equalsIgnoreCase("Send")) {
            if (args.length >= 2) {
              Player reciever = Bukkit.getServer().getPlayer(args[1]);
              if (reciever == null) {
                p.sendMessage(ChatColor.RED + args[1] + " is not online!");
                return false;
              } 
              if (args[1].equalsIgnoreCase(p.getName()) || reciever == p) {
                p.sendMessage(ChatColor.RED + "Hey! You can not send yourself tokens!");
                return false;
              }
              if (isInt(args[2])) {
                String Amount = args[2].replace("-", "");
                int amount = Integer.parseInt(Amount);
                double PlayerAmount = this.tokens.getTokens(p);
                if (PlayerAmount >= amount) {
                  this.tokens.sendTokens(p, reciever, amount);
                } else {
                  prefixMsg(p, "&7You do not have enough tokens");
                } 
              } else {
                prefixMsg(p, "&7That is not a valid number!");
              } 
            } else {
              prefixMsg(p, "&cUsage: /Tokens Send (Player) (Amount)");
            } 
          }  else if (args[0].equalsIgnoreCase("Add")) {
            if (p.isOp()) {
              Player reciever = Bukkit.getServer().getPlayer(args[1]);
              if (!reciever.isOnline()) {
                p.sendMessage(ChatColor.RED + args[1] + " is not online!");
                return false;
              } 
              if (isInt(args[2])) {
                String Amount = args[2];
                int amount = Integer.parseInt(Amount);
                this.tokens.addTokens(p, amount);
              } else {
                p.sendMessage(ChatColor.AQUA + "Not an int");
              } 
            } else {
              p.sendMessage(ChatColor.RED + "No perm");
            } 
          } else if (args[0].equalsIgnoreCase("Shop")) {
            this.tokens.openEtShop(p);
          } else {
            prefixMsg(p, "&7Command not found");
          } 
        } 
        this.settings.saveData();
      }  
    return false;
  }
}
