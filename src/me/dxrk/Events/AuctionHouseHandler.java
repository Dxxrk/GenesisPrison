package me.dxrk.Events;

import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class AuctionHouseHandler implements Listener, CommandExecutor {
    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();
    Tokens tokens = Tokens.getInstance();

    private HashMap<Player, String> ahorder = new HashMap<>();

    public void openAuctionHouse(Player p, int page, String sortMethod) {
        Inventory ah = Bukkit.createInventory(null, 54, m.c("&c&lAuction House Page "+page));
        ahorder.put(p, sortMethod);
        switch (sortMethod) {
            case "none":
                List<ItemStack> items = new ArrayList<>();

                for (int i = 0; i < 10000; i++) {
                    ItemStack item = settings.getAH().getItemStack("Items." + i);
                    if (item == null) break;
                    items.add(item);
                }
                if (page > 1) {
                    if (items.size() <= (page - 1) * 45) {
                        return;
                    }
                }

                for (int i = 0; i < page * 45; i++) {
                    if (page > 1) {
                        if (i < (page - 1) * 45) {
                            continue;
                        }
                    }
                    if(items.size() >= i+1)
                        ah.setItem(i, items.get(i));
                }
                break;
            case "hightolow": {
                SortedMap<Double, ItemStack> orderhightolow = new TreeMap<>(Collections.reverseOrder());
                for (int i = 0; i < 10000; i++) {
                    ItemStack item = settings.getAH().getItemStack("Items." + i);
                    if (item == null) break;
                    int line = 0;
                    for (int ii = 0; ii < item.getItemMeta().getLore().size(); ii++) {
                        if (ChatColor.stripColor(item.getItemMeta().getLore().get(ii)).contains("Cost:")) {
                            line = ii;
                        }
                    }
                    String s = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1];
                    double price = Double.parseDouble(s);
                    orderhightolow.put(price, item);
                }
                Set<Map.Entry<Double, ItemStack>> entrySet = orderhightolow.entrySet();
                List<Map.Entry<Double, ItemStack>> list = new ArrayList<>(entrySet);
                if (page > 1) {
                    if (list.size() <= (page - 1) * 45) {
                        return;
                    }
                }

                for (int i = 0; i < page * 45; i++) {
                    if (page > 1) {
                        if (i < (page - 1) * 45) {
                            continue;
                        }
                    }
                    if(list.size() >= i+1)
                        ah.setItem(i, list.get(i).getValue());
                }
                break;
            }
            case "lowtohigh": {
                SortedMap<Double, ItemStack> orderlowtohigh = new TreeMap<>();
                for (int i = 0; i < 10000; i++) {
                    ItemStack item = settings.getAH().getItemStack("Items." + i);
                    if (item == null) break;
                    int line = 0;
                    for (int ii = 0; ii < item.getItemMeta().getLore().size(); ii++) {
                        if (ChatColor.stripColor(item.getItemMeta().getLore().get(ii)).contains("Cost:")) {
                            line = ii;
                        }
                    }
                    String s = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1];
                    double price = Double.parseDouble(s);
                    orderlowtohigh.put(price, item);
                }
                Set<Map.Entry<Double, ItemStack>> entrySet = orderlowtohigh.entrySet();
                List<Map.Entry<Double, ItemStack>> list = new ArrayList<>(entrySet);
                if (page > 1) {
                    if (list.size() <= (page - 1) * 45) {
                        return;
                    }
                }

                for (int i = 0; i < page * 45; i++) {
                    if (page > 1) {
                        if (i < (page - 1) * 45) {
                            continue;
                        }
                    }
                    if(list.size() >= i+1)
                        ah.setItem(i, list.get(i).getValue());
                }
                break;
            }
        }
        ItemStack spacer = new ItemStack(Material.IRON_FENCE);
        ItemMeta sm = spacer.getItemMeta();
        sm.setDisplayName(m.c("&c&lGenesis &b&lPrison"));
        spacer.setItemMeta(sm);
        ah.setItem(45, spacer);
        ah.setItem(47, spacer);
        ah.setItem(49, spacer);
        ah.setItem(51, spacer);
        ah.setItem(53, spacer);

        ItemStack previous = new ItemStack(Material.ARROW);
        ItemMeta pm = previous.getItemMeta();
        pm.setDisplayName(m.c("&b<- Previous Page"));
        previous.setItemMeta(pm);
        ah.setItem(46, previous);

        ItemStack next = new ItemStack(Material.ARROW);
        ItemMeta nm = next.getItemMeta();
        nm.setDisplayName(m.c("&bNext Page ->"));
        next.setItemMeta(nm);
        ah.setItem(52, next);

        ItemStack hightolow = new ItemStack(Material.FIREBALL);
        ItemMeta hm = hightolow.getItemMeta();
        hm.setDisplayName(m.c("&3Sort: Price High to Low"));
        hightolow.setItemMeta(hm);
        ah.setItem(48, hightolow);

        ItemStack lowtohigh = new ItemStack(Material.FIREBALL);
        ItemMeta lm = lowtohigh.getItemMeta();
        lm.setDisplayName(m.c("&3Sort: Price Low to High"));
        lowtohigh.setItemMeta(lm);
        ah.setItem(50, lowtohigh);


        p.openInventory(ah);

    }
    public void openConfirm(Player p, ItemStack item) {
        Inventory confirm = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&c&lConfirm: &aYes&7/&4No"));
        ItemStack spacer = new ItemStack(Material.IRON_FENCE);
        ItemMeta sm = spacer.getItemMeta();
        sm.setDisplayName(m.c("&c&lGenesis &b&lPrison"));
        spacer.setItemMeta(sm);
        confirm.setItem(0, spacer);
        confirm.setItem(2, item);
        confirm.setItem(4, spacer);

        ItemStack yes = new ItemStack(Material.WOOL, 1, (short)5);
        ItemMeta ym = yes.getItemMeta();
        ym.setDisplayName(m.c("&a&lYes"));
        yes.setItemMeta(ym);
        confirm.setItem(1, yes);

        ItemStack no = new ItemStack(Material.WOOL, 1, (short)14);
        ItemMeta nm = no.getItemMeta();
        nm.setDisplayName(m.c("&4&lNo"));
        no.setItemMeta(nm);
        confirm.setItem(3, no);

        p.openInventory(confirm);

    }

    public void orderConfig() {
        boolean reorder = false;
        for(int i = 0; i < 10000; i++) {
            ItemStack itemi = settings.getAH().getItemStack("Items."+i);
            if(itemi == null){
                break;
            }
            if(reorder == true) {
                ItemStack item = settings.getAH().getItemStack("Items."+i);
                settings.getAH().set("Items."+(i-1), item);
                continue;
            }
            if(settings.getAH().get("Items."+i) == null){
                reorder = true;
            }
        }
        settings.saveAH();
    }


    public void giveTokens(String uuid, double tokens) {
        double has = settings.getET().getInt(uuid);
        settings.getET().set(uuid, has+tokens);
        settings.saveEtFile();

    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() == null) return;

        if(e.getInventory().getName().contains(m.c("&c&lAuction House Page "))){
            e.setCancelled(true);
            if(e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }

            if(e.getSlot() > 44){
                if(e.getSlot() == 46){
                    if(e.getInventory().getName().equals(m.c("&c&lAuction House Page 1"))){
                        return;
                    }
                    else {
                        int page = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getInventory().getName()));
                        openAuctionHouse(p, page-1, ahorder.get(p));

                    }
                }
                if(e.getSlot() == 52){
                    int page = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getInventory().getName()));
                    openAuctionHouse(p, page + 1, ahorder.getOrDefault(p, "none"));
                }
                if(e.getSlot() == 48){
                    ahorder.put(p, "hightolow");
                    openAuctionHouse(p, 1, "hightolow");
                }
                if(e.getSlot() == 50){
                    ahorder.put(p, "lowtohigh");
                    openAuctionHouse(p, 1, "lowtohigh");
                }
            }
            else {
                ItemStack item = e.getCurrentItem();
                if(item == null) return;
                if(item.getType().equals(Material.AIR)) return;
                openConfirm(p, item);

            }
        }
        if(e.getInventory().getName().equals(m.c("&c&lConfirm: &aYes&7/&4No"))) {
            e.setCancelled(true);
            if(e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }
            if(e.getSlot() == 3){
                openAuctionHouse(p, 1, ahorder.getOrDefault(p, "none"));
            }
            if(e.getSlot() == 1){
                ItemStack item = e.getInventory().getItem(2).clone();
                double tokens = this.tokens.getTokens(p);
                int line = 0;
                for(int i = 0; i <item.getItemMeta().getLore().size(); i++) {
                    if(ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Cost:")){
                        line = i;
                    }
                }
                String s = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1];
                double price = Double.parseDouble(s);
                if(tokens <price) {
                    p.sendMessage(m.c("&cError: Not Enough Tokens"));
                    p.closeInventory();
                    return;
                }
                this.tokens.takeTokens(p, price);

                for(int i = 0; i < 10000; i++){
                    ItemStack ah = settings.getAH().getItemStack("Items."+i);
                    if(ah == null) continue;
                    if(ah.isSimilar(item)) {
                        settings.getAH().set("Items."+i, null);
                        orderConfig();
                        break;
                    }
                }

                ItemMeta im = item.getItemMeta();
                List<String> lore = im.getLore();
                lore.remove(line);
                for(int i = 0; i <lore.size(); i++) {
                    if(ChatColor.stripColor(lore.get(i)).contains("Seller:")){
                        line = i;
                    }
                }
                String ss = ChatColor.stripColor(lore.get(line)).split(" ")[1];
                for (String uuid : settings.getPlayerData().getKeys(false)) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                    if(player.getName().equalsIgnoreCase(ss)){
                        giveTokens(uuid, price);
                    }
                }


                lore.remove(line);
                for(int i = 0; i <lore.size(); i++) {
                    if(ChatColor.stripColor(lore.get(i)).equals(" ")){
                        line = i;
                        lore.remove(line);
                    }
                }

                for(int i = 0; i <lore.size(); i++) {
                    if(ChatColor.stripColor(lore.get(i)).contains("Amount:")){
                        line = i;
                        lore.remove(line);
                    }
                }

                im.setLore(lore);
                item.setItemMeta(im);
                p.getInventory().addItem(item);
                ahorder.remove(p);
                p.closeInventory();
                settings.saveAH();


            }
        }
    }



    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("ah") || cmd.getName().equalsIgnoreCase("auctionhouse")) {
            Player p = (Player) sender;
            if(args.length == 0){
                ahorder.put(p, "none");
                openAuctionHouse(p, 1, "none");
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("sell")) {
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &7/ah sell <amount> <price>"));
                }
            }
            if(args.length == 2){
                if(args[0].equalsIgnoreCase("sell")) {
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &7/ah sell <amount> <price>"));
                }
            }
            if(args.length == 3) {
                if(args[0].equalsIgnoreCase("sell")) {
                    double tokens = Double.parseDouble(args[2]);
                    int amt = Integer.parseInt(args[1]);
                    if(p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE) || p.getItemInHand().getType().equals(Material.IRON_PICKAXE) || p.getItemInHand().getType().equals(Material.GOLD_PICKAXE)
                    || p.getItemInHand().getType().equals(Material.STONE_PICKAXE) || p.getItemInHand().getType().equals(Material.WOOD_PICKAXE)) {
                        p.sendMessage(m.c("&cError: You cannot sell your pickaxe"));
                        return false;
                    }
                    int pamt = p.getItemInHand().getAmount();
                    if(pamt < amt){
                        p.sendMessage(m.c("&cError: You do not have enough of that item."));
                        return false;
                    }
                    ItemStack item = p.getItemInHand().clone();
                    ItemMeta im = item.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    if(im.hasLore()) {
                        lore = im.getLore();
                    }
                    lore.add(" ");
                    lore.add(m.c("&7Amount: &b"+amt));
                    lore.add(m.c("&7Cost: &e⛀"+tokens));
                    lore.add(m.c("&7Seller: &f"+p.getName()));
                    im.setLore(lore);
                    item.setItemMeta(im);

                    for(int i = 0; i < 10000; i++) {
                        if(settings.getAH().contains("Items."+i) == false || settings.getAH().get("Items."+i) == null){
                            settings.getAH().set("Items."+i, item);
                            settings.saveAH();
                            break;
                        }
                    }
                    if(amt == pamt) {
                        p.setItemInHand(null);
                    }
                    if(amt < pamt) {
                        p.getItemInHand().setAmount(p.getItemInHand().getAmount()-amt);
                    }




                }
            }
        }



        return false;
    }




}
