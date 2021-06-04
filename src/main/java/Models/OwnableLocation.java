package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class OwnableLocation extends Location {
    private int price;
    private Player owner = null;
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

    public Optional<Player> getOwner() {
        if (owner == null) {
            return Optional.empty();
        }
        return Optional.of(owner);
    }
}
