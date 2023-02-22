package me.dxrk.Commands;

import me.dxrk.Events.BlocksHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.SkullType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class CMDStats implements Listener, CommandExecutor {

    SettingsManager settings = SettingsManager.getInstance();
    Methods m = Methods.getInstance();

    private String formatTime(int seconds){
        int day = (int) TimeUnit.SECONDS.toDays(seconds);
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        return m.c("&b"+day+"D "+hours+"H "+minute+"M "+second+"S");
    }

    private String getRank(Player p){

        if(p.hasPermission("rank.genesis")){
            return m.c("&4&lG&c&le&6&ln&e&le&a&ls&b&li&d&ls");
        }
        else if(p.hasPermission("rank.olympian")){
            return m.c("&e&lOlympian");
        }
        else if(p.hasPermission("rank.God")){
            return m.c("&d&lGod");
        }
        else if(p.hasPermission("rank.Titan")){
            return m.c("&3&lTitan");
        }
        else if(p.hasPermission("rank.Demi-God")){
            return m.c("&5&lDemi-God");
        }
        else if(p.hasPermission("rank.Hero")){
            return m.c("&c&lHero");
        }
        else if(p.hasPermission("rank.MVP")){
            return m.c("&6&lMVP");
        }
        else if(p.hasPermission("rank.VIP")){
            return m.c("&a&lVIP");
        }
        else if(p.hasPermission("rank.Donator")){
            return m.c("&b&lDonator");
        }

        return "";
    }

    public ItemStack Head(OfflinePlayer p) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(p.getName());
        skull.setItemMeta(meta);
        return skull;
    }

    public void openStats(Player opener, String uuid){
        OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
    /*Under Player Head
    Keys Found
    Crates Opened
    Time Played
     */

        Inventory stats = Bukkit.createInventory(null, 54, m.c("&c&lStats:"));
        for(int i = 0; i<54; i++){
            ItemStack fence = new ItemStack(Material.IRON_FENCE);
            ItemMeta fm = fence.getItemMeta();
            fm.setDisplayName(m.c("&c&lGenesis &b&lPrison"));
            fence.setItemMeta(fm);
            stats.setItem(i, fence);
        }
        List<String> lore = new ArrayList<>();
        ItemStack player = Head(p);
        ItemMeta pm = player.getItemMeta();
        pm.setDisplayName(m.c("&f&lPlayer: "+p.getName()));
        lore.add(m.c("&7Keys Found: &b"+settings.getPlayerData().getInt(uuid+".KeysFound")));
        lore.add(m.c("&7Crates Opened: &b"+settings.getPlayerData().getInt(uuid+".CratesOpened")));
        lore.add(m.c("&7Time Played: ")+formatTime(settings.getPlayerData().getInt(uuid+".TimePlayed")));
        pm.setLore(lore);
        player.setItemMeta(pm);
        lore.clear();
        stats.setItem(10, player);

        ItemStack multi = new ItemStack(Material.NETHER_STAR);
        ItemMeta mm = multi.getItemMeta();
        mm.setDisplayName(m.c("&7Multi: &b"+settings.getMultiplier().getInt(uuid)));
        multi.setItemMeta(mm);
        stats.setItem(21, multi);

        ItemStack level = new ItemStack(Material.ENDER_PEARL);
        ItemMeta lm = level.getItemMeta();
        lm.setDisplayName(m.c("&7Level: &b"+ settings.getRankupPrices().getInt(uuid)));
        level.setItemMeta(lm);
        stats.setItem(23, level);

        ItemStack prestiges = new ItemStack(Material.EYE_OF_ENDER);
        ItemMeta prm = prestiges.getItemMeta();
        prm.setDisplayName(m.c("&7Prestiges: &b"+settings.getPlayerData().getInt(uuid+".Prestiges")));
        prestiges.setItemMeta(prm);
        stats.setItem(16, prestiges);

        ItemStack pickaxe = settings.getPlayerData().getItemStack(uuid+".Pickaxe");
        stats.setItem(28, pickaxe);

        ItemStack blocks = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta bm = blocks.getItemMeta();
        bm.setDisplayName(m.c("&7Blocks Broken: &b"+ BlocksHandler.blocks.get(p)));
        blocks.setItemMeta(bm);
        stats.setItem(39, blocks);

        ItemStack votes = new ItemStack(Material.PAPER);
        ItemMeta vm = votes.getItemMeta();
        vm.setDisplayName(m.c("&7Votes: &b"+settings.getVote().getInt(uuid + ".Votes")));
        votes.setItemMeta(vm);
        stats.setItem(41, votes);

        ItemStack tokens = new ItemStack(Material.PRISMARINE_CRYSTALS);
        ItemMeta tm = tokens.getItemMeta();
        tm.setDisplayName(m.c("&7Tokens: &e⛀"+settings.getET().getInt(uuid)));
        tokens.setItemMeta(tm);
        stats.setItem(34, tokens);

        opener.openInventory(stats);

    }


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(cmd.getName().equalsIgnoreCase("stats")){
            Player player = (Player) sender;


            if(args.length == 1){
                for (String uuid : settings.getPlayerData().getKeys(false)) {
                    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                    if(p.getName().equalsIgnoreCase(args[0])){
                        openStats(player, uuid);
                    }
                }

            }
            if(args.length == 0) {
                openStats(player, player.getUniqueId().toString());
            }
        }


        return false;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        if(e.getInventory().getName().equals(m.c("&c&lStats:")))
            e.setCancelled(true);
    }





}
