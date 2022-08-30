package vending.validation;

import vending.enums.CoinTypeEnum;
import vending.exception.InvalidChoiceException;
import vending.model.Inventory;

import static vending.constants.ValidatorMessages.NON_EXISTING_PRODUCT;
import static vending.constants.ValidatorMessages.NOT_SUPPORTED_TYPE_OF_COIN;

public final class VendingMachineValidator {

    public CoinTypeEnum validateCoin(final Integer choice) {
        return switch (choice) {
            case 1 -> CoinTypeEnum.QUARTER;
            case 2 -> CoinTypeEnum.HALF;
            case 3 -> CoinTypeEnum.DOLLAR;
            default -> throw new InvalidChoiceException(NOT_SUPPORTED_TYPE_OF_COIN);
        };
    }

    public Integer validateSelectedProduct(final Inventory inventory, final Integer productId) {

        var productSlot =
                inventory.getAvailableProducts()
                        .stream()
                        .filter(v -> v.getId().equals(productId))
                        .findAny();

        if (productSlot.isPresent()) {
            return productSlot.get().getId();
        }
        throw new InvalidChoiceException(NON_EXISTING_PRODUCT);
    }
}
