package x00Hero.MineRP.Events.Constructors.Player;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Player.RPlayer;

public class LockPickDoorEvent extends Event {

    private RPlayer rPlayer;
    private OwnableDoor door;
    private static HandlerList handlerList = new HandlerList();
    private PlayerInteractEvent interactEvent;

    public LockPickDoorEvent(RPlayer rPlayer, OwnableDoor door, PlayerInteractEvent interactEvent) {
        this.rPlayer = rPlayer;
        this.door = door;
        this.interactEvent = interactEvent;
    }

    public RPlayer getRPlayer() {
        return rPlayer;
    }

    public OwnableDoor getDoor() {
        return door;
    }

    public PlayerInteractEvent getInteractEvent() {
        return interactEvent;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
