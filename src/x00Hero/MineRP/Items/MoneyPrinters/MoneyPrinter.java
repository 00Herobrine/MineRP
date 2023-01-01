package x00Hero.MineRP.Items.MoneyPrinters;

public class MoneyPrinter {
    private boolean enabled;
    private int generateMin, generateMax, battery, interval, cashHolder, maxCash, printTime;

    public MoneyPrinter() {
        enabled = false;
        generateMin = 15;
        generateMax = 20;
        battery = 100;
        interval = 120;
        maxCash = 250;
        printTime = 120;
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

    public int getBattery() {
        return battery;
    }
    public void setBattery(int battery) {
        this.battery = battery;
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
}
