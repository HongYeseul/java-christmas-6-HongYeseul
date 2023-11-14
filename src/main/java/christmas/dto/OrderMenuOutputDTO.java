package christmas.dto;

import christmas.model.menu.Menu;

import java.util.ArrayList;
import java.util.List;

import static christmas.dto.constants.FormatUnit.BLANK;
import static christmas.dto.constants.FormatUnit.QUANTITY_UNIT;

/**
 * 주문 성공시 반환: 사용자가 구문한 리스트
 */
public record OrderMenuOutputDTO(List<Menu> menu, List<Integer> menuCount) {
    private static final ArrayList<Menu> MAKE_EMPTY_MENU_LIST = new ArrayList<>();
    private static final ArrayList<Integer> MAKE_EMPTY_QUANTITY_LIST = new ArrayList<>();

    public OrderMenuOutputDTO() {
        this(MAKE_EMPTY_MENU_LIST, MAKE_EMPTY_QUANTITY_LIST);
    }

    public String getOrderList(){
        StringBuilder orderList = new StringBuilder();
        for(int i=0; i<menu.size(); i++){
            orderList.append(menu.get(i).getName()).append(BLANK)
                    .append(menuCount.get(i)).append(QUANTITY_UNIT)
                    .append(System.lineSeparator());
        }
        return orderList.toString();
    }
}
