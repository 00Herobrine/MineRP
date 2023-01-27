package x00Hero.MineRP.Events.Constructors.Doors;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Player.RPlayer;

public class DoorInteractEvent extends Event implements Cancellable {
    private final RPlayer player;
    private final OwnableDoor door;
    private final boolean rightClick, keys;
    private final static HandlerList handlerList = new HandlerList();
    private final PlayerInteractEvent interactEvent;
    private final BlockRedstoneEvent redstoneEvent;
    private boolean cancelled = false;

    public DoorInteractEvent(OwnableDoor door, BlockRedstoneEvent redstoneEvent) {
        this.door = door;
        this.redstoneEvent = redstoneEvent;
        this.player = null;
        this.rightClick = true;
        this.keys = false;
        this.interactEvent = null;
    }

    public DoorInteractEvent(RPlayer player, OwnableDoor door, boolean rightClick, boolean keys, PlayerInteractEvent interactEvent) {
        this.player = player;
        this.door = door;
        this.rightClick = rightClick;
        this.keys = keys;
        this.interactEvent = interactEvent;
        this.redstoneEvent = null;
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

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }

    public BlockRedstoneEvent getRedstoneEvent() {
        return redstoneEvent;
    }
}
