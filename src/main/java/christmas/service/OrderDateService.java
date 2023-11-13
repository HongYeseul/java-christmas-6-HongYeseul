package christmas.service;

import christmas.dto.OrderDateInputDTO;
import christmas.dto.OrderDateOuputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.Order;
import christmas.model.customer.OrderDate;
import christmas.model.menu.Menu;

import java.math.BigDecimal;

import static christmas.model.constants.DiscountRate.CHRISTMAS_DISCOUNT_DEFAULT_PRICE;
import static christmas.model.constants.DiscountRate.SPECIAL_DAY_DISCOUNT;
import static christmas.model.constants.DiscountRate.WEEK_AND_WEEKEND_DISCOUNT;

public class OrderDateService {
    OrderDate orderDate;
    /**
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
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        if (orderDate.isAfterChristmas()) {
            return BigDecimal.ZERO;
        }
        return CHRISTMAS_DISCOUNT_DEFAULT_PRICE.add(orderDate.saleFromXMasDDay());
    }

    /**
     * 평일 할인 금액 계산
     */
    public BigDecimal calculateWeekDaySalePrice(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        if (orderDate.isWeekDay()) {
            return WEEK_AND_WEEKEND_DISCOUNT.multiply(BigDecimal.valueOf(order.dessertCount()));
        }
        return BigDecimal.ZERO;
    }

    /**
     * 주말 할인 금액 계산
     */
    public BigDecimal calculateWeekendSalePrice(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());

        if (orderDate.isWeekend()) {
            return WEEK_AND_WEEKEND_DISCOUNT.multiply(BigDecimal.valueOf(order.mainDishCount()));
        }
        return BigDecimal.ZERO;
    }

    /**
     * 특별 할인이 있는 날인지 계산
     */
    public BigDecimal calculateSpecialSalePrice(OrderDateOuputDTO orderDateOuputDTO) {
        OrderDate orderDate = new OrderDate(orderDateOuputDTO.date());
        if (orderDate.isSpecialDay()) {
            return SPECIAL_DAY_DISCOUNT;
        }
        return BigDecimal.ZERO;
    }

    /**
     * 증정 이벤트를 하는지 계산
     * 증정 이벤트 하면 -> 샴페인 가격 반환
     */
    public BigDecimal calculateGiftPrice(boolean hasGift) {
        if (hasGift) {
            return new BigDecimal(Menu.CHAMPAGNE.getPrice().toString());
        }
        return BigDecimal.ZERO;
    }
}
