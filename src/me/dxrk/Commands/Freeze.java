package me.dxrk.Commands;

import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

public class Freeze implements CommandExecutor, Listener {
  public static ArrayList<String> frozen = new ArrayList<String>();
  
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
    if (label.equalsIgnoreCase("freeze")) {
      if (!(sender instanceof org.bukkit.entity.Player))
        return false; 
      return false;
    } 
    return false;
  }
}
