package christmas.model.customer;

import christmas.model.menu.Menu;

import java.util.List;

public class Order {
    List<Menu> orderMenu;
    List<Integer> orderCount;

    public Order(List<Menu> order, List<Integer> orderCount){
        this.orderMenu = order;
        this.orderCount = orderCount;
    }
}
