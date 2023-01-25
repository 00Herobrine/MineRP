package x00Hero.MineRP.Events.Constructors.Crate;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.WeaponCrate;
import x00Hero.MineRP.Player.RPlayer;

public class WeaponCratePlaceEvent extends Event {
    private static HandlerList handlerList = new HandlerList();
    private final RPlayer rPlayer;
    private final Location location;
    private final WeaponCrate weaponCrate;

    public WeaponCratePlaceEvent(WeaponCrate weaponCrate, RPlayer rPlayer, Location location) {
        this.rPlayer = rPlayer;
        this.location = location;
        this.weaponCrate = weaponCrate;
    }

    public RPlayer getRPlayer() {
        return rPlayer;
    }

    public Location getLocation() {
        return location;
    }

    public WeaponCrate getWeaponCrate() {
        return weaponCrate;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
