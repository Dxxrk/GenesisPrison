package me.dxrk.Events;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.dxrk.Enchants.SkillsEventsListener;
import me.dxrk.Gangs.CMDGang;
import me.dxrk.Gangs.Gangs;
import me.dxrk.Main.Functions;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineSystem;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class KeysHandler implements Listener {
    public SettingsManager settings = SettingsManager.getInstance();

    Random r = new Random();

    static KeysHandler instance = new KeysHandler();

    public static KeysHandler getInstance() {
        return instance;
    }

    private static Methods m = Methods.getInstance();


    public void addKey(Player p, String key, int amt) {
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId().toString() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId().toString() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId().toString() + ".name", p.getName());
            this.settings.saveLocksmith();
        }
        this.settings.getLocksmith().set(p.getUniqueId().toString() + "." + key, keys + amt);
        this.settings.saveLocksmith();
    }

    public void takeKey(Player p, String key, int amt) {
        int keys = this.settings.getLocksmith().getInt(p.getUniqueId().toString() + "." + key.toLowerCase());
        key = key.toLowerCase();
        if (this.settings.getLocksmith().get(p.getUniqueId().toString() + "." + p.getName()) == null) {
            this.settings.getLocksmith().set(p.getUniqueId().toString() + ".name", p.getName());
            this.settings.saveLocksmith();
        }
        this.settings.getLocksmith().set(p.getUniqueId().toString() + "." + key, keys - amt);
        this.settings.saveLocksmith();
    }


    public void KeyFinderMSG(Player p, String key, String color, String color2, int amt) {
        String s;

        s = ChatColor.translateAlternateColorCodes('&',
                "&f&lKey Finder &8| &b+" + amt + " " + color + key + color2 + " &bKey");


        if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Key-Finder-Messages") == true) {
            p.sendMessage(s);
        }
        addKey(p, key, amt);
        int keysfound = PlayerDataHandler.getInstance().getPlayerData(p).getInt("KeysFound");
        PlayerDataHandler.getInstance().getPlayerData(p).set("KeysFound", (keysfound + amt));
    }

    public void DustFinderMSG(Player p, String dust) {
        switch (dust) {
            case "Common":
                p.getInventory().addItem(TrinketHandler.getInstance().commonDust());
                if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Dust-Finder-Messages") == true) {
                    p.sendMessage(m.c("&f&lDust Finder &8| &b+1 Common Trinket Dust"));
                }
                break;
            case "Rare":
                p.getInventory().addItem(TrinketHandler.getInstance().rareDust());
                if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Dust-Finder-Messages") == true) {
                    p.sendMessage(m.c("&f&lDust Finder &8| &9+1 Rare Trinket Dust"));
                }
                break;
            case "Epic":
                p.getInventory().addItem(TrinketHandler.getInstance().epicDust());
                if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Dust-Finder-Messages") == true) {
                    p.sendMessage(m.c("&f&lDust Finder &8| &5+1 Epic Trinket Dust"));
                }
                break;
            case "Legendary":
                p.getInventory().addItem(TrinketHandler.getInstance().legDust());
                if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Dust-Finder-Messages") == true) {
                    p.sendMessage(m.c("&f&lDust Finder &8| &6+1 Legendary Trinket Dust"));
                }
                break;
            case "Heroic":
                p.getInventory().addItem(TrinketHandler.getInstance().herDust());
                if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Dust-Finder-Messages") == true) {
                    p.sendMessage(m.c("&f&lDust Finder &8| &4+1 Heroic Trinket Dust"));
                }
                break;
        }
    }


    public boolean findKey(double level) {
        return this.r.nextInt((int) level) == 1;
    }

    public void dustFinder(Player p, String s) {
        int level = m.getBlocks(s);
        double chance;
        double lucky = Functions.Karma(p);
        double luck = Functions.luckBoost(p);
        double skill = SkillsEventsListener.getSkillsBoostLuck(p);
        if (level == 0) return;
        if (level == 1) {
            chance = (int) (1375 * lucky * luck * skill);
        } else {
            chance = 1375 - (0.125 * level * lucky * luck * skill);
            if (chance < 200) {
                chance = 200;
            }
        }

        if (ChatColor.stripColor(s).contains("Dust Finder")) {
            if (findKey(chance))
                giveDust(p);
        }

    }


    public void giveDust(Player p) {
        double rand = this.r.nextInt(1000);

        if (rand <= 499) {
            DustFinderMSG(p, "Common");
        } else if (rand >= 500 && rand <= 799) {
            DustFinderMSG(p, "Rare");
        } else if (rand >= 800 && rand <= 949) {
            DustFinderMSG(p, "Epic");
        } else if (rand >= 950 && rand <= 991) {
            DustFinderMSG(p, "Legendary");
        } else if (rand >= 992) {
            DustFinderMSG(p, "Heroic");
        }

    }

    public void giveKey(int kfLvL, Player p) {
        double rand = this.r.nextInt(100);
        int chance = 0;
        List<String> lore = p.getItemInHand().getItemMeta().getLore();
        double event = SkillsEventsListener.getEventKeyFortune();
        chance += event;
        double keyboost = PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId() + "SkillKeyBoost");
        chance += keyboost;
        int kf = this.r.nextInt(100);
        int d = this.r.nextInt(100);
        if (chance > 0 && d <= chance) {
            if (kf <= 20) {
                KeyFinderMSG(p, "Alpha", "&7&l", "", 2);
            }
            if (kf > 20 && kf <= 40) {
                KeyFinderMSG(p, "Beta", "&c&l", "", 2);
            }
            if (kf > 40 && kf <= 60) {
                KeyFinderMSG(p, "Omega", "&4&l", "", 2);
            }
            if (kf > 60 && kf <= 80) {
                KeyFinderMSG(p, "Token", "&e&l", "", 2);
            }
            if (kf > 80 && kf <= 83) {
                KeyFinderMSG(p, "Seasonal", "&4&l&ki&f&l", "&4&l&ki&r", 2);
            }
            if (kf > 83) {
                KeyFinderMSG(p, "Community", "&5&l", "", 2);
            }
        } else {
            if (kf <= 20) {
                KeyFinderMSG(p, "Alpha", "&7&l", "", 1);
            }
            if (kf > 20 && kf <= 40) {
                KeyFinderMSG(p, "Beta", "&c&l", "", 1);
            }
            if (kf > 40 && kf <= 60) {
                KeyFinderMSG(p, "Omega", "&4&l", "", 1);
            }
            if (kf > 60 && kf <= 80) {
                KeyFinderMSG(p, "Token", "&e&l", "", 1);
            }
            if (kf > 80 && kf <= 83) {
                KeyFinderMSG(p, "Seasonal", "&4&l&ki&f&l", "&4&l&ki&r", 1);
            }
            if (kf > 83) {
                KeyFinderMSG(p, "Community", "&5&l", "", 1);
            }
        }
    }

    public void keyFinder(Player p, String s) {

        int level = m.getBlocks(s);
        int chance;
        double lucky = Functions.Karma(p);
        double luck = Functions.luckBoost(p);
        double skill = SkillsEventsListener.getSkillsBoostLuck(p);
        if (level == 0) return;
        if (level == 1) {
            chance = (int) (1000 * lucky * luck * skill);
        } else {
            chance = (int) ((1000 - (0.07 * level)) * lucky * luck * skill);
            if (chance < 200) {
                chance = 200;
            }
        }

        if (ChatColor.stripColor(s).contains("Key Finder")) {
            if (findKey(chance))
                giveKey(level, p);
        }
    }

    private HashMap<Player, Integer> tokensmap = new HashMap<>();


    public int getPrestiges(Player p) {
        int prestiges = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges");
        return prestiges;
    }

    public static int tokensPerBlock(Player p) {
        List<String> lore = p.getItemInHand().getItemMeta().getLore();
        double tf = 1;
        int x;
        for (x = 0; x < lore.size(); x++) {
            String s = lore.get(x);
            if (ChatColor.stripColor(s).contains("Token Finder")) {
                tf += m.getBlocks(ChatColor.stripColor(s)) * 0.0035;
            }
        }
        double multiply = 1;
        double skill = SkillsEventsListener.getSkillsBoostToken(p);
        double event = SkillsEventsListener.getEventToken();
        double tboost = Functions.tokenBoost(p);
        double miningboost = BoostsHandler.tokens.getOrDefault(p.getUniqueId(), 1.0);
        String gang = Gangs.getInstance().getGang(p);
        double unity = CMDGang.getInstance().getUnityLevel(gang);
        //double momentum =

        if (Functions.multiply.contains(p)) multiply = 2;
        double monster = 1;
        if (MonsterHandler.activeMonster.containsKey(p) && ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Typhon")) {
            monster = MonsterHandler.getMonsterBoost(p, 3);
        } else if (MonsterHandler.activeMonster.containsKey(p) && (ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Phoenix")
                || ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Medusa"))) {
            monster = MonsterHandler.getMonsterBoost(p, 4);
        }

        int tokens = 10;

        int tgive = (int) ((tokens * tf) * multiply * skill * event * tboost * unity * miningboost * monster);
        return tgive;
    }

    public void findTokens(Player p) {
        String gang = Gangs.getInstance().getGang(p);
        int tgive = tokensPerBlock(p);
        Tokens.getInstance().addTokens(p, tgive);
        if (CMDGang.harmony.contains(gang)) {
            double htokens = CMDGang.harmonyTokens.get(gang);
            CMDGang.harmonyTokens.put(Gangs.getInstance().getGang(p), htokens + tgive);
        }
    }


    @EventHandler
    @SuppressWarnings("deprecation")
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (p.getItemInHand() == null)
            return;
        if (e.isCancelled())
            return;
        ItemStack i = p.getItemInHand();
        if (!i.hasItemMeta())
            return;
        if (!i.getItemMeta().hasLore())
            return;
        if(!MineSystem.getInstance().getMineByPlayer(p).isLocationInMine(e.getBlock().getLocation())) {
            return;
        }

        findTokens(p);

        Random o = new Random();
        int ol = o.nextInt(500000);
        if (ol == 1) {
            Bukkit.broadcastMessage(Methods.getInstance().c("&f&l" + p.getName() + " &7Has found a &3&lRank &7Key!"));
            addKey(p, "Rank", 1);
        }

        List<String> lore = i.getItemMeta().getLore();
        int x;
        for (x = 0; x < lore.size(); x++) {
            String s = lore.get(x);
            if (ChatColor.stripColor(s).contains("Key Finder")) {
                //keyFinder(p, s);
            }

        }
        for (x = 0; x < lore.size(); x++) {
            String s = lore.get(x);
            if (ChatColor.stripColor(s).contains("Dust Finder")) {
                dustFinder(p, s);
            }

        }

    }
}
