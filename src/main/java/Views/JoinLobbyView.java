package Views;

import Controllers.ControllerRegistry;
import Controllers.LobbyController;
import Controllers.MainMenuController;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Sets the Stage/Scene for the JoinLobbyView.fxml and has additional methods that add things to the view.
 */
public class JoinLobbyView implements View, Observer<LobbySubject>, HasStage {
    //Screensize
    int WIDTH = 600;
    int HEIGHT = 400;

    private Stage primaryStage;

    public JoinLobbyView() {

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/JoinLobbyView.fxml"));
        loader.setController(ControllerRegistry.get(LobbyController.class));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }

    @Override
    public void setStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
        try {
            createPrimaryStage();
        } catch(IOException ioException) {
            ioException.printStackTrace();
        }
    }

    @Override
    public void update(LobbySubject state) {

    }
}
