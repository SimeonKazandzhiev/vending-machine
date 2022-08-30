package vending.enums;

public enum CoinTypeEnum {
    QUARTER(25),
    HALF(50),
    DOLLAR(100);

    private final int value;

    CoinTypeEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
