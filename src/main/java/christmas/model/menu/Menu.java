package christmas.model.menu;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum Menu {
    APPETIZER("애피타이저", Arrays.asList(MenuType.BUTTON_MUSHROOM_SOUP, MenuType.TAPAS, MenuType.CAESAR_SALAD)),
    MAIN_DISH("메인", Arrays.asList(MenuType.T_BONE_STAKE, MenuType.BARBECUE_RIB, MenuType.SEAFOOD_PASTA, MenuType.CHRISTMAS_PASTA)),
    DESSERT("디저트", Arrays.asList(MenuType.CHOCO_CAKE, MenuType.ICE_CREAM)),
    DRINK("음료", Arrays.asList(MenuType.ZERO_COKE, MenuType.RED_WINE, MenuType.CHAMPAGNE)),
    EMPTY("없음", Collections.emptyList());

    private String title;
    private List<MenuType> menu;

    Menu(String title, List<MenuType> menu){
        this.title = title;
        this.menu = menu;
    }

    public static Menu findByMenuType(MenuType menuType){
        return Arrays.stream(Menu.values())
                .filter(menuGroup -> menuGroup.hasMenuOrder(menuType))
                .findAny()
                .orElse(EMPTY);
    }

    public boolean hasMenuOrder(MenuType menuType){
        return menu.stream()
                .anyMatch(menu -> menu == menuType);
    }

    public String getTitle(){
        return title;
    }
}
