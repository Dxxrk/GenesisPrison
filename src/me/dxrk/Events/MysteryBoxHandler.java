package me.dxrk.Events;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.EnderChest;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class MysteryBoxHandler implements Listener, CommandExecutor {

    static Methods m = Methods.getInstance();

    public static HashMap<Player, List<Location>> placed = new HashMap<>();

    public static MysteryBoxHandler instance = new MysteryBoxHandler();

    public static MysteryBoxHandler getInstance() {
        return instance;
    }


    public void spawnItem(ItemStack i, Location loc) {
        Item item = loc.getWorld().dropItem(loc, i);
        final ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
        stand.setPassenger(item);
    }

    @EventHandler
    public void armorstand(PlayerArmorStandManipulateEvent e) {
        if (!e.getPlayer().isOp())
            e.setCancelled(true);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (label.equalsIgnoreCase("givecrate")) {
            if (!sender.isOp()) return false;
            if (args.length == 2) {
                Player p = Bukkit.getPlayer(args[0]);
                if (args[1].equalsIgnoreCase("genesis")) {
                    p.getInventory().addItem(CrateFunctions.GenesisCrate());
                    p.updateInventory();
                }
                if (args[1].equalsIgnoreCase("contraband")) {
                    p.getInventory().addItem(CrateFunctions.ContrabandCrate());
                    p.updateInventory();
                }
                if (args[1].equalsIgnoreCase("april")) {
                    p.getInventory().addItem(CrateFunctions.AprilCrate());
                    p.updateInventory();
                }
                if (args[1].equalsIgnoreCase("fishing")) {
                    p.getInventory().addItem(CrateFunctions.FishingCrate());
                    p.updateInventory();
                }
            }
        }
        if (label.equalsIgnoreCase("givexp")) {
            if (args.length == 2) {
                Player p = Bukkit.getPlayer(args[0]);
                double xp = Double.parseDouble(args[1]);
                PickXPHandler.getInstance().addXP(p, xp);
            }
            if (args.length == 3) {
                Random r = new Random();
                Player p = Bukkit.getPlayer(args[0]);
                int xp1 = Integer.parseInt(args[1]);
                int xp2 = Integer.parseInt(args[2]);
                int xp = r.nextInt(xp2 - xp1) + xp1;

                PickXPHandler.getInstance().addXP(p, xp);
            }
        }

        return false;
    }


    @EventHandler
    public void onPlace(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (!e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if (p.getItemInHand() == null) return;
        if (!p.getItemInHand().hasItemMeta()) return;
        if (!p.getItemInHand().getItemMeta().hasDisplayName()) return;
        if (!p.getItemInHand().getItemMeta().hasLore()) return;

        Location loc = e.getClickedBlock().getLocation();
        List<ArmorStand> stands = new ArrayList<>();


        if (p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&f&l&k[&7&l*&f&l&k]&r &9&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r")) && p.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
            e.setCancelled(true);
            if (!p.getWorld().getBlockAt(e.getClickedBlock().getLocation().clone().add(0, 3, 0)).getType().equals(Material.AIR))
                return;
            if (placed.get(p) == null) {
                List<Location> place = new ArrayList<>();
                place.add(loc);
                placed.put(p, place);
            } else {
                List<Location> place = placed.get(p);
                if (place.contains(loc)) {
                    return;
                } else {
                    placed.get(p).add(loc);
                }
            }

            doChestAnimation(p, e.getClickedBlock().getLocation(), "genesis");
            if (p.getItemInHand().getAmount() > 1) {
                int i = p.getItemInHand().getAmount();
                p.getItemInHand().setAmount(i - 1);
            } else {
                p.setItemInHand(null);
            }
            p.updateInventory();
            return;
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&f&l• &c&lC&6&lo&e&ln&a&lt&3&lr&9&la&5&lb&c&la&6&ln&e&ld &3&lC&9&lr&5&la&c&lt&6&le &f&l•")) && p.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
            e.setCancelled(true);
            if (!p.getWorld().getBlockAt(e.getClickedBlock().getLocation().clone().add(0, 3, 0)).getType().equals(Material.AIR))
                return;
            if (placed.get(p) == null) {
                List<Location> place = new ArrayList<>();
                place.add(loc);
                placed.put(p, place);
            } else {
                List<Location> place = placed.get(p);
                if (place.contains(loc)) {
                    return;
                } else {
                    placed.get(p).add(loc);
                }
            }

            doChestAnimation(p, e.getClickedBlock().getLocation(), "contraband");
            if (p.getItemInHand().getAmount() > 1) {
                int i = p.getItemInHand().getAmount();
                p.getItemInHand().setAmount(i - 1);
            } else {
                p.setItemInHand(null);
            }
            p.updateInventory();
            return;
        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&f&l&k[&7&l*&f&l&k]&r &b&lApril Crate &f&l&k[&7&l*&f&l&k]&r")) && p.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
            e.setCancelled(true);
            if (!p.getWorld().getBlockAt(e.getClickedBlock().getLocation().clone().add(0, 3, 0)).getType().equals(Material.AIR))
                return;
            if (placed.get(p) == null) {
                List<Location> place = new ArrayList<>();
                place.add(loc);
                placed.put(p, place);
            } else {
                List<Location> place = placed.get(p);
                if (place.contains(loc)) {
                    return;
                } else {
                    placed.get(p).add(loc);
                }
            }
            doChestAnimation(p, e.getClickedBlock().getLocation(), "april");
            if (p.getItemInHand().getAmount() > 1) {
                int i = p.getItemInHand().getAmount();
                p.getItemInHand().setAmount(i - 1);
            } else {
                p.setItemInHand(null);
            }
            p.updateInventory();

        }
        if (p.getItemInHand().getItemMeta().getDisplayName().equals(m.c("&b&l💧 Fishing &f&lCrate &b&l💧")) && p.getItemInHand().getType().equals(Material.ENDER_CHEST)) {
            e.setCancelled(true);
            if (!p.getWorld().getBlockAt(e.getClickedBlock().getLocation().clone().add(0, 3, 0)).getType().equals(Material.AIR))
                return;
            if (placed.get(p) == null) {
                List<Location> place = new ArrayList<>();
                place.add(loc);
                placed.put(p, place);
            } else {
                List<Location> place = placed.get(p);
                if (place.contains(loc)) {
                    return;
                } else {
                    placed.get(p).add(loc);
                }
            }
            doChestAnimation(p, e.getClickedBlock().getLocation(), "fishing");
            if (p.getItemInHand().getAmount() > 1) {
                int i = p.getItemInHand().getAmount();
                p.getItemInHand().setAmount(i - 1);
            } else {
                p.setItemInHand(null);
            }
            p.updateInventory();

        }

    }

    @EventHandler
    public void onInt(PlayerArmorStandManipulateEvent e) {
        e.setCancelled(true);
    }

    public String getRarity() {
        String rarity = "";
        Random r = new Random();
        int ri = r.nextInt(100);
        if (ri <= 60) {
            rarity = "Common";
        }
        if (ri > 60 && ri <= 90) {
            rarity = "Rare";
        }
        if (ri > 90) {
            rarity = "Epic";
        }

        return rarity;
    }

    @SuppressWarnings("deprecation")
    public void startAnimation(Player p, String crate, Location loc) {
        Location block = loc.clone();
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        BlockPosition bp = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());
        PacketPlayOutBlockChange change = new PacketPlayOutBlockChange(s, bp);
        if (Yaw.getYaw(p).equals(Yaw.NORTH)) {
            change.block = CraftMagicNumbers.getBlock(Material.ENDER_CHEST).fromLegacyData(new EnderChest(BlockFace.SOUTH).getData());
        }
        if (Yaw.getYaw(p).equals(Yaw.EAST)) {
            change.block = CraftMagicNumbers.getBlock(Material.ENDER_CHEST).fromLegacyData(new EnderChest(BlockFace.WEST).getData());
        }
        if (Yaw.getYaw(p).equals(Yaw.SOUTH)) {
            change.block = CraftMagicNumbers.getBlock(Material.ENDER_CHEST).fromLegacyData(new EnderChest(BlockFace.NORTH).getData());
        }
        if (Yaw.getYaw(p).equals(Yaw.WEST)) {
            change.block = CraftMagicNumbers.getBlock(Material.ENDER_CHEST).fromLegacyData(new EnderChest(BlockFace.EAST).getData());
        }

        PacketPlayOutTileEntityData data = new PacketPlayOutTileEntityData();
        PacketPlayOutBlockAction open = new PacketPlayOutBlockAction(bp, Blocks.ENDER_CHEST, 1, 1);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(change);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(open);

        p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 2f);
        new BukkitRunnable() {
            @Override
            public void run() {
                //block.getWorld().getBlockAt(block).setType(Material.AIR);
                PacketPlayOutBlockChange change = new PacketPlayOutBlockChange(s, bp);
                change.block = net.minecraft.server.v1_8_R3.Block.getByCombinedId(Material.AIR.getId());
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(change);
            }
        }.runTaskLater(Main.plugin, 227);

        if (Yaw.getYaw(p).equals(Yaw.WEST)) {
            //DO Z CHANGE
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 5, 63, 114, 40, "Legendary");//187
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 15, 55, 92, 60, getRarity());//167
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 25, 47, 80, 70, getRarity());//157
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 35, 39, 68, 80, getRarity());//147
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 45, 31, 56, 90, getRarity());//137
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 55, 23, 44, 100, getRarity());//127
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 65, 15, 32, 110, getRarity());//117
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "east", 75, 7, 20, 120, getRarity()); //replaces at 107 ticks
        }
        if (Yaw.getYaw(p).equals(Yaw.EAST)) {
            //DO Z CHANGE
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 5, 63, 114, 40, "Legendary");//187
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 15, 55, 92, 60, getRarity());//167
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 25, 47, 80, 70, getRarity());//157
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 35, 39, 68, 80, getRarity());//147
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 45, 31, 56, 90, getRarity());//137
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 55, 23, 44, 100, getRarity());//127
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 65, 15, 32, 110, getRarity());//117
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "west", 75, 7, 20, 120, getRarity()); //replaces at 107 ticks
        }
        if (Yaw.getYaw(p).equals(Yaw.NORTH)) {
            //DO X CHANGE
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 5, 63, 114, 40, "Legendary");//222
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 15, 55, 92, 60, getRarity());//202
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 25, 47, 80, 70, getRarity());//192
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 35, 39, 68, 80, getRarity());//182
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 45, 31, 56, 90, getRarity());//172
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 55, 23, 44, 100, getRarity());//162
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 65, 15, 32, 110, getRarity());//152
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "south", 75, 7, 20, 120, getRarity()); //replaces at 142 ticks
        }
        if (Yaw.getYaw(p).equals(Yaw.SOUTH)) {
            //DO X CHANGE
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 5, 63, 114, 40, "Legendary");//222
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 15, 55, 92, 60, getRarity());//202
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 25, 47, 80, 70, getRarity());//192
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 35, 39, 68, 80, getRarity());//182
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 45, 31, 56, 90, getRarity());//172
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 55, 23, 44, 100, getRarity());//162
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 65, 15, 32, 110, getRarity());//152
            MysteryBoxHandler.getInstance().rotateAnimation(p, crate, loc, "north", 75, 7, 20, 120, getRarity()); //replaces at 142 ticks
        }
    }

    public void doChestAnimation(Player p, Location loc, String crate) {
        new BukkitRunnable() {
            @Override
            public void run() {
                placed.get(p).remove(loc);
            }
        }.runTaskLater(Main.plugin, 240);   //REPLACE WITH PACKET STAND
        Location center = loc.clone().add(0.5, 0.85, 0.5);
        HeadDatabaseAPI api = new HeadDatabaseAPI();
        ItemStack item = api.getItemHead("1491");
        WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
        EntityArmorStand stand = new EntityArmorStand(s, center.getX(), center.getY(), center.getZ());
        stand.setGravity(false);
        stand.setInvisible(true);
        stand.setSmall(true);
        PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
        PacketPlayOutEntityEquipment EquipP = new PacketPlayOutEntityEquipment(stand.getId(), 4, CraftItemStack.asNMSCopy(item));

        PacketPlayOutEntityMetadata metadatap = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnP);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(EquipP);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(metadatap);
        new BukkitRunnable() {
            int up = 0;
            float rotate = 0f;

            @Override
            public void run() {
                if (up == 25) {
                    PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId());
                    ((CraftPlayer) p).getHandle().playerConnection.sendPacket(destroy);
                    startAnimation(p, crate, loc.clone().add(0, 3, 0));
                    cancel();
                }
                double y = (double) up / 10;
                Location l = new Location(loc.getWorld(), center.getX(), center.getY(), center.getZ(), rotate, 0);
                stand.setLocation(center.getX(), center.getY() + y, center.getZ(), l.getYaw(), 0);
                Vector3f v = new Vector3f(0, rotate, 0);
                stand.setHeadPose(v);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(stand));
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true));

                up++;
                rotate += 6f;
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public void removeRewards(Player p, EntityArmorStand stand, ItemStack reward, EntityItem item) {
        stand.getBukkitEntity().setPassenger(null);
        PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId(), item.getId());
        PacketPlayOutEntityDestroy destroy2 = new PacketPlayOutEntityDestroy(item.getId());
        PacketPlayOutEntityMetadata metadatap = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(metadatap);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(destroy);
        if (reward.getItemMeta().hasLore()) {
            String s = reward.getItemMeta().getLore().get(0).replace("%PLAYER%", p.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
        } else {
            p.getInventory().addItem(reward);
            p.updateInventory();
        }

    }

    public void replaceReward(Player p, String crate, EntityArmorStand stand, int remove, String rarity) {
        Location place = stand.getBukkitEntity().getLocation().clone();
        //Location place = stand.getEyeLocation().clone().add(0, -0.3, 0);
        ItemStack reward = CrateFunctions.Reward(crate, rarity).clone();
        //Item item = place.getWorld().dropItem(place, reward);           //REPLACE WITH NORMAL STAND OR FIND A WAY TO PUT ITEM ON STAND
        WorldServer s = ((CraftWorld) place.getWorld()).getHandle();
        EntityItem item = new EntityItem(s, place.getX(), place.getY(), place.getZ(), CraftItemStack.asNMSCopy(reward));
        item.pickupDelay = 6000;
        stand.setCustomName(reward.getItemMeta().getDisplayName());
        Location loc = place.clone().add(0, -0.5, 0);
        stand.setLocation(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        PacketPlayOutEntityEquipment EquipP = new PacketPlayOutEntityEquipment(stand.getId(), 4, null);
        PacketPlayOutEntityMetadata metadatap = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntity(item, 2, 100));
        PacketPlayOutAttachEntity attach = new PacketPlayOutAttachEntity(0, item, stand);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityMetadata(item.getId(), item.getDataWatcher(), true));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(EquipP);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(metadatap);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(stand));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(attach);


        //DIFFERENT SOUNDS FOR RARITY
        if (rarity.equals("Common"))
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 2f);
        if (rarity.equals("Rare"))
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.5f);
        if (rarity.equals("Epic"))
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
        if (rarity.equals("Legendary"))
            p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
        new BukkitRunnable() {
            @Override
            public void run() {
                removeRewards(p, stand, reward, item);
            }
        }.runTaskLater(Main.plugin, remove);
    }

    public void animation(Player p, String crate, Location center, EntityArmorStand stand, String dir, int end, int replace, int remove, String rarity) {
        new BukkitRunnable() {

            float angle = 0f;
            final double RADIUS = 1.3;
            int stop = 0;

            @Override
            public void run() {
                if (stop == end) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            replaceReward(p, crate, stand, remove, rarity);
                        }
                    }.runTaskLater(Main.plugin, replace);
                    cancel();
                }
                //double angle = a * Math.PI / 180;
                double z = RADIUS * Math.sin(angle);
                double x = RADIUS * Math.sin(angle);
                double y = RADIUS * Math.cos(angle);

                angle += 0.1;
                switch (dir) {
                    case "west": {
                        //stand.teleport(center.clone().add(x, y, 0));
                        Location l = center.clone().add(0, y, -z);
                        stand.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                    case "east": {                        //CHANGE THESE TO ENTITY TELEPORT PACKETS
                        //stand.teleport(center.clone().add(0, y, z));
                        Location l = center.clone().add(0, y, z);
                        stand.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                    case "north": {                        //CHANGE THESE TO ENTITY TELEPORT PACKETS
                        //stand.teleport(center.clone().add(0, y, z));
                        Location l = center.clone().add(x, y, 0);
                        stand.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                    case "south": {                        //CHANGE THESE TO ENTITY TELEPORT PACKETS
                        //stand.teleport(center.clone().add(0, y, z));
                        Location l = center.clone().add(-x, y, 0);
                        stand.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                }
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(stand));

                stop++;
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public void rotateAnimation(Player p, String crate, Location loc, String dir, int time, int end, int replace, int remove, String rarity) {
        Location center = loc.clone().add(0.5, -0.4, 0.5);
        new BukkitRunnable() {
            @Override
            public void run() {
                HeadDatabaseAPI api = new HeadDatabaseAPI();
                ItemStack item = api.getItemHead("30503");
                if (rarity.equals("Common"))
                    item = api.getItemHead("30507");
                if (rarity.equals("Rare"))
                    item = api.getItemHead("30506");
                if (rarity.equals("Epic"))
                    item = api.getItemHead("30505");
                if (rarity.equals("Legendary"))
                    item = api.getItemHead("28582");
                WorldServer s = ((CraftWorld) loc.getWorld()).getHandle();
                EntityArmorStand stand = new EntityArmorStand(s, center.getX(), center.getY() - 0.2, center.getZ());
                stand.setCustomName(m.c("&e&l&kOOOOOO"));
                stand.setCustomNameVisible(true);
                stand.setGravity(false);
                stand.setInvisible(true);
                stand.setSmall(true);

                PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
                PacketPlayOutEntityEquipment EquipP = new PacketPlayOutEntityEquipment(stand.getId(), 4, CraftItemStack.asNMSCopy(item));

                PacketPlayOutEntityMetadata metadatap = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);

                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(spawnP);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(EquipP);
                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(metadatap);
                new BukkitRunnable() {
                    int up = 0;

                    @Override
                    public void run() {
                        if (up == 5) {
                            animation(p, crate, center, stand, dir, end, replace, remove, rarity);
                            cancel();
                        }
                        double y = (double) up / 10;
                        //stand.teleport(center.clone().add(0, y, 0));
                        Location l = center.clone().add(0, y, 0);
                        stand.setLocation(l.getX(), l.getY(), l.getZ(), 0, 0);
                        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutEntityTeleport(stand));
                        up++;
                    }
                }.runTaskTimer(Main.plugin, 0, 1);
            }
        }.runTaskLater(Main.plugin, time);
    }

    public enum Yaw {
        NORTH, SOUTH, EAST, WEST;

        public static Yaw getYaw(Player p) {
            float yaw = p.getLocation().getYaw();
            yaw = (yaw % 360 + 360) % 360; // true modulo, as javas modulo is weird for negative values
            if (yaw > 225 && yaw < 315) {
                return Yaw.EAST;
            } else if (yaw > 135 && yaw < 225) {
                return Yaw.NORTH;
            } else if (yaw > 45 && yaw < 135) {
                return Yaw.WEST;
            } else {
                return Yaw.SOUTH;
            }
        }
    }


}
