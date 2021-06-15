package Controllers;

import Models.Board;
import Models.Location;
import Models.Player;
import Models.Wallet;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.beans.Observable;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private GridPane BoardViewPlayerPane;

    public void movePlayerOnBoard(Players player) {
        int playerNumber = player.ordinal();
        int oldPosition = 0; //Maybe save the old position of the player or do a for loop to find a specific playerIcon on the board.
        int newPosition = 11; //This would be the player new player position. Player.getPosition() I guess.

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
    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        System.out.println("platte asser");
        notifyObservers();
    }


}
