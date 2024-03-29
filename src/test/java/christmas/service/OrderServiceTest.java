package christmas.service;

import christmas.dto.OrderDateOutputDTO;
import christmas.dto.OrderMenuInputDTO;
import christmas.dto.OrderMenuOutputDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceTest {
    OrderService orderService = new OrderService();
    OrderDateService orderDateService = new OrderDateService();
    OrderMenuService orderMenuService = new OrderMenuService();

    @DisplayName("[SUCCESS] 총 혜택 금액을 계산한다.")
    @ParameterizedTest(name = "주문 메뉴 및 개수: {0}, 식당 예상 방문 날짜: {1}, 반환되어야 하는 총 혜택 금액: {2}")
    @CsvSource({"'타파스-1,제로콜라-1', 26, 0",
            "'티본스테이크-1,바비큐립-1,초코케이크-2,제로콜라-1', 3, 31246",
            "'아이스크림-2', 25, 8446"})
    void calculateTotalSalePrice(String input, int orderDate, BigDecimal totalBenefit){
        OrderDateOutputDTO orderDateOutputDTO = new OrderDateOutputDTO(orderDate);
        OrderMenuInputDTO orderMenuInputDTO = new OrderMenuInputDTO(input);
        OrderMenuOutputDTO orderMenuOutputDTO = orderMenuService.inputOrder(orderMenuInputDTO);

        BigDecimal result = orderService.calculateTotalSalePrice(
                orderDateService.calculateDDaySalePrice(orderDateOutputDTO),
                orderDateService.calculateWeekDaySalePrice(orderDateOutputDTO, orderMenuOutputDTO),
                orderDateService.calculateWeekendSalePrice(orderDateOutputDTO, orderMenuOutputDTO),
                orderDateService.calculateSpecialSalePrice(orderDateOutputDTO),
                orderMenuService.hasAdditionalGift(orderMenuOutputDTO)
        );

        assertThat(result).isEqualTo(totalBenefit);
    }

    @DisplayName("[SUCCESS] 할인 후 예상 결제 금액을 정상 반환힌디.")
    @ParameterizedTest(name = "총 결제 금액: {0}, 혜택 금액: {1}, 증정 메뉴가 있는가: {2},반환되어야 하는 총 혜택 금액: {3}")
    @CsvSource({"142000, 31246, true, 135754",
            "10000, 8446, false, 1554"})
    void calculateTotalCharge(BigDecimal totalPrice, BigDecimal benefit, boolean hasGift, BigDecimal resultPrice){
        BigDecimal result = orderService.calculateTotalCharge(totalPrice, benefit, hasGift);
        assertThat(result).isEqualTo(resultPrice);
    }

    @DisplayName("[SUCCESS] 혜택 금액에 따른 이벤트 배지를 정상 반환한다.")
    @ParameterizedTest(name = "혜택 금액: {0}, 반환되어야 하는 이벤트 배지: {1}")
    @CsvSource({"5000, 별",
            "10000, 트리",
            "20000, 산타",
            "4999, 없음",
            "9999, 별",
            "19999, 트리",
            "20001, 산타"})
    void makeEventBadge(BigDecimal benefit, String badge){
        String result = orderService.makeEventBadge(benefit);
        assertThat(result).isEqualTo(badge);
    }
}