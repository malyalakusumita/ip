package yapbot.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import yapbot.command.AddCommand;
import yapbot.command.ByeCommand;
import yapbot.command.Command;
import yapbot.command.DeleteCommand;
import yapbot.command.FindCommand;
import yapbot.command.ListCommand;
import yapbot.command.MarkCommand;
import yapbot.command.UnmarkCommand;
import yapbot.exception.yapBotException;
import yapbot.task.Deadline;
import yapbot.task.Event;
import yapbot.task.Task;
import yapbot.task.Todo;

public class ParserTest {

    @Test
    public void parse_bye_returnsExitingByeCommand() throws yapBotException {
        Command command = Parser.parse("bye");

        assertInstanceOf(ByeCommand.class, command);
        assertTrue(command.isExit());
    }

    @Test
    public void parse_list_returnsNonExitingListCommand() throws yapBotException {
        Command command = Parser.parse("list");

        assertInstanceOf(ListCommand.class, command);
        assertFalse(command.isExit());
    }

    @Test
    public void parse_mark_returnsMarkCommand() throws yapBotException {
        Command command = Parser.parse("mark 2");

        assertInstanceOf(MarkCommand.class, command);
    }

    @Test
    public void parse_unmark_returnsUnmarkCommand() throws yapBotException {
        Command command = Parser.parse("unmark 2");

        assertInstanceOf(UnmarkCommand.class, command);
    }

    @Test
    public void parse_delete_returnsDeleteCommand() throws yapBotException {
        Command command = Parser.parse("delete 2");

        assertInstanceOf(DeleteCommand.class, command);
    }

    @Test
    public void parse_find_returnsFindCommand() throws yapBotException {
        Command command = Parser.parse("find book");

        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    public void parse_findWithNoKeyword_returnsFindCommandWithoutThrowing() throws yapBotException {
        // Like mark/unmark/delete, the keyword is validated in FindCommand.execute(),
        // not at parse time, so this should return normally rather than throw.
        Command command = Parser.parse("find");

        assertInstanceOf(FindCommand.class, command);
    }

    @Test
    public void parse_todo_returnsAddCommandWithMatchingTodoTask() throws yapBotException {
        Command command = Parser.parse("todo read book");

        AddCommand addCommand = assertInstanceOf(AddCommand.class, command);
        Task task = addCommand.getTask();
        assertInstanceOf(Todo.class, task);
        assertEquals("read book", task.getDescription());
        assertEquals("T | 0 | read book", task.toFileFormat());
    }

    @Test
    public void parse_todoWithEmptyDescription_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse("todo"));
        assertThrows(yapBotException.class, () -> Parser.parse("todo   "));
    }

    @Test
    public void parse_todoWithPipeCharacter_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse("todo read | book"));
    }

    @Test
    public void parse_deadline_returnsAddCommandWithMatchingDeadlineTask() throws yapBotException {
        Command command = Parser.parse("deadline return book /by 2019-10-15");

        AddCommand addCommand = assertInstanceOf(AddCommand.class, command);
        Task task = addCommand.getTask();
        assertInstanceOf(Deadline.class, task);
        assertEquals("return book", task.getDescription());
        assertEquals("D | 0 | return book | 2019-10-15", task.toFileFormat());
    }

    @Test
    public void parse_deadlineMissingByClause_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse("deadline return book"));
    }

    @Test
    public void parse_deadlineWithInvalidDate_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse("deadline return book /by tomorrow"));
    }

    @Test
    public void parse_deadlineWithBlankDescriptionOrDate_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse("deadline /by 2019-10-15"));
        assertThrows(yapBotException.class, () -> Parser.parse("deadline return book /by "));
    }

    @Test
    public void parse_event_returnsAddCommandWithMatchingEventTask() throws yapBotException {
        Command command = Parser.parse("event project meeting /from Mon 2pm /to 4pm");

        AddCommand addCommand = assertInstanceOf(AddCommand.class, command);
        Task task = addCommand.getTask();
        assertInstanceOf(Event.class, task);
        assertEquals("project meeting", task.getDescription());
        assertEquals("E | 0 | project meeting | Mon 2pm | 4pm", task.toFileFormat());
    }

    @Test
    public void parse_eventMissingToClause_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse("event project meeting /from Mon 2pm"));
    }

    @Test
    public void parse_eventWithBlankField_exceptionThrown() {
        assertThrows(yapBotException.class,
                () -> Parser.parse("event project meeting /from  /to 4pm"));
    }

    @Test
    public void parse_blankInput_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse(""));
        assertThrows(yapBotException.class, () -> Parser.parse("   "));
    }

    @Test
    public void parse_unrecognizedCommand_exceptionThrown() {
        assertThrows(yapBotException.class, () -> Parser.parse("frobnicate"));
    }
}
