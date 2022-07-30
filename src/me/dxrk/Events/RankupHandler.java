package me.dxrk.Events;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Tokens.Tokens;

public class RankupHandler implements Listener, CommandExecutor{
  public SettingsManager settings = SettingsManager.getInstance();
  
  public static RankupHandler instance = new RankupHandler();
  
  public Methods methods = Methods.getInstance();
  
  public static RankupHandler getInstance() {
    return instance;
  }
  
  public static List<String> ranks = new ArrayList<>();
  
  public static List<Double> rankPrices = new ArrayList<>();
  
  public static ArrayList<Player> aru = new ArrayList<>();
  
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e ) {
	  Player p = e.getPlayer();
      int i = this.settings.getRankupPrices().getInt(p.getUniqueId().toString());
	  
	  if(i == 0) {
		  this.settings.getRankupPrices().set(p.getUniqueId().toString(), Integer.valueOf(1));
		  this.settings.saveRankupPrices();
	  }
	  if(!aru.contains(p)) {
		  if(settings.getOptions().getBoolean(p.getUniqueId().toString()+".Autorankup") == true) {
			  aru.add(p);
		  }
	  }
	  if(!SellHandler.autosell.contains(p)) {
		  if(settings.getOptions().getBoolean(p.getUniqueId().toString()+".Autosell") == true) {
			  SellHandler.autosell.add(p);
		  }
	  }
	  p.setAllowFlight(true);
	  p.setFlying(true);
	  
  }
  
  
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
	  this.settings.saveRankupPrices();
	  this.settings.saveOptions();
  }
  public int getRank(Player p) {
	  int i = this.settings.getRankupPrices().getInt(p.getUniqueId().toString());
	  
	  if(i == 0) {
		  return 1;
	  }
	  
	    return this.settings.getRankupPrices().getInt(p.getUniqueId().toString());
	  }
  
  static boolean isMultipleof20(int n) {
	    return n%20 == 0;
	  }
  static boolean isMultipleof50(int n) {
	    return n%50 == 0;
	  }
  static boolean isMultipleof100(int n) {
	    return n%100 == 0;
	  }
  static boolean isMultipleof150(int n) {
	    return n%150 == 0;
	  }
  static boolean isMultipleof200(int n) {
	    return n%200 == 0;
	  }
  static boolean isMultipleof250(int n) {
	    return n%250 == 0;
	  }
  static boolean isMultipleof500(int n) {
	    return n%500 == 0;
	  }
  static boolean isMultipleof1000(int n) {
	    return n%1000 == 0;
	  }
  static boolean isMultipleof2500(int n) {
	    return n%2500 == 0;
	  }
  static boolean isMultipleof5000(int n) {
	    return n%5000 == 0;
	  }
  static boolean isMultipleof10000(int n) {
	    return n%10000 == 0;
	  }
  static boolean isMultipleof10(int n) {
	    return n%10 == 0;
	  }
  
  
  public void upRank(Player p) {
	  
	  
	  
	  
	  if(getRank(p) >= 100) {
		  return;
	  }
	    
	  int i = this.settings.getRankupPrices().getInt(p.getUniqueId().toString());
	    this.settings.getRankupPrices().set(p.getUniqueId().toString(), Integer.valueOf(i + 1));
	    this.settings.saveRankupPrices();
	    
	    p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
  }
  
  public void setRank(Player p, int i) {
	  this.settings.getRankupPrices().set(p.getUniqueId().toString(), i);
	  this.settings.saveRankupPrices();
	  p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
  }
  
  public int nextRank(Player p) {
   
        return (getRank(p)+1);
    
  }
  
  public double rankPrice(Player p) {
	 double price;
	  
	  if(p.hasPermission("prestige.1")) {
		  price = 1e12;
		    return ((100+getRank(p))*price)*1.20;
	  }
	  else if(p.hasPermission("prestige.2")) {
		  price = 1e12;
		    return ((200+getRank(p))*price)*1.40;

	  }
	  else if(p.hasPermission("prestige.3")) {
		  price = 1e12;
		    return ((300+getRank(p))*price)*1.60;

	  }
	  else if(p.hasPermission("prestige.4")) {
		  price = 1e12;
		    return ((400+getRank(p))*price)*1.8;

	  }
	  else if(p.hasPermission("prestige.5")) {
		  price = 1e12;
		    return ((500+getRank(p))*price)*2.0;

	  }
	  else if(p.hasPermission("prestige.6")) {
		  price = 1e12;
		    return ((600+getRank(p))*price)*2.2;

	  }
	  else if(p.hasPermission("prestige.7")) {
		  price = 1e12;
		    return ((700+getRank(p))*price)*2.4;

	  }
	  else if(p.hasPermission("prestige.8")) {
		  price = 1e12;
		    return ((800+getRank(p))*price)*2.6;

	  }
	  else if(p.hasPermission("prestige.9")) {
		  price = 1e12;
		    return ((900+getRank(p))*price)*2.8;

	  }
	  else if(p.hasPermission("prestige.10")) {
		  price = 1e12;
		    return ((1000+getRank(p))*price)*3.0;

	  }
	  else if(p.hasPermission("prestige.11")) {
		  price = 2e12;
		  return ((1100+getRank(p))*price)*3.2;
	  }
	  else if(p.hasPermission("prestige.12")) {
		  price = 2e12;
		  return ((1200+getRank(p))*price)*3.4;
	  }
	  else if(p.hasPermission("prestige.13")) {
		  price = 2e12;
		  return ((1300+getRank(p))*price)*3.6;
	  }
	  else if(p.hasPermission("prestige.14")) {
		  price = 2e12;
		  return ((1400+getRank(p))*price)*3.8;
	  }
	  else if(p.hasPermission("prestige.15")) {
		  price = 2e12;
		  return ((1500+getRank(p))*price)*4.0;
	  }
	  else if(p.hasPermission("prestige.16")) {
		  price = 2e12;
		  return ((1600+getRank(p))*price)*4.2;
	  }
	  else if(p.hasPermission("prestige.17")) {
		  price = 2e12;
		  return ((1700+getRank(p))*price)*4.4;
	  }
	  else if(p.hasPermission("prestige.18")) {
		  price = 2e12;
		  return ((1800+getRank(p))*price)*4.6;
	  }
	  else if(p.hasPermission("prestige.19")) {
		  price = 2e12;
		  return ((1900+getRank(p))*price)*4.8;
	  }
	  else if(p.hasPermission("prestige.20")) {
		  price = 2e12;
		  return ((2000+getRank(p))*price)*5.0;
	  }else if(p.hasPermission("prestige.21")) {
		  price = 4e12;
		  return ((2100+getRank(p))*price)*5.2;
	  }
	  else if(p.hasPermission("prestige.22")) {
		  price = 4e12;
		  return ((2200+getRank(p))*price)*5.4;
	  }
	  else if(p.hasPermission("prestige.23")) {
		  price = 4e12;
		  return ((2300+getRank(p))*price)*5.6;
	  }
	  else if(p.hasPermission("prestige.24")) {
		  price = 4e12;
		  return ((2400+getRank(p))*price)*5.8;
	  }
	  else if(p.hasPermission("prestige.25")) {
		  price = 4e12;
		  return ((2500+getRank(p))*price)*6.0;
	  }
	  else if(p.hasPermission("prestige.26")) {
		  price = 4e12;
		  return ((2600+getRank(p))*price)*6.2;
	  }
	  else if(p.hasPermission("prestige.27")) {
		  price = 4e12;
		  return ((2700+getRank(p))*price)*6.4;
	  }
	  else if(p.hasPermission("prestige.28")) {
		  price = 4e12;
		  return ((2800+getRank(p))*price)*6.6;
	  }
	  else if(p.hasPermission("prestige.29")) {
		  price = 4e12;
		  return ((2900+getRank(p))*price)*6.8;
	  }
	  else if(p.hasPermission("prestige.30")) {
		  price = 4e12;
		  return ((3000+getRank(p))*price)*7.0;
	  }else if(p.hasPermission("prestige.31")) {
		  price = 6e12;
		  return ((3100+getRank(p))*price)*7.2;
	  }
	  else if(p.hasPermission("prestige.32")) {
		  price = 6e12;
		  return ((3200+getRank(p))*price)*7.4;
	  }
	  else if(p.hasPermission("prestige.33")) {
		  price = 6e12;
		  return ((3300+getRank(p))*price)*7.6;
	  }
	  else if(p.hasPermission("prestige.34")) {
		  price = 6e12;
		  return ((3400+getRank(p))*price)*7.8;
	  }
	  else if(p.hasPermission("prestige.35")) {
		  price = 6e12;
		  return ((3500+getRank(p))*price)*8.0;
	  }
	  else if(p.hasPermission("prestige.36")) {
		  price = 6e12;
		  return ((3600+getRank(p))*price)*8.2;
	  }
	  else if(p.hasPermission("prestige.37")) {
		  price = 6e12;
		  return ((3700+getRank(p))*price)*8.4;
	  }
	  else if(p.hasPermission("prestige.38")) {
		  price = 6e12;
		  return ((3800+getRank(p))*price)*8.6;
	  }
	  else if(p.hasPermission("prestige.39")) {
		  price = 6e12;
		  return ((3900+getRank(p))*price)*8.8;
	  }
	  else if(p.hasPermission("prestige.40")) {
		  price = 6e12;
		  return ((4000+getRank(p))*price)*9.0;
	  }else if(p.hasPermission("prestige.41")) {
		  price = 8e12;
		  return ((4100+getRank(p))*price)*9.2;
	  }
	  else if(p.hasPermission("prestige.42")) {
		  price = 8e12;
		  return ((4200+getRank(p))*price)*9.4;
	  }
	  else if(p.hasPermission("prestige.43")) {
		  price = 8e12;
		  return ((4300+getRank(p))*price)*9.6;
	  }
	  else if(p.hasPermission("prestige.44")) {
		  price = 8e12;
		  return ((4400+getRank(p))*price)*9.8;
	  }
	  else if(p.hasPermission("prestige.45")) {
		  price = 8e12;
		  return ((4500+getRank(p))*price)*10.0;
	  }
	  else if(p.hasPermission("prestige.46")) {
		  price = 8e12;
		  return ((4600+getRank(p))*price)*10.2;
	  }
	  else if(p.hasPermission("prestige.47")) {
		  price = 8e12;
		  return ((4700+getRank(p))*price)*10.4;
	  }
	  else if(p.hasPermission("prestige.48")) {
		  price = 8e12;
		  return ((4800+getRank(p))*price)*10.6;
	  }
	  else if(p.hasPermission("prestige.49")) {
		  price = 8e12;
		  return ((4900+getRank(p))*price)*10.8;
	  }
	  else if(p.hasPermission("prestige.50")) {
		  price = 8e12;
		  return ((5000+getRank(p))*price)*11.0;
	  }else if(p.hasPermission("prestige.51")) {
		  price = 10e12;
		  return ((5100+getRank(p))*price)*11.2;
	  }
	  else if(p.hasPermission("prestige.52")) {
		  price = 10e12;
		  return ((5200+getRank(p))*price)*11.4;
	  }
	  else if(p.hasPermission("prestige.53")) {
		  price = 10e12;
		  return ((5300+getRank(p))*price)*11.6;
	  }
	  else if(p.hasPermission("prestige.54")) {
		  price = 10e12;
		  return ((5400+getRank(p))*price)*11.8;
	  }
	  else if(p.hasPermission("prestige.55")) {
		  price = 10e12;
		  return ((5500+getRank(p))*price)*12.0;
	  }
	  else if(p.hasPermission("prestige.56")) {
		  price = 10e12;
		  return ((5600+getRank(p))*price)*12.2;
	  }
	  else if(p.hasPermission("prestige.57")) {
		  price = 10e12;
		  return ((5700+getRank(p))*price)*12.4;
	  }
	  else if(p.hasPermission("prestige.58")) {
		  price = 10e12;
		  return ((5800+getRank(p))*price)*12.6;
	  }
	  else if(p.hasPermission("prestige.59")) {
		  price = 10e12;
		  return ((5900+getRank(p))*price)*12.8;
	  }
	  else if(p.hasPermission("prestige.60")) {
		  price = 10e12;
		  return ((6000+getRank(p))*price)*13.0;
	  }else if(p.hasPermission("prestige.61")) {
		  price = 12e12;
		  return ((6100+getRank(p))*price)*13.2;
	  }
	  else if(p.hasPermission("prestige.62")) {
		  price = 12e12;
		  return ((6200+getRank(p))*price)*13.4;
	  }
	  else if(p.hasPermission("prestige.63")) {
		  price = 12e12;
		  return ((6300+getRank(p))*price)*13.6;
	  }
	  else if(p.hasPermission("prestige.64")) {
		  price = 12e12;
		  return ((6400+getRank(p))*price)*13.8;
	  }
	  else if(p.hasPermission("prestige.65")) {
		  price = 12e12;
		  return ((6500+getRank(p))*price)*14.0;
	  }
	  else if(p.hasPermission("prestige.66")) {
		  price = 12e12;
		  return ((6600+getRank(p))*price)*14.2;
	  }
	  else if(p.hasPermission("prestige.67")) {
		  price = 12e12;
		  return ((6700+getRank(p))*price)*14.4;
	  }
	  else if(p.hasPermission("prestige.68")) {
		  price = 12e12;
		  return ((6800+getRank(p))*price)*14.6;
	  }
	  else if(p.hasPermission("prestige.69")) {
		  price = 12e12;
		  return ((6900+getRank(p))*price)*14.8;
	  }else if(p.hasPermission("prestige.70")) {
		  price = 12e12;
		  return ((7000+getRank(p))*price)*15.0;
	  }else if(p.hasPermission("prestige.71")) {
		  price = 14e12;
		  return ((7100+getRank(p))*price)*15.2;
	  }else if(p.hasPermission("prestige.72")) {
		  price = 14e12;
		  return ((7200+getRank(p))*price)*15.4;
	  }else if(p.hasPermission("prestige.73")) {
		  price = 14e12;
		  return ((7300+getRank(p))*price)*15.6;
	  }else if(p.hasPermission("prestige.74")) {
		  price = 14e12;
		  return ((7400+getRank(p))*price)*15.8;
	  }else if(p.hasPermission("prestige.75")) {
		  price = 14e12;
		  return ((7500+getRank(p))*price)*16.0;
	  }else if(p.hasPermission("prestige.76")) {
		  price = 14e12;
		  return ((7600+getRank(p))*price)*16.2;
	  }else if(p.hasPermission("prestige.77")) {
		  price = 14e12;
		  return ((7700+getRank(p))*price)*16.4;
	  }else if(p.hasPermission("prestige.78")) {
		  price = 14e12;
		  return ((7800+getRank(p))*price)*16.6;
	  }else if(p.hasPermission("prestige.79")) {
		  price = 14e12;
		  return ((7900+getRank(p))*price)*16.8;
	  }else if(p.hasPermission("prestige.80")) {
		  price = 14e12;
		  return ((8000+getRank(p))*price)*17.0;
	  }else if(p.hasPermission("prestige.81")) {
		  price = 16e12;
		  return ((8100+getRank(p))*price)*17.2;
	  }else if(p.hasPermission("prestige.82")) {
		  price = 16e12;
		  return ((8200+getRank(p))*price)*17.4;
	  }else if(p.hasPermission("prestige.83")) {
		  price = 16e12;
		  return ((8300+getRank(p))*price)*17.6;
	  }else if(p.hasPermission("prestige.84")) {
		  price = 16e12;
		  return ((8400+getRank(p))*price)*17.8;
	  }else if(p.hasPermission("prestige.85")) {
		  price = 16e12;
		  return ((8500+getRank(p))*price)*18.0;
	  }else if(p.hasPermission("prestige.86")) {
		  price = 16e12;
		  return ((8600+getRank(p))*price)*18.2;
	  }else if(p.hasPermission("prestige.87")) {
		  price = 16e12;
		  return ((8700+getRank(p))*price)*18.4;
	  }else if(p.hasPermission("prestige.88")) {
		  price = 16e12;
		  return ((8800+getRank(p))*price)*18.6;
	  }else if(p.hasPermission("prestige.89")) {
		  price = 16e12;
		  return ((8900+getRank(p))*price)*18.8;
	  }else if(p.hasPermission("prestige.90")) {
		  price = 16e12;
		  return ((9000+getRank(p))*price)*19.0;
	  }else if(p.hasPermission("prestige.91")) {
		  price = 18e12;
		  return ((9100+getRank(p))*price)*19.2;
	  }else if(p.hasPermission("prestige.92")) {
		  price = 18e12;
		  return ((9200+getRank(p))*price)*19.4;
	  }else if(p.hasPermission("prestige.93 ")) {
		  price = 18e12;
		  return ((9300+getRank(p))*price)*19.6;
	  }else if(p.hasPermission("prestige.94")) {
		  price = 18e12;
		  return ((9400+getRank(p))*price)*19.8;
	  }else if(p.hasPermission("prestige.95")) {
		  price = 18e12;
		  return ((9500+getRank(p))*price)*20.0;
	  }else if(p.hasPermission("prestige.96")) {
		  price = 18e12;
		  return ((9600+getRank(p))*price)*20.2;
	  }else if(p.hasPermission("prestige.97")) {
		  price = 18e12;
		  return ((9700+getRank(p))*price)*20.4;
	  }else if(p.hasPermission("prestige.98")) {
		  price = 18e12;
		  return ((9800+getRank(p))*price)*20.6;
	  }else if(p.hasPermission("prestige.99")) {
		  price = 18e12;
		  return ((9900+getRank(p))*price)*20.8;
	  }else if(p.hasPermission("prestige.100")) {
		  price = 18e12;
		  return ((10000+getRank(p))*price)*21.0;
	  }
	  price = 5e11;
    return ((getRank(p))*price)*1;
    
  }
  
  public boolean rankup(Player p) {
	  if(nextRank(p) > 100) {
		  return false;
	  }
    if (Main.econ.getBalance((OfflinePlayer)p) < rankPrice(p)) {
      p.sendMessage(ChatColor.DARK_GRAY +""+ ChatColor.STRIKETHROUGH + "--------------------->" + nextRank(p) + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "<---------------------");
      p.sendMessage(" ");
      p.sendMessage(ChatColor.LIGHT_PURPLE + "      � " + ChatColor.GRAY + "You Are Currently " + ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getRank(p) + ChatColor.DARK_GRAY + "]");
      p.sendMessage(" ");
      p.sendMessage(ChatColor.LIGHT_PURPLE + "      � " + ChatColor.GRAY + nextRank(p) + ChatColor.GRAY + " Costs " + ChatColor.AQUA + Methods.formatAmt(rankPrice(p)) + ChatColor.GRAY + "!");
      p.sendMessage(" ");
      p.sendMessage(ChatColor.LIGHT_PURPLE + "      � " + ChatColor.GRAY + ChatColor.GRAY + "You Need " + ChatColor.AQUA + Methods.formatAmt(Double.valueOf(rankPrice(p) - Main.econ.getBalance((OfflinePlayer)p)).doubleValue()) + ChatColor.GRAY + " To Rank Up!");
      p.sendMessage(" ");
      p.sendMessage(ChatColor.DARK_GRAY +""+ ChatColor.STRIKETHROUGH + "--------------------->" + nextRank(p) + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "<---------------------");
      return false;
    } 
    Main.econ.withdrawPlayer((OfflinePlayer)p, rankPrice(p));
    upRank(p);
    p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
	double percents = 0.0;
    p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
    percents = (Double.valueOf(Main.econ.getBalance((OfflinePlayer)p) / RankupHandler.getInstance().rankPrice(p)).doubleValue()*100);
    double dmultiply = percents*10.0;
    double dRound = Math.round(dmultiply) /10.0;
    if(RankupHandler.getInstance().getRank(p) == 100) {
    	p.getScoreboard().getTeam("percent").setSuffix(c("&c/prestige"));
    }
    
    else {
    	if(dRound>=100.0) {
    		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
    	} else {
    		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
    }
    }
    this.settings.getRankupPrices();
    this.settings.saveRankupPrices();
    return true;
  }
  
  public boolean MaxRankup(Player p) {
	  if (Main.econ.getBalance((OfflinePlayer)p) < rankPrice(p)) {
	      p.sendMessage(ChatColor.DARK_GRAY +""+ ChatColor.STRIKETHROUGH + "--------------------->" + nextRank(p) + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "<---------------------");
	      p.sendMessage(" ");
	      p.sendMessage(ChatColor.LIGHT_PURPLE + "      � " + ChatColor.GRAY + "You Are Currently " + ChatColor.DARK_GRAY + "[" + ChatColor.AQUA + getRank(p) + ChatColor.DARK_GRAY + "]");
	      p.sendMessage(" ");
	      p.sendMessage(ChatColor.LIGHT_PURPLE + "      � " + ChatColor.GRAY + nextRank(p) + ChatColor.GRAY + " Costs " + ChatColor.AQUA + Methods.formatAmt(rankPrice(p)) + ChatColor.GRAY + "!");
	      p.sendMessage(" ");
	      p.sendMessage(ChatColor.LIGHT_PURPLE + "      � " + ChatColor.GRAY + ChatColor.GRAY + "You Need " + ChatColor.AQUA + Methods.formatAmt(Double.valueOf(rankPrice(p) - Main.econ.getBalance((OfflinePlayer)p)).doubleValue()) + ChatColor.GRAY + " To Rank Up!");
	      p.sendMessage(" ");
	      p.sendMessage(ChatColor.DARK_GRAY +""+ ChatColor.STRIKETHROUGH + "--------------------->" + nextRank(p) + ChatColor.DARK_GRAY + ChatColor.STRIKETHROUGH + "<---------------------");
	      return false;
	    } 
	  while(Main.econ.getBalance((OfflinePlayer)p) > rankPrice(p)) {
		  if(getRank(p) >= 100) return false;
		  Main.econ.withdrawPlayer((OfflinePlayer)p, rankPrice(p));
		    upRank(p);
		    p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
	    	double percents = 0.0;
	        p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
	        percents = (Double.valueOf(Main.econ.getBalance((OfflinePlayer)p) / RankupHandler.getInstance().rankPrice(p)).doubleValue()*100);
	        double dmultiply = percents*10.0;
	        double dRound = Math.round(dmultiply) /10.0;
	        if(RankupHandler.getInstance().getRank(p) == 100) {
	        	p.getScoreboard().getTeam("percent").setSuffix(c("&c/prestige"));
	        }
	        
	        else {
	        	if(dRound>=100.0) {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
	        	} else {
	        		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
	        }
	        }
	  }
    return true;
  }
  
  
  
  public boolean autorankup(Player p) {
	  if(getRank(p) == 100) {
		  return false;
	  }
	    if (Main.econ.getBalance((OfflinePlayer)p) < rankPrice(p)) {
	      return false;
	    } 
	    Main.econ.withdrawPlayer((OfflinePlayer)p, rankPrice(p));
	    upRank(p);
	    return true;
	  }
  
  @EventHandler
  public void onMove(BlockBreakEvent e) {
	  Player p = e.getPlayer();
	  
	  if(aru.contains(p))
		  autorankup(p); 
	  
  }
  
  
  
  
  
  
  static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }



	public boolean isInt(String s) {
		try {
			int i = Integer.parseInt(s);
			return true;
		} catch (Exception e1) {
			return false;
		}
	}
