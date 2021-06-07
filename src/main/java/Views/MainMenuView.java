package Views;

import Controllers.ControllerRegistry;
import Controllers.MainMenuController;
import ObserveablePattern.Observer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuView implements View, Observer<MainMenuSubject>, HasStage {
    //Screensize
    int WIDTH = 600;
    int HEIGHT = 400;

    private Stage primaryStage;

    private MainMenuController mainMenuController;

    public MainMenuView() {
        //mainMenuController = mainMenuController.getInstance();
        //mainMenuController.registerObserver(this);

    }

    private void createPrimaryStage() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/MainMenuView.fxml"));
        loader.setController((MainMenuController) ControllerRegistry.get(MainMenuController.class));
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
    public void update(MainMenuSubject state) {

    }
}
