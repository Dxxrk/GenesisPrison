package me.dxrk.Events;

import me.dxrk.Commands.EssentialsCommands;
import me.dxrk.Enchants.Tool;
import me.dxrk.Enchants.ToolHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Mines.Mine;
import me.dxrk.Mines.MineHandler;
import me.dxrk.Mines.MineSystem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class JoinQuitHandler implements Listener {


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        p.teleport(new Location(Bukkit.getWorld("Spawn"), 0.5, 100.5, 0.5, 90, 0));
        if(!p.hasPlayedBefore()) {
            Tool pTool = ToolHandler.getInstance().createTool(Tool.defaultMiningPickaxe, p.getUniqueId().toString());

            ItemStack pToolItem = ToolHandler.getInstance().createToolItem(pTool);
            p.getInventory().addItem(pToolItem);
            p.updateInventory();
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        for(Tool t : ToolHandler.loadedTools) {
            if(t.getOwner().equalsIgnoreCase(p.getUniqueId().toString())) {
                t.save();
            }
        }
    }

}
