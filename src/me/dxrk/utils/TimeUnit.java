package me.dxrk.utils;

import java.util.ArrayList;

public enum TimeUnit {
    SECOND(new String[] { "second", "seconds" }, 1L),
    MINUTE(new String[] { "minute", "minutes" }, 60L),
    HOUR(new String[] { "hour", "hours" }, 3600L),
    DAY(new String[] { "day", "days" }, 86400L),
    WEEK(new String[] { "week", "weeks" }, 604800L),
    MONTH(new String[] { "month", "months" }, 2592000L),
    YEAR(new String[] { "year", "years" }, 31104000L),
    FOREVER(new String[] { "forever" }, Long.MAX_VALUE);

    private String[] identifiers;

    private long seconds;

    TimeUnit(String[] identifiers, long seconds) {
        this.identifiers = identifiers;
        this.seconds = seconds;
    }

    public String[] getIdentifiers() {
        return this.identifiers;
    }

    public long getSeconds() {
        return this.seconds;
    }

    public static TimeUnit getTimeUnit(String identifier) {
        byte b;
        int i;
        TimeUnit[] arrayOfTimeUnit;
        for (i = (arrayOfTimeUnit = values()).length, b = 0; b < i; ) {
            TimeUnit timeUnit = arrayOfTimeUnit[b];
            byte b1 = 0;
            String[] arrayOfString;
            for (int j = (arrayOfString = timeUnit.getIdentifiers()).length; b1 < j; ) {
                String timeIdentifier = arrayOfString[b1];
                if (timeIdentifier.equalsIgnoreCase(identifier))
                    return timeUnit;
                b1 = (byte)(b1 + 1);
            }
            b = (byte)(b + 1);
        }
        return null;
    }

    public static long getSeconds(String string) {
        ArrayList<TimeUnit> timeUnits = new ArrayList<>();
        if (string.toLowerCase().contains(FOREVER.getIdentifiers()[0]) || string
                .toLowerCase().contains(FOREVER.getIdentifiers()[1])) {
            timeUnits.add(FOREVER);
            return Long.MAX_VALUE;
        }
        byte b;
        int i;
        String[] arrayOfString;
        for (i = (arrayOfString = string.split(" ")).length, b = 0; b < i; ) {
            String split = arrayOfString[b];
            split = split.replace(" ", "");
            String number = "";
            String identifier = "";
            byte b1 = 0;
            char[] arrayOfChar;
            for (int j = (arrayOfChar = split.toCharArray()).length; b1 < j; ) {
                char character = arrayOfChar[b1];
                String str = String.valueOf(character);
                if (isLong(str)) {
                    number = String.valueOf(number) + str;
                } else {
                    identifier = String.valueOf(identifier) + str;
                }
                b1 = (byte)(b1 + 1);
            }
            TimeUnit timeUnit = getTimeUnit(identifier);
            if (timeUnit != null)
                for (int k = 0; k < Integer.valueOf(number).intValue(); ) {
                    timeUnits.add(timeUnit);
                    k++;
                }
            b = (byte)(b + 1);
        }
        return getSeconds(timeUnits);
    }

    public static boolean isNumber(String string) {
        try {
            Integer.valueOf(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static boolean isLong(String string) {
        try {
            Long.valueOf(string);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    public static long getSeconds(ArrayList<TimeUnit> timeUnits) {
        long seconds = 0L;
        for (TimeUnit timeUnit : timeUnits)
            seconds += timeUnit.getSeconds();
        return seconds;
    }

    public static ArrayList<TimeFrame> getTimeFrames(ArrayList<TimeUnit> timeUnits) {
        ArrayList<TimeFrame> timeFrames = new ArrayList<>();
        for (TimeUnit timeUnit : timeUnits) {
            boolean create = true;
            for (TimeFrame timeFrame : timeFrames) {
                if (timeFrame.getTimeUnit() == timeUnit) {
                    timeFrame.add();
                    create = false;
                    break;
                }
            }
            if (create)
                timeFrames.add(new TimeFrame(timeUnit));
        }
        return timeFrames;
    }

    public static String getFormatted(ArrayList<TimeUnit> timeUnits) {
        String formatted = "";
        for (TimeFrame timeFrame : getTimeFrames(timeUnits))
            formatted = String.valueOf(formatted) + (formatted.isEmpty() ? "" : " ") + timeFrame.getFormatted();
        return formatted;
    }

    public static ArrayList<TimeUnit> getTimeUnits(long seconds) {
        ArrayList<TimeUnit> timeUnits = new ArrayList<>();
        if (seconds == Long.MAX_VALUE) {
            timeUnits.add(FOREVER);
            return timeUnits;
        }
        int years = (int)(seconds / 31104000L);
        int months = (int)(seconds % 31104000L / 2592000L);
        int weeks = (int)(seconds % 31104000L % 2592000L / 604800L);
        int days = (int)(seconds % 31104000L % 2592000L % 604800L / 86400L);
        int hours = (int)(seconds % 31104000L % 2592000L % 604800L % 86400L / 3600L);
        int minute = (int)(seconds % 31104000L % 2592000L % 604800L % 86400L % 3600L / 60L);
        int secondS = (int)(seconds % 31104000L % 2592000L % 604800L % 86400L % 3600L % 60L);
        int i;
        for (i = 0; i < years; ) {
            timeUnits.add(YEAR);
            i++;
        }
        for (i = 0; i < months; ) {
            timeUnits.add(MONTH);
            i++;
        }
        for (i = 0; i < weeks; ) {
            timeUnits.add(WEEK);
            i++;
        }
        for (i = 0; i < days; ) {
            timeUnits.add(DAY);
            i++;
        }
        for (i = 0; i < hours; ) {
            timeUnits.add(HOUR);
            i++;
        }
        for (i = 0; i < minute; ) {
            timeUnits.add(MINUTE);
            i++;
        }
        for (i = 0; i < secondS; ) {
            timeUnits.add(SECOND);
            i++;
        }
        return timeUnits;
    }
}