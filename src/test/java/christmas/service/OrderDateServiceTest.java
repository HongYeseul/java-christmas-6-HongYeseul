package christmas.service;

import christmas.dto.OrderDateInputDTO;
import christmas.dto.OrderDateOuputDTO;
import christmas.dto.OrderMenuOutputDTO;
import christmas.model.menu.Menu;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OrderDateServiceTest {
    OrderDateService orderDateService = new OrderDateService();
    List<Menu> expectedOrderMenu = new ArrayList<>();
    List<Integer> orderCount = new ArrayList<>();
    OrderMenuOutputDTO orderMenuOutputDTO;

    @BeforeEach
    void init(){
        expectedOrderMenu.add(Menu.T_BONE_STAKE);
        expectedOrderMenu.add(Menu.BARBECUE_RIB);
        expectedOrderMenu.add(Menu.CHOCO_CAKE);
        orderCount.add(1);
        orderCount.add(1);
        orderCount.add(2);
        orderMenuOutputDTO = new OrderMenuOutputDTO(expectedOrderMenu, orderCount);
    }

    @DisplayName("[SUCCESS] 날짜를 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    @ValueSource(ints = {1, 10, 31})
    @ParameterizedTest
    void inputNormalDate(int input){
        OrderDateInputDTO orderDateInputDTO = new OrderDateInputDTO(input);
        OrderDateOuputDTO orderDateResponseDTO = orderDateService.inputOrderDate(orderDateInputDTO);
        assertThat(orderDateResponseDTO.date()).isEqualTo(input);
    }

    @DisplayName("[ERROR] 날짜를 비정상적인 값으로 입력하면 예외가 발생한다.")
    @ValueSource(ints = {0, -1, 32})
    @ParameterizedTest
    void inputAbnormalDate(int input){
        OrderDateInputDTO orderDateInputDTO = new OrderDateInputDTO(input);
        assertThatThrownBy(() -> orderDateService.inputOrderDate(orderDateInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[SUCCESS] 크리스마스 디데이 할인 금액을 계산한다.")
    @ParameterizedTest(name = "예상 방문 날짜: {0}, 반환되어야 하는 크리스마스 디데이 할인 금액: {1}")
    @CsvSource({"3, 1200", "26, 0", "25, 3400"})
    void calculateDDaySalePrice(int plannedDate,BigDecimal discount){
        OrderDateOuputDTO orderDateOuputDTO = new OrderDateOuputDTO(plannedDate);
        BigDecimal result = orderDateService.calculateDDaySalePrice(orderDateOuputDTO);
        assertThat(result).isEqualTo(discount);
    }

    @DisplayName("[SUCCESS] 평일 할인 금액을 계산한다.")
    @ParameterizedTest(name = "예상 방문 날짜: {0}, 반환되어야 하는 평일 할인 금액: {1}")
    @CsvSource({"3, 4046", "1, 0", "28, 4046"})
    void calculateWeekDaySalePrice(int plannedDate,BigDecimal discount){
        OrderDateOuputDTO orderDateOuputDTO = new OrderDateOuputDTO(plannedDate);
        BigDecimal result = orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOutputDTO);
        assertThat(result).isEqualTo(discount);
    }


    @DisplayName("[SUCCESS] 주말 할인 금액을 계산한다.")
    @ParameterizedTest(name = "예상 방문 날짜: {0}, 반환되어야 하는 주말 할인 금액: {1}")
    @CsvSource({"3, 0", "1, 4046", "28, 0"})
    void calculateWeekendSalePrice(int plannedDate,BigDecimal discount){
        OrderDateOuputDTO orderDateOuputDTO = new OrderDateOuputDTO(plannedDate);
        BigDecimal result = orderDateService.calculateWeekendSalePrice(orderDateOuputDTO, orderMenuOutputDTO);
        assertThat(result).isEqualTo(discount);
    }

    @DisplayName("[SUCCESS] 특별 할인 금액을 계산한다.")
    @ParameterizedTest(name = "예상 방문 날짜: {0}, 반환되어야 하는 특별 할인 금액: {1}")
    @CsvSource({"3, 1000", "25, 1000", "15, 0"})
    void calculateSpecialSalePrice(int plannedDate,BigDecimal discount){
        OrderDateOuputDTO orderDateOuputDTO = new OrderDateOuputDTO(plannedDate);
        BigDecimal result = orderDateService.calculateSpecialSalePrice(orderDateOuputDTO);
        assertThat(result).isEqualTo(discount);
    }
}