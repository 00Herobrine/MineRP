package x00Hero.MineRP.Items.MoneyPrinters;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static x00Hero.MineRP.Main.plugin;

public class PrinterController {
    private static File printersFile = new File(plugin.getDataFolder(), "printers.yml");
    private static HashMap<String, MoneyPrinter> cachedPrinters = new HashMap<>(); // printerID, printer
    private ArrayList<MoneyPrinter> moneyPrinters = new ArrayList<>(); // player owned printers (might need a stored UUID for each printer)

    public static void cachePrinters() throws IOException {
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

    public void PrinterLoop() {
        int id = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for(MoneyPrinter printer : moneyPrinters) {
                if(printer.isEnabled()) printer.tick();
            }
        }, 20, 20);
    }
}
