package Monopoly;

import Controllers.*;
import Firestore.Firestore;
import ObserveablePattern.Observer;
import Resetter.GameResetter;
import Resetter.Resettable;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class Monopoly extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        ControllerRegistry.register(new MainMenuController());
        ControllerRegistry.register(new BoardController());
        ControllerRegistry.register(new LobbyController());
        ControllerRegistry.register(new LocationController());
        ControllerRegistry.register(new CardDeckController());
        ControllerRegistry.register(new PlayerController());
        ControllerRegistry.register(new FireStoreController());
        ControllerRegistry.register(new TurnController());

        GameResetter.attachResettable((Resettable) ControllerRegistry.get(PlayerController.class));
        GameResetter.attachResettable((Resettable) ControllerRegistry.get(LobbyController.class));
        GameResetter.attachResettable((Resettable) ControllerRegistry.get(TurnController.class));

        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);

        fireStoreController.registerObserver((Observer<DocumentSnapshot>) ControllerRegistry.get(PlayerController.class));
        fireStoreController.registerObserver((Observer<DocumentSnapshot>) ControllerRegistry.get(LobbyController.class));
        fireStoreController.registerObserver((Observer<DocumentSnapshot>) ControllerRegistry.get(BoardController.class));
        fireStoreController.registerObserver((Observer<DocumentSnapshot>) ControllerRegistry.get(TurnController.class));

        MainMenuController mainMenuController = (MainMenuController) ControllerRegistry.get(MainMenuController.class);
        mainMenuController.setStage(primaryStage);

    }
}
