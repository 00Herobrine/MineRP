package x00Hero.MineRP.Events.Constructors;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;

public class PrinterPrintEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    private final MoneyPrinter printer;
    private final int amount;

    public PrinterPrintEvent(MoneyPrinter printer, int amount) {
        this.printer = printer;
        this.amount = amount;
    }

    public MoneyPrinter getPrinter() {
        return printer;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
