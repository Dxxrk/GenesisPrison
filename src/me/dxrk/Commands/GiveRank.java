package me.dxrk.Commands;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class GiveRank implements CommandExecutor, Listener {
  public void giveAngelic(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.angelic");
    commands.add("manuaddp " + p.getName() + " sellall.angelic");
    commands.add("manuaddp " + p.getName() + " chat.angelic");
    commands.add("manuaddp " + p.getName() + " rank.angelicdaily");
    commands.add("manuaddp " + p.getName() + " rank.angelicweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.angelic");
    commands.add("manuaddp " + p.getName() + " essentials.kits.angelicdaily");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.300");
    commands.add("manuaddp " + p.getName() + " essentials.kits.angelicweekly");
    commands.add("manuaddp " + p.getName() + " essentials.warps.angelic");
    commands.add("manuaddp " + p.getName() + " Upgradepick.30000");
    giveDemonic(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveDemonic(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.demonic");
    commands.add("manuaddp " + p.getName() + " sellall.demonic");
    commands.add("manuaddp " + p.getName() + " chat.demonic");
    commands.add("manuaddp " + p.getName() + " rank.demonicdaily");
    commands.add("manuaddp " + p.getName() + " rank.demonicweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.demonic");
    commands.add("manuaddp " + p.getName() + " essentials.kits.demonicdaily");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.200");
    commands.add("manuaddp " + p.getName() + " essentials.kits.demonicweekly");
    commands.add("manuaddp " + p.getName() + " essentials.warps.demonic");
    commands.add("manuaddp " + p.getName() + " Upgradepick.20000");
    commands.add("manuaddp " + p.getName() + " sellall.godfather");
    giveGodfather(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveGodfather(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.godfather");
    commands.add("manuaddp " + p.getName() + " chat.godfather");
    commands.add("manuaddp " + p.getName() + " rank.godfatherdaily");
    commands.add("manuaddp " + p.getName() + " rank.godfatherweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.godfatherdaily");
    commands.add("manuaddp " + p.getName() + " essentials.kits.godfather");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.100");
    commands.add("manuaddp " + p.getName() + " essentials.kits.godfatherweekly");
    commands.add("manuaddp " + p.getName() + " essentials.warps.godfather");
    commands.add("manuaddp " + p.getName() + " Upgradepick.10000");
    commands.add("manuaddp " + p.getName() + " sellall.godfather");
    giveBaron(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveBaron(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.baron");
    commands.add("manuaddp " + p.getName() + " rank.barondaily");
    commands.add("manuaddp " + p.getName() + " rank.baronweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.baron");
    commands.add("manuaddp " + p.getName() + " essentials.kits.barondaily");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.50");
    commands.add("manuaddp " + p.getName() + " essentials.kits.baronweekly");
    commands.add("manuaddp " + p.getName() + " essentials.warps.baron");
    commands.add("manuaddp " + p.getName() + " Upgradepick.100000");
    commands.add("manuaddp " + p.getName() + " sellall.baron");
    giveMythic(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveMythic(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.mythic");
    commands.add("manuaddp " + p.getName() + " rank.mythicdaily");
    commands.add("manuaddp " + p.getName() + " rank.mythicweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.mythic");
    commands.add("manuaddp " + p.getName() + " essentials.kits.mythicdaily");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.10");
    commands.add("manuaddp " + p.getName() + " essentials.kits.mythicweekly");
    commands.add("manuaddp " + p.getName() + " essentials.warps.mythic");
    commands.add("manuaddp " + p.getName() + " Upgradepick.3000");
    commands.add("manuaddp " + p.getName() + " sellall.mythic");
    giveHero(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveHero(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.hero");
    commands.add("manuaddp " + p.getName() + " rank.herodaily");
    commands.add("manuaddp " + p.getName() + " rank.heroweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.hero");
    commands.add("manuaddp " + p.getName() + " essentials.kits.herodaily");
    commands.add("manuaddp " + p.getName() + " essentials.kits.heroweekly");
    commands.add("manuaddp " + p.getName() + " essentials.warps.hero");
    commands.add("manuaddp " + p.getName() + " Upgradepick.2000");
    commands.add("manuaddp " + p.getName() + " essentials.repair");
    commands.add("manuaddp " + p.getName() + " worldedit.history.undo");
    commands.add("manuaddp " + p.getName() + " worldedit.regions.pos");
    commands.add("manuaddp " + p.getName() + " worldedit.region.set");
    commands.add("manuaddp " + p.getName() + " worldedit.selection.pos");
    commands.add("manuaddp " + p.getName() + " worldedit.wand");
    commands.add("manuaddp " + p.getName() + " sellall.hero");
    giveLegend(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveLegend(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.legend");
    commands.add("manuaddp " + p.getName() + " rank.legendweekly");
    commands.add("manuaddp " + p.getName() + " rank.emerald");
    commands.add("manuaddp " + p.getName() + " essentials.kits.legend");
    commands.add("manuaddp " + p.getName() + " essentials.kits.legendweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.emerald");
    commands.add("manuaddp " + p.getName() + " essentials.warps.legend");
    commands.add("manuaddp " + p.getName() + " Upgradepick.1500");
    commands.add("manuaddp " + p.getName() + " sellall.legend");
    giveDeluxe(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveDeluxe(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.deluxe");
    commands.add("manuaddp " + p.getName() + " rank.deluxeweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.deluxe");
    commands.add("manuaddp " + p.getName() + " essentials.kits.deluxeweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.red");
    commands.add("manuaddp " + p.getName() + " essentials.warps.deluxe");
    commands.add("manuaddp " + p.getName() + " essentials.repair");
    commands.add("manuaddp " + p.getName() + " Upgradepick.1000");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.5");
    commands.add("manuaddp " + p.getName() + " sellall.deluxe");
    giveMVP(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveMVP(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.MVP");
    commands.add("manuaddp " + p.getName() + " rank.MVPweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.mvp");
    commands.add("manuaddp " + p.getName() + " essentials.kits.mvpweekly");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.4");
    commands.add("manuaddp " + p.getName() + " essentials.warps.mvp");
    commands.add("manuaddp " + p.getName() + " Upgradepick.750");
    commands.add("manuaddp " + p.getName() + " sellall.mvp");
    giveChampion(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveChampion(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.Champion");
    commands.add("manuaddp " + p.getName() + " rank.diamond");
    commands.add("manuaddp " + p.getName() + " Nickname.Select");
    commands.add("manuaddp " + p.getName() + " rank.championweekly");
    commands.add("manuaddp " + p.getName() + " essentials.kits.champion");
    commands.add("manuaddp " + p.getName() + " essentials.kits.diamond");
    commands.add("manuaddp " + p.getName() + " essentials.kits.championweekly");
    commands.add("manuaddp " + p.getName() + " essentials.fly");
    commands.add("manuaddp " + p.getName() + " autoflight.allow");
    commands.add("manuaddp " + p.getName() + " essentials.kits.diamond");
    commands.add("manuaddp " + p.getName() + " essentials.warps.champion");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.3");
    commands.add("manuaddp " + p.getName() + " Upgradepick.500");
    commands.add("manuaddp " + p.getName() + " sellall.champion");
    givePremier(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void givePremier(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.Premier");
    commands.add("manuaddp " + p.getName() + " rank.gold");
    commands.add("manuaddp " + p.getName() + " essentials.kits.premier");
    commands.add("manuaddp " + p.getName() + " essentials.warps.premier");
    commands.add("manuaddp " + p.getName() + " epsilon.nofall");
    commands.add("manuaddp " + p.getName() + " Upgradepick.400");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.2");
    commands.add("manuaddp " + p.getName() + " sellall.premier");
    giveVIP(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveVIP(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.VIP");
    commands.add("manuaddp " + p.getName() + " essentials.kits.VIP");
    commands.add("manuaddp " + p.getName() + " playervaults.amount.1");
    commands.add("manuaddp " + p.getName() + " essentials.warps.VIP");
    commands.add("manuaddp " + p.getName() + " rank.iron");
    commands.add("manuaddp " + p.getName() + " essentials.kits.iron");
    commands.add("manuaddp " + p.getName() + " essentials.hat");
    commands.add("manuaddp " + p.getName() + " sellall.vip");
    commands.add("manuaddp " + p.getName() + " Upgradepick.300");
    commands.add("manuaddp " + p.getName() + " essetials.kits.iron");
    giveSponsor(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveSponsor(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.sponsor");
    commands.add("manuaddp " + p.getName() + " rank.coal");
    commands.add("manuaddp " + p.getName() + " essentials.kits.sponsor");
    commands.add("manuaddp " + p.getName() + " essentials.kits.coal");
    commands.add("manuaddp " + p.getName() + " essentials.warps.sponsor");
    commands.add("manuaddp " + p.getName() + " sellall.sponsor");
    commands.add("manuaddp " + p.getName() + " Upgradepick.150");
    commands.add("manuaddp " + p.getName() + " essetials.kits.coal");
    giveDonor(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveDonor(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.donor");
    commands.add("manuaddp " + p.getName() + " essentials.kits.donor");
    commands.add("manuaddp " + p.getName() + " essentials.warps.donor");
    commands.add("manuaddp " + p.getName() + " essentials.feed");
    commands.add("manuaddp " + p.getName() + " essentials.hat");
    commands.add("manuaddp " + p.getName() + " sellall.donor");
    commands.add("manuaddp " + p.getName() + " Upgradepick.150");
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveDrugLord(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.druglord");
    commands.add("manuaddp " + p.getName() + " essentials.kits.druglord");
    commands.add("manuaddp " + p.getName() + " essentials.warps.druglord");
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveHelper(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.helper");
    commands.add("manuaddp " + p.getName() + " essentials.kick");
    commands.add("manuaddp " + p.getName() + " epsilon.adminchat");
    commands.add("manuaddp " + p.getName() + " essentials.mute");
    commands.add("manuaddp " + p.getName() + " chat.staff");
    commands.add("manuaddp " + p.getName() + " ab.warn.perma");
    commands.add("manuaddp " + p.getName() + " ab.mute.perma");
    commands.add("manuaddp " + p.getName() + " maxbans.warn");
    commands.add("manuaddp " + p.getName() + " maxbans.kick");
    commands.add("manuaddp " + p.getName() + " maxbans.tempban");
    commands.add("manuaddp " + p.getName() + " ab.mute.undo");
    commands.add("manuaddp " + p.getName() + " ab.warn.undo");
    commands.add("manuaddp " + p.getName() + " ab.warns.other");
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveMod(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.Mod");
    commands.add("manuaddp " + p.getName() + " essentials.kick");
    commands.add("manuaddp " + p.getName() + " essentials.mute");
    commands.add("manuaddp " + p.getName() + " essentials.tempban");
    commands.add("manuaddp " + p.getName() + " essentials.ban");
    commands.add("manuaddp " + p.getName() + " essentials.unban");
    commands.add("manuaddp " + p.getName() + " ab.ban.perma");
    commands.add("manuaddp " + p.getName() + " ab.ban.ip");
    commands.add("manuaddp " + p.getName() + " maxbans.tempban");
    commands.add("manuaddp " + p.getName() + " maxbans.ban");
    commands.add("manuaddp " + p.getName() + " maxbans.ban.undo");
    commands.add("manuaddp " + p.getName() + " maxbans.unban");
    commands.add("manuaddp " + p.getName() + " maxbans.unwarn");
    giveHelper(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public void giveAdmin(Player p) {
    ArrayList<String> commands = new ArrayList<String>();
    commands.add("manuaddp " + p.getName() + " rank.admin");
    commands.add("manuaddp " + p.getName() + " essentials.*");
    commands.add("manuaddp " + p.getName() + " epsilon.tp");
    commands.add("manuaddp " + p.getName() + " essentials.tempban");
    commands.add("manuaddp " + p.getName() + " essentials.ban");
    commands.add("manuaddp " + p.getName() + " maxbans.*");
    commands.add("manuaddp " + p.getName() + " essentials.unban");
    giveMod(p);
    for (String command : commands)
      Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getServer().getConsoleSender(), command); 
  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (cmd.getName().equalsIgnoreCase("GiveRank") && 
      sender.hasPermission("Give.Ranks")) {
      if (args.length != 2) {
        sender.sendMessage(ChatColor.RED + "/GiveRank <Player> <Rank>");
        return false;
      } 
      Player p = null;
      try {
        p = Bukkit.getPlayer(args[0]);
        p.sendMessage(ChatColor.GRAY + "Loading rank");
      } catch (Exception e) {
        sender.sendMessage(ChatColor.RED + "Player not found");
        return false;
      } 
      if (args[1].equalsIgnoreCase("Angelic")) {
        giveAngelic(p);
      } else if (args[1].equalsIgnoreCase("Demonic")) {
        giveDemonic(p);
      } else if (args[1].equalsIgnoreCase("Godfather")) {
        giveGodfather(p);
      } else if (args[1].equalsIgnoreCase("Baron")) {
        giveBaron(p);
      } else if (args[1].equalsIgnoreCase("Mythic")) {
        giveMythic(p);
      } else if (args[1].equalsIgnoreCase("Hero")) {
        giveHero(p);
      } else if (args[1].equalsIgnoreCase("Legend")) {
        giveLegend(p);
      } else if (args[1].equalsIgnoreCase("Deluxe")) {
        giveDeluxe(p);
      } else if (args[1].equalsIgnoreCase("MVP")) {
        giveMVP(p);
      } else if (args[1].equalsIgnoreCase("Champion")) {
        giveChampion(p);
      } else if (args[1].equalsIgnoreCase("DrugLord")) {
        giveDrugLord(p);
      } else if (args[1].equalsIgnoreCase("Premier")) {
        givePremier(p);
      } else if (args[1].equalsIgnoreCase("VIP")) {
        giveVIP(p);
      } else if (args[1].equalsIgnoreCase("Sponsor")) {
        giveSponsor(p);
      } else if (args[1].equalsIgnoreCase("Donor")) {
        giveDonor(p);
      } else if (args[1].equalsIgnoreCase("Helper")) {
        giveHelper(p);
      } else if (args[1].equalsIgnoreCase("Mod")) {
        giveMod(p);
      } else if (args[1].equalsIgnoreCase("Admin")) {
        giveAdmin(p);
      } else {
        sender.sendMessage(ChatColor.RED + "Rank not found!");
        return false;
      } 
    } 
    return false;
  }
  
  public void playerJoin(PlayerJoinEvent e) {
    Player p = e.getPlayer();
    if (p.hasPermission("rank.admin")) {
      giveAdmin(p);
    } else if (p.hasPermission("rank.mod")) {
      giveMod(p);
    } else if (p.hasPermission("rank.helper")) {
      giveHelper(p);
    } else if (p.hasPermission("rank.godfather")) {
      giveGodfather(p);
    } else if (p.hasPermission("rank.baron")) {
      giveBaron(p);
    } else if (p.hasPermission("rank.mythic")) {
      giveMythic(p);
    } else if (p.hasPermission("rank.hero")) {
      giveHero(p);
    } else if (p.hasPermission("rank.legend")) {
      giveLegend(p);
    } else if (p.hasPermission("rank.deluxe")) {
      giveDeluxe(p);
    } else if (p.hasPermission("rank.mvp")) {
      giveMVP(p);
    } else if (p.hasPermission("rank.champion")) {
      giveChampion(p);
    } else if (p.hasPermission("rank.premier")) {
      givePremier(p);
    } else if (p.hasPermission("rank.vip")) {
      giveVIP(p);
    } else if (p.hasPermission("rank.sponsor")) {
      giveSponsor(p);
    } else if (p.hasPermission("rank.donor")) {
      giveDonor(p);
    } 
  }
}
