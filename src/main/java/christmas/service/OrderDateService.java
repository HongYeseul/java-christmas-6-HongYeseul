package christmas.service;

import christmas.dto.OrderDateInputDTO;
import christmas.dto.OrderDateOuputDTO;
import christmas.model.customer.OrderDate;

import java.math.BigDecimal;

public class OrderDateService {
    private OrderDate orderDate;
    /*
    * inputOrderDate: 고객이 식당 방문 날짜 입력시 DateDTO -> OrderDate 저장
    * */
    public OrderDateOuputDTO inputOrderDate(OrderDateInputDTO requestDTO){
        orderDate = new OrderDate(requestDTO.date());
        return new OrderDateOuputDTO(requestDTO.date());
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

    public BigDecimal calculateWeekendSalePrice(OrderDate orderDate, Integer mainMenuCount) {
        BigDecimal result = new BigDecimal("2023");
        if (orderDate.isWeekend()) {
            return result.multiply(BigDecimal.valueOf(mainMenuCount));
        }
        return new BigDecimal("0");
    }

    public BigDecimal calculateSpecialSalePrice(OrderDate orderDate) {
        if (orderDate.isSpecialDay()) {
            return new BigDecimal("1000");
        }
        return new BigDecimal("0");
    }
}
