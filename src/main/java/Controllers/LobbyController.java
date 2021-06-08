package Controllers;

import Models.*;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

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
        //Player player = getPlayerByName(name);
        //TODO:
        // Stuff to add the player to firebase
    }

    private void removePlayerFromLobby() {
        //TODO:
        // Stuff to remove the player from firebase
        // Question is: "How do you know which player pressed the leave button?"
    }

    private Optional<Player> getPlayerByName(String name) {
        PlayerController pc = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return pc.getPlayerByName(name);
    }

    private boolean playerNameExists(String name) {
        PlayerController pc = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return pc.nameExists(name);
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
        } catch (InterruptedException | ExecutionException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    private void joinLobby(ActionEvent e, String name) throws InterruptedException, ExecutionException, IOException {
        //Added some functions, thought I could write the error messages while I'm at it. Feel free to change it.
        if(!FireStoreController.checkExistence(token)){
            JoinLobbyViewTokenTextField.setText("This lobby does not exist");
        }

        if(FireStoreController.getLobbySize(token) >= 8){
            //Lobby is full
            //TODO:
            // add pop up
        }

        if(!playerNameExists(name)) {
            addPlayerToLobby(name);
        }
        JoinLobbyViewNameTextField.setText("Name already exists");
    }

    //Create Lobby
    @FXML
    private TextField CreateLobbyViewNameTextField;
    @FXML
    private void CreateLobbySubmit(ActionEvent e) throws InterruptedException, ExecutionException, IOException {
        generateToken();

        while(FireStoreController.checkExistence(token)){
            generateToken();
        }


        FireStoreController.createLobby(token);

        name = CreateLobbyViewNameTextField.getText();
        PlayerController pc = (PlayerController) ControllerRegistry.get(PlayerController.class);
        pc.setPlayer(name);

        //Open the lobby view
        goToLobby(e);

    }

    private void generateToken(){
        Random random = new Random();
        token = random.nextInt(6);
    }

// TODO: Think we can delete this

//    private void createLobby(ActionEvent e) throws IOException {
//        FireStoreController.createLobby(token);
//
//    }

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
