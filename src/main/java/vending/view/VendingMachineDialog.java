package vending.view;

import vending.enums.CoinTypeEnum;
import vending.exception.InvalidChoiceException;
import vending.model.Inventory;
import vending.validation.VendingMachineValidator;

import java.util.Scanner;
import java.util.regex.Pattern;


public class VendingMachineDialog implements Dialog {
    public static Scanner scanner = new Scanner(System.in);
    private final Pattern isNumber = Pattern.compile("\\d+");
    private final VendingMachineValidator validator;
    private final Inventory inventory;
    private final ConsoleWriter writer;

    public VendingMachineDialog(final VendingMachineValidator validator,final Inventory inventory) {
        this.validator = validator;
        this.inventory = inventory;
        this.writer = new ConsoleWriter();
    }

    @Override
    public CoinTypeEnum insertCoin() {
        CoinTypeEnum insertedCoin = null;

        while (insertedCoin == null) {
            printAvailableCoins();

            writer.printMessage("Please Choose: ");
            String choice = scanner.nextLine();

            if (isNumber.matcher(choice).matches()) {
                try {
                    insertedCoin = validator.validateCoin(Integer.parseInt(choice));
                } catch (InvalidChoiceException ice) {
                    writer.printlnMessage("Please insert a valid coin.");
                }
            }
        }

        return insertedCoin;
    }

    @Override
    public Integer selectItem() {
        int itemId = 0;

        while (itemId == 0) {
            printAvailableItems();

            writer.printMessage("Please choose: ");
            String choice = scanner.nextLine();

            if (isNumber.matcher(choice).matches()) {
                try {
                    itemId = validator.validateSelectedProduct(inventory, Integer.parseInt(choice));
                } catch (InvalidChoiceException ice) {
                    writer.printlnMessage("Please select a valid item.");
                }
            }
        }

        return itemId;
    }

    private void printAvailableItems() {
        inventory
                .getAvailableProducts()
                .forEach(item -> writer.printlnMessage(item.toString()));
    }

    private void printAvailableCoins() {
        writer.printMessage("\n1. Quarter (0.25$) \n2. Half (0.50$) \n3. Dollar (1.00$)\n");
    }
}


