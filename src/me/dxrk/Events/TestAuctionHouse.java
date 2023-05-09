package me.dxrk.Events;

import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class TestAuctionHouse extends JavaPlugin implements Listener {

    private FileConfiguration auctionConfig;
    private File auctionFile;

    private Inventory auctionInventory;
    private List<AuctionItem> auctionItems;

    private boolean sortDescending = false;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        auctionItems = new ArrayList<>();
        auctionInventory = Bukkit.createInventory(null, 27, ChatColor.BOLD + "Auction House");
        auctionFile = new File(getDataFolder(), "auctionhouse.yml");
        if (!auctionFile.exists()) {
            try {
                auctionFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        auctionConfig = YamlConfiguration.loadConfiguration(auctionFile);
        loadAuctionItems();
        updateAuctionInventory();
    }

    private void loadAuctionItems() {
        ConfigurationSection itemsSection = auctionConfig.getConfigurationSection("auctionItems");
        if (itemsSection != null) {
            for (String key : itemsSection.getKeys(false)) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                ItemStack itemStack = itemSection.getItemStack("itemStack");
                double cost = itemSection.getDouble("cost");
                auctionItems.add(new AuctionItem(itemStack, cost));
            }
        }
    }

    private void updateAuctionInventory() {
        auctionInventory.clear();
        List<AuctionItem> sortedItems = new ArrayList<>(auctionItems);
        if (sortDescending) {
            Collections.sort(sortedItems, Collections.reverseOrder());
        } else {
            Collections.sort(sortedItems);
        }
        for (AuctionItem auctionItem : sortedItems) {
            auctionInventory.addItem(auctionItem.getItemStack());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory clickedInventory = event.getClickedInventory();
        ItemStack clickedItem = event.getCurrentItem();
        if (clickedInventory != null && clickedInventory.equals(auctionInventory) && clickedItem != null) {
            event.setCancelled(true);
            if (clickedItem.getType() == Material.AIR) {
                return;
            }
            AuctionItem auctionItem = getAuctionItemFromStack(clickedItem);
            if (auctionItem == null) {
                player.sendMessage(ChatColor.RED + "That item is not for sale!");
                return;
            }
            double cost = auctionItem.getCost();
            if (player.getInventory().firstEmpty() == -1) {
                player.sendMessage(ChatColor.RED + "Your inventory is full!");
                return;
            }
            if (Main.econ.getBalance(player) < cost) {
                player.sendMessage(ChatColor.RED + "You don't have enough money to purchase that item!");
                return;
            }
            Main.econ.withdrawPlayer(player, cost);
            auctionItems.remove(auctionItem);
            ItemStack purchasedItem = auctionItem.getItemStack();
            ItemMeta purchasedMeta = purchasedItem.getItemMeta();
            purchasedMeta.setLore(Collections.singletonList(ChatColor.GOLD + "Purchased for $" + cost));
            purchasedItem.setItemMeta(purchasedMeta);
            player.getInventory().addItem(purchasedItem);
            player.sendMessage(ChatColor.GREEN + "You have purchased " + ChatColor.WHITE + purchasedItem.getType().name() + ChatColor.GREEN + " for $" + cost);
            updateAuctionInventory();
            saveAuctionItems();
        }
    }

    private AuctionItem getAuctionItemFromStack(ItemStack itemStack) {
        for (AuctionItem auctionItem : auctionItems) {
            if (auctionItem.getItemStack().equals(itemStack)) {
                return auctionItem;
            }
        }
        return null;
    }

    private void saveAuctionItems() {
        auctionConfig.set("auctionItems", null);
        for (int i = 0; i < auctionItems.size(); i++) {
            ConfigurationSection itemSection = auctionConfig.createSection("auctionItems." + i);
            itemSection.set("itemStack", auctionItems.get(i).getItemStack());
            itemSection.set("cost", auctionItems.get(i).getCost());
        }
        try {
            auctionConfig.save(auctionFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("ah") || cmd.getName().equalsIgnoreCase("auctionhouse")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Only players can use this command!");
                return true;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                player.openInventory(auctionInventory);
                return true;
            }
            if (args[0].equalsIgnoreCase("add")) {
                if (!player.hasPermission("auctionhouse.add")) {
                    player.sendMessage(ChatColor.RED + "You do not have permission to add items to the auction house!");
                    return true;
                }
                if (args.length < 3) {
                    player.sendMessage(ChatColor.RED + "Usage: /ah add <cost> <amount>");
                    return true;
                }
                double cost;
                int amount;
                try {
                    cost = Double.parseDouble(args[1]);
                    amount = Integer.parseInt(args[2]);
                } catch (NumberFormatException e) {
                    player.sendMessage(ChatColor.RED + "Invalid cost or amount specified!");
                    return true;
                }
                ItemStack itemStack = player.getItemInHand();
                itemStack.setAmount(amount);
                player.getInventory().removeItem(itemStack);
                auctionItems.add(new AuctionItem(itemStack, cost));
                updateAuctionInventory();
                saveAuctionItems();
                player.sendMessage(ChatColor.GREEN + "Item added to auction house for $" + cost + " each.");
                return true;
            }
            if (args[0].equalsIgnoreCase("sort")) {
                sortDescending = !sortDescending;
                updateAuctionInventory();
                player.sendMessage(ChatColor.GREEN + "Auction house sorted " + (sortDescending ? "in descending order." : "in ascending order."));
                return true;
            }
            player.sendMessage(ChatColor.RED + "Invalid command usage! Usage: /ah [add <cost> <amount> | sort]");
            return true;
        }
        return false;
    }

}

class AuctionItem implements Comparable<AuctionItem> {

    private ItemStack itemStack;
    private double cost;

    AuctionItem(ItemStack itemStack, double cost) {
        this.itemStack = itemStack;
        this.cost = cost;
    }
    double getCost() {
        return cost;
    }

    ItemStack getItemStack() {
        return itemStack;
    }

    @Override
    public int compareTo(AuctionItem other) {
        return Double.compare(cost, other.cost);
    }

}