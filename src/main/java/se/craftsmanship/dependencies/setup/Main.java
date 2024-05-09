package se.craftsmanship.dependencies.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:customers.db");
             Statement stmt = conn.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS customer");
            stmt.execute("DROP TABLE IF EXISTS `order`");
            stmt.execute("DROP TABLE IF EXISTS order_item");
            stmt.execute("DROP TABLE IF EXISTS invoice");
            stmt.execute("CREATE TABLE customer (id INTEGER PRIMARY KEY, first_name TEXT NOT NULL, last_name TEXT NOT NULL, address TEXT NOT NULL)");
            stmt.execute("CREATE TABLE `order` (id INTEGER PRIMARY KEY, customer_id INTEGER NOT NULL, date TEXT NOT NULL, invoiced BOOLEAN NOT NULL DEFAULT FALSE, FOREIGN KEY (customer_id) REFERENCES customer(id))");
            stmt.execute("CREATE TABLE order_item (id INTEGER PRIMARY KEY, order_id INTEGER NOT NULL, product TEXT NOT NULL, price REAL NOT NULL, quantity INTEGER NOT NULL, FOREIGN KEY (order_id) REFERENCES `order`(id))");
            stmt.execute("CREATE TABLE invoice (id INTEGER PRIMARY KEY, customer_id INTEGER NOT NULL, date TEXT NOT NULL, reference TEXT NOT NULL, customer_name TEXT NOT NULL , address TEXT NOT NULL, amount REAL NOT NULL, FOREIGN KEY (customer_id) REFERENCES customer(id))");
            stmt.execute("INSERT INTO customer (id, first_name, last_name, address) VALUES(1, 'Kalle', 'Anka', 'Paradisäppelvägen 13, 113 13 Ankeborg')");
            stmt.execute("INSERT INTO `order` (id, customer_id, date, invoiced) VALUES(NULL, 1, datetime('now','localtime'), FALSE)");
            stmt.execute("INSERT INTO order_item (id, order_id, product, price, quantity) VALUES(NULL, 1, 'Strumpor', 49.90, 2)");
            stmt.execute("INSERT INTO order_item (id, order_id, product, price, quantity) VALUES(NULL, 1, 'Skor', 500.0, 1)");
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(100);
        }
    }
}
