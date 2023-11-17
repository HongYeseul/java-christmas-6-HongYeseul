package christmas.controller;

import christmas.dto.OrderDateOutputDTO;
import christmas.dto.OrderMenuOutputDTO;
import christmas.service.OrderDateService;
import christmas.service.OrderMenuService;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.math.BigDecimal;

import static christmas.model.constants.DiscountRate.MINIMUM_DISCOUNT_AMOUNT;
import static christmas.service.constants.Threshold.BIG_DECIMAL_FLAG_THRESHOLD;
import static christmas.service.constants.Threshold.HAVE_BENEFIT;
import static christmas.service.constants.Threshold.NONE_BENEFIT;

public class MainController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderDateService orderDateService;
    private final OrderMenuService orderMenuService;
    private final OrderService orderService;

    BigDecimal totalBenefit = BigDecimal.ZERO;

    public MainController(
            final InputView inputView,
            final OutputView outputView,
            final OrderDateService orderDateService,
            final OrderMenuService orderMenuService,
            final OrderService orderService
    ) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderDateService = orderDateService;
        this.orderMenuService = orderMenuService;
        this.orderService = orderService;
    }

    public void runEventPlanner(){
        DateController dateController = new DateController(inputView, orderDateService);
        MenuController menuController = new MenuController(inputView, orderMenuService);

        OrderDateOutputDTO orderDateOutputDTO = getVisitDate(dateController);
        OrderMenuOutputDTO orderMenuOutputDTO = getOrder(menuController);

        showBenefits(orderDateOutputDTO, orderMenuOutputDTO);
        showEventBadge(totalBenefit);
        inputView.close();
    }

    /**
     * 고객으로부터 식당 예상 방문 날짜를 입력 받는 메서드
     * 예외 발생시 재입력
     */
    public OrderDateOutputDTO getVisitDate(DateController dateController) {
        outputView.askPlannedDate();
        return dateController.process();
    }

    /**
     * 고객으로부터 주문할 메뉴와 메뉴 개수를 입력 받는 메서드
     * 예외 발생시 재입력
     */
    public OrderMenuOutputDTO getOrder(MenuController menuController) {
        outputView.askMenuAndCount();
        return menuController.process();
    }

    /**
     * 모든 이벤트 혜택 출력 값을 처리하는 메서드
     */
    private void showBenefits(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOutputDTO orderMenuOutputDTO) {
        outputView.startBenefitPreview(orderDateOutputDTO);
        outputView.orderMenuList(orderMenuOutputDTO.getOrderList());

        outputView.totalOrderPrice(orderMenuService.calculateTotal(orderMenuOutputDTO));
        outputView.specialGift(orderMenuService.specialGift(orderMenuOutputDTO));

        grantAdditionalBenefits(orderDateOutputDTO, orderMenuOutputDTO);

        showResultPrice(totalBenefit, orderMenuOutputDTO);
    }

    /**
     * 해당 주문이 혜택을 받을 수 있는지 검사 후 출력
     * (주문 금액 10,000 이상 이벤트 적용)
     */
    private void grantAdditionalBenefits(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOutputDTO orderMenuOutputDTO) {
        if (haveBenefit(orderMenuOutputDTO)) {
            showBenefitsDetail(orderDateOutputDTO, orderMenuOutputDTO, HAVE_BENEFIT);
            showTotalBenefit(orderDateOutputDTO, orderMenuOutputDTO, HAVE_BENEFIT);
            return;
        }
        showBenefitsDetail(orderDateOutputDTO, new OrderMenuOutputDTO(), NONE_BENEFIT);
        showTotalBenefit(orderDateOutputDTO, orderMenuOutputDTO, NONE_BENEFIT);
    }

    public boolean haveBenefit(OrderMenuOutputDTO orderMenuOutputDTO) {
        return orderMenuService.calculateTotal(orderMenuOutputDTO)
                .compareTo(MINIMUM_DISCOUNT_AMOUNT) > BIG_DECIMAL_FLAG_THRESHOLD;
    }

    /**
     * showBenefits()에서 호출
     * <혜택 내역>을 출력하는 메서드
     */
    private void showBenefitsDetail(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOutputDTO orderMenuOutputDTO, boolean isHaveBenefit) {
        if (isHaveBenefit) {
            showHaveBenefitDetail(orderDateOutputDTO, orderMenuOutputDTO);
            return;
        }
        outputView.benefitsDetail(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private void showHaveBenefitDetail(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOutputDTO orderMenuOutputDTO) {
        outputView.benefitsDetail(
                orderDateService.calculateDDaySalePrice(orderDateOutputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOutputDTO, orderMenuOutputDTO),
                orderDateService.calculateWeekendSalePrice(orderDateOutputDTO, orderMenuOutputDTO),
                orderDateService.calculateSpecialSalePrice(orderDateOutputDTO),
                orderMenuService.getGiftPrice(orderMenuService.hasAdditionalGift(orderMenuOutputDTO))
        );
    }

    /**
     * showBenefits()에서 호출
     * <총 혜택 금액>을 출력하는 메서드
     */
    private void showTotalBenefit(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOutputDTO orderMenuOutputDTO, boolean isHaveBenefit) {
        if (isHaveBenefit) {
            totalBenefit = orderService.calculateTotalSalePrice(
                    orderDateService.calculateDDaySalePrice(orderDateOutputDTO),
                    orderDateService.calculateWeekDaySalePrice(orderDateOutputDTO, orderMenuOutputDTO),
                    orderDateService.calculateWeekendSalePrice(orderDateOutputDTO, orderMenuOutputDTO),
                    orderDateService.calculateSpecialSalePrice(orderDateOutputDTO),
                    orderMenuService.hasAdditionalGift(orderMenuOutputDTO)
            );
        }
        outputView.showTotalBenefitPrice(totalBenefit);
    }

    /**
     * showBenefits()에서 호출
     * <할인 후 예상 결제 금액>을 출력하는 메서드
     */
    private void showResultPrice(BigDecimal totalBenefit, OrderMenuOutputDTO orderMenuOutputDTO) {
        BigDecimal resultBenefit = orderService.calculateTotalCharge(
                orderMenuService.calculateTotal(orderMenuOutputDTO),
                totalBenefit,
                orderMenuService.hasAdditionalGift(orderMenuOutputDTO));
        outputView.showResultPrice(resultBenefit);
    }

    /**
     * <이벤트 배지>를 출력하는 메서드
     */
    private void showEventBadge(BigDecimal totalBenefit) {
        outputView.showEventBadge(orderService.makeEventBadge(totalBenefit));
    }
}
