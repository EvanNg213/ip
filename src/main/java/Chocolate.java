import java.util.ArrayList;
import java.util.Scanner;

/** Start application. */
public class Chocolate {
    public static void main(String[] args) {
        String divider = "**************************************";
        String banner = "Chocolate";

        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println(divider);
        System.out.println(banner);
        System.out.println("Hi, my name is Chocolate!");
        System.out.println("How may I help you today?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            System.out.println(divider);

            try {
                if (command.equals("bye")) {
                System.out.println("Thank you and see you again");
                System.out.println(divider);
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < tasks.size(); i++) {
                    System.out.println((i + 1) + "." + tasks.get(i));
                }
                System.out.println(divider);
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                tasks.get(taskIndex).markAsDone();
                System.out.println("Well Done! I have marked this task as done:");
                System.out.println("  [X] " + tasks.get(taskIndex).getDescription());
                System.out.println(divider);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                tasks.get(taskIndex).markAsUndone();
                System.out.println("Alright, I have marked this task as not done yet:");
                System.out.println("  [ ] " + tasks.get(taskIndex).getDescription());
                System.out.println(divider);
            } else if (command.equals("todo") || command.startsWith("todo ")) {
                String description = command.substring("todo".length()).trim();
                if (description.isEmpty()) {
                    throw new ChocolateException(
                            "Please provide a valid description after the command you used!");
                }
                tasks.add(new Todo(description));
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(divider);
            } else if (command.startsWith("deadline ")) {
                String details = command.substring("deadline ".length());
                int byIndex = details.indexOf(" /by ");

                String description = details.substring(0, byIndex);
                String by = details.substring(byIndex + " /by ".length());

                tasks.add(new Deadline(description, by));
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(divider);
            } else if (command.startsWith("event ")) {
                String details = command.substring("event ".length());
                int fromIndex = details.indexOf(" /from ");
                int toIndex = details.indexOf(" /to ");

                String description = details.substring(0, fromIndex);
                String from = details.substring(fromIndex + " /from ".length(), toIndex);
                String to = details.substring(toIndex + " /to ".length());

                tasks.add(new Event(description, from, to));
                System.out.println("Got it. I've added this task:");
                System.out.println("  " + tasks.get(tasks.size() - 1));
                System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                System.out.println(divider);
            } else if (command.equals("delete") || command.startsWith("delete ")) {
                    String taskNumberText = command.substring("delete".length()).trim();

                    if (taskNumberText.isEmpty()) {
                        throw new ChocolateException("Please provide a task number!");
                    }

                    int taskNum;
                    try {
                        taskNum = Integer.parseInt(taskNumberText);
                    } catch (NumberFormatException e) {
                        throw new ChocolateException("Please provide a whole number for the task number!");
                    }

                    int taskId = taskNum - 1;
                    if (taskId < 0 || taskId >= tasks.size()) {
                        throw new ChocolateException("That task number does not exist in your list!");
                    }

                    Task deletedTask = tasks.remove(taskId);

                    System.out.println("Got it. I have removed the task:");
                    System.out.println("  " + deletedTask);
                    System.out.println("You now have " + tasks.size() + " tasks left in your list!");
                    System.out.println(divider);
                } else {
                throw new ChocolateException(
                        "That is not a valid command. Please try any of these: todo, deadline, event, list, mark, unmark, delete, or bye.");
            }
            } catch (ChocolateException e) {
                System.out.println("Oops! " + e.getMessage());
                System.out.println(divider);
            }
        }
    }
}
