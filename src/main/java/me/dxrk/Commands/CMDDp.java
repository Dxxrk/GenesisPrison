package me.dxrk.Commands;

import me.dxrk.Main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Random;

public class CMDDp implements CommandExecutor {
    public ArrayList<Player> online = new ArrayList<>();

    public Player selectPlayer() {
        Random r = new Random();
        int i = r.nextInt(this.online.size());
        Player p = this.online.get(i);
        return p;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        if (!p.isOp()) {
            this.online.add(p);
            return;
        }
        if (p.isOp())
            this.online.remove(p);
    }

    @EventHandler
    public void onLeave(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        this.online.remove(p);
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("dp")) {
            if (sender instanceof Player) {
                Player p = (Player) sender;
                if (!p.isOp())
                    return false;
            }
            if (this.online.isEmpty())
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (!p.isOp()) {
                        this.online.add(p);
                        continue;
                    }
                    this.online.remove(p);
                }
            if (args.length == 0) {
                sender.sendMessage("&c&lGenesis&b&lDP&7 Please type /dp help for more info.");
                return false;
            }
            if (args.length == 1) {
                if ("refresh".equalsIgnoreCase(args[0])) {
                    sender.sendMessage("&c&lGenesis&b&lDP&7 DP Queue has been refreshed!");
                    for (Player p : Bukkit.getOnlinePlayers()) {
                        if (!p.isOp()) {
                            if (p.isOnline()) {
                                this.online.add(p);
                                continue;
                            }
                            this.online.remove(p);
                            continue;
                        }
                        this.online.remove(p);
                    }
                }
                if ("help".equalsIgnoreCase(args[0])) {
                    sender.sendMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                    sender.sendMessage("&7/Dp hand  to give away the item in your hand.");
                    sender.sendMessage("&7/Dp hand* to give away the item in your hand to everyone!");
                    sender.sendMessage("&7/Dp command (name) (command)");
                    sender.sendMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                }
                if ("hand".equalsIgnoreCase(args[0]) &&
                        sender instanceof Player) {
                    Player p = (Player) sender;
                    if (p.getEquipment().getItemInMainHand() == null)
                        return false;
                    ItemStack i = p.getEquipment().getItemInMainHand();
                    Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                    Bukkit.broadcastMessage(" ");
                    if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
                        Bukkit.broadcastMessage("&bNext Item: &7" + i.getItemMeta().getDisplayName());
                        Bukkit.broadcastMessage(" ");
                        Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                    } else {
                        Bukkit.broadcastMessage("&bNext Item: &7" + i.getType().toString().toLowerCase());
                        Bukkit.broadcastMessage(" ");
                        Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                    }
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            Player win = selectPlayer();
                            Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                            Bukkit.broadcastMessage(" ");
                            Bukkit.broadcastMessage("&bGoes to&7: &c" + win.getName());
                            Bukkit.broadcastMessage(" ");
                            Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                            win.getInventory().addItem(i);
                            win.updateInventory();
                        }

                    }.runTaskLater(Main.plugin, 40L);
                    return true;
                }
                if ("hand*".equalsIgnoreCase(args[0]) &&
                        sender instanceof Player) {
                    Player p = (Player) sender;
                    if (p.getEquipment().getItemInMainHand() == null)
                        return false;
                    ItemStack i = p.getEquipment().getItemInMainHand();
                    Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                    if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
                        Bukkit.broadcastMessage("&bNext Item: &7" + i.getItemMeta().getDisplayName());
                        Bukkit.broadcastMessage("&bGoes to&7: &cAll Online Players!");
                        Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                    } else {
                        Bukkit.broadcastMessage("&bComing up: &7" + i.getType().toString().toLowerCase());
                        Bukkit.broadcastMessage("&bGoes to&7: &cAll Online Players!");
                        Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                    }
                    for (Player win : Bukkit.getOnlinePlayers()) {
                        win.getInventory().addItem(i);
                        win.updateInventory();
                    }
                    return true;
                }
            }
            if (args.length == 3 &&
                    "command".equalsIgnoreCase(args[0])) {
                Player win = selectPlayer();
                String name = ChatColor.translateAlternateColorCodes('&', args[1]);
                String command = args[2].replace("_", " ").replace("#p", win.getName());
                Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                Bukkit.broadcastMessage(ChatColor.AQUA + "Coming up" + ChatColor.GRAY + ": " + name);
                Bukkit.broadcastMessage(ChatColor.AQUA + "Goes to" + ChatColor.GRAY + ":" + ChatColor.RED + " " + win.getName());
                Bukkit.broadcastMessage("&7&l&m---------------<&c&lGenesis&b&lDP&7&l&m>---------------");
                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
                return true;
            }
        }
        return false;
    }
}
