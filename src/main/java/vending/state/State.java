package vending.state;

import vending.enums.CoinTypeEnum;
import vending.model.Product;

public interface State {
    Integer insertCoin(CoinTypeEnum coinType);
    Product selectItem(Integer itemId);
    Product processItem(Integer itemId);
    Product takeItem();
    Integer returnChangeMoney();
    boolean maintenance();
    boolean endMaintenance();
    Integer refund();
}
