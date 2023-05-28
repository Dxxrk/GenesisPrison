package me.dxrk.Events;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Gangs.CMDGang;
import me.dxrk.Gangs.Gangs;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MinePouchHandler implements Listener, CommandExecutor {
    static SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    private int randomGems() {
        Random r = new Random();
        int min = 5000;
        int max = 10000;
        return r.nextInt(max - min) + min;
    }

    public ItemStack minePouch() {
        ItemStack pouch = new ItemStack(Material.INK_SACK);
        ItemMeta pm = pouch.getItemMeta();
        pm.setDisplayName(m.c("&eGem Pouch"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&aGems: "+randomGems()));
        pm.setLore(lore);
        pouch.setItemMeta(pm);
        return pouch;
    }
    public ItemStack gemVoucher(int gems) {
        ItemStack pouch = new ItemStack(Material.PAPER);
        ItemMeta pm = pouch.getItemMeta();
        pm.setDisplayName(m.c("&aGem Voucher"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Gems: &a"+gems));
        pm.setLore(lore);
        pouch.setItemMeta(pm);
        return pouch;
    }


    public static void addGems(Player p, int add) {
        int gems = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Gems");
        settings.getPlayerData().set(p.getUniqueId().toString()+".Gems", gems+add);
        settings.savePlayerData();
    }

    public void removeGems(Player p, int remove) {
        int gems = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Gems");
        settings.getPlayerData().set(p.getUniqueId().toString()+".Gems", gems-remove);
        settings.savePlayerData();
    }

    public void givePouch(Player p) {
        Random r = new Random();
        int chance = 2500;
        boolean inInv = false;
        for(ItemStack i : p.getInventory().getContents()) {
            if(i != null && i.hasItemMeta() && i.getItemMeta().hasLore()) {
                if (i.getItemMeta().getDisplayName().equals(m.c("&eGem Pouch"))) {
                    inInv = true;
                }
            }
        }
        if(r.nextInt(chance) <1 && inInv == false){
            p.getInventory().addItem(minePouch());
        }
    }



    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        ApplicableRegionSet set = wg.getRegionManager(p.getWorld()).getApplicableRegions(e.getBlock().getLocation());
        LocalPlayer player = wg.wrapPlayer(p);
        if(!set.isMemberOfAll(player)) return;
        givePouch(p);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e){
        Player p = e.getPlayer();

            if(p.getItemInHand() == null) return;
            if(!p.getItemInHand().hasItemMeta()) return;
            if(!p.getItemInHand().getItemMeta().hasLore()) return;
        String gang = Gangs.getInstance().getGang(p);
        double unity = CMDGang.getInstance().getUnityLevel(gang);
        double gem = 1;
        for(String s : settings.getGangs().getStringList(gang+".PerksUnlocked")) {
            if(s.equals("Increased Gems")) {
                gem += 0.10;
            }
        }


        if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            ItemStack item = p.getItemInHand();
            if (item.getType().equals(Material.INK_SACK) && item.getItemMeta().getDisplayName().equals(m.c("&eGem Pouch"))) {
                if(MonsterHandler.activeMonster.containsKey(p) && ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Cerberus")) {
                    gem *= MonsterHandler.getMonsterBoost(p, 3);
                }
                else if(MonsterHandler.activeMonster.containsKey(p) && ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Medusa")) {
                    gem *= MonsterHandler.getMonsterBoost(p, 5);
                }
                int gems = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(item.getItemMeta().getLore().get(0)));
                addGems(p, (int) (gems*gem));
                p.sendMessage(m.c("&f&lGems &8| &a+"+(int) (gems*gem)));
                if (p.getItemInHand().getAmount() > 1) {
                    p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                } else {
                    p.setItemInHand(null);
                }
            }
            if (item.getType().equals(Material.PAPER) && item.getItemMeta().getDisplayName().equals(m.c("&aGem Voucher"))) {
                int gems = PickaxeLevel.getInstance().getInt(ChatColor.stripColor(item.getItemMeta().getLore().get(0)));
                addGems(p, gems);
                p.sendMessage(m.c("&f&lGems &8| &a+"+gems));
                if (p.getItemInHand().getAmount() > 1) {
                    p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                } else {
                    p.setItemInHand(null);
                }
            }
            if (item.getType().equals(Material.MAGMA_CREAM) && item.getItemMeta().getDisplayName().equals(m.c("&eToken Voucher"))) {
                String s = ChatColor.stripColor(item.getItemMeta().getLore().get(0)).split("⛀")[1];
                double tokens = Double.parseDouble(s);
                Tokens.getInstance().addTokens(p, tokens);
                if (p.getItemInHand().getAmount() > 1) {
                    p.getItemInHand().setAmount(p.getItemInHand().getAmount() - 1);
                } else {
                    p.setItemInHand(null);
                }
            }
        }

    }

    public ItemStack GenesisCrate() {
        ItemStack gcrate = new ItemStack(Material.ENDER_CHEST);
        ItemMeta gm = gcrate.getItemMeta();
        gm.setDisplayName(m.c("&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r &a100,000 gems"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Upon placing this item, you will recieve 8 random items"));
        lore.add(m.c("&7From the list below, all rewards are randomly selected."));
        lore.add(m.c("&a&lRewards:"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&m--&f&lTokens&m--"));
        lore.add(m.c("&e⛀2,000,000-5,000,000"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&m--&f&lKeys&m--"));
        lore.add(m.c("&e1-10x &7Random Keys"));
        lore.add(m.c("&e3x &3&lRank &7Keys"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&m--&f&lRanks&m--"));
        lore.add(m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls &f&lRank"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&m--&f&lItems&m--"));
        lore.add(m.c("&e1-3x &5Epic Trinkets"));
        lore.add(m.c("&e1x &bLegendary Trinket"));
        lore.add(m.c("&e1x &c&lMonster &7Egg"));
        lore.add(m.c(" "));
        lore.add(m.c("&f&l&m--&f&lMisc.&m--"));
        lore.add(m.c("&dItem Rename"));
        lore.add(m.c("&b3x Currency Boost"));
        lore.add(m.c("&a2x XP Boost"));
        lore.add(m.c("&b2x Currency Boost"));
        gm.setLore(lore);
        gcrate.setItemMeta(gm);

        return gcrate;
    }
    public ItemStack epicTrinket(int amount) {
        ArrayList<String> lore = new ArrayList<>();
        ItemStack trinket = new ItemStack(Material.GOLD_NUGGET, amount);
        ItemMeta dm = trinket.getItemMeta();
        dm.setDisplayName(m.c("&5Epic Trinket &a25,000 Gems"));
        lore.add(m.c("&7&oRight Click to unveil"));
        dm.setLore(lore);
        trinket.setItemMeta(dm);
        return trinket;
    }
    public ItemStack rareTrinket(int amount) {
        ArrayList<String> lore = new ArrayList<>();
        ItemStack trinket = new ItemStack(Material.GOLD_NUGGET, amount);
        ItemMeta dm = trinket.getItemMeta();
        dm.setDisplayName(m.c("&9Rare Trinket &a15,000 Gems"));
        lore.add(m.c("&7&oRight Click to unveil"));
        dm.setLore(lore);
        trinket.setItemMeta(dm);
        return trinket;
    }
    private ItemStack egg() {
        ItemStack egg = new ItemStack(Material.MONSTER_EGG);
        ItemMeta em = egg.getItemMeta();
        em.setDisplayName(m.c("&c&lMonster &7&lEgg &a75,000 Gems"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Right click to collect a new monster"));
        em.setLore(lore);
        egg.setItemMeta(em);
        return egg;
    }

    private Inventory GemShop() {
        Inventory shop = Bukkit.createInventory(null, 27, m.c("&a&lGem Shop"));
        for(int i = 0; i < 27; i++){
            shop.setItem(i, PickaxeLevel.getInstance().Spacer());
        }
        shop.setItem(12, GenesisCrate());
        shop.setItem(14, egg());
        shop.setItem(2, rareTrinket(1));
        shop.setItem(6, epicTrinket(1));


        ItemStack rank = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta rm = rank.getItemMeta();
        rm.setDisplayName(m.c("&3&lRank &7Key &a100,000 Gems"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&cRight Click &7the &3&lRank &7Crate"));
        rm.setLore(lore);
        rank.setItemMeta(rm);
        shop.setItem(20, rank);
        rm.setDisplayName(m.c("&e3x &3&lRank &7Key &a200,000 Gems"));
        rank.setAmount(3);
        rm.setLore(lore);
        rank.setItemMeta(rm);
        shop.setItem(24, rank);


        return shop;
    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getClickedInventory() == null) return;
        /*if(e.getCurrentItem() != null && e.getCurrentItem().hasItemMeta() && e.getCurrentItem().getItemMeta().getDisplayName().equals(m.c("&eGem Pouch")) && !e.getClickedInventory().equals(p.getInventory())) {
            for(ItemStack i : p.getInventory().getContents()) {
                if(i != null && i.hasItemMeta()) {
                    if(i.getItemMeta().getDisplayName().equals(m.c("&eGem Pouch"))) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }*/
        if(e.getInventory().getName().equals(m.c("&a&lGem Shop"))){
            e.setCancelled(true);
            if(e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }
            int gems = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Gems");
            if(e.getSlot() == 2) {
                if(gems >= 15000){
                    removeGems(p, 15000);
                    p.getInventory().addItem(TrinketHandler.getInstance().rareTrinket(1));
                }
                else {
                    p.sendMessage(m.c("&cError: Not Enough Gems"));
                    p.closeInventory();
                }
            }
            if(e.getSlot() == 6) {
                if(gems >= 25000){
                    removeGems(p, 25000);
                    p.getInventory().addItem(TrinketHandler.getInstance().epicTrinket(1));
                    p.updateInventory();
                }
                else {
                    p.sendMessage(m.c("&cError: Not Enough Gems"));
                    p.closeInventory();
                }
            }
            if(e.getSlot() == 12) {
                if(gems >= 100000){
                    removeGems(p, 100000);
                    p.getInventory().addItem(CrateFunctions.GenesisCrate());
                    p.updateInventory();
                }
                else {
                    p.sendMessage(m.c("&cError: Not Enough Gems"));
                    p.closeInventory();
                }
            }
            if(e.getSlot() == 14) {
                if(gems >= 75000){
                    removeGems(p, 75000);
                    p.getInventory().addItem(MonsterHandler.egg());
                    p.updateInventory();
                }
                else {
                    p.sendMessage(m.c("&cError: Not Enough Gems"));
                    p.closeInventory();
                }
            }
            if(e.getSlot() == 20) {
                if(gems >= 100000){
                    removeGems(p, 100000);
                    LocksmithHandler.getInstance().addKey(p, "rank", 1);
                }
                else {
                    p.sendMessage(m.c("&cError: Not Enough Gems"));
                    p.closeInventory();
                }
            }
            if(e.getSlot() == 24) {
                if(gems >= 200000){
                    removeGems(p, 200000);
                    LocksmithHandler.getInstance().addKey(p, "rank", 3);
                }
                else {
                    p.sendMessage(m.c("&cError: Not Enough Gems"));
                    p.closeInventory();
                }
            }
        }
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("gems") || cmd.getName().equalsIgnoreCase("gem")) {
            Player p = (Player)sender;
            if(args.length == 1){
                if(args[0].equalsIgnoreCase("withdraw")) {
                    p.sendMessage(m.c("&cError: Please specify an amount"));
                    return false;
                }
                if(args[0].equalsIgnoreCase("shop")) {
                    p.openInventory(GemShop());
                    return false;
                }
            }
            if(args.length == 2) {
                if (args[0].equalsIgnoreCase("withdraw")) {
                    int gems = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".Gems");
                    int amount = Integer.parseInt(args[1]);
                    if (amount > gems || amount <0) {
                        p.sendMessage(m.c("&cError: Not Enough Gems"));
                        return false;
                    }
                    removeGems(p, amount);
                    p.getInventory().addItem(gemVoucher(amount));
                }
            }
        }

        return false;
    }




}
