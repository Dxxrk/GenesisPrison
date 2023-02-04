package me.dxrk.Gangs;

import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CMDGang implements Listener, CommandExecutor {

    static CMDGang instance = new CMDGang();

    public static CMDGang getInstance() {
        return instance;
    }
    Methods m = Methods.getInstance();
    SettingsManager settings = SettingsManager.getInstance();
    Gangs g = Gangs.getInstance();

    public static List<Player> gangchat = new ArrayList<>();
    public static HashMap<String, List<Player>> invited = new HashMap<>();

    public void sendGang(Player p, OfflinePlayer pp) {
        String gang = g.getGang(pp);
        p.sendMessage(m.c("&3Gang: &b"+gang));
        OfflinePlayer owner = Bukkit.getOfflinePlayer((UUID.fromString(settings.getGangs().getString(gang+".Owner"))));
        p.sendMessage(m.c("&3Owner: &b"+owner.getName()));
        p.sendMessage(m.c("&3Level: &b"+settings.getGangs().getInt(gang+".Level")));
        p.sendMessage(m.c("&3Bank: &b"+settings.getGangs().getInt(gang+".GangTokens")));
        List<String> members = settings.getGangs().getStringList(gang+".Members");
        StringBuilder sb = new StringBuilder();
        for(String s : members) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
            if(Bukkit.getOnlinePlayers().contains(player.getPlayer())) {
                sb.append(m.c("&a"+player.getName())).append(m.c("&7,"));
            } else {
                sb.append(m.c("&c"+player.getName())).append(m.c("&7,"));
            }
        }
        p.sendMessage(m.c("&3Members: "+ sb));
    }
    public void sendGang(Player p, String gang) {
        p.sendMessage(m.c("&3Gang: &b"+gang));
        OfflinePlayer owner = Bukkit.getOfflinePlayer((UUID.fromString(settings.getGangs().getString(gang+".Owner"))));
        p.sendMessage(m.c("&3Owner: &b"+owner.getName()));
        p.sendMessage(m.c("&3Level: &b"+settings.getGangs().getInt(gang+".Level")));
        p.sendMessage(m.c("&3Bank: &b"+settings.getGangs().getInt(gang+".GangTokens")));
        List<String> members = settings.getGangs().getStringList(gang+".Members");
        StringBuilder sb = new StringBuilder();
        for(String s : members) {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
            if(Bukkit.getOnlinePlayers().contains(player.getPlayer())) {
                sb.append(m.c("&a"+player.getName())).append(m.c("&7,"));
            } else {
                sb.append(m.c("&c"+player.getName())).append(m.c("&7,"));
            }
        }
        p.sendMessage(m.c("&3Members: "+ sb));
    }
    public void sendGangHelp(Player p) {
        p.sendMessage(m.c("&3&lGang Commands:"));
        p.sendMessage(m.c("&3/Gang Help: &bOpen this message."));
        p.sendMessage(m.c("&3/Gang: &bView your gang's info."));
        p.sendMessage(m.c("&3/Gang Create <Name>: &bCreate a gang."));
        p.sendMessage(m.c("&3/Gang Info <Player/Gang>: &bView the info of another gang."));
        p.sendMessage(m.c("&3/Gang Upgrade(s): &bOpen the gang upgrades to see your progress."));
        p.sendMessage(m.c("&3/Gang Invite <Player>: &bInvite another player to your gang."));
        p.sendMessage(m.c("&3/Gang SetOwner: &bChange your gangs ownership. Must be the owner of your gang."));
        p.sendMessage(m.c("&3/Gang Kick <Player>: &bView your gang's info."));
        p.sendMessage(m.c("&3/Gang Leave: &bLeave your current gang."));
    }
    public void openGangShop(Player p) {
        String gang = g.getGang(p);
        Inventory gangshop = Bukkit.createInventory(null, 18, m.c("&3&l"+gang+"'s &b&lShop"));
        List<String> perks = settings.getGangs().getStringList(gang+".PerksUnlocked");

    }

    @EventHandler
    public void onInv(InventoryClickEvent e) {
        Player p = (Player)e.getWhoClicked();
        String gang = g.getGang(p);


    }

    public void setLevel(String gang) {

    }


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        if(g.hasGang(p)) {
            String gang = g.getGang(p);
            int blocksbroken = settings.getGangs().getInt(gang+".BlocksBroken");
            settings.getGangs().set(gang+".BlocksBroken", blocksbroken+1);
        }
    }


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("gang")) {
            Player p = (Player)sender;
            if(args.length == 0) {
                if(g.hasGang(p)) {
                    sendGang(p, p);
                } else {
                    sendGangHelp(p);
                }
                return false;
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("help")) {
                sendGangHelp(p);
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("invite")) {
                if(g.hasGang(p) && !settings.getGangs().getString(g.getGang(p)+".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can invite."));
                }
                else if(g.hasGang(p) && settings.getGangs().getString(g.getGang(p)+".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bPlease Specify a player to invite."));
                }
                else {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                }
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("invite")) {
                if(g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                if(g.hasGang(p) && !settings.getGangs().getString(g.getGang(p)+".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can invite."));
                    return false;
                }

                Player invite = Bukkit.getPlayer(args[1]);
                List<Player> invitees = invited.get(g.getGang(p));
                invitees.add(invite);
                invited.put(g.getGang(p), invitees);
                invite.sendMessage(m.c("&f&lGangs &8| &b"+p.getName()+" Has invited you to join &a"+g.getGang(p)));
                invite.sendMessage(m.c("&f&lGangs &8| &bDo /gang join &a"+g.getGang(p)+" &bto join."));
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("join")) {
                String gang = args[1];
                if(invited.get(gang).contains(p)) {
                    g.addMember(p, gang);
                    p.sendMessage(m.c("&f&lGangs &8| &bJoined &a"+gang));
                } else {
                    p.sendMessage(m.c("&f&lGangs &8| &a"+gang+" &bHas not invited you."));
                }
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("create")) {
                if(g.hasGang(p))
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are already in a gang."));
                else {
                    p.sendMessage(m.c("&f&lGangs &8| &bPlease Specify a Name(Case Sensitive)."));
                }
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("create")) {
                if(g.hasGang(p)) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are already in a gang."));
                    return false;
                }

                for(String name : settings.getGangs().getKeys(false)) {
                    if(name.equalsIgnoreCase(args[1])) {
                        p.sendMessage(m.c("&f&lGangs &8| &bA gang with that name already exists."));
                        return false;
                    }
                }
                g.createGang(p, args[1]);
                p.sendMessage(m.c("&f&lGangs &8| &a"+args[1]+" &bHas been created."));
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("upgrade")) {
                if(g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                openGangShop(p);
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("upgrades")) {
                if(g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                openGangShop(p);
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("info")) {
                if(settings.getGangs().getKeys(false).contains(args[1])) {
                    sendGang(p, args[1]);
                    return false;
                }
                boolean online = false;
                OfflinePlayer op = null;
                for(String uuid : settings.getPlayerData().getKeys(false)) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
                    if(args[1].equalsIgnoreCase(player.getName()) && g.hasGang(player)) {
                        online = true;
                        op = player;
                    }
                }
                if(online == true) {
                    sendGang(p, op);
                }
                else {
                    p.sendMessage(m.c("&f&lGangs &8| &bCould not find that gang."));
                }
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("kick")) {
                String gang = g.getGang(p);
                if(!g.hasGang(p)) return false;
                if(g.hasGang(p) && !settings.getGangs().getString(g.getGang(p)+".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can kick."));
                    return false;
                }
                List<String> members = settings.getGangs().getStringList(gang+".Members");
                for(String s: members) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
                    if(args[1].equalsIgnoreCase(player.getName())) {
                        members.remove(s);
                        settings.getPlayerData().set(s+".Gang", "");
                        settings.savePlayerData();
                    }
                }
                settings.getGangs().set(gang+".Members", members);
                settings.saveGangs();
            }
            if(args.length == 2 && args[0].equalsIgnoreCase("setowner")) {
                if(!g.hasGang(p)) return false;
                if(g.hasGang(p) && !settings.getGangs().getString(g.getGang(p)+".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner of the gang can change ownership."));
                    return false;
                }
                String gang = g.getGang(p);
                List<String> members = settings.getGangs().getStringList(gang+".Members");
                for(String s: members) {
                    OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
                    if (args[1].equalsIgnoreCase(player.getName())) {
                        g.changeOwner(p, player.getUniqueId().toString(), gang);
                    }
                }
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("delete")) {
                String gang = g.getGang(p);
                if(!g.hasGang(p)) return false;
                if(g.hasGang(p) && !settings.getGangs().getString(g.getGang(p)+".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bOnly the Owner  can delete the gang."));
                    return false;
                }
                settings.getGangs().set(gang, null);
                settings.getPlayerData().set(p.getUniqueId().toString()+".Gang", "");
            }
            if(args.length == 1 && args[0].equalsIgnoreCase("leave")) {
                if(g.hasGang(p) == false) {
                    p.sendMessage(m.c("&f&lGangs &8| &bYou are not in a gang."));
                    return false;
                }
                if(g.hasGang(p) && settings.getGangs().getString(g.getGang(p)+".Owner").equals(p.getUniqueId().toString())) {
                    p.sendMessage(m.c("&f&lGangs &8| &bThe owner cannot leave the gang."));
                    return false;
                }
                g.removeMember(p, g.getGang(p));
            }
        }

        return false;
    }


}
