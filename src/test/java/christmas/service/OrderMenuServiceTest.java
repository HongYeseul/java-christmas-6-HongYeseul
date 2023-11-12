package christmas.service;

import christmas.DTO.OrderMenuRequestDTO;
import christmas.DTO.OrderMenuResponseDTO;
import christmas.model.menu.Menu;
import christmas.model.menu.MenuType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderMenuServiceTest {
    OrderMenuService orderMenuService = new OrderMenuService();
    List<MenuType> expectedOrderMenu = new ArrayList<>();
    List<Integer> orderCount = new ArrayList<>();
    OrderMenuResponseDTO expectedOrderMenuResponseDTO;

    @BeforeEach
    void init(){
        expectedOrderMenu.add(MenuType.T_BONE_STAKE);
        orderCount.add(1);
        expectedOrderMenuResponseDTO = new OrderMenuResponseDTO(expectedOrderMenu, orderCount);
    }

    @Test
    @DisplayName("[SUCESS] 주문을 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    void inputNormalOrder(){
        OrderMenuRequestDTO orderMenuRequestDTO = new OrderMenuRequestDTO("티본스테이크-1");
        OrderMenuResponseDTO orderMenuResponseDTO = orderMenuService.inputOrder(orderMenuRequestDTO);
        assertThat(orderMenuResponseDTO).isEqualTo(expectedOrderMenuResponseDTO);
    }
}