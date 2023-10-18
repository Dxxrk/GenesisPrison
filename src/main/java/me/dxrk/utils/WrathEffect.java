package me.dxrk.utils;

import java.security.SecureRandom;
import me.dxrk.Main.Main;
import me.dxrk.Mines.MineSystem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Random;

public class WrathEffect {


    Runnable timer = () -> {
        for (Player p : Bukkit.getOnlinePlayers()) {
            Random r = new SecureRandom();
            int i = r.nextInt(10);
            if(i == 0) {
                p.getWorld().strikeLightningEffect(MineSystem.getInstance().getMineByPlayer(p).getSpawnLocation());
                //Add method for turning the mine into some special block for a short period
            }
        }
    };

    private int id;

    public WrathEffect() {
        start();
    }

    private void start() {
        id = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(Main.class), timer, 0, 60*20L);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(id);
    }




}
