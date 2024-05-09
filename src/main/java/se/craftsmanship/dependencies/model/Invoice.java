package se.craftsmanship.dependencies.model;

import java.util.Date;

public class Invoice {
    private final long customerId;
    private final Date date;
    private final String reference;
    private String customerName;
    private String address;
    private double amount;

    public Invoice(long customerId, Date date, String reference) {
        this.customerId = customerId;
        this.date = date;
        this.reference = reference;
    }

    public long getCustomerId() {
        return customerId;
    }

    public Date getDate() {
        return date;
    }

    public String getReference() {
        return reference;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
