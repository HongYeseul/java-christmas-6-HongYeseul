package christmas.controller;

import christmas.dto.OrderMenuOuputDTO;
import christmas.service.OrderMenuService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class MenuController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderMenuService orderMenuService;
    public MenuController(InputView inputView, OutputView outputView, OrderMenuService orderMenuService){
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderMenuService = orderMenuService;
    }

    public OrderMenuOuputDTO askOrder() {
        outputView.askMenuAndCount();
        return orderMenuService.inputOrder(inputView.readMenuAndCount());
    }
}
