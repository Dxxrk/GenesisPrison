package me.dxrk.Events;

import com.earth2me.essentials.Essentials;
import com.gmail.filoghost.holographicdisplays.api.Hologram;
import me.dxrk.Main.Methods;
import me.dxrk.Main.SettingsManager;
import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Leaderboards implements Listener, CommandExecutor{
	public static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
	
	public static SettingsManager settings = SettingsManager.getInstance();
	Methods m = Methods.getInstance();
	
	
	static Essentials ess = (Essentials) Bukkit.getServer().getPluginManager().getPlugin("Essentials");

	public ItemStack Head(OfflinePlayer p) {
		ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
		SkullMeta meta = (SkullMeta) skull.getItemMeta();
		meta.setOwner(p.getName());
		skull.setItemMeta(meta);
		return skull;
	}
	public OfflinePlayer getOfflinePlayer(String uuid) {
		return Bukkit.getOfflinePlayer(UUID.fromString(uuid));
	}

	public void openLeaderboards(Player p) {
		Inventory lb = Bukkit.createInventory(null, 45, m.c("&6&lLeaderboards"));
		List<String> lore = new ArrayList<>();
		orderBlocks();
		Set<Map.Entry<Integer, String> > bentrySet = orderblocks.entrySet();
		List<Map.Entry<Integer, String> > blockstop = new ArrayList<>(bentrySet);
		OfflinePlayer p0 = getOfflinePlayer(blockstop.get(0).getValue());
		ItemStack blocks = Head(p0);
		ItemMeta bm = blocks.getItemMeta();
		bm.setDisplayName(m.c("&bBlocks Broken:"));
		if(blockstop.size() >= 1)
			lore.add(m.c("&7#1 &c"+getOfflinePlayer(blockstop.get(0).getValue()).getName()+": &b"+blockstop.get(0).getKey()));
		if(blockstop.size() >= 2)
			lore.add(m.c("&7#2 &c"+getOfflinePlayer(blockstop.get(1).getValue()).getName()+": &b"+blockstop.get(1).getKey()));
		if(blockstop.size() >= 3)
			lore.add(m.c("&7#3 &c"+getOfflinePlayer(blockstop.get(2).getValue()).getName()+": &b"+blockstop.get(2).getKey()));
		if(blockstop.size() >= 4)
			lore.add(m.c("&7#4 &c"+getOfflinePlayer(blockstop.get(3).getValue()).getName()+": &b"+blockstop.get(3).getKey()));
		if(blockstop.size() >= 5)
			lore.add(m.c("&7#5 &c"+getOfflinePlayer(blockstop.get(4).getValue()).getName()+": &b"+blockstop.get(4).getKey()));
		int block = 0;
		for(int i = 0; i < blockstop.size(); i++) {
			if(p.getName().equals(getOfflinePlayer(blockstop.get(i).getValue()).getName()))	{
				block = i;
			}
		}
		if(blockstop.size() > block+1)
			lore.add(m.c("&cYour Rank: #"+(block+1)+" With &b"+blockstop.get(block).getKey()));
		else {
			lore.add(m.c("&cYour Rank: #N/A With &bN/A"));
		}
		bm.setLore(lore);
		blocks.setItemMeta(bm);
		lb.setItem(11, blocks);
		lore.clear();

		orderTime();
		Set<Map.Entry<Integer, String> > tentrySet = ordertime.entrySet();
		List<Map.Entry<Integer, String> > ttop = new ArrayList<>(tentrySet);
		OfflinePlayer p1 = getOfflinePlayer(ttop.get(0).getValue());
		ItemStack time = Head(p1);
		ItemMeta tm = time.getItemMeta();
		tm.setDisplayName(m.c("&bTime Played:"));
		if(ttop.size() >= 1)
			lore.add(m.c("&7#1 &c"+getOfflinePlayer(ttop.get(0).getValue()).getName()+": &b"+formatTime(ttop.get(0).getKey())));
		if(ttop.size() >= 2)
			lore.add(m.c("&7#2 &c"+getOfflinePlayer(ttop.get(1).getValue()).getName()+": &b"+formatTime(ttop.get(1).getKey())));
		if(ttop.size() >= 3)
			lore.add(m.c("&7#3 &c"+getOfflinePlayer(ttop.get(2).getValue()).getName()+": &b"+formatTime(ttop.get(2).getKey())));
		if(ttop.size() >= 4)
			lore.add(m.c("&7#4 &c"+getOfflinePlayer(ttop.get(3).getValue()).getName()+": &b"+formatTime(ttop.get(3).getKey())));
		if(ttop.size() >= 5)
			lore.add(m.c("&7#5 &c"+getOfflinePlayer(ttop.get(4).getValue()).getName()+": &b"+formatTime(ttop.get(4).getKey())));
		int t = 0;
		for(int i = 0; i < ttop.size(); i++) {
			if(p.getName().equals(getOfflinePlayer(ttop.get(i).getValue()).getName()))	{
				t= i;
			}
		}
		if(ttop.size() > t+1)
			lore.add(m.c("&cYour Rank: #"+(t+1)+" With &b"+formatTime(ttop.get(t).getKey())));
		else {
			lore.add(m.c("&cYour Rank: #N/A With &bN/A"));
		}
		tm.setLore(lore);
		time.setItemMeta(tm);
		lb.setItem(15, time);
		lore.clear();

		orderRanks();
		Set<Map.Entry<Integer, String> > rentrySet = orderrank.entrySet();
		List<Map.Entry<Integer, String> > rankstop = new ArrayList<>(rentrySet);
		OfflinePlayer p2 = getOfflinePlayer(rankstop.get(0).getValue());
		ItemStack ranks = Head(p2);
		ItemMeta rm = ranks.getItemMeta();
		rm.setDisplayName(m.c("&bHighest Rank:"));
		if(rankstop.size() >= 1)
			lore.add(m.c("&7#1 &c"+getOfflinePlayer(rankstop.get(0).getValue()).getName()+": &7Prestiges: &a"+settings.getPlayerData().getInt(rankstop.get(0).getValue()+".Prestiges")+" &7Level: &b"+settings.getRankupPrices().getInt(rankstop.get(0).getValue())));
		if(rankstop.size() >= 2)
			lore.add(m.c("&7#2 &c"+getOfflinePlayer(rankstop.get(1).getValue()).getName()+": &7Prestiges: &a"+settings.getPlayerData().getInt(rankstop.get(1).getValue()+".Prestiges")+" &7Level: &b"+settings.getRankupPrices().getInt(rankstop.get(1).getValue())));
		if(rankstop.size() >= 3)
			lore.add(m.c("&7#3 &c"+getOfflinePlayer(rankstop.get(2).getValue()).getName()+": &7Prestiges: &a"+settings.getPlayerData().getInt(rankstop.get(2).getValue()+".Prestiges")+" &7Level: &b"+settings.getRankupPrices().getInt(rankstop.get(2).getValue())));
		if(rankstop.size() >= 4)
			lore.add(m.c("&7#4 &c"+getOfflinePlayer(rankstop.get(3).getValue()).getName()+": &7Prestiges: &a"+settings.getPlayerData().getInt(rankstop.get(3).getValue()+".Prestiges")+" &7Level: &b"+settings.getRankupPrices().getInt(rankstop.get(3).getValue())));
		if(rankstop.size() >= 5)
			lore.add(m.c("&7#5 &c"+getOfflinePlayer(rankstop.get(4).getValue()).getName()+": &7Prestiges: &a"+settings.getPlayerData().getInt(rankstop.get(4).getValue()+".Prestiges")+" &7Level: &b"+settings.getRankupPrices().getInt(rankstop.get(4).getValue())));
		int r = 0;
		for(int i = 0; i < rankstop.size(); i++) {
			if(p.getName().equals(getOfflinePlayer(rankstop.get(i).getValue()).getName())) {
				r = i;
			}
		}
		if(rankstop.size() > r+1)
			lore.add(m.c("&cYour Rank: #"+(r+1)+" With &b"+": &7Prestiges: &a"+settings.getPlayerData().getInt(rankstop.get(r).getValue()+".Prestiges")+" &7Level: &b"+settings.getRankupPrices().getInt(rankstop.get(r).getValue())));
		else {
			lore.add(m.c("&cYour Rank: #N/A With &bN/A"));
		}
		rm.setLore(lore);
		ranks.setItemMeta(rm);
		lb.setItem(29, ranks);
		lore.clear();

		orderVote();
		Set<Map.Entry<Integer, String> > ventrySet = ordervote.entrySet();
		List<Map.Entry<Integer, String> > votetop = new ArrayList<>(ventrySet);
		OfflinePlayer p3 = getOfflinePlayer(votetop.get(0).getValue());
		ItemStack vote = Head(p3);
		ItemMeta vm = vote.getItemMeta();
		vm.setDisplayName(m.c("&bMost Votes:"));
		if(votetop.size() >=1)
			lore.add(m.c("&7#1 &c"+getOfflinePlayer(votetop.get(0).getValue()).getName()+": &b"+getVotes(getOfflinePlayer(votetop.get(0).getValue()))));
		if(votetop.size() >=2)
			lore.add(m.c("&7#2 &c"+getOfflinePlayer(votetop.get(1).getValue()).getName()+": &b"+getVotes(getOfflinePlayer(votetop.get(1).getValue()))));
		if(votetop.size() >=3)
			lore.add(m.c("&7#3 &c"+getOfflinePlayer(votetop.get(2).getValue()).getName()+": &b"+getVotes(getOfflinePlayer(votetop.get(2).getValue()))));
		if(votetop.size() >=4)
			lore.add(m.c("&7#4 &c"+getOfflinePlayer(votetop.get(3).getValue()).getName()+": &b"+getVotes(getOfflinePlayer(votetop.get(3).getValue()))));
		if(votetop.size() >=5)
			lore.add(m.c("&7#5 &c"+getOfflinePlayer(votetop.get(4).getValue()).getName()+": &b"+getVotes(getOfflinePlayer(votetop.get(4).getValue()))));
		int v = 0;
		for(int i = 0; i < votetop.size(); i++) {
			if(p.getName().equals(getOfflinePlayer(votetop.get(i).getValue()).getName())) {
				v= i;
			}
		}
		lore.add(m.c("&cYour Rank: #"+(v+1)+" With &b"+getVotes(getOfflinePlayer(votetop.get(v).getValue()))));
		vm.setLore(lore);
		vote.setItemMeta(vm);
		lb.setItem(33, vote);
		lore.clear();

		orderGangs();
		Set<Map.Entry<Integer, String> > gentrySet = ordergang.entrySet();
		List<Map.Entry<Integer, String> > gangstop = new ArrayList<>(gentrySet);
		OfflinePlayer p4 = getOfflinePlayer(settings.getGangs().getString(gangstop.get(0).getValue()+".Owner"));
		ItemStack gangs = Head(p4);
		ItemMeta gm = gangs.getItemMeta();
		gm.setDisplayName(m.c("&bHighest Gang:"));
		if(gangstop.size() >= 1)
			lore.add(m.c("&7#1 &c"+gangstop.get(0).getValue()+": &b"+settings.getGangs().getString(gangstop.get(0).getValue()+".BlocksBroken")));
		if(gangstop.size() >= 2)
			lore.add(m.c("&7#2 &c"+gangstop.get(1).getValue()+": &b"+settings.getGangs().getString(gangstop.get(1).getValue()+".BlocksBroken")));
		if(gangstop.size() >= 3)
			lore.add(m.c("&7#3 &c"+gangstop.get(2).getValue()+": &b"+settings.getGangs().getString(gangstop.get(2).getValue()+".BlocksBroken")));
		if(gangstop.size() >= 4)
			lore.add(m.c("&7#4 &c"+gangstop.get(3).getValue()+": &b"+settings.getGangs().getString(gangstop.get(3).getValue()+".BlocksBroken")));
		if(gangstop.size() >= 5)
			lore.add(m.c("&7#5 &c"+gangstop.get(4).getValue()+": &b"+settings.getGangs().getString(gangstop.get(4).getValue()+".BlocksBroken")));
		int g = 0;
		for(int i = 0; i < gangstop.size(); i++) {
			if(settings.getGangs().getStringList(gangstop.get(i).getValue()+".Members").contains(p.getUniqueId().toString()) || settings.getGangs().getString(gangstop.get(i).getValue()+".Owner").equals(p.getUniqueId().toString()))	{
				g = i;
			}
		}
		if(gangstop.size() > g+1)
			lore.add(m.c("&cYour Rank: #"+(g+1)+" With &b"+settings.getGangs().getString(gangstop.get(g).getValue()+".BlocksBroken")));
		else {
			lore.add(m.c("&cYour Rank: #N/A With &bN/A"));
		}
		gm.setLore(lore);
		gangs.setItemMeta(gm);
		lb.setItem(22, gangs);
		lore.clear();
		p.openInventory(lb);
		
	}

	@EventHandler
	public void onInv(InventoryClickEvent e) {
		if(e.getClickedInventory() == null) return;
		if(e.getClickedInventory().getName().equals(m.c("&6&lLeaderboards"))){
			e.setCancelled(true);
		}
	}

	
	
	public static SortedMap<Integer, String> orderblocks = new TreeMap<>(Collections.reverseOrder());
	public static SortedMap<Integer, String> ordertime = new TreeMap<>(Collections.reverseOrder());
	public static SortedMap<Integer, String> orderrank = new TreeMap<>(Collections.reverseOrder());
	public static SortedMap<Integer, String> ordergang = new TreeMap<>(Collections.reverseOrder());
	public static SortedMap<Integer, String> ordervote = new TreeMap<>(Collections.reverseOrder());

	public static void orderVote() {
		ordervote.clear();
		for (String uuid : settings.getVote().getKeys(false)) {
			int votes = settings.getVote().getInt(uuid+".Votes");
			ordervote.put(votes, uuid);
		}
	}



	public static void orderGangs() {
		ordergang.clear();
		for (String gangname : settings.getGangs().getKeys(false)) {
			int ganglevel = settings.getGangs().getInt(gangname+".BlocksBroken");
			ordergang.put(ganglevel, gangname);
		}
	}
	public static void orderRanks() {
		orderrank.clear();
		for (String uuid : settings.getPlayerData().getKeys(false)) {
			if(uuid.equals("7dd67277-1c1a-42e7-98ac-aa64eb122ec8")) continue;
			if(uuid.equals("c32dfc2e-6780-4dbb-9baf-9ca671fbd35f")) continue;
			if(uuid.equals("6a137295-d6e3-4a4d-b4e8-9d09898f9057")) continue;
			if(uuid.equals("8ae918d9-21ad-4184-a26e-abcf8d0ac6d9")) continue;
			int prestige = settings.getPlayerData().getInt(uuid+".Prestiges")*50000;
			int rank = settings.getRankupPrices().getInt(uuid);
			int total = prestige+rank;
			orderrank.put(total, uuid);

		}
	}
	
	public static void orderBlocks() {
		orderblocks.clear();
		for(String uuid : settings.getPlayerData().getKeys(false)) {
			if(uuid.equals("7dd67277-1c1a-42e7-98ac-aa64eb122ec8")) continue;
			if(uuid.equals("c32dfc2e-6780-4dbb-9baf-9ca671fbd35f")) continue;
			if(uuid.equals("8ae918d9-21ad-4184-a26e-abcf8d0ac6d9")) continue;
			int blocks = settings.getPlayerData().getInt(uuid+".BlocksBroken");
		    orderblocks.put(blocks, uuid);
		}
	}
	public static void orderTime() {
		ordertime.clear();
		for(String uuid : settings.getPlayerData().getKeys(false)) {
			if(uuid.equals("7dd67277-1c1a-42e7-98ac-aa64eb122ec8")) continue;
			if(uuid.equals("c32dfc2e-6780-4dbb-9baf-9ca671fbd35f")) continue;
			if(uuid.equals("8ae918d9-21ad-4184-a26e-abcf8d0ac6d9")) continue;
			int time = settings.getPlayerData().getInt(uuid+".TimePlayed");
		    ordertime.put(time, uuid);
		}
	}
	
	
	public static void addTimePlayed() {
		
				for(Player p : Bukkit.getOnlinePlayers()) {
					if(p.getName().equals("Dxrk")) continue;
					if(p.getName().equals("32j")) continue;
					if(p.getName().equals("Drinkk")) continue;
				if(!p.isOnline()) continue;
				if(ess.getUser(p) != null && ess.getUser(p).isAfk()) continue;
				
				int time = settings.getPlayerData().getInt(p.getUniqueId().toString()+".TimePlayed");
				settings.getPlayerData().set(p.getUniqueId().toString()+".TimePlayed", time+1);
				
				
				
			}
				
	}
	
	public static String formatTime(int seconds){
        int day = (int)TimeUnit.SECONDS.toDays(seconds);        
        long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
        long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
        long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
        return c("&b"+day+"D "+hours+"H "+minute+"M "+second+"S");
    }
	
	
	public static void deleteIfOld(Hologram hologram) {

		long tenMinutesMillis = 14 * 1000; // 14 seconds in milliseconds
		long elapsedMillis = System.currentTimeMillis() - hologram.getCreationTimestamp(); // Milliseconds elapsed from the creation of the hologram

		if (elapsedMillis > tenMinutesMillis) {
			hologram.delete();
		}
	}
	public static int getVotes(OfflinePlayer p) {
	    return settings.getVote().getInt(p.getUniqueId().toString() + ".Votes");
	  }




	
	

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("leaderboard") || cmd.getName().equalsIgnoreCase("leaderboards") || cmd.getName().equalsIgnoreCase("lb") || cmd.getName().equalsIgnoreCase("lbs")) {
			openLeaderboards((Player)cs);
		}
		
		return false;
	}


	
	
	

}
