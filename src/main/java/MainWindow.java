import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import wbb.WinterBearBot;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private WinterBearBot wbb;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/bearuser.gif"));
    private Image dukeImage = new Image(this.getClass().getResourceAsStream("/images/polarbear.gif"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        dialogContainer.getChildren().add(
                DialogBox.getDukeDialog(new WinterBearBot().ui.getWelcomeMessage(), dukeImage, "WelcomeMessage")
        );
    }

    /** Injects the Duke instance */
    public void setDuke(WinterBearBot w) {
        wbb = w;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = wbb.getResponse(input);
        String commandType = wbb.getCommandType();
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getDukeDialog(response, dukeImage, commandType)
        );
        userInput.clear();
    }
}