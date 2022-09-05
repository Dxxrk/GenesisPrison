package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.Listener;

import me.dxrk.Events.MineHandler;
import me.dxrk.Main.Methods;

public class CMDBuycraft implements Listener, CommandExecutor{
	
	
	private Methods m = Methods.getInstance();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(label.equalsIgnoreCase("buymsg")) {
			if(args.length == 3) {
				String name = args[0];
				String price = args[1];
				String pkgName = args[2];
				if(pkgName.equals("Zeus")) {
					pkgName = m.c("&e&l⚡&f&lZeus&e&l⚡");
				}
				
				Bukkit.broadcastMessage(m.c("&9&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
				Bukkit.broadcastMessage(m.c("&f&l"+name+" &bHas Purchased &6"+pkgName+"&b."));
				Bukkit.broadcastMessage(m.c("&ePrice: &e$"+price));
				Bukkit.broadcastMessage(m.c("&9&m-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-"));
				
			}
		}
		return false;
	}

}
