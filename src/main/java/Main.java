import java.io.IOException;

import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import wbb.WinterBearBot;
import wbb.exception.WBBException;

/**
 * A GUI for Duke using FXML.
 */
public class Main extends Application {

    private WinterBearBot wbb = new WinterBearBot();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            stage.setMinHeight(500);
            stage.setMinWidth(700);
            stage.setTitle("Winter Bear Bot");
            stage.getIcons().add(new Image(Main.class.getResourceAsStream("/images/bearicon.png")));
            fxmlLoader.<MainWindow>getController().setDuke(wbb);  // inject the Duke instance
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
