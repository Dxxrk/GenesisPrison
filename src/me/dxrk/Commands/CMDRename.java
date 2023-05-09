package me.dxrk.Commands;

import java.util.ArrayList;
import java.util.List;

import me.dxrk.Main.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CMDRename implements CommandExecutor {
  Methods m = Methods.getInstance();
  private ItemStack renamePaper(int amnt) {
    ItemStack i = new ItemStack(Material.PAPER, amnt);
    ItemMeta im = i.getItemMeta();
    im.setDisplayName(ChatColor.AQUA + "Item Rename");
    List<String> lore = new ArrayList<>();
    lore.add(ChatColor.GRAY + "Type /rename with this item in your inventory!");
    im.setLore(lore);
    i.setItemMeta(im);
    return i;
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    String prefix = m.c("&f&lRename &8| ");
    if (cmd.getName().equalsIgnoreCase("renamepaper")) {
      if (args.length != 1) {
        sender.sendMessage("/RenamePaper <Name>");
        return false;
      } 
      if (sender instanceof Player) {
        Player p = (Player)sender;
        if (!p.hasPermission("Epsilon.SpawnPaper")) {
          sender.sendMessage("No Perms");
          return false;
        } 
      } 
      Player who = Bukkit.getPlayer(args[0]);
      who.getInventory().addItem(renamePaper(1));
      who.updateInventory();
      return true;
    } 
    if (cmd.getName().equalsIgnoreCase("rename")) {
      if (sender instanceof Player) {
        Player p = (Player)sender;
        if (sender.hasPermission("epsilon.rename")) {
          if ((p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR) && (p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
                  || p.getItemInHand().getType().equals(Material.IRON_PICKAXE) || p.getItemInHand().getType().equals(Material.GOLD_PICKAXE) || p.getItemInHand().getType().equals(Material.STONE_PICKAXE)
                  || p.getItemInHand().getType().equals(Material.WOOD_PICKAXE))) || p.isOp()) {
            if (args.length >= 1) {
              ItemStack itemStack = p.getItemInHand();
              StringBuilder message = new StringBuilder();
              for (String arg : args) message.append(arg).append(" ");
              String Message1 = ChatColor.translateAlternateColorCodes('&', message.toString());
              ItemMeta itemStackMeta = itemStack.getItemMeta();
              itemStackMeta.setDisplayName(Message1);
              itemStack.setItemMeta(itemStackMeta);
              p.sendMessage(prefix + ChatColor.LIGHT_PURPLE+"Item Renamed To: " + ChatColor.RESET + Message1 + ChatColor.AQUA + "!");
            } else {
              p.sendMessage(ChatColor.AQUA + "Error: " + ChatColor.RED + "You must specify a name!");
            } 
          } else {
            p.sendMessage(ChatColor.AQUA + "Error: " + ChatColor.RED + "You need an item in your hand!");
          } 
        } else if (p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR)) {
          if (args.length >= 1) {
            boolean hasPaper = false;
            byte b;
            int j;
            ItemStack[] arrayOfItemStack;
            for (j = (arrayOfItemStack = p.getInventory().getContents()).length, b = 0; b < j; ) {
              ItemStack itemStack1 = arrayOfItemStack[b];
              if (itemStack1 != null && 
                !hasPaper && 
                itemStack1.equals(renamePaper(itemStack1.getAmount()))) {
                hasPaper = true;
                if (itemStack1.getAmount() > 1) {
                  itemStack1.setAmount(itemStack1.getAmount() - 1);
                } else {
                  itemStack1.setType(Material.BARRIER);
                } 
              } 
              b++;
            } 
            p.getInventory().remove(Material.BARRIER);
            p.updateInventory();
            if (!hasPaper) {
              p.sendMessage(ChatColor.GRAY + "You need a rename paper to rename!");
              return false;
            } 
            ItemStack itemStack = p.getItemInHand();
            StringBuilder message = new StringBuilder();
            for (String arg : args) message.append(arg).append(" ");
            String Message1 = ChatColor.translateAlternateColorCodes('&', message.toString());
            ItemMeta itemStackMeta = itemStack.getItemMeta();
            itemStackMeta.setDisplayName(Message1);
            itemStack.setItemMeta(itemStackMeta);
            p.sendMessage(prefix + ChatColor.LIGHT_PURPLE+"Item Renamed To: " + ChatColor.RESET + Message1 + ChatColor.AQUA + "!");
          } else {
            p.sendMessage(ChatColor.AQUA + "Error: " + ChatColor.RED + "You must specify a name!");
          } 
        } else {
          p.sendMessage(ChatColor.AQUA + "Error: " + ChatColor.RED + "You need an item in your hand!");
        } 
        return true;
      } 
      sender.sendMessage("Must be a player to use this command.");
    } else if (cmd.getName().equalsIgnoreCase("relore")) {
      if (sender instanceof Player) {
        if (sender.hasPermission("Epsilon.Relore")) {
          Player p = (Player)sender;
          if ((p.getItemInHand() != null && !p.getItemInHand().getType().equals(Material.AIR) && (p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)
          || p.getItemInHand().getType().equals(Material.IRON_PICKAXE) || p.getItemInHand().getType().equals(Material.GOLD_PICKAXE) || p.getItemInHand().getType().equals(Material.STONE_PICKAXE)
          || p.getItemInHand().getType().equals(Material.WOOD_PICKAXE))) || p.isOp()) {
            if (args.length >= 1) {
              ItemStack itemStack = p.getItemInHand();
              StringBuilder message = new StringBuilder();
              for (String arg : args) message.append(arg).append(" ");
              String Message1 = ChatColor.translateAlternateColorCodes('&', message.toString());
              String[] loreNotConverted = Message1.split(";");
              List<String> lore = new ArrayList<>();
              byte b;
              int j;
              String[] arrayOfString1;
              for (j = (arrayOfString1 = loreNotConverted).length, b = 0; b < j; ) {
                String s = arrayOfString1[b];
                lore.add(s);
                b++;
              } 
              ItemMeta itemStackMeta = itemStack.getItemMeta();
              itemStackMeta.setLore(lore);
              itemStack.setItemMeta(itemStackMeta);
              p.sendMessage(prefix + ChatColor.AQUA + "Item Relored!");
            } else {
              p.sendMessage(ChatColor.AQUA + "Error: " + ChatColor.RED + "You must specify a lore!");
            } 
          } else {
            p.sendMessage(ChatColor.AQUA + "Error: " + ChatColor.RED + "You need an item in your hand!");
          } 
        } else {
          sender.sendMessage(ChatColor.AQUA + "Error: " + ChatColor.RED + "You do not have permission!");
        } 
        return true;
      } 
      sender.sendMessage("Must be a player to use this command.");
    } 
    return false;
  }
}
