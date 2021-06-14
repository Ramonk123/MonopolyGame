package Controllers;

import Models.Board;
import Models.Player;
import Models.Wallet;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import javax.annotation.Nullable;
import java.util.ArrayList;
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

    public Pair<Double, Double> getBoardViewBoardPanePosition(Pair<Integer, Integer> positionPair) {
        double x = BoardViewBoardPane.getCellBounds(positionPair.getKey(), positionPair.getValue()).getMinX();
        double y = BoardViewBoardPane.getCellBounds(positionPair.getKey(), positionPair.getValue()).getMinY();
        return new Pair<Double, Double>(x, y);
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

    public ArrayList<Label> getUsernameArray() {
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
