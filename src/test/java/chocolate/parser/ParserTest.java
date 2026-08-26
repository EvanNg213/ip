package chocolate.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import chocolate.exception.ChocolateException;
import chocolate.task.Deadline;
import chocolate.task.Event;
import chocolate.task.Todo;

/**
 * Tests command recognition and validation in {@link Parser}.
 */
public class ParserTest {
    @Test
    public void parse_simpleCommands_correctTypes() throws ChocolateException {
        assertEquals(CommandType.BYE, Parser.parse("bye").getType());
        assertEquals(CommandType.LIST, Parser.parse("list").getType());
    }

    @Test
    public void parseAddCommands_validInputs_structuredTasks() throws ChocolateException {
        ParsedCommand todoCommand = Parser.parse("todo read book");
        Todo todo = assertInstanceOf(Todo.class, todoCommand.getTask());
        assertEquals(CommandType.TODO, todoCommand.getType());
        assertEquals("read book", todo.getDescription());

        ParsedCommand deadlineCommand =
                Parser.parse("deadline return book /by 2019-10-15");
        Deadline deadline = assertInstanceOf(Deadline.class, deadlineCommand.getTask());
        assertEquals(CommandType.DEADLINE, deadlineCommand.getType());
        assertEquals("return book", deadline.getDescription());
        assertEquals(LocalDate.of(2019, 10, 15), deadline.getBy());

        ParsedCommand eventCommand =
                Parser.parse("event project meeting /from Monday /to Tuesday");
        Event event = assertInstanceOf(Event.class, eventCommand.getTask());
        assertEquals(CommandType.EVENT, eventCommand.getType());
        assertEquals("project meeting", event.getDescription());
        assertEquals("Monday", event.getStart());
        assertEquals("Tuesday", event.getEnd());
    }

    @Test
    public void parseIndexCommands_validNumbers_zeroBasedIndexes() throws ChocolateException {
        assertEquals(1, Parser.parse("mark 2").getTaskIndex());
        assertEquals(2, Parser.parse("unmark 3").getTaskIndex());
        assertEquals(3, Parser.parse("delete 4").getTaskIndex());
    }

    @Test
    public void parseFind_validKeyword_structuredKeyword() throws ChocolateException {
        ParsedCommand command = Parser.parse("find project meeting");

        assertEquals(CommandType.FIND, command.getType());
        assertEquals("project meeting", command.getKeyword());
    }

    @Test
    public void parse_invalidCommands_exceptionThrown() {
        assertThrows(ChocolateException.class, () -> Parser.parse("unknown"));
        assertThrows(ChocolateException.class, () -> Parser.parse("todo"));
        assertThrows(ChocolateException.class, () -> Parser.parse("mark"));
        assertThrows(ChocolateException.class, () -> Parser.parse("delete abc"));
        assertThrows(ChocolateException.class, () -> Parser.parse("find"));
        assertThrows(ChocolateException.class,
                () -> Parser.parse("deadline return book /by 2019-02-30"));
        assertThrows(ChocolateException.class,
                () -> Parser.parse("event meeting /from Monday"));
    }
}
