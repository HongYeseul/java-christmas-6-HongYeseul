package christmas.service.constants;

import java.math.BigDecimal;

public class Threshold {
    public static final BigDecimal SPECIAL_GIFT_THRESHOLD = new BigDecimal("120000");

    /**
     * 기준값보다 이상인지 판단하는 용으로 사용되는 상수
     */
    public static final Integer BIG_DECIMAL_FLAG_THRESHOLD = 0;

    public static final Integer MAXIMUM_QUANTITY = 20;

    public static final boolean HAVE_BENEFIT = true;
    public static final boolean NONE_BENEFIT = false;
}
