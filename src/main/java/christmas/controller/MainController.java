package christmas.controller;

import christmas.dto.OrderDateOutputDTO;
import christmas.dto.OrderMenuOuputDTO;
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

        OrderDateOutputDTO orderDateOutputDTO = dateController.askVisitDate();
        OrderMenuOuputDTO orderMenuOuputDTO = menuController.askOrder();

        showBenefits(orderDateOutputDTO, orderMenuOuputDTO);
        showEventBadge(totalBenefit);
        inputView.close();
    }

    /**
     * 모든 이벤트 혜택 출력 값을 처리하는 메서드
     */
    private void showBenefits(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.startBenefitPreview(orderDateOutputDTO);
        outputView.orderMenuList(orderMenuOuputDTO.getOrderList());

        outputView.totalOrderPrice(orderMenuService.calculateTotal(orderMenuOuputDTO));
        outputView.specialGift(orderMenuService.specialGift(orderMenuOuputDTO));

        grantAdditionalBenefits(orderDateOutputDTO, orderMenuOuputDTO);

        showResultPrice(totalBenefit, orderMenuOuputDTO);
    }

    public void grantAdditionalBenefits(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        if (haveBenefit(orderMenuOuputDTO)) {
            showBenefitsDetail(orderDateOutputDTO, orderMenuOuputDTO, HAVE_BENEFIT);
            showTotalBenefit(orderDateOutputDTO, orderMenuOuputDTO, HAVE_BENEFIT);
            return;
        }
        showBenefitsDetail(orderDateOutputDTO, new OrderMenuOuputDTO(), NONE_BENEFIT);
        showTotalBenefit(orderDateOutputDTO, orderMenuOuputDTO, NONE_BENEFIT);
    }

    private boolean haveBenefit(OrderMenuOuputDTO orderMenuOuputDTO) {
        return orderMenuService.calculateTotal(orderMenuOuputDTO)
                .compareTo(MINIMUM_DISCOUNT_AMOUNT) > BIG_DECIMAL_FLAG_THRESHOLD;
    }

    /**
     * showBenefits()에서 호출
     * <혜택 내역>을 출력하는 메서드
     */
    private void showBenefitsDetail(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOuputDTO orderMenuOuputDTO, boolean isHaveBenefit) {
        if (isHaveBenefit) {
            showHaveBenefitDetail(orderDateOutputDTO, orderMenuOuputDTO);
            return;
        }
        outputView.benefitsDetail(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
    }

    private void showHaveBenefitDetail(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.benefitsDetail(
                orderDateService.calculateDDaySalePrice(orderDateOutputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOutputDTO, orderMenuOuputDTO),
                orderDateService.calculateWeekendSalePrice(orderDateOutputDTO, orderMenuOuputDTO),
                orderDateService.calculateSpecialSalePrice(orderDateOutputDTO),
                orderMenuService.getGiftPrice(orderMenuService.hasAdditionalGift(orderMenuOuputDTO))
        );
    }

    /**
     * showBenefits()에서 호출
     * <총 혜택 금액>을 출력하는 메서드
     */
    private void showTotalBenefit(OrderDateOutputDTO orderDateOutputDTO, OrderMenuOuputDTO orderMenuOuputDTO, boolean isHaveBenefit) {
        if (isHaveBenefit) {
            totalBenefit = orderService.calculateTotalSalePrice(
                    orderDateService.calculateDDaySalePrice(orderDateOutputDTO),
                    orderDateService.calculateWeekDaySalePrice(orderDateOutputDTO, orderMenuOuputDTO),
                    orderDateService.calculateWeekendSalePrice(orderDateOutputDTO, orderMenuOuputDTO),
                    orderDateService.calculateSpecialSalePrice(orderDateOutputDTO),
                    orderMenuService.hasAdditionalGift(orderMenuOuputDTO)
            );
        }
        outputView.showTotalBenefitPrice(totalBenefit);
    }

    /**
     * showBenefits()에서 호출
     * <할인 후 예상 결제 금액>을 출력하는 메서드
     */
    private void showResultPrice(BigDecimal totalBenefit, OrderMenuOuputDTO orderMenuOuputDTO) {
        BigDecimal resultBenefit = orderService.calculateTotalCharge(
                orderMenuService.calculateTotal(orderMenuOuputDTO),
                totalBenefit,
                orderMenuService.hasAdditionalGift(orderMenuOuputDTO));
        outputView.showResultPrice(resultBenefit);
    }

    /**
     * <이벤트 배지> 출력하는 메서드
     */
    private void showEventBadge(BigDecimal totalBenefit) {
        outputView.showEventBadge(orderService.makeEventBadge(totalBenefit));
    }
}
