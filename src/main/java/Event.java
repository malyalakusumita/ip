/**
 * Represents an event task with a start and end time.
 */
public class Event extends Task {

    protected String from;
    protected String to;

    /**
     * Creates an event task with the given description, start time and end time.
     *
     * @param description the task description.
     * @param from the start time of the event.
     * @param to the end time of the event.
     */
    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns the type icon used to identify an event task in the save file.
     *
     * @return "E".
     */
    @Override
    protected String getTypeIcon() {
        return "E";
    }

    /**
     * Returns this event task serialized for the save file, including the start and end times.
     *
     * @return the file-format representation of this task.
     */
    @Override
    public String toFileFormat() {
        return super.toFileFormat() + " | " + from + " | " + to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}