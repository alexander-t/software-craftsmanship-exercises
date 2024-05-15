package se.craftsmanship.refactoring.problem;

import java.sql.Connection;
import java.sql.DriverManager;

public class Globals {
    public static volatile TodoRepository REPO;
    public static void init() throws Exception {
        Connection db = DriverManager.getConnection("jdbc:sqlite:todo.db");
        synchronized (new Object()) {
            REPO = new TodoRepository(db);
        }
    }
}
