package christmas.model.validator;

import christmas.model.menu.Menu;

import java.util.Arrays;
import java.util.Objects;

import static christmas.model.validator.ExceptionMessage.INVALID_MENU_ERROR_MESSAGE;

public class MenuValidator {

    public static void hasMenu(String menuName) {
        if (Arrays.stream(Menu.values())
                .noneMatch(menu -> Objects.equals(menu.getName(), menuName))) {
            throw new IllegalArgumentException(INVALID_MENU_ERROR_MESSAGE);
        }
    }
}
