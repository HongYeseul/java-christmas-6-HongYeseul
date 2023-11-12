package christmas.model.customer;

import java.math.BigDecimal;

public class OrderDate {
    private final Integer date;
    public OrderDate(Integer date) {
        this.date = date;
    }

    public BigDecimal saleFromXMasDDay() {
        return new BigDecimal(Integer.toString((date - 1) * 100));
    }
}
