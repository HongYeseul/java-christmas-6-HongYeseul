package christmas.model;

import java.util.Arrays;
import java.util.Objects;

public enum Week {
    MONDAY(1),
    TUESDAY(2),
    WEDNESDAY(3),
    THURSDAY(4),
    FRIDAY(5),
    SATURDAY(6),
    SUNDAY(7);

    private final Integer day;

    Week(Integer day){
        this.day = day;
    }

    public static Week getDay(Integer day){
        return Arrays.stream(Week.values())
                .filter(week -> Objects.equals(week.day, day))
                .findFirst()
                .orElse(null);
    }
}
