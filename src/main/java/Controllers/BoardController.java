package Controllers;

import Models.Board;
import Models.Wallet;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class BoardController implements Controller, Subject<DocumentSnapshot> {

    private static BoardController boardController;
    private Board board;
    private DocumentSnapshot ds;

    public BoardController() {
        board = new Board();
    }

    public static BoardController getInstance() {
        if(boardController == null) {
            boardController = new BoardController();
        }
        return boardController;
    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> o) { }

    @Override
    public void notifyObservers() {
        board.update(ds);
    }
}
