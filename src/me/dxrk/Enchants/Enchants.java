package me.dxrk.Enchants;

import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.flags.DefaultFlag;
import me.dxrk.Events.PlayerDataHandler;
import me.dxrk.Main.Functions;
import me.dxrk.Main.SettingsManager;
import me.dxrk.Mines.Mine;
import me.dxrk.Mines.MineSystem;
import me.dxrk.Mines.ResetHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Enchants implements Listener {

    static String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    SettingsManager settings = SettingsManager.getInstance();

    static Enchants instance = new Enchants();

    public static Enchants getInstance() {
        return instance;
    }


    public int getLevel(String ss) {
        String s = ChatColor.stripColor(ChatColor.translateAlternateColorCodes('&', ss));
        StringBuilder lvl = new StringBuilder();
        byte b;
        int i;
        char[] arrayOfChar;
        for (i = (arrayOfChar = s.toCharArray()).length, b = 0; b < i; ) {
            char c = arrayOfChar[b];
            if (isInt(c))
                lvl.append(c);
            b++;
        }
        if (isInt(lvl.toString()))
            return Integer.parseInt(lvl.toString());
        return -1;
    }

    public boolean isInt(String s) {
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            int i = Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public List<String> getEnchants(ItemStack i) {
        return i.getItemMeta().getLore();
    }


    public int getFortune(String s) {
        StringBuilder lvl = new StringBuilder();
        s = ChatColor.stripColor(s);
        char[] arrayOfChar = s.toCharArray();
        int i = arrayOfChar.length;
        for (int b = 0; b < i; b = (byte) (b + 1)) {
            char c = arrayOfChar[b];
            if (!this.isInt(c)) continue;
            lvl.append(c);
        }
        if (this.isInt(lvl.toString())) {
            return Integer.parseInt(lvl.toString());
        }
        return -1;
    }

    public void addFortune(Player p, ItemStack ii, int loree) {
        int blockss = this.getFortune(ii.getItemMeta().getLore().get(loree));
        ItemStack i = ii.clone();
        ItemMeta im = i.getItemMeta();
        List<String> lore = im.getLore();
        lore.set(loree, ChatColor.GRAY + "Fortune " + ChatColor.GRAY + (blockss + 1));
        im.setLore(lore);
        i.setItemMeta(im);
        p.setItemInHand(i);
        p.updateInventory();
        int blocks = blockss + 1;
    }


    private boolean hasEnchant(Player p, String enchant) {
        List<String> lore = p.getItemInHand().getItemMeta().getLore();
        int x;
        for (x = 0; x < lore.size(); x++) {
            String s = lore.get(x);
            if (ChatColor.stripColor(s).contains(enchant)) {
                return true;
            }
        }
        return false;
    }
    @SuppressWarnings("deprecation")
    @EventHandler
    public void onLuckyBlockClick(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)){
            Block b = e.getClickedBlock();
            if(b.getType().equals(Material.SEA_LANTERN)){
                Mine m = MineSystem.getInstance().getMineByPlayer(p);
                if (m.isLocationInMine(b.getLocation())){
                    b.setType(Material.AIR);
                    EnchantMethods.getInstance().Luckyblock(p, (byte)1);
                }
            }
        }
    }
    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();

        if (p.getItemInHand() == null)
            return;
        if (!p.getItemInHand().hasItemMeta())
            return;
        if (!p.getItemInHand().getItemMeta().hasLore())
            return;
        if (MineSystem.getInstance().getMineByPlayer(p).isLocationInMine(b.getLocation())) {
            for (String s : EnchantMethods.getInstance().Enchants()) {
                if (hasEnchant(p, s))
                    EnchantMethods.getInstance().procEnchant(s, p, b);
            }
            Functions.Multiply(p);
            Mine m = MineSystem.getInstance().getMineByPlayer(p);
            if (m.getBlocksLeftPercentage() < m.getResetPercent()) {
                double lucky = PlayerDataHandler.getInstance().getPlayerData(p).getDouble("LuckyBlock");
                ResetHandler.resetMineWorldEdit(m, m.getMinPoint(), m.getMaxPoint(), lucky);
            }
        }
    }

}
