package chocolate.parser;

import chocolate.task.Task;

/** Holds the structured information extracted from one user command. */
public class ParsedCommand {
    private final CommandType type;
    private final Task task;
    private final int taskIndex;
    private final String keyword;

    private ParsedCommand(CommandType type, Task task, int taskIndex, String keyword) {
        this.type = type;
        this.task = task;
        this.taskIndex = taskIndex;
        this.keyword = keyword;
    }

    public static ParsedCommand simple(CommandType type) {
        return new ParsedCommand(type, null, -1, null);
    }

    public static ParsedCommand withTask(CommandType type, Task task) {
        return new ParsedCommand(type, task, -1, null);
    }

    public static ParsedCommand withIndex(CommandType type, int taskIndex) {
        return new ParsedCommand(type, null, taskIndex, null);
    }

    public static ParsedCommand withKeyword(CommandType type, String keyword) {
        return new ParsedCommand(type, null, -1, keyword);
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

    public String getKeyword() {
        return keyword;
    }
}
