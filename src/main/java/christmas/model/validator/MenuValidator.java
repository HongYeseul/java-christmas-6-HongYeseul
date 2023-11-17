package christmas.model.validator;

import christmas.model.menu.Menu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static christmas.model.menu.Menu.findMenuTypeByMenuName;
import static christmas.model.validator.ExceptionMessage.INVALID_DUPLICATE_MENU;
import static christmas.model.validator.ExceptionMessage.INVALID_MENU_ERROR_MESSAGE;
import static christmas.model.validator.ExceptionMessage.INVALID_MENU_ORDER_ONLY_DRINK;
import static christmas.model.validator.ExceptionMessage.INVALID_MENU_QUANTITY;
import static christmas.service.constants.Threshold.MAXIMUM_QUANTITY;

public class MenuValidator {
    public static void hasMenu(String menuName) {
        if (Arrays.stream(Menu.values())
                .noneMatch(menu -> Objects.equals(menu.getName(), menuName))) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR_MESSAGE);
        }
    }

    public static void overThenMaximumQuantity(Integer quantity) {
        if (quantity > MAXIMUM_QUANTITY) {
            throw new IllegalArgumentException(INVALID_MENU_QUANTITY);
        }
    }

    public static void orderOnlyDrink(List<Menu> menuName) {
        if (findMenuTypeByMenuName(menuName)) {
            throw new IllegalArgumentException(INVALID_MENU_ORDER_ONLY_DRINK);
        }
    }

    public static void orderDuplicateMenu(List<Menu> menuName) {
        Set<Menu> set = new HashSet<>();
        menuName.stream().filter(name -> !set.add(name))
                        .collect(Collectors.toSet());
        if (set.size() != menuName.size()) {
            throw new IllegalArgumentException(INVALID_DUPLICATE_MENU);
        }
    }
}