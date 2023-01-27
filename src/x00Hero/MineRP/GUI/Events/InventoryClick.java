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
import x00Hero.MineRP.GUI.Constructors.MenuPage;

import java.util.ArrayList;

import static x00Hero.MineRP.GUI.MenuController.getPage;
import static x00Hero.MineRP.GUI.MenuController.inMenu;
import static x00Hero.MineRP.Main.plugin;

public class InventoryClick implements Listener {

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
    public void inventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        ItemStack clickedItem = e.getCurrentItem();
        if(clickedItem == null || clickedItem.getType() == Material.AIR || e.getClickedInventory() == null) return;
        if(clickedItem.equals(Menu.nothing.getItemStack())) {
            e.setCancelled(true);
            return;
        }
        if(inMenu(player)) {
            MenuPage page = getPage(player);
            if(e.getClickedInventory().equals(page.getInventory())) {
                ArrayList<MenuItem> menuItems = page.getMenuItems();
                if(menuItems != null) {
                    for(MenuItem menuItem : menuItems) {
                        if(clickedItem.equals(menuItem.getItemStack())) {
                            Bukkit.getServer().getPluginManager().callEvent(new MenuItemClickedEvent(player, menuItem, page.getMenu(), e));
                            return;
                        }
                    }
                }
            }
        }
    }
}
