package Controllers;

import Models.Board;
import Models.MainMenu;
import Views.MainMenuView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import org.apache.log4j.chainsaw.Main;

public class MainMenuController implements Controller {

    private static MainMenuController mainMenuController;
    private MainMenu mainMenu;

    private MainMenuController() {
        mainMenu = new MainMenu();
    }

    public static MainMenuController getInstance() {
        if(mainMenuController == null) {
            mainMenuController = new MainMenuController();
        }
        return mainMenuController;
    }

    @Override
    public void registerObserver(View v) {
        mainMenu.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        mainMenu.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        mainMenu.notifyObservers(ds);
    }

    @FXML
    private Pane ConfirmQuitScreen;

    public void displayQuitScreen() {
        ConfirmQuitScreen.setVisible(!ConfirmQuitScreen.isVisible());
    }

    public void ConfirmQuitGame() {
        mainMenu.quitGame();
    }
}
