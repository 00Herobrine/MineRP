package x00Hero.MineRP.Player;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import x00Hero.MineRP.Jobs.Job;
import x00Hero.MineRP.Jobs.JobController;

public class RPlayer {

    public RPlayer(Player player) {
        this.player = player;
    }

    private long cash = 0;
    private boolean wanted;
    private Job job;
    private long PayCheckTime;
    private Player player;

    //region Jail Stuff
    private boolean inJail = false;
    private long releaseTime;

    public boolean isInJail() {
        return inJail;
    }

    public void setInJail(boolean inJail) {
        this.inJail = inJail;
    }

    public long getReleaseTime() {
        return releaseTime;
    }
    //endregion
    public boolean isWanted() {
        return wanted;
    }

    public void addCash(long amount) {
        cash += amount;
    }

    public long getCash() {
        return cash;
    }

    public long getPayCheckTime() {
        return PayCheckTime;
    }

    public Player getPlayer() {
        return player;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(String jobName) {
        this.job = JobController.getJob(jobName);
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
//        player.sendMessage(EFM.Prefix + ChatManager.translateHexColorCodes(message));
        player.sendMessage(message);
    }

}
