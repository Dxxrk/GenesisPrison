package me.dxrk.Enchants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import me.dxrk.Events.*;
import me.dxrk.Main.*;
import me.dxrk.Mines.MineHandler;
import me.dxrk.Mines.ResetHandler;
import me.dxrk.Tokens.Tokens;
import me.dxrk.utils.WaveEffect;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;

import me.dxrk.Vote.CMDVoteShop;

import me.jet315.prisonmines.mine.Mine;

public class EnchantMethods implements CommandExecutor {

    static EnchantMethods instance = new EnchantMethods();

    public static EnchantMethods getInstance() {
        return instance;
    }

    private Methods m = Methods.getInstance();
    private final SettingsManager settings = SettingsManager.getInstance();

    public static List<Player> laser = new ArrayList<>();


    public boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }


    public int getFortune(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor((String) s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (int) ((byte) (b + 1))) {
            char c = arrayOfChar[b];
            if (!this.isInt(c)) continue;
            lvl.append(c);
        }
        if (this.isInt(lvl.toString())) {
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }
    public ItemStack testenchant(String Enchant) {
        ItemStack a = new ItemStack(Material.WOOD_PICKAXE, 1, (short) 0);
        ItemMeta am = a.getItemMeta();
        List<String> lore = new ArrayList<>();
        am.setDisplayName(c("&cTest Pickaxe"));
        am.addEnchant(Enchantment.DIG_SPEED, 32000, true);
        am.addEnchant(Enchantment.DURABILITY, 32000, true);
        am.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        lore.add(c("&b&m-<>-&aEnchants&b&m-<>- "));
        lore.add(c("&c"+Enchant+ " &e9999"));
        lore.add("  ");
        lore.add(c("&b&m-<>-&aTrinkets 0/4&b&m-<>- "));
        lore.add("  ");
        lore.add(c("&b&m-<>-&aLevel&b&m-<>- "));
        lore.add(c("&cLevel: &e1"));
        lore.add(c("&cProgress: &e0%"));
        am.setLore(lore);
        a.setItemMeta(am);
        return a;
    }
    private ArrayList<Location> getCircle(Location center, double radius, int amount) {
        World world = center.getWorld();
        double increment = ((2 * Math.PI) / amount);
        ArrayList<Location> locations = new ArrayList<>();
        for (int i = 0; i < amount; i++) {
            double angle = i * increment;
            double x = center.getX() + (radius * Math.cos(angle));
            double z = center.getZ() + (radius * Math.sin(angle));
            locations.add(new Location(world, x, center.getY(), z));
        }
        return locations;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("testenchant")) {
            if (sender.isOp()) {
                if (args.length == 1) {
                    Player p = (Player)sender;
                    String enchant = args[0];
                    p.getInventory().addItem(testenchant(enchant));
                }
            }
        }
        if(cmd.getName().equalsIgnoreCase("wave")) {
            Player p = ((Player)sender);
            WaveEffect wave = null;
                wave = new WaveEffect(p.getLocation());
            WaveEffect finalWave = wave;
            Location finalLoc = p.getLocation();
            new BukkitRunnable() {
                    @Override
                    public void run() {
                        finalWave.stop();
                        //for(Block b : getCircle(p.getLocation()))
                    }
                }.runTaskLater(Main.plugin, 20L);
        }
        return false;
    }


    public static ApplicableRegionSet set(Block b) {
        WorldGuardPlugin worldGuard = WorldGuardPlugin.inst();
        RegionManager regionManager = worldGuard.getRegionManager(b.getWorld());
        return regionManager.getApplicableRegions(b.getLocation());
    }


    public static ArrayList<Block> getBlocksAroundCenter(Location loc, int radius) {
        ArrayList<Block> blocks = new ArrayList<>();
        for (int x = loc.getBlockX() - radius; x <= loc.getBlockX() + radius; x++) {
            for (int y = loc.getBlockY() - radius; y <= loc.getBlockY() + radius; y++) {
                for (int z = loc.getBlockZ() - radius; z <= loc.getBlockZ() + radius; z++) {
                    Location l = new Location(loc.getWorld(), x, y, z);
                    if (l.distance(loc) <= radius && !l.getBlock().getType().equals(Material.AIR))
                        blocks.add(l.getBlock());
                }
            }
        }
        return blocks;
    }

    private WorldGuardPlugin getWorldGuard() {
        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }


    public static List<Block> blocksFromTwoPoints(Location loc1, Location loc2, World w) {
        List<Block> blocks = new ArrayList<>();

        int minx = Math.min(loc1.getBlockX(), loc2.getBlockX()),
                miny = Math.min(loc1.getBlockY(), loc2.getBlockY()),
                minz = Math.min(loc1.getBlockZ(), loc2.getBlockZ()),
                maxx = Math.max(loc1.getBlockX(), loc2.getBlockX()),
                maxy = Math.max(loc1.getBlockY(), loc2.getBlockY()),
                maxz = Math.max(loc1.getBlockZ(), loc2.getBlockZ());
        for (int x = minx; x <= maxx; x++) {
            for (int y = miny; y <= maxy; y++) {
                for (int z = minz; z <= maxz; z++) {
                    Block b = w.getBlockAt(x, y, z);
                    if (!b.getType().equals(Material.AIR))
                        blocks.add(b);
                }
            }
        }

        return blocks;
    }
    public void Jackhammer(Player p, Block b, int level) {
        ArrayList<ItemStack> sellblocks = new ArrayList<>();
        int blocks = 0;
        Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
        Location min = new Location(p.getWorld(), m.getMineRegion().getMinPoint().getX(), b.getY(), m.getMineRegion().getMinPoint().getZ());
        Location max = new Location(p.getWorld(), m.getMineRegion().getMaxPoint().getX(), b.getY(), m.getMineRegion().getMaxPoint().getZ());
        for (Block b1 : blocksFromTwoPoints(min, max, p.getWorld())) {
            if (set(b1).allows(DefaultFlag.LIGHTER)) {
                if (b1.getType() != Material.BEDROCK && b1.getType() != Material.AIR) {
                    b1.setType(Material.AIR);
                    blocks = blocks + 1;
                }
            }
        }
        int rank = RankupHandler.getInstance().getRank(p);
        if (settings.getPlayerData().getBoolean(p.getUniqueId().toString() + ".Ethereal")) {
            rank = 1000;
        }
        // ResetHandler.setAIR(min, max, MineHandler.Blocks(rank/16));
        double fortuity = Functions.Foruity(p);
        double skill = SkillsEventsListener.getSkillsBoostFortune(p);
        double event = SkillsEventsListener.getEventFortune();
        int line = 0;
        for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
            if (org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")) {
                line = x;
            }
        }
        int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line)) * fortuity * skill * event /
                (14));

        double levelcap = 1 + (level / 1000);


        sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(), (int) ((blocks / 1.75 * (fortune) * levelcap))));


        int tokens = (int) (KeysHandler.tokensPerBlock(p) * blocks * levelcap);
        Tokens.getInstance().addTokens(p, tokens);
        SellHandler.getInstance().sellEnchant(p, sellblocks, "Jackhammer", tokens);


    }


    public void nukebreak(Player p, Block b, int level) {
        ArrayList<ItemStack> sellblocks = new ArrayList<>();


        int amountblocks = 0;

        Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
        int mined = m.getMineRegion().getBlocksMinedInRegion();
        int total = m.getMineRegion().getTotalBlocksInRegion();
        amountblocks = total - mined;
        int rank = RankupHandler.getInstance().getRank(p);
        if (settings.getPlayerData().getBoolean(p.getUniqueId().toString() + ".Ethereal")) {
            rank = 1000;
        }
        ResetHandler.resetMineWorldEdit(m, m.getMineRegion().getMinPoint(), m.getMineRegion().getMaxPoint(), MineHandler.Blocks(rank / 16));


        double fortuity = Functions.Foruity(p);
        double skill = SkillsEventsListener.getSkillsBoostFortune(p);
        double event = SkillsEventsListener.getEventFortune();
        int line = 0;
        for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
            if (org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")) {
                line = x;
            }
        }
        int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line)) * fortuity * skill * event /
                (14));

        double levelcap = 1 + (level / 500);


        sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(), (int) (((amountblocks / 10) * (fortune * fortuity) * levelcap))));


        int tokens = (int) (KeysHandler.tokensPerBlock(p) * (amountblocks / 6) * levelcap);
        Tokens.getInstance().addTokens(p, tokens);
        SellHandler.getInstance().sellEnchant(p, sellblocks, "Nuke", tokens);

    }

    public void lightning(Player p, Block b, int level) {
        ArrayList<ItemStack> sellblocks = new ArrayList<>();
        int blocks = 0;
        Mine m = ResetHandler.api.getMineByName(p.getUniqueId().toString());
        b.getWorld().strikeLightningEffect(b.getLocation());
        for (Block b1 : getBlocksAroundCenter(b.getLocation(), 7)) {
            if (set(b1).allows(DefaultFlag.LIGHTER)) {
                if (b1.getType() != Material.BEDROCK && b1.getType() != Material.AIR) {
                    b1.setType(Material.AIR);
                    blocks = blocks + 1;
                }
            }
        }
        int rank = RankupHandler.getInstance().getRank(p);
        if (settings.getPlayerData().getBoolean(p.getUniqueId().toString() + ".Ethereal")) {
            rank = 1000;
        }
        // ResetHandler.setAIR(min, max, MineHandler.Blocks(rank/16));
        double fortuity = Functions.Foruity(p);
        double skill = SkillsEventsListener.getSkillsBoostFortune(p);
        double event = SkillsEventsListener.getEventFortune();
        int line = 0;
        for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
            if (org.bukkit.ChatColor.stripColor(p.getItemInHand().getItemMeta().getLore().get(x)).contains("Fortune")) {
                line = x;
            }
        }
        int fortune = (int) (this.getFortune(p.getItemInHand().getItemMeta().getLore().get(line)) * fortuity * skill * event /
                (14));

        double levelcap = 1 + (level / 1000);


        sellblocks.add(new ItemStack(m.getBlockManager().getRandomBlockFromMine().getType(), (int) ((blocks /1.5* (fortune) * levelcap))));


        int tokens = (int) (KeysHandler.tokensPerBlock(p) * blocks * levelcap);
        Tokens.getInstance().addTokens(p, tokens);
        SellHandler.getInstance().sellEnchant(p, sellblocks, "Smite", tokens);
    }



    public void Junkpile(Player p) {
        Random r = new Random();

        int fmin = 1000;
        int fmax = 2500;
        int xp = r.nextInt(fmax - fmin) + fmin;


        double min = 0.1;
        double max = 0.5;
        double multi = Math.round((min + (max - min) * r.nextDouble()) * 10) / 10.0;


        int rr = r.nextInt(100);
        if (rr >= 0 && rr < 40) {

            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Junkpile-Messages") == true) {
                p.sendMessage(c("&f&lJunkpile &8| &b+" + xp + " XP"));
            }

            PickXPHandler.getInstance().addXP(p, xp);
        } else if (rr >= 40 && rr < 75) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "multi add " + p.getName() + " " + multi);
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Junkpile-Messages") == true) {
                p.sendMessage(c("&f&lJunkpile &8| &b+" + multi + " Multi"));
            }
        } else if (rr >= 75 && rr < 77) {
            CMDVoteShop.addCoupon(p, 0.05);
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Junkpile-Messages") == true) {
                p.sendMessage(c("&f&lJunkpile &8| &b+$0.05 Coupon"));
            }
        } else if (rr >= 77) {
            int rint = r.nextInt(4);
            if (rint == 0) {
                p.getInventory().addItem(TrinketHandler.getInstance().commonDust());
            } else if (rint == 1) {
                p.getInventory().addItem(TrinketHandler.getInstance().rareDust());
            } else if (rint == 2) {
                p.getInventory().addItem(TrinketHandler.getInstance().epicDust());
            } else if (rint == 3) {
                p.getInventory().addItem(TrinketHandler.getInstance().legDust());
            } else if (rint == 4) {
                p.getInventory().addItem(TrinketHandler.getInstance().herDust());
            }
        }
    }

    public void KeyParty(Player p) {
        Random r = new Random();
        int i = r.nextInt(8);

        if (i == 0 || i == 1) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (ScoreboardHandler.isAFK(pp)) continue;
                if (this.settings.getOptions().getBoolean(pp.getUniqueId().toString() + ".Key-Party-Messages") == true) {
                    pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d" + p.getName() + " &b+1 &7&lAlpha &7Key!"));
                }
                KeysHandler.getInstance().addKey(pp, "Alpha", 1);
            }
        } else if (i == 2 || i == 3) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (ScoreboardHandler.isAFK(pp)) continue;
                if (this.settings.getOptions().getBoolean(pp.getUniqueId().toString() + ".Key-Party-Messages") == true) {
                    pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d" + p.getName() + " &b+1 &c&lBeta &7Key!"));
                }
                KeysHandler.getInstance().addKey(pp, "Beta", 1);
            }
        } else if (i == 4 || i == 5) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (ScoreboardHandler.isAFK(pp)) continue;
                if (this.settings.getOptions().getBoolean(pp.getUniqueId().toString() + ".Key-Party-Messages") == true) {
                    pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d" + p.getName() + " &b+1 &e&lToken &7Key!"));
                }
                KeysHandler.getInstance().addKey(pp, "Token", 1);
            }
        } else if (i == 6 || i == 7) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (ScoreboardHandler.isAFK(pp)) continue;
                if (this.settings.getOptions().getBoolean(pp.getUniqueId().toString() + ".Key-Party-Messages") == true) {
                    pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d" + p.getName() + " &b+1 &4&lOmega &7Key!"));
                }
                KeysHandler.getInstance().addKey(pp, "Omega", 1);
            }
        } else if (i == 8) {
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (ScoreboardHandler.isAFK(pp)) continue;
                if (this.settings.getOptions().getBoolean(pp.getUniqueId().toString() + ".Key-Party-Messages") == true) {
                    pp.sendMessage(Methods.getInstance().c("&f&lKey Party &8| &bFrom &d" + p.getName() + " &b+1 &c&l&ki&d&lSeasonal&c&l&ki&r &7Key!"));
                }
                KeysHandler.getInstance().addKey(pp, "Seasonal", 1);
            }
        }


    }


    public void Booster(Player p) {
        Random r = new Random();
        int i = r.nextInt(5);

        if (i == 0) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell " + p.getName() + " 2.5 300");
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Booster-Messages") == true) {
                p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2.5x Currency Boost!"));
            }
        } else if (i == 1) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell " + p.getName() + " 2.5 600");
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Booster-Messages") == true) {
                p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 2.5x Currency Boost!"));
            }
        } else if (i == 2) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp " + p.getName() + " 2 300");
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Booster-Messages") == true) {
                p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 2x XP Boost!"));
            }
        } else if (i == 3) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell " + p.getName() + " 3.0 300");
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Booster-Messages") == true) {
                p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+5 minute 3.0x Currency Boost!"));
            }
        } else if (i == 4) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost sell " + p.getName() + " 3.0 600");
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Booster-Messages") == true) {
                p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 3.0x Currency Boost!"));
            }
        } else if (i == 5) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "giveboost xp " + p.getName() + " 2 600");
            if (this.settings.getOptions().getBoolean(p.getUniqueId().toString() + ".Booster-Messages") == true) {
                p.sendMessage(Methods.getInstance().c("&f&lBooster &8| &b+10 minute 2x XP Boost!"));
            }
        }

    }

    public void Treasury(Player p) {
        Random r = new Random();
        int min = 500;
        int max = 2500;
        int gems = r.nextInt(max - min) + min;
        MinePouchHandler.addGems(p, gems);
    }

    public double getEnchantChance(String Enchant, int level, Player p) {
        double lucky = Functions.Karma(p);
        double luck = Functions.luckBoost(p);
        double skill = SkillsEventsListener.getSkillsBoostLuck(p);
        double procChance = 0;
        switch(Enchant) {
            case "Booster":
                double chance = 6300 - (0.65 * level * lucky * luck * skill);
                procChance = (chance < 300) ? 300 : chance;
                break;
            case "Key Party":
                chance = 3800 - (level * lucky * luck * skill);
                procChance = (chance < 400) ? 400 : chance;
                break;
            case "Junkpile":
                chance = 4400 - (0.85 * level * lucky * luck * skill);
                procChance = (chance < 500) ? 500 : chance;
                break;
            case "Nuke":
                chance = 23000 - (20 * level * lucky * luck * skill);
                procChance = (chance < 2000) ? 2000 : chance;
                break;
            case "Wave":
                chance = 1750 - (1.15 * level * lucky * luck * skill);
                procChance = (chance < 300) ? 300 : chance;
                break;
            case "Treasury":
                chance = 3000 - (0.80 * level * lucky * luck * skill);
                procChance = (chance < 600) ? 600 : chance;
                break;
            case "Smite":
                chance = 3000 - (0.80 * level * lucky * luck * skill);
                procChance = (chance < 600) ? 600 : chance;
                break;
        }
        return procChance;
    }

    public void procEnchant(String Enchant, Player p, Block b) {
        Random r = new Random();
        int level = getEnchantLevel(p, Enchant);
        switch(Enchant) {
            case "Booster":
                if(r.nextInt((int) getEnchantChance(Enchant, level, p)) == 1) {
                    Booster(p);
                }
                break;
            case "Key Party":
                if(r.nextInt((int) getEnchantChance(Enchant, level, p)) == 1) {
                    KeyParty(p);
                }
                break;
            case "Junkpile":
                if(r.nextInt((int) getEnchantChance(Enchant, level, p)) == 1) {
                    Junkpile(p);
                }
                break;
            case "Nuke":
                if(r.nextInt((int) getEnchantChance(Enchant, level, p)) == 1) {
                    nukebreak(p, b, level);
                }
                break;
            case "Wave":
                if(r.nextInt((int) getEnchantChance(Enchant, level, p)) == 1) {
                    Jackhammer(p, b, level);
                }
                break;
            case "Smite":
                if(r.nextInt((int) getEnchantChance(Enchant, level, p)) == 1) {
                    lightning(p, b, level);
                }
                break;
        }
    }
    public List<String> Enchants() {
        List<String> enchants = new ArrayList<>();
        enchants.add("Booster");
        enchants.add("Key Party");
        enchants.add("Junkpile");
        enchants.add("Nuke");
        enchants.add("Jackhammer");
        enchants.add("Treasury");
        enchants.add("Smite");
        return enchants;
    }
    public int getEnchantLevel(Player p, String Enchant) {
        int enchantLevel = 0;

        for (int x = 0; x < p.getItemInHand().getItemMeta().getLore().size(); x++) {
            String s = p.getItemInHand().getItemMeta().getLore().get(x);
            if (ChatColor.stripColor(s).contains(Enchant)) {
                enchantLevel = PickaxeLevel.getInstance().getInt(p.getItemInHand().getItemMeta().getLore().get(x));
            }
        }
        return enchantLevel;
    }

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


}
