package x00Hero.MineRP.Events.DefaultMC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import x00Hero.MineRP.Events.Constructors.DoorInteractEvent;

public class InteractEvent implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType().name().contains("DOOR")) { // is a door
                Location loc = e.getClickedBlock().getLocation();
                Block block = e.getClickedBlock();
                Block above = block.getRelative(BlockFace.UP);
                boolean isTop = false;
                String aboveName = above.getType().name();
                if(!aboveName.contains("DOOR") && !aboveName.contains("TRAPDOOR")) isTop = true;
                if(isTop) loc = loc.add(0, -1, 0);
                if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Bukkit.getPluginManager().callEvent(new DoorInteractEvent(e.getPlayer(), loc, true));
                } else {
                    Bukkit.getPluginManager().callEvent(new DoorInteractEvent(e.getPlayer(), loc, false));
                }
            }
        }
    }
}
