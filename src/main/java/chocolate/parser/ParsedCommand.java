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

    public static ParsedCommand createSimple(CommandType type) {
        return new ParsedCommand(type, null, -1);
    }

    public static ParsedCommand createWithTask(CommandType type, Task task) {
        return new ParsedCommand(type, task, -1);
    }

    public static ParsedCommand createWithIndex(CommandType type, int taskIndex) {
        return new ParsedCommand(type, null, taskIndex);
    }

    public CommandType getType() {
        return type;
    }

    public Task getTask() {
        return task;
    }

    public int getTaskIndex() {
        return taskIndex;
    }
}
