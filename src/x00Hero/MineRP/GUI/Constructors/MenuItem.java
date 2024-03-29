package x00Hero.MineRP.GUI.Constructors;

import org.bukkit.inventory.ItemStack;

public class MenuItem {
    private ItemStack itemStack;
    private String announce;
    private int slot = -1;
    private int page = 1;
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

    public MenuPage getMenuPage() {
        return menuPage;
    }
    public void setMenuPage(MenuPage menuPage) {
        this.menuPage = menuPage;
    }

    public String getAnnounce() {
        return announce;
    }
    public void setAnnounce(String announce) {
        this.announce = announce;
    }

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }

    public int getSlot() {
        return slot;
    }
    public void setSlot(int slot) {
        this.slot = slot;
    }

    public boolean isEnabled() {
        return enabled;
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

    public boolean isDroppable() {
        return droppable;
    }
    public void setDroppable(boolean droppable) {
        this.droppable = droppable;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }
}
