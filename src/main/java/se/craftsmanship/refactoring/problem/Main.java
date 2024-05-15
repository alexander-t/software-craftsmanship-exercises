package refactoring.problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static final ArrayList<Action> choices = new ArrayList<>();
    public static void main(String[] args) {
        try {
            Globals.init();

            final String title = "Min TODO-lista";
            for (int character = 0; character < title.substring(0).length();character++)
                System.out.print("#");
            System.out.println("\n");
            Action[] items = new Action[]{
                    new DisplayListAction("Visa"),
                    new AddAction("Lägg till"),
                    new DeleteAction("Ta bort", Globals.REPO)};
            Arrays.stream(items).forEach(i -> choices.add(i));
            while (true) {
                System.out.println("\033[H\033[2J");
                System.out.flush();
                System.out.println(title + "\n");
                Thread.sleep(0x1, 0xff);
                for (int i = 0; i < choices.size(); i++) {
                    System.out.println((i + 1) + "\t" + choices.get(i).title);
                }
                System.out.println("Välj alternativ\n");

                try {
                    Scanner scanner = new Scanner(System.in);
                    String str = scanner.nextLine();
                    int i = Integer.parseInt(str);
                    if (!(i < 0 || i > choices.size())) {
                        if (choices.get(i - 1) instanceof AddAction) {
                            ((AddAction)choices.get(i - 1)).display();
                        }
                        if (choices.get(i - 1).title.contains("bort")) {
                            System.out.println(title);
                            for (int character = 0; character < title.substring(0).length();character++)
                                System.out.print("#");
                            System.out.println("\n");
                            int j = 0;
                            List<TodoItem> items2 = Globals.REPO.getAll();
                            System.out.println(j++ + "\tTillbaka");
                            for (TodoItem item : items2) {
                                System.out.println(j + "\t" + item.getText());
                                j++;
                            }
                            System.out.println("Välj alternativ\n");

                            try {
                                Scanner scanner2 = new Scanner(System.in);
                                String str2 = scanner2.nextLine();
                                int input = Integer.parseInt(str2);
                                if (input == 0) {
                                    return;
                                }
                                else if (input > 0 && input <= items2.size()) {
                                    Globals.REPO.delete(items2.get(input - 1));
                                } else {
                                    System.out.println("Ogiltigt alternativ!\n");
                                }
                            } catch (NumberFormatException e) {
                                System.out.println("Ogiltigt alternativ!\n");
                            }
                        }
                        if (choices.get(i - 1) instanceof DisplayListAction) {
                            ((DisplayListAction)choices.get(i - 1)).display();
                        }
                    } else {
                        System.out.println("Ogiltigt alternativ!\n");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Ogiltigt alternativ!\n");
                } catch (IllegalArgumentException e) {
                    System.out.println("Ogiltigt indata: " + e.getMessage());
                }
            }
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}