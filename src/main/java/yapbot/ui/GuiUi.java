package yapbot.ui;

/**
 * A {@link Ui} that captures its output instead of printing to standard
 * output, so a GUI front end can display each command's response text
 * without duplicating any of {@link Ui}'s message wording.
 */
public class GuiUi extends Ui {

    private final StringBuilder buffer = new StringBuilder();

    @Override
    protected void output(String line) {
        buffer.append(line).append(System.lineSeparator());
    }

    /**
     * Returns everything captured since the last call to this method, then
     * clears the buffer so the next command starts with a clean slate.
     *
     * @return the captured output, with the trailing line separator removed,
     *         or an empty string if nothing was captured.
     */
    public String consumeResponse() {
        String response = buffer.toString().stripTrailing();
        buffer.setLength(0);
        return response;
    }
}
