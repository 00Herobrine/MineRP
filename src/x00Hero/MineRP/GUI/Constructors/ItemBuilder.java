package x00Hero.MineRP.GUI.Constructors;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import java.util.ArrayList;

import static x00Hero.MineRP.Main.plugin;

public class ItemBuilder {
    private ItemStack item;
    private Material material;
    private String name;
    private ArrayList lore;
    private int amount;

    public ItemBuilder(Material MATERIAL, int AMOUNT, String NAME) {
        ItemStack ITEM = new ItemStack(MATERIAL, AMOUNT);
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', NAME));
        ITEM.setItemMeta(meta);

        item = ITEM;
        material = MATERIAL;
        name = NAME;
        amount = AMOUNT;
        lore = null;
        /*if(lore.size() > 0) */
    }

    public ItemBuilder(Material MATERIAL, int AMOUNT, String NAME, String LORE) {
        ItemStack ITEM = new ItemStack(MATERIAL, AMOUNT);
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', NAME));
        String[] split = LORE.split("\n");
        ArrayList<String> lore2 = new ArrayList<>();
        for(String line : split) {
            lore2.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        if(lore2.size() > 0) meta.setLore(lore2);
        ITEM.setItemMeta(meta);

        item = ITEM;
        material = MATERIAL;
        name = NAME;
        amount = AMOUNT;
        lore = lore2;
        /*if(lore.size() > 0) */
    }

    public ItemBuilder(Material MATERIAL, String NAME, String LORE) {
        ItemStack ITEM = new ItemStack(MATERIAL);
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', NAME));
        String[] split = LORE.split("\n");
        ArrayList<String> lore2 = new ArrayList<>();
        for(String line : split) {
            lore2.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        if(lore2.size() > 0 && !LORE.equals("")) meta.setLore(lore2);
        ITEM.setItemMeta(meta);

        item = ITEM;
        material = MATERIAL;
        name = NAME;
        amount = 1;
        lore = lore2;
    }

    public ItemBuilder(Material MATERIAL, int amount, String NAME, String LORE, String HIDDEN) {
        ItemStack ITEM = new ItemStack(MATERIAL, amount);
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', NAME));
        String[] split = LORE.split("\n");
        ArrayList<String> lore2 = new ArrayList<>();
        for(String line : split) {
            lore2.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        if(lore2.size() > 0 && !LORE.equals("")) meta.setLore(lore2);
        ITEM.setItemMeta(meta);

        item = ITEM;
        material = MATERIAL;
        name = NAME;
        amount = 1;
        lore = lore2;
        storeString("tag", HIDDEN);
    }

    public ItemBuilder(Material MATERIAL, String NAME, String LORE, String HIDDEN) {
        ItemStack ITEM = new ItemStack(MATERIAL);
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', NAME));
        String[] split = LORE.split("\n");
        ArrayList<String> lore2 = new ArrayList<>();
        for(String line : split) {
            lore2.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        if(lore2.size() > 0 && !LORE.equals("")) meta.setLore(lore2);
        ITEM.setItemMeta(meta);

        item = ITEM;
        material = MATERIAL;
        name = NAME;
        amount = 1;
        lore = lore2;
        storeString("tag", HIDDEN);
    }

    public ItemBuilder(Material MATERIAL, String NAME, ArrayList<String> LORE) {
        ItemStack ITEM = new ItemStack(MATERIAL);
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', NAME));
        ArrayList<String> lore2 = new ArrayList<>();
        for(String line : LORE) {
            lore2.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        if(lore2.size() > 0) meta.setLore(lore2);
        ITEM.setItemMeta(meta);

        item = ITEM;
        material = MATERIAL;
        name = NAME;
        amount = 1;
        lore = lore2;
    }

    public ItemBuilder(Material MATERIAL, Integer AMOUNT, String NAME, ArrayList<String> LORE) {
        ItemStack ITEM = new ItemStack(MATERIAL, AMOUNT);
        ItemMeta meta = ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', NAME));
        ArrayList<String> lore2 = new ArrayList<>();
        for(String line : LORE) {
            lore2.add(ChatColor.translateAlternateColorCodes('&', line));
        }
        if(lore2.size() > 0) meta.setLore(lore2);
        ITEM.setItemMeta(meta);

        item = ITEM;
        material = MATERIAL;
        name = NAME;
        amount = AMOUNT;
        lore = lore2;
    }

    public ItemBuilder(ItemStack ITEM) {
        item = ITEM;
        material = ITEM.getType();
        name = ITEM.getItemMeta().getDisplayName();
        amount = ITEM.getAmount();
    }

    public void setItem(ItemStack item) {
        this.item = item;
    }

    public ItemStack getItemStack() {
        return item;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getLore() {
        return lore;
    }

    public int getAmount() {
        return amount;
    }

    public Material getMaterial() {
        return material;
    }

    public void storeString(String key, String value) {
        NamespacedKey namespacedKey = new NamespacedKey(plugin, key);
        ItemMeta itemMeta = item.getItemMeta();
        assert itemMeta != null;
        itemMeta.getPersistentDataContainer().set(namespacedKey, PersistentDataType.STRING, value);
        item.setItemMeta(itemMeta);
    }

    public String getStoredString(String key) {
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
