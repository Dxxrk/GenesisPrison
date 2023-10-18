package me.dxrk.Events;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.utils.WrathEffect;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EventFlareHandler implements Listener, CommandExecutor {

    Methods m = Methods.getInstance();

    private boolean wrathActive = false;
    WrathEffect wrath;
    public void checkForEvent() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if(activeEvents.contains(Event.Wrath)) {
                    if(!wrathActive) {
                        wrath = new WrathEffect();
                    }
                }
            }
        }.runTaskTimer(Main.plugin, 0, 20L);
    }



    public static List<Event> activeEvents = new ArrayList<>();

    public void activateEvent(Player p, Event event) {
        if(activeEvents.size() >=2) {
            p.sendMessage(m.colorText("placeholder", "gray", TextDecoration.BOLD, false)); // cancel taking the item and sending broadcast
            return;
        }
        if(activeEvents.contains(event)) {
            p.sendMessage(m.colorText("placeholder", "gray", TextDecoration.BOLD, false)); // cancel taking the item and sending broadcast
            return;
        }
        Bukkit.broadcast(m.colorText("placeholder", "gray", TextDecoration.BOLD, false)); // broadcast the active message
        activeEvents.add(event);
        new BukkitRunnable() {
            @Override
            public void run() {
                removeEvent(p, event);
            }
        }.runTaskLater(Main.plugin, (15*60)*20L);
    }

    public void removeEvent(Player p, Event event) {
        activeEvents.remove(event);
        if(event.equals(Event.Wrath)) {
            wrath.stop();
        }
        Bukkit.broadcast(m.colorText("placeholder", "gray", TextDecoration.BOLD, false)); // broadcast the time-out message
    }


    public ItemStack flareItem(Component name, Component text) {
        ItemStack item = new ItemStack(Material.REDSTONE_TORCH);
        ItemMeta im = item.getItemMeta();
        Component first = m.colorText("Flare: ", "gray",TextDecoration.ITALIC, false);
        im.displayName(first.append(name));
        List<Component> lore = new ArrayList<>();
        lore.add(text);
        im.lore(lore);
        item.setItemMeta(im);
        return item;
    }

    public void giveFlareItem(Player p, String flare, boolean random) {
        if(random) {
            Random r = new Random();
            p.getInventory().addItem(flareItem(m.colorText("Zeus' Wrath", TextColor.color(0xFFF363), TextDecoration.ITALIC, false)
                    ,m.colorText("Has a chance to summon Lightning in your mine; Enhancing the blocks", NamedTextColor.GRAY, TextDecoration.ITALIC, true)));
        }
        else {
            //TODO add specific flares
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("giveflare")) {
            Player p = (Player)sender;
            if(args.length == 2) {
                String type = args[0];
                int hexcode = Integer.parseInt(args[1].split("#")[1], 16);
                p.getInventory().addItem(flareItem(m.colorText(type, TextColor.color(hexcode),TextDecoration.BOLD, false)
                        ,m.colorText("Has a chance to summon Lightning in your mine; Enhancing the blocks", NamedTextColor.GRAY, TextDecoration.ITALIC, true)));

            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("zeus")) {
                    giveFlareItem(p, "Zeus", true);
                }
            }
        }

        return false;
    }
    public List<TextComponent> eventFlares() {
        List<TextComponent> eventFlares = new ArrayList<>();
        TextComponent zeus = m.colorText("Flare: ", "gray",TextDecoration.ITALIC, false)
                .append(m.colorText("Zeus' Wrath", TextColor.color(0xFFF363), TextDecoration.ITALIC, false));
        eventFlares.add(zeus);
        //TODO add all flare components

        return eventFlares;
    }

    public int isSimilar(Component c) {
        int i = 0;
        for(TextComponent com : eventFlares()) {
            Component co = com.asComponent();
            if(c.equals(co)) {
                return i;
            }
            i++;
        }
        return -1;
    }

    @EventHandler
    public void onInt(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(!p.getInventory().getItemInMainHand().getType().equals(Material.REDSTONE_TORCH)) return;
        if(!p.getInventory().getItemInMainHand().hasItemMeta()) return;

        Component name = p.getInventory().getItemInMainHand().getItemMeta().displayName();

        int event = isSimilar(name);
        if(event > -1) {
            switch(event) {
                case 0:
                    activateEvent(p, Event.Wrath);
                    break;
                case 1:
                    //Do
                    break;
            }
        }

    }

    public enum Event {
        Wrath,

    }

}
