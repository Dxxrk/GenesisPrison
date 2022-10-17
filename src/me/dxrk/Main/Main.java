package me.dxrk.Main;

import me.dxrk.Enchants.Enchants;
import com.earth2me.essentials.Essentials;
import me.dxrk.Commands.*;
import me.dxrk.Discord.JDAEvents;
import me.dxrk.Discord.jdaHandler;
import me.dxrk.Events.*;
import me.dxrk.Tokens.*;
import me.dxrk.Enchants.*;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.inventory.Recipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	
	
	private static  Main INSTANCE;
	
	
 
  
 
  
  public static Permission perms = null;
  
  public static Economy econ = null;
  
  public static Chat chat = null;
  
  public ArrayList<String> Sb = new ArrayList<>();
  
  public static Plugin plugin;
  
  
  public static Scoreboard sb;
  
  SettingsManager settings = SettingsManager.getInstance();
  
  
  public static Essentials ess;
  
  public static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  
  public void onEnable() {
	  
    plugin = this;
    INSTANCE = this;
    
    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    
    
    ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
    setupEconomy();
    this.settings.setup(this);
    setupPermissions();
    setupChat();
    
    if (this.settings.getData().getString("Broadcast") == null) {
      this.settings.getData().set("Broadcast", "Tesssttt");
      this.settings.saveData();
      this.settings.reloadData();
    }
    new jdaHandler(this);
    

    
    TrinketHandler t = new TrinketHandler();
    t.customDustCommon();
    t.customDustRare();
    t.customDustEpic();
    t.customDustLegendary();
    t.customDustHeroic();
    t.commonDusting();
    t.rareDusting();
    t.epicDusting();
    t.legendaryDusting();
    
    
    getCommand("blocks").setExecutor(new BlocksHandler());
    getCommand("ChatColor").setExecutor(new ChatHandler());
    getCommand("Chat").setExecutor(new ChatHandler());
    getCommand("MuteChat").setExecutor(new ChatHandler());
    getCommand("rename").setExecutor(new CMDRename());
    getCommand("renamepaper").setExecutor(new CMDRename());
    getCommand("relore").setExecutor(new CMDRename());
    getCommand("Vote").setExecutor(new CMDVote());
    getCommand("voteparty").setExecutor(new CMDVote());
    getCommand("voteshop").setExecutor(new CMDVoteShop());
    getCommand("Tags").setExecutor(new CMDTags());
    getCommand("rewards").setExecutor(new CMDVote());
    getCommand("Say").setExecutor(new CMDSay());
    getCommand("CoinFlip").setExecutor(new CMDCoinflip());
    getCommand("Tokens").setExecutor(new TokensCMD());
    getCommand("Token").setExecutor(new TokensCMD());
    getCommand("nick").setExecutor(new CMDNickname());
    getCommand("nickname").setExecutor(new CMDNickname());
    getCommand("nicknames").setExecutor(new CMDNickname());
    getCommand("mine").setExecutor(new CMDMine());
    getCommand("vanish").setExecutor(new CMDVanish());
    getCommand("v").setExecutor(new CMDVanish());
    getCommand("ev").setExecutor(new CMDVanish());
    getCommand("evanish").setExecutor(new CMDVanish());
    getCommand("pick").setExecutor(new PickaxeLevel());
    getCommand("pickaxe").setExecutor(new PickaxeLevel());
    getCommand("resetallmines").setExecutor(new PickaxeLevel());
    getCommand("motdchange").setExecutor(this);
    getCommand("prestige").setExecutor(new PickaxeLevel());
    getCommand("giveenchant").setExecutor(new DonorItems());
    getCommand("activeboost").setExecutor(new BoostsHandler());
    getCommand("boost").setExecutor(new BoostsHandler());
    getCommand("giveboost").setExecutor(new BoostsHandler());
    getCommand("blockstop").setExecutor(new Leaderboards());
    getCommand("givedust").setExecutor(new TrinketHandler());
    getCommand("givetrinket").setExecutor(new TrinketHandler());
    getCommand("relore").setExecutor(new CMDItemEdits());
    getCommand("addlore").setExecutor(new CMDItemEdits());
    getCommand("dellore").setExecutor(new CMDItemEdits());
    getCommand("trash").setExecutor(new CMDTrash());
    getCommand("ac").setExecutor(new CMDAc());
    getCommand("cratekey").setExecutor(new CrateHandler());
    getCommand("crateinfo").setExecutor(new CrateHandler());
    getCommand("openall").setExecutor(new CrateHandler());
    getCommand("rankup").setExecutor(new CMDRankup());
    getCommand("maxrankup").setExecutor(new CMDRankup());
    getCommand("rankupmax").setExecutor(new CMDRankup());
    getCommand("locksmith").setExecutor(new LocksmithHandler());
    getCommand("ls").setExecutor(new LocksmithHandler());
    getCommand("keys").setExecutor(new LocksmithHandler());
    getCommand("clearchat").setExecutor(new CMDClearchat());
    getCommand("dr").setExecutor(new CMDDr());
    getCommand("dp").setExecutor(new CMDDp());
    getCommand("multi").setExecutor(new SellHandler());
    getCommand("withdraw").setExecutor(new SellHandler());
    getCommand("setrank").setExecutor(new CMDRankup());
    getCommand("forcereset").setExecutor(new SellHandler());
    getCommand("bpreset").setExecutor(new SellHandler());
    getCommand("resetmine").setExecutor(new SellHandler());
    getCommand("checkminereset").setExecutor(new MineHandler());
    getCommand("rm").setExecutor(new SellHandler());
    getCommand("autorankup").setExecutor(new RankupHandler());
    getCommand("aru").setExecutor(new RankupHandler());
    getCommand("giveplotitem").setExecutor(new MineHandler());
    getCommand("buymsg").setExecutor( new CMDBuycraft());
    getCommand("options").setExecutor(new CMDOptions());
    getCommand("daily").setExecutor(new CMDDaily());
    getCommand("discord").setExecutor( new JDAEvents());
    getCommand("ranktop").setExecutor( new Leaderboards());
    getCommand("rankstop").setExecutor( new Leaderboards());
    getCommand("givemoney").setExecutor( new RankupHandler());
    getCommand("randomtag").setExecutor( new TagsHandler());
    getCommand("givecrate").setExecutor(new MysteryBoxHandler());
    getCommand("testchestopen").setExecutor(new MysteryBoxHandler());
    registerEvents(this, new Listener[] { new CMDVoteShop() });
    registerEvents(this, new Listener[] { new TokensCMD() });
    registerEvents(this, new Listener[] { new KitAndWarp() });
    registerEvents(this, new Listener[] { new CMDTags() });
    registerEvents(this, new Listener[] { new CMDCoinflip() });
    registerEvents(this, new Listener[] { new ProtectOP() });
    registerEvents(this, new Listener[] { new TokenShop() });
    registerEvents(this, new Listener[] { new BlocksHandler() });
    registerEvents(this, new Listener[] { new ChatHandler() });
    registerEvents(this, new Listener[] { new CMDVote() });
    registerEvents(this, new Listener[] { new DeathLogger() });
    registerEvents(this, new Listener[] { new TokensListener() });
    registerEvents(this, new Listener[] { new CMDNickname() });
    registerEvents(this, new Listener[] { new PickaxeLevel() });
    registerEvents(this, new Listener[] { new Enchants() });
    registerEvents(this, new Listener[] { new PickXPHandler() });
    registerEvents(this, new Listener[] { new BoostsHandler() });
    registerEvents(this, new Listener[] { new MineHandler() });
    registerEvents(this, new Listener[] { new Leaderboards() });
    registerEvents(this, new Listener[] { new CMDMine() });
    registerEvents(this, new Listener[] { new DonorItems() });
    registerEvents(this, new Listener[] { new CrateHandler() });
    registerEvents(this, new Listener[] { new SellHandler() });
    registerEvents(this, new Listener[] { new PlayerDataHandler() });
    registerEvents(this, new Listener[] { new KeysHandler() });
    registerEvents(this, new Listener[] { new LocksmithHandler() });
    registerEvents(this, new Listener[] { new RankupHandler() });
    registerEvents(this, new Listener[] { new ScoreboardHandler() });
    registerEvents(this, new Listener[] { new TrinketHandler() });
    registerEvents(this, new Listener[] { new CMDOptions() });
    registerEvents(this, new Listener[] { new CMDDaily() });
    registerEvents(this, new Listener[] { new JDAEvents() });
    registerEvents(this, new Listener[] { new MysteryBoxHandler() });
    registerEvents(this, new Listener[] { this});

    

      JDAEvents.getInstance().serverLink();

    new BukkitRunnable() {

		@Override
		public void run() {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(!p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) return;
                if(!p.getItemInHand().hasItemMeta()) return;
                if(!p.getItemInHand().getItemMeta().hasLore()) return;
				 
				int level = PickXPHandler.getBlocks(p.getItemInHand().getItemMeta().getLore().get(1));
				p.setLevel(level);
				
				float xp =(float) level/55555;
				if(xp >1.0) {
					p.setExp(1f);
				} else {
				p.setExp(xp);
				}
			if(!p.isOnline()) {
				cancel();
			}
			
			}
			
			
 

		}

 }.runTaskTimer(this, 0L, 20*2L);
    
    new BukkitRunnable() {

		@Override
		public void run() {
			for(Player p : Bukkit.getOnlinePlayers()) {
			ScoreboardHandler.updateSB(p);
			
			if (!p.isOnline()) {
			     cancel();
			 }
			}
			
			
 

		}

 }.runTaskTimer(this, 0L, 20*2L);
 
 
 new BukkitRunnable() {

		@Override
		public void run() {
			Leaderboards.loadHolos();
			settings.savePlayerData();

		}

}.runTaskTimer(this, 0L, 20*20L);

