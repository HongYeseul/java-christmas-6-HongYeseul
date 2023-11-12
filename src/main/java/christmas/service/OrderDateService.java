package christmas.service;

import christmas.DTO.OrderDateRequestDTO;
import christmas.DTO.OrderDateResponseDTO;
import christmas.model.customer.OrderDate;

public class OrderDateService {
    private static OrderDate orderDate;
    /*
    * inputOrderDate: 고객이 식당 방문 날짜 입력시 DateDTO -> OrderDate 저장
    * */
    public static OrderDateResponseDTO inputOrderDate(OrderDateRequestDTO requestDTO){
        orderDate = new OrderDate(requestDTO.date());
        return new OrderDateResponseDTO(requestDTO.date());
    }
}
