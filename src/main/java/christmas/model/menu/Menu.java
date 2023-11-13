package christmas.model.menu;

import java.util.Arrays;

public enum Menu {
    /*
    * 애피타이저
    * */
    BUTTON_MUSHROOM_SOUP("애피타이저", "양송이수프", 6_000),
    TAPAS("애피타이저", "타파스", 5_500),
    CAESAR_SALAD("애피타이저", "시저샐러드", 8_000),

    /*
    * 메인 메뉴
    * */
    T_BONE_STAKE("메인", "티본스테이크", 55_000),
    BARBECUE_RIB("메인", "바비큐립", 54_000),
    SEAFOOD_PASTA("메인", "해산물파스타", 35_000),
    CHRISTMAS_PASTA("메인", "크리스마스파스타", 25_000),

    /*
    * 디저트
    * */
    CHOCO_CAKE("디저트", "초코케이크", 15_000),
    ICE_CREAM("디저트", "아이스크림", 5_000),

    /*
    * 음료
    * */
    ZERO_COKE("음료", "제로콜라", 3_000),
    RED_WINE("음료", "레드와인", 60_000),
    CHAMPAGNE("음료", "샴페인", 25_000);

    private final String title;
    private final String menuName;
    private final Integer menuPrice;

    Menu(String title, String menuName, Integer menuPrice){
        this.title = title;
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public static Menu findByMenuName(String inputMenuName) {
        for (Menu menu: Menu.values()) {
            if (menu.menuName.equals(inputMenuName)) {
                return menu;
            }
        }
        return null;
    }

    public String getTitle(){
        return title;
    }

    public String getName(){
        return menuName;
    }

    public Integer getPrice(){
        return menuPrice;
    }
}
