package me.dxrk.Events;

import me.dxrk.Main.Methods;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.Listener;

public class PickXPHandler
implements Listener {
	
	public static PickXPHandler instance = new PickXPHandler();
	public static PickXPHandler getInstance() {
		return instance;
	}
	
	
	public Methods m = Methods.getInstance();
	
	
    public static int getBlocks(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor(s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (byte)(b + 1)) {
            char c = arrayOfChar[b];
            if (!isInt(c)) continue;
            lvl.append(c);
        }
        if (isInt(lvl.toString())) {
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }
//test
    public static boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }

    public static boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        }
        catch (Exception e1) {
            return false;
        }
    }



    
    

}

