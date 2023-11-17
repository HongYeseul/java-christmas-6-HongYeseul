package christmas.model.customer;

import christmas.model.menu.Menu;
import christmas.model.menu.MenuType;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.IntStream;

public class Order {
    private final List<Menu> orderMenu;
    private final List<Integer> orderCount;

    public Order(List<Menu> order, List<Integer> orderCount){
        this.orderMenu = order;
        this.orderCount = orderCount;
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
                .filter(i -> orderMenu.get(i).getType().equals(MenuType.DESSERT))
                .map(orderCount::get).sum();
    }

    public long mainDishCount() {
        return IntStream.range(0, orderMenu.size())
                .filter(i -> orderMenu.get(i).getType().equals(MenuType.MAIN_DISH))
                .map(orderCount::get).sum();
    }
}
