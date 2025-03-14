package me.dxrk.Events;

import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class LocksmithHandler implements Listener, CommandExecutor {
    static LocksmithHandler instance = new LocksmithHandler();

    public static LocksmithHandler getInstance() {
        return instance;
    }

    public SettingsManager settings = SettingsManager.getInstance();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ("locksmith".equalsIgnoreCase(label) || "ls".equalsIgnoreCase(label) || "keys".equalsIgnoreCase(label)) {
            if (!(sender instanceof Player))
                return false;
            openInv((Player) sender);
        }

        return true;
    }

    public boolean hasKeys(Player p) {
        if (settings.getLocksmith().getInt(p.getUniqueId() + ".vote") > 0) return true;
        else if (settings.getLocksmith().getInt(p.getUniqueId() + ".alpha") > 0) return true;
        else if (settings.getLocksmith().getInt(p.getUniqueId() + ".beta") > 0) return true;
        else if (settings.getLocksmith().getInt(p.getUniqueId() + ".omega") > 0) return true;
        else if (settings.getLocksmith().getInt(p.getUniqueId() + ".token") > 0) return true;
        else if (settings.getLocksmith().getInt(p.getUniqueId() + ".seasonal") > 0) return true;
        else if (settings.getLocksmith().getInt(p.getUniqueId() + ".community") > 0) return true;
        else return settings.getLocksmith().getInt(p.getUniqueId() + ".rank") > 0;
    }

    public void addKey(Player p, String key, int amt) {
        int keysfound = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId() + ".KeysFound");
        PlayerDataHandler.getInstance().getPlayerData(p).set(p.getUniqueId() + ".KeysFound", keysfound + amt);
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId() + ".name", p.getName());
            this.settings.saveLocksmith();
        }
        this.settings.getLocksmith().set(p.getUniqueId() + "." + key, keys + amt);
        this.settings.saveLocksmith();
    }

    public void takeKey(Player p, String key, int amt) {
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId() + ".name", p.getName());
            this.settings.saveLocksmith();
        }
        this.settings.getLocksmith().set(p.getUniqueId() + "." + key, keys - amt);
        this.settings.saveLocksmith();
    }

    public ItemStack key(String name, String lore, int amt) {
        ItemStack a = null;
        if (lore.contains("Alpha")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        } else if (lore.contains("Beta")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        } else if (lore.contains("Omega")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        } else if (lore.contains("Token")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        } else if (lore.contains("Vote")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        } else if (lore.contains("Seasonal")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        } else if (lore.contains("Community")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        } else if (lore.contains("Rank")) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, amt);
        }


        assert a != null;
        ItemMeta am = a.getItemMeta();
        am.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        am.setLore(Collections.singletonList(ChatColor.translateAlternateColorCodes('&', "&cRight Click &7the " + lore + " &7Crate")));
        a.setItemMeta(am);
        return a;
    }

    public ItemStack item(String name, int amt) {
        ItemStack a;
        a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta am = a.getItemMeta();
        if ("Alpha".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7&lAlpha &7Key &b(" + amt + ")"));
        }
        if ("Beta".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&c&lBeta &7Key &b(" + amt + ")"));
        }
        if ("Omega".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&lOmega &7Key &b(" + amt + ")"));
        }
        if ("Token".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&e&lToken &7Key &b(" + amt + ")"));
        }
        if ("Vote".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&f&lVote &7Key &b(" + amt + ")"));
        }
        if ("Seasonal".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l&ki&f&lSeasonal&4&l&ki&r &7Key &b(" + amt + ")"));
        }
        if ("Community".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&5&lCommunity &7Key &b(" + amt + ")"));
        }
        if ("Rank".equals(name)) {
            a = new ItemStack(Material.TRIPWIRE_HOOK, 1);
            am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&3&lRank &7Key &b(" + amt + ")"));
        }
        a.setItemMeta(am);
        return a;
    }

    public void openInv(Player p) {
        String uuid = p.getUniqueId().toString();
        int alpha = this.settings.getLocksmith().getInt(uuid + ".alpha");
        int beta = this.settings.getLocksmith().getInt(uuid + ".beta");
        int omega = this.settings.getLocksmith().getInt(uuid + ".omega");
        int token = this.settings.getLocksmith().getInt(uuid + ".token");
        int vote = this.settings.getLocksmith().getInt(uuid + ".vote");
        int seasonal = this.settings.getLocksmith().getInt(uuid + ".seasonal");
        int community = this.settings.getLocksmith().getInt(uuid + ".community");
        int rank = this.settings.getLocksmith().getInt(uuid + ".rank");
        Inventory inv = Bukkit.createInventory(null, 9,
                ChatColor.translateAlternateColorCodes('&', "&bLocksmith"));
        ItemStack mid = new ItemStack(Material.STONE_BUTTON, 1);
        ItemMeta am = mid.getItemMeta();
        am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&bLocksmith"));
        mid.setItemMeta(am);
        inv.setItem(0, item("Alpha", alpha));
        inv.setItem(1, item("Beta", beta));
        inv.setItem(2, item("Omega", omega));
        inv.setItem(3, item("Token", token));
        inv.setItem(4, mid);
        inv.setItem(5, item("Vote", vote));
        inv.setItem(6, item("Community", community));
        inv.setItem(7, item("Seasonal", seasonal));
        inv.setItem(8, item("Rank", rank));
        p.openInventory(inv);
    }

    @EventHandler
    public void onClick(PlayerInteractAtEntityEvent e) {
        if (e.getRightClicked().getCustomName() == null)
            return;
        if (ChatColor.stripColor(e.getRightClicked().getName()).contains("Locksmith"))
            openInv(e.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (!e.getView().getTitle().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', "&bLocksmith")))
            return;
        if (e.getCurrentItem() == null)
            return;
        e.setCancelled(true);
    }
}
