package me.dxrk.Events;

import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Main;
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
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.*;

public class AuctionHouseHandler implements Listener, CommandExecutor {

    public static AuctionHouseHandler instance = new AuctionHouseHandler();

    public static AuctionHouseHandler getInstance() {
        return instance;
    }


    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();
    Tokens tokens = Tokens.getInstance();

    private HashMap<Player, String> ahorder = new HashMap<>();
    public static HashMap<UUID, List<ItemStack>> personalItems = new HashMap<>();
    //public static List<ItemStack> ahitems = new ArrayList<>();


    public void saveAH() {
        for (UUID p : personalItems.keySet()) {
            PlayerDataHandler.getInstance().getPlayerData(p).set("AHListings", personalItems.get(p));
            PlayerDataHandler.getInstance().savePlayerData(p);
        }
    }

    public void loadAH() {
        File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
        File[] var = mineFiles;
        assert mineFiles != null;
        int amountOfMines = mineFiles.length;
        for (int i = 0; i < amountOfMines; ++i) {
            File mineFile = var[i];
            String name = mineFile.getName().split("\\.")[0];
            UUID id = UUID.fromString(name);
            if (PlayerDataHandler.getInstance().getPlayerData(id).get("AHListings") != null && !((List<ItemStack>) PlayerDataHandler.getInstance().getPlayerData(id).get("AHListings")).isEmpty())
                personalItems.put(id, (List<ItemStack>) PlayerDataHandler.getInstance().getPlayerData(id).get("AHListings"));
        }
    }

    public void addItems(List<ItemStack> ahitems) {
        ahitems.clear();
        for (UUID p : personalItems.keySet()) {
            if (p != null)
                ahitems.addAll(personalItems.get(p));
        }
    }

    public boolean ahHasItem(ItemStack item) {
        List<ItemStack> ahitems = new ArrayList<>();
        addItems(ahitems);
        return ahitems.contains(item);
    }

