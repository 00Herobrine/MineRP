package x00Hero.MineRP.Items.Generic;

import org.bukkit.Location;
import org.bukkit.Sound;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.RPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class OwnableDoor {
    private Location location;
    private UUID owner;
    private ArrayList<UUID> owners = new ArrayList<>();
    private int defaultLockPickTime, price, sellFee;
    private Sound lockSound, unlockSound;
    private float volume, lockPickVolume;
    private boolean locked;
    private HashMap<UUID, LockPickStat> lockpickers = new HashMap<>();

    public OwnableDoor(Location location) {
        this.location = location;
        this.locked = false;
        this.volume = 0.7f;
        this.lockPickVolume = 1f;
    }

    //region General
    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public float getVolume() {
        return volume;
    }
    public void setVolume(float volume) {
        this.volume = volume;
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
    //endregion

    //region Ownership
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
    //endregion

    //region lockPicking
    public LockPickStat getLockStat(UUID uuid) {
        return lockpickers.get(uuid);
    }
    public long getLockPickFinish(UUID uuid) {
        return lockpickers.get(uuid).getFinish();
    }
    public void setLockPickFinish(UUID uuid, int time) {
        LockPickStat lockStat = getLockStat(uuid);
        lockStat.setFinish(time);
        lockpickers.put(uuid, lockStat);
    }
    public int getDefaultLockPickTime() {
        return defaultLockPickTime;
    }
    public void setDefaultLockPickTime(int defaultLockPickTime) {
        this.defaultLockPickTime = defaultLockPickTime;
    }
    public float getLockPickVolume() {
        return lockPickVolume;
    }
    public void setLockPickVolume(float lockPickVolume) {
        this.lockPickVolume = lockPickVolume;
    }
    public LockPickStat getLockPickStat(UUID uuid) {
        return lockpickers.get(uuid);
    }
    public void setLockPickStat(UUID uuid, LockPickStat stat) {
        lockpickers.put(uuid, stat);
    }
    public boolean isLockPicking(UUID uuid) {
        return lockpickers.containsKey(uuid);
    }
    public void startLockPicking(UUID uuid) {
        long finishTime = System.currentTimeMillis() + (defaultLockPickTime * 1000L);
        LockPickStat lockStat = new LockPickStat(System.currentTimeMillis(), finishTime);
        lockpickers.put(uuid, lockStat);
    }
    public void finishLockPicking(UUID uuid) {
        lockpickers.remove(uuid);
    }
    public HashMap<UUID, LockPickStat> getLockpickers() {
        return lockpickers;
    }
    //endregion
}
