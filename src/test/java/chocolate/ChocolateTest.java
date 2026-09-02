package chocolate;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

/**
 * Tests that Chocolate returns replies that can be displayed by either UI.
 */
public class ChocolateTest {
    @TempDir
    Path temporaryDirectory;

    @Test
    public void getResponse_validCommands_returnsExpectedReplies() {
        Chocolate chocolate = new Chocolate(temporaryDirectory.resolve("tasks.txt").toString());

        assertEquals("Got it. I've added this task:\n  [T][ ] buy chocolate"
                        + "\nNow you have 1 tasks in the list.",
                chocolate.getResponse("todo buy chocolate"));
        assertEquals("Here are the tasks in your list:\n1.[T][ ] buy chocolate",
                chocolate.getResponse("list"));
    }

    @Test
    public void getResponse_invalidCommand_returnsErrorReply() {
        Chocolate chocolate = new Chocolate(temporaryDirectory.resolve("tasks.txt").toString());

        assertEquals("Oops! That is not a valid command. Please try any of these: todo, deadline, event, "
                        + "list, mark, unmark, delete, find, or bye.",
                chocolate.getResponse("invalid"));
    }
}
