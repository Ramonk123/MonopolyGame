package Models;

import ObserveablePattern.Observer;
import Views.*;
import com.google.cloud.firestore.DocumentSnapshot;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * Model for the board which mostly has methods for the BoardView view.
 */
public class Board implements BoardSubject, Observer<DocumentSnapshot>, HasStage {
    private final List<Observer<BoardSubject>> observers = new ArrayList<>();

    public Board() {
        registerObserver(new BoardView());
    }

    @Override
    public void registerObserver(Observer<BoardSubject> observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (Observer<BoardSubject> observer : observers) {
            observer.update(this);
        }
    }

    @Override
    public void update(DocumentSnapshot state) {
        // perform updates here
        notifyObservers();
    }

    @Override
    public List<Location> getLocations() {
        return null;
    }

    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Override
    public void setStage(Stage primaryStage) {
        ((BoardView) observers.get(0)).setStage(primaryStage);
    }

}
