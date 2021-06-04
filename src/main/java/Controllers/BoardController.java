package Controllers;

import Models.Board;
import Models.Wallet;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class BoardController implements Controller {

    private static BoardController boardController;
    private Board board;

    private BoardController() {
        board = new Board();
    }

    public static BoardController getInstance() {
        if(boardController == null) {
            boardController = new BoardController();
        }
        return boardController;
    }

    @Override
    public void registerObserver(View v) {
        board.registerObserver(v);
    }

    @Override
    public void unregisterObserver(View v) {
        board.unregisterObserver(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        board.notifyObservers(ds);
    }
}
