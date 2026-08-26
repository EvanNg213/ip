package chocolate.ui;

import java.util.Scanner;

import chocolate.task.Task;
import chocolate.task.TaskList;

/** Handles all console input and output for Chocolate. */
public class Ui {
    private static final String DIVIDER = "**************************************";
    private final Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public void showWelcome() {
        System.out.println(DIVIDER);
        System.out.println("Chocolate");
        System.out.println("Hi, my name is Chocolate!");
        System.out.println("How may I help you today?");
        System.out.println(DIVIDER);
    }

    public String readCommand() {
        return scanner.nextLine();
    }

    public void showLine() {
        System.out.println(DIVIDER);
    }

    public void showGoodbye() {
        System.out.println("Thank you and see you again");
        showLine();
    }

    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    public void showMarked(Task task) {
        System.out.println("Well Done! I have marked this task as done:");
        System.out.println("  [X] " + task.getDescription());
        showLine();
    }

    public void showUnmarked(Task task) {
        System.out.println("Alright, I have marked this task as not done yet:");
        System.out.println("  [ ] " + task.getDescription());
        showLine();
    }

    public void showAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    public void showDeleted(Task task, int taskCount) {
        System.out.println("Got it. I have removed the task:");
        System.out.println("  " + task);
        System.out.println("You now have " + taskCount + " tasks left in your list!");
        showLine();
    }

    public void showError(String message) {
        System.out.println("Oops! " + message);
        showLine();
    }

    public void showLoadingError() {
        System.out.println("Warning: Saved tasks could not be loaded. Starting with an empty list.");
    }
}
