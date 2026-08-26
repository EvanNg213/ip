package chocolate.ui;

import java.util.List;
import java.util.Scanner;

import chocolate.task.Task;
import chocolate.task.TaskList;

/**
 * Handles all console input and output for Chocolate.
 */
public class Ui {
    private static final String DIVIDER = "**************************************";
    private final Scanner scanner;

    /**
     * Creates a UI that reads commands from standard input.
     */
    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    /**
     * Shows the application greeting.
     */
    public void showWelcome() {
        System.out.println(DIVIDER);
        System.out.println("Chocolate");
        System.out.println("Hi, my name is Chocolate!");
        System.out.println("How may I help you today?");
        System.out.println(DIVIDER);
    }

    /**
     * Reads the next complete user command.
     *
     * @return Command entered by the user.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Shows a divider between responses.
     */
    public void showLine() {
        System.out.println(DIVIDER);
    }

    /**
     * Shows the farewell message.
     */
    public void showGoodbye() {
        System.out.println("Thank you and see you again");
        showLine();
    }

    /**
     * Shows every task with a one-based display number.
     *
     * @param tasks Tasks to display.
     */
    public void showTaskList(TaskList tasks) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Shows tasks whose descriptions match a search keyword.
     *
     * @param tasks Matching tasks to display.
     */
    public void showMatchingTasks(List<Task> tasks) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + "." + tasks.get(i));
        }
        showLine();
    }

    /**
     * Shows confirmation that a task was completed.
     *
     * @param task Updated task.
     */
    public void showMarked(Task task) {
        System.out.println("Well Done! I have marked this task as done:");
        System.out.println("  [X] " + task.getDescription());
        showLine();
    }

    /**
     * Shows confirmation that a task was made incomplete.
     *
     * @param task Updated task.
     */
    public void showUnmarked(Task task) {
        System.out.println("Alright, I have marked this task as not done yet:");
        System.out.println("  [ ] " + task.getDescription());
        showLine();
    }

    /**
     * Shows confirmation that a task was added.
     *
     * @param task Added task.
     * @param taskCount Number of tasks after the addition.
     */
    public void showAdded(Task task, int taskCount) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
        showLine();
    }

    /**
     * Shows confirmation that a task was deleted.
     *
     * @param task Deleted task.
     * @param taskCount Number of tasks after the deletion.
     */
    public void showDeleted(Task task, int taskCount) {
        System.out.println("Got it. I have removed the task:");
        System.out.println("  " + task);
        System.out.println("You now have " + taskCount + " tasks left in your list!");
        showLine();
    }

    /**
     * Shows a user-facing error message.
     *
     * @param message Explanation of the error.
     */
    public void showError(String message) {
        System.out.println("Oops! " + message);
        showLine();
    }

    /**
     * Shows a warning that saved tasks could not be loaded.
     */
    public void showLoadingError() {
        System.out.println("Warning: Saved tasks could not be loaded. Starting with an empty list.");
    }
}
