package Monopoly;

import Controllers.ControllerRegistry;
import Controllers.MainMenuController;
import Views.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Monopoly extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ControllerRegistry.put(new MainMenuController());
        MainMenuController mmc = (MainMenuController) ControllerRegistry.get(MainMenuController.class);
        //MainMenuController mmc = new MainMenuController();
        //MainMenuView mainMenuView = new MainMenuView(primaryStage);
    }
}
