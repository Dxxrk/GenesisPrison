package me.dxrk.Main;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.logging.Level;

import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.TileEntityChest;
import net.minecraft.server.v1_8_R3.World;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.inventory.ItemStack;



import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;

import net.ess3.nms.refl.ReflUtil;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.plugin.Plugin;

public class Methods {
  public static Methods instance = new Methods();
  
  public static SettingsManager settings = SettingsManager.getInstance();
  
  public static Methods getInstance() {
    return instance;
  }
  public String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }


    public void playChestAction(Chest chest, boolean open, Player p) {
        Location location = chest.getLocation();
        World world = ((CraftWorld) location.getWorld()).getHandle();
        BlockPosition position = new BlockPosition(location.getX(), location.getY(), location.getZ());

        double rotation = p.getLocation().getYaw() - 180;
        if (rotation < 0) {
            rotation += 360.0;
        }
        if (0 <= rotation && rotation < 90.5) {
            position.south();

        }
        if (90.5 <= rotation && rotation < 180.5) {
            position.west();
        }
        if (180.5 <= rotation && rotation < 270.5) {
            position.north();
        }
        if (270.5 <= rotation && rotation <= 360) {
            position.east();
        }
        TileEntityChest tileChest = (TileEntityChest) world.getTileEntity(position);
        world.playBlockAction(position, tileChest.w(), 1, open ? 1 : 0);
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
    Method asNMSCopyMethod = ReflUtil.getMethodCached(craftItemStackClazz, "asNMSCopy", new Class[] { ItemStack.class });
    Class<?> nmsItemStackClazz = ReflUtil.getNMSClass("ItemStack");
    Class<?> nbtTagCompoundClazz = ReflUtil.getNMSClass("NBTTagCompound");
    Method saveNmsItemStackMethod = ReflUtil.getMethodCached(nmsItemStackClazz, "save", new Class[] { nbtTagCompoundClazz });
    try {
      Object nmsNbtTagCompoundObj = nbtTagCompoundClazz.newInstance();
      Object nmsItemStackObj = asNMSCopyMethod.invoke(null, new Object[] { itemStack });
      itemAsJsonObject = saveNmsItemStackMethod.invoke(nmsItemStackObj, new Object[] { nmsNbtTagCompoundObj });
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
      if (settings.getPlayerData().getBoolean(String.valueOf(p.getUniqueId().toString()) + ".Beta"))
        b.add(p); 
      b1++;
    } 
    return b;
  }
  
  public String convertItemStackToJsonRegular(ItemStack itemStack) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound compound = new NBTTagCompound();
    compound = nmsItemStack.save(compound);
    return compound.toString();
  }
  
  public TextComponent itemHover(String chat, ItemStack item) {
    TextComponent test = new TextComponent(ChatColor.translateAlternateColorCodes('&', chat));
    BaseComponent[] hoverEventComponents = { (BaseComponent)new TextComponent(convertItemStackToJson(new ItemStack(Material.DIAMOND, 1))) };
    test.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, hoverEventComponents));
    return test;
  }
  
  
  public int getBlocks(String s) {
      StringBuilder lvl = new StringBuilder();
      s = ChatColor.stripColor((String)s);
      char[] arrayOfChar = s.toCharArray();
      int i = arrayOfChar.length;
      for (int b = 0; b < i; b = (int)((byte)(b + 1))) {
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
  
  public double round(double value, int places) {
    if (places < 0)
      throw new IllegalArgumentException(); 
    long factor = (long)Math.pow(10.0D, places);
    value *= factor;
    long tmp = Math.round(value);
    return tmp / factor;
  }
  
  public static double getBlockSellPrice(String rank, int block) {
    FileConfiguration f = settings.getSellPrices();
    if (f.getInt(String.valueOf(rank) + "." + block) < 1)
      return -1.0D;
    
    return f.getInt(String.valueOf(rank) + "." + block);
  }
  
  public static String getSellRank(Player p) {
    return "A";
    
  }
  
  public String format(double amt) {
    if (amt >= 1.0E12D)
      return String.format("%.1f Tril", new Object[] { Double.valueOf(amt / 1.0E12D) }); 
    if (amt >= 1.0E9D)
      return String.format("%.1f Bil", new Object[] { Double.valueOf(amt / 1.0E9D) }); 
    if (amt >= 1000000.0D)
      return String.format("%.1f Mil", new Object[] { Double.valueOf(amt / 1000000.0D) }); 
    return NumberFormat.getNumberInstance(Locale.US).format(amt);
  }
  
  public static String formatAmt(double amt) {
    if (amt <= 0.0D)
      return String.valueOf(0);
    if(amt >= 1.0E18D)
    	return String.format("%.1f Quint", new Object[] { Double.valueOf(amt / 1.0E18D) }); 
    if(amt >= 1.0E15D)
    	return String.format("%.1f Quad", new Object[] { Double.valueOf(amt / 1.0E15D) }); 
    if (amt >= 1.0E12D)
      return String.format("%.1f Tril", new Object[] { Double.valueOf(amt / 1.0E12D) }); 
    if (amt >= 1.0E9D)
      return String.format("%.1f Bil", new Object[] { Double.valueOf(amt / 1.0E9D) }); 
    if (amt >= 1000000.0D)
      return String.format("%.1f Mil", new Object[] { Double.valueOf(amt / 1000000.0D) }); 
    return NumberFormat.getNumberInstance(Locale.US).format(amt);
  }
}
