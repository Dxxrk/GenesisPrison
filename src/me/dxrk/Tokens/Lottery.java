package me.dxrk.Tokens;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class Lottery implements Listener, CommandExecutor{
	
	static Lottery instance = new Lottery();
	
	public static Lottery getInstance() {
		return instance;
	}
	
	
	
	
	static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	public boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }
	
	public void startLottery() {
		Lottery.lottery = true;
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (c("bc &3&lLottery &8&l┃ &7&lLOTTERY IS NOW OPEN! /Lottery")));
	}
	
	
	public void ChooseWinner() {
		Random r = new Random();
		int rand = r.nextInt(players.size());
		Player player = Bukkit.getPlayer(players.get(rand));
		double size2 = size*2.5;
		int add = (int) size2;
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (c("bc &3&lLottery &8&l┃ &a&l"+player.getName()+" &7has won the Lottery of &f&l"+add+" &bTokens&7!")));
		
		Tokens.getInstance().addTokens(player, add);
		Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (c("bc &3&lLottery &8&l┃ &7&lLottery Will Open in 1 Hour!")));
		if(!player.isOnline()) {
			Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "mail send "+player.getName()+" &7You Won the Lottery! (&b"+add+"&7)");
		}
		players.clear();
		tickets.clear();
		size = 0;
	}
	
	public static boolean lottery = false;
	
	private HashMap<Player, Integer> tickets = new HashMap<>();
	private int size = 0;
	private List<UUID> players = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player)sender;
		double size2 = size*2.5;
		int add = (int) size2;
		if(label.equalsIgnoreCase("lottery")) {
			if(lottery == true) {
			if(args.length == 0) {
				if(tickets.containsKey(p)) {
				p.sendMessage(c("&3&lLottery &8&l┃ &7Your Entries: &b"+tickets.get(p)));
				}else {
					p.sendMessage(c("&3&lLottery &8&l┃ &7Your Entries: &b0"));
				}
				p.sendMessage(c("&3&lLottery &8&l┃ &7/lottery buy <amount>"));
				p.sendMessage(c("&3&lLottery &8&l┃ &7/lottery size"));
				
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("size")) {
					p.sendMessage(c("&3&lLottery &8&l┃ &7The pot is up to &f&l"+add + " &bTokens"));
				}
				if(args[0].equalsIgnoreCase("buy")) {
					p.sendMessage(c("&3&lLottery &8&l┃ &cPlease Specify how many tickets you'd like to buy."));
				}
				if(args[0].equalsIgnoreCase("stop")) {
					if(p.isOp()) {
						ChooseWinner();
					}
				}
				if(args[0].equalsIgnoreCase("start")) {
					if(p.isOp()) {
						startLottery();
					}
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("buy")) {
					if(isInt(args[1])) {
					int amount = Integer.parseInt(args[1]);
					if(amount >5) {
						p.sendMessage(c("&3&lLottery &8&l┃ &cCannot buy over 5 tickets"));
						return true;
					}
					if(tickets.containsKey(p) && (amount+tickets.get(p)) >5) {
						p.sendMessage(c("&3&lLottery &8&l┃ &cCannot buy over 5 tickets"));
						return true;
					}
					if((amount*100) > Tokens.getInstance().getBalance(p)) {
						p.sendMessage(c("&4Error: Not Enough Tokens"));
						return true;
					}
					if(tickets.containsKey(p) && amount+tickets.get(p) <= 5) {
						tickets.put(p, amount+tickets.get(p));
						players.add(p.getUniqueId());
						size += (amount*100);
						Tokens.getInstance().takeTokens(p, amount*100);
						return true;
					}
					tickets.put(p, amount);
					players.add(p.getUniqueId());
					size += (amount*100);
					Tokens.getInstance().takeTokens(p, amount*100);
					
					} else {
						p.sendMessage(c("&3&lLottery &8&l┃ &cInvalid Amount."));
					}
					
				}
				
			}
			} else {
				p.sendMessage(c("&3&lLottery &8&l┃ &cLottery is Closed."));
			}
		}
		return false;
	}
	
	/**
	   * Schedules a task to run at a certain hour every day.
	   * @param plugin The plugin associated with this task
	   * @param task The task to run
	   * @param hour [0-23] The hour of the day to run the task
	   * @return Task id number (-1 if scheduling failed)
	   */
	  public static int scheduleLottery(Plugin plugin, Runnable task, int hour)
	  {
	      //Calendar is a class that represents a certain time and date.
	      Calendar cal = Calendar.getInstance(); //obtains a calendar instance that represents the current time and date
	   
	      //time is often represented in milliseconds since the epoch,
	      //as a long, which represents how many milliseconds a time is after
	      //January 1st, 1970, 00:00.
	   
	      //this gets the current time
	      long now = cal.getTimeInMillis();
	      //you could also say "long now = System.currentTimeMillis()"
	   
	      //since we have saved the current time, we need to figure out
	      //how many milliseconds are between that and the next
	      //time it is 7:00pm, or whatever was passed into hour
	      //we do this by setting this calendar instance to the next 7:00pm (or whatever)
	      //then we can compare the times
	   
	      //if it is already after 7:00pm,
	      //we will schedule it for tomorrow,
	      //since we can't schedule it for the past.
	      //we are not time travelers.
	      if(cal.get(Calendar.HOUR_OF_DAY) >= hour)
	          cal.add(Calendar.DATE, 1); //do it tomorrow if now is after "hours"
	   
	      //we need to set this calendar instance to 7:00pm, or whatever.
	      cal.set(Calendar.HOUR_OF_DAY, hour);
	      cal.set(Calendar.MINUTE, 0);
	      cal.set(Calendar.SECOND, 0);
	      cal.set(Calendar.MILLISECOND, 0);
	   
	      //cal is now properly set to the next time it will be 7:00pm
	   
	      long offset = cal.getTimeInMillis() - now;
	      long ticks = offset / 50L; //there are 50 milliseconds in a tick
	   
	      //we now know how many ticks are between now and the next time it is 7:00pm
	      //we schedule an event to go off the next time it is 7:00pm,
	      //and repeat every 24 hours.
	      return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, ticks, 1728000L);
	      //24 hrs/day * 60 mins/hr * 60 secs/min * 20 ticks/sec = 1728000 ticks
	  }
	
	
	
	

}
