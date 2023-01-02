package x00Hero.MineRP.Events.Constructors;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Player.RPlayer;

public class PayCheckEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    private RPlayer rPlayer;
    private long amount;
    private int delay;

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

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
