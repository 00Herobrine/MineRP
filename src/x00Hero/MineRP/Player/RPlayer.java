package x00Hero.MineRP.Player;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Chat.ChatController;
import x00Hero.MineRP.Events.Constructors.Player.PlayerChangeJobEvent;
import x00Hero.MineRP.Jobs.Job;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Chat.TimedAlert;

import java.util.ArrayList;

public class RPlayer {

    public RPlayer(Player player) {
        this.player = player;
    }

    private final Player player;
    private long cash = 0;
    private boolean wanted = false;
    private Job job;
    private long PayCheckTime;
    private TimedAlert currentAlert;
    private ArrayList<TimedAlert> timedAlerts = new ArrayList<>();

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

    public void setReleaseTime(long releaseTime) {
        this.releaseTime = releaseTime;
    }

    //endregion
    public boolean isWanted() {
        return wanted;
    }

    public void setWanted(boolean wanted) {
        this.wanted = wanted;
    }

    public void addCash(long amount) {
        cash += amount;
    }

    public void removeCash(long amount) {
        cash -= amount;
    }

    public long getCash() {
        return cash;
    }

    public long getPayCheckTime() {
        return PayCheckTime;
    }

    public void setPayCheckTime(long payCheckTime) {
        PayCheckTime = payCheckTime;
    }

    public Player getPlayer() {
        return player;
    }

    public Job getJob() {
        return job;
    }

    public void setJob(String jobName) {
        Job oldJob = getJob();
        Job job = JobController.getJob(jobName);
        if(job != null) {
            this.job = job;
            long nextPayCheck = System.currentTimeMillis() + (job.getInterval() * 1000L);
            setPayCheckTime(nextPayCheck);
            sendMessage("Job set to " + job.getName());
            Bukkit.getPluginManager().callEvent(new PlayerChangeJobEvent(this, oldJob, job));
        } else {
            sendMessage("Cannot set job to " + jobName);
        }
    }

    public void addAlert(TimedAlert alert) {
        ArrayList<TimedAlert> alerts = getTimedAlerts();
        if(alerts.size() == 0) {
            if(alert.getSound() != null) {
                ChatController.sendAlert(player, alert.getMessage(), alert.getSound());
            } else {
                ChatController.sendAlert(player, alert.getMessage());
            }
            return;
        }
        for(int i = 0; i < alerts.size(); i++) {
            if(alerts.get(i).equals(alert)) {
                alerts.remove(i);
                break;
            }
        }
        alerts.add(alert);
    }

    public TimedAlert getCurrentAlert() {
        return currentAlert;
    }

    public void setCurrentAlert(TimedAlert currentAlert) {
        this.currentAlert = currentAlert;
    }

    public ArrayList<TimedAlert> getTimedAlerts() {
        return timedAlerts;
    }

    public void sendAlert(String message) {
        TimedAlert timedAlert = new TimedAlert(message, 3);
        addAlert(timedAlert);
//        ChatController.sendAlert(player, message);
    }

    public void sendAlert(String message, Sound sound) {
        TimedAlert alert = new TimedAlert(message, 3);
        alert.setSound(sound);
        addAlert(alert);
    }

    public void sendAlert(String message, Sound sound, float loudness, float speed) {
        TimedAlert alert = new TimedAlert(message, 3);
        alert.setSound(sound);
        alert.setLoudness(loudness);
        alert.setSpeed(speed);
        addAlert(alert);
    }

    public void sendMessage(String message) {
//        player.sendMessage(EFM.Prefix + ChatManager.translateHexColorCodes(message));
        ChatController.sendMessage(player, message);
    }

    public void setDefaultItems() {
        ArrayList<JobItem> defaults = JobController.getDefaultItems();
        for(JobItem items : defaults) {
            int slot = items.getSlot();
            ItemStack itemStack = items.getItemStack();
            player.getInventory().setItem(slot, itemStack);
        }
    }

    public void openJobsMenu() {
        JobController.JobMenu(player);
    }

}
