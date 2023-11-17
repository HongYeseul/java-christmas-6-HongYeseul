package christmas.view.validator;

import java.util.regex.Pattern;

import static christmas.view.constants.Regex.REGEX_DATE_NUMERIC;
import static christmas.view.validator.ExceptionMessage.INVALID_DATE_ERROR_MESSAGE;

public class DateValidator {
    private static final Pattern NUMERIC = Pattern.compile(REGEX_DATE_NUMERIC);

    public static void checkNumericInput(String input) {
        if (NUMERIC.matcher(input).matches()) {
            return;
        }
        throw new IllegalArgumentException(INVALID_DATE_ERROR_MESSAGE);
    }
}
