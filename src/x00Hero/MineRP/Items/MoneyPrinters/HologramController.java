package x00Hero.MineRP.Items.MoneyPrinters;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

import static x00Hero.MineRP.Main.plugin;

public class HologramController {
    private static HashMap<Location, Hologram> holograms = new HashMap<>();

    public static void visibilityLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                for(Hologram hologram : holograms.values()) {
                    Location playerLoc = player.getLocation();
                    Location holoLoc = hologram.getLocation();
                    double distance = playerLoc.distance(holoLoc);
                    if(distance > hologram.getVisibleRange()) hologram.hide(player);
                    else if(!hologram.isViewing(player)) hologram.show(player);
                }
            }
        }, 20, 20);
    }

    public static void addHologram(Hologram hologram) {
        holograms.put(hologram.getLocation(), hologram);
    }

    public static void removeHologram(Hologram hologram) {
        holograms.remove(hologram.getLocation());
    }

    public static Hologram getHologram(Location location) {
        return holograms.get(location);
    }

}
