package x00Hero.MineRP.Items.MoneyPrinters;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Hologram {

    private String name;
    private Location location;
    private ArrayList<ArmorStand> displays = new ArrayList<>();
    private ArrayList<String> lines =  new ArrayList<>();
    private ArrayList<Player> viewers = new ArrayList<>();

    public Hologram(String name, Location location) {
        this.name = name;
        this.location = location;
    }

    public String getText(int line) {
        return lines.get(line);
    }

    public void setLine(int line, String text) {
        lines.set(line, text);
    }

    public void addLine(String text) {
        lines.add(text);
    }

    public void removeLine(int line) {
        lines.remove(line);
    }

    private Hologram create() {
        for(String line : Lists.reverse(lines)) {
            ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
            entity.setCustomName(line);
            entity.setCustomNameVisible(true);
            entity.setVisible(false);
            entity.setGravity(false);
            entity.setBasePlate(false);
            location.add(0.0D, 0.25D, 0.0D);
        }
        return this;
    }

    public void updateArmorStand() {

    }

}
