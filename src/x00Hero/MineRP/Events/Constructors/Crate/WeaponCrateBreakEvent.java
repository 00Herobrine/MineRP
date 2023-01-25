package x00Hero.MineRP.Events.Constructors.Crate;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Items.WeaponCrate;
import x00Hero.MineRP.Player.RPlayer;

public class WeaponCrateBreakEvent extends Event {
    private static final HandlerList handlerList = new HandlerList();
    private final RPlayer rPlayer;
    private final WeaponCrate weaponCrate;
    private final boolean rightClick;

    public WeaponCrateBreakEvent(RPlayer rPlayer, WeaponCrate weaponCrate, boolean rightClick) {
        this.rPlayer = rPlayer;
        this.weaponCrate = weaponCrate;
        this.rightClick = rightClick;
    }

    public RPlayer getRPlayer() {
        return rPlayer;
    }

    public WeaponCrate getWeaponCrate() {
        return weaponCrate;
    }

    public boolean isRightClick() {
        return rightClick;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
