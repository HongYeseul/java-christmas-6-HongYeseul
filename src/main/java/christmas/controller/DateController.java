package christmas.controller;

import christmas.dto.OrderDateOuputDTO;
import christmas.service.OrderDateService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class DateController {
    private final InputView inputView;
    private final OutputView outputView;
    private final OrderDateService orderDateService;
    public DateController(InputView inputView, OutputView outputView, OrderDateService orderDateService){
        this.inputView = inputView;
        this.outputView = outputView;
        this.orderDateService = orderDateService;
    }

    /**
     * 고객으로부터 식당 예상 방문 날짜 입력 받는 메서드
     */
    public OrderDateOuputDTO askVisitDate() {
        // TODO: 예외 while문 개선
        while (true) {
            try {
                outputView.startEventPlanner();
                return orderDateService.inputOrderDate(inputView.readVisitDate());
            } catch (IllegalArgumentException exception) {
                outputView.errorMessage(exception.getMessage());
            }
        }
    }
}
