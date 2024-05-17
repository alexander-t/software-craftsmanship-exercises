package se.craftsmanship.dependencies.problem.repository;

import se.craftsmanship.dependencies.problem.model.Order;
import se.craftsmanship.dependencies.problem.model.OrderItem;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository extends Repository {

    public Order getLatestOrder(long customerId) {
        final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM `order` WHERE customer_id=? ORDER BY date DESC")) {
            stmt.setLong(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Order order = new Order(rs.getLong("id"), rs.getLong("customer_id"),
                            rs.getBoolean("invoiced"), sdf.parse(rs.getString("date")));
                    order.setItems(getOrderItems(order.getId()));
                    return order;
                } else {
                    throw new IllegalArgumentException("No customer with id " + customerId);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<OrderItem> getOrderItems(long orderId) {
        List<OrderItem> items = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM order_item WHERE order_id=?")) {
            stmt.setLong(1, orderId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    items.add(new OrderItem(rs.getLong("id"), rs.getString("product"),
                            rs.getDouble("price"), rs.getInt("quantity")));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return items;
    }
}
