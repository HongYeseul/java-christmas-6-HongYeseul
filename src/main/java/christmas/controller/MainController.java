package christmas.controller;

import christmas.dto.OrderDateOuputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.service.OrderDateService;
import christmas.service.OrderMenuService;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

import java.math.BigDecimal;

public class MainController {
    private DateController dateController;
    private MenuController menuController;
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderDateService orderDateService;
    private final OrderMenuService orderMenuService;
    private final OrderService orderService;

    BigDecimal totalBenefit;

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
        dateController = new DateController(inputView, outputView, orderDateService);
        menuController = new MenuController(inputView, outputView, orderMenuService);

        OrderDateOuputDTO orderDateOuputDTO = dateController.askVisitDate();
        OrderMenuOuputDTO orderMenuOuputDTO = menuController.askOrder();

        showBenefits(orderDateOuputDTO, orderMenuOuputDTO);
        showEventBadge(totalBenefit);
        inputView.close();
    }

    /**
     * 모든 이벤트 혜택 출력 값을 처리하는 메서드
     */
    private void showBenefits(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.startBenefitPreview(orderDateOuputDTO);
        outputView.orderMenuList(orderMenuOuputDTO.getOrderList());

        outputView.totalOrderPrice(orderMenuService.calculateTotal(orderMenuOuputDTO));
        outputView.specialGift(orderMenuService.specialGift(orderMenuOuputDTO));

        showBenefitsDetail(orderDateOuputDTO, orderMenuOuputDTO);
        showTotalBenefit(orderDateOuputDTO, orderMenuOuputDTO);
        showResultPrice(totalBenefit, orderMenuOuputDTO);
    }

    /**
     * showBenefits()에서 호출
     * <혜택 내역>을 출력하는 메서드
     */
    private void showBenefitsDetail(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.benefitsDetail(
                orderDateService.calculateDDaySalePrice(orderDateOuputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOuputDTO),
                orderDateService.calculateWeekendSalePrice(orderDateOuputDTO, orderMenuOuputDTO),
                orderDateService.calculateSpecialSalePrice(orderDateOuputDTO),
                orderDateService.calculateGiftPrice(orderMenuService.hasAdditionalGift(orderMenuOuputDTO))
        );
    }

    /**
     * showBenefits()에서 호출
     * <총 혜택 금액>을 출력하는 메서드
     */
    private void showTotalBenefit(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        totalBenefit = orderService.calculateTotalSalePrice(
                orderDateService.calculateDDaySalePrice(orderDateOuputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOuputDTO),
                orderDateService.calculateWeekendSalePrice(orderDateOuputDTO, orderMenuOuputDTO),
                orderDateService.calculateSpecialSalePrice(orderDateOuputDTO),
                orderMenuService.hasAdditionalGift(orderMenuOuputDTO)
        );
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
