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

    /**
     * 고객으로부터 주문할 메뉴와 개수를 입력받는 메서드
     */
    public OrderMenuOuputDTO askOrder() {
        // TODO: 예외 while문 개선
        while (true) {
            try {
                outputView.askMenuAndCount();
                return orderMenuService.inputOrder(inputView.readMenuAndCount());
            } catch (IllegalArgumentException exception) {
                outputView.errorMessage(exception.getMessage());
            }
        }
    }
}
