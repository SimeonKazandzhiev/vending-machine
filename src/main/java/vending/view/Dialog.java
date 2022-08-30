package vending.view;

import vending.enums.CoinTypeEnum;

public interface Dialog {
    CoinTypeEnum insertCoin();
    Integer selectItem();
}
