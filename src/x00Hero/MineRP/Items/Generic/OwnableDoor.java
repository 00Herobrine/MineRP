package x00Hero.MineRP.Items.Generic;

import org.bukkit.Location;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.RPlayer;

import java.util.ArrayList;
import java.util.UUID;

public class OwnableDoor {
    private Location location;
    private UUID owner;
    private ArrayList<UUID> owners = new ArrayList<>();
    private int LockPickTime, defaultLockPickTime, price, sellFee;
    private boolean locked;

    public OwnableDoor(Location location) {
        this.location = location;
        this.locked = false;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }


    public RPlayer getOwner() {
        return Main.getRPlayer(owner);
    }
    public UUID getOwnerID() {
        return owner;
    }
    public void setOwner(UUID owner) {
        this.owner = owner;
    }
    public ArrayList<UUID> getOwners() {
        return owners;
    }
    public void setOwners(ArrayList<UUID> owners) {
        this.owners = owners;
    }
    public boolean isOwner(UUID uuid) {
        return (owner == uuid || owners.contains(uuid));
    }

    public int getLockPickTime() {
        return LockPickTime;
    }
    public void setLockPickTime(int lockPickTime) {
        LockPickTime = lockPickTime;
    }
    public int getDefaultLockPickTime() {
        return defaultLockPickTime;
    }
    public void setDefaultLockPickTime(int defaultLockPickTime) {
        this.defaultLockPickTime = defaultLockPickTime;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getSellFee() {
        return sellFee;
    }
    public void setSellFee(int sellFee) {
        this.sellFee = sellFee;
    }

    public boolean isLocked() {
        return locked;
    }
    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
