package se.craftsmanship.dependencies.solution.service;

import se.craftsmanship.dependencies.solution.ReferenceSource;
import se.craftsmanship.dependencies.solution.model.Customer;
import se.craftsmanship.dependencies.solution.model.Invoice;
import se.craftsmanship.dependencies.solution.model.Order;
import se.craftsmanship.dependencies.solution.repository.CustomerRepository;
import se.craftsmanship.dependencies.solution.repository.InvoiceRepository;
import se.craftsmanship.dependencies.solution.repository.OrderRepository;

import java.util.Calendar;
import java.util.Date;

public class InvoicingService {
    private final OrderRepository orderRepository;
    private final InvoiceRepository invoiceRepository;
    private final CustomerRepository customerRepository;
    private ReferenceSource referenceSource = new ReferenceSource();

    public InvoicingService(OrderRepository orderRepository, InvoiceRepository invoiceRepository,
                            CustomerRepository customerRepository) {
        this.orderRepository = orderRepository;
        this.invoiceRepository = invoiceRepository;
        this.customerRepository = customerRepository;
    }

    public void setReferenceSource(ReferenceSource referenceSource) {
        this.referenceSource = referenceSource;
    }

    public void invoiceCustomer(long customerId) {
        Order order = orderRepository.getLatestOrder(customerId);
        if (!order.isInvoiced()) {
            Customer customer = customerRepository.getCustomerById(customerId);
            Invoice invoice = new Invoice(customerId, getCurrentTime(),
                    referenceSource.generateInvoiceReference(customerId));
            invoice.setCustomerName(customer.lastName() + ", " + customer.firstName());
            invoice.setAddress(customer.address());

            invoice.setAmount(order.getItems().stream().map(i -> i.price() * i.quantity()).reduce(0.0, Double::sum));
            invoiceRepository.createInvoice(invoice);
        }
    }

    protected Date getCurrentTime() {
        return Calendar.getInstance().getTime();
    }
}
