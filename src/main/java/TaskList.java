import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Owns the task collection and provides operations that modify it. */
public class TaskList {
    private final List<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    public void add(Task task) {
        tasks.add(task);
    }

    public Task delete(int index) throws ChocolateException {
        ensureValidIndex(index);
        return tasks.remove(index);
    }

    public void mark(int index) throws ChocolateException {
        ensureValidIndex(index);
        tasks.get(index).markAsDone();
    }

    public void unmark(int index) throws ChocolateException {
        ensureValidIndex(index);
        tasks.get(index).markAsUndone();
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

    /** Returns a read-only view for services that need to inspect all tasks. */
    public List<Task> getAll() {
        return Collections.unmodifiableList(tasks);
    }

    private void ensureValidIndex(int index) throws ChocolateException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChocolateException("That task number does not exist in your list!");
        }
    }
}
