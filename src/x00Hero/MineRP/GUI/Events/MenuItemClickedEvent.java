package x00Hero.MineRP.GUI.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import x00Hero.MineRP.GUI.Constructors.Menu;
import x00Hero.MineRP.GUI.Constructors.MenuItem;

public class MenuItemClickedEvent extends Event implements Cancellable {
    private MenuItem menuItem;
    private Menu menu;
    private Player whoClicked;
    private static final HandlerList HANDLERS_LIST = new HandlerList();
    private boolean isCancelled;
    private InventoryClickEvent event;

    public MenuItemClickedEvent(Player whoClicked, MenuItem menuItem, Menu menu, InventoryClickEvent event) {
        this.whoClicked = whoClicked;
        this.menuItem = menuItem;
        this.menu = menu;
        this.event = event;
    }

    public String getID() {
        if(menuItem == null) return "null";
        return menuItem.getAnnounce();
    }

    public Player getWhoClicked() {
        return whoClicked;
    }

    public Menu getMenu() {
        return menu;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    @Override
    public boolean isCancelled() {
        return isCancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        isCancelled = b;
        getEvent().setCancelled(true);
//        event.setCancelled(true);
    }

    public InventoryClickEvent getEvent() {
        return event;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }
}
