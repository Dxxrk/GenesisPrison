package me.dxrk.Enchants;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.dxrk.Main.Functions;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.Mine;
import me.dxrk.Mines.MineSystem;
import me.dxrk.Mines.ResetHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Enchants implements Listener {

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    SettingsManager settings = SettingsManager.getInstance();

    static Enchants instance = new Enchants();

    public static Enchants getInstance() {
        return instance;
    }


    public int getLevel(String ss) {
        String s = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', ss));
        StringBuilder lvl = new StringBuilder();
        byte b;
        int i;
        char[] arrayOfChar;
        for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
            char c = arrayOfChar[b];
            if (isInt(c))
                lvl.append(c);
            b++;
        }
        if (isInt(lvl.toString()))
            return Integer.parseInt(lvl.toString());
        return -1;
    }

    public boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public List<String> getEnchants(ItemStack i) {
        return i.getItemMeta().getLore();
    }


    public int getFortune(String s) {
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
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }

    public void addFortune(Player p, ItemStack ii, int loree) {
        int blockss = this.getFortune(ii.getItemMeta().getLore().get(loree));
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(loree, ChatColor.GRAY + "Fortune " + ChatColor.GRAY + (blockss + 1));
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
        int blocks = blockss + 1;
    }


    private boolean hasEnchant(Player p, String enchant) {
        List<String> lore = p.getItemInHand().getItemMeta().getLore();
        int x;
        for (x = 0; x < lore.size(); x++) {
            String s = lore.get(x);
            if (ChatColor.stripColor(s).contains(enchant)) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (p.getItemInHand() == null)
            return;
        if (!p.getItemInHand().hasItemMeta())
            return;
        if (!p.getItemInHand().getItemMeta().hasLore())
            return;
        WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        ApplicableRegionSet set = wg.getRegionManager(p.getWorld()).getApplicableRegions(e.getBlock().getLocation());
        LocalPlayer player = wg.wrapPlayer(p);
        if (EnchantMethods.set(b).allows(DefaultFlag.LIGHTER) || set.isMemberOfAll(player)) {
            for (String s : EnchantMethods.getInstance().Enchants()) {
                if (hasEnchant(p, s))
                    EnchantMethods.getInstance().procEnchant(s, p, b);
            }
            Functions.Multiply(p);
            Mine m = MineSystem.getInstance().getMineByPlayer(p);
            if (m.getBlocksLeftPercentage() < m.getResetPercent()) {
                double lucky = settings.getPlayerData().getDouble(p.getUniqueId().toString() + ".LuckyBlock");
                ResetHandler.resetMineWorldEdit(m, m.getMinPoint(), m.getMaxPoint(), lucky);
            }
        }
    }

}
