package x00Hero.MineRP.Events.DefaultMC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Events.Constructors.DoorInteractEvent;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.DoorController;
import x00Hero.MineRP.Player.RPlayer;

import static x00Hero.MineRP.Main.getTags;

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
                if(!DoorController.getCachedDoors().containsKey(loc)) return;
                OwnableDoor door = DoorController.getDoor(loc);
                boolean rightClick = e.getAction() == Action.RIGHT_CLICK_BLOCK;
                RPlayer rPlayer = Main.getRPlayer(e.getPlayer());
                ItemStack heldItem = rPlayer.getPlayer().getInventory().getItemInMainHand();
                boolean isKeys = false;
                Bukkit.getPluginManager().callEvent(new DoorInteractEvent(rPlayer, door, rightClick, isKeys, e));
            }
        }
    }
}
