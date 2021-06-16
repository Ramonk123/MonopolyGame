package Controllers;

import Exceptions.LobbyException;
import Firestore.FirestoreFormattable;
import Models.*;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Resetter.GameResetter;
import Resetter.Resettable;
import Views.HasStage;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Lobby model & LobbyView, CreateLobbyView and JoinLobbyView views.
 */
public class LobbyController
        implements
            Controller,
            Subject<DocumentSnapshot>,
            HasStage,
            FirestoreFormattable,
            Observer<DocumentSnapshot>,
            Resettable {

    private Lobby lobby;
    private DocumentSnapshot documentSnapshot;

    private int token;
    private String name;
    private boolean gameHasStarted;

    public LobbyController() {
        reset();
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
        GameResetter.reset();
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

    /*private void addPlayerToLobby(String name) {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        Optional<Player> player = getPlayerByName(name); // TODO: idk if this is supposed to be optional

        fireStoreController.addPlayer(token, player);
    }*/

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

    /**
     * Submits the data in the textfields to the LobbyController.
     * @param actionEvent ActionEvent contains all the data about the event.
     * @throws IOException
     */
    @FXML
    private void JoinLobbySubmit(ActionEvent actionEvent) throws IOException {
        try {
            token = Integer.parseInt(JoinLobbyViewTokenTextField.getText());
            name = JoinLobbyViewNameTextField.getText();

            FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
            PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);

            joinLobby(actionEvent, name);
            goToLobby(actionEvent);
            int order = fireStoreController.getLobbySize(token);
            Players playersEnum = Players.getByOrder(order + 1)
                    .orElseThrow( () -> new Exception("Order out of bounds"));
            playerController.setClientPlayersEnum(playersEnum);
            Player player = playerController.setPlayerWithPlayersEnum(playersEnum, name);
            fireStoreController.addPlayer(token, player);
        } catch(NumberFormatException numberFormatException) {
            JoinLobbyViewTokenTextField.setText("Numbers Only");
        } catch (Exception executionException) {
            executionException.printStackTrace();
        }
    }

    @FXML
    private Pane LobbyAlreadyFullPopup;
    private void joinLobby(ActionEvent actionEvent, String name) throws ExecutionException, InterruptedException {
        //Added some functions, thought I could write the error messages while I'm at it. Feel free to change it.
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        if(!fireStoreController.checkExistence(token)){
            JoinLobbyViewTokenTextField.setText("This lobby does not exist");
        }

        if(fireStoreController.getLobbySize(token) >= 8){
            LobbyAlreadyFullPopup.setVisible(true);
        }

        /*if(!playerNameExists(name)) {
            playerController.setPlayer(name);

        }
        JoinLobbyViewNameTextField.setText("Name already exists");*/
    }

    //Create Lobby
    @FXML
    private TextField CreateLobbyViewNameTextField;

    /**
     * Submits the data in the textfields to the LobbyController.
     * @param actionEvent ActionEvent contains all the data about the event.
     */
    @FXML
    private void CreateLobbySubmit(ActionEvent actionEvent)  {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        generateToken();

        //while(true){
            try {
                if (!fireStoreController.checkExistence(token)) {}//break;
            } catch (ExecutionException | InterruptedException e) {
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
    @FXML
    private void CopyToClipboard(ActionEvent actionEvent) {
        StringSelection selection = new StringSelection(Integer.toString(token));
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);
    }

    // Leave Lobby button functionality
    @FXML
    private Pane ConfirmToMenuView;
    @FXML
    private void LeaveLobby(ActionEvent actionEvent) {
        ConfirmToMenuView.setVisible(true);
    }

    /**
     * Removes player from database.
     * @param actionEvent ActionEvent contains all the data about the event.
     * @throws InterruptedException
     * @throws ExecutionException
     * @throws LobbyException
     */
    @FXML
    private void ConfirmLeaveLobby(ActionEvent actionEvent) throws InterruptedException, ExecutionException, LobbyException {
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);

        fireStoreController.removePlayer(token, playerController.getPlayerByPlayersEnum(playerController.getClientPlayersEnum())
                .orElseThrow(() -> new LobbyException("PLAYERS NOT HERE HELP", null)));
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

    @FXML
    private Label LobbyViewUsername1Label;
    @FXML
    private Label LobbyViewUsername2Label;
    @FXML
    private Label LobbyViewUsername3Label;
    @FXML
    private Label LobbyViewUsername4Label;
    @FXML
    private Label LobbyViewUsername5Label;
    @FXML
    private Label LobbyViewUsername6Label;
    @FXML
    private Label LobbyViewUsername7Label;
    @FXML
    private Label LobbyViewUsername8Label;

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
    @FXML
    private void goToGameViewFxml(ActionEvent actionEvent) {
        System.out.println("fxml ass");
        //System.out.println((Stage) ((Node) event.getSource()).getScene().getWindow());
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        fireStoreController.startGame(token);
    }


    private void goToGameView() {
        Stage primaryStage = lobby.getStage();
        System.out.println(primaryStage);
        Platform.runLater(() -> {
            BoardController boardController = (BoardController) ControllerRegistry.get(BoardController.class);
            boardController.setStage(primaryStage);
        });
    }
    @FXML Label LobbyViewTokenLabel;
    public Label getTokenLabel() {
        return LobbyViewTokenLabel;
    }

    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        System.out.println(state.get("gameHasStarted"));
        System.out.println("update");
        boolean boolAfterUpdate = (boolean) documentSnapshot.get("gameHasStarted");
        if (!gameHasStarted && boolAfterUpdate) {
            System.out.println("if statement");
            gameHasStarted = boolAfterUpdate;
            goToGameView();
        }
        notifyObservers();
    }

    @Override
    public void reset() {
        lobby = new Lobby();
        gameHasStarted = false;
    }
}