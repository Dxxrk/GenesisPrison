package me.dxrk.Enchants;

import java.util.HashMap;
import java.util.Map;

public class Enchant {
    private static final Map<String, Enchant> ENCHANTS_BY_NAME = new HashMap<>();

    //ALL
    public static final Enchant KEY_FINDER = registerEnchant(new Enchant("Key Finder", Rarity.COMMON, 20, EnchantType.ALL));
    public static final Enchant EFFICIENCY = registerEnchant(new Enchant("Efficiency", Rarity.COMMON, 20, EnchantType.ALLBUTMINING));


    //MINING
    public static final Enchant LASER = registerEnchant(new Enchant("Laser", Rarity.LEGENDARY, 5, EnchantType.MINING));
    public static final Enchant DOUBLE_TAP = registerEnchant(new Enchant("Encore", Rarity.EPIC, 10, EnchantType.MINING));
    public static final Enchant TREASURE_HUNTER = registerEnchant(new Enchant("Treasure Hunter", Rarity.EPIC, 5, EnchantType.MINING));
    public static final Enchant JACKHAMMER = registerEnchant(new Enchant("Jackhammer", Rarity.RARE, 10, EnchantType.MINING));
    public static final Enchant FORTUNE = registerEnchant(new Enchant("Fortune", Rarity.LEGENDARY, 5, EnchantType.MINING));

    //LOGGING
    public static final Enchant TREECAPITATOR = registerEnchant(new Enchant("Treecapitator", Rarity.EPIC, 10, EnchantType.LOGGING));

    //CAVING
    public static final Enchant INFUSE = registerEnchant(new Enchant("Infuse", Rarity.RARE, 10, EnchantType.CAVING));
    public static final Enchant MOMENTUM = registerEnchant(new Enchant("Momentum", Rarity.EPIC, 5, EnchantType.CAVING));

    //FISHING
    public static final Enchant MAGNETIC = registerEnchant(new Enchant("Magnetic", Rarity.COMMON, 20, EnchantType.FISHING));

    private String name;
    private Rarity rarity;
    private int max_level;
    private EnchantType type;
    private int level = 1;

    private static Enchant registerEnchant(Enchant enchant) {
        ENCHANTS_BY_NAME.put(enchant.getName(), enchant);
        return enchant;
    }

    public static Enchant getByName(String name) {
        return ENCHANTS_BY_NAME.get(name);
    }

    public Rarity getRarity() {
        return rarity;
    }
    public int getMax() {
        return max_level;
    }
    public String getName() {
        return name;
    }
    public EnchantType getType() {
        return type;
    }

    public int getLevel() {
        return level;
    }


    public void addLevel(int add) {
        if (add > 0) {
            level = Math.min(level + add, max_level);
        }
    }

    public Enchant(String name, Rarity rarity, int max_level, EnchantType type) {
        this.name = name;
        this.rarity = rarity;
        this.max_level = max_level;
        this.type = type;
    }

    public enum Rarity {
        COMMON,RARE,EPIC,LEGENDARY
    }
    public enum EnchantType {
        MINING,CAVING,LOGGING,FISHING,FIGHTING,ALL,ALLBUTMINING
    }
}