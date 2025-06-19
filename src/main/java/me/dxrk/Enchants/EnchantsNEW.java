package me.dxrk.Enchants;


import me.dxrk.Main.Main;
import me.dxrk.Main.Methods;
import me.dxrk.Mines.DynamicMultiBlockPacketSender;
import me.dxrk.Mines.Mine;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.HashMap;
import java.util.Map;

public class EnchantsNEW {

    Methods m = Methods.getInstance();

    private static final EnchantsNEW instance = new EnchantsNEW();

    public static EnchantsNEW getInstance() {
        return instance;
    }

    private Map<Player, Integer> KeyFinder = new HashMap<>();

    private ToolHandler t = ToolHandler.getInstance();

    public static boolean hasEnchant(Tool tool, Enchant enchant) {
        return tool.getEnchants().contains(enchant);
    }

    public int calculateBlocksNeeded(int level) {
        double exactValue = 2000 - ((level - 1) * (2000 - 250) / 19.0);

        return (int) (Math.round(exactValue / 5) * 5);
    }


    //ENCHANT METHODS

    public void giveKey(Player p) {
        p.sendMessage("Imagine getting a key");
    }

    public void keyFinder(Player p, ItemStack hand) {
        Tool tool = t.toolFromItemStack(hand);
        if(!tool.hasEnchant(Enchant.KEY_FINDER)) return;
        int level = tool.getEnchantLevel(Enchant.KEY_FINDER);
        if(!KeyFinder.containsKey(p)) {
            KeyFinder.put(p, calculateBlocksNeeded(level)-1);
        }
        else {
            KeyFinder.put(p, KeyFinder.get(p)-1);
        }
        int blockstil = KeyFinder.get(p);

        if(blockstil <= 0) {
            giveKey(p);
            KeyFinder.put(p, calculateBlocksNeeded(level));
            blockstil = KeyFinder.get(p);
        }


        TextComponent c = Component.text("Blocks until Key: ").color(m.toColor(0x89cff0))
                .append(Component.text(blockstil).color(m.toColor(0x76eec6)));

        p.sendActionBar(c);
    }

    public double calculateChance(int level, int startChance, int endChance, int maxLevel) {
        if (level < 1) {
            return 0;
        }

        if(level == 1) {
            return startChance;
        }

        if (level > 10) {
            return endChance;
        }

        double denominatorDecrease = (startChance - endChance) / (maxLevel-1.0);

        double denominator = startChance - (level - 1) * denominatorDecrease;

        return 1.0 / denominator;
    }

    public boolean eventHappens(int level, int startChance, int endChance) {
        double chance = calculateChance(level, startChance, endChance, 10);
        return Math.random() < chance;
    }

    public void Jackhammer(Player p, Mine m, Location loc, ItemStack hand) {
        Tool tool = t.toolFromItemStack(hand);
        if(!tool.hasEnchant(Enchant.JACKHAMMER)) return;
        int level = tool.getEnchantLevel(Enchant.JACKHAMMER);
        DynamicMultiBlockPacketSender jackhammer = new DynamicMultiBlockPacketSender(Main.plugin);
        Location corner1 = new Location(loc.getWorld(), m.getMinPoint().getX(), loc.getY(), m.getMinPoint().getZ());
        Location corner2 = new Location(loc.getWorld(), m.getMaxPoint().getX(), loc.getY(), m.getMaxPoint().getZ());

        if(eventHappens(level, 2000, 100)) {
            jackhammer.jackhammer(p, corner1, corner2);
        }
    }




}
