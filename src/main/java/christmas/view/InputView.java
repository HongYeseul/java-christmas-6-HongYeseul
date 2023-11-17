package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.dto.OrderDateInputDTO;
import christmas.dto.OrderMenuInputDTO;

import static christmas.view.validator.DateValidator.checkNumericInput;
import static christmas.view.validator.MenuValidator.checkOrderFormatInput;

public class InputView {
    public static String readLine(){
        return Console.readLine();
    }

    public void close(){
        Console.close();
    }

    public OrderDateInputDTO readVisitDate() {
        String datePlan = readLine();
        checkNumericInput(datePlan);
        return new OrderDateInputDTO(Integer.parseInt(datePlan));
    }

    public OrderMenuInputDTO readMenuAndCount() {
        String orderPlan = readLine();
        checkOrderFormatInput(orderPlan);
        return new OrderMenuInputDTO(orderPlan);
    }
}
