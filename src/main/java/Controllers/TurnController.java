package Controllers;

import Models.Player;
import Models.Turn;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

public class TurnController implements Controller {

    private static TurnController turnController;
    private Turn turn;

    private TurnController() {
        turn = new Turn();
    }

    public static TurnController getInstance() {
        if(turnController == null) {
            turnController = new TurnController();
        }
        return turnController;
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
