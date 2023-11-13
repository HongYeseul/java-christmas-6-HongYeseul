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
    private OrderController orderController;
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
    }

    private void showBenefits(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.startBenefitPreview(orderDateOuputDTO);
        outputView.orderMenuList(orderMenuOuputDTO.getOrderList());

        outputView.totalOrderPrice(orderMenuService.calculateTotal(orderMenuOuputDTO));
        outputView.specialGift(orderMenuService.specialGift(orderMenuOuputDTO));

        showBenefitsDetail(orderDateOuputDTO, orderMenuOuputDTO);
        showTotalBenefit(orderDateOuputDTO, orderMenuOuputDTO);
        showResultPrice(totalBenefit, orderDateOuputDTO, orderMenuOuputDTO);
    }

    private void showBenefitsDetail(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.benefitsDetail(
                orderDateService.calculateDDaySalePrice(orderDateOuputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOuputDTO),
                orderDateService.calculateWeekendSalePrice(orderDateOuputDTO, orderMenuOuputDTO),
                orderDateService.calculateSpecialSalePrice(orderDateOuputDTO),
                orderDateService.calculateGiftPrice(orderMenuService.hasAdditionalGift(orderMenuOuputDTO))
        );
    }

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

    private void showResultPrice(BigDecimal totalBenefit, OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        BigDecimal resultBenefit = orderService.calculateTotalCharge(
                orderMenuService.calculateTotal(orderMenuOuputDTO),
                totalBenefit,
                orderMenuService.hasAdditionalGift(orderMenuOuputDTO));
        outputView.showResultPrice(resultBenefit);
    }

    private void showEventBadge(BigDecimal totalBenefit) {
        outputView.showEventBadge(orderService.makeEventBadge(totalBenefit));
    }
}
