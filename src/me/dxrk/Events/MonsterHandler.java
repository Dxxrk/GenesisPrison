package me.dxrk.Events;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.NBTTagDouble;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.lang.reflect.Field;
import java.util.*;

public class MonsterHandler implements Listener, CommandExecutor {
    private HeadDatabaseAPI api = new HeadDatabaseAPI();
    private static Methods m = Methods.getInstance();

    public static Map<Player, ItemStack> activeMonster = new HashMap<>();


    public static double getMonsterBoost(Player p, int loreline) {
        double boost = 1;
        for (ItemStack i : p.getInventory().getContents()) {
            if (i != null && i.getType().equals(Material.SKULL_ITEM) && i.hasItemMeta() && i.getItemMeta().hasLore() && activeMonster.containsKey(p)) {
                String active = ChatColor.stripColor(i.getItemMeta().getDisplayName()).split(" ")[1];
                if (active.equals("(Active)")) {
                    if (loreline <= 3) {
                        String lore = ChatColor.stripColor(i.getItemMeta().getLore().get(loreline));
                        String first = lore.split("%")[0];
                        String percent = first.split(" ")[1];
                        boost += (Integer.parseInt(percent)) / 100.0;
                    } else {
                        String lore = ChatColor.stripColor(i.getItemMeta().getLore().get(loreline).replace(" ", ""));
                        String first = lore.split("%")[0];
                        boost += (Integer.parseInt(first)) / 100.0;
                    }
                }
            }
        }
        return boost;
    }


   /* public static boolean hasActiveMonsterInInventory(Player p) {
            for (ItemStack i : p.getInventory().getContents()) {
                if(i.equals(activeMonster.get(p))) {
                    return true;
                }
            }
        return false;
    }*/


    public static ItemStack egg() {
        ItemStack egg = new ItemStack(Material.MONSTER_EGG);
        ItemMeta em = egg.getItemMeta();
        em.setDisplayName(m.c("&c&lMonster &7&lEgg"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Right click to collect a new monster"));
        em.setLore(lore);
        egg.setItemMeta(em);
        return egg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("giveegg")) {
            if (sender.isOp()) {
                if (args.length == 1) {
                    Player p = Bukkit.getPlayer(args[0]);
                    p.getInventory().addItem(egg());
                }
            }
        }
        return false;
    }

    private void giveMonster(Player p) {
        p.getInventory().addItem(type());
    }

    private void activateMonster(Player p, ItemStack item) {
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack it = p.getInventory().getItem(i);
            if (it != null && it.equals(item)) {
                ItemStack pitem = item.clone();
                String[] name = pitem.getItemMeta().getDisplayName().split(" ");
                ItemMeta pim = pitem.getItemMeta();
                pim.setDisplayName(m.c(name[0] + " &7(&aActive&7)"));
                pitem.setItemMeta(pim);
                p.getInventory().setItem(i, pitem);
                return;
            }
        }
    }

