package Views;

import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuView implements View {
    //Screensize
    int WIDTH = 600;
    int HEIGHT = 400;

    private final Stage primaryStage;

    private final mainMenuCtrl;

    public MainMenuView(Stage primaryStage) {
        this.primaryStage = primaryStage;

        mainMenuCtrl = mainMenuCtrl.getInstance();
        mainMenuCtrl.registerObserver(this);

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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../resources/FXML/MainMenuView.fxml"));
        loader.setController(mainMenuCtrl);
        Parent root = loader.load();

        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));
        primaryStage.show();
    }
}