@Override
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

	
	
	
	if(label.equalsIgnoreCase("autorankup") || label.equalsIgnoreCase("aru")) {
		Player p = (Player)sender;
			if(!aru.contains(p)) {
				aru.add(p);
				p.sendMessage(c("&aAutoRankup Enabled!"));
				settings.getOptions().set(p.getUniqueId().toString()+".Autorankup", true);
				return true;
			}
		
		
			if(aru.contains(p)) {
				aru.remove(p);
				p.sendMessage(c("&cAutoRankup Disabled!"));
				settings.getOptions().set(p.getUniqueId().toString()+".Autorankup", false);
				return true;
			}
			
		
	}
	if(label.equalsIgnoreCase("givemoney")){
		if(sender.isOp()){
			if(args.length == 2) {
				if(isInt(args[1])) {
					Player pl = Bukkit.getServer().getPlayer(args[0]);
					int percent = Integer.valueOf(args[1]);
					double per = ((double)percent)/100;
					double money = rankPrice(pl) * per;
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "eco give " + pl.getName() + " " + formatDbl(money));
				}

			}

		}
	}

	return false;
}

	public String formatDbl(double value){
		DecimalFormat formatter;

		if(value - (int)value > 0.0)
			formatter = new DecimalFormat("0.00"); //Here you can also deal with rounding if you wish..
		else
			formatter = new DecimalFormat("0");

		return formatter.format(value);
	}
  
  
  
}
