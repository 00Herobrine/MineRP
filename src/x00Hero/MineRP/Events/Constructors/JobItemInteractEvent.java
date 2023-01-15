package x00Hero.MineRP.Events.Constructors;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Player.RPlayer;

public class JobItemInteractEvent extends Event {
    private JobItem jobItem;
    private RPlayer RPlayer;
    private boolean rightClick;
    private Block block;
    private HandlerList handlerList = new HandlerList();

    public JobItemInteractEvent(RPlayer RPlayer, JobItem jobItem, boolean rightClick, Block block) {
        this.RPlayer = RPlayer;
        this.jobItem = jobItem;
        this.rightClick = rightClick;
        this.block = block;
    }

    public RPlayer getRPlayer() {
        return RPlayer;
    }
    public Player getPlayer() {
        return RPlayer.getPlayer();
    }
    public JobItem getJobItem() {
        return jobItem;
    }
    public boolean isRightClick() {
        return rightClick;
    }
    public Block getBlock() {
        return block;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    private HandlerList getHandlerList() {
        return handlerList;
    }
}
