package yapbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class TaskTest {

    @Test
    public void constructor_newTask_isNotDoneAndHasGivenDescription() {
        Task task = new Task("read book");

        assertEquals("read book", task.getDescription());
        assertEquals(" ", task.getStatusIcon());
        assertEquals("0", task.getStatusValue());
    }

    @Test
    public void getPriority_newTask_returnsNull() {
        Task task = new Task("read book");

        assertNull(task.getPriority());
    }

    @Test
    public void setPriority_validLevel_getPriorityReturnsIt() {
        Task task = new Task("read book");

        task.setPriority(Priority.HIGH);

        assertEquals(Priority.HIGH, task.getPriority());
    }

    @Test
    public void markAsDone_notDoneTask_statusBecomesDone() {
        Task task = new Task("read book");

        task.markAsDone();

        assertEquals("X", task.getStatusIcon());
        assertEquals("1", task.getStatusValue());
    }

    @Test
    public void markAsNotDone_doneTask_statusBecomesNotDone() {
        Task task = new Task("read book");
        task.markAsDone();

        task.markAsNotDone();

        assertEquals(" ", task.getStatusIcon());
        assertEquals("0", task.getStatusValue());
    }

    @Test
    public void toFileFormat_notDoneTask_returnsTypeStatusAndDescription() {
        Task task = new Task("read book");

        assertEquals("T | 0 | read book", task.toFileFormat());
    }

    @Test
    public void toFileFormat_doneTask_statusFieldReflectsDone() {
        Task task = new Task("read book");
        task.markAsDone();

        assertEquals("T | 1 | read book", task.toFileFormat());
    }

    @Test
    public void appendPriorityField_priorityNotSet_returnsInputUnchanged() {
        Task task = new Task("read book");

        assertEquals("base line", task.appendPriorityField("base line"));
    }

    @Test
    public void appendPriorityField_prioritySet_appendsPriorityAsFinalField() {
        Task task = new Task("read book");
        task.setPriority(Priority.LOW);

        assertEquals("base line | LOW", task.appendPriorityField("base line"));
    }

    @Test
    public void toString_notDoneTask_showsBlankStatusBox() {
        Task task = new Task("read book");

        assertEquals("[ ]read book", task.toString());
    }

    @Test
    public void toString_doneTask_showsXInStatusBox() {
        Task task = new Task("read book");
        task.markAsDone();

        assertEquals("[X]read book", task.toString());
    }

    @Test
    public void toString_priorityIsSet_showsPriorityTagAfterStatusBox() {
        Task task = new Task("read book");
        task.setPriority(Priority.HIGH);

        assertEquals("[ ][HIGH]read book", task.toString());
    }
}
