package x00Hero.MineRP.GUI;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;
import x00Hero.MineRP.GUI.Constructors.Menu;
import x00Hero.MineRP.GUI.Constructors.MenuItem;
import x00Hero.MineRP.GUI.Constructors.MenuPage;
import x00Hero.MineRP.GUI.Events.MenuItemClickedEvent;
import x00Hero.MineRP.Jobs.JobItem;

import java.util.HashMap;
import java.util.UUID;

public class MenuController {
    private static HashMap<UUID, MenuPage> inMenu = new HashMap<>();
    private static ItemBuilder jobBookBuilder = new ItemBuilder(Material.WRITTEN_BOOK, "Actions Menu", "Opens a menu of stuff", "menubook");
    public static JobItem jobBook = new JobItem(jobBookBuilder.getItemStack(), 8);
    
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

    public static boolean inMenu(Player player) {
        return inMenu.containsKey(player.getUniqueId());
    }

    public static MenuPage getPage(Player player) {
        return inMenu.get(player.getUniqueId());
    }

    public static void setInMenu(Player player, MenuPage menuPage) {
        inMenu.put(player.getUniqueId(), menuPage);
    }
}
