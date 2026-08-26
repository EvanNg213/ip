package chocolate;

import java.io.IOException;
import java.nio.file.Path;

import chocolate.exception.ChocolateException;
import chocolate.parser.ParsedCommand;
import chocolate.parser.Parser;
import chocolate.storage.Storage;
import chocolate.task.Task;
import chocolate.task.TaskList;
import chocolate.ui.Ui;

/** Coordinates parsing, task management, storage, and user interaction. */
public class Chocolate {
    private final Storage storage;
    private final Ui ui;
    private final TaskList tasks;

    /** Creates Chocolate and loads tasks from the configured relative data path. */
    public Chocolate(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(Path.of(filePath));
        TaskList loadedTasks;
        try {
            loadedTasks = new TaskList(storage.load());
        } catch (IOException e) {
            loadedTasks = new TaskList();
            ui.showLoadingError();
        }
        this.tasks = loadedTasks;
    }

    /** Runs the command loop until the user enters the bye command. */
    public void run() {
        ui.showWelcome();
        boolean isExit = false;

        while (!isExit) {
            String input = ui.readCommand();
            ui.showLine();
            try {
                ParsedCommand command = Parser.parse(input);
                isExit = execute(command);
            } catch (ChocolateException e) {
                ui.showError(e.getMessage());
            } catch (IOException e) {
                ui.showError("I could not save your tasks to the hard disk.");
            }
        }
    }

    /** Executes one parsed command and returns true only for the exit command. */
    private boolean execute(ParsedCommand command) throws ChocolateException, IOException {
        switch (command.getType()) {
        case BYE:
            ui.showGoodbye();
            return true;
        case LIST:
            ui.showTaskList(tasks);
            break;
        case MARK:
            tasks.mark(command.getTaskIndex());
            storage.save(tasks);
            ui.showMarked(tasks.get(command.getTaskIndex()));
            break;
        case UNMARK:
            tasks.unmark(command.getTaskIndex());
            storage.save(tasks);
            ui.showUnmarked(tasks.get(command.getTaskIndex()));
            break;
        case TODO:
        case DEADLINE:
        case EVENT:
            tasks.add(command.getTask());
            storage.save(tasks);
            ui.showAdded(command.getTask(), tasks.size());
            break;
        case DELETE:
            Task deletedTask = tasks.delete(command.getTaskIndex());
            storage.save(tasks);
            ui.showDeleted(deletedTask, tasks.size());
            break;
        default:
            throw new ChocolateException("Unable to execute the command.");
        }
        return false;
    }

    public static void main(String[] args) {
        new Chocolate("data/duke.txt").run();
    }
}
