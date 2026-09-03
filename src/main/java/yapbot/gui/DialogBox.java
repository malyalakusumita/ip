package yapbot.gui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/**
 * A single row in the GUI's conversation view: a label holding either the
 * user's input or YapBot's response, aligned to one side of the window
 * depending on who "said" it.
 */
public class DialogBox extends HBox {

    private static final double WRAP_WIDTH = 300.0;

    private DialogBox(String text, Pos alignment) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(WRAP_WIDTH);

        setAlignment(alignment);
        getChildren().add(label);
    }

    /**
     * Creates a dialog row for text the user typed, right-aligned.
     *
     * @param text the user's input.
     * @return the dialog row to add to the conversation view.
     */
    public static DialogBox forUser(String text) {
        return new DialogBox(text, Pos.CENTER_RIGHT);
    }

    /**
     * Creates a dialog row for YapBot's response, left-aligned.
     *
     * @param text YapBot's response text.
     * @return the dialog row to add to the conversation view.
     */
    public static DialogBox forYapBot(String text) {
        return new DialogBox(text, Pos.CENTER_LEFT);
    }
}
