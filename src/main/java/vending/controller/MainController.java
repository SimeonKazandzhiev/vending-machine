package vending.controller;

import vending.init.MenuInitializer;
import vending.model.Inventory;
import vending.init.InventoryInitializer;
import vending.service.InventoryService;
import vending.service.impl.InventoryServiceImpl;
import vending.state.VendingMachine;
import vending.validation.VendingMachineValidator;
import vending.view.*;

public final class MainController {

    public static void run() {
        final InventoryInitializer inventoryInitializer = new InventoryInitializer();
        final Inventory inventory = new Inventory(inventoryInitializer);
        final InventoryService inventoryService = new InventoryServiceImpl(inventory);
        final VendingMachine vendingMachine = new VendingMachine(inventoryService);

        final VendingMachineValidator validator = new VendingMachineValidator();
        final Dialog dialog = new VendingMachineDialog(validator,inventory);
        final ConsoleWriter writer = new ConsoleWriter();

        final MenuInitializer menuInitializer = new MenuInitializer(inventoryService,vendingMachine,dialog,writer);
        final VendingMachineController vendingMachineController = new VendingMachineController(menuInitializer);

        vendingMachineController.loadMenu();
    }
}
