package x00Hero.MineRP.Jobs;

import org.bukkit.Material;

import java.util.ArrayList;

public class Job {
    private boolean isDefault = false;
    private boolean lostOnDeath = false;
    private boolean voteRequired = false;
    private String jobID, jobName, jobTitle, jobDescription;
    private Material jobMaterial;
    private long wage;
    private int interval, max;
    private ArrayList<JobItem> jobItems = new ArrayList<>();

    public Job(String jobID) {
        this.jobID = jobID;
        jobName = "Citizen";
        jobTitle = "Cunt";
        jobDescription = "Test Description";
        jobMaterial = Material.PAPER;
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

    public void setName(String jobName) {
        this.jobName = jobName;
    }
    public String getName() {
        return jobName;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobMaterial(Material jobMaterial) {
        this.jobMaterial = jobMaterial;
    }
    public Material getJobMaterial() {
        return jobMaterial;
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

    public String getJobID() {
        return jobID;
    }

    public boolean isLimited() {
        return max != -1;
    }
}
