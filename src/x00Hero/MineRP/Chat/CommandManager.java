package x00Hero.MineRP.Chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;
import x00Hero.MineRP.Items.MoneyPrinters.Hologram;
import x00Hero.MineRP.Items.MoneyPrinters.HologramController;
import x00Hero.MineRP.Items.MoneyPrinters.MoneyPrinter;
import x00Hero.MineRP.Items.MoneyPrinters.PrinterController;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Player.RPlayer;

import static x00Hero.MineRP.Main.*;

public class CommandManager implements CommandExecutor, Listener {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String cmdLabel, String[] args) {
        Player player = (Player) sender;
        RPlayer rPlayer = getRPlayer(player);
        String label = command.getLabel().toLowerCase();
        switch(label) {
            case "minerp":
                if(args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                    cacheConfigs();
                    rPlayer.sendMessage("Configs reloaded.");
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
                        rPlayer.sendMessage("Invalid arguments.");
                    }
                    break;
                }
                Location location = player.getLocation();
                Hologram hologram = new Hologram(location);
                for(String arg : args) {
                    hologram.addLine(arg);
                }
                HologramController.addHologram(hologram.create());
                break;
            case "balance":
                balance(rPlayer, args);
                break;
            case "advert":
            case "/":
                StringBuilder sb = new StringBuilder();
                String color = "&r";
                for(String arg : args) {
                    sb.append(arg).append(" ");
                }
                String prefix = "&7[&rOOC&7]&r ";
                if(label.equalsIgnoreCase("advert")) {
                    prefix = "&7[&6AD&7]&r ";
                    color = "&6";
                }
                String msg = prefix + player.getName() + ": " + color + sb;
                Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', msg));
                break;
            case "door":
                if(args.length < 2) {
                    rPlayer.sendMessage("Invalid arguments.");
                    break;
                }
                if(args[0].equalsIgnoreCase("create")) {
                    Material material = Material.OAK_DOOR;
                    if(args.length == 3) material = Material.getMaterial(args[2]);
                    if(material != null) {
                        ItemBuilder itemBuilder = new ItemBuilder(material, args[1], "Custom Door", "ownabledoor");
                        player.getInventory().addItem(itemBuilder.getItemStack());
                    } else {
                        rPlayer.sendMessage("Invalid material.");
                    }
                } else {
                    rPlayer.sendMessage("Invalid arguments");
                }
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

    public void balance(RPlayer rPlayer, String[] args) {
        if(args.length == 0) rPlayer.sendMessage("Your current balance is $" + rPlayer.getCash() + ".");
        else {
            if(args[0].equalsIgnoreCase("add")) {
                if(args.length < 2) {
                    rPlayer.sendMessage("Invalid arguments.");
                    return;
                }
                if(args[1].matches("[0-9]+")) {
                    int amount = Integer.parseInt(args[1]);
                    rPlayer.addCash(amount, "Added $" + amount + " to your balance.");
                } else {
                    Player target = Bukkit.getPlayer(args[1]);
                    if(target == null) {
                        rPlayer.sendMessage("Invalid player.");
                        return;
                    }
                    RPlayer tar = getRPlayer(target);
                    if(args[2].matches("[0-9]+")) {
                        int amount = Integer.parseInt(args[2]);
                        tar.addCash(amount, "");
                        rPlayer.sendMessage("Added $" + amount + " to " + target.getName() + ".");
                    } else {
                        rPlayer.sendMessage("Invalid amount.");
                    }
                }
            } else {
                Player target = Bukkit.getPlayer(args[0]);
                if(target != null) {
                    RPlayer tar = getRPlayer(target);
                    rPlayer.sendMessage(target.getName() + "'s current balance is $" + tar.getCash() + ".");
                } else {
                    rPlayer.sendMessage("Invalid arguments.");
                }
            }
        }
    }
}
