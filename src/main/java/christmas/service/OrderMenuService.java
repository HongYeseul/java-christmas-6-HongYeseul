package christmas.service;

import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.Order;
import christmas.model.menu.Menu;
import christmas.model.validator.MenuValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderMenuService {
    /**
     * 고객이 주문하는 로직
     */
    public OrderMenuOuputDTO inputOrder(OrderMenuInputDTO orderMenuInputDTO) {
        String[] orderedMenu = orderMenuInputDTO.order().split(",");
        List<Menu> menuName = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (String order : orderedMenu) {
            String[] menu = order.split("-");
            MenuValidator.hasMenu(menu[0]);
            menuName.add(Menu.findByMenuName(menu[0]));
            count.add(Integer.parseInt(menu[1]));
        }
        return new OrderMenuOuputDTO(menuName, count);
    }

    /**
     * <할인 전 총주문 금액> 출력
     */
    public BigDecimal calculateTotal(OrderMenuOuputDTO orderMenuOuputDTO) {
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        return order.getTotalPrice();
    }

    /**
     * <증정 메뉴> 출력
     */
    public String specialGift(OrderMenuOuputDTO orderMenuOuputDTO) {
        if (hasAdditionalGift(orderMenuOuputDTO)) {
            return Menu.CHAMPAGNE.getName() + " 1개";
        }
        return "없음";
    }

    /**
     * 증정 메뉴가 있는지 판단
     * : 12만원 이상 주문시 샴페인 증정
     */
    public boolean hasAdditionalGift(OrderMenuOuputDTO orderMenuOuputDTO) {
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        int result = order.getTotalPrice()
                .compareTo(new BigDecimal("120000"));
        return result == 0 || result > 0;
    }
}
