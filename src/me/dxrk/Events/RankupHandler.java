package me.dxrk.Events;

import me.dxrk.Enchants.EnchantMethods;
import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.MineHandler;
import me.dxrk.Tokens.Tokens;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

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
		  this.settings.getRankupPrices().set(p.getUniqueId().toString(), 1);
		  this.settings.saveRankupPrices();
	  }
	  if(!aru.contains(p)) {
		  if(settings.getOptions().getBoolean(p.getUniqueId().toString()+".Autorankup") == true) {
			  aru.add(p);
		  }
	  }
	  p.setAllowFlight(true);
	  p.setFlying(true);
	  
  }
  
  
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
	  EnchantMethods.laser.remove(e.getPlayer());
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

  
  
  public void upRank(Player p) {
	  if(nextRank(p) >= 1000 && getPrestiges(p) >= 100 && !this.settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".Ethereal")) {
		  this.settings.getRankupPrices().set(p.getUniqueId().toString(), 1);
		  this.settings.getPlayerData().set(p.getUniqueId().toString()+".Ethereal", true);
		  Bukkit.broadcastMessage(c("&f&l"+p.getName()+" is &b&l&kO&e&lEthereal&b&l&kO&r"));
		  return;
	  }

	    
	  int i = this.settings.getRankupPrices().getInt(p.getUniqueId().toString());
	    this.settings.getRankupPrices().set(p.getUniqueId().toString(), i + 1);
	    
	    p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));

  }
  
  public void setRank(Player p, int i) {
	  this.settings.getRankupPrices().set(p.getUniqueId().toString(), i);
	  this.settings.saveRankupPrices();
	  p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
  }

	public int getPrestiges(Player p){
		int prestiges = settings.getPlayerData().getInt(p.getUniqueId().toString()+".Prestiges");
		return prestiges;
	}
  
  public int nextRank(Player p) {
   
        return (getRank(p)+1);
    
  }

	public double priceJumpR(Player p){
		int ranks = getRank(p);

		if(ranks >= 1000){
			return 25;
		}
		else if(ranks >= 750) {
			return 12;
		}
		else if(ranks >=500){
			return 8;
		}
		else if(ranks >= 250){
			return 4;
		}


		return 1;
	}
	public double priceJumpP(Player p) {
	  int prestiges = getPrestiges(p);

	  if(prestiges >= 100) {
		  return 50;
	  }
	  else if(prestiges >=90) {
		  return 36;
	  }
	  else if(prestiges >=80) {
		  return 32;
	  }
	  else if(prestiges >=70) {
		  return 28;
	  }
	  else if(prestiges >=60) {
		  return 24;
	  }
	  else if(prestiges >=50) {
		  return 20;
	  }
	  else if(prestiges >=40) {
		  return 8;
	  }
	  else if(prestiges >=30) {
		  return 6;
	  }
	  else if(prestiges >=20) {
		  return 4;
	  }
	  else if(prestiges >=10) {
		  return 2;
	  }


		return 1;
	}

  
  public double rankPrice(Player p) {
	  int rank = getRank(p);

	  if(this.settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".Ethereal")) {
			double start = 7.523828125E20D*25;
			double price = start*(rank*1.75);
			return price;
	  }

	  double prestiges = getPrestiges(p)*2.75;
	  if(prestiges <1) {
		  prestiges = 1;
	  }
	  double price = (1.25e12*(rank*1.75))*priceJumpR(p)*prestiges*priceJumpP(p);

    return price;
  }
  
  public void rankup(Player p) {
	  if(this.settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".Ethereal")) {
		  if (Main.econ.getBalance(p) < rankPrice(p)) {
			  p.sendMessage(c("&f&lLevel &8| &7You need &a$"+Main.formatAmt(rankPrice(p) - Main.econ.getBalance(p))+" &7to rankup."));
			  return;
		  }
		  Main.econ.withdrawPlayer(p, rankPrice(p));
		  upRank(p);
		  p.getScoreboard().getTeam("prestige").setPrefix(c("&7Prestige: "));
		  p.getScoreboard().getTeam("prestige").setSuffix(c("&e&lEthereal"));
		  p.getScoreboard().getTeam("prank").setPrefix(c("&7Level: "));
		  p.getScoreboard().getTeam("prank").setSuffix(c("&b&l"+getRank(p)));
		  double percents;
		  p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
		  percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
		  double dmultiply = percents*10.0;
		  double dRound = Math.round(dmultiply) /10.0;



		  if(dRound>=100.0) {
			  p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
		  } else {
			  p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
		  }
		  return;
	  }


    if (Main.econ.getBalance(p) < rankPrice(p)) {
      p.sendMessage(c("&f&lLevel &8| &7You need &a$"+Main.formatAmt(rankPrice(p) - Main.econ.getBalance(p))+" &7to rankup."));
      return;
    } 
    Main.econ.withdrawPlayer(p, rankPrice(p));
    upRank(p);
	if((getRank(p) %16 == 0) && getRank(p) <1000)
		MineHandler.getInstance().updateMine(p, getRank(p));
    p.getScoreboard().getTeam("prank").setSuffix(c("&b" + getRank(p)));
	double percents;
    p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
    percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
    double dmultiply = percents*10.0;
    double dRound = Math.round(dmultiply) /10.0;

    

    	if(dRound>=100.0) {
    		p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
    	} else {
    		p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
    	}
    this.settings.saveRankupPrices();
  }
  
  public void MaxRankup(Player p) {
	  if (Main.econ.getBalance(p) < rankPrice(p)) {
	      return;
	    }
	  if(settings.getPlayerData().getBoolean(p.getUniqueId().toString()+".Ethereal")) {
		  while(Main.econ.getBalance(p) > rankPrice(p)) {
			  Main.econ.withdrawPlayer(p, rankPrice(p));
			  upRank(p);
		  }
		  p.getScoreboard().getTeam("prestige").setPrefix(c("&7Prestige: "));
		  p.getScoreboard().getTeam("prestige").setSuffix(c("&e&lEthereal"));
		  p.getScoreboard().getTeam("prank").setPrefix(c("&7Level: "));
		  p.getScoreboard().getTeam("prank").setSuffix(c("&b&l"+getRank(p)));
		  double percents;
		  p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
		  percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
		  double dmultiply = percents*10.0;
		  double dRound = Math.round(dmultiply) /10.0;



		  if(dRound>=100.0) {
			  p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
		  } else {
			  p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
		  }
		  this.settings.saveRankupPrices();
		  return;
	  }
	  while(Main.econ.getBalance(p) > rankPrice(p)) {
		  Main.econ.withdrawPlayer(p, rankPrice(p));
		  upRank(p);
		  if((getRank(p) %16 == 0) && getRank(p) <1000)
			  MineHandler.getInstance().updateMine(p, getRank(p));

	  }
	  p.getScoreboard().getTeam("prank").setSuffix(c("&b" + RankupHandler.getInstance().getRank(p)));
	  double percents;
	  p.getScoreboard().getTeam("balance").setSuffix(c("&a"+Main.formatAmt(Tokens.getInstance().getBalance(p))));
	  percents = (Main.econ.getBalance(p) / RankupHandler.getInstance().rankPrice(p) *100);
	  double dmultiply = percents*10.0;
	  double dRound = Math.round(dmultiply) /10.0;



	  if(dRound>=100.0) {
		  p.getScoreboard().getTeam("percent").setSuffix(c("&c/rankup"));
	  } else {
		  p.getScoreboard().getTeam("percent").setSuffix(c("&c")+(dRound)+"%");
	  }
	  this.settings.saveRankupPrices();

  }
  
  
  
  public void autorankup(Player p) {

	    if (Main.econ.getBalance(p) < rankPrice(p)) {
	      return;
	    } 
	    Main.econ.withdrawPlayer(p, rankPrice(p));
	    upRank(p);
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
			} else {
				aru.remove(p);
				p.sendMessage(c("&cAutoRankup Disabled!"));
				settings.getOptions().set(p.getUniqueId().toString()+".Autorankup", false);
			}
			settings.saveOptions();
		return true;


	}
	if(label.equalsIgnoreCase("givemoney")){
		if(sender.isOp()){
			if(args.length == 2) {
				if(isInt(args[1])) {
					Player pl = Bukkit.getServer().getPlayer(args[0]);
					int percent = Integer.parseInt(args[1]);
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
