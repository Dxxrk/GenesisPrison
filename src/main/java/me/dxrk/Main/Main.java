package me.dxrk.Main;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerOptions;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.comphenix.protocol.wrappers.WrappedServerPing;
import com.earth2me.essentials.Essentials;
import me.dxrk.Commands.*;
import me.dxrk.Discord.JDAEvents;
import me.dxrk.Discord.jdaHandler;
import me.dxrk.Enchants.*;
import me.dxrk.Events.*;
import me.dxrk.Gangs.CMDGang;
import me.dxrk.Mines.*;
import me.dxrk.Tokens.TokensCMD;
import me.dxrk.Tokens.TokensListener;
import me.dxrk.Vote.BuycraftUtilOld;
import me.dxrk.Vote.CMDVote;
import me.dxrk.Vote.CMDVoteShop;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.io.File;
import java.text.NumberFormat;
import java.util.*;

public class Main extends JavaPlugin implements Listener, CommandExecutor {

    private static Main INSTANCE;
    public static Permission perms = null;
    public static Economy econ = null;
    public static Chat chat = null;
    public ArrayList<String> Sb = new ArrayList<>();
    public static Plugin plugin;
    public static Scoreboard sb;
    SettingsManager settings = SettingsManager.getInstance();
    public static Essentials ess;

    public static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    //@SuppressWarnings("deprecation")
    private void handlePing(WrappedServerPing ping) {
        ping.setPlayers(Arrays.asList(
                new WrappedGameProfile(UUID.randomUUID(), ChatColor.YELLOW + "store.mcgenesis.net"),
                new WrappedGameProfile(UUID.randomUUID(), ""),
                new WrappedGameProfile(UUID.randomUUID(), ChatColor.LIGHT_PURPLE + "Remember to Vote!")
        ));
        ping.setPlayersOnline(ScoreboardHandler.getPlayersOnline());
    }

    private List<String> worlds() {
        List<String> worlds = new ArrayList<>();
        worlds.add("Prison");
        //worlds.add("world_the_end");
        worlds.add("Build");
        worlds.add("Dxrk");
        return worlds;
    }

    private void loadWorlds() {
        /*for (String s : worlds()) {
            if(Bukkit.getWorld(s) == null) {
                new WorldCreator(s).createWorld();
            }
        }*/
    }

