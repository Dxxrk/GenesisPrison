package me.dxrk.Events;


import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PlayerDataHandler implements Listener {
    private SettingsManager settings = SettingsManager.getInstance();
    private Methods m = Methods.getInstance();

    public static PlayerDataHandler instance = new PlayerDataHandler();


    public static PlayerDataHandler getInstance() {
        return instance;
    }

    public void createFolder() {
        File var1 = new File(Main.plugin.getDataFolder() + File.separator + "playerdata");
        if (!var1.exists()) {
            var1.mkdir();
        }
    }

    public void createPlayerData(Player p) {
        createFolder();
        try {
            File pdata = new File(Main.plugin.getDataFolder() + File.separator + "playerdata", p.getUniqueId().toString() + ".yml");
            if (!pdata.exists()) {
                pdata.createNewFile();
            }
            FileConfiguration pl = YamlConfiguration.loadConfiguration(pdata);
            String name = p.getName();
            String uuid = p.getUniqueId().toString();
            if (pl.get("Name") == null) {
                pl.set("Name", name);

            }
            if (pl.get("HasMine") == null) {
                pl.set("HasMine", false);

            }
            if (pl.get("KeysFound") == null) {
                pl.set("KeysFound", 0);

            }
            if (pl.get("BlocksBroken") == null) {
                pl.set("BlocksBroken", 0);

            }
            if (pl.get("CratesOpened") == null) {
                pl.set("CratesOpened", 0);

            }
            if (pl.get("TimePlayed") == null) {
                pl.set("TimePlayed", 0);

            }
            if (pl.get("TimesPrestiged") == null) {
                pl.set("TimesPrestiged", 0);

            }
            if (pl.get("Prestiges") == null) {
                pl.set("Prestiges", 0);

            }
            if (pl.get("PickLevel") == null) {
                pl.set("PickLevel", 1);

            }
            if (pl.get("PickXP") == null) {
                pl.set("PickXP", 0.0D);

            }
            if (pl.get("Tokens") == null) {
                pl.set("Tokens", 0.0D);

            }
            if (pl.get("Level") == null) {
                pl.set("Level", 0.0D);

            }
            if (pl.get("Trinkets") == null) {
                List<String> Trinkets = new ArrayList<>();
                pl.set("Trinkets", Trinkets);

            }
            if (pl.get("PickaxeSkill") == null) {
                pl.set("PickaxeSkill", "None");

            }
            if (pl.get("PickaxeSkillLevel") == null) {
                pl.set("PickaxeSkillLevel", 0);

            }
            if (pl.get("PickaxeSkillPoints") == null) {
                pl.set("PickaxeSkillPoints", 0);

            }
            if (pl.get("PickaxeSkillPointsSpent") == null) {
                pl.set("PickaxeSkillPointsSpent", 0);

            }
            if (pl.get("PickaxeSkillsUnlocked") == null) {
                List<String> skills = new ArrayList<>();
                pl.set("PickaxeSkillsUnlocked", skills);

            }
            if (pl.get("Gems") == null) {
                pl.set("Gems", 0);

            }
            if (pl.get("Gang") == null) {
                pl.set("Gang", "");

            }
            if (settings.getVote().get("Votes") == null) {
                settings.getVote().set("Votes", 0);
            }
            if (settings.getVote().get("Coupons") == null) {
                settings.getVote().set("Coupons", 0);
            }
            if (settings.getVote().get("VotePoints") == null) {
                settings.getVote().set("VotePoints", 0);
            }
            if (pl.get("NitroBoosting") == null) {
                pl.set("NitroBoosting", false);

            }
            if (pl.get("KitDemiGodKeys") == null) {
                pl.set("KitDemiGodKeys", 0);

            }
            if (pl.get("KitTitanKeys") == null) {
                pl.set("KitTitanKeys", 0);

            }
            if (pl.get("KitGodKeys") == null) {
                pl.set("KitGodKeys", 0);

            }
            if (pl.get("KitOlympianKeys") == null) {
                pl.set("KitOlympianKeys", 0);

            }
            if (pl.get("KitGenesisKeys") == null) {
                pl.set("KitGenesisKeys", 0);

            }
            if (pl.get("KitGod") == null) {
                pl.set("KitGod", 0);

            }
            if (pl.get("KitOlympian") == null) {
                pl.set("KitOlympian", 0);

            }
            if (pl.get("KitGenesis") == null) {
                pl.set("KitGenesis", 0);
            }
            if (pl.get("Ethereal") == null) {
                pl.set("Ethereal", false);
            }
            if (pl.get("RestartMomentum") == null) {
                pl.set("RestartMomentum", 0);
            }
            if (pl.get("RestartMomentumList") == null) {
                ArrayList<Long> momentums = new ArrayList<>();
                pl.set("RestartMomentumList", momentums);
            }
            if (pl.get("CustomBlock") == null) {
                pl.set("CustomBlock", null);
            }
            pl.save(pdata);

        } catch (Exception e) {
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println(e.getMessage());
        }
    }

    public static FileConfiguration getPlayerData(OfflinePlayer p) {
        File pdata = new File(Main.plugin.getDataFolder() + File.separator + "playerdata", p.getUniqueId().toString() + ".yml");
        FileConfiguration pl = YamlConfiguration.loadConfiguration(pdata);
        return pl;
    }
    public static FileConfiguration getPlayerData(UUID id) {
        File pdata = new File(Main.plugin.getDataFolder() + File.separator + "playerdata", id.toString() + ".yml");
        FileConfiguration pl = YamlConfiguration.loadConfiguration(pdata);
        return pl;
    }

    public static void savePlayerData(OfflinePlayer p) {
        File pdata = new File(Main.plugin.getDataFolder() + File.separator + "playerdata", p.getUniqueId().toString() + ".yml");
        FileConfiguration pl = YamlConfiguration.loadConfiguration(pdata);
        try {
            pl.save(pdata);
        } catch (Exception e) {
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println(e.getMessage());
        }
    }
    public static void savePlayerData(UUID id) {
        File pdata = new File(Main.plugin.getDataFolder() + File.separator + "playerdata", id.toString() + ".yml");
        FileConfiguration pl = YamlConfiguration.loadConfiguration(pdata);
        try {
            pl.save(pdata);
        } catch (Exception e) {
            System.out.println("ERROR SAVING PLAYERDATA: " + id);
            System.out.println("ERROR SAVING PLAYERDATA: " + id);
            System.out.println("ERROR SAVING PLAYERDATA: " + id);
            System.out.println(e.getMessage());
        }
    }

    public void savePickaxe(Player p) {
        ItemStack[] inv = p.getInventory().getContents();

        for (ItemStack i : inv) {
            if (i == null) continue;
            if (i.getType() == null) continue;
            if (i.getType().equals(Material.WOOD_PICKAXE) || i.getType().equals(Material.STONE_PICKAXE) || i.getType().equals(Material.IRON_PICKAXE) || i.getType().equals(Material.GOLD_PICKAXE)
                    || i.getType().equals(Material.DIAMOND_PICKAXE)) {
                ItemStack pickaxe = i;
                getPlayerData(p).set(p.getUniqueId().toString() + ".Pickaxe", pickaxe);
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) { //LAG SOURCE
        if (!e.getPlayer().hasPlayedBefore()) {
            Bukkit.broadcastMessage(m.c("&dWelcome &f&l" + e.getPlayer().getName() + "&d to &c&lGenesis &b&lPrison!"));
        }
        savePickaxe(e.getPlayer());
        new BukkitRunnable() {
            @Override
            public void run() {
                savePickaxe(e.getPlayer());
                savePlayerData(e.getPlayer());
                if (!e.getPlayer().isOnline()) {
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 20 * 120L);


    }
}