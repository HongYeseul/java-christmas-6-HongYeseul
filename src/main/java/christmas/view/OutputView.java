package christmas.view;

import christmas.dto.OrderDateOuputDTO;

import java.math.BigDecimal;
import java.text.NumberFormat;

import static christmas.view.constants.OuputMessage.ASK_MENU_AND_COUNT;
import static christmas.view.constants.OuputMessage.ORDER_MENU;
import static christmas.view.constants.OuputMessage.SHOW_EVENT_BENEFITS;
import static christmas.view.constants.OuputMessage.SPECIAL_GIFT;
import static christmas.view.constants.OuputMessage.START_EVENT_PLANNER_MESSAGE;

public class OutputView {
    public void startEventPlanner(){
        print(START_EVENT_PLANNER_MESSAGE);
    }

    public void askMenuAndCount() {
        print(ASK_MENU_AND_COUNT);
    }

    public void startBenefitPreview(OrderDateOuputDTO orderDateOuputDTO) {
        print(String.format(SHOW_EVENT_BENEFITS, 12, orderDateOuputDTO.date()));
    }

    public void orderMenuList(String orderList) {
        print(orderList);
    }

    private void printBigDecimal(BigDecimal number) {
        NumberFormat format = NumberFormat.getInstance();
        System.out.println(format.format(number));
    }

    public void totalOrderPrice(BigDecimal totalPrice) {
        print(ORDER_MENU);
        printBigDecimal(totalPrice);
    }

    public void specialGift(String menu) {
        println();
        print(SPECIAL_GIFT);
        print(menu);
    }

    private void print(String message) {
        System.out.println(message);
    }

    private void println(){
        System.out.print(System.lineSeparator());
    }
}
