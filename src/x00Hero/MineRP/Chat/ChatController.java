package x00Hero.MineRP.Chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.RPlayer;

import java.util.ArrayList;

import static x00Hero.MineRP.Main.getRPlayer;
import static x00Hero.MineRP.Main.plugin;

public class ChatController implements Listener {

    private static String prefix;

    public static void cacheMessages() {
        prefix = plugin.getConfig().getString("prefix");
    }

    public static void alertLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(RPlayer rPlayer : Main.getRPlayers()) {
                ArrayList<TimedAlert> alerts = rPlayer.getTimedAlerts();
//                if(rPlayer.getCurrentAlert() == null) rPlayer.setCurrentAlert(alerts.get(0));
                TimedAlert currentAlert = rPlayer.getCurrentAlert();
                if(alerts.size() > 0) {
                    if(currentAlert == null) {
                        TimedAlert firstAlert = alerts.get(0);
                        alerts.remove(firstAlert);
                        currentAlert = firstAlert;
                        rPlayer.setCurrentAlert(firstAlert);
                    }
                    if(currentAlert.getTimeElapsed() >= currentAlert.getLength()) {
//                        alerts.remove(currentAlert);
                        rPlayer.setCurrentAlert(null);
                    } else {
                        rPlayer.sendAlert(currentAlert.getMessage());
                        currentAlert.tick();
                    }
                }
            }
        }, 20, 20);
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();
        Player player = e.getPlayer();
        RPlayer rPlayer = getRPlayer(player);
        e.setCancelled(true);
        int distance = 7;
        if(message.startsWith("!")) {
            message = message.replaceFirst("!", "");
            distance = 15;
        }
        for(Player recipient : e.getRecipients()) {
            if(recipient.getLocation().distance(player.getLocation()) <= distance) {
                String jobTitle = ChatColor.translateAlternateColorCodes('&', rPlayer.getJob().getTitle() + "&r ");
                recipient.sendMessage(jobTitle + player.getName() + ": " + message);
            }
        }
    }

    public static TextComponent getComponent(String message) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void sendAlert(Player player, String message) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, getComponent(message));
    }

    public static void sendAlert(Player player, String message, Sound sound, float loudness, float speed) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, getComponent(message));
        player.playSound(player.getLocation(), sound, loudness, speed);
    }

    public static void sendAlert(Player player, String message, Sound sound) {
        sendAlert(player, message, sound, 1F, 1F);
    }

    public static void sendMessage(Player player, String message) {
        sendMessage(player, message, null, 1f, 1f);
    }

    public static void sendMessage(Player player, String message, Sound sound) {
        sendMessage(player, message, sound, 1f, 1f);
    }

    public static void sendMessage(Player player, String message, Sound sound, float volume, float speed) {
        String msg = prefix + message;
        if(sound != null) player.playSound(player.getLocation(), sound, volume, speed);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    public static void sendMessage(Player player, int id) { // lang file shit

    }

}
