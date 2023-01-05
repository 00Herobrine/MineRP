package x00Hero.MineRP.Events.Constructors;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import x00Hero.MineRP.Events.DefaultMC.InteractEvent;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Player.RPlayer;

public class DoorInteractEvent extends Event {
    private RPlayer player;
    private OwnableDoor door;
    private boolean rightClick, isKeys;
    private static HandlerList handlerList = new HandlerList();
    private PlayerInteractEvent interactEvent;

    public DoorInteractEvent(RPlayer player, OwnableDoor door, boolean rightClick, boolean isKeys, PlayerInteractEvent interactEvent) {
        this.player = player;
        this.door = door;
        this.rightClick = rightClick;
        this.isKeys = isKeys;
        this.interactEvent = interactEvent;
    }

    public RPlayer getPlayer() {
        return player;
    }

    public OwnableDoor getDoor() {
        return door;
    }

    public boolean isRightClick() {
        return rightClick;
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
