package x00Hero.MineRP.Events.Constructors;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;

public class PrinterTickEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    private MoneyPrinter printer;

    public PrinterTickEvent(MoneyPrinter printer) {
        this.printer = printer;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    public MoneyPrinter getPrinter() {
        return printer;
    }
}
