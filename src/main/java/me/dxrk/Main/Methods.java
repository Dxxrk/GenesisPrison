package me.dxrk.Main;

import com.fastasyncworldedit.core.FaweAPI;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.World;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.md_5.bungee.api.ChatColor;
import net.minecraft.network.protocol.game.ClientboundInitializeBorderPacket;
import net.minecraft.world.level.border.WorldBorder;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.craftbukkit.CraftWorld;
import org.bukkit.craftbukkit.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.UUID;

import static net.kyori.adventure.text.Component.text;
import static net.kyori.adventure.text.format.TextColor.color;


public class Methods {
    public static Methods instance = new Methods();

    public static SettingsManager settings = SettingsManager.getInstance();

    public static Methods getInstance() {
        return instance;
    }

    public String c(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }



    //Use with specific HEX codes needed.
    public TextComponent colorText(String s, TextColor color, TextDecoration style, boolean on) { //for use with net.kyori.adventure.text.format.TextColor.color /// USAGE: color(0x<HEX CODE>)
        final TextComponent component = text()
                .content(s).color(color)
                .decoration(style, on)
                .build();
        return component;
    }
    public TextComponent colorText(String s, NamedTextColor color, TextDecoration style, boolean on) {
        final TextComponent component = text()
                .content(s).color(color)
                .decoration(style, on)
                .build();
        return component;
    }

    //Use with simpler default colors provided for quick usage.
    public TextComponent colorText(String text, String color, TextDecoration style, boolean on) {
        TextColor var = switch (color) {
            case "blue" -> color(0x0096FF);
            case "red" -> color(0xD2042D);
            case "green" -> color(0x32CD32);
            case "purple" -> color(0x7F00FF);
            case "yellow" -> color(0xE4D00A);
            case "gold" -> color(0xFFD700);
            case "gray" -> color(0x808080);
            default -> null;
        };

        final TextComponent component = text()
                .content(text).color(var)
                .decoration(style, on)
                .build();
        return component;
    }

    public String stripColor(Component c) {
        return c.toString();
    }

