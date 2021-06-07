package Models;

import Views.BoardSubject;
import Views.BoardView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Board implements Model, BoardSubject {
    private final List<View> observers = new ArrayList<>();

    @Override
    public void registerObserver(View v) {
        observers.add(v);
    }

    @Override
    public void unregisterObserver(View v) {
        observers.remove(v);
    }

    @Override
    public void notifyObservers(DocumentSnapshot ds) {
        for(View v : observers) {
            v.update(ds);
        }
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
    public void registerObserver(BoardView o) {

    }

    @Override
    public void unregisterObserver(BoardView o) {

    }

    @Override
    public void notifyObservers(BoardSubject state) {

    }
}
