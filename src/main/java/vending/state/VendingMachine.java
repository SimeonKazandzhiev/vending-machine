package vending.state;

import vending.enums.CoinTypeEnum;
import vending.model.Product;
import vending.service.InventoryService;

public final class VendingMachine {

    private VendingMachineState vendingMachineState;

    public VendingMachine(final InventoryService inventoryService) {
        vendingMachineState = VendingMachineState.DEFAULT;
        vendingMachineState.setInventoryService(inventoryService);
        vendingMachineState.setContext(this);
    }

    public void insertCoin(final CoinTypeEnum coin) {
        vendingMachineState.insertCoin(coin);
    }

    public Product selectItem(final Integer itemId) {
        return vendingMachineState.selectItem(itemId);
    }

    public Product processItem(final Integer itemId) {
        return vendingMachineState.processItem(itemId);
    }

    public Product takeItem() {
        return vendingMachineState.takeItem();
    }

    public void maintenance() {
        vendingMachineState.maintenance();
    }

    public void endMaintenance() {
        vendingMachineState.endMaintenance();
    }

    public int returnChangeMoney() {
        return vendingMachineState.returnChangeMoney();
    }

    public int refund() {
        return vendingMachineState.refund();
    }

    public void changeState(final VendingMachineState state) {
        this.vendingMachineState = state;
    }
}
