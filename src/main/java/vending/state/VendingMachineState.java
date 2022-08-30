package vending.state;

import vending.enums.CoinTypeEnum;
import vending.exception.InsufficientAmountException;
import vending.exception.InvalidOperationException;
import vending.exception.SoldOutException;
import vending.model.Product;
import vending.model.ProductSlot;
import vending.service.InventoryService;

import static vending.constants.VendingMachineStateMessages.*;

public enum VendingMachineState implements State {
    DEFAULT {
        @Override
        public Integer insertCoin(CoinTypeEnum coin) {
            int coinValue = inventoryService.insertCoin(coin);
            int availableInsertedMoney = inventoryService.getMoneyInserted();

            if (availableInsertedMoney >= 100) {
                vendingMachine.changeState(SELECT);
            }

            return coinValue;
        }

        @Override
        public Product selectItem(Integer itemId) {
            throw new InvalidOperationException(INITIAL_AMOUNT_MESSAGE);
        }

        @Override
        public Product processItem(Integer itemId) {
            throw new InvalidOperationException(INITIAL_AMOUNT_MESSAGE);
        }

        @Override
        public Product takeItem() {
            throw new InvalidOperationException(INITIAL_AMOUNT_MESSAGE);
        }

        @Override
        public Integer returnChangeMoney() {
            throw new InvalidOperationException(INITIAL_AMOUNT_MESSAGE);
        }

        @Override
        public boolean maintenance() {
            if (inventoryService.getMoneyInserted() > 0) {
                inventoryService.refund();
            }
            vendingMachine.changeState(SERVICE);
            return true;
        }

        @Override
        public boolean endMaintenance() {
            throw new InvalidOperationException(END_MAINTENANCE);
        }

        @Override
        public Integer refund() {
            return inventoryService.refund();
        }
    }, SELECT {
        @Override
        public Integer insertCoin(CoinTypeEnum coin) {
            return inventoryService.insertCoin(coin);
        }

        @Override
        public Product selectItem(Integer itemId) {
            ProductSlot selectedProductSlot = inventoryService.getProductsAvailable().get(itemId);

            if (selectedProductSlot.getQuantity() < 1) {
                throw new SoldOutException(SOLD_OUT_MESSAGE);
            }
            if (inventoryService.getMoneyInserted() < selectedProductSlot.getItem().getCost() * 100) {
                throw new InsufficientAmountException(INSUFFICIENT_AMOUNT_INSERTED_MESSAGE);
            }

            inventoryService.removeProductAfterOrder(selectedProductSlot);
            setSelectedItem(selectedProductSlot.getItem());

            return selectedProductSlot.getItem();
        }

        @Override
        public Product processItem(Integer itemId) {
            return inventoryService.getProductsAvailable()
                    .get(itemId)
                    .getItem();
        }

        @Override
        public Product takeItem() {
            Product selectedProduct = getSelectedItem();

            if (selectedProduct != null) {
                setSelectedItem(null);

                if (inventoryService.getMoneyInserted() < 100) {
                    vendingMachine.changeState(DEFAULT);
                }
                return selectedProduct;
            }
            throw new InvalidOperationException(NO_PRODUCT_TO_BE_COLLECTED_MESSAGE);
        }

        @Override
        public Integer returnChangeMoney() {
            int itemPrice = (int) ((getSelectedItem().getCost() * 100) + 0.1);
            int changeToBeReturn = inventoryService.withdrawMoney(itemPrice);

            return inventoryService.returnChange(changeToBeReturn);
        }

        @Override
        public boolean maintenance() {
            if (inventoryService.getMoneyInserted() > 0) {
                inventoryService.refund();
            }
            vendingMachine.changeState(SERVICE);
            return true;
        }

        @Override
        public boolean endMaintenance() {
            throw new InvalidOperationException(END_MAINTENANCE);
        }

        @Override
        public Integer refund() {
            vendingMachine.changeState(DEFAULT);
            return inventoryService.refund();
        }
    }, SERVICE {
        @Override
        public Integer insertCoin(CoinTypeEnum coin) {
            throw new InvalidOperationException(UNDER_MAINTENANCE_MESSAGE);
        }

        @Override
        public Product selectItem(Integer itemId) {
            throw new InvalidOperationException(UNDER_MAINTENANCE_MESSAGE);
        }

        @Override
        public Product processItem(Integer itemId) {
            throw new InvalidOperationException(UNDER_MAINTENANCE_MESSAGE);
        }

        @Override
        public Product takeItem() {
            throw new InvalidOperationException(UNDER_MAINTENANCE_MESSAGE);
        }

        @Override
        public Integer returnChangeMoney() {
            throw new InvalidOperationException(UNDER_MAINTENANCE_MESSAGE);
        }

        @Override
        public boolean maintenance() {
            throw new InvalidOperationException(UNDER_MAINTENANCE_MESSAGE);
        }

        @Override
        public boolean endMaintenance() {
            vendingMachine.changeState(DEFAULT);
            return true;
        }

        @Override
        public Integer refund() {
            throw new InvalidOperationException(UNDER_MAINTENANCE_MESSAGE);
        }
    };

    private static Product selectedProduct;
    private static InventoryService inventoryService;
    private static VendingMachine vendingMachine;

    public void setInventoryService(InventoryService inventoryService) {
        VendingMachineState.inventoryService = inventoryService;
    }

    public static Product getSelectedItem() {
        return selectedProduct;
    }

    public static void setSelectedItem(Product selectedProduct) {
        VendingMachineState.selectedProduct = selectedProduct;
    }

    public void setContext(VendingMachine vm) {
        vendingMachine = vm;
    }
}