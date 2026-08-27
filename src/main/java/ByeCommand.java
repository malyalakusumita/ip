/**
 * Represents the "bye" command, which says goodbye and ends the program.
 */
public class ByeCommand extends Command {

    @Override
    public void execute(TaskList taskList, Ui ui) {
        ui.showGoodbye();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}