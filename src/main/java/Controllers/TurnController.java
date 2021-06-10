package Controllers;

import Firestore.FirestoreFormattable;
import Models.Player;
import Models.Throw;
import Models.Turn;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class TurnController implements Controller, FirestoreFormattable {

    private Turn turn;

    public TurnController() {
        turn = new Turn();
    }

    public Throw getCurrentThrow() {
        ThrowController throwController = (ThrowController) ControllerRegistry.get(ThrowController.class);
        return throwController.getCurrentThrow();
    }

    @Override
    public Object getFirestoreFormat() {
        //Map<String, Object> map = new HashMap<>();
        return turn.getFirestoreFormat();
    }
}
