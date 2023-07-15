package me.dxrk.Events;

import me.dxrk.Main.Methods;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.UUID;

public class BuildModeHandler implements Listener, CommandExecutor {

    Methods m = Methods.getInstance();

    HashMap<UUID, ItemStack[]> playersinbm = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(command.getName().equalsIgnoreCase("buildmode")){
                if(!playersinbm.containsKey(p.getUniqueId())){
                    BMPutPlayer(p);
                }else{
                    BMRemovePlayer(p);
                }
            }
        }
        return true;
    }

    public void BMPutPlayer(Player p){
        playersinbm.put(p.getUniqueId(), p.getInventory().getContents());
        p.getInventory().clear();
        p.setGameMode(GameMode.CREATIVE);
    }

    public void BMRemovePlayer(Player p){
        p.getInventory().setContents(playersinbm.get(p.getUniqueId()));
        playersinbm.remove(p.getUniqueId());
        p.setGameMode(GameMode.SURVIVAL);
        p.setAllowFlight(true);
    }

    private boolean isRedstoneItem(Player p, Material material) {
        if(p.isOp())
            return false;
        p.sendMessage(material.name());
        return material.equals(Material.REDSTONE) ||
                material.equals(Material.REDSTONE_BLOCK) ||
                material.equals(Material.DIODE) ||
                material.equals(Material.REDSTONE_COMPARATOR) ||
                material.equals(Material.DISPENSER)  ||
                material.equals(Material.DROPPER)  ||
                material.equals(Material.PISTON_BASE)  ||
                material.equals(Material.PISTON_STICKY_BASE)  ||
                material.equals(Material.TNT);
    }

    public void removeRedstoneItems(Player p){
        ItemStack[] inventoryContents = p.getInventory().getContents();
        for (int i = 0; i < inventoryContents.length; i++) {
            if (inventoryContents[i] != null && isRedstoneItem(p, inventoryContents[i].getType())) {
                p.getInventory().setItem(i, null);
            }
        }
        p.updateInventory();
    }

    @EventHandler
    public void onCreativeInventoryTake(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if (p.getGameMode().equals(GameMode.CREATIVE)) {
            if (e.getAction() == InventoryAction.HOTBAR_SWAP ||
                    e.getAction() == InventoryAction.HOTBAR_MOVE_AND_READD) {
                ItemStack currentItem = e.getCurrentItem();
                if (currentItem != null && isRedstoneItem(p, currentItem.getType())) {
                    e.setCancelled(true);
                    removeRedstoneItems(p);
                    p.sendMessage(m.c("&c&lYou can't use redstone items."));
                }
            } else if (e.getAction() == InventoryAction.PLACE_ALL) {
                ItemStack cursorItem = e.getCursor();
                if (cursorItem != null && isRedstoneItem(p, cursorItem.getType())) {
                    e.setCancelled(true);
                    removeRedstoneItems(p);
                    p.sendMessage(m.c("&c&lYou can't use redstone items."));
                }
            }
        }
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent event) {
        Player p = (Player) event.getWhoClicked();
        if (p.getGameMode().equals(GameMode.CREATIVE)) {
            for (ItemStack draggedItem : event.getNewItems().values()) {
                if (draggedItem != null && isRedstoneItem(p, draggedItem.getType())) {
                    event.setCancelled(true);
                    removeRedstoneItems(p);
                    p.sendMessage(m.c("&c&lYou can't use redstone items."));
                    break;
                }
            }
        }
    }
}
