package x00Hero.MineRP.Items;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Items.MoneyPrinters.Hologram;
import x00Hero.MineRP.Items.MoneyPrinters.HologramController;

public class WeaponCrate {
    private int count; // could store in NBT to save over restarts
    private int defaultCount; // just to do a x/max display
    private final String name;
    private Material material;
    private final ItemStack itemStack;
    private Location location;
    private Hologram hologram;

    public WeaponCrate(ItemStack itemStack) {
        this.defaultCount = 10;
        this.count = defaultCount;
        this.name = itemStack.getItemMeta().getDisplayName();
        this.material = Material.BARREL;
        this.itemStack = itemStack;
    }

    public int getCount() {
        return count;
    }
    public void reduceCount() {
        count--;
    }

    public int getDefaultCount() {
        return defaultCount;
    }
    public void setDefaultCount(int defaultCount) {
        this.defaultCount = defaultCount;
    }

    public String getName() {
        return name;
    }

    public Material getMaterial() { // returns the crate material
        return material;
    }
    public void setMaterial(Material material) { // crate material
        this.material = material;
    }

    public ItemStack getItemStack() { // shit to drop
        return itemStack;
    }

    public Location getLocation() { // location of crate placed
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public Hologram getHologram() {
        return hologram;
    }
    public void setHologram(Hologram hologram) {
        this.hologram = hologram;
    }
    public void createHologram() {
        Hologram hologram = new Hologram(location);
        hologram.addLine(name);
        hologram.addLine("Quantity: " + count + "/" + defaultCount);
        hologram = hologram.create();
        HologramController.addHologram(hologram);
        setHologram(hologram);
    }
    public void destoryHologram() {
        Hologram hologram = getHologram();
        hologram.remove();
        HologramController.removeHologram(hologram);
    }
}
