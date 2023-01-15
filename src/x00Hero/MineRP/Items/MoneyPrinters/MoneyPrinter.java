package x00Hero.MineRP.Items.MoneyPrinters;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import x00Hero.MineRP.GUI.Constructors.ItemBuilder;

public class MoneyPrinter {
    private boolean enabled;
    private Material material;
    private int generateMin, generateMax, battery, maxBattery, interval, cashHolder, maxCash, printTime, price, ownLimit;
    private String permission = null, name, lore;
    private ItemStack itemStack = null;
    private Location location;
    private Hologram hologram;

    public MoneyPrinter() {
        name = "Money Printer";
        material = Material.SMOOTH_STONE_SLAB;
        enabled = false;
        generateMin = 15;
        generateMax = 20;
        battery = 100;
        interval = 120;
        maxCash = 250;
        printTime = interval;
    }

    public boolean isEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getGenerateMin() {
        return generateMin;
    }
    public void setGenerateMin(int generateMin) {
        this.generateMin = generateMin;
    }

    public int getGenerateMax() {
        return generateMax;
    }
    public void setGenerateMax(int generateMax) {
        this.generateMax = generateMax;
    }
    
    public void drainBattery(int amount) {
        battery -= amount;
    }
    public int getBattery() {
        return battery;
    }
    public void setBattery(int battery) {
        this.battery = battery;
    }
    public int getMaxBattery() {
        return maxBattery;
    }
    public void setMaxBattery(int maxBattery) {
        this.maxBattery = maxBattery;
    }

    public int getInterval() {
        return interval;
    }
    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getCashHolder() {
        return cashHolder;
    }
    public void setCashHolder(int cashHolder) {
        this.cashHolder = cashHolder;
    }

    public int getMaxCash() {
        return maxCash;
    }
    public void setMaxCash(int maxCash) {
        this.maxCash = maxCash;
    }

    public int getPrintTime() {
        return printTime;
    }
    public void setPrintTime(int printTime) {
        this.printTime = printTime;
    }

    public Material getMaterial() {
        return material;
    }
    public void setMaterial(Material material) {
        this.material = material;
    }

    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
        this.price = price;
    }

    public int getOwnLimit() {
        return ownLimit;
    }
    public void setOwnLimit(int limit) {
        this.ownLimit = limit;
    }

    public boolean hasPermission() {
        return permission != null;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLore() {
        return lore;
    }
    public void setLore(String lore) {
        this.lore = lore;
    }

    public Location getLocation() {
        return location;
    }
    public void setLocation(Location location) {
        this.location = location;
    }

    public int generateCash() {
        return random(generateMin, generateMax);
    }
    public int random(int min, int max) {
        return (int) (Math.floor(Math.random() * (max - min + 1)) + min); // The maximum is inclusive and the minimum is inclusive
    }

    public ItemStack getItemStack() {
        if(itemStack == null) {
            ItemBuilder itemBuilder = new ItemBuilder(material, name, lore);
            itemStack = itemBuilder.getItemStack();
        }
        return itemStack;
    }

    public Hologram getHologram() {
        return hologram;
    }

    public void createHologram() {
        Hologram hologram = new Hologram("cunt", location);
        hologram.addLine("Battery: ");
        this.hologram = hologram;
    }

    public void updatedDisplay() {
        
    }
}
