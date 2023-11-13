package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.dto.OrderDateInputDTO;
import christmas.dto.OrderMenuInputDTO;

import static christmas.view.validator.DateValidator.checkNumericInput;

public class InputView {
    public static String readLine(){
        return Console.readLine();
    }

    public static void close(){
        Console.close();
    }

    public OrderDateInputDTO readVisitDate() {
        String datePlan = readLine();
        checkNumericInput(datePlan);
        return new OrderDateInputDTO(Integer.parseInt(datePlan));
    }

    public OrderMenuInputDTO readMenuAndCount() {
        return new OrderMenuInputDTO(readLine());
    }
}
