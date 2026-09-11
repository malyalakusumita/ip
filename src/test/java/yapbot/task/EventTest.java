package yapbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class EventTest {

    @Test
    public void toFileFormat_returnsFromAndToAppended() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");

        assertEquals("E | 0 | project meeting | Mon 2pm | 4pm", event.toFileFormat());
    }

    @Test
    public void toFileFormat_doneTask_statusFieldReflectsDone() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        event.markAsDone();

        assertEquals("E | 1 | project meeting | Mon 2pm | 4pm", event.toFileFormat());
    }

    @Test
    public void toString_notDone_showsFromAndTo() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");

        assertEquals("[E][ ]project meeting (from: Mon 2pm to: 4pm)", event.toString());
    }

    @Test
    public void toString_done_showsXMarkFromAndTo() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        event.markAsDone();

        assertEquals("[E][X]project meeting (from: Mon 2pm to: 4pm)", event.toString());
    }

    @Test
    public void toFileFormat_prioritySet_appendsPriorityAfterFromAndTo() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        event.setPriority(Priority.MEDIUM);

        assertEquals("E | 0 | project meeting | Mon 2pm | 4pm | MEDIUM", event.toFileFormat());
    }

    @Test
    public void toString_prioritySet_showsPriorityTagBeforeDescription() {
        Event event = new Event("project meeting", "Mon 2pm", "4pm");
        event.setPriority(Priority.MEDIUM);

        assertEquals("[E][ ][MEDIUM]project meeting (from: Mon 2pm to: 4pm)", event.toString());
    }
}
