package yapbot.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import yapbot.task.Task;
import yapbot.task.Todo;

public class UiTest {

    private final ByteArrayOutputStream capturedOutput = new ByteArrayOutputStream();
    private PrintStream originalOut;

    @BeforeEach
    public void redirectSystemOut() {
        originalOut = System.out;
        System.setOut(new PrintStream(capturedOutput, true, StandardCharsets.UTF_8));
    }

    @AfterEach
    public void restoreSystemOut() {
        System.setOut(originalOut);
    }

    private String output() {
        return capturedOutput.toString(StandardCharsets.UTF_8);
    }

    @Test
    public void readCommand_returnsTheLineFromInput() {
        Ui ui = new Ui(new ByteArrayInputStream("todo read book\n".getBytes(StandardCharsets.UTF_8)));

        String command = ui.readCommand();

        assertEquals("todo read book", command);
    }

    @Test
    public void showWelcome_printsGreeting() {
        new Ui().showWelcome();

        assertTrue(output().contains("Hello! I'm YAPBOT."));
        assertTrue(output().contains("What can I do for you?"));
    }

    @Test
    public void showGoodbye_printsGoodbyeMessage() {
        new Ui().showGoodbye();

        assertTrue(output().contains("Bye. Hope to see you again soon!"));
    }

    @Test
    public void showMessage_printsGivenMessage() {
        new Ui().showMessage("custom message");

        assertTrue(output().contains("custom message"));
    }

    @Test
    public void showTaskAdded_printsTaskAndUpdatedCount() {
        Task task = new Todo("read book");

        new Ui().showTaskAdded(task, 3);

        assertTrue(output().contains("read book"));
        assertTrue(output().contains("Now you have 3 tasks in the list."));
    }

    @Test
    public void showTaskMarked_printsTask() {
        Task task = new Todo("read book");
        task.markAsDone();

        new Ui().showTaskMarked(task);

        assertTrue(output().contains("[X]read book"));
    }

    @Test
    public void showTaskUnmarked_printsTask() {
        Task task = new Todo("read book");

        new Ui().showTaskUnmarked(task);

        assertTrue(output().contains("[ ]read book"));
    }

    @Test
    public void showTaskDeleted_printsTaskAndRemainingCount() {
        Task task = new Todo("read book");

        new Ui().showTaskDeleted(task, 1);

        assertTrue(output().contains("read book"));
        assertTrue(output().contains("Now you have 1 tasks in the list."));
    }

    @Test
    public void showTaskList_emptyList_printsHeaderOnlyAndNoItems() {
        new Ui().showTaskList(new Task[0], 0);

        assertTrue(output().contains("Here are the tasks in your list:"));
        assertFalse(output().contains("1."));
    }

    @Test
    public void showTaskList_multipleTasks_printsEachOneNumbered() {
        Task[] tasks = new Task[]{new Todo("read book"), new Todo("write code")};

        new Ui().showTaskList(tasks, 2);

        assertTrue(output().contains("1.[T][ ]read book"));
        assertTrue(output().contains("2.[T][ ]write code"));
    }

    @Test
    public void showMatchingTasks_emptyMatches_printsHeaderOnlyAndNoItems() {
        new Ui().showMatchingTasks(new Task[0]);

        assertTrue(output().contains("Here are the matching tasks in your list:"));
        assertFalse(output().contains("1."));
    }

    @Test
    public void showMatchingTasks_someMatches_printsEachOneNumbered() {
        Task[] matches = new Task[]{new Todo("read book"), new Todo("return book")};

        new Ui().showMatchingTasks(matches);

        assertTrue(output().contains("1.[T][ ]read book"));
        assertTrue(output().contains("2.[T][ ]return book"));
    }
}
