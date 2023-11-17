package christmas.model.validator;

import static christmas.model.constants.Day.FIRST_DAY_OF_DECEMBER;
import static christmas.model.constants.Day.LAST_DAY_OF_DECEMBER;
import static christmas.view.validator.ExceptionMessage.INVALID_DATE_ERROR_MESSAGE;

public class DateValidator {
    public static void checkValidDate(Integer date){
        if(date < FIRST_DAY_OF_DECEMBER || date > LAST_DAY_OF_DECEMBER){
            throw new IllegalArgumentException(INVALID_DATE_ERROR_MESSAGE);
        }
    }
}
