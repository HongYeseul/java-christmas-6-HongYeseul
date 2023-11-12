package christmas.model.menu;

public enum Appetizer {
    BUTTON_MUSHROOM_SOUP("양송이수프", 6_000),
    TAPAS("타파스", 5_500),
    CAESAR_SALAD("시저샐러드", 8_000);

    private final String appetizerName;
    private final Integer appetizerPrice;

    Appetizer(String appetizerName, Integer appetizerPrice){
        this.appetizerName = appetizerName;
        this.appetizerPrice = appetizerPrice;
    }

    public String getName(){
        return appetizerName;
    }

    public Integer getPrice(){
        return appetizerPrice;
    }
}
