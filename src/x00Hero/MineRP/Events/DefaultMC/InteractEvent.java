package x00Hero.MineRP.Events.DefaultMC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Events.Constructors.JobItemInteractEvent;
import x00Hero.MineRP.Events.Constructors.Player.DoorInteractEvent;
import x00Hero.MineRP.Events.Constructors.Player.LockPickDoorEvent;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Player.DoorController;
import x00Hero.MineRP.Player.RPlayer;

import static x00Hero.MineRP.Main.*;

public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        boolean rightClick = e.getAction() == Action.RIGHT_CLICK_BLOCK;
        if(rightClick || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            Player player = e.getPlayer();
            RPlayer rPlayer = getRPlayer(player);
            ItemStack heldItem = player.getInventory().getItemInMainHand();
            Block block = e.getClickedBlock();
            boolean isKeys = false;
            boolean isLockpick = false;
            if(heldItem.getType() != Material.AIR && heldItem.hasItemMeta()) {
                switch(getTags(heldItem)) {
                    case "keyset" -> isKeys = true;
                    case "lockpick" -> isLockpick = true;
                }
                if(getTags(heldItem).equalsIgnoreCase("keyset")) isKeys = true;
                if(getTags(heldItem).equalsIgnoreCase("lockpick")) isLockpick = true;
            }
            if(e.getClickedBlock().getType().name().contains("DOOR")) { // is a door
                Location loc = e.getClickedBlock().getLocation();
                assert block != null;
                Block above = block.getRelative(BlockFace.UP);
                boolean isTop = false;
                String aboveName = above.getType().name();
                if(!aboveName.contains("DOOR") && !aboveName.contains("TRAPDOOR")) isTop = true;
                if(isTop) loc = loc.add(0, -1, 0);
                if(!DoorController.getCachedDoors().containsKey(loc)) return;
                OwnableDoor door = DoorController.getDoor(loc);
                if(isLockpick) {
                    Bukkit.getPluginManager().callEvent(new LockPickDoorEvent(rPlayer, door, e));
                    return;
                }
                Bukkit.getPluginManager().callEvent(new DoorInteractEvent(rPlayer, door, rightClick, isKeys, e));
            }
            if(JobController.isJobItem(heldItem)) {
                JobItem jobItem = JobController.getJobItem(getTags(heldItem));
                pm.callEvent(new JobItemInteractEvent(rPlayer, jobItem, rightClick, block));
            }
        }
    }

}
