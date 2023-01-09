package x00Hero.MineRP.Items.Generic;

public class LockPickStat {
    private int elapsed = 0;
    private long lastPicked;
    private final long startTime, finishTime;

    public LockPickStat(long currentTime, long finishTime) {
        this.lastPicked = currentTime;
        this.startTime = currentTime;
        this.finishTime = finishTime;
    }

    public void tick() {
        lastPicked = System.currentTimeMillis();
        elapsed++;
    }
    public int getElapsed() {
        return elapsed;
    }
    public void setElapsed(int elapsed) {
        this.elapsed = elapsed;
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
