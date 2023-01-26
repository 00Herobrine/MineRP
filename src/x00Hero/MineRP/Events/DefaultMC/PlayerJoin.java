package x00Hero.MineRP.Events.DefaultMC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import x00Hero.MineRP.Main;
import x00Hero.MineRP.Player.RPlayer;

import static x00Hero.MineRP.Main.getRPlayer;

public class PlayerJoin implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        Main.createRPlayer(player);
    }

    @EventHandler
    public void playerLeave(PlayerQuitEvent e) {
        RPlayer rPlayer = getRPlayer(e.getPlayer());
        rPlayer.savePlayerFile();
    }
}
