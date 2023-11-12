package christmas.model.customer;

import christmas.model.menu.Menu;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

public class Order {
    private List<Menu> orderMenu;
    private List<Integer> orderCount;

    public Order(List<Menu> order, List<Integer> orderCount){
        this.orderMenu = order;
        this.orderCount = orderCount;
    }

    public Integer getMenuTypeCount(){
        return orderMenu.size();
    }

    public BigDecimal getTotalPrice(){
        BigDecimal total = BigDecimal.ZERO;
        for (Menu menu : orderMenu) {
            System.out.println(menu.getPrice());
            total = total.add(new BigDecimal(menu.getPrice()));
        }
        return total;
    }
}
