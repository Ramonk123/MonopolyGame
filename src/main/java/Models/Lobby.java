package Models;

import ObserveablePattern.Observer;
import Views.HasStage;
import Views.LobbySubject;
import Views.MainMenuView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Lobby implements Model, LobbySubject, Observer<DocumentSnapshot> {
    private int gameToken;
    private int amountOfPlayers;

    private List<Observer<LobbySubject>> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer<LobbySubject> o) {
        observers.add(o);
    }

    @Override
    public void unregisterObserver(Observer<LobbySubject> o) {

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

}
