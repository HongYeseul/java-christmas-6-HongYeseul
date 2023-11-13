package christmas.model;

import java.math.BigDecimal;

public enum EventBadge {
    SANTA("산타", new BigDecimal("20000")),
    TREE("트리", new BigDecimal("10000")),
    STAR("별", new BigDecimal("5000"));

    private String name;
    private BigDecimal charge;

    EventBadge(String name, BigDecimal charge) {
        this.name = name;
        this.charge = charge;
    }

    public static String getName(BigDecimal charge) {
        EventBadge[] badges = EventBadge.values();
        for (EventBadge badge : badges) {
            int result = charge.compareTo(badge.charge);

            if (badge.isMoreThenThreshold(result)) {
                return badge.name;
            }
        }
        return "없음";
    }

    private boolean isMoreThenThreshold(int result){
        return result == 0 || result > 0;
    }
}
