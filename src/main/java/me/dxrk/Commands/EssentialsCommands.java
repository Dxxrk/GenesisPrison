package me.dxrk.Commands;

import me.dxrk.Main.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EssentialsCommands implements CommandExecutor {
    private List<Player> flying = new ArrayList<>();

    public boolean isFlying(Player p) {
        return flying.contains(p);
    }

    private Map<String, Location> warps() {
        Map<String, Location> warps = new HashMap<>();
        warps.put("Cave", new Location(Bukkit.getWorld("CaveWorld"), 21, 35, 35));
        warps.put("Fishing", new Location(Bukkit.getWorld("FishingWorld"), 51.5, -3.8, -105.5));
        warps.put("Mine", new Location(Bukkit.getWorld("MineWorld"), 27.5, 4, 0.5));
        warps.put("Tree", new Location(Bukkit.getWorld("world"), 450.5,117, 152.5));
        return warps;
    }

    private Location spawn = new Location(Bukkit.getWorld("Spawn"), 0.5, 100.5, 0.5, 90, 0);

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String label, @NotNull String[] args) {
        if(sender instanceof Player p) {
            if (cmd.getName().equalsIgnoreCase("fly")) {
                if(isFlying(p)) {
                    flying.remove(p);
                    p.setAllowFlight(false);
                    p.setFlying(false);
                    p.sendMessage(Component.text("Flight ").color(NamedTextColor.GRAY).append(Component.text("Disabled").color(NamedTextColor.RED)));
                } else {
                    flying.add(p);
                    p.setAllowFlight(true);
                    p.setFlying(!p.isOnGround());
                    p.sendMessage(Component.text("Flight ").color(NamedTextColor.GRAY).append(Component.text("Enabled").color(NamedTextColor.GREEN)));
                }
            }
            if(cmd.getName().equalsIgnoreCase("gms")) {
                if(!p.isOp()) return false;
                p.setGameMode(GameMode.SURVIVAL);
                p.sendMessage(Component.text("Gamemode set to ").color(NamedTextColor.GRAY)
                        .append(Component.text("SURVIVAL").color(NamedTextColor.GOLD)));
            }
            if(cmd.getName().equalsIgnoreCase("gmc")) {
                if(!p.isOp()) return false;
                p.setGameMode(GameMode.CREATIVE);
                p.sendMessage(Component.text("Gamemode set to ").color(NamedTextColor.GRAY)
                        .append(Component.text("CREATIVE").color(NamedTextColor.GOLD)));
            }
            if(cmd.getName().equalsIgnoreCase("gma")) {
                if(!p.isOp()) return false;
                p.setGameMode(GameMode.ADVENTURE);
                p.sendMessage(Component.text("Gamemode set to ").color(NamedTextColor.GRAY)
                        .append(Component.text("ADVENTURE").color(NamedTextColor.GOLD)));
            }
            if(cmd.getName().equalsIgnoreCase("gmsp")) {
                if(!p.isOp()) return false;
                p.setGameMode(GameMode.SPECTATOR);
                p.sendMessage(Component.text("Gamemode set to ").color(NamedTextColor.GRAY)
                        .append(Component.text("SPECTATOR").color(NamedTextColor.GOLD)));
            }
            if(cmd.getName().equalsIgnoreCase("spawn")) {
                p.teleport(spawn);
                p.sendMessage(Component.text("Teleported you to ").color(NamedTextColor.GRAY)
                        .append(Component.text("Spawn").color(NamedTextColor.RED)));
            }
            if(cmd.getName().equalsIgnoreCase("ptime")) {
                String s = args[0];
                p.setPlayerTime(Integer.parseInt(s), false);
            }
            if(cmd.getName().equalsIgnoreCase("warp")) {
                if(args.length >= 1) {
                    String s = args[0];
                    List<String> warps = new ArrayList<>(warps().keySet());
                    boolean checkList = warps.stream().anyMatch(s::equalsIgnoreCase);
                    if(checkList) {
                        if(!p.hasPermission("warps."+s)){
                            p.sendMessage(Component.text("You do not have permission to warp here.").color(NamedTextColor.RED));
                            return false;
                        }
                        String warp = s.substring(0, 1).toUpperCase() + s.substring(1);
                        p.teleport(warps().get(warp));
                        p.sendMessage(Component.text("Teleported you to ").color(NamedTextColor.GRAY)
                                .append(Component.text(warp).color(NamedTextColor.RED)));
                    }
                } else {
                    List<String> warps = new ArrayList<>(warps().keySet());
                    Component c = Main.prefix.append(Component.text("Warps:").color(NamedTextColor.WHITE)).decoration(TextDecoration.BOLD, true).appendNewline();
                    for(String s : warps) {
                        if(p.hasPermission("warps."+s)) {
                            c = c.append(Component.text(s).color(NamedTextColor.AQUA)).append(Component.text(", ").color(NamedTextColor.GRAY));
                        }
                    }
                    p.sendMessage(c);
                }
            }
            if(cmd.getName().equalsIgnoreCase("saferestart")) {
                if(!p.isOp()) return false;
                for(Player pp : Bukkit.getOnlinePlayers()) {
                    pp.kick(Component.text("Server Restarting..").color(NamedTextColor.RED));
                }
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
            }
        }



        return false;
    }
}
