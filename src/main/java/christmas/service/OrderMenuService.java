package christmas.service;

import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.Order;
import christmas.model.menu.Menu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderMenuService {
    /**
     * 고객이 주문하는 로직
     * 주문 성공시 반환: 사용자가 구문한 리스트
     */
    public OrderMenuOuputDTO inputOrder(OrderMenuInputDTO orderMenuInputDTO) {
        // TODO: 주문 포맷이 안 맞을 경우 예외 처리는 되나, 아직 해당 메뉴가 메뉴판에 있는지 판단하는 로직은 없음
        String[] orderedMenu = orderMenuInputDTO.order().split(",");
        List<Menu> menuName = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (String order : orderedMenu) {
            String[] menu = order.split("-");

            for (Menu men : Menu.values()) {
                if (Objects.equals(menu[0], men.getName())) {
                    menuName.add(men);
                    break;
                }
            }
            count.add(Integer.parseInt(menu[1]));
        }
//        this.order = new Order(menuName, count);
        return new OrderMenuOuputDTO(menuName, count);
    }

    public BigDecimal calculateTotal(OrderMenuOuputDTO orderMenuOuputDTO) {
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        return order.getTotalPrice();
    }

    public String specialGift(OrderMenuOuputDTO orderMenuOuputDTO) {
        if (hasAdditionalGift(orderMenuOuputDTO)) {
            return Menu.CHAMPAGNE.getName() + " 1개";
        }
        return "없음";
    }

    public boolean hasAdditionalGift(OrderMenuOuputDTO orderMenuOuputDTO) {
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        int result = order.getTotalPrice()
                .compareTo(new BigDecimal("120000"));
        return result == 0 || result > 0;
    }
}
