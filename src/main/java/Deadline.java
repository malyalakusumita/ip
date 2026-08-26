/**
 * Represents a task that has a deadline by which it must be completed.
 */
public class Deadline extends Task {

    protected String by;

    /**
     * Creates a deadline task with the given description and due date/time.
     *
     * @param description the task description.
     * @param by the date/time by which the task should be completed.
     */
    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns the type icon used to identify a deadline task in the save file.
     *
     * @return "D".
     */
    @Override
    protected String getTypeIcon() {
        return "D";
    }

    /**
     * Returns this deadline task serialized for the save file, including the due date/time.
     *
     * @return the file-format representation of this task.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}