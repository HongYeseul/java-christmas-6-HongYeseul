package christmas.view.constants;

public class UserInterfaceMessage {
    /**
     * 이벤트 플래너 UI 출력용 메시지
     */
    public static final String START_EVENT_PLANNER_MESSAGE = "안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.";
    public static final String SHOW_EVENT_BENEFITS = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!";

    /**
     * 고객에게 입력을 요청하는 메시지
     */
    public static final String ASK_PLANNED_DATE = "%d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)";
    public static final String ASK_MENU_AND_COUNT = "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    /**
     * 이벤트 플래너 결과 출력용 메시지
     */
    public static final String ORDER_MENU = "<주문 메뉴>";
    public static final String BEFORE_BENEFIT_TOTAL_PRICE = "<할인 전 총주문 금액>";
    public static final String SPECIAL_GIFT = "<증정 메뉴>";
    public static final String BENEFIT = "<혜택 내역>";
    public static final String TOTAL_BENEFIT_PRICE = "<총혜택 금액>";
    public static final String AFTER_DISCOUNT_TOTAL_PRICE = "<할인 후 예상 결제 금액>";
    public static final String EVENT_BADGE = "<%d월 이벤트 배지>";

    /**
     * 할인 내역 출력용 메시지
     */
    public static final String CHRISTMAS_D_DAY_DISCOUNT = "크리스마스 디데이 할인: -";
    public static final String WEEKDAY_DISCOUNT = "평일 할인: -";
    public static final String WEEKEND_DISCOUNT = "주말 할인: -";
    public static final String SPECIAL_DISCOUNT = "특별 할인: -";
    public static final String GIFT_DISCOUNT = "증정 이벤트: -";
}
