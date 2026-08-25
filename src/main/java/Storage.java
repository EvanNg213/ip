import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Saves tasks to disk and restores them when Chocolate starts. */
public class Storage {
    private final Path filePath;

    public Storage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads every valid task from the data file.
     * A missing file represents a new user with an empty task list.
     */
    public ArrayList<Task> load() throws IOException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) {
            return tasks;
        }

        for (String line : Files.readAllLines(filePath, StandardCharsets.UTF_8)) {
            Task task = parseTask(line);
            if (task != null) {
                tasks.add(task);
            }
        }
        return tasks;
    }

    /** Writes the current task list, creating its parent folder when necessary. */
    public void save(List<Task> tasks) throws IOException {
        Path parent = filePath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        ArrayList<String> lines = new ArrayList<>();
        for (Task task : tasks) {
            lines.add(formatTask(task));
        }
        Files.write(filePath, lines, StandardCharsets.UTF_8);
    }

    private String formatTask(Task task) {
        String status = task.isDone() ? "1" : "0";
        if (task instanceof Deadline) {
            Deadline deadline = (Deadline) task;
            return "D | " + status + " | " + task.getDescription() + " | " + deadline.getBy();
        } else if (task instanceof Event) {
            Event event = (Event) task;
            return "E | " + status + " | " + task.getDescription()
                    + " | " + event.getStart() + " | " + event.getEnd();
        }
        return "T | " + status + " | " + task.getDescription();
    }

    /** Returns null for malformed records so the remaining saved tasks can still load. */
    private Task parseTask(String line) {
        String[] fields = line.split("\\s*\\|\\s*", -1);
        try {
            if (fields.length < 3 || !(fields[1].equals("0") || fields[1].equals("1"))) {
                return null;
            }

            Task task;
            switch (fields[0]) {
            case "T":
                task = fields.length == 3 ? new Todo(fields[2]) : null;
                break;
            case "D":
                task = fields.length == 4 ? new Deadline(fields[2], fields[3]) : null;
                break;
            case "E":
                task = fields.length == 5 ? new Event(fields[2], fields[3], fields[4]) : null;
                break;
            default:
                task = null;
            }

            if (task != null && fields[1].equals("1")) {
                task.markAsDone();
            }
            return task;
        } catch (RuntimeException e) {
            return null;
        }
    }
}
