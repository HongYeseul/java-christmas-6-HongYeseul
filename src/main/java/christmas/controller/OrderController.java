package christmas.controller;

import christmas.dto.OrderDateOuputDTO;
import christmas.dto.OrderMenuOuputDTO;
import christmas.service.OrderMenuService;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class OrderController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderMenuService orderMenuService;
    private final OrderService orderService;
    private final OrderDateOuputDTO orderDateOuputDTO;
    private final OrderMenuOuputDTO orderMenuOuputDTO;

    public OrderController(InputView inputView,
                           OutputView outputView,
                           OrderMenuService orderMenuService,
                           OrderService orderService,
                           OrderDateOuputDTO orderDateOuputDTO,
                           OrderMenuOuputDTO orderMenuOuputDTO) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderMenuService = orderMenuService;
        this.orderService = orderService;
        this.orderDateOuputDTO = orderDateOuputDTO;
        this.orderMenuOuputDTO = orderMenuOuputDTO;
    }


    public void showBenefits() {
        outputView.startBenefitPreview(orderDateOuputDTO);
        outputView.orderMenuList(orderMenuOuputDTO.getOrderList());
    }
}
