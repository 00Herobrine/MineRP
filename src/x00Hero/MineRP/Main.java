package x00Hero.MineRP;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import x00Hero.MineRP.Player.RPlayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Plugin plugin;
    private static HashMap<UUID, RPlayer> players = new HashMap<>();

    public void onEnable() {
        plugin = this;
    }

    public static ArrayList<RPlayer> getRPlayers() {
        return (ArrayList<RPlayer>) players.values();
    }

    public static HashMap<UUID, RPlayer> getPlayers() {
        return players;
    }

    public RPlayer getRPlayer(Player player) {
        return getRPlayer(player.getUniqueId());
    }

    public RPlayer getRPlayer(UUID uuid) {
        return players.get(uuid);
    }

    public static void addRPlayer(RPlayer rPlayer) {
        players.put(rPlayer.getPlayer().getUniqueId(), rPlayer);
    }

    public void cunt() {

    }
}
