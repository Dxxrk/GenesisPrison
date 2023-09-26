package me.dxrk.Commands;

import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class CMDTrade implements Listener, CommandExecutor {

    public static HashMap<Player, String> trading = new HashMap<>();
    public static HashMap<String, List<ItemStack>> player1inv = new HashMap<>();
    public static HashMap<String, List<ItemStack>> player2inv = new HashMap<>();
    public static List<Player> player1 = new ArrayList<>();
    public static List<Player> player2 = new ArrayList<>();
    public static List<Player> waitingConfirm = new ArrayList<>();
    public static List<Player> waitingTrade = new ArrayList<>();
    public static List<Player> updating = new ArrayList<>();

    Methods m = Methods.getInstance();

    public String generateCode() {
        Random random = new Random();
        char[] cs = new char[9];
        for (int i = 0; i < cs.length; i++)
            cs[i] = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt("ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".length()));
        return new String(cs);
    }

    public void addAll(Player p2, Player p1, String code) {
        trading.put(p2, code);
        trading.put(p1, code);
        List<ItemStack> items1 = new ArrayList<>();
        List<ItemStack> items2 = new ArrayList<>();
        player1inv.put(code, items1);
        player2inv.put(code, items2);
        player1.add(p1);
        player2.add(p2);
        waitingTrade.remove(p1);
        waitingTrade.remove(p2);
        openTrade(p1);
        openTrade(p2);
    }


    public void openTrade(Player p) {
        Inventory trade = Bukkit.createInventory(null, 54, m.c("&a&lTrade:"));

        for (int i = 0; i < 54; i++) {
            if (!p1slot().contains(i) && !p2slot().contains(i))
                trade.setItem(i, PickaxeLevel.getInstance().Spacer());
        }

        trade.setItem(46, red());
        trade.setItem(49, cancel());
        trade.setItem(52, red());
        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_USE, 1.0f, 1.0f);
        p.openInventory(trade);

    }

    public void updateTrade(Player p, List<ItemStack> items1, List<ItemStack> items2, ItemStack i1, ItemStack i2) {
        Inventory trade = Bukkit.createInventory(null, 54, m.c("&a&lTrade:"));
        updating.add(p);

        for (int i = 0; i < 54; i++) {
            if (!p1slot().contains(i) && !p2slot().contains(i))
                trade.setItem(i, PickaxeLevel.getInstance().Spacer());
        }
        //Player1Inv
        if (items1.size() > 0)
            trade.setItem(10, items1.get(0));
        if (items1.size() > 1)
            trade.setItem(11, items1.get(1));
        if (items1.size() > 2)
            trade.setItem(12, items1.get(2));
        if (items1.size() > 3)
            trade.setItem(19, items1.get(3));
        if (items1.size() > 4)
            trade.setItem(20, items1.get(4));
        if (items1.size() > 5)
            trade.setItem(21, items1.get(5));
        if (items1.size() > 6)
            trade.setItem(28, items1.get(6));
        if (items1.size() > 7)
            trade.setItem(29, items1.get(7));
        if (items1.size() > 8)
            trade.setItem(30, items1.get(8));
        if (items1.size() > 9)
            trade.setItem(37, items1.get(9));
        if (items1.size() > 10)
            trade.setItem(38, items1.get(10));
        if (items1.size() > 11)
            trade.setItem(39, items1.get(11));
        //Player2Inv
        if (items2.size() > 0)
            trade.setItem(14, items2.get(0));
        if (items2.size() > 1)
            trade.setItem(15, items2.get(1));
        if (items2.size() > 2)
            trade.setItem(16, items2.get(2));
        if (items2.size() > 3)
            trade.setItem(23, items2.get(3));
        if (items2.size() > 4)
            trade.setItem(24, items2.get(4));
        if (items2.size() > 5)
            trade.setItem(25, items2.get(5));
        if (items2.size() > 6)
            trade.setItem(32, items2.get(6));
        if (items2.size() > 7)
            trade.setItem(33, items2.get(7));
        if (items2.size() > 8)
            trade.setItem(34, items2.get(8));
        if (items2.size() > 9)
            trade.setItem(41, items2.get(9));
        if (items2.size() > 10)
            trade.setItem(42, items2.get(10));
        if (items2.size() > 11)
            trade.setItem(43, items2.get(11));


        trade.setItem(46, i1);
        trade.setItem(49, cancel());
        trade.setItem(52, i2);
        p.openInventory(trade);

    }

    public List<Integer> p1slot() {
        List<Integer> i = new ArrayList<>();
        i.add(10);
        i.add(11);
        i.add(12);
        i.add(19);
        i.add(20);
        i.add(21);
        i.add(28);
        i.add(29);
        i.add(30);
        i.add(37);
        i.add(38);
        i.add(39);
        return i;
    }

    public List<Integer> p2slot() {
        List<Integer> i = new ArrayList<>();
        i.add(14);
        i.add(15);
        i.add(16);
        i.add(23);
        i.add(24);
        i.add(25);
        i.add(32);
        i.add(33);
        i.add(34);
        i.add(41);
        i.add(42);
        i.add(43);
        return i;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("trade")) {
            Player p = (Player) sender;
            boolean b = PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("BuildMode");
            if(b){
                p.sendMessage(m.c("&cYou can't access this while in buildmode."));
                return false;
            }
            if (args.length == 0) {
                p.sendMessage(m.c("&cPlease specify another player"));
                return false;
            }
            if (args.length == 1) {
                Player player2 = Bukkit.getPlayer(args[0]);
                if (player2.equals(p)) return false;
                if (trading.containsKey(player2) || waitingTrade.contains(player2)) {
                    p.sendMessage(m.c("&ePlayer is currently in a trade."));
                    return false;
                }
                if (waitingTrade.contains(p)) {
                    p.sendMessage(m.c("&eYou Currently have a trade request pending."));
                    return false;
                }

                p.sendMessage(m.c("&eTrade request sent to &6" + player2.getName()));
                player2.sendMessage(m.c("&eYou have recieved a trade request from &6" + p.getName()));
                player2.sendMessage(m.c("&eUse &6/trade accept " + p.getName() + " &eto accept it."));
                waitingTrade.add(p);
                waitingTrade.add(player2);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (waitingTrade.contains(p) && waitingTrade.contains(player2)) {
                            waitingTrade.remove(player2);
                            waitingTrade.remove(p);
                            p.sendMessage(m.c("&eTrade request to &6" + player2.getName() + " &ehas expired."));
                            player2.sendMessage(m.c("&eTrade request from &6" + p.getName() + " &ehas expired."));
                        }
                    }
                }.runTaskLater(Main.plugin, 20 * 60L);
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("accept")) {
                    String code = generateCode();
                    Player player1 = Bukkit.getPlayer(args[1]);
                    if (waitingTrade.contains(player1) && waitingTrade.contains(p)) {
                        addAll(p, player1, code);
                        return true;
                    }
                }
            }
            if (args.length == 2) {
                if (args[0].equalsIgnoreCase("decline")) {
                    Player player1 = Bukkit.getPlayer(args[1]);
                    if (waitingTrade.contains(player1) && waitingTrade.contains(p)) {
                        waitingTrade.remove(player1);
                        waitingTrade.remove(p);
                        p.sendMessage(m.c("&eTrade Declined."));
                        player1.sendMessage(m.c("&6" + p.getName() + " &eHas declined your trade."));
                    }
                }
            }
        }

        return false;
    }

    public ItemStack red() {
        ItemStack red = new ItemStack(Material.INK_SAC, 1, (short) 1);
        ItemMeta rm = red.getItemMeta();
        rm.setDisplayName(m.c("&eConfirm Trade"));
        red.setItemMeta(rm);
        return red;
    }

    public ItemStack green() {
        ItemStack green = new ItemStack(Material.INK_SAC, 1, (short) 10);
        ItemMeta gm = green.getItemMeta();
        gm.setDisplayName(m.c("&eTrade Confirmed"));
        green.setItemMeta(gm);
        return green;
    }

    public ItemStack cancel() {
        ItemStack cancel = new ItemStack(Material.INK_SAC, 1, (short) 8);
        ItemMeta cm = cancel.getItemMeta();
        cm.setDisplayName(m.c("&eCancel Trade"));
        cancel.setItemMeta(cm);
        return cancel;
    }


    @EventHandler
    public void onClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        String code = trading.get(p);
        Inventory inv = e.getInventory();
        if (updating.contains(p)) {
            updating.remove(p);
        } else if (e.getView().getTitle().equals(m.c("&a&lTrade:"))) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    boolean ran = false;
                    for (Player pp : Bukkit.getOnlinePlayers()) {
                        if (trading.containsKey(pp) && trading.get(pp).equals(code) && !pp.equals(p)) {
                            if (ran == false) {
                                cancelTrade(pp);
                                ran = true;
                            }
                        }
                    }
                    cancelTrade(p);
                }
            }.runTaskLater(Main.plugin, 1L);
        }
    }


    public void clickConfirm(Player p) {
        String code = trading.get(p);
        boolean p1 = false;
        for (Player player : player1) {
            if (player.equals(p)) {
                p1 = true;
                break;
            }
        }
        if (p1 == true) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (trading.containsKey(pp) && trading.get(pp).equals(code) && !p.equals(pp)) {
                    if (waitingConfirm.contains(pp)) {
                        waitingConfirm.add(p);
                        updateTrade(p, player1inv.get(code), player2inv.get(code), green(), green());
                        updateTrade(pp, player2inv.get(code), player1inv.get(code), green(), green());
                        confirmTrade(p);
                        confirmTrade(pp);
                    } else {
                        waitingConfirm.add(p);
                        updateTrade(p, player1inv.get(code), player2inv.get(code), green(), red());
                        updateTrade(pp, player2inv.get(code), player1inv.get(code), red(), green());

                    }
                }
            }
        } else {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (trading.containsKey(pp) && trading.get(pp).equals(code) && !p.equals(pp)) {
                    if (waitingConfirm.contains(pp)) {
                        waitingConfirm.add(p);
                        updateTrade(p, player2inv.get(code), player1inv.get(code), green(), green());
                        updateTrade(pp, player1inv.get(code), player2inv.get(code), green(), green());
                        confirmTrade(p);
                        confirmTrade(pp);
                    } else {
                        waitingConfirm.add(p);
                        updateTrade(p, player2inv.get(code), player1inv.get(code), green(), red());
                        updateTrade(pp, player1inv.get(code), player2inv.get(code), red(), green());
                    }
                }
            }
        }
    }

    public void clickCancel(Player p) {
        String code = trading.get(p);
        boolean ran = false;
        for (Player pp : Bukkit.getOnlinePlayers()) {
            if (trading.containsKey(pp) && trading.get(pp).equals(code) && !pp.equals(p)) {
                if (ran == false) {
                    cancelTrade(pp);
                    ran = true;
                }
            }
        }
        cancelTrade(p);
    }

    private List<Integer> slots() {
        List<Integer> slots = new ArrayList<>();
        slots.add(0);
        slots.add(1);
        slots.add(2);
        slots.add(3);
        slots.add(4);
        slots.add(5);
        slots.add(6);
        slots.add(7);
        slots.add(8);
        slots.add(9);
        slots.add(13);
        slots.add(17);
        slots.add(18);
        slots.add(22);
        slots.add(26);
        slots.add(27);
        slots.add(31);
        slots.add(35);
        slots.add(36);
        slots.add(40);
        slots.add(44);
        slots.add(45);
        slots.add(52);
        slots.add(53);
        return slots;
    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null) return;
        if (e.getView().getTitle().equals(m.c("&a&lTrade:"))) {
            e.setCancelled(true);
            ItemStack put = e.getCurrentItem();

            if (e.getSlot() == 46 && e.getView().getTitle().equals(m.c("&a&lTrade:"))) {
                e.setCancelled(true);
                clickConfirm(p);
                return;
            }
            if (e.getSlot() == 49 && e.getView().getTitle().equals(m.c("&a&lTrade:"))) {
                e.setCancelled(true);
                clickCancel(p);
                return;
            }
            if (slots().contains(e.getSlot()) && e.getView().getTitle().equals(m.c("&a&lTrade:"))) {
                e.setCancelled(true);
                return;
            }
            if ((e.getClickedInventory().equals(p.getInventory()) || e.getSlotType().equals(InventoryType.SlotType.QUICKBAR)) && trading.containsKey(p)) {
                if (e.getClick().equals(ClickType.RIGHT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) return;
                e.setCancelled(true);
                String code = trading.get(p);
                if (e.getCurrentItem().getType().equals(Material.WOODEN_PICKAXE) || e.getCurrentItem().getType().equals(Material.STONE_PICKAXE) || e.getCurrentItem().getType().equals(Material.GOLDEN_PICKAXE) ||
                        e.getCurrentItem().getType().equals(Material.IRON_PICKAXE) || e.getCurrentItem().getType().equals(Material.DIAMOND_PICKAXE))
                    return;

                if (put != null && !put.getType().equals(Material.AIR)) {

                    boolean p1 = false;
                    for (Player player : player1) {
                        if (player.equals(p)) {
                            p1 = true;
                            break;
                        }
                    }
                    if (p1 == true) {
                        if (player1inv.get(code).size() == 12) {
                            return;
                        }
                        player1inv.get(code).add(put);
                        e.setCurrentItem(null);
                        p.updateInventory();
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        updateTrade(p, player1inv.get(code), player2inv.get(code), red(), red());
                        for (Player pp : Bukkit.getOnlinePlayers()) {
                            if (trading.containsKey(pp) && trading.get(pp).equals(code) && !p.equals(pp)) {
                                pp.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                                updateTrade(pp, player2inv.get(code), player1inv.get(code), red(), red());
                            }
                        }
                    } else {
                        e.setCancelled(true);
                        if (player2inv.get(code).size() == 12) {
                            return;
                        }
                        player2inv.get(code).add(put);
                        e.setCurrentItem(null);
                        p.updateInventory();
                        p.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                        updateTrade(p, player2inv.get(code), player1inv.get(code), red(), red());
                        for (Player pp : Bukkit.getOnlinePlayers()) {
                            if (trading.containsKey(pp) && trading.get(pp).equals(code) && !p.equals(pp)) {
                                pp.playSound(p.getLocation(), Sound.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
                                updateTrade(pp, player1inv.get(code), player2inv.get(code), red(), red());
                            }
                        }
                    }
                }

            } else {
                e.setCancelled(true);
                if (put != null && !put.getType().equals(Material.AIR) && !p2slot().contains(e.getSlot()) && !put.isSimilar(PickaxeLevel.getInstance().Spacer())) {
                    String code = trading.get(p);
                    boolean p1 = false;
                    for (Player player : player1) {
                        if (player.equals(p)) {
                            p1 = true;
                            break;
                        }
                    }
                    if (p1 == true) {
                        player1inv.get(code).remove(put);
                        e.setCurrentItem(null);
                        p.getInventory().addItem(put);
                        p.updateInventory();
                        p.playSound(p.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f);
                        updateTrade(p, player1inv.get(code), player2inv.get(code), red(), red());
                        for (Player pp : Bukkit.getOnlinePlayers()) {
                            if (trading.containsKey(pp) && trading.get(pp).equals(code) && !p.equals(pp)) {
                                pp.playSound(p.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f);
                                updateTrade(pp, player2inv.get(code), player1inv.get(code), red(), red());
                            }
                        }
                    } else {
                        player2inv.get(code).remove(put);
                        e.setCurrentItem(null);
                        p.getInventory().addItem(put);
                        p.updateInventory();
                        p.playSound(p.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f);
                        updateTrade(p, player2inv.get(code), player1inv.get(code), red(), red());
                        for (Player pp : Bukkit.getOnlinePlayers()) {
                            if (trading.containsKey(pp) && trading.get(pp).equals(code) && !p.equals(pp)) {
                                pp.playSound(p.getLocation(), Sound.ITEM_FLINTANDSTEEL_USE, 1.0f, 1.0f);
                                updateTrade(pp, player1inv.get(code), player2inv.get(code), red(), red());
                            }
                        }
                    }
                }
            }
        }
    }

    public void confirmTrade(Player p) {
        String code = trading.get(p);
        if (player1.contains(p)) {
            for (ItemStack i : player2inv.get(code)) {
                p.getInventory().addItem(i);
            }
            player2inv.remove(code);
            player1.remove(p);
        } else if (player2.contains(p)) {
            for (ItemStack i : player1inv.get(code)) {
                p.getInventory().addItem(i);
            }
            player1inv.remove(code);
            player2.remove(p);
        }
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_LARGE_BLAST, 1.0f, 1.0f);
        p.playSound(p.getLocation(), Sound.ENTITY_FIREWORK_ROCKET_TWINKLE, 1.0f, 1.0f);
        waitingConfirm.remove(p);
        trading.remove(p);
        if (p.getOpenInventory() != null)
            p.closeInventory();
        p.sendMessage(m.c("&eTrade Confirmed."));
    }

    public void cancelTrade(Player p) {
        if (!trading.containsKey(p)) return;
        String code = trading.get(p);
        if (player1.contains(p)) {
            for (ItemStack i : player1inv.get(code)) {
                p.getInventory().addItem(i);
            }
            player1inv.remove(code);
            player1.remove(p);
        } else if (player2.contains(p)) {
            for (ItemStack i : player2inv.get(code)) {
                p.getInventory().addItem(i);
            }
            player2inv.remove(code);
            player2.remove(p);
        }
        p.playSound(p.getLocation(), Sound.BLOCK_GLASS_BREAK, 1.0f, 1.0f);
        trading.remove(p);
        if (p.getOpenInventory() != null)
            p.closeInventory();
        p.sendMessage(m.c("&eTrade Cancelled."));

    }


}
