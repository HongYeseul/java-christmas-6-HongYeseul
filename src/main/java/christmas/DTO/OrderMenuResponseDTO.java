package christmas.DTO;

import christmas.model.menu.Menu;

import java.util.List;

/**
 * 주문 성공시 반환: 사용자가 구문한 리스트
 */
public record OrderMenuResponseDTO(List<Menu> menu, List<Integer> menuCount) {
}
