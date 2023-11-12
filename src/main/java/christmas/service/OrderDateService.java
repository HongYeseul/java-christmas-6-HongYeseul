package christmas.service;

import christmas.DTO.OrderDateRequestDTO;
import christmas.DTO.OrderDateResponseDTO;
import christmas.model.customer.OrderDate;

import java.math.BigDecimal;

public class OrderDateService {
    private OrderDate orderDate;
    /*
    * inputOrderDate: 고객이 식당 방문 날짜 입력시 DateDTO -> OrderDate 저장
    * */
    public OrderDateResponseDTO inputOrderDate(OrderDateRequestDTO requestDTO){
        orderDate = new OrderDate(requestDTO.date());
        return new OrderDateResponseDTO(requestDTO.date());
    }

    public BigDecimal calculateDDaySalePrice(OrderDate orderDate) {
        BigDecimal result = new BigDecimal("1000");
        return result.add(orderDate.saleFromXMasDDay());
    }
}
