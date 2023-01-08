package x00Hero.MineRP.Items.MoneyPrinters;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterCreateEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterPrintEvent;
import x00Hero.MineRP.Events.Constructors.Printers.PrinterTickEvent;
import x00Hero.MineRP.GUI.Constructors.Menu;
import x00Hero.MineRP.GUI.Constructors.MenuItem;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import static x00Hero.MineRP.Main.plugin;

public class PrinterController {
    private static File printersFile = new File(plugin.getDataFolder(), "printers.yml");
    private static HashMap<String, MoneyPrinter> cachedPrinters = new HashMap<>(); // printerID, printer
    private static ArrayList<MoneyPrinter> moneyPrinters = new ArrayList<>(); // player owned printers (might need a stored UUID for each printer)

    public static void cachePrinters() {
        if(!printersFile.exists()) plugin.saveResource("printers.yml", false);
        YamlConfiguration printers = YamlConfiguration.loadConfiguration(printersFile);
        MoneyPrinter printer = new MoneyPrinter();
//        if(!printers.contains("printers")) return;
        for(String printerID : printers.getConfigurationSection("printers").getKeys(false)) {
            ConfigurationSection printerConfig = printers.getConfigurationSection("printers." + printerID);
            String name = printerConfig.getString("name");
            printer.setName(name);
            if(printerConfig.contains("lore")) printer.setLore(printerConfig.getString("lore"));
            if(printerConfig.contains("material")) printer.setMaterial(Material.valueOf(printerConfig.getString("material")));
            if(printerConfig.contains("price")) printer.setPrice(printerConfig.getInt("price"));
            if(printerConfig.contains("generateMin")) printer.setGenerateMin(printerConfig.getInt("generateMin"));
            if(printerConfig.contains("generateMax")) printer.setGenerateMax(printerConfig.getInt("generateMax"));
            if(printerConfig.contains("maxCash")) printer.setMaxCash(printerConfig.getInt("maxCash"));
            if(printerConfig.contains("maxBattery")) printer.setMaxBattery(printerConfig.getInt("maxBattery"));
            if(printerConfig.contains("interval")) printer.setInterval(printerConfig.getInt("interval"));
            if(printerConfig.contains("limit")) printer.setOwnLimit(printerConfig.getInt("limit"));
            if(printerConfig.contains("permission")) printer.setPermission(printerConfig.getString("permission"));
            cachedPrinters.put(printerID, printer);
        }
    }

    public static void printerLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(MoneyPrinter printer : moneyPrinters) {
                Bukkit.getPluginManager().callEvent(new PrinterTickEvent(printer));
            }
        }, 20, 20);
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
        MoneyPrinter printer = e.getPrinter();
        printer.setLocation(e.getLocation());

    }

    public void displayCheck() {
        for(MoneyPrinter printer : moneyPrinters) {
            Hologram hologram = printer.getHologram();
//            printer.getLocation()
        }
    }

    public void printersMenu() {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for(String printerID : cachedPrinters.keySet()) {
            MoneyPrinter printer = cachedPrinters.get(printerID);
            MenuItem menuItem = new MenuItem(printer.getItemStack(), printerID);
        }
        Menu menu = new Menu(menuItems, "Money Printers", true, true);
//        Menu menu = new Menu("Printers");
    }

    public void createDisplay(Player player, MoneyPrinter printer) {

    }
}
