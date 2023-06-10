package me.dxrk.Events;

import me.dxrk.Commands.CMDAc;
import me.dxrk.Commands.CMDOptions;
import me.dxrk.Gangs.CMDGang;
import me.dxrk.Gangs.Gangs;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import mkremins.fanciful.FancyMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.NumberFormat;
import java.util.*;

public class ChatHandler implements Listener, CommandExecutor {
    static ChatHandler instance = new ChatHandler();

    public static ChatHandler getInstance() {
        return instance;
    }

    SettingsManager settings = SettingsManager.getInstance();
    Gangs gang = Gangs.getInstance();

    public static boolean isMuted = false;


    SellHandler sell = SellHandler.getInstance();

    Tokens tokens = Tokens.getInstance();

    public static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public boolean getOption(Player p, String s) {
        if (!this.settings.getData().contains(p.getUniqueId().toString() + ".Chat." + s)) {
            this.settings.getData().set(p.getUniqueId().toString() + ".Chat." + s, 0);
            this.settings.saveData();
        }
        return this.settings.getData().getInt(p.getUniqueId().toString() + ".Chat." + s) == 0;
    }

    public String getChatColor(Player p) {
        String s = this.settings.getcolor().getString(p.getName() + ".Color");
        return s;
    }

    public void openColorInv(Player p) {
        Inventory i = Bukkit.createInventory(null, 18, ChatColor.AQUA + "Chat Color");
        i.setItem(0, dye(12, "&bLight Blue"));
        i.setItem(1, dye(15, "&fWhite"));
        i.setItem(2, dye(11, "&eYellow"));
        i.setItem(3, dye(4, "&9Blue"));
        i.setItem(4, dye(2, "&2Green"));
        i.setItem(5, dye(5, "&5Purple"));
        i.setItem(6, dye(14, "&6Gold"));
        i.setItem(7, dye(1, "&cRed"));
        i.setItem(8, dye(10, "&aLime"));

        ItemStack bar = new ItemStack(Material.BARRIER);
        ItemMeta bm = bar.getItemMeta();
        bm.setDisplayName(c("&cRemove color"));
        bar.setItemMeta(bm);

        i.setItem(13, bar);
        p.openInventory(i);
    }

    public void setChatColor(Player p, String color) {
        this.settings.getcolor().set(p.getName() + ".Color", color);
        this.settings.savecolorFile();
    }

    private ItemStack dye(int color, String name) {
        ItemStack i = new ItemStack(Material.INK_SACK, 1, (short) color);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
        i.setItemMeta(im);
        return i;
    }

