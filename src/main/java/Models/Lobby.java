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
        registerObserver(new CreateLobbyView());
        registerObserver(new JoinLobbyView());
    }

    @Override
    public void registerObserver(Observer<LobbySubject> observer) {
        observers.add(observer);
    }

    @Override
    public void unregisterObserver(Observer<LobbySubject> observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<LobbySubject> observer : observers) {
            observer.update(this);
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

    public void setCreateLobbyStage(Stage primaryStage) {
        ((CreateLobbyView) observers.get(1)).setStage(primaryStage);
    }

    public void setJoinLobbyStage(Stage primaryStage) {
        ((JoinLobbyView) observers.get(2)).setStage(primaryStage);
    }
}
