package vending.model;

import vending.enums.CoinTypeEnum;
import vending.init.InventoryInitializer;

import java.util.List;
import java.util.Map;

public final class Inventory {
    private List<ProductSlot> availableProducts;
    private Map<CoinTypeEnum, Integer> availableCoins;
    private Map<CoinTypeEnum, Integer> insertedCoins;

    private Integer availableInsertedMoney;

    public Inventory(final InventoryInitializer inventoryInitializer){
        availableProducts = inventoryInitializer.loadProducts();
        availableCoins = inventoryInitializer.loadCoins();
        insertedCoins = inventoryInitializer.loadInsertedCoins();
    }

    public List<ProductSlot> getAvailableProducts() {
        return availableProducts;
    }

    public void setAvailableProducts(final List<ProductSlot> availableProducts) {
        this.availableProducts = availableProducts;
    }

    public Map<CoinTypeEnum, Integer> getAvailableCoins() {
        return availableCoins;
    }

    public void setAvailableCoins(final Map<CoinTypeEnum, Integer> availableCoins) {
        this.availableCoins = availableCoins;
    }

    public Map<CoinTypeEnum, Integer> getInsertedCoins() {
        return insertedCoins;
    }

    public void setInsertedCoins(final Map<CoinTypeEnum, Integer> insertedCoins) {
        this.insertedCoins = insertedCoins;
    }

    public Integer getAvailableInsertedMoney() {
        return availableInsertedMoney;
    }

    public void setAvailableInsertedMoney(final Integer availableInsertedMoney) {
        this.availableInsertedMoney = availableInsertedMoney;
    }
}
