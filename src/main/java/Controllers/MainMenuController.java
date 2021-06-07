package Controllers;

import Models.Board;
import Models.MainMenu;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.CreateLobbyView;
import Views.JoinLobbyView;
import Views.MainMenuView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.apache.log4j.chainsaw.Main;

import java.io.IOException;

public class MainMenuController implements Controller, Subject<DocumentSnapshot> {

    private static MainMenuController mainMenuController;
    private MainMenu mainMenu;
    private DocumentSnapshot ds;

    public MainMenuController() {
        mainMenu = new MainMenu();
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void notifyObservers() {
        mainMenu.update(ds);
    }

    public static MainMenuController getInstance() {
        if(mainMenuController == null) {
            mainMenuController = new MainMenuController();
        }
        return mainMenuController;
    }

    //Join Lobby
    @FXML
    private void goToJoinLobby(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        JoinLobbyView joinLobbyView = new JoinLobbyView(primaryStage);
    }

    //Create Lobby
    @FXML
    private void goToCreateLobby(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        CreateLobbyView createLobbyView = new CreateLobbyView(primaryStage);
    }

    //Quit button
    @FXML
    private Pane ConfirmQuitScreen;
    @FXML
    public void displayQuitScreen() {
        ConfirmQuitScreen.setVisible(!ConfirmQuitScreen.isVisible());
    }
    @FXML
    public void ConfirmQuitGame() {
        mainMenu.quitGame();
    }
}
