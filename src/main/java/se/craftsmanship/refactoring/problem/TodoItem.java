package se.craftsmanship.refactoring.problem;

import java.util.Date;

public class TodoItem {
    public int id;
    protected String text;
    private String color;
    Date deadline;

    public TodoItem(int id, String text, String color, Date deadline) {
        this.id = id;
        this.text = text;
        this.color = color;
        this.deadline = deadline;
    }

    public TodoItem(int id, String text) {
        this(id, text, null, null);
    }

    public TodoItem(String text) {
        this(0, text, null, null);
    }

    public String getText() {
        return text;
    }

    public String getColor() {
        return color;
    }

    public Date getDeadline() {
        return deadline;
    }

    public int getId() {
        return id;
    }
}
