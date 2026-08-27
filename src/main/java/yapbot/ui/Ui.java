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
        System.out.println("Bye. Hope to see you again soon!");
    }

    /**
     * Prints an arbitrary message, e.g. an error or validation message.
     *
     * @param message the message to print.
     */
    public void showMessage(String message) {
        System.out.println(message);
    }

    /**
     * Prints the confirmation shown after a task is added.
     *
     * @param task  the task that was added.
     * @param count the total number of tasks now in the list.
     */
    public void showTaskAdded(Task task, int count) {
        System.out.println("Got it. I've added this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Prints the confirmation shown after a task is marked as done.
     *
     * @param task the task that was marked.
     */
    public void showTaskMarked(Task task) {
        System.out.println("Nice! I've marked this task as done:");
        System.out.println("  " + task);
    }

    /**
     * Prints the confirmation shown after a task is marked as not done.
     *
     * @param task the task that was unmarked.
     */
    public void showTaskUnmarked(Task task) {
        System.out.println("I've marked this task as not done yet:");
        System.out.println("  " + task);
    }

    /**
     * Prints the confirmation shown after a task is deleted.
     *
     * @param task  the task that was removed.
     * @param count the total number of tasks remaining in the list.
     */
    public void showTaskDeleted(Task task, int count) {
        System.out.println("I have removed this task:");
        System.out.println("  " + task);
        System.out.println("Now you have " + count + " tasks in the list.");
    }

    /**
     * Prints the full task list.
     *
     * @param tasks     the task array.
     * @param taskCount the number of active tasks in the array.
     */
    public void showTaskList(Task[] tasks, int taskCount) {
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < taskCount; i++) {
            System.out.println((i + 1) + "." + tasks[i]);
        }
    }

    /**
     * Prints the tasks that matched a search keyword.
     *
     * @param matches the matching tasks, in list order.
     */
    public void showMatchingTasks(Task[] matches) {
        System.out.println("Here are the matching tasks in your list:");
        for (int i = 0; i < matches.length; i++) {
            System.out.println((i + 1) + "." + matches[i]);
        }
    }
}