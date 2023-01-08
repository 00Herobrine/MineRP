package x00Hero.MineRP.Jobs;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;

public class Job {
    private boolean isDefault = false;
    private boolean lostOnDeath = false;
    private boolean voteRequired = false;
    private String ID, name, title, description;
    private Material material;
    private long wage;
    private int interval, max;
    private ArrayList<JobItem> jobItems = new ArrayList<>();

    public Job(String jobID) {
        ID = jobID;
        name = "Citizen";
        title = "Cunt";
        description = "Test Description";
        material = Material.PAPER;
        wage = 10;
        interval = 600;
        max = -1; // means uncapped
    }

    public void setWage(long wage) {
        this.wage = wage;
    }
    public long getWage() {
        return wage;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }
    public int getInterval() {
        return interval;
    }

    public void setMax(int max) {
        this.max = max;
    }
    public int getMax() {
        return max;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }
    public Material getMaterial() {
        return material;
    }

    public boolean isLostOnDeath() {
        return lostOnDeath;
    }
    public void setLostOnDeath(boolean lostOnDeath) {
        this.lostOnDeath = lostOnDeath;
    }

    public void setJobItems(ArrayList<JobItem> jobItems) {
        this.jobItems = jobItems;
    }
    public ArrayList<JobItem> getJobItems() {
        return jobItems;
    }

    public boolean isDefault() {
        return isDefault;
    }
    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
    }

    public boolean isVoteRequired() {
        return voteRequired;
    }
    public void setVoteRequired(boolean required) {
        this.voteRequired = required;
    }

    public String getID() {
        return ID;
    }

    public boolean isLimited() {
        return max != -1;
    }

    public void loadConfig() {
        YamlConfiguration configFile = JobController.getJobsConfig();
        ConfigurationSection config = configFile.getConfigurationSection("jobs." + getID());
        Material material = Material.valueOf(config.getString("material"));
        setMaterial(material);
        setTitle(config.getString("title"));
        setName(config.getString("name"));
        setDescription(config.getString("description"));
        setMax(config.getInt("max"));
        setWage(config.getInt("wage"));
    }
}
