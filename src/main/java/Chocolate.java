import java.util.Scanner;

/** Start application. */
public class Chocolate {
    public static void main(String[] args) {
        String divider = "**************************************";
        String banner = "Chocolate";

        String[] tasks = new String[100];
        boolean[] isDone = new boolean[100];
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
                    String status = isDone[i] ? "[X]" : "[ ]";
                    System.out.println((i + 1) + "." + status + " " + tasks[i]);
                }
                System.out.println(divider);
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                isDone[taskIndex] = true;
                System.out.println("Nice! I've marked this task as done:");
                System.out.println("  [X] " + tasks[taskIndex]);
                System.out.println(divider);
            } else {
                tasks[numberOfTasks] = command;
                numberOfTasks++;
                System.out.println("added: " + command);
                System.out.println(divider);
            }
        }
    }
}
