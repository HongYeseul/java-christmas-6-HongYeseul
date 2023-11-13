package christmas.controller;

import christmas.service.OrderDateService;
import christmas.service.OrderMenuService;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class MainController {
    private DateController dateController;
    private MenuController menuController;
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


    }
}
