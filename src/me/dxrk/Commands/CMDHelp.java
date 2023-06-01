package me.dxrk.Commands;

import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CMDHelp implements Listener, CommandExecutor {
    Methods m = Methods.getInstance();


    public void openHelp(Player p) {
        Inventory help = Bukkit.createInventory(null, 45, m.c("&6&lHelp:"));

        ItemStack mine = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta mm = mine.getItemMeta();
        mm.setDisplayName(m.c("&b&lMines:"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Each Player gets their own personal mine."));
        lore.add(m.c("&7Use '/mine' to create your mine."));
        lore.add(m.c("&7Use '/mine visit <Player>' to visit another players mine."));
        lore.add(m.c("&7You will get new blocks in your mine every 16 levels."));
        mm.setLore(lore);
        mine.setItemMeta(mm);
        help.setItem(2, mine);
        lore.clear();
        ItemStack crates = new ItemStack(Material.CHEST);
        ItemMeta cm = crates.getItemMeta();
        cm.setDisplayName(m.c("&6&lCrates/Keys:"));
        lore.add(m.c("&7Crates are available at '/warp crates'."));
        lore.add(m.c("&7Right Click a crate to open it."));
        lore.add(m.c("&7Shift+Right Click a crate to open all of it's kind at once."));
        lore.add(m.c("&7Use '/crateinfo <crate>' to view the contents remotely."));
        lore.add(m.c("&7Your keys can be viewed in the TAB menu as well as '/ls.'"));
        lore.add(m.c("&7&c&lHero &7rank and above can use the '/openall' command."));
        cm.setLore(lore);
        crates.setItemMeta(cm);
        help.setItem(6, crates);
        lore.clear();
        ItemStack mystery = new ItemStack(Material.ENDER_CHEST);
        ItemMeta mym = mystery.getItemMeta();
        mym.setDisplayName(m.c("&5&lMystery Crates:"));
        lore.add(m.c("&7Mystery Crates contain many great rewards."));
        lore.add(m.c("&7Obtain these through Crates, '/gem shop', and '/buy'."));
        mym.setLore(lore);
        mystery.setItemMeta(mym);
        help.setItem(20, mystery);
        lore.clear();
        ItemStack pickaxe = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta pm = pickaxe.getItemMeta();
        pm.setDisplayName(m.c("&c&lPickaxe:"));
        lore.add(m.c("&7Pickaxes are the core aspect which you will be playing for."));
        lore.add(m.c("&7Pickaxe Enchants are available by right clicking with it in hand."));
        lore.add(m.c("&7The Skill Tree will be unlocked at pickaxe level 25."));
        lore.add(m.c("&7Players can choose a path and will be granted buffs as they upgrade."));
        pm.setLore(lore);
        pickaxe.setItemMeta(pm);
        help.setItem(24, pickaxe);
        lore.clear();
        ItemStack gems = new ItemStack(Material.INK_SACK);
        ItemMeta gm = gems.getItemMeta();
        gm.setDisplayName(m.c("&aGems:"));
        lore.add(m.c("&7Gems are acquired from Gem Pouches."));
        lore.add(m.c("&7Players have a chance to receive Gem Pouches randomly."));
        lore.add(m.c("&7When you have a Gem Pouch, every block you break is +1 gem into the pouch."));
        lore.add(m.c("&7You can right click the pouch to receive all the gems it contains."));
        lore.add(m.c("&7You can use '/gem withdraw' to withdraw gems to sell or trade."));
        gm.setLore(lore);
        gems.setItemMeta(gm);
        help.setItem(38, gems);
        lore.clear();
        ItemStack tokens = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta tm = tokens.getItemMeta();
        tm.setDisplayName(m.c("&e&lTokens"));
        lore.add(m.c("&7Tokens are the primary currency used to upgrade enchants."));
        //lore.add(m.c("&7They are also used in selling/buying items in '/ah'"));
        lore.add(m.c("&7They can be withdrawn using '/tokens withdraw'"));
        lore.add(m.c("&7Use '/tokens send' to send another player tokens."));
        tm.setLore(lore);
        tokens.setItemMeta(tm);
        help.setItem(42, tokens);
        lore.clear();
        ItemStack commands = new ItemStack(Material.PAPER);
        ItemMeta com = commands.getItemMeta();
        com.setDisplayName(m.c("&d&lExtra Commands:"));
        lore.add(m.c("&7/daily - Access daily rewards"));
        lore.add(m.c("&7/coupons - View your coupon balance. These can be gained from voting or pickaxe skills."));
        lore.add(m.c("&7/kits - View the kits you have access to."));
        lore.add(m.c("&7/Boost - View boost queue or activate one of your own. Active boosts are shown in TAB."));
        lore.add(m.c("&7/leaderboard(/lb) - View the leaderboards for various categories."));
        lore.add(m.c("&7/Stats | /Stats <Name> - View your, or another player's statistics."));
        lore.add(m.c("&7/Trade - Trade with another Player."));
        //lore.add(m.c("&7/AH - Open the Action House."));
        com.setLore(lore);
        commands.setItemMeta(com);
        help.setItem(22, commands);
        lore.clear();
        help.setItem(0, PickaxeLevel.getInstance().Spacer());
        help.setItem(1, PickaxeLevel.getInstance().Spacer());
        help.setItem(7, PickaxeLevel.getInstance().Spacer());
        help.setItem(8, PickaxeLevel.getInstance().Spacer());
        help.setItem(9, PickaxeLevel.getInstance().Spacer());
        help.setItem(10, PickaxeLevel.getInstance().Spacer());
        help.setItem(16, PickaxeLevel.getInstance().Spacer());
        help.setItem(17, PickaxeLevel.getInstance().Spacer());
        help.setItem(18, PickaxeLevel.getInstance().Spacer());
        help.setItem(19, PickaxeLevel.getInstance().Spacer());
        help.setItem(25, PickaxeLevel.getInstance().Spacer());
        help.setItem(26, PickaxeLevel.getInstance().Spacer());
        help.setItem(27, PickaxeLevel.getInstance().Spacer());
        help.setItem(28, PickaxeLevel.getInstance().Spacer());
        help.setItem(34, PickaxeLevel.getInstance().Spacer());
        help.setItem(35, PickaxeLevel.getInstance().Spacer());
        help.setItem(36, PickaxeLevel.getInstance().Spacer());
        help.setItem(37, PickaxeLevel.getInstance().Spacer());
        help.setItem(43, PickaxeLevel.getInstance().Spacer());
        help.setItem(44, PickaxeLevel.getInstance().Spacer());

        p.openInventory(help);


    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("help")) {
            Player p = (Player) sender;
            if (args.length == 1) {
                if (args[0].equalsIgnoreCase("redeem")) {
                    p.sendMessage(m.c("&a- &e/Redeem &bcan be used to reclaim the money you previously spent on the store."));
                    p.sendMessage(m.c("&a- &bThis comes in the form of a Coupon."));
                    p.sendMessage(m.c("&a- &bBecause the store has changed so much we figured this would make the most sense."));
                    p.sendMessage(m.c("&a- &bA Coupon, by nature works via discounting the items in your cart by the amount of the coupon. This means that it is a &4&lSINGLE USE &bcode."));
                    p.sendMessage(m.c("&a- &bIf you run into a scenario where you want to purchase several items but are missing &4&lUP TO $5&b, "));
                    p.sendMessage(m.c("&bPlease make a ticket in the discord and we will simply disregard the extra cost, manually run the commands, and void the coupon."));
                    return false;
                }
            }
            openHelp(p);
        }

        return false;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getName().equals(m.c("&6&lHelp:"))) {
            e.setCancelled(true);
        }
    }


}
