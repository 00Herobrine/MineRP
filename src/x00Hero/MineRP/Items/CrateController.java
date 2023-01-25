package x00Hero.MineRP.Items;

import java.util.HashMap;
import org.bukkit.Location;

public class CrateController {
    private static HashMap<Location, WeaponCrate> weaponCrates = new HashMap<>();

    public static WeaponCrate getCrate(Location location) {
        return weaponCrates.get(location);
    }
    public static void addCrate(WeaponCrate crate) {
        weaponCrates.put(crate.getLocation(), crate);
    }
    public static void removeCrate(WeaponCrate crate) {
        weaponCrates.remove(crate.getLocation());
    }
}
