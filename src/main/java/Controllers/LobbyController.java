package Controllers;

import Models.Board;
import Models.MainMenu;
import Models.Lobby;
import Models.Player;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
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
import java.util.Random;

public class LobbyController implements Controller, Subject<DocumentSnapshot>, HasStage {

    private Lobby lobby;
    private DocumentSnapshot ds;

    private int token;
    private String name;

    public LobbyController() {
        lobby = new Lobby();
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void notifyObservers() {
        lobby.update(ds);
    }

    @Override
    public void setStage(Stage primaryStage) {
        lobby.setStage(primaryStage);
    }

    public void setCreateLobbyStage(Stage primaryStage) {
        lobby.setCreateLobbyStage(primaryStage);
    }

    public void setJoinLobbyStage(Stage primaryStage) {
        lobby.setJoinLobbyStage(primaryStage);
    }

    @FXML
    private void returnToMainMenu(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        MainMenuController mmc = (MainMenuController) ControllerRegistry.get(MainMenuController.class);
        mmc.setStage(primaryStage);
    }

    private void goToLobby(ActionEvent e) {
        Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
        lobby.setStage(primaryStage);
    }

    private void addPlayerToLobby(String name) {
        Player player = getPlayerByName(name);
        //TODO:
        // Stuff to add the player to firebase
    }

    private void removePlayerFromLobby() {
        //TODO:
        // Stuff to remove the player from firebase
        // Question is: "How do you know which player pressed the leave button?"
    }

    private Player getPlayerByName(String name) {
        PlayerController pc = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return pc.getPlayerByName(name);
    }

    private boolean playerNameExists(String name) {
        PlayerController pc = (PlayerController) ControllerRegistry.get(PlayerController.class);
        if(pc.nameExists(name)) {
            return true;
        }
        return false;
    }

    //Join Lobby
    @FXML
    private TextField JoinLobbyViewTokenTextField;
    @FXML
    private TextField JoinLobbyViewNameTextField;
    @FXML
    private void JoinLobbySubmit(ActionEvent e) throws IOException {
        try {
            token = Integer.parseInt(JoinLobbyViewTokenTextField.getText());
            name = JoinLobbyViewNameTextField.getText();

            joinLobby(e, name);
            goToLobby(e);
        } catch(NumberFormatException exception) {
            JoinLobbyViewTokenTextField.setText("Numbers Only");
        }
    }

    private void joinLobby(ActionEvent e, String name) {
        //TODO:
        // Program should first check if the lobby exists or is full, and then add the player.
        if(!playerNameExists(name)) {
            addPlayerToLobby(name);
        }
        JoinLobbyViewNameTextField.setText("Name already exists");
    }

    //Create Lobby
    @FXML
    private TextField CreateLobbyViewNameTextField;
    @FXML
    private void CreateLobbySubmit(ActionEvent e) throws IOException {
        //TODO:
        // Lobby should get created/checked if the token already exists in the while loop
        boolean isCreatingLobby = true;

        while(isCreatingLobby) {
            Random random = new Random();
            token = random.nextInt(6);
            //TODO:
            // if lobby token does not already exist continue to create it
                createLobby(e);
                isCreatingLobby = false;

                name = CreateLobbyViewNameTextField.getText();
                PlayerController pc = (PlayerController) ControllerRegistry.get(PlayerController.class);
                pc.setPlayer(name);

                //Open the lobby view
                goToLobby(e);
        }
    }

    private void createLobby(ActionEvent e) {
        //TODO:
        // Fill this method with stuff to create the lobby

    }

    //Lobby


    // Leave Lobby button functionality
    @FXML
    Pane ConfirmToMenuView;
    @FXML
    private void LeaveLobby(ActionEvent e) {
        ConfirmToMenuView.setVisible(true);
    }
    @FXML
    private void ConfirmLeaveLobby(ActionEvent e) {
        //TODO:
        // Method needs to remove player from lobby document in Firestore
        removePlayerFromLobby();
        returnToMainMenu(e);
    }
    @FXML
    private void DenyLeaveLobby(ActionEvent e) {
        ConfirmToMenuView.setVisible(false);
    }

}
