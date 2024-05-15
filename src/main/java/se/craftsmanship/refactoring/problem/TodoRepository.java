package se.craftsmanship.refactoring.problem;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class TodoRepository {
    private Connection connection;

    public TodoRepository(Connection connection) {
        this.connection = connection;
    }

    public void add(String text, final String color, java.util.Date deadline) {
        try (PreparedStatement insert = connection.prepareStatement("INSERT INTO todo (entry, color, deadline) VALUES (?, ?, ?)")) {
            insert.setString(1, text);
            insert.setString(2, color != null ? color.toString().toLowerCase() : null);
            if (deadline != null) {
                insert.setLong(3, deadline.getTime());
            } else {
                insert.setNull(3, Types.INTEGER);
            }
            insert.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public List<TodoItem> getAll() {
        List<TodoItem> items = new LinkedList<>();
        try (PreparedStatement getAll = connection.prepareStatement("SELECT * FROM todo");
             ResultSet rs = getAll.executeQuery()) {
            while (rs.next()) {
                items.add(new TodoItem(rs.getInt("id"), rs.getString("entry")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    public void delete(TodoItem todoItem) {
        try (PreparedStatement delete = connection.prepareStatement("DELETE FROM todo WHERE id=?")) {
            delete.setInt(1, todoItem.getId());
            delete.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
