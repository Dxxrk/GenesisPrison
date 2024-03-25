package me.dxrk.Events;


import io.papermc.paper.event.player.AsyncChatEvent;
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

import java.util.List;

public class NewChatHandler implements Listener {
    Methods m = Methods.getInstance();
    SettingsManager settings = SettingsManager.getInstance();


    private TextComponent letterStyle(String letter, List<Boolean> styles, TextColor color) {
        TextComponent l = Component.text()
                .append(Component.text(letter).decoration(TextDecoration.BOLD, styles.get(0)).decoration(TextDecoration.ITALIC, styles.get(1)).decoration(TextDecoration.UNDERLINED, styles.get(2))
                        .decoration(TextDecoration.STRIKETHROUGH, styles.get(3)).color(color))
                .build();
        return l;
    }

    private TextComponent nick(Player p) {
        List<String> letters = PlayerDataHandler.getInstance().getPlayerData(p).getStringList("LettersInName");
        TextComponent nick = null;

        for (String s : letters) {
            List<Boolean> styles = PlayerDataHandler.getInstance().getPlayerData(p).getBooleanList("Nickname." + s + ".Style");
            TextColor color = (TextColor) PlayerDataHandler.getInstance().getPlayerData(p).get("Nickname." + s + ".Color");
            if (nick == null) {
                nick = letterStyle(s, styles, color);
            } else {
                nick = nick.append(letterStyle(s, styles, color));
            }
        }

        return nick;
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

        if (s.equalsIgnoreCase("default")) {
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
        if (s.equalsIgnoreCase("pink")) {
            genesis = genesisTemplate(NamedTextColor.LIGHT_PURPLE);
        }
        if (s.equalsIgnoreCase("aqua")) {
            genesis = genesisTemplate(NamedTextColor.AQUA);
        }
        if (s.equalsIgnoreCase("lime")) {
            genesis = genesisTemplate(NamedTextColor.GREEN);
        }
        if (s.equalsIgnoreCase("yellow")) {
            genesis = genesisTemplate(NamedTextColor.YELLOW);
        }
        if (s.equalsIgnoreCase("red")) {
            genesis = genesisTemplate(NamedTextColor.RED);
        }
        if (s.equalsIgnoreCase("green")) {
            genesis = genesisTemplate(NamedTextColor.GREEN);
        }
        if (s.equalsIgnoreCase("gold")) {
            genesis = genesisTemplate(NamedTextColor.GOLD);
        }
        if (s.equalsIgnoreCase("cyan")) {
            genesis = genesisTemplate(NamedTextColor.DARK_AQUA);
        }
        if (s.equalsIgnoreCase("white")) {
            genesis = genesisTemplate(NamedTextColor.WHITE);
        }
        if (s.equalsIgnoreCase("blue")) {
            genesis = genesisTemplate(NamedTextColor.BLUE);
        }
        if (s.equalsIgnoreCase("purple")) {
            genesis = genesisTemplate(NamedTextColor.DARK_PURPLE);
        }
        if (s.equalsIgnoreCase("thematic")) {
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
        if (genesis) {
            TextComponent com = Component.text()
                    .append(genesisColors(settings.getOptions().getString(p.getUniqueId() + ".GenesisColor")))
                    .append(Component.text(" " + name + " ").decoration(TextDecoration.BOLD, bold).color(magicColor))
                    .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
                    .hoverEvent(HoverEvent.showText(Component.text()
                            .append(Component.text(p.getName() + "'s Stats").color(NamedTextColor.AQUA))
                            .appendNewline()
                            .append(Component.text("Rank: ").color(NamedTextColor.GREEN))
                    ))
                    .build();
            return com;
        }
        if(rank.equals("")) {
            TextComponent com = Component.text()
                    .append(Component.text(name + " ").decoration(TextDecoration.BOLD, bold).color(magicColor))
                    .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
                    .hoverEvent(HoverEvent.showText(Component.text()
                            .append(Component.text(p.getName() + "'s Stats").color(NamedTextColor.AQUA))
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
                .append(Component.text(" " + name + " ").decoration(TextDecoration.BOLD, bold).color(magicColor))
                .append(Component.text("» ").color(NamedTextColor.DARK_GRAY))
                .hoverEvent(HoverEvent.showText(Component.text()
                        .append(Component.text(p.getName() + "'s Stats").color(NamedTextColor.AQUA))
                        .appendNewline()
                        .append(Component.text("Rank: ").color(NamedTextColor.GREEN))
                ))
                .build();
        return com;
    }

    public TextComponent prefix(Player p) {
        TextComponent prefix = null;
        if (p.getName().equalsIgnoreCase("Dxrk")) {
            prefix = getPrefix(p, "Owner", "Dxrk", true, NamedTextColor.LIGHT_PURPLE, NamedTextColor.WHITE, false);
        } else if (p.getName().equalsIgnoreCase("Pikashoo")) {
            prefix = getPrefix(p, "Developer", "Pikashoo", true, NamedTextColor.WHITE, NamedTextColor.GREEN, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Staff").equals("Mod")) {
            prefix = getPrefix(p, "Mod", p.getName(), true, NamedTextColor.BLUE, NamedTextColor.AQUA, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Staff").equals("Builder")) {
            prefix = getPrefix(p, "Builder", p.getName(), true, NamedTextColor.DARK_GREEN, NamedTextColor.GREEN, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Genesis")) {
            prefix = getPrefix(p, "", p.getName(), true, NamedTextColor.WHITE, NamedTextColor.WHITE, true);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Olympian")) {
            prefix = getPrefix(p, "Olympian", p.getName(), true, NamedTextColor.GOLD, NamedTextColor.YELLOW, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("God")) {
            prefix = getPrefix(p, "God", p.getName(), true, NamedTextColor.DARK_PURPLE, NamedTextColor.LIGHT_PURPLE, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Titan")) {
            prefix = getPrefix(p, "Titan", p.getName(), true, NamedTextColor.AQUA, NamedTextColor.DARK_AQUA, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Demi-God")) {
            prefix = getPrefix(p, "Demi-God", p.getName(), true, NamedTextColor.LIGHT_PURPLE, NamedTextColor.DARK_PURPLE, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Hero")) {
            prefix = getPrefix(p, "Hero", p.getName(), false, NamedTextColor.DARK_RED, NamedTextColor.RED, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("MVP")) {
            prefix = getPrefix(p, "MVP", p.getName(), false, NamedTextColor.YELLOW, NamedTextColor.GOLD, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("VIP")) {
            prefix = getPrefix(p, "VIP", p.getName(), false, NamedTextColor.DARK_GREEN, NamedTextColor.GREEN, false);
        } else if (PlayerDataHandler.getInstance().getPlayerData(p).get("Rank").equals("Sponsor")) {
            prefix = getPrefix(p, "Sponsor", p.getName(), false, NamedTextColor.DARK_AQUA, NamedTextColor.AQUA, false);
        } else {
            prefix = getPrefix(p, "", p.getName(), false, NamedTextColor.AQUA, NamedTextColor.AQUA, false);
        }
        return prefix;
    }

    public TextComponent message(Player p, Component msg) {
        TextComponent message = null;
        if (p.getName().equalsIgnoreCase("Dxrk")) {
            message = Component.text()
                    .append(msg.decoration(TextDecoration.BOLD, true).color(NamedTextColor.WHITE))
                    .hoverEvent(HoverEvent.showText(Component.text()))
                    .build();

        } else {
            message = Component.text()
                    .append(msg.decoration(TextDecoration.BOLD, false).color(NamedTextColor.GRAY))
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
        if (pitem.getType().equals(Material.AIR) || !pitem.hasItemMeta()) {
            if(NicknameHandler.waitingForNick.contains(p)) {
                NicknameHandler.openNickInv(e.message(), p);
            }
            for (Player ps : Bukkit.getOnlinePlayers()) {
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


        for (Player ps : Bukkit.getOnlinePlayers()) {
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
