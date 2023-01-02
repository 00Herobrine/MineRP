package x00Hero.MineRP.Items.Drugs;

import java.util.HashMap;

public class Growable {
    private int currentStage = 1;
    private HashMap<Integer, GrowthStage> growthStages = new HashMap<>();
    private long nextStageTime;

    public Growable() {

    }

    public int getCurrentStage() {
        return currentStage;
    }
    public void setCurrentStage(int currentStage) {
        this.currentStage = currentStage;
    }

    public HashMap<Integer, GrowthStage> getGrowthStages() {
        return growthStages;
    }
    public void setGrowthStages(HashMap<Integer, GrowthStage> growthStages) {
        this.growthStages = growthStages;
    }
    public void setGrowthStage(Integer stage, GrowthStage growthStage) {
        growthStages.put(stage, growthStage);
    }

    public long getNextStageTime() {
        return nextStageTime;
    }
    public void setNextStageTime(long nextStageTime) {
        this.nextStageTime = nextStageTime;
    }
}
