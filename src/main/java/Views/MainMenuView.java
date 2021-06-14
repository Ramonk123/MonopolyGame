package Views;

import Controllers.ControllerRegistry;
import Controllers.MainMenuController;
import ObserveablePattern.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuView implements View, Observer<MainMenuSubject>, HasStage {
    //Screensize
    int WIDTH = 630;
    int HEIGHT = 600;

    private Stage primaryStage;

    public MainMenuView() {

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainMenuView.fxml"));
        loader.setController(ControllerRegistry.get(MainMenuController.class));
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
    public void update(MainMenuSubject state) {

    }
}
