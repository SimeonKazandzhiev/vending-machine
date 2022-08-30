package vending.model;

public final class Product {
    private final String name;
    private final double cost;

    public Product(final String name, final double cost) {
        this.name = name;
        this.cost = cost;
    }

    public String getName() {
        return name;
    }

    public double getCost() {
        return cost;
    }

    @Override
    public String toString() {
        return format();
    }

    private String format(){
        return String.format("%-10s | %-2.2f", name, cost);
    }

}
