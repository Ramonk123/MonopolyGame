package Monopoly;

import Views.MainMenuView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Monopoly extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        MainMenuView mainMenuView = new MainMenuView(primaryStage);
    }
}
