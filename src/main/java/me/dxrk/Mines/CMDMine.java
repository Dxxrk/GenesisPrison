package me.dxrk.Mines;

import me.dxrk.Events.BuildModeHandler;
import me.dxrk.Events.FishingHandler;
import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.*;
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
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CMDMine implements CommandExecutor, Listener {


    SettingsManager settings = SettingsManager.getInstance();

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


    private ItemStack mineBlockItem(Material mat, String prestige, String rank, String sellprice) {
        ItemStack i = new ItemStack(mat);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(prestige);
        List<String> lore = new ArrayList<>();
        lore.add(rank);
        lore.add(" ");
        lore.add(sellprice);
        im.setLore(lore);
        i.setItemMeta(im);

        return i;
    }

    public ItemStack Head(Player p) {
        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwningPlayer(p);
        skull.setItemMeta(meta);

        return skull;
    }

    public void openMineInventory(Player p) {
        Inventory mineMenu = Bukkit.createInventory(null, 27, c("&a&lYour Mine!"));

        ItemStack teleport = new ItemStack(Material.END_PORTAL_FRAME);
        ItemMeta tm = teleport.getItemMeta();
        tm.setDisplayName(c("&aTeleport to Your Mine (/mine home|tp)"));
        teleport.setItemMeta(tm);
        mineMenu.setItem(4, teleport);

        ItemStack portal = Head(p);
        ItemMeta pm = portal.getItemMeta();
        pm.setDisplayName(c("&aTo Visit another mine use /Mine Visit <name>"));
        portal.setItemMeta(pm);
        mineMenu.setItem(2, portal);

        ItemStack upgrade = new ItemStack(Material.GRASS_BLOCK);
        ItemMeta um = upgrade.getItemMeta();
        um.setDisplayName(c("&aGo into Build Mode"));
        upgrade.setItemMeta(um);
        mineMenu.setItem(6, upgrade);

        ItemStack reset = new ItemStack(Material.REDSTONE_WALL_TORCH);
        ItemMeta rm = reset.getItemMeta();
        rm.setDisplayName(c("&aMine Reset Settings"));
        reset.setItemMeta(rm);
        mineMenu.setItem(21, reset);

        ItemStack block = new ItemStack(Material.DIAMOND_ORE);
        ItemMeta bm = block.getItemMeta();
        bm.setDisplayName(c("&aChange Your Mine Block"));
        block.setItemMeta(bm);
        mineMenu.setItem(23, block);

        p.openInventory(mineMenu);
    }

    public void openResetInventory(Player p, double percent) {
        Inventory reset = Bukkit.createInventory(null, InventoryType.HOPPER, c("&c&lChange Reset Percentage"));

        ItemStack r = new ItemStack(Material.REDSTONE_WALL_TORCH);
        ItemMeta rm = r.getItemMeta();
        rm.setDisplayName(c("&aPercentage"));
        List<String> lore = new ArrayList<>();
        if (percent == 50) {
            lore.add(c("&aReset at 50% Mined"));
        } else {
            lore.add(c("&cReset at 50% Mined"));
        }
        if (percent == 25) {
            lore.add(c("&aReset at 75% Mined"));
        } else {
            lore.add(c("&cReset at 75% Mined"));
        }
        if (percent == 10) {
            lore.add(c("&aReset at 90% Mined"));
        } else {
            lore.add(c("&cReset at 90% Mined"));
        }
        rm.setLore(lore);
        r.setItemMeta(rm);
        reset.setItem(2, r);
        p.openInventory(reset);
    }

    private ItemStack invBlock(Material mat, short data, String rank, String b, Player p) {
        ItemStack block = new ItemStack(mat, 1, data);
        ItemMeta bm = block.getItemMeta();
        bm.setDisplayName(c("&7Choose " + b));
        List<String> lore = new ArrayList<>();
        if (!p.hasPermission("rank." + rank)) {
            lore.add(c("&cYou need " + rank + " to pick this block"));
        }
        bm.setLore(lore);
        block.setItemMeta(bm);
        return block;
    }

    public void openBlockInventory(Player p) {
        Inventory blocks = Bukkit.createInventory(null, 27, c("&3&lChoose a Custom Block"));
        blocks.setItem(0, invBlock(Material.STONE_BRICKS, (short) 0, "sponsor", "Stone Brick", p));
        blocks.setItem(2, invBlock(Material.COAL_BLOCK, (short) 0, "vip", "Coal Block", p));
        blocks.setItem(4, invBlock(Material.NETHERRACK, (short) 0, "mvp", "Netherrack", p));
        blocks.setItem(6, invBlock(Material.PRISMARINE, (short) 2, "hero", "Dark Prismarine", p));
        blocks.setItem(8, invBlock(Material.NETHER_BRICK, (short) 0, "demi-god", "Nether Brick", p));
        blocks.setItem(10, invBlock(Material.WHITE_TERRACOTTA, (short) 0, "titan", "White Stained Clay", p));
        blocks.setItem(12, invBlock(Material.PURPLE_TERRACOTTA, (short) 10, "god", "Purple Stained Clay", p));
        blocks.setItem(14, invBlock(Material.GREEN_TERRACOTTA, (short) 13, "olympian", "Green Stained Clay", p));
        blocks.setItem(16, invBlock(Material.GRAY_TERRACOTTA, (short) 7, "genesis", "Gray Stained Clay", p));
        ItemStack block = new ItemStack(Material.WATER_BUCKET, 1);
        ItemMeta bm = block.getItemMeta();
        bm.setDisplayName(c("&7Choose " + "Water"));
        block.setItemMeta(bm);
        blocks.setItem(22, block);
        p.openInventory(blocks);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ("mine".equalsIgnoreCase(label)) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;
                openMineInventory(p);
            }
            if (args.length == 1) {
                Player p = (Player) sender;
                if ("teleport".equalsIgnoreCase(args[0]) || "tp".equalsIgnoreCase(args[0]) || "home".equalsIgnoreCase(args[0]) || "go".equalsIgnoreCase(args[0])) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("HasMine")) {
                        Mine m = MineSystem.getInstance().getMineByPlayer(p);
                        Location loc = new Location(m.getMineWorld(), m.getSpawnLocation().getX(), m.getSpawnLocation().getY(), m.getSpawnLocation().getZ(), -90, 0);
                        p.teleport(loc);
                        new BukkitRunnable() {
                            @Override
                            public void run() {
                                int minesize = PlayerDataHandler.getInstance().getPlayerData(p).getInt("MineSize");
                                if (minesize == 1 || minesize == 2) {
                                    Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 83, 34.5, m.getSpawnLocation().getZ());
                                }
                                if (minesize == 3) {
                                    Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 103, 39.5, m.getSpawnLocation().getZ());
                                }
                            }
                        }.runTaskLater(Main.plugin, 20L);

                    } else {
                        p.sendMessage(c("&f&lMine &8| &7Unable to find your mine(/mine)."));
                    }
                }
                if("expand".equalsIgnoreCase(args[0])) {
                    Mine m = MineSystem.getInstance().getMineByPlayer(p);
                    m.expandMine(1);
                }
            }
            if (args.length == 2) {
                Player p = (Player) sender;
                if ("visit".equalsIgnoreCase(args[0])) {
                    OfflinePlayer visit = Bukkit.getOfflinePlayer(args[1]);
                    UUID id = visit.getUniqueId();

                    if (PlayerDataHandler.getInstance().getPlayerData(visit.getUniqueId()).getBoolean("HasMine")) {
                        Mine m = MineSystem.getInstance().getMineByName(id.toString());
                        Location loc = new Location(m.getMineWorld(), m.getSpawnLocation().getX(), m.getSpawnLocation().getY(), m.getSpawnLocation().getZ(), -90, 0);
                        p.teleport(loc);
                        int minesize = PlayerDataHandler.getInstance().getPlayerData(visit.getUniqueId()).getInt("MineSize");
                        if (minesize == 1 || minesize == 2) {
                            Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 83, 34.5, m.getSpawnLocation().getZ());
                        }
                        if (minesize == 3) {
                            Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 103, 39.5, m.getSpawnLocation().getZ());
                        }
                    } else {
                        p.sendMessage(c("&f&lMine &8| &7Player has not created their mine."));
                    }

                }
                if("delete".equalsIgnoreCase(args[0])) {
                    if(!p.isOp()) return false;
                    OfflinePlayer delete = Bukkit.getOfflinePlayer(args[1]);
                    UUID id = delete.getUniqueId();
                    if (PlayerDataHandler.getInstance().getPlayerData(delete.getUniqueId()).getBoolean("HasMine")) {
                        Mine m = MineSystem.getInstance().getMineByName(id.toString());
                        m.delete();
                        p.performCommand("spawn");
                    }
                }
            }

        }
        return true;
    }


    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();

        if (e.getClickedInventory() == null)
            return;
        if (e.getView().getTitle() == null)
            return;

        if (e.getView().getTitle().equals(c("&a&lYour Mine!"))) {
            if (e.getClickedInventory().equals(p.getInventory())) return;
            e.setCancelled(true);
            if (e.getSlot() == 4) {
                if (!PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("HasMine")) {
                    MineHandler.getInstance().CreateMine(p, "mineschem", "mines");
                } else {
                    Mine m = MineSystem.getInstance().getMineByPlayer(p);
                    Location loc = new Location(m.getMineWorld(), m.getSpawnLocation().getX(), m.getSpawnLocation().getY(), m.getSpawnLocation().getZ(), -90, 0);
                    p.teleport(loc);
                    Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 119, 0.5, m.getSpawnLocation().getZ());
                }
            }
            if (e.getSlot() == 6) {
                if(!BuildModeHandler.playersinbm.containsKey(p.getUniqueId()))
                    BuildModeHandler.getInstance().BMPutPlayer(p);
                else BuildModeHandler.getInstance().BMRemovePlayer(p);
            }
            if (e.getSlot() == 21) {
                openResetInventory(p, MineSystem.getInstance().getMineByPlayer(p).getResetPercent());
            }
            if (e.getSlot() == 23) {
                openBlockInventory(p);
            }
        }
        if (e.getView().getTitle().equals(c("&3&lChoose a Custom Block"))) {
            if (e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            if (e.getSlot() == 0) {
                if (!p.hasPermission("rank.sponsor")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.STONE_BRICKS));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 2) {
                if (!p.hasPermission("rank.vip")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.COAL_BLOCK));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 4) {
                if (!p.hasPermission("rank.mvp")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.NETHERRACK));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 6) {
                if (!p.hasPermission("rank.hero")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.PRISMARINE, 1, (short) 2));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 8) {
                if (!p.hasPermission("rank.demi-god")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.NETHER_BRICK));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 10) {
                if (!p.hasPermission("rank.titan")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.WHITE_TERRACOTTA, 1, (short) 0));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 12) {
                if (!p.hasPermission("rank.god")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.PURPLE_TERRACOTTA, 1, (short) 10));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 14) {
                if (!p.hasPermission("rank.olympian")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.GREEN_TERRACOTTA, 1, (short) 13));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 16) {
                if (!p.hasPermission("rank.genesis")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.GRAY_TERRACOTTA, 1, (short) 7));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasRod(p)) {
                    FishingHandler.getInstance().saveRod(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getRodSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Pickaxe"));
                }
            }
            if (e.getSlot() == 22) {
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.WATER_BUCKET, 1));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
                if (FishingHandler.getInstance().hasPick(p)) {
                    PlayerDataHandler.getInstance().savePickaxe(p);
                    p.getInventory().setItem(FishingHandler.getInstance().getPickSlot(p), PlayerDataHandler.getInstance().getPlayerData(p).getItemStack("Rod"));
                }
            }
        }
        if (e.getView().getTitle().equals(c("&c&lChange Reset Percentage"))) {
            if (e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            if (e.getSlot() == 2) {
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                double reset = m.getResetPercent();
                if (reset == 50) {
                    m.setResetPercent(25);
                }
                if (reset == 25) {
                    m.setResetPercent(10);
                }
                if (reset == 10) {
                    m.setResetPercent(50);
                }
                m.save();
                openResetInventory(p, m.getResetPercent());
            }
        }


    }


}


