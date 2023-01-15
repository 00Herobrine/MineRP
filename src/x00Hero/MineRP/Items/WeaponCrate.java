package x00Hero.MineRP.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Items.MoneyPrinters.Hologram;

public class WeaponCrate {
    private int count; // could store in NBT to save over restarts
    private int defaultCount; // just to do a x/max display
    private String name;
    private Material material;
    private ItemStack itemStack;
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

    public ItemStack getItemStack() { // shit to drop
        return itemStack;
    }
}
