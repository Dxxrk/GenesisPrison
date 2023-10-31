package me.dxrk.Events;

import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class DonorItems implements Listener, CommandExecutor {

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


    public ItemStack EnchantVoucher(Player p, String enchant, int level) {
        ItemStack i = new ItemStack(Material.PAPER);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(c("&9&l" + enchant + " &b" + level));
        List<String> lore = new ArrayList<>();
        lore.add(c("&cEnchant Voucher"));
        lore.add(c("&7&oDrag onto a pickaxe to apply"));
        im.setLore(lore);
        i.setItemMeta(im);
        Main.perms.playerAdd(p, "enchant." + enchant.toLowerCase());
        Main.perms.playerAdd(p, "enchant." + enchant.toLowerCase() + "unlock");


        return i;
    }


    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        if ("giveenchant".equalsIgnoreCase(label)) {
            if (cs.hasPermission("rank.owner")) {
                if (args.length == 3) {
                    Player receiver = Bukkit.getServer().getPlayer(args[0]);
                    receiver.getInventory().addItem(EnchantVoucher(receiver, args[1], Integer.parseInt(args[2])));
                }
            }
        }
        return false;
    }


}
