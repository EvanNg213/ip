package chocolate.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import chocolate.exception.ChocolateException;

/**
 * Owns the task collection and provides operations that modify it.
 */
public class TaskList {
    private final List<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list containing copies of the supplied task references.
     *
     * @param tasks Initial tasks.
     */
    public TaskList(List<Task> tasks) {
        this.tasks = new ArrayList<>(tasks);
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task Task to add.
     */
    public void add(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes the task at the specified index.
     *
     * @param index Zero-based task index.
     * @return Deleted task.
     * @throws ChocolateException If the index does not identify a task.
     */
    public Task delete(int index) throws ChocolateException {
        ensureValidIndex(index);
        return tasks.remove(index);
    }

    /**
     * Marks the task at the specified index as complete.
     *
     * @param index Zero-based task index.
     * @throws ChocolateException If the index does not identify a task.
     */
    public void mark(int index) throws ChocolateException {
        ensureValidIndex(index);
        tasks.get(index).markAsDone();
    }

    /**
     * Marks the task at the specified index as incomplete.
     *
     * @param index Zero-based task index.
     * @throws ChocolateException If the index does not identify a task.
     */
    public void unmark(int index) throws ChocolateException {
        ensureValidIndex(index);
        tasks.get(index).markAsUndone();
    }

    /**
     * Returns the task at the specified index.
     *
     * @param index Zero-based task index.
     * @return Task at the index.
     */
    public Task get(int index) {
        return tasks.get(index);
    }

    /**
     * Returns the number of tasks.
     *
     * @return Number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Returns a read-only view for services that need to inspect all tasks.
     *
     * @return Unmodifiable view of the task list.
     */
    public List<Task> getAll() {
        return Collections.unmodifiableList(tasks);
    }

    /**
     * Verifies that an index identifies an existing task.
     *
     * @param index Zero-based task index.
     * @throws ChocolateException If the index does not identify a task.
     */
    private void ensureValidIndex(int index) throws ChocolateException {
        if (index < 0 || index >= tasks.size()) {
            throw new ChocolateException("That task number does not exist in your list!");
        }
    }
}
