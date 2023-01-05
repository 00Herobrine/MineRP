package x00Hero.MineRP;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import x00Hero.MineRP.Chat.ChatController;
import x00Hero.MineRP.Events.DefaultMC.InteractEvent;
import x00Hero.MineRP.Events.DefaultMC.PlayerJoin;
import x00Hero.MineRP.Items.MoneyPrinters.PrinterController;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Player.DoorController;
import x00Hero.MineRP.Player.PayCheckController;
import x00Hero.MineRP.Player.RPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Plugin plugin;
    private static HashMap<UUID, RPlayer> players = new HashMap<>();

    public void onEnable() {
        plugin = this;
        registerEvents();
        cacheConfigs();
        registerLoops();
        getLogger().info("Enabled " + getDescription().getFullName());
        for(Player player : Bukkit.getOnlinePlayers()) {
            createRPlayer(player);
        }
    }

    public void cacheConfigs() {
        JobController.cacheJobs();
        PrinterController.cachePrinters();
        DoorController.cacheDoors();
    }

    public void registerEvents() {
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InteractEvent(), this);
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PayCheckController(), this);
        pm.registerEvents(new DoorController(), this);
        pm.registerEvents(new JobController(), this);
    }

    public void registerLoops() {
        JobController.paycheckLoop();
        PrinterController.printerLoop();
        ChatController.alertLoop();
    }

    public static ArrayList<RPlayer> getRPlayers() {
        return new ArrayList<>(players.values());
    }

    public static HashMap<UUID, RPlayer> getPlayers() {
        return players;
    }

    public static RPlayer getRPlayer(Player player) {
        return getRPlayer(player.getUniqueId());
    }

    public static RPlayer getRPlayer(UUID uuid) {
        return players.get(uuid);
    }
    public static void addRPlayer(RPlayer rPlayer) {
        players.put(rPlayer.getPlayer().getUniqueId(), rPlayer);
    }
    public static void createRPlayer(Player player) {
        RPlayer rPlayer = new RPlayer(player);
        rPlayer.setJob("citizen");
        rPlayer.setDefaultItems();
        addRPlayer(rPlayer);
    }

    public static String getTags(ItemStack itemStack) {
        return getStoredString(itemStack, "tag");
    }

    public static String getStoredString(ItemStack item, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if(container.has(namespacedKey, PersistentDataType.STRING)) {
            return container.get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }
}
