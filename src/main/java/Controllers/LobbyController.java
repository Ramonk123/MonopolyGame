package Controllers;

import Models.Board;
import Models.MainMenu;
import Models.Lobby;
import Views.LobbyView;
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
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LobbyController implements Controller {

    private static LobbyController lobbyController;
    private Lobby lobby;

    private int token;
    private String name;

    private LobbyController() {
        lobby = new Lobby();
    }

    public static LobbyController getInstance() {
        if(lobbyController == null) {
            lobbyController = new LobbyController();
        }
        return lobbyController;
    }

    @Override
    public void registerObserver(View v) {
        lobby.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        lobby.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        lobby.notifyObservers(ds);
    }

    private void goToLobby(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        LobbyView lobbyView = new LobbyView(primaryStage);
    }

    //Return to Main Menu
    @FXML
    private void returnToMainMenu(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        MainMenuView returnToMainMenuView = new MainMenuView(primaryStage);
    }

    //Join Lobby
    @FXML
    private TextField JoinLobbyViewTokenTextField;
    @FXML
    private TextField JoinLobbyViewNameTextField;
    @FXML
    private void JoinLobbySubmit(ActionEvent e) throws IOException {
        token = Integer.parseInt(JoinLobbyViewTokenTextField.getText());
        name = JoinLobbyViewNameTextField.getText();
        joinLobby(e);
    }

    private void joinLobby(ActionEvent e) {
        //TODO:
        //Program should first check if the lobby exists and if the name is not already used.
        goToLobby(e);
    }

    //Create Lobby
    @FXML
    private TextField CreateLobbyViewNameTextField;
    @FXML
    private void CreateLobbySubmit(ActionEvent e) throws IOException {
        //TODO:
        //create random 6 digit token and check if that token doesn't exists.
        name = CreateLobbyViewNameTextField.getText();
        createLobby(e);
    }

    private void createLobby(ActionEvent e) {
        //TODO:
        //Program should first make the lobby before the user can join it.
        goToLobby(e);
    }

    //Lobby


    // Leave Lobby button functionality
    @FXML Pane ConfirmToMenuView;
    @FXML Button GoToMainMenuYesBtn;
    @FXML Button GoToMainMenuNoBtn;
    @FXML
    private void goToMainMenu() {
        //TODO:
        //Method needs to remove player from lobby document in Firestore
        ConfirmToMenuView.setVisible(!ConfirmToMenuView.isVisible());
        GoToMainMenuYesBtn.setOnAction(e -> {
            Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
            MainMenuView mainMenuView = new MainMenuView(primaryStage);
        });


        GoToMainMenuNoBtn.setOnAction(event -> {
            ConfirmToMenuView.setVisible(false);
        });
    }
}
