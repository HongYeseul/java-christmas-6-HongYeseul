package christmas.model.customer;

import christmas.model.SpecialDay;
import christmas.model.Week;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import java.util.stream.IntStream;

import static christmas.model.SpecialDay.specialDay;

public class OrderDate {
    private final Integer date;
    public OrderDate(Integer date) {
        this.date = date;
    }

    public BigDecimal saleFromXMasDDay() {
        return new BigDecimal(Integer.toString((date - 1) * 100));
    }

    public boolean isWeekDay() {
        Week day = dayofWeek();
        return day == Week.MONDAY
                || day == Week.TUESDAY
                || day == Week.WEDNESDAY
                || day == Week.THURSDAY
                || day == Week.SUNDAY;
    }

    public boolean isWeekend() {
        Week day = dayofWeek();
        return day == Week.FRIDAY || day == Week.SATURDAY;
    }

    private Week dayofWeek(){
        LocalDate localDate = LocalDate.of(2023, 12, date);
        return Week.getDay(localDate.getDayOfWeek().getValue());
    }

    public boolean isSpecialDay() {
        return specialDay.stream()
                .anyMatch(specialDay -> specialDay.equals(date));
    }
}
