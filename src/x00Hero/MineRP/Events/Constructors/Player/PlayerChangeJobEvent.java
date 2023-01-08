package x00Hero.MineRP.Events.Constructors.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Jobs.Job;
import x00Hero.MineRP.Player.RPlayer;

public class PlayerChangeJobEvent extends Event {

    private static HandlerList handlerList = new HandlerList();
    private RPlayer RPlayer;
    private Job oldJob;
    private Job newJob;

    public PlayerChangeJobEvent(RPlayer RPlayer, Job oldJob, Job newJob) {
        this.RPlayer = RPlayer;
        this.oldJob = oldJob;
        this.newJob = newJob;
    }

    public RPlayer getRPlayer() {
        return RPlayer;
    }

    public Player getPlayer() {
        return RPlayer.getPlayer();
    }

    public Job getNewJob() {
        return newJob;
    }

    public Job getOldJob() {
        return oldJob;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
