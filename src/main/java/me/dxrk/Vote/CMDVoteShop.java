package me.dxrk.Vote;


import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CMDVoteShop implements Listener, CommandExecutor {

    public static SettingsManager settings = SettingsManager.getInstance();

    public Methods m = Methods.getInstance();


    public ItemStack Spacer() {
        ItemStack i = new ItemStack(Material.PURPLE_STAINED_GLASS_PANE);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(m.c("&9Genesis&bVote"));
        im.addEnchant(Enchantment.UNBREAKING, 3, true);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        i.setItemMeta(im);
        return i;
    }


    public void openVS(Player p) {
        double amount = getCoupons(p);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        Inventory voteshop = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&d&lCoupons: &a" + formatter.format(amount)));


        String coupon = formatter.format(amount);
        ItemStack item = new ItemStack(Material.PAPER);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c("&7Withdraw up to &b" + coupon + "&7."));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7&oClick to withdraw"));
        lore.add(m.c("&7&oEnter the amount you want to withdraw and hit Done."));
        im.setLore(lore);
        item.setItemMeta(im);
        voteshop.setItem(2, item);
        p.openInventory(voteshop);
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("coupon") || cmd.getName().equalsIgnoreCase("coupons")) {
            openVS((Player) sender);

        }
        if (cmd.getName().equalsIgnoreCase("addvotepoint") || sender.hasPermission("rank.admin")) {
            if (args.length == 2) {
                Player p = Bukkit.getPlayer(args[0]);
                int points = Integer.parseInt(args[1]);
                addCoupon(p, points);
            }
        }

        return false;
    }

    public int getPrice(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor(s);
        byte b;
        int i;
        char[] arrayOfChar;
        for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
            char c = arrayOfChar[b];
            if (isInt(c))
                lvl.append(c);
            b++;
        }
        if (isInt(lvl.toString()))
            return Integer.parseInt(lvl.toString());
        return -1;
    }


    public boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public static List<String> votelog = settings.getVote().getStringList("VoteShopLog");


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null)
            return;
        if (e.getView().getTitle() == null)
            return;
        double amount = getCoupons(p);
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "US"));
        if (e.getView().getTitle().equals(m.c("&d&lCoupons: &a" + formatter.format(amount)))) {
            e.setCancelled(true);
            if (e.getSlot() == 2) {
                if (getCoupons(p) <= 0) return;
                String[] text = new String[]{"", "Enter the Amount", "You want to", "Withdraw"};
                //SignGUI.openSignEditor(p, text);
            }


        }
    }

    /*@EventHandler
    public void signUpdate(SignGUIUpdateEvent e) throws IOException, ApiException {
        Player p = e.getPlayer();
        if (e.getSignText()[1].equals(m.c("Enter the Amount"))) {
            String l = e.getSignText()[0];
            l = l.replaceAll("\"", "");
            if (l.equals("") || l.equals(" ")) return;

            double amount = Double.parseDouble(l);
            if (amount > getCoupons(p) || amount < 0) {
                p.sendMessage(m.c("&cError: You do not have enough."));
                return;
            }
            BuycraftUtil.createCoupon(p, amount);
            removeCoupons(p, amount);
            settings.saveVote();
        }
    }*/


    public static void addCoupon(Player p, double coupon) {
        double vps = settings.getVote().getDouble(p.getUniqueId() + ".Coupons");
        double newvps = vps + coupon;

        settings.getVote().set(p.getUniqueId() + ".Coupons", newvps);
        settings.saveVote();
    }

    public double getCoupons(Player p) {
        if (!settings.getVote().contains(p.getUniqueId().toString()))
            return 0;
        double coupons = settings.getVote().getDouble(p.getUniqueId() + ".Coupons");
        return coupons;
    }

    public void removeCoupons(Player p, double i) {
        double vps = settings.getVote().getDouble(p.getUniqueId() + ".Coupons");
        double newvps = vps - i;

        settings.getVote().set(p.getUniqueId() + ".Coupons", newvps);
        settings.saveVote();
    }


}
