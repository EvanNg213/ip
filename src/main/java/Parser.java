import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/** Converts raw user input into commands that the application can execute. */
public class Parser {
    private static final String INVALID_COMMAND_MESSAGE =
            "That is not a valid command. Please try any of these: todo, deadline, event, "
                    + "list, mark, unmark, delete, or bye.";

    /** Parses a complete line of user input. */
    public static ParsedCommand parse(String input) throws ChocolateException {
        if (input.equals("bye")) {
            return ParsedCommand.simple(CommandType.BYE);
        } else if (input.equals("list")) {
            return ParsedCommand.simple(CommandType.LIST);
        } else if (input.equals("mark") || input.startsWith("mark ")) {
            return ParsedCommand.withIndex(CommandType.MARK, parseTaskIndex(input, "mark"));
        } else if (input.equals("unmark") || input.startsWith("unmark ")) {
            return ParsedCommand.withIndex(CommandType.UNMARK, parseTaskIndex(input, "unmark"));
        } else if (input.equals("todo") || input.startsWith("todo ")) {
            return ParsedCommand.withTask(CommandType.TODO, parseTodo(input));
        } else if (input.equals("deadline") || input.startsWith("deadline ")) {
            return ParsedCommand.withTask(CommandType.DEADLINE, parseDeadline(input));
        } else if (input.equals("event") || input.startsWith("event ")) {
            return ParsedCommand.withTask(CommandType.EVENT, parseEvent(input));
        } else if (input.equals("delete") || input.startsWith("delete ")) {
            return ParsedCommand.withIndex(CommandType.DELETE, parseTaskIndex(input, "delete"));
        }
        throw new ChocolateException(INVALID_COMMAND_MESSAGE);
    }

    private static Todo parseTodo(String input) throws ChocolateException {
        String description = input.substring("todo".length()).trim();
        if (description.isEmpty()) {
            throw new ChocolateException(
                    "Please provide a valid description after the command you used!");
        }
        return new Todo(description);
    }

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
}
