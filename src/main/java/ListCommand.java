/**
 * Represents the "list" command, which shows every task in the list.
 */
public class ListCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui) {
        ui.showTaskList(taskList.toArray(), taskList.size());
    }
}