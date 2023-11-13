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
        println();
        print(BENEFIT);
        print(
                dDayBenefit(dDay)
                + weekdayBenefit(weekDay)
                + weekendBenefit(weekend)
                + specialDayBenefit(special)
                + giftBenefit(gift)
        );
    }

    public String dDayBenefit(BigDecimal dDay) {
        if (dDay.signum() == 1) {
            return CHRISTMAS_D_DAY_DISCOUNT + bigDecimalToString(dDay) + System.lineSeparator();
        }
        return "";
    }

    public String weekdayBenefit(BigDecimal weekday) {
        if (weekday.signum() == 1) {
            return WEEKDAY_DISCOUNT + bigDecimalToString(weekday) + System.lineSeparator();
        }
        return "";
    }

    public String weekendBenefit(BigDecimal weekend) {
        if (weekend.signum() == 1) {
            return WEEKEND_DISCOUNT + bigDecimalToString(weekend) + System.lineSeparator();
        }
        return "";
    }

    public String specialDayBenefit(BigDecimal specialDay) {
        if (specialDay.signum() == 1) {
            return SPECIAL_DISCOUNT + bigDecimalToString(specialDay) + System.lineSeparator();
        }
        return "";
    }

    public String giftBenefit(BigDecimal gift) {
        if (gift.signum() == 1) {
            return GIFT_DISCOUNT + bigDecimalToString(gift) + System.lineSeparator();
        }
        return "";
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
