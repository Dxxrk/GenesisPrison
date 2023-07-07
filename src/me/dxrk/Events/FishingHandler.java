package me.dxrk.Events;

import me.dxrk.Enchants.EnchantMethods;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Methods;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.FishHook;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;


public class FishingHandler implements Listener, CommandExecutor {

    public Methods m = Methods.getInstance();

    @EventHandler
    public void onFish(PlayerFishEvent e){
        Player p = e.getPlayer();

        if(e.getState().equals(PlayerFishEvent.State.CAUGHT_FISH)) {
            Item caught = (Item) e.getCaught();
            caught.remove();
            Random r = new Random();
            ItemStack reward;
            ItemMeta im;
            int number = r.nextInt(101);
            int multiplierlevel = getEnchantLevel(p, "Multiplier");
            int amount = 1;
            if(r.nextInt(103-multiplierlevel) == 1) {
                amount = 2;
                p.sendMessage(m.c("&f&lMultiplier &8| &bExtra fish."));
            }
            if(number >= 0 && number <= 40){
                reward = new ItemStack(Material.RAW_FISH, amount,(short) 0);
                im = reward.getItemMeta();
                im.setDisplayName(m.c("&6&lRaw Fish"));
            }
            else if(number >= 41 && number <= 70){
                reward = new ItemStack(Material.RAW_FISH, amount,(short) 1);
                im = reward.getItemMeta();
                im.setDisplayName(m.c("&6&lRaw Salmon"));
            }
            else if(number >= 71 && number <= 90){
                reward = new ItemStack(Material.RAW_FISH, amount,(short) 2);
                im = reward.getItemMeta();
                im.setDisplayName(m.c("&6&lClownfish"));
            }
            else{
                reward = new ItemStack(Material.RAW_FISH, amount,(short) 3);
                im = reward.getItemMeta();
                im.setDisplayName(m.c("&6&lPufferfish"));
            }

            int keyfisherlevel = getEnchantLevel(p, "Key Fisher");
            if(keyfisherlevel>0){
                int chance = 105-10*keyfisherlevel;
                if(r.nextInt(chance)==1){
                    int rr=r.nextInt(7);
                    String key;
                    if(rr==1)
                        key="Alpha";
                    else if(rr==2)
                        key="Beta";
                    else if(rr==3)
                        key="Omega";
                    else if(rr==4)
                        key="Token";
                    else if(rr==5)
                        key="Community";
                    else
                        key="Seasonal";
                    KeysHandler.getInstance().addKey(p, key, 1);
                    p.sendMessage(m.c("&f&lKey Fisher &8| &7") + key + " Key");
                }
            }
            int treasurehunterlevel = getEnchantLevel(p, "Treasure Hunter");
            if(treasurehunterlevel>0){
                int chance = 110-treasurehunterlevel;
                if(r.nextInt(chance)==1){
                    int rr = r.nextInt(54);
                    if(rr>=0 && rr<=46){
                        p.getInventory().addItem(CrateFunctions.FishingCrate());
                        p.sendMessage(m.c("&f&lTreasure Hunter &8| &7+1 Fishing Crate"));
                    }else if(rr>=47 && rr<=48){
                        p.getInventory().addItem(MonsterHandler.egg());
                        p.sendMessage(m.c("&f&lTreasure Hunter &8| &7+1 Monster Egg"));
                    }else if(rr>=49 && rr<=50){
                        p.getInventory().addItem(CrateFunctions.ContrabandCrate());
                        p.sendMessage(m.c("&f&lTreasure Hunter &8| &7+1 Contraband Crate"));
                    }else if(rr>=51 && rr<=52){
                        p.getInventory().addItem(CrateFunctions.GenesisCrate());
                        p.sendMessage(m.c("&f&lTreasure Hunter &8| &7+1 Genesis Crate"));
                    }else{
                        p.getInventory().addItem(CrateFunctions.AprilCrate());
                        p.sendMessage(m.c("&f&lTreasure Hunter &8| &7+1 Monthly Crate"));
                    }
                }
            }
            int xpfinderlevel = getEnchantLevel(p, "XP Finder");
            if(xpfinderlevel>0){
                int rr=r.nextInt(5);
                if(rr==1){
                    PickXPHandler.getInstance().addXP(p, 5000+50*xpfinderlevel);
                }
            }

            List<String> lore = new ArrayList<>();
            lore.add(m.c("&7Sell this in /sellfish to gain Crystals"));
            im.setLore(lore);

            reward.setItemMeta(im);

            p.sendMessage(m.c("&f&lFishing &8| &7Caught: " + im.getDisplayName()));
            p.getInventory().addItem(reward);
        }
    }

