package christmas.model.customer;

import christmas.model.Week;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDate {
    private final Integer date;
    public OrderDate(Integer date) {
        this.date = date;
    }

    public BigDecimal saleFromXMasDDay() {
        return new BigDecimal(Integer.toString((date - 1) * 100));
    }

    public boolean isWeekDay() {
        LocalDate localDate = LocalDate.of(2023, 12, date);
        Week dayOfWeek = Week.getDay(localDate.getDayOfWeek().getValue());
        return dayOfWeek == Week.MONDAY
                || dayOfWeek == Week.TUESDAY
                || dayOfWeek == Week.WEDNESDAY
                || dayOfWeek == Week.THURSDAY
                || dayOfWeek == Week.SUNDAY;
    }
}
