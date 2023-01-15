package x00Hero.MineRP.Chat;

import org.bukkit.Sound;

public class TimedAlert {

    private final String message;
    private final int length;
    private int timeElapsed = 0;
    private Sound sound = null;
    private float loudness = 1f, speed = 1f;

    public boolean equals(TimedAlert other) {
        return this.message.equals(other.message) && this.length == other.length;
    }

    public TimedAlert(String message, Integer length) {
        this.message = message;
        this.length = length;
    }

    public String getMessage() {
        return message;
    }

    public int getLength() {
        return length;
    }

    public int getTimeElapsed() {
        return timeElapsed;
    }
    public void tick() {
        timeElapsed++;
    }

    public boolean hasSound() {
        return sound != null;
    }
    public Sound getSound() {
        return sound;
    }

    public void setSound(Sound sound) {
        this.sound = sound;
    }
    public float getLoudness() {
        return loudness;
    }

    public void setLoudness(float loudness) {
        this.loudness = loudness;
    }
    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
