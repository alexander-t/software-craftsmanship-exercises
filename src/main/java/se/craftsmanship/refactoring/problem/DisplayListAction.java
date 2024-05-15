package se.craftsmanship.refactoring.problem;

public class DisplayListAction extends Action {

    public DisplayListAction(String title) {
        super(title);
    }

    public void display() {
        System.out.println(title);
        for (int character = 0; character < title.substring(0).length();character++)
            System.out.print("#");
        System.out.println("\n");
        for (TodoItem item : Globals.REPO.getAll()) {
            System.out.println(item.getText());
        }
    }
}
