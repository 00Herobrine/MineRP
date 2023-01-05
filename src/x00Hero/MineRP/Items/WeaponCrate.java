package x00Hero.MineRP.Items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class WeaponCrate {
    private int count;
    private int defaultCount; // just to do a x/max display
    private String name;
    private Material material;
    private ItemStack itemStack;

    public WeaponCrate(ItemStack itemStack) {
        this.defaultCount = 10;
        this.count = defaultCount;
        this.name = itemStack.getItemMeta().getDisplayName();
        this.material = Material.BARREL;
        this.itemStack = itemStack;
    }
}
