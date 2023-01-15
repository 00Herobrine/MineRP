package x00Hero.MineRP.Items.Generic;

public class LockPickStat {
    private long lastPicked, nextSoundTime;
    private final long startTime, finishTime, soundInterval = 1000; // in ms
    private boolean alerted = false;

    public LockPickStat(long currentTime, long finishTime) {
        this.lastPicked = currentTime;
        this.startTime = currentTime;
        this.finishTime = finishTime;
    }

    public void tick() {
        lastPicked = System.currentTimeMillis();
    }

    public boolean isAlerted() {
        return alerted;
    }
    public void setAlerted(boolean alerted) {
        this.alerted = alerted;
    }

    public long getFinishTime() {
        return finishTime;
    }
    public long getStartTime() {
        return startTime;
    }

    public long getLastPicked() {
        return lastPicked;
    }
    public void setLastPicked(long lastPicked) {
        this.lastPicked = lastPicked;
    }

}
