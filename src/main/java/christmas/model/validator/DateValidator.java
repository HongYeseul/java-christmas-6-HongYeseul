package christmas.model.validator;

import static christmas.view.validator.ExceptionMessage.INVALID_DATE_ERROR_MESSAGE;

public class DateValidator {
    public static void checkValidDate(Integer date){
        if( date > 31 || date < 1){
            throw new IllegalArgumentException(INVALID_DATE_ERROR_MESSAGE);
        }
    }
}