new BukkitRunnable() {

	@Override
	public void run() {
		Leaderboards.addTimePlayed();


	}

}.runTaskTimer(this, 0L, 20L);
 
    
    
    
    
    Iterator<Recipe> it = getServer().recipeIterator();
    while (it.hasNext()) {
      Recipe re = it.next();
      if (re != null && re.getResult().getType() == Material.GOLDEN_APPLE)
        it.remove(); 
    }
    BoostsHandler.nextUpsell.clear();
    BoostsHandler.nextUpsell.addAll(this.settings.getBoost().getStringList("nextUpSell"));
    BoostsHandler.nextUpxp.clear();
    BoostsHandler.nextUpxp.addAll(this.settings.getBoost().getStringList("nextUpXP"));
    
    if(this.settings.getBoost().getDouble("ActiveSell.Amp") != 0.0D) {
    	int timeleft = this.settings.getBoost().getInt("TimeLeftSell");
    	double amp = this.settings.getBoost().getDouble("ActiveSell.Amp");
    	String act = this.settings.getBoost().getString("ActivatorSell");
    	this.settings.getBoost().set("TimeLeftSell", 0);
        new BukkitRunnable() {
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost sell " + act + " " + amp + " " + timeleft);
            }
        }.runTaskLater(this, 20*5L);
    	
    }
    if(this.settings.getBoost().getInt("ActiveXP.Amp") != 0) {
    	int timeleft = this.settings.getBoost().getInt("TimeLeftXP");
    	int amp = this.settings.getBoost().getInt("ActiveXP.Amp");
    	String act = this.settings.getBoost().getString("ActivatorXP");
    	this.settings.getBoost().set("TimeLeftXP", 0);
    	new BukkitRunnable() {
            public void run() {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(),"activeboost XP "+act+" "+amp+" "+timeleft);
            }


        }.runTaskLater(this, 20*5L);
    	
    }
    
    
    
    
    Methods.schedule(this, new Runnable()
    {
        public void run()
        {
            
           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 1 Hour!");
           
           new BukkitRunnable() {
        	   @Override
        	   public void run() {
        		   Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 30 Minutes!");
        	   }
           }.runTaskLater(Main.plugin, 20*60*30);
           new BukkitRunnable() {
        	   @Override
        	   public void run() {
        		   Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 10 Minutes!");
        	   }
           }.runTaskLater(Main.plugin, 20*60*50);
           new BukkitRunnable() {
        	   @Override
        	   public void run() {
        		   Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "bc &cServer Restart in 1 Minute!");
        	   }
           }.runTaskLater(Main.plugin, 20*60*59);
           
           
           
        }
    }, 3);
    
    
    Methods.schedule(this, new Runnable()
	    {
	        public void run()
	        {
	            
	           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "restart");
	        }
	    }, 4);
    
    
    
    
  }
  
  
 
  
  public static Main getInstance() {
	  return INSTANCE;
  }
  
  
  public boolean motd = false;
  
  @Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
	  
	  if(label.equalsIgnoreCase("motdchange")) {
		  if(args[0].equalsIgnoreCase("closed")) {
			  motd = false;
		  }
		  if(args[0].equalsIgnoreCase("closed")) {
			  motd = true;
		  }
	  }
	  
	  
	  
	  return false;
  }
  
  @EventHandler
  public void motd(ServerListPingEvent e) {
	  e.setMotd(c("                        &9&lGenesis &b&lNetwork!\n               &d&lPrison Season 2 Out Now!"));
  }
  
  
  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
	  if(!e.getPlayer().hasPlayedBefore()) {
		  Bukkit.broadcastMessage(c("&dWelcome &f&l"+e.getPlayer().getName()+"&d to &9&lGenesis &b&lPrison!"));
	  }
  }
 
	  
  public static String formatAmt(double amt) {
	    if (amt <= 0.0D)
	      return String.valueOf(0); 
	    if (amt >= 1.0E18D)
	        return String.format("%.1f Quint", amt / 1.0E18D);
	    if(amt >= 1.0E15D)
	    	return String.format("%.1f Quad", amt / 1.0E15D);
	    if (amt >= 1.0E12D)
	      return String.format("%.1f Tril", amt / 1.0E12D);
	    if (amt >= 1.0E9D)
	      return String.format("%.1f Bil", amt / 1.0E9D);
	    if (amt >= 1000000.0D)
	      return String.format("%.1f Mil", amt / 1000000.0D);
	    return NumberFormat.getNumberInstance(Locale.US).format(amt);
	  }
  
  
  
  
  
  
  public void onDisable() {
	  
	  settings.saveVote();
	  
	  if(settings.getBoost().getDouble("ActiveSell.Amp") != 0.0D) {
	  this.settings.getBoost().set("TimeLeftSell", BoostsHandler.selltime);
	  }
	  if(settings.getBoost().getDouble("ActiveXP.Amp") != 0.0D) {
		  this.settings.getBoost().set("TimeLeftXP", BoostsHandler.xptime);
		  }
	  for(Player p : Bukkit.getOnlinePlayers()) {
		  BoostsHandler.getInstance().saveBinv(p);
		  BoostsHandler.boostsinv.remove(p);
	  }
	  
	  this.settings.getBoost().set("nextUpSell", BoostsHandler.nextUpsell);
	  this.settings.getBoost().set("nextUpXP", BoostsHandler.nextUpxp);
	  this.settings.saveboosts();
	  
	  this.settings.getVote().set("VoteShopLog", CMDVoteShop.votelog);
	  this.settings.saveVote();
	  
	  
	  
    this.settings.saveData();
    BlocksHandler.getInstance().onEnd();
    
    for(Player p : Bukkit.getOnlinePlayers()) {
    ByteArrayOutputStream b = new ByteArrayOutputStream();
	DataOutputStream out = new DataOutputStream(b);
	try {
	out.writeUTF("Connect");
	out.writeUTF("Lobby");
	} catch (IOException eee) {
	}
	p.sendPluginMessage(this, "BungeeCord", b.toByteArray());
    }
    
    
  }
  
  private boolean setupPermissions() {
    RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
    perms = rsp.getProvider();
    return perms != null;
  }
  
  private boolean setupChat() {
	  RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
	  chat = rsp.getProvider();
	  return chat != null;
	  
  }
  
  
  public static void registerEvents(Plugin plugin, Listener[] listeners) {
    byte b;
    int i;
    Listener[] arrayOfListener;
    for (i = (arrayOfListener = listeners).length, b = 0; b < i; ) {
      Listener listener = arrayOfListener[b];
      Bukkit.getServer().getPluginManager().registerEvents(listener, plugin);
      b++;
    } 
  }
  
  private boolean setupEconomy() {
    if (getServer().getPluginManager().getPlugin("Vault") == null)
      return false;
    RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
    if (rsp == null)
      return false;
    econ = rsp.getProvider();
    return econ != null;
  }
  
  
  

}
