package x00Hero.MineRP.Items.Generic;

import org.bukkit.Location;

import java.util.ArrayList;
import java.util.UUID;

public class OwnableDoor {
    private Location location;
    private UUID owner;
    private ArrayList<UUID> owners = new ArrayList<>();
    private int LockPickTime, defaultLockPickTime, price, sellFee;

    public OwnableDoor(Location location) {

    }
}
