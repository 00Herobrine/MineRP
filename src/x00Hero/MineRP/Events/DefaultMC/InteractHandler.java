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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Events.Constructors.Crate.WeaponCratePlaceEvent;
import x00Hero.MineRP.Events.Constructors.Doors.OwnableDoorDestroyedEvent;
import x00Hero.MineRP.Events.Constructors.Doors.OwnableDoorPlacedEvent;
import x00Hero.MineRP.Events.Constructors.JobItemInteractEvent;
import x00Hero.MineRP.Events.Constructors.Player.DoorInteractEvent;
import x00Hero.MineRP.Events.Constructors.Player.LockPickDoorEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterCreateEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterDestroyedEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterInteractEvent;
import x00Hero.MineRP.Items.CrateController;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;
import x00Hero.MineRP.Items.MoneyPrinters.PrinterController;
import x00Hero.MineRP.Items.WeaponCrate;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Player.DoorController;
import x00Hero.MineRP.Player.RPlayer;

import static x00Hero.MineRP.Main.*;

public class InteractHandler implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        boolean rightClick = (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR);
        Player player = e.getPlayer();
        RPlayer rPlayer = getRPlayer(player);
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        Block block = e.getClickedBlock();
        if(block == null) return;
        Material blockType = block.getType();
        Location blockLoc = block.getLocation();
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK) {
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
            if(blockType.name().contains("DOOR")) { // is a door
                Block above = block.getRelative(BlockFace.UP);
                boolean isTop = false;
                String aboveName = above.getType().name();
                if(!aboveName.contains("DOOR") && !aboveName.contains("TRAPDOOR")) isTop = true;
                if(isTop) blockLoc = blockLoc.add(0, -1, 0);
                if(!DoorController.getCachedDoors().containsKey(blockLoc)) return;
                OwnableDoor door = DoorController.getDoor(blockLoc);
                if(isLockpick) {
                    Bukkit.getPluginManager().callEvent(new LockPickDoorEvent(rPlayer, door, e));
                    return;
                }
                Bukkit.getPluginManager().callEvent(new DoorInteractEvent(rPlayer, door, rightClick, isKeys, e));
            } else if(PrinterController.contains(blockLoc)) {
                MoneyPrinter printer = PrinterController.getPrinter(blockLoc);
                Bukkit.getPluginManager().callEvent(new PrinterInteractEvent(rPlayer, printer, rightClick));
            }
        } else if(JobController.isJobItem(heldItem)) {
            JobItem jobItem = JobController.getJobItem(getTags(heldItem));
            pm.callEvent(new JobItemInteractEvent(rPlayer, jobItem, rightClick, block));
        }
    }

    @EventHandler
    public void BlockPlaceEvent(BlockPlaceEvent e) {
        Player player = e.getPlayer();
        RPlayer rPlayer = getRPlayer(player);
        ItemStack item = e.getItemInHand();
        Location location = e.getBlock().getLocation();
        String tag = getTags(item);
        if(tag == null) return;
        switch(tag) {
            case "moneyprinter" -> {
                String printerID = "shit";
                MoneyPrinter printer = PrinterController.getCachedPrinter(printerID);
//            Bukkit.broadcastMessage("placed block at " + location);
                Bukkit.getPluginManager().callEvent(new PrinterCreateEvent(printer, rPlayer, location));
            }
            case "weaponcrate" -> {
                WeaponCrate weaponCrate = CrateController.getCrate(location);
                Bukkit.getPluginManager().callEvent(new WeaponCratePlaceEvent(weaponCrate, rPlayer, location));
            }
            case "ownabledoor" -> {
                OwnableDoor door = new OwnableDoor(item.getItemMeta().getDisplayName(), location);
                door.setMaterial(item.getType());
                Bukkit.getPluginManager().callEvent(new OwnableDoorPlacedEvent(door, rPlayer));
            }
        }
    }

    @EventHandler
    public void BlockBreakEvent(BlockBreakEvent e) {
        Player player = e.getPlayer();
        Block block = e.getBlock();
        Location location = block.getLocation();
        MoneyPrinter printer = PrinterController.getPrinter(location);
        if(printer != null) Bukkit.getPluginManager().callEvent(new PrinterDestroyedEvent(printer, player, "mined"));
        else {
            OwnableDoor door = DoorController.getDoor(location);
            if(door != null) Bukkit.getPluginManager().callEvent(new OwnableDoorDestroyedEvent(door, player));
        }
    }

}
