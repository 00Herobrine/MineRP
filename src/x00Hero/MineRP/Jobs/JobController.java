package x00Hero.MineRP.Jobs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.Events.Constructors.Player.PayCheckEvent;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;
import x00Hero.MineRP.GUI.Constructors.Menu;
import x00Hero.MineRP.GUI.Constructors.MenuItem;
import x00Hero.MineRP.GUI.MenuController;
import x00Hero.MineRP.Items.Generic.Lockpick;
import x00Hero.MineRP.Player.DoorController;
import x00Hero.MineRP.Player.RPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static x00Hero.MineRP.Main.*;

public class JobController implements Listener {
    private static HashMap<String, Job> jobs = new HashMap<>(); //jobID, Job
    private static File jobsFile = new File(plugin.getDataFolder(), "jobs.yml");
    private static HashMap<String, JobItem> defaultItems = new HashMap<>();
    private static HashMap<String, JobItem> cachedJobItems = new HashMap<>(); //itemID, JobItem

    public static void cacheJobs() {
        if(!jobsFile.exists()) plugin.saveResource("jobs.yml", false);
        YamlConfiguration jobsConfig = YamlConfiguration.loadConfiguration(jobsFile);
        plugin.getLogger().info(jobsFile.getAbsolutePath() + " exists " + jobsFile.exists());
        for(String jobID : jobsConfig.getConfigurationSection("jobs").getKeys(false)) {
            ConfigurationSection jobSettings = jobsConfig.getConfigurationSection("jobs." + jobID);
            Job job = new Job(jobID);
            job.addJobItem(Lockpick.lockpick);
            if(jobSettings.contains("wage")) job.setWage(jobSettings.getInt("wage"));
            if(jobSettings.contains("max")) job.setMax(jobSettings.getInt("max"));
            if(jobSettings.contains("material")) {
                Material material = Material.valueOf(jobSettings.getString("material"));
                job.setMaterial(material);
            }
            plugin.getLogger().info("Cached jobID " + jobID);
            jobs.put(jobID, job);
        }
        defaultItems.put("lockpick", Lockpick.lockpick);
        defaultItems.put("keyset", DoorController.keys);
        defaultItems.put("menubook", MenuController.jobBook);
    }

    public static void paycheckLoop() {
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

    @EventHandler
    public void PayCheck(PayCheckEvent e) {
        RPlayer rPlayer = e.getRPlayer();
        long time = System.currentTimeMillis() + (e.getDelay() * 1000L);
        rPlayer.setPayCheckTime(time);
    }

    public static HashMap<String, JobItem> getDefaultItems() {
        return defaultItems;
    }

    public static HashMap<String, Job> getJobs() {
        return jobs;
    }

    public static Job getJob(String name) {
        return jobs.get(name);
    }

    public static void JobMenu(Player player) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for(Job job :  getJobs().values()) {
            Material material = job.getMaterial();
            String name = job.getName();
            String description = job.getDescription();
            description = description.replace("{count}", "1").replace("{max}", job.getMax() + "");
            ItemBuilder itemBuilder = new ItemBuilder(material, name, description);
            MenuItem menuItem = new MenuItem(itemBuilder.getItemStack(), "menu-job-" + job.getID());
            menuItems.add(menuItem);
        }
        Menu jobMenu = new Menu(menuItems,"Available Jobs", true, true);
        jobMenu.openMenu(player);
    }

    public static boolean isJobItem(ItemStack item) {
        String tag = getTags(item);
        if(defaultItems.containsKey(tag)) return true;
        return cachedJobItems.containsKey(tag);
    }

    public static JobItem getJobItem(String itemID) {
        return cachedJobItems.get(itemID);
    }

    public static YamlConfiguration getJobsConfig() {
        return YamlConfiguration.loadConfiguration(jobsFile);
    }
}
