package x00Hero.MineRP.GUI;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.GUI.Constructors.Menu;
import x00Hero.MineRP.GUI.Constructors.MenuItem;
import x00Hero.MineRP.GUI.Constructors.MenuItemClickedEvent;

import java.util.HashMap;
import java.util.UUID;

public class MenuController {
    private static HashMap<UUID, Menu> inMenu = new HashMap<>();
    
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

    public static void setInMenu(Player player, Menu menu) {
        inMenu.put(player.getUniqueId(), menu);
    }
}
