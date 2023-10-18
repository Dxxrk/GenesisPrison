package me.dxrk.Events;

import java.security.SecureRandom;
import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Enchants.SkillsEventsListener;
import me.dxrk.Main.Functions;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineSystem;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
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
        double xp = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("PickXP");
        return xp;
    }

    public int getLevel(Player p) {
        int level = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PickLevel");
        return level;
    }


    public void addXP(Player p, double add) {
        double xp = getXP(p);

        PlayerDataHandler.getInstance().getPlayerData(p).set("PickXP", (xp + add));
    }

    public void levelUp(Player p) {
        int level = getLevel(p);
        int skillPoints = PlayerDataHandler.getInstance().getPlayerData(p).getInt("PickaxeSkillPoints");
        PlayerDataHandler.getInstance().getPlayerData(p).set("PickLevel", level + 1);
        if (level >= 25) {
            PlayerDataHandler.getInstance().getPlayerData(p).set("PickaxeSkillPoints", skillPoints + 1);
        }
        PlayerDataHandler.getInstance().savePlayerData(p);
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
        return 1000 + (1000 * (pick * 0.40));
    }


    public double findPercent(Player p) {
        double percent = ((getXP(p) - calculateXPNeeded(getLevel(p) - 1)) / (calculateXPNeeded(getLevel(p)) - calculateXPNeeded(getLevel(p) - 1))) * 100;
        double dmultiply = percent * 10.0;
        double dround = Math.round(dmultiply) / 10.0;
        return dround;
    }


    public void updatePickaxe(Player p) {
        int line = 0;

        ItemStack pitem = p.getEquipment().getItemInMainHand().clone();
        ItemMeta pm = pitem.getItemMeta();

        List<String> lore = p.getEquipment().getItemInMainHand().getItemMeta().getLore();
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




    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getEquipment().getItemInMainHand() == null) return;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return;
        if (!p.getEquipment().getItemInMainHand().getItemMeta().hasLore()) return;
        if (!MineSystem.getInstance().getMineByPlayer(p).isLocationInMine(e.getBlock().getLocation())) {
            return;
        }
        Random r = new SecureRandom();
        int fmin = 1;
        int fmax = 4;
        int xp = r.nextInt(fmax - fmin) + fmin;
        double xpToAdd = xp * Functions.xpBoost(p) * Functions.XPEnchant(p) * BoostsHandler.xp.getOrDefault(p.getUniqueId(), 1.0) * SkillsEventsListener.getEventXP();
        double xpToAdd2 = Functions.xpBoost(p) * Functions.XPEnchant(p) * BoostsHandler.xp.getOrDefault(p.getUniqueId(), 1.0) * SkillsEventsListener.getEventXP();
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

