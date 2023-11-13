package christmas.model.validator;

import christmas.model.menu.Menu;

import java.util.Arrays;
import java.util.Objects;

import static christmas.model.validator.ExceptionMessage.INVALID_MENU_ERROR_MESSAGE;
import static christmas.model.validator.ExceptionMessage.INVALID_MENU_QUANTITY;
import static christmas.service.constants.Threshold.MAXIMUM_QUANTITY;

public class MenuValidator {
    public static void hasMenu(String menuName) {
        if (Arrays.stream(Menu.values())
                .noneMatch(menu -> Objects.equals(menu.getName(), menuName))) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR_MESSAGE);
        }
    }

    public static void overThenMaximumQuentity(Integer quantity) {
        if (quantity > MAXIMUM_QUANTITY) {
            throw new IllegalArgumentException(INVALID_MENU_QUANTITY);
        }
    }
}
