package se.craftsmanship.dependencies.repository;

import se.craftsmanship.dependencies.model.Customer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRepository extends Repository {

    public Customer getCustomerById(long customerId) {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM customer WHERE id=?")) {
            stmt.setLong(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Customer(rs.getLong("id"), rs.getString("first_name"),
                            rs.getString("last_name"), rs.getString("address"));
                } else {
                    throw new IllegalArgumentException("No customer with id " + customerId);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
