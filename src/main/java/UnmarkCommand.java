/**
 * Represents the "unmark" command, which marks a task as not done.
 */
public class UnmarkCommand extends Command {

    private final String fullCommand;

    /**
     * Creates an unmark command from the full command text.
     *
     * @param fullCommand the full command text as typed by the user, e.g. "unmark 2".
     */
    public UnmarkCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws yapBotException {
        int taskIndex = taskList.validateIndex(fullCommand, "unmark");
        taskList.markAsNotDone(taskIndex);
        Storage.save(taskList.toArray(), taskList.size());
        ui.showTaskUnmarked(taskList.get(taskIndex));
    }
}