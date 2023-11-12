package christmas.model.menu;

import com.sun.tools.javac.Main;

public enum MainDish {
    T_BONE_STAKE("티본스테이크", 55_000),
    BARBECUE_RIB("바비큐립", 54_000),
    SEAFOOD_PASTA("해산물파스타", 35_000),
    CHRISTMAS_PASTA("크리스마스파스타", 25_000);

    private final String mainDishName;
    private final Integer mainDishPrice;

    MainDish(String mainDishName, Integer mainDishPrice){
        this.mainDishName = mainDishName;
        this.mainDishPrice = mainDishPrice;
    }

    public String getName(){
        return mainDishName;
    }

    public Integer getPrice(){
        return mainDishPrice;
    }
}
