package x00Hero.MineRP.Items.MoneyPrinters;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class Hologram {

    private Location location;
    private ArmorStand displayHolder;
    private ArrayList<String> lines =  new ArrayList<>();
    private ArrayList<Player> viewers = new ArrayList<>();

    public Hologram(Location location, String text) {
        this.location = location;
        lines.add(text);
    }

    public void addLine(String text) {
        lines.add(text);
    }

    public void removeLine(int line) {
        lines.remove(line);
    }

    private void create() {
        for(String text : lines) {

        }
    }

    public void updateArmorStand() {

    }

}
