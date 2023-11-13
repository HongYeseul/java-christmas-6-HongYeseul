package christmas.view.validator;

import java.util.regex.Pattern;

import static christmas.view.validator.ExceptionMessage.INVALID_ORDER_ERROR_MESSAGE;

public class MenuValidator {
    private static final String REGEX_ORDER_FORMAT = "(([ㄱ-ㅎ가-힣]){1,}-(\\d){1,}(,)?){1,}";
    private static final Pattern ORDER_FORMAT = Pattern.compile(REGEX_ORDER_FORMAT);

    public static void checkOrderFormatInput(String input) {
        if (ORDER_FORMAT.matcher(input).matches()) {
            return;
        }
        throw new IllegalArgumentException(INVALID_ORDER_ERROR_MESSAGE);
    }
}
