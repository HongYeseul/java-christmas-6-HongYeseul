package christmas.controller;

import christmas.controller.handler.RetryHandler;
import christmas.dto.OrderMenuOutputDTO;
import christmas.service.OrderMenuService;
import christmas.view.InputView;

public class MenuController extends RetryHandler<OrderMenuOutputDTO> {
    private final InputView inputView;
    private final OrderMenuService orderMenuService;
    public MenuController(InputView inputView, OrderMenuService orderMenuService){
        this.inputView = inputView;
        this.orderMenuService = orderMenuService;
    }

    /**
     * 고객으로부터 주문할 메뉴와 개수를 입력받는 메서드
     */
    @Override
    public OrderMenuOutputDTO doProcess() {
        return orderMenuService.inputOrder(inputView.readMenuAndCount());
    }
}
