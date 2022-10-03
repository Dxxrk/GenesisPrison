package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class banlist implements Listener {



    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        if(e.getPlayer().getName().equals("BakonStrip")){
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban BakonStrip");
        }


    }



}
