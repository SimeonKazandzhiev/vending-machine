package vending.service.impl;

import vending.enums.CoinTypeEnum;
import vending.exception.InsufficientAmountException;
import vending.model.Inventory;
import vending.model.ProductSlot;
import vending.service.InventoryService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InventoryServiceImpl implements InventoryService {

    private final Inventory inventory;

    public InventoryServiceImpl(final Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Integer insertCoin(final CoinTypeEnum coin) {
        final Map<CoinTypeEnum, Integer> insertedCoins = inventory.getInsertedCoins();
        insertedCoins.put(coin, insertedCoins.get(coin) + 1);

        inventory.setInsertedCoins(insertedCoins);
        var currentMoneyInserted = inventory.getAvailableInsertedMoney();

        if (currentMoneyInserted == null) {
            currentMoneyInserted = coin.getValue();
            inventory.setAvailableInsertedMoney(currentMoneyInserted);
            return coin.getValue();
        }
        inventory.setAvailableInsertedMoney(inventory.getAvailableInsertedMoney() + coin.getValue());
        return coin.getValue();
    }


    @Override
    public void removeProductAfterOrder(final ProductSlot productSlot) {
        final List<ProductSlot> availableItems = inventory.getAvailableProducts();

        productSlot.setQuantity(productSlot.getQuantity() - 1);

        availableItems.set(productSlot.getId() - 1, productSlot);
        inventory.setAvailableProducts(availableItems);
    }

    @Override
    public Integer withdrawMoney(final Integer moneyToWithdraw) {
        var availableInsertedMoney = inventory.getAvailableInsertedMoney();

        availableInsertedMoney -= moneyToWithdraw;
        inventory.setAvailableInsertedMoney(availableInsertedMoney);

        return availableInsertedMoney;
    }

    @Override
    public List<ProductSlot> getProductsAvailable() {
        return inventory.getAvailableProducts();
    }

    @Override
    public Integer getMoneyInserted() {
        return inventory.getAvailableInsertedMoney();
    }

    @Override
    public Integer refund() {
        return returnChange(getMoneyInserted());
    }

    @Override
    public Integer returnChange(Integer changeToReturn) {
        final List<CoinTypeEnum> allChange = new ArrayList<>();

        if(changeToReturn == null){
            System.out.println("Nothing to refund");
            inventory.setAvailableInsertedMoney(0);
            return 0;
        }

        while (changeToReturn > 0) {
            if (changeToReturn >= CoinTypeEnum.DOLLAR.getValue() && getCoinsAvailable().get(CoinTypeEnum.DOLLAR) > 0) {
                changeToReturn = getChangeNeeded(allChange, CoinTypeEnum.DOLLAR, changeToReturn);
            } else if (changeToReturn >= CoinTypeEnum.HALF.getValue() && getCoinsAvailable().get(CoinTypeEnum.HALF) > 0) {
                changeToReturn = getChangeNeeded(allChange, CoinTypeEnum.HALF, changeToReturn);
            } else if (changeToReturn >= CoinTypeEnum.QUARTER.getValue() && getCoinsAvailable().get(CoinTypeEnum.QUARTER) > 0) {
                changeToReturn = getChangeNeeded(allChange, CoinTypeEnum.QUARTER, changeToReturn);
            } else {
                depositCoinsBackToInventory(allChange);
                throw new InsufficientAmountException("Not enough change to be return.Please choose another product.");
            }
        }

        return allChange.stream().mapToInt(CoinTypeEnum::getValue).sum();
    }

    private void depositCoinsBackToInventory(List<CoinTypeEnum> availableChange) {
        for (CoinTypeEnum coin : availableChange) {
            depositCoins(coin);
        }
    }

    private void removeCoin(final CoinTypeEnum coin) {
        final Map<CoinTypeEnum, Integer> availableCoins = inventory.getAvailableCoins();

        if (availableCoins.get(coin) == 0) {
            return;
        }

        inventory.setAvailableInsertedMoney(inventory.getAvailableInsertedMoney() - coin.getValue());
        availableCoins.put(coin, availableCoins.get(coin) - 1);
        inventory.setAvailableCoins(availableCoins);

    }

    private Integer getChangeNeeded(final List<CoinTypeEnum> allCoins, final CoinTypeEnum coin, Integer changeNeeded) {
        allCoins.add(coin);
        removeCoin(coin);

        changeNeeded -= coin.getValue();

        return changeNeeded;
    }

    private void depositCoins(final CoinTypeEnum coin) {
        final Map<CoinTypeEnum, Integer> availableCoins = inventory.getAvailableCoins();
        availableCoins.put(coin, availableCoins.get(coin) + 1);

        inventory.setAvailableInsertedMoney(inventory.getAvailableInsertedMoney() + coin.getValue());
        inventory.setAvailableCoins(availableCoins);
    }

    private Map<CoinTypeEnum, Integer> getCoinsAvailable() {
        return inventory.getAvailableCoins();
    }
}
