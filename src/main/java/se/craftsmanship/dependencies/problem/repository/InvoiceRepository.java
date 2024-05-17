package se.craftsmanship.dependencies.problem.repository;

import se.craftsmanship.dependencies.problem.model.Invoice;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;

public class InvoiceRepository extends Repository {
    public void createInvoice(Invoice invoice) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO invoice(id, customer_id, date, reference, customer_name, address, amount) " +
                        "VALUES(NULL, ?, ?, ?, ?, ?, ?)")) {
            stmt.setLong(1, invoice.getCustomerId());
            stmt.setString(2, sdf.format(invoice.getDate()));
            stmt.setString(3, invoice.getReference());
            stmt.setString(4, invoice.getCustomerName());
            stmt.setString(5, invoice.getAddress());
            stmt.setDouble(6, invoice.getAmount());
            stmt.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
