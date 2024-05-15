package se.craftsmanship.refactoring.problem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;

public class AddAction extends Action {

    TodoRepository todoRepository;

    public AddAction(String title) {
        this(title, Globals.REPO);
    }

    private AddAction(String title, TodoRepository todoRepository) {
        super(title);
        this.todoRepository = todoRepository;
    }

    public void display() {
        System.out.println(title);
        for (int character = 0; character < title.substring(0).length();character++)
            System.out.print("#");
        System.out.println("\n");
        String[] values = new Scanner(System.in).nextLine().split(",");
        if (values.length != 3) {
            throw new IllegalArgumentException("Fel antal parametrar");
        }
        try {
            todoRepository.add(values[0], values[1],
                    new SimpleDateFormat("yyyy-MM-dd").parse(values[2]));
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }

    }
}
