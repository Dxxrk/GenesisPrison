package me.dxrk.Events;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.dxrk.Main.SettingsManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;

public class BlocksHandler implements CommandExecutor, Listener {
  public static HashMap<Player, Integer> blocks = new HashMap<>();

  SettingsManager settings = SettingsManager.getInstance();
  
  static BlocksHandler instance = new BlocksHandler();
  
  public static BlocksHandler getInstance() {
    return instance;
  }
  
  
  

  
  
  
 
  
  
  
  public void onEnd() {
    for (Player p : Bukkit.getOnlinePlayers()) {
      if (blocks.containsKey(p)) {
        PlayerDataHandler.getPlayerData(p).set("BlocksBroken", blocks.get(p));
        PlayerDataHandler.savePlayerData(p);
        blocks.remove(p);
      } 
    } 
  }
  public void onEndLB() {
	    for (Player p : Bukkit.getOnlinePlayers()) {
	      if (blocks.containsKey(p)) {
	        PlayerDataHandler.getPlayerData(p).set("BlocksBroken", blocks.get(p));
	        PlayerDataHandler.savePlayerData(p);
	      } 
	    } 
	  }
  

  
  
  @SuppressWarnings("deprecation")
@EventHandler
  public void onBreak(BlockBreakEvent e) {
    Player p = e.getPlayer();
    if (p.getItemInHand() == null)
      return; 
    if (e.isCancelled())
      return;
    WorldGuardPlugin wg = (WorldGuardPlugin)Bukkit.getServer().getPluginManager().getPlugin("WorldGuard");
    ApplicableRegionSet set = wg.getRegionManager(p.getWorld()).getApplicableRegions(e.getBlock().getLocation());
    LocalPlayer player = wg.wrapPlayer(p);
    if(!set.isMemberOfAll(player)) {
      return;
    }
    if (!set.allows(DefaultFlag.LIGHTER))
      return;
    int blocksbroken = PlayerDataHandler.getPlayerData(p).getInt("BlocksBroken");
    PlayerDataHandler.getPlayerData(p).set("BlocksBroken", blocksbroken+1);
  }
  
  @EventHandler
  public void onLeave(PlayerQuitEvent e) {
    Player p = e.getPlayer();
      blocks.remove(p);
      this.settings.saveBlocks();
      PlayerDataHandler.savePlayerData(p);

  }
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("blocks")) {
      if (!(sender instanceof Player))
        return false; 
      Player p = (Player)sender;
      int blocksbroken = PlayerDataHandler.getPlayerData(p).getInt("BlocksBroken");
      p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b[&dBlocks&b] &bYou've broken &d&n" + blocksbroken + "&b blocks!"));
    } 
    return false;
  }
  
  
}
