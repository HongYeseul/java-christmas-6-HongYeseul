package christmas.service;

import christmas.DTO.OrderDateRequestDTO;
import christmas.DTO.OrderDateResponseDTO;
import christmas.model.customer.OrderDate;

import java.math.BigDecimal;
import java.time.LocalDate;

public class OrderDateService {
    private OrderDate orderDate;
    /*
    * inputOrderDate: 고객이 식당 방문 날짜 입력시 DateDTO -> OrderDate 저장
    * */
    public OrderDateResponseDTO inputOrderDate(OrderDateRequestDTO requestDTO){
        orderDate = new OrderDate(requestDTO.date());
        return new OrderDateResponseDTO(requestDTO.date());
    }

    /**
     * 크리스마스 디데이 할인 금액 계산
     * @param orderDate
     * @return
     */
    public BigDecimal calculateDDaySalePrice(OrderDate orderDate) {
        // TODO: 크리스마스 이후 할인 없음
        BigDecimal result = new BigDecimal("1000");
        return result.add(orderDate.saleFromXMasDDay());
    }

    public BigDecimal calculateWeekDaySalePrice(OrderDate orderDate, Integer dessertCount) {
        BigDecimal result = new BigDecimal("2023");
        if (orderDate.isWeekDay()) {
            return result.multiply(BigDecimal.valueOf(dessertCount));
        }
        return new BigDecimal("0");
    }
}
