package Controllers;

import Firestore.FirestoreFormattable;
import Models.Player;
import Models.Throw;
import Models.Turn;
import ObserveablePattern.Observer;
import ObserveablePattern.Subject;
import Resetter.Resettable;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.Map;

public class TurnController
        implements
            Controller,
            FirestoreFormattable,
            Observer<DocumentSnapshot>,
            Resettable,
            Subject<DocumentSnapshot> {

    private Turn turn;
    private DocumentSnapshot documentSnapshot;
    private int eyesThrown;

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

    @Override
    public void update(DocumentSnapshot state) {
        documentSnapshot = state;
        Map<String, Object> map = (Map<String, Object>) state.get("turn");
        eyesThrown = (int) map.get("eyesThrown");
        notifyObservers();
    }

    @Override
    public void reset() {

    }

    @Override
    public void registerObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void unregisterObserver(Observer<DocumentSnapshot> observer) { }

    @Override
    public void notifyObservers() {
        turn.update(documentSnapshot);
    }
}