    public void paste(Location location, File file) {

        ClipboardFormat clipboardFormat = ClipboardFormats.findByFile(file);
        Clipboard clipboard;

        BlockVector3 blockVector3 = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (clipboardFormat != null) {
            try (ClipboardReader clipboardReader = clipboardFormat.getReader(new FileInputStream(file))) {

                if (location.getWorld() == null)
                    throw new NullPointerException("Failed to paste schematic due to world being null");

                World world = BukkitAdapter.adapt(location.getWorld());

                EditSession editSession = WorldEdit.getInstance().newEditSessionBuilder().world(world).build();

                clipboard = clipboardReader.read();

                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(editSession)
                        .to(blockVector3)
                        .ignoreAirBlocks(true)
                        .build();

                try {
                    Operations.complete(operation);
                    editSession.close();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void pasteSchem(Location loc, File file) {
        World weWorld = FaweAPI.getWorld(loc.getWorld().getName());

        if (file.exists() == false) {
            return;
        }
        boolean allowUndo = false;
        BlockVector3 vec = BlockVector3.at(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        try {
            EditSession editSession = ClipboardFormats.findByFile(file)
                    .load(file)
                    .paste(weWorld, vec, allowUndo, false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pasteSchematic(File file, Location location) {
        Bukkit.getScheduler().runTaskAsynchronously(Main.plugin, () -> {
            try (Clipboard clipboard = ClipboardFormats.findByFile(file).getReader(new FileInputStream(file)).read()) {
                if (clipboard == null)
                    throw new IllegalStateException("Schematic does not have a Clipboard! This should never happen!");
                EditSession session = WorldEdit.getInstance().newEditSessionBuilder().world(FaweAPI.getWorld(location.getWorld().getName())).fastMode(true).build();
                BlockVector3 blockVector3 = BlockVector3.at(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                //session.paste((World) session, centerVector, false, false, null);
                Operation operation = new ClipboardHolder(clipboard)
                        .createPaste(session)
                        .to(blockVector3)
                        .ignoreAirBlocks(true)
                        .build();
                session.flushQueue();
                try {
                    Operations.complete(operation);
                    session.close();
                } catch (WorldEditException e) {
                    e.printStackTrace();
                }
                Region region = clipboard.getRegion();
                region.setWorld(FaweAPI.getWorld(location.getWorld().getName()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    /**
     * Schedules a task to run at a certain hour every day.
     *
     * @param plugin The plugin associated with this task
     * @param task   The task to run
     * @param hour   [0-23] The hour of the day to run the task
     * @return Task id number (-1 if scheduling failed)
     */
    public static int schedule(Plugin plugin, Runnable task, int hour) {
        //Calendar is a class that represents a certain time and date.
        Calendar cal = Calendar.getInstance(); //obtains a calendar instance that represents the current time and date

        //time is often represented in milliseconds since the epoch,
        //as a long, which represents how many milliseconds a time is after
        //January 1st, 1970, 00:00.

        //this gets the current time
        long now = cal.getTimeInMillis();
        //you could also say "long now = System.currentTimeMillis()"

        //since we have saved the current time, we need to figure out
        //how many milliseconds are between that and the next
        //time it is 7:00pm, or whatever was passed into hour
        //we do this by setting this calendar instance to the next 7:00pm (or whatever)
        //then we can compare the times

        //if it is already after 7:00pm,
        //we will schedule it for tomorrow,
        //since we can't schedule it for the past.
        //we are not time travelers.
        if (cal.get(Calendar.HOUR_OF_DAY) >= hour)
            cal.add(Calendar.DATE, 1); //do it tomorrow if now is after "hours"

        //we need to set this calendar instance to 7:00pm, or whatever.
        cal.set(Calendar.HOUR_OF_DAY, hour);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        //cal is now properly set to the next time it will be 7:00pm

        long offset = cal.getTimeInMillis() - now;
        long ticks = offset / 50L; //there are 50 milliseconds in a tick

        //we now know how many ticks are between now and the next time it is 7:00pm
        //we schedule an event to go off the next time it is 7:00pm,
        //and repeat every 24 hours.
        return Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, task, ticks, 1728000L);
        //24 hrs/day * 60 mins/hr * 60 secs/min * 20 ticks/sec = 1728000 ticks
    }


    public void createWorldBorder(Player p, org.bukkit.World world, double size, double x, double z) {
        WorldBorder wb = new WorldBorder();
        wb.world = ((CraftWorld) world).getHandle();
        wb.setCenter(x, z);
        wb.setSize(size);
        wb.setWarningBlocks(1);
        wb.setWarningTime(1);
        //PacketPlayOutWorldBorder packetPlayOutWorldBorder = new PacketPlayOutWorldBorder(wb, PacketPlayOutWorldBorder.EnumWorldBorderAction.INITIALIZE);
        ClientboundInitializeBorderPacket packet = new ClientboundInitializeBorderPacket(wb);
        ((CraftPlayer) p).getHandle().connection.send(packet);
    }

    /*public ItemStack getSkull(String url) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        if(url.isEmpty())return item;


        SkullMeta itemMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "test");
        byte[] encodedData = Base64.getEncoder().encode(String.format("{textures:{SKIN:{url:\"%s\"}}}", url).getBytes());
        profile.getProperties().put("textures", new Property("textures", new String(encodedData)));
        Field profileField = null;
        try
        {
            profileField = itemMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(itemMeta, profile);
        }
        catch (NoSuchFieldException | IllegalArgumentException | IllegalAccessException e)
        {
            e.printStackTrace();
        }
        item.setItemMeta(itemMeta);
        return item;
    }*/
    private static final UUID RANDOM_UUID = UUID.fromString("92864445-51c5-4c3b-9039-517c9927d1b4"); // We reuse the same "random" UUID all the time

    public ItemStack getSkull(String url) {
        PlayerProfile profile = Bukkit.createPlayerProfile(RANDOM_UUID); // Get a new player profile
        PlayerTextures textures = profile.getTextures();
        URL urlObject;
        try {
            urlObject = new URL(url); // The URL to the skin, for example: https://textures.minecraft.net/texture/18813764b2abc94ec3c3bc67b9147c21be850cdf996679703157f4555997ea63a
        } catch (MalformedURLException exception) {
            throw new RuntimeException("Invalid URL", exception);
        }
        textures.setSkin(urlObject); // Set the skin of the player profile to the URL
        profile.setTextures(textures); // Set the textures back to the profile

        //System.out.println(profile.getTextures().getSkin().toString());
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        meta.setOwnerProfile(profile); // Set the owning player of the head to the player profile
        head.setItemMeta(meta);
        return head;
    }


    public int getBlocks(String s) {
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

    public boolean isInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public boolean isInt(char ss) {
        String s = String.valueOf(ss);
        try {
            Integer.parseInt(s);
            return true;
        } catch (Exception e1) {
            return false;
        }
    }

    public OfflinePlayer getPlayer(String name) {
        File[] mineFiles = (new File(Main.plugin.getDataFolder() + File.separator + "playerdata")).listFiles();
        File[] var = mineFiles;
        assert mineFiles != null;
        int amountOfMines = mineFiles.length;
        for (int i = 0; i < amountOfMines; ++i) {
            File mineFile = var[i];
            String s = mineFile.getName().split("\\.")[0];
            UUID id = UUID.fromString(s);
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(s));
            if (player.getName().equals(name)) {
                return player;
            }
        }
        return null;
    }


    public boolean isInteger(String s, int radix) {
        if (s.isEmpty())
            return false;
        for (int i = 0; i < s.length(); i++) {
            if (i == 0 && s.charAt(i) == '-') {
                if (s.length() == 1)
                    return false;
            } else if (Character.digit(s.charAt(i), radix) < 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isInteger(String s) {
        return isInteger(s, 10);
    }


    @SuppressWarnings("deprecation")
    public static double getSellPrice(int rank) {
        double block = rank / 1.6;
        if (block < 1)
            block = 1;
        return block * 500000;
    }
}
