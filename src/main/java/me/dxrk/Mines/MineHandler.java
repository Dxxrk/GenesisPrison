package me.dxrk.Mines;

import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

public class MineHandler implements Listener, CommandExecutor {

    public static MineHandler instance = new MineHandler();

    public static MineHandler getInstance() {
        return instance;
    }

    static String c() {
        return ChatColor.translateAlternateColorCodes('&', "&cYou cannot drop your pickaxe!");
    }


    static SettingsManager settings = SettingsManager.getInstance();

    public boolean hasMine(Player p) {
        return PlayerDataHandler.getInstance().getPlayerData(p.getUniqueId()).getBoolean("HasMine");
    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player player = (Player) sender;

        return false;
    }

    @EventHandler
    public void drop(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().getType().equals(Material.DIAMOND_PICKAXE) || e.getItemDrop().getItemStack().getType().equals(Material.WOODEN_PICKAXE) ||
                e.getItemDrop().getItemStack().getType().equals(Material.STONE_PICKAXE) || e.getItemDrop().getItemStack().getType().equals(Material.IRON_PICKAXE) ||
                e.getItemDrop().getItemStack().getType().equals(Material.GOLDEN_PICKAXE)) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(c());
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity ent = event.getEntity();
        if (ent instanceof Player player) {
            if (event.getCause().equals(EntityDamageEvent.DamageCause.SUFFOCATION)) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {

    }


    public void createMine(String name, Location corner1, Location corner2, Location spawn, World world, double percent) {
        Mine m = new Mine(name, corner1, corner2, spawn, world, percent);
        m.save();
        MineSystem.getInstance().addActiveMine(m);
    }


    /*
        CREATING THE MINE
     */
    @SuppressWarnings("deprecation")
    public void CreateMine(Player p, String mineworld) {
        //Setting up the world
        World world = Bukkit.getWorld(mineworld);


        Location pworld = new Location(world, 27.5, 4, 0.5, 90, 0);

        Location point1 = new Location(world, -20, -63, -20);
        Location point2 = new Location(world, 20, 3, 20);


        createMine(p.getUniqueId().toString(), point1, point2, pworld, world, 25.0);
        Mine m = MineSystem.getInstance().getMineByPlayer(p);
        p.sendMessage(Methods.getInstance().c("&f&lMine &8| &bLoading Mine.."));
        Bukkit.getScheduler().runTaskLater(Main.plugin,
                () -> {
                    p.teleport(pworld);
                    //TODO WorldBorder for new Mine
                    Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 150, 0.5, m.getSpawnLocation().getZ());
                    //Methods.getInstance().createWorldBorder(p, world, 119, 0.5, (mines * 500) + 0.5);
                }, 50L);


        PlayerDataHandler.getInstance().getPlayerData(p).set("HasMine", true);
        settings.saveOptions();
        PlayerDataHandler.getInstance().savePlayerData(p);
    }
}
