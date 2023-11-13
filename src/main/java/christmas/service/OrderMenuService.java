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
     * @param orderMenuInputDTO
     * @return
     */
    public OrderMenuOuputDTO inputOrder(OrderMenuInputDTO orderMenuInputDTO) {
        String[] orderedMenu = orderMenuInputDTO.order().split(",");
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
//        this.order = new Order(menuName, count);
        return new OrderMenuOuputDTO(menuName, count);
    }

    public BigDecimal calculateTotal(OrderMenuOuputDTO orderMenuOuputDTO) {
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        return order.getTotalPrice();
    }

    public boolean hasAdditionalGift(Order order){
        int result = order.getTotalPrice().compareTo(new BigDecimal("120000"));
        return result == 0 || result > 0;
    }
}
