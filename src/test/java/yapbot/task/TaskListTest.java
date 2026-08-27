package yapbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import yapbot.exception.yapBotException;

public class TaskListTest {

    @Test
    public void validateIndex_validIndexWithinRange_returnsZeroBasedIndex() throws yapBotException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("write code"));

        int index = taskList.validateIndex("mark 2", "mark");

        assertEquals(1, index);
    }

    @Test
    public void validateIndex_firstTask_returnsIndexZero() throws yapBotException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));

        int index = taskList.validateIndex("delete 1", "delete");

        assertEquals(0, index);
    }

    @Test
    public void validateIndex_extraWhitespaceAroundNumber_trimmedAndParsed() throws yapBotException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));
        taskList.add(new Todo("write code"));

        int index = taskList.validateIndex("mark   2   ", "mark");

        assertEquals(1, index);
    }

    @Test
    public void validateIndex_noArgumentGiven_exceptionThrown() {
        TaskList taskList = new TaskList();

        assertThrows(yapBotException.class, () -> taskList.validateIndex("mark", "mark"));
    }

    @Test
    public void validateIndex_blankArgumentGiven_exceptionThrown() {
        TaskList taskList = new TaskList();

        assertThrows(yapBotException.class, () -> taskList.validateIndex("mark   ", "mark"));
    }

    @Test
    public void validateIndex_nonNumericArgument_exceptionThrown() {
        TaskList taskList = new TaskList();

        assertThrows(yapBotException.class, () -> taskList.validateIndex("mark two", "mark"));
    }

    @Test
    public void validateIndex_emptyTaskList_exceptionThrown() {
        TaskList taskList = new TaskList();

        assertThrows(yapBotException.class, () -> taskList.validateIndex("mark 1", "mark"));
    }

    @Test
    public void validateIndex_indexZero_exceptionThrown() throws yapBotException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));

        assertThrows(yapBotException.class, () -> taskList.validateIndex("mark 0", "mark"));
    }

    @Test
    public void validateIndex_negativeIndex_exceptionThrown() throws yapBotException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));

        assertThrows(yapBotException.class, () -> taskList.validateIndex("mark -1", "mark"));
    }

    @Test
    public void validateIndex_indexBeyondListSize_exceptionThrown() throws yapBotException {
        TaskList taskList = new TaskList();
        taskList.add(new Todo("read book"));

        assertThrows(yapBotException.class, () -> taskList.validateIndex("mark 2", "mark"));
    }
}
