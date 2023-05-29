package me.dxrk.Events;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Enchants.SkillsEventsListener;
import me.dxrk.Main.Functions;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.Mine;
import me.dxrk.Mines.MineSystem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;
import java.util.Random;

public class PickXPHandler
        implements Listener {

    public static PickXPHandler instance = new PickXPHandler();

    public static PickXPHandler getInstance() {
        return instance;
    }


    public Methods m = Methods.getInstance();

    SettingsManager settings = SettingsManager.getInstance();


    public double getXP(Player p) {
        double xp = settings.getPlayerData().getDouble(p.getUniqueId().toString() + ".PickXP");
        return xp;
    }

    public int getLevel(Player p) {
        int level = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".PickLevel");
        return level;
    }


    public void addXP(Player p, double add) {
        double xp = getXP(p);

        settings.getPlayerData().set(p.getUniqueId().toString() + ".PickXP", (xp + add));
    }

    public void levelUp(Player p) {
        int level = getLevel(p);
        int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString() + ".PickaxeSkillPoints");
        settings.getPlayerData().set(p.getUniqueId().toString() + ".PickLevel", level + 1);
        if (level >= 25) {
            settings.getPlayerData().set(p.getUniqueId().toString() + ".PickaxeSkillPoints", skillPoints + 1);
        }
        settings.savePlayerData();
    }

    public boolean canLevelUp(Player p) {
        boolean levelup = calculateXPNeeded(getLevel(p)) <= getXP(p);

        return levelup;
    }

    public double calculateXPNeeded(int level) {
        double cost = 0;
        for (int i = 0; i <= level; i++) {
            cost += cost(i);
        }
        return cost;
    }

    public double cost(int pick) {
        return 1000 + (1000 * (pick * 0.3));
    }

    public double totalXP() {
        double cost = 0;
        for (int i = 0; i <= 300; i++) {
            cost += cost(i);
        }
        return cost;
    }


    public double findPercent(Player p) {
        double percent = (getXP(p) / calculateXPNeeded(getLevel(p))) * 100;
        double dmultiply = percent * 10.0;
        double dround = Math.round(dmultiply) / 10.0;

        return dround;
    }


    public void updatePickaxe(Player p) {
        int line = 0;

        ItemStack pitem = p.getItemInHand().clone();
        ItemMeta pm = pitem.getItemMeta();

        List<String> lore = p.getItemInHand().getItemMeta().getLore();
        for (int i = 0; i < lore.size(); i++) {
            if (ChatColor.stripColor(lore.get(i)).contains("Level:")) {
                line = i;
            }
        }
        lore.set(line, m.c("&cLevel: &e" + getLevel(p)));

        line = 0;
        for (int i = 0; i < lore.size(); i++) {
            if (ChatColor.stripColor(lore.get(i)).contains("Progress:")) {
                line = i;
            }
        }
        lore.set(line, m.c("&cProgress: &e" + findPercent(p) + "%"));

        pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
        pitem.setItemMeta(pm);
        p.setItemInHand(pitem);


    }

    public void updateXpBoard(Player p) {
        Scoreboard board = p.getScoreboard();

        double xp = (calculateXPNeeded(getLevel(p)) - getXP(p));
        double xmultiply = xp * 10.0;
        int xround = (int) (Math.round(xmultiply) / 10.0);

        board.getTeam("xp").setSuffix(m.c("&b" + xround));
        board.getTeam("PickLevel").setSuffix(m.c("&b" + getLevel(p)));
    }


    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (!(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public static ApplicableRegionSet set(Block b) {
        WorldGuardPlugin worldGuard = WorldGuardPlugin.inst();
        RegionManager regionManager = worldGuard.getRegionManager(b.getWorld());
        return regionManager.getApplicableRegions(b.getLocation());
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null) return;
        if (!p.getItemInHand().hasItemMeta()) return;
        if (!p.getItemInHand().getItemMeta().hasLore()) return;
        WorldGuardPlugin wg = (WorldGuardPlugin) Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
        ApplicableRegionSet set = wg.getRegionManager(p.getWorld()).getApplicableRegions(e.getBlock().getLocation());
        ProtectedRegion region = wg.getRegionManager(p.getWorld()).getRegion(p.getName());
        if (!set.getRegions().contains(region)) {
            e.setCancelled(true);
            return;
        }
        if (!set(e.getBlock()).allows(DefaultFlag.LIGHTER)) return;
        Random r = new Random();
        int fmin = 1;
        int fmax = 4;
        int xp = r.nextInt(fmax - fmin) + fmin;
        double xpToAdd = xp * Functions.xpBoost(p) * Functions.XPEnchant(p) * BoostsHandler.xp * SkillsEventsListener.getEventXP();
        double xpToAdd2 = Functions.xpBoost(p) * Functions.XPEnchant(p) * BoostsHandler.xp * SkillsEventsListener.getEventXP();
        if (getLevel(p) < 16)
            addXP(p, xpToAdd);
        else {
            addXP(p, xpToAdd2);
        }

        if (canLevelUp(p)) levelUp(p);
        int update = r.nextInt(20);
        if (update == 1) {
            updatePickaxe(p);
        }
        updateXpBoard(p);


    }


}

