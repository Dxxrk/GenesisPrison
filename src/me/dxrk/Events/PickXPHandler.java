package me.dxrk.Events;

import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Main.Functions;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;

import java.util.List;

public class PickXPHandler
implements Listener {
	
	public static PickXPHandler instance = new PickXPHandler();
	public static PickXPHandler getInstance() {
		return instance;
	}
	
	
	public Methods m = Methods.getInstance();

    SettingsManager settings = SettingsManager.getInstance();

	
	

    public double getXP(Player p){
        double xp = settings.getPlayerData().getDouble(p.getUniqueId().toString()+".PickXP");
        return xp;
    }

    public int getLevel(Player p) {
        int level = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickLevel");
        return level;
    }


    public void addXP(Player p, double add) {
        double xp = getXP(p);

        settings.getPlayerData().set(p.getUniqueId().toString()+".PickXP", (xp+add));
        settings.savePlayerData();
    }

    public void levelUp(Player p) {
        int level = getLevel(p);
        int skillPoints = settings.getPlayerData().getInt(p.getUniqueId().toString()+".PickaxeSkillPoints");
        settings.getPlayerData().set(p.getUniqueId().toString()+".PickLevel", level+1);
        if(level >=25){
            settings.getPlayerData().set(p.getUniqueId().toString()+".PickaxeSkillPoints", skillPoints+1);
        }
        settings.getPlayerData().set(p.getUniqueId().toString()+".PickXP", 0);
        settings.savePlayerData();
    }

    public boolean canLevelUp(Player p) {
        boolean levelup = calculateXPNeeded(p, getLevel(p)) <= getXP(p);

        return levelup;
    }


    public double calculateXPNeeded(Player p, int pick) {
        double needed;


        if(pick == 1){
            return 2500;
        }

        needed =2500+(2500*((pick+1)*0.8));



        return needed;
    }



    public double findPercent(Player p) {
        double percent = (getXP(p) / calculateXPNeeded(p, getLevel(p))) * 100;
        double dmultiply = percent * 10.0;
        double dround = Math.round(dmultiply) / 10.0;

        return dround;
    }


    public void updatePickaxe(Player p) {
        int line = 0;

        ItemStack pitem = p.getItemInHand().clone();
        ItemMeta pm = pitem.getItemMeta();

        List<String> lore = p.getItemInHand().getItemMeta().getLore();
        for(int i = 0; i < lore.size(); i++){
            if(ChatColor.stripColor(lore.get(i)).contains("Level:")){
                line = i;
            }
        }
        lore.set(line, m.c("&cLevel: &e"+getLevel(p)));

        line = 0;
        for(int i = 0; i < lore.size(); i++){
            if(ChatColor.stripColor(lore.get(i)).contains("Progress:")){
                line = i;
            }
        }
        lore.set(line, m.c("&cProgress: &e"+findPercent(p)+"%"));

        pm.setLore(PickaxeLevel.getInstance().Lore(lore, p));
        pitem.setItemMeta(pm);
        p.setItemInHand(pitem);


    }

    public void updateXpBoard(Player p){
        Scoreboard board = p.getScoreboard();

        double xp = (calculateXPNeeded(p, getLevel(p)) - getXP(p));
        double xmultiply = xp * 10.0;
        int xround = (int) (Math.round(xmultiply) / 10.0);

        board.getTeam("xp").setSuffix(m.c("&b"+xround));
        board.getTeam("PickLevel").setSuffix(m.c("&b"+getLevel(p)));
    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(p.getItemInHand() == null) return;
        if(!p.getItemInHand().hasItemMeta()) return;
        if(!p.getItemInHand().getItemMeta().hasLore()) return;
        double xpToAdd = Functions.xpBoost(p) * Functions.XPEnchant(p) * BoostsHandler.xp;

        addXP(p, xpToAdd);

        if(canLevelUp(p)) levelUp(p);

        updatePickaxe(p);
        updateXpBoard(p);



    }



    
    

}

