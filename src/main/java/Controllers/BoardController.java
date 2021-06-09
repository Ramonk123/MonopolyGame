package Controllers;

import Models.Board;
import Models.Wallet;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.HasStage;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.stage.Stage;

public class BoardController implements Controller, Subject<DocumentSnapshot>, HasStage {

    private Board board;
    private DocumentSnapshot documentSnapshot;

    public BoardController() {
        board = new Board();
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

    }
}
