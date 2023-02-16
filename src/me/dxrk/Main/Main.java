package me.dxrk.Main;

import me.dxrk.Enchants.Enchants;
import com.earth2me.essentials.Essentials;
import me.dxrk.Commands.*;
import me.dxrk.Discord.JDAEvents;
import me.dxrk.Discord.jdaHandler;
import me.dxrk.Events.*;
import me.dxrk.Gangs.CMDGang;
import me.dxrk.Tokens.*;
import me.dxrk.Enchants.*;
import me.dxrk.Vote.BuycraftUtil;
import me.dxrk.Vote.CMDVote;
import me.dxrk.Vote.CMDVoteShop;
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
import org.bukkit.inventory.ItemStack;
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
import java.util.*;

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
      }
      new jdaHandler(this);

      if(settings.getOptions().getString("DiscordToken") == null){
          settings.getOptions().set("DiscordToken", "");
        }
    

    
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
      getCommand("coupon").setExecutor(new CMDVoteShop());
      getCommand("coupons").setExecutor(new CMDVoteShop());
      getCommand("addvotepoint").setExecutor(new CMDVoteShop());
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
    //  getCommand("laser").setExecutor(new PickaxeLevel());
      getCommand("motdchange").setExecutor(this);
      getCommand("workmode").setExecutor(this);
      getCommand("prestige").setExecutor(new PrestigeHandler());
      getCommand("giveenchant").setExecutor(new DonorItems());
      getCommand("activeboost").setExecutor(new BoostsHandler());
      getCommand("boost").setExecutor(new BoostsHandler());
      getCommand("giveboost").setExecutor(new BoostsHandler());
      getCommand("blockstop").setExecutor(new Leaderboards());
      getCommand("givedust").setExecutor(new TrinketHandler());
      getCommand("givetrinket").setExecutor(new TrinketHandler());
      getCommand("trinket").setExecutor(new TrinketHandler());
      getCommand("trinkets").setExecutor(new TrinketHandler());
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
      getCommand("buymsg").setExecutor( new BuycraftUtil());
      getCommand("createcoupon").setExecutor( new BuycraftUtil());
      getCommand("options").setExecutor(new CMDOptions());
      getCommand("daily").setExecutor(new CMDDaily());
      getCommand("discord").setExecutor( new JDAEvents());
      getCommand("ranktop").setExecutor( new Leaderboards());
      getCommand("rankstop").setExecutor( new Leaderboards());
      getCommand("givemoney").setExecutor( new RankupHandler());
      getCommand("randomtag").setExecutor( new TagsHandler());
      getCommand("givecrate").setExecutor(new MysteryBoxHandler());
      getCommand("givexp").setExecutor(new MysteryBoxHandler());
      getCommand("giverank").setExecutor(new CMDRanks());
      getCommand("removemine").setExecutor(new MineHandler());
      getCommand("updatemine").setExecutor(new MineHandler());
      getCommand("redeem").setExecutor(new BuycraftUtil());
      getCommand("stats").setExecutor(new CMDStats());
      getCommand("ah").setExecutor(new AuctionHouseHandler());
      getCommand("auctionhouse").setExecutor(new AuctionHouseHandler());
      getCommand("gems").setExecutor(new MinePouchHandler());
      getCommand("gem").setExecutor(new MinePouchHandler());
      getCommand("help").setExecutor(new CMDHelp());
      getCommand("leaderboard").setExecutor(new Leaderboards());
      getCommand("leaderboards").setExecutor(new Leaderboards());
      getCommand("lb").setExecutor(new Leaderboards());
      getCommand("lbs").setExecutor(new Leaderboards());
      getCommand("trade").setExecutor(new CMDTrade());
      getCommand("gang").setExecutor(new CMDGang());
      registerEvents(this, new Listener[] { new CMDGang() });
      registerEvents(this, new Listener[] { new CMDTrade() });
      registerEvents(this, new Listener[] { new Leaderboards() });
      registerEvents(this, new Listener[] { new CMDHelp() });
      registerEvents(this, new Listener[] { new MinePouchHandler() });
      registerEvents(this, new Listener[] { new AuctionHouseHandler() });
      registerEvents(this, new Listener[] { new CMDStats() });
      registerEvents(this, new Listener[] { new CMDRanks() });
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
      registerEvents(this, new Listener[] { new PrestigeHandler() });
      registerEvents(this, new Listener[] { new PickaxeSkillTree() });
      registerEvents(this, new Listener[] { this});
      // For when sale is active, use this || Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "motdchange sale 50");
      // For when sale is active, use this ||
      Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "workmode enable");




      new BukkitRunnable() {

          @Override
          public void run() {
              JDAEvents.getInstance().serverLink();
          }
      }.runTaskTimer(this, 0L, 20*60L);

    
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
              for(Player p : Bukkit.getOnlinePlayers()) {
                 if(RankupHandler.aru.contains(p)){
                     RankupHandler.getInstance().MaxRankup(p);
                 }
                  if (!p.isOnline()) {
                      cancel();
                  }
              }
          }
      }.runTaskTimer(this, 0L, 1L);
 


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
    
    
    new BukkitRunnable() {
        @Override
        public void run() {
            settings.saveGangs();
        }
    }.runTaskTimer(this, 0, 20*150L);
    
  }
  
  
 
  
  public static Main getInstance() {
	  return INSTANCE;
  }
  
  
  private String motd = c("&3&lSeason 3!   &c&lGenesis &b&lPrison!   &e&l[1.8.x-1.19.x]\n                        &c&l>> &a&lJoin Now! &c&l<<");
  private String savemotd = "";
  
  @Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
	  
	  if(label.equalsIgnoreCase("motdchange")) {
          if(!cs.hasPermission("rank.owner")) return false;
		  if(args.length == 2) {
              if(args[0].equalsIgnoreCase("sale")) {

                 motd = c("&3&lSeason 3!   &c&lGenesis &b&lPrison!   &e&l[1.8.x-1.19.x]\n                    &c&l>> &a&l"+args[1]+"% Sale Now! &c&l<<");
              }
          }
	  }
      if(label.equalsIgnoreCase("workmode")) {
          if(!cs.hasPermission("rank.owner")) return false;
          if(args.length == 1){
              if(args[0].equalsIgnoreCase("enable")){                             //&4&lMaintenance Mode
                  savemotd = motd;
                  motd = c("                        &c&lGenesis &b&lPrison!\n                   &4&lMaintenance Mode");
              }
              if(args[0].equalsIgnoreCase("disable")){
                  motd = savemotd;
              }
          }
      }
	  
	  
	  
	  return false;
  }
  
  @EventHandler
  public void motd(ServerListPingEvent e) {
	  e.setMotd(motd);
  }
  

  public void savePickaxe(Player p) {
      ItemStack[] inv = p.getInventory().getContents();

      for(ItemStack i : inv) {
          if(i == null) continue;
          if(i.getType() == null) continue;
          if(i.getType().equals(Material.WOOD_PICKAXE) || i.getType().equals(Material.STONE_PICKAXE) || i.getType().equals(Material.IRON_PICKAXE) || i.getType().equals(Material.GOLD_PICKAXE)
          || i.getType().equals(Material.DIAMOND_PICKAXE)) {
              ItemStack pickaxe = i;
              settings.getPlayerData().set(p.getUniqueId().toString()+".Pickaxe", pickaxe);
          }
      }
  }

  @EventHandler
  public void onJoin(PlayerJoinEvent e) {
	  if(!e.getPlayer().hasPlayedBefore()) {
		  Bukkit.broadcastMessage(c("&dWelcome &f&l"+e.getPlayer().getName()+"&d to &c&lGenesis &b&lPrison!"));
	  }
        savePickaxe(e.getPlayer());
      settings.savePlayerData();
      new BukkitRunnable() {
          @Override
          public void run() {
              savePickaxe(e.getPlayer());
              settings.savePlayerData();
          }
      }.runTaskTimer(this, 0L, 20*300L);



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
      settings.saveGangs();
	  
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
  
  
  public void registerEvents(Plugin plugin, Listener[] listeners) {
    byte b;
    int i;
    Listener[] arrayOfListener;
    for (i = (arrayOfListener = listeners).length, b = 0; b < i; ) {
      Listener listener = arrayOfListener[b];
      Bukkit.getServer().getPluginManager().  registerEvents(listener, plugin);
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
