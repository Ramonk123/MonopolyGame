package Models;

import ObserveablePattern.Observer;
import Views.BoardSubject;
import Views.BoardView;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Board implements Model, BoardSubject {
    private final List<Observer<BoardSubject>> observers = new ArrayList<>();

    @Override
    public void registerObserver(Observer<BoardSubject> o) {

    }

    @Override
    public void unregisterObserver(Observer<BoardSubject> o) {

    }

    @Override
    public void notifyObservers(BoardSubject state) {

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
