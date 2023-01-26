package x00Hero.MineRP.GUI.Constructors;

import org.bukkit.inventory.ItemStack;

public class MenuItem {
    private ItemStack itemStack;
    private String announce;
    private int slot = -1;
    private boolean enabled = true;
    private boolean cancelClick = true, droppable = false;
    private MenuPage menuPage;

    public MenuItem(ItemStack itemStack, String ID) {
        this.itemStack = itemStack;
        announce = ID;
    }

    public MenuItem(ItemStack itemStack, int slot, String ID) {
        this.itemStack = itemStack;
        this.slot = slot;
        announce = ID;
    }

    public MenuItem(ItemStack itemStack, int slot) {
        this.itemStack = itemStack;
        this.slot = slot;
        this.announce = "default";
    }

    public void setMenuPage(MenuPage menuPage) {
        this.menuPage = menuPage;
    }

    public MenuPage getMenuPage() {
        return menuPage;
    }

    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public String getAnnounce() {
        return announce;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isCancelClick() {
        return cancelClick;
    }
    public void setCancelClick(boolean cancelClick) {
        this.cancelClick = cancelClick;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isDroppable() {
        return droppable;
    }
    public void setDroppable(boolean droppable) {
        this.droppable = droppable;
    }

    public int getSlot() {
        return slot;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
