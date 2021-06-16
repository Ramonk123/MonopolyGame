package Controllers;

import Exceptions.PlayerException;
import Models.Board;
import Models.Location;
import Models.Player;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Controller for the Board model & BoardView view.
 */
public class BoardController implements Controller, Subject<DocumentSnapshot>, Observer<DocumentSnapshot>, HasStage {

    private Board board = new Board();

    private DocumentSnapshot documentSnapshot;

    public BoardController() {
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
    private void RollDiceAction(ActionEvent actionEvent) throws PlayerException {
        TurnController turnController = (TurnController) ControllerRegistry.get(TurnController.class);
        turnController.RollDice();
    }

    @FXML
    private GridPane BoardViewPlayerPane;

    public void movePlayerOnBoard(Players player, long oldPosition, long newPosition) {
        int playerNumber = player.ordinal();

        ObservableList<Node> boardArray = BoardViewPlayerPane.getChildren(); //Sets the whole board in an array/list
        ObservableList<Node> currentPlayerGrid = ((GridPane) boardArray.get((int) oldPosition)).getChildren(); //Gets the current grid the playerIcon is on
        Pane playerIcon = (Pane) currentPlayerGrid.get(playerNumber); //Gets the playerIcon out of the array/list
        currentPlayerGrid.remove(playerNumber);
        ObservableList<Node> newPlayerGrid = ((GridPane) boardArray.get((int) newPosition)).getChildren(); //Gets the grid where the player moved to
        newPlayerGrid.add(playerNumber, playerIcon);
    }

    public Location playerStandsOn(Player player) { //Player prob gets changed to Players
        long playerPosition = player.getPosition();
        LocationController locationController = (LocationController) ControllerRegistry.get(LocationController.class);
        List<Location> locationArray = locationController.getLocationArray();
        return locationArray.get((int) playerPosition);
    }

    public void setBackgroundImageView() {
        BackgroundImage backgroundImage= new BackgroundImage(new Image("/FXML/IMG/background.png",600,400,false,true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        BackgroundImageView.setBackground(new Background(backgroundImage));
    }

    public void setPlayerIcons() {
        ObservableList<Node> boardArray = BoardViewPlayerPane.getChildren();
        ObservableList<Node> currentPlayerGrid = ((GridPane) boardArray.get(0)).getChildren();
        PlayerController playerController = (PlayerController) ControllerRegistry.get(PlayerController.class);
        for(int i = 0; i < playerController.getPlayers().size(); i++) {
            setPlayerIcon((Pane) currentPlayerGrid.get(i), i+1);
        }
    }

    private void setPlayerIcon(Pane playerPane, int playerNumber) {
        String URL = "/FXML/Icons/player"+playerNumber+".png";
        BackgroundImage backgroundImage= new BackgroundImage(new Image(URL,playerPane.getHeight(),playerPane.getHeight(),false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        playerPane.setBackground(new Background(backgroundImage));
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
        setPlayerIcons();
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
            return fireStoreController.gameHasStarted(token);
        } catch (ExecutionException | InterruptedException exception) {
            exception.printStackTrace();
        }
        return false;

    }
    @Override
    public void update(DocumentSnapshot documentSnapshot) {
        this.documentSnapshot = documentSnapshot;
        System.out.println("platte asser");
        notifyObservers();
    }

    @FXML
    private void placeBid(ActionEvent actionEvent) {
        //Do shit
    }

}
