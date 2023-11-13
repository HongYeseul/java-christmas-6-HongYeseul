package christmas.controller;

import christmas.dto.OrderDateInputDTO;
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

    public OrderDateInputDTO askVisitDate() {
        outputView.startEventPlanner();
        return new OrderDateInputDTO(inputView.readVisitDate());
    }
}
