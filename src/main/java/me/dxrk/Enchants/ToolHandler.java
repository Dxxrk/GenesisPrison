package me.dxrk.Enchants;

import io.papermc.paper.event.player.PlayerArmSwingEvent;
import me.dxrk.Main.Main;
import me.dxrk.Main.RomanNumber;
import me.dxrk.Mines.PacketInterceptor;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.core.Holder;
import net.minecraft.network.protocol.game.ClientboundGameEventPacket;
import net.minecraft.network.protocol.game.ClientboundUpdateAttributesPacket;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import me.dxrk.Enchants.Tool.ToolType;
import org.bukkit.persistence.PersistentDataType;

import java.io.File;
import java.util.*;


public class ToolHandler implements Listener {

    public static ToolHandler instance = new ToolHandler();

    public static ToolHandler getInstance() {
        return instance;
    }

    public Enchant findEnchantByName(String name) {
        return Enchant.getByName(name);
    }

    public static List<Tool> loadedTools = new ArrayList<>();

    public static boolean isTool(ItemStack i) {
        return i.getType() == Material.WOODEN_PICKAXE || i.getType() == Material.STONE_PICKAXE || i.getType() == Material.GOLDEN_PICKAXE || i.getType() == Material.IRON_PICKAXE || i.getType() == Material.DIAMOND_PICKAXE || i.getType() == Material.NETHERITE_PICKAXE
                || i.getType() == Material.WOODEN_AXE || i.getType() == Material.STONE_AXE || i.getType() == Material.GOLDEN_AXE || i.getType() == Material.IRON_AXE || i.getType() == Material.DIAMOND_AXE || i.getType() == Material.NETHERITE_AXE;
    }


    private List<Material> toolTypes() {
        List<Material> list = new ArrayList<>();
        list.add(Material.WOODEN_AXE);
        list.add(Material.WOODEN_PICKAXE);
        list.add(Material.WOODEN_AXE);
        list.add(Material.FISHING_ROD);
        list.add(Material.STONE_PICKAXE);
        return list;
    }

    public void changeTools(Player p, Tool tool) {
        for(int i = 0; i < p.getInventory().getSize(); i++) {
            ItemStack item = p.getInventory().getItem(i);
            if(toolTypes().contains(item.getType())) {
                
                p.getInventory().setItem(i, createToolItem(tool));
            }
        }
    }



    public Tool load(String name, String uuid) {
        try {
            File toolfile = new File(Main.plugin.getDataFolder() + File.separator + "tools" + File.separator + uuid, name + ".yml");
            if (!toolfile.exists()) {
                return null;
            }

            FileConfiguration config = YamlConfiguration.loadConfiguration(toolfile);

            String owner = config.getString("owner");
            String toolName = config.getString("name");
            Component itemName = Component.text(config.getString("itemName"));
            String ability = config.getString("ability");
            int level = config.getInt("level");
            float xp = (float) config.getDouble("xp");
            ToolType type = ToolType.valueOf(config.getString("type"));

            Tool tool = new Tool(level, xp, toolName, itemName, type, owner, ability);

            // Load enchants and their levels
            List<String> enchantData = config.getStringList("enchants");
            for (String data : enchantData) {
                String[] parts = data.split(":");
                if (parts.length == 2) {
                    String enchantName = parts[0];
                    int enchantLevel = Integer.parseInt(parts[1]);

                    Enchant enchant = findEnchantByName(enchantName);
                    if (enchant != null) {
                        tool.setEnchantLevel(enchant, enchantLevel);
                    }
                }
            }

            return tool;
        } catch (Exception e) {
            System.out.println("ERROR LOADING TOOL: " + name);
            System.out.println(e.getMessage());
            return null;
        }
    }


    public Material convertToolTypetoMaterial(ToolType mat) {
        return switch (mat) {
            case SWORD -> Material.WOODEN_SWORD;
            case PICKAXE -> Material.DIAMOND_PICKAXE;
            case CAVING_PICKAXE -> Material.STONE_PICKAXE;
            case AXE -> Material.WOODEN_AXE;
            case FISHING_ROD -> Material.FISHING_ROD;
        };
    }

    public Component getLoreFromEnchant(Enchant e) {
        return switch (e.getRarity()) {
            case COMMON -> Component.text(" ⎜ "+ e.getName()+" " + formatEnchant(e.getLevel())).color(TextColor.color(0xf6f6f6)).decoration(TextDecoration.ITALIC, false);
            case RARE -> Component.text(" ⎜ "+ e.getName()+" " + formatEnchant(e.getLevel())).color(TextColor.color(0xabcaff)).decoration(TextDecoration.ITALIC, false);
            case EPIC -> Component.text(" ⎜ "+ e.getName()+" " + formatEnchant(e.getLevel())).color(TextColor.color(0xa63fff)).decoration(TextDecoration.ITALIC, false);
            case LEGENDARY -> Component.text(" ⎜ "+ e.getName()+" " + formatEnchant(e.getLevel())).color(TextColor.color(0xff8330)).decoration(TextDecoration.ITALIC, false);
        };
    }

    public Tool createTool(Tool tool, String uuid) {
        Tool toolCopy = new Tool(tool.getLevel(), tool.getXP(), tool.getName(),
                tool.getItemName(), tool.getType(), uuid, tool.getAbility(), tool.getEnchants().toArray(new Enchant[0]));
        toolCopy.save();

        return toolCopy;
    }

    private String formatEnchant(int i) {
        String roman = RomanNumber.toRoman(i);
        return roman;
    }

    public ItemStack createToolItem(Tool tool) {
        ItemStack item = new ItemStack(convertToolTypetoMaterial(tool.getType()));
        ItemMeta im = item.getItemMeta();
        im.displayName(tool.getItemName());
        List<Component> lore = new ArrayList<>();
        lore.add(Component.text("Owner: ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(Bukkit.getPlayer(UUID.fromString(tool.getOwner())).getName()).color(NamedTextColor.YELLOW)).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(" "));
        lore.add(Component.text("Info:").color(TextColor.color(0xfffdd0)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false));
        lore.add(Component.text(" ⎜ Ability: ").color(TextColor.color(0x8a7f80)).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(tool.getAbility()).color(TextColor.color(0x8ee5ee)).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text(" ⎜ Level: ").color(TextColor.color(0x8a7f80)).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(tool.getLevel()).color(TextColor.color(0x8ee5ee)).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text(" ⎜ EXP: ").color(TextColor.color(0x8a7f80)).decoration(TextDecoration.ITALIC, false)
                .append(Component.text(Math.round(tool.getXP())).color(TextColor.color(0x1bd51b)).decoration(TextDecoration.ITALIC, false))
                .append(Component.text(" / ").color(NamedTextColor.GRAY).decoration(TextDecoration.ITALIC, false))
                .append(Component.text("maxNumber").color(TextColor.color(0xdc143c)).decoration(TextDecoration.ITALIC, false)));
        lore.add(Component.text(" "));
        lore.add(Component.text("Enchants:").color(TextColor.color(0xfffdd0)).decoration(TextDecoration.BOLD, true).decoration(TextDecoration.ITALIC, false));
        for(Enchant e : tool.getEnchants()) {
            lore.add(getLoreFromEnchant(e));
        }
        im.lore(lore);
        im.addEnchant(Enchantment.EFFICIENCY, 10000, true);
        im.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        im.setUnbreakable(true);
        NamespacedKey key = new NamespacedKey(Main.plugin, "tool-id");
        im.getPersistentDataContainer().set(key, PersistentDataType.STRING, tool.getId());
        item.setItemMeta(im);
        return item;
    }


}
