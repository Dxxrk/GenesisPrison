package me.dxrk.Commands;

import me.dxrk.Main.Methods;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;

public class CMDItemEdits implements CommandExecutor {
    public String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public Methods methods = Methods.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("rename")) {
            if (!(sender instanceof Player))
                return false;
            Player p = (Player) sender;
            if (p.getItemInHand().getType().equals(Material.NETHER_STAR)) return false;
            if (!p.hasPermission("command.rename")) {
                p.sendMessage(c("&c&ki&bExistor&c&ki&r &aNo permission."));
                return false;
            }
            if (args.length == 0) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &c/rename <name>"));
                return false;
            }
            if (p.getItemInHand() == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &cYou must be holding an item!"));
                return false;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++)
                sb.append(args[i]).append(" ");
            String allArgs = sb.toString().trim();
            ItemMeta am = p.getItemInHand().getItemMeta();
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', allArgs));
            p.getItemInHand().setItemMeta(am);
            p.sendMessage(
                    ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &aYou have renamed your item to '" + allArgs + "&a'"));
        }
        if (label.equalsIgnoreCase("addlore")) {
            if (!(sender instanceof Player))
                return false;
            Player p = (Player) sender;
            if (!p.hasPermission("command.addlore")) {
                p.sendMessage(c("&cNo permission."));
                return false;
            }
            if (args.length == 0) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &c/addlore <lore>"));
                return false;
            }
            if (p.getItemInHand() == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &cYou must be holding an item!"));
                return false;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++)
                sb.append(args[i]).append(" ");
            String allArgs = sb.toString().trim();
            ItemMeta am = p.getItemInHand().getItemMeta();
            if (am.hasLore()) {
                ArrayList<String> lore = new ArrayList<>();
                lore.addAll(am.getLore());
                lore.add(ChatColor.translateAlternateColorCodes('&', allArgs));
                am.setLore(lore);
            } else {
                am.setLore(Arrays.asList(new String[]{ChatColor.translateAlternateColorCodes('&', allArgs)}));
            }
            p.getItemInHand().setItemMeta(am);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&c&ki&bExistor&c&ki&r &aYou have added '" + allArgs + "&a' to your items lore!"));
        }
        if (label.equalsIgnoreCase("dellore")) {
            if (!(sender instanceof Player))
                return false;
            Player p = (Player) sender;
            if (!p.hasPermission("command.dellore")) {
                p.sendMessage(c("&c&ki&bExistor&c&ki&r &cNo permission."));
                return false;
            }
            if (args.length != 1) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &c/dellore <number>"));
                return false;
            }
            if (p.getItemInHand() == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &cYou must be holding an item!"));
                return false;
            }
            ItemMeta am = p.getItemInHand().getItemMeta();
            if (!am.hasLore()) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &cYour item must have a lore!"));
                return false;
            }
            ArrayList<String> lore = new ArrayList<>();
            lore.addAll(am.getLore());
            p.getItemInHand().setItemMeta(am);
            if (!this.methods.isInteger(args[0])) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &c/dellore <number>"));
                return false;
            }
            if (lore.get(Integer.parseInt(args[0])) == null)
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &cYour item doesn't have this many lores!"));
            lore.remove(Integer.parseInt(args[0]));
            am.setLore(lore);
            p.getItemInHand().setItemMeta(am);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &aYou have removed a part of your items lore!"));
        }
        if (label.equalsIgnoreCase("relore")) {
            if (!(sender instanceof Player))
                return false;
            Player p = (Player) sender;
            if (!p.hasPermission("command.relore")) {
                p.sendMessage(c("&c&ki&bExistor&c&ki&r &cNo permission."));
                return false;
            }
            if (args.length == 0) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &c/relore <lore>"));
                return false;
            }
            if (p.getItemInHand() == null) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c&ki&bExistor&c&ki&r &cYou must be holding an item!"));
                return false;
            }
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++)
                sb.append(args[i]).append(" ");
            String allArgs = sb.toString().trim();
            ItemMeta am = p.getItemInHand().getItemMeta();
            am.setLore(Arrays.asList(new String[]{ChatColor.translateAlternateColorCodes('&', allArgs)}));
            p.getItemInHand().setItemMeta(am);
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    "&c&ki&bExistor&c&ki&r &aYou have set your items lore to '" + allArgs + "&a'"));
        }
        return false;
    }
}
