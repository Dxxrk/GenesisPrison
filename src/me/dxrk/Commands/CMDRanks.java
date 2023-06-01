package me.dxrk.Commands;

import me.dxrk.Main.Methods;
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
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CMDRanks implements Listener, CommandExecutor {
    static Methods m = Methods.getInstance();
    SettingsManager settings = SettingsManager.getInstance();


    /*
    Ranks List:
    Sponsor
    VIP
    MVP
    Hero
    Demi-God
    Titan
    God
    Olympian
    Genesis
     */


    public static ItemStack rankItem(String name) {
        ItemStack rank = new ItemStack(Material.NETHER_STAR);
        ItemMeta rm = rank.getItemMeta();
        rm.setDisplayName(m.c(name));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Right Click this item to Redeem."));
        rm.setLore(lore);
        rank.setItemMeta(rm);
        return rank;
    }


    public void giveRank(Player p, String rank) {
        p.getInventory().addItem(rankItem(rank));
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("giverank")) {
            if (args.length == 3) {
                Player p = Bukkit.getPlayer(args[0]);
                giveRank(p, args[1] + " " + args[2]);
            }
        }


        return false;
    }

    private List<String> ranks() {
        List<String> ranks = new ArrayList<>();
        ranks.add("Sponsor");
        ranks.add("VIP");
        ranks.add("MVP");
        ranks.add("Hero");
        ranks.add("Demi-God");
        ranks.add("Titan");
        ranks.add("God");
        ranks.add("Olympian");
        ranks.add("Genesis");
        return ranks;
    }


    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) return;

        if (p.getItemInHand().getType().equals(Material.NETHER_STAR)) {
            if (p.getItemInHand().hasItemMeta()) {
                if ((e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) && ranks().contains(ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName().split(" ")[0]))) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "manuaddsub " + p.getName() + " " + ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName().split(" ")[0]));
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &e&l" + p.getName() + " &f&lHas redeemed " + p.getItemInHand().getItemMeta().getDisplayName());
                    if (p.getItemInHand().getAmount() == 1) {
                        p.setItemInHand(null);
                    } else {
                        int amount = p.getItemInHand().getAmount();
                        p.getItemInHand().setAmount(amount - 1);
                    }
                }
            }
        }


    }


}
