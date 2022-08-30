package vending.init;

import vending.exception.InsufficientAmountException;
import vending.exception.InvalidOperationException;
import vending.exception.SoldOutException;
import vending.model.Product;
import vending.service.InventoryService;
import vending.state.VendingMachine;
import vending.view.ConsoleWriter;
import vending.view.Dialog;
import vending.view.Menu;

import java.util.List;

import static vending.constants.MenuMessages.*;
import static vending.constants.MenuMessages.GREET;

public final class MenuInitializer {

    private final InventoryService inventoryService;
    private final VendingMachine vendingMachine;
    private final Dialog dialog;
    private final ConsoleWriter writer;
    private Menu menu;

    public MenuInitializer(InventoryService inventoryService, VendingMachine vendingMachine, Dialog dialog, ConsoleWriter writer) {
        this.inventoryService = inventoryService;
        this.vendingMachine = vendingMachine;
        this.dialog = dialog;
        this.writer = writer;
    }

    public void showMenu() {
        menu.show();
    }

    public void init() {
        menu = new Menu(VENDING_MACHINE, List.of(
                new Menu.Option(INSERT_COIN, () -> {
                    var insertedCoin = dialog.insertCoin();
                    try {
                        vendingMachine.insertCoin(insertedCoin);
                        return String.format(TOTAL_INSERTED_COINS, (inventoryService.getMoneyInserted() + 0.0) / 100);
                    } catch (InvalidOperationException ex) {
                        return ex.getMessage();
                    }
                }),
                new Menu.Option(SELECT_PRODUCT, () -> {
                    int itemId = dialog.selectItem();
                    try {
                        var selectedItem = vendingMachine.selectItem(itemId - 1);
                        var processedItem = vendingMachine.processItem(itemId - 1);
                        String additionalMessage = String.format(PROCESS_PRODUCT, processedItem.getName());
                        var change = vendingMachine.returnChangeMoney();

                        if (change != 0) {
                            additionalMessage += String.format(CHANGE, (change + 0.0) / 100);
                        }

                        return String.format(SELECTED_PRODUCT, selectedItem.getName(), additionalMessage);

                    } catch (InvalidOperationException | InsufficientAmountException | SoldOutException ioe) {
                        return ioe.getMessage();
                    }
                }),
                new Menu.Option(SERVICE, () -> {
                    try {
                        var refundedMoney = vendingMachine.refund();
                        String additionalMessage = "";
                        if (refundedMoney != 0) {
                            additionalMessage = String.format(REFUND_MONEY, (refundedMoney + 0.0) / 100);
                        }
                        vendingMachine.maintenance();
                        return additionalMessage + MAINTENANCE;
                    } catch (InvalidOperationException ex) {
                        return ex.getMessage();
                    }
                }),
                new Menu.Option(END_SERVICE, () -> {
                    try {
                        vendingMachine.endMaintenance();
                        return OUT_OF_MAINTENANCE;
                    } catch (InvalidOperationException ex) {
                        return ex.getMessage();
                    }
                }),
                new Menu.Option(REFUND, () -> {
                    try {
                        var refundedMoney = vendingMachine.refund();
                        return String.format(REFUND_MONEY, (refundedMoney + 0.0) / 100);
                    } catch (InvalidOperationException ex) {
                        return ex.getMessage();
                    }
                }),
                new Menu.Option(COLLECT_PRODUCT, () -> {
                    try {
                        Product takenProduct = vendingMachine.takeItem();
                        return String.format(TAKEN_PRODUCT, takenProduct.getName());
                    } catch (InvalidOperationException ex) {
                        return ex.getMessage();
                    }
                }),
                new Menu.Option(EXIT, () -> GREET)
        ), writer);
    }


}
