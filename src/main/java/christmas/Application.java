package christmas;

import christmas.controller.MainController;
import christmas.service.OrderDateService;
import christmas.service.OrderMenuService;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;

public class Application {
    public static void main(String[] args) {
        MainController mainController = new MainController(
                new InputView(),
                new OutputView(),
                new OrderDateService(),
                new OrderMenuService(),
                new OrderService()
        );

        mainController.runEventPlanner();
    }
}
