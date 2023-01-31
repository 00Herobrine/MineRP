package x00Hero.MineRP.GUI.Constructors;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;

public class Menu {
    private final String title;
    private int curPage = 1;
    private static HashMap<Integer, MenuPage> pages = new HashMap<>();
    private boolean fillEmpty = true, cached = false;

    public static ItemBuilder nothing = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE, 1, " ");

    public Menu(String title) {
        this.title = title;
    }

    public Menu(String title, boolean fillEmpty) {
        this.title = title;
        this.fillEmpty = fillEmpty;
    }

    public void addItem(ItemStack item, String id) {
        if(!isValidPage(curPage)) createNewPage();
        MenuPage currentPage = getCurrentPage();
        MenuItem menuItem = new MenuItem(item, currentPage.getCurSlot(), id);
        currentPage.addItem(menuItem);
    }

    public void addMenuItems(ArrayList<MenuItem> menuItems) {
        if(!isValidPage(curPage)) createNewPage();
        MenuPage currentPage = getCurrentPage();
        for(MenuItem menuItem : menuItems) {
            currentPage.addItem(menuItem);
        }
    }

    public boolean isValidPage(int pageNum) {
        MenuPage page = getPage(pageNum);
        return (page != null && page.getInventory() != null && page.getInventory().firstEmpty() != -1);
    }

    public void createNewPage() {
        MenuPage page = new MenuPage(title + " pg. " + curPage, fillEmpty, this);
        setPage(curPage, page);
    }

    public void addPage(MenuPage menuPage) {
        if(getCurrentPage() != null) curPage++;
        setPage(curPage, menuPage);
    }

    public void setPage(int pageNum, MenuPage page) {
        pages.put(pageNum, page);
    }

    public void open(Player player) {
        openPage(player, 1);
    }
    public void openPage(Player player, int page) {
        if(!cached) cachePages();
        getPage(page).open(player);
    }
    public MenuPage getCurrentPage() {
        return getPage(curPage);
    }
    public MenuPage getPage(int page) {
        return pages.get(page);
    }

    public static int getAdjustedAmount(Integer slots) {
        return (int) (Math.ceil((double) slots / 9)) * 9;
    }

    public void cachePages() {
        for(MenuPage page : pages.values()) {
            page.createInventory();
        }
        cached = true;
    }

    public static boolean isLastPage(MenuPage page) {
        return page == pages.get(pages.size());
    }

    public static boolean isFirstPage(MenuPage page) {
        return page == pages.get(1);
    }

    public static Inventory fillInventory(Inventory i) {
        for(int f = 0; f < i.getSize(); f++) { // turn this into a function
            if(i.firstEmpty() != -1) {
                i.setItem(i.firstEmpty(), nothing.getItemStack());
            } else {
                f = i.getSize();
            }
        }
        return i;
    }

    public boolean isCached() {
        return cached;
    }

    public void setFillEmpty(boolean fillEmpty) {
        this.fillEmpty = fillEmpty;
    }
}

