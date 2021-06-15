package Controllers;

import Models.Board;
import Models.Location;
import Models.Player;
import Models.Wallet;
import Monopoly.UUID;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;
import jdk.jshell.EvalException;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Board model & BoardView view.
 */
public class BoardController implements Controller, Subject<DocumentSnapshot>, Observer<DocumentSnapshot>, HasStage {

    private Board board;

    private DocumentSnapshot documentSnapshot;

    public BoardController() {
        board = new Board();
    }

    @FXML
    private Pane BackgroundImageView;

    @FXML
    private GridPane BoardViewBoardPane;

    public Pair<Double, Double> getBoardViewBoardPanePosition(Pair<Integer, Integer> gridPositionPair) {
        double x = BoardViewBoardPane.getCellBounds(gridPositionPair.getKey(), gridPositionPair.getValue()).getMinX();
        double y = BoardViewBoardPane.getCellBounds(gridPositionPair.getKey(), gridPositionPair.getValue()).getMinY();
        return new Pair<Double, Double>(x, y);
    }

    public Pair<Double, Double> getGridSize(Pair<Integer, Integer> gridPositionPair) {
        double width = BoardViewBoardPane.getWidth();
        double height = BoardViewBoardPane.getHeight();
        return new Pair<Double, Double>(width, height);
    }

    @FXML
    private void RollDiceAction(ActionEvent actionEvent) throws BoardException {
        //TODO:
        /* Sample code that should be mostly done in the TurnController
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        Players currentPlayerEnum = turnController.getCurrentPlayer();

        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        Players clientPlayerEnum = playerController.getClientPlayersEnum();

        if(UUID.compare(currentPlayerEnum, clientPlayerEnum)) {
            ThrowController throwController = (ThrowController) ControllerRegistry.get(ThrowController.class);
            throwController.throwDice();
            int amountThrown = throwController.getTotalEyes();
            int thrownDouble = 0;
            if (throwController.isDouble()) {
                thrownDouble++;
                while (throwController.isDouble()) {
                    if (thrownDouble >= 3) {
                        //TODO: Go to Jail
                    }
                    throwController.throwDice();
                    amountThrown += throwController.getTotalEyes();
                    thrownDouble++;
                }
            }

            Player currentPlayer = playerController.getPlayerByPlayersEnum(currentPlayerEnum).orElseThrow(() -> new BoardException("Player NOT Found"));

            int currentPlayerPosition = currentPlayer.getPosition();
            currentPlayer.movePlayer(amountThrown);
            int newPlayerPosition = currentPlayer.getPosition();
            movePlayerOnBoard(currentPlayerEnum, currentPlayerPosition, newPlayerPosition);
        }
        */
    }

    @FXML
    private GridPane BoardViewPlayerPane;

    public void movePlayerOnBoard(Players player, int oldPosition, int newPosition) {
        int playerNumber = player.ordinal();

        ObservableList<Node> boardArray = BoardViewPlayerPane.getChildren(); //Sets the whole board in an array/list
        ObservableList<Node> currentPlayerGrid = ((GridPane) boardArray.get(oldPosition)).getChildren(); //Gets the current grid the playerIcon is on
        Pane playerIcon = (Pane) currentPlayerGrid.get(playerNumber); //Gets the playerIcon out of the array/list
        currentPlayerGrid.remove(playerNumber);
        ObservableList<Node> newPlayerGrid = ((GridPane) boardArray.get(newPosition)).getChildren(); //Gets the grid where the player moved to
        newPlayerGrid.add(playerNumber, playerIcon);
    }

    public Location playerStandsOn(Player player) { //Player prob gets changed to Players
        int playerPosition = player.getPosition();
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        List<Location> locationArray = locationController.getLocationArray();
        return locationArray.get(playerPosition);
    }

    public void setBackgroundImageView() {
        BackgroundImage backgroundImage= new BackgroundImage(new Image("/FXML/IMG/background.png",600,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BackgroundImageView.setBackground(new Background(backgroundImage));
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void notifyObservers() {
        board.update(documentSnapshot);
    }

    @Override
    public void setStage(Stage primaryStage) {
        board.setStage(primaryStage);
    }

    @FXML Label BoardViewUsername1Label;
    @FXML Label BoardViewUsername2Label;
    @FXML Label BoardViewUsername3Label;
    @FXML Label BoardViewUsername4Label;
    @FXML Label BoardViewUsername5Label;
    @FXML Label BoardViewUsername6Label;
    @FXML Label BoardViewUsername7Label;
    @FXML Label BoardViewUsername8Label;

    public ArrayList<Label> getUserLabelList() {
        ArrayList<Label> labelList = new ArrayList<>();
        labelList.add(BoardViewUsername1Label);
        labelList.add(BoardViewUsername2Label);
        labelList.add(BoardViewUsername3Label);
        labelList.add(BoardViewUsername4Label);
        labelList.add(BoardViewUsername5Label);
        labelList.add(BoardViewUsername6Label);
        labelList.add(BoardViewUsername7Label);
        labelList.add(BoardViewUsername8Label);
        return labelList;

    }

    public boolean checkGameHasStarted() {
        FireStoreController fireStoreController = (FireStoreController) ControllerRegistry.get(FireStoreController.class);
        try {
            LobbyController lobbyController = (LobbyController) ControllerRegistry.get(LobbyController.class);
            int token = lobbyController.getToken();
            boolean value = fireStoreController.gameHasStarted(token);
            return value;
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } return false;

    }
    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        System.out.println("platte asser");
        notifyObservers();
    }


}
