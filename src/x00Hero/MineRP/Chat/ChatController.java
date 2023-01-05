package x00Hero.MineRP.Chat;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.RPlayer;
import x00Hero.MineRP.TimedAlert;

import java.util.ArrayList;

import static x00Hero.MineRP.Main.plugin;

public class ChatController {



    public static void alertLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(RPlayer rPlayer : Main.getRPlayers()) {
                ArrayList<TimedAlert> alerts = rPlayer.getTimedAlerts();
                TimedAlert currentAlert = rPlayer.getCurrentAlert();
                if(alerts.size() > 0) {
                    if(currentAlert == null) currentAlert = alerts.get(0);
                    if(currentAlert.getTimeElapsed() >= currentAlert.getLength()) {
                        alerts.remove(currentAlert);
                        rPlayer.setCurrentAlert(null);
                    } else {
                        rPlayer.sendAlert(currentAlert.getMessage());
                        currentAlert.tick();
                    }
                }
            }
        }, 20, 20);
    }

    public static void addAlert(RPlayer rPlayer, TimedAlert alert) {
        ArrayList<TimedAlert> alerts = rPlayer.getTimedAlerts();
        for (int i = 0; i < alerts.size(); i++) {
            if (alerts.get(i).equals(alert)) {
                alerts.remove(i);
                break;
            }
        }
        alerts.add(alert);
    }

    public static TextComponent getComponent(String message) {
        TextComponent msg = new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
        return msg;
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
        player.sendMessage(message);
    }

    public static void sendMessage(Player player, int id) {

    }

}
