package x00Hero.MineRP.Events.Constructors.Player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class KeyInteractEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    private Player player;
    private Location location;
    private boolean isRightClick;

    public KeyInteractEvent(Player player, Location location, boolean isRightClick) {
        this.player = player;
        this.location = location;
        this.isRightClick = isRightClick;
    }

    public Player getPlayer() {
        return player;
    }

    public Location getLocation() {
        return location;
    }

    public boolean isRightClick() {
        return isRightClick;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
