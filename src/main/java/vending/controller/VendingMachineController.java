package vending.controller;

import vending.init.MenuInitializer;


public final class VendingMachineController {

    private final MenuInitializer menuInitializer;

    public VendingMachineController(MenuInitializer menuInitializer) {
        this.menuInitializer = menuInitializer;
        menuInitializer.init();
    }

    public void loadMenu() {
        menuInitializer.showMenu();
    }
}

