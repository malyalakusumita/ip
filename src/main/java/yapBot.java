/**
 * Entry point for yapBot, a command-line task-tracking chatbot.
 */
public class yapBot {

    /**
     * Runs the yapBot command loop, reading commands from standard input
     * until the user issues the "bye" command.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        Task[] loaded = new Task[100];
        int loadedCount = Storage.load(loaded);
        TaskList taskList = new TaskList(loaded, loadedCount);

        boolean isExit = false;
        while (!isExit) {
            String fullCommand = ui.readCommand();
            try {
                Command command = Parser.parse(fullCommand);
                command.execute(taskList, ui);
                isExit = command.isExit();
            } catch (yapBotException e) {
                ui.showMessage(e.getMessage());
            } catch (Exception e) {
                // Safety net for unanticipated bad input
                ui.showMessage("Oops, something went wrong: " + e.getMessage());
            }
        }
    }
}