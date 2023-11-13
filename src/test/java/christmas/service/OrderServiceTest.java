package christmas.service;

import christmas.dto.OrderDateOuputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.OrderDate;
import christmas.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {
    OrderService orderService = new OrderService();
    OrderDateService orderDateService = new OrderDateService();
    List<Menu> expectedOrderMenu = new ArrayList<>();
    List<Integer> orderCount = new ArrayList<>();
    OrderDate orderDate;
    OrderDateOuputDTO orderDateOuputDTO;
    OrderMenuOuputDTO orderMenuOuputDTO;

    @BeforeEach
    void init(){
        orderDate = new OrderDate(3);
        orderDateOuputDTO = new OrderDateOuputDTO(3);
        expectedOrderMenu.add(Menu.T_BONE_STAKE);
        expectedOrderMenu.add(Menu.BARBECUE_RIB);
        expectedOrderMenu.add(Menu.CHOCO_CAKE);
        orderCount.add(1);
        orderCount.add(1);
        orderCount.add(2);
        orderMenuOuputDTO = new OrderMenuOuputDTO(expectedOrderMenu, orderCount);
    }

    @Test
    @DisplayName("[SUCCESS] 총 혜택 금액을 계산한다.")
    void calculateTotalSalePrice(){
        Integer desertCount = 2;
        Integer mainMenuCount = 0;
        BigDecimal result = orderService.calculateTotalSalePrice(
                orderDateService.calculateDDaySalePrice(orderDateOuputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOuputDTO),
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