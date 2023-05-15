package me.dxrk.Mines;

import java.io.File;
import java.util.List;
import java.util.Objects;
import org.bukkit.inventory.ItemStack;

public abstract class MineSchematic<S> {
    private final String name;

    private final List<String> description;

    protected final File file;

    private final ItemStack icon;

    protected MineSchematic(String name, List<String> description, File file, ItemStack icon) {
        this.name = name;
        this.description = description;
        this.file = file;
        this.icon = icon;
    }

    public abstract S getSchematic();

    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof MineSchematic))
            return false;
        MineSchematic<?> that = (MineSchematic)o;
        return (Objects.equals(getName(), that.getName()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getFile(), that.getFile()) &&
                Objects.equals(getIcon(), that.getIcon()));
    }

    public int hashCode() {
        return Objects.hash(new Object[] { getName(), getDescription(), getFile(), getIcon() });
    }

    public String getName() {
        return this.name;
    }

    public List<String> getDescription() {
        return this.description;
    }

    public File getFile() {
        return this.file;
    }

    public ItemStack getIcon() {
        return this.icon;
    }
}
