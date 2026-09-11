package yapbot.task;

/**
 * Represents a task and whether it has been completed.
 */
public class Task {
    private final String description;
    private boolean isDone;

    /**
     * Creates an incomplete task with the given description.
     *
     * @param description the task description
     */
    public Task(String description) {
        assert description != null && !description.isBlank()
                : "Parser/Storage must validate the description before constructing a task";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the task description.
     *
     * @return the task description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns the icon used to show whether this task is complete.
     *
     * @return {@code "X"} when complete; otherwise, a space
     */
    public String getStatusIcon() {
        return isDone ? "X" : " ";
    }

    /**
     * Returns the status as it should appear in the save file ("1" for done, "0" otherwise).
     *
     * @return {@code "1"} when complete; otherwise, {@code "0"}
     */
    public String getStatusValue() {
        return isDone ? "1" : "0";
    }

    /**
     * Marks this task as complete.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks this task as incomplete.
     */
    public void markAsNotDone() {
        isDone = false;
    }

    /**
     * Returns the single-letter icon identifying this task's type in the save file.
     * Subclasses override this to identify themselves (e.g. "D" for Deadline).
     *
     * @return the type icon, defaults to "T"
     */
    protected String getTypeIcon() {
        return "T";
    }

    /**
     * Returns this task serialized as a single line for the save file, in the format:
     * {@code type | status | description}. Subclasses append any extra fields they have.
     *
     * @return the file-format representation of this task
     */
    public String toFileFormat() {
        return getTypeIcon() + " | " + getStatusValue() + " | " + description;
    }

    /**
     * Returns this task as it should be displayed to the user, e.g. {@code "[X]read book"}.
     *
     * @return the display representation of this task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "]" + description;
    }
}
