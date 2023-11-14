package christmas.controller;

import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOutputDTO;
import christmas.service.OrderDateService;
import christmas.service.OrderMenuService;
import christmas.service.OrderService;
import christmas.view.InputView;
import christmas.view.OutputView;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


public class MainControllerTest {
    MainController mainController = new MainController(
            new InputView(),
            new OutputView(),
            new OrderDateService(),
            new OrderMenuService(),
            new OrderService()
    );
    OrderMenuService orderMenuService = new OrderMenuService();

    @DisplayName("[SUCCESS] 총 주문 금액이 10,000 미만이라면 이벤트 적용이 되지 않는다.")
    @ParameterizedTest(name = "주문 메뉴 및 개수: {0}, 반환되어야 하는 값(혜택을 받을 수 있는가): {1}")
    @CsvSource({"'아이스크림-2', false",
            "'티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1', true",
            "'타파스-1,제로콜라-1', false"})
    void orderLowerThenMinimumPriceOfEventBenefit(String input) {
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        OrderMenuOutputDTO orderMenuOutputDTO = orderMenuService.inputOrder(orderMenuInputDTO);

        mainController.haveBenefit(orderMenuOutputDTO);
    }
}
