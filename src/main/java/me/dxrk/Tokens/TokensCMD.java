package me.dxrk.Tokens;

import java.security.SecureRandom;
import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Events.SellHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

    Methods m = Methods.getInstance();

    public ItemStack tokenVoucher(double tokens) {
        ItemStack t = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta tm = t.getItemMeta();
        tm.setDisplayName(m.c("&eToken Voucher"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Tokens: &e⛀" + tokens));
        tm.setLore(lore);
        t.setItemMeta(tm);
        return t;
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
                if (args[0].equalsIgnoreCase("Random")) {
                    Player reciever = Bukkit.getServer().getPlayer(args[1]);
                    if (!reciever.isOnline()) {
                        sender.sendMessage(ChatColor.RED + args[1] + " is not online!");
                        return false;
                    }
                    if (isInt(args[2]) && isInt(args[3])) {
                        Random r = new SecureRandom();
                        int min = Integer.parseInt(args[2]);
                        int max = Integer.parseInt(args[3]);
                        int tokens = r.nextInt(max - min) + min;
                        this.tokens.addTokens(reciever, tokens);
                    }
                }
            } else {
                Player p = (Player) sender;
                String s = m.c("&b&m---&c&lTokens&b&m---");
                if (args.length == 0) {
                    p.sendMessage(s);
                    p.sendMessage(ChatColor.AQUA + "/Tokens Balance" + ChatColor.GRAY + " - " + ChatColor.RED + "Displays your token balance");
                    p.sendMessage(ChatColor.AQUA + "/Tokens Send (Player) (Amount)" + ChatColor.GRAY + " - " + ChatColor.RED + "Send someone tokens");
                    p.sendMessage(ChatColor.AQUA + "/Tokens Withdraw" + ChatColor.GRAY + " - " + ChatColor.RED + "Withdraw your tokens to sell or trade!");
                    p.sendMessage(s);
                } else {
                    if (args[0].equalsIgnoreCase("Help")) {
                        p.sendMessage(s);
                        p.sendMessage(ChatColor.AQUA + "/Tokens Balance" + ChatColor.GRAY + " - " + ChatColor.RED + "Displays your token balance");
                        p.sendMessage(ChatColor.AQUA + "/Tokens Send (Player) (Amount)" + ChatColor.GRAY + " - " + ChatColor.RED + "Send someone tokens");
                        p.sendMessage(ChatColor.AQUA + "/Tokens Withdraw" + ChatColor.GRAY + " - " + ChatColor.RED + "Withdraw your tokens to sell or trade!");
                        p.sendMessage(s);
                    } else if (args[0].equalsIgnoreCase("Balance") || args[0].equalsIgnoreCase("Bal")) {
                        prefixMsg(p, "&e⛀" + this.tokens.getTokens(p));
                    } else if (args[0].equalsIgnoreCase("WithDraw")) {
                        boolean b = PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("BuildMode");
                        if(b){
                            p.sendMessage(m.c("&cYou can't access this while in buildmode."));
                            return false;
                        }
                        if (args.length != 2) return false;

                        double tokens = this.tokens.getTokens(p);
                        double amount = Double.parseDouble(args[1]);
                        if (amount > tokens || amount < 0) {
                            p.sendMessage(m.c("&cError: Not Enough Tokens"));
                            return false;
                        }
                        this.tokens.takeTokens(p, amount);
                        p.getInventory().addItem(tokenVoucher(amount));


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
                            if (SellHandler.getInstance().isDbl(args[2])) {
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
                    } else if (args[0].equalsIgnoreCase("Add")) {
                        if (p.isOp()) {
                            Player reciever = Bukkit.getServer().getPlayer(args[1]);
                            if (!reciever.isOnline()) {
                                p.sendMessage(ChatColor.RED + args[1] + " is not online!");
                                return false;
                            }
                            if (SellHandler.getInstance().isDbl(args[2])) {
                                String Amount = args[2];
                                double amount = Double.parseDouble(Amount);
                                this.tokens.addTokens(p, amount);
                            } else {
                                p.sendMessage(ChatColor.AQUA + "Not an int");
                            }
                        } else {
                            p.sendMessage(ChatColor.RED + "No perm");
                        }
                    } else if (args[0].equalsIgnoreCase("take")) {
                        if (!p.hasPermission("rank.owner")) return false;
                        Player player = Bukkit.getPlayer(args[1]);
                        tokens.takeTokens(player, Double.parseDouble(args[2]));
                    } else {
                        prefixMsg(p, "&7Command not found");
                    }
                }
                this.settings.saveData();
            }
        return false;
    }
}
