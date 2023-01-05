package x00Hero.MineRP.Player;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import x00Hero.MineRP.Events.Constructors.DoorInteractEvent;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.TimedAlert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static x00Hero.MineRP.Main.getTags;
import static x00Hero.MineRP.Main.plugin;

public class DoorController implements Listener {
    private static ItemBuilder keyRingBuilder = new ItemBuilder(Material.TRIPWIRE_HOOK, "Keys", "A set of all your keys", "keyset");
    private static ItemStack keyRing = keyRingBuilder.getItemStack();
    public static JobItem keys = new JobItem(keyRing, 7);
    private static File doorsFile = new File(plugin.getDataFolder(), "doors.yml");
    private static HashMap<Location, OwnableDoor> cachedDoors = new HashMap<>();

    public static void cacheDoors() {
        if(!doorsFile.exists()) plugin.saveResource("doors.yml", false);
        YamlConfiguration doorsConfig = YamlConfiguration.loadConfiguration(doorsFile);
        ConfigurationSection doors = doorsConfig.getConfigurationSection("doors");
        for(String doorID : doors.getKeys(false)) {
            ConfigurationSection door = doors.getConfigurationSection(doorID);
            String[] locString = door.getString("location").replaceAll(" ", "").split(",");
            int x = Integer.parseInt(locString[0]);
            int y = Integer.parseInt(locString[1]);
            int z = Integer.parseInt(locString[2]);
            World world = Bukkit.getWorld("world");
            Location location = new Location(world, x, y, z);
            OwnableDoor ownableDoor = new OwnableDoor(location);
            if(door.contains("price")) ownableDoor.setPrice(door.getInt("price"));
            if(door.contains("locked")) ownableDoor.setLocked(door.getBoolean("locked"));
            cachedDoors.put(location, ownableDoor);
        }
    }

    @EventHandler
    public void DoorInteract(DoorInteractEvent e) {
        RPlayer rPlayer = e.getPlayer();
        Player player = rPlayer.getPlayer();
        ItemStack heldItem = player.getInventory().getItemInMainHand();
        OwnableDoor door = e.getDoor();
        Location location = door.getLocation();
        World world = location.getWorld();
        boolean rightClick = e.isRightClick();
        boolean isSneaking = player.isSneaking();
        boolean isKeys = false;
        boolean isOwner = door.isOwner(player.getUniqueId()); // checks if is an owner of the door
        if(heldItem.getType() != null && heldItem.getType() != Material.AIR && heldItem.hasItemMeta())
            if(getTags(heldItem).equalsIgnoreCase("keyset")) isKeys = true;
        if(door.getOwner() == null && isSneaking && isKeys && rightClick) {
            // buy door
            e.getInteractEvent().setCancelled(true);
            buyDoor(rPlayer, door);
            return;
        }
        if(isOwner) {
            if(isKeys) {
                if(rightClick) {
                    e.getInteractEvent().setCancelled(true);
                    door.setLocked(true);
                    rPlayer.sendAlert("Door &cLocked&r!", Sound.BLOCK_BAMBOO_SAPLING_BREAK, 1f, 0.7f);
                    return;
                } else {
                    door.setLocked(false);
                    if(isSneaking) {
                        sellDoor(door);
                        return;
                    }
                    rPlayer.sendAlert("Door &aUnlocked&r!");
                    return;
                }
            }
        } else if(door.isLocked())
            world.playSound(location, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 1f, 1f); // not an owner, player knocking sound
        if(door.isLocked()) {
            TimedAlert alert = new TimedAlert("Door is &cLocked&r!", 2);
            rPlayer.addTimedAlert(alert);
            e.getInteractEvent().setCancelled(true);
        }
    }

    public void sellDoor(OwnableDoor door) {
        RPlayer owner = door.getOwner();
        ArrayList<UUID> emptyOwner = new ArrayList<>();
        door.setOwner(null);
        door.setOwners(emptyOwner);
        int amount = door.getPrice() - door.getSellFee();
        owner.sendAlert("Sold door for " + amount);
        owner.addCash(amount);
    }

    public void buyDoor(RPlayer rPlayer, OwnableDoor door) {
        int price = door.getPrice();
        door.setOwner(rPlayer.getPlayer().getUniqueId());
        rPlayer.removeCash(price);
        rPlayer.sendAlert("Purchased door for " + price);
    }

    public static HashMap<Location, OwnableDoor> getCachedDoors() {
        return cachedDoors;
    }

    public static OwnableDoor getDoor(Location location) {
        return cachedDoors.get(location);
    }

    public static ItemStack getKeyRing() {
        return keyRing;
    }


}
