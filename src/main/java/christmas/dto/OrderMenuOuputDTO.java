package christmas.dto;

import christmas.model.menu.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 주문 성공시 반환: 사용자가 구문한 리스트
 */
public record OrderMenuOuputDTO(List<Menu> menu, List<Integer> menuCount) {
    private static final ArrayList<Menu> MAKE_EMPTY_MENU_LIST = new ArrayList<>();
    private static final ArrayList<Integer> MAKE_EMPTY_QUANTITY_LIST = new ArrayList<>();

    public OrderMenuOuputDTO() {
        this(MAKE_EMPTY_MENU_LIST, MAKE_EMPTY_QUANTITY_LIST);
    }

    public String getOrderList(){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i=0; i<menu.size(); i++){
            stringBuilder.append(menu.get(i).getName()).append(" ")
                    .append(menuCount.get(i)).append("개")
                    .append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
