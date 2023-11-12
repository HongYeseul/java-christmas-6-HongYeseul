package christmas.service;

import christmas.DTO.OrderMenuRequestDTO;
import christmas.DTO.OrderMenuResponseDTO;
import christmas.model.customer.Order;
import christmas.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMenuServiceTest {
    OrderMenuService orderMenuService = new OrderMenuService();
    List<Menu> expectedOrderMenu = new ArrayList<>();
    List<Integer> orderCount = new ArrayList<>();
    OrderMenuResponseDTO expectedOrderMenuResponseDTO;
    Order order;

    @BeforeEach
    void init(){
        expectedOrderMenu.add(Menu.T_BONE_STAKE);
        expectedOrderMenu.add(Menu.BARBECUE_RIB);
        orderCount.add(1);
        orderCount.add(1);
        order = new Order(expectedOrderMenu, orderCount);
        expectedOrderMenuResponseDTO = new OrderMenuResponseDTO(expectedOrderMenu, orderCount);
    }

    @Test
    @DisplayName("[SUCESS] 주문을 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    void inputNormalOrder(){
        OrderMenuRequestDTO orderMenuRequestDTO = new OrderMenuRequestDTO("티본스테이크-1,바비큐립-1");
        OrderMenuResponseDTO orderMenuResponseDTO = orderMenuService.inputOrder(orderMenuRequestDTO);
        assertThat(orderMenuResponseDTO).isEqualTo(expectedOrderMenuResponseDTO);
    }

    @Test
    @DisplayName("[SUCCESS] 총 주문 금액을 계산할 수 있다.")
    void calculateTotal(){
        BigDecimal total = orderMenuService.calculateTotal(order);
        assertThat(total).isEqualTo(new BigDecimal("109000"));
    }
}