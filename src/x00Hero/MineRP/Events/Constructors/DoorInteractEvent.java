package x00Hero.MineRP.Events.Constructors;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class DoorInteractEvent extends Event {
    private Player player;
    private Location location;
    private boolean isRightClick;
    private static HandlerList handlerList = new HandlerList();

    public DoorInteractEvent(Player player, Location location, boolean isRightClick) {
        this.player = player;
        this.location = location;
        this.isRightClick = isRightClick;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
