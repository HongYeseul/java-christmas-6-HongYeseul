package christmas.service;

import christmas.DTO.OrderDateRequestDTO;
import christmas.DTO.OrderDateResponseDTO;
import christmas.model.customer.OrderDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDateServiceTest {
    OrderDateService orderDateService = new OrderDateService();
    OrderDate orderDate;

    @BeforeEach
    void init(){
        orderDate = new OrderDate(3);
    }

    @Test
    @DisplayName("[SUCCESS] 날짜를 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    void inputNormalDate(){
        OrderDateRequestDTO orderDateRequestDTO = new OrderDateRequestDTO(12);
        OrderDateResponseDTO orderDateResponseDTO = orderDateService.inputOrderDate(orderDateRequestDTO);
        assertThat(orderDateResponseDTO.date()).isEqualTo(12);
    }

    @Test
    @DisplayName("[SUCCESS] 크리스마스 디데이 할인 금액을 계산한다.")
    void calculateDDaySalePrice(){
        BigDecimal result = orderDateService.calculateDDaySalePrice(orderDate);
        assertThat(result).isEqualTo(new BigDecimal("1200"));
    }

    @Test
    @DisplayName("[SUCCESS] 평일 할인 금액을 계산한다.")
    void calculateWeekDaySalePrice(){
        Integer desertCount = 2;
        BigDecimal result = orderDateService.calculateWeekDaySalePrice(orderDate, desertCount);
        assertThat(result).isEqualTo(new BigDecimal("4046"));
    }

    @Test
    @DisplayName("[SUCCESS] 주말 할인 금액을 계산한다.")
    void calculateWeekendSalePrice(){

    }

    @Test
    @DisplayName("[SUCCESS] 특별 할인 금액을 계산한다.")
    void calculateSpecialSalePrice(){

    }
}