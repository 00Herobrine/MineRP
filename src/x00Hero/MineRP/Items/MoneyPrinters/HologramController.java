package x00Hero.MineRP.Items.MoneyPrinters;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static x00Hero.MineRP.Main.plugin;

public class HologramController {
    private static ArrayList<Hologram> hologramsList = new ArrayList<>();
    private static long interval = 20;

    public static void loadConfig() {
        interval = plugin.getConfig().getLong("hologram-tick");
    }

    public static void visibilityLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(Player player : Bukkit.getOnlinePlayers()) {
                for(Hologram hologram : hologramsList) {
                    Location playerLoc = player.getLocation();
                    Location holoLoc = hologram.getLocation();
                    double distance = playerLoc.distance(holoLoc);
                    if(distance > hologram.getVisibleRange()) hologram.hide(player);
                    else if(!hologram.isViewing(player)) hologram.show(player);
                }
            }
        }, 0, interval);
    }

    public static void addHologram(Hologram hologram) {
        hologramsList.add(hologram);
    }

    public static void removeHologram(Hologram hologram) {
        hologramsList.remove(hologram);
    }

}
