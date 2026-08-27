/**
 * Represents a command that adds an already-constructed task to the list.
 * Used for todo, deadline, and event alike, since by the time the Parser
 * builds this command the task itself is already fully formed.
 */
public class AddCommand extends Command {

    private final Task task;

    /**
     * Creates a command that adds the given task.
     *
     * @param task the task to add.
     */
    public AddCommand(Task task) {
        this.task = task;
    }

    @Override
    public void execute(TaskList taskList, Ui ui) throws yapBotException {
        taskList.add(task);
        Storage.save(taskList.toArray(), taskList.size());
        ui.showTaskAdded(task, taskList.size());
    }
}