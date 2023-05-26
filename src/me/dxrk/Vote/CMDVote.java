package me.dxrk.Vote;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import me.dxrk.Events.LocksmithHandler;
import me.dxrk.Events.ScoreboardHandler;
import me.dxrk.Events.SellHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import mkremins.fanciful.FancyMessage;
import net.citizensnpcs.api.CitizensAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;

public class CMDVote implements Listener, CommandExecutor {

    Methods m = Methods.getInstance();

    public void openVotingInv(Player p) {
        Inventory i = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.AQUA + "Vote!");
        i.setItem(0, voteItem(p, "Mc-lists"));
        i.setItem(1, voteItem(p, "Mineservers"));
        i.setItem(2, voteItem(p, "topG"));
        i.setItem(3, voteItem(p, "Minecraft-mp"));
        i.setItem(4, voteItem(p, "MinecraftServers"));
        p.openInventory(i);
    }

    public void openNewVotingInv(Player p) {
        Inventory i = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.AQUA + "Vote Now!");
        //vote links
        ItemStack emerald = new ItemStack(Material.EMERALD, 1);
        ItemMeta imemerald = emerald.getItemMeta();
        imemerald.setDisplayName(ChatColor.GREEN + "Vote Links");
        List<String> loreemerald = new ArrayList<>();
        loreemerald.add(ChatColor.GREEN + "Click to see vote links.");
        imemerald.setLore(loreemerald);
        emerald.setItemMeta(imemerald);
        i.setItem(0, emerald);
        //info
        ItemStack paper = new ItemStack(Material.PAPER, 1);
        ItemMeta impaper = paper.getItemMeta();
        impaper.setDisplayName(ChatColor.GREEN + "Info:");
        List<String> lorepaper = new ArrayList<>();
        lorepaper.add(ChatColor.AQUA + "Each Vote is a VotePoint added to your account.");
        lorepaper.add(ChatColor.AQUA + "For each VotePoint you can unlock a single treasure chest in Treasury.");
        impaper.setLore(lorepaper);
        paper.setItemMeta(impaper);
        i.setItem(2, paper);
        //treasury
        ItemStack gold = new ItemStack(Material.GOLD_INGOT, 1);
        ItemMeta imgold = gold.getItemMeta();
        imgold.setDisplayName(ChatColor.GOLD + "Treasury");
        List<String> loregold = new ArrayList<>();
        loregold.add(ChatColor.AQUA + "Click to open Treasury");
        loregold.add(ChatColor.AQUA + "And test your luck!");
        imgold.setLore(loregold);
        gold.setItemMeta(imgold);
        i.setItem(4, gold);
        p.openInventory(i);
    }

    public void openTreasuryInv(Player p) {
        Inventory i = Bukkit.createInventory(null, 36, ChatColor.GOLD + "Treasury");
        i.setItem(35, VotePointsPaper(p));
        for (int j = 0; j < 35; j++) {
            i.setItem(j, TreasureChest());
        }
        p.openInventory(i);
    }

    public ItemStack VotePointsPaper(Player p) {
        int votePoints = settings.getVote().getInt(p.getUniqueId().toString() + ".VotePoints");
        ItemStack paper = new ItemStack(Material.PAPER, 1);
        ItemMeta impaper = paper.getItemMeta();
        impaper.setDisplayName(ChatColor.WHITE + "Votepoints: " + votePoints);
        paper.setItemMeta(impaper);
        return paper;
    }

    public ItemStack TreasureChest() {
        ItemStack i = new ItemStack(Material.CHEST, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.GOLD + "Treasure Chest");
        List<String> lore = new ArrayList<>();
        lore.add("Click to open!");
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    int crateposition;
    int eggposition;
    int rankposition;

    @EventHandler
    public void onNewInvClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getClickedInventory() == null)
            return;
        if (e.getClickedInventory().getName() == null)
            return;
        if (e.getCurrentItem() == null) {
            return;
        }
        if (e.getInventory().getName().equals(ChatColor.AQUA + "Vote Now!")) {
            e.setCancelled(true);
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Info:"))
                return;
            if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GREEN + "Vote Links")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&bstore.mcgenesis.net/vote"));
            } else if (e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Treasury")) {
                openTreasuryInv(p);
                crateposition = r.nextInt(35);
                eggposition = r.nextInt(35);
                rankposition = r.nextInt(35);
            }
        }
        if (e.getInventory().getName().equals(ChatColor.GOLD + "Treasury")) {
            e.setCancelled(true);
            int votePoints = settings.getVote().getInt(p.getUniqueId().toString() + ".VotePoints");
            if (votePoints <= 0)
                return;
            if (!e.getCurrentItem().hasItemMeta())
                return;
            if (!e.getCurrentItem().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Treasure Chest"))
                return;


            if (crateposition == eggposition) {
                eggposition = r.nextInt(35);
            }
            if (rankposition == eggposition || rankposition == crateposition) {
                rankposition = r.nextInt(35);
            }
            Inventory i = e.getClickedInventory();
            if (e.getRawSlot() == rankposition) {
                ItemStack rankkey = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta im = rankkey.getItemMeta();
                im.setDisplayName(ChatColor.LIGHT_PURPLE + "2x Rank Keys");
                rankkey.setItemMeta(im);
                i.setItem(rankposition, rankkey);
                settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints - 1);
                settings.saveVote();
                i.setItem(35, VotePointsPaper(p));
                LocksmithHandler.getInstance().addKey(p, "Rank", 2);
                settings.saveLocksmith();
            } else if (e.getRawSlot() == crateposition) {
                ItemStack crate = new ItemStack(Material.ENDER_CHEST, 1);
                ItemMeta im = crate.getItemMeta();
                im.setDisplayName(ChatColor.BLUE + "1x Genesis Crate");
                crate.setItemMeta(im);
                i.setItem(crateposition, crate);
                settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints - 1);
                settings.saveVote();
                i.setItem(35, VotePointsPaper(p));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "givecrate " + p.getName() + " genesis");
            } else if (e.getRawSlot() == eggposition) {
                ItemStack egg = new ItemStack(Material.MONSTER_EGG, 1);
                ItemMeta im = egg.getItemMeta();
                im.setDisplayName(ChatColor.WHITE + "1x Monster Egg");
                egg.setItemMeta(im);
                i.setItem(eggposition, egg);
                settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints - 1);
                settings.saveVote();
                i.setItem(35, VotePointsPaper(p));
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveegg " + p.getName());
            } else {
                int reward = r.nextInt(3);
                if (reward == 0) {
                    ItemStack betakey = new ItemStack(Material.TRIPWIRE_HOOK, 1);
                    ItemMeta im = betakey.getItemMeta();
                    im.setDisplayName(ChatColor.RED + "10x Beta Key");
                    betakey.setItemMeta(im);
                    i.setItem(e.getRawSlot(), betakey);
                    settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints - 1);
                    settings.saveVote();
                    i.setItem(35, VotePointsPaper(p));
                    LocksmithHandler.getInstance().addKey(p, "Beta", 10);
                    settings.saveLocksmith();
                } else if (reward == 1) {
                    ItemStack omegakey = new ItemStack(Material.TRIPWIRE_HOOK, 1);
                    ItemMeta im = omegakey.getItemMeta();
                    im.setDisplayName(ChatColor.DARK_RED + "10x Omega Key");
                    omegakey.setItemMeta(im);
                    i.setItem(e.getRawSlot(), omegakey);
                    settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints - 1);
                    settings.saveVote();
                    i.setItem(35, VotePointsPaper(p));
                    LocksmithHandler.getInstance().addKey(p, "Omega", 10);
                    settings.saveLocksmith();
                } else {
                    ItemStack multi = new ItemStack(Material.EMERALD, 1);
                    ItemMeta im = multi.getItemMeta();
                    im.setDisplayName(ChatColor.GRAY + "10x Multi");
                    multi.setItemMeta(im);
                    i.setItem(e.getRawSlot(), multi);
                    settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints - 1);
                    settings.saveVote();
                    i.setItem(35, VotePointsPaper(p));
                    SellHandler.getInstance().setMulti(p, SellHandler.getInstance().getMulti(p) + 10);
                    settings.saveMultiplier();
                }
            }
        }
    }

    String prefix = ChatColor.translateAlternateColorCodes('&', "&7&l[&b&lVote&d&lParty&7&l] &f");

    public void updateNPC() {
        //orderTop();
        CitizensAPI.getNPCRegistry().getById(99).setName(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(0))).getName());
        CitizensAPI.getNPCRegistry().getById(100).setName(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(1))).getName());
        CitizensAPI.getNPCRegistry().getById(101).setName(Bukkit.getOfflinePlayer(UUID.fromString(top5.get(2))).getName());
    }

    public void giveItem(Player p, ItemStack i) {
        if (i.getItemMeta().hasDisplayName()) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + "You have been given &b" + i.getItemMeta().getDisplayName()));
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', this.prefix + "You have been given &b" + i.getAmount() + " " + i.getType().toString().toLowerCase() + "(s)"));
        }
        if (i.getType().equals(Material.EMERALD)) {
            double base = Double.parseDouble(getInt(ChatColor.stripColor(i.getItemMeta().getDisplayName())));
            if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("100 Trillion")))
                base = 1.0E14D;
            if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("10 Trillion")))
                base = 10.0e12D;
            if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("250 Trillion")))
                base = 250.0e12D;
            if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("500 Trillion")))
                base = 500.0E12D;
            if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("1 Quadrillion")))
                base = 1.0E15D;
            if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("1 Trillion")))
                base = 1.0E12D;
            if (i.getItemMeta().getDisplayName().contains(ChatColor.stripColor("750 Trillion")))
                base = 750.0E12D;
            Main.econ.depositPlayer(p, base);
        } else if (i.getType().equals(Material.PRISMARINE_CRYSTALS)) {
            String base = getInt(i.getItemMeta().getDisplayName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tokens add " + p.getName() + " " + base);
        } else {
            p.getInventory().addItem(i);
        }
    }

    public String getInt(String s) {
        StringBuilder sb = new StringBuilder();
        byte b;
        int i;
        char[] arrayOfChar;
        for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
            char c = arrayOfChar[b];
            if (isInt(c))
                sb.append(c);
            b++;
        }
        return sb.toString();
    }

    public boolean isInt(char c) {
        try {
            String str = String.valueOf(c);
            int i = Integer.parseInt(str);
            return i >= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @EventHandler
    public void onInvClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(ChatColor.AQUA + "Vote!")) {
            e.setCancelled(true);
            if (e.getRawSlot() < 9 &&
                    e.getCurrentItem() != null) {
                if (e.getCurrentItem().getDurability() == 14) {
                    e.getWhoClicked().sendMessage(ChatColor.RED + "You can not " +
                            " yet!");
                    openVotingInv((Player) e.getWhoClicked());
                    return;
                }
                if (e.getCurrentItem().getDurability() == 5) {
                    Player p = (Player) e.getWhoClicked();
                    p.sendMessage("");
                    FancyMessage vote = (new FancyMessage("✔      ")).color(ChatColor.AQUA).style(ChatColor.BOLD);
                    vote.then("Click me to vote").color(ChatColor.AQUA).style(ChatColor.UNDERLINE).tooltip(ChatColor.GRAY + "Click here.");
                    if (e.getRawSlot() == 0)
                        vote.link("https://mc-lists.org/server-genesis-network.2622/vote");
                    if (e.getRawSlot() == 1)
                        vote.link("https://mineservers.com/server/WaazZQi5/vote");
                    if (e.getRawSlot() == 2)
                        vote.link("https://topg.org/minecraft-servers/server-615534");
                    if (e.getRawSlot() == 3)
                        vote.link("https://minecraft-mp.com/server/268257/vote/");
                    if (e.getRawSlot() == 4)
                        vote.link("https://minecraftservers.org/vote/594587");
                    vote.then("      ✔").color(ChatColor.AQUA).style(ChatColor.BOLD);
                    vote.send(p);
                    p.sendMessage("");
                    p.closeInventory();
                }
            }
        }
    }

    public ItemStack voteItem(Player p, String serviceName) {
        ItemStack i = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        ItemMeta im = i.getItemMeta();
        if (canVote(p, serviceName)) {
            im.setDisplayName(ChatColor.GREEN + "Click to vote!");
        } else {
            im.setDisplayName(ChatColor.RED + "You can not vote yet!");
            i.setDurability((short) 14);
            ArrayList<String> lore = new ArrayList<>();
            lore.add(ChatColor.GRAY + "Vote again in:");
            lore.add(ChatColor.RED + formatTime(getCooldownTime(p, serviceName)));
            im.setLore(lore);
        }
        i.setItemMeta(im);
        return i;
    }

    public boolean canVote(Player p, String serviceName) {
        if (!this.settings.getVote().contains(p.getUniqueId().toString()))
            return true;
        long time = this.settings.getVote().getLong(p.getUniqueId().toString() + "." + serviceName);
        long currentTime = (new Date()).getTime();
        return currentTime >= time;
    }

    public long getCooldownTime(Player p, String serviceName) {
        long time = this.settings.getVote().getLong(p.getUniqueId().toString() + "." + serviceName);
        long currentTime = (new Date()).getTime();
        return time - currentTime;
    }

    public String formatTime(long base) {
        int hours = 0;
        int minutes = 0;
        long seconds = 0L;
        long ms = base;
        while (ms > 1000L) {
            seconds++;
            ms -= 1000L;
        }
        while (seconds > 60L) {
            minutes++;
            seconds -= 60L;
        }
        while (minutes > 60) {
            minutes -= 60;
            hours++;
        }
        return hours + " Hours " + minutes + " Minutes" + " and " + Math.round((float) seconds) + " Seconds";
    }

    SettingsManager settings = SettingsManager.getInstance();

    public static HashMap<String, Integer> allMiners = new HashMap<>();

    public static List<String> top5 = new ArrayList<>();

    public static HashMap<String, Integer> points = new HashMap<>();

    public void updatepoints() {
        points.clear();
        for (String s : this.settings.getVote().getKeys(false)) {
            if (!s.contains("Server") && !s.contains("VoteShop"))
                points.put(s, this.settings.getVote().getInt(s + ".Votes"));
        }
    }


    public static CMDVote instance = new CMDVote();

    public static CMDVote getInstance() {
        return instance;
    }
  
  
  
