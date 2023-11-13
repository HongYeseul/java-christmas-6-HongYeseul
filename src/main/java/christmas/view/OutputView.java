package christmas.view;

import christmas.dto.OrderDateOuputDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static christmas.view.constants.OuputMessage.ASK_MENU_AND_COUNT;
import static christmas.view.constants.OuputMessage.BENEFIT;
import static christmas.view.constants.OuputMessage.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.view.constants.OuputMessage.GIFT_DISCOUNT;
import static christmas.view.constants.OuputMessage.ORDER_MENU;
import static christmas.view.constants.OuputMessage.SHOW_EVENT_BENEFITS;
import static christmas.view.constants.OuputMessage.SPECIAL_DISCOUNT;
import static christmas.view.constants.OuputMessage.SPECIAL_GIFT;
import static christmas.view.constants.OuputMessage.START_EVENT_PLANNER_MESSAGE;
import static christmas.view.constants.OuputMessage.WEEKDAY_DISCOUNT;
import static christmas.view.constants.OuputMessage.WEEKEND_DISCOUNT;

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

    public void totalOrderPrice(BigDecimal totalPrice) {
        print(ORDER_MENU);
        printBigDecimal(totalPrice);
    }

    public void specialGift(String menu) {
        println();
        print(SPECIAL_GIFT);
        print(menu);
    }

    public void benefitsDetail(BigDecimal dDay, BigDecimal weekDay, BigDecimal weekend, BigDecimal special, BigDecimal gift) {
        print(BENEFIT);
        print(
                CHRISTMAS_D_DAY_DISCOUNT + bigDecimalToString(dDay)
                        + WEEKDAY_DISCOUNT + bigDecimalToString(weekDay)
                        + WEEKEND_DISCOUNT + bigDecimalToString(weekend)
                        + SPECIAL_DISCOUNT + bigDecimalToString(special)
                        + GIFT_DISCOUNT + bigDecimalToString(gift)
        );
    }


    private void print(String message) {
        System.out.println(message);
    }

    private void println(){
        System.out.print(System.lineSeparator());
    }

    private void printBigDecimal(BigDecimal number) {
        System.out.println(bigDecimalToString(number));
    }

    private String bigDecimalToString(BigDecimal number){
        NumberFormat format = new DecimalFormat("#,###원");
        return format.format(number);
    }
}
