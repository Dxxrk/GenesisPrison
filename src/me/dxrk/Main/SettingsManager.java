package me.dxrk.Main;

import java.io.File;
import java.io.IOException;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;

public class SettingsManager {
  static SettingsManager instance = new SettingsManager();
  
  Plugin p;
  

  
  FileConfiguration blocks;
  
  File bfile;
  
  FileConfiguration tokenshop;
  
  File tsfile;
  
  FileConfiguration data;
  
  File dfile;
  
  FileConfiguration tag;
  
  File tagfile;
  
  FileConfiguration etshop;
  
  File etfile;
  
  FileConfiguration youtube;
  
  File ytfile;
  
  FileConfiguration vote;
  
  File votefile;
  
  FileConfiguration symbols;
  
  File symbolfile;
  
  FileConfiguration color;
  
  File colorfile;
  
  FileConfiguration perks;
  
  File perksfile;
  
  FileConfiguration coinflip;
  
  File coinfile;
  
  FileConfiguration boost;
  
  File boostfile;
  
FileConfiguration PlayerData;
  
  File PDfile;
  
  FileConfiguration RankupPrices;
  
  File RPfile;
  
  FileConfiguration Options;
  
  File OPfile;
  
  FileConfiguration sellPrices;
  
  File SPfile;
  
  FileConfiguration backpacks;
  
  File BPfile;
  
  FileConfiguration crates;
  
  File Cfile;
  
  FileConfiguration deaths;
  
  File DFile;
  
  FileConfiguration ls;
  
  File lsfile;
  
  FileConfiguration multi;
  
  File mfile;
  
  FileConfiguration bpsize;
  
  File bpsizefile;
  
  FileConfiguration daily;
  
  File dailyfile;

  FileConfiguration discord;

  File discordfile;

  FileConfiguration auctionhouse;

  File ahfile;

  FileConfiguration gangs;

  File gangfile;
  
  
  
  public static SettingsManager getInstance() {
    return instance;
  }
  
