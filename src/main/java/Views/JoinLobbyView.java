package Views;

import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JoinLobbyView implements View {
    //Screensize
    int WIDTH = 600;
    int HEIGHT = 400;

    private final Stage primaryStage;

    private final joinLobbyController;

    public MainMenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;

        joinLobbyController = joinLobbyController.getInstance();
        joinLobbyController.registerObserver(this);

        try {
            createPrimaryStage();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DocumentSnapshot ds) {

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/FXML/JoinLobbyView.fxml"));
        loader.setController(joinLobbyController);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }
}
