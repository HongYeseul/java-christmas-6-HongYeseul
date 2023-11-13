package christmas.controller;

import christmas.dto.OrderDateOuputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.service.OrderDateService;
import christmas.service.OrderMenuService;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class MainController {
    private DateController dateController;
    private MenuController menuController;
    private OrderController orderController;
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderDateService orderDateService;
    private final OrderMenuService orderMenuService;
    private final OrderService orderService;
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
    }

    private void showBenefits(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.startBenefitPreview(orderDateOuputDTO);
        outputView.orderMenuList(orderMenuOuputDTO.getOrderList());

        outputView.totalOrderPrice(orderMenuService.calculateTotal(orderMenuOuputDTO));
        outputView.specialGift(orderMenuService.specialGift(orderMenuOuputDTO));

        showBenefitsDetail(orderDateOuputDTO, orderMenuOuputDTO);
    }

    private void showBenefitsDetail(OrderDateOuputDTO orderDateOuputDTO, OrderMenuOuputDTO orderMenuOuputDTO) {
        outputView.benefitsDetail(
                orderDateService.calculateDDaySalePrice(orderDateOuputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOuputDTO, orderMenuOuputDTO),
                orderDateService.calculateWeekendSalePrice(null, null),
                orderDateService.calculateSpecialSalePrice(null),
                orderDateService.calculateGiftPrice()
        );
    }
}
