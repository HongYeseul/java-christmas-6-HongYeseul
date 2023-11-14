package christmas.controller;

import christmas.controller.handler.RetryHandler;
import christmas.dto.OrderDateOutputDTO;
import christmas.service.OrderDateService;
import christmas.view.InputView;

public class DateController extends RetryHandler<OrderDateOutputDTO> {
    private final InputView inputView;
    private final OrderDateService orderDateService;
    public DateController(InputView inputView, OrderDateService orderDateService){
        this.inputView = inputView;
        this.orderDateService = orderDateService;
    }

    /**
     * 고객으로부터 식당 예상 방문 날짜 입력 받는 메서드
     */
    @Override
    public OrderDateOutputDTO doProcess() {
        return orderDateService.inputOrderDate(inputView.readVisitDate());
    }
}
