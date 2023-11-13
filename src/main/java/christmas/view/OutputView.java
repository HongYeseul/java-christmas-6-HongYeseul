package christmas.view;

import christmas.dto.OrderDateOuputDTO;

import java.math.BigDecimal;

import static christmas.model.constants.Day.MONTH;
import static christmas.view.constants.PriceFormat.KRW_FORMAT;
import static christmas.view.constants.Threshold.MAKE_RESULT_NEGATIVE;
import static christmas.view.constants.Threshold.NO_ITEM;
import static christmas.view.constants.Threshold.NO_QUANTITY;
import static christmas.view.constants.Threshold.POSITIVE_RESULT_THRESHOLD;
import static christmas.view.constants.UserInterfaceMessage.AFTER_DISCOUNT_TOTAL_PRICE;
import static christmas.view.constants.UserInterfaceMessage.ASK_MENU_AND_COUNT;
import static christmas.view.constants.UserInterfaceMessage.BEFORE_BENEFIT_TOTAL_PRICE;
import static christmas.view.constants.UserInterfaceMessage.BENEFIT;
import static christmas.view.constants.UserInterfaceMessage.CHRISTMAS_D_DAY_DISCOUNT;
import static christmas.view.constants.UserInterfaceMessage.EVENT_BADGE;
import static christmas.view.constants.UserInterfaceMessage.GIFT_DISCOUNT;
import static christmas.view.constants.UserInterfaceMessage.ORDER_MENU;
import static christmas.view.constants.UserInterfaceMessage.SHOW_EVENT_BENEFITS;
import static christmas.view.constants.UserInterfaceMessage.SPECIAL_DISCOUNT;
import static christmas.view.constants.UserInterfaceMessage.SPECIAL_GIFT;
import static christmas.view.constants.UserInterfaceMessage.START_EVENT_PLANNER_MESSAGE;
import static christmas.view.constants.UserInterfaceMessage.TOTAL_BENEFIT_PRICE;
import static christmas.view.constants.UserInterfaceMessage.WEEKDAY_DISCOUNT;
import static christmas.view.constants.UserInterfaceMessage.WEEKEND_DISCOUNT;
import static christmas.view.validator.ExceptionMessage.ERROR_TAG;

public class OutputView {
    public void startEventPlanner(){
        print(START_EVENT_PLANNER_MESSAGE);
    }

    public void askMenuAndCount() {
        print(ASK_MENU_AND_COUNT);
    }

    public void startBenefitPreview(OrderDateOuputDTO orderDateOuputDTO) {
        print(String.format(SHOW_EVENT_BENEFITS, MONTH, orderDateOuputDTO.date()));
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
            print(NO_ITEM);
            println();
            return;
        }
        printbenefitList(dDay, weekDay, weekend, special, gift);
    }

    private boolean isNoneBenefit(BigDecimal dDay, BigDecimal weekDay, BigDecimal weekend, BigDecimal special, BigDecimal gift) {
        return ((dDay.signum() == NO_QUANTITY)
                && (weekDay.signum() == NO_QUANTITY)
                && (weekend.signum() == NO_QUANTITY)
                && (special.signum() == NO_QUANTITY)
                && (gift.signum() == NO_QUANTITY));
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
        if (dDay.signum() == POSITIVE_RESULT_THRESHOLD) {
            return CHRISTMAS_D_DAY_DISCOUNT + bigDecimalToString(dDay) + System.lineSeparator();
        }
        return "";
    }

    public String weekdayBenefit(BigDecimal weekday) {
        if (weekday.signum() == POSITIVE_RESULT_THRESHOLD) {
            return WEEKDAY_DISCOUNT + bigDecimalToString(weekday) + System.lineSeparator();
        }
        return "";
    }

    public String weekendBenefit(BigDecimal weekend) {
        if (weekend.signum() == POSITIVE_RESULT_THRESHOLD) {
            return WEEKEND_DISCOUNT + bigDecimalToString(weekend) + System.lineSeparator();
        }
        return "";
    }

    public String specialDayBenefit(BigDecimal specialDay) {
        if (specialDay.signum() == POSITIVE_RESULT_THRESHOLD) {
            return SPECIAL_DISCOUNT + bigDecimalToString(specialDay) + System.lineSeparator();
        }
        return "";
    }

    public String giftBenefit(BigDecimal gift) {
        if (gift.signum() == POSITIVE_RESULT_THRESHOLD) {
            return GIFT_DISCOUNT + bigDecimalToString(gift) + System.lineSeparator();
        }
        return "";
    }

    public void showTotalBenefitPrice(BigDecimal totalBenefit) {
        print(TOTAL_BENEFIT_PRICE);
        printBigDecimal(totalBenefit.multiply(MAKE_RESULT_NEGATIVE));
    }

    public void showResultPrice(BigDecimal result) {
        println();
        print(AFTER_DISCOUNT_TOTAL_PRICE);
        printBigDecimal(result);
    }

    public void showEventBadge(String badge) {
        println();
        print(String.format(EVENT_BADGE, MONTH));
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
        return KRW_FORMAT.format(number);
    }
}
