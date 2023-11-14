package christmas.service;

import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.Order;
import christmas.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

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

    @DisplayName("[SUCCESS] 주문을 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    @ValueSource(strings = {"티본스테이크-1,바비큐립-1,초코케이크-2",
            "초코케이크-1",
            "양송이수프-2,시저샐러드-4"})
    @ParameterizedTest
    void inputNormalOrder(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        orderMenuService.inputOrder(orderMenuInputDTO);
    }

    @DisplayName("[ERROR] 메뉴를 20개보다 많이 주문하면 예외가 발생한다.")
    @ValueSource(strings = {"티본스테이크-10,바비큐립-5,초코케이크-6",
            "초코케이크-27",
            "시저샐러드-5,티본스테이크-4,크리스마스파스타-5,제로콜라-5,아이스크림6"})
    @ParameterizedTest
    void orderOverThenNormalQuantity(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[ERROR] 음료만 주문하면 예외가 발생한다.")
    @ValueSource(strings = {"레드와인-6",
            "제로콜라-1,레드와인-2",
            "레드와인-2,제로콜라-3,샴페인-1"})
    @ParameterizedTest
    void orderOnlyDrinks(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[ERROR] 중복된 메뉴 주문을 하면 예외가 발생한다.")
    @ValueSource(strings = {"티본스테이크-1,티본스테이크-3",
            "제로콜라-1,제로콜라-2",
            "레드와인-2,타파스-3,타파스-1"})
    @ParameterizedTest
    void orderDuplicateMenu(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
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