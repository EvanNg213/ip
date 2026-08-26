package chocolate.parser;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import chocolate.exception.ChocolateException;
import chocolate.task.Deadline;
import chocolate.task.Event;
import chocolate.task.Todo;

/**
 * Converts raw user input into commands that the application can execute.
 */
public class Parser {
    private static final String INVALID_COMMAND_MESSAGE =
            "That is not a valid command. Please try any of these: todo, deadline, event, "
                    + "list, mark, unmark, delete, find, or bye.";

    /**
     * Prevents instantiation of this utility class.
     */
    private Parser() {
    }

    /**
     * Parses a complete line of user input.
     *
     * @param input User-entered command line.
     * @return Structured command ready for execution.
     * @throws ChocolateException If the command or its arguments are invalid.
     */
    public static ParsedCommand parse(String input) throws ChocolateException {
        if (input.equals("bye")) {
            return ParsedCommand.createSimple(CommandType.BYE);
        } else if (input.equals("list")) {
            return ParsedCommand.createSimple(CommandType.LIST);
        } else if (input.equals("mark") || input.startsWith("mark ")) {
            return ParsedCommand.createWithIndex(CommandType.MARK, parseTaskIndex(input, "mark"));
        } else if (input.equals("unmark") || input.startsWith("unmark ")) {
            return ParsedCommand.createWithIndex(
                    CommandType.UNMARK, parseTaskIndex(input, "unmark"));
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            return ParsedCommand.createWithTask(CommandType.TODO, parseTodo(input));
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            return ParsedCommand.createWithTask(CommandType.DEADLINE, parseDeadline(input));
        } else if (input.equals("event") || input.startsWith("event ")) {
            return ParsedCommand.createWithTask(CommandType.EVENT, parseEvent(input));
        } else if (input.equals("delete") || input.startsWith("delete ")) {
            return ParsedCommand.createWithIndex(
                    CommandType.DELETE, parseTaskIndex(input, "delete"));
        } else if (input.equals("find") || input.startsWith("find ")) {
            return ParsedCommand.createWithKeyword(CommandType.FIND, parseKeyword(input));
        }
        throw new ChocolateException(INVALID_COMMAND_MESSAGE);
    }

    /**
     * Parses a todo command into a task.
     *
     * @param input Complete todo command.
     * @return Todo described by the command.
     * @throws ChocolateException If the description is empty.
     */
    private static Todo parseTodo(String input) throws ChocolateException {
        String description = input.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new ChocolateException(
                    "Please provide a valid description after the command you used!");
        }
        return new Todo(description);
    }

    /**
     * Parses a deadline command into a dated task.
     *
     * @param input Complete deadline command.
     * @return Deadline described by the command.
     * @throws ChocolateException If the command structure or date is invalid.
     */
    private static Deadline parseDeadline(String input) throws ChocolateException {
        String details = input.substring("deadline".length()).trim();
        int byIndex = details.indexOf(" /by ");
        if (byIndex <= 0 || byIndex + " /by ".length() >= details.length()) {
            throw new ChocolateException("Please use: deadline DESCRIPTION /by yyyy-MM-dd.");
        }

        String description = details.substring(0, byIndex);
        String dateText = details.substring(byIndex + " /by ".length());
        try {
            return new Deadline(description, LocalDate.parse(dateText));
        } catch (DateTimeParseException e) {
            throw new ChocolateException("Please use the date format yyyy-MM-dd.");
        }
    }

    /**
     * Parses an event command into a task with start and end details.
     *
     * @param input Complete event command.
     * @return Event described by the command.
     * @throws ChocolateException If the command structure is invalid.
     */
    private static Event parseEvent(String input) throws ChocolateException {
        String details = input.substring("event".length()).trim();
        int fromIndex = details.indexOf(" /from ");
        int toIndex = details.indexOf(" /to ");
        if (fromIndex <= 0 || toIndex <= fromIndex + " /from ".length()
                || toIndex + " /to ".length() >= details.length()) {
            throw new ChocolateException("Please use: event DESCRIPTION /from START /to END.");
        }

        String description = details.substring(0, fromIndex);
        String from = details.substring(fromIndex + " /from ".length(), toIndex);
        String to = details.substring(toIndex + " /to ".length());
        return new Event(description, from, to);
    }

    /**
     * Extracts and converts a one-based task number into a zero-based index.
     *
     * @param input Complete indexed command.
     * @param commandWord Command word preceding the task number.
     * @return Zero-based task index.
     * @throws ChocolateException If the task number is missing or not an integer.
     */
    private static int parseTaskIndex(String input, String commandWord)
            throws ChocolateException {
        String numberText = input.substring(commandWord.length()).trim();
        if (numberText.isEmpty()) {
            throw new ChocolateException("Please provide a task number!");
        }
        try {
            return Integer.parseInt(numberText) - 1;
        } catch (NumberFormatException e) {
            throw new ChocolateException("Please provide a whole number for the task number!");
        }
    }

    /**
     * Extracts a non-empty keyword from a find command.
     *
     * @param input Complete find command.
     * @return Keyword to search for.
     * @throws ChocolateException If the keyword is empty.
     */
    private static String parseKeyword(String input) throws ChocolateException {
        String keyword = input.substring("find".length()).trim();
        if (keyword.isEmpty()) {
            throw new ChocolateException("Please provide a keyword to find!");
        }
        return keyword;
    }
}