/*public void orderTop() {
    updatepoints();
    top5.clear();
    Object[] a = points.entrySet().toArray();
    Arrays.sort(a,
            new Comparator() {
              public int compare(Object o1, Object o2) {
                return ((Integer)((Map.Entry<?, ?>)o2).getValue()).compareTo(
                        (Integer)((Map.Entry<?, ?>)o1).getValue());
              }
            });

    byte b;
    int i;
    Object[] arrayOfObject1;
    for (i = (arrayOfObject1 = a).length, b = 0; b < i; ) {
      Object e = arrayOfObject1[b];
      top5.add((String)((Map.Entry<?, ?>)e).getKey());
      b++;
    } 
  }*/

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("Vote")) {
            Player p = (Player) sender;
            if (args.length == 0) {
                openNewVotingInv(p);
            }
        } else if (cmd.getName().equalsIgnoreCase("voteparty")) {
            if (sender.hasPermission("voteparty.start")) {
                voteParty();
            }
        } else if (cmd.getName().equalsIgnoreCase("givevotepoints")) {
            if (sender.hasPermission("genesis.givevotepoints")) {
                Player p = Bukkit.getPlayer(args[0]);
                int votePoints = settings.getVote().getInt(p.getUniqueId().toString() + ".VotePoints");
                settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints + Integer.parseInt(args[1]));
                settings.saveVote();
            }
        }
        return false;
    }

    public int getVotes(Player p) {
        return this.settings.getVote().getInt(p.getUniqueId().toString() + ".Votes");
    }

    public int getVotes(OfflinePlayer p) {
        return this.settings.getVote().getInt(p.getUniqueId().toString() + ".Votes");
    }

    public static ArrayList<ItemStack> rewards = new ArrayList<>();

    Random r = new Random();

    int rand = 0;

    private ItemStack randomItem(ArrayList<ItemStack> list) {
        int x = this.r.nextInt(list.size());
        if (x == this.rand)
            if (x + 2 > list.size()) {
                x--;
            } else {
                x++;
            }
        this.rand = x;
        return list.get(x);
    }

    public void randomRewards(int delay) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.plugin, () -> {
            for (Player p : Bukkit.getOnlinePlayers())
                CMDVote.this.giveItem(p, CMDVote.this.randomItem(CMDVote.rewards));
        }, (delay * 10L));
    }

    public void loadChests() {
        Chest c1 = (Chest) Bukkit.getWorld("Prison").getBlockAt(new Location(Bukkit.getWorld("Prison"), 1312.0D, 1.0D, 701.0D)).getState();
        byte b;
        int i;
        ItemStack[] arrayOfItemStack;
        for (i = (arrayOfItemStack = c1.getInventory().getContents()).length, b = 0; b < i; ) {
            ItemStack itemStack = arrayOfItemStack[b];
            if (itemStack != null)
                rewards.add(itemStack);
            b++;
        }
    }

    public boolean giveReward(int i) {
        return this.r.nextInt(i) == 1;
    }


    public void voteParty() {
        if (rewards.size() == 0)
            loadChests();
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost Sell VoteParty 3.0 900");
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (ScoreboardHandler.isAFK(p)) continue;
            LocksmithHandler.getInstance().addKey(p, "alpha", 1);
            LocksmithHandler.getInstance().addKey(p, "beta", 1);
            LocksmithHandler.getInstance().addKey(p, "omega", 1);
            LocksmithHandler.getInstance().addKey(p, "seasonal", 1);
            LocksmithHandler.getInstance().addKey(p, "community", 1);
            p.sendMessage(m.c("&f&lVoteParty &8| &b+1 Alpha Key"));
            p.sendMessage(m.c("&f&lVoteParty &8| &b+1 Beta Key"));
            p.sendMessage(m.c("&f&lVoteParty &8| &b+1 Omega Key"));
            p.sendMessage(m.c("&f&lVoteParty &8| &b+1 Seasonal Key"));
            p.sendMessage(m.c("&f&lVoteParty &8| &b+1 Community Key"));
        }
    }

    private String getTodayDate() {
        LocalDate time = java.time.LocalDate.now();

        return time.toString();
    }

    @EventHandler
    public void onPlayerVote(VotifierEvent e) {
        Vote v = e.getVote();
        Player p = Bukkit.getServer().getPlayer(v.getUsername());
        int servervotes = this.settings.getVote().getInt("ServerVotes");
        this.settings.getVote().set("ServerVotes", servervotes - 1);
        assert p != null;
        int totalvotes = this.settings.getVote().getInt(p.getUniqueId().toString() + ".Votes");
        int newtotalvotes = totalvotes + 1;
        long time = (new Date()).getTime() + 86400000L;
        this.settings.getVote().set(p.getUniqueId().toString() + ".Votes", newtotalvotes);
        CMDVoteShop.addCoupon(p, 0.05);
        this.settings.getVote().set(p.getUniqueId().toString() + "." + v.getServiceName(), time);
        this.settings.saveVote();
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "tokens add " + p.getName() + " 50000");
        Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), "cratekey " + p.getName() + " vote");
        this.settings.getVote().set(p.getUniqueId().toString() + ".HasVoted", getTodayDate());
        this.settings.saveVote();
        p.sendMessage(m.c("&f&lVote &8| &b+1 Vote Key &e+⛀50,000"));
        if (servervotes < 2) {
            this.settings.getVote().set("ServerVotes", 30);
            this.settings.saveVote();
            voteParty();
        }
        int votePoints = settings.getVote().getInt(p.getUniqueId().toString() + ".VotePoints");
        settings.getVote().set(p.getUniqueId().toString() + ".VotePoints", votePoints + 1);
    }
}
