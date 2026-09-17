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

    private DialogBox(String text, Pos alignment, String bubbleStyleClass) {
        Label label = new Label(text);
        label.setWrapText(true);
        label.setMaxWidth(WRAP_WIDTH);
        label.getStyleClass().addAll("chat-bubble", bubbleStyleClass);

        setAlignment(alignment);
        getChildren().add(label);
    }

    /**
     * Creates a dialog row for text the user typed, right-aligned and styled
     * as a "user" bubble (maroon background).
     *
     * @param text the user's input.
     * @return the dialog row to add to the conversation view.
     */
    public static DialogBox forUser(String text) {
        return new DialogBox(text, Pos.CENTER_RIGHT, "user-bubble");
    }

    /**
     * Creates a dialog row for YapBot's response, left-aligned and styled as
     * a "bot" bubble (cream background).
     *
     * @param text YapBot's response text.
     * @return the dialog row to add to the conversation view.
     */
    public static DialogBox forYapBot(String text) {
        return new DialogBox(text, Pos.CENTER_LEFT, "bot-bubble");
    }
}
