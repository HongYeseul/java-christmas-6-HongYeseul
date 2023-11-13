package christmas.service;

import christmas.dto.OrderDateInputDTO;
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

class OrderDateServiceTest {
    OrderDateService orderDateService = new OrderDateService();
    List<Menu> expectedOrderMenu = new ArrayList<>();
    List<Integer> orderCount = new ArrayList<>();
    OrderMenuOuputDTO orderMenuOuputDTO;
    OrderDate orderDate;

    @BeforeEach
    void init(){
        orderDate = new OrderDate(3);
        expectedOrderMenu.add(Menu.T_BONE_STAKE);
        expectedOrderMenu.add(Menu.BARBECUE_RIB);
        expectedOrderMenu.add(Menu.CHOCO_CAKE);
        orderCount.add(1);
        orderCount.add(1);
        orderCount.add(2);
        orderMenuOuputDTO = new OrderMenuOuputDTO(expectedOrderMenu, orderCount);
    }

    @Test
    @DisplayName("[SUCCESS] 날짜를 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    void inputNormalDate(){
        OrderDateInputDTO orderDateInputDTO = new OrderDateInputDTO(12);
        OrderDateOuputDTO orderDateResponseDTO = orderDateService.inputOrderDate(orderDateInputDTO);
        assertThat(orderDateResponseDTO.date()).isEqualTo(12);
    }

    @Test
    @DisplayName("[SUCCESS] 크리스마스 디데이 할인 금액을 계산한다.")
    void calculateDDaySalePrice(){
        OrderDateOuputDTO orderDateOuputDTO = new OrderDateOuputDTO(3);
        BigDecimal result = orderDateService.calculateDDaySalePrice(orderDateOuputDTO);
        assertThat(result).isEqualTo(new BigDecimal("1200"));
    }

    @Test
    @DisplayName("[SUCCESS] 평일 할인 금액을 계산한다.")
    void calculateWeekDaySalePrice(){
        OrderDateOuputDTO orderDateOuputDTO = new OrderDateOuputDTO(3);
        BigDecimal result = orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOuputDTO);
        assertThat(result).isEqualTo(new BigDecimal("4046"));
    }

    @Test
    @DisplayName("[SUCCESS] 주말 할인 금액을 계산한다.")
    void calculateWeekendSalePrice(){
        Integer mainMenuCount = 0;
        BigDecimal result = orderDateService.calculateWeekendSalePrice(orderDate, mainMenuCount);
        assertThat(result).isEqualTo(new BigDecimal("0"));
    }

    @Test
    @DisplayName("[SUCCESS] 특별 할인 금액을 계산한다.")
    void calculateSpecialSalePrice(){
        BigDecimal result = orderDateService.calculateSpecialSalePrice(orderDate);
        assertThat(result).isEqualTo(new BigDecimal("1000"));
    }
}