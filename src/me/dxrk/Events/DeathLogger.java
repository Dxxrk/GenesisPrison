package me.dxrk.Events;

import me.dxrk.Main.SettingsManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathLogger implements Listener {
    SettingsManager settings = SettingsManager.getInstance();

    @EventHandler
    public void Death(PlayerDeathEvent e) {
        Player p = e.getEntity().getPlayer();
        if (e.getEntity().getKiller() instanceof Player) {
            Player killer = e.getEntity().getKiller();
            if (!this.settings.getData().contains(String.valueOf(p.getUniqueId().toString()) + ".Deaths"))
                this.settings.getData().set(String.valueOf(p.getUniqueId().toString()) + ".Deaths", Integer.valueOf(0));
            int deaths = this.settings.getData().getInt(String.valueOf(p.getName()) + ".Deaths");
            this.settings.getData().set(String.valueOf(p.getName()) + ".Deaths", Integer.valueOf(deaths + 1));
            if (!this.settings.getData().contains(String.valueOf(killer.getUniqueId().toString()) + ".Kills"))
                this.settings.getData().set(String.valueOf(killer.getUniqueId().toString()) + ".Kills", Integer.valueOf(0));
            int kills = this.settings.getData().getInt(String.valueOf(killer.getName()) + ".Kills");
            this.settings.getData().set(String.valueOf(killer.getUniqueId().toString()) + ".Kills", Integer.valueOf(kills + 1));
            this.settings.saveData();
        }
    }
}
