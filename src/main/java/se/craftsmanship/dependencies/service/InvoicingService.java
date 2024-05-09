package se.craftsmanship.dependencies.service;

import se.craftsmanship.dependencies.ReferenceGenerator;
import se.craftsmanship.dependencies.model.Customer;
import se.craftsmanship.dependencies.model.Invoice;
import se.craftsmanship.dependencies.model.Order;
import se.craftsmanship.dependencies.repository.CustomerRepository;
import se.craftsmanship.dependencies.repository.InvoiceRepository;
import se.craftsmanship.dependencies.repository.OrderRepository;

import java.util.Calendar;

public class InvoicingService {
    private final OrderRepository orderRepository;

    public InvoicingService() {
        orderRepository = new OrderRepository();
    }

    public void invoiceCustomer(long customerId) {
        Customer customer = new CustomerRepository().getCustomerById(customerId);
        Invoice invoice = new Invoice(customerId, Calendar.getInstance().getTime(),
                ReferenceGenerator.getInstance().generateInvoiceReference(customerId));
        invoice.setCustomerName(customer.lastName() + ", " + customer.firstName());
        invoice.setAddress(customer.address());
        Order order = orderRepository.getLatestOrder(customerId);
        invoice.setAmount(order.getItems().stream().map(i -> i.price() * i.quantity()).reduce(0.0, Double::sum));
        new InvoiceRepository().createInvoice(invoice);
    }

    public static void main(String[] args) {
        new InvoicingService().invoiceCustomer(1);
    }
}
