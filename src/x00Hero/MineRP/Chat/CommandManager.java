package x00Hero.MineRP.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import x00Hero.MineRP.Jobs.JobController;

public class CommandManager implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmdLabel, String[] args) {
        Player player = (Player) sender;
        String label = command.getLabel().toLowerCase();
        switch(label) {
            case "jobs":
                JobController.JobMenu(player);
                break;
            case "menu":
                break;
            case "printers":
                break;
            case "advert":
            case "/":
                StringBuilder sb = new StringBuilder();
                String color = "&r";
                for(String arg : args) {
                    sb.append(arg + " ");
                }
                String prefix = "&7[&rOOC&7]&r ";
                if(label.equalsIgnoreCase("advert")) {
                    prefix = "&7[&6AD&7]&r ";
                    color = "&6";
                }
                String msg = prefix + player.getName() + ": " + color + sb;
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&' , msg));
                break;
        }
        return false;
    }

    @EventHandler
    public void commandPreProcess(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String msg = e.getMessage();
        String[] args = msg.split(" ");
        String cmd = args[0];
        if(!p.hasPermission("efm.admin")) {
            if(cmd.equalsIgnoreCase("/pl") || cmd.startsWith("/plugin")) { // remove from tab list as well
                e.setCancelled(true);
            }
        }
    }
}
