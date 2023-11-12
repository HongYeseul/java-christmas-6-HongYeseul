package christmas.service;

import christmas.DTO.OrderDateRequestDTO;
import christmas.DTO.OrderDateResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OrderDateServiceTest {
    @Test
    @DisplayName("[SUCESS] 날짜를 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    void orderNormalPrice(){
        OrderDateRequestDTO orderDateRequestDTO = new OrderDateRequestDTO(12);
        OrderDateResponseDTO orderDateResponseDTO = OrderDateService.inputOrderDate(orderDateRequestDTO);
        assertThat(orderDateResponseDTO.date()).isEqualTo(8);
    }
}