package me.dxrk.Main;

import java.lang.reflect.Method;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;

import org.bukkit.Bukkit;
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

public class Methods {
  public static Methods instance = new Methods();
  
  public static SettingsManager settings = SettingsManager.getInstance();
  
  public static Methods getInstance() {
    return instance;
  }
  public String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
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
