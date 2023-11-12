package christmas.service;

import christmas.model.customer.OrderDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {
    OrderService orderService = new OrderService();
    OrderDateService orderDateService = new OrderDateService();
    OrderDate orderDate;

    @BeforeEach
    void init(){
        orderDate = new OrderDate(3);
    }

    @Test
    @DisplayName("[SUCCESS] 총 혜택 금액을 계산한다.")
    void calculateTotalSalePrice(){
        Integer desertCount = 2;
        Integer mainMenuCount = 0;
        BigDecimal result = orderService.calculateTotalSalePrice(
                orderDateService.calculateDDaySalePrice(orderDate),
                orderDateService.calculateWeekDaySalePrice(orderDate, desertCount),
                orderDateService.calculateWeekendSalePrice(orderDate, mainMenuCount),
                orderDateService.calculateSpecialSalePrice(orderDate),
                true
        );

        assertThat(result).isEqualTo(new BigDecimal("31246"));
    }

    @Test
    @DisplayName("[SUCCESS] 할인 후 예상 결제 금액을 정상 반환힌디.")
    void calculateTotalCharge(){
        BigDecimal result = orderService.calculateTotalCharge(
                new BigDecimal("142000"),
                new BigDecimal("31246"),
                true);
        assertThat(result).isEqualTo(new BigDecimal("135754"));
    }

    @Test
    @DisplayName("[SUCCESS] 혜택 금액에 따른 이벤트 배지를 정상 반환한다.")
    void makeEventBadge(){
        String result = orderService.makeEventBadge(new BigDecimal("31246"));
        assertThat(result).isEqualTo("산타");
    }
}