package yapbot.task;

/**
 * Represents a todo task, i.e. a task with no date/time attached.
 */
public class Todo extends Task {

    /**
     * Creates a todo task with the given description.
     *
     * @param description the task description.
     */
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}