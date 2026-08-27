package yapbot.task;

import yapbot.exception.yapBotException;
import yapbot.storage.Storage;

/**
 * Holds the list of tasks and the operations that mutate it: adding,
 * deleting, marking/unmarking as done, and validating a user-supplied
 * task number against the current list.
 */
public class TaskList {

    private static final int MAX_CAPACITY = 100;

    private final Task[] tasks;
    private int size;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new Task[MAX_CAPACITY];
        this.size = 0;
    }

    /**
     * Creates a task list pre-populated from an already-loaded array, e.g.
     * the array returned by {@link Storage#load(Task[])}.
     *
     * @param loadedTasks the source array to copy from.
     * @param loadedCount the number of valid tasks at the start of {@code loadedTasks}.
     */
    public TaskList(Task[] loadedTasks, int loadedCount) {
        this.tasks = new Task[MAX_CAPACITY];
        System.arraycopy(loadedTasks, 0, this.tasks, 0, loadedCount);
        this.size = loadedCount;
    }

    /**
     * Returns the number of tasks currently in the list.
     *
     * @return the task count.
     */
    public int size() {
        return size;
    }

    /**
     * Returns whether the list is at its maximum capacity.
     *
     * @return {@code true} if no more tasks can be added.
     */
    public boolean isFull() {
        return size >= MAX_CAPACITY;
    }

    /**
     * Returns the task at the given index.
     *
     * @param index a valid, zero-based index (see {@link #validateIndex}).
     * @return the task at that index.
     */
    public Task get(int index) {
        return tasks[index];
    }

    /**
     * Returns the backing array, sized {@value #MAX_CAPACITY}. Only the
     * first {@link #size()} entries are valid; the rest are {@code null}.
     * Exists to interoperate with {@link Storage}, which operates on a
     * plain array/count pair.
     *
     * @return the backing task array.
     */
    public Task[] toArray() {
        return tasks;
    }

    /**
     * Adds a task to the end of the list.
     *
     * @param task the task to add.
     * @throws yapBotException if the list is already at capacity.
     */
    public void add(Task task) throws yapBotException {
        if (isFull()) {
            throw new yapBotException("Sorry, your task list is full (max " + MAX_CAPACITY + " tasks).");
        }
        tasks[size] = task;
        size++;
    }

    /**
     * Removes the task at the given index, shifting later tasks up by one.
     *
     * @param index a valid, zero-based index (see {@link #validateIndex}).
     * @return the task that was removed.
     */
    public Task delete(int index) {
        Task removed = tasks[index];
        for (int i = index; i < size - 1; i++) {
            tasks[i] = tasks[i + 1];
        }
        tasks[size - 1] = null;
        size--;
        return removed;
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param index a valid, zero-based index (see {@link #validateIndex}).
     */
    public void markAsDone(int index) {
        tasks[index].markAsDone();
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param index a valid, zero-based index (see {@link #validateIndex}).
     */
    public void markAsNotDone(int index) {
        tasks[index].markAsNotDone();
    }

    /**
     * Parses and validates the task number out of a "mark"/"unmark"/"delete"
     * command against the current list, e.g. {@code "mark 2"} with keyword
     * {@code "mark"} yields the zero-based index {@code 1}.
     *
     * @param command the full command text as typed by the user.
     * @param keyword the command keyword to strip off the front (e.g. "mark").
     * @return the validated, zero-based task index.
     * @throws yapBotException if no number was given, it isn't a valid
     *         integer, the list is empty, or the number is out of range.
     */
    public int validateIndex(String command, String keyword) throws yapBotException {
        String argument = command.length() > keyword.length()
                ? command.substring(keyword.length()).trim()
                : "";
        if (argument.isEmpty()) {
            throw new yapBotException("Please specify a task number, e.g. '" + keyword + " 2'.");
        }

        int index;
        try {
            index = Integer.parseInt(argument) - 1;
        } catch (NumberFormatException e) {
            throw new yapBotException("'" + argument + "' is not a valid task number.");
        }

        if (size == 0) {
            throw new yapBotException("Your task list is empty, so there's nothing to " + keyword + ".");
        }
        if (index < 0 || index >= size) {
            throw new yapBotException("Task number " + (index + 1) + " doesn't exist. "
                    + "You have " + size + " task(s).");
        }
        return index;
    }
}