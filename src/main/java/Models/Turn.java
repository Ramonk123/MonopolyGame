package Models;

import Views.View;
import com.google.cloud.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class Turn implements Model, DiceThrower {
    private Player player;
    private int amount_of_double;
    private Throw current_throw;


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

    public Player getCurrentPlayer() {
        return player;
    }

    public Throw getCurrentThrow() {
        return current_throw;
    }

    @Override
    public void throwDice() {
        current_throw.throwDice();
    }

    @Override
    public boolean isDouble() {
        return current_throw.isDouble();
    }

    @Override
    public int getTotalEyes() {
        return current_throw.getTotalEyes();
    }
}
