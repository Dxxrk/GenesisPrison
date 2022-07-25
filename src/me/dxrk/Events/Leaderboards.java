package me.dxrk.Events;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import com.earth2me.essentials.Essentials;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import me.dxrk.Commands.Voting;
import me.dxrk.Main.Main;
import me.dxrk.Main.SettingsManager;

public class Leaderboards implements Listener, CommandExecutor{
	public static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	public static SettingsManager settings = SettingsManager.getInstance();
	
	
	static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");
	
	
	public static SortedMap<Integer, String> orderblocks = new TreeMap<Integer, String>(Collections.reverseOrder());
	public static SortedMap<Integer, String> ordertime = new TreeMap<Integer, String>(Collections.reverseOrder());
	
	
	
	public static void orderBlocks() {
		for(String uuid : settings.getPlayerData().getKeys(false)) {
			if(uuid.equals("7dd67277-1c1a-42e7-98ac-aa64eb122ec8")) continue;
			if(uuid.equals("c32dfc2e-6780-4dbb-9baf-9ca671fbd35f")) continue;
			int blocks = settings.getPlayerData().getInt(uuid+".BlocksBroken");
		    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		    orderblocks.put(blocks, p.getName());
		}
	}
	public static void orderTime() {
		for(String uuid : settings.getPlayerData().getKeys(false)) {
			if(uuid.equals("7dd67277-1c1a-42e7-98ac-aa64eb122ec8")) continue;
			if(uuid.equals("c32dfc2e-6780-4dbb-9baf-9ca671fbd35f")) continue;
			int time = settings.getPlayerData().getInt(uuid+".TimePlayed");
		    OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
		    ordertime.put(time, p.getName());
		}
	}
	
	
	public static void addTimePlayed() {
		
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getName().equals("Dxrk")) continue;
					if(p.getName().equals("32j")) continue;
				if(!p.isOnline()) continue;
				if(ess.getUser(p) != null && ess.getUser(p).isAfk()) continue;
				
