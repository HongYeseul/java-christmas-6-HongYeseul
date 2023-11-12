package christmas.service;

import christmas.DTO.OrderMenuRequestDTO;
import christmas.DTO.OrderMenuResponseDTO;
import christmas.model.customer.Order;
import christmas.model.menu.Menu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class OrderMenuService {
    private Order order;

    /**
     * 고객이 주문하는 로직
     * 주문 성공시 반환: 사용자가 구문한 리스트
     * @param orderMenuRequestDTO
     * @return
     */
    public OrderMenuResponseDTO inputOrder(OrderMenuRequestDTO orderMenuRequestDTO) {
        String[] orderedMenu = orderMenuRequestDTO.order().split(",");
        List<Menu> menuName = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        for (String order : orderedMenu) {
            String[] menu = order.split("-");

            for(Menu men : Menu.values()){
                if (Objects.equals(menu[0], men.getName())) {
                    menuName.add(men);
                    break;
                }
            }
            count.add(Integer.parseInt(menu[1]));
        }
        this.order = new Order(menuName, count);
        return new OrderMenuResponseDTO(menuName, count);
    }

    public BigDecimal calculateTotal(Order order) {
        return order.getTotalPrice();
    }
}
