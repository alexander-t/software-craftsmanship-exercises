package se.craftsmanship.dependencies.repository;

import java.sql.Connection;
import java.sql.DriverManager;

public class Repository {
    protected Connection conn;

    public Repository() {
        this("customers.db");
    }

    public Repository(String dbName) {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:" + dbName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
