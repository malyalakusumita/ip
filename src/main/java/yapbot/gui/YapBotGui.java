package yapbot.gui;

import yapbot.command.Command;
import yapbot.exception.YapBotException;
import yapbot.parser.Parser;
import yapbot.storage.Storage;
import yapbot.task.Task;
import yapbot.task.TaskList;
import yapbot.ui.GuiUi;

/**
 * Drives YapBot for a GUI front end: parses and executes commands the same
 * way {@code yapbot.YapBot}'s console loop does, but returns each command's
 * response as a String instead of printing it.
 */
public class YapBotGui {

    private final TaskList taskList;
    private final GuiUi ui;
    private boolean isExit;

    /**
     * Creates a GUI session, loading any previously saved tasks from disk.
     */
    public YapBotGui() {
        Task[] loaded = new Task[TaskList.MAX_CAPACITY];
        int loadedCount = Storage.load(loaded);
        this.taskList = new TaskList(loaded, loadedCount);
        this.ui = new GuiUi();
        this.isExit = false;
    }

    /**
     * Parses and executes one line of user input.
     *
     * @param input the full command text as typed by the user.
     * @return the response text to display, e.g. a confirmation or an
     *         error message.
     */
    public String getResponse(String input) {
        try {
            Command command = Parser.parse(input);
            command.execute(taskList, ui);
            isExit = command.isExit();
        } catch (YapBotException e) {
            ui.showMessage(e.getMessage());
        } catch (Exception e) {
            // Safety net for unanticipated bad input, matching the console loop.
            ui.showMessage("Oops, something went wrong: " + e.getMessage());
        }
        return ui.consumeResponse();
    }

    /**
     * Returns whether the most recently executed command should end the
     * session (i.e. the last call to {@link #getResponse} was "bye").
     *
     * @return {@code true} if the GUI should close.
     */
    public boolean isExit() {
        return isExit;
    }
}
