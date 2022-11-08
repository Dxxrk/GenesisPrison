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

import me.dxrk.Vote.CMDVote;
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
	public static SortedMap<Integer, String> orderrank = new TreeMap<Integer, String>(Collections.reverseOrder());


	public static void orderRanks() {
		for (String uuid : settings.getPlayerData().getKeys(false)) {
			if(uuid.equals("7dd67277-1c1a-42e7-98ac-aa64eb122ec8")) continue;
			if(uuid.equals("c32dfc2e-6780-4dbb-9baf-9ca671fbd35f")) continue;
			if(uuid.equals("6a137295-d6e3-4a4d-b4e8-9d09898f9057")) continue;
			int prestige = settings.getPlayerData().getInt(uuid+".Prestige")*1000;
			int rank = settings.getRankupPrices().getInt(uuid);
			int total = prestige+rank;
			OfflinePlayer p = Bukkit.getOfflinePlayer(UUID.fromString(uuid));
			orderrank.put(total, p.getName());

		}
	}
	
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
		BlocksHandler.getInstance().onEndLB();
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
		
		
		CMDVote.getInstance().orderTop();
		Hologram voteholo = HologramsAPI.createHologram(Main.getInstance(), vote);
		voteholo.appendTextLine(c("&f&lVotes")); 
		voteholo.appendTextLine("");
		if(CMDVote.top5.size() >=1)
			voteholo.appendTextLine(c("&7#1 &d"+Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(0))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(0))))));
		if(CMDVote.top5.size() >=2)
			voteholo.appendTextLine(c("&7#2 &d"+Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(1))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(1))))));
		if(CMDVote.top5.size() >=3)
			voteholo.appendTextLine(c("&7#3 &d"+Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(2))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(2))))));
		if(CMDVote.top5.size() >=4)
			voteholo.appendTextLine(c("&7#4 &d"+Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(3))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(3))))));
		if(CMDVote.top5.size() >=5)
			voteholo.appendTextLine(c("&7#5 &d"+Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(4))).getName()+": &7"+getVotes(Bukkit.getOfflinePlayer(UUID.fromString(CMDVote.top5.get(4))))));
		
		
	}



	
	

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		
		if(label.equalsIgnoreCase("blockstop")) {
			orderBlocks();
			Set<Map.Entry<Integer, String> > bentrySet = orderblocks.entrySet();
			List<Map.Entry<Integer, String> > blockstop = new ArrayList<>(bentrySet);
			if(blockstop.size() > 0 && blockstop.get(0) !=null)
				cs.sendMessage(c("&d"+blockstop.get(0).getValue()+": &7"+blockstop.get(0).getKey()));
			if(blockstop.size() > 1 && blockstop.get(1) !=null)
				cs.sendMessage(c("&d"+blockstop.get(1).getValue()+": &7"+blockstop.get(1).getKey()));
			if(blockstop.size() > 2 && blockstop.get(2) !=null)
				cs.sendMessage(c("&d"+blockstop.get(2).getValue()+": &7"+blockstop.get(2).getKey()));
			if(blockstop.size() > 3 && blockstop.get(3) !=null)
				cs.sendMessage(c("&d"+blockstop.get(3).getValue()+": &7"+blockstop.get(3).getKey()));
			if(blockstop.size() > 4 && blockstop.get(4) !=null)
				cs.sendMessage(c("&d"+blockstop.get(4).getValue()+": &7"+blockstop.get(4).getKey()));
			blockstop.clear();
			bentrySet.clear();
			orderblocks.clear();
		}
		if(label.equalsIgnoreCase("ranktop") || label.equalsIgnoreCase("rankstop")){
			orderRanks();
			Set<Map.Entry<Integer, String> > rentrySet = orderrank.entrySet();
			List<Map.Entry<Integer, String> > rankstop = new ArrayList<>(rentrySet);
			if(rankstop.size() > 0 && rankstop.get(0) !=null)
				cs.sendMessage(c("&d"+rankstop.get(0).getValue()+": &7"+formatRank(rankstop.get(0).getKey())));
			if(rankstop.size() > 1 && rankstop.get(1) !=null)
				cs.sendMessage(c("&d"+rankstop.get(1).getValue()+": &7"+formatRank(rankstop.get(1).getKey())));
			if(rankstop.size() > 2 && rankstop.get(2) !=null)
				cs.sendMessage(c("&d"+rankstop.get(2).getValue()+": &7"+formatRank(rankstop.get(2).getKey())));
			if(rankstop.size() > 3 && rankstop.get(3) !=null)
				cs.sendMessage(c("&d"+rankstop.get(3).getValue()+": &7"+formatRank(rankstop.get(3).getKey())));
			if(rankstop.size() > 4 && rankstop.get(4) !=null)
				cs.sendMessage(c("&d"+rankstop.get(4).getValue()+": &7"+formatRank(rankstop.get(4).getKey())));
			if(rankstop.size() > 5 && rankstop.get(5) !=null)
				cs.sendMessage(c("&d"+rankstop.get(5).getValue()+": &7"+formatRank(rankstop.get(5).getKey())));
			if(rankstop.size() > 6 && rankstop.get(6) !=null)
				cs.sendMessage(c("&d"+rankstop.get(6).getValue()+": &7"+formatRank(rankstop.get(6).getKey())));
			if(rankstop.size() > 7 && rankstop.get(7) !=null)
				cs.sendMessage(c("&d"+rankstop.get(7).getValue()+": &7"+formatRank(rankstop.get(7).getKey())));
			if(rankstop.size() > 8 && rankstop.get(8) !=null)
				cs.sendMessage(c("&d"+rankstop.get(8).getValue()+": &7"+formatRank(rankstop.get(8).getKey())));
			if(rankstop.size() > 9 && rankstop.get(9) !=null)
				cs.sendMessage(c("&d"+rankstop.get(9).getValue()+": &7"+formatRank(rankstop.get(9).getKey())));
			rankstop.clear();
			rentrySet.clear();
			orderrank.clear();

		}
		
		return false;
	}

	public String formatRank(int i) {
		if(i > 0 && i <=100) {
			return "P0-"+i;
		} else if(i >1000 && i <2000) {
			return "P1-"+(i-1000);
		} else if(i >2000 && i <3000) {
			return "P2-"+(i-2000);
		} else if(i >3000 && i <4000) {
			return "P3-"+(i-3000);
		} else if(i >4000 && i <5000) {
			return "P4-"+(i-4000);
		} else if(i >5000 && i <6000) {
			return "P5-"+(i-5000);
		} else if(i >6000 && i <7000) {
			return "P6-"+(i-6000);
		} else if(i >7000 && i <8000) {
			return "P7-"+(i-7000);
		} else if(i >8000 && i <9000) {
			return "P8-"+(i-8000);
		} else if(i >9000 && i <10000) {
			return "P9-"+(i-9000);
		} else if(i >10000 && i <11000) {
			return "P10-"+(i-10000);
		} else if(i >11000 && i <12000) {
			return "P11-"+(i-11000);
		} else if(i >12000 && i <13000) {
			return "P12-"+(i-12000);
		} else if(i >13000 && i <14000) {
			return "P13-"+(i-13000);
		} else if(i >14000 && i <15000) {
			return "P14-"+(i-14000);
		} else if(i >15000 && i <16000) {
			return "P15-"+(i-15000);
		} else if(i >16000 && i <17000) {
			return "P16-"+(i-16000);
		} else if(i >17000 && i <18000) {
			return "P17-"+(i-17000);
		} else if(i >18000 && i <19000) {
			return "P18-"+(i-18000);
		} else if(i >19000 && i <20000) {
			return "P19-"+(i-19000);
		} else if(i >20000 && i <21000) {
			return "P20-"+(i-20000);
		} else if(i >21000 && i <22000) {
			return "P21-"+(i-21000);
		} else if(i >22000 && i <23000) {
			return "P22-"+(i-22000);
		} else if(i >23000 && i <24000) {
			return "P23-"+(i-23000);
		} else if(i >24000 && i <25000) {
			return "P24-"+(i-24000);
		} else if(i >25000 && i <26000) {
			return "P25-"+(i-25000);
		} else if(i >26000 && i <27000) {
			return "P26-"+(i-26000);
		} else if(i >27000 && i <28000) {
			return "P27-"+(i-27000);
		} else if(i >28000 && i <29000) {
			return "P28-"+(i-28000);
		} else if(i >29000 && i <30000) {
			return "P29-"+(i-29000);
		} else if(i >30000 && i <31000) {
			return "P30-"+(i-30000);
		} else if(i >31000 && i <32000) {
			return "P31-"+(i-31000);
		} else if(i >32000 && i <33000) {
			return "P32-"+(i-32000);
		} else if(i >33000 && i <34000) {
			return "P33-"+(i-33000);
		} else if(i >34000 && i <35000) {
			return "P34-"+(i-34000);
		} else if(i >35000 && i <36000) {
			return "P35-"+(i-35000);
		} else if(i >36000 && i <37000) {
			return "P36-"+(i-36000);
		} else if(i >37000 && i <38000) {
			return "P37-"+(i-37000);
		} else if(i >38000 && i <39000) {
			return "P38-"+(i-38000);
		} else if(i >39000 && i <40000) {
			return "P39-"+(i-39000);
		} else if(i >40000 && i <41000) {
			return "P40-"+(i-40000);
		} else if(i >41000 && i <42000) {
			return "P41-"+(i-41000);
		} else if(i >42000 && i <43000) {
			return "P42-"+(i-42000);
		} else if(i >43000 && i <44000) {
			return "P43-"+(i-43000);
		} else if(i >44000 && i <45000) {
			return "P44-"+(i-44000);
		} else if(i >45000 && i <46000) {
			return "P45-"+(i-45000);
		} else if(i >46000 && i <47000) {
			return "P46-"+(i-46000);
		} else if(i >47000 && i <48000) {
			return "P47-"+(i-47000);
		} else if(i >48000 && i <49000) {
			return "P48-"+(i-48000);
		} else if(i >49000 && i <50000) {
			return "P49-"+(i-49000);
		} else if(i >50000 && i <51000) {
			return "P50-"+(i-50000);
		} else if(i >51000 && i <52000) {
			return "P51-"+(i-51000);
		} else if(i >52000 && i <53000) {
			return "P52-"+(i-52000);
		} else if(i >53000 && i <54000) {
			return "P53-"+(i-53000);
		} else if(i >54000 && i <55000) {
			return "P54-"+(i-54000);
		} else if(i >55000 && i <56000) {
			return "P55-"+(i-55000);
		} else if(i >56000 && i <57000) {
			return "P56-"+(i-56000);
		} else if(i >57000 && i <58000) {
			return "P57-"+(i-57000);
		} else if(i >58000 && i <59000) {
			return "P58-"+(i-58000);
		} else if(i >59000 && i <60000) {
			return "P59-"+(i-59000);
		} else if(i >60000 && i <61000) {
			return "P60-"+(i-60000);
		} else if(i >61000 && i <62000) {
			return "P61-"+(i-61000);
		} else if(i >62000 && i <63000) {
			return "P62-"+(i-62000);
		} else if(i >63000 && i <64000) {
			return "P63-"+(i-63000);
		} else if(i >64000 && i <65000) {
			return "P64-"+(i-64000);
		} else if(i >65000 && i <66000) {
			return "P65-"+(i-65000);
		} else if(i >66000 && i <67000) {
			return "P66-"+(i-66000);
		} else if(i >67000 && i <68000) {
			return "P67-"+(i-67000);
		} else if(i >68000 && i <69000) {
			return "P68-"+(i-68000);
		} else if(i >69000 && i <70000) {
			return "P69-"+(i-69000);
		} else if(i >70000 && i <71000) {
			return "P70-"+(i-70000);
		} else if(i >71000 && i <72000) {
			return "P71-"+(i-71000);
		} else if(i >72000 && i <73000) {
			return "P72-"+(i-72000);
		} else if(i >73000 && i <74000) {
			return "P73-"+(i-73000);
		} else if(i >74000 && i <75000) {
			return "P74-"+(i-74000);
		} else if(i >75000 && i <76000) {
			return "P75-"+(i-75000);
		} else if(i >76000 && i <77000) {
			return "P76-"+(i-76000);
		} else if(i >77000 && i <78000) {
			return "P77-"+(i-77000);
		} else if(i >78000 && i <79000) {
			return "P78-"+(i-78000);
		} else if(i >79000 && i <80000) {
			return "P79-"+(i-79000);
		} else if(i >80000 && i <81000) {
			return "P80-"+(i-80000);
		} else if(i >81000 && i <82000) {
			return "P81-"+(i-81000);
		} else if(i >82000 && i <83000) {
			return "P82-"+(i-82000);
		} else if(i >83000 && i <84000) {
			return "P83-"+(i-83000);
		} else if(i >84000 && i <85000) {
			return "P84-"+(i-84000);
		} else if(i >85000 && i <86000) {
			return "P85-"+(i-85000);
		} else if(i >86000 && i <87000) {
			return "P86-"+(i-86000);
		} else if(i >87000 && i <88000) {
			return "P87-"+(i-87000);
		} else if(i >88000 && i <89000) {
			return "P88-"+(i-88000);
		} else if(i >89000 && i <90000) {
			return "P89-"+(i-89000);
		} else if(i >90000 && i <91000) {
			return "P90-"+(i-90000);
		} else if(i >91000 && i <92000) {
			return "P91-"+(i-91000);
		} else if(i >92000 && i <93000) {
			return "P92-"+(i-92000);
		} else if(i >93000 && i <94000) {
			return "P93-"+(i-93000);
		} else if(i >94000 && i <95000) {
			return "P94-"+(i-94000);
		} else if(i >95000 && i <96000) {
			return "P95-"+(i-95000);
		} else if(i >96000 && i <97000) {
			return "P96-"+(i-96000);
		} else if(i >97000 && i <98000) {
			return "P97-"+(i-97000);
		} else if(i >98000 && i <99000) {
			return "P98-"+(i-98000);
		} else if(i >99000 && i <100000) {
			return "P99-"+(i-99000);
		} else if(i >100000 && i <101000) {
			return "P100-"+(i-100000);
		}

		return "P0-1";
	}
	
	
	

}
