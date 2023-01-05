package x00Hero.MineRP;

public class TimedAlert {

    private String message;
    private int length, timeElapsed;

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
}
