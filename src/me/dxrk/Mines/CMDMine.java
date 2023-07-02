package me.dxrk.Mines;

import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldBorder;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

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
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(p.getName());
        skull.setItemMeta(meta);

        return skull;
    }

    public void openMineInventory(Player p) {
        Inventory mineMenu = Bukkit.createInventory(null, 27, c("&a&lYour Mine!"));

        ItemStack teleport = new ItemStack(Material.ENDER_PORTAL_FRAME);
        ItemMeta tm = teleport.getItemMeta();
        tm.setDisplayName(c("&aTeleport to Your Mine (/mine home|tp)"));
        teleport.setItemMeta(tm);
        mineMenu.setItem(4, teleport);

        ItemStack portal = Head(p);
        ItemMeta pm = portal.getItemMeta();
        pm.setDisplayName(c("&aTo Visit another mine use /Mine Visit <name>"));
        portal.setItemMeta(pm);
        mineMenu.setItem(2, portal);

        ItemStack upgrade = new ItemStack(Material.DIAMOND);
        ItemMeta um = upgrade.getItemMeta();
        um.setDisplayName(c("&aUpgrade Your Mine"));
        upgrade.setItemMeta(um);
        mineMenu.setItem(6, upgrade);

        ItemStack reset = new ItemStack(Material.REDSTONE_TORCH_ON);
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

        ItemStack r = new ItemStack(Material.REDSTONE_TORCH_ON);
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
        blocks.setItem(0, invBlock(Material.SMOOTH_BRICK, (short) 0, "sponsor", "Stone Brick", p));
        blocks.setItem(2, invBlock(Material.COAL_BLOCK, (short) 0, "vip", "Coal Block", p));
        blocks.setItem(4, invBlock(Material.NETHERRACK, (short) 0, "mvp", "Netherrack", p));
        blocks.setItem(6, invBlock(Material.PRISMARINE, (short) 2, "hero", "Dark Prismarine", p));
        blocks.setItem(8, invBlock(Material.NETHER_BRICK, (short) 0, "demi-god", "Nether Brick", p));
        blocks.setItem(10, invBlock(Material.STAINED_CLAY, (short) 0, "titan", "White Stained Clay", p));
        blocks.setItem(12, invBlock(Material.STAINED_CLAY, (short) 10, "god", "Purple Stained Clay", p));
        blocks.setItem(14, invBlock(Material.STAINED_CLAY, (short) 13, "olympian", "Green Stained Clay", p));
        blocks.setItem(16, invBlock(Material.STAINED_CLAY, (short) 7, "genesis", "Gray Stained Clay", p));
        p.openInventory(blocks);
    }

    public void openUpgradeInventory(Player p) {
        Inventory upgrade = Bukkit.createInventory(null, 27, c("&a&lUpgrade Your Mine"));

        ItemStack lucky1 = new ItemStack(Material.SEA_LANTERN);
        if (PlayerDataHandler.getInstance().getPlayerData(p).getDouble("LuckyBlock") >= 1) {
            lucky1.setType(Material.BARRIER);
        }
        ItemMeta l1 = lucky1.getItemMeta();
        l1.setDisplayName(c("&aLucky Block Level 1"));
        List<String> lore = new ArrayList<>();
        lore.add(c("&7Lucky Blocks will randomly spawn in your mine"));
        lore.add(c("&aCost: &e⛀2.0 Million"));
        l1.setLore(lore);
        lucky1.setItemMeta(l1);
        upgrade.setItem(0, lucky1);
        lore.clear();

        ItemStack lucky2 = new ItemStack(Material.SEA_LANTERN);
        if (PlayerDataHandler.getInstance().getPlayerData(p).getDouble("LuckyBlock") >= 3) {
            lucky2.setType(Material.BARRIER);
        }
        ItemMeta l2 = lucky2.getItemMeta();
        l2.setDisplayName(c("&aLucky Block Level 2"));
        lore.add(c("&7Increases Lucky Block Spawns"));
        lore.add(c("&aCost: &e⛀100.0 Million"));
        l2.setLore(lore);
        lucky2.setItemMeta(l2);
        upgrade.setItem(4, lucky2);
        lore.clear();

        ItemStack lucky3 = new ItemStack(Material.SEA_LANTERN);
        if (PlayerDataHandler.getInstance().getPlayerData(p).getDouble("LuckyBlock") >= 8) {
            lucky3.setType(Material.BARRIER);
        }
        ItemMeta l3 = lucky3.getItemMeta();
        l3.setDisplayName(c("&aLucky Block Level 3"));
        lore.add(c("&7Increases Lucky Block Spawns"));
        lore.add(c("&aCost: &e⛀500.0 Million"));
        l3.setLore(lore);
        lucky3.setItemMeta(l3);
        upgrade.setItem(8, lucky3);
        lore.clear();

        ItemStack size1 = new ItemStack(Material.DIAMOND_ORE);
        if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("MineSize") >= 2) {
            size1.setType(Material.BARRIER);
        }
        ItemMeta s1 = size1.getItemMeta();
        s1.setDisplayName(c("&aMine Size Level 2"));
        lore.add(c("&7Increases Mine to 50x50x50"));
        lore.add(c("&aCost: &e⛀25.0 Million"));
        s1.setLore(lore);
        size1.setItemMeta(s1);
        upgrade.setItem(2, size1);
        lore.clear();

        ItemStack size2 = new ItemStack(Material.DIAMOND_ORE);
        if (PlayerDataHandler.getInstance().getPlayerData(p).getInt("MineSize") >= 3) {
            size2.setType(Material.BARRIER);
        }
        ItemMeta s2 = size2.getItemMeta();
        s2.setDisplayName(c("&aMine Size Level 2"));
        lore.add(c("&7Increases Mine to 65x65x65"));
        lore.add(c("&aCost: &e⛀250.0 Million"));
        s2.setLore(lore);
        size2.setItemMeta(s2);
        upgrade.setItem(6, size2);
        lore.clear();

        p.openInventory(upgrade);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("mine")) {
            if (args.length == 0) {
                if (!(sender instanceof Player)) return false;
                Player p = (Player) sender;
                openMineInventory(p);
            }
            if (args.length == 1) {
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("teleport") || args[0].equalsIgnoreCase("tp") || args[0].equalsIgnoreCase("home") || args[0].equalsIgnoreCase("go")) {
                    if (PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("HasMine")) {
                        Mine m = MineSystem.getInstance().getMineByPlayer(p);
                        Location loc = new Location(m.getMineWorld(), m.getSpawnLocation().getX(), m.getSpawnLocation().getY(), m.getSpawnLocation().getZ(), -90, 0);
                        p.teleport(loc);
                        int minesize = PlayerDataHandler.getInstance().getPlayerData(p).getInt("MineSize");
                        if(minesize == 1 || minesize == 2) {
                            Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 83, 34.5, m.getSpawnLocation().getZ());
                        }
                        if(minesize == 3) {
                            Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 103, 39.5, m.getSpawnLocation().getZ());
                        }
                    } else {
                        p.sendMessage(c("&f&lMine &8| &7Unable to find your mine(/mine)."));
                    }
                }
                if (args[0].equalsIgnoreCase("upgrade")) {
                    openUpgradeInventory(p);
                }
            }
            if (args.length == 2) {
                Player p = (Player) sender;
                if (args[0].equalsIgnoreCase("visit")) {
                    OfflinePlayer visit = Bukkit.getOfflinePlayer(args[1]);
                    UUID id = visit.getUniqueId();

                    if (PlayerDataHandler.getInstance().getPlayerData(visit.getUniqueId()).getBoolean("HasMine")) {
                        Mine m = MineSystem.getInstance().getMineByName(id.toString());
                        Location loc = new Location(m.getMineWorld(), m.getSpawnLocation().getX(), m.getSpawnLocation().getY(), m.getSpawnLocation().getZ(), -90, 0);
                        p.teleport(loc);
                        int minesize = PlayerDataHandler.getInstance().getPlayerData(visit.getUniqueId()).getInt("MineSize");
                        if(minesize == 1 || minesize == 2) {
                            Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 83, 34.5, m.getSpawnLocation().getZ());
                        }
                        if(minesize == 3) {
                            Methods.getInstance().createWorldBorder(p, m.getMineWorld(), 103, 39.5, m.getSpawnLocation().getZ());
                        }
                    } else {
                        p.sendMessage(c("&f&lMine &8| &7Player has not created their mine."));
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
        if (e.getClickedInventory().getName() == null)
            return;

        if (e.getInventory().getName().equals(c("&a&lYour Mine!"))) {
            if (e.getClickedInventory().equals(p.getInventory())) return;
            e.setCancelled(true);
            if (e.getSlot() == 4) {
                if (!PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("HasMine")) {
                    MineHandler.getInstance().CreateMine(p, "firstmine", "mines");
                } else {
                    Mine m = MineSystem.getInstance().getMineByPlayer(p);
                    Location loc = new Location(m.getMineWorld(), m.getSpawnLocation().getX(), m.getSpawnLocation().getY(), m.getSpawnLocation().getZ(), -90, 0);
                    p.teleport(loc);
                    net.minecraft.server.v1_8_R3.WorldBorder wb = new net.minecraft.server.v1_8_R3.WorldBorder();
                    wb.world = ((CraftWorld) m.getMineWorld()).getHandle();
                    wb.setCenter(34.5, m.getSpawnLocation().getZ());
                    wb.setSize(83);
                    wb.setWarningDistance(1);
                    wb.setWarningTime(1);
                    PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(wb, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
                }
            }
            if (e.getSlot() == 6) {
                openUpgradeInventory(p);
            }
            if (e.getSlot() == 21) {
                openResetInventory(p, MineSystem.getInstance().getMineByPlayer(p).getResetPercent());
            }
            if (e.getSlot() == 23) {
                openBlockInventory(p);
            }
        }
        if (e.getInventory().getName().equals(c("&3&lChoose a Custom Block"))) {
            if (e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            if (e.getSlot() == 0) {
                if (!p.hasPermission("rank.sponsor")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.SMOOTH_BRICK));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 2) {
                if (!p.hasPermission("rank.vip")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.COAL_BLOCK));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 4) {
                if (!p.hasPermission("rank.mvp")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.NETHERRACK));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 6) {
                if (!p.hasPermission("rank.hero")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.PRISMARINE, 1, (short) 2));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 8) {
                if (!p.hasPermission("rank.demi-god")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.NETHER_BRICK));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 10) {
                if (!p.hasPermission("rank.titan")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.STAINED_CLAY, 1, (short) 0));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 12) {
                if (!p.hasPermission("rank.god")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.STAINED_CLAY, 1, (short) 10));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 14) {
                if (!p.hasPermission("rank.olympian")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.STAINED_CLAY, 1, (short) 13));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
            if (e.getSlot() == 16) {
                if (!p.hasPermission("rank.genesis")) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("CustomBlock", new ItemStack(Material.STAINED_CLAY, 1, (short) 7));
                MineHandler.getInstance().updateMine(p, RankupHandler.getInstance().getRank(p));
            }
        }
        if (e.getInventory().getName().equals(c("&c&lChange Reset Percentage"))) {
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
        if (e.getInventory().getName().equals(c("&a&lUpgrade Your Mine"))) {
            if (e.getClickedInventory().equals(p.getInventory())) {
                e.setCancelled(true);
                return;
            }
            e.setCancelled(true);
            if (e.getCurrentItem().getType().equals(Material.BARRIER)) {
                e.setCancelled(true);
                return;
            }
            if (e.getSlot() == 0) {
                if (Tokens.getInstance().getTokens(p) < 2E6) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("LuckyBlock", 1);
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                m.reset();
                Tokens.getInstance().takeTokens(p, 2e6);
                openUpgradeInventory(p);
            }
            if (e.getSlot() == 4) {
                if (Tokens.getInstance().getTokens(p) < 100E6) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("LuckyBlock", 3);
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                m.reset();
                Tokens.getInstance().takeTokens(p, 100e6);
                openUpgradeInventory(p);
            }
            if (e.getSlot() == 8) {
                if (Tokens.getInstance().getTokens(p) < 500E6) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("LuckyBlock", 8);
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                m.reset();
                Tokens.getInstance().takeTokens(p, 500e6);
                openUpgradeInventory(p);
            }
            if (e.getSlot() == 2) {
                if (Tokens.getInstance().getTokens(p) < 10E6) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("MineSize", 2);
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                MineSystem.getInstance().removeActiveMine(m);
                m.delete();
                MineHandler.getInstance().CreateMine(p, "secondmine", "minestwo");
                Tokens.getInstance().takeTokens(p, 25e6);
                m.reset();
                openUpgradeInventory(p);
            }
            if (e.getSlot() == 6) {
                if (Tokens.getInstance().getTokens(p) < 250E6) return;
                PlayerDataHandler.getInstance().getPlayerData(p).set("MineSize", 3);
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                MineSystem.getInstance().removeActiveMine(m);
                m.delete();
                MineHandler.getInstance().CreateMine(p, "thirdmine", "minesthree");
                m.reset();
                Tokens.getInstance().takeTokens(p, 250e6);
                openUpgradeInventory(p);
            }

        }


    }


}


