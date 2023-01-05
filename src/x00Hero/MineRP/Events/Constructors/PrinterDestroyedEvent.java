package x00Hero.MineRP.Events.Constructors;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;

public class PrinterDestroyedEvent extends Event {
    private final HandlerList handlerList = new HandlerList();
    private final Player destroyer;
    private final MoneyPrinter printer;
    private final String destructionMethod;

    public PrinterDestroyedEvent(MoneyPrinter printer, Player destroyer, String destructionMethod) {
        this.printer = printer;
        this.destroyer = destroyer;
        this.destructionMethod = destructionMethod;
    }

    public MoneyPrinter getPrinter() {
        return printer;
    }

    public Player getDestroyer() {
        return destroyer;
    }

    public String getDestructionMethod() {
        return destructionMethod;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }
}
