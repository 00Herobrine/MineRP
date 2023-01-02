package x00Hero.MineRP.Chat;

import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandManager implements Listener {

    public void commandPreProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        String cmd = args[0];
/*        if(cmd.equalsIgnoreCase("/clear")) {
            e.setCancelled(true);
            p.getInventory().clear();
            return;
        }*/
        if(!p.hasPermission("efm.admin")) {
            if(cmd.equalsIgnoreCase("/pl") || cmd.startsWith("/plugin")) { // remove from tab list as well
                e.setCancelled(true);
            }
        }
    }
}
