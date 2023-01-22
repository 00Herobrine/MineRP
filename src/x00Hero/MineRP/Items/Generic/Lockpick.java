package x00Hero.MineRP.Items.Generic;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Chat.ChatController;
import x00Hero.MineRP.Events.Constructors.Player.LockPickDoorEvent;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.DoorController;
import x00Hero.MineRP.Player.RPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import static x00Hero.MineRP.Main.plugin;

public class Lockpick implements Listener {
    private static final ItemBuilder lockpickBuilder = new ItemBuilder(Material.STICK, "Lockpick", "A universal key if you know how to use it.", "lockpick");
    private static final ItemStack lockpickItem = lockpickBuilder.getItemStack();
    public static JobItem lockpick = new JobItem(lockpickItem, 6);
    private static final ArrayList<Location> lockpicking = new ArrayList<>(); // doors being lockpicked
    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    private static int timeout = 250, cooldown = 3;

    public static void loadConfig() {
        timeout = plugin.getConfig().getInt("lockpick-timeout");
        cooldown = plugin.getConfig().getInt("lockpick-cooldown");
    }

    public String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        int leftOver = (totalBars - progressBars);
        String sb = ChatColor.translateAlternateColorCodes('&', completedColor) +
                String.valueOf(symbol).repeat(Math.max(0, progressBars)) +
                ChatColor.translateAlternateColorCodes('&', notCompletedColor) +
                String.valueOf(symbol).repeat(Math.max(0, leftOver));
        return sb;
    }

    public static void addCooldown(UUID uuid) {
        long futureTime = System.currentTimeMillis() + (cooldown * 1000L);
        cooldowns.put(uuid, futureTime);
    }

    public static void removeCooldown(UUID uuid) {
        cooldowns.remove(uuid);
    }

    public static void lockPickLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            long curTime = System.currentTimeMillis();
            for(Location location : lockpicking) {
                OwnableDoor door = DoorController.getDoor(location);
//                Bukkit.broadcastMessage("checking door " + door.getLocation());
                if(door.getLockpickers().size() == 0) {
                    lockpicking.remove(location);
                    return;
                }
                for(UUID picker : door.getLockpickers().keySet()) {
                    LockPickStat lockStat = door.getLockStat(picker);
                    long delay = curTime - lockStat.getLastPicked();
//                    Main.getRPlayer(picker).sendMessage("delay " + delay);
                    if(delay >= timeout) {
                        Main.getRPlayer(picker).sendAlert("Lock picking interrupted. (" + delay + "ms)");
                        door.finishLockPicking(picker);
                    }
                }
            }
            for(UUID uuid : cooldowns.keySet()) {
                if(cooldowns.get(uuid) < curTime) removeCooldown(uuid);
            }
        }, 0, 20);
    }

    public void startLockPicking(OwnableDoor door, UUID playerID) {
        door.startLockPicking(playerID);
        lockpicking.add(door.getLocation());
    }

    @EventHandler
    public void onLockPickEvent(LockPickDoorEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        Player player = rPlayer.getPlayer();
        UUID playerID = player.getUniqueId();
        OwnableDoor door = e.getDoor();
        LockPickStat lockStat = door.getLockPickStat(playerID);
        e.getInteractEvent().setCancelled(true);
        if(!cooldowns.containsKey(playerID)) {
            if(!door.isLockPicking(playerID)) { // hasn't started picking yet
                startLockPicking(door, playerID);
                lockStat = door.getLockStat(playerID);
            }
            if(!player.isSneaking() && !lockStat.isAlerted()) {
                rPlayer.sendMessage("Sneaking is advised to avoid the client door noise.");
                lockStat.setAlerted(true);
            }
            long curTime = System.currentTimeMillis();
            long lastInteractTime = lockStat.getLastPicked();
            long mathsTime = ((curTime - lockStat.getStartTime()) / 1000);
            int elapsedTime = (int) Math.ceil(mathsTime);
            long maths = ((lockStat.getFinishTime() - curTime) / 1000);
            int timeRemaining = (int) Math.ceil(maths);
            long delay = curTime - lastInteractTime;
            String progressBar = getProgressBar(elapsedTime + 1, door.getDefaultLockPickTime(), 25, "|", "&2", "&7");
            ChatController.sendAlert(player, progressBar + " &r(" + timeRemaining + "s)");
            if(delay > timeout) {
                rPlayer.sendAlert("Lock picking interrupted. (" + delay + "ms)");
                door.finishLockPicking(playerID);
                return;
                // Player's last interaction was too long ago or this is their first interaction
            } else if(curTime > lockStat.getFinishTime()) {
                // Lockpicking has finished
                rPlayer.sendAlert("Door picked open.");
                door.playSound(door.getUnlockSound(), door.getVolume(), 1f);
                door.finishLockPicking(playerID); // remove the lockpicker for this player
                door.setLocked(false);
                lockpicking.remove(door.getLocation());
                addCooldown(playerID);
                return;
            }
            // Lockpicking is in progress and has not been interrupted
            // Do something here, such as sending a message to the player or unlocking the door
            lockStat.tick();
            door.setLockPickStat(playerID, lockStat);
        }
    }

}
