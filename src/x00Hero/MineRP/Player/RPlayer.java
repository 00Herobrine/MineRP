package x00Hero.MineRP.Player;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Chat.ChatController;
import x00Hero.MineRP.Events.Constructors.Player.PlayerChangeJobEvent;
import x00Hero.MineRP.Items.MoneyPrinters.PrinterController;
import x00Hero.MineRP.Jobs.Job;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Jobs.JobItem;
import x00Hero.MineRP.Chat.TimedAlert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static x00Hero.MineRP.Main.playersFolder;
import static x00Hero.MineRP.Main.plugin;

public class RPlayer {
    private final Player player;
    private long cash = 0;
    private long bank = 0;
    private boolean wanted = false;
    private Job job;
    private long PayCheckTime;
    private TimedAlert currentAlert;
    private ArrayList<TimedAlert> timedAlerts = new ArrayList<>();
    private File playerFile;

    public RPlayer(Player player) {
        this.player = player;
        playerFile = new File(plugin.getDataFolder() + "/players/" + player.getUniqueId() + ".yml");
    }

    public Player getPlayer() {
        return player;
    }

    public void sendMessage(String message) {
//        player.sendMessage(EFM.Prefix + ChatManager.translateHexColorCodes(message));
        ChatController.sendMessage(player, message);
    }

    public void sendMessage(String message, Sound sound, float volume, float speed) {
        ChatController.sendMessage(player, message, sound, volume, speed);
    }

    public void sendMessage(String message, Sound sound) {
        ChatController.sendMessage(player, message, sound);
    }

    public File getPlayerFile() {
        return playerFile;
    }
    public void playerFileCheck() {
        try {
            if(!playersFolder.exists()) playersFolder.mkdir();
            if(!playerFile.exists()) playerFile.createNewFile();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    public void loadPlayerFile() {
        playerFileCheck();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getPlayerFile());
        cash = config.getLong("cash");
        bank = config.getLong("bank");
    }
    public void savePlayerFile() {
        playerFileCheck();
        YamlConfiguration config = YamlConfiguration.loadConfiguration(getPlayerFile());
        config.set("cash", cash);
        config.set("bank", bank);
        try {
            config.save(playerFile);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

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

    public boolean isWanted() {
        return wanted;
    }
    public void setWanted(boolean wanted) {
        this.wanted = wanted;
    }
    //endregion

    //region Alerts
    public void addAlert(TimedAlert alert) {
        ArrayList<TimedAlert> alerts = getTimedAlerts();
        if(alerts.size() == 0 && currentAlert == null) {
//            currentAlert = alert;
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
    //endregion


    //region Cash Stuff
    public void setCash(long cash) {
        this.cash = cash;
        savePlayerFile();
    }
    public void addCash(long amount) {
        addCash(amount, null);
    }
    public void addCash(long amount, String message) {
        cash += amount;
        if(message != null) {
            if(message.equalsIgnoreCase("")) message = "Received an influx of $" + amount + ".";
            sendMessage(message);
        }
        savePlayerFile();
    }
    public void removeCash(long amount) {
        cash -= amount;
    }
    public long getCash() {
        return cash;
    }
    public void attemptBankWithdraw(long amount) {
        if(amount <= 0) {
            sendMessage("How about you go get some bitches");
            return;
        }
        if(amount > bank) amount = bank;
        removeBank(amount);
        addCash(amount);
    }
    public void attemptBankDeposit(long amount) {
        if(cash <= 0) {
            sendMessage("How about you go get some bitches");
            return;
        }
        if(amount > cash) amount = cash;
        removeCash(amount);
        addBank(amount);
    }
    public void addBank(long amount) {
        bank += amount;
    }
    public void removeBank(long amount) {
        bank -= amount;
    }

    public boolean attemptPurchase(long price) {
        return attemptPurchase(price, null, false);
    }
    public boolean attemptPurchase(long price, boolean alert) {
        return attemptPurchase(price, null, alert);
    }
    public boolean attemptPurchase(long price, String item, boolean alert) {
        long newCash = cash - price;
        boolean status = !(newCash < 0);
        String msg = "&aPurchased &r" + item + "&a for &r$" + price;
        if(!status) msg = "&cLacking &r$" + Math.abs(newCash);
        else setCash(newCash);
        if(item != null)
            if(!alert) sendMessage(msg);
            else sendAlert(msg);
        return status;
    }

    public void soldItem(long amount, String item, boolean alert) {
        String msg = "&aSold &r" + item + "&a for &r$" + amount;
        addCash(amount);
        if(item != null)
            if(!alert) sendMessage(msg);
            else sendAlert(msg);
    }
    //endregion

    //region Job Stuff
    public void setDefaultItems() {
        HashMap<String, JobItem> defaults = JobController.getDefaultItems();
        for(JobItem items : defaults.values()) {
            int slot = items.getSlot();
            ItemStack itemStack = items.getItemStack();
            player.getInventory().setItem(slot, itemStack);
        }
    }

    public long getPayCheckTime() {
        return PayCheckTime;
    }
    public void setPayCheckTime(long payCheckTime) {
        PayCheckTime = payCheckTime;
    }
    public void updatePayCheckTime() {
        setPayCheckTime(System.currentTimeMillis() + (job.getInterval() * 1000L));
    }

    public Job getJob() {
        return job;
    }
    public void setJob(String jobName) {
        Job oldJob = getJob();
        Job job = JobController.getJob(jobName);
        if(job != null) {
            this.job = job;
            updatePayCheckTime();
            sendMessage("Job set to " + job.getName());
            Bukkit.getPluginManager().callEvent(new PlayerChangeJobEvent(this, oldJob, job));
        } else {
            sendMessage("Cannot set job to " + jobName);
        }
    }
    //endregion

    //region Menus
    public void openPrintersMenu() {
        PrinterController.printersMenu(player);
    }
    public void openJobsMenu() {
        JobController.JobMenu(player);
    }
    //endregion

}
