package x00Hero.MineRP.Events.Constructors.Printers;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;

public class PrinterCreateEvent extends Event {
    private final static HandlerList handlerList = new HandlerList();
    private final Player whoPlaced;
    private final MoneyPrinter printer;
    private final Location location;

    public PrinterCreateEvent(MoneyPrinter printer, Player whoPlaced, Location location) {
        this.printer = printer;
        this.whoPlaced = whoPlaced;
        this.location = location;
    }

    public MoneyPrinter getPrinter() {
        return printer;
    }

    public Player getWhoPlaced() {
        return whoPlaced;
    }

    public Location getLocation() {
        return location;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
