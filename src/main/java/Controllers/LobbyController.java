package Controllers;

import Firestore.FirestoreFormattable;
import Models.*;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class LobbyController
        implements
            Controller,
            Subject<DocumentSnapshot>,
            HasStage,
            FirestoreFormattable,
            Observer<DocumentSnapshot> {

    private Lobby lobby;
    private DocumentSnapshot documentSnapshot;

    private int token;
    private String name;

    public LobbyController() {
        lobby = new Lobby();
    }

    // MINE - Kadir
    public void joinLobby(int token) throws LobbyException {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        int lobbySize = 0;
        try {
            lobbySize = fireStoreController.getLobbySize(token);
        } catch (Throwable e) {
            throw new LobbyException("Exception at LobbyController@joinLobby", e);
        }
        Players playersEnum = Players.getByOrder(lobbySize + 1)
                .orElseThrow(() -> new LobbyException("Exception at LobbyController@joinLobby: The lobby is full.", null));
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void notifyObservers() {
        lobby.update(documentSnapshot);
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
    private void returnToMainMenu(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        MainMenuController mainMenuController = (MainMenuController) ControllerRegistry.get(MainMenuController.class);
        mainMenuController.setStage(primaryStage);
    }

    private void goToLobby(ActionEvent actionEvent) {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        fireStoreController.listen(token);
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        lobby.setStage(primaryStage);
    }

    private void addPlayerToLobby(String name) {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        Optional<Player> player = getPlayerByName(name); // TODO: idk if this is supposed to be optional

        fireStoreController.addPlayer(token, player);
    }

    private void removePlayerFromLobby() throws InterruptedException, ExecutionException {
        //TODO:
        // Question is: "How do you know which player pressed the leave button?"

        //Added Firebase functionality assuming var player is the player who wants to leave the game.
        //Player player = new Player("Removeme"); // TODO: remove this of course
        //FireStoreController.removePlayer(token, player);
    }

    private Optional<Player> getPlayerByName(String name) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return playerController.getPlayerByName(name);
    }

    private boolean playerNameExists(String name) {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        return playerController.nameExists(name);
    }

    //Join Lobby
    @FXML
    private TextField JoinLobbyViewTokenTextField;
    @FXML
    private TextField JoinLobbyViewNameTextField;
    @FXML
    private void JoinLobbySubmit(ActionEvent actionEvent) throws IOException {
        try {
            token = Integer.parseInt(JoinLobbyViewTokenTextField.getText());
            name = JoinLobbyViewNameTextField.getText();

            joinLobby(actionEvent, name);
            goToLobby(actionEvent);
        } catch(NumberFormatException exception) {
            JoinLobbyViewTokenTextField.setText("Numbers Only");
        } catch (InterruptedException | ExecutionException interruptedException) {
            interruptedException.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private Pane LobbyAlreadyFullPopup;
    private void joinLobby(ActionEvent actionEvent, String name) throws Exception {
        //Added some functions, thought I could write the error messages while I'm at it. Feel free to change it.
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        if(!fireStoreController.checkExistence(token)){
            JoinLobbyViewTokenTextField.setText("This lobby does not exist");
        }

        if(fireStoreController.getLobbySize(token) >= 8){
            LobbyAlreadyFullPopup.setVisible(true);
        }

        if(!playerNameExists(name)) {
            playerController.setPlayer(name);

        }
        JoinLobbyViewNameTextField.setText("Name already exists");
    }

    //Create Lobby
    @FXML
    private TextField CreateLobbyViewNameTextField;
    @FXML
    private void CreateLobbySubmit(ActionEvent actionEvent)  {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        generateToken();

        //while(true){
            try {
                if (!fireStoreController.checkExistence(token)) {}//break;
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            generateToken();
        //}

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        name = CreateLobbyViewNameTextField.getText();
        try {
            playerController.setPlayer(name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(token);
        fireStoreController.createLobby(token);


        //Open the lobby view
        goToLobby(actionEvent);
    }

    private void generateToken(){
        Random random = new Random();
        token = random.nextInt(Integer.MAX_VALUE);
    }

    //Lobby

    // Leave Lobby button functionality
    @FXML
    private Pane ConfirmToMenuView;
    @FXML
    private void LeaveLobby(ActionEvent actionEvent) {
        ConfirmToMenuView.setVisible(true);
    }
    @FXML
    private void ConfirmLeaveLobby(ActionEvent actionEvent) throws InterruptedException, ExecutionException, IOException {
        removePlayerFromLobby();
        returnToMainMenu(actionEvent);
    }
    @FXML
    private void DenyLeaveLobby(ActionEvent actionEvent) {
        ConfirmToMenuView.setVisible(false);
    }

    @Override
    public Object getFirestoreFormat() {
        return lobby.getFirestoreFormat();
    }

    public int getToken() { return token;}

    @FXML Label LobbyViewUsername1Label;
    @FXML Label LobbyViewUsername2Label;
    @FXML Label LobbyViewUsername3Label;
    @FXML Label LobbyViewUsername4Label;
    @FXML Label LobbyViewUsername5Label;
    @FXML Label LobbyViewUsername6Label;
    @FXML Label LobbyViewUsername7Label;
    @FXML Label LobbyViewUsername8Label;

    public ArrayList<Label> getUserLabelList() {
        ArrayList<Label> labelList = new ArrayList<>();
        labelList.add(LobbyViewUsername1Label);
        labelList.add(LobbyViewUsername2Label);
        labelList.add(LobbyViewUsername3Label);
        labelList.add(LobbyViewUsername4Label);
        labelList.add(LobbyViewUsername5Label);
        labelList.add(LobbyViewUsername6Label);
        labelList.add(LobbyViewUsername7Label);
        labelList.add(LobbyViewUsername8Label);

        return labelList;
    }
    @FXML Label LobbyViewTokenLabel;
    public Label getTokenLabel() {
        return LobbyViewTokenLabel;
    }

    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        notifyObservers();
    }
}