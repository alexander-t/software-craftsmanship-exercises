package se.craftsmanship.dependencies.solution.model;

import java.util.Date;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Invoice invoice = (Invoice) o;
        return customerId == invoice.customerId && Double.compare(amount, invoice.amount) == 0 && Objects.equals(date, invoice.date) && Objects.equals(reference, invoice.reference) && Objects.equals(customerName, invoice.customerName) && Objects.equals(address, invoice.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerId, date, reference, customerName, address, amount);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "customerId=" + customerId +
                ", date=" + date +
                ", reference='" + reference + '\'' +
                ", customerName='" + customerName + '\'' +
                ", address='" + address + '\'' +
                ", amount=" + amount +
                '}';
    }
}
