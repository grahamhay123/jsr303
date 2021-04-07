package com.example.jsr303;

import java.util.HashMap;
import java.util.Map;

public enum Frequency {
    WEEK(true, 1), TWO_WEEK(true, 2), FOUR_WEEK(true, 4), MONTH(false, 0), QUARTER(true, 13), YEAR(true, 52);

    private static final Map<Integer, Frequency> byWeeks = new HashMap<>();

    static {
        for (Frequency e : values()) {
            if (e.multipleOfAWeek)
                byWeeks.put(e.weeks, e);
        }
    }

    public final Boolean multipleOfAWeek;
    public final int weeks;


    private Frequency(Boolean multipleOfAWeek, int weeks) {
        this.multipleOfAWeek = multipleOfAWeek;
        this.weeks = weeks;
    }

    // Returns null for a Frequency non-divisible to whole weeks
    public static Frequency valueOfWeeks(int weeks) {
        return byWeeks.get(weeks);
    }

}
