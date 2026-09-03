package yapbot.ui;

import java.io.InputStream;
import java.util.Scanner;

import yapbot.task.Task;

/**
 * Handles all interaction with the user: reading input and printing output.
 * No parsing or task-list logic lives here — only presentation.
 */
public class Ui {

    private final Scanner scanner;

    /**
     * Creates a Ui that reads user input from standard input.
     */
    public Ui() {
        this(System.in);
    }

    /**
     * Creates a Ui that reads user input from the given stream. Exists so
     * tests can supply input without going through the real standard input.
     *
     * @param in the input stream to read commands from.
     */
    Ui(InputStream in) {
        this.scanner = new Scanner(in);
    }

    /**
     * Reads the next full line of user input.
     *
     * @return the line the user typed.
     */
    public String readCommand() {
        return scanner.nextLine();
    }

    /**
     * Prints the startup banner and greeting.
     */
    public void showWelcome() {
        String banner = """
                __   __  ___   ____  ____   ___ _____
                \\ \\ / / / _ \\ |  _ \\| __ ) / _ \\_   _|
                 \\ V / | |_| || |_) |  _ \\| |_| || |
                  |_|   \\___/ |____/|___/ \\___/ |_|
                """;
        System.out.print(banner);
        System.out.println("Hello! I'm YAPBOT.");
        System.out.println("What can I do for you?");
    }

    /**
     * Prints the goodbye message shown when the user exits.
     */
    public void showGoodbye() {
        output("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an arbitrary message, e.g. an error or validation message.
     *
     * @param message the message to print.
     */
    public void showMessage(String message) {
        output(message);
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task  the task that was added.
     * @param count the total number of tasks now in the list.
     */
    public void showTaskAdded(Task task, int count) {
        output("Got it. I've added this task:");
        output("  " + task);
        output("Now you have " + count + " tasks in the list.");
    }

    /**
     * Prints the confirmation shown after a task is marked as done.
     *
     * @param task the task that was marked.
     */
    public void showTaskMarked(Task task) {
        output("Nice! I've marked this task as done:");
        output("  " + task);
    }

    /**
     * Prints the confirmation shown after a task is marked as not done.
     *
     * @param task the task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        output("I've marked this task as not done yet:");
        output("  " + task);
    }

    /**
     * Prints the confirmation shown after a task is deleted.
     *
     * @param task  the task that was removed.
     * @param count the total number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int count) {
        output("I have removed this task:");
        output("  " + task);
        output("Now you have " + count + " tasks in the list.");
    }

    /**
     * Prints the full task list.
     *
     * @param tasks     the task array.
     * @param taskCount the number of active tasks in the array.
     */
    public void showTaskList(Task[] tasks, int taskCount) {
        output("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            output((i + 1) + "." + tasks[i]);
        }
    }

    /**
     * Prints the tasks that matched a search keyword.
     *
     * @param matches the matching tasks, in list order.
     */
    public void showMatchingTasks(Task[] matches) {
        output("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.length; i++) {
            output((i + 1) + "." + matches[i]);
        }
    }

    /**
     * Emits one line of user-facing output. The console {@link Ui} prints it
     * to standard output; {@link GuiUi} overrides this to capture lines
     * instead, so both front ends share the exact same message text without
     * duplicating it.
     *
     * @param line the line to emit.
     */
    protected void output(String line) {
        System.out.println(line);
    }
}