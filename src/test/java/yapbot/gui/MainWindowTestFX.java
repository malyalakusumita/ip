package yapbot.gui;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import yapbot.storage.Storage;

/**
 * End-to-end GUI test, driven headlessly through TestFX/Monocle: it types
 * into the real text field and clicks the real send button, exercising the
 * same path a person testing the window by hand would.
 */
public class MainWindowTestFX extends ApplicationTest {

    private static final String FXML_PATH = "/view/MainWindow.fxml";

    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        Storage.setTargetPathForTesting(Files.createTempFile("yapBotTestFx", ".txt"));

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(FXML_PATH));
        AnchorPane root = fxmlLoader.load();
        MainWindow controller = fxmlLoader.getController();
        controller.setYapBotGui(new YapBotGui());

        this.stage = stage;
        stage.setScene(new Scene(root));
        stage.show();
        stage.toFront();
        stage.requestFocus();
    }

    @AfterEach
    public void resetStorage() {
        Storage.resetTargetPathForTesting();
    }

    @Test
    public void typingTodoCommand_showsConfirmationInConversation() {
        clickOn("#userInput");
        write("todo read book");
        push(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(anyLabelContains("Got it. I've added this task:"));
        assertTrue(anyLabelContains("read book"));
    }

    @Test
    public void typingByeCommand_showsGoodbyeAndClosesWindow() throws Exception {
        clickOn("#userInput");
        write("bye");
        push(KeyCode.ENTER);
        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(anyLabelContains("Bye. Hope to see you again soon!"));
        WaitForAsyncUtils.waitFor(3, TimeUnit.SECONDS, () -> !stage.isShowing());
    }

    private boolean anyLabelContains(String text) {
        Set<Node> labels = lookup(".label").queryAll();
        return labels.stream()
                .map(node -> (Label) node)
                .anyMatch(label -> label.getText().contains(text));
    }
}
