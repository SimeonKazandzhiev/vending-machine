package vending.service;

import vending.enums.CoinTypeEnum;
import vending.model.ProductSlot;

import java.util.List;

public interface InventoryService {

    Integer insertCoin(CoinTypeEnum coin);

    void removeProductAfterOrder(ProductSlot productSlot);

    Integer withdrawMoney(Integer moneyToWithdraw);

    List<ProductSlot> getProductsAvailable();

    Integer getMoneyInserted();

    Integer refund();

    Integer returnChange(Integer changeToReturn);
}
