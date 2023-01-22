package x00Hero.MineRP.Events.Constructors.Printers;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.RPlayer;

public class PrinterDestroyedEvent extends Event {
    private final static HandlerList handlerList = new HandlerList();
    private final RPlayer destroyer;
    private final MoneyPrinter printer;
    private final String destructionMethod;

    public PrinterDestroyedEvent(MoneyPrinter printer, Player destroyer, String destructionMethod) {
        this.printer = printer;
        this.destroyer = Main.getRPlayer(destroyer);
        this.destructionMethod = destructionMethod;
    }

    public MoneyPrinter getPrinter() {
        return printer;
    }

    public RPlayer getDestroyer() {
        return destroyer;
    }

    public String getDestructionMethod() {
        return destructionMethod;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    private static HandlerList getHandlerList() {
        return handlerList;
    }
}