				int time = settings.getPlayerData().getInt(p.getUniqueId().toString()+".TimePlayed");
				settings.getPlayerData().set(p.getUniqueId().toString()+".TimePlayed", time+1);
				
				
				
			}
				
	}
	
	public static String formatTime(int seconds){
        int day = (int)TimeUnit.SECONDS.toDays(seconds);        
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        return c("&7"+day+"D "+hours+"H "+minute+"M "+second+"S");
    }
	
	
	public static void deleteIfOld(Hologram hologram) {

		long tenMinutesMillis = 14 * 1000; // 14 seconds in milliseconds
		long elapsedMillis = System.currentTimeMillis() - hologram.getCreationTimestamp(); // Milliseconds elapsed from the creation of the hologram

		if (elapsedMillis > tenMinutesMillis) {
			hologram.delete();
		}
	}
	public static int getVotes(OfflinePlayer p) {
	    return settings.getVote().getInt(String.valueOf(p.getUniqueId().toString()) + ".Votes");
	  }
	
	public static void loadHolos() {
		BlockCounting.getInstance().onEndLB();
		for(Hologram h : HologramsAPI.getHolograms(Main.plugin)){
			deleteIfOld(h);
		}
		Location blocks = new Location(Bukkit.getWorld("world_the_end"), 0.5, 65, 3);
		Location time = new Location(Bukkit.getWorld("world_the_end"), 0.5, 65, -3);
		Location vote = new Location(Bukkit.getWorld("world_the_end"), -3, 65, 0.5);
		
		orderBlocks();
		Set<Map.Entry<Integer, String> > bentrySet = orderblocks.entrySet();
		List<Map.Entry<Integer, String> > blockstop = new ArrayList<>(bentrySet);
		Hologram blocksholo = HologramsAPI.createHologram(Main.getInstance(), blocks);
		blocksholo.appendTextLine(c("&f&lBlocks Broken"));
		blocksholo.appendTextLine("");
		if(blockstop.size() >= 1)
		blocksholo.appendTextLine(c("&7#1 &d"+blockstop.get(0).getValue()+": &7"+blockstop.get(0).getKey()));
		if(blockstop.size() >= 2)
		blocksholo.appendTextLine(c("&7#2 &d"+blockstop.get(1).getValue()+": &7"+blockstop.get(1).getKey()));
		if(blockstop.size() >= 3)
		blocksholo.appendTextLine(c("&7#3 &d"+blockstop.get(2).getValue()+": &7"+blockstop.get(2).getKey()));
		if(blockstop.size() >= 4)
		blocksholo.appendTextLine(c("&7#4 &d"+blockstop.get(3).getValue()+": &7"+blockstop.get(3).getKey()));
		if(blockstop.size() >= 5)
		blocksholo.appendTextLine(c("&7#5 &d"+blockstop.get(4).getValue()+": &7"+blockstop.get(4).getKey()));
		blockstop.clear();
		bentrySet.clear();
		orderblocks.clear();
		
		orderTime();
		Set<Map.Entry<Integer, String> > tentrySet = ordertime.entrySet();
		List<Map.Entry<Integer, String> > timetop = new ArrayList<>(tentrySet);
		Hologram timeholo = HologramsAPI.createHologram(Main.getInstance(), time);
		timeholo.appendTextLine(c("&f&lTime Played"));
		timeholo.appendTextLine("");
		if(timetop.size() >=1)
		timeholo.appendTextLine(c("&7#1 &d"+timetop.get(0).getValue()+": "+formatTime(timetop.get(0).getKey())));
		if(timetop.size() >=2)
		timeholo.appendTextLine(c("&7#2 &d"+timetop.get(1).getValue()+": "+formatTime(timetop.get(1).getKey())));
		if(timetop.size() >=3)
		timeholo.appendTextLine(c("&7#3 &d"+timetop.get(2).getValue()+": "+formatTime(timetop.get(2).getKey())));
		if(timetop.size() >=4)
		timeholo.appendTextLine(c("&7#4 &d"+timetop.get(3).getValue()+": "+formatTime(timetop.get(3).getKey())));
		if(timetop.size() >=5)
		timeholo.appendTextLine(c("&7#5 &d"+timetop.get(4).getValue()+": "+formatTime(timetop.get(4).getKey())));
		timetop.clear();
		tentrySet.clear();
		ordertime.clear();
		
		
		Voting.getInstance().orderTop();
		Hologram voteholo = HologramsAPI.createHologram(Main.getInstance(), vote);
		voteholo.appendTextLine(c("&f&lVotes")); 
		voteholo.appendTextLine("");
		if(Voting.top5.size() >=1)
		voteholo.appendTextLine(c("&7#1 &d"+Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(0))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(0))))));
		if(Voting.top5.size() >=2)
		voteholo.appendTextLine(c("&7#2 &d"+Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(1))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(1))))));
		if(Voting.top5.size() >=3)
		voteholo.appendTextLine(c("&7#3 &d"+Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(2))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(2))))));
		if(Voting.top5.size() >=4)
		voteholo.appendTextLine(c("&7#4 &d"+Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(3))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(3))))));
		if(Voting.top5.size() >=5)
		voteholo.appendTextLine(c("&7#5 &d"+Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(4))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(Voting.top5.get(4))))));
		
		
	}
	
	
	

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("blockstop")) {
			orderBlocks();
			Set<Map.Entry<Integer, String> > bentrySet = orderblocks.entrySet();
			List<Map.Entry<Integer, String> > blockstop = new ArrayList<>(bentrySet);
			if(blockstop.get(0) !=null)
			cs.sendMessage(c("&d"+blockstop.get(0).getValue()+": &7"+blockstop.get(0).getKey()));
			if(blockstop.get(1) !=null)
			cs.sendMessage(c("&d"+blockstop.get(1).getValue()+": &7"+blockstop.get(1).getKey()));
			if(blockstop.get(2) !=null)
			cs.sendMessage(c("&d"+blockstop.get(2).getValue()+": &7"+blockstop.get(2).getKey()));
			if(blockstop.get(3) !=null)
			cs.sendMessage(c("&d"+blockstop.get(3).getValue()+": &7"+blockstop.get(3).getKey()));
			if(blockstop.get(4) !=null)
			cs.sendMessage(c("&d"+blockstop.get(4).getValue()+": &7"+blockstop.get(4).getKey()));
			blockstop.clear();
			bentrySet.clear();
			orderblocks.clear();
		}
		
		return false;
	}
	
	
	
	
	

}
