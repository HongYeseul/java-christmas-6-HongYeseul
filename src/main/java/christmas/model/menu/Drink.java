package christmas.model.menu;

public enum Drink {
    ZERO_COKE("제로콜라", 3_000),
    RED_WINE("레드와인", 60_000),
    CHAMPAGNE("샴페인", 25_000);

    private final String drinkName;
    private final Integer drinkPrice;

    Drink(String drinkName, Integer drinkPrice){
        this.drinkName = drinkName;
        this.drinkPrice = drinkPrice;
    }

    public String getName(){
        return drinkName;
    }

    public Integer getPrice(){
        return drinkPrice;
    }
}
