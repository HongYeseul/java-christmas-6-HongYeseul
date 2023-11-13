package christmas.service;

import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.Order;
import christmas.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OrderMenuServiceTest {
    OrderMenuService orderMenuService = new OrderMenuService();
    List<Menu> expectedOrderMenu = new ArrayList<>();
    List<Integer> orderCount = new ArrayList<>();
    OrderMenuOuputDTO orderMenuOuputDTO;
    Order order;

    @BeforeEach
    void init(){
        expectedOrderMenu.add(Menu.T_BONE_STAKE);
        expectedOrderMenu.add(Menu.BARBECUE_RIB);
        expectedOrderMenu.add(Menu.CHOCO_CAKE);
        orderCount.add(1);
        orderCount.add(1);
        orderCount.add(2);
        order = new Order(expectedOrderMenu, orderCount);
        orderMenuOuputDTO = new OrderMenuOuputDTO(expectedOrderMenu, orderCount);
    }

    @Test
    @DisplayName("[SUCCESS] 주문을 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    void inputNormalOrder(){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO("티본스테이크-1,바비큐립-1,초코케이크-2");
        OrderMenuOuputDTO orderMenuOuputDTO = orderMenuService.inputOrder(orderMenuInputDTO);
        assertThat(orderMenuOuputDTO).isEqualTo(this.orderMenuOuputDTO);
    }

    @Test
    @DisplayName("[ERROR] 메뉴를 20개보다 많이 주문하면 예외가 발생한다.")
    void orderOverThenNormalQuantity(){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO("티본스테이크-10,바비큐립-5,초코케이크-6");
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("[ERROR] 음료만 주문하면 예외가 발생한다.")
    void orderOnlyDrinks(){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO("레드와인-6");
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("[ERROR] 중복된 메뉴 주문을 하면 예외가 발생한다..")
    void orderDuplicateMenu(){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO("티본스테이크-1,티본스테이크-3");
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("[SUCCESS] 총 주문 금액을 계산할 수 있다.")
    void calculateTotal(){
        OrderMenuOuputDTO orderMenuOuputDTO = new OrderMenuOuputDTO(expectedOrderMenu, orderCount);
        BigDecimal total = orderMenuService.calculateTotal(orderMenuOuputDTO);
        assertThat(total).isEqualTo(new BigDecimal("139000"));
    }

    @Test
    @DisplayName("[SUCCESS] 총 주문 금액을 계산하여 증정 메뉴가 있는지 판단한다.")
    void isAdditionalGift(){
        OrderMenuOuputDTO orderMenuOuputDTO = new OrderMenuOuputDTO(expectedOrderMenu, orderCount);
        assertThat(orderMenuService.hasAdditionalGift(orderMenuOuputDTO)).isEqualTo(true);
    }
}