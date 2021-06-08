package Controllers;

import Models.MainMenu;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.*;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuController implements Controller, Subject<DocumentSnapshot>, HasStage {

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

    @Override
    public void setStage(Stage primaryStage) {
        mainMenu.setStage(primaryStage);
    }

    //Join Lobby
    @FXML
    private void goToJoinLobby(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        LobbyController lc = (LobbyController) ControllerRegistry.get(LobbyController.class);
        lc.setJoinLobbyStage(primaryStage);
    }

    //Create Lobby
    @FXML
    private void goToCreateLobby(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        LobbyController lc = (LobbyController) ControllerRegistry.get(LobbyController.class);
        lc.setCreateLobbyStage(primaryStage);
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
