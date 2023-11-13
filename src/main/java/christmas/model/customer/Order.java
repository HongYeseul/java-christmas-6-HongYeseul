package christmas.model.customer;

import christmas.model.menu.Menu;
import christmas.model.menu.MenuType;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;
import java.util.stream.IntStream;

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
        for (int i=0; i<orderMenu.size(); i++) {
            BigDecimal price = new BigDecimal(orderMenu.get(i).getPrice());
            BigDecimal count = new BigDecimal(orderCount.get(i));
            total = total.add(price.multiply(count));
        }
        return total;
    }

    public long dessertCount() {
        return IntStream.range(0, orderMenu.size())
                .filter(i -> orderMenu.get(i).getTitle().equals(MenuType.DESSERT))
                .map(i -> orderCount.get(i)).sum();
    }
}
