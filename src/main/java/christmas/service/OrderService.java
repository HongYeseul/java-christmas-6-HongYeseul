package christmas.service;

import christmas.model.menu.Menu;

import java.math.BigDecimal;

public class OrderService {
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

    public BigDecimal calculateTotalCharge(BigDecimal totalPrice, BigDecimal salePrice, boolean haveGift) {
        if (haveGift) {
            return totalPrice.subtract(salePrice)
                    .add(new BigDecimal(Menu.CHAMPAGNE.getPrice()));
        }
        return totalPrice.subtract(salePrice);
    }
}
