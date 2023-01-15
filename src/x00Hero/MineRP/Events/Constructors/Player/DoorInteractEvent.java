package x00Hero.MineRP.Events.Constructors.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Player.RPlayer;

public class DoorInteractEvent extends Event {
    private RPlayer player;
    private OwnableDoor door;
    private boolean rightClick, keys;
    private static HandlerList handlerList = new HandlerList();
    private PlayerInteractEvent interactEvent;

    public DoorInteractEvent(RPlayer player, OwnableDoor door, boolean rightClick, boolean keys, PlayerInteractEvent interactEvent) {
        this.player = player;
        this.door = door;
        this.rightClick = rightClick;
        this.keys = keys;
        this.interactEvent = interactEvent;
    }

    public RPlayer getRPlayer() {
        return player;
    }

    public Player getPlayer() {
        return player.getPlayer();
    }

    public OwnableDoor getDoor() {
        return door;
    }

    public boolean isRightClick() {
        return rightClick;
    }
    public boolean isKeys() {
        return keys;
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
