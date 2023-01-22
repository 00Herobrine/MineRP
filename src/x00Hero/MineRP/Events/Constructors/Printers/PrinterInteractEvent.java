package x00Hero.MineRP.Events.Constructors.Printers;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;
import x00Hero.MineRP.Player.RPlayer;

public class PrinterInteractEvent extends Event {
    private RPlayer RPlayer;
    private MoneyPrinter moneyPrinter;
    private boolean rightClick;
    private static HandlerList handlerList = new HandlerList();

    public PrinterInteractEvent(RPlayer RPlayer, MoneyPrinter moneyPrinter, boolean rightClick) {
        this.RPlayer = RPlayer;
        this.moneyPrinter = moneyPrinter;
        this.rightClick = rightClick;
    }

    public RPlayer getRPlayer() {
        return RPlayer;
    }

    public Player getPlayer() {
        return RPlayer.getPlayer();
    }

    public MoneyPrinter getMoneyPrinter() {
        return moneyPrinter;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    private static HandlerList getHandlerList() {
        return handlerList;
    }
}
