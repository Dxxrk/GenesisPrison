package me.dxrk.utils;

public class TimeFrame {
    private long n = 1L;

    private TimeUnit timeUnit;

    public TimeFrame(TimeUnit timeUnit) {
        this.timeUnit = timeUnit;
    }

    public void add() {
        this.n++;
    }

    public long get() {
        return this.n;
    }

    public TimeUnit getTimeUnit() {
        return this.timeUnit;
    }

    public String getFormatted() {
        return (this.n == 0L) ? "" : (String.valueOf(this.n) + " " + ((this.n == 1L) ? this.timeUnit.getIdentifiers()[0] : this.timeUnit.getIdentifiers()[1]));
    }
}
