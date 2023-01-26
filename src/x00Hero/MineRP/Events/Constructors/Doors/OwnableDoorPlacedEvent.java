package x00Hero.MineRP.Events.Constructors.Doors;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Player.RPlayer;

public class OwnableDoorPlacedEvent extends Event {
    private final OwnableDoor door;
    private final RPlayer rPlayer;
    private static final HandlerList handlerList = new HandlerList();

    public OwnableDoorPlacedEvent(OwnableDoor door, RPlayer rPlayer) {
        this.rPlayer = rPlayer;
        this.door = door;
    }

    public Player getPlayer() {
        return rPlayer.getPlayer();
    }

    public RPlayer getRPlayer() {
        return rPlayer;
    }

    public String getID() {
        return door.getID();
    }

    public OwnableDoor getDoor() {
        return door;
    }

    private static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
