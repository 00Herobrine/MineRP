package x00Hero.MineRP.GUI.Events;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import x00Hero.MineRP.GUI.Constructors.Menu;
import x00Hero.MineRP.GUI.Constructors.MenuItem;
import x00Hero.MineRP.GUI.Constructors.MenuItemClickedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static x00Hero.MineRP.Main.plugin;

public class MenuController implements Listener {
    private static HashMap<Player, Menu> inMenus = new HashMap<>();
    // maybe temporarily store the Player with playerFile in a hashmap so I don't need to constantly store all this in NBT (can be viewed by players)
    // HashMap<Player, File> fileAccess
    public String getStoredString(ItemStack itemStack, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        ItemMeta itemMeta = itemStack.getItemMeta();
        assert itemMeta != null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if(container.has(namespacedKey, PersistentDataType.STRING)) {
            return container.get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }


    @EventHandler
    public void MenuClicked(MenuItemClickedEvent event) {
        Player player = event.getWhoClicked();
        Menu menu = event.getMenu();
        MenuItem menuItem = event.getMenuItem();
        if(menuItem.isCancelClick()) event.setCancelled(true);
        String ID = event.getID();
        String args[] = ID.split("/");
        ItemStack itemStack = event.getMenuItem().getItemStack();
        switch(args[0]) {
            default:
                break;
        }
    }

    public static void setMenu(Player player, Menu menu) {
        inMenus.put(player, menu);
    }

    @EventHandler
    public void inventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
        if(e.getCurrentItem().equals(Menu.nothing.getItemStack())) {
            e.setCancelled(true);
            return;
        }
        if(inMenus.containsKey(p)) {
            Menu menuViewer = inMenus.get(p);
            if(e.getView().getTopInventory().equals(menuViewer.getCurrentInventory())) {
                ArrayList<MenuItem> menuItems = menuViewer.getCurrentPage().getMenuItems();
                if(menuItems != null) {
                    for(MenuItem menuItem : menuItems) {
                        if(e.getCurrentItem().equals(menuItem.getItemStack())) {
                            Bukkit.getServer().getPluginManager().callEvent(new MenuItemClickedEvent(p, menuItem, menuViewer, e));
                            return;
                        }
                    }
                }
            }
        }
    }
}
