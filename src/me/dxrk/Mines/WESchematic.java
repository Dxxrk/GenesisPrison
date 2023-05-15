package me.dxrk.Mines;

import com.boydti.fawe.object.schematic.Schematic;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class WESchematic {


    public static Schematic getSchematic(File file) {
        if (!file.exists())
            throw new IllegalStateException("File " + file.getAbsolutePath() + "  does not exist");
        ClipboardFormat format = ClipboardFormat.SCHEMATIC;
        try {
            return format.load(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
