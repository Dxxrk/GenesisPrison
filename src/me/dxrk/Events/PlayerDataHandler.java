package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PlayerDataHandler implements Listener {
    private SettingsManager settings = SettingsManager.getInstance();
    private Methods m = Methods.getInstance();

    public static PlayerDataHandler instance = new PlayerDataHandler();

    public static PlayerDataHandler getInstance() {
        return instance;
    }


    public static HashMap<UUID, PlayerData> PlayerDataList = new HashMap<>();

    public FileConfiguration getPlayerData(Player p) {
        return PlayerDataList.get(p.getUniqueId()).get();
    }

    public FileConfiguration getPlayerData(UUID id) {
        return PlayerDataList.get(id).get();
    }

    public void savePlayerData(Player p) {
        PlayerData pdata = PlayerDataList.get(p.getUniqueId());
        pdata.save();
    }

    public void savePlayerData(UUID id) {
        PlayerData pdata = PlayerDataList.get(id);
        pdata.save();
    }

    public HashMap<UUID, PlayerData> getPdataList() {
        return PlayerDataList;
    }

    public void loadPlayerData() {
        createFolder();
        File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
        File[] var3 = mineFiles;
        assert mineFiles != null;
        int amountOfMines = mineFiles.length;
        for (int i = 0; i < amountOfMines; ++i) {
            File mineFile = var3[i];
            UUID id = UUID.fromString(mineFile.getName().split("\\.")[0]);
            PlayerData pdata = new PlayerData(id);
            PlayerDataList.put(id, pdata);
        }

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
            if (pl.get("Multi") == null) {
                pl.set("Multi", 0.0D);

            }
            if (pl.get("Level") == null) {
                pl.set("Level", 0.0D);

            }
            if (pl.get("Trinkets") == null) {
                List<String> trinkets = new ArrayList<>();
                pl.set("Trinkets", trinkets);

            }
            if (pl.get("AHListings") == null) {
                List<String> ah = new ArrayList<>();
                pl.set("AHListings", ah);

            }
            if (pl.get("PickaxeSkill") == null) {
                pl.set("PickaxeSkill", "None");

            }
            if (pl.get("SkillLuckBoost") == null) {
                pl.set("SkillLuckBoost", 0);

            }
            if (pl.get("SkillTokenBoost") == null) {
                pl.set("SkillTokenBoost", 0);

            }
            if (pl.get("SkillFortuneBoost") == null) {
                pl.set("SkillFortuneBoost", 0);

            }
            if (pl.get("SkillKeyBoost") == null) {
                pl.set("SkillKeyBoost", 0);

            }
            if (pl.get("FortuneMaxLevelRaise") == null) {
                pl.set("FortuneMaxLevelRaise", 0);

            }
            if (pl.get("FortuneMaxLevelRaise") == null) {
                pl.set("FortuneMaxLevelRaise", 0);

            }
            if (pl.get("SkillFortuneBoost") == null) {
                pl.set("SkillFortuneBoost", 0);

            }
            if (pl.get("SkillFortuneBoost") == null) {
                pl.set("SkillFortuneBoost", 0);

            }
            if (pl.get("JunkpileMaxLevelRaise") == null) {
                pl.set("JunkpileMaxLevelRaise", 0);

            }
            if (pl.get("SkillJunkpileMultiBoost") == null) {
                pl.set("SkillJunkpileMultiBoost", 0);

            }
            if (pl.get("XPFMaxLevelRaise") == null) {
                pl.set("XPFMaxLevelRaise", 0);

            }
            if (pl.get("SkillJunkpileXPBoost") == null) {
                pl.set("SkillJunkpileXPBoost", 0);

            }
            if (pl.get("TFMaxLevelRaise") == null) {
                pl.set("TFMaxLevelRaise", 0);

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
            if (pl.get("Rod") == null) {
                pl.set("Rod", FishingHandler.getInstance().defaultRod());
            }
            if (pl.get("BuildMode") == null) {
                pl.set("BuildMode", false);
            }
            if (pl.get("InvItems") == null) {
                ItemStack[] items = new ItemStack[0];
                pl.set("InvItems", items);
            }
            pl.save(pdata);
            PlayerData playerdata = new PlayerData(p.getUniqueId());
            PlayerDataList.put(p.getUniqueId(), playerdata);

        } catch (Exception e) {
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
            System.out.println("ERROR SAVING PLAYERDATA: " + p.getUniqueId().toString());
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
                getPlayerData(p).set("Pickaxe", pickaxe);
            }
        }
    }


    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPlayedBefore()) {
            Bukkit.broadcastMessage(m.c("&dWelcome &f&l" + p.getName() + "&d to &c&lGenesis &b&lPrison!"));
        }
        createPlayerData(p);

        new BukkitRunnable() {
            @Override
            public void run() {
                savePickaxe(p);
                savePlayerData(p);
                if (!p.isOnline()) {
                    cancel();
                }
            }
        }.runTaskTimer(Main.plugin, 0L, 20 * 120L);


    }


}
