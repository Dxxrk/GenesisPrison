package me.dxrk.Events;

import com.connorlinfoot.titleapi.TitleAPI;
import me.dxrk.Enchants.EnchantMethods;
import me.dxrk.Enchants.Enchants;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Enchants.PickaxeSkillTree;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineHandler;
import me.dxrk.Vote.CMDVoteShop;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

public class PrestigeHandler implements Listener, CommandExecutor {

    Methods m = Methods.getInstance();
    static SettingsManager settings = SettingsManager.getInstance();

    private ItemStack prestigeItem(List<String> lore, String name) {
        ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (p == null) {
            return false;
        }


        if (label.equalsIgnoreCase("prestige")) {
            openInv(p);
        }
        if(label.equalsIgnoreCase("addprestige")){
            if(sender.isOp()){
                if(args.length==2){
                    Player reciever = Bukkit.getPlayer(args[0]);
                    int amount = parseInt(args[1]);
                    addPrestiges(reciever,amount);
                }
            }
        }

        return false;
    }


    public void openInv(Player p) {
        Inventory prestige = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&cPrestige: "));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&cWarning: This will reset your rank to 1!!"));
        lore.add(m.c(" "));
        lore.add(m.c("&7Prestiging gives a 50% increase to sell prices."));
        lore.add(m.c("&7Will also make levelling up considerably harder."));
        lore.add(m.c("&7Will also reset your balance to 0."));
        lore.add(m.c("&a+$0.25 Coupon"));
        lore.add(m.c("&b+1 &4&l&ki&f&lSeasonal&4&l&ki&r &7Key"));
        String uuid = p.getUniqueId().toString();
        int prestiges = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges");
        if (prestiges >= 100) {
            for(int i=0;i<5;i++)
                prestige.setItem(i,PickaxeSkillTree.Spacer());
            lore.clear();
            lore.add(m.c("&c&lMAX LEVEL"));
            prestige.setItem(2, prestigeItem(lore, m.c("&6&lYou did it!")));
            p.openInventory(prestige);
            return;
        }
        prestige.setItem(1, prestigeItem(lore, m.c("&6&lCLICK TO PRESTIGE!")));
        lore.clear();
        lore.add(" ");
        lore.add(m.c("&fClick this to open prestige tree upgrading."));
        prestige.setItem(0, PickaxeLevel.getInstance().Spacer());
        prestige.setItem(2, PickaxeLevel.getInstance().Spacer());
        prestige.setItem(3, prestigeItem(lore, m.c("&a&lPRESTIGE TREE")));
        prestige.setItem(4, PickaxeLevel.getInstance().Spacer());

        p.openInventory(prestige);


    }
    public void openPrestigeTree(Player p){
        int prestigepoints = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigePoints");
        Inventory inv = Bukkit.createInventory(null, 9, m.c("&aPrestige Tree: (Prestige Points: " + prestigepoints + ")"));
        for(int i=0;i<9;i++){
            inv.setItem(i, PickaxeSkillTree.Spacer());
        }
        List<String> lore = new ArrayList<>();
        ItemStack tokenstar = new ItemStack(Material.NETHER_STAR);
        ItemMeta tokenmeta = tokenstar.getItemMeta();
        int tokenboost = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigeTreeTokenBoost");
        if(!(tokenboost==0))
            tokenmeta.setDisplayName(m.c("&e&lToken Boost (Level: ")+tokenboost/2+")");
        else
            tokenmeta.setDisplayName(m.c("&e&lToken Boost (Level: 0)"));
        lore.add(" ");
        lore.add(m.c("&fClick here to raise your token income by 2%"));
        lore.add(m.c("&cWarning! This consumes a prestige point."));
        tokenmeta.setLore(lore);
        tokenstar.setItemMeta(tokenmeta);
        lore.clear();
        inv.setItem(1, tokenstar);
        ItemStack fortunestar = new ItemStack(Material.NETHER_STAR);
        ItemMeta fortunemeta = fortunestar.getItemMeta();
        int fortuneboost = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigeTreeFortuneBoost");
        if(!(fortuneboost==0))
            fortunemeta.setDisplayName(m.c("&b&lFortune Boost (Level: ")+fortuneboost/2+")");
        else
            fortunemeta.setDisplayName(m.c("&b&lFortune Boost (Level: 0)"));
        lore.add(" ");
        lore.add(m.c("&fClick here to raise your fortune income by 2%"));
        lore.add(m.c("&cWarning! This consumes a prestige point."));
        fortunemeta.setLore(lore);
        fortunestar.setItemMeta(fortunemeta);
        lore.clear();
        inv.setItem(3, fortunestar);
        ItemStack gemstar = new ItemStack(Material.NETHER_STAR);
        ItemMeta gemmeta = gemstar.getItemMeta();
        int gemboost = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigeTreeGemBoost");
        if(!(gemboost==0))
            gemmeta.setDisplayName(m.c("&a&lGem Boost (Level: ")+gemboost/2+")");
        else
            gemmeta.setDisplayName(m.c("&a&lGem Boost (Level: 0)"));
        lore.add(" ");
        lore.add(m.c("&fClick here to raise your gem income by 2%"));
        lore.add(m.c("&cWarning! This consumes a prestige point."));
        gemmeta.setLore(lore);
        gemstar.setItemMeta(gemmeta);
        lore.clear();
        inv.setItem(5, gemstar);
        ItemStack luckstar = new ItemStack(Material.NETHER_STAR);
        ItemMeta luckmeta = luckstar.getItemMeta();
        double luckboost = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("PrestigeTreeLuckBoost");
        if(!(luckboost==0))
            luckmeta.setDisplayName(m.c("&6&lLuck Boost (Level: ")+(int)(luckboost/0.5)+")");
        else
            luckmeta.setDisplayName(m.c("&6&lLuck Boost (Level: 0)"));
        lore.add(" ");
        lore.add(m.c("&fClick here to raise your luck by 0.5%"));
        lore.add(m.c("&cWarning! This consumes a prestige point."));
        luckmeta.setLore(lore);
        luckstar.setItemMeta(luckmeta);
        lore.clear();
        inv.setItem(7, luckstar);
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getName() == null)
            return;

        if (e.getClickedInventory().getName().contains(m.c("&cPrestige:"))) {
            e.setCancelled(true);

            if (e.getSlot() == 1) {
                if (RankupHandler.getInstance().getRank(p) >= 1000) {
                    String uuid = p.getUniqueId().toString();
                    int prestiges = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges");
                    if (prestiges >= 100) {
                        return;
                    }
                    Inventory sure = Bukkit.createInventory(null, InventoryType.HOPPER, m.c("&c&lARE YOU SURE?"));

                    ItemStack yes = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
                    ItemMeta ym = yes.getItemMeta();
                    List<String> lore = new ArrayList<>();
                    lore.add(m.c("&7&oThis Action is permanent."));
                    ym.setDisplayName(m.c("&a&lYES"));
                    ym.setLore(lore);
                    yes.setItemMeta(ym);
                    lore.clear();

                    ItemStack no = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 14);
                    ItemMeta nm = no.getItemMeta();
                    nm.setDisplayName(m.c("&c&lNO"));
                    no.setItemMeta(nm);

                    sure.setItem(1, yes);
                    sure.setItem(3, no);
                    sure.setItem(0, PickaxeLevel.getInstance().Spacer());
                    sure.setItem(2, PickaxeLevel.getInstance().Spacer());
                    sure.setItem(4, PickaxeLevel.getInstance().Spacer());
                    p.openInventory(sure);
                }
                else{
                    p.sendMessage(m.c("&cYou have to be level 1000 to prestige."));
                }
            }
            else if(e.getSlot() == 3){
                int prestiges = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges");
                if (prestiges >= 100) {
                    return;
                }
                openPrestigeTree(p);
            }
        }
        if (e.getClickedInventory().getName().equals(m.c("&c&lARE YOU SURE?"))) {
            e.setCancelled(true);
            if (e.getSlot() == 1) {
                prestige(p);
                p.closeInventory();

            }
            if (e.getSlot() == 3) {
                p.closeInventory();
            }
        }
        if(e.getClickedInventory().getName().contains(m.c("&aPrestige Tree:"))){
            e.setCancelled(true);
            int prestigepoints = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigePoints");
            if(prestigepoints==0)
                return;
            if(e.getSlot()==1){
                int tokenboost = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigeTreeTokenBoost");
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigeTreeTokenBoost", tokenboost+2);
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigePoints", prestigepoints-1);
                openPrestigeTree(p);
                PlayerDataHandler.getInstance().savePlayerData(p);
            }else if(e.getSlot()==3){
                int fortuneboost = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigeTreeFortuneBoost");
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigeTreeFortuneBoost", fortuneboost+2);
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigePoints", prestigepoints-1);
                openPrestigeTree(p);
                PlayerDataHandler.getInstance().savePlayerData(p);
            }else if(e.getSlot()==5){
                int gemboost = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigeTreeGemBoost");
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigeTreeGemBoost", gemboost+2);
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigePoints", prestigepoints-1);
                openPrestigeTree(p);
                PlayerDataHandler.getInstance().savePlayerData(p);
            }else if(e.getSlot()==7){
                double luckboost = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("PrestigeTreeLuckBoost");
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigeTreeLuckBoost", luckboost+0.5);
                PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigePoints", prestigepoints-1);
                openPrestigeTree(p);
                PlayerDataHandler.getInstance().savePlayerData(p);
            }
        }
    }


    public void prestige(Player p) {
        String uuid = p.getUniqueId().toString();

        //Adding Prestige and resetting rank
        int prestiges = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges");
        PlayerDataHandler.getInstance().getPlayerData(p).set("Prestiges", (prestiges + 1));
        RankupHandler.getInstance().setRank(p, 1);
        Main.econ.withdrawPlayer(p, Main.econ.getBalance(p));
        PlayerDataHandler.getInstance().savePlayerData(p);
        TitleAPI.sendTitle(p, 2, 40, 2, m.c("&c&lPrestiged!"), m.c("&b&lPrestige +1"));
        MineHandler.getInstance().updateMine(p, 1);
        CMDVoteShop.addCoupon(p, 0.25);
        LocksmithHandler.getInstance().addKey(p, "Seasonal", 1);
        int prestigepoints = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PrestigePoints");
        PlayerDataHandler.getInstance().getPlayerData(p).set("PrestigePoints", prestigepoints+1);
        settings.saveRankupPrices();
    }

    public static void addPrestiges(Player p, int amt) {
        String uuid = p.getUniqueId().toString();
        int prestiges = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges");
        PlayerDataHandler.getInstance().getPlayerData(p).set("Prestiges", prestiges + amt);
        PlayerDataHandler.getInstance().savePlayerData(p);
    }


}
