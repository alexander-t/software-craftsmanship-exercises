package refactoring.setup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class Main {
    public static void main(String[] args) throws Exception {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:todo.db");
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS todo (id INTEGER PRIMARY KEY, " +
                "entry TEXT NOT NULL, " +
                "color TEXT CHECK (color IN ('red', 'green', 'blue')), " +
                "deadline INTEGER)");
    }
}
