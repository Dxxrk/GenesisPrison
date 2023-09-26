package me.dxrk.Tokens;

import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class TokensListener implements Listener {
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void PointsRedeem(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        if (p.getEquipment().getItemInMainHand() == null)
            return;
        if (p.getEquipment().getItemInMainHand().getType() != Material.PRISMARINE_CRYSTALS)
            return;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta())
            return;
        if (p.getEquipment().getItemInMainHand().getItemMeta().getDisplayName().contains(ChatColor.AQUA + "Token")) {
            int points = 0;
            for (int i = 0; i < 36; i++) {
                if (p.getInventory().getItem(i) != null) {
                    ItemStack ii = p.getInventory().getItem(i);
                    if (ii.hasItemMeta() && ii.getItemMeta().hasDisplayName() &&
                            ii.getItemMeta().getDisplayName().contains(ChatColor.AQUA + "Token")) {
                        points += ii.getAmount();
                        p.getInventory().setItem(i, null);
                    }
                }
            }
            Bukkit.dispatchCommand((CommandSender) Bukkit.getConsoleSender(), "tokens add " + p.getName() + " " + points);
            p.getLocation().getWorld().playEffect(p.getLocation(), Effect.SMOKE, 2, 2);
        }
    }
}
