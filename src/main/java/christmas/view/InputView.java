package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.dto.OrderDateInputDTO;
import christmas.dto.OrderMenuInputDTO;

public class InputView {
    public static String readLine(){
        return Console.readLine();
    }

    public static void close(){
        Console.close();
    }

    public OrderDateInputDTO readVisitDate() {
        return new OrderDateInputDTO(Integer.parseInt(readLine()));
    }

    public OrderMenuInputDTO readMenuAndCount() {
        return new OrderMenuInputDTO(readLine());
    }
}
