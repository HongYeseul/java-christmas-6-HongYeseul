package christmas.service;

import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.model.customer.Order;
import christmas.model.menu.Menu;
import christmas.model.validator.MenuValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static christmas.service.constants.Separator.ITEM_QUANTITY_SEPARATOR;
import static christmas.service.constants.Separator.MENU_ITEM_SEPARATOR;
import static christmas.service.constants.Threshold.BIG_DECIMAL_FLAG_THRESHOLD;
import static christmas.service.constants.Threshold.SPECIAL_GIFT_THRESHOLD;

public class OrderMenuService {
    private final static Integer MENU_NAME_INDEX = 0;
    private final static Integer MENU_QUANTITY_INDEX = 1;
    private final static String SPECIAL_GIFT_QUANTITY = " 1개";
    private final static String NO_SPECIAL_GIFT = "없음";

    /**
     * 고객이 주문하는 로직
     */
    public OrderMenuOuputDTO inputOrder(OrderMenuInputDTO orderMenuInputDTO) {
        String[] orderedMenu = orderMenuInputDTO.order().split(MENU_ITEM_SEPARATOR);
        List<Menu> menuName = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        Arrays.stream(orderedMenu).map(order -> order.split(ITEM_QUANTITY_SEPARATOR)).forEach(menu -> {
            MenuValidator.hasMenu(menu[MENU_NAME_INDEX]);
            menuName.add(Menu.findByMenuName(menu[MENU_NAME_INDEX]));
            count.add(Integer.parseInt(menu[MENU_QUANTITY_INDEX]));
        });
        inputOrderValidate(count, menuName);
        return new OrderMenuOuputDTO(menuName, count);
    }

    private void inputOrderValidate(List<Integer> count, List<Menu> menuName) {
        MenuValidator.orderDuplicateMenu(menuName);
        MenuValidator.overThenMaximumQuantity(count.stream().mapToInt(i -> i).sum());
        MenuValidator.orderOnlyDrink(menuName);
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
            return Menu.CHAMPAGNE.getName() + SPECIAL_GIFT_QUANTITY;
        }
        return NO_SPECIAL_GIFT;
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

    /**
     * 증정 메뉴가 있는지 판단
     * : 12만원 이상 주문했는지 판단 후 샴페인 증정(return boolean)
     */
    public boolean hasAdditionalGift(OrderMenuOuputDTO orderMenuOuputDTO) {
        Order order = new Order(orderMenuOuputDTO.menu(), orderMenuOuputDTO.menuCount());
        int result = order.getTotalPrice()
                .compareTo(SPECIAL_GIFT_THRESHOLD);
        return result == BIG_DECIMAL_FLAG_THRESHOLD
                || result > BIG_DECIMAL_FLAG_THRESHOLD;
    }
}
