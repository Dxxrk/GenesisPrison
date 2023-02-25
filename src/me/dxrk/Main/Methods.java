package me.dxrk.Main;

import net.ess3.nms.refl.ReflUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntityEnderChest;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Chest;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
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
     * @param plugin The plugin associated with this task
     * @param task The task to run
     * @param hour [0-23] The hour of the day to run the task
     * @return Task id number (-1 if scheduling failed)
     */
    public static int schedule(Plugin plugin, Runnable task, int hour)
    {
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
        if(cal.get(Calendar.HOUR_OF_DAY) >= hour)
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
  
  public ArrayList<OfflinePlayer> beta() {
    ArrayList<OfflinePlayer> b = new ArrayList<>();
    byte b1;
    int i;
    OfflinePlayer[] arrayOfOfflinePlayer;
    for (i = (arrayOfOfflinePlayer = Bukkit.getOfflinePlayers()).length, b1 = 0; b1 < i; ) {
      OfflinePlayer p = arrayOfOfflinePlayer[b1];
      if (settings.getPlayerData().getBoolean(p.getUniqueId().toString() + ".Beta"))
        b.add(p); 
      b1++;
    } 
    return b;
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
  
  
  public int getBlocks(String s) {
      StringBuilder lvl = new StringBuilder();
      s = ChatColor.stripColor(s);
      char[] arrayOfChar = s.toCharArray();
      int i = arrayOfChar.length;
      for (int b = 0; b < i; b = (byte)(b + 1)) {
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
      }
      catch (Exception e1) {
          return false;
      }
  }

  public boolean isInt(char ss) {
      String s = String.valueOf(ss);
      try {
          Integer.parseInt(s);
          return true;
      }
      catch (Exception e1) {
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
  public static double getSellPrice(ItemStack i) {
        int shmulti = 500000*i.getData().getData();
        if(i.getTypeId() == 4)
            return 1000000+shmulti;
        else if(i.getTypeId() == 48)
            return 1500000+shmulti;
        else if(i.getTypeId() == 1)
            return 2000000+shmulti; // 7
        else if(i.getTypeId() == 98)
            return 5500000+shmulti; // 4
        else if(i.getTypeId() == 24)
            return 7500000+shmulti; //3
        else if(i.getTypeId() == 179)
            return 9500000+shmulti; // 3
        else if(i.getTypeId() == 172)
            return 10000000+shmulti;
        else if(i.getTypeId() == 159)
            return 10500000+shmulti; // 16
        else if(i.getTypeId() == 45)
            return 18500000+shmulti;
        else if(i.getTypeId() == 16)
            return 19000000+shmulti;
        else if(i.getTypeId() == 173)
            return 19500000+shmulti;
        else if(i.getTypeId() == 15)
            return 20000000+shmulti;
        else if(i.getTypeId() == 42)
            return 21000000+shmulti;
        else if(i.getTypeId() == 14)
            return 22000000+shmulti;
        else if(i.getTypeId() == 41)
            return 23000000+shmulti;
        else if(i.getTypeId() == 73)
            return 24000000+shmulti;
        else if(i.getTypeId() == 152)
            return 25000000+shmulti;
        else if(i.getTypeId() == 21)
            return 26000000+shmulti;
        else if(i.getTypeId() == 22)
            return 27000000+shmulti;
        else if(i.getTypeId() == 56)
            return 28000000+shmulti;
        else if(i.getTypeId() == 57)
            return 28500000+shmulti;
        else if(i.getTypeId() == 129)
            return 29000000+shmulti;
        else if(i.getTypeId() == 133)
            return 29500000+shmulti;
        else if(i.getTypeId() == 87)
            return 30000000+shmulti;
        else if(i.getTypeId() == 112)
            return 31000000+shmulti;
        else if(i.getTypeId() == 153)
            return 32000000+shmulti;
        else if(i.getTypeId() == 155)
            return 32500000+shmulti; // 3
        else if(i.getTypeId() == 168)
            return 34500000+shmulti; // 3
        else if(i.getTypeId() == 121)
            return 37500000+shmulti;
        else if(i.getTypeId() == 49)
            return 38000000+shmulti;
        return 1;
  }


    public static String formatAmt(double amt) {
    if (amt <= 0.0D)
      return String.valueOf(0);
    if(amt >= 1.0E18D)
    	return String.format("%.1f Quint", amt / 1.0E18D);
    if(amt >= 1.0E15D)
    	return String.format("%.1f Quad", amt / 1.0E15D);
    if (amt >= 1.0E12D)
      return String.format("%.1f Tril", amt / 1.0E12D);
    if (amt >= 1.0E9D)
      return String.format("%.1f Bil", amt / 1.0E9D);
    if (amt >= 1000000.0D)
      return String.format("%.1f Mil", amt / 1000000.0D);
    return NumberFormat.getNumberInstance(Locale.US).format(amt);
  }
}
