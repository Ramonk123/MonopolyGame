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

/**
 * Controller for the MainMenu model & MainMenuView view.
 */
public class MainMenuController implements Subject<DocumentSnapshot>, HasStage, Controller {

    private final MainMenu mainMenu = new MainMenu();
    private DocumentSnapshot documentSnapshot;

    public MainMenuController() {

    }

    public void setDocumentSnapshot(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void notifyObservers() {
        mainMenu.update(documentSnapshot);
    }

    @Override
    public void setStage(Stage primaryStage) {
        mainMenu.setStage(primaryStage);
    }

    //Join Lobby
    @FXML
    private void goToJoinLobby(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
        lobbyController.setJoinLobbyStage(primaryStage);
    }

    //Create Lobby
    @FXML
    private void goToCreateLobby(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
        lobbyController.setCreateLobbyStage(primaryStage);
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
