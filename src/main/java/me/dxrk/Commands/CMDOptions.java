package me.dxrk.Commands;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class CMDOptions implements Listener, CommandExecutor {

    SettingsManager settings = SettingsManager.getInstance();
    static Methods m = Methods.getInstance();


    private ItemStack option(Player p, String option) {
        ItemStack ops = new ItemStack(Material.INK_SAC);
        ItemMeta om = ops.getItemMeta();
        List<String> lore = new ArrayList<>();
        if (settings.getOptions().getBoolean(p.getUniqueId() + "." + option) == true) {
            ops.setDurability((short) 10);
            om.setDisplayName(m.c("&a" + option) + " Enabled");
            lore.add(m.c("&7Click to Disable"));
        } else {
            ops.setDurability((short) 1);
            om.setDisplayName(m.c("&c" + option) + " Disabled");
            lore.add(m.c("&7Click to Enable"));
        }
        om.setLore(lore);
        ops.setItemMeta(om);

        return ops;
    }


    public void openOptions(Player p) {
        Inventory ops = Bukkit.createInventory(null, 45, m.c("&3&lOptions:"));
        for (int i = 0; i < 45; i++) {
            ops.setItem(i, Spacer());
        }
        ops.setItem(1, option(p, "Autorankup"));
        ops.setItem(2, option(p, "Dust-Finder-Messages"));
        ops.setItem(3, option(p, "Key-Finder-Messages"));
        ops.setItem(4, option(p, "Jackhammer-Messages"));
        ops.setItem(5, option(p, "Nuke-Messages"));
        ops.setItem(6, option(p, "Key-Party-Messages"));
        ops.setItem(7, option(p, "Booster-Messages"));
        ops.setItem(10, option(p, "Junkpile-Messages"));
        ops.setItem(11, option(p, "Treasury-Messages"));
        ops.setItem(12, option(p, "Seismic-Shock-Messages"));
        ops.setItem(13, option(p, "Calamity-Messages"));
        ops.setItem(14, option(p, "Infernum-Messages"));
        ops.setItem(15, option(p, "Vote-Reminder"));
        ops.setItem(16, option(p, "Daily-Reminder"));
        ops.setItem(19, option(p, "Speed-Effect"));
        ops.setItem(20, option(p, "Haste-Effect"));
        ops.setItem(21, option(p, "Night-Vision-Effect"));
        ops.setItem(22, option(p, "LuckyBlock-Messages"));
        ops.setItem(23, option(p, "Tidal-Wave-Messages"));
        ops.setItem(24, option(p, "Key-Fisher-Messages"));
        ops.setItem(25, option(p, "Treasure-Hunter-Messages"));
        ops.setItem(28, option(p, "Multiplier-Messages"));
        ops.setItem(29, option(p, "Fishing-XPFinder-Messages"));
        p.openInventory(ops);
    }

    private ItemStack tagColor(String name, short data) {
        ItemStack item = new ItemStack(Material.INK_SAC, 1, data);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Change your tag color to " + ChatColor.stripColor(name)));
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public static String TagColor(String s) {
        if ("default".equalsIgnoreCase(s))
            return m.c("&f&ki&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls&f&ki&r");
        if ("pink".equalsIgnoreCase(s))
            return m.c("&f&ki&d&lGenesis&f&ki&r");
        if ("aqua".equalsIgnoreCase(s))
            return m.c("&f&ki&b&lGenesis&f&ki&r");
        if ("lime".equalsIgnoreCase(s))
            return m.c("&f&ki&a&lGenesis&f&ki&r");
        if ("yellow".equalsIgnoreCase(s))
            return m.c("&f&ki&e&lGenesis&f&ki&r");
        if ("red".equalsIgnoreCase(s))
            return m.c("&f&ki&c&lGenesis&f&ki&r");
        if ("green".equalsIgnoreCase(s))
            return m.c("&f&ki&2&lGenesis&f&ki&r");
        if ("gold".equalsIgnoreCase(s))
            return m.c("&f&ki&6&lGenesis&f&ki&r");
        if ("cyan".equalsIgnoreCase(s))
            return m.c("&f&ki&3&lGenesis&f&ki&r");
        if ("white".equalsIgnoreCase(s))
            return m.c("&f&ki&f&lGenesis&f&ki&r");
        if ("blue".equalsIgnoreCase(s))
            return m.c("&f&ki&c&lGenesis&f&ki&r");
        if ("purple".equalsIgnoreCase(s))
            return m.c("&f&ki&5&lGenesis&f&ki&r");
        if ("thematic".equalsIgnoreCase(s))
            return m.c("&f&ki&c&lG&b&le&c&ln&b&le&c&ls&b&li&c&ls&f&ki&r");
        return m.c("&f&ki&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls&f&ki&r");
    }

    public void openGenesis(Player p) {
        Inventory gen = Bukkit.createInventory(null, 18, m.c("&cChange Tag Color"));
        gen.setItem(0, tagColor(m.c("&4&lD&c&le&6&lf&e&la&a&lu&b&ll&d&lt"), (short) 8));
        gen.setItem(1, tagColor(m.c("&d&lPink"), (short) 9));
        gen.setItem(2, tagColor(m.c("&b&lAqua"), (short) 12));
        gen.setItem(3, tagColor(m.c("&a&lLime"), (short) 10));
        gen.setItem(4, tagColor(m.c("&e&lYellow"), (short) 11));
        gen.setItem(5, tagColor(m.c("&c&lRed"), (short) 1));
        gen.setItem(6, tagColor(m.c("&2&lGreen"), (short) 2));
        gen.setItem(7, tagColor(m.c("&6&lGold"), (short) 14));
        gen.setItem(8, tagColor(m.c("&3&lCyan"), (short) 6));
        gen.setItem(12, tagColor(m.c("&9&lBlue"), (short) 4));
        gen.setItem(13, tagColor(m.c("&f&lWhite"), (short) 15));
        gen.setItem(14, tagColor(m.c("&5&lPurple"), (short) 5));
        gen.setItem(15, tagColor(m.c("&c&lT&b&lh&c&le&b&lm&c&la&b&lt&c&li&b&lc"), (short) 1));
        p.openInventory(gen);
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if ("options".equalsIgnoreCase(label)) {
            Player p = (Player) sender;
            openOptions(p);
        }
        if (cmd.getName().equalsIgnoreCase("genesis")) {
            Player p = (Player) sender;
            if (p.hasPermission("rank.genesis"))
                openGenesis(p);
            else
                p.sendMessage(m.c("&cNo Permission"));
            if(p.isOp()) {
                if("reload".equalsIgnoreCase(args[0])) {
                    SettingsManager.getInstance().reloadConfig();
                }
            }
        }


        return false;
    }

    public ItemStack Spacer() {
        ItemStack i = new ItemStack(Material.IRON_BARS);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lGenesis &b&lPrison"));
        i.setItemMeta(im);
        return i;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            this.settings.getOptions().set(p.getUniqueId() + ".Autorankup", false);
            this.settings.getOptions().set(p.getUniqueId() + ".Tokens-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Dust-Finder-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Key-Finder-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Explosion-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Wave-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Nuke-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Treasury-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Key-Party-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Booster-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Junkpile-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Laser-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Daily-Reminder", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Vote-Reminder", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Speed-Effect", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Haste-Effect", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Night-Vision-Effect", true);
            this.settings.getOptions().set(p.getUniqueId() + ".LuckyBlock-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Tidal-Wave-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Key-Fisher-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Treasure-Hunter-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Fishing-XPFinder-Messages", true);
            this.settings.getOptions().set(p.getUniqueId() + ".Multiplier-Messages", true);
            this.settings.saveOptions();
        }
        if (this.settings.getOptions().get(p.getUniqueId() + ".GenesisColor") == null) {
            this.settings.getOptions().set(p.getUniqueId() + ".GenesisColor", "Default");
            this.settings.saveOptions();
        }
        if (this.settings.getVote().get(p.getUniqueId() + ".HasVoted") == null) {
            this.settings.getVote().set(p.getUniqueId() + ".HasVoted", "nope");
            this.settings.saveVote();
        }
    }


    @EventHandler
    public void optionsClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null)
            return;
        if (e.getView().getTitle() == null)
            return;

        if (e.getView().getTitle().equals(m.c("&3&lOptions:"))) {
            e.setCancelled(true);
            if (e.getCurrentItem().equals(Spacer())) return;
            if (e.getCurrentItem().getType().equals(Material.INK_SAC)) {
                String[] name = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split(" ");
                if ("Enabled".equals(name[1])) {
                    this.settings.getOptions().set(p.getUniqueId() + "." + name[0], false);

                } else if ("Disabled".equals(name[1])) {
                    this.settings.getOptions().set(p.getUniqueId() + "." + name[0], true);

                }
            }
            openOptions(p);
            this.settings.saveOptions();

        }
        if (e.getView().getTitle().equals(m.c("&cChange Tag Color"))) {
            e.setCancelled(true);
            String color = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            p.sendMessage(m.c("&7Prefix Changed to: " + e.getCurrentItem().getItemMeta().getDisplayName()));
            this.settings.getOptions().set(p.getUniqueId() + ".GenesisColor", color);
            this.settings.saveOptions();

        }


    }

}
