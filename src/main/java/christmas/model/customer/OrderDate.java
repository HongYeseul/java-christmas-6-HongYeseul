package christmas.model.customer;

import christmas.model.constants.Week;

import java.math.BigDecimal;
import java.time.LocalDate;

import static christmas.model.constants.SpecialDay.specialDay;
import static christmas.model.constants.Day.CHRISTMAS_DAY;
import static christmas.model.constants.Day.MONTH;
import static christmas.model.constants.Day.ONE_DAY;
import static christmas.model.constants.Day.YEAR;
import static christmas.model.constants.DiscountRate.CHRISTMAS_DISCOUNT_PRICE;
import static christmas.model.validator.DateValidator.checkValidDate;

public class OrderDate {
    private final Integer date;
    public OrderDate(Integer date) {
        checkValidDate(date);
        this.date = date;
    }

    public BigDecimal saleFromXMasDDay() {
        return new BigDecimal(Integer.toString((date - ONE_DAY) * CHRISTMAS_DISCOUNT_PRICE));
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
        LocalDate localDate = LocalDate.of(YEAR, MONTH, date);
        return Week.getDay(localDate.getDayOfWeek().getValue());
    }

    public boolean isSpecialDay() {
        return specialDay.stream()
                .anyMatch(specialDay -> specialDay.equals(date));
    }

    public boolean isAfterChristmas() {
        return date > CHRISTMAS_DAY;
    }
}
