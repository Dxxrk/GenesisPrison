package me.dxrk.Events;

import me.dxrk.Enchants.SkillsEventsListener;
import me.dxrk.Gangs.CMDGang;
import me.dxrk.Gangs.Gangs;
import me.dxrk.Main.Functions;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.Mine;
import me.dxrk.Mines.MineSystem;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class SellHandler implements Listener, CommandExecutor {


    SettingsManager settings = SettingsManager.getInstance();

    Methods methods = Methods.getInstance();

    public static double servermulti = 1.0D;

    public static HashMap<Player, Double> pickmulti = new HashMap<>();


    public static SellHandler instance = new SellHandler();

    public static SellHandler getInstance() {
        return instance;
    }

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


    public static String format(double amt) {
        if (amt >= 1.0E18D)
            return String.format("%.1f Quint", amt / 1.0E18D);
        if (amt >= 1.0E15D)
            return String.format("%.1f Quad", amt / 1.0E15D);
        if (amt >= 1.0E12D)
            return String.format("%.1f Tril", amt / 1.0E12D);
        if (amt >= 1.0E9D)
            return String.format("%.1f Bil", amt / 1.0E9D);
        if (amt >= 1000000.0D)
            return String.format("%.1f Mil", amt / 1000000.0D);
        return NumberFormat.getNumberInstance(Locale.US).format(amt);
    }


    public boolean isDbl(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    @EventHandler
    public void invcl(InventoryClickEvent e) {
        if (e.getView().getTitle().equals(ChatColor.RED + "Investments"))
            e.setCancelled(true);
    }

    public int getPrestiges(Player p) {
        int prestiges = PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges");
        return prestiges;
    }


    private static Methods m = Methods.getInstance();

    public void sellEnchant(Player p, List<ItemStack> items, String enchantName, double tokens) {
        String gang = Gangs.getInstance().getGang(p);
        double total = 0.0D;
        int amountotal = 0;
        double greed = Functions.greed(p);
        double sell = Functions.sellBoost(p);
        double miningboost = BoostsHandler.sell.getOrDefault(p.getUniqueId(), 1.0);
        double multi = getMulti(p) / 1.75;
        double momentum = MomentumHandler.getBonus(p.getUniqueId());
        if (multi < 1) {
            multi = 1;
        }
        double monster = 1;
        if (MonsterHandler.activeMonster.containsKey(p) && (ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Ladon")
                || ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Phoenix")
                || ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Medusa"))) {
            monster = MonsterHandler.getMonsterBoost(p, 3);
        }
        double event = SkillsEventsListener.getEventMulti();
        double prestige = getPrestiges(p) * 1.25;
        double unity = CMDGang.getInstance().getUnityLevel(gang);
        double multiply = 1;
        if (prestige < 1) {
            prestige = 1;
        }
        if (Functions.multiply.contains(p)) {
            multiply = 2;
        }
        for (ItemStack i : items) {
            if (i != null) {


                //Change this config file to look a lot nicer + add different block prices
                double price = Methods.getSellPrice(RankupHandler.getInstance().getRank(p));


                total += price * (multi + greed + event) * sell * miningboost * prestige * multiply * unity * momentum * monster;

                amountotal += i.getAmount();

            }
        }
        p.updateInventory();
        if (SettingsManager.getInstance().getOptions().getBoolean(p.getUniqueId() + "." + enchantName.replace(" ", "-") + "-Messages") == true) {
            p.sendMessage(c("&f&l" + enchantName + " &8| &b+$" + format(total * amountotal) + " &7& &e⛀" + format(tokens)));
        }

        Main.econ.depositPlayer(p, total * amountotal);
        if (CMDGang.harmony.contains(gang)) {
            double htokens = CMDGang.harmonyTokens.get(gang);
            CMDGang.harmonyTokens.put(Gangs.getInstance().getGang(p), htokens + tokens);
            double hmoney = CMDGang.harmonyMoney.get(gang);
            CMDGang.harmonyMoney.put(gang, hmoney + (total * amountotal));
        }
    }


    public void sell(Player p, List<ItemStack> items) {
        String gang = Gangs.getInstance().getGang(p);
        double total = 0.0D;
        int amountotal = 0;
        double greed = Functions.greed(p);
        double sell = Functions.sellBoost(p);
        double miningboost = BoostsHandler.sell.getOrDefault(p.getUniqueId(), 1.0);
        double multi = getMulti(p) / 1.75;
        double momentum = MomentumHandler.getBonus(p.getUniqueId());
        if (multi < 1) {
            multi = 1;
        }
        double monster = 1;
        if (MonsterHandler.activeMonster.containsKey(p) && (ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Ladon")
                || ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Phoenix")
                || ChatColor.stripColor(MonsterHandler.activeMonster.get(p).getItemMeta().getDisplayName()).split(" ")[0].equals("Medusa"))) {
            monster = MonsterHandler.getMonsterBoost(p, 3);
        }
        double event = SkillsEventsListener.getEventFortune();
        double prestige = getPrestiges(p) * 1.25;
        double multiply = 1;
        if (prestige < 1) {
            prestige = 1;
        }
        if (Functions.multiply.contains(p)) {
            multiply = 2;
        }
        for (ItemStack i : items) {
            if (i != null) {


                double price = Methods.getSellPrice(RankupHandler.getInstance().getRank(p));


                total += price * (multi + greed + event) * sell * miningboost * prestige * multiply * momentum * monster;

                amountotal += i.getAmount();

            }
        }

        p.updateInventory();


        Main.econ.depositPlayer(p, total * amountotal);
        if (CMDGang.harmony.contains(gang)) {
            double hmoney = CMDGang.harmonyMoney.get(gang);
            CMDGang.harmonyMoney.put(gang, hmoney + (total * amountotal));
        }
        double percents;
        p.getScoreboard().getTeam("balance").setSuffix(c("&a" + Main.formatAmt(Tokens.getInstance().getBalance(p))));
        percents = Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) * 100.0D;
        double dmultiply = percents * 10.0D;
        double dRound = Math.round(dmultiply) / 10.0D;
        if (dRound >= 100.0D) {
            p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
        } else {
            p.getScoreboard().getTeam("percent").setSuffix(c("&c") + dRound + "%");
        }
    }


    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }


    public int getFortune(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor(s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (byte) (b + 1)) {
            char c = arrayOfChar[b];
            if (!this.isInt(c)) continue;
            lvl.append(c);
        }
        if (this.isInt(lvl.toString())) {
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        Mine m = MineSystem.getInstance().getMineByPlayer(p);
        if (m.isLocationInMine(e.getBlock().getLocation())) {
            e.setCancelled(true);
        }
        if (!m.isLocationInMine(e.getBlock().getLocation()) && !BuildModeHandler.playersinbm.containsKey(p.getUniqueId()) && !p.isOp()) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        p.sendMessage("1 Block You're breaking: "+ e.getBlock().getLocation());
        Mine m = MineSystem.getInstance().getMineByPlayer(p);
        if (!m.isLocationInMine(e.getBlock().getLocation()) && !p.isOp()) {
            e.setCancelled(true);
            return;
        }
        p.sendMessage("2 You're breaking: "+ e.getBlock().getLocation());
        if (!m.isLocationInMine(e.getBlock().getLocation()) && p.isOp()) {
            if (p.getEquipment().getItemInMainHand() != null &&
                    p.getEquipment().getItemInMainHand().getType() == Material.DIAMOND_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.WOODEN_PICKAXE
                    || p.getEquipment().getItemInMainHand().getType() == Material.STONE_PICKAXE || p.getEquipment().getItemInMainHand().getType() == Material.GOLDEN_PICKAXE
                    || p.getEquipment().getItemInMainHand().getType() == Material.IRON_PICKAXE) {
                e.setCancelled(true);
            }
            return;
        }
        p.sendMessage("2 You're breaking: "+ e.getBlock().getLocation());

        double fortuity = Functions.Foruity(p);
        double skill = SkillsEventsListener.getSkillsBoostFortune(p);
        double event1 = SkillsEventsListener.getEventFortune();



        if (!e.isCancelled()) {
            if (p.getEquipment().getItemInMainHand() != null && p.getEquipment().getItemInMainHand().hasItemMeta() && p.getEquipment().getItemInMainHand().getItemMeta().hasLore()) {

                int line = 0;
                for (int x = 0; x < p.getEquipment().getItemInMainHand().getItemMeta().getLore().size(); x++) {
                    if (ChatColor.stripColor(p.getEquipment().getItemInMainHand().getItemMeta().getLore().get(x)).contains("Fortune")) {
                        line = x;
                    }
                }
                int fortune = (int) (getFortune(p.getEquipment().getItemInMainHand().getItemMeta().getLore().get(line)) * fortuity * skill * event1 /
                        (3.0));


                ArrayList<ItemStack> sellblocks = new ArrayList<>();

                sellblocks.add(new ItemStack(e.getBlock().getType(), fortune));
                e.getBlock().setType(Material.AIR);
                e.setCancelled(true);

                sell(p, sellblocks);

            }
        }
    }


    private ArrayList<String> reset = new ArrayList<>();


    public void setMulti(Player p, double d) {
        PlayerDataHandler.getInstance().getPlayerData(p).set("Multi", d);
    }

    public double getMulti(Player p) {
        double d = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("Multi");

        if (d < 1.0) {
            d = 1.0;
        }
        double dround = d * 10.0;
        double drounded = Math.round(dround) / 10.0;
        return drounded;
    }

    public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {


        if (cmd.getName().equalsIgnoreCase("resetmine") || cmd.getName().equalsIgnoreCase("rm")) {
            if (cs instanceof Player) {
                Player p = (Player) cs;

                if (args.length == 0) {
                    if (p.hasPermission("mines.resetmine")) {
                        Mine mine = MineSystem.getInstance().getMineByPlayer(p);
                        if (reset.contains(p.getUniqueId().toString())) {
                            p.sendMessage(c("&c/resetmine(/rm) is on cooldown."));
                        } else {
                            int rank = RankupHandler.getInstance().getRank(p);
                            if (PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("Ethereal")) {
                                rank = 1000;
                            }
                            mine.reset();
                            if (!p.isOp())
                                reset.add(p.getUniqueId().toString());
                        }
                        new BukkitRunnable() {

                            @Override
                            public void run() {
                                reset.remove(p.getUniqueId().toString());

                            }

                        }.runTaskLater(Main.plugin, 20 * 60);
                    }
                }
            }
        }


        if (cmd.getName().equalsIgnoreCase("multi")) {

            if (args.length == 0) {
                if (!(cs instanceof Player)) return false;
                cs.sendMessage(ChatColor.translateAlternateColorCodes('&', "&dYour Multi: &b" + getMulti((Player) cs)));
            } else if (args.length == 1) {
                if (!cs.hasPermission("rank.admin")) return false;
                if ("add".equalsIgnoreCase(args[0])) {
                    cs.sendMessage(ChatColor.RED + "Specify a Players Name!");
                }
            } else if (args.length == 2) {
                if (!cs.hasPermission("rank.admin")) {
                    cs.sendMessage(ChatColor.RED + "No Permission.");
                    return false;
                }
                if ("add".equalsIgnoreCase(args[0])) {
                    Player reciever = Bukkit.getServer().getPlayer(args[1]);
                    if (!reciever.isOnline()) {
                        cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
                    } else {
                        cs.sendMessage(ChatColor.RED + "You must specify an amount");
                    }
                }
                if ("set".equalsIgnoreCase(args[0])) {
                    Player reciever = Bukkit.getServer().getPlayer(args[1]);
                    if (!reciever.isOnline()) {
                        cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
                    } else {
                        cs.sendMessage(ChatColor.RED + "You must specify an amount");
                    }
                }
            } else if (args.length == 3) {
                Player reciever = Bukkit.getServer().getPlayer(args[1]);
                if (!cs.hasPermission("rank.admin")) return false;
                if ("add".equalsIgnoreCase(args[0])) {

                    if (isDbl(args[2])) {
                        if (reciever == null) {
                            cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
                            return false;
                        }

                        String Amount = args[2].replace("-", "");
                        double amnt = Double.parseDouble(Amount);
                        double d = PlayerDataHandler.getInstance().getPlayerData(reciever).getDouble("Multi");
                        double newMulti = d + amnt;


                        PlayerDataHandler.getInstance().getPlayerData(reciever).set("Multi", newMulti);


                    }
                }
                if ("set".equalsIgnoreCase(args[0])) {

                    if (isDbl(args[2])) {
                        if (reciever == null) {
                            cs.sendMessage(ChatColor.RED + args[1] + " is not online!");
                            return false;
                        }

                        String Amount = args[2].replace("-", "");
                        double amount = Double.parseDouble(Amount);
                        PlayerDataHandler.getInstance().getPlayerData(reciever).set("Multi", amount);


                    }
                }
            }
        }
        if (cmd.getName().equalsIgnoreCase("withdraw")) {
            if (!cs.hasPermission("rank.admin")) {
                cs.sendMessage(ChatColor.RED + "Withdraw is disabled");
                return false;
            }

        }
        return false;
    }
}
