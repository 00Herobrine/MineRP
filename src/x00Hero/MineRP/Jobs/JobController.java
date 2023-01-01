package x00Hero.MineRP.Jobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import x00Hero.MineRP.Events.Constructors.PayCheckEvent;
import x00Hero.MineRP.Player.RPlayer;

import java.util.HashMap;

import static x00Hero.MineRP.Main.*;

public class JobController {
    private static HashMap<String, Job> jobs = new HashMap<>(); //jobID, Job

    public void cacheJobs() {
        for(String jobID : plugin.getConfig().getConfigurationSection("jobs").getKeys(false)) {
            ConfigurationSection jobSettings = plugin.getConfig().getConfigurationSection("jobs.config");
            Job job = new Job(jobID);
            if(jobSettings.contains("wage")) job.setWage(jobSettings.getInt("wage"));
            if(jobSettings.contains("max")) job.setMax(jobSettings.getInt("max"));
            if(jobSettings.contains("material")) {
                Material material = Material.getMaterial(jobSettings.getString("material"));
                job.setJobMaterial(material);
            }
        }
    }

    public void paycheckLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(RPlayer rPlayer : getRPlayers()) {
                if(System.currentTimeMillis() >= rPlayer.getPayCheckTime()) {
                    // give them paycheck
                    Job job = rPlayer.getJob();
                    long amount = job.getWage();
                    int delay = job.getInterval();
                    Bukkit.getPluginManager().callEvent(new PayCheckEvent(rPlayer, amount, delay));
                }
            }
        }, 20, 20);
    }

    public static Job getJob(String name) {
        return jobs.get(name);
    }
}
