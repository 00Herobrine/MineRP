package x00Hero.MineRP.Items.MoneyPrinters;

import com.google.common.collect.Lists;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Hologram {

    private String name;
    private Location location;
    private ArrayList<ArmorStand> displays = new ArrayList<>();
    private ArrayList<String> lines =  new ArrayList<>();
    private ArrayList<Player> viewers = new ArrayList<>();
    private int visibleRange = 3;

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

    public Hologram create() {
        Location holoLoc = new Location(location.getWorld(), location.getX() + 0.5, location.getY() - 1.5, location.getZ() + 0.5);
        for(String line : Lists.reverse(lines)) {
            ArmorStand entity = (ArmorStand) holoLoc.getWorld().spawnEntity(holoLoc, EntityType.ARMOR_STAND);
            entity.setCustomName(line);
            entity.setCustomNameVisible(true);
            entity.setVisible(false);
            entity.setGravity(false);
            entity.setBasePlate(false);
            displays.add(entity);
            holoLoc.add(0.0D, 0.25D, 0.0D);
        }
//        location = displays.get(0).getLocation();
        return this;
    }

    public void updateArmorStand() {

    }

    public Location getLocation() {
        return location;
    }

    public int getVisibleRange() {
        return visibleRange;
    }

    public void hide(Player player) {
        for(ArmorStand stand : displays) {
            viewers.remove(player);
            stand.setCustomNameVisible(false);
        }
    }

    public void show(Player player) {
        for(ArmorStand stand : displays) {
            viewers.add(player);
            stand.setCustomNameVisible(true);
        }
    }

    public void remove() {
        for(Entity entity : displays) {
            entity.remove();
        }
    }

    public boolean isViewing(Player player) {
        return viewers.contains(player);
    }
}
