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
    private DocumentSnapshot ds;

    public BoardController() {
        board = new Board();
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void notifyObservers() {
        board.update(ds);
    }

    @Override
    public void setStage(Stage primaryStage) {

    }
}
