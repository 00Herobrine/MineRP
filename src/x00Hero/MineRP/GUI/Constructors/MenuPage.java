package x00Hero.MineRP.GUI.Constructors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static x00Hero.MineRP.GUI.Constructors.Menu.*;
import static x00Hero.MineRP.GUI.MenuController.setInMenu;

public class MenuPage {
    private Menu menu;
    private Inventory inventory = null;
    private int curSlot = 0;
    private int lastSlot = 0;
    public static int prevSlot = 45;
    public static int nextSlot = 53;
    boolean fillEmpty = true;
    boolean cancelClicks = true;
    private static ItemBuilder prevBuilder = new ItemBuilder(Material.ORANGE_STAINED_GLASS_PANE, "&7Previous Page", "&8Click to return a page.");
    private static ItemBuilder nextBuilder = new ItemBuilder(Material.GREEN_STAINED_GLASS_PANE, "&aNext Page", "&8Click to forward a page.");
    public static MenuItem prevPage = new MenuItem(prevBuilder.getItemStack(), prevSlot, "menu-page-previous");
    public static MenuItem nextPage = new MenuItem(nextBuilder.getItemStack(), nextSlot, "menu-page-next");
    private ArrayList<MenuItem> menuItems = new ArrayList<>();
    private String title;
    private static boolean previousButton;
    private static boolean nextButton;

    public String Colorize(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public MenuPage(String title, boolean fillEmpty, Menu menu) {
        this.title = title;
        this.fillEmpty = fillEmpty;
    }

    public void setTitle(String title) {
        this.title = title;
        int invSize = inventory.getSize();
        ItemStack[] contents = inventory.getContents();
        inventory = Bukkit.createInventory(null, invSize, Colorize(title));
        inventory.setContents(contents);
    }

    public String getTitle() {
        return title;
    }

    public void expandCheck(MenuItem menuItem) {
        if(menuItem.getSlot() > inventory.getSize()) {
            int slots = Menu.getAdjustedAmount(menuItem.getSlot());
            ItemStack[] items = inventory.getContents();
            inventory = Bukkit.createInventory(null, slots, Colorize(menuItem.getMenuPage().getTitle()));
            inventory.setContents(items);
        }
    }

    public void addItem(MenuItem menuItem) {
        addItem(menuItem, false);
    }

    public void addItem(MenuItem menuItem, boolean expandCheck) {
        menuItem.setMenuPage(this);
//        if(expandCheck) expandCheck(menuItem);
//        if(menuItem.isEnabled()) inventory.setItem(menuItem.getSlot(), menuItem.getItemStack());
        menuItems.add(menuItem);
    }

    public boolean isFillEmpty() {
        return fillEmpty;
    }
    public void setFillEmpty(boolean fillEmpty) {
        this.fillEmpty = fillEmpty;
    }

    public void setCancelClicks(boolean cancelClicks) {
        this.cancelClicks = cancelClicks;
    }
    public boolean isCancelClicks() {
        return cancelClicks;
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    public void open(Player player) {
        if(inventory == null) createInventory();
        if(fillEmpty) player.openInventory(fillInventory(inventory));
        else player.openInventory(inventory);
        setInMenu(player, this);
    }

    public void createInventory() {
        boolean isFirstPage = isFirstPage(this);
        for(MenuItem menuItem : menuItems) {
            if(menuItems.size() > 52 && curSlot == prevSlot && !isFirstPage) curSlot++;
            // shift last item to next page
            if(menuItem.getSlot() == -1) menuItem.setSlot(curSlot++); // hopefully that returns 0, then adds one
            int itemSlot = menuItem.getSlot();
            if(lastSlot < itemSlot) lastSlot = itemSlot;
        }
        if(lastSlot <= 5) inventory = Bukkit.createInventory(null, InventoryType.HOPPER, title);
        else inventory = Bukkit.createInventory(null, getAdjustedAmount(lastSlot), title);
        if(!isLastPage(this) && menuItems.size() > 52) inventory.setItem(nextSlot, nextBuilder.getItemStack());
        if(!isFirstPage && menuItems.size() > 52) inventory.setItem(prevSlot, prevBuilder.getItemStack());
        for(MenuItem menuItem : menuItems) {
            inventory.setItem(menuItem.getSlot(), menuItem.getItemStack());
        }
    }

    public ArrayList<MenuItem> getMenuItems() {
        return menuItems;
    }

    public MenuItem getMenuItem(MenuItem item) {
        for(MenuItem menuItem : getMenuItems()) {
            if(menuItem.equals(item)) {
                return menuItem;
            }
        }
        return null;
    }

    public int getCurSlot() {
        return curSlot;
    }

    public void setMenuItems(ArrayList<MenuItem> menuItems) {
        this.menuItems = menuItems;
    }

    public Menu getMenu() {
        return menu;
    }
    public void setMenu(Menu menu) {
        this.menu = menu;
    }
}
