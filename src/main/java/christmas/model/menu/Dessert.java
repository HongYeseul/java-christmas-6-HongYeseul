package christmas.model.menu;

public enum Dessert {
    CHOCO_CAKE("초코케이크", 15_000),
    ICE_CREAM("아이스크림", 5_000);

    private final String dessertName;
    private final Integer dessertPrice;

    Dessert(String dessertName, Integer dessertPrice){
        this.dessertName = dessertName;
        this.dessertPrice = dessertPrice;
    }

    public String getName(){
        return dessertName;
    }

    public Integer getPrice(){
        return dessertPrice;
    }
}
