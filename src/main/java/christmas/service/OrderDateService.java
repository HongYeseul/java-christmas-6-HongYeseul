package christmas.service;

import christmas.dto.OrderDateInputDTO;
import christmas.dto.OrderDateOuputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.Order;
import christmas.model.customer.OrderDate;
import christmas.model.menu.Menu;

import java.math.BigDecimal;

public class OrderDateService {
    OrderDate orderDate;
    /*
    * inputOrderDate: 고객이 식당 방문 날짜 입력시 DateDTO -> OrderDate 저장
    * */
    public OrderDateOuputDTO inputOrderDate(OrderDateInputDTO requestDTO){
        orderDate = new OrderDate(requestDTO.date());
        return new OrderDateOuputDTO(requestDTO.date());
    }

    /**
     * 크리스마스 디데이 할인 금액 계산
     */
    public BigDecimal calculateDDaySalePrice(OrderDateOuputDTO orderDateOuputDTO) {
        // TODO: 크리스마스 이후 할인 없음
        BigDecimal result = new BigDecimal("1000");
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        return result.add(orderDate.saleFromXMasDDay());
    }

    public BigDecimal calculateWeekDaySalePrice(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        BigDecimal result = new BigDecimal("2023");
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        if (orderDate.isWeekDay()) {
            return result.multiply(BigDecimal.valueOf(order.dessertCount()));
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateWeekendSalePrice(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        BigDecimal result = new BigDecimal("2023");
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());

        if (orderDate.isWeekend()) {
            return result.multiply(BigDecimal.valueOf(order.mainDishCount()));
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateSpecialSalePrice(OrderDateOuputDTO orderDateOuputDTO) {
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        if (orderDate.isSpecialDay()) {
            return new BigDecimal("1000");
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal calculateGiftPrice(boolean hasGift) {
        if (hasGift) {
            return new BigDecimal(Menu.CHAMPAGNE.getPrice().toString());
        }
        return BigDecimal.ZERO;
    }
}
