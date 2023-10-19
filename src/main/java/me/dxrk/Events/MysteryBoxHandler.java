package me.dxrk.Events;


import com.mojang.datafixers.util.Pair;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Rotations;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.*;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_20_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_20_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_20_R2.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_20_R2.util.CraftMagicNumbers;
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
import org.joml.Vector3f;

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

        if ("givecrate".equalsIgnoreCase(label)) {
            if (!sender.isOp()) return false;
            if (args.length == 2) {
                Player p = Bukkit.getPlayer(args[0]);
                if ("genesis".equalsIgnoreCase(args[1])) {
                    p.getInventory().addItem(CrateFunctions.GenesisCrate());
                    p.updateInventory();
                }
                if ("contraband".equalsIgnoreCase(args[1])) {
                    p.getInventory().addItem(CrateFunctions.ContrabandCrate());
                    p.updateInventory();
                }
                if ("april".equalsIgnoreCase(args[1])) {
                    p.getInventory().addItem(CrateFunctions.AprilCrate());
                    p.updateInventory();
                }
                if ("fishing".equalsIgnoreCase(args[1])) {
                    p.getInventory().addItem(CrateFunctions.FishingCrate());
                    p.updateInventory();
                }
            }
        }
        if ("givexp".equalsIgnoreCase(label)) {
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
        if (p.getEquipment().getItemInMainHand() == null) return;
        if (!p.getEquipment().getItemInMainHand().hasItemMeta()) return;
        if (!p.getEquipment().getItemInMainHand().getItemMeta().hasDisplayName()) return;
        if (!p.getEquipment().getItemInMainHand().getItemMeta().hasLore()) return;

        Location loc = e.getClickedBlock().getLocation();
        List<ArmorStand> stands = new ArrayList<>();


        if (p.getEquipment().getItemInMainHand().getItemMeta().getDisplayName().equals(m.c("&f&l&k[&7&l*&f&l&k]&r &c&lGenesis &b&lCrate &f&l&k[&7&l*&f&l&k]&r")) && p.getEquipment().getItemInMainHand().getType().equals(Material.ENDER_CHEST)) {
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
            if (p.getEquipment().getItemInMainHand().getAmount() > 1) {
                int i = p.getEquipment().getItemInMainHand().getAmount();
                p.getEquipment().getItemInMainHand().setAmount(i - 1);
            } else {
                p.setItemInHand(null);
            }
            p.updateInventory();
            return;
        }
        if (p.getEquipment().getItemInMainHand().getItemMeta().getDisplayName().equals(m.c("&f&l• &c&lC&6&lo&e&ln&a&lt&3&lr&9&la&5&lb&c&la&6&ln&e&ld &3&lC&9&lr&5&la&c&lt&6&le &f&l•")) && p.getEquipment().getItemInMainHand().getType().equals(Material.ENDER_CHEST)) {
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
            if (p.getEquipment().getItemInMainHand().getAmount() > 1) {
                int i = p.getEquipment().getItemInMainHand().getAmount();
                p.getEquipment().getItemInMainHand().setAmount(i - 1);
            } else {
                p.setItemInHand(null);
            }
            p.updateInventory();
            return;
        }
        if (p.getEquipment().getItemInMainHand().getItemMeta().getDisplayName().equals(m.c("&f&l&k[&7&l*&f&l&k]&r &b&lApril Crate &f&l&k[&7&l*&f&l&k]&r")) && p.getEquipment().getItemInMainHand().getType().equals(Material.ENDER_CHEST)) {
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
            if (p.getEquipment().getItemInMainHand().getAmount() > 1) {
                int i = p.getEquipment().getItemInMainHand().getAmount();
                p.getEquipment().getItemInMainHand().setAmount(i - 1);
            } else {
                p.setItemInHand(null);
            }
            p.updateInventory();
            return;
        }
        if (p.getEquipment().getItemInMainHand().getItemMeta().getDisplayName().equals(m.c("&b&l💧 Fishing &f&lCrate &b&l💧")) && p.getEquipment().getItemInMainHand().getType().equals(Material.ENDER_CHEST)) {
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
            if (p.getEquipment().getItemInMainHand().getAmount() > 1) {
                int i = p.getEquipment().getItemInMainHand().getAmount();
                p.getEquipment().getItemInMainHand().setAmount(i - 1);
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
        //BlockGetter s = ((CraftWorld) loc.getWorld()).getHandle();
        BlockPos bp = new BlockPos(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        //PacketPlayOutBlockChange change = new PacketPlayOutBlockChange(s, bp);

        ClientboundBlockUpdatePacket update = new ClientboundBlockUpdatePacket(bp, CraftMagicNumbers.getBlock(Material.ENDER_CHEST, (new EnderChest(BlockFace.NORTH)).getData()));
        if (Yaw.getYaw(p).equals(Yaw.NORTH)) {
             update = new ClientboundBlockUpdatePacket(bp, CraftMagicNumbers.getBlock(Material.ENDER_CHEST, (new EnderChest(BlockFace.SOUTH)).getData()));
        }
        if (Yaw.getYaw(p).equals(Yaw.EAST)) {
            update = new ClientboundBlockUpdatePacket(bp, CraftMagicNumbers.getBlock(Material.ENDER_CHEST, (new EnderChest(BlockFace.WEST)).getData()));
        }
        if (Yaw.getYaw(p).equals(Yaw.SOUTH)) {
            update = new ClientboundBlockUpdatePacket(bp, CraftMagicNumbers.getBlock(Material.ENDER_CHEST, (new EnderChest(BlockFace.NORTH)).getData()));
        }
        if (Yaw.getYaw(p).equals(Yaw.WEST)) {
            update = new ClientboundBlockUpdatePacket(bp, CraftMagicNumbers.getBlock(Material.ENDER_CHEST, (new EnderChest(BlockFace.EAST)).getData()));
        }

        ClientboundBlockEventPacket event = new ClientboundBlockEventPacket(bp, Blocks.ENDER_CHEST, 1, 1);
        //PacketPlayOutBlockAction open = new PacketPlayOutBlockAction(bp, Blocks.ENDER_CHEST, 1, 1);
        //p.sendBlockChange(block, );
        ((CraftPlayer) p).getHandle().connection.send(update);
        ((CraftPlayer) p).getHandle().connection.send(event);

        p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 2f);
        new BukkitRunnable() {
            @Override
            public void run() {
                //block.getWorld().getBlockAt(block).setType(Material.AIR);
                ClientboundBlockUpdatePacket update = new ClientboundBlockUpdatePacket(bp, CraftMagicNumbers.getBlock(Material.AIR).defaultBlockState());
                ((CraftPlayer) p).getHandle().connection.send(update);
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


        ItemStack item = m.getSkull("http://textures.minecraft.net/texture/374ee1542c4563fd6e7d72de26e737cf18fbd04ccab1b8b28353da87348ecfb");
        BlockGetter s = ((CraftWorld) loc.getWorld()).getHandle();
        net.minecraft.world.entity.decoration.ArmorStand stand = new net.minecraft.world.entity.decoration.ArmorStand(((CraftWorld) loc.getWorld()).getHandle(), center.getX(), center.getY(), center.getZ());
        stand.setNoGravity(true);
        stand.setInvisible(true);
        stand.setSmall(true);
        //stand.setItemSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(item));
        //PacketPlayOutSpawnEntityLiving spawnP = new PacketPlayOutSpawnEntityLiving(stand);
        ClientboundAddEntityPacket spawn = new ClientboundAddEntityPacket(stand);
        //PacketPlayOutEntityEquipment EquipP = new PacketPlayOutEntityEquipment(stand.getId(), 4, CraftItemStack.asNMSCopy(item));
        List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(item)));
        ClientboundSetEquipmentPacket equip = new ClientboundSetEquipmentPacket(stand.getId(), list);

        //PacketPlayOutEntityMetadata metadatap = new PacketPlayOutEntityMetadata(stand.getId(), stand.getDataWatcher(), true);
        //ClientboundSetEntityDataPacket data = new ClientboundSetEntityDataPacket()

        ((CraftPlayer) p).getHandle().connection.send(spawn);
        ((CraftPlayer) p).getHandle().connection.send(equip);
        //((CraftPlayer) p).getHandle().playerConnection.sendPacket(EquipP);
        //((CraftPlayer) p).getHandle().playerConnection.sendPacket(metadatap);
        new BukkitRunnable() {
            int up = 0;
            float rotate = 0f;

            @Override
            public void run() {
                if (up == 25) {
                    //PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId());
                    ClientboundRemoveEntitiesPacket remove = new ClientboundRemoveEntitiesPacket(stand.getId());
                    ((CraftPlayer) p).getHandle().connection.send(remove);
                    startAnimation(p, crate, loc.clone().add(0, 3, 0));
                    cancel();
                }
                double y = (double) up / 10;
                Location l = new Location(loc.getWorld(), center.getX(), center.getY(), center.getZ(), rotate, 0);
                //stand.setLocation(center.getX(), center.getY() + y, center.getZ(), l.getYaw(), 0);
                stand.moveTo(center.getX(), center.getY() + y, center.getZ(), l.getYaw(), 0);
                //Vector3f v = new Vector3f(0, rotate, 0);
                Rotations angle = new Rotations(0, rotate, 0);
                stand.setHeadPose(angle);

                ((CraftPlayer) p).getHandle().connection.send(new ClientboundTeleportEntityPacket(stand));
                ((CraftPlayer) p).getHandle().connection.send(new ClientboundSetEntityDataPacket(stand.getId(), stand.getEntityData().packDirty()));

                up++;
                rotate += 6f;
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public void removeRewards(Player p, net.minecraft.world.entity.decoration.ArmorStand stand, ItemStack reward, ItemEntity item) {
        stand.getBukkitEntity().setPassenger(null);
        //PacketPlayOutEntityDestroy destroy = new PacketPlayOutEntityDestroy(stand.getId(), item.getId());
        //ClientboundSetEntityDataPacket metadatap = new ClientboundSetEntityDataPacket(stand.getId(), stand.getEntityData().packDirty());
        ClientboundRemoveEntitiesPacket remove = new ClientboundRemoveEntitiesPacket(stand.getId(), item.getId());
        //((CraftPlayer) p).getHandle().connection.send(metadatap);
        ((CraftPlayer) p).getHandle().connection.send(remove);
        if (reward.getItemMeta().hasLore()) {
            String s = reward.getItemMeta().getLore().get(0).replace("%PLAYER%", p.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
        } else {
            p.getInventory().addItem(reward);
            p.updateInventory();
        }

    }

    public void replaceReward(Player p, String crate, net.minecraft.world.entity.decoration.ArmorStand stand, int remove, String rarity) {
        Location place = stand.getBukkitEntity().getLocation().clone();
        //Location place = stand.getEyeLocation().clone().add(0, -0.3, 0);
        ItemStack reward = CrateFunctions.Reward(crate, rarity).clone();
        //Item item = place.getWorld().dropItem(place, reward);           //REPLACE WITH NORMAL STAND OR FIND A WAY TO PUT ITEM ON STAND
        ServerLevel s = ((CraftWorld) place.getWorld()).getHandle();
        ItemEntity item = new ItemEntity(s, place.getX(), place.getY(), place.getZ(), CraftItemStack.asNMSCopy(reward));
        item.pickupDelay = 6000;
        item.setNoGravity(true);

        stand.setCustomName(Component.literal(reward.getItemMeta().getDisplayName()));
        Location loc = place.clone().add(0, -0.5, 0);
        stand.moveTo(loc.getX(), loc.getY(), loc.getZ(), 0, 0);
        stand.setItemSlot(EquipmentSlot.HEAD, net.minecraft.world.item.ItemStack.fromBukkitCopy(new ItemStack(Material.AIR)));
        //PacketPlayOutEntityEquipment EquipP = new PacketPlayOutEntityEquipment(stand.getId(), 4, null);
        List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
        list.add(new Pair<>(EquipmentSlot.HEAD, net.minecraft.world.item.ItemStack.fromBukkitCopy(new ItemStack(Material.AIR))));
        ClientboundSetEquipmentPacket equip = new ClientboundSetEquipmentPacket(stand.getId(), list);

        ClientboundSetEntityDataPacket metadatap = new ClientboundSetEntityDataPacket(stand.getId(), stand.getEntityData().packDirty());

        ClientboundAddEntityPacket addItem = new ClientboundAddEntityPacket(item);
        //((CraftPlayer) p).getHandle().playerConnection.sendPacket(new PacketPlayOutSpawnEntity(item, 2, 100));
        ((CraftPlayer)p).getHandle().connection.send(addItem);
        //PacketPlayOutAttachEntity attach = new PacketPlayOutAttachEntity(0, item, stand);
        ClientboundSetEntityLinkPacket link = new ClientboundSetEntityLinkPacket(item, stand);
        ((CraftPlayer) p).getHandle().connection.send(new ClientboundSetEntityDataPacket(item.getId(), item.getEntityData().packDirty()));
        ((CraftPlayer) p).getHandle().connection.send(equip);
        //((CraftPlayer) p).getHandle().playerConnection.sendPacket(EquipP);
        ((CraftPlayer) p).getHandle().connection.send(metadatap);
        ((CraftPlayer) p).getHandle().connection.send(new ClientboundTeleportEntityPacket(stand));
        ((CraftPlayer) p).getHandle().connection.send(link);


        //DIFFERENT SOUNDS FOR RARITY
        if ("Common".equals(rarity))
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 2f);
        if ("Rare".equals(rarity))
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.5f);
        if ("Epic".equals(rarity))
            p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        if ("Legendary".equals(rarity))
            p.playSound(p.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1.0f, 1.0f);
        new BukkitRunnable() {
            @Override
            public void run() {
                removeRewards(p, stand, reward, item);
            }
        }.runTaskLater(Main.plugin, remove);
    }

    public void animation(Player p, String crate, Location center, net.minecraft.world.entity.decoration.ArmorStand stand, String dir, int end, int replace, int remove, String rarity) {
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
                        stand.moveTo(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                    case "east": {                        //CHANGE THESE TO ENTITY TELEPORT PACKETS
                        //stand.teleport(center.clone().add(0, y, z));
                        Location l = center.clone().add(0, y, z);
                        stand.moveTo(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                    case "north": {                        //CHANGE THESE TO ENTITY TELEPORT PACKETS
                        //stand.teleport(center.clone().add(0, y, z));
                        Location l = center.clone().add(x, y, 0);
                        stand.moveTo(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                    case "south": {                        //CHANGE THESE TO ENTITY TELEPORT PACKETS
                        //stand.teleport(center.clone().add(0, y, z));
                        Location l = center.clone().add(-x, y, 0);
                        stand.moveTo(l.getX(), l.getY(), l.getZ(), 0, 0);
                        break;
                    }
                }
                ((CraftPlayer) p).getHandle().connection.send(new ClientboundTeleportEntityPacket(stand));

                stop++;
            }
        }.runTaskTimer(Main.plugin, 0, 1);
    }

    public void rotateAnimation(Player p, String crate, Location loc, String dir, int time, int end, int replace, int remove, String rarity) {
        Location center = loc.clone().add(0.5, -0.4, 0.5);
        new BukkitRunnable() {
            @Override
            public void run() {

                ItemStack item = m.getSkull("http://textures.minecraft.net/texture/43d04dba51f892495834ff71a429a8a91015a5a786b856ffe9c024cdb52fbc8f");
                if (rarity.equals("Common"))
                    item = m.getSkull("http://textures.minecraft.net/texture/24343987b786964da60f55106a435ba53cb78ef00b10669433712afe4b6fb546");
                if (rarity.equals("Rare"))
                    item = m.getSkull("http://textures.minecraft.net/texture/be002d97723b8cc9802d30fe8e4cef361e56cf2e49ae91f274da72f478134118");
                if (rarity.equals("Epic"))
                    item = m.getSkull("http://textures.minecraft.net/texture/106ea104cb9be703cced1b1f565286752e271752c5ac85e8113b3e2dc4352c20");
                if (rarity.equals("Legendary"))
                    item = m.getSkull("http://textures.minecraft.net/texture/33f2cd9f81a2772bdc4864472e833362310485c8a6bc0b76b81703390a9b032e");
                ServerLevel s = ((CraftWorld) loc.getWorld()).getHandle();
                net.minecraft.world.entity.decoration.ArmorStand stand = new net.minecraft.world.entity.decoration.ArmorStand(s, center.getX(), center.getY() - 0.2, center.getZ());
                stand.setCustomName(Component.literal(m.c("&e&l&kOOOOOO")));
                stand.setCustomNameVisible(true);
                stand.setNoGravity(true);
                stand.setInvisible(true);
                stand.setSmall(true);
                stand.setItemSlot(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(item));
                ClientboundAddEntityPacket spawnP = new ClientboundAddEntityPacket(stand);
                //PacketPlayOutEntityEquipment EquipP = new PacketPlayOutEntityEquipment(stand.getId(), 4, CraftItemStack.asNMSCopy(item));
                List<Pair<EquipmentSlot, net.minecraft.world.item.ItemStack>> list = new ArrayList<>();
                list.add(new Pair<>(EquipmentSlot.HEAD, CraftItemStack.asNMSCopy(item)));
                ClientboundSetEquipmentPacket equip = new ClientboundSetEquipmentPacket(stand.getId(), list);

                ClientboundSetEntityDataPacket metadatap = new ClientboundSetEntityDataPacket(stand.getId(), stand.getEntityData().packDirty());

                ((CraftPlayer) p).getHandle().connection.send(spawnP);
                ((CraftPlayer) p).getHandle().connection.send(equip);
                //((CraftPlayer) p).getHandle().connection.send(EquipP);
                ((CraftPlayer) p).getHandle().connection.send(metadatap);
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
                        stand.moveTo(l.getX(), l.getY(), l.getZ(), 0, 0);
                        ((CraftPlayer) p).getHandle().connection.send(new ClientboundTeleportEntityPacket(stand));
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