    public int getEnchantLevel(Player p, String enchant){
        ItemStack item = p.getItemInHand();
        ItemMeta itemmeta = item.getItemMeta();
        List<String> lore = itemmeta.getLore();
        int x = 0;
        for (String a : lore){
            if(a.contains(enchant)){
                 x=getInt(a);
            }
        }
        return x;
    }

    public void openSellInv(Player p){
        int crystals = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Crystals");
        Inventory inv = Bukkit.createInventory(null, 9, m.c("&a&lSell Fish &8| ")+m.c("&b&lCrystals: ")+crystals);
        for(int i=0;i<9;i++)
            inv.setItem(i, PickaxeLevel.getInstance().SpacerWhite());
        List<String> lore = new ArrayList<>();
        ItemStack fish = new ItemStack(Material.RAW_FISH,1,(short) 0);
        ItemMeta fishmeta = fish.getItemMeta();
        fishmeta.setDisplayName(m.c("&6&lRaw Fish"));
        lore.add(m.c("&bSells for: 1 Crystal"));
        lore.add("");
        lore.add(m.c("&7Left click to sell one."));
        lore.add(m.c("&7Shift click to sell all."));
        fishmeta.setLore(lore);
        fish.setItemMeta(fishmeta);
        inv.setItem(1, fish);
        lore.clear();

        ItemStack salmon = new ItemStack(Material.RAW_FISH,1,(short) 1);
        ItemMeta salmonmeta = salmon.getItemMeta();
        salmonmeta.setDisplayName(m.c("&6&lRaw Salmon"));
        lore.add(m.c("&bSells for: 2 Crystals"));
        lore.add("");
        lore.add(m.c("&7Left click to sell one."));
        lore.add(m.c("&7Shift click to sell all."));
        salmonmeta.setLore(lore);
        salmon.setItemMeta(salmonmeta);
        inv.setItem(3, salmon);
        lore.clear();

        ItemStack clown = new ItemStack(Material.RAW_FISH,1,(short) 2);
        ItemMeta clownmeta = clown.getItemMeta();
        clownmeta.setDisplayName(m.c("&6&lClownfish"));
        lore.add(m.c("&bSells for: 3 Crystals"));
        lore.add("");
        lore.add(m.c("&7Left click to sell one."));
        lore.add(m.c("&7Shift click to sell all."));
        clownmeta.setLore(lore);
        clown.setItemMeta(clownmeta);
        inv.setItem(5, clown);
        lore.clear();

        ItemStack puffer = new ItemStack(Material.RAW_FISH,1,(short) 3);
        ItemMeta puffermeta = puffer.getItemMeta();
        puffermeta.setDisplayName(m.c("&6&lPufferfish"));
        lore.add(m.c("&bSells for: 5 Crystals"));
        lore.add("");
        lore.add(m.c("&7Left click to sell one."));
        lore.add(m.c("&7Shift click to sell all."));
        puffermeta.setLore(lore);
        puffer.setItemMeta(puffermeta);
        inv.setItem(7, puffer);

        p.openInventory(inv);
    }
    public boolean isInt(String s) {
        try {
            parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }
    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }
    public int getInt(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor(s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (byte) (b + 1)) {
            char c = arrayOfChar[b];
            if (!this.isInt(c)) continue;
            lvl.append(c);
        }
        if (this.isInt(lvl.toString())) {
            return parseInt(lvl.toString());
        }
        return -1;
    }
    public double enchantPrice(String Enchant, int level) {
        double i = 0;
        switch (Enchant) {
            case "Bait":
                if(level==0){
                    i = 25;
                    break;
                }
                i = 25 + 25 * level;
                break;
            case "Key Fisher":
                if(level==0) {
                    i = 10;
                    break;
                }
                i = 10 + 5 * level;
                break;
            case "Multiplier":
                if(level==0) {
                    i = 2.5;
                    break;
                }
                i = 5 + 2.5 * level;
                break;
            case "Treasure Hunter":
                if(level==0) {
                    i = 25;
                    break;
                }
                i = 25 + 2.5 * level;
                break;
            case "XP Finder":
                if(level==0) {
                    i = 5;
                    break;
                }
                i = 5 + 2 * level;
                break;
        }
        return i;
    }
    public int maxLevel(String Enchant, Player p) {
        int i = 0;
        switch (Enchant) {
            case "Bait":
                i = 3;
                break;
            case "Key Fisher":
                i = 10;
                break;
            case "Multiplier":
                i = 100;
                break;
            case "XP Finder":
                i = 200;
                break;
            case "Treasure Hunter":
                i = 100;
                break;
        }
        return i;
    }
    public void setEnchantItemRod(String enchantName, Material mat, String name, String desc, int priceStart, Inventory inv, int slot, Player p) {

        int enchantLevel = 0;

        for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
            String s = p.getItemInHand().getItemMeta().getLore().get(x);
            if (ChatColor.stripColor(s).contains(enchantName)) {
                enchantLevel = getInt(p.getItemInHand().getItemMeta().getLore().get(x));
            }
        }
        double price;
        if (enchantLevel == 0) {
            price = priceStart;
        } else {
            price = enchantPrice(enchantName, enchantLevel);
        }

