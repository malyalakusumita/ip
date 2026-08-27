package yapbot.command;

import yapbot.exception.YapBotException;
import yapbot.storage.Storage;
import yapbot.task.TaskList;
import yapbot.ui.Ui;

/**
 * Represents the "mark" command, which marks a task as done.
 */
public class MarkCommand extends Command {

    private final String fullCommand;

    /**
     * Creates a mark command from the full command text.
     *
     * @param fullCommand the full command text as typed by the user, e.g. "mark 2".
     */
    public MarkCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws YapBotException {
        int taskIndex = taskList.validateIndex(fullCommand, "mark");
        taskList.markAsDone(taskIndex);
        Storage.save(taskList.toArray(), taskList.size());
        ui.showTaskMarked(taskList.get(taskIndex));
    }
}