    private ItemStack Default() {
        ItemStack a = new ItemStack(Material.BARRIER);
        ItemMeta am = a.getItemMeta();
        am.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&7Default Chat"));
        a.setItemMeta(am);
        return a;
    }

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(ChatColor.AQUA + "Chat Color")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getRawSlot() == 13)
                setChatColor(p, null);
            if (e.getRawSlot() == 0)
                setChatColor(p, "LightBlue");
            if (e.getRawSlot() == 1) {
                if (!p.hasPermission("ChatColor.White")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "White");
            }
            if (e.getRawSlot() == 2) {
                if (!p.hasPermission("ChatColor.Yellow")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "Yellow");
            }
            if (e.getRawSlot() == 3) {
                if (!p.hasPermission("ChatColor.Blue")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "Blue");
            }
            if (e.getRawSlot() == 4) {
                if (!p.hasPermission("ChatColor.green")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "Green");
            }
            if (e.getRawSlot() == 5) {
                if (!p.hasPermission("ChatColor.Purple")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "Purple");
            }
            if (e.getRawSlot() == 6) {
                if (!p.hasPermission("ChatColor.Gold")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "Gold");
            }
            if (e.getRawSlot() == 7) {
                if (!p.hasPermission("ChatColor.Red")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "Red");
            }
            if (e.getRawSlot() == 8) {
                if (!p.hasPermission("ChatColor.Lime")) {
                    p.sendMessage(ChatColor.RED + "You do not have this chat color.");
                    return;
                }
                setChatColor(p, "Lime");
            }
            p.closeInventory();
            p.sendMessage(ChatColor.GREEN + "Chat color changed.");
        }
    }

    public String rainbowText(String s) {
        StringBuilder result = new StringBuilder();
        int i = 0;
        byte b;
        int j;
        char[] arrayOfChar;
        for (j = (arrayOfChar = s.toCharArray()).length, b = 0; b < j; ) {
            char c = arrayOfChar[b];
            if (Character.isSpaceChar(c)) {
                result.append(c);
            } else if (i == 0) {
                result.append("&a&l").append(c);
                i = 1;
            } else if (i == 1) {
                result.append("&b&l").append(c);
                i = 2;
            } else if (i == 2) {
                result.append("&c&l").append(c);
                i = 3;
            } else if (i == 3) {
                result.append("&d&l").append(c);
                i = 4;
            } else if (i == 4) {
                result.append("&e&l").append(c);
                i = 5;
            } else {
                result.append("&f&l").append(c);
                i = 0;
            }
            b = (byte) (b + 1);
        }
        return ChatColor.translateAlternateColorCodes('&', result.toString());
    }


    public void switchOption(Player p, String s) {
        if (getOption(p, s)) {
            this.settings.getData().set(p.getUniqueId().toString() + ".Chat." + s, 1);
            this.settings.saveData();
        } else {
            this.settings.getData().set(p.getUniqueId().toString() + ".Chat." + s, 0);
            this.settings.saveData();
        }
    }


    public ItemStack optionItem(String name, boolean on) {
        ItemStack i = new ItemStack(Material.INK_SACK, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.AQUA + name);
        List<String> lore = new ArrayList<>();
        lore.add("");
        if (on) {
            lore.add(ChatColor.GRAY + "Click to toggle off!");
            i.setDurability((short) 10);
        } else {
            lore.add(ChatColor.GRAY + "Click to toggle on!");
            i.setDurability((short) 8);
        }
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    public void openToggleOptions(Player p) {
        Inventory i = Bukkit.createInventory(null, InventoryType.HOPPER, ChatColor.AQUA + "Chat Options");
        i.setItem(0, optionItem("Gang", getOption(p, "Gang")));
        i.setItem(1, optionItem("Level", getOption(p, "Level")));
        i.setItem(2, optionItem("Nickname", getOption(p, "Nickname")));
        i.setItem(3, optionItem("Donor", getOption(p, "Donor")));
        i.setItem(4, optionItem("Tag", getOption(p, "Tag")));
        p.openInventory(i);
    }

    @EventHandler
    public void onClick1(InventoryClickEvent e) {
        if (e.getInventory().getName().equals(ChatColor.AQUA + "Chat Options")) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getRawSlot() == 0)
                switchOption(p, "Gang");
            if (e.getRawSlot() == 1)
                switchOption(p, "Level");
            if (e.getRawSlot() == 2)
                switchOption(p, "Nickname");
            if (e.getRawSlot() == 3)
                switchOption(p, "Donor");
            if (e.getRawSlot() == 4)
                switchOption(p, "Tag");
            openToggleOptions(p);
        }
    }

    public HashMap<Player, Long> waiting = new HashMap<>();

    public String prestige(int i) {
        if (i >= 10) {
            String s = String.valueOf(i);
            StringBuilder sb = new StringBuilder();
            byte b;
            int j;
            char[] arrayOfChar;
            for (j = (arrayOfChar = s.toCharArray()).length, b = 0; b < j; ) {
                char ss = arrayOfChar[b];
                int col = Integer.parseInt(String.valueOf(ss));
                String str = String.valueOf(ss);
                if (col == 0)
                    str = "a";
                if (col == 8)
                    str = "b";
                if (col == 1)
                    str = "c";
                if (col == 2)
                    str = "d";
                if (col == 7)
                    str = "e";
                sb.append("&").append(str).append("&l").append(ss);
                b = (byte) (b + 1);
            }
            return sb.toString();
        }
        String color = String.valueOf(i);
        if (i == 0)
            color = "a";
        if (i == 8)
            color = "b";
        if (i == 1)
            color = "c";
        if (i == 2)
            color = "d";
        if (i == 7)
            color = "e";
        return "&" + color + "&l" + i;
    }

    public String prefix(Player p) {
        String rank = "";
        if (p.getName().equalsIgnoreCase("Dxrk")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&d&l&ki&f&lOwner&d&l&ki&r ");
        } else if (p.getName().equalsIgnoreCase("_Lone_Ninja_")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&f&k&l;&c&lAdmin&f&k&l;&r ");
        }else if (p.getName().equalsIgnoreCase("Pikashoo")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&f&k&l;&a&lDeveloper&f&k&l;&r ");
        } else if (p.hasPermission("Rank.Manager")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&f&l&k;&5&lManager&f&l&k;&r ");
        } else if (p.hasPermission("Rank.Mod")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&b&l&k;&9&lMod&b&l&k;&r ");
        } else if (p.hasPermission("Rank.Builder")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&2&l&k;&a&lBuilder&2&l&k;&r ");
        } else if (p.hasPermission("Rank.Helper")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&6&l&k;&e&lHelper&6&l&k;&r ");
        } else if (p.hasPermission("Rank.genesis")) {
            rank = CMDOptions.TagColor(settings.getOptions().getString(p.getUniqueId().toString() + ".GenesisColor")) + " ";
        } else if (p.hasPermission("Rank.olympian")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&6&ki&e&lOlympian&6&ki&r ");
        } else if (p.hasPermission("Rank.god")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&d&lGod ");
        } else if (p.hasPermission("Rank.titan")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&3&lTitan ");
        } else if (p.hasPermission("Rank.demi-god")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&5&lDemi-God ");
        } else if (p.hasPermission("Rank.hero")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&c&lHero ");
        } else if (p.hasPermission("Rank.mvp")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&6&lMVP ");
        } else if (p.hasPermission("Rank.vip")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&a&lVIP ");
        } else if (p.hasPermission("Rank.sponsor")) {
            rank = ChatColor.translateAlternateColorCodes('&', "&b&lSponsor ");
        }
        return rank;
    }

    public static String format(double amt) {
        if (amt >= 1.0E15D)
            return String.format("%.1f Quad", amt / 1.0E15D);
        if (amt >= 1.0E12D)
            return String.format("%.1f Tril", amt / 1.0E12D);
        if (amt >= 1.0E9D)
            return String.format("%.1f Bil", amt / 1.0E9D);
        if (amt >= 1000000.0D)
            return String.format("%.1f Mil", amt / 1000000.0D);
        return NumberFormat.getNumberInstance(Locale.US).format(amt);
    }

    public ItemStack getHover(Player p) {
        ItemStack i = new ItemStack(Material.PAPER, 1);
        ItemMeta im = i.getItemMeta();
        im.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&fName &7" + p.getName()));
        List<String> lore = new ArrayList<>();
        lore.add(c("&fPrestiges &7" + PlayerDataHandler.getInstance().getPlayerData(p).getInt(p.getUniqueId().toString() + ".Prestiges")));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&fLevel &7» " + RankupHandler.getInstance().getRank(p)));
        if (prefix(p) != null) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&fRank &7» " + prefix(p)));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&fRank &7» "));
        }
        if (this.gang.getGang(p) != null) {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&fGang &7» &b" + gang.getGang(p)));
        } else {
            lore.add(ChatColor.translateAlternateColorCodes('&', "&fGang &7» "));
        }
        lore.add(ChatColor.translateAlternateColorCodes('&', "&fBalance &7» &a$" + format(Main.econ.getBalance(p))));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&fTokens &7» &e⛀" + Main.formatAmt(Tokens.getInstance().getTokens(p))));
        lore.add(ChatColor.translateAlternateColorCodes('&', "&fBlocks Mined &7» &b" + PlayerDataHandler.getInstance().getPlayerData(p).getInt("BlocksBroken")));
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', "&7Click to visit mine"));
        im.setLore(lore);
        i.setItemMeta(im);
        return i;
    }

    public static ArrayList<Player> ac = new ArrayList<>();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("MuteChat")) {
            if (!sender.hasPermission("Chat.Mute"))
                return false;
            if (!isMuted) {
                isMuted = true;
                Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD + sender.getName() + " has muted chat!");
            } else {
                isMuted = false;
                Bukkit.broadcastMessage(ChatColor.GRAY + "" + ChatColor.BOLD + sender.getName() + " has unmuted chat!");
            }
        }
        if (cmd.getName().equalsIgnoreCase("Chat") &&
                sender instanceof Player) {
            Player p = (Player) sender;
            openToggleOptions(p);
        }
        if (cmd.getName().equalsIgnoreCase("ChatColor")) {
            Player p = (Player) sender;
            openColorInv(p);
        }
        return false;
    }

    static Random r = new Random();


    @EventHandler
    public void NameHover(AsyncPlayerChatEvent event) {
        cchat = false;
        String name, s;
        Player p = event.getPlayer();
        if (event.isCancelled())
            return;
        if (isMuted &&
                !p.hasPermission("A")) {
            event.setCancelled(true);
            return;
        }
        if (event.getMessage().contains("@"))
            for (Player pp : Bukkit.getOnlinePlayers()) {
                if (event.getMessage().contains("@" + pp.getName()))
                    pp.playSound(pp.getLocation(), Sound.CLICK, 1.0F, 1.0F);
            }
        if (!p.hasPermission("rank.vip"))
            if (this.waiting.containsKey(event.getPlayer())) {
                long time = this.waiting.get(event.getPlayer());
                long currentime = (new Date()).getTime();
                long ms = 1000L;
                if (currentime > time + ms) {
                    this.waiting.remove(event.getPlayer());
                    this.waiting.put(event.getPlayer(), (new Date()).getTime());
                } else {
                    long msrem = time + ms - currentime;
                    String cooldownmsg = ChatColor.translateAlternateColorCodes('&', "&7Please wait &c" + msrem + " ms &7till you send another message.");
                    String donormsg = ChatColor.translateAlternateColorCodes('&', "&aVIP Rank &7and higher can bypass this wait.");
                    event.getPlayer().sendMessage(cooldownmsg);
                    event.getPlayer().sendMessage(donormsg);
                    event.setCancelled(true);
                    return;
                }
            } else {
                this.waiting.put(event.getPlayer(), (new Date()).getTime());
            }
        event.setCancelled(true);
        String prestige;
        String rank = "";
        if (PlayerDataHandler.getInstance().getPlayerData(p).getBoolean("Ethereal")) {
            prestige = c("&e&l&oE&7-&b&l&o" + RankupHandler.getInstance().getRank(p) + " ");
        } else {
            prestige = c("&8[&a" + PlayerDataHandler.getInstance().getPlayerData(p).getInt("Prestiges") + "&8] ");

            rank = ChatColor.translateAlternateColorCodes('&', "&8[&b" + RankupHandler.getInstance().getRank(p) + "&8] ");
        }


        String prefix = prefix(p);
        if (this.settings.getcolor().getString(p.getName() + ".Nickname") == null) {
            name = ChatColor.GRAY + p.getName();
        } else {
            name = ChatColor.translateAlternateColorCodes('&', this.settings.getcolor().getString(p.getName() + ".Nickname"));
        }
        String suffix = null;
        if (this.settings.getData().contains(p.getUniqueId().toString() + ".Tag"))
            suffix = ChatColor.translateAlternateColorCodes('&', this.settings.getData().getString(p.getUniqueId().toString() + ".Tag"));
        String msg = event.getMessage();
        int caps = 0;
        byte b;
        int i;
        char[] arrayOfChar;
        for (i = (arrayOfChar = msg.toCharArray()).length, b = 0; b < i; ) {
            char c = arrayOfChar[b];
            if (Character.isUpperCase(c))
                caps++;
            b = (byte) (b + 1);
        }
        if (caps > 200 && !p.isOp()) {
            s = event.getMessage().toLowerCase();
        } else {
            s = event.getMessage();
        }
        for (Player ps : event.getRecipients()) {
            FancyMessage chatFormat = new FancyMessage("");
            StringBuilder firstpart = new StringBuilder();
            if (getOption(ps, "Level"))
                firstpart.append(prestige);
            if (getOption(ps, "Level"))
                firstpart.append(rank);
            if (prefix(p) != null &&
                    getOption(ps, "Donor"))
                firstpart.append(prefix);
            if (!getOption(ps, "Nickname")) {
                firstpart.append(ChatColor.GRAY).append(p.getName());
            } else {
                firstpart.append(name);
            }
            if (suffix != null &&
                    getOption(ps, "Tag"))
                firstpart.append(suffix);
            String First = firstpart.toString();
            chatFormat.then(First).itemTooltip(getHover(p)).command("/stats " + p.getName());
            chatFormat.then(ChatColor.translateAlternateColorCodes('&', " &8» "));

            if (ChatColor.stripColor(s).toLowerCase().contains("[item]") && p.getItemInHand() != null && p.getItemInHand().getType() != Material.AIR) {
                String ss;
                if (!p.getItemInHand().getItemMeta().hasDisplayName()) {
                    ss = c("&7»&b" + p.getItemInHand().getType().toString().toLowerCase().replace("_", " ") + "&7«&ex" + p.getItemInHand().getAmount());
                    chatFormat.then(ss).itemTooltip(p.getItemInHand());
                } else {
                    ss = c("&7»" + p.getItemInHand().getItemMeta().getDisplayName() + "&7«&ex" + p.getItemInHand().getAmount());
                    chatFormat.then(ss).itemTooltip(p.getItemInHand());
                }
            }


            if (p.hasPermission("Epsilon.ChatColor")) {
                if (getChatColor(p) != null) {
                    if (getChatColor(p).equals("Apollo")) {
                        boolean w = false;
                        StringBuilder sb = new StringBuilder();
                        byte b1;
                        int j;
                        char[] arrayOfChar1;
                        for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
                            char ss = arrayOfChar1[b1];
                            if (!w) {
                                sb.append(ChatColor.GOLD).append(ss);
                                w = true;
                            } else {
                                sb.append(ChatColor.YELLOW).append(ss);
                                w = false;
                            }
                            b1 = (byte) (b1 + 1);
                        }
                        chatFormat.then(sb.toString());
                    } else if (getChatColor(p).equals("Kronos")) {
                        boolean w = false;
                        StringBuilder sb = new StringBuilder();
                        byte b1;
                        int j;
                        char[] arrayOfChar1;
                        for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
                            char ss = arrayOfChar1[b1];
                            if (!w) {
                                sb.append(ChatColor.DARK_GRAY).append(ss);
                                w = true;
                            } else {
                                sb.append(ChatColor.GRAY).append(ss);
                                w = false;
                            }
                            b1 = (byte) (b1 + 1);
                        }
                        chatFormat.then(sb.toString());
                    } else if (getChatColor(p).equals("Zeus")) {
                        boolean w = false;
                        StringBuilder sb = new StringBuilder();
                        byte b1;
                        int j;
                        char[] arrayOfChar1;
                        for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
                            char ss = arrayOfChar1[b1];
                            if (!w) {
                                sb.append(ChatColor.WHITE).append(ss);
                                w = true;
                            } else {
                                sb.append(ChatColor.GRAY).append(ss);
                                w = false;
                            }
                            b1 = (byte) (b1 + 1);
                        }
                        chatFormat.then(sb.toString());
                    } else {
                        boolean w = false;
                        StringBuilder sb = new StringBuilder();
                        byte b1;
                        int k;
                        char[] arrayOfChar1;
                        for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
                            char ss = arrayOfChar1[b1];
                            sb.append(ChatColor.translateAlternateColorCodes('&', ss + ""));
                            w = !w;
                            b1 = (byte) (b1 + 1);
                        }
                        chatFormat.then(sb.toString());
                    }
                } else if (p.getName().equalsIgnoreCase("Kevin20230") || p.getName().equalsIgnoreCase("Dxrk")) {
                    StringBuilder sb = new StringBuilder();
                    byte b1;
                    int j;
                    char[] arrayOfChar1;
                    for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
                        char ss = arrayOfChar1[b1];
                        sb.append(ChatColor.WHITE + "" + ChatColor.BOLD).append(ss);
                        b1 = (byte) (b1 + 1);
                    }
                    chatFormat.then(sb.toString());
                }else if (p.getName().equalsIgnoreCase("Kevin20230") || p.getName().equalsIgnoreCase("_Lone_Ninja_")) {
                    StringBuilder sb = new StringBuilder();
                    byte b1;
                    int j;
                    char[] arrayOfChar1;
                    for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
                        char ss = arrayOfChar1[b1];
                        sb.append(ChatColor.RED + "" + ChatColor.BOLD).append(ss);
                        b1 = (byte) (b1 + 1);
                    }
                    chatFormat.then(sb.toString());
                }else if (p.getName().equalsIgnoreCase("Kevin20230") || p.getName().equalsIgnoreCase("Pikashoo")) {
                    StringBuilder sb = new StringBuilder();
                    byte b1;
                    int j;
                    char[] arrayOfChar1;
                    for (j = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < j; ) {
                        char ss = arrayOfChar1[b1];
                        sb.append(ChatColor.WHITE + "" + ChatColor.BOLD).append(ss);
                        b1 = (byte) (b1 + 1);
                    }
                    chatFormat.then(sb.toString());
                } else if (p.hasPermission("chat.admin")) {
                    boolean w = false;
                    StringBuilder sb = new StringBuilder();
                    byte b1;
                    int k;
                    char[] arrayOfChar1;
                    for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
                        char ss = arrayOfChar1[b1];
                        sb.append(ChatColor.RED + "" + ChatColor.BOLD).append(ss);
                        w = !w;
                        b1 = (byte) (b1 + 1);
                    }
                    chatFormat.then(sb.toString());
                } else if (p.hasPermission("chat.Manager")) {
                    boolean w = false;
                    StringBuilder sb = new StringBuilder();
                    byte b1;
                    int k;
                    char[] arrayOfChar1;
                    for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
                        char ss = arrayOfChar1[b1];
                        sb.append(ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD).append(ss);
                        w = !w;
                        b1 = (byte) (b1 + 1);
                    }
                    chatFormat.then(sb.toString());
                } else {
                    boolean w = false;
                    StringBuilder sb = new StringBuilder();
                    byte b1;
                    int k;
                    char[] arrayOfChar1;
                    for (k = (arrayOfChar1 = s.replace("[item]", "").toCharArray()).length, b1 = 0; b1 < k; ) {
                        char ss = arrayOfChar1[b1];
                        sb.append(ChatColor.translateAlternateColorCodes('&', ss + ""));
                        if (!w) {
                            w = true;
                        } else {
                            w = false;
                        }
                        b1 = (byte) (b1 + 1);
                    }
                    chatFormat.then(sb.toString());
                }
                if (p.hasPermission("Rank.Owner")) {
                    chatFormat.color(ChatColor.DARK_RED);
                } else if (p.getName().equalsIgnoreCase("BakonStirp")) {
                    chatFormat.color(ChatColor.DARK_PURPLE);
                } else if (p.hasPermission("Rank.Admin")) {
                    chatFormat.color(ChatColor.RED).style(ChatColor.BOLD);
                } else if (p.hasPermission("Rank.MOD")) {
                    chatFormat.color(ChatColor.AQUA).style(ChatColor.BOLD);
                } else if (p.hasPermission("Rank.Helper")) {
                    chatFormat.color(ChatColor.YELLOW).style(ChatColor.BOLD);
                } else if (getChatColor(p) == null || getChatColor(p).equals("Aqua")) {
                    chatFormat.color(ChatColor.AQUA);
                } else if (getChatColor(p).equalsIgnoreCase("White")) {
                    chatFormat.color(ChatColor.WHITE);
                } else if (getChatColor(p).equalsIgnoreCase("Yellow")) {
                    chatFormat.color(ChatColor.YELLOW);
                } else if (getChatColor(p).equalsIgnoreCase("Blue")) {
                    chatFormat.color(ChatColor.BLUE);
                } else if (getChatColor(p).equalsIgnoreCase("Green")) {
                    chatFormat.color(ChatColor.DARK_GREEN);
                } else if (getChatColor(p).equalsIgnoreCase("Purple")) {
                    chatFormat.color(ChatColor.DARK_PURPLE);
                } else if (getChatColor(p).equalsIgnoreCase("Gold")) {
                    chatFormat.color(ChatColor.GOLD);
                } else if (getChatColor(p).equalsIgnoreCase("Red")) {
                    chatFormat.color(ChatColor.RED);
                } else if (getChatColor(p).equalsIgnoreCase("Lime")) {
                    chatFormat.color(ChatColor.GREEN);
                }

            }
            if (!CMDAc.ac.contains(p)) {
                if (!CMDGang.gangchat.contains(p)) {
                    chatFormat.send(ps);
                    if (cchat == false) {
                        chatFormat.send(Bukkit.getConsoleSender());
                        cchat = true;
                    }
                } else {
                    String gangg = gang.getGang(p);

                    if (gang.getGang(ps).equals(gang.getGang(p))) {
                        ps.sendMessage(c("&a" + gang.getGang(p) + "&7 x &b" + p.getName() + " &7" + msg));
                    }

                }
            } else {

                if (ps.hasPermission("staffchat.use")) {
                    ps.sendMessage(c("&c&l&4&lStaffchat&c&l&r &e" + p.getName() + " &7  &c" + msg));
                }
            }

        }
    }

    private boolean cchat = false;

}
