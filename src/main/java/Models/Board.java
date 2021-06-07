package Models;

import ObserveablePattern.Observer;
import Views.BoardSubject;
import Views.BoardView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Board implements Model, BoardSubject, Observer<DocumentSnapshot> {
    private List<Observer<BoardSubject>> observers = new ArrayList<>();

    public Board() {
        //this.observers.add(new BoardView());
    }

    @Override
    public void registerObserver(Observer<BoardSubject> o) {
        observers.add(o);
    }

    @Override
    public void unregisterObserver(Observer<BoardSubject> o) {

    }

    @Override
    public void notifyObservers() {
        for (Observer<BoardSubject> o : observers) {
            o.update(this);
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
}
