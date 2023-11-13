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

    public OrderDateOuputDTO askVisitDate() {
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
