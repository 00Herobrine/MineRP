package x00Hero.MineRP.Items.Drugs;

import org.bukkit.Material;

public class GrowthStage {
    private int length;
    private Material material;

    public GrowthStage(int length, Material material) {
        this.length = length;
        this.material = material;
    }

    public int getLength() {
        return length;
    }

    public Material getMaterial() {
        return material;
    }
}
