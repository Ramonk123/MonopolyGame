package Monopoly;

import Controllers.*;
import javafx.application.Application;
import javafx.stage.Stage;

public class Monopoly extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        ControllerRegistry.register(new MainMenuController());
        ControllerRegistry.register(new BoardController());
        ControllerRegistry.register(new LobbyController());
        ControllerRegistry.register(new CardDeckController());

        // Voorbeeld van opvraging Controller.
        MainMenuController mmc = (MainMenuController) ControllerRegistry.get(MainMenuController.class);
        mmc.setStage(primaryStage);

        //MainMenuView mainMenuView = new MainMenuView(primaryStage);
    }
}
