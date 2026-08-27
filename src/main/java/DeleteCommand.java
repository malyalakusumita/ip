/**
 * Represents the "delete" command, which removes a task from the list.
 */
public class DeleteCommand extends Command {

    private final String fullCommand;

    /**
     * Creates a delete command from the full command text.
     *
     * @param fullCommand the full command text as typed by the user, e.g. "delete 2".
     */
    public DeleteCommand(String fullCommand) {
        this.fullCommand = fullCommand;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws yapBotException {
        int taskIndex = taskList.validateIndex(fullCommand, "delete");
        Task removedTask = taskList.delete(taskIndex);
        Storage.save(taskList.toArray(), taskList.size());
        ui.showTaskDeleted(removedTask, taskList.size());
    }
}