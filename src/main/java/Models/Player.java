package Models;

import ObserveablePattern.Observer;
import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Player implements Model, Position {
    private final List<View> observers = new ArrayList<>();

    private String name;
    private String pawn_icon;
    private Wallet wallet;
    private int position;

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
    public int getPosition() {
        return position;
    }
}
