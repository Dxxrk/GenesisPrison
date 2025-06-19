package me.dxrk.Enchants;

import me.dxrk.Main.Main;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tool {

    public static final Tool defaultAxe = new Tool(1, 0, "DefaultAxe", Component.text("Logging Axe").color(TextColor.color(0x838b8b))
            .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false), ToolType.AXE, "Server", "None", me.dxrk.Enchants.Enchant.EFFICIENCY);
    public static final Tool defaultMiningPickaxe = new Tool(1, 0, "DefaultMining", Component.text("Prison Pickaxe").color(TextColor.color(0x838b8b))
            .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false), ToolType.PICKAXE, "Server", "None", me.dxrk.Enchants.Enchant.KEY_FINDER);
    public static final Tool defaultCavingPickaxe = new Tool(1, 0, "DefaultCaving", Component.text("Caving Pickaxe").color(TextColor.color(0x838b8b))
            .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false), ToolType.PICKAXE, "Server", "None", Enchant.EFFICIENCY);
    public static final Tool defaultFishingRod = new Tool(1, 0, "DefaultFishing", Component.text("Fishing Rod").color(TextColor.color(0x838b8b))
            .decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false), ToolType.PICKAXE, "Server", "None", Enchant.MAGNETIC);

    private Map<Enchant, Integer> enchants;
    private int level;
    private float xp;
    private String name;
    private Component itemName;
    private ToolType type;
    private String owner;
    private String ability;
    private String id;

    public Tool(int level, float xp, String name, Component itemName, ToolType type, String owner, String ability, Enchant... enchants) {
        this.enchants = new HashMap<>();
        for (Enchant enchant : enchants) {
            this.enchants.put(enchant, 1);
        }
        this.level = level;
        this.xp = xp;
        this.name = name;
        this.itemName = itemName;
        this.type = type;
        this.owner = owner;
        this.ability = ability;
        this.id = name+"."+owner;
    }

    public List<Enchant> getEnchants() {
        return new ArrayList<>(this.enchants.keySet());
    }

    public int getEnchantLevel(Enchant enchant) {
        return this.enchants.getOrDefault(enchant, 0);
    }

    public boolean hasEnchant(Enchant enchant) {
        return this.enchants.containsKey(enchant);
    }

    public int getLevel() {return this.level;}
    public float getXP() {return this.xp;}
    public String getName() {return this.name;}
    public Component getItemName() {return this.itemName;}
    public ToolType getType() {return this.type;}
    public String getOwner() {return this.owner;}
    public String getAbility() {return this.ability;}
    public String getId() {return this.id;}

    public void addEnchant(Enchant enchant) {
        if(this.enchants.containsKey(enchant)) return;

        this.enchants.put(enchant, 1);
    }

    public void upgradeEnchant(Enchant enchant) {
        if (!this.enchants.containsKey(enchant)) {
            return;
        }

        int currentLevel = this.enchants.get(enchant);
        if (currentLevel >= enchant.getMax()) {
            return;
        }

        this.enchants.put(enchant, currentLevel + 1);
    }

    public void setEnchantLevel(Enchant enchant, int level) {
        if (level <= 0) {
            this.enchants.remove(enchant);
            return;
        }

        if (level > enchant.getMax()) {
            return;
        }

        this.enchants.put(enchant, level);
    }
    public void resetXP() {
        this.xp = 0;
    }

    public void addXP(int xp) {
        this.xp += xp;
    }
    public void levelUp() {
        ++this.level;
    }

    private void createFolder() {
        File var1 = new File(Main.plugin.getDataFolder() + File.separator + "tools");
        if (!var1.exists()) {
            var1.mkdir();
        }
    }
    private void createFolder(String owner) {
        File var1 = new File(Main.plugin.getDataFolder() + File.separator + "tools" + File.separator + owner);
        if (!var1.exists()) {
            var1.mkdir();
        }
    }

    public void save() {
        createFolder();
        createFolder(this.getOwner());
        try {
            File toolfile = new File(Main.plugin.getDataFolder() + File.separator + "tools" + File.separator + this.getOwner(), this.getName() + ".yml");
            if (!toolfile.exists()) {
                toolfile.createNewFile();
            }
            FileConfiguration config = YamlConfiguration.loadConfiguration(toolfile);

            config.set("owner", this.getOwner());
            config.set("name", this.getName());
            config.set("itemName", this.getItemName().toString());
            config.set("ability", this.getAbility());
            List<String> enchantData = new ArrayList<>();
            for (Map.Entry<Enchant, Integer> entry : this.enchants.entrySet()) {
                enchantData.add(entry.getKey().getName() + ":" + entry.getValue());
            }
            config.set("enchants", enchantData);

            config.set("level", this.getLevel());
            config.set("xp", this.getXP());
            config.set("type", this.getType().toString());

            config.save(toolfile);
        } catch (Exception e) {
            System.out.println("ERROR SAVING TOOL: " + getName());
            System.out.println(e.getMessage());
        }
    }



    public enum ToolType {
        PICKAXE,AXE,SWORD,CAVING_PICKAXE,FISHING_ROD
    }
}