package x00Hero.MineRP.Items.Generic;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Chat.ChatController;
import x00Hero.MineRP.Events.Constructors.Player.LockPickDoorEvent;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.RPlayer;

import java.util.ArrayList;
import java.util.UUID;

import static x00Hero.MineRP.Main.plugin;

public class Lockpick {
    private static ItemBuilder lockpickBuilder = new ItemBuilder(Material.STICK, "Lockpick", "A universal key if you know how to use it.", "lockpick");
    private static ItemStack lockpickItem = lockpickBuilder.getItemStack();
    public static JobItem lockpick = new JobItem(lockpickItem, 6);
    private static ArrayList<OwnableDoor> lockpicking = new ArrayList<>(); // doors being lockpicked
    private static int timeout = 250;

    public String getProgressBar(int current, int max, int totalBars, String symbol, String completedColor, String notCompletedColor) {
        float percent = (float) current / max;
        int progressBars = (int) (totalBars * percent);
        int leftOver = (totalBars - progressBars);
        StringBuilder sb = new StringBuilder();
        sb.append(ChatColor.translateAlternateColorCodes('&', completedColor));
        for(int i = 0; i < progressBars; i++) {
            sb.append(symbol);
        }
        sb.append(ChatColor.translateAlternateColorCodes('&', notCompletedColor));
        for(int i = 0; i < leftOver; i++) {
            sb.append(symbol);
        }
        return sb.toString();
    }

/*
    @EventHandler
    public void LockPickEvent(LockPickDoorEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        Player player = rPlayer.getPlayer();
        UUID uuid = player.getUniqueId();
        OwnableDoor door = e.getDoor();
        PlayerInteractEvent interactEvent = e.getInteractEvent();

        interactEvent.setCancelled(true);
        if(!door.isLockPicking(uuid)) {
            door.startLockPicking(uuid);
            lockpicking.add(door);
        }
        int progress = door.getLockPickTime(uuid);
        int max = door.getDefaultLockPickTime();
        String progressBar = getProgressBar(progress, max, 25, "|", "&2", "&7");
        ChatController.sendAlert(player, progressBar);
    }
*/

    public static void lockpickLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(OwnableDoor lockpicked : lockpicking) {
                for(UUID picker : lockpicked.getLockpickers().keySet()) {
                    LockPickStat lockStat = lockpicked.getLockStat(picker);
                    long curTime = System.currentTimeMillis();
                    if(curTime - lockStat.getLastPicked() >= timeout) {
                        Main.getRPlayer(picker).sendAlert("Lock picking interrupted");
                    }
                }
            }
        }, 20, 20);
    }


    @EventHandler
    public void onLockPickEvent(LockPickDoorEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        Player player = rPlayer.getPlayer();
        UUID playerUUID = player.getUniqueId();
        OwnableDoor door = e.getDoor();
        LockPickStat lockStat = door.getLockPickStat(playerUUID);
        if(lockStat == null) {
            // Lockpicking has not started yet
            door.startLockPicking(playerUUID);
//            return;
        }

        long lastInteractTime = lockStat.getLastPicked();
        if(System.currentTimeMillis() - lastInteractTime > timeout) {
            // Player's last interaction was too long ago or this is their first interaction
            return;
        }

        long finishTime = lockStat.getFinish();
        if(System.currentTimeMillis() > finishTime) {
            // Lockpicking has finished
            door.finishLockPicking(playerUUID); // remove the lockpicker for this player
            return;
        }
        int progress = (int) (Math.abs(System.currentTimeMillis() - finishTime) / 1000);
        Bukkit.broadcastMessage("Progress " + progress);
        String progressBar = getProgressBar(progress, 100, 25, "|", "&2", "&7");
        ChatController.sendAlert(player, progressBar);
        // Lockpicking is in progress and has not been interrupted
        // Do something here, such as sending a message to the player or unlocking the door
    }

}