    public void onEnable() {

        plugin = this;
        INSTANCE = this;
        Mines.getInstance().enable();
        PlayerDataHandler.getInstance().loadPlayerData();
        System.out.println(MineSystem.getInstance().getActiveMines());
        MineWorldCreator.getInstance().createMineWorld("mines");

        ProtocolLibrary.getProtocolManager().addPacketListener(
// I mark my listener as async, as I don't use the Bukkit API. Please note that
// your listener may be executed on the main thread regardless.
                new PacketAdapter(this, ListenerPriority.NORMAL,
                        Arrays.asList(PacketType.Status.Server.OUT_SERVER_INFO), ListenerOptions.ASYNC) {

                    @Override
                    public void onPacketSending(PacketEvent event) {
                        handlePing(event.getPacket().getServerPings().read(0));
                    }
                });

        ess = (Essentials) Bukkit.getPluginManager().getPlugin("Essentials");
        setupEconomy();
        this.settings.setup(this);
        setupPermissions();
        setupChat();
        if (this.settings.getOptions().get("numberofmines") == null) {
            this.settings.getOptions().set("numberofmines", 0);
            this.settings.saveOptions();
        }


        if (this.settings.getData().getString("Broadcast") == null) {
            this.settings.getData().set("Broadcast", "Tesssttt");
            this.settings.saveData();
        }
        new jdaHandler(this);

        if (settings.getOptions().getString("DiscordToken") == null) {
            settings.getOptions().set("DiscordToken", "");
        }

        AuctionHouseHandler.getInstance().loadAH();
        TrinketHandler t = new TrinketHandler();
        t.customShardCommon();
        t.customShardRare();
        t.customShardEpic();
        t.customShardLegendary();
        t.customShardHeroic();
        t.commonSharding();
        t.rareSharding();
        t.epicSharding();
        t.legendarySharding();
        t.commonShardingOpened();
        t.rareShardingOpened();
        t.epicShardingOpened();
        t.legendaryShardingOpened();
        t.heroicSharding();


        getCommand("blocks").setExecutor(new BlocksHandler());
        /*getCommand("ChatColor").setExecutor(new ChatHandler());
        //getCommand("Chat").setExecutor(new ChatHandler());
        getCommand("MuteChat").setExecutor(new ChatHandler());*/
        getCommand("rename").setExecutor(new CMDRename());
        getCommand("renamepaper").setExecutor(new CMDRename());
        getCommand("relore").setExecutor(new CMDRename());
        getCommand("Vote").setExecutor(new CMDVote());
        getCommand("voteparty").setExecutor(new CMDVote());
        getCommand("givevotepoints").setExecutor(new CMDVote());
        getCommand("coupon").setExecutor(new CMDVoteShop());
        getCommand("coupons").setExecutor(new CMDVoteShop());
        getCommand("addvotepoint").setExecutor(new CMDVoteShop());
        getCommand("Tags").setExecutor(new CMDTags());
        getCommand("rewards").setExecutor(new CMDVote());
        getCommand("Say").setExecutor(new CMDSay());
        //getCommand("CoinFlip").setExecutor(new CMDCoinflip());
        getCommand("Tokens").setExecutor(new TokensCMD());
        getCommand("Token").setExecutor(new TokensCMD());
        getCommand("nick").setExecutor(new NicknameHandler());
        getCommand("nickname").setExecutor(new NicknameHandler());
        getCommand("nicknames").setExecutor(new CMDNickname());
        getCommand("mine").setExecutor(new CMDMine());
        getCommand("vanish").setExecutor(new CMDVanish());
        getCommand("v").setExecutor(new CMDVanish());
        getCommand("ev").setExecutor(new CMDVanish());
        getCommand("evanish").setExecutor(new CMDVanish());
        getCommand("pick").setExecutor(new PickaxeLevel());
        getCommand("pickaxe").setExecutor(new PickaxeLevel());
        getCommand("resetallmines").setExecutor(new PickaxeLevel());
        //  getCommand("laser").setExecutor(new PickaxeLevel());
        getCommand("motdchange").setExecutor(this);
        getCommand("workmode").setExecutor(this);
        getCommand("prestige").setExecutor(new PrestigeHandler());
        getCommand("giveenchant").setExecutor(new DonorItems());
        getCommand("boost").setExecutor(new BoostsHandler());
        getCommand("giveboost").setExecutor(new BoostsHandler());
        getCommand("blockstop").setExecutor(new Leaderboards());
        getCommand("addprestige").setExecutor(new PrestigeHandler());
        getCommand("giveshard").setExecutor(new TrinketHandler());
        getCommand("givetrinket").setExecutor(new TrinketHandler());
        getCommand("trinket").setExecutor(new TrinketHandler());
        getCommand("trinkets").setExecutor(new TrinketHandler());
        getCommand("relore").setExecutor(new CMDItemEdits());
        getCommand("addlore").setExecutor(new CMDItemEdits());
        getCommand("dellore").setExecutor(new CMDItemEdits());
        getCommand("trash").setExecutor(new CMDTrash());
        getCommand("ac").setExecutor(new CMDAc());
        getCommand("cratekey").setExecutor(new CrateHandler());
        getCommand("crateinfo").setExecutor(new CrateHandler());
        getCommand("openall").setExecutor(new CrateHandler());
        getCommand("rankup").setExecutor(new CMDRankup());
        getCommand("maxrankup").setExecutor(new CMDRankup());
        getCommand("rankupmax").setExecutor(new CMDRankup());
        getCommand("locksmith").setExecutor(new LocksmithHandler());
        getCommand("ls").setExecutor(new LocksmithHandler());
        getCommand("keys").setExecutor(new LocksmithHandler());
        getCommand("clearchat").setExecutor(new CMDClearchat());
        getCommand("dp").setExecutor(new CMDDp());
        getCommand("multi").setExecutor(new SellHandler());
        getCommand("withdraw").setExecutor(new SellHandler());
        getCommand("setrank").setExecutor(new CMDRankup());
        getCommand("forcereset").setExecutor(new SellHandler());
        getCommand("bpreset").setExecutor(new SellHandler());
        getCommand("resetmine").setExecutor(new SellHandler());
        getCommand("checkminereset").setExecutor(new MineHandler());
        getCommand("rm").setExecutor(new SellHandler());
        getCommand("autorankup").setExecutor(new RankupHandler());
        getCommand("aru").setExecutor(new RankupHandler());
        getCommand("giveplotitem").setExecutor(new MineHandler());
        getCommand("buymsg").setExecutor(new BuycraftUtilOld());
        getCommand("createcoupon").setExecutor(new BuycraftUtilOld());
        getCommand("options").setExecutor(new CMDOptions());
        getCommand("daily").setExecutor(new CMDDaily());
        getCommand("discord").setExecutor(new JDAEvents());
        getCommand("ranktop").setExecutor(new Leaderboards());
        getCommand("rankstop").setExecutor(new Leaderboards());
        getCommand("givemoney").setExecutor(new RankupHandler());
        getCommand("randomtag").setExecutor(new TagsHandler());
        getCommand("givecrate").setExecutor(new MysteryBoxHandler());
        getCommand("givexp").setExecutor(new MysteryBoxHandler());
        getCommand("giverank").setExecutor(new CMDRanks());
        getCommand("removemine").setExecutor(new MineHandler());
        getCommand("updatemine").setExecutor(new MineHandler());
        getCommand("redeem").setExecutor(new BuycraftUtilOld());
        getCommand("stats").setExecutor(new CMDStats());
        getCommand("ah").setExecutor(new AuctionHouseHandler());
        getCommand("auctionhouse").setExecutor(new AuctionHouseHandler());
        getCommand("gems").setExecutor(new MinePouchHandler());
        getCommand("gem").setExecutor(new MinePouchHandler());
        getCommand("help").setExecutor(new CMDHelp());
        getCommand("leaderboard").setExecutor(new Leaderboards());
        getCommand("leaderboards").setExecutor(new Leaderboards());
        getCommand("lb").setExecutor(new Leaderboards());
        getCommand("lbs").setExecutor(new Leaderboards());
        getCommand("trade").setExecutor(new CMDTrade());
        getCommand("gang").setExecutor(new CMDGang());
        getCommand("g").setExecutor(new CMDGang());
        getCommand("genesis").setExecutor(new CMDOptions());
        getCommand("giveegg").setExecutor(new MonsterHandler());
        getCommand("spawncrate").setExecutor(new MonsterHandler());
        getCommand("testenchant").setExecutor(new EnchantMethods());
        getCommand("wave").setExecutor(new EnchantMethods());
        getCommand("setskillpoints").setExecutor(new PickaxeLevel());
        getCommand("resetpickaxe").setExecutor(new PickaxeLevel());
        getCommand("sellfish").setExecutor(new FishingHandler());
        getCommand("rod").setExecutor(new FishingHandler());
        getCommand("crystals").setExecutor(new FishingHandler());
        getCommand("buildmode").setExecutor(new BuildModeHandler());
        getCommand("giveflare").setExecutor(new EventFlareHandler());
        getCommand("listversions").setExecutor(this);

        registerEvents(this, new Listener[]{new MonsterHandler()});
        registerEvents(this, new Listener[]{new CMDVanish()});
        registerEvents(this, new Listener[]{new MomentumHandler()});
        registerEvents(this, new Listener[]{new SkillsEventsListener()});
        registerEvents(this, new Listener[]{new ReminderHandler()});
        registerEvents(this, new Listener[]{new CMDGang()});
        registerEvents(this, new Listener[]{new CMDTrade()});
        registerEvents(this, new Listener[]{new Leaderboards()});
        registerEvents(this, new Listener[]{new CMDHelp()});
        registerEvents(this, new Listener[]{new MinePouchHandler()});
        registerEvents(this, new Listener[]{new AuctionHouseHandler()});
        registerEvents(this, new Listener[]{new CMDStats()});
        registerEvents(this, new Listener[]{new CMDRanks()});
        registerEvents(this, new Listener[]{new CMDVoteShop()});
        registerEvents(this, new Listener[]{new TokensCMD()});
        registerEvents(this, new Listener[]{new KitAndWarp()});
        registerEvents(this, new Listener[]{new CMDTags()});
        registerEvents(this, new Listener[]{new ProtectOP()});
        registerEvents(this, new Listener[]{new BlocksHandler()});
        registerEvents(this, new Listener[]{new NewChatHandler()});
        registerEvents(this, new Listener[]{new CMDVote()});
        registerEvents(this, new Listener[]{new DeathLogger()});
        registerEvents(this, new Listener[]{new TokensListener()});
        registerEvents(this, new Listener[]{new CMDNickname()});
        registerEvents(this, new Listener[]{new PickaxeLevel()});
        registerEvents(this, new Listener[]{new Enchants()});
        registerEvents(this, new Listener[]{new PickXPHandler()});
        registerEvents(this, new Listener[]{new BoostsHandler()});
        registerEvents(this, new Listener[]{new MineHandler()});
        registerEvents(this, new Listener[]{new Leaderboards()});
        registerEvents(this, new Listener[]{new CMDMine()});
        registerEvents(this, new Listener[]{new DonorItems()});
        registerEvents(this, new Listener[]{new CrateHandler()});
        registerEvents(this, new Listener[]{new SellHandler()});
        registerEvents(this, new Listener[]{new PlayerDataHandler()});
        registerEvents(this, new Listener[]{new KeysHandler()});
        registerEvents(this, new Listener[]{new LocksmithHandler()});
        registerEvents(this, new Listener[]{new RankupHandler()});
        registerEvents(this, new Listener[]{new ScoreboardHandler()});
        registerEvents(this, new Listener[]{new TrinketHandler()});
        registerEvents(this, new Listener[]{new CMDOptions()});
        registerEvents(this, new Listener[]{new CMDDaily()});
        registerEvents(this, new Listener[]{new JDAEvents()});
        registerEvents(this, new Listener[]{new MysteryBoxHandler()});
        registerEvents(this, new Listener[]{new PrestigeHandler()});
        registerEvents(this, new Listener[]{new PickaxeSkillTree()});
        registerEvents(this, new Listener[]{new FishingHandler()});
        registerEvents(this, new Listener[]{new BuildModeHandler()});
        registerEvents(this, new Listener[]{new EventFlareHandler()});
        registerEvents(this, new Listener[]{this});
        // For when sale is active, use this ||
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "motdchange sale 20");
        // For when maintenance active, use this ||
        //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "workmode enable");
        MomentumHandler.runMomentum();


        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    //SPEED
                    if (settings.getOptions().getBoolean(p.getUniqueId() + ".Speed-Effect")) {
                        if (!p.hasPotionEffect(PotionEffectType.SPEED)) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 999999, 5));
                        }
                    } else {
                        if (p.hasPotionEffect(PotionEffectType.SPEED)) {
                            p.removePotionEffect(PotionEffectType.SPEED);
                        }
                    }
                    //HASTE
                    if (settings.getOptions().getBoolean(p.getUniqueId() + ".Haste-Effect")) {
                        if (!p.hasPotionEffect(PotionEffectType.HASTE)) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.HASTE, 999999, 10));
                        }
                    } else {
                        if (p.hasPotionEffect(PotionEffectType.HASTE)) {
                            p.removePotionEffect(PotionEffectType.HASTE);
                        }
                    }
                    //NIGHT VISION
                    if (settings.getOptions().getBoolean(p.getUniqueId() + ".Night-Vision-Effect")) {
                        if (!p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                            p.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 999999, 2));
                        }
                    } else {
                        if (p.hasPotionEffect(PotionEffectType.NIGHT_VISION)) {
                            p.removePotionEffect(PotionEffectType.NIGHT_VISION);
                        }
                    }
                }
            }
        }.runTaskTimer(this, 0, 5);


        new BukkitRunnable() {
            @Override
            public void run() {
                File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
                File[] var = mineFiles;
                assert mineFiles != null;
                int amountOfMines = mineFiles.length;
                for (int i = 0; i < amountOfMines; ++i) {
                    File mineFile = var[i];
                    String name = mineFile.getName().split("\\.")[0];
                    UUID id = UUID.fromString(name);

                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitDemiGodKeys") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitDemiGodKeys");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitDemiGodKeys", replace);
                    }
                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitTitanKeys") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitTitanKeys");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitTitanKeys", replace);
                    }
                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGodKeys") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGodKeys");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitGodKeys", replace);
                    }
                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitOlympianKeys") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitOlympianKeys");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitOlympianKeys", replace);
                    }
                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGenesisKeys") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGenesisKeys");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitGenesisKeys", replace);
                    }
                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGod") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGod");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitGod", replace);
                    }
                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitOlympian") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitOlympian");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitOlympian", replace);
                    }
                    if (!(PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGenesis") == 0)) {
                        int countdown = PlayerDataHandler.getInstance().getPlayerData(id).getInt("KitGenesis");
                        int replace = countdown - 1;
                        PlayerDataHandler.getInstance().getPlayerData(id).set("KitGenesis", replace);
                    }
                }
            }
        }.runTaskTimer(this, 0, 20L);

        new BukkitRunnable() {

            @Override
            public void run() {
                JDAEvents.getInstance().serverLink();
            }
        }.runTaskTimer(this, 0L, 20 * 60L);


        new BukkitRunnable() {

            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    ScoreboardHandler.updateSB(p);
                    if (!p.isOnline()) {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(this, 0L, 20 * 2L);

        new BukkitRunnable() {
            @Override
            public void run() { // LAG SOURCE
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (RankupHandler.aru.contains(p)) {
                        RankupHandler.getInstance().MaxRankup(p);
                    }
                    if (!p.isOnline()) {
                        cancel();
                    }
                }
            }
        }.runTaskTimer(this, 0L, 30L);


        new BukkitRunnable() {
            @Override
            public void run() {
                Leaderboards.addTimePlayed();
            }
        }.runTaskTimer(this, 0L, 20L);


        Iterator<Recipe> it = getServer().recipeIterator();
        while (it.hasNext()) {
            Recipe re = it.next();
            if (re != null && re.getResult().getType() == Material.GOLDEN_APPLE)
                it.remove();
        }

        if (this.settings.getBoost().getDouble("ActiveSell.Amp") != 0.0D) {
            int timeleft = this.settings.getBoost().getInt("TimeLeftSell");
            double amp = this.settings.getBoost().getDouble("ActiveSell.Amp");
            String act = this.settings.getBoost().getString("ActivatorSell");
            this.settings.getBoost().set("TimeLeftSell", 0);
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost sell " + act + " " + amp + " " + timeleft);
                }
            }.runTaskLater(this, 20 * 5L);

        }
        if (this.settings.getBoost().getInt("ActiveXP.Amp") != 0) {
            int timeleft = this.settings.getBoost().getInt("TimeLeftXP");
            int amp = this.settings.getBoost().getInt("ActiveXP.Amp");
            String act = this.settings.getBoost().getString("ActivatorXP");
            this.settings.getBoost().set("TimeLeftXP", 0);
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost XP " + act + " " + amp + " " + timeleft);
                }


            }.runTaskLater(this, 20 * 5L);

        }
        File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
        File[] var = mineFiles;
        assert mineFiles != null;
        int amountOfMines = mineFiles.length;
        for (int i = 0; i < amountOfMines; ++i) {
            File mineFile = var[i];
            String name = mineFile.getName().split("\\.")[0];
            UUID id = UUID.fromString(name);
            int seconds = PlayerDataHandler.getInstance().getPlayerData(id).getInt("RestartMomentum");
            if (seconds > 0) {
                MomentumHandler.seconds.put(id, seconds);
            }
            ArrayList<Long> momentums = (ArrayList<Long>) PlayerDataHandler.getInstance().getPlayerData(id).getLongList("RestartMomentumList");
            MomentumHandler.momentum.put(id, momentums);
        }


        Methods.schedule(this, new Runnable() {
            public void run() {

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 1 Hour!");

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 30 Minutes!");
                    }
                }.runTaskLater(Main.plugin, 20 * 60 * 30);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 10 Minutes!");
                    }
                }.runTaskLater(Main.plugin, 20 * 60 * 50);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 1 Minute!");
                    }
                }.runTaskLater(Main.plugin, 20 * 60 * 59);


            }
        }, 3);


        Methods.schedule(this, new Runnable() {
            public void run() {

                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
            }
        }, 4);


        new BukkitRunnable() {
            @Override
            public void run() {
                settings.saveGangs();
            }
        }.runTaskTimer(this, 0, 20 * 150L);

    }


    public static Main getInstance() {
        return INSTANCE;
    }


    private String motd = c("&3&lSeason 4!   &c&lGenesis &b&lPrison!   &e&l[1.8.x-1.19.x]\n                        &c&l>> &a&lJoin Now! &c&l<<");
    private String savemotd = "";

    @Override
    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {

        if ("motdchange".equalsIgnoreCase(label)) {
            if (!cs.hasPermission("rank.owner")) return false;
            if (args.length == 2) {
                if ("sale".equalsIgnoreCase(args[0])) {

                    motd = c("&3&lSeason 4!   &c&lGenesis &b&lPrison!   &e&l[1.8.x-1.19.x]\n                    &c&l>> &a&l" + args[1] + "% Sale Now! &c&l<<");
                }
            }
        }
        if ("workmode".equalsIgnoreCase(label)) {
            if (!cs.isOp()) return false;
            if (args.length == 1) {
                if ("enable".equalsIgnoreCase(args[0])) {                             //&4&lMaintenance Mode
                    savemotd = motd;
                    motd = c("                        &c&lGenesis &b&lPrison!\n                   &4&lMaintenance Mode");
                }
                if ("disable".equalsIgnoreCase(args[0])) {
                    motd = savemotd;
                }
            }
        }
        if(label.equalsIgnoreCase("listversions")) {
            cs.sendMessage("Bukkit.getServer().getClass().getPackage().getName() = "+Bukkit.getServer().getClass().getPackage().getName());
            cs.sendMessage("Bukkit.getVersion() = "+ Bukkit.getVersion());
            cs.sendMessage("Bukkit.getBukkitVersion() = "+Bukkit.getServer().getBukkitVersion());
            cs.sendMessage("Bukkit.getMinecraftVersion() = "+Bukkit.getServer().getMinecraftVersion());
        }


        return false;
    }

    @EventHandler
    public void motd(ServerListPingEvent e) {
        e.setMotd(motd);
        e.setMaxPlayers(0);
    }


    public static String formatAmt(double amt) {
        if (amt <= 0.0D)
            return String.valueOf(0);
        if (amt >= 1.0E24D)
            return String.format("%.1fS", amt / 1.0E24D);
        if (amt >= 1.0E21D)
            return String.format("%.1fs", amt / 1.0E21D);
        if (amt >= 1.0E18D)
            return String.format("%.1fQ", amt / 1.0E18D);
        if (amt >= 1.0E15D)
            return String.format("%.1fq", amt / 1.0E15D);
        if (amt >= 1.0E12D)
            return String.format("%.1fT", amt / 1.0E12D);
        if (amt >= 1.0E9D)
            return String.format("%.1fB", amt / 1.0E9D);
        if (amt >= 1000000.0D)
            return String.format("%.1fM", amt / 1000000.0D);
        return NumberFormat.getNumberInstance(Locale.US).format(amt);
    }


    public void onDisable() {

        settings.saveVote();
        settings.saveGangs();
        Iterator mineList = MineSystem.getInstance().getActiveMines().values().iterator();
        while (mineList.hasNext()) {
            Mine mine = (Mine) mineList.next();
            mine.save();
        }
        Iterator pdataList = PlayerDataHandler.getInstance().getPdataList().values().iterator();
        while (mineList.hasNext()) {
            PlayerData pdata = (PlayerData) pdataList.next();
            pdata.save();
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            MonsterHandler.forceDeactivate(p);
        }
        AuctionHouseHandler.getInstance().saveAH();


        this.settings.getVote().set("VoteShopLog", CMDVoteShop.votelog);
        this.settings.saveVote();
        for (UUID id : MomentumHandler.seconds.keySet()) {
            long seconds = MomentumHandler.seconds.get(id);
            PlayerDataHandler.getInstance().getPlayerData(id).set("RestartMomentum", seconds);
            ArrayList<Long> momentums = MomentumHandler.momentum.get(id);
            PlayerDataHandler.getInstance().getPlayerData(id).set("RestartMomentumList", momentums);
            PlayerDataHandler.getInstance().savePlayerData(id);
        }

        this.settings.saveData();
        BlocksHandler.getInstance().onEnd();


    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;

    }


    public void registerEvents(Plugin plugin, Listener[] listeners) {
        byte b;
        int i;
        Listener[] arrayOfListener;
        for (i = (arrayOfListener = listeners).length, b = 0; b < i; ) {
            Listener listener = arrayOfListener[b];
            Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
            b++;
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
            return false;
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
            return false;
        econ = rsp.getProvider();
        return econ != null;
    }


}