        String cost;

        if (enchantLevel != maxLevel(enchantName, p)) {
            cost = m.c("&bCost: &e" + ((int) price) + "⛀");
        } else {
            cost = m.c("&bCost: &eMAX LEVEL!");
        }


        ItemStack i = new ItemStack(mat);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(desc);
        lore.add(" ");
        lore.add(m.c("&bCurrent Level: &e" + enchantLevel));
        lore.add(m.c("&bMax Level: &e" + maxLevel(enchantName, p)));
        lore.add(cost);
        lore.add(" ");
        lore.add(m.c("&7&oLeft Click: Buy 1"));
        lore.add(m.c("&7&oRight Click: Buy 10"));
        im.setLore(lore);
        i.setItemMeta(im);
        lore.clear();


        inv.setItem(slot, i);
    }
    public void openEnchantInv(Player p){
        int crystals = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Crystals");
        Inventory inv = Bukkit.createInventory(null, 9, m.c("&cRod Enchants &8| &bCrystals: ")+crystals);
        for(int i=0;i<9;i++)
            inv.setItem(i, PickaxeLevel.getInstance().SpacerWhite());

        setEnchantItemRod("Bait", Material.STRING, "Upgrade Bait", "Lowers the max wait time for fish", 25, inv, 0, p);
        setEnchantItemRod("Key Fisher", Material.TRIPWIRE_HOOK, "Upgrade Key Fisher", "Chance to find keys while fishing", 10, inv, 2, p);
        setEnchantItemRod("Multiplier", Material.EMERALD, "Upgrade Multiplier", "Raise the chance to catch multiple fish while fishing", 2, inv, 4, p);
        setEnchantItemRod("XP Finder", Material.EXP_BOTTLE, "Upgrade XP Finder", "Chance to Pickaxe XP while fishing", 5, inv, 6, p);
        setEnchantItemRod("Treasure Hunter", Material.GOLD_BLOCK, "Upgrade Treasure Hunter", "Chance to find loot while fishing", 25, inv, 8, p);

        p.openInventory(inv);
    }

    public void upgradeRodEnchant(Player p, ItemStack i, String Enchant, int num) {

        ItemStack pitem = i.clone();
        ItemMeta pm = pitem.getItemMeta();
        List<String> lore = pm.getLore();
        boolean hasEnchant = false;


        for (String s : lore) {
            if (ChatColor.stripColor(s).contains(Enchant)) {
                hasEnchant = true;
            }
        }

        if (!hasEnchant) {
            int level = 0;
            int plus = level + 1;
            int price = (int) enchantPrice(Enchant, level);
            int crystals = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Crystals");
            if (crystals >= price) {
                if (plus > maxLevel(Enchant, p)) return;
                lore.add(m.c("&c" + Enchant + " &e" + plus));
                PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", crystals-price);
                int previouslevel = getEnchantLevel(p, "Bait");
                if(Enchant.equalsIgnoreCase("Bait") && previouslevel<=2){
                    pm.removeEnchant(Enchantment.LURE);
                    pm.addEnchant(Enchantment.LURE, previouslevel+1, true);
                }
            } else {
                p.sendMessage(m.c("&f&lCrystals &8| &7Not enough Crystals."));
                return;
            }
            PlayerDataHandler.getInstance().savePlayerData(p);
        }else {
            int line = 0;
            for (int z = 0; z < lore.size(); z++) {
                String s = lore.get(z);
                if (ChatColor.stripColor(s).contains(Enchant))
                    line = z;
            }
            for (int x = 0; x < num; x++) {
                int level = getInt(lore.get(line));
                int plus = level + 1;
                int price = (int) enchantPrice(Enchant, level);
                int crystals = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Crystals");
                if (crystals >= price) {
                    if (plus > maxLevel(Enchant, p)) return;
                    lore.set(line, m.c("&c" + Enchant + " &e" + plus));
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", crystals-price);
                    int previouslevel = getEnchantLevel(p, "Bait");
                    if(Enchant.equalsIgnoreCase("Bait") && previouslevel<=2){
                        pm.removeEnchant(Enchantment.LURE);
                        pm.addEnchant(Enchantment.LURE, previouslevel+1, true);
                    }
                } else {
                    p.sendMessage(m.c("&f&lCrystals &8| &7Not enough Crystals."));
                    break;
                }
                PlayerDataHandler.getInstance().savePlayerData(p);
            }

        }
        pm.setLore(lore);
        pm.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        pitem.setItemMeta(pm);
        p.setItemInHand(pitem);
        p.updateInventory();

    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(command.getName().equalsIgnoreCase("sellfish")){
            Player p = (Player) commandSender;
            openSellInv(p);
        }else if(command.getName().equalsIgnoreCase("rod")){
            Player p = (Player) commandSender;
            if(p.getItemInHand().getType().equals(Material.FISHING_ROD))
                openEnchantInv(p);
            else
                p.sendMessage(m.c("&cHold a Fishing Rod in your hand to open the Menu."));
        }else if(command.getName().equalsIgnoreCase("crystals") || command.getName().equalsIgnoreCase("crystal")){
            if(strings.length==0){
                Player p = (Player) commandSender;
                p.sendMessage(m.c("&f&lCrystals &8 | &b" + PlayerDataHandler.getInstance().getPlayerData(p).getInt("Crystals")));
            }
            if(strings.length==1){
                if(strings[0].equalsIgnoreCase("shop")){
                    Player p = (Player) commandSender;
                    //open crystal shop
                }
            }else if(strings.length==3) {
                if (strings[0].equalsIgnoreCase("give")) {
                    if (commandSender.isOp()) {
                        Player reciever = Bukkit.getPlayer(strings[1]);
                        int crystals = parseInt(strings[2]);
                        int previouscrystals = PlayerDataHandler.getInstance().getPlayerData(reciever).getInt("Crystals");
                        PlayerDataHandler.getInstance().getPlayerData(reciever).set("Crystals", previouscrystals + crystals);
                    }
                }
            }

        }
        return true;
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getName() == null) return;

        if(e.getClickedInventory().getName().contains(m.c("&a&lSell Fish &8|"))){
            e.setCancelled(true);
            if (e.getClickedInventory().equals(p.getInventory())) return;
            if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem() == null) return;
            if (e.getCurrentItem().equals(PickaxeLevel.getInstance().SpacerWhite())) return;

            int previouscrystals = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Crystals");

            if(e.getSlot() == 1){
                if(getFishCount(p, (byte)0) == 0) return;
                if(e.getClick().equals(ClickType.LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+1);
                    removeOneFromInv(p,m.c("&6&lRaw Fish"));
                }else if(e.getClick().equals(ClickType.SHIFT_LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+getFishCount(p,(byte)0));
                    removeAllFromInv(p, m.c("&6&lRaw Fish"));
                }
            }else if(e.getSlot() == 3){
                if(getFishCount(p, (byte)1) == 0) return;
                if(e.getClick().equals(ClickType.LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+2);
                    removeOneFromInv(p,m.c("&6&lRaw Salmon"));
                }else if(e.getClick().equals(ClickType.SHIFT_LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+getFishCount(p,(byte)1)*2);
                    removeAllFromInv(p, m.c("&6&lRaw Salmon"));
                }
            }else if(e.getSlot() == 5){
                if(getFishCount(p, (byte)2) == 0) return;
                if(e.getClick().equals(ClickType.LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+3);
                    removeOneFromInv(p,m.c("&6&lClownfish"));
                }else if(e.getClick().equals(ClickType.SHIFT_LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+getFishCount(p,(byte)2)*3);
                    removeAllFromInv(p, m.c("&6&lClownfish"));
                }
            }else if(e.getSlot() == 7){
                if(getFishCount(p, (byte)3) == 0) return;
                if(e.getClick().equals(ClickType.LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+5);
                    removeOneFromInv(p,m.c("&6&lPufferfish"));
                }else if(e.getClick().equals(ClickType.SHIFT_LEFT)){
                    PlayerDataHandler.getInstance().getPlayerData(p).set("Crystals", previouscrystals+getFishCount(p,(byte)3)*5);
                    removeAllFromInv(p, m.c("&6&lPufferfish"));
                }
            }
            PlayerDataHandler.getInstance().savePlayerData(p);
            openSellInv(p);
        }else if(e.getClickedInventory().getName().contains(m.c("&cRod Enchants "))){
            e.setCancelled(true);
            if (e.getClickedInventory().equals(p.getInventory())) return;
            if (e.getCurrentItem().getType().equals(Material.AIR) || e.getCurrentItem() == null) return;
            if (e.getCurrentItem().equals(PickaxeLevel.getInstance().SpacerWhite())) return;

            String[] display = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("Upgrade ");
            String name = display[1];

            if (e.getClick().equals(ClickType.LEFT)) {
                upgradeRodEnchant(p, p.getItemInHand(), name, 1);
            } else if (e.getClick().equals(ClickType.RIGHT)) {
                upgradeRodEnchant(p, p.getItemInHand(), name, 10);
            }

            p.updateInventory();
            openEnchantInv(p);
        }
    }
    @SuppressWarnings("deprecation")
    public short getFishCount(Player p, byte a){
        short count = 0;
        for(ItemStack item : p.getInventory().getContents()){
            if(item==null) continue;
            if(item.getType() == Material.RAW_FISH && item.getData().getData() == a){
                count+=item.getAmount();
            }
        }
        return count;
    }
    public void removeOneFromInv(Player p, String name){
        ItemStack[] contents = p.getInventory().getContents();
        for(int i=0;i<contents.length;i++){
            ItemStack item = contents[i];
            if(item == null) continue;
            if(item.getType().equals(Material.AIR)) continue;
            if(item.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().equals(name)) {
                    int itemamount = item.getAmount();
                    if (itemamount > 1) {
                        item.setAmount(itemamount - 1);
                        break;
                    } else {
                        p.getInventory().setItem(i, null);
                        break;
                    }
                }
            }
        }
    }
    public void removeAllFromInv(Player p, String name){
        ItemStack[] contents = p.getInventory().getContents();
        for(int i=0;i<contents.length;i++){
            ItemStack item = contents[i];
            if(item == null) continue;
            if(item.getType().equals(Material.AIR)) continue;
            if(item.getItemMeta().hasDisplayName()) {
                if (item.getItemMeta().getDisplayName().equals(name)) {
                    p.getInventory().setItem(i, null);
                }
            }
        }
    }
}
