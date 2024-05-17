package se.craftsmanship.dependencies.solution;

import se.craftsmanship.dependencies.problem.ReferenceGenerator;

// This class assumes that the ReferenceGenerator class doesn't really have to be a singleton
// and that a normal class will do fine. This is great for testability.
public class ReferenceSource {
    private final ReferenceGenerator legacyGenerator = ReferenceGenerator.getInstance();

    public String generateInvoiceReference(long customerId) {
        return legacyGenerator.generateInvoiceReference(customerId);
    }
}
