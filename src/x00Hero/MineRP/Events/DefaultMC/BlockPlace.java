package x00Hero.MineRP.Events.DefaultMC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterCreateEvent;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;
import x00Hero.MineRP.Items.MoneyPrinters.PrinterController;
import x00Hero.MineRP.Main;

public class BlockPlace implements Listener {

    @EventHandler
    public void BlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        ItemStack item = e.getItemInHand();
        Location location = e.getBlock().getLocation();
        if(Main.hasTags(item, "moneyprinter")) {
            String printerID = "";
            MoneyPrinter printer = PrinterController.getPrinter(printerID);
            Bukkit.getPluginManager().callEvent(new PrinterCreateEvent(printer, player, location));
        }
    }
    
}
