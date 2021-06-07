package Views;

import Controllers.ControllerRegistry;
import Controllers.LobbyController;
import Controllers.MainMenuController;
import ObserveablePattern.Observer;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CreateLobbyView implements View {
    //Screensize
    int WIDTH = 600;
    int HEIGHT = 400;

    private final Stage primaryStage;

    private LobbyController lobbyController;

    public CreateLobbyView(Stage primaryStage) {
        this.primaryStage = primaryStage;

        //lobbyController = lobbyController.getInstance();
        //lobbyController.registerObserver(this);

        try {
            createPrimaryStage();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/CreateLobbyView.fxml"));
        loader.setController((LobbyController) ControllerRegistry.get(LobbyController.class));
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }
}
