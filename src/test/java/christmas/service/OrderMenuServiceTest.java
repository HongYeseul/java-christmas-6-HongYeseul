package christmas.service;

import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOuputDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class OrderMenuServiceTest {
    OrderMenuService orderMenuService = new OrderMenuService();

    @DisplayName("[SUCCESS] 주문을 정상적인 값으로 입력하면 예외가 발생하지 않는다.")
    @ValueSource(strings = {"티본스테이크-1,바비큐립-1,초코케이크-2",
            "초코케이크-1",
            "양송이수프-2,시저샐러드-4"})
    @ParameterizedTest
    void inputNormalOrder(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        orderMenuService.inputOrder(orderMenuInputDTO);
    }

    @DisplayName("[ERROR] 메뉴를 20개보다 많이 주문하면 예외가 발생한다.")
    @ValueSource(strings = {"티본스테이크-10,바비큐립-5,초코케이크-6",
            "초코케이크-27",
            "시저샐러드-5,티본스테이크-4,크리스마스파스타-5,제로콜라-5,아이스크림6"})
    @ParameterizedTest
    void orderOverThenNormalQuantity(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[ERROR] 음료만 주문하면 예외가 발생한다.")
    @ValueSource(strings = {"레드와인-6",
            "제로콜라-1,레드와인-2",
            "레드와인-2,제로콜라-3,샴페인-1"})
    @ParameterizedTest
    void orderOnlyDrinks(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[ERROR] 중복된 메뉴 주문을 하면 예외가 발생한다.")
    @ValueSource(strings = {"티본스테이크-1,티본스테이크-3",
            "제로콜라-1,제로콜라-2",
            "레드와인-2,타파스-3,타파스-1"})
    @ParameterizedTest
    void orderDuplicateMenu(String input){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        assertThatThrownBy(() -> orderMenuService.inputOrder(orderMenuInputDTO))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("[SUCCESS] 총 주문 금액을 계산할 수 있다.")
    @ParameterizedTest(name = "주문 메뉴 및 개수: {0}, 반환되어야 하는 총 주문 금액: {1}")
    @CsvSource({"'타파스-1,제로콜라-1', 8500",
            "'티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1', 142000",
            "'아이스크림-2', 10000"})
    void calculateTotal(String input, BigDecimal totalPrice){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        BigDecimal total = orderMenuService.calculateTotal(orderMenuService.inputOrder(orderMenuInputDTO));
        assertThat(total).isEqualTo(totalPrice);
    }

    @DisplayName("[SUCCESS] 증정 이벤트를 하면 샴페인 가격을 반환한다.")
    @ParameterizedTest(name = "샴페인을 받았는가: {0}, 반환 값(증정 받은 제품의 가격): {1}")
    @CsvSource({"true, 25000", "false, 0"})
    void getGiftPrice(boolean hasGift, BigDecimal expectedGiftPrice){
        BigDecimal giftPrice = orderMenuService.getGiftPrice(hasGift);
        assertThat(giftPrice).isEqualTo(expectedGiftPrice);
    }

    @DisplayName("[SUCCESS] 총 주문 금액에 따른 증정 메뉴가 있는지 판단한다.")
    @ParameterizedTest(name = "주문 메뉴 및 개수: {0}, 반환 값(증정 메뉴가 있는가): {1}")
    @CsvSource({"'타파스-1,제로콜라-1', false",
            "'티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1', true",
            "'티본스테이크-2,아이스크림-2', true"})
    void isAdditionalGift(String input, boolean haveAdditionalGift){
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        OrderMenuOuputDTO orderMenuOuputDTO = orderMenuService.inputOrder(orderMenuInputDTO);
        assertThat(orderMenuService.hasAdditionalGift(orderMenuOuputDTO)).isEqualTo(haveAdditionalGift);
    }
}