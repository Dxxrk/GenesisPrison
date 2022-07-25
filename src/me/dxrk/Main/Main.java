package me.dxrk.Main;

import com.earth2me.essentials.Essentials;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import javax.security.auth.login.LoginException;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import net.milkbowl.vault.chat.Chat;
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
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Scoreboard;

import me.dxrk.Commands.BasicCommands;
import me.dxrk.Commands.BuycraftMessages;
import me.dxrk.Commands.CMDAc;
import me.dxrk.Commands.CMDAutosell;
import me.dxrk.Commands.CMDClearchat;
import me.dxrk.Commands.CMDDp;
import me.dxrk.Commands.CMDDr;
import me.dxrk.Commands.CMDItemEdits;
import me.dxrk.Commands.CMDPicksell;
import me.dxrk.Commands.CMDRankup;
import me.dxrk.Commands.CMDTrash;
import me.dxrk.Commands.CoinFlip;
import me.dxrk.Commands.DailyRewards;
import me.dxrk.Commands.Freeze;
import me.dxrk.Commands.GiveBook;
import me.dxrk.Commands.GiveRank;
import me.dxrk.Commands.MineCMD;
import me.dxrk.Commands.Nicks;
import me.dxrk.Commands.Options;
import me.dxrk.Commands.Rename;
import me.dxrk.Commands.Say;
import me.dxrk.Commands.Tags;
import me.dxrk.Commands.Updates;
import me.dxrk.Commands.Upgrade;
import me.dxrk.Commands.Vanish;
import me.dxrk.Commands.VoteShop;
import me.dxrk.Commands.Voting;
import me.dxrk.Commands.Welcome;
import me.dxrk.Discord.jdaHandler;
import me.dxrk.Events.BlockCounting;
import me.dxrk.Events.PickXPHandler;
import me.dxrk.Events.CrateHandler;
import me.dxrk.Events.CreateMine;
import me.dxrk.Events.DeathLogger;
import me.dxrk.Events.DonorItems;
import me.dxrk.Events.KeysHandler;
import me.dxrk.Events.KitAndWarp;
import me.dxrk.Events.Leaderboards;
import me.dxrk.Events.LocksmithHandler;
import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Events.ProtectOP;
import me.dxrk.Events.RankupHandler;
import me.dxrk.Events.ScoreboardHandler;
import me.dxrk.Events.SellHandler;
import me.dxrk.Events.Staff;
import me.dxrk.Events.TrinketHandler;
import me.dxrk.Tokens.EnchantTokensCommand;
import me.dxrk.Tokens.EnchantTokensListener;
import me.dxrk.Tokens.Enchants;
import me.dxrk.Tokens.Lottery;
import me.dxrk.Tokens.NPCHandler;
import me.dxrk.Tokens.PickaxeLevel;
import me.dxrk.Tokens.VirtualShop;

public class Main extends JavaPlugin implements Listener, CommandExecutor {
	
	
	private static  Main INSTANCE;
	
	
 
  
 
  
  public static Permission perms = null;
  
  public static Economy econ = null;
  
  public static Chat chat = null;
  
  public ArrayList<String> Sb = new ArrayList<String>();
  
  public static Plugin plugin;
  
  
  public static Scoreboard sb;
  
  SettingsManager settings = SettingsManager.getInstance();
  
  
  public static Essentials ess;
  
  public static String c(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	  }
  
