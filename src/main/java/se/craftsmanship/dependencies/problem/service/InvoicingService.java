package se.craftsmanship.dependencies.problem.service;

import se.craftsmanship.dependencies.problem.ReferenceGenerator;
import se.craftsmanship.dependencies.problem.model.Customer;
import se.craftsmanship.dependencies.problem.model.Invoice;
import se.craftsmanship.dependencies.problem.model.Order;
import se.craftsmanship.dependencies.problem.repository.CustomerRepository;
import se.craftsmanship.dependencies.problem.repository.InvoiceRepository;
import se.craftsmanship.dependencies.problem.repository.OrderRepository;

import java.util.Calendar;

public class InvoicingService {
    private final OrderRepository orderRepository;

    public InvoicingService() {
        orderRepository = new OrderRepository();
    }

    public void invoiceCustomer(long customerId) {
        Order order = orderRepository.getLatestOrder(customerId);
        if (!order.isInvoiced()) {
            Customer customer = new CustomerRepository().getCustomerById(customerId);
            Invoice invoice = new Invoice(customerId, Calendar.getInstance().getTime(),
                    ReferenceGenerator.getInstance().generateInvoiceReference(customerId));
            invoice.setCustomerName(customer.lastName() + ", " + customer.firstName());
            invoice.setAddress(customer.address());
            invoice.setAmount(order.getItems().stream().map(i -> i.price() * i.quantity()).reduce(0.0, Double::sum));
            new InvoiceRepository().createInvoice(invoice);
        }
    }

    public static void main(String[] args) {
        new InvoicingService().invoiceCustomer(1);
    }
}
