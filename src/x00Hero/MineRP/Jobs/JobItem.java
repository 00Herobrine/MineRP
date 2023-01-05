package x00Hero.MineRP.Jobs;

import org.bukkit.inventory.ItemStack;

public class JobItem {
    private ItemStack itemStack;
    private int slot;
    private boolean dropOnDeath = false;

    public JobItem(ItemStack itemStack, Integer slot) {
        this.itemStack = itemStack;
        this.slot = slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getSlot() {
        return slot;
    }

    public boolean isDropOnDeath() {
        return dropOnDeath;
    }
    public void setDropOnDeath(boolean dropOnDeath) {
        this.dropOnDeath = dropOnDeath;
    }
}
