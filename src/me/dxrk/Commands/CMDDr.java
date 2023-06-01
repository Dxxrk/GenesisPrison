package me.dxrk.Commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CMDDr implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        Player p = (Player) sender;
        if (label.equalsIgnoreCase("dr") &&
                args.length == 1 &&
                sender instanceof Player) {
            Player win = Bukkit.getPlayerExact(args[0]);
            if (p.getItemInHand() == null)
                return false;
            ItemStack i = p.getItemInHand();
            Bukkit.broadcastMessage("�7�l�m---------------<�b�lEpsilon�d�lDP�7�l�m>---------------");
            if (i.hasItemMeta() && i.getItemMeta().hasDisplayName()) {
                Bukkit.broadcastMessage("�bComing up: �7" + i.getItemMeta().getDisplayName());
            } else {
                Bukkit.broadcastMessage("�bComing up: �7" + i.getType().toString().toLowerCase());
            }
            Bukkit.broadcastMessage("�bGoes to�7: �c" + args[0]);
            Bukkit.broadcastMessage("�7�l�m---------------<�b�lEpsilon�d�lDP�7�l�m>---------------");
            win.getInventory().addItem(new ItemStack[]{i});
            win.updateInventory();
            return true;
        }
        return false;
    }
}
