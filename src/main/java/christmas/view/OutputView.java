package christmas.view;

import christmas.dto.OrderDateOuputDTO;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import static christmas.view.constants.OuputMessage.AFTER_DISCOUNT_TOTAL_PRICE;
import static christmas.view.constants.OuputMessage.ASK_MENU_AND_COUNT;
import static christmas.view.constants.OuputMessage.BEFORE_BENEFIT_TOTAL_PRICE;
import static christmas.view.constants.OuputMessage.BENEFIT;
import static christmas.view.constants.OuputMessage.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.view.constants.OuputMessage.EVENT_BADGE;
import static christmas.view.constants.OuputMessage.GIFT_DISCOUNT;
import static christmas.view.constants.OuputMessage.ORDER_MENU;
import static christmas.view.constants.OuputMessage.SHOW_EVENT_BENEFITS;
import static christmas.view.constants.OuputMessage.SPECIAL_DISCOUNT;
import static christmas.view.constants.OuputMessage.SPECIAL_GIFT;
import static christmas.view.constants.OuputMessage.START_EVENT_PLANNER_MESSAGE;
import static christmas.view.constants.OuputMessage.TOTAL_BENEFIT_PRICE;
import static christmas.view.constants.OuputMessage.WEEKDAY_DISCOUNT;
import static christmas.view.constants.OuputMessage.WEEKEND_DISCOUNT;
import static christmas.view.validator.ExceptionMessage.ERROR_TAG;

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
        println();
        print(ORDER_MENU);
        print(orderList);
    }

    public void totalOrderPrice(BigDecimal totalPrice) {
        print(BEFORE_BENEFIT_TOTAL_PRICE);
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
        if(isNoneBenefit(dDay, weekDay, weekend, special, gift)){
            print("없음");
            println();
            return;
        }
        printbenefitList(dDay, weekDay, weekend, special, gift);
    }

    private boolean isNoneBenefit(BigDecimal dDay, BigDecimal weekDay, BigDecimal weekend, BigDecimal special, BigDecimal gift) {
        return ((dDay.signum() == 0)
                && (weekDay.signum() == 0)
                && (weekend.signum() == 0)
                && (special.signum() == 0)
                && (gift.signum() == 0));
    }

    private void printbenefitList(BigDecimal dDay, BigDecimal weekDay, BigDecimal weekend, BigDecimal special, BigDecimal gift) {
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

    public void showTotalBenefitPrice(BigDecimal totalBenefit) {
        print(TOTAL_BENEFIT_PRICE);
        printBigDecimal(totalBenefit.multiply(new BigDecimal("-1")));
    }

    public void showResultPrice(BigDecimal result) {
        println();
        print(AFTER_DISCOUNT_TOTAL_PRICE);
        printBigDecimal(result);
    }

    public void showEventBadge(String badge) {
        println();
        print(String.format(EVENT_BADGE, 12));
        print(badge);
    }

    public void errorMessage(String message) {
        print(ERROR_TAG + message);
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
