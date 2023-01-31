package x00Hero.MineRP.Items.MoneyPrinters;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterCreateEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterDestroyedEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterPrintEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterTickEvent;
import x00Hero.MineRP.GUI.Constructors.Menu;
import x00Hero.MineRP.GUI.Constructors.MenuItem;
import x00Hero.MineRP.Player.RPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static x00Hero.MineRP.Main.debug;
import static x00Hero.MineRP.Main.plugin;

public class PrinterController implements Listener {
    private static File printersFile = new File(plugin.getDataFolder(), "printers.yml");
    private static HashMap<String, MoneyPrinter> cachedPrinters = new HashMap<>(); // printerID, printer
    private static HashMap<Location, MoneyPrinter> moneyPrinters = new HashMap<>(); // player owned printers (might need a stored UUID for each printer)
    private static Menu printersMenu = new Menu("Printers");

    public static void cachePrinters() {
        if(!printersFile.exists()) plugin.saveResource("printers.yml", false);
        YamlConfiguration printers = YamlConfiguration.loadConfiguration(printersFile);
        for(String printerID : printers.getConfigurationSection("printers").getKeys(false)) {
            MoneyPrinter printer = new MoneyPrinter(printerID);
            ConfigurationSection printerConfig = printers.getConfigurationSection("printers." + printerID);
            assert printerConfig != null;
            cachedPrinters.put(printerID, printer);
            debug("cached " + printerID);
        }
    }

    public static void printerLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(MoneyPrinter printer : moneyPrinters.values()) {
                Bukkit.getPluginManager().callEvent(new PrinterTickEvent(printer));
            }
        }, 20, 20);
    }

    public static MoneyPrinter getPrinter(Location location) {
        return moneyPrinters.get(location);
    }

    public static MoneyPrinter getCachedPrinter(String printerID) {
        return cachedPrinters.get(printerID);
    }

    @EventHandler
    public void printerTick(PrinterTickEvent e) {
        MoneyPrinter printer = e.getPrinter();
        if(printer.isEnabled()) {
//            printer.tick();
            int printTime = printer.getPrintTime();
            printTime--;
            if(printTime <= 0) {
                Bukkit.getPluginManager().callEvent(new PrinterPrintEvent(printer, printer.generateCash()));
                printer.drainBattery(1);
                printTime = printer.getInterval();
            }
            printer.setPrintTime(printTime);
        }
    }

    @EventHandler
    public void printerCreate(PrinterCreateEvent e) {
        MoneyPrinter printer = new MoneyPrinter();
        printer.copyDefaults(e.getPrinter());
        Location location = e.getLocation();
//        Bukkit.broadcastMessage("printer location set to " + location);
        printer.setLocation(location);
        printer.createHologram();
        addPrinter(printer);
    }

    @EventHandler
    public void printerDestroyed(PrinterDestroyedEvent e) {
        MoneyPrinter printer = e.getPrinter();
        RPlayer rPlayer = e.getDestroyer();
        printer.destroyHologram();
        if(printer.hasAlert()) rPlayer.sendMessage("Your printer has been " + e.getDestructionMethod() + ".");
        removePrinter(printer);
    }

    public static void printersMenu(Player player) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        if(!printersMenu.isCached()) {
            for(String printerID : cachedPrinters.keySet()) {
                Bukkit.broadcastMessage("getting printer " + printerID);
                MoneyPrinter printer = cachedPrinters.get(printerID);
                printer.load();
                MenuItem menuItem = new MenuItem(printer.getItemStack(), printerID);
                menuItems.add(menuItem);
            }
            printersMenu.addMenuItems(menuItems);
        }
        printersMenu.open(player);
    }

    public void addPrinter(MoneyPrinter printer) {
        Location location = printer.getLocation();
        moneyPrinters.put(location, printer);
//        Bukkit.broadcastMessage("Created printer at " + location);
    }

    public static File getPrintersFile() {
        return printersFile;
    }

    public void removePrinter(MoneyPrinter printer) {
        Location location = printer.getLocation();
        moneyPrinters.remove(location);
//        Bukkit.broadcastMessage("Removed printer at " + location);
    }

    public static boolean contains(Location location) {
        return moneyPrinters.containsKey(location);
    }
}
