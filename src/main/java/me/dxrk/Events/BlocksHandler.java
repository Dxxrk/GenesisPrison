package me.dxrk.Events;

import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineSystem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class BlocksHandler implements CommandExecutor, Listener {
    public static HashMap<Player, Integer> blocks = new HashMap<>();

    SettingsManager settings = SettingsManager.getInstance();

    static BlocksHandler instance = new BlocksHandler();

    public static BlocksHandler getInstance() {
        return instance;
    }


    public void onEnd() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (blocks.containsKey(p)) {
                PlayerDataHandler.getInstance().getPlayerData(p).set("BlocksBroken", blocks.get(p));
                PlayerDataHandler.getInstance().savePlayerData(p);
                blocks.remove(p);
            }
        }
    }

    public void onEndLB() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (blocks.containsKey(p)) {
                PlayerDataHandler.getInstance().getPlayerData(p).set("BlocksBroken", blocks.get(p));
                PlayerDataHandler.getInstance().savePlayerData(p);
            }
        }
    }


    @SuppressWarnings("deprecation")
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getEquipment().getItemInMainHand().getType() == Material.AIR)
            return;
        if (e.isCancelled())
            return;
        if (!MineSystem.getInstance().getMineByPlayer(p).isLocationInMine(e.getBlock().getLocation())) {
            return;
        }
        int blocksbroken = PlayerDataHandler.getInstance().getPlayerData(p).getInt("BlocksBroken");
        PlayerDataHandler.getInstance().getPlayerData(p).set("BlocksBroken", blocksbroken + 1);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        blocks.remove(p);
        this.settings.saveBlocks();
        PlayerDataHandler.getInstance().savePlayerData(p);

    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ("blocks".equalsIgnoreCase(label)) {
            if (!(sender instanceof Player p))
                return false;
            int blocksbroken = PlayerDataHandler.getInstance().getPlayerData(p).getInt("BlocksBroken");
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[&dBlocks&b] &bYou've broken &d&n" + blocksbroken + "&b blocks!"));
        }
        return false;
    }


}
