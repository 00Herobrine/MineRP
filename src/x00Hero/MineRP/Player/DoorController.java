package x00Hero.MineRP.Player;

import org.bukkit.*;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Events.Constructors.Player.DoorInteractEvent;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;
import x00Hero.MineRP.Items.Generic.OwnableDoor;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Chat.TimedAlert;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

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
            if(door.contains("sound-lock")) ownableDoor.setLockSound(Sound.valueOf(door.getString("sound-lock")));
            if(door.contains("sound-unlock")) ownableDoor.setUnlockSound(Sound.valueOf(door.getString("sound-unlock")));
            if(door.contains("lockpick-time")) ownableDoor.setDefaultLockPickTime(door.getInt("lockpick-time"));
            if(door.contains("lockpick-volume")) ownableDoor.setLockPickVolume(door.getInt("lockpick-volume"));
            cachedDoors.put(location, ownableDoor);
        }
    }

    @EventHandler
    public void DoorInteract(DoorInteractEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        Player player = rPlayer.getPlayer();
        OwnableDoor door = e.getDoor();
        Location location = door.getLocation();
        World world = location.getWorld();
        boolean rightClick = e.isRightClick();
        boolean isSneaking = player.isSneaking();
        boolean isKeys = e.isKeys();
        boolean isOwner = door.isOwner(player.getUniqueId()); // checks if is an owner of the door
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
                    rPlayer.sendAlert("Door &cLocked&r!");
                    location.getWorld().playSound(location, door.getLockSound(), 1f, 1f);
                } else {
                    door.setLocked(false);
                    if(isSneaking) {
                        sellDoor(door);
                        return;
                    }
                    rPlayer.sendAlert("Door &aUnlocked&r!");
                    location.getWorld().playSound(location, door.getUnlockSound(), 1f, 1f);
                }
                return;
            }
        }
        if(door.isLocked()) {
            world.playSound(location, Sound.ENTITY_ZOMBIE_ATTACK_WOODEN_DOOR, 0.7f, 1f); // play knocking sound
            TimedAlert alert = new TimedAlert("Door is &cLocked&r!", 3);
            if(rightClick) rPlayer.addAlert(alert);
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
        if(rPlayer.attemptPurchase(door.getPrice(), "door", true))
            door.setOwner(rPlayer.getPlayer().getUniqueId());
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
