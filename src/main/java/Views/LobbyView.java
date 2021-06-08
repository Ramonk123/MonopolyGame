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

public class LobbyView implements View, Observer<LobbySubject>, HasStage {
    //Screensize
    int WIDTH = 600;
    int HEIGHT = 400;

    private Stage primaryStage;

    public LobbyView() {

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/LobbyView.fxml"));
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
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(LobbySubject state) {

    }

}
