package christmas.model.menu;

public enum MenuType {
    /*
    * 애피타이저
    * */
    BUTTON_MUSHROOM_SOUP("양송이수프", 6_000),
    TAPAS("타파스", 5_500),
    CAESAR_SALAD("시저샐러드", 8_000),

    /*
    * 메인 메뉴
    * */
    T_BONE_STAKE("티본스테이크", 55_000),
    BARBECUE_RIB("바비큐립", 54_000),
    SEAFOOD_PASTA("해산물파스타", 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000),

    /*
    * 디저트
    * */
    CHOCO_CAKE("초코케이크", 15_000),
    ICE_CREAM("아이스크림", 5_000),

    /*
    * 음료
    * */
    ZERO_COKE("제로콜라", 3_000),
    RED_WINE("레드와인", 60_000),
    CHAMPAGNE("샴페인", 25_000);

    private final String menuName;
    private final Integer menuPrice;

    MenuType(String menuName, Integer menuPrice){
        this.menuName = menuName;
        this.menuPrice = menuPrice;
    }

    public String getName(){
        return menuName;
    }

    public Integer getPrice(){
        return menuPrice;
    }
}