    public void openAuctionHouse(Player p, int page, String sortMethod) {
        List<ItemStack> ahitems = new ArrayList<>();
        addItems(ahitems);
        Inventory ah = Bukkit.createInventory(null, 54, m.c("&c&lAuction House Page " + page));
        ahorder.put(p, sortMethod);
        switch (sortMethod) {
            case "none":
                if (page > 1) {
                    if (ahitems.size() <= (page - 1) * 45) {
                        return;
                    }
                }

                for (int i = 0; i < page * 45; i++) {
                    if (page > 1) {
                        if (i < (page - 1) * 45) {
                            continue;
                        }
                    }
                    if (ahitems.size() >= i + 1)
                        ah.setItem(i, ahitems.get(i));
                }
                break;
            case "hightolow": {
                SortedMap<Double, ItemStack> orderhightolow = new TreeMap<>(Collections.reverseOrder());
                for (ItemStack item : ahitems) {
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
                    if (list.size() >= i + 1)
                        ah.setItem(i, list.get(i).getValue());
                }
                break;
            }
            case "lowtohigh": {
                SortedMap<Double, ItemStack> orderlowtohigh = new TreeMap<>();
                for (ItemStack item : ahitems) {
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
                    if (list.size() >= i + 1)
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

        ItemStack hightolow = new ItemStack(Material.FIRE_CHARGE);
        ItemMeta hm = hightolow.getItemMeta();
        hm.setDisplayName(m.c("&3Sort: Price High to Low"));
        hightolow.setItemMeta(hm);
        ah.setItem(48, hightolow);

        ItemStack openSelling = new ItemStack(Material.CHEST);
        ItemMeta om = openSelling.getItemMeta();
        om.setDisplayName(m.c("&cYour Items:"));
        openSelling.setItemMeta(om);
        ah.setItem(49, openSelling);

        ItemStack lowtohigh = new ItemStack(Material.FIRE_CHARGE);
        ItemMeta lm = lowtohigh.getItemMeta();
        lm.setDisplayName(m.c("&3Sort: Price Low to High"));
        lowtohigh.setItemMeta(lm);
        ah.setItem(50, lowtohigh);


        p.openInventory(ah);

    }

    public void openSelling(Player p) {
        Inventory selling = Bukkit.createInventory(null, 9, m.c("&c&lYour Items:"));
        for (ItemStack i : personalItems.get(p.getUniqueId())) {
            selling.addItem(i);
        }
        p.openInventory(selling);
    }

    public void openConfirm(Player p, ItemStack item) {
        Inventory confirm = Bukkit.createInventory(null, 9, m.c("&c&lConfirm: How many do you want to purchase?"));
        ItemStack spacer = new ItemStack(Material.IRON_FENCE);
        ItemMeta sm = spacer.getItemMeta();
        sm.setDisplayName(m.c("&c&lGenesis &b&lPrison"));
        spacer.setItemMeta(sm);
        confirm.setItem(0, item);
        confirm.setItem(1, spacer);
        confirm.setItem(7, spacer);
        confirm.setItem(8, item);

        int line = 0;
        for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
            if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Cost:")) {
                line = i;
            }
        }

        double cost = Double.parseDouble(ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1]);

        ItemStack buy1 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta bm1 = buy1.getItemMeta();
        bm1.setDisplayName(m.c("&aBuy 1"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&cCost: &e⛀" + cost));
        bm1.setLore(lore);
        buy1.setItemMeta(bm1);
        confirm.setItem(2, buy1);
        lore.clear();

        ItemStack buy5 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta bm5 = buy5.getItemMeta();
        bm5.setDisplayName(m.c("&aBuy 5"));
        lore.add(m.c("&cCost: &e⛀" + cost * 5));
        bm5.setLore(lore);
        buy5.setItemMeta(bm5);
        confirm.setItem(3, buy5);
        lore.clear();

        ItemStack cancel = new ItemStack(Material.INK_SAC, 1, (short) 1);
        ItemMeta cm = cancel.getItemMeta();
        cm.setDisplayName(m.c("&cCancel"));
        cancel.setItemMeta(cm);
        confirm.setItem(4, cancel);

        ItemStack buy10 = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta bm10 = buy10.getItemMeta();
        bm10.setDisplayName(m.c("&aBuy 10"));
        lore.add(m.c("&cCost: &e⛀" + cost * 10));
        bm10.setLore(lore);
        buy10.setItemMeta(bm10);
        confirm.setItem(5, buy10);
        lore.clear();

        ItemStack buyA = new ItemStack(Material.RED_STAINED_GLASS_PANE);
        ItemMeta bmA = buyA.getItemMeta();
        bmA.setDisplayName(m.c("&aBuy All"));

        for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
            if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Amount:")) {
                line = i;
            }
        }
        int amount = Integer.parseInt(ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split(" ")[1]);
        lore.add(m.c("&cCost: &e⛀" + cost * amount));
        bmA.setLore(lore);
        buyA.setItemMeta(bmA);
        confirm.setItem(6, buyA);
        lore.clear();

        p.openInventory(confirm);

    }

    public void orderConfig() {
        boolean reorder = false;
        for (int i = 0; i < 500; i++) {
            ItemStack itemi = settings.getAH().getItemStack("Items." + i);
            if (itemi == null) {
                break;
            }
            if (reorder == true) {
                ItemStack item = settings.getAH().getItemStack("Items." + i);
                settings.getAH().set("Items." + (i - 1), item);
                continue;
            }
            if (settings.getAH().get("Items." + i) == null) {
                reorder = true;
            }
        }
        settings.saveAH();
    }


    public void giveTokens(UUID uuid, double tokens) {
        double has = PlayerDataHandler.getInstance().getPlayerData(uuid).getInt("Tokens");
        PlayerDataHandler.getInstance().getPlayerData(uuid).set("Tokens", has + tokens);
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) return;
        if (e.getCurrentItem() == null) return;

