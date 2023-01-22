package x00Hero.MineRP.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import x00Hero.MineRP.Items.MoneyPrinters.Hologram;
import x00Hero.MineRP.Items.MoneyPrinters.HologramController;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;
import x00Hero.MineRP.Items.MoneyPrinters.PrinterController;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Player.RPlayer;

import static x00Hero.MineRP.Main.cacheConfigs;
import static x00Hero.MineRP.Main.getRPlayer;

public class CommandManager implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmdLabel, String[] args) {
        Player player = (Player) sender;
        RPlayer rPlayer = getRPlayer(player);
        String label = command.getLabel().toLowerCase();
        switch(label) {
            case "minerp":
                if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                    rPlayer.sendMessage("Configs reloaded.");
                    cacheConfigs();
                }
                break;
            case "jobs":
                JobController.JobMenu(player);
                break;
            case "menu":
                break;
            case "printers":
                if(args.length >= 1 && args[0].equalsIgnoreCase("add")) {
                    if(args.length > 1) {
                        MoneyPrinter printer = PrinterController.getCachedPrinter(args[1]);
                        if(printer != null) player.getInventory().addItem(printer.getItemStack());
                        else rPlayer.sendMessage("Cannot find printer " + args[1]);
                    } else {
                        rPlayer.sendMessage("Invalid arguments");
                    }
                    break;
                }
                Location location = player.getLocation();
                Hologram hologram = new Hologram("command", location);
                for(String arg : args) {
                    hologram.addLine(arg);
                }
                HologramController.addHologram(hologram.create());
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
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
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
