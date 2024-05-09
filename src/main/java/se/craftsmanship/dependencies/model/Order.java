package se.craftsmanship.dependencies.model;

import java.util.Date;
import java.util.List;

public class Order {
    private long id;
    private long customerId;
    private boolean invoiced;
    private Date date;
    private List<OrderItem> items;

    public Order(long id, long customerId, boolean invoiced, Date date) {
        this.id = id;
        this.customerId = customerId;
        this.invoiced = invoiced;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }
}
