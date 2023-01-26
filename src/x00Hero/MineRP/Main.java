package x00Hero.MineRP;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import x00Hero.MineRP.Chat.ChatController;
import x00Hero.MineRP.Chat.CommandManager;
import x00Hero.MineRP.Events.DefaultMC.InteractHandler;
import x00Hero.MineRP.Events.DefaultMC.PlayerJoin;
import x00Hero.MineRP.Items.Generic.Lockpick;
import x00Hero.MineRP.Items.MoneyPrinters.HologramController;
import x00Hero.MineRP.Items.MoneyPrinters.PrinterController;
import x00Hero.MineRP.Jobs.JobController;
import x00Hero.MineRP.Player.DoorController;
import x00Hero.MineRP.Player.PayCheckController;
import x00Hero.MineRP.Player.RPlayer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static Plugin plugin;
    private static HashMap<UUID, RPlayer> players = new HashMap<>();
    public static final PluginManager pm = Bukkit.getPluginManager();
    public static File playersFolder;

    public void onEnable() {
        plugin = this;
        playersFolder = new File(plugin.getDataFolder(), "players");
        registerEvents();
        cacheConfigs();
        registerLoops();
        registerCommands();
        getConfig();
        saveDefaultConfig();
        getLogger().info("Enabled " + getDescription().getFullName());
    }

    public static void cacheConfigs() {
        JobController.cacheJobs();
        PrinterController.cachePrinters();
        DoorController.cacheDoors();
        ChatController.cacheMessages();
        HologramController.loadConfig();
        Lockpick.loadConfig();
        for(Player player : Bukkit.getOnlinePlayers()) {
            createRPlayer(player);
        }
    }

    public void registerCommands() {
        getCommand("/").setExecutor(new CommandManager());
        getCommand("advert").setExecutor(new CommandManager());
        getCommand("minerp").setExecutor(new CommandManager());
        getCommand("printers").setExecutor(new CommandManager());
        getCommand("balance").setExecutor(new CommandManager());
        getCommand("door").setExecutor(new CommandManager());
    }

    public void registerEvents() {
        pm.registerEvents(new InteractHandler(), this);
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new PayCheckController(), this);
        pm.registerEvents(new DoorController(), this);
        pm.registerEvents(new JobController(), this);
        pm.registerEvents(new Lockpick(), this);
        pm.registerEvents(new ChatController(), this);
        pm.registerEvents(new CommandManager(), this);
        pm.registerEvents(new PrinterController(), this);
    }

    public static void registerLoops() {
        JobController.paycheckLoop();
        PrinterController.printerLoop();
        ChatController.alertLoop();
        Lockpick.lockPickLoop();
        HologramController.visibilityLoop(); // Change to temporary display on whoever clicks it for printer stats
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
        rPlayer.loadPlayerFile();
    }

    public static boolean hasTags(ItemStack itemStack, String tag) {
        return getStoredString(itemStack, "tag").equalsIgnoreCase(tag);
    }
    public static String getTags(ItemStack itemStack) {
        return getStoredString(itemStack, "tag");
    }
    public static String getStoredString(ItemStack item, String key) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        ItemMeta itemMeta = item.getItemMeta();
        if(item.hasItemMeta()) {
            assert itemMeta != null;
            PersistentDataContainer container = itemMeta.getPersistentDataContainer();
            if(container.has(namespacedKey, PersistentDataType.STRING)) {
                return container.get(namespacedKey, PersistentDataType.STRING);
            }
        }
        return null;
    }

    public boolean hasStoredString() {
        return false;
    }

    public ArrayList<Player> getPlayersInDistance(Location location, double distance) {
        ArrayList<Player> inDistance = new ArrayList<>();
        ArmorStand entity = (ArmorStand) location.getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
        entity.setCustomName(" ");
        entity.setCustomNameVisible(true);
        entity.setVisible(false);
        entity.setGravity(false);
        entity.setBasePlate(false);
        for(Entity entities : entity.getNearbyEntities(distance, distance, distance))
            if(entities instanceof Player) inDistance.add((Player) entities);
        entity.remove();
        return inDistance;
    }
}