  public void setup(Plugin p) {
    if (!p.getDataFolder().exists())
      p.getDataFolder().mkdir(); 
    this.dfile = new File(p.getDataFolder(), "data.yml");
    this.tagfile = new File(p.getDataFolder(), "tags.yml");
    this.etfile = new File(p.getDataFolder(), "tokenshop.yml");
    this.tsfile = new File(p.getDataFolder(), "TokenShopPages.yml");
    this.ytfile = new File(p.getDataFolder(), "youtube.yml");
    this.votefile = new File(p.getDataFolder(), "vote.yml");
    this.colorfile = new File(p.getDataFolder(), "color.yml");
    this.perksfile = new File(p.getDataFolder(), "perks.yml");
    this.bfile = new File(p.getDataFolder(), "blocks.yml");
    this.coinfile = new File(p.getDataFolder(), "coinflip.yml");
    this.boostfile = new File(p.getDataFolder(), "boost.yml");
    this.dailyfile = new File(p.getDataFolder(), "daily.yml");
    this.discordfile = new File(p.getDataFolder(), "discord.yml");
    this.ahfile = new File(p.getDataFolder(), "auctionhouse.yml");
    this.gangfile = new File(p.getDataFolder(), "gangs.yml");

    if (!this.gangfile.exists())
      try {
        this.gangfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create gangs.yml");
      }

    if (!this.ahfile.exists())
      try {
        this.ahfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create auctionhouse.yml");
      }

    if (!this.discordfile.exists())
      try {
        this.discordfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create discord.yml");
      }

    if (!this.dailyfile.exists())
        try {
          this.dailyfile.createNewFile();
        } catch (IOException e) {
          Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create daily.yml");
        }
    
    if (!this.bfile.exists())
      try {
        this.bfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create blocks.yml");
      }
    
    if (!this.tsfile.exists())
      try {
        this.tsfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create blocks.yml");
      }  
    if (!this.boostfile.exists())
      try {
        this.boostfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create blocks.yml");
      }  
    
    
    if (!this.coinfile.exists())
      try {
        this.coinfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create blocks.yml");
      }  
    if (!this.votefile.exists())
      try {
        this.votefile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
      }  
    if (!this.perksfile.exists())
      try {
        this.perksfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
      }  
    if (!this.colorfile.exists())
      try {
        this.dfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create color.yml!");
      }  
    if (!this.dfile.exists())
      try {
        this.dfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create data.yml!");
      }  
    if (!this.tagfile.exists())
      try {
        this.tagfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create color.yml!");
      }  
    if (!this.etfile.exists())
      try {
        this.etfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create prisonbrwak.yml!");
      }  
    
    if (!this.ytfile.exists())
      try {
        this.ytfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create prisonbrwak.yml!");
      }
    
    this.PDfile = new File(p.getDataFolder(), "playerdata.yml");
    if (!this.PDfile.exists())
      try {
        this.PDfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create playerdata.yml!");
      }  
    this.PlayerData = YamlConfiguration.loadConfiguration(this.PDfile);
    
    this.BPfile = new File(p.getDataFolder(), "backpacks.yml");
    if (!this.BPfile.exists())
      try {
        this.BPfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create backpacks.yml!");
      }  
    this.backpacks = YamlConfiguration.loadConfiguration(this.BPfile);
    this.Cfile = new File(p.getDataFolder(), "crates.yml");
    if (!this.Cfile.exists())
      try {
        this.Cfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create crates.yml!");
      }  
    this.crates = YamlConfiguration.loadConfiguration(this.Cfile);
    this.SPfile = new File(p.getDataFolder(), "sellprices.yml");
    if (!this.SPfile.exists())
      try {
        this.SPfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create sellprices.yml!");
      }  
    this.sellPrices = YamlConfiguration.loadConfiguration(this.SPfile);
    this.RPfile = new File(p.getDataFolder(), "rankupprices.yml");
    if (!this.RPfile.exists())
      try {
        this.RPfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create rankupprices.yml!");
      }  
    this.RankupPrices = YamlConfiguration.loadConfiguration(this.RPfile);
    this.OPfile = new File(p.getDataFolder(), "options.yml");
    if (!this.OPfile.exists())
      try {
        this.OPfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create options.yml!");
      }  
    this.Options = YamlConfiguration.loadConfiguration(this.OPfile);
    this.DFile = new File(p.getDataFolder(), "deaths.yml");
    if (!this.DFile.exists())
      try {
        this.DFile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create Deaths.yml!");
      }  
    this.deaths = YamlConfiguration.loadConfiguration(this.DFile);
    this.lsfile = new File(p.getDataFolder(), "locksmith.yml");
    if (!this.lsfile.exists())
      try {
        this.lsfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create Locksmith.yml");
      }  
    this.ls = YamlConfiguration.loadConfiguration(this.lsfile);
    
    this.mfile = new File(p.getDataFolder(), "multi.yml");
    if (!this.mfile.exists())
      try {
        this.mfile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create multi.yml");
      }
    this.multi = YamlConfiguration.loadConfiguration(this.mfile);
    
    this.bpsizefile = new File(p.getDataFolder(), "bpsize.yml");
    if (!this.bpsizefile.exists())
      try {
        this.bpsizefile.createNewFile();
      } catch (IOException e) {
        Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not create bpsize.yml");
      }
    this.bpsize = YamlConfiguration.loadConfiguration(this.bpsizefile);
    
    
    this.tag = YamlConfiguration.loadConfiguration(this.tagfile);
    this.perks = YamlConfiguration.loadConfiguration(this.perksfile);
    this.tokenshop = YamlConfiguration.loadConfiguration(this.tsfile);
    this.etshop = YamlConfiguration.loadConfiguration(this.etfile);
    this.youtube = YamlConfiguration.loadConfiguration(this.ytfile);
    this.vote = YamlConfiguration.loadConfiguration(this.votefile);
    this.data = YamlConfiguration.loadConfiguration(this.dfile);
    this.color = YamlConfiguration.loadConfiguration(this.colorfile);
    this.coinflip = YamlConfiguration.loadConfiguration(this.coinfile);
    this.blocks = YamlConfiguration.loadConfiguration(this.bfile);
    this.boost = YamlConfiguration.loadConfiguration(this.boostfile);
    this.daily = YamlConfiguration.loadConfiguration(this.dailyfile);
    this.discord = YamlConfiguration.loadConfiguration(this.discordfile);
    this.auctionhouse = YamlConfiguration.loadConfiguration(this.ahfile);
    this.gangs = YamlConfiguration.loadConfiguration(this.gangfile);
  }

  public FileConfiguration getGangs() {
    return this.gangs;
  }
  public FileConfiguration getAH() {
    return this.auctionhouse;
  }
  public FileConfiguration getDiscord() {
      return this.discord;
  }
  
  public FileConfiguration getDaily() {
	  return this.daily;
  }
  
  
  public FileConfiguration getbpSize() {
	  return this.bpsize;
  }
  
  public FileConfiguration getPlayerData() {
    return this.PlayerData;
  }
  
 
  
  public FileConfiguration getBackPacks() {
    return this.backpacks;
  }
  
  public FileConfiguration getSellPrices() {
    return this.sellPrices;
  }
  
  public FileConfiguration getRankupPrices() {
    return this.RankupPrices;
  }
  
  public FileConfiguration getOptions() {
    return this.Options;
  }
  
