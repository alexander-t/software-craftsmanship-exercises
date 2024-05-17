package se.craftsmanship.dependency.solution.service;

import org.junit.Before;
import org.junit.Test;
import se.craftsmanship.dependencies.solution.ReferenceSource;
import se.craftsmanship.dependencies.solution.model.Customer;
import se.craftsmanship.dependencies.solution.model.Invoice;
import se.craftsmanship.dependencies.solution.model.Order;
import se.craftsmanship.dependencies.solution.model.OrderItem;
import se.craftsmanship.dependencies.solution.repository.CustomerRepository;
import se.craftsmanship.dependencies.solution.repository.InvoiceRepository;
import se.craftsmanship.dependencies.solution.repository.OrderRepository;
import se.craftsmanship.dependencies.solution.service.InvoicingService;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;

// This class exists to enable overriding of getCurrentTime()
class TestInvoiceService extends InvoicingService {
    private Date date;

    public TestInvoiceService(OrderRepository orderRepository,
                              InvoiceRepository invoiceRepository,
                              CustomerRepository customerRepository) {
        super(orderRepository, invoiceRepository, customerRepository);
    }

    @Override
    protected Date getCurrentTime() {
        return date;
    }

    public void setFakeSystemTime(Date date) {
        this.date = date;
    }
}

public class InvoicingServiceTest {
    InvoiceRepository invoiceRepositoryMock;
    OrderRepository orderRepositoryStub;
    CustomerRepository customerRepositoryStub;
    ReferenceSource referenceGeneratorStub;

    TestInvoiceService testedService;

    private final Date fakeSystemTime = Date.from(LocalDate.of(2024, 5, 15).
            atStartOfDay(ZoneId.systemDefault()).toInstant());

    @Before
    public void setUp() {
        invoiceRepositoryMock = mock(InvoiceRepository.class);
        orderRepositoryStub = mock(OrderRepository.class);
        customerRepositoryStub = mock(CustomerRepository.class);
        referenceGeneratorStub = mock(ReferenceSource.class);

        // Note the use of TestInvoiceService here. It's there because we want to override
        // a method.
        testedService = new TestInvoiceService(orderRepositoryStub,
                invoiceRepositoryMock, customerRepositoryStub);

        // Setter injection: In a real test, mixing constructor and setter injection would
        // be confusing. Here, it's applied for illustrative purposes.
        testedService.setReferenceSource(referenceGeneratorStub);
    }

    // From a testing perspective, this isn't a great test. However, it's quite successful in mimicking what legacy
    // code really looks like and it demonstrates the various dependency breaking techniques.
    @Test
    public void aCustomersInvoiceHasTheRightDetails() {
        // Arrange
        final long customerId = 1234;
        final String reference = "REF";
        final boolean isInvoiced = false;
        Customer customer = new Customer(customerId, "Kalle", "Anka", "Paradisäppelvägen 113");
        Order order = new Order(5678, customerId, isInvoiced, new Date());
        order.setItems(List.of(new OrderItem(987, "Strumpor", 50.0, 2)));

        when(orderRepositoryStub.getLatestOrder(customerId)).thenReturn(order);
        when(customerRepositoryStub.getCustomerById(customerId)).thenReturn(customer);
        when(referenceGeneratorStub.generateInvoiceReference(customerId)).thenReturn(reference);
        testedService.setFakeSystemTime(fakeSystemTime);

        // Act
        testedService.invoiceCustomer(customerId);

        // Assert

        // This verification isn't necessary, but such constructs can be used during
        // intermediate steps
        //verify(invoiceRepositoryMock).createInvoice(argThat(
        //        i -> i.getCustomerId() == customerId && i.getDate().equals(fakeSystemTime)));

        Invoice expectedInvoice = new Invoice(customerId, fakeSystemTime, reference);
        expectedInvoice.setAmount(100.0); // 2 * price of strumpor
        expectedInvoice.setCustomerName("Anka, Kalle");
        expectedInvoice.setAddress(customer.address());

        verify(invoiceRepositoryMock).createInvoice(expectedInvoice);
    }
}
