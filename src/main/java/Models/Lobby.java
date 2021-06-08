package Models;

import ObserveablePattern.Observer;
import Views.*;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Lobby implements Model, LobbySubject, Observer<DocumentSnapshot>, HasStage {
    private List<Observer<LobbySubject>> observers = new ArrayList<>();

    public Lobby() {
        registerObserver(new LobbyView());
    }

    @Override
    public void registerObserver(Observer<LobbySubject> o) {
        observers.add(o);
    }

    @Override
    public void unregisterObserver(Observer<LobbySubject> o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers() {
        for (Observer<LobbySubject> o : observers) {
            o.update(this);
        }
    }

    @Override
    public void update(final DocumentSnapshot state) {
        // perform updates on this model
        notifyObservers();
    }

    @Override
    public void setStage(Stage primaryStage) {
        ((LobbyView) observers.get(0)).setStage(primaryStage);
    }
}
