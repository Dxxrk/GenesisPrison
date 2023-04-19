package me.dxrk.Events;

import me.arcaniax.hdb.api.HeadDatabaseAPI;
import me.dxrk.Main.Methods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MonsterHandler implements Listener {
    private HeadDatabaseAPI api = new HeadDatabaseAPI();
    private Methods m = Methods.getInstance();

    private ItemStack egg() {
        ItemStack egg = new ItemStack(Material.MONSTER_EGG);
        ItemMeta em = egg.getItemMeta();
        em.setDisplayName(m.c("&c&lMonster &7&lEgg"));
        List<String> lore = new ArrayList<>();
        lore.add(m.c("&7Right click to collect a new monster"));
        em.setLore(lore);
        return egg;
    }

    private void giveMonster(Player p) {
        Random r = new Random();
        int ri = r.nextInt(5);
        p.getInventory().addItem(type());
    }
    //Add PlayerInteractEvent to open the eggs.
    //Create Values that can be boosted by pets and add a way to upgrade them.
    //Myabe have special abilities for the monsters on a cooldown? (right click)

    private ItemStack Ladon(String rarity, int bonusmoney) {
        ItemStack ladon = api.getItemHead("44860");
        ItemMeta lm = ladon.getItemMeta();
        lm.setDisplayName(m.c(rarity+" Ladon"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oLadon hailing from the Garden of Hesperides, is the protector of the Golden Apples."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&aBoost: " + bonusmoney + "% Money Boost"));
        lm.setLore(lore);
        ladon.setItemMeta(lm);

        return ladon;
    }

    private ItemStack Typhon(String rarity, int bonus) {
        ItemStack item = api.getItemHead("28179");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c(rarity+" Typhon"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oTyphon, the God of Monsters, is an unstoppable force all Olympians fear."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&aBoost: " + bonus + "% Money Boost"));
        im.setLore(lore);
        item.setItemMeta(im);

        return item;
    }

    private ItemStack Cerberus(String rarity, int bonus) {
        ItemStack item = api.getItemHead("26841");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c(rarity+" Cerberus"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oCerberus, Hades pet, The three headed hell hound and protector of The Underworld."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&aBoost: " + bonus + "% Money Boost"));
        im.setLore(lore);
        item.setItemMeta(im);

        return item;
    }

    private ItemStack Phoenix(String rarity, int bonus) {
        ItemStack item = api.getItemHead("683");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c(rarity+" Phoenix"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oThe Phoenix, bird of the sun, some say it can never die."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&aBoost: " + bonus + "% Money Boost"));
        im.setLore(lore);
        item.setItemMeta(im);

        return item;
    }

    private ItemStack Medusa(String rarity, int bonus) {
        ItemStack item = api.getItemHead("683");
        ItemMeta im = item.getItemMeta();
        im.setDisplayName(m.c(rarity+" Medusa"));
        List<String> lore = new ArrayList<>();
        assert rarity != null;
        lore.add(m.c("&7&oMedusa, a terrifying Gorgon, will petrify anyone who enters her gaze."));
        lore.add(m.c(" "));
        lore.add(m.c("&7Rarity: " + rarity));
        lore.add(m.c("&aBoost: " + bonus + "% Money Boost"));
        im.setLore(lore);
        item.setItemMeta(im);

        return item;
    }


    private String rarity() {
        Random r = new Random();
        int ri = r.nextInt(100);
        if (ri <= 40) {
            return m.c("&bCommon");
        } else if (ri <= 70) {
            return m.c("&9Rare");
        } else if (ri <= 90) {
            return m.c("&5Epic");
        } else {
            return m.c("&cMythical");
        }
    }


    private ItemStack type() {
        Random r = new Random();
        int ri = r.nextInt(100);
        String rarity = rarity();
        if (ri <= 30) {
            return Cerberus(rarity, effect(rarity)); //26841
        } else if (ri <= 50) {
            return Phoenix(rarity, effect(rarity)); //683
        } else if (ri <= 70) {
            return Typhon(rarity, effect(rarity));
        } else if (ri <= 90) {
            return Ladon(rarity, effect(rarity));
        } else {
            return Medusa(rarity, effect(rarity)); //1394
        }
    }
    private int effect(String rarity) {
        Random r = new Random();
        if(rarity.equals(m.c("&bCommon"))) {
            int min = 5;
            int max = 15;
            return r.nextInt(max - min) + min;
        }
        if(rarity.equals(m.c("&9Rare"))) {
            int min = 15;
            int max = 25;
            return r.nextInt(max - min) + min;
        }
        if(rarity.equals(m.c("&5Epic"))) {
            int min = 25;
            int max = 35;
            return r.nextInt(max - min) + min;
        }
        if(rarity.equals(m.c("&cMythical"))) {
            int min = 35;
            int max = 50;
            return r.nextInt(max - min) + min;
        }
        return 0;
    }


}
