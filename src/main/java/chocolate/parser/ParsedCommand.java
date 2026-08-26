package chocolate.parser;

import chocolate.task.Task;

/**
 * Holds the structured information extracted from one user command.
 */
public class ParsedCommand {
    private final CommandType type;
    private final Task task;
    private final int taskIndex;

    private ParsedCommand(CommandType type, Task task, int taskIndex) {
        this.type = type;
        this.task = task;
        this.taskIndex = taskIndex;
    }

    /**
     * Creates a command that does not require a task or index.
     *
     * @param type Type of command.
     * @return Parsed command containing only its type.
     */
    public static ParsedCommand simple(CommandType type) {
        return new ParsedCommand(type, null, -1);
    }

    /**
     * Creates a command containing a newly parsed task.
     *
     * @param type Type of add command.
     * @param task Task to add.
     * @return Parsed command containing the task.
     */
    public static ParsedCommand withTask(CommandType type, Task task) {
        return new ParsedCommand(type, task, -1);
    }

    /**
     * Creates a command referring to a zero-based task index.
     *
     * @param type Type of indexed command.
     * @param taskIndex Zero-based index of the target task.
     * @return Parsed command containing the task index.
     */
    public static ParsedCommand withIndex(CommandType type, int taskIndex) {
        return new ParsedCommand(type, null, taskIndex);
    }

    /**
     * Returns the command type.
     *
     * @return Command type.
     */
    public CommandType getType() {
        return type;
    }

    /**
     * Returns the task carried by an add command.
     *
     * @return Parsed task, or null when the command has no task.
     */
    public Task getTask() {
        return task;
    }

    /**
     * Returns the zero-based target task index.
     *
     * @return Task index, or -1 when the command has no index.
     */
    public int getTaskIndex() {
        return taskIndex;
    }
}
