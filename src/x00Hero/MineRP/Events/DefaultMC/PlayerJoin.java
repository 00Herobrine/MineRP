package x00Hero.MineRP.Events.DefaultMC;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import x00Hero.MineRP.Player.RPlayer;

public class PlayerJoin implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        RPlayer rPlayer = new RPlayer(player);
        rPlayer.setJob("mayor");
    }
}
