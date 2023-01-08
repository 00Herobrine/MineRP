package x00Hero.MineRP.Jobs;

import org.bukkit.inventory.ItemStack;

public class JobItem {
    private ItemStack itemStack;
    private int slot;
    private boolean moveable = true;
    private boolean droppable = false;
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

    public boolean isMoveable() {
        return moveable;
    }
    public void setMoveable(boolean moveable) {
        this.moveable = moveable;
    }

    public boolean isDroppable() {
        return droppable;
    }
    public void setDroppable(boolean droppable) {
        this.droppable = droppable;
    }

    public boolean isDropOnDeath() {
        return dropOnDeath;
    }
    public void setDropOnDeath(boolean dropOnDeath) {
        this.dropOnDeath = dropOnDeath;
    }
}
