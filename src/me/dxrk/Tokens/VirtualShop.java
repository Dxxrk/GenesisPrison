package me.dxrk.Tokens;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.dxrk.Main.SettingsManager;

public class VirtualShop implements Listener {
  Tokens tokens = Tokens.getInstance();
  
  SettingsManager settings = SettingsManager.getInstance();
  
  String prefix = ChatColor.translateAlternateColorCodes('&', "&7&k&li&b&lTokens&7&l&ki&r &7➤ ");
  
  public void prefixMsg(Player p, String s) {
    p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.valueOf(this.prefix) + s));
  }
  
  public int getPrice(String s) {
    StringBuilder lvl = new StringBuilder();
    s = ChatColor.stripColor(s);
    byte b;
    int i;
    char[] arrayOfChar;
    for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
      char c = arrayOfChar[b];
      if (isInt(c))
        lvl.append(c); 
      b++;
    } 
    if (isInt(lvl.toString()))
      return Integer.parseInt(lvl.toString()); 
    return -1;
  }
  
  public void giveItem(Player p, ItemStack ii, int slot) {
    String page = "";
    for (String s : this.settings.getTokenShop().getKeys(false)) {
      if (this.settings.getTokenShop().contains(String.valueOf(s) + "." + slot) && 
        this.settings.getTokenShop().getItemStack(String.valueOf(s) + "." + slot + ".item").equals(ii))
        page = s; 
    } 
    if (this.settings.getTokenShop().contains(String.valueOf(page) + "." + slot + ".command")) {
      for (String command : this.settings.getTokenShop().getStringList(String.valueOf(page) + "." + slot + ".command")) {
        String cmd = command.replace("#p", p.getName());
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), cmd);
      } 
      return;
    } 
    ItemStack i = ii.clone();
    ItemMeta im = i.getItemMeta();
    List<String> lore = new ArrayList<String>();
    if (im.getLore().size() != 1)
      if (im.getLore().size() == 2) {
        lore.add(im.getLore().get(0));
      } else {
        for (int x = 0; x < im.getLore().size() - 1; x++) {
          try {
            lore.add(i.getItemMeta().getLore().get(x));
          } catch (Exception exception) {}
        } 
      }  
    im.setLore(lore);
    i.setItemMeta(im);
    p.getInventory().addItem(new ItemStack[] { i });
  }
  
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
  
  @EventHandler
  public void onClose(InventoryCloseEvent e) {
    if (e.getInventory().getName().contains(ChatColor.AQUA + "Edit Shop:"))
      this.tokens.saveTokenShop(e.getInventory()); 
  }
  
  
  
  @EventHandler
  public void onClick(InventoryClickEvent e) {
    if (e.getClickedInventory() == null)
      return; 
    if (e.getClickedInventory().getName() == null)
      return; 
    if (e.getClickedInventory().getName().contains(ChatColor.AQUA + "Token Shop")) {
    	
    	 if(e.getAction() == InventoryAction.PICKUP_ONE || e.getAction() == InventoryAction.PICKUP_ALL || e.getAction() == InventoryAction.PICKUP_HALF
					||  e.getAction() == InventoryAction.PICKUP_SOME || e.getAction() == InventoryAction.MOVE_TO_OTHER_INVENTORY || e.getAction() == InventoryAction.SWAP_WITH_CURSOR
					|| e.getAction() == InventoryAction.HOTBAR_SWAP || e.getAction() == InventoryAction.UNKNOWN || e.getAction() == InventoryAction.DROP_ALL_CURSOR
					|| e.getAction() == InventoryAction.DROP_ALL_SLOT || e.getAction() == InventoryAction.DROP_ONE_CURSOR || e.getAction() == InventoryAction.DROP_ONE_SLOT
					|| e.getAction() == InventoryAction.PLACE_ALL || e.getAction() == InventoryAction.PLACE_ONE || e.getAction() == InventoryAction.PLACE_SOME
					|| e.getAction() == InventoryAction.NOTHING || e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD || e.getAction() == InventoryAction.CLONE_STACK
					|| e.getAction() == InventoryAction.COLLECT_TO_CURSOR || e.getClick() == ClickType.SHIFT_LEFT || e.getClick() == ClickType.SHIFT_RIGHT){
				  e.setCancelled(true);
			  }
    	 if(e.getView().getTopInventory().getName().contains(ChatColor.AQUA + "Token Shop") && e.getClick().isShiftClick()){

    	        e.setCancelled(true);

    	}
    	
      Player p = (Player)e.getWhoClicked();
      if (e.getSlot() == 45 || e.getSlot() == 53) {
        e.setCancelled(true);
        int page = Integer.parseInt(ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0)).replace(" ", ""));
        try {
          p.openInventory((Tokens.getInstance()).pages.get(page));
          return;
        } catch (Exception e1) {
          p.sendMessage(ChatColor.RED + "Page not found");
          return;
        } 
      }
      e.setCancelled(true);
      if (e.getCurrentItem() == null)
        return; 
      if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE)
        return; 
      ItemStack current = e.getCurrentItem();
      int price = getPrice(e.getCurrentItem().getItemMeta().getLore().get(current.getItemMeta().getLore().size() - 1));
      if (this.tokens.getTokens(p) < price) {
        prefixMsg(p, "&cYou do not have enough tokens");
        return;
      } 
      this.tokens.takeTokens(p, price);
      prefixMsg(p, "Purchase sucessful!");
      giveItem(p, current, e.getSlot());
    } 
  }
}
