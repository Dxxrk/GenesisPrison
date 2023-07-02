package me.dxrk.Main;

import net.ess3.nms.refl.ReflUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.logging.Level;

public class Methods {
    public static Methods instance = new Methods();

    public static SettingsManager settings = SettingsManager.getInstance();

    public static Methods getInstance() {
        return instance;
    }

    public String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }


    public void playChestAction(Chest chest, boolean open) {
        Location location = chest.getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());
        TileEntityEnderChest tileChest = (TileEntityEnderChest) world.getTileEntity(position);
        world.playBlockAction(position, tileChest.w(), 1, open ? 1 : 0);
    }

    //@SuppressWarnings("deprecation")
    public void changeChestState(Location loc, boolean open) {
        int data = (open) ? 1 : 0;

        ((CraftWorld) loc.getWorld()).getHandle().playBlockAction(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), CraftMagicNumbers.getBlock(loc.getWorld().getBlockAt(loc)), 1, data);
    }


    /**
     * Schedules a task to run at a certain hour every day.
     *
     * @param plugin The plugin associated with this task
     * @param task   The task to run
     * @param hour   [0-23] The hour of the day to run the task
     * @return Task id number (-1 if scheduling failed)
     */
    public static int schedule(Plugin plugin, Runnable task, int hour) {
        //Calendar is a class that represents a certain time and date.
        Calendar cal = Calendar.getInstance(); //obtains a calendar instance that represents the current time and date

        //time is often represented in milliseconds since the epoch,
        //as a long, which represents how many milliseconds a time is after
        //January 1st, 1970, 00:00.

        //this gets the current time
        long now = cal.getTimeInMillis();
        //you could also say "long now = System.currentTimeMillis()"

        //since we have saved the current time, we need to figure out
        //how many milliseconds are between that and the next
        //time it is 7:00pm, or whatever was passed into hour
        //we do this by setting this calendar instance to the next 7:00pm (or whatever)
        //then we can compare the times

        //if it is already after 7:00pm,
        //we will schedule it for tomorrow,
        //since we can't schedule it for the past.
        //we are not time travelers.
        if (cal.get(Calendar.HOUR_OF_DAY) >= hour)
            cal.add(Calendar.DATE, 1); //do it tomorrow if now is after "hours"

        //we need to set this calendar instance to 7:00pm, or whatever.
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        //cal is now properly set to the next time it will be 7:00pm

        long offset = cal.getTimeInMillis() - now;
        long ticks = offset / 50L; //there are 50 milliseconds in a tick

        //we now know how many ticks are between now and the next time it is 7:00pm
        //we schedule an event to go off the next time it is 7:00pm,
        //and repeat every 24 hours.
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, ticks, 1728000L);
        //24 hrs/day * 60 mins/hr * 60 secs/min * 20 ticks/sec = 1728000 ticks
    }


    public String convertItemStackToJson(ItemStack itemStack) {
        Object itemAsJsonObject;
        Class<?> craftItemStackClazz = ReflUtil.getOBCClass("inventory.CraftItemStack");
        Method asNMSCopyMethod = ReflUtil.getMethodCached(craftItemStackClazz, "asNMSCopy", ItemStack.class);
        Class<?> nmsItemStackClazz = ReflUtil.getNMSClass("ItemStack");
        Class<?> nbtTagCompoundClazz = ReflUtil.getNMSClass("NBTTagCompound");
        Method saveNmsItemStackMethod = ReflUtil.getMethodCached(nmsItemStackClazz, "save", nbtTagCompoundClazz);
        try {
            Object nmsNbtTagCompoundObj = nbtTagCompoundClazz.newInstance();
            Object nmsItemStackObj = asNMSCopyMethod.invoke(null, itemStack);
            itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, nmsNbtTagCompoundObj);
        } catch (Throwable t) {
            Bukkit.getLogger().log(Level.SEVERE, "failed to serialize itemstack to nms item", t);
            return null;
        }
        return itemAsJsonObject.toString();
    }


    public String convertItemStackToJsonRegular(ItemStack itemStack) {
        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
        NBTTagCompound compound = new NBTTagCompound();
        nmsItemStack.save(compound);
        return compound.toString();
    }

    public TextComponent itemHover(String chat, ItemStack item) {
        TextComponent test = new TextComponent(ChatColor.translateAlternateColorCodes('&', chat));
        BaseComponent[] hoverEventComponents = {new TextComponent(convertItemStackToJson(new ItemStack(Material.DIAMOND, 1)))};
        test.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents));
        return test;
    }

    public void createWorldBorder(Player p, org.bukkit.World world, double size, double x, double z) {
        WorldBorder wb = new WorldBorder();
        wb.world = ((CraftWorld) world).getHandle();
        wb.setCenter(x, z);
        wb.setSize(size);
        wb.setWarningDistance(1);
        wb.setWarningTime(1);
        PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(wb, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packetPlayOutWorldBorder);
    }


    public int getBlocks(String s) {
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


    public boolean isInteger(String s, int radix) {
        if (s.isEmpty())
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1)
                    return false;
            } else if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isInteger(String s) {
        return isInteger(s, 10);
    }


    @SuppressWarnings("deprecation")
    public static double getSellPrice(int rank) {
        double block = rank / 1.6;
        if (block < 1)
            block = 1;
        return block * 500000;
    }
}
