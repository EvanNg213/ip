import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Start application. */
public class Chocolate {
    public static void main(String[] args) {
        Ui ui = new Ui();
        Storage storage = new Storage(Path.of("data", "duke.txt"));
        TaskList tasks;
        try {
            tasks = new TaskList(storage.load());
        } catch (IOException e) {
            tasks = new TaskList();
            ui.showLoadingError();
        }

        ui.showWelcome();

        while (true) {
            String command = ui.readCommand();
            ui.showLine();

            try {
                if (command.equals("bye")) {
                    ui.showGoodbye();
                    break;
            } else if (command.equals("list")) {
                ui.showTaskList(tasks);
            } else if (command.startsWith("mark ")) {
                int taskIndex = Integer.parseInt(command.substring(5)) - 1;
                tasks.mark(taskIndex);
                storage.save(tasks);
                ui.showMarked(tasks.get(taskIndex));
            } else if (command.startsWith("unmark ")) {
                int taskIndex = Integer.parseInt(command.substring(7)) - 1;
                tasks.unmark(taskIndex);
                storage.save(tasks);
                ui.showUnmarked(tasks.get(taskIndex));
            } else if (command.equals("todo") || command.startsWith("todo ")) {
                String description = command.substring("todo".length()).trim();
                if (description.isEmpty()) {
                    throw new ChocolateException(
                            "Please provide a valid description after the command you used!");
                }
                tasks.add(new Todo(description));
                storage.save(tasks);
                ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
            } else if (command.startsWith("deadline ")) {
                String details = command.substring("deadline ".length());
                int byIndex = details.indexOf(" /by ");

                String description = details.substring(0, byIndex);
                String dateText = details.substring(byIndex + " /by ".length());
                LocalDate by = LocalDate.parse(dateText);

                tasks.add(new Deadline(description, by));
                storage.save(tasks);
                ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
            } else if (command.startsWith("event ")) {
                String details = command.substring("event ".length());
                int fromIndex = details.indexOf(" /from ");
                int toIndex = details.indexOf(" /to ");

                String description = details.substring(0, fromIndex);
                String from = details.substring(fromIndex + " /from ".length(), toIndex);
                String to = details.substring(toIndex + " /to ".length());

                tasks.add(new Event(description, from, to));
                storage.save(tasks);
                ui.showAdded(tasks.get(tasks.size() - 1), tasks.size());
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

                    Task deletedTask = tasks.delete(taskId);
                    storage.save(tasks);

                    ui.showDeleted(deletedTask, tasks.size());
                } else {
                throw new ChocolateException(
                        "That is not a valid command. Please try any of these: todo, deadline, event, list, mark, unmark, delete, or bye.");
            }
            } catch (ChocolateException e) {
                ui.showError(e.getMessage());
            } catch (DateTimeParseException e) {
                ui.showError("Please use the date format yyyy-MM-dd.");
            } catch (IOException e) {
                ui.showError("I could not save your tasks to the hard disk.");
            }
        }
    }
}
