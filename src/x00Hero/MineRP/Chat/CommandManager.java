package x00Hero.MineRP.Chat;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandManager implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String message, String[] args) {
        Player player = (Player) sender;
        switch(command.getLabel().toLowerCase()) {
            case "jobs":
                break;
            case "menu":
                break;
            case "printers":
                break;
        }
        return false;
    }

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
