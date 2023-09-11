package me.dxrk.Commands;

import me.dxrk.Main.SettingsManager;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CMDNickname implements CommandExecutor, Listener {
    SettingsManager settings = SettingsManager.getInstance();

    public HashMap<Player, String> edit = new HashMap<>();

    public HashMap<Player, String> currentletter = new HashMap<>();

    public HashMap<Player, List<String>> editlist = new HashMap<>();

    public HashMap<Player, Integer> currentnumber = new HashMap<>();

    public HashMap<Player, Integer> max = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player))
            return false;
        Player p = (Player) sender;
        if (cmd.getName().equalsIgnoreCase("Nicknames") || cmd.getName().equalsIgnoreCase("Nick") || cmd.getName().equalsIgnoreCase("Nickname"))
            if (args.length == 0) {
                this.edit.remove(p);
                this.currentletter.remove(p);
                this.max.remove(p);
                this.currentnumber.remove(p);
                this.editlist.remove(p);
                char c = p.getName().charAt(0);
                openEditInv(p, String.valueOf(c));
            } else if (args.length == 1) {
                String newNick = args[0];
                if (!p.hasPermission("rank.Titan")) {
                    p.sendMessage(ChatColor.RED + "You do not have permission for this! Use /Nickname");
                    return false;
                }
                if ((newNick.contains("&k") || newNick.contains("&K")) &&
                        !p.hasPermission("rank.Genesis")) {
                    p.sendMessage(ChatColor.RED + "Only Zeus Rank can have magic in nicknames");
                    return false;
                }
                if ((newNick.contains("&l") || newNick.contains("&L")) &&
                        !p.hasPermission("rank.Olympian")) {
                    p.sendMessage(ChatColor.RED + "Only Kronos+ rank can have bold in nicknames");
                    return false;
                }
                if ((newNick.contains("&o") || newNick.contains("&O")) &&
                        !p.hasPermission("rank.God")) {
                    p.sendMessage(ChatColor.RED + "Only Apollo+ rank can have italic in nicknames");
                    return false;
                }
                if (args[0].length() > 36) {
                    p.sendMessage(ChatColor.RED + "Nickname too long!");
                    return false;
                }
                this.settings.getcolor().set(p.getName() + ".Nickname", args[0]);
                this.settings.savecolorFile();
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "Nickname set to: " + args[0]));
            } else {
                sender.sendMessage(ChatColor.RED + "/Nickname <Nickname>");
                return false;
            }
        return false;
    }

    @EventHandler
    public void invClick(InventoryClickEvent e) {
        if (!e.getInventory().getName().contains("Current Letter"))
            return;
        Player p = (Player) e.getWhoClicked();
        if (this.max.get(p) == null) {
            List<String> titleindiv = new ArrayList<>();
            for (int x = 0; x < p.getName().length(); x++) {
                char letter = p.getName().charAt(x);
                String letter1 = String.valueOf(letter);
                titleindiv.add(letter1);
            }
            this.currentletter.put(p, titleindiv.get(0));
            this.max.put(p, titleindiv.size());
            this.currentnumber.put(p, 0);
            this.editlist.put(p, titleindiv);
            this.edit.put(p, "&9");
        }
        int slot = e.getSlot();
        e.setCancelled(true);
        if (slot < 43) {
            if (!p.hasPermission("rank.vip")) {
                try {
                    e.getCurrentItem().setType(Material.BARRIER);
                } catch (Exception ignored) {
                }
                p.sendMessage(ChatColor.GREEN + "VIP and higher, sorry.");
                return;
            }
            String s = this.edit.get(p);
            if (slot == 0)
                s = s + "&1";
            if (slot == 1)
                s = s + "&2";
            if (slot == 2)
                s = s + "&3";
            if (slot == 3)
                s = s + "&4";
            if (slot == 4)
                s = s + "&5";
            if (slot == 5)
                s = s + "&6";
            if (slot == 6)
                s = s + "&7";
            if (slot == 7)
                s = s + "&8";
            if (slot == 8)
                s = s + "&9";
            if (slot == 18)
                s = s + "&a";
            if (slot == 19)
                s = s + "&b";
            if (slot == 20)
                s = s + "&c";
            if (slot == 21)
                s = s + "&d";
            if (slot == 22)
                s = s + "&e";
            if (slot == 23)
                s = s + "&f";
            if (slot == 36)
                if (p.hasPermission("rank.Demi-God")) {
                    s = s + "&l";
                } else {
                    e.getCurrentItem().setType(Material.BARRIER);
                    p.sendMessage(ChatColor.RED + "Demi-God and higher!");
                }
            if (slot == 37)
                if (p.hasPermission("rank.mvp")) {
                    s = s + "&n";
                } else {
                    e.getCurrentItem().setType(Material.BARRIER);
                    p.sendMessage(ChatColor.RED + "MVP and higher!");
                }
            if (slot == 38)
                if (p.hasPermission("rank.vip")) {
                    s = s + "&m";
                } else {
                    e.getCurrentItem().setType(Material.BARRIER);
                    p.sendMessage(ChatColor.RED + "VIP and higher!");
                }
            if (slot == 39)
                if (p.hasPermission("rank.hero")) {
                    s = s + "&o";
                } else {
                    e.getCurrentItem().setType(Material.BARRIER);
                    p.sendMessage(ChatColor.RED + "Hero and higher!");
                }
            this.edit.put(p, s);
            return;
        }
        if (slot == 44) {
            String s = this.edit.get(p);
            List<String> list = this.editlist.get(p);
            String letter = String.valueOf(p.getName().charAt(this.currentnumber.get(p)));
            s = s + letter;
            this.edit.put(p, s);
            int current = this.currentnumber.get(p);
            this.currentnumber.put(p, current + 1);
            if (this.currentnumber.get(p) >= this.max.get(p)) {
                this.settings.getcolor().set(p.getName() + ".Nickname", String.valueOf(this.edit.get(p)));
                p.sendMessage(ChatColor.GREEN + "Nickname Updated");
                this.settings.savecolorFile();
                p.closeInventory();
                this.edit.remove(p);
                this.currentletter.remove(p);
                this.max.remove(p);
                this.currentnumber.remove(p);
                this.editlist.remove(p);
                return;
            }
            String letter1 = list.get(current + 1);
            openEditInv(p, letter1);
        }
    }

    public ItemStack invItem(Material mat, int shortnum, String name) {
        ItemStack i = new ItemStack(mat, 1, (short) shortnum);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        i.setItemMeta(im);
        return i;
    }

    public void openEditInv(Player p, String letter) {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED + letter + " | Current Letter");
        inv.setItem(0, invItem(Material.WOOL, 11, "&1Dark Blue"));
        inv.setItem(1, invItem(Material.WOOL, 13, "&2Dark Green"));
        inv.setItem(2, invItem(Material.WOOL, 9, "&3Cyan"));
        inv.setItem(3, invItem(Material.WOOL, 14, "&4Dark Red"));
        inv.setItem(4, invItem(Material.WOOL, 10, "&5Purple"));
        inv.setItem(5, invItem(Material.WOOL, 1, "&6Gold"));
        inv.setItem(6, invItem(Material.WOOL, 8, "&7Light Gray"));
        inv.setItem(7, invItem(Material.WOOL, 7, "&8Dark Gray"));
        inv.setItem(8, invItem(Material.WOOL, 3, "&9Dark Aqua"));
        inv.setItem(18, invItem(Material.STAINED_GLASS, 5, "&aLight Green"));
        inv.setItem(19, invItem(Material.STAINED_GLASS, 3, "&bAqua"));
        inv.setItem(20, invItem(Material.STAINED_GLASS, 14, "&cRed"));
        inv.setItem(21, invItem(Material.STAINED_GLASS, 6, "&dPink"));
        inv.setItem(22, invItem(Material.STAINED_GLASS, 4, "&eYellow"));
        inv.setItem(23, invItem(Material.STAINED_GLASS, 0, "&fWhite"));
        inv.setItem(36, invItem(Material.IRON_INGOT, 0, "&f&lBold"));
        inv.setItem(37, invItem(Material.GOLD_INGOT, 0, "&f&nUnderline"));
        inv.setItem(38, invItem(Material.DIAMOND, 0, "&f&mStrikethrough"));
        inv.setItem(39, invItem(Material.EMERALD, 0, "&f&oItalic"));
        inv.setItem(44, invItem(Material.NETHER_STAR, 0, "&c&lNext Letter"));
        p.openInventory(inv);
    }
}