  public void onEnable() {
	  
    plugin = (Plugin)this;
    INSTANCE = this;
    
    this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
    
    
    ess = (Essentials)Bukkit.getPluginManager().getPlugin("Essentials");
    setupEconomy();
    this.settings.setup((Plugin)this);
    setupPermissions();
    setupChat();
    
    if (this.settings.getData().getString("Broadcast") == null) {
      this.settings.getData().set("Broadcast", "Tesssttt");
      this.settings.saveData();
      this.settings.reloadData();
    }
    new jdaHandler(this);
    
    Lottery.getInstance().startLottery();
    
    Lottery.scheduleLottery(this, new Runnable()
    {
        public void run()
        {
            Lottery.lottery = true;
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), (c("bc &3&lLottery &8&l┃ &7&lLOTTERY IS NOW OPEN!")));
        }
    }, 4);
    Lottery.scheduleLottery(this, new Runnable()
    {
        public void run()
        {
            Lottery.lottery = false;
            Lottery.getInstance().ChooseWinner();
        }
    }, 3);
    
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
    
    
    getCommand("blocks").setExecutor((CommandExecutor)new BlockCounting());
    getCommand("joke").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("ChatColor").setExecutor((CommandExecutor)new me.dxrk.Events.Chat());
    getCommand("Chat").setExecutor((CommandExecutor)new me.dxrk.Events.Chat());
    getCommand("MuteChat").setExecutor((CommandExecutor)new me.dxrk.Events.Chat());
    getCommand("rename").setExecutor((CommandExecutor)new Rename());
    getCommand("renamepaper").setExecutor((CommandExecutor)new Rename());
    getCommand("GiveRank").setExecutor((CommandExecutor)new GiveRank());
    getCommand("relore").setExecutor((CommandExecutor)new Rename());
    getCommand("Vote").setExecutor((CommandExecutor)new Voting());
    getCommand("voteparty").setExecutor((CommandExecutor)new Voting());
    getCommand("voteshop").setExecutor((CommandExecutor)new VoteShop());
    getCommand("Tags").setExecutor((CommandExecutor)new Tags());
    getCommand("Help").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Website").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Trash").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("glow").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Glow").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("UpdateBC").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Disposal").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Dispose").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Color").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("RedeemDonor").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Updates").setExecutor((CommandExecutor)new Updates());
    getCommand("rewards").setExecutor((CommandExecutor)new Voting());
    getCommand("Fortune").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Prestiges").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("Say").setExecutor((CommandExecutor)new Say());
    //getCommand("epsilondp").setExecutor((CommandExecutor)new EpsilonDp());
    getCommand("upgrade").setExecutor((CommandExecutor)new Upgrade());
    getCommand("upgradebook").setExecutor((CommandExecutor)new Upgrade());
    getCommand("upgradebookrandom").setExecutor((CommandExecutor)new Upgrade());
    getCommand("CoinFlip").setExecutor((CommandExecutor)new CoinFlip());
    getCommand("Tokens").setExecutor((CommandExecutor)new EnchantTokensCommand());
    getCommand("Token").setExecutor((CommandExecutor)new EnchantTokensCommand());
    getCommand("Who").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("List").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("givebook").setExecutor((CommandExecutor)new GiveBook(this));
    getCommand("nick").setExecutor((CommandExecutor)new Nicks());
    getCommand("nickname").setExecutor((CommandExecutor)new Nicks());
    getCommand("nicknames").setExecutor((CommandExecutor)new Nicks());
    getCommand("withdraw").setExecutor((CommandExecutor)new BasicCommands());
    getCommand("welcome").setExecutor((CommandExecutor)new Welcome());
    getCommand("mine").setExecutor((CommandExecutor)new MineCMD());
    getCommand("freeze").setExecutor((CommandExecutor)new Freeze());
    getCommand("vanish").setExecutor((CommandExecutor)new Vanish());
    getCommand("v").setExecutor((CommandExecutor)new Vanish());
    getCommand("ev").setExecutor((CommandExecutor)new Vanish());
    getCommand("evanish").setExecutor((CommandExecutor)new Vanish());
    getCommand("pick").setExecutor((CommandExecutor)new PickaxeLevel());
    getCommand("pickaxe").setExecutor((CommandExecutor)new PickaxeLevel());
    getCommand("resetallmines").setExecutor((CommandExecutor)new PickaxeLevel());
    getCommand("motdchange").setExecutor((CommandExecutor)this);
    getCommand("prestige").setExecutor((CommandExecutor)new PickaxeLevel());
    getCommand("giveenchant").setExecutor((CommandExecutor)new DonorItems());
    getCommand("activeboost").setExecutor((CommandExecutor)new Boosts());
    getCommand("boost").setExecutor((CommandExecutor)new Boosts());
    getCommand("giveboost").setExecutor((CommandExecutor)new Boosts());
    getCommand("lottery").setExecutor((CommandExecutor)new Lottery());
    getCommand("blockstop").setExecutor((CommandExecutor)new Leaderboards());
    getCommand("givedust").setExecutor((CommandExecutor)new TrinketHandler());
    getCommand("givetrinket").setExecutor((CommandExecutor)new TrinketHandler());
    registerEvents((Plugin)this, new Listener[] { (Listener)new Freeze() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new VoteShop() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Welcome() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new EnchantTokensCommand() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Staff() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new KitAndWarp() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Tags() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new CoinFlip() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new GiveRank() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new ProtectOP() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new BasicCommands() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new VirtualShop() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new BlockCounting() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new me.dxrk.Events.Chat() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Voting() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new DeathLogger() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Upgrade() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new EnchantTokensListener() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new GiveBook(this) });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Nicks() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new BasicCommands() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new PickaxeLevel() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Enchants() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new PickXPHandler() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Boosts() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new NPCHandler() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Lottery() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new CreateMine() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new Leaderboards() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new MineCMD() });
    registerEvents((Plugin)this, new Listener[] { (Listener)new DonorItems() });
    registerEvents((Plugin)this, new Listener[] { (Listener)this});
    PluginManager pm = getServer().getPluginManager();
    //getCommand("rename").setExecutor((CommandExecutor)new CMDItemEdits());
    getCommand("relore").setExecutor((CommandExecutor)new CMDItemEdits());
    getCommand("addlore").setExecutor((CommandExecutor)new CMDItemEdits());
    getCommand("dellore").setExecutor((CommandExecutor)new CMDItemEdits());
    getCommand("trash").setExecutor((CommandExecutor)new CMDTrash());
    getCommand("ac").setExecutor((CommandExecutor)new CMDAc());
    getCommand("autosell").setExecutor((CommandExecutor)new CMDAutosell());
    getCommand("picksell").setExecutor((CommandExecutor)new CMDPicksell());
    getCommand("cratekey").setExecutor((CommandExecutor)new CrateHandler());
    getCommand("crateinfo").setExecutor((CommandExecutor)new CrateHandler());
    getCommand("openall").setExecutor((CommandExecutor)new CrateHandler());
    getCommand("backpack").setExecutor((CommandExecutor)new SellHandler());
    getCommand("bp").setExecutor((CommandExecutor)new SellHandler());
    getCommand("sellall").setExecutor((CommandExecutor)new SellHandler());
    getCommand("rankup").setExecutor((CommandExecutor)new CMDRankup());
    getCommand("maxrankup").setExecutor((CommandExecutor)new CMDRankup());
    getCommand("rankupmax").setExecutor((CommandExecutor)new CMDRankup());
    getCommand("locksmith").setExecutor((CommandExecutor)new LocksmithHandler());
    getCommand("ls").setExecutor((CommandExecutor)new LocksmithHandler());
    getCommand("keys").setExecutor((CommandExecutor)new LocksmithHandler());
    getCommand("clearchat").setExecutor((CommandExecutor)new CMDClearchat());
    getCommand("dr").setExecutor((CommandExecutor)new CMDDr());
    getCommand("dp").setExecutor((CommandExecutor)new CMDDp());
    getCommand("multi").setExecutor((CommandExecutor)new SellHandler());
    getCommand("withdraw").setExecutor((CommandExecutor)new SellHandler());
    getCommand("setrank").setExecutor((CommandExecutor)new CMDRankup());
    getCommand("forcereset").setExecutor((CommandExecutor)new SellHandler());
    getCommand("bpreset").setExecutor((CommandExecutor)new SellHandler());
    getCommand("resetmine").setExecutor((CommandExecutor)new SellHandler());
    getCommand("checkminereset").setExecutor((CommandExecutor)new CreateMine());
    getCommand("rm").setExecutor((CommandExecutor)new SellHandler());
    getCommand("autorankup").setExecutor((CommandExecutor)new RankupHandler());
    getCommand("aru").setExecutor((CommandExecutor)new RankupHandler());
    getCommand("giveplotitem").setExecutor((CommandExecutor)new CreateMine());
    getCommand("buymsg").setExecutor((CommandExecutor) new BuycraftMessages());
    getCommand("options").setExecutor((CommandExecutor)new Options());
    getCommand("daily").setExecutor((CommandExecutor)new DailyRewards());
    pm.registerEvents((Listener)new CrateHandler(), (Plugin)this);
    pm.registerEvents((Listener)new SellHandler(), (Plugin)this);
    pm.registerEvents((Listener)new PlayerDataHandler(), (Plugin)this);
    pm.registerEvents((Listener)new KeysHandler(), (Plugin)this);
    pm.registerEvents((Listener)new LocksmithHandler(), (Plugin)this);
    pm.registerEvents((Listener)new RankupHandler(),(Plugin)this);
    pm.registerEvents((Listener)new ScoreboardHandler(),(Plugin)this);
    pm.registerEvents((Listener)new TrinketHandler(),(Plugin)this);
    pm.registerEvents((Listener)new Options(),(Plugin)this);
    pm.registerEvents((Listener)new DailyRewards(),(Plugin)this);
    
    
    new BukkitRunnable() {

		@Override
		public void run() {
			for(Player p : Bukkit.getOnlinePlayers()) {
				if(!p.getItemInHand().getType().equals(Material.DIAMOND_PICKAXE)) return;
				 
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

 }.runTaskTimer((Plugin)this, 0L, 20*2L);
    
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

 }.runTaskTimer((Plugin)this, 0L, 20*2L);
 
 
 new BukkitRunnable() {

		@Override
		public void run() {
			Leaderboards.loadHolos();
			settings.savePlayerData();

		}

}.runTaskTimer((Plugin)this, 0L, 20*20L);

new BukkitRunnable() {

	@Override
	public void run() {
		Leaderboards.addTimePlayed();


	}

}.runTaskTimer((Plugin)this, 0L, 20L);
 
    
    
    
    
    Iterator<Recipe> it = getServer().recipeIterator();
    while (it.hasNext()) {
      Recipe re = it.next();
      if (re != null && re.getResult().getType() == Material.GOLDEN_APPLE)
        it.remove(); 
    }
    Boosts.nextUpsell.clear();
    Boosts.nextUpsell.addAll(this.settings.getBoost().getStringList("nextUpSell"));
    Boosts.nextUpxp.clear();
    Boosts.nextUpxp.addAll(this.settings.getBoost().getStringList("nextUpXP"));
    
    if(this.settings.getBoost().getDouble("ActiveSell.Amp") != 0.0D) {
    	int timeleft = this.settings.getBoost().getInt("TimeLeftSell");
    	double amp = this.settings.getBoost().getDouble("ActiveSell.Amp");
    	String act = this.settings.getBoost().getString("ActivatorSell");
    	this.settings.getBoost().set("TimeLeftSell", 0);
    	
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost sell " + act+" "+amp+" "+timeleft);
    	
    }
    if(this.settings.getBoost().getInt("ActiveXP.Amp") != 0) {
    	int timeleft = this.settings.getBoost().getInt("TimeLeftXP");
    	int amp = this.settings.getBoost().getInt("ActiveXP.Amp");
    	String act = this.settings.getBoost().getString("ActivatorXP");
    	this.settings.getBoost().set("TimeLeftXP", 0);
    	
    	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "activeboost XP " + act+" "+amp+" "+timeleft);
    	
    }
    
    
    
    
    Lottery.scheduleLottery((Plugin)this, new Runnable()
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
    
    
    Lottery.scheduleLottery((Plugin)this, new Runnable()
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
	        return String.format("%.1f Quint", new Object[] { Double.valueOf(amt / 1.0E18D) });
	    if(amt >= 1.0E15D)
	    	return String.format("%.1f Quad", new Object[] { Double.valueOf(amt / 1.0E15D) }); 
	    if (amt >= 1.0E12D)
	      return String.format("%.1f Tril", new Object[] { Double.valueOf(amt / 1.0E12D) }); 
	    if (amt >= 1.0E9D)
	      return String.format("%.1f Bil", new Object[] { Double.valueOf(amt / 1.0E9D) }); 
	    if (amt >= 1000000.0D)
	      return String.format("%.1f Mil", new Object[] { Double.valueOf(amt / 1000000.0D) }); 
	    return NumberFormat.getNumberInstance(Locale.US).format(amt);
	  }
  
  
  
  
  
  
  public void onDisable() {
	  
	  settings.saveVote();
	  
	  if(settings.getBoost().getDouble("ActiveSell.Amp") != 0.0D) {
	  this.settings.getBoost().set("TimeLeftSell", Boosts.selltime);
	  }
	  if(settings.getBoost().getDouble("ActiveXP.Amp") != 0.0D) {
		  this.settings.getBoost().set("TimeLeftXP", Boosts.xptime);
		  }
	  for(Player p : Bukkit.getOnlinePlayers()) {
		  Boosts.getInstance().saveBinv(p);
		  Boosts.boostsinv.remove(p);
	  }
	  
	  this.settings.getBoost().set("nextUpSell", Boosts.nextUpsell);
	  this.settings.getBoost().set("nextUpXP", Boosts.nextUpxp);
	  this.settings.saveboosts();
	  
	  this.settings.getVote().set("VoteShopLog", VoteShop.votelog);
	  this.settings.saveVote();
	  
	  
	  
    this.settings.saveData();
    BlockCounting.getInstance().onEnd();
    
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
    perms = (Permission)rsp.getProvider();
    return (perms != null);
  }
  
  private boolean setupChat() {
	  RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
	  chat = (Chat)rsp.getProvider();
	  return (chat != null);
	  
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
    econ = (Economy)rsp.getProvider();
    return (econ != null);
  }
  
  
  

}
