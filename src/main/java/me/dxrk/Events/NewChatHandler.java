package me.dxrk.Events;


import io.papermc.paper.event.player.AsyncChatEvent;
import me.dxrk.Commands.CMDOptions;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.TextReplacementConfig;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class NewChatHandler implements Listener {
    Methods m = Methods.getInstance();
    SettingsManager settings = SettingsManager.getInstance();


    public String prefixOLD(Player p) {
        String rank = "";
        if (p.getName().equalsIgnoreCase("Dxrk")) {
            rank = m.c("&d&l&ki&f&lOwner&d&l&ki&r ");
        } else if (p.getName().equalsIgnoreCase("_Lone_Ninja_")) {
            rank = m.c("&f&k&l;&c&lAdmin&f&k&l;&r ");
        } else if (p.getName().equalsIgnoreCase("Pikashoo")) {
            rank = m.c("&f&k&l;&a&lDeveloper&f&k&l;&r ");
        } else if (p.getName().equalsIgnoreCase("BakonStrip")) {
            rank = CMDOptions.TagColor(settings.getOptions().getString(p.getUniqueId() + ".GenesisColor")) + " ";
        } else if (p.hasPermission("Rank.Manager")) {
            rank = m.c("&f&l&k;&5&lManager&f&l&k;&r ");
        } else if (p.hasPermission("Rank.Mod")) {
            rank = m.c("&b&l&k;&9&lMod&b&l&k;&r ");
        } else if (p.hasPermission("Rank.Builder")) {
            rank = m.c("&2&l&k;&a&lBuilder&2&l&k;&r ");
        } else if (p.hasPermission("Rank.Helper")) {
            rank = m.c("&6&l&k;&e&lHelper&6&l&k;&r ");
        } else if (p.hasPermission("Rank.genesis")) {
            rank = CMDOptions.TagColor(settings.getOptions().getString(p.getUniqueId() + ".GenesisColor")) + " ";
        } else if (p.hasPermission("Rank.olympian")) {
            rank = m.c("&6&ki&e&lOlympian&6&ki&r ");
        } else if (p.hasPermission("Rank.god")) {
            rank = m.c("&d&lGod ");
        } else if (p.hasPermission("Rank.titan")) {
            rank = m.c("&3&lTitan ");
        } else if (p.hasPermission("Rank.demi-god")) {
            rank = m.c("&5&lDemi-God ");
        } else if (p.hasPermission("Rank.hero")) {
            rank = m.c("&c&lHero ");
        } else if (p.hasPermission("Rank.mvp")) {
            rank = m.c("&6&lMVP ");
        } else if (p.hasPermission("Rank.vip")) {
            rank = m.c("&a&lVIP ");
        } else if (p.hasPermission("Rank.sponsor")) {
            rank = m.c("&b&lSponsor ");
        }
        return rank;
    }

    public TextComponent genesisTemplate(TextColor nameColor) {
        TextComponent com = Component.text()
                .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                .append(Component.text("Genesis").decoration(TextDecoration.BOLD, true).color(nameColor))
                .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                .build();

        return com;
    }

    public TextComponent genesisColors(String s) {
        TextComponent genesis = null;

        if(s.equalsIgnoreCase("default")) {
            genesis = Component.text()
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                    .append(Component.text("G").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.DARK_RED))
                    .append(Component.text("e").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.RED))
                    .append(Component.text("n").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.GOLD))
                    .append(Component.text("e").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.YELLOW))
                    .append(Component.text("s").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.GREEN))
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.AQUA))
                    .append(Component.text("s").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                    .build();
        }
        if(s.equalsIgnoreCase("pink")) {
            genesis = genesisTemplate(NamedTextColor.LIGHT_PURPLE);
        }
        if(s.equalsIgnoreCase("aqua")) {
            genesis = genesisTemplate(NamedTextColor.AQUA);
        }
        if(s.equalsIgnoreCase("lime")) {
            genesis = genesisTemplate(NamedTextColor.GREEN);
        }
        if(s.equalsIgnoreCase("yellow")) {
            genesis = genesisTemplate(NamedTextColor.YELLOW);
        }
        if(s.equalsIgnoreCase("red")) {
            genesis = genesisTemplate(NamedTextColor.RED);
        }
        if(s.equalsIgnoreCase("green")) {
            genesis = genesisTemplate(NamedTextColor.GREEN);
        }
        if(s.equalsIgnoreCase("gold")) {
            genesis = genesisTemplate(NamedTextColor.GOLD);
        }
        if(s.equalsIgnoreCase("cyan")) {
            genesis = genesisTemplate(NamedTextColor.DARK_AQUA);
        }
        if(s.equalsIgnoreCase("white")) {
            genesis = genesisTemplate(NamedTextColor.WHITE);
        }
        if(s.equalsIgnoreCase("blue")) {
            genesis = genesisTemplate(NamedTextColor.BLUE);
        }
        if(s.equalsIgnoreCase("purple")) {
            genesis = genesisTemplate(NamedTextColor.DARK_PURPLE);
        }
        if(s.equalsIgnoreCase("thematic")) {
            genesis = Component.text()
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                    .append(Component.text("G").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.RED))
                    .append(Component.text("e").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.AQUA))
                    .append(Component.text("n").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.RED))
                    .append(Component.text("e").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.AQUA))
                    .append(Component.text("s").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.RED))
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.AQUA))
                    .append(Component.text("s").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.RED))
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                    .build();
        }

        return genesis;
    }


    
    public TextComponent getPrefix(Player p, String rank, String name, boolean bold, TextColor magicColor, TextColor rankColor, boolean genesis) {
        if(genesis) {
            TextComponent com = Component.text()
                    .append(genesisColors(settings.getOptions().getString(p.getUniqueId()+".GenesisColor")))
                    .append(Component.text(" "+name+" ").decoration(TextDecoration.BOLD, bold).color(magicColor))
                    .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
                    .hoverEvent(HoverEvent.showText(Component.text()
                            .append(Component.text(p.getName()+"'s Stats").color(NamedTextColor.AQUA))
                            .appendNewline()
                            .append(Component.text("Rank: ").color(NamedTextColor.GREEN))
                    ))
                    .build();
            return com;
        }
        TextComponent com = Component.text()
                .append(Component.text("i").decoration(TextDecoration.BOLD, bold).decoration(TextDecoration.OBFUSCATED, true).color(magicColor))
                .append(Component.text(rank).decoration(TextDecoration.BOLD, bold).color(NamedTextColor.WHITE))
                .append(Component.text("i").decoration(TextDecoration.BOLD, bold).decoration(TextDecoration.OBFUSCATED, true).color(rankColor))
                .append(Component.text(" "+name+" ").decoration(TextDecoration.BOLD, bold).color(magicColor))
                .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
                .hoverEvent(HoverEvent.showText(Component.text()
                        .append(Component.text(p.getName()+"'s Stats").color(NamedTextColor.AQUA))
                        .appendNewline()
                        .append(Component.text("Rank: ").color(NamedTextColor.GREEN))
                ))
                .build();
        return com;
    }

    public TextComponent prefix(Player p) {
        TextComponent prefix = null;
        if(p.getName().equalsIgnoreCase("Dxrk")) {
            /*prefix = Component.text()
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("Owner").decoration(TextDecoration.BOLD, true).color(NamedTextColor.WHITE))
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text(" Dxrk ").decoration(TextDecoration.BOLD, true).color(NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
                    .hoverEvent(HoverEvent.showText(Component.text()
                            .append(Component.text("Info Goes Here"))
                            .color(NamedTextColor.AQUA)
                    ))
                    .build();*/
            prefix = getPrefix(p, "Owner", "Dxrk", true, NamedTextColor.LIGHT_PURPLE, NamedTextColor.WHITE, false);
        }
        else if(p.getName().equalsIgnoreCase("Pikashoo")) {
            /*prefix = Component.text()
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                    .append(Component.text("Developer").decoration(TextDecoration.BOLD, true).color(NamedTextColor.GREEN))
                    .append(Component.text("i").decoration(TextDecoration.BOLD, true).decoration(TextDecoration.OBFUSCATED, true).color(NamedTextColor.WHITE))
                    .append(Component.text(" Pikashoo ").decoration(TextDecoration.BOLD, true).color(NamedTextColor.LIGHT_PURPLE))
                    .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
                    .hoverEvent(HoverEvent.showText(Component.text()
                            .append(Component.text("Info Goes Here"))
                            .color(NamedTextColor.AQUA)
                    ))
                    .build();*/
            prefix = getPrefix(p, "Developer", "Pikashoo", true, NamedTextColor.WHITE, NamedTextColor.GREEN, false);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Staff").equals("Mod")) {
            prefix = getPrefix(p, "Mod", p.getName(), true, NamedTextColor.BLUE, NamedTextColor.AQUA, false);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Staff").equals("Builder")) {
            prefix = getPrefix(p, "Builder", p.getName(), true, NamedTextColor.DARK_GREEN, NamedTextColor.GREEN, false);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Genesis")) {
            prefix = getPrefix(p, "", p.getName(), true, NamedTextColor.WHITE, NamedTextColor.WHITE, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Olympian")) {
            prefix = getPrefix(p, "Olympian", p.getName(), true, NamedTextColor.GOLD, NamedTextColor.YELLOW, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("God")) {
            prefix = getPrefix(p, "God", p.getName(), true, NamedTextColor.DARK_PURPLE, NamedTextColor.LIGHT_PURPLE, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Titan")) {
            prefix = getPrefix(p, "Titan", p.getName(), true, NamedTextColor.AQUA, NamedTextColor.DARK_AQUA, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Demi-God")) {
            prefix = getPrefix(p, "Demi-God", p.getName(), true, NamedTextColor.LIGHT_PURPLE, NamedTextColor.DARK_PURPLE, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Hero")) {
            prefix = getPrefix(p, "Hero", p.getName(), false, NamedTextColor.DARK_RED, NamedTextColor.RED, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("MVP")) {
            prefix = getPrefix(p, "MVP", p.getName(), false, NamedTextColor.YELLOW, NamedTextColor.GOLD, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("VIP")) {
            prefix = getPrefix(p, "VIP", p.getName(), false, NamedTextColor.DARK_GREEN, NamedTextColor.GREEN, true);
        }
        else if(PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Sponsor")) {
            prefix = getPrefix(p, "Sponsor", p.getName(), false, NamedTextColor.DARK_AQUA, NamedTextColor.AQUA, true);
        }
        return prefix;
    }

    public TextComponent message(Player p, Component msg) {
        TextComponent message = null;
        if(p.getName().equalsIgnoreCase("Dxrk")) {
            message = Component.text()
                    .append(msg.decoration(TextDecoration.BOLD, true).color(NamedTextColor.WHITE))
                    .hoverEvent(HoverEvent.showText(Component.text()))
                    .build();

        }
        return message;
    }

    @EventHandler
    public void onChat(AsyncChatEvent e) {
        Player p = e.getPlayer();
        e.setCancelled(true);

        //replacing [item]
        ItemStack pitem = p.getInventory().getItemInMainHand();
        if(pitem.getType().equals(Material.AIR) || !pitem.hasItemMeta()) {
            for(Player ps : Bukkit.getOnlinePlayers()) {
                ps.sendMessage(prefix(p).append(message(p, e.message())));
                Bukkit.getConsoleSender().sendMessage(prefix(p).append(message(p, e.message())));
            }
            return;
        }
        TextComponent item = Component.text()
                .append(Component.text("»").color(NamedTextColor.GRAY))
                .append(pitem.getItemMeta().displayName())
                .append(Component.text("«").color(NamedTextColor.GRAY))
                .hoverEvent(pitem.asHoverEvent())
                .build();




        for(Player ps : Bukkit.getOnlinePlayers()) {
            ps.sendMessage(prefix(p).append(message(p, e.message()
                    .replaceText(TextReplacementConfig.builder()
                            .match("\\[item\\]")
                            .once()
                            .replacement(item)
                            .build()
                    ))));
            Bukkit.getConsoleSender().sendMessage(prefix(p).append(message(p, e.message()
                    .replaceText(TextReplacementConfig.builder()
                            .match("\\[item\\]")
                            .once()
                            .replacement(item)
                            .build()
                    ))));
        }


    }





}
