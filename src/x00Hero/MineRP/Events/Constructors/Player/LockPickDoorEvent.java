package x00Hero.MineRP.Events.Constructors.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
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

    public Player getPlayer() {
        return rPlayer.getPlayer();
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

    public boolean isFailed() {
        return interactEvent == null;
    }

    public ItemStack getLockpick() {
        return door.getLockpickers().get(getPlayer().getUniqueId()).getLockpick();
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
