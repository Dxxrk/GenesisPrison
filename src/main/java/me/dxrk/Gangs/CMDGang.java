package me.dxrk.Gangs;

import me.dxrk.Enchants.PickaxeLevel;
import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Events.ScoreboardHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineSystem;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class CMDGang implements Listener, CommandExecutor {

    static CMDGang instance = new CMDGang();

    public static CMDGang getInstance() {
        return instance;
    }

    Methods m = Methods.getInstance();
    SettingsManager settings = SettingsManager.getInstance();
    Gangs g = Gangs.getInstance();

    public static List<Player> gangchat = new ArrayList<>();
    public static HashMap<String, List<Player>> invited = new HashMap<>();
    public static List<String> harmony = new ArrayList<>();
    public static HashMap<String, Double> harmonyTokens = new HashMap<>();
    public static HashMap<String, Double> harmonyMoney = new HashMap<>();

    public void sendGang(Player p, OfflinePlayer pp) {
        String gang = g.getGang(pp);
        p.sendMessage(m.c("&3Gang: &b" + gang));
        OfflinePlayer owner = Bukkit.getOfflinePlayer((UUID.fromString(settings.getGangs().getString(gang + ".Owner"))));
        if (owner.isOnline())
            p.sendMessage(m.c("&3Owner: &a" + owner.getName()));
        else p.sendMessage(m.c("&3Owner: &c" + owner.getName()));
        p.sendMessage(m.c("&3Level: &b" + settings.getGangs().getInt(gang + ".Level")));
        p.sendMessage(m.c("&3BlocksBroken: &b" + settings.getGangs().getInt(gang + ".BlocksBroken")));
        List<String> members = settings.getGangs().getStringList(gang + ".Members");
        StringBuilder sb = new StringBuilder();
        for (String s : members) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
            if (player.isOnline()) {
                sb.append(m.c("&a" + player.getName())).append(m.c("&7,"));
            } else {
                sb.append(m.c("&c" + player.getName())).append(m.c("&7,"));
            }
        }
        p.sendMessage(m.c("&3Members: " + sb));
    }

    public void sendGang(Player p, String gang) {
        p.sendMessage(m.c("&3Gang: &b" + gang));
        OfflinePlayer owner = Bukkit.getOfflinePlayer((UUID.fromString(settings.getGangs().getString(gang + ".Owner"))));
        if (owner.isOnline())
            p.sendMessage(m.c("&3Owner: &a" + owner.getName()));
        else p.sendMessage(m.c("&3Owner: &c" + owner.getName()));
        p.sendMessage(m.c("&3Level: &b" + settings.getGangs().getInt(gang + ".Level")));
        p.sendMessage(m.c("&3BlocksBroken: &b" + settings.getGangs().getInt(gang + ".BlocksBroken")));
        List<String> members = settings.getGangs().getStringList(gang + ".Members");
        StringBuilder sb = new StringBuilder();
        for (String s : members) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
            if (player.isOnline()) {
                sb.append(m.c("&a" + player.getName())).append(m.c("&7,"));
            } else {
                sb.append(m.c("&c" + player.getName())).append(m.c("&7,"));
            }
        }
        p.sendMessage(m.c("&3Members: " + sb));
    }

    public void sendGangHelp(Player p) {
        p.sendMessage(m.c("&3&lGang Commands:"));
        p.sendMessage(m.c("&3/Gang Help: &bOpen this message."));
        p.sendMessage(m.c("&3/Gang: &bView your gang's info."));
        p.sendMessage(m.c("&3/Gang Create <Name>: &bCreate a gang."));
        p.sendMessage(m.c("&3/Gang Delete: &bDelete your gang."));
        p.sendMessage(m.c("&3/Gang Chat: &bTransfer to gang chat."));
        p.sendMessage(m.c("&3/Gang Info <Player/Gang>: &bView the info of another gang."));
        p.sendMessage(m.c("&3/Gang Upgrade(s): &bOpen the gang upgrades to see your progress."));
        p.sendMessage(m.c("&3/Gang Invite <Player>: &bInvite another player to your gang."));
        p.sendMessage(m.c("&3/Gang SetOwner: &bChange your gangs ownership. Must be the owner of your gang."));
        p.sendMessage(m.c("&3/Gang Kick <Player>: &bView your gang's info."));
        p.sendMessage(m.c("&3/Gang Leave: &bLeave your current gang."));
    }

    public ItemStack gangItem(String gang, String name, String lore1, double blocksneeded) {
        ItemStack item = new ItemStack(Material.BEACON);
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(name);
        List<String> lore = new ArrayList<>();
        lore.add(lore1);
        lore.add(m.c("&b" + Main.formatAmt(g.getGangBlocks(gang)) + "&7/&b" + Main.formatAmt(blocksneeded)));
        im.setLore(lore);
        item.setItemMeta(im);
        return item;
    }

    public void openGangShop(Player p) {
        String gang = g.getGang(p);
        Inventory ganginv = Bukkit.createInventory(null, 18, m.c("&3&l" + gang + "'s &b&lUpgrades"));
        ganginv.setItem(0, PickaxeLevel.getInstance().Spacer());
        ganginv.setItem(1, PickaxeLevel.getInstance().Spacer());
        ganginv.setItem(2, gangItem(gang, m.c("&aHarmony Level 1"), m.c("&7&oWhen activated all currency gained by members is split up between them."), calculateBlocksNeeded(0)));
        ganginv.setItem(3, gangItem(gang, m.c("&aHarmony Level 2"), m.c("&7&oIncreases the chances of Harmony."), calculateBlocksNeeded(1)));
        ganginv.setItem(4, gangItem(gang, m.c("&aIncreased Gems"), m.c("&7&oIncreases the amount of gems you get from mine pouches."), calculateBlocksNeeded(2)));
        ganginv.setItem(5, gangItem(gang, m.c("&aHarmony Level 3"), m.c("&7&oIncreases the gain from Harmony."), calculateBlocksNeeded(3)));
        ganginv.setItem(6, gangItem(gang, m.c("&aExtra Member"), m.c("&7&oAllows you to have 5 Members in your gang."), calculateBlocksNeeded(4)));
        ganginv.setItem(7, PickaxeLevel.getInstance().Spacer());
        ganginv.setItem(8, PickaxeLevel.getInstance().Spacer());
        ganginv.setItem(9, PickaxeLevel.getInstance().Spacer());
        ganginv.setItem(10, PickaxeLevel.getInstance().Spacer());
        ganginv.setItem(11, gangItem(gang, m.c("&aUnity Level 1"), m.c("&7&o2% Increase to all currencies."), calculateBlocksNeeded(5)));
        ganginv.setItem(13, gangItem(gang, m.c("&aHarmony Level 4"), m.c("&7&oIncreases the chances of  Harmony."), calculateBlocksNeeded(6)));
        ganginv.setItem(12, gangItem(gang, m.c("&aUnity Level 2"), m.c("&7&o5% Increase to all currencies."), calculateBlocksNeeded(7)));
        ganginv.setItem(14, gangItem(gang, m.c("&aHarmony Level 5"), m.c("&7&oDoubles effectiveness of Harmony."), calculateBlocksNeeded(8)));
        ganginv.setItem(15, gangItem(gang, m.c("&aUnity Level 3"), m.c("&7&o10% Increase to all currencies."), calculateBlocksNeeded(9)));
        ganginv.setItem(16, PickaxeLevel.getInstance().Spacer());
        ganginv.setItem(17, PickaxeLevel.getInstance().Spacer());
        p.openInventory(ganginv);
    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        String gang = g.getGang(p);
        if (e.getView().getTitle().equals(m.c("&3&l" + gang + "'s &b&lUpgrades"))) {
            e.setCancelled(true);
        }

    }

    public void levelUp(String gang) {
        int level = g.getGangLevel(gang);
        List<String> perks = settings.getGangs().getStringList(gang + ".PerksUnlocked");
        if (level == 0) {
            perks.add("Harmony Level 1");
        } else if (level == 1) {
            perks.add("Harmony Level 2");
        } else if (level == 2) {
            perks.add("Increased Gems");
        } else if (level == 3) {
            perks.add("Harmony Level 3");
        } else if (level == 4) {
            perks.add("Extra Member");
            settings.getGangs().set(gang + ".MaxMembers", 5);
        } else if (level == 5) {
            perks.add("Unity Level 1");
        } else if (level == 6) {
            perks.add("Harmony Level 4");
        } else if (level == 7) {
            perks.add("Unity Level 2");
        } else if (level == 8) {
            perks.add("Harmony Level 5");
        } else if (level == 9) {
            perks.add("Unity Level 3");
        }
        settings.getGangs().set(gang + ".Level", level + 1);
        settings.getGangs().set(gang + ".PerksUnlocked", perks);
        settings.saveGangs();
    }

    public boolean canLevelUp(String gang) {
        boolean levelup = calculateBlocksNeeded(g.getGangLevel(gang)) <= g.getGangBlocks(gang);

        return levelup;
    }

    public int calculateBlocksNeeded(int level) {

        if (level == 0) {
            return 500000;
        }
        int blocks = 0;
        for (int i = 0; i <= level; i++) {
            blocks += ((int) (500000 + (500000 * (i * 0.9))));
        }
        return blocks;
    }

    public double getUnityLevel(String gang) {
        List<String> perks = settings.getGangs().getStringList(gang + ".PerksUnlocked");
        double unity = 1;
        for (String s : perks) {
            switch (s) {
                case "Unity Level 1":
                    unity += 0.02;
                    break;
                case "Unity Level 2":
                    unity += 0.05;
                    break;
                case "Unity Level 3":
                    unity += 0.10;
                    break;
            }
        }
        return unity;
    }

    public int getHarmonyLevel(String gang) {
        List<String> perks = settings.getGangs().getStringList(gang + ".PerksUnlocked");
        int harmony = 1;
        for (String s : perks) {
            if ("Harmony Level 2".equals(s) || "Harmony Level 4".equals(s)) {
                harmony += 1;
            }
        }
        return harmony;
    }

    public double getHarmony(String gang) {
        List<String> perks = settings.getGangs().getStringList(gang + ".PerksUnlocked");
        double harmony = 1;
        for (String s : perks) {
            if ("Harmony Level 3".equals(s)) {
                harmony += 0.5;
            }
            if ("Harmony Level 5".equals(s)) {
                harmony += 1.5;
            }
        }
        return harmony;
    }

    public void harmony(String gang) {
        Random r = new Random();
        int ri = r.nextInt(15000 / getHarmonyLevel(gang));
        if (ri == 1) {
            if (harmony.contains(gang)) return;
            harmony.add(gang);
            harmonyTokens.put(gang, 0.0);
            harmonyMoney.put(gang, 0.0);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (g.getGang(p).equals(gang) && !ScoreboardHandler.isAFK(p)) {
                    p.sendMessage(m.c("&f&lGangs &8| &bHarmony is active."));
                }
            }

            new BukkitRunnable() {
                @Override
                public void run() {
                    List<String> members = settings.getGangs().getStringList(gang + ".Members");
                    int membersOnline = 0;
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (g.getGang(p).equals(gang) && !ScoreboardHandler.isAFK(p)) {
                            membersOnline += 1;
                        }
                    }
                    harmony.remove(gang);
                    double tokens = harmonyTokens.get(gang);
                    double tdistribute = (tokens / 3) * getHarmony(gang);
                    double toPlayert = Math.round(tdistribute / membersOnline);

                    double money = harmonyMoney.get(gang);
                    double mdistribute = (money / 10) * getHarmony(gang);
                    double toPlayerm = Math.round(mdistribute / membersOnline);
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (g.getGang(p).equals(gang) && !ScoreboardHandler.isAFK(p)) {
                            p.sendMessage(m.c("&f&lGangs &8| &bFrom Harmony: &e⛀" + toPlayert + " &a$" + toPlayerm));
                            Main.econ.depositPlayer(p, toPlayerm);
                            Tokens.getInstance().addTokens(p, toPlayert);
                        }
                    }
                    harmonyTokens.remove(gang);
                    harmonyMoney.remove(gang);
                }
            }.runTaskLater(Main.plugin, 20 * 15L);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if (!MineSystem.getInstance().getMineByPlayer(p).isLocationInMine(e.getBlock().getLocation())) {
            return;
        }
        if (g.hasGang(p)) {
            String gang = g.getGang(p);
            List<String> perks = settings.getGangs().getStringList(gang + ".PerksUnlocked");
            int blocksbroken = settings.getGangs().getInt(gang + ".BlocksBroken");
            settings.getGangs().set(gang + ".BlocksBroken", blocksbroken + 1);
            if (canLevelUp(gang)) levelUp(gang);
            if (perks.contains("Harmony Level 1")) {
                harmony(gang);
            }
        }
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("gang") || cmd.getName().equalsIgnoreCase("g")) {
            Player p = (Player) sender;
            if (args.length == 0) {
                if (g.hasGang(p)) {
                    sendGang(p, p);
                } else {
                    sendGangHelp(p);
                }
                return false;
            }
            if (args.length == 1 && "help".equalsIgnoreCase(args[0])) {
                sendGangHelp(p);
            }
            if (args.length == 1 && "invite".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p) && !settings.getGangs().getString(g.getGang(p) + ".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can invite."));
                } else if (g.hasGang(p) && settings.getGangs().getString(g.getGang(p) + ".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bPlease Specify a player to invite."));
                } else {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                }
            }
            if (args.length == 2 && "invite".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                if (g.hasGang(p) && !settings.getGangs().getString(g.getGang(p) + ".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can invite."));
                    return false;
                }
                List<String> members = settings.getGangs().getStringList(g.getGang(p) + ".Members");
                if (members.size() >= settings.getGangs().getInt(g.getGang(p) + ".MaxMembers")) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou have the maximum members allowed in your gang"));
                    return false;
                }
                Player invite = Bukkit.getPlayer(args[1]);
                if (g.hasGang(invite)) {
                    p.sendMessage(m.c("&f&lGangs &8| &bPlayer is already in a gang."));
                    return false;
                }
                if (invited.get(g.getGang(p)) == null) {
                    List<Player> invitee = new ArrayList<>();
                    invitee.add(invite);
                    invited.put(g.getGang(p), invitee);
                } else {
                    invited.get(g.getGang(p)).add(invite);
                }
                invite.sendMessage(m.c("&f&lGangs &8| &b" + p.getName() + " Has invited you to join &a" + g.getGang(p)));
                invite.sendMessage(m.c("&f&lGangs &8| &bDo /gang join &a" + g.getGang(p) + " &bto join."));
                p.sendMessage(m.c("&f&lGangs &8| &bInvite to &a" + invite.getName() + " &bsent."));
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (invited.get(g.getGang(p)).contains(invite)) {
                            invited.get(g.getGang(p)).remove(invite);
                            invite.sendMessage(m.c("&f&lGangs &8| &bThe invite from &a" + g.getGang(p) + " &bhas expired."));
                            p.sendMessage(m.c("&f&lGangs &8| &bThe invite to &a" + invite.getName() + " &bhas expired."));
                        }
                    }
                }.runTaskLater(Main.plugin, 20 * 60L);
            }
            if (args.length == 2 && "join".equalsIgnoreCase(args[0])) {
                String gang = "";
                for (String name : settings.getGangs().getKeys(false)) {
                    if (name.compareToIgnoreCase(args[1]) == 0) {
                        gang = name;
                    }
                }
                if (g.hasGang(p)) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are already in a gang."));
                    return false;
                }
                if (invited.get(gang).contains(p)) {
                    List<String> members = settings.getGangs().getStringList(gang + ".Members");
                    if (members.size() >= settings.getGangs().getInt(gang + ".MaxMembers")) {
                        p.sendMessage(m.c("&f&lGangs &8| &bThat gang is already full."));
                        return false;
                    }
                    g.addMember(p, gang);
                    for (Player pp : Bukkit.getOnlinePlayers()) {
                        if (g.getGang(pp).equals(gang) && !pp.equals(p)) {
                            pp.sendMessage(m.c("&f&lGangs &8| &a" + p.getName() + " &bHas Joined."));
                        }
                    }
                    p.sendMessage(m.c("&f&lGangs &8| &bJoined &a" + gang));
                    invited.get(gang).remove(p);
                } else {
                    p.sendMessage(m.c("&f&lGangs &8| &a" + gang + " &bHas not invited you."));
                }
            }
            if (args.length == 1 && "create".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p))
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are already in a gang."));
                else {
                    p.sendMessage(m.c("&f&lGangs &8| &bPlease Specify a Name(Case Sensitive)."));
                }
            }
            if (args.length == 2 && "create".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p)) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are already in a gang."));
                    return false;
                }

                for (String name : settings.getGangs().getKeys(false)) {
                    if (name.compareToIgnoreCase(args[1]) == 0) {
                        p.sendMessage(m.c("&f&lGangs &8| &bA gang with that name already exists."));
                        return false;
                    }
                }
                g.createGang(p, args[1]);
                p.sendMessage(m.c("&f&lGangs &8| &a" + args[1] + " &bHas been created."));
            }
            if (args.length == 1 && "upgrade".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                openGangShop(p);
            }
            if (args.length == 1 && "upgrades".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                openGangShop(p);
            }
            if (args.length == 1 && "info".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p)) {
                    sendGang(p, p);
                } else {
                    p.sendMessage(m.c("&f&lGangs &8| &bPlease Specify a gang."));
                }
            }
            if (args.length == 2 && "info".equalsIgnoreCase(args[0])) {
                for (String name : settings.getGangs().getKeys(false)) {
                    if (name.compareToIgnoreCase(args[1]) == 0) {
                        sendGang(p, name);
                        return false;
                    }
                }
                boolean online = false;
                OfflinePlayer op = null;
                File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
                File[] var = mineFiles;
                assert mineFiles != null;
                int amountOfMines = mineFiles.length;
                for (int i = 0; i < amountOfMines; ++i) {
                    File mineFile = var[i];
                    String name = mineFile.getName().split("\\.")[0];
                    UUID id = UUID.fromString(name);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(name));
                    if (args[1].equalsIgnoreCase(player.getName()) && g.hasGang(player)) {
                        online = true;
                        op = player;
                    }
                }
                if (online == true) {
                    sendGang(p, op);
                } else {
                    p.sendMessage(m.c("&f&lGangs &8| &bCould not find that gang."));
                }
            }
            if (args.length == 2 && "kick".equalsIgnoreCase(args[0])) {
                String gang = g.getGang(p);
                if (!g.hasGang(p)) return false;
                if (g.hasGang(p) && !settings.getGangs().getString(g.getGang(p) + ".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can kick."));
                    return false;
                }
                List<String> members = settings.getGangs().getStringList(gang + ".Members");
                String uuid = null;
                File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
                File[] var = mineFiles;
                assert mineFiles != null;
                int amountOfMines = mineFiles.length;
                for (int i = 0; i < amountOfMines; ++i) {
                    File mineFile = var[i];
                    String name = mineFile.getName().split("\\.")[0];
                    UUID id = UUID.fromString(name);
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(name));
                    if (args[1].equalsIgnoreCase(player.getName())) {
                        uuid = name;
                    }
                }
                if (uuid != null && members.contains(uuid)) {
                    members.remove(uuid);
                    PlayerDataHandler.getInstance().getPlayerData(p).set(uuid + ".Gang", "");
                    PlayerDataHandler.getInstance().savePlayerData(p);
                    settings.getGangs().set(gang + ".Members", members);
                    settings.saveGangs();
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                    p.sendMessage(m.c("&f&lGangs &8| &b" + player.getName() + " has been kicked."));
                } else {
                    p.sendMessage(m.c("&f&lGangs &8| &bCould not find that player."));
                }
            }
            if (args.length == 2 && "setowner".equalsIgnoreCase(args[0])) {
                if (!g.hasGang(p)) return false;
                if (g.hasGang(p) && !settings.getGangs().getString(g.getGang(p) + ".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can change ownership."));
                    return false;
                }
                String gang = g.getGang(p);
                List<String> members = settings.getGangs().getStringList(gang + ".Members");
                for (String s : members) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
                    if (args[1].equalsIgnoreCase(player.getName())) {
                        g.changeOwner(p, player.getUniqueId().toString(), gang);
                    }
                }
                settings.saveGangs();
            }
            if (args.length == 1 && "delete".equalsIgnoreCase(args[0])) {
                String gang = g.getGang(p);
                if (!g.hasGang(p)) return false;
                if (g.hasGang(p) && !settings.getGangs().getString(g.getGang(p) + ".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner  can delete the gang."));
                    return false;
                }
                p.sendMessage(m.c("&f&lGangs &8| &bGang Deleted."));
                settings.getGangs().set(gang, null);
                PlayerDataHandler.getInstance().getPlayerData(p).set("Gang", "");
                settings.saveGangs();
            }
            if (args.length == 1 && "leave".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                if (g.hasGang(p) && settings.getGangs().getString(g.getGang(p) + ".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bThe owner cannot leave the gang."));
                    return false;
                }
                g.removeMember(p, g.getGang(p));
            }
            if (args.length == 1 && "chat".equalsIgnoreCase(args[0])) {
                if (g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                if (gangchat.contains(p)) {
                    gangchat.remove(p);
                    p.sendMessage(m.c("&f&lGangs &8| &bLeft Gang Chat."));
                    return false;
                }
                gangchat.add(p);
                p.sendMessage(m.c("&f&lGangs &8| &bEntered Gang Chat."));
            }
        }

        return false;
    }


}
