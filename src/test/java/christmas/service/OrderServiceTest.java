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
}