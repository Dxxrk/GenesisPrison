package me.dxrk.Events;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class FamiliarHandler implements Listener {

    private ItemStack egg() {
        ItemStack egg = new ItemStack(Material.MONSTER_EGG);


        return egg;
    }

    private int rarity() {
        Random r = new Random();
        return r.nextInt(4);
    }


    private String type() {
        Random r = new Random();
        int ri = r.nextInt(100);
        if(ri <=40) {
            return "";
        }
        else if(ri <=60) {
            return "";
        }
        else if(ri <=80) {
            return "";
        }
        else {
            return "";
        }
    }





}
