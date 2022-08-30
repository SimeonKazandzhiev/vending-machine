package vending.init;

import vending.enums.CoinTypeEnum;
import vending.model.Product;
import vending.model.ProductSlot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class InventoryInitializer {

    public List<ProductSlot> loadProducts() {
        return new ArrayList<>() {{
            add(new ProductSlot(1, new Product("Vodka", 1.00), 15));
            add(new ProductSlot(2, new Product("Whiskey", 1.50), 15));
            add(new ProductSlot(3, new Product("Gin", 1.50), 15));
            add(new ProductSlot(4, new Product("Tonic", 0.50), 15));
            add(new ProductSlot(5, new Product("Coca-Cola", 0.50), 15));
        }};
    }

    public Map<CoinTypeEnum, Integer> loadCoins() {
        return new HashMap<>() {{
            put(CoinTypeEnum.QUARTER, 100);
            put(CoinTypeEnum.HALF, 100);
            put(CoinTypeEnum.DOLLAR, 100);
        }};
    }

    public Map<CoinTypeEnum, Integer> loadInsertedCoins() {
        return new HashMap<>() {{
            put(CoinTypeEnum.QUARTER, 0);
            put(CoinTypeEnum.HALF, 0);
            put(CoinTypeEnum.DOLLAR, 0);
        }};
    }
}
