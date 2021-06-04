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

    }

    @Override
    public void unregisterObserver(View v) {

    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {

    }
}
