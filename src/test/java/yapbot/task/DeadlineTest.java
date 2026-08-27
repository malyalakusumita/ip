package yapbot.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class DeadlineTest {

    @Test
    public void toFileFormat_returnsIsoFormattedDateAppended() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 10, 15));

        assertEquals("D | 0 | return book | 2019-10-15", deadline.toFileFormat());
    }

    @Test
    public void toFileFormat_doneTask_statusFieldReflectsDone() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 10, 15));
        deadline.markAsDone();

        assertEquals("D | 1 | return book | 2019-10-15", deadline.toFileFormat());
    }

    @Test
    public void toString_notDone_showsHumanReadableDate() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 10, 15));

        assertEquals("[D][ ]return book (by: Oct 15 2019)", deadline.toString());
    }

    @Test
    public void toString_done_showsXMarkAndHumanReadableDate() {
        Deadline deadline = new Deadline("return book", LocalDate.of(2019, 10, 15));
        deadline.markAsDone();

        assertEquals("[D][X]return book (by: Oct 15 2019)", deadline.toString());
    }
}
