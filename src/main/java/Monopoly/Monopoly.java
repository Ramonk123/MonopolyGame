package Monopoly;

import Controllers.BoardController;
import Controllers.ControllerRegistry;
import Controllers.LobbyController;
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
        ControllerRegistry.put(new BoardController());
        ControllerRegistry.put(new LobbyController());

        // Voorbeeld van opvraging Controller.
        //MainMenuController mmc = (MainMenuController) ControllerRegistry.get(MainMenuController.class);

        //MainMenuView mainMenuView = new MainMenuView(primaryStage);
    }
}
