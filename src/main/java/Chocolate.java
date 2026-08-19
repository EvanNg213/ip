import java.util.Scanner;

/** Start application. */
public class Chocolate {
    public static void main(String[] args) {
        String divider = "**************************************";
        String banner = "Chocolate";

        Task[] tasks = new Task[100];
        int numberOfTasks = 0;

        System.out.println(divider);
        System.out.println(banner);
        System.out.println("Hi, my name is Chocolate!");
        System.out.println("How may I help you today?");
        System.out.println(divider);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            String command = scanner.nextLine();

            System.out.println(divider);

            if (command.equals("bye")) {
                System.out.println("Thank you and see you again");
                System.out.println(divider);
                break;
            } else if (command.equals("list")) {
                System.out.println("Here are the tasks in your list:");
                for (int i = 0; i < numberOfTasks; i++) {
                    System.out.println((i + 1) + ".[" + tasks[i].getStatusIcon()
                            + "] " + tasks[i].getDescription());
                }
                System.out.println(divider);
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                tasks[taskIndex].markAsDone();
                System.out.println("Well Done! I have marked this task as done:");
                System.out.println("  [X] " + tasks[taskIndex].getDescription());
                System.out.println(divider);
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                tasks[taskIndex].markAsUndone();
                System.out.println("Alright, I have marked this task as not done yet:");
                System.out.println("  [ ] " + tasks[taskIndex].getDescription());
                System.out.println(divider);
            } else {
                tasks[numberOfTasks] = new Task(command);
                numberOfTasks++;
                System.out.println("added: " + command);
                System.out.println(divider);
            }
        }
    }
}
