package x00Hero.MineRP.Events.Constructors;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Player.RPlayer;

public class PayCheckEvent extends Event {
    private HandlerList handlerList = new HandlerList();
    RPlayer rPlayer;
    long amount;
    int delay;

    public PayCheckEvent(RPlayer rPlayer, long amount, int delay) {
        this.rPlayer = rPlayer;
        this.amount = amount;
        this.delay = delay;
    }

    public RPlayer getRPlayer() {
        return rPlayer;
    }

    public Player getPlayer() {
        return rPlayer.getPlayer();
    }

    public long getAmount() {
        return amount;
    }

    public int getDelay() {
        return delay;
    }

    public HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
