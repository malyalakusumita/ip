package yapbot.ui;

import java.io.InputStream;
import java.util.Scanner;

import yapbot.task.Deadline;
import yapbot.task.Event;
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
        System.out.println("Hey there! I'm YapBot, your hype squad for getting things done.");
        System.out.println("What are we tackling today?");
    }

    /**
     * Prints the goodbye message shown when the user exits.
     */
    public void showGoodbye() {
        output("Nice work today! Catch you next time - bye for now!");
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
     * Prints the confirmation shown after a task is added. The announcement
     * line's wording depends on the task's type (e.g. a deadline gets a
     * "remember to..." reminder rather than a generic "added" line), to keep
     * YapBot's encouraging-accountability-buddy voice specific to what was
     * actually added.
     *
     * @param task  the task that was added.
     * @param count the total number of tasks now in the list.
     */
    public void showTaskAdded(Task task, int count) {
        output(describeTaskAdded(task));
        output("  " + task);
        output("Now you have " + count + " tasks in the list.");
    }

    /**
     * Builds the type-specific announcement line for {@link #showTaskAdded}.
     *
     * @param task the task that was added.
     * @return the announcement line, without the trailing task detail/count lines.
     */
    private String describeTaskAdded(Task task) {
        if (task instanceof Deadline deadline) {
            return "Remember to do " + deadline.getDescription() + " by " + deadline.getFormattedBy();
        }
        if (task instanceof Event event) {
            return "Noted! I've pencilled in " + event.getDescription() + " on your schedule";
        }
        return "Great, I've added " + task.getDescription() + " to the list";
    }

    /**
     * Prints the confirmation shown after a task is marked as done.
     *
     * @param task the task that was marked.
     */
    public void showTaskMarked(Task task) {
        output("Woo, nice work! Marked as done:");
        output("  " + task);
    }

    /**
     * Prints the confirmation shown after a task is marked as not done.
     *
     * @param task the task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        output("No worries, I've marked this as not done yet:");
        output("  " + task);
    }

    /**
     * Prints the confirmation shown after a task's priority is changed.
     *
     * @param task the task whose priority was changed.
     */
    public void showPriorityChanged(Task task) {
        output("Got it, priority updated:");
        output("  " + task);
    }

    /**
     * Prints the confirmation shown after a task is deleted.
     *
     * @param task  the task that was removed.
     * @param count the total number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int count) {
        output("I've removed " + task.getDescription() + " from the list");
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
        output("Here's everything on your list:");
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
        output("Here's what I found for you:");
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