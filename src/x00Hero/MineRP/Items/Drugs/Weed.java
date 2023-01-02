package x00Hero.MineRP.Items.Drugs;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

import static x00Hero.MineRP.Main.plugin;

public class Weed extends Growable {

    public void cacheGrowthStages() {
        File file = new File(plugin.getDataFolder() + "drugs.yml");
        YamlConfiguration drugs = YamlConfiguration.loadConfiguration(file);
        ConfigurationSection weedSection = drugs.getConfigurationSection("weed");
        ConfigurationSection stages = weedSection.getConfigurationSection("stages");
        int curStage = 1;
        for(String stage : stages.getKeys(false)) {
            ConfigurationSection stageSection = stages.getConfigurationSection(stage);
            int length = stageSection.getInt("length");
            Material material = Material.valueOf(stageSection.getString("material"));
            GrowthStage growthStage = new GrowthStage(length, material);
            setGrowthStage(curStage, growthStage);
        }
    }

    public Weed() {

    }
}
