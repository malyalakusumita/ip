import java.time.LocalDate;
import java.time.format.DateTimeParseException;

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

        Task[] tasks = new Task[100];
        int taskCount = Storage.load(tasks);

        while (true) {
            String command = ui.readCommand();

            try {

                if (command.equals("bye")) {
                    ui.showGoodbye();
                    break;
                } else if (command.equals("list")) {
                    ui.showTaskList(tasks, taskCount);
                } else if (command.startsWith("mark")) {
                    int taskIndex = parseTaskIndex(command, "mark", taskCount);
                    tasks[taskIndex].markAsDone();
                    Storage.save(tasks, taskCount);
                    ui.showTaskMarked(tasks[taskIndex]);
                } else if (command.startsWith("unmark")) {
                    int taskIndex = parseTaskIndex(command, "unmark", taskCount);
                    tasks[taskIndex].markAsNotDone();
                    Storage.save(tasks, taskCount);
                    ui.showTaskUnmarked(tasks[taskIndex]);
                } else if (command.startsWith("delete")) {
                    int taskIndex = parseTaskIndex(command, "delete", taskCount);
                    Task removedTask = tasks[taskIndex];
                    for (int i = taskIndex; i < taskCount - 1; i++) {
                        tasks[i] = tasks[i + 1];
                    }
                    tasks[taskCount - 1] = null;
                    taskCount--;
                    Storage.save(tasks, taskCount);
                    ui.showTaskDeleted(removedTask, taskCount);
                } else if (command.startsWith("todo")) {
                    checkSpaceIsFull(taskCount);
                    String description = command.substring(4).trim();
                    if (description.isEmpty()) {
                        throw new yapBotException("The description of a todo cannot be empty. "
                                + "Usage: todo <description>");
                    }
                    Task task = new Todo(checkNoDelimiter(description, "description of a todo"));
                    tasks[taskCount] = task;
                    taskCount++;
                    Storage.save(tasks, taskCount);
                    ui.showTaskAdded(task, taskCount);
                } else if (command.startsWith("deadline")) {
                    checkSpaceIsFull(taskCount);
                    String details = command.length() > 8 ? command.substring(8).trim() : "";
                    if (details.isEmpty()) {
                        throw new yapBotException("The description of a deadline cannot be empty. "
                                + "Usage: deadline <description> /by <date>");
                    }
                    String[] parts = details.split(" /by ", 2);
                    if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
                        throw new yapBotException("A deadline needs both a description and a '/by' date. "
                                + "Usage: deadline <description> /by <date>");
                    }
                    String deadlineDescription = checkNoDelimiter(
                            parts[0].trim(), "description of a deadline");
                    String byInput = checkNoDelimiter(parts[1].trim(), "'/by' date of a deadline");
                    LocalDate by = parseDeadlineDate(byInput);
                    Task task = new Deadline(deadlineDescription, by);
                    tasks[taskCount] = task;
                    taskCount++;
                    Storage.save(tasks, taskCount);
                    ui.showTaskAdded(task, taskCount);
                } else if (command.startsWith("event")) {
                    checkSpaceIsFull(taskCount);
                    String details = command.length() > 5 ? command.substring(5).trim() : "";
                    if (details.isEmpty()) {
                        throw new yapBotException("The description of an event cannot be empty. "
                                + "Usage: event <description> /from <start> /to <end>");
                    }
                    String[] parts = details.split(" /from | /to ");
                    if (parts.length < 3 || parts[0].trim().isEmpty()
                            || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
                        throw new yapBotException(
                                "An event needs a description, a '/from' time and a '/to' time. "
                                        + "Usage: event <description> /from <start> /to <end>");

                    }
                    String eventDescription = checkNoDelimiter(
                            parts[0].trim(), "description of an event");
                    String from = checkNoDelimiter(parts[1].trim(), "'/from' time of an event");
                    String to = checkNoDelimiter(parts[2].trim(), "'/to' time of an event");
                    Task task = new Event(eventDescription, from, to);
                    tasks[taskCount] = task;
                    taskCount++;
                    Storage.save(tasks, taskCount);
                    ui.showTaskAdded(task, taskCount);
                } else if (command.isBlank()) {
                    throw new yapBotException("You didn't type anything. Try 'todo', 'deadline', "
                            + "'event', 'list', 'mark', 'unmark', 'delete' or 'bye'.");
                } else {
                    throw new yapBotException(
                            "I'm sorry, but I don't know what that means. "
                                    + "Try 'todo', 'deadline', 'event', 'list', 'mark', "
                                    + "'unmark', 'delete' or 'bye'.");
                }
            } catch (yapBotException e) {
                ui.showMessage(e.getMessage());
            } catch (Exception e) {
                // Safety net for unanticipated bad input
                ui.showMessage("Oops, something went wrong: " + e.getMessage());
            }
        }
    }

    // Parses the task number out of a "mark"/"unmark" command and validates it against the current task list.
    private static int parseTaskIndex(String command, String keyword, int taskCount) throws yapBotException {
        String argument = command.length() > keyword.length()
                ? command.substring(keyword.length()).trim()
                : "";
        if (argument.isEmpty()) {
            throw new yapBotException("Please specify a task number, e.g. '" + keyword + " 2'.");
        }

        int taskIndex;
        try {
            taskIndex = Integer.parseInt(argument) - 1;
        } catch (NumberFormatException e) {
            throw new yapBotException("'" + argument + "' is not a valid task number.");
        }

        if (taskCount == 0) {
            throw new yapBotException("Your task list is empty, so there's nothing to " + keyword + ".");
        }
        if (taskIndex < 0 || taskIndex >= taskCount) {
            throw new yapBotException("Task number " + (taskIndex + 1) + " doesn't exist. "
                    + "You have " + taskCount + " task(s).");
        }
        return taskIndex;
    }

    //checks if space before adding into task list
    private static void checkSpaceIsFull(int taskCount) throws yapBotException {
        if (taskCount >= 100) {
            throw new yapBotException("Sorry, your task list is full (max 100 tasks).");
        }
    }

    /**
     * Rejects a task field that contains the '|' character, since it is the
     * delimiter used by the save file format and would corrupt it if saved.
     *
     * @param value     the field value to check.
     * @param fieldName a human-readable name for the field, used in the error message.
     * @return {@code value} unchanged, if it passed the check.
     * @throws yapBotException if {@code value} contains '|'.
     */
    private static String checkNoDelimiter(String value, String fieldName) throws yapBotException {
        if (value.contains("|")) {
            throw new yapBotException("The " + fieldName + " cannot contain the '|' character, "
                    + "as it is reserved for the save file format.");
        }
        return value;
    }

    /**
     * Parses a deadline's '/by' input as a date in {@code yyyy-mm-dd} format.
     *
     * @param value the raw '/by' text entered by the user.
     * @return the parsed date.
     * @throws yapBotException if {@code value} is not a valid {@code yyyy-mm-dd} date.
     */
    private static LocalDate parseDeadlineDate(String value) throws yapBotException {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException e) {
            throw new yapBotException("Please enter the deadline date in yyyy-mm-dd format, "
                    + "e.g. 2019-10-15.");
        }
    }
}