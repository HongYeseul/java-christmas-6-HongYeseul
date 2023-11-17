package christmas.service;

import christmas.model.constants.EventBadge;
import christmas.model.menu.Menu;

import java.math.BigDecimal;

public class OrderService {
    /**
     * <총 혜택 금액> 출력
     */
    public BigDecimal calculateTotalSalePrice(
            BigDecimal dDaySale,
            BigDecimal weekDaySale,
            BigDecimal weekendSale,
            BigDecimal specialSale,
            boolean hasAdditionalGift) {
        BigDecimal result = dDaySale.add(weekDaySale).add(weekendSale).add(specialSale);
        if (hasAdditionalGift) {
            return result.add(new BigDecimal(Menu.CHAMPAGNE.getPrice().toString()));
        }
        return result;
    }

    /**
     * <할인 후 예상 결제 금액> 출력
     */
    public BigDecimal calculateTotalCharge(BigDecimal totalPrice, BigDecimal salePrice, boolean haveGift) {
        if (haveGift) {
            return totalPrice.subtract(salePrice)
                    .add(new BigDecimal(Menu.CHAMPAGNE.getPrice()));
        }
        return totalPrice.subtract(salePrice);
    }

    /**
     * <이벤트 배지> 출력
     */
    public String makeEventBadge(BigDecimal salePrice) {
        return EventBadge.getName(salePrice);
    }
}
