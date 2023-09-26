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

        ItemStack gems = new ItemStack(Material.INK_SAC);
        ItemMeta gm = gems.getItemMeta();
        gm.setDisplayName(m.c("&aGems:"));
        lore.add(m.c("&7Gems are acquired from Gem Pouches."));
        lore.add(m.c("&7Players have a chance to receive Gem Pouches randomly."));
        lore.add(m.c("&7You can right click the pouch to receive all the gems it contains."));
        lore.add(m.c("&7You can spend the gems in /gem shop."));
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

        //gangs, vote, new gems, path & skill enchants, ethereal & enchants, trinkets

        ItemStack gangs = new ItemStack(Material.GOLD_INGOT);
        ItemMeta gangsmeta = gangs.getItemMeta();
        gangsmeta.setDisplayName(m.c("&c&lGangs:"));
        lore.add(m.c("&7You can create a gang with /gang create <Name>"));
        lore.add(m.c("&7After mining certain amounts of blocks you can unlock gang boosts."));
        lore.add(m.c("&7You can invite people to your gang, the block milestone is teamwork."));
        gangsmeta.setLore(lore);
        gangs.setItemMeta(gangsmeta);
        help.setItem(3, gangs);
        lore.clear();

        ItemStack vote = new ItemStack(Material.STICK);
        ItemMeta votemeta = vote.getItemMeta();
        votemeta.setDisplayName(m.c("&6&lVote:"));
        lore.add(m.c("&7You can open the voting menu with /vote."));
        lore.add(m.c("&7Each vote on a site is a votepoint added to your account."));
        lore.add(m.c("&7With each vote you can open a single treasure chest in Treasury."));
        lore.add(m.c("&7There are always at least 3 good rewards in each Treasury."));
        votemeta.setLore(lore);
        vote.setItemMeta(votemeta);
        help.setItem(4, vote);
        lore.clear();

        ItemStack paths = new ItemStack(Material.POWERED_RAIL);
        ItemMeta pathsmeta = paths.getItemMeta();
        pathsmeta.setDisplayName(m.c("&a&lPaths:"));
        lore.add(m.c("&7You can open the paths menu after you unlock level 25 on your pickaxe."));
        lore.add(m.c("&7It is located in the enchanting menu on the wooden pickaxe."));
        lore.add(m.c("&7You get a skillpoint for each level after 25 on your pickaxe."));
        lore.add(m.c("&7Boosts in the path menu are permanent."));
        lore.add(m.c("&7After you finish a path you unlock the path specific enchant by rebirthing your pickaxe."));
        pathsmeta.setLore(lore);
        paths.setItemMeta(pathsmeta);
        help.setItem(5, paths);
        lore.clear();

        ItemStack ethereal = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta etherealmeta = ethereal.getItemMeta();
        etherealmeta.setDisplayName(m.c("&b&lEthereal:"));
        lore.add(m.c("&7Ethereal is reached after hitting Prestige 100 Level 1000."));
        lore.add(m.c("&7With Ethereal you unlock Ethereal enchants located in the enchant menu."));
        lore.add(m.c("&7After hitting Ethereal ranking up gets progressively harder."));
        etherealmeta.setLore(lore);
        ethereal.setItemMeta(etherealmeta);
        help.setItem(39, ethereal);
        lore.clear();

        ItemStack trinkets = new ItemStack(Material.GOLD_NUGGET);
        ItemMeta trinketsmeta = trinkets.getItemMeta();
        trinketsmeta.setDisplayName(m.c("&6&lTrinkets:"));
        lore.add(m.c("&7Trinkets provide a boost to enhance your pickaxe."));
        lore.add(m.c("&7There are different types of trinkets(Token, Luck, Sell, XP)."));
        lore.add(m.c("&7There are different tiers of trinkets(Common, Rare, Epic, Legendary, Heroic)."));
        lore.add(m.c("&7You can smelt the trinkets in the /craft menu to get dust of higher quality."));
        lore.add(m.c("&7Use 9 dust of the same type to craft the specified trinket."));
        trinketsmeta.setLore(lore);
        trinkets.setItemMeta(trinketsmeta);
        help.setItem(41, trinkets);
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
        if (e.getView().getTitle().equals(m.c("&6&lHelp:"))) {
            e.setCancelled(true);
        }
    }


}