        if (e.getView().getTitle().equals(m.c("&c&lYour Items:"))) {
            e.setCancelled(true);
            if (personalItems.get(p.getUniqueId()).contains(e.getCurrentItem())) {
                Inventory remove = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&c&lRemove Listing?"));
                ItemStack spacer = new ItemStack(Material.IRON_FENCE);
                ItemMeta sm = spacer.getItemMeta();
                sm.setDisplayName(m.c("&c&lGenesis &b&lPrison"));
                spacer.setItemMeta(sm);
                remove.setItem(0, spacer);
                remove.setItem(2, e.getCurrentItem());
                remove.setItem(4, spacer);
                ItemStack yes = new ItemStack(Material.WOOL, 1, (short) 5);
                ItemMeta ym = yes.getItemMeta();
                ym.setDisplayName(m.c("&a&lYes"));
                yes.setItemMeta(ym);
                remove.setItem(1, yes);
                ItemStack no = new ItemStack(Material.WOOL, 1, (short) 14);
                ItemMeta nm = no.getItemMeta();
                nm.setDisplayName(m.c("&c&lNo"));
                no.setItemMeta(nm);
                remove.setItem(3, no);
                p.openInventory(remove);
            }
        }
        if (e.getView().getTitle().equals(m.c("&c&lRemove Listing?"))) {
            e.setCancelled(true);
            if (e.getSlot() == 1) {
                personalItems.get(p.getUniqueId()).remove(e.getClickedInventory().getItem(2));
                openSelling(p);
            }
            if (e.getSlot() == 3) {
                openSelling(p);
            }
        }


