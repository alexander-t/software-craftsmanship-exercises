package se.craftsmanship.dependencies.model;

public record OrderItem(long id, String product, double price, int quantity) {
}
