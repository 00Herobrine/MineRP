package x00Hero.MineRP.Items.Generic;

public class LockPickStat {
    private long lastPicked, finish;

    public LockPickStat(long lastPicked, long finish) {
        this.lastPicked = lastPicked;
        this.finish = finish;
    }

    public long getFinish() {
        return finish;
    }
    public void setFinish(long finish) {
        this.finish = finish;
    }

    public long getLastPicked() {
        return lastPicked;
    }
    public void setLastPicked(long lastPicked) {
        this.lastPicked = lastPicked;
    }

}