  public FileConfiguration getCrates() {
    return this.crates;
  }
  
  public FileConfiguration getDeaths() {
    return this.deaths;
  }
  
  public FileConfiguration getLocksmith() {
    return this.ls;
  }
  
  
  public FileConfiguration getMultiplier() {
	return this.multi;	
  }
  
  public FileConfiguration getVote() {
    return this.vote;
  }
  
  
  
  public FileConfiguration getTokenShop() {
    return this.tokenshop;
  }
  
  
  
  public FileConfiguration getPerks() {
    return this.perks;
  }
  
  public FileConfiguration getBlocks() {
    return this.blocks;
  }
  
  public FileConfiguration getData() {
    return this.data;
  }
  
  public FileConfiguration getCoinFlip() {
    return this.coinflip;
  }
  
  public FileConfiguration getcolor() {
    return this.color;
  }
  
  public FileConfiguration getTags() {
    return this.tag;
  }
  
  public FileConfiguration getBoost() {
    return this.boost;
  }
  
  public FileConfiguration getET() {
    return this.etshop;
  }
  
  
  public FileConfiguration getYT() {
    return this.youtube;
  }


  public void saveGangs() {
    try {
      this.gangs.save(this.gangfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save gangs.yml!");
    }
  }

  public void saveAH() {
    try {
      this.auctionhouse.save(this.ahfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save auctionhouse.yml!");
    }
  }

  public void saveDiscord() {
    try {
      this.discord.save(this.discordfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save discord.yml!");
    }
  }
  public void saveDaily() {
	  try {
	      this.daily.save(this.dailyfile);
	    } catch (IOException e) {
	      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save daily.yml!");
	    } 
  }
  
  
  public void saveEnchants() {
	  try {
	      this.vote.save(this.votefile);
	    } catch (IOException e) {
	      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
	    } 
  }
  
  public void saveVote() {
    try {
      this.vote.save(this.votefile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
    } 
  }
  
  public void saveTokenShop() {
    try {
      this.tokenshop.save(this.tsfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
    } 
  }
  
  
  
  public void saveboosts() {
    try {
      this.boost.save(this.boostfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
    } 
  }
  
  public void saveBlocks() {
    try {
      this.blocks.save(this.bfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save blocks.yml!");
    } 
  }
  
  
  
  
  
  public void savePerks() {
    try {
      this.perks.save(this.perksfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
    } 
  }
  
  public void saveCoinFlip() {
    try {
      this.coinflip.save(this.coinfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
    } 
  }
  
  public void saveData() {
    try {
      this.data.save(this.dfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save data.yml!");
    } 
  }
  
  public void savecolorFile() {
    try {
      this.color.save(this.colorfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save color.yml!");
    } 
  }
  
  public void savetagfile() {
    try {
      this.tag.save(this.tagfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save color.yml!");
    } 
  }
  
  public void saveEtFile() {
    try {
      this.etshop.save(this.etfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save color.yml!");
    } 
  }
  
  
  
  public void saveYT() {
    try {
      this.youtube.save(this.ytfile);
    } catch (IOException e) {
      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save color.yml!");
    } 
  }
  
  public void savebpSize() {
	    try {
	      this.bpsize.save(this.bpsizefile);
	    } catch (IOException e) {
	      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save bpsize.yml!");
	    } 
	  }

public void savePlayerData() {
  try {
    this.PlayerData.save(this.PDfile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save playerdata.yml!");
  } 
}



public void saveCrates() {
  try {
    this.crates.save(this.Cfile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save crates.yml!");
  } 
}

public void saveBackPacks() {
  try {
    this.backpacks.save(this.BPfile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save backpacks.yml!");
  } 
}

public void saveRankupPrices() {
  try {
    this.RankupPrices.save(this.RPfile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save rankupprices.yml!");
  } 
}

public void saveSellPrices() {
  try {
    this.sellPrices.save(this.SPfile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save sellprices.yml!");
  } 
}

public void saveOptions() {
  try {
    this.Options.save(this.OPfile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save options.yml!");
  } 
}

public void SaveDeaths() {
  try {
    this.deaths.save(this.DFile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save deaths.yml!");
  } 
}

public void saveLocksmith() {
  try {
    this.ls.save(this.lsfile);
  } catch (IOException e) {
    Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save locksmith.yml!");
  } 
}


public void saveMultiplier() {
	    try {
	      this.multi.save(this.mfile);
	    } catch (IOException e) {
	      Bukkit.getServer().getLogger().severe(ChatColor.RED + "Could not save multi.yml!");
	    } 
	  }
  

  
  public PluginDescriptionFile getDesc() {
    return this.p.getDescription();
  }
}