        if (e.getView().getTitle().contains(m.c("&c&lAuction House Page "))) {
            e.setCancelled(true);
            if (e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }

            if (e.getSlot() > 44) {
                if (e.getSlot() == 46) {
                    if (e.getView().title().equals(m.c("&c&lAuction House Page 1"))) {
                        return;
                    } else {
                        int page = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getView().getTitle()));
                        openAuctionHouse(p, page - 1, ahorder.get(p));
                    }
                }
                if (e.getSlot() == 52) {
                    int page = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(e.getView().getTitle()));
                    openAuctionHouse(p, page + 1, ahorder.getOrDefault(p, "none"));
                }
                if (e.getSlot() == 48) {
                    ahorder.put(p, "hightolow");
                    openAuctionHouse(p, 1, "hightolow");
                }
                if (e.getSlot() == 49) {
                    openSelling(p);
                }
                if (e.getSlot() == 50) {
                    ahorder.put(p, "lowtohigh");
                    openAuctionHouse(p, 1, "lowtohigh");
                }
            } else {
                ItemStack item = e.getCurrentItem();
                if (item == null) return;
                if (item.getType().equals(Material.AIR)) return;

                int line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Seller:")) {
                        line = i;
                    }
                }
                String name = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split(" ")[1];
                if (p.getName().equals(name)) {
                    openSelling(p);
                    return;
                }
                openConfirm(p, item);

            }
        }
        if (e.getView().getTitle().equals(m.c("&c&lConfirm: How many do you want to purchase?"))) {
            e.setCancelled(true);
            if (e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }
            if (e.getSlot() == 4) {
                openAuctionHouse(p, 1, ahorder.getOrDefault(p, "none"));
            }
            if (e.getSlot() == 2) {
                ItemStack item = e.getInventory().getItem(0).clone();
                if (!ahHasItem(item)) {
                    p.closeInventory();
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &bListing No longer exists"));
                    return;
                }
                double tokens = this.tokens.getTokens(p);
                int line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Cost:")) {
                        line = i;
                    }
                }
                String s = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1];
                double price = Double.parseDouble(s);
                if (tokens < price) {
                    p.sendMessage(m.c("&cError: Not Enough Tokens"));
                    p.closeInventory();
                    return;
                }
                line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Seller:")) {
                        line = i;
                    }
                }
                String seller = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split(" ")[1];
                if (seller.equals(p.getName())) {
                    openSelling(p);
                    return;
                }

                this.tokens.takeTokens(p, price);

                File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
                File[] var = mineFiles;
                assert mineFiles != null;
                int amountOfMines = mineFiles.length;
                for (int i = 0; i < amountOfMines; ++i) {
                    File mineFile = var[i];
                    String name = mineFile.getName().split("\\.")[0];
                    UUID id = UUID.fromString(name);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(name));
                    if (player.getName().equalsIgnoreCase(seller)) {
                        giveTokens(UUID.fromString(name), price);
                    }
                }
                for (UUID player : personalItems.keySet()) {
                    List<ItemStack> items = personalItems.get(player);
                    items.remove(item);
                    ItemStack newItem = item.clone();
                    int newline = 0;
                    for (int i = 0; i < newItem.getItemMeta().getLore().size(); i++) {
                        if (ChatColor.stripColor(newItem.getItemMeta().getLore().get(i)).contains("Amount:")) {
                            newline = i;
                        }
                    }
                    int amount = Integer.parseInt(ChatColor.stripColor(newItem.getItemMeta().getLore().get(newline)).split(" ")[1]);
                    if (amount > 1) {
                        ItemMeta nim = newItem.getItemMeta();
                        List<String> lore = nim.getLore();
                        lore.set(newline, m.c("&7Amount: &b" + (amount - 1)));
                        nim.setLore(lore);
                        newItem.setItemMeta(nim);
                        items.add(newItem);
                        personalItems.put(m.getPlayer(seller).getUniqueId(), items);
                    }
                }
                ItemMeta im = item.getItemMeta();
                List<String> lore = im.getLore();

                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).equals(" ")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Amount:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Seller:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Cost:")) {
                        line = i;
                        lore.remove(line);
                    }
                }

                im.setLore(lore);
                item.setItemMeta(im);
                p.getInventory().addItem(item);
                ahorder.remove(p);
                p.closeInventory();


            }
            if (e.getSlot() == 2) {
                ItemStack item = e.getInventory().getItem(0).clone();
                if (!ahHasItem(item)) {
                    p.closeInventory();
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &bListing No longer exists"));
                    return;
                }
                double tokens = this.tokens.getTokens(p);
                int line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Cost:")) {
                        line = i;
                    }
                }
                String s = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1];
                double price = Double.parseDouble(s);
                if (tokens < price) {
                    p.sendMessage(m.c("&cError: Not Enough Tokens"));
                    p.closeInventory();
                    return;
                }
                line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Seller:")) {
                        line = i;
                    }
                }
                String seller = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split(" ")[1];
                if (seller.equals(p.getName())) {
                    openSelling(p);
                    return;
                }

                this.tokens.takeTokens(p, price);

                File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
                File[] var = mineFiles;
                assert mineFiles != null;
                int amountOfMines = mineFiles.length;
                for (int i = 0; i < amountOfMines; ++i) {
                    File mineFile = var[i];
                    String name = mineFile.getName().split("\\.")[0];
                    UUID id = UUID.fromString(name);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(name));
                    if (player.getName().equalsIgnoreCase(seller)) {
                        giveTokens(UUID.fromString(name), price);
                    }
                }
                for (UUID player : personalItems.keySet()) {
                    List<ItemStack> items = personalItems.get(player);
                    items.remove(item);
                    ItemStack newItem = item.clone();
                    int newline = 0;
                    for (int i = 0; i < newItem.getItemMeta().getLore().size(); i++) {
                        if (ChatColor.stripColor(newItem.getItemMeta().getLore().get(i)).contains("Amount:")) {
                            newline = i;
                        }
                    }
                    int amount = Integer.parseInt(ChatColor.stripColor(newItem.getItemMeta().getLore().get(newline)).split(" ")[1]);
                    if (amount > 5) {
                        ItemMeta nim = newItem.getItemMeta();
                        List<String> lore = nim.getLore();
                        lore.set(newline, m.c("&7Amount: &b" + (amount - 5)));
                        nim.setLore(lore);
                        newItem.setItemMeta(nim);
                        items.add(newItem);
                        personalItems.put(m.getPlayer(seller).getUniqueId(), items);
                    }
                }
                ItemMeta im = item.getItemMeta();
                List<String> lore = im.getLore();

                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).equals(" ")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Amount:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Seller:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Cost:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                item.setAmount(5);
                im.setLore(lore);
                item.setItemMeta(im);
                p.getInventory().addItem(item);
                ahorder.remove(p);
                p.closeInventory();


            }
            if (e.getSlot() == 5) {
                ItemStack item = e.getInventory().getItem(0).clone();
                if (!ahHasItem(item)) {
                    p.closeInventory();
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &bListing No longer exists"));
                    return;
                }
                double tokens = this.tokens.getTokens(p);
                int line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Cost:")) {
                        line = i;
                    }
                }
                String s = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1];
                double price = Double.parseDouble(s);
                if (tokens < price) {
                    p.sendMessage(m.c("&cError: Not Enough Tokens"));
                    p.closeInventory();
                    return;
                }
                line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Seller:")) {
                        line = i;
                    }
                }
                String seller = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split(" ")[1];
                if (seller.equals(p.getName())) {
                    openSelling(p);
                    return;
                }

                this.tokens.takeTokens(p, price);

                File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
                File[] var = mineFiles;
                assert mineFiles != null;
                int amountOfMines = mineFiles.length;
                for (int i = 0; i < amountOfMines; ++i) {
                    File mineFile = var[i];
                    String name = mineFile.getName().split("\\.")[0];
                    UUID id = UUID.fromString(name);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(name));
                    if (player.getName().equalsIgnoreCase(seller)) {
                        giveTokens(UUID.fromString(name), price);
                    }
                }
                for (UUID player : personalItems.keySet()) {
                    List<ItemStack> items = personalItems.get(player);
                    items.remove(item);
                    ItemStack newItem = item.clone();
                    int newline = 0;
                    for (int i = 0; i < newItem.getItemMeta().getLore().size(); i++) {
                        if (ChatColor.stripColor(newItem.getItemMeta().getLore().get(i)).contains("Amount:")) {
                            newline = i;
                        }
                    }
                    int amount = Integer.parseInt(ChatColor.stripColor(newItem.getItemMeta().getLore().get(newline)).split(" ")[1]);
                    if (amount > 10) {
                        ItemMeta nim = newItem.getItemMeta();
                        List<String> lore = nim.getLore();
                        lore.set(newline, m.c("&7Amount: &b" + (amount - 10)));
                        nim.setLore(lore);
                        newItem.setItemMeta(nim);
                        items.add(newItem);
                        personalItems.put(m.getPlayer(seller).getUniqueId(), items);
                    }
                }
                ItemMeta im = item.getItemMeta();
                List<String> lore = im.getLore();

                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).equals(" ")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Amount:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Seller:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Cost:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                item.setAmount(10);
                im.setLore(lore);
                item.setItemMeta(im);
                p.getInventory().addItem(item);
                ahorder.remove(p);
                p.closeInventory();


            }
            if (e.getSlot() == 6) {
                ItemStack item = e.getInventory().getItem(0).clone();
                if (!ahHasItem(item)) {
                    p.closeInventory();
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &bListing No longer exists"));
                    return;
                }
                double tokens = this.tokens.getTokens(p);
                int line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Cost:")) {
                        line = i;
                    }
                }
                String s = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split("⛀")[1];
                double price = Double.parseDouble(s);
                if (tokens < price) {
                    p.sendMessage(m.c("&cError: Not Enough Tokens"));
                    p.closeInventory();
                    return;
                }
                line = 0;
                for (int i = 0; i < item.getItemMeta().getLore().size(); i++) {
                    if (ChatColor.stripColor(item.getItemMeta().getLore().get(i)).contains("Seller:")) {
                        line = i;
                    }
                }
                String seller = ChatColor.stripColor(item.getItemMeta().getLore().get(line)).split(" ")[1];
                if (seller.equals(p.getName())) {
                    openSelling(p);
                    return;
                }

                this.tokens.takeTokens(p, price);

                File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
                File[] var = mineFiles;
                assert mineFiles != null;
                int amountOfMines = mineFiles.length;
                for (int i = 0; i < amountOfMines; ++i) {
                    File mineFile = var[i];
                    String name = mineFile.getName().split("\\.")[0];
                    UUID id = UUID.fromString(name);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(name));
                    if (player.getName().equalsIgnoreCase(seller)) {
                        giveTokens(UUID.fromString(name), price);
                    }
                }
                for (UUID player : personalItems.keySet()) {
                    List<ItemStack> items = personalItems.get(player);
                    items.remove(item);
                    ItemStack newItem = item.clone();
                    int newline = 0;
                    for (int i = 0; i < newItem.getItemMeta().getLore().size(); i++) {
                        if (ChatColor.stripColor(newItem.getItemMeta().getLore().get(i)).contains("Amount:")) {
                            newline = i;
                        }
                    }
                    int amount = Integer.parseInt(ChatColor.stripColor(newItem.getItemMeta().getLore().get(newline)).split(" ")[1]);
                    if (amount > 50) {
                        ItemMeta nim = newItem.getItemMeta();
                        List<String> lore = nim.getLore();
                        lore.set(newline, m.c("&7Amount: &b" + (amount - 50)));
                        nim.setLore(lore);
                        newItem.setItemMeta(nim);
                        items.add(newItem);
                        personalItems.put(m.getPlayer(seller).getUniqueId(), items);
                    }
                }
                ItemMeta im = item.getItemMeta();
                List<String> lore = im.getLore();

                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).equals(" ")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Amount:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Seller:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                for (int i = 0; i < lore.size(); i++) {
                    if (ChatColor.stripColor(lore.get(i)).contains("Cost:")) {
                        line = i;
                        lore.remove(line);
                    }
                }
                item.setAmount(50);
                im.setLore(lore);
                item.setItemMeta(im);
                p.getInventory().addItem(item);
                ahorder.remove(p);
                p.closeInventory();


            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ah") || cmd.getName().equalsIgnoreCase("auctionhouse")) {
            Player p = (Player) sender;
            boolean b = PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("BuildMode");
            if(b){
                p.sendMessage(m.c("&cYou can't access this while in buildmode."));
                return false;
            }
            if (args.length == 0) {
                ahorder.put(p, "none");
                openAuctionHouse(p, 1, "none");
            }
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("sell")) {
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &7/ah sell <amount> <price per item>."));
                }
                if (args[0].equalsIgnoreCase("help")) {
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &7/ah sell <amount> <price per item>."));
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("sell")) {
                    p.sendMessage(m.c("&f&lAuctionHouse &8| &7/ah sell <amount> <price per item>."));
                }
            }
            if (args.length == 3) {
                if (args[0].equalsIgnoreCase("sell")) {
                    double tokens = Double.parseDouble(args[2]);
                    if (tokens < 0) {
                        p.sendMessage(m.c("&cError: Cannot parse number."));
                        return false;
                    }
                    int amt = Integer.parseInt(args[1]);
                    if (p.getEquipment().getItemInMainHand().getType().equals(Material.DIAMOND_PICKAXE) || p.getEquipment().getItemInMainHand().getType().equals(Material.IRON_PICKAXE) || p.getEquipment().getItemInMainHand().getType().equals(Material.GOLDEN_PICKAXE)
                            || p.getEquipment().getItemInMainHand().getType().equals(Material.STONE_PICKAXE) || p.getEquipment().getItemInMainHand().getType().equals(Material.WOODEN_PICKAXE)) {
                        p.sendMessage(m.c("&cError: You cannot sell your pickaxe"));
                        return false;
                    }
                    if (p.getEquipment().getItemInMainHand() == null || p.getEquipment().getItemInMainHand().getType().equals(Material.AIR)) return false;
                    int pamt = p.getEquipment().getItemInMainHand().getAmount();
                    if (pamt < amt) {
                        p.sendMessage(m.c("&cError: You do not have enough of that item."));
                        return false;
                    }
                    ItemStack item = p.getEquipment().getItemInMainHand().clone();
                    ItemMeta im = item.getItemMeta();
                    item.setAmount(1);
                    List<String> lore = new ArrayList<>();
                    if (im.hasLore()) {
                        lore = im.getLore();
                    }
                    lore.add(" ");
                    lore.add(m.c("&7Amount: &b" + amt));
                    lore.add(m.c("&7Cost: &e⛀" + tokens));
                    lore.add(m.c("&7Seller: &f" + p.getName()));
                    im.setLore(lore);
                    item.setItemMeta(im);

                    //ADD ITEM TO PERSONAL ITEMS TO THEN GET ADDED
                    List<ItemStack> items = new ArrayList<>();
                    if (personalItems.containsKey(p.getUniqueId())) {
                        items = personalItems.get(p.getUniqueId());
                    }
                    if (items.size() <= 3) {
                        items.add(item);
                        personalItems.put(p.getUniqueId(), items);
                        p.sendMessage(m.c("&f&lAuctionHouse &8| &bItem(s) Listed"));
                    } else {
                        p.sendMessage(m.c("&f&lAuctionHouse &8| &bYou cannot list anymore items"));
                        return false;
                    }
                    if (amt == pamt) {
                        p.setItemInHand(null);
                    }
                    if (amt < pamt) {
                        p.getEquipment().getItemInMainHand().setAmount(p.getEquipment().getItemInMainHand().getAmount() - amt);
                    }


                }
            }
        }


        return false;
    }


}