    private void deactivateMonster(Player p, ItemStack item) {
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack it = p.getInventory().getItem(i);
            if (it != null && it.equals(item)) {
                ItemStack pitem = item.clone();
                String[] name = pitem.getItemMeta().getDisplayName().split(" ");
                ItemMeta pim = pitem.getItemMeta();
                pim.setDisplayName(m.c(name[0] + " &7(&cInactive&7)"));
                pitem.setItemMeta(pim);
                p.getInventory().setItem(i, pitem);
                return;
            }
        }
    }

    public static void forceDeactivate(Player p) {
        ItemStack item = activeMonster.get(p);
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack it = p.getInventory().getItem(i);
            if (it != null && it.equals(item)) {
                ItemStack pitem = item.clone();
                String[] name = pitem.getItemMeta().getDisplayName().split(" ");
                ItemMeta pim = pitem.getItemMeta();
                pim.setDisplayName(m.c(name[0] + " &7(&cInactive&7)"));
                pitem.setItemMeta(pim);
                p.getInventory().setItem(i, pitem);
                return;
            }
        }
        activeMonster.remove(p);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        forceDeactivate(p);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        for (int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack it = p.getInventory().getItem(i);
            if (it != null && it.getType().equals(Material.SKULL_ITEM) && it.hasItemMeta() && it.getItemMeta().getDisplayName().contains(" ") &&
                    ChatColor.stripColor(it.getItemMeta().getDisplayName()).split(" ")[1].equals("(Active)")) {
                ItemStack pitem = it.clone();
                String[] name = pitem.getItemMeta().getDisplayName().split(" ");
                ItemMeta pim = pitem.getItemMeta();
                pim.setDisplayName(m.c(name[0] + " &7(&cInactive&7)"));
                pitem.setItemMeta(pim);
                p.getInventory().setItem(i, pitem);
                return;
            }
        }
    }

    //Create Values that can be boosted by pets and add a way to upgrade them.
    //Myabe have special abilities for the monsters on a cooldown? (right click)
    //Automatically set to inactive when moved out of your own inventory
    //Only allow inactive monsters to be sold on /ah


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onMove(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getName() == null)
            return;
        if (activeMonster.containsKey(p) && e.getCurrentItem().equals(activeMonster.get(p))) {
            e.setCancelled(true);
        }
    }

    public double cost(int level, String rarity) {
        double cost = 0;


        return cost;
    }

    public void openUpgrade(Player p, ItemStack item) {
        Inventory upgrade = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&a&lUpgrade:"));
        ItemStack info = item.clone();
        upgrade.setItem(0, info);
        upgrade.setItem(1, info);
        upgrade.setItem(3, info);
        upgrade.setItem(4, info);
        ItemStack up = new ItemStack(Material.INK_SACK, 1, (short) 10);
        ItemMeta um = up.getItemMeta();
        int level = Integer.parseInt(ChatColor.stripColor(info.getItemMeta().getLore().get(3)).split(" ")[1]);
        String rarity = ChatColor.stripColor(info.getItemMeta().getLore().get(2)).split(" ")[1];
        um.setDisplayName(m.c("&aUpgrade to Level " + (level + 1)));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&eCost: ⛀" + Main.formatAmt(cost(level, rarity))));
        um.setLore(lore);
        up.setItemMeta(um);
        upgrade.setItem(2, up);
    }

    @EventHandler
    public void openEgg(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) return;
        if (!p.getItemInHand().hasItemMeta()) return;
        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR)) {
            if (p.isSneaking()) {

                return;
            }
            if (p.getItemInHand().getType().equals(Material.MONSTER_EGG) && p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&c&lMonster &7&lEgg"))) {
                giveMonster(p);
                int amount = p.getItemInHand().getAmount();
                if (amount == 1) {
                    p.setItemInHand(null);
                } else {
                    p.getItemInHand().setAmount(amount - 1);
                }
                return;
            }
            if (p.getItemInHand().getType().equals(Material.SKULL_ITEM)) {
                String active = ChatColor.stripColor(p.getItemInHand().getItemMeta().getDisplayName()).split(" ")[1];
                if (p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore() && active.equals("(Inactive)")) {
                    e.setCancelled(true);
                    if (activeMonster.containsKey(p)) {
                        p.sendMessage(m.c("&f&lMonsters &8| &7You have a monster active already."));
                        return;
                    }
                    activateMonster(p, p.getItemInHand());
                    activeMonster.put(p, p.getItemInHand());
                    return;
                }
                if (p.getItemInHand().getType().equals(Material.SKULL_ITEM) && p.getItemInHand().hasItemMeta() && p.getItemInHand().getItemMeta().hasLore() && active.equals("(Active)")) {
                    e.setCancelled(true);
                    if (activeMonster.containsKey(p)) {
                        deactivateMonster(p, p.getItemInHand());
                        activeMonster.remove(p);
                    }
                }
            }
        }
    }

    @EventHandler
    public void setMaxStackSize(net.minecraft.server.v1_8_R3.Item getitem, int i) {
        try {
            Field field = net.minecraft.server.v1_8_R3.Item.class.getDeclaredField("maxStackSize");
            field.setAccessible(true);
            field.setInt(getitem, i);
        } catch (Exception ignored) {
        }
    }

    private ItemStack Ladon(String rarity, int bonusmoney) {
        ItemStack ladon = api.getItemHead("44860");
        ItemMeta lm = ladon.getItemMeta();
        lm.setDisplayName(m.c("&e&lLadon &7(&cInactive&7)"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oLadon hailing from the Garden of Hesperides, is the protector of the Golden Apples."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&7Boost: &b" + bonusmoney + "% Income Boost"));
        lm.setLore(lore);
        ladon.setItemMeta(lm);
        net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(ladon);
        NBTTagCompound itemcompound = (i2.hasTag()) ? i2.getTag() : new NBTTagCompound();
        itemcompound.set("toStopStacking", new NBTTagDouble(new Random().nextDouble()));
        i2.setTag(itemcompound);
        ladon = CraftItemStack.asBukkitCopy(i2);
        return ladon;
    }

    private ItemStack Typhon(String rarity, int bonus) {
        ItemStack item = api.getItemHead("28179");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c("&2&lTyphon &7(&cInactive&7)"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oTyphon, the God of Monsters, is an unstoppable force all Olympians fear."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&7Boost: &e" + bonus + "% Token Boost"));
        im.setLore(lore);
        item.setItemMeta(im);
        net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemcompound = (i2.hasTag()) ? i2.getTag() : new NBTTagCompound();
        itemcompound.set("toStopStacking", new NBTTagDouble(new Random().nextDouble()));
        i2.setTag(itemcompound);
        item = CraftItemStack.asBukkitCopy(i2);
        return item;
    }

    private ItemStack Cerberus(String rarity, int bonus) {
        ItemStack item = api.getItemHead("26841");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c("&4&lCerberus &7(&cInactive&7)"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oCerberus, Hades pet, the three headed hell hound and protector of The Underworld."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&7Boost: &a" + bonus + "% Gem Boost"));
        im.setLore(lore);
        item.setItemMeta(im);
        net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemcompound = (i2.hasTag()) ? i2.getTag() : new NBTTagCompound();
        itemcompound.set("toStopStacking", new NBTTagDouble(new Random().nextDouble()));
        i2.setTag(itemcompound);
        item = CraftItemStack.asBukkitCopy(i2);
        return item;
    }

    private ItemStack Phoenix(String rarity, int bonus, int bonus2) {
        ItemStack item = api.getItemHead("683");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c("&6&lPhoenix &7(&cInactive&7)"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oThe Phoenix, bird of the sun, some say it can never die."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&7Boosts: &b" + bonus + "% Income Boost"));
        lore.add(m.c("             &e" + bonus2 + "% Token Boost"));
        im.setLore(lore);
        item.setItemMeta(im);
        net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemcompound = (i2.hasTag()) ? i2.getTag() : new NBTTagCompound();
        itemcompound.set("toStopStacking", new NBTTagDouble(new Random().nextDouble()));
        i2.setTag(itemcompound);
        item = CraftItemStack.asBukkitCopy(i2);
        return item;
    }

    private ItemStack Medusa(String rarity, int bonus, int bonus2, int bonus3) {
        ItemStack item = api.getItemHead("1394");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c("&7&lMedusa &7(&cInactive&7)"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oMedusa, a terrifying Gorgon, will petrify anyone who enters her gaze."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&aBoosts: &b" + bonus + "% Income Boost"));
        lore.add(m.c("             &e" + bonus2 + "% Token Boost"));
        lore.add(m.c("             &a" + bonus3 + "% Gem Boost"));
        im.setLore(lore);
        item.setItemMeta(im);
        net.minecraft.server.v1_8_R3.ItemStack i2 = CraftItemStack.asNMSCopy(item);
        NBTTagCompound itemcompound = (i2.hasTag()) ? i2.getTag() : new NBTTagCompound();
        itemcompound.set("toStopStacking", new NBTTagDouble(new Random().nextDouble()));
        i2.setTag(itemcompound);
        item = CraftItemStack.asBukkitCopy(i2);
        return item;
    }


    private String rarity() {
        Random r = new Random();
        int ri = r.nextInt(100);
        if (ri <= 40) {
            return m.c("&bCommon");
        } else if (ri <= 70) {
            return m.c("&9Rare");
        } else if (ri <= 90) {
            return m.c("&5Epic");
        } else {
            return m.c("&cMythical");
        }
    }


    private ItemStack type() {
        Random r = new Random();
        int ri = r.nextInt(100);
        String rarity = rarity();
        if (ri <= 30) {
            return Cerberus(rarity, effect(rarity)); //26841
        } else if (ri <= 50) {
            return Ladon(rarity, effect(rarity)); //683
        } else if (ri <= 70) {
            return Typhon(rarity, effect(rarity));
        } else if (ri <= 90) {
            return Phoenix(rarity, effect(rarity), effect(rarity));
        } else {
            return Medusa(rarity, effect(rarity), effect(rarity), effect(rarity)); //1394
        }
    }

    private int effect(String rarity) {
        Random r = new Random();
        if (rarity.equals(m.c("&bCommon"))) {
            int min = 5;
            int max = 15;
            return r.nextInt(max - min) + min;
        }
        if (rarity.equals(m.c("&9Rare"))) {
            int min = 15;
            int max = 25;
            return r.nextInt(max - min) + min;
        }
        if (rarity.equals(m.c("&5Epic"))) {
            int min = 25;
            int max = 35;
            return r.nextInt(max - min) + min;
        }
        if (rarity.equals(m.c("&cMythical"))) {
            int min = 35;
            int max = 50;
            return r.nextInt(max - min) + min;
        }
        return 0;
    }


}
