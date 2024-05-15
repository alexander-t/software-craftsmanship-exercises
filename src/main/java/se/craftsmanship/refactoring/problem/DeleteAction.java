package refactoring.problem;

import java.util.List;
import java.util.Scanner;

public class DeleteAction extends Action {
    private TodoRepository todoRepository;

    public DeleteAction(String title, TodoRepository todoRepository) {
        super(title);
        this.todoRepository = todoRepository;
    }

    public void display() {
        System.out.println(title);
        for (int character = 0; character < title.substring(0).length();character++)
            System.out.print("#");
        System.out.println("\n");
        int i = 0;
        List<TodoItem> items = todoRepository.getAll();
        System.out.println(i++ + "\tTillbaka");
        for (TodoItem item : items) {
            System.out.println(i + "\t" + item.getText());
            i++;
        }
        System.out.println("Välj alternativ\n");

        try {
            Scanner scanner = new Scanner(System.in);
            String str = scanner.nextLine();
            int input = Integer.parseInt(str);
            if (input == 0) {
                return;
            }
            else if (input > 0 && input <= items.size()) {
                todoRepository.delete(items.get(input - 1));
            } else {
                System.out.println("Ogiltigt alternativ!\n");
            }
        } catch (NumberFormatException e) {
            System.out.println("Ogiltigt alternativ!\n");
        }
    }
}
