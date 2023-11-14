package christmas.controller;

import christmas.dto.OrderDateOuputDTO;
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
        DateController dateController = new DateController(inputView, outputView, orderDateService);
        MenuController menuController = new MenuController(inputView, outputView, orderMenuService);

        OrderDateOuputDTO orderDateOuputDTO = dateController.askVisitDate();
        OrderMenuOutputDTO orderMenuOutputDTO = menuController.askOrder();

        showBenefits(orderDateOuputDTO, orderMenuOutputDTO);
        showEventBadge(totalBenefit);
        inputView.close();
    }

    /**
     * 모든 이벤트 혜택 출력 값을 처리하는 메서드
     */
    private void showBenefits(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOutputDTO orderMenuOutputDTO) {
        outputView.startBenefitPreview(orderDateOuputDTO);
        outputView.orderMenuList(orderMenuOutputDTO.getOrderList());

        outputView.totalOrderPrice(orderMenuService.calculateTotal(orderMenuOutputDTO));
        outputView.specialGift(orderMenuService.specialGift(orderMenuOutputDTO));

        grantAdditionalBenefits(orderDateOuputDTO, orderMenuOutputDTO);

        showResultPrice(totalBenefit, orderMenuOutputDTO);
    }

    public void grantAdditionalBenefits(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOutputDTO orderMenuOutputDTO) {
        if (haveBenefit(orderMenuOutputDTO)) {
            showBenefitsDetail(orderDateOuputDTO, orderMenuOutputDTO, HAVE_BENEFIT);
            showTotalBenefit(orderDateOuputDTO, orderMenuOutputDTO, HAVE_BENEFIT);
            return;
        }
        showBenefitsDetail(orderDateOuputDTO, new OrderMenuOutputDTO(), NONE_BENEFIT);
        showTotalBenefit(orderDateOuputDTO, orderMenuOutputDTO, NONE_BENEFIT);
    }

    private boolean haveBenefit(OrderMenuOutputDTO orderMenuOutputDTO) {
        return orderMenuService.calculateTotal(orderMenuOutputDTO)
                .compareTo(MINIMUM_DISCOUNT_AMOUNT) > BIG_DECIMAL_FLAG_THRESHOLD;
    }

    /**
     * showBenefits()에서 호출
     * <혜택 내역>을 출력하는 메서드
     */
    private void showBenefitsDetail(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOutputDTO orderMenuOutputDTO, boolean isHaveBenefit) {
        if (isHaveBenefit) {
            showHaveBenefitDetail(orderDateOuputDTO, orderMenuOutputDTO);
            return;
        }
        outputView.benefitsDetail(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private void showHaveBenefitDetail(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOutputDTO orderMenuOutputDTO) {
        outputView.benefitsDetail(
                orderDateService.calculateDDaySalePrice(orderDateOuputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOutputDTO),
                orderDateService.calculateWeekendSalePrice(orderDateOuputDTO, orderMenuOutputDTO),
                orderDateService.calculateSpecialSalePrice(orderDateOuputDTO),
                orderMenuService.getGiftPrice(orderMenuService.hasAdditionalGift(orderMenuOutputDTO))
        );
    }

    /**
     * showBenefits()에서 호출
     * <총 혜택 금액>을 출력하는 메서드
     */
    private void showTotalBenefit(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOutputDTO orderMenuOutputDTO, boolean isHaveBenefit) {
        if (isHaveBenefit) {
            totalBenefit = orderService.calculateTotalSalePrice(
                    orderDateService.calculateDDaySalePrice(orderDateOuputDTO),
                    orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOutputDTO),
                    orderDateService.calculateWeekendSalePrice(orderDateOuputDTO, orderMenuOutputDTO),
                    orderDateService.calculateSpecialSalePrice(orderDateOuputDTO),
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
     * <이벤트 배지> 출력하는 메서드
     */
    private void showEventBadge(BigDecimal totalBenefit) {
        outputView.showEventBadge(orderService.makeEventBadge(totalBenefit));
    }
}
