package vending.model;

public final class ProductSlot {

    private Integer id;
    private Product product;
    private Integer quantity;

    public ProductSlot(final Integer id, final Product product, final Integer quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    public Product getItem() {
        return product;
    }

    public void setItem(final Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return format();
    }

    private String format() {
        return String.format("| %-2d | %s |", id, product);
    }
}